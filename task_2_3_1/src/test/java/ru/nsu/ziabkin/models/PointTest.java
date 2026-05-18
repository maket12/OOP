package ru.nsu.ziabkin.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Класс для модульного тестирования координат (Point).
 * Проверяет инициализацию, сравнение точек и генерацию хэш-кода.
 */
class PointTest {

    /**
     * Проверяет, что геттеры возвращают правильные координаты, заданные в конструкторе.
     */
    @Test
    void testCoordinatesInitialization() {
        Point point = new Point(10, 15);
        Assertions.assertEquals(10, point.getPosX());
        Assertions.assertEquals(15, point.getPosY());
    }

    /**
     * Проверяет переопределенный метод equals для корректного сравнения объектов.
     */
    @Test
    void testEqualsAndHashCode() {
        Point point1 = new Point(5, 5);
        Point point2 = new Point(5, 5);
        Point point3 = new Point(5, 6);

        Assertions.assertEquals(point1, point2);
        Assertions.assertNotEquals(point1, point3);
        Assertions.assertNotEquals(null, point1);
        Assertions.assertNotEquals(new Object(), point1);

        Assertions.assertEquals(point1.hashCode(), point2.hashCode());
        Assertions.assertNotEquals(point1.hashCode(), point3.hashCode());
    }
}