package io.github.thdudk.graphs.weighted;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.graphs.EdgeDataContainer;
import io.github.thdudk.graphs.NodeDataContainer;
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
public class AdjacencyListWeightedGraphImpl<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements WeightedGraph<N, E> {
    private final Map<NodeID, Set<EdgeEndpointPair>> adjacencyList;
    private final NodeDataContainer<N> nodeData;
    private final EdgeDataContainer<E> edgeData;

    public AdjacencyListWeightedGraphImpl(Collection<GraphRestriction<N>> restrictions, Collection<GraphEdgeRestriction<N, E>> edgeRestrictions, Map<NodeID, Set<EdgeEndpointPair>> adjacencyList, Map<NodeID, N> nodeData, Map<EdgeID, E> edgeData) {
        super(restrictions, edgeRestrictions);
        this.adjacencyList = adjacencyList;

        this.nodeData = new NodeDataContainer<>(nodeData);
        this.edgeData = new EdgeDataContainer<>(edgeData);

        throwIfRestrictionsNotSatisfied(this);
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
