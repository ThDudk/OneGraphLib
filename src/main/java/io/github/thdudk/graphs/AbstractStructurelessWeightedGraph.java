package io.github.thdudk.graphs;

import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractStructurelessWeightedGraph<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements WeightedGraph<N, E> {
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

    @Override
    public Graph<N> addRestriction(GraphRestriction<N> restriction) {
        if(!restriction.isSatisfied(this)) throw new RuntimeException("Restriction not satisfied: " + restriction);

        super.addRestriction(restriction);
        return this;
    }
    @Override
    public WeightedRestrictedGraph<N, E> addRestriction(GraphEdgeRestriction<N, E> restriction) {
        if(!restriction.isSatisfied(this)) throw new RuntimeException("Restriction not satisfied: " + restriction);

        super.addRestriction(restriction);
        return this;
    }
}
