package ru.nsu.ziabkin.markdown;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Tests for Heading.
 */
public class HeadingTest {
    @Test
    void toStringTest() {
        Heading h = new Heading(1, new Text.Plain("Damn"));
        Assertions.assertEquals("# Damn", h.toString());
    }

    @Test
    void levelOneHeadingRendersCorrectly() {
        Heading h = new Heading(1, new Text.Plain("Title"));
        Assertions.assertEquals("# Title", h.toMarkdown());
    }

    @Test
    void levelThreeHeadingWithFormattedTextRendersCorrectly() {
        Heading h = new Heading(3, new Text.Bold("Important"));
        Assertions.assertEquals("### **Important**", h.toMarkdown());
    }

    @Test
    void levelSixHeadingRendersCorrectly() {
        Heading h = new Heading(6, new Text.Plain("Deep"));
        Assertions.assertEquals("###### Deep", h.toMarkdown());
    }

    @Test
    void invalidLevelZeroThrowsException() {
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Heading(0, new Text.Plain("Bad"))
        );
        Assertions.assertTrue(ex.getMessage().contains("Heading level"));
    }

    @Test
    void invalidLevelSevenThrowsException() {
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> new Heading(7, new Text.Plain("Bad"))
        );
        Assertions.assertTrue(ex.getMessage().contains("Heading level"));
    }

    @Test
    void equalsAndHashCodeWorkForSameLevelAndContent() {
        Heading h1 = new Heading(2, new Text.Plain("Same"));
        Heading h2 = new Heading(2, new Text.Plain("Same"));

        Assertions.assertEquals(h1, h2);
        Assertions.assertEquals(h1.hashCode(), h2.hashCode());
    }

    @Test
    void notEqualsForDifferentLevel() {
        Heading h1 = new Heading(1, new Text.Plain("Same"));
        Heading h2 = new Heading(2, new Text.Plain("Same"));

        Assertions.assertNotEquals(h1, h2);
    }

    @Test
    void notEqualsForDifferentContent() {
        Heading h1 = new Heading(1, new Text.Plain("One"));
        Heading h2 = new Heading(1, new Text.Plain("Two"));

        Assertions.assertNotEquals(h1, h2);
    }
}
