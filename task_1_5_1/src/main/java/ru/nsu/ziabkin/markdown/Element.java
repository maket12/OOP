package ru.nsu.ziabkin.markdown;

/**
 * Represents Element.
 */
public interface Element {
    /**
     * converts method into markdown.
     *
     * @return Markdown implementation
     */
    String toMarkdown();
}
