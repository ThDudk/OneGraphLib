package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.weighted.WeightedGraph;

public interface GraphEdgeRestriction<N, E> {
    boolean isSatisfied(WeightedGraph<N, E> graph);
}
