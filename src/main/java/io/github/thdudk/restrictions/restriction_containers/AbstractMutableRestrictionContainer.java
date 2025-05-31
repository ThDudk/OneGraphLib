package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphRestriction;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public abstract class AbstractMutableRestrictionContainer<N> extends AbstractRestrictionContainer<N> implements MutableRestrictionContainer<N>{
    public AbstractMutableRestrictionContainer(Collection<GraphRestriction<N>> graphRestrictions) {
        super(graphRestrictions);
    }

    @Override
    public MutableRestrictionContainer<N> addRestriction(GraphRestriction<N> restriction) {
        restrictions.add(restriction);
        return this;
    }

    @Override
    public MutableRestrictionContainer<N> removeRestriction(GraphRestriction<N> restriction) {
        if(!restrictions.remove(restriction)) throw new IllegalArgumentException("Restriction not contained. Cannot remove " + restriction);
        return this;
    }
}
