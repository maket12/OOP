package ru.nsu.ziabkin.controllers;

import java.net.URL;
import java.util.prefs.Preferences;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import ru.nsu.ziabkin.models.Direction;
import ru.nsu.ziabkin.models.GameModel;
import ru.nsu.ziabkin.models.GameState;
import ru.nsu.ziabkin.models.Point;
import ru.nsu.ziabkin.models.Snake;
import ru.nsu.ziabkin.views.GameView;

/**
 * The Controller manages input, sounds, and the game loop by linking the Model and View.
 */
public class GameController {
    private static final int WIDTH = 30;
    private static final int HEIGHT = 20;
    private static final int WIN_LENGTH = 21;

    @FXML private Canvas canvas;
    @FXML private javafx.scene.layout.StackPane canvasContainer;
    @FXML private VBox menuPane;
    @FXML private Label scoreLabel;
    @FXML private Label bestScoreLabel;
    @FXML private Label lastScoreLabel;
    @FXML private Text titleText;

    private GameModel model;
    private GameView view; // Добавляем ссылку на View
    private AnimationTimer timer;
    private long lastUpdate = 0;

    private AudioClip eatSound;
    private AudioClip gameOverSound;
    private final Preferences prefs = Preferences.userNodeForPackage(GameController.class);

    @FXML
    public void initialize() {
        view = new GameView(
                canvas, scoreLabel,
                bestScoreLabel, lastScoreLabel,
                menuPane, titleText
        );

        int bestScore = prefs.getInt("bestScore", 0);
        view.updateBestScore(bestScore);

        loadSounds();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(this::handleKeyPress);

        canvas.widthProperty().bind(canvasContainer.widthProperty());
        canvas.heightProperty().bind(canvasContainer.heightProperty());

        canvas.widthProperty().addListener(observable -> redrawOnResize());
        canvas.heightProperty().addListener(observable -> redrawOnResize());
    }

    private void redrawOnResize() {
        if (model != null && view != null) {
            view.draw(model);
        }
    }

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
        } catch (Exception e) {
            System.err.println("Audio files missing or unsupported.");
        }
    }

    private void handleGameOver() {
        timer.stop();

        if (model.getState() == GameState.LOST && gameOverSound != null) {
            gameOverSound.play();
        }

        int current = model.getPlayerSnake().getBody().size() - 1;
        int best = prefs.getInt("bestScore", 0);
        if (current > best) {
            prefs.putInt("bestScore", current);
            view.updateBestScore(current);
        }

        if (model.getState() == GameState.WON) {
            view.showVictoryMenu(current);
        } else {
            view.showGameOverMenu(current);
        }
    }

    @FXML
    public void startGame() {
        view.hideMenu();
        model = new GameModel(WIDTH, HEIGHT, WIN_LENGTH, 3);

        model.setOnStateChanged(() -> {
            view.draw(model);
            view.updateScore(model.getScore());

            if (model.getState() != GameState.PLAYING) {
                handleGameOver();
            }
        });

        startTimer();
    }

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

    private void processTick() {
        int oldLength = model.getPlayerSnake().getBody().size();

        model.update();

        if (model.getPlayerSnake().getBody().size() > oldLength && eatSound != null) {
            eatSound.play();
        }
    }

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
}