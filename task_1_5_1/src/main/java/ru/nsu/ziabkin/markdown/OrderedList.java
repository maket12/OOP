package ru.nsu.ziabkin.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Represents OrderedList element
 */
final class OrderedList implements Element {
    private final List<Element> items;

    private OrderedList(List<Element> items) {
        this.items = List.copyOf(items);
    }

    public static class Builder {
        private final List<Element> items = new ArrayList<>();

        public Builder addItem(String text) {
            return addItem(new Text.Plain(text));
        }

        public Builder addItem(Element element) {
            items.add(Objects.requireNonNull(element));
            return this;
        }

        public OrderedList build() {
            return new OrderedList(items);
        }
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            if (i > 0) {
                sb.append('\n');
            }
            sb.append(i + 1)
                    .append(". ")
                    .append(items.get(i).toMarkdown());
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

        if (!(o instanceof OrderedList)) {
            return false;
        }

        OrderedList that = (OrderedList) o;

        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }
}
