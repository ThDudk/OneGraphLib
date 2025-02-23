package io.github.thdudk.builders;

import io.github.thdudk.RestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;

import java.util.Set;

public interface GraphBuilder<N> extends RestrictedGraph<N> {
    Graph<N> build();

    GraphBuilder<N> addNode(N node);
    GraphBuilder<N> removeNode(N node);
    Set<N> getNodes();

    GraphBuilder<N> addDirEdge(N root, N neighbor);
    default GraphBuilder<N> addUndirEdge(N node1, N node2) {
        addDirEdge(node1, node2);
        addDirEdge(node2, node1);
        return this;
    }
}
