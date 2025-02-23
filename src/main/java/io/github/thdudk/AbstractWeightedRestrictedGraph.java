package io.github.thdudk;

import io.github.thdudk.restrictions.GraphEdgeRestriction;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractWeightedRestrictedGraph<N, E> extends AbstractRestrictedGraph<N> implements WeightedRestrictedGraph<N, E> {
    private final Set<GraphEdgeRestriction<N, E>> restrictions = new HashSet<>();

    @Override
    public Set<GraphEdgeRestriction<N, E>> getEdgeRestrictions() {
        return Collections.unmodifiableSet(restrictions);
    }
    @Override
    public WeightedRestrictedGraph<N, E> addRestriction(GraphEdgeRestriction<N, E> restriction) {
        restrictions.add(restriction);
        return this;
    }
    @Override
    public WeightedRestrictedGraph<N, E> removeRestriction(GraphEdgeRestriction<N, E> restriction) {
        restrictions.remove(restriction);
        return this;
    }
}
