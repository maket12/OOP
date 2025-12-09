package ru.nsu.ziabkin.markdown;

import java.util.Objects;

/*
 * Represents Link element
 */
final class Link implements Element {
    private final Element text;
    private final String url;
    private final String title;

    public Link(Element text, String url) {
        this(text, url, null);
    }

    public Link(Element text, String url, String title) {
        this.text = Objects.requireNonNull(text);
        this.url = Objects.requireNonNull(url);
        this.title = title;
    }

    /*
     * converts method into markdown
     */
    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append('[').append(text.toMarkdown()).append(']');
        sb.append('(').append(url);
        if (title != null) {
            sb.append(" \"").append(title).append('"');
        }
        sb.append(')');
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

        if (!(o instanceof Link)) {
            return false;
        }

        Link link = (Link) o;

        return Objects.equals(text, link.text)
                && Objects.equals(url, link.url)
                && Objects.equals(title, link.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, url, title);
    }
}
