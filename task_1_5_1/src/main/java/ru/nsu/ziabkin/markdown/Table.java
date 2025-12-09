package ru.nsu.ziabkin.markdown;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
 * Represents Table element
 */
public final class Table implements Element {
    public static final int ALIGN_LEFT = -1;
    public static final int ALIGN_CENTER = 0;
    public static final int ALIGN_RIGHT = 1;

    private final List<Integer> alignments;
    private final List<List<Element>> rows;

    /*
     * Represents Table element
     */
    Table(List<Integer> alignments, List<List<Element>> rows) {
        this.alignments = List.copyOf(alignments);
        this.rows = rows.stream()
                .map(List::copyOf)
                .toList();
    }

    public static class Builder {
        private final List<Integer> alignments = new ArrayList<>();
        private final List<List<Element>> rows = new ArrayList<>();
        private int rowLimit = Integer.MAX_VALUE;

        public Builder withAlignments(int... alignments) {
            this.alignments.clear();
            for (int a : alignments) {
                if (a != ALIGN_LEFT && a != ALIGN_RIGHT && a != ALIGN_CENTER) {
                    throw new IllegalArgumentException("Unknown alignment: " + a);
                }
                this.alignments.add(a);
            }
            return this;
        }

        public Builder withRowLimit(int rowLimit) {
            if (rowLimit <= 0) {
                throw new IllegalArgumentException("rowLimit must be > 0");
            }
            this.rowLimit = rowLimit;
            return this;
        }

        public Builder addRow(Object... cells) {
            if (rows.size() >= rowLimit) {
                return this;
            }
            List<Element> row = new ArrayList<>(cells.length);
            for (Object c : cells) {
                if (c instanceof Element) {
                    row.add((Element) c);
                } else {
                    row.add(new Text.Plain(String.valueOf(c)));
                }
            }
            rows.add(row);
            return this;
        }

        public Table build() {
            if (rows.isEmpty()) {
                throw new IllegalStateException("Table must have at least one row (header)");
            }
            return new Table(alignments, rows);
        }
    }

    @Override
    public String toMarkdown() {
        if (rows.isEmpty()) {
            return "";
        }

        int columns = rows.stream()
                .mapToInt(List::size)
                .max()
                .orElse(0);

        List<List<String>> rendered = new ArrayList<>();
        for (List<Element> row : rows) {
            List<String> r = new ArrayList<>();
            for (int col = 0; col < columns; col++) {
                if (col < row.size()) {
                    r.add(row.get(col).toMarkdown());
                } else {
                    r.add("");
                }
            }
            rendered.add(r);
        }

        int[] widths = new int[columns];
        for (List<String> row : rendered) {
            for (int col = 0; col < columns; col++) {
                widths[col] = Math.max(widths[col], row.get(col).length());
            }
        }

        StringBuilder sb = new StringBuilder();

        appendRow(sb, rendered.getFirst(), widths);
        sb.append('\n');

        appendAlignmentRow(sb, widths);
        sb.append('\n');
        for (int r = 1; r < rendered.size(); r++) {
            appendRow(sb, rendered.get(r), widths);
            if (r + 1 < rendered.size()) {
                sb.append('\n');
            }
        }

        return sb.toString();
    }

    private void appendRow(StringBuilder sb, List<String> row, int[] widths) {
        sb.append('|');
        for (int col = 0; col < widths.length; col++) {
            String text = row.get(col);
            int align = getAlignment(col);
            String padded = pad(text, widths[col], align);
            sb.append(' ')
                    .append(padded)
                    .append(' ')
                    .append('|');
        }
    }

    private void appendAlignmentRow(StringBuilder sb, int[] widths) {
        sb.append('|');
        for (int col = 0; col < widths.length; col++) {
            int width = widths[col];
            int align = getAlignment(col);

            String segment;
            switch (align) {
                case ALIGN_RIGHT:
                    if (width < 2) {
                        width = 2;
                    }
                    segment = "-".repeat(width - 1) + ":";
                    break;
                case ALIGN_CENTER:
                    if (width < 3) {
                        width = 3;
                    }
                    segment = ":" + "-".repeat(width - 2) + ":";
                    break;
                case ALIGN_LEFT:
                default:
                    if (width < 1) {
                        width = 1;
                    }
                    segment = "-".repeat(width);
                    break;
            }

            sb.append(' ')
                    .append(segment)
                    .append(' ')
                    .append('|');
        }
    }

    private int getAlignment(int col) {
        if (col < alignments.size()) {
            return alignments.get(col);
        }
        return ALIGN_LEFT;
    }

    private static String pad(String text, int width, int align) {
        int len = text.length();
        if (len >= width) {
            return text;
        }

        int diff = width - len;
        switch (align) {
            case ALIGN_RIGHT: {
                return " ".repeat(diff) + text;
            }
            case ALIGN_CENTER: {
                int left = diff / 2;
                int right = diff - left;
                return " ".repeat(left) + text + " ".repeat(right);
            }
            case ALIGN_LEFT:
            default: {
                return text + " ".repeat(diff);
            }
        }
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
        if (!(o instanceof Table)) {
            return false;
        }
        Table table = (Table) o;
        return Objects.equals(alignments, table.alignments)
                && Objects.equals(rows, table.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alignments, rows);
    }
}

