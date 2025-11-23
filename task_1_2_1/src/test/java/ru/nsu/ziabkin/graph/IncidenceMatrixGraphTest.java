package ru.nsu.ziabkin.graph;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IncidenceMatrixGraphTest {
    private IncidenceMatrixGraph makeGraph(int size) {
        IncidenceMatrixGraph g = new IncidenceMatrixGraph(0, 0);
        for (int i = 0; i < size; i++) {
            g.addVertex(i);
        }
        return g;
    }

    @Test
    void testAddVertexAndEdge() {
        IncidenceMatrixGraph graph = makeGraph(3);
        graph.addEdge(0, 1);

        List<Integer> neighbors0 = graph.getNeighbors(0);
        List<Integer> neighbors1 = graph.getNeighbors(1);

        Assertions.assertTrue(neighbors0.contains(1));
        Assertions.assertEquals(0, neighbors1.size());
    }

    @Test
    void testTopologicalSort() {
        IncidenceMatrixGraph graph = makeGraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        List<Integer> sorted = graph.topologicalSort();
        Assertions.assertEquals(List.of(0, 1, 2), sorted);
    }

    @Test
    void testEqualsGraphs() {
        IncidenceMatrixGraph g1 = makeGraph(2);
        g1.addEdge(0, 1);

        IncidenceMatrixGraph g2 = makeGraph(2);
        g2.addEdge(0, 1);

        Assertions.assertEquals(g1, g2);

        IncidenceMatrixGraph g3 = makeGraph(3);
        g3.addEdge(0, 1);

        Assertions.assertNotEquals(g1, g3);
    }

    @Test
    void testToString() {
        IncidenceMatrixGraph graph = makeGraph(2);
        graph.addEdge(0, 1);

        String output = graph.toString();
        Assertions.assertTrue(output.contains("0 -> 1"));
        Assertions.assertTrue(output.contains("1 -> null") || output.contains("1 -> "));
    }
}