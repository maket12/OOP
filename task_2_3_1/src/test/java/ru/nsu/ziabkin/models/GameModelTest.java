package ru.nsu.ziabkin.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

/**
 * Класс для модульного тестирования игровой модели.
 */
class GameModelTest {

    private GameModel model;

    /**
     * Подготавливает новую игровую модель перед запуском каждого теста.
     */
    @BeforeEach
    void setUp() {
        // Создаем поле 30x20, длина для победы = 5, количество еды = 3
        model = new GameModel(30, 20, 5, 3);
    }

    /**
     * Проверяет корректность инициализации полей игры.
     */
    @Test
    void testInitialState() {
        Assertions.assertEquals(30, model.getWidth());
        Assertions.assertEquals(20, model.getHeight());
        Assertions.assertEquals(GameState.PLAYING, model.getState());

        // ФИКС ПОД МОМЕНТ Б: Теперь на старте должно быть ровно 4 змейки (1 игрок + 3 бота)
        Assertions.assertEquals(4, model.getSnakes().size());
        Assertions.assertEquals(3, model.getFoods().size());
        Assertions.assertEquals(0, model.getScore());
    }

    /**
     * Проверяет, что начальные змейки распределены по ролям правильно:
     * первая — человек, остальные — роботы.
     */
    @Test
    void testInitialSnakesRoles() {
        List<Snake> snakes = model.getSnakes();

        // Индекс 0 — это всегда змейка игрока-человека
        Assertions.assertFalse(snakes.get(0).isRobot());

        // Индексы 1, 2, 3 — это автоматически созданные боты
        Assertions.assertTrue(snakes.get(1).isRobot());
        Assertions.assertTrue(snakes.get(2).isRobot());
        Assertions.assertTrue(snakes.get(3).isRobot());
    }

    /**
     * Проверяет алгоритм вычисления текущих очков игрока.
     */
    @Test
    void testGetScoreCalculation() {
        Snake player = model.getPlayerSnake();

        // Изначально размер змейки равен 1 (только голова).
        // Имитируем рост, добавляя еще 2 сегмента.
        player.getBody().add(new Point(1, 1));
        player.getBody().add(new Point(1, 2));

        // Очки считаются как (текущая длина - 1)
        Assertions.assertEquals(2, model.getScore());
    }

    /**
     * Проверяет, что при достижении необходимой длины состояние игры переходит в WON.
     */
    @Test
    void testCheckWinConditionByLength() {
        Snake player = model.getPlayerSnake();

        // winLength в setUp равен 5. Искусственно увеличиваем змейку до 5 элементов.
        // Добавляем точки в безопасные координаты (10, 10), чтобы избежать пересечений.
        while (player.getBody().size() < 5) {
            player.getBody().add(new Point(10, 10));
        }

        model.update();

        Assertions.assertEquals(GameState.WON, model.getState());
    }

    /**
     * Проверяет, что коллбек изменения состояния модели корректно срабатывает.
     */
    @Test
    void testOnStateChangedCallback() {
        final boolean[] callbackCalled = new boolean[1];
        callbackCalled[0] = false;

        model.setOnStateChanged(() -> {
            callbackCalled[0] = true;
        });

        model.update();

        Assertions.assertTrue(callbackCalled[0]);
    }
}