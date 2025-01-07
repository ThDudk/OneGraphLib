package io.github.thdudk.construction.builders;

import io.github.thdudk.construction.restrictions.GraphRestriction;
import io.github.thdudk.graphs.unweighted.AdjacencyListGraphImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.AllArgsConstructor;
import lombok.val;

import java.util.*;

@AllArgsConstructor
public final class GraphBuilderImpl<N> implements GraphBuilder<N> {
    private final Map<N, Set<N>> adjacencyList = new HashMap<>();
    private final Set<GraphRestriction<N>> restrictions;

    public GraphBuilderImpl() {
        restrictions = Collections.emptySet();
    }
    public GraphBuilderImpl(Graph<N> graph) {
        this();
        // create a builder with all the given graph's nodes and neighbors
        for(N node : graph.getNodes()) {
            addNode(node);
            for(N neighbor : graph.getOutNeighbours(node)) {
                addOutNeighbor(node, neighbor);
            }
        }
    }

    /**
     * Adds node to this. If node is already contained in this, nothing happens.
     * @param node node to add
     * @return this to allow chaining
     */
    @Override
    public GraphBuilder<N> addNode(N node) {
        if(!adjacencyList.containsKey(node)) adjacencyList.put(node, new HashSet<>());
        return this;
    }

    @Override
    public GraphBuilder<N> addOutNeighbor(N root, N neighbor) {
        addNode(root).addNode(neighbor);
        adjacencyList.get(root).add(neighbor);
        return this;
    }

    @Override
    public GraphBuilder<N> removeNode(N node) {
        adjacencyList.remove(node);
        for(Set<N> neighbours : adjacencyList.values()) {
            neighbours.remove(node);
        }
        return this;
    }

    /**
     * Builds the graph with information from the builder
     * @return the constructed graph
     */
    @Override
    public Graph<N> build() {
        Graph<N> graph = new AdjacencyListGraphImpl<>(adjacencyList);

        // throws an exception if a restriction is not met
        for(val restriction : restrictions)
            restriction.onBuild(graph);

        return graph;
    }
}
