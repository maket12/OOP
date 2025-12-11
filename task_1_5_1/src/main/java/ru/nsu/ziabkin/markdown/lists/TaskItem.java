package ru.nsu.ziabkin.markdown.lists;

import java.util.Objects;
import ru.nsu.ziabkin.markdown.Element;
import ru.nsu.ziabkin.markdown.text.Text;

/*
 * Represents TaskItem element
 */
public final class TaskItem implements Element {
    private final Element text;
    private final boolean done;

    /*
     * Create TaskItem element
     */
    public TaskItem(String text, boolean done) {
        this(new Text.Plain(text), done);
    }

    /*
     * Implements
     */
    public TaskItem(Element text, boolean done) {
        this.text = Objects.requireNonNull(text, "task item text must be not null");
        this.done = done;
    }

    /*
     * Converts method into markdown
     */
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

        return done == taskItem.done
                && Objects.equals(text, taskItem.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, done);
    }
}
