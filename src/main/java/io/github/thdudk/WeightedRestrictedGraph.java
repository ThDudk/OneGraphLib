package io.github.thdudk;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;

import java.util.Set;

public interface WeightedRestrictedGraph<N, E> extends RestrictedGraph<N> {
    Set<GraphEdgeRestriction<N, E>> getEdgeRestrictions();

    WeightedRestrictedGraph<N, E> addRestriction(GraphEdgeRestriction<N, E> restriction);

    WeightedRestrictedGraph<N, E> removeRestriction(GraphEdgeRestriction<N, E> restriction);

    default boolean hasEdgeRestriction(GraphEdgeRestriction<N, E> restriction) {
        return getEdgeRestrictions().contains(restriction);
    }

    @Override
    default boolean isSatisfied(Graph<N> graph) {
        return RestrictedGraph.super.isSatisfied(graph) && edgeRestrictionsSatisfied(graph);
    }

    private boolean edgeRestrictionsSatisfied(Graph<N> graph) {
        for(GraphRestriction<N> restriction : getRestrictions()) {
            if(!restriction.isSatisfied(graph)) return false;
        }
        return true;
    }

}