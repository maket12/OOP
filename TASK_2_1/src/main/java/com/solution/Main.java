package com.solution;

import com.solution.graph.*;
import com.solution.graph.parser.GraphParser;
import com.solution.graph.sorter.TopologicalSorter;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("=== Graph Topological Sort ===");
            System.out.println("Choose graph implementation:");
            System.out.println("1 - Adjacency List");
            System.out.println("2 - Adjacency Matrix");
            System.out.println("3 - Incidence Matrix");
            System.out.print("Your choice (1-3): ");

            int implChoice = scanner.nextInt();
            scanner.nextLine();

            Graph graph;
            switch (implChoice) {
                case 1:
                    graph = new AdjacencyMapGraph();
                    System.out.println("Using Adjacency Map (List) implementation");
                    break;
                case 2:
                    graph = new AdjacencyMatrixGraph();
                    System.out.println("Using Adjacency Matrix implementation");
                    break;
                case 3:
                    graph = new IncidenceMatrixGraph(0, 0);
                    System.out.println("Using Incidence Matrix implementation");
                    break;
                default:
                    System.out.println("Invalid choice. Using Adjacency Matrix by default.");
                    graph = new AdjacencyMatrixGraph();
            }

            System.out.println("Choose input method:");
            System.out.println("1 - Read from file");
            System.out.println("2 - Enter graph manually");
            System.out.print("Your choice (1 or 2): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                // чтение из файла
                System.out.print("Enter filename: ");
                String filename = scanner.nextLine().trim();

                GraphParser parser = new GraphParser();
                parser.parse(graph, filename);

                System.out.println("\nGraph loaded successfully from file: " + filename);
            } else if (choice == 2) {
                // Ручной ввод
                graph = enterGraphManually(scanner);
            } else {
                System.out.println("Invalid choice. Exiting.");
                return;
            }

            System.out.println("\nGraph structure:");
            System.out.println(graph);

            System.out.println("\nPerforming topological sort...");
            TopologicalSorter sorter = new TopologicalSorter();
            TopologicalSorter.Result res = sorter.sort(graph);

            System.out.println("Topological sort result: " + res.order());

            if (res.acyclic()) {
                System.out.println("✓ The graph is acyclic (DAG)");
            } else {
                System.out.println("⚠ Warning: The graph contains cycles!");
                System.out.println("Returned order is a DFS postorder (not a valid topo-order for cyclic graphs).");
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // === Manual Input Section ===

    private static Graph enterGraphManually(Scanner scanner) {
        Graph graph = new IncidenceMatrixGraph(0, 0);

        System.out.println("\n=== Manual Graph Input ===");
        System.out.println("Enter edges in format: 'from to'");
        System.out.println("Example: '0 1' for edge from vertex 0 to vertex 1");
        System.out.println("Enter 'done' to finish input");
        System.out.println("Enter 'add' to add a single edge");

        while (true) {
            System.out.print("\nEnter command (edge/add/done): ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                break;
            } else if (input.equalsIgnoreCase("add")) {
                addSingleEdge(scanner, graph);
            } else if (input.matches("\\d+\\s+\\d+")) {
                String[] parts = input.split("\\s+");
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                graph.addEdge(from, to);
                System.out.println("Added edge: " + from + " -> " + to);
            } else {
                System.out.println("Invalid input. Use format: 'from to' or commands: add/done");
            }
        }

        return graph;
    }

    private static void addSingleEdge(Scanner scanner, Graph graph) {
        try {
            System.out.print("Enter source vertex: ");
            int from = scanner.nextInt();
            System.out.print("Enter target vertex: ");
            int to = scanner.nextInt();
            scanner.nextLine();

            graph.addEdge(from, to);
            System.out.println("Added edge: " + from + " -> " + to);

        } catch (Exception e) {
            System.out.println("Invalid vertex numbers. Please enter integers.");
            scanner.nextLine();
        }
    }
}
