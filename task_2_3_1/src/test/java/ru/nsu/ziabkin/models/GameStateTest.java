package ru.nsu.ziabkin.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Класс для модульного тестирования перечисления GameState.
 */
class GameStateTest {

    /**
     * Проверяет наличие базовых состояний игры: процесс, победа, поражение.
     */
    @Test
    void testEnumValues() {
        GameState[] states = GameState.values();
        Assertions.assertEquals(3, states.length);
        Assertions.assertEquals(GameState.PLAYING, GameState.valueOf("PLAYING"));
        Assertions.assertEquals(GameState.WON, GameState.valueOf("WON"));
        Assertions.assertEquals(GameState.LOST, GameState.valueOf("LOST"));
    }
}