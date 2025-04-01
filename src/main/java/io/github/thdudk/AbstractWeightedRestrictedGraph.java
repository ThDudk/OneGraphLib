package io.github.thdudk;

import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@EqualsAndHashCode(callSuper = true)
@ToString
@NoArgsConstructor
public abstract class AbstractWeightedRestrictedGraph<N, E> extends AbstractRestrictedGraph<N> implements WeightedRestrictedGraph<N, E> {
    private final Collection<GraphEdgeRestriction<N, E>> restrictions = new HashSet<>();

    public AbstractWeightedRestrictedGraph(Collection<GraphRestriction<N>> restrictions, Collection<GraphEdgeRestriction<N, E>> edgeRestrictions) {
        super(restrictions);
        for(GraphEdgeRestriction<N, E> restriction : edgeRestrictions) addRestriction(restriction);
    }

    @Override
    public Collection<GraphEdgeRestriction<N, E>> getEdgeRestrictions() {
        return Collections.unmodifiableCollection(restrictions);
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
