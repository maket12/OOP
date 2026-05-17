package ru.nsu.ziabkin.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Assertions.assertEquals(1, model.getSnakes().size());
        Assertions.assertEquals(3, model.getFoods().size());
        Assertions.assertEquals(0, model.getScore());
    }

    /**
     * Проверяет алгоритм вычисления текущих очков игрока.
     */
    @Test
    void testGetScoreCalculation() {
        Snake player = model.getPlayerSnake();

        player.getBody().add(new Point(1, 1));
        player.getBody().add(new Point(1, 2));

        Assertions.assertEquals(2, model.getScore());
    }

    /**
     * Проверяет, что при достижении необходимой длины состояние игры переходит в WON.
     */
    @Test
    void testCheckWinConditionByLength() {
        Snake player = model.getPlayerSnake();

        while (player.getBody().size() < 5) {
            player.getBody().add(new Point(0, 0));
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