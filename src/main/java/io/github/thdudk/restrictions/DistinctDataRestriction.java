package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;

@EqualsAndHashCode
@ToString
public class DistinctDataRestriction<N> implements GraphRestriction<N> {
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        return graph.getNodeDataMap().size() == new HashSet<>(graph.getNodeDataMap().values()).size();
    }
}
