package io.github.thdudk;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.restrictions.GraphRestriction;

import java.util.Collection;
import java.util.List;

public interface RestrictedGraph<N> {
    Collection<GraphRestriction<N>> getRestrictions();
    RestrictedGraph<N> addRestriction(GraphRestriction<N> restriction);
    default RestrictedGraph<N> addAllRestrictions(Collection<GraphRestriction<N>> restrictions) {
        for(GraphRestriction<N> restriction : restrictions) {
            addRestriction(restriction);
        }
        return this;
    }
    RestrictedGraph<N> removeRestriction(GraphRestriction<N> restriction);

    default boolean hasRestriction(GraphRestriction<N> restriction) {
        return getRestrictions().contains(restriction);
    }
}
