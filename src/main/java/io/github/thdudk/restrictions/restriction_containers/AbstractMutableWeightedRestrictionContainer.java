package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public abstract class AbstractMutableWeightedRestrictionContainer<N, E> extends AbstractWeightedRestrictionContainer<N, E> implements MutableWeightedRestrictionContainer<N, E>{
    public AbstractMutableWeightedRestrictionContainer(Collection<GraphRestriction<N>> graphRestrictions, Collection<GraphEdgeRestriction<N, E>> edgeRestrictions) {
        super(graphRestrictions, edgeRestrictions);
    }

    @Override
    public MutableWeightedRestrictionContainer<N, E> addEdgeRestriction(GraphEdgeRestriction<N, E> restriction) {
        edgeRestrictions.add(restriction);
        return this;
    }

    @Override
    public MutableWeightedRestrictionContainer<N, E> removeEdgeRestriction(GraphEdgeRestriction<N, E> restriction) {
        if(!edgeRestrictions.remove(restriction)) throw new IllegalArgumentException("Restriction not contained. Cannot remove" + restriction);
        return this;
    }
}
