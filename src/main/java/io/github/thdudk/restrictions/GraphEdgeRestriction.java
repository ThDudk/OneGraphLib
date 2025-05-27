package io.github.thdudk.restrictions;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.thdudk.graphs.weighted.WeightedGraph;

@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public interface GraphEdgeRestriction<N, E> {
    boolean isSatisfied(WeightedGraph<N, E> graph);
}
