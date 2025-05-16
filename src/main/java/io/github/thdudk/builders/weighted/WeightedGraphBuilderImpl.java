package io.github.thdudk.builders.weighted;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.IntegerNodeID;
import io.github.thdudk.ids.LongEdgeID;
import io.github.thdudk.ids.NodeID;

public class WeightedGraphBuilderImpl<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements WeightedGraphBuilder<N, E> {
    private IntegerNodeID prevNode = new IntegerNodeID(0);
    private LongEdgeID prevEdge = new LongEdgeID(0);
    private final ExplicitIdsWeightedGraphBuilder<N, E> builder;

    public WeightedGraphBuilderImpl() {
        builder = new ExplicitIdsWeightedGraphBuilderImpl<>();
    }

    public WeightedGraphBuilderImpl(WeightedGraph<N, E> graph) {
        builder = new ExplicitIdsWeightedGraphBuilderImpl<>(graph);
    }

    @Override
    public NodeID addNode(N data) {
        NodeID id = nextNodeID();
        builder.addNode(id, data);
        return id;
    }

    @Override
    public EdgeID addDirEdge(NodeID start, NodeID end, E edgeData) {
        EdgeID id = nextEdgeID();
        builder.addDirEdge(start, end, id, edgeData);
        return id;
    }

    @Override
    public WeightedGraph<N, E> build() {
        builder.addAllRestrictions(getRestrictions());
        return builder.build();
    }

    private IntegerNodeID nextNodeID() {
        prevNode = prevNode.incremented();;
        return prevNode;
    }
    private LongEdgeID nextEdgeID() {
        prevEdge = prevEdge.incremented();;
        return prevEdge;
    }
}
