package ru.nsu.ziabkin.markdown;

import java.util.Objects;

/**
 * Represents Text element.
 */
public abstract class Text implements Element {
    protected final String value;

    /**
     * Constructs a Text element with the given value.
     * @param value the text value, cannot be null
     */
    protected Text(String value) {
        this.value = Objects.requireNonNull(value);
    }

    /**
     * Returns the text value.
     * @return the text value
     */
    public String getValue() {
        return value;
    }

    /**
     * Converts the text element to markdown format.
     * @return markdown representation
     */
    @Override
    public abstract String toMarkdown();

    @Override
    public String toString() {
        return toMarkdown();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Text text = (Text) o;
        return value.equals(text.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), value);
    }

    /**
     * Represents plain text.
     */
    public static final class Plain extends Text {
        /**
         * plain text.
         * @param value the text value
         */
        public Plain(String value) {
            super(value);
        }

        /**
         * to markdown.
         * @return markdown
         */
        @Override
        public String toMarkdown() {
            return value;
        }
    }

    /**
     * Represents bold text.
     */
    public static final class Bold extends Text {
        /**
         * bold text.
         * @param value the text value
         */
        public Bold(String value) {
            super(value);
        }

        /**
         * to markdown.
         * @return markdown
         */
        @Override
        public String toMarkdown() {
            return "**" + value + "**";
        }
    }

    /**
     * Represents italic text.
     */
    public static final class Italic extends Text {
        /**
         * italic text.
         * @param value the text value
         */
        public Italic(String value) {
            super(value);
        }

        /**
         * italic text to markdown.
         * @return markdown
         */
        @Override
        public String toMarkdown() {
            return "*" + value + "*";
        }
    }

    /**
     * Represents strikethrough text.
     */
    public static final class Strike extends Text {
        /**
         * strikethrough text.
         * @param value the text value
         */
        public Strike(String value) {
            super(value);
        }

        /**
         * to markdown.
         * @return markdown
         */
        @Override
        public String toMarkdown() {
            return "~~" + value + "~~";
        }
    }

    /**
     * Represents inline code text.
     */
    public static final class Code extends Text {
        /**
         * inline code text.
         * @param value the text value
         */
        public Code(String value) {
            super(value);
        }

        /**
         * to markdown.
         * @return markdown
         */
        @Override
        public String toMarkdown() {
            return "`" + value + "`";
        }
    }
}