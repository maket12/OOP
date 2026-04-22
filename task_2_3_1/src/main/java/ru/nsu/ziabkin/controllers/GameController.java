package ru.nsu.ziabkin.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import ru.nsu.ziabkin.models.Direction;
import ru.nsu.ziabkin.models.GameModel;
import ru.nsu.ziabkin.models.GameState;
import ru.nsu.ziabkin.models.Point;
import ru.nsu.ziabkin.models.Snake;

/**
 * Controller class that manages the game UI, input, and sounds.
 */
public class GameController implements Initializable {
    private static final int CELL_SIZE = 20;
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;

    @FXML private Canvas canvas;
    @FXML private VBox menuPane;
    @FXML private Label scoreLabel;
    @FXML private Label bestScoreLabel;
    @FXML private Label lastScoreLabel;

    private GameModel model;
    private AnimationTimer timer;
    private long lastUpdate = 0;

    private AudioClip eatSound;
    private AudioClip gameOverSound;
//    private MediaPlayer backgroundMusic;
    private final Preferences prefs = Preferences.userNodeForPackage(GameController.class);

    private static final int WIN_LENGTH = 25;

    /**
     * Initializes the controller, loads preferences and sounds.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bestScoreLabel.setText("Best: " + prefs.getInt("bestScore", 0));
        loadSounds();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPress);
    }

    /**
     * Loads audio resources for the game.
     */
    private void loadSounds() {
        try {
            URL eatUrl = getClass().getResource("/audio/eat.mp3");
            if (eatUrl != null) {
                eatSound = new AudioClip(eatUrl.toExternalForm());
                eatSound.setVolume(1.0);
            }

            URL dieUrl = getClass().getResource("/audio/game_over.mp3");
            if (dieUrl != null) {
                gameOverSound = new AudioClip(dieUrl.toExternalForm());
                gameOverSound.setVolume(1.0);
            }

//            URL bgUrl = getClass().getResource("/audio/background.mp3");
//            if (bgUrl != null) {
//                Media media = new Media(bgUrl.toExternalForm());
//                backgroundMusic = new MediaPlayer(media);
//                backgroundMusic.setCycleCount(MediaPlayer.INDEFINITE);
//                backgroundMusic.setVolume(0.2);
//            }
        } catch (Exception e) {
            System.err.println("Audio files missing or unsupported.");
        }
    }

    /**
     * Handles game over logic and plays the death sound for the player.
     */
    private void handleGameOver() {
        timer.stop();
//        if (backgroundMusic != null) {
//            backgroundMusic.stop();
//        }

        if (model.getState() == GameState.LOST && gameOverSound != null) {
            gameOverSound.play();
        }

        int current = model.getPlayerSnake().getBody().size() - 1;
        int best = prefs.getInt("bestScore", 0);
        if (current > best) {
            prefs.putInt("bestScore", current);
            bestScoreLabel.setText("Best: " + current);
        }
        lastScoreLabel.setText("Last Score: " + current);
        menuPane.setVisible(true);
    }

    /**
     * Starts the game session, spawns bots, and begins the music.
     */
    @FXML
    public void startGame() {
        menuPane.setVisible(false);
        model = new GameModel(WIDTH, HEIGHT, WIN_LENGTH, 3);

        // Adding 3 bots at the start
        model.getSnakes().add(new Snake(new Point(2, 2), true));
        model.getSnakes().add(new Snake(new Point(25, 15), true));
        model.getSnakes().add(new Snake(new Point(5, 15), true));

//        if (backgroundMusic != null) {
//            backgroundMusic.play();
//        }

        startTimer();
    }

    /**
     * Main animation loop.
     */
    private void startTimer() {
        if (timer != null) {
            timer.stop();
        }
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (now - lastUpdate >= 150_000_000) {
                    processTick();
                    lastUpdate = now;
                }
            }
        };
        timer.start();
    }

    /**
     * Processes one game tick: logic update and rendering.
     */
    private void processTick() {
        int oldLength = model.getPlayerSnake().getBody().size();
        model.update();

        if (model.getPlayerSnake().getBody().size() > oldLength && eatSound != null) {
            eatSound.play();
        }

        updateScoreUI();
        draw();

        if (model.getState() != GameState.PLAYING) {
            handleGameOver();
        }
    }

    /**
     * Updates the score on the UI labels.
     */
    private void updateScoreUI() {
        int current = model.getPlayerSnake().getBody().size() - 1;
        scoreLabel.setText("Score: " + current);
    }

    /**
     * Handles keyboard input for snake direction.
     * * @param event the key event
     */
    private void handleKeyPress(KeyEvent event) {
        if (model == null) {
            return;
        }
        KeyCode code = event.getCode();
        Snake player = model.getPlayerSnake();
        Direction current = player.getDirection();

        if (code == KeyCode.UP && current != Direction.DOWN) {
            player.setDirection(Direction.UP);
        } else if (code == KeyCode.DOWN && current != Direction.UP) {
            player.setDirection(Direction.DOWN);
        } else if (code == KeyCode.LEFT && current != Direction.RIGHT) {
            player.setDirection(Direction.LEFT);
        } else if (code == KeyCode.RIGHT && current != Direction.LEFT) {
            player.setDirection(Direction.RIGHT);
        }
    }

    /**
     * Draws the game state on the canvas.
     */
    private void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * CELL_SIZE, HEIGHT * CELL_SIZE);

        // Draw Food (Neon Red)
        gc.setFill(Color.web("#FF003C"));
        for (Point f : model.getFoods()) {
            gc.fillOval(f.getX() * CELL_SIZE, f.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Draw Snakes
        for (Snake s : model.getSnakes()) {
            if (s.isAlive()) {
                gc.setFill(s.isRobot() ? Color.web("#00F3FF") : Color.web("#00FF41"));
                for (Point p : s.getBody()) {
                    gc.fillRect(p.getX() * CELL_SIZE + 1, p.getY() * CELL_SIZE + 1,
                            CELL_SIZE - 2, CELL_SIZE - 2);
                }
            } else if (s.isRobot()) {
                drawDeathEffect(gc, s.getHead());
                }
        }
    }

    /**
     * Draws a simple flash effect when a snake dies.
     * * @param gc the graphics context
     * @param p the death location
     */
    private void drawDeathEffect(GraphicsContext gc, Point p) {
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(3);
        gc.strokeOval(p.getX() * CELL_SIZE - 5, p.getY() * CELL_SIZE - 5,
                CELL_SIZE + 10, CELL_SIZE + 10);
    }
}