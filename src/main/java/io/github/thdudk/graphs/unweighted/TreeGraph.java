package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;

/// Subtype of Graph representing a tree structure.
///
/// Should adhere to the following restrictions:
/// - directed
/// - max in degree = 1
/// - no multi-edges
/// - weakly connected
@JsonDeserialize(as = TwoWayTreeGraphImpl.class)
public interface TreeGraph<N> extends Graph<N> {
    NodeID getRoot();
    NodeID getParent(NodeID node);
    Collection<NodeID> getChildren(NodeID node);
    int getDepth(NodeID node);
}
