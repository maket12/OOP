package graph;

import java.util.List;

public class IncidenceMatrixGraph implements Graph {
    private boolean[][] incidenceMatrix;
    private int vertexCount;
    private int edgeCount;

    public IncidenceMatrixGraph(int vertices, int edges) {
        incidenceMatrix = new boolean[vertices][edges];
        vertexCount = vertices;
        edgeCount = edges;
    }

    @Override
    public void addVertex(int vertex) {
        // Реализовать добавление вершины в матрицу инцидентности
    }

    @Override
    public void removeVertex(int vertex) {
        // Реализовать удаление вершины из матрицы инцидентности
    }

    @Override
    public void addEdge(int vertex1, int vertex2) {
        // Реализовать добавление ребра в матрицу инцидентности
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) {
        // Реализовать удаление ребра из матрицы инцидентности
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        // Реализовать получение соседей вершины из матрицы инцидентности
        return null;
    }

    @Override
    public void readFromFile(String filename) {
        // Реализовать чтение графа из файла
    }

    @Override
    public List<Integer> topologicalSort() {
        // Реализовать топологическую сортировку
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        // Реализовать сравнение
        return false;
    }

    @Override
    public String toString() {
        // Реализовать вывод
        return null;
    }
}
