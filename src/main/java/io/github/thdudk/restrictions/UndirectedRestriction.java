package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

public class UndirectedRestriction<N> implements GraphRestriction<N>{
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        for(NodeID node : graph.getNodes())
            if(!graph.getInNeighbours(node).equals(graph.getNeighbours(node)))
                return false;

        return true;
    }
}
