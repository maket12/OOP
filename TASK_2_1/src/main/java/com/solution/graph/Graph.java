package com.solution.graph;

import java.util.List;

/**
 * Represents a generic directed graph interface.
 * <p>
 * Provides basic operations for manipulating vertices and edges,
 * as well as utility methods for inspecting graph structure.
 * </p>
 *
 * <p>Implementations may represent graphs using adjacency lists,
 * adjacency matrices, incidence matrices, or any other internal structure.</p>
 */
public interface Graph {

    /**
     * Adds a vertex to the graph.
     * <p>
     * If the vertex already exists, the graph remains unchanged.
     * </p>
     *
     * @param vertex the vertex index to add (must be non-negative)
     */
    void addVertex(int vertex);

    /**
     * Removes a vertex and all edges connected to it.
     * <p>
     * If the vertex does not exist, this method has no effect.
     * </p>
     *
     * @param vertex the vertex to remove
     */
    void removeVertex(int vertex);

    /**
     * Adds a directed edge from one vertex to another.
     * <p>
     * Implementations should automatically create missing vertices if needed.
     * </p>
     *
     * @param vertex1 source vertex (edge starts here)
     * @param vertex2 target vertex (edge ends here)
     */
    void addEdge(int vertex1, int vertex2);

    /**
     * Removes a directed edge from one vertex to another.
     * <p>
     * If the edge does not exist, the graph remains unchanged.
     * </p>
     *
     * @param vertex1 source vertex
     * @param vertex2 target vertex
     */
    void removeEdge(int vertex1, int vertex2);

    /**
     * Returns a list of vertices that are directly reachable
     * from the specified vertex (i.e., outgoing neighbors).
     *
     * @param vertex vertex whose outgoing edges should be returned
     * @return list of neighbor vertices; an empty list if none exist
     */
    List<Integer> getNeighbors(int vertex);

    /**
     * Compares this graph to another object for equality.
     * Implementations should define equality based on
     * structural equivalence (same vertices and edges).
     *
     * @param obj the object to compare with
     * @return {@code true} if the graphs are structurally equal; {@code false} otherwise
     */
    boolean equals(Object obj);

    /**
     * Returns a human-readable string representation of the graph.
     *
     * @return formatted description of vertices and edges
     */
    String toString();

    /**
     * Returns the total number of vertices currently in the graph.
     *
     * @return number of vertices
     */
    int getVertexCount();
}
