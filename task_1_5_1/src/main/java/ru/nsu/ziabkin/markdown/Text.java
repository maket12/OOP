package ru.nsu.ziabkin.markdown;

import java.util.Objects;

/*
 * Represents Text element
 */
public abstract class Text implements Element {
    protected final String value;

    protected Text(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public abstract String toMarkdown();

    @Override
    public String toString() {
        return toMarkdown();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text = (Text) o;
        return value.equals(text.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClass(), value);
    }


    public static final class Plain extends Text {
        public Plain(String value) { super(value); }

        @Override
        public String toMarkdown() { return value; }
    }

    public static final class Bold extends Text {
        public Bold(String value) { super(value); }

        @Override
        public String toMarkdown() { return "**" + value + "**"; }
    }

    public static final class Italic extends Text {
        public Italic(String value) { super(value); }

        @Override
        public String toMarkdown() { return "*" + value + "*"; }
    }

    public static final class Strike extends Text {
        public Strike(String value) { super(value); }

        @Override
        public String toMarkdown() { return "~~" + value + "~~"; }
    }

    public static final class Code extends Text {
        public Code(String value) { super(value); }

        @Override
        public String toMarkdown() { return "`" + value + "`"; }
    }
}