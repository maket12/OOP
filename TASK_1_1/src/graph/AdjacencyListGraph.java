package graph;

import java.util.*;

public class AdjacencyListGraph implements Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyListGraph() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(int vertex) {
        adjacencyList.putIfAbsent(vertex, new ArrayList<>());
    }

    @Override
    public void removeVertex(int vertex) {
        adjacencyList.remove(vertex);
        for (List<Integer> neighbors : adjacencyList.values()) {
            neighbors.remove(Integer.valueOf(vertex));
        }
    }

    @Override
    public void addEdge(int vertex1, int vertex2) {
        adjacencyList.get(vertex1).add(vertex2);
        adjacencyList.get(vertex2).add(vertex1);
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) {
        adjacencyList.get(vertex1).remove(Integer.valueOf(vertex2));
        adjacencyList.get(vertex2).remove(Integer.valueOf(vertex1));
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void readFromFile(String filename) {
        // Реализовать чтение графа из файла
    }

    @Override
    public List<Integer> topologicalSort() {
        List<Integer> sortedList = new ArrayList<>();
        Map<Integer, Integer> inDegree = new HashMap<>();

        for (Integer vertex : adjacencyList.keySet()) {
            inDegree.put(vertex, 0);
        }

        for (List<Integer> neighbors : adjacencyList.values()) {
            for (Integer neighbor : neighbors) {
                inDegree.put(neighbor, inDegree.get(neighbor) + 1);
            }
        }

        Queue<Integer> queue = new LinkedList<>();

        for (Map.Entry<Integer, Integer> entry : inDegree.entrySet()) {
            if (entry.getValue() == 0) {
                queue.add(entry.getKey());
            }
        }

        while (!queue.isEmpty()) {
            Integer vertex = queue.poll();
            sortedList.add(vertex);

            for (Integer neighbor : adjacencyList.get(vertex)) {
                inDegree.put(neighbor, inDegree.get(neighbor) - 1);
                if (inDegree.get(neighbor) == 0) {
                    queue.add(neighbor);
                }
            }
        }

        return sortedList;
    }

    @Override
    public boolean equals(Object obj) {
        // Реализовать сравнение
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            builder.append(entry.getKey()).append(": ");
            for (Integer neighbor : entry.getValue()) {
                builder.append(neighbor).append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
