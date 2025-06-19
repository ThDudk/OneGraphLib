package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.graphs.AbstractStructurelessGraph;
import io.github.thdudk.graphs.GraphValidator;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.GeneratedEdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdjacencyListGraphImpl<N> extends AbstractStructurelessGraph<N> implements Graph<N> {
    private final Map<NodeID, Set<Graph.EdgeEndpointPair>> adjacencyList;

    public AdjacencyListGraphImpl(
        Collection<GraphRestriction<N>> restrictions,
        Map<NodeID, Collection<Graph.EdgeEndpointPair>> adjacencyList,
        Map<NodeID, N> nodeData
    ) {
        super(nodeData, restrictions);

        Map<NodeID, Set<Graph.EdgeEndpointPair>> adjList = new HashMap<>();
        for(Map.Entry<NodeID, Collection<Graph.EdgeEndpointPair>> entry : adjacencyList.entrySet()) {
            adjList.put(entry.getKey(), Set.copyOf(entry.getValue()));
        }

        this.adjacencyList = adjList;
    }

    @Override
    public boolean hasEdge(EdgeID id) {
        if(!(id instanceof GeneratedEdgeID genId)) {
            return adjacencyList.values().stream()
                .flatMap(Collection::stream)
                .anyMatch(pair -> pair.getEdge().equals(id));
        };

        return getEdgesBetween(genId.start(), genId.end()).contains(genId);
    }

    @Override
    public Collection<NodeID> getNodes() {
        return Set.copyOf(adjacencyList.keySet());
    }
    @Override
    public Collection<NodeID> getNeighbours(NodeID node) {
        GraphValidator.requireNodesContained(List.of(node), this);

        return Set.copyOf(
            adjacencyList.get(node).stream()
                .map(EdgeEndpointPair::getEndpoint)
                .collect(Collectors.toSet())
        );
    }
    @Override
    public Collection<EdgeID> getEdgesBetween(NodeID start, NodeID end) {
        GraphValidator.requireNodesContained(List.of(start, end), this);

        return adjacencyList.get(start).stream()
            .filter(pair -> pair.getEndpoint().equals(end))
            .map(EdgeEndpointPair::getEdge)
            .collect(Collectors.toList());
    }
}