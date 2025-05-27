package io.github.thdudk.graphs;

import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@ToString
public abstract class AbstractStructurelessGraph<N> extends AbstractRestrictedGraph<N> implements Graph<N> {
    private final TwoWayDataContainer<NodeID, N> nodeData;

    public AbstractStructurelessGraph(Map<NodeID, N> data, Collection<GraphRestriction<N>> restrictions) {
        super(restrictions);
        this.nodeData = new TwoWayDataContainer<>(data);
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
    public Graph<N> addRestriction(GraphRestriction<N> restriction) {
        if(!restriction.isSatisfied(this)) throw new RuntimeException("Restriction not satisfied");

        super.addRestriction(restriction);
        return this;
    }
}
