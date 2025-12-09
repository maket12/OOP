package ru.nsu.ziabkin.markdown;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Tests for Link.
 */
public class LinkTest {
    @Test
    void toStringTest() {
        Link link = new Link(new Text.Plain("Google"), "https://google.com");
        Assertions.assertEquals("[Google](https://google.com)", link.toString());
    }

    @Test
    void simpleLinkRendersCorrectly() {
        Link link = new Link(new Text.Plain("Google"), "https://google.com");
        Assertions.assertEquals("[Google](https://google.com)", link.toMarkdown());
    }

    @Test
    void linkWithTitleRendersCorrectly() {
        Link link = new Link(new Text.Plain("NSU"), "https://nsu.ru", "Novosibirsk State University");
        Assertions.assertEquals("[NSU](https://nsu.ru \"Novosibirsk State University\")", link.toMarkdown());
    }

    @Test
    void linkWithFormattedTextRendersCorrectly() {
        Link link = new Link(new Text.Bold("Bold"), "https://example.com");
        Assertions.assertEquals("[**Bold**](https://example.com)", link.toMarkdown());
    }

    @Test
    void equalsAndHashCodeForSameFields() {
        Link l1 = new Link(new Text.Plain("Text"), "https://example.com", "Title");
        Link l2 = new Link(new Text.Plain("Text"), "https://example.com", "Title");

        Assertions.assertEquals(l1, l2);
        Assertions.assertEquals(l1.hashCode(), l2.hashCode());
    }

    @Test
    void notEqualsForDifferentText() {
        Link l1 = new Link(new Text.Plain("One"), "https://example.com", "Title");
        Link l2 = new Link(new Text.Plain("Two"), "https://example.com", "Title");

        Assertions.assertNotEquals(l1, l2);
    }

    @Test
    void notEqualsForDifferentUrl() {
        Link l1 = new Link(new Text.Plain("Text"), "https://one.com", "Title");
        Link l2 = new Link(new Text.Plain("Text"), "https://two.com", "Title");

        Assertions.assertNotEquals(l1, l2);
    }

    @Test
    void notEqualsForDifferentTitleNullVsNonNull() {
        Link l1 = new Link(new Text.Plain("Text"), "https://example.com");
        Link l2 = new Link(new Text.Plain("Text"), "https://example.com", "Title");

        Assertions.assertNotEquals(l1, l2);
    }
}
