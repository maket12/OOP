package ru.nsu.ziabkin.graph;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AdjacencyMatrixGraphTest {
    private AdjacencyMatrixGraph makeGraph(int size) {
        AdjacencyMatrixGraph g = new AdjacencyMatrixGraph();
        for (int i = 0; i < size; i++) {
            g.addVertex(i);
        }
        return g;
    }

    @Test
    void testAddVertexAndEdge() {
        AdjacencyMatrixGraph graph = makeGraph(3);
        graph.addEdge(0, 1);

        List<Integer> neighbors0 = graph.getNeighbors(0);
        List<Integer> neighbors1 = graph.getNeighbors(1);

        Assertions.assertTrue(neighbors0.contains(1));
        Assertions.assertEquals(0, neighbors1.size());
    }

    @Test
    void testTopologicalSort() {
        AdjacencyMatrixGraph graph = makeGraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        Assertions.assertEquals(List.of(0, 1, 2), sorted);
    }

    @Test
    void testEqualsGraphs() {
        AdjacencyMatrixGraph g1 = makeGraph(3);
        g1.addEdge(0, 1);

        AdjacencyMatrixGraph g2 = makeGraph(3);
        g2.addEdge(0, 1);

        Assertions.assertEquals(g1, g2);

        g2.addVertex(2);
        g2.addVertex(3);
        Assertions.assertNotEquals(g1, g2);
    }

    @Test
    void testToString() {
        AdjacencyMatrixGraph graph = makeGraph(2);
        graph.addEdge(0, 1);

        String output = graph.toString();
        Assertions.assertTrue(output.contains("0 -> 1"));
        Assertions.assertTrue(output.contains("1 -> "));
    }
}