package ru.nsu.ziabkin.markdown.structs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nsu.ziabkin.markdown.text.Text;

/**
 * Tests for BlockQuote.
 */
public class BlockQuoteTest {
    @Test
    void singleLineBlockQuoteRenders() {
        BlockQuote quote = new BlockQuote.Builder()
                .addLine("Hello")
                .build();

        Assertions.assertEquals("> Hello", quote.toString());
    }

    @Test
    void singleLineBlockQuoteRendersCorrectly() {
        BlockQuote quote = new BlockQuote.Builder()
                .addLine("Hello")
                .build();

        Assertions.assertEquals("> Hello", quote.toMarkdown());
    }

    @Test
    void multiLineBlockQuoteRendersEachLineWithPrefix() {
        BlockQuote quote = new BlockQuote.Builder()
                .addLine("Line one")
                .addLine(new Text.Bold("Line two"))
                .build();

        String expected = String.join("\n",
                "> Line one",
                "> **Line two**"
        );

        Assertions.assertEquals(expected, quote.toMarkdown());
    }

    @Test
    void equalsAndHashCodeForSameLines() {
        BlockQuote q1 = new BlockQuote.Builder()
                .addLine("A")
                .addLine("B")
                .build();

        BlockQuote q2 = new BlockQuote.Builder()
                .addLine("A")
                .addLine("B")
                .build();

        Assertions.assertEquals(q1, q2);
        Assertions.assertEquals(q1.hashCode(), q2.hashCode());
    }

    @Test
    void notEqualsForDifferentLines() {
        BlockQuote q1 = new BlockQuote.Builder()
                .addLine("A")
                .build();

        BlockQuote q2 = new BlockQuote.Builder()
                .addLine("A")
                .addLine("B")
                .build();

        Assertions.assertNotEquals(q1, q2);
    }
}
