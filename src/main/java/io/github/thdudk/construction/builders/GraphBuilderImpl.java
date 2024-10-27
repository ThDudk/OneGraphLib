package io.github.thdudk.construction.builders;

import io.github.thdudk.construction.restrictions.UnweightedGraphRestriction;
import io.github.thdudk.graphs.unweighted.AdjacencyListGraphImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.AllArgsConstructor;
import lombok.val;

import java.util.*;

@AllArgsConstructor
public final class GraphBuilderImpl<N> implements GraphBuilder<N> {
    private final Map<N, Set<N>> adjacencyList = new HashMap<>();
    private final Set<UnweightedGraphRestriction<N>> restrictions;

    public GraphBuilderImpl() {
        restrictions = Collections.emptySet();
    }

    /**
     * Adds node to this. If node is already contained in this, nothing happens.
     * @param node node to add
     * @return this to allow chaining
     */
    @Override
    public GraphBuilder<N> addNode(N node) {
        for(val restriction : restrictions) restriction.onNodeAdded(node);

        if(!adjacencyList.containsKey(node)) adjacencyList.put(node, new HashSet<>());
        return this;
    }

    @Override
    public GraphBuilder<N> addOutNeighbor(N root, N neighbor) {
        addNode(root).addNode(neighbor);
        for(val restriction : restrictions) restriction.onOutNeighborAdded(root, neighbor);
        adjacencyList.get(root).add(neighbor);
        return this;
    }

    /**
     * Builds the graph with information from the builder
     * @return the constructed graph
     */
    @Override
    public Graph<N> build() {
        for(val restriction : restrictions) restriction.onBuild();

        return new AdjacencyListGraphImpl<>(adjacencyList);
    }
}
