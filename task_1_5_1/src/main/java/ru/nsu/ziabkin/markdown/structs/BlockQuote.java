package ru.nsu.ziabkin.markdown.structs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.nsu.ziabkin.markdown.Element;
import ru.nsu.ziabkin.markdown.text.Text;

/**
 * Represents BlockQuote element
 */
public final class BlockQuote implements Element {
    private final List<Element> lines;

    /**
     * UnorderedList element
     */
    public BlockQuote(List<Element> lines) {
        this.lines = List.copyOf(lines);
    }

    /**
     * Builder
     */
    public static class Builder {
        private final List<Element> lines = new ArrayList<>();

        public Builder addLine(String text) {
            return addLine(new Text.Plain(text));
        }

        public Builder addLine(Element element) {
            lines.add(Objects.requireNonNull(element, "element must not be null"));
            return this;
        }

        public BlockQuote build() {
            return new BlockQuote(lines);
        }
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append("> ").append(lines.get(i).toMarkdown());
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

        if (!(o instanceof BlockQuote)) {
            return false;
        }

        BlockQuote that = (BlockQuote) o;

        return Objects.equals(lines, that.lines);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lines);
    }
}
