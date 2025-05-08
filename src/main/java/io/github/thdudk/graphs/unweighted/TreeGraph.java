package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.ids.NodeID;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public interface TreeGraph<N> extends Graph<N> {
    NodeID getRoot();
    NodeID getParent(NodeID node);
    Collection<NodeID> getChildren(NodeID node);
}
