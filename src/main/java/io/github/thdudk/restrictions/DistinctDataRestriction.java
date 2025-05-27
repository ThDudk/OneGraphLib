package io.github.thdudk.restrictions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashMap;
import java.util.HashSet;

@EqualsAndHashCode
@ToString
public class DistinctDataRestriction<N> implements GraphRestriction<N> {
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        return graph.getNodeDataMap().size() == new HashSet<>(graph.getNodeDataMap().values()).size();
    }
}
