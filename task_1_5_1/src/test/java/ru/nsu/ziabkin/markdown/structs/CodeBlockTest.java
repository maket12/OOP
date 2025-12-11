package ru.nsu.ziabkin.markdown.structs;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for CodeBlock.
 */
public class CodeBlockTest {
    @Test
    void codeBlockWithLanguageAndMultipleLinesRenders() {
        CodeBlock block = new CodeBlock.Builder()
                .withLanguage("java")
                .addLine("System.out.println(\"Hello\");")
                .addLine("int x = 42;")
                .build();

        String expected = String.join("\n",
                "```java",
                "System.out.println(\"Hello\");",
                "int x = 42;",
                "```"
        );

        Assertions.assertEquals(expected, block.toString());
    }

    @Test
    void codeBlockWithLanguageAndMultipleLinesRendersCorrectly() {
        CodeBlock block = new CodeBlock.Builder()
                .withLanguage("java")
                .addLine("System.out.println(\"Hello\");")
                .addLine("int x = 42;")
                .build();

        String expected = String.join("\n",
                "```java",
                "System.out.println(\"Hello\");",
                "int x = 42;",
                "```"
        );

        Assertions.assertEquals(expected, block.toMarkdown());
    }

    @Test
    void codeBlockWithoutLanguageRendersTripleBackticksOnly() {
        CodeBlock block = new CodeBlock.Builder()
                .addLine("print('hi')")
                .build();

        String expected = String.join("\n",
                "```",
                "print('hi')",
                "```"
        );

        Assertions.assertEquals(expected, block.toMarkdown());
    }

    @Test
    void equalsAndHashCodeForSameLanguageAndCode() {
        CodeBlock b1 = new CodeBlock.Builder()
                .withLanguage("python")
                .addLine("x = 1")
                .addLine("print(x)")
                .build();

        CodeBlock b2 = new CodeBlock.Builder()
                .withLanguage("python")
                .addLine("x = 1")
                .addLine("print(x)")
                .build();

        Assertions.assertEquals(b1, b2);
        Assertions.assertEquals(b1.hashCode(), b2.hashCode());
    }

    @Test
    void notEqualsForDifferentCode() {
        CodeBlock b1 = new CodeBlock.Builder()
                .withLanguage("python")
                .addLine("x = 1")
                .build();

        CodeBlock b2 = new CodeBlock.Builder()
                .withLanguage("python")
                .addLine("x = 2")
                .build();

        Assertions.assertNotEquals(b1, b2);
    }

    @Test
    void notEqualsForDifferentLanguage() {
        CodeBlock b1 = new CodeBlock.Builder()
                .withLanguage("python")
                .addLine("x = 1")
                .build();

        CodeBlock b2 = new CodeBlock.Builder()
                .withLanguage("java")
                .addLine("x = 1")
                .build();

        Assertions.assertNotEquals(b1, b2);
    }
}
