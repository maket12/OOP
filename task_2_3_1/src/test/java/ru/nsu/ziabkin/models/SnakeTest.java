package ru.nsu.ziabkin.models;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Класс для модульного тестирования логики змейки.
 */
class SnakeTest {

    private Point startPoint;
    private Snake snake;

    /**
     * Подготавливает тестовое окружение: инициализирует змейку игрока.
     */
    @BeforeEach
    void setUp() {
        startPoint = new Point(10, 10);
        snake = new Snake(startPoint, false);
    }

    /**
     * Тестирует начальное состояние свойств змейки.
     */
    @Test
    void testConstructorInitialization() {
        Assertions.assertNotNull(snake.getBody());
        Assertions.assertEquals(1, snake.getBody().size());
        Assertions.assertEquals(startPoint, snake.getHead());
        Assertions.assertEquals(Direction.RIGHT, snake.getDirection());
        Assertions.assertTrue(snake.isAlive());
        Assertions.assertFalse(snake.isRobot());
    }

    /**
     * Проверяет вычисление следующей позиции головы при движении вверх.
     */
    @Test
    void testCalculateNextHeadUp() {
        snake.setDirection(Direction.UP);
        Point next = snake.calculateNextHead();
        Assertions.assertEquals(10, next.getPosX());
        Assertions.assertEquals(9, next.getPosY());
    }

    /**
     * Проверяет вычисление следующей позиции головы при движении вниз.
     */
    @Test
    void testCalculateNextHeadDown() {
        snake.setDirection(Direction.DOWN);
        Point next = snake.calculateNextHead();
        Assertions.assertEquals(10, next.getPosX());
        Assertions.assertEquals(11, next.getPosY());
    }

    /**
     * Тестирует перемещение без еды (длина тела не меняется).
     */
    @Test
    void testMoveWithoutEating() {
        Point nextHead = new Point(11, 10);
        snake.move(nextHead, false);

        List<Point> body = snake.getBody();
        Assertions.assertEquals(1, body.size());
        Assertions.assertEquals(nextHead, snake.getHead());
    }

    /**
     * Тестирует перемещение на клетку с едой (длина тела растет).
     */
    @Test
    void testMoveWithEating() {
        Point nextHead = new Point(11, 10);
        snake.move(nextHead, true);

        List<Point> body = snake.getBody();
        Assertions.assertEquals(2, body.size());
        Assertions.assertEquals(nextHead, body.get(0));
        Assertions.assertEquals(startPoint, body.get(1));
    }
}