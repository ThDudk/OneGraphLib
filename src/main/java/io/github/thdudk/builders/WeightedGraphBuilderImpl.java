package io.github.thdudk.builders;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;
import lombok.val;

import java.util.*;

public final class WeightedGraphBuilderImpl<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements WeightedGraphBuilder<N, E> {
    private final Map<N, Set<EdgeEndpointPair<N, E>>> adjacencyList = new HashMap<>();

    public WeightedGraphBuilderImpl() {
        super();
    }
    public WeightedGraphBuilderImpl(WeightedGraph<N, E> graph) {
        this();

        for(N node : graph.getNodes()) {
            addNode(node);
            for (N neighbour : graph.getNeighbours(node)) {
                addDirEdge(node, graph.getEdgeBetween(node, neighbour).orElseThrow(), neighbour);
            }
        }
    }

    @Override
    public WeightedGraphBuilder<N, E> addNode(N node) {
        if(!adjacencyList.containsKey(node)) adjacencyList.put(node, new HashSet<>());
        return this;
    }
    @Override
    public WeightedGraphBuilder<N, E> removeNode(N node) {
        adjacencyList.remove(node);
        for(Set<EdgeEndpointPair<N, E>> edges : adjacencyList.values()) {
            edges.removeIf(a -> a.getEndpoint().equals(node));
        }
        return this;
    }

    @Override
    public Set<N> getNodes() {
        return Collections.unmodifiableSet(adjacencyList.keySet());
    }

    @Override
    public WeightedGraphBuilder<N, E> addDirEdge(N start, E edge, N end) {
        addNode(start).addNode(end);
        adjacencyList.get(start).add(new EdgeEndpointPair<>(edge, end));
        return this;
    }

    @Override
    public WeightedGraph<N, E> build() {
        WeightedGraph<N, E> graph = new AdjacencyListWeightedGraphImpl<>(adjacencyList);

        // throw an exception if the restrictions are not met
        for(val restriction : getRestrictions())
            if(!restriction.isSatisfied(graph))
                throw new RuntimeException("Failed to satisfy restriction: " + restriction);

        return graph;
    }
}
