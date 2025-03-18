package io.github.thdudk.restrictions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.thdudk.graphs.unweighted.Graph;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface GraphRestriction<N> {
    boolean isSatisfied(Graph<N> graph);
}
