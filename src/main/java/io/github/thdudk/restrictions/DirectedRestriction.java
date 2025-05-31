package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class DirectedRestriction<N> implements GraphRestriction<N> {
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                if(graph.getNeighbours(neighbour).contains(node)) return false;
            }
        }
        return true;
    }
}
