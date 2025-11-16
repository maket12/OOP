package ru.nsu.ziabkin.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of the {Graph} interface using an adjacency matrix.
 * The graph is directed: an edge from {v1} to {v2} is represented as
 * {adjacencyMatrix[v1][v2] = true}.
 * The matrix expands dynamically when new vertices are added.
 */
public class AdjacencyMatrixGraph implements Graph {
    private boolean[][] adjacencyMatrix;
    private int vertexCount;

    public AdjacencyMatrixGraph() {
        adjacencyMatrix = new boolean[0][0];
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
        if (vertex < 0 || vertex >= adjacencyMatrix.length) {
            return Collections.emptyList();
        }

        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertex][i]) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AdjacencyMatrixGraph that = (AdjacencyMatrixGraph) obj;

        if (this.vertexCount != that.vertexCount) {
            return false;
        }

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
