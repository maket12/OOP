package ru.nsu.ziabkin.markdown;

import java.util.Objects;

/*
 * Represents CodeBlock element
 */
final class CodeBlock implements Element {
    private final String language; // может быть null/пустой
    private final String code;

    private CodeBlock(String language, String code) {
        this.language = language;
        this.code = Objects.requireNonNull(code);
    }

    public static class Builder {
        private String language;
        private final StringBuilder code = new StringBuilder();

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