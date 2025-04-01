package io.github.thdudk.builders;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.NodeID;

public interface WeightedGraphBuilder<N, E> extends WeightedRestrictedGraph<N, E> {
    WeightedGraph<N, E> build();

    NodeID addNode(N node);

    void addDirEdge(NodeID start, E edge, NodeID end);
    default void addUndirEdge(NodeID node1, E edge, NodeID node2) {
        addDirEdge(node1, edge, node2);
        addDirEdge(node2, edge, node1);
    };

    /// constructs a builder from graph
    /// @return the created builder
    static <N, E> WeightedGraphBuilder<N, E> of(WeightedGraph<N, E> graph) {
        return new WeightedGraphBuilderImpl<>(graph);
    }
}
