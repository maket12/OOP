package ru.nsu.ziabkin.markdown.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for Image.
 */
public class ImageTest {
    @Test
    void toStringTest() {
        Image img = new Image("Alt text", "https://example.com/image.png");
        Assertions.assertEquals("![Alt text](https://example.com/image.png)", img.toString());
    }

    @Test
    void simpleImageRendersCorrectly() {
        Image img = new Image("Alt text", "https://example.com/image.png");
        Assertions.assertEquals("![Alt text](https://example.com/image.png)", img.toMarkdown());
    }

    @Test
    void imageWithTitleRendersCorrectly() {
        Image img = new Image("Logo", "https://example.com/logo.png", "Site logo");
        Assertions.assertEquals("![Logo](https://example.com/logo.png \"Site logo\")", img.toMarkdown());
    }

    @Test
    void equalsAndHashCodeForSameFields() {
        Image i1 = new Image("Alt", "https://example.com/a.png", "Title");
        Image i2 = new Image("Alt", "https://example.com/a.png", "Title");

        Assertions.assertEquals(i1, i2);
        Assertions.assertEquals(i1.hashCode(), i2.hashCode());
    }

    @Test
    void notEqualsForDifferentAlt() {
        Image i1 = new Image("First", "https://example.com/a.png", "Title");
        Image i2 = new Image("Second", "https://example.com/a.png", "Title");

        Assertions.assertNotEquals(i1, i2);
    }

    @Test
    void notEqualsForDifferentUrl() {
        Image i1 = new Image("Alt", "https://example.com/one.png", "Title");
        Image i2 = new Image("Alt", "https://example.com/two.png", "Title");

        Assertions.assertNotEquals(i1, i2);
    }

    @Test
    void notEqualsForDifferentTitleNullVsNonNull() {
        Image i1 = new Image("Alt", "https://example.com/a.png");
        Image i2 = new Image("Alt", "https://example.com/a.png", "Title");

        Assertions.assertNotEquals(i1, i2);
    }
}
