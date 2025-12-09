package ru.nsu.ziabkin;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for Demo.
 */
public class DemoTest {
    @Test
    void demoMainPrintsValidMarkdownTable() {
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream testOut = new PrintStream(baos);

        try {
            System.setOut(testOut);
            Demo.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        String output = baos.toString().trim();
        Assertions.assertFalse(
                output.isEmpty(),
                "Demo.main must print something");

        String[] lines = output.split("\\R");

        Assertions.assertEquals(
                7,
                lines.length,
                "Waiting 7 rows");
        Assertions.assertEquals("| Index | Random |",
                lines[0],
                "The first row must be the header of the table");
        Assertions.assertEquals(
                "| ----: | ------ |",
                lines[1],
                "The second row must be aligning in the table");

        for (int i = 0; i < lines.length; i++) {
            Assertions.assertTrue(lines[i].startsWith("|"), "Row " + i + " must starts with |");
            Assertions.assertTrue(lines[i].endsWith("|"), "Row " + i + " must ends with |");
        }
    }
}
