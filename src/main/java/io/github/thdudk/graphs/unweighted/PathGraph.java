package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.ids.NodeID;

import java.util.List;

/// Graph with the following restrictions:
/// - directed
/// - max degree = 1
/// - no multi-edges
@JsonDeserialize(as = PathGraphImpl.class)
public interface PathGraph<N> extends Graph<N> {
    NodeID getRoot();
    NodeID getEnd();
    List<NodeID> asList();
}
