package io.github.thdudk.graphs;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;
import java.util.List;

// TODO replace with annotation
/// Simple class that throws detailed exceptions when certain conditions are not being held
public class GraphValidator {
    /// Determines which (if any) of the given nodes are not contained in this and throws an exception listing the nodes not contained.
    /// @param nodes nodes to check
    /// @throws IllegalArgumentException If any of the provided nodes are not contained in this
    public static void requireContained(Collection<NodeID> nodes, Graph<?> graph) {
        List<NodeID> notContained = nodes.stream().filter(a -> a == null || !graph.getNodes().contains(a)).toList();

        if(notContained.isEmpty()) return;
        throw new IllegalArgumentException("Given nodes must be within the graph. Illegal nodes: " + notContained);
    }

    public static <N> void requireContainedData(Collection<N> nodes, Graph<?> graph) {
        List<N> notContained = nodes.stream().filter(a -> a == null || !graph.getNodeDataMap().containsValue(a)).toList();

        if(notContained.isEmpty()) return;
        throw new IllegalArgumentException("Given nodes must be within the graph. Illegal nodes: " + notContained);
    }
}