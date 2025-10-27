import graph.AdjacencyListGraph;
import graph.Graph;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new AdjacencyListGraph();

        graph.addVertex(0);
        graph.addVertex(1);
        graph.addVertex(2);

        graph.addEdge(0, 1);
        graph.addEdge(1, 2);

        System.out.println("Граф:");
        System.out.println(graph);

        List<Integer> sorted = graph.topologicalSort();
        System.out.println("Топологическая сортировка: " + sorted);
    }
}
