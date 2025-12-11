package ru.nsu.ziabkin.markdown.lists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.ziabkin.markdown.text.Text;

/**
 * Tests for TaskItem and TaskList.
 */
public class TaskTest {
    @Test
    void toStringTest() {
        TaskItem item = new TaskItem("Do something", false);
        Assertions.assertEquals("- [ ] Do something", item.toString());
    }

    @Test
    void uncheckedTaskItemRendersCorrectly() {
        TaskItem item = new TaskItem("Do something", false);
        Assertions.assertEquals("- [ ] Do something", item.toMarkdown());
    }

    @Test
    void checkedTaskItemRendersCorrectly() {
        TaskItem item = new TaskItem("Done", true);
        Assertions.assertEquals("- [x] Done", item.toMarkdown());
    }

    @Test
    void taskItemWithFormattedTextRendersCorrectly() {
        TaskItem item = new TaskItem(new Text.Bold("Important"), false);
        Assertions.assertEquals("- [ ] **Important**", item.toMarkdown());
    }

    @Test
    void taskItemEqualsAndHashCodeForSameTextAndState() {
        TaskItem t1 = new TaskItem("Task", true);
        TaskItem t2 = new TaskItem("Task", true);

        Assertions.assertEquals(t1, t2);
        Assertions.assertEquals(t1.hashCode(), t2.hashCode());
    }

    @Test
    void taskItemNotEqualsForDifferentState() {
        TaskItem t1 = new TaskItem("Task", true);
        TaskItem t2 = new TaskItem("Task", false);

        Assertions.assertNotEquals(t1, t2);
    }

    @Test
    void taskItemNotEqualsForDifferentText() {
        TaskItem t1 = new TaskItem("One", false);
        TaskItem t2 = new TaskItem("Two", false);

        Assertions.assertNotEquals(t1, t2);
    }

    @Test
    void emptyTaskListBuildAndRenders() {
        TaskList list = new TaskList.Builder()
                .addTask("First", false)
                .addTask("Second", true)
                .build();

        String expected = String.join("\n",
                "- [ ] First",
                "- [x] Second"
        );

        Assertions.assertEquals(expected, list.toString());
    }

    @Test
    void emptyTaskListBuildAndRenderSingleLinePerItem() {
        TaskList list = new TaskList.Builder()
                .addTask("First", false)
                .addTask("Second", true)
                .build();

        String expected = String.join("\n",
                "- [ ] First",
                "- [x] Second"
        );

        Assertions.assertEquals(expected, list.toMarkdown());
    }

    @Test
    void taskListEqualsAndHashCodeForSameItems() {
        TaskList list1 = new TaskList.Builder()
                .addTask("First", false)
                .addTask("Second", true)
                .build();

        TaskList list2 = new TaskList.Builder()
                .addTask("First", false)
                .addTask("Second", true)
                .build();

        Assertions.assertEquals(list1, list2);
        Assertions.assertEquals(list1.hashCode(), list2.hashCode());
    }

    @Test
    void taskListNotEqualsForDifferentItems() {
        TaskList list1 = new TaskList.Builder()
                .addTask("First", false)
                .build();

        TaskList list2 = new TaskList.Builder()
                .addTask("First", false)
                .addTask("Second", true)
                .build();

        Assertions.assertNotEquals(list1, list2);
    }
}
