package ru.nsu.ziabkin.views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import ru.nsu.ziabkin.models.GameModel;
import ru.nsu.ziabkin.models.Point;
import ru.nsu.ziabkin.models.Snake;

/**
 * Responsible solely for the visual display of the game state and UI updating.
 */
public class GameView {
    private static final int CELL_SIZE = 20;

    private final Canvas canvas;
    private final Label scoreLabel;
    private final Label bestScoreLabel;
    private final Label lastScoreLabel;
    private final VBox menuPane;
    private final Text titleText;

    /**
     * Constructs a new GameView with UI components.
     *
     * @param canvas the drawing canvas
     * @param scoreLabel the current score label
     * @param bestScoreLabel the best score label
     * @param lastScoreLabel the last score label
     * @param menuPane the menu container
     * @param titleText the title text node
     */
    public GameView(
        Canvas canvas, Label scoreLabel,
        Label bestScoreLabel, Label lastScoreLabel,
        VBox menuPane, Text titleText
    ) {
        this.canvas = canvas;
        this.scoreLabel = scoreLabel;
        this.bestScoreLabel = bestScoreLabel;
        this.lastScoreLabel = lastScoreLabel;
        this.menuPane = menuPane;
        this.titleText = titleText;
    }

    /**
     * Draws the current state of the model onto the Canvas.
     */
    public void draw(GameModel model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        double cellWidth = canvas.getWidth() / model.getWidth();
        double cellHeight = canvas.getHeight() / model.getHeight();
        double cellSize = Math.min(cellWidth, cellHeight);

        if (cellSize <= 0) {
            cellSize = 20;
        }

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(Color.web("#FF003C"));
        for (Point f : model.getFoods()) {
            gc.fillOval(f.getPosX() * cellSize, f.getPosY() * cellSize, cellSize, cellSize);
        }

        for (Snake s : model.getSnakes()) {
            if (s.isAlive()) {
                gc.setFill(s.isRobot() ? Color.web("#00F3FF") : Color.web("#00FF41"));
                for (Point p : s.getBody()) {
                    gc.fillRect(p.getPosX() * cellSize + 1, p.getPosY() * cellSize + 1,
                            cellSize - 2, cellSize - 2);
                }
            } else if (s.isRobot()) {
                drawDeathEffect(gc, s.getHead(), cellSize);
            }
        }
    }

    /**
     * Draws the death effect.
     *
     * @param gc graphics context
     * @param p center point
     */
    private void drawDeathEffect(GraphicsContext gc, Point p, double cellSize) {
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(cellSize * 0.15);
        gc.strokeOval(p.getPosX() * cellSize - (cellSize * 0.25),
                p.getPosY() * cellSize - (cellSize * 0.25),
                cellSize * 1.5, cellSize * 1.5);
    }

    /**
     * Updates the score label text.
     *
     * @param currentScore new score value
     */
    public void updateScore(int currentScore) {
        scoreLabel.setText("Score: " + currentScore);
    }

    /**
     * Updates the best score label text.
     *
     * @param bestScore new best score value
     */
    public void updateBestScore(int bestScore) {
        bestScoreLabel.setText("Best: " + bestScore);
    }

    /**
     * Shows the game over overlay layout menu.
     *
     * @param currentScore final score value
     */
    public void showGameOverMenu(int currentScore) {
        titleText.setText("GAME OVER");
        lastScoreLabel.setText("Last Score: " + currentScore);
        menuPane.setVisible(true);
    }

    /**
     * Shows the game victory overlay layout menu.
     *
     * @param currentScore final score value
     */
    public void showVictoryMenu(int currentScore) {
        titleText.setText("VICTORY");
        lastScoreLabel.setText("Score: " + currentScore);
        menuPane.setVisible(true);
    }

    /**
     * Hides menu.
     */
    public void hideMenu() {
        menuPane.setVisible(false);
        titleText.setText("SNAKE CYBERPUNK");
    }
}