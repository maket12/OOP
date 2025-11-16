package ru.nsu.ziabkin.graph;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class IncidenceMatrixGraph implements Graph {
    private int[][] incidenceMatrix;
    private int vertexCount;
    private int edgeCount;

    public IncidenceMatrixGraph(int vertices, int edges) {
        int colCapacity = Math.max(1, edges);
        this.incidenceMatrix = new int[Math.max(0, vertices)][colCapacity];
        this.vertexCount = Math.max(0, vertices);
        this.edgeCount = 0;
    }

    @Override
    public void addVertex(int vertex) {
        if (vertex < 0) {
            return;
        }
        if (vertex < vertexCount) {
            return;
        }

        int newVertexCount = vertex + 1;
        int colCapacity = incidenceMatrix.length > 0 ? incidenceMatrix[0].length : Math.max(1, edgeCount);

        int[][] newMatrix = new int[newVertexCount][colCapacity];
        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
        }

        for (int i = vertexCount; i < newVertexCount; i++) {
            for (int j = 0; j < edgeCount; j++) {
                newMatrix[i][j] = 0;
            }
        }

        incidenceMatrix = newMatrix;
        vertexCount = newVertexCount;
    }

    @Override
    public void removeVertex(int vertex) {
        if (vertex < 0 || vertex >= vertexCount) return;

        for (int j = edgeCount - 1; j >= 0; j--) {
            if (incidenceMatrix[vertex][j] != 0) {
                for (int i = 0; i < vertexCount; i++) {
                    for (int k = j; k < edgeCount - 1; k++) {
                        incidenceMatrix[i][k] = incidenceMatrix[i][k + 1];
                    }
                    incidenceMatrix[i][edgeCount - 1] = 0;
                }
                edgeCount--;
            }
        }

        int[][] newMatrix = new int[vertexCount - 1][incidenceMatrix[0].length];
        int dst = 0;
        for (int i = 0; i < vertexCount; i++) {
            if (i == vertex) continue;
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[dst++], 0, edgeCount);
        }
        incidenceMatrix = newMatrix;
        vertexCount--;
    }

    @Override
    public void addEdge(int vertex1, int vertex2) {
        if (vertex1 < 0 || vertex2 < 0) return;
        if (vertex1 >= vertexCount) addVertex(vertex1);
        if (vertex2 >= vertexCount) addVertex(vertex2);

        ensureEdgeCapacity(edgeCount + 1);

        for (int i = 0; i < vertexCount; i++) incidenceMatrix[i][edgeCount] = 0;
        incidenceMatrix[vertex1][edgeCount] = 1;
        incidenceMatrix[vertex2][edgeCount] = -1;

        edgeCount++;
    }

    @Override
    public void removeEdge(int vertex1, int vertex2) {
        if (vertex1 < 0 || vertex2 < 0 || vertex1 >= vertexCount || vertex2 >= vertexCount) return;

        for (int j = 0; j < edgeCount; j++) {
            if (incidenceMatrix[vertex1][j] == 1 && incidenceMatrix[vertex2][j] == -1) {
                for (int i = 0; i < vertexCount; i++) {
                    for (int k = j; k < edgeCount - 1; k++) {
                        incidenceMatrix[i][k] = incidenceMatrix[i][k + 1];
                    }
                    incidenceMatrix[i][edgeCount - 1] = 0;
                }
                edgeCount--;
                return;
            }
        }
    }

    @Override
    public List<Integer> getNeighbors(int vertex) {
        List<Integer> neighbors = new ArrayList<>();
        if (vertex < 0 || vertex >= vertexCount) return Collections.emptyList();

        for (int j = 0; j < edgeCount; j++) {
            if (incidenceMatrix[vertex][j] == 1) {
                for (int i = 0; i < vertexCount; i++) {
                    if (incidenceMatrix[i][j] == -1) {
                        neighbors.add(i);
                        break;
                    }
                }
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof IncidenceMatrixGraph other)) return false;
        if (vertexCount != other.vertexCount) return false;

        for (int v = 0; v < vertexCount; v++) {
            List<Integer> thisNeighbors = getNeighbors(v);
            List<Integer> otherNeighbors = other.getNeighbors(v);

            if (thisNeighbors.size() != otherNeighbors.size()) {
                return false;
            }

            Collections.sort(thisNeighbors);
            Collections.sort(otherNeighbors);

            if (!thisNeighbors.equals(otherNeighbors)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Incidence Matrix Graph:\n");
        for (int v = 0; v < vertexCount; v++) {
            List<Integer> neighbors = getNeighbors(v);
            if (neighbors.isEmpty()) {
                sb.append(v).append(" -> null\n");
            } else {
                for (int u : neighbors) {
                    sb.append(v).append(" -> ").append(u).append("\n");
                }
            }
        }
        return sb.toString();
    }

    @Override
    public int getVertexCount() {
        return vertexCount;
    }

    private void ensureEdgeCapacity(int needed) {
        int colCapacity = (vertexCount == 0) ? 0 : incidenceMatrix[0].length;
        if (needed <= colCapacity) return;

        int newCap = Math.max(1, Math.max(needed, colCapacity * 2));
        int[][] newMatrix = new int[vertexCount][newCap];
        for (int i = 0; i < vertexCount; i++) {
            System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
        }
        incidenceMatrix = newMatrix;
    }
}
