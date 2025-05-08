package io.github.thdudk.builders.unweighted;

import io.github.thdudk.RestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

import java.util.Optional;

public interface GraphBuilder<N> extends RestrictedGraph<N> {
    Graph<N> build();

    NodeID addNode(N node);
    /// Adds the node while preserving its NodeID.
    /// If a node already exists in the builder with the given ID, the existing node data is replaced with the new data
    ///
    /// It's recommended you either use {@link #addNode(Object) addNode(data)} or this function, not both as that can cause ID collisions.
    /// @return the node data replaced or an empty optional
    Optional<N> addNode(NodeID id, N data);

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
