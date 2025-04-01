package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.ids.NodeID;

import java.util.List;

/// Graph with the following restrictions:
/// - directed
/// - max degree = 2
public interface PathGraph<N> extends Graph<N> {
    NodeID getRoot();
    NodeID getEnd();
    List<NodeID> asList();
}
