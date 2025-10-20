package com.solution.graph.sorter;

import com.solution.graph.Graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TopologicalSorter {
    public static final class Result {
        private final List<Integer> order;
        private final boolean acyclic;

        public Result(List<Integer> order, boolean acyclic) {
            this.order = order;
            this.acyclic = acyclic;
        }

        public List<Integer> order()   { return order; }
        public boolean      acyclic()  { return acyclic; }
    }

    public Result sort(Graph graph) {
        int n = graph.getVertexCount();
        // 0 = WHITE (не посещён), 1 = GRAY (в стеке рекурсии), 2 = BLACK (завершён)
        int[] color = new int[n];
        Deque<Integer> out = new ArrayDeque<>();
        boolean[] hasCycle = new boolean[1]; // мутируемый флаг

        for (int v = 0; v < n; v++) {
            if (color[v] == 0) {
                dfs(graph, v, color, out, hasCycle);
            }
        }

        return new Result(new ArrayList<>(out), !hasCycle[0]);
    }

    private void dfs(Graph graph, int v, int[] color, Deque<Integer> out, boolean[] hasCycle) {
        color[v] = 1;
        for (int u : graph.getNeighbors(v)) {
            if (color[u] == 0) {
                dfs(graph, u, color, out, hasCycle);
            } else if (color[u] == 1) {
                hasCycle[0] = true;
            }
        }
        color[v] = 2;
        out.push(v);
    }
}
