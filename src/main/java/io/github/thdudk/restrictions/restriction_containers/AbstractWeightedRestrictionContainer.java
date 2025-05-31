package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public abstract class AbstractWeightedRestrictionContainer<N, E> extends AbstractMutableRestrictionContainer<N> implements WeightedRestrictionContainer<N, E>{
    protected final Set<GraphEdgeRestriction<N, E>> edgeRestrictions = new HashSet<>();

    public AbstractWeightedRestrictionContainer(Collection<GraphRestriction<N>> graphRestrictions, Collection<GraphEdgeRestriction<N, E>> edgeRestrictions) {
        super(graphRestrictions);
        this.edgeRestrictions.addAll(edgeRestrictions);
    }

    @Override
    public Collection<GraphEdgeRestriction<N, E>> getEdgeRestrictions() {
        return Set.copyOf(edgeRestrictions);
    }
}
