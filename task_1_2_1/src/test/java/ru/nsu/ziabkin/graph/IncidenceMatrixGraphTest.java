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
    void testAddVertex() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(0, 0);

        graph.addVertex(0);

        Assertions.assertEquals(1, graph.getVertexCount());

        graph.addVertex(1);
        Assertions.assertEquals(2, graph.getVertexCount());

        List<Integer> neighbors = graph.getNeighbors(0);
        Assertions.assertNotNull(neighbors);
        Assertions.assertEquals(0, neighbors.size());
    }

    @Test
    void testAddEdge() {
        IncidenceMatrixGraph graph = makeGraph(3);

        graph.addEdge(0, 1);

        List<Integer> neighbors0 = graph.getNeighbors(0);
        List<Integer> neighbors1 = graph.getNeighbors(1);

        Assertions.assertTrue(neighbors0.contains(1));
        Assertions.assertEquals(0, neighbors1.size());

        graph.addEdge(1, 2);
        List<Integer> neighbors1After = graph.getNeighbors(1);
        Assertions.assertTrue(neighbors1After.contains(2));
    }

    @Test
    void testGetVertexCount() {
        // Тест для пустого графа
        IncidenceMatrixGraph emptyGraph = new IncidenceMatrixGraph(0, 0);
        Assertions.assertEquals(0, emptyGraph.getVertexCount());

        // Тест для графа с начальным размером
        IncidenceMatrixGraph graphWithSize = new IncidenceMatrixGraph(5, 0);
        Assertions.assertEquals(5, graphWithSize.getVertexCount());

        // Тест после добавления вершин
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(0, 0);
        graph.addVertex(0);
        Assertions.assertEquals(1, graph.getVertexCount());

        graph.addVertex(1);
        Assertions.assertEquals(2, graph.getVertexCount());

        graph.addVertex(5); // Добавляем вершину с большим индексом
        Assertions.assertEquals(6, graph.getVertexCount()); // Должно быть 6 вершин (0,1,2,3,4,5)

        // Тест после удаления вершины
        graph.removeVertex(2);
        Assertions.assertEquals(5, graph.getVertexCount());
    }

    @Test
    void testEnsureEdgeCapacity() {
        IncidenceMatrixGraph graph = new IncidenceMatrixGraph(3, 0);

        graph.addEdge(0, 1);

        List<Integer> neighbors0 = graph.getNeighbors(0);
        Assertions.assertTrue(neighbors0.contains(1));

        graph.addEdge(1, 2);
        graph.addEdge(0, 2);
        graph.addEdge(2, 0);

        Assertions.assertTrue(graph.getNeighbors(0).contains(1));
        Assertions.assertTrue(graph.getNeighbors(0).contains(2));
        Assertions.assertTrue(graph.getNeighbors(1).contains(2));
        Assertions.assertTrue(graph.getNeighbors(2).contains(0));
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