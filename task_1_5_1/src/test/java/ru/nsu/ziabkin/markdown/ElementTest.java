package ru.nsu.ziabkin.markdown;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for base class Element.
 */
public class ElementTest {
    /**
     * Testing toMarkdown method.
     */
    @Test
    public void anonymousElementReturnsItsMarkdown() {
        Element e = () -> "markdown";
        Assertions.assertEquals("markdown", e.toMarkdown());
    }

    /**
     * Testing(check class).
     */
    @Test
    void textPlainIsAlsoElement() {
        Element e = new Text.Plain("hello");
        Assertions.assertEquals("hello", e.toMarkdown());
        Assertions.assertInstanceOf(Element.class, e);
    }
}
