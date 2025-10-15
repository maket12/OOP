package com.solution.graph;

import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

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
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) {
        adjacencyList.get(vertex1).remove(Integer.valueOf(vertex2));
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        return adjacencyList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void readFromFile(String filename) {
        try (Scanner scanner = new Scanner(new java.io.File(filename))) {
            this.adjacencyList = new HashMap<>();

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

            for (int i = 0; i < vertexCount; i++) {
                addVertex(i);
            }

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

                addEdge(from, to);
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
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        AdjacencyListGraph other = (AdjacencyListGraph) obj;

        if (!this.adjacencyList.keySet().equals(other.adjacencyList.keySet())) {
            return false;
        }

        for (Integer vertex : adjacencyList.keySet()) {
            List<Integer> neighborsThis = adjacencyList.get(vertex);
            List<Integer> neighborsOther = other.adjacencyList.get(vertex);

            Set<Integer> setThis = new HashSet<>(neighborsThis);
            Set<Integer> setOther = new HashSet<>(neighborsOther);

            if (!setThis.equals(setOther)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Graph:\n");
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            int vertex = entry.getKey();
            List<Integer> neighbors = entry.getValue();
            for (Integer neighbor : neighbors) {
                builder.append(vertex).append(" -> ").append(neighbor).append("\n");
            }
            if (neighbors.isEmpty()) {
                builder.append(vertex).append(" -> null\n");
            }
        }
        return builder.toString();
    }

    @Override
    public int getVertexCount() {
        return adjacencyList.size();
    }
}
