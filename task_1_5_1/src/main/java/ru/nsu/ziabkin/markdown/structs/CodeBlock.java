package ru.nsu.ziabkin.markdown.structs;

import java.util.Objects;
import ru.nsu.ziabkin.markdown.Element;

/*
 * Represents CodeBlock element
 */
public final class CodeBlock implements Element {
    private final String language;
    private final String code;

    /*
     * Represents CodeBlock element
     */
    public CodeBlock(String language, String code) {
        this.language = language;
        this.code = Objects.requireNonNull(code, "code must be not null");
    }

    /*
     * Builds UnorderedList element
     */
    public static class Builder {
        private String language;
        private final StringBuilder code = new StringBuilder();

        /*
         * Use given language
         */
        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }

        public Builder addLine(String line) {
            if (!code.isEmpty()) {
                code.append('\n');
            }
            code.append(line);
            return this;
        }

        public CodeBlock build() {
            return new CodeBlock(language, code.toString());
        }
    }

    /*
     * converts method into markdown
     */
    @Override
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append("```");
        if (language != null && !language.isEmpty()) {
            sb.append(language);
        }
        sb.append('\n')
                .append(code)
                .append("\n```");
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

        if (!(o instanceof CodeBlock)) {
            return false;
        }

        CodeBlock codeBlock = (CodeBlock) o;

        return Objects.equals(language, codeBlock.language)
                && Objects.equals(code, codeBlock.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, code);
    }
}