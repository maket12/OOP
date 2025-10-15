package graph;

import java.util.*;

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
        adjacencyMatrix[vertex2][vertex1] = true; // для неориентированного графа
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
        // Реализация чтения графа из файла (например, CSV)
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
        return Arrays.deepEquals(adjacencyMatrix, that.adjacencyMatrix);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            builder.append(i).append(": ");
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
}
