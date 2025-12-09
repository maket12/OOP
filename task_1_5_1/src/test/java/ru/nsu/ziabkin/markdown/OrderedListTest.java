package ru.nsu.ziabkin.markdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for OrderedList.
 */
public class OrderedListTest {
    @Test
    void orderedListRenders() {
        OrderedList list = new OrderedList.Builder()
                .addItem("First")
                .addItem("Second")
                .addItem(new Text.Italic("Third"))
                .build();

        String expected = String.join("\n",
                "1. First",
                "2. Second",
                "3. *Third*"
        );

        Assertions.assertEquals(expected, list.toString());
    }

    @Test
    void orderedListRendersNumbersSequentially() {
        OrderedList list = new OrderedList.Builder()
                .addItem("First")
                .addItem("Second")
                .addItem(new Text.Italic("Third"))
                .build();

        String expected = String.join("\n",
                "1. First",
                "2. Second",
                "3. *Third*"
        );

        Assertions.assertEquals(expected, list.toMarkdown());
    }

    @Test
    void equalsAndHashCodeForSameItems() {
        OrderedList list1 = new OrderedList.Builder()
                .addItem("A")
                .addItem("B")
                .build();

        OrderedList list2 = new OrderedList.Builder()
                .addItem("A")
                .addItem("B")
                .build();

        Assertions.assertEquals(list1, list2);
        Assertions.assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    void notEqualsForDifferentItems() {
        OrderedList list1 = new OrderedList.Builder()
                .addItem("A")
                .build();

        OrderedList list2 = new OrderedList.Builder()
                .addItem("A")
                .addItem("B")
                .build();

        Assertions.assertNotEquals(list1, list2);
    }
}

