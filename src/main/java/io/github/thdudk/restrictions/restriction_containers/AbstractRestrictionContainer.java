package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphRestriction;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
public abstract class AbstractRestrictionContainer<N> implements RestrictionContainer<N> {
    protected final Set<GraphRestriction<N>> restrictions = new HashSet<>();

    public AbstractRestrictionContainer(Collection<GraphRestriction<N>> restrictions) {
        this.restrictions.addAll(restrictions);
    }

    @Override
    public Collection<GraphRestriction<N>> getRestrictions() {
        return Set.copyOf(restrictions);
    }
}
