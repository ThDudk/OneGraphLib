package io.github.thdudk.builders.paths;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

public interface PathBuilder<N> extends ExplicitIdsGraphBuilder<N> {
    void addStartNode(NodeID node, N data);
    void nextNode(NodeID node, N data, EdgeID edgeID);

    @Override
    default void addNode(NodeID id, N data) {
        addStartNode(id, data);
    }
    @Override
    default void addDirEdge(NodeID root, NodeID neighbour, EdgeID edgeID) {
        throw new UnsupportedOperationException();
    }

    PathGraph<N> build();
}
