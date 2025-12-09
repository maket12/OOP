package ru.nsu.ziabkin.markdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for Table.
 */
public class TableTest {

    @Test
    void simpleTableRendersHeaderAlignmentAndRows() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Index", "Random")
                .addRow(1, new Text.Bold("8"))
                .addRow(2, 2)
                .build();

        String md = table.toMarkdown();
        String[] lines = md.split("\\R");

        Assertions.assertEquals(4, lines.length, "Waiting for 4 rows");

        Assertions.assertEquals("| Index | Random |", lines[0]);
        Assertions.assertEquals("| ----: | ------ |", lines[1]);

        for (int i = 0; i < lines.length; i++) {
            Assertions.assertTrue(
                    lines[i].startsWith("|"),
                    "Row " + i + " must starts with |");
            Assertions.assertTrue(
                    lines[i].endsWith("|"),
                    "Row " + i + " must ends with |");
        }

        Assertions.assertTrue(lines[1].contains("----:"),
                "First column must have right pagination");
        Assertions.assertTrue(
                lines[1].contains("------"),
                "Second column must have left pagination");

        Assertions.assertTrue(lines[2].contains("8"),
                "8 expected in the second row");
    }

    @Test
    void rightAlignmentMakesNumbersLineUpVisually() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_RIGHT, Table.ALIGN_LEFT)
                .addRow("Index", "Value")
                .addRow(1, "A")
                .addRow(12, "B")
                .addRow(123, "C")
                .build();

        String md = table.toMarkdown();
        String[] lines = md.split("\\R");

        int pos1 = lastNonSpaceInFirstColumn(lines[2]);
        int pos2 = lastNonSpaceInFirstColumn(lines[3]);
        int pos3 = lastNonSpaceInFirstColumn(lines[4]);

        Assertions.assertEquals(pos1, pos2, "Digits pagination(2 row)");
        Assertions.assertEquals(pos1, pos3, "Digits pagination(3 row)");
    }

    private int lastNonSpaceInFirstColumn(String line) {
        int firstBar = line.indexOf('|');
        int secondBar = line.indexOf('|', firstBar + 1);
        String col = line.substring(firstBar + 1, secondBar);

        for (int i = col.length() - 1; i >= 0; i--) {
            if (col.charAt(i) != ' ') {
                return i;
            }
        }
        return -1;
    }

    @Test
    void rowLimitStopsAddingExtraRows() {
        Table table = new Table.Builder()
                .withAlignments(Table.ALIGN_LEFT, Table.ALIGN_LEFT)
                .withRowLimit(3)
                .addRow("H1", "H2")
                .addRow("R1C1", "R1C2")
                .addRow("R2C1", "R2C2")
                .addRow("R3C1", "R3C2")
                .build();

        String md = table.toMarkdown();
        String[] lines = md.split("\\R");

        Assertions.assertEquals(4, lines.length);

        Assertions.assertFalse(
                md.contains("R3C1"),
                "Row is under limit has not to be in the table");
    }
}
