package com.solution.graph;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] adjacencyMatrix;
    private int vertexCount;

    public AdjacencyMatrixGraph(int size) {
        adjacencyMatrix = new boolean[size][size];
        vertexCount = 0;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex >= adjacencyMatrix.length) {
            expandMatrix(vertex + 1);
        }
        vertexCount++;
    }

    @Override
    public void removeVertex(int vertex) {
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            adjacencyMatrix[vertex][i] = false;
            adjacencyMatrix[i][vertex] = false;
        }
        vertexCount--;
    }

    @Override
    public void addEdge(int vertex1, int vertex2) {
        adjacencyMatrix[vertex1][vertex2] = true;
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) {
        adjacencyMatrix[vertex1][vertex2] = false;
        adjacencyMatrix[vertex2][vertex1] = false;
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertex][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public void readFromFile(String filename) {
        try (Scanner scanner = new Scanner(new java.io.File(filename))) {
            if (!scanner.hasNextLine()) {
                return;
            }

            String firstLine = scanner.nextLine().trim();
            String[] parts = firstLine.split("\\s+");

            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid file format: first line should contain V and E");
            }

            int vertexCount = Integer.parseInt(parts[0]);
            int edgeCount = Integer.parseInt(parts[1]);

            this.adjacencyMatrix = new boolean[vertexCount][vertexCount];
            this.vertexCount = vertexCount;

            for (int i = 0; i < edgeCount && scanner.hasNextLine(); i++) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] edgeParts = line.split("\\s+");
                if (edgeParts.length < 2) {
                    throw new IllegalArgumentException("Invalid edge format at line " + (i + 2));
                }

                int from = Integer.parseInt(edgeParts[0]);
                int to = Integer.parseInt(edgeParts[1]);

                if (from < 0 || from >= vertexCount || to < 0 || to >= vertexCount) {
                    throw new IllegalArgumentException("Invalid vertex index in edge: " + from + " -> " + to);
                }

                adjacencyMatrix[from][to] = true;
            }

        } catch (java.io.FileNotFoundException e) {
            throw new RuntimeException("File not found: " + filename, e);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Invalid number format in file", e);
        } catch (Exception e) {
            throw new RuntimeException("Error reading graph from file: " + filename, e);
        }
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sortedList = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyMatrix.length];
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (!visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        while (!stack.isEmpty()) {
            sortedList.add(stack.pop());
        }

        return sortedList;
    }

    private void topologicalSortUtil(int v, boolean[] visited, Stack<Integer> stack) {
        visited[v] = true;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[v][i] && !visited[i]) {
                topologicalSortUtil(i, visited, stack);
            }
        }

        stack.push(v);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) obj;

        if (this.vertexCount != that.vertexCount) return false;

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (this.adjacencyMatrix[i][j] != that.adjacencyMatrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Graph adjacency matrix:\n");
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            builder.append(i).append(" -> ");
            for (int j = 0; j < adjacencyMatrix[i].length; j++) {
                if (adjacencyMatrix[i][j]) {
                    builder.append(j).append(" ");
                }
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    private void expandMatrix(int newSize) {
        boolean[][] newMatrix = new boolean[newSize][newSize];
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            System.arraycopy(adjacencyMatrix[i], 0, newMatrix[i], 0, adjacencyMatrix[i].length);
        }
        adjacencyMatrix = newMatrix;
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }
}
