package io.github.thdudk.builders.paths;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.graphs.unweighted.PathGraphImpl;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;

public class PathBuilderImpl<N> extends ExplicitIdsGraphBuilderImpl<N> implements PathBuilder<N> {
    private NodeID root;
    private NodeID prev;

    @Override
    public void addStartNode(NodeID node, N data) {
        if(root != null) throw new RuntimeException("Cannot add multiple path starts");

        root = node;
        prev = node;
        super.addNode(node, data);
    }

    @Override
    public void nextNode(NodeID nodeId, N data, EdgeID edgeId) {
        if(root == null) throw new RuntimeException("Add start node first.");

        super.addNode(nodeId, data);
        super.addDirEdge(prev, nodeId, edgeId);
        prev = nodeId;
    }

    @Override
    public PathGraph<N> build() {
        return new PathGraphImpl<>(getRestrictions(), adjacencyList, nodeData, root, prev);
    }
}
