package ru.nsu.ziabkin.graph.parser;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GraphParserTest {
    @Test
    void testParsing() {
        parser = new GraphParser();
        res = parser.parse(null, "")
        Assertions.assertEquals(null, res)
    }
}
