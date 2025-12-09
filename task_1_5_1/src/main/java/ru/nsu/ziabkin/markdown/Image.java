package ru.nsu.ziabkin.markdown;

import java.util.Objects;

/*
 * Represents Image element
 */
final class Image implements Element {
    private final String altText;
    private final String url;
    private final String title;

    public Image(String altText, String url) {
        this(altText, url, null);
    }

    public Image(String altText, String url, String title) {
        this.altText = Objects.requireNonNull(altText);
        this.url = Objects.requireNonNull(url);
        this.title = title;
    }

    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append("![").append(altText).append("](").append(url);
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

        if (!(o instanceof Image)) {
            return false;
        }

        Image image = (Image) o;

        return Objects.equals(altText, image.altText)
                && Objects.equals(url, image.url)
                && Objects.equals(title, image.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(altText, url, title);
    }
}
