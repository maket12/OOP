package ru.nsu.ziabkin.views;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
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

    public GameView(Canvas canvas, Label scoreLabel, Label bestScoreLabel, Label lastScoreLabel, VBox menuPane) {
        this.canvas = canvas;
        this.scoreLabel = scoreLabel;
        this.bestScoreLabel = bestScoreLabel;
        this.lastScoreLabel = lastScoreLabel;
        this.menuPane = menuPane;
    }

    /**
     * Draws the current state of the model onto the Canvas.
     */
    public void draw(GameModel model) {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, model.getWidth() * CELL_SIZE, model.getHeight() * CELL_SIZE);

        gc.setFill(Color.web("#FF003C"));
        for (Point f : model.getFoods()) {
            gc.fillOval(f.getX() * CELL_SIZE, f.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

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

    private void drawDeathEffect(GraphicsContext gc, Point p) {
        gc.setStroke(Color.ORANGE);
        gc.setLineWidth(3);
        gc.strokeOval(p.getX() * CELL_SIZE - 5, p.getY() * CELL_SIZE - 5,
                CELL_SIZE + 10, CELL_SIZE + 10);
    }

    public void updateScore(int currentScore) {
        scoreLabel.setText("Score: " + currentScore);
    }

    public void updateBestScore(int bestScore) {
        bestScoreLabel.setText("Best: " + bestScore);
    }

    public void showGameOverMenu(int currentScore) {
        lastScoreLabel.setText("Last Score: " + currentScore);
        menuPane.setVisible(true);
    }

    public void hideMenu() {
        menuPane.setVisible(false);
    }
}