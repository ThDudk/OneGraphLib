package io.github.thdudk.construction.builders;

import io.github.thdudk.construction.restrictions.GraphRestriction;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;

@RequiredArgsConstructor
public final class WeightedGraphBuilderImpl<N, E> implements WeightedGraphBuilder<N, E> {
    private final Map<N, Set<EdgeEndpointPair<N, E>>> adjacencyList = new HashMap<>();
    private final Set<GraphRestriction<N>> restrictions;

    public WeightedGraphBuilderImpl() {
        this.restrictions = Collections.emptySet();
    }
    public WeightedGraphBuilderImpl(WeightedGraph<N, E> graph) {
        this();

        for(N node : graph.getNodes()) {
            addNode(node);
            for (EdgeEndpointPair<N, E> edge : graph.getEdges(node)) {
                addEdge(node, edge.getEdge(), edge.getEndpoint());
            }
        }
    }

    @Override
    public WeightedGraphBuilder<N, E> addNode(N node) {
        if(!adjacencyList.containsKey(node)) adjacencyList.put(node, new HashSet<>());
        return this;
    }
    @Override
    public GraphBuilder<N> removeNode(N node) {
        adjacencyList.remove(node);
        for(Set<EdgeEndpointPair<N, E>> edges : adjacencyList.values()) {
            edges.removeIf(a -> a.getEndpoint().equals(node));
        }
        return this;
    }

    @Override
    public WeightedGraphBuilder<N, E> addEdge(N start, E edge, N end) {
        addNode(start).addNode(end);
        adjacencyList.get(start).add(new EdgeEndpointPair<>(edge, end));
        return this;
    }

    @Override
    public WeightedGraph<N, E> build() {
        WeightedGraph<N, E> graph = new AdjacencyListWeightedGraphImpl<>(adjacencyList);

        // throw an exception if the restrictions are not met
        for(val restriction : restrictions)
            restriction.onBuild(graph);

        return graph;
    }
}
