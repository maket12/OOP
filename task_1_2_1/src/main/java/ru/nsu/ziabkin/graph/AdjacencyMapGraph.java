package ru.nsu.ziabkin.graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import ru.nsu.ziabkin.graph.sorter.TopologicalSorter;


/**
 * A graph implementation based on an adjacency map, where each vertex
 * maps to a list of outgoing adjacent vertices (directed edges).
 * This implementation supports dynamic addition and removal of vertices
 * and edges. Vertices are represented as integers.
 */
public class AdjacencyMapGraph implements Graph {
    private Map<Integer, List<Integer>> adjacencyList;

    public AdjacencyMapGraph() {
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
        return adjacencyList.getOrDefault(vertex, Collections.emptyList());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AdjacencyMapGraph other = (AdjacencyMapGraph) obj;

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

    @Override
    public List<Integer> topologicalSort() {
        return new TopologicalSorter().sort(this).order();
    }
}
