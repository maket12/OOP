package ru.nsu.ziabkin.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Represents TaskList element
 */
final class TaskList implements Element {
    private final List<TaskItem> items;

    private TaskList(List<TaskItem> items) {
        this.items = List.copyOf(items);
    }

    public static class Builder {
        private final List<TaskItem> items = new ArrayList<>();

        public Builder addTask(String text, boolean done) {
            items.add(new TaskItem(text, done));
            return this;
        }

        public Builder addTask(Element text, boolean done) {
            items.add(new TaskItem(text, done));
            return this;
        }

        public TaskList build() {
            return new TaskList(items);
        }
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append(items.get(i).toMarkdown());
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return toMarkdown();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof TaskList)) {
            return false;
        }

        TaskList taskList = (TaskList) o;

        return Objects.equals(items, taskList.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
