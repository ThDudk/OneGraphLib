package io.github.thdudk.builders.unweighted;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.restriction_containers.MutableRestrictionContainer;
import lombok.NonNull;

/// Graph builder that involves manually managing your own NodeIDs.
///
/// This is useful in constructing graphs related to other graphs (such as paths or trees), in which sharing NodeIDs is important.
///
/// It's also useful when custom NodeIDs are being used, such as coordinate node IDs.
public interface ExplicitIdsGraphBuilder<N> extends MutableRestrictionContainer<N> {
    /// Adds the node while preserving its NodeID.
    ///
    /// @throws IllegalArgumentException if id is already present or null
    void addNode(NodeID id, N data);

    /// Adds a directed (one way) edge from `root` to `neighbour`
    ///
    /// @throws IllegalArgumentException if `root` or `neighbour` don't exist.
    /// @throws IllegalArgumentException if edgeID is already present in this
    void addDirEdge(NodeID root, NodeID neighbor, EdgeID edgeID);
    /// Adds an undirected-edge (two-way) between `node1` and `node2`
    ///
    /// Because graphs cannot represent undirected edges, oneToTwo and twoToOne must be distinct to avoid ID conflicts.
    ///
    /// @throws IllegalArgumentException if `node1` or `node2` don't exist
    /// @throws IllegalArgumentException if oneToTwo and twoToOne are not distinct
    default void addUndirEdge(NodeID node1, NodeID node2, EdgeID oneToTwo, EdgeID twoToOne) {
        // check for if oneToTwo and twoToOne are equal should be covered by addDirEdge checking for existing edges.
        addDirEdge(node1, node2, oneToTwo);
        addDirEdge(node2, node1, twoToOne);
    }

    static <N> ExplicitIdsGraphBuilder<N> copyOf(Graph<N> graph) {
        return new ExplicitIdsGraphBuilderImpl<>(graph);
    }

    Graph<N> build();
}
