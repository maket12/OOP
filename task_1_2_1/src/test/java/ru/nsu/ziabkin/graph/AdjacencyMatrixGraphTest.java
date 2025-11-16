package ru.nsu.ziabkin.graph;

import java.util.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdjacencyMatrixGraphTest {

    @Test
    void testAddVertexAndEdge() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(3);
        graph.addVertex(0);
        graph.addVertex(1);

        graph.addEdge(0, 1);

        List<Integer> neighbors0 = graph.getNeighbors(0);
        List<Integer> neighbors1 = graph.getNeighbors(1);

        assertTrue(neighbors0.contains(1));
        assertEquals(0, neighbors1.size());
    }

    @Test
    void testTopologicalSort() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(3);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        assertEquals(List.of(0, 1, 2), sorted);
    }

    @Test
    void testEqualsGraphs() {
        AdjacencyMatrixGraph g1 = new AdjacencyMatrixGraph(3);
        g1.addVertex(0); g1.addVertex(1);
        g1.addEdge(0, 1);

        AdjacencyMatrixGraph g2 = new AdjacencyMatrixGraph(3);
        g2.addVertex(0);
        g2.addVertex(1);
        g2.addEdge(0, 1);

        assertEquals(g1, g2);

        g2.addVertex(2);
        assertNotEquals(g1, g2);
    }

    @Test
    void testToString() {
        AdjacencyMatrixGraph graph = new AdjacencyMatrixGraph(2);
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        String output = graph.toString();
        assertTrue(output.contains("0 -> 1"));
        assertTrue(output.contains("1 -> "));
    }
}