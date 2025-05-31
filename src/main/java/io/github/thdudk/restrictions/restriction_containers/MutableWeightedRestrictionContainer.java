package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphEdgeRestriction;

import java.util.Collection;

public interface MutableWeightedRestrictionContainer<N, E> extends WeightedRestrictionContainer<N, E>, MutableRestrictionContainer<N> {
    MutableWeightedRestrictionContainer<N, E> addEdgeRestriction(GraphEdgeRestriction<N, E> restriction);
    default MutableWeightedRestrictionContainer<N, E> addAllEdgeRestrictions(Collection<GraphEdgeRestriction<N, E>> restrictions) {
        for(GraphEdgeRestriction<N, E> restriction : restrictions) {
            addEdgeRestriction(restriction);
        }
        return this;
    }
    MutableWeightedRestrictionContainer<N, E> removeEdgeRestriction(GraphEdgeRestriction<N, E> restriction);
}
