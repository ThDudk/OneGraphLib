package io.github.thdudk.builders.weighted;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import org.apache.commons.lang3.tuple.Pair;

public interface WeightedGraphBuilder<N, E> extends WeightedRestrictedGraph<N, E> {
    NodeID addNode(N data);
    EdgeID addDirEdge(NodeID start, NodeID end, E edgeData);
    /// @return a pair of edge ids, where the left is the id from node1 to node2 and the right is from node2 to node1
    default Pair<EdgeID, EdgeID> addUndirEdge(NodeID node1, NodeID node2, E edgeData) {
        return Pair.of(
            addDirEdge(node1, node2, edgeData),
            addDirEdge(node2, node1, edgeData)
        );
    }

    WeightedGraph<N, E> build();
}
