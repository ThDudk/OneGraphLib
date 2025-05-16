package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

public class NoMultiEdgesRestriction<N> implements GraphRestriction<N> {
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                if(graph.getEdgesBetween(node, neighbour).size() > 1) return false;
            }
        }
        return true;
    }
}
