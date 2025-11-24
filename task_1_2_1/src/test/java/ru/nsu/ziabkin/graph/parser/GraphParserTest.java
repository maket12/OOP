package ru.nsu.ziabkin.graph.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import ru.nsu.ziabkin.graph.AdjacencyMapGraph;
import ru.nsu.ziabkin.graph.Graph;


class GraphParserTest {

    @Test
    void testParseWithNullGraph() {
        GraphParser parser = new GraphParser();
        Assertions.assertThrows(RuntimeException.class, () -> {
            parser.parse(null, "nonexistent.txt");
        });
    }

    @Test
    void testParseWithEmptyFilename() {
        GraphParser parser = new GraphParser();
        Graph graph = new AdjacencyMapGraph();

        Assertions.assertThrows(RuntimeException.class, () -> {
            parser.parse(graph, "");
        });
    }

    @Test
    void testParseWithNonexistentFile() {
        GraphParser parser = new GraphParser();
        Graph graph = new AdjacencyMapGraph();

        Assertions.assertThrows(RuntimeException.class, () -> {
            parser.parse(graph, "nonexistent_file_12345.txt");
        });
    }

    @Test
    void testParseEmptyFile(@TempDir File tempDir) throws IOException {
        File emptyFile = new File(tempDir, "empty.txt");
        emptyFile.createNewFile();

        GraphParser parser = new GraphParser();
        Graph graph = new AdjacencyMapGraph();

        Assertions.assertDoesNotThrow(() -> {
            parser.parse(graph, emptyFile.getAbsolutePath());
        });

        Assertions.assertEquals(0, graph.getVertexCount());
    }

    @Test
    void testParseValidGraph(@TempDir File tempDir) throws IOException {
        File graphFile = new File(tempDir, "graph.txt");
        try (FileWriter writer = new FileWriter(graphFile)) {
            writer.write("3 2\n");
            writer.write("0 1\n");
            writer.write("1 2\n");
        }

        GraphParser parser = new GraphParser();
        Graph graph = new AdjacencyMapGraph();

        Assertions.assertDoesNotThrow(() -> {
            parser.parse(graph, graphFile.getAbsolutePath());
        });

        Assertions.assertEquals(3, graph.getVertexCount());
        Assertions.assertTrue(graph.getNeighbors(0).contains(1));
        Assertions.assertTrue(graph.getNeighbors(1).contains(2));
    }
}