package ru.nsu.ziabkin.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Класс для модульного тестирования перечисления Direction.
 * Проверяет наличие всех необходимых направлений движения.
 */
class DirectionTest {

    /**
     * Проверяет, что перечисление содержит корректные константы направлений.
     */
    @Test
    void testEnumValues() {
        Direction[] directions = Direction.values();
        Assertions.assertEquals(4, directions.length);
        Assertions.assertEquals(Direction.UP, Direction.valueOf("UP"));
        Assertions.assertEquals(Direction.DOWN, Direction.valueOf("DOWN"));
        Assertions.assertEquals(Direction.LEFT, Direction.valueOf("LEFT"));
        Assertions.assertEquals(Direction.RIGHT, Direction.valueOf("RIGHT"));
    }
}