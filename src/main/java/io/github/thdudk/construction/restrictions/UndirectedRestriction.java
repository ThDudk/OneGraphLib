package io.github.thdudk.construction.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;

/**
 * Enforces that all edges are undirected. Directed parts of the edge must be added in sequence and
 * @param <N> The type of nodes
 */
public class UndirectedRestriction<N> implements GraphRestriction<N> {
    @Override
    public void onBuild(Graph<N> graph) {
        for(N node : graph.getNodes()) {
            if(!graph.getInNeighbours(node).equals(graph.getOutNeighbours(node)))
                throw new RuntimeException("Failed to satisfy undirected restriction. Graph is directed.");
        }
    }
}
