package com.solution.graph.parser;

import com.solution.graph.Graph;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GraphParser {

    public void parse(Graph graph, String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            if (!scanner.hasNextLine()) return;

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
                if (line.isEmpty()) continue;

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
