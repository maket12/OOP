package ru.nsu.ziabkin.markdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for UnorderedList.
 */
public class UnorderedListTest {
    @Test
    void unorderedListRenders() {
        UnorderedList list = new UnorderedList.Builder()
                .addItem("First")
                .addItem("Second")
                .addItem(new Text.Bold("Third"))
                .build();

        String expected = String.join("\n",
                "- First",
                "- Second",
                "- **Third**"
        );

        Assertions.assertEquals(expected, list.toString());
    }

    @Test
    void unorderedListRendersEachItemOnNewLine() {
        UnorderedList list = new UnorderedList.Builder()
                .addItem("First")
                .addItem("Second")
                .addItem(new Text.Bold("Third"))
                .build();

        String expected = String.join("\n",
                "- First",
                "- Second",
                "- **Third**"
        );

        Assertions.assertEquals(expected, list.toMarkdown());
    }

    @Test
    void equalsAndHashCodeForSameItems() {
        UnorderedList list1 = new UnorderedList.Builder()
                .addItem("A")
                .addItem("B")
                .build();

        UnorderedList list2 = new UnorderedList.Builder()
                .addItem("A")
                .addItem("B")
                .build();

        Assertions.assertEquals(list1, list2);
        Assertions.assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    void notEqualsForDifferentItems() {
        UnorderedList list1 = new UnorderedList.Builder()
                .addItem("A")
                .build();

        UnorderedList list2 = new UnorderedList.Builder()
                .addItem("A")
                .addItem("B")
                .build();

        Assertions.assertNotEquals(list1, list2);
    }
}
