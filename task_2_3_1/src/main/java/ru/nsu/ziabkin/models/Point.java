package ru.nsu.ziabkin.models;

import java.util.Objects;

/**
 * Class representing a point on a two-dimensional grid.
 */
public class Point {
    private final int posX;
    private final int posY;

    public Point(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Returns the X coordinate.
     *
     * @return the X coordinate
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Returns the Y coordinate.
     *
     * @return the Y coordinate
     */
    public int getPosY() {
        return posY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return posX == point.posX && posY == point.posY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(posX, posY);
    }
}