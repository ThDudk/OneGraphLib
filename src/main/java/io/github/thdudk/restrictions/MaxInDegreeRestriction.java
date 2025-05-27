package io.github.thdudk.restrictions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@ToString
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
