package io.github.thdudk.graphs.weighted;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.graphs.AbstractStructurelessWeightedGraph;
import io.github.thdudk.graphs.TwoWayDataContainer;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdjacencyListWeightedGraphImpl<N, E> extends AbstractStructurelessWeightedGraph<N, E> implements WeightedGraph<N, E> {
    private final Map<NodeID, Collection<EdgeEndpointPair>> adjacencyList;

    public AdjacencyListWeightedGraphImpl(Collection<GraphRestriction<N>> restrictions, Collection<GraphEdgeRestriction<N, E>> edgeRestrictions, Map<NodeID, Collection<EdgeEndpointPair>> adjacencyList, Map<NodeID, N> nodeData, Map<EdgeID, E> edgeData) {
        super(nodeData, edgeData, restrictions, edgeRestrictions);
        this.adjacencyList = adjacencyList;

        throwIfRestrictionsNotSatisfied();
        throwIfEdgeRestrictionsNotSatisfied(this);
    }

    @Override
    public Collection<NodeID> getNodes() {
        return Collections.unmodifiableCollection(adjacencyList.keySet());
    }
    @Override
    public Collection<NodeID> getNeighbours(NodeID node) {
        return adjacencyList.get(node).stream().map(EdgeEndpointPair::getEndpoint).toList();
    }

    @Override
    public Collection<EdgeID> getEdgesBetween(NodeID start, NodeID end) {
        return adjacencyList.get(start).stream()
            .filter(a -> a.getEndpoint().equals(end))
            .map(EdgeEndpointPair::getEdge)
            .collect(Collectors.toUnmodifiableSet());
    }
}
