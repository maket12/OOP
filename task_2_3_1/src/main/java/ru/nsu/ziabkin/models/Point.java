package ru.nsu.ziabkin.models;

import java.util.Objects;

/**
 * Class representing a point on a two-dimensional grid.
 */
public class Point {
    private final int x;
    private final int y;

    /**
     * Creates a new point with specified coordinates.
     *
     * @param x the X coordinate
     * @param y the Y coordinate
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the X coordinate.
     *
     * @return the X coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Y coordinate.
     *
     * @return the Y coordinate
     */
    public int getY() {
        return y;
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
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}