package io.github.thdudk.builders.trees;

import io.github.thdudk.graphs.unweighted.TreeGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.restriction_containers.MutableRestrictionContainer;

public interface TreeBuilder<N> extends MutableRestrictionContainer<N> {
    /// Adds the node while preserving its NodeID.
    ///
    /// @throws IllegalArgumentException if id is already present
    void addRoot(NodeID id, N data);

    /// Adds child as a child to parent
    ///
    /// @throws IllegalArgumentException if `child` is already present`
    void addChild(NodeID parent, NodeID child, N childData, EdgeID edgeID);

    TreeGraph<N> build();
}
