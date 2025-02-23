package io.github.thdudk;

import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode
@ToString
public abstract class AbstractRestrictedGraph<N> implements RestrictedGraph<N> {
    private final Set<GraphRestriction<N>> restrictions = new HashSet<>();

    @Override
    public Set<GraphRestriction<N>> getRestrictions() {
        return Collections.unmodifiableSet(restrictions);
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
