package com.solution.graph.sorter;

import com.solution.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Performs topological sorting of a directed graph using
 * a depth-first search (DFS) approach.
 * <p>
 * The algorithm uses color marking to detect cycles:
 * <ul>
 *     <li><b>WHITE (0)</b> — vertex not yet visited</li>
 *     <li><b>GRAY (1)</b> — vertex is currently in recursion stack</li>
 *     <li><b>BLACK (2)</b> — vertex and all its descendants processed</li>
 * </ul>
 * If a back edge to a GRAY vertex is found, a cycle exists in the graph.
 * </p>
 * <p>
 * The result of sorting includes the list of vertices in topological order
 * and a flag indicating whether the graph is acyclic.
 * </p>
 */
public class TopologicalSorter {

    /**
     * Represents the result of topological sorting.
     * <p>
     * Contains both the computed vertex order and a flag
     * showing whether the input graph is acyclic.
     * </p>
     */
    public static final class Result {
        private final List<Integer> order;
        private final boolean acyclic;

        /**
         * Constructs a new result object.
         *
         * @param order   list of vertices in topological order (post-order DFS)
         * @param acyclic {@code true} if the graph has no cycles, {@code false} otherwise
         */
        public Result(List<Integer> order, boolean acyclic) {
            this.order = order;
            this.acyclic = acyclic;
        }

        /**
         * Returns the vertices in computed topological order.
         *
         * @return ordered list of vertices
         */
        public List<Integer> order() {
            return order;
        }

        /**
         * Indicates whether the graph is acyclic.
         *
         * @return {@code true} if the graph is acyclic (DAG); {@code false} if cycles were found
         */
        public boolean acyclic() {
            return acyclic;
        }
    }

    /**
     * Performs topological sort on the given graph.
     * <p>
     * The algorithm runs in O(V + E) time using DFS traversal.
     * It also detects cycles by tracking recursion stack (GRAY vertices).
     * </p>
     *
     * @param graph the directed graph to sort
     * @return {@link Result} containing the vertex order and acyclicity flag
     */
    public Result sort(Graph graph) {
        int n = graph.getVertexCount();
        // 0 = WHITE (unvisited), 1 = GRAY (in recursion), 2 = BLACK (finished)
        int[] color = new int[n];
        Deque<Integer> out = new ArrayDeque<>();
        boolean[] hasCycle = new boolean[1]; // mutable flag for recursion

        for (int v = 0; v < n; v++) {
            if (color[v] == 0) {
                dfs(graph, v, color, out, hasCycle);
            }
        }

        return new Result(new ArrayList<>(out), !hasCycle[0]);
    }

    /**
     * Recursive depth-first traversal used for topological sorting.
     * <p>
     * Marks vertices with color states (WHITE, GRAY, BLACK) and
     * adds them to the output stack when fully processed.
     * </p>
     *
     * @param graph    the graph being traversed
     * @param v        current vertex
     * @param color    array representing vertex visit states
     * @param out      output stack storing the order of processed vertices
     * @param hasCycle single-element array used to flag cycle detection
     */
    private void dfs(Graph graph, int v, int[] color, Deque<Integer> out, boolean[] hasCycle) {
        color[v] = 1; // GRAY: vertex in recursion
        for (int u : graph.getNeighbors(v)) {
            if (color[u] == 0) {
                dfs(graph, u, color, out, hasCycle);
            } else if (color[u] == 1) {
                hasCycle[0] = true; // back edge → cycle
            }
        }
        color[v] = 2; // BLACK: fully processed
        out.push(v);
    }
}
