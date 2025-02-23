package io.github.thdudk.builders;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph;

import java.util.Set;

public interface WeightedGraphBuilder<N, E> extends WeightedRestrictedGraph<N, E> {
    WeightedGraph<N, E> build();

    WeightedGraphBuilder<N, E> addNode(N node);
    WeightedGraphBuilder<N, E> removeNode(N node);
    Set<N> getNodes();

    WeightedGraphBuilder<N, E> addDirEdge(N start, E edge, N end);
    default WeightedGraphBuilder<N, E> addUndirEdge(N node1, E edge, N node2) {
        addDirEdge(node1, edge, node2);
        addDirEdge(node2, edge, node1);
        return this;
    };
}
