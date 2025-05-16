package io.github.thdudk.builders.unweighted;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.IntegerNodeID;
import io.github.thdudk.ids.LongEdgeID;
import io.github.thdudk.ids.NodeID;

public class GraphBuilderImpl<N> extends AbstractRestrictedGraph<N> implements GraphBuilder<N> {
    private IntegerNodeID prevNode = new IntegerNodeID(0);
    private LongEdgeID prevEdge = new LongEdgeID(0);
    private final ExplicitIdsGraphBuilder<N> builder;

    public GraphBuilderImpl() {
        builder = new ExplicitIdsGraphBuilderImpl<>();
    }

    public GraphBuilderImpl(Graph<N> graph) {
        builder = new ExplicitIdsGraphBuilderImpl<>(graph);
    }

    @Override
    public NodeID addNode(N data) {
        NodeID id = nextNodeID();
        builder.addNode(id, data);
        return id;
    }

    @Override
    public EdgeID addDirEdge(NodeID root, NodeID neighbour) {
        EdgeID id = nextEdgeID();
        builder.addDirEdge(root, neighbour, id);
        return id;
    }

    @Override
    public Graph<N> build() {
        builder.addAllRestrictions(getRestrictions());
        return builder.build();
    }

    /// Returns the next available nodeID.
    private NodeID nextNodeID() {
        prevNode = prevNode.incremented();
        return prevNode;
    }
    /// Returns the next available edgeID.
    private EdgeID nextEdgeID() {
        prevEdge = prevEdge.incremented();
        return prevEdge;
    }
}
