package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaxInDegreeRestriction<N> implements GraphRestriction<N> {
    private final int maxInDegree;

    @Override
    public boolean isSatisfied(Graph<N> graph) {
        for(NodeID node : graph.getNodes()) {
            if(graph.getInDegree(node) > maxInDegree) return false;
        }

        return true;
    }
}
