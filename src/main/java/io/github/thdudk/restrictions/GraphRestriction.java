package io.github.thdudk.restrictions;

import io.github.thdudk.graphs.unweighted.Graph;

public interface GraphRestriction<N> {
    boolean isSatisfied(Graph<N> graph);
}
