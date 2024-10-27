package io.github.thdudk.construction.builders;

import io.github.thdudk.construction.restrictions.WeightedGraphRestriction;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;

import java.util.*;

@RequiredArgsConstructor
public final class WeightedGraphBuilderImpl<N, E> implements WeightedGraphBuilder<N, E> {
    private final Map<N, Set<EdgeEndpointPair<N, E>>> adjacencyList = new HashMap<>();
    private final Set<WeightedGraphRestriction<N, E>> restrictions;

    public WeightedGraphBuilderImpl() {
        this.restrictions = Collections.emptySet();
    }

    @Override
    public WeightedGraphBuilder<N, E> addNode(N node) {
        for(val restriction : restrictions) restriction.onNodeAdded(node);

        if(!adjacencyList.containsKey(node)) adjacencyList.put(node, new HashSet<>());
        return this;
    }

    @Override
    public WeightedGraphBuilder<N, E> addEdge(N start, E edge, N end) {
        addNode(start).addNode(end);
        for(val restriction : restrictions) restriction.onEdgeAdded(start, edge, end);
        adjacencyList.get(start).add(new EdgeEndpointPair<>(edge, end));
        return this;
    }

    @Override
    public WeightedGraph<N, E> build() {
        for(val restriction : restrictions) restriction.onBuild();

        return new AdjacencyListWeightedGraphImpl<>(adjacencyList);
    }
}
