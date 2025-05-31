package io.github.thdudk.graphs;

import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import io.github.thdudk.restrictions.restriction_containers.AbstractWeightedRestrictionContainer;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractStructurelessWeightedGraph<N, E> extends AbstractWeightedRestrictionContainer<N, E> implements WeightedGraph<N, E> {
    private final TwoWayDataContainer<NodeID, N> nodeData;
    private final TwoWayDataContainer<EdgeID, E> edgeData;

    public AbstractStructurelessWeightedGraph(Map<NodeID, N> nodeData, Map<EdgeID, E> edgeData, Collection<GraphRestriction<N>> restrictions, Collection<GraphEdgeRestriction<N, E>> edgeRestrictions) {
        super(restrictions, edgeRestrictions);
        this.nodeData = new TwoWayDataContainer<>(nodeData);
        this.edgeData = new TwoWayDataContainer<>(edgeData);
    }

    @Override
    public N getNodeData(NodeID id) {
        return nodeData.getData(id);
    }
    @Override
    public Collection<NodeID> nodeIdsWithData(N data) {
        return nodeData.getIDs(data);
    }

    @Override
    public E getEdgeData(EdgeID id) {
        return edgeData.getData(id);
    }
    @Override
    public Collection<EdgeID> edgeIdsWithData(E data) {
        return edgeData.getIDs(data);
    }
}
