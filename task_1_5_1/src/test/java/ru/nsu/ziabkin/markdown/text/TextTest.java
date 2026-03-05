package ru.nsu.ziabkin.markdown.text;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for different variables of formatted text: Plain, Bold, Italic, Strike, Code.
 */
public class TextTest {
    @Test
    void getValue() {
        Text.Plain plain = new Text.Plain("Damn");
        Assertions.assertEquals("Damn", plain.getValue());
    }

    @Test
    void toStringTest() {
        Text.Plain plain = new Text.Plain("Damn");
        Assertions.assertEquals("Damn", plain.toString());
    }

    @Test
    void plainToMarkdownReturnsValueAsIs() {
        Text.Plain plain = new Text.Plain("Hello");
        Assertions.assertEquals("Hello", plain.toMarkdown());
    }

    @Test
    void boldToMarkdownWrapsWithDoubleAsterisks() {
        Text.Bold bold = new Text.Bold("Hello");
        Assertions.assertEquals("**Hello**", bold.toMarkdown());
    }

    @Test
    void italicToMarkdownWrapsWithSingleAsterisks() {
        Text.Italic italic = new Text.Italic("Hello");
        Assertions.assertEquals("*Hello*", italic.toMarkdown());
    }

    @Test
    void strikeToMarkdownWrapsWithTildes() {
        Text.Strike strike = new Text.Strike("Hello");
        Assertions.assertEquals("~~Hello~~", strike.toMarkdown());
    }

    @Test
    void codeToMarkdownWrapsWithBackticks() {
        Text.Code code = new Text.Code("x = 42");
        Assertions.assertEquals("`x = 42`", code.toMarkdown());
    }

    @Test
    void equalsReturnsTrueForSameSubclassAndValue() {
        Text.Bold bold1 = new Text.Bold("Hello");
        Text.Bold bold2 = new Text.Bold("Hello");

        Assertions.assertEquals(bold1, bold2);
        Assertions.assertEquals(bold1.hashCode(), bold2.hashCode());
    }

    @Test
    void equalsReturnsFalseForDifferentValues() {
        Text.Bold bold1 = new Text.Bold("Hello");
        Text.Bold bold2 = new Text.Bold("World");

        Assertions.assertNotEquals(bold1, bold2);
    }

    @Test
    void equalsReturnsFalseForDifferentSubclassesEvenWithSameValue() {
        Text.Bold bold = new Text.Bold("Hello");
        Text.Italic italic = new Text.Italic("Hello");

        Assertions.assertNotEquals(bold, italic);
    }
}
