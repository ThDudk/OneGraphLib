package io.github.thdudk.builders;

import io.github.thdudk.RestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

public interface GraphBuilder<N> extends RestrictedGraph<N> {
    Graph<N> build();

    NodeID addNode(N node);

    /// Adds a directed (one way) edge from `root` to `neighbour`
    void addDirEdge(NodeID root, NodeID neighbor);
    /// Adds an undirected edge (two way) edge between `node1` and `node2`
    default void addUndirEdge(NodeID node1, NodeID node2) {
        addDirEdge(node1, node2);
        addDirEdge(node2, node1);
    }

    /// Constructs a builder from `graph`
    static <N> GraphBuilder<N> of(Graph<N> graph) {
        return new GraphBuilderImpl<>(graph);
    }

}
