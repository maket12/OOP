package ru.nsu.ziabkin;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainClassCanBeInstantiated() {
        assertDoesNotThrow(() -> new Main());
    }

    @Test
    void testMainMethodRunsWithoutCrashing() {
        String simulatedInput = "1\n2\ndone\n";
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    void testMainWithInvalidInput() {
        String simulatedInput = "999\n999\ndone\n";
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
        }
    }
}