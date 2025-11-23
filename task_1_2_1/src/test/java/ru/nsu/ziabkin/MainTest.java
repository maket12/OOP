package ru.nsu.ziabkin;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MainTest {

    @Test
    void testMainClassCanBeInstantiated() {
        Assertions.assertDoesNotThrow(() -> new Main());
    }

    @Test
    void testMainMethodRunsWithoutCrashing() {
        String simulatedInput = "1\n2\ndone\n";
        InputStream originalIn = System.in;

        try {
            System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));
            Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
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
            Assertions.assertDoesNotThrow(() -> Main.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    void testEnterGraphManuallyMethodExists() {
        Assertions.assertDoesNotThrow(() -> {
            Main.class.getDeclaredMethod("enterGraphManually", java.util.Scanner.class);
        });
    }

    @Test
    void testAddSingleEdgeMethodExists() {
        Assertions.assertDoesNotThrow(() -> {
            Main.class.getDeclaredMethod("addSingleEdge", java.util.Scanner.class, Graph.class);
        });
    }
}