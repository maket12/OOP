package ru.nsu.ziabkin.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Class describing the behavior and state of a snake.
 */
public class Snake {
    private final List<Point> body;
    private Direction direction;
    private boolean isAlive;
    private final boolean isRobot;

    /**
     * Snake constructor.
     *
     * @param startPosition initial head position
     * @param isRobot flag indicating if the snake is controlled by a bot
     */
    public Snake(Point startPosition, boolean isRobot) {
        this.body = new ArrayList<>();
        this.body.add(startPosition);
        this.direction = Direction.RIGHT;
        this.isAlive = true;
        this.isRobot = isRobot;
    }

    /**
     * Returns the list of points making up the snake's body.
     *
     * @return list of body points
     */
    public List<Point> getBody() {
        return body;
    }

    /**
     * Returns the current movement direction.
     *
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * Sets the movement direction.
     *
     * @param direction new direction
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Checks if the snake is alive.
     *
     * @return true if alive
     */
    public boolean isAlive() {
        return isAlive;
    }

    /**
     * Sets the snake's life status.
     *
     * @param alive life status
     */
    public void setAlive(boolean alive) {
        this.isAlive = alive;
    }

    /**
     * Checks if the snake is controlled by a robot.
     *
     * @return true if it is a robot
     */
    public boolean isRobot() {
        return isRobot;
    }

    /**
     * Returns the head point of the snake.
     *
     * @return head point
     */
    public Point getHead() {
        return body.get(0);
    }

    /**
     * Calculates the next head position based on current direction.
     *
     * @return new head point
     */
    public Point calculateNextHead() {
        Point head = getHead();
        int nextX = head.getPosX();
        int nextY = head.getPosY();
        if (direction == Direction.UP) {
            nextY--;
        }
        if (direction == Direction.DOWN) {
            nextY++;
        }
        if (direction == Direction.LEFT) {
            nextX--;
        }
        if (direction == Direction.RIGHT) {
            nextX++;
        }
        return new Point(nextX, nextY);
    }

    /**
     * Moves the snake to a new position.
     *
     * @param nextHead the new head point
     * @param ateFood flag indicating if food was consumed this turn
     */
    public void move(Point nextHead, boolean ateFood) {
        body.add(0, nextHead);
        if (!ateFood) {
            body.remove(body.size() - 1);
        }
    }
}