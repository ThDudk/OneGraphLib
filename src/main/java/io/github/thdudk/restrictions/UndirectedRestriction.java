package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;

public class UndirectedRestriction<N> implements GraphRestriction<N>{
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        for(N node : graph.getNodes())
            if(!graph.getInNeighbours(node).equals(graph.getNeighbours(node)))
                return false;

        return true;
    }
}
