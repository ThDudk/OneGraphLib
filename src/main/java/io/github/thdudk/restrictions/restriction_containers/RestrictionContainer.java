package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphRestriction;

import java.util.Collection;

public interface RestrictionContainer<N> {
    Collection<GraphRestriction<N>> getRestrictions();
    default boolean hasRestriction(GraphRestriction<N> restriction) {
        return getRestrictions().contains(restriction);
    }
}
