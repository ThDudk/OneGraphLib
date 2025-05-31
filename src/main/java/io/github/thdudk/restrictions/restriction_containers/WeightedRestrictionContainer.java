package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphEdgeRestriction;

import java.util.Collection;

public interface WeightedRestrictionContainer<N, E> extends RestrictionContainer<N> {
    Collection<GraphEdgeRestriction<N, E>> getEdgeRestrictions();
    default boolean hasEdgeRestriction(GraphEdgeRestriction<N, E> restriction) {
        return getEdgeRestrictions().contains(restriction);
    }
}
