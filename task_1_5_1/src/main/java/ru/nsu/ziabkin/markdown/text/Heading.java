package ru.nsu.ziabkin.markdown.text;

import java.util.Objects;
import ru.nsu.ziabkin.markdown.Element;

/**
 * Represents Heading element
 */
public final class Heading implements Element {
    private final int level;
    private final Element content;

    /**
     * Constructor
     */
    public Heading(int level, Element content) {
        if (level < 1 || level > 6) {
            throw new IllegalArgumentException("Heading level must be between 1 and 6");
        }
        this.level = level;
        this.content = Objects.requireNonNull(content,  "content must not be null");
    }

    /**
     * Converts method into markdown form
     */
    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append("#".repeat(level)).append(' ').append(content.toMarkdown());
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

        if (!(o instanceof Heading)) {
            return false;
        }

        Heading heading = (Heading) o;
        return level == heading.level
                && Objects.equals(content, heading.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(level, content);
    }
}
