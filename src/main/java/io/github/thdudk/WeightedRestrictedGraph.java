package io.github.thdudk;

import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.restrictions.GraphEdgeRestriction;

import java.util.Collection;
import java.util.List;

public interface WeightedRestrictedGraph<N, E> extends RestrictedGraph<N> {
    Collection<GraphEdgeRestriction<N, E>> getEdgeRestrictions();

    WeightedRestrictedGraph<N, E> addRestriction(GraphEdgeRestriction<N, E> restriction);
    WeightedRestrictedGraph<N, E> removeRestriction(GraphEdgeRestriction<N, E> restriction);

    default boolean hasEdgeRestriction(GraphEdgeRestriction<N, E> restriction) {
        return getEdgeRestrictions().contains(restriction);
    }

    default void throwIfEdgeRestrictionsNotSatisfied(WeightedGraph<N, E> graph) {
        List<GraphEdgeRestriction<N, E>> unsatisfied = getEdgeRestrictions().stream().filter(a -> !a.isSatisfied(graph)).toList();
        if(!unsatisfied.isEmpty()) throw new RuntimeException("Graph failed to satisfy edge restrictions: " + unsatisfied);
    }
}