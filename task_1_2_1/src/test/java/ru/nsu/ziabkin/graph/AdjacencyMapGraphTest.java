package ru.nsu.ziabkin.graph;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdjacencyListGraphTest {

    @Test
    void testAddVertexAndEdge() {
        Graph graph = new AdjacencyListGraph();
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
        Graph graph = new AdjacencyListGraph();
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
        AdjacencyListGraph g1 = new AdjacencyListGraph();
        g1.addVertex(0);
        g1.addVertex(1);
        g1.addEdge(0, 1);

        AdjacencyListGraph g2 = new AdjacencyListGraph();
        g2.addVertex(0);
        g2.addVertex(1);
        g2.addEdge(0, 1);

        Assertions.assertEquals(g1, g2);

        g2.addVertex(2);
        Assertions.assertNotEquals(g1, g2);
    }

    @Test
    void testToString() {
        AdjacencyListGraph graph = new AdjacencyListGraph();
        graph.addVertex(0);
        graph.addVertex(1);
        graph.addEdge(0, 1);

        String output = graph.toString();
        Assertions.assertTrue(output.contains("0 -> 1"));
        Assertions.assertTrue(output.contains("1 -> null"));
    }
}