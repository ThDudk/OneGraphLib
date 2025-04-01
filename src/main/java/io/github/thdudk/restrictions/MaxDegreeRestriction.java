package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MaxDegreeRestriction<N> implements GraphRestriction<N> {
    private final int maxDegree;

    @Override
    public boolean isSatisfied(Graph<N> graph) {
        for(NodeID node : graph.getNodes()) {
            if(graph.getDegree(node) > maxDegree) return false;
        }

        return true;
    }
}
