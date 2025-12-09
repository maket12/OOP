package ru.nsu.ziabkin.markdown;

import java.util.Objects;

/*
 * Represents TaskItem element
 */
final class TaskItem implements Element {
    private final Element text;
    private final boolean done;

    public TaskItem(String text, boolean done) {
        this(new Text.Plain(text), done);
    }

    public TaskItem(Element text, boolean done) {
        this.text = Objects.requireNonNull(text);
        this.done = done;
    }

    @Override
    public String toMarkdown() {
        return "- [" + (done ? "x" : " ") + "] " + text.toMarkdown();
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

        if (!(o instanceof TaskItem)) {
            return false;
        }

        TaskItem taskItem = (TaskItem) o;

        return done == taskItem.done &&
                Objects.equals(text, taskItem.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, done);
    }
}
