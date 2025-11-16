package ru.nsu.ziabkin.graph.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import ru.nsu.ziabkin.graph.Graph;

/**
 * Utility class responsible for reading a graph structure from a text file.
 * <p>
 * The parser is independent of the specific graph implementation
 * and can fill any object that implements the {@link Graph} interface.
 * </p>
 *
 * <p><b>Expected file format:</b></p>
 * <pre>
 * V E
 * from1 to1
 * from2 to2
 * ...
 * </pre>
 * Where:
 * <ul>
 *   <li><b>V</b> — number of vertices</li>
 *   <li><b>E</b> — number of edges</li>
 *   <li>Each subsequent line — an edge defined by two integers:
 *       source vertex and target vertex</li>
 * </ul>
 *
 * <p>Example:</p>
 * <pre>
 * 4 3
 * 0 1
 * 1 2
 * 2 3
 * </pre>
 *
 * <p>This describes a directed graph with 4 vertices and 3 edges:
 * 0 → 1, 1 → 2, 2 → 3.</p>
 */
public class GraphParser {

    /**
     * Parses the graph from the specified file and fills the given {@link Graph} instance.
     * <p>
     * All vertices from {@code 0} to {@code V - 1} are automatically added before edges.
     * </p>
     *
     * @param graph    the graph instance to populate
     * @param filename the path to the input file
     * @throws RuntimeException if the file is not found or has an invalid format
     */
    public void parse(Graph graph, String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (!scanner.hasNextLine()) {
                return;
            }

            String[] parts = scanner.nextLine().trim().split("\\s+");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid file format: expected V and E");
            }

            int vertexCount = Integer.parseInt(parts[0]);
            int edgeCount = Integer.parseInt(parts[1]);

            for (int i = 0; i < vertexCount; i++) {
                graph.addVertex(i);
            }

            for (int i = 0; i < edgeCount && scanner.hasNextLine(); i++) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] edgeParts = line.split("\\s+");
                if (edgeParts.length >= 2) {
                    int from = Integer.parseInt(edgeParts[0]);
                    int to = Integer.parseInt(edgeParts[1]);
                    graph.addEdge(from, to);
                }
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filename, e);
        } catch (Exception e) {
            throw new RuntimeException("Error reading graph from file: " + filename, e);
        }
    }
}
