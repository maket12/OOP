package ru.nsu.ziabkin.markdown.text;

import java.util.Objects;
import ru.nsu.ziabkin.markdown.Element;

/*
 * Represents Link element
 */
public final class Link implements Element {
    private final Element text;
    private final String url;
    private final String title;

    /*
     * Represents Link element
     */
    public Link(Element text, String url) {
        this(text, url, null);
    }

    public Link(Element text, String url, String title) {
        this.text = Objects.requireNonNull(text, "link text must be not null");
        this.url = Objects.requireNonNull(url, "link url must be not null");
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
