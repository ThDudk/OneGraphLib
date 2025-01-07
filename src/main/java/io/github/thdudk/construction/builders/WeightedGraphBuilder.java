package io.github.thdudk.construction.builders;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.weighted.WeightedGraph;

public interface WeightedGraphBuilder<N, E> extends GraphBuilder<N> {
    default WeightedGraphBuilder<N, E> addOutNeighbor(N root, N neighbor) {
        throw new UnsupportedOperationException("Cannot add neighbors in a weighted graph as edge data is required. Use addEdge() instead.");
    }

    WeightedGraphBuilder<N, E> addNode(N node);
    WeightedGraphBuilder<N, E> addEdge(N start, E edge, N end);
    default WeightedGraphBuilder<N, E> addUndirEdge(N node1, E edge, N node2) {
        addEdge(node1, edge, node2);
        addEdge(node2, edge, node1);
        return this;
    };
    WeightedGraph<N, E> build();

    static <N, E> WeightedGraphBuilder<N, E> of() {
        return new WeightedGraphBuilderImpl<>();
    }
    static <N, E> WeightedGraphBuilder<N, E> of(WeightedGraph<N, E> graph) {
        return new WeightedGraphBuilderImpl<>(graph);
    }
}
