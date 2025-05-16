package io.github.thdudk.builders.weighted;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

public interface ExplicitIdsWeightedGraphBuilder<N, E> extends WeightedRestrictedGraph<N, E> {
    WeightedGraph<N, E> build();

    void addNode(NodeID id, N data);

    void addDirEdge(NodeID start, NodeID end, EdgeID edgeID, E edgeData);
    default void addUndirEdge(NodeID node1, NodeID node2, EdgeID edgeID, E edgeData) {
        addDirEdge(node1, node2, edgeID, edgeData);
        addDirEdge(node2, node1, edgeID, edgeData);
    };

    /// constructs a builder from graph
    /// @return the created builder
    static <N, E> WeightedGraphBuilder<N, E> of(WeightedGraph<N, E> graph) {
        return new WeightedGraphBuilderImpl<>(graph);
    }
}
