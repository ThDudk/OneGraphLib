package io.github.thdudk.builders;

public interface DistinctDataGraphBuilder<N> extends GraphBuilder<N> {
    void addDirEdge(N start, N end);
    default void addUndirEdge(N node1, N node2) {
        addDirEdge(node1, node2);
        addDirEdge(node2, node1);
    }
}
