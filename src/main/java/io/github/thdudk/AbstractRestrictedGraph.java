package io.github.thdudk;

import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
public abstract class AbstractRestrictedGraph<N> implements RestrictedGraph<N> {
    private final Collection<GraphRestriction<N>> restrictions = new HashSet<>();

    public AbstractRestrictedGraph(Collection<GraphRestriction<N>> restrictions) {
        this.restrictions.addAll(restrictions);
    }

    @Override
    public Collection<GraphRestriction<N>> getRestrictions() {
        return Collections.unmodifiableCollection(restrictions);
    }

    @Override
    public RestrictedGraph<N> addRestriction(GraphRestriction<N> restriction) {
        restrictions.add(restriction);
        return this;
    }

    @Override
    public RestrictedGraph<N> removeRestriction(GraphRestriction<N> restriction) {
        restrictions.remove(restriction);
        return this;
    }
}
