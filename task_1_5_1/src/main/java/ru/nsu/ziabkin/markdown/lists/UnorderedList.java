package ru.nsu.ziabkin.markdown.lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import ru.nsu.ziabkin.markdown.Element;
import ru.nsu.ziabkin.markdown.text.Text;

/*
 * Represents UnorderedList element
 */
public final class UnorderedList implements Element {
    private final List<Element> items;

    /*
     * Represents UnorderedList element
     */
    public UnorderedList(List<Element> items) {
        this.items = List.copyOf(items);
    }

    /*
     * Builds the element
     */
    public static class Builder {
        private final List<Element> items = new ArrayList<>();

        public Builder addItem(String text) {
            return addItem(new Text.Plain(text));
        }

        public Builder addItem(Element element) {
            items.add(Objects.requireNonNull(element, "element must not be null"));
            return this;
        }

        public UnorderedList build() {
            return new UnorderedList(items);
        }
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append("- ").append(items.get(i).toMarkdown());
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

        if (!(o instanceof UnorderedList)) {
            return false;
        }

        UnorderedList that = (UnorderedList) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
