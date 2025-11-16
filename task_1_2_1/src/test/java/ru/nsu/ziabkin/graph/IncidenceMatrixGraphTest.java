package ru.nsu.ziabkin.graph;

import java.util.List;
import org.junit.jupiter.api.Assertions.Assertions.assertEquals;
import org.junit.jupiter.api.Assertions.Assertions.assertNotEquals;
import org.junit.jupiter.api.Assertions.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {

    @Test
    void testAddVertexAndEdge() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(3, 3);
        graph.addVertex(0);
        graph.addVertex(1);

        graph.addEdge(0, 1);

        List<Integer> neighbors0 = graph.getNeighbors(0);
        List<Integer> neighbors1 = graph.getNeighbors(1);

        Assertions.assertTrue(neighbors0.contains(1));
        Assertions.assertEquals(0, neighbors1.size());
    }

    @Test
    void testTopologicalSort() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(3, 3);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        Assertions.assertEquals(List.of(0, 1, 2), sorted);
    }

    @Test
    void testEqualsGraphs() {
        IncidenceMatrixGraph g1 = new IncidenceMatrixGraph(2, 2); // Только 2 вершины
        g1.addEdge(0, 1);

        IncidenceMatrixGraph g2 = new IncidenceMatrixGraph(2, 2); // Только 2 вершины
        g2.addEdge(0, 1);

        Assertions.assertEquals(g1, g2);

        IncidenceMatrixGraph g3 = new IncidenceMatrixGraph(3, 2); // 3 вершины
        g3.addEdge(0, 1);

        Assertions.assertNotEquals(g1, g3);
    }

    @Test
    void testToString() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(2, 2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        String output = graph.toString();
        Assertions.assertTrue(output.contains("0 -> 1"));
        Assertions.assertTrue(output.contains("1 -> null"));
    }
}