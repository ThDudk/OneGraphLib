package io.github.thdudk.restrictions.restriction_containers;

import io.github.thdudk.restrictions.GraphRestriction;

import java.util.Collection;

public interface MutableRestrictionContainer<N> extends RestrictionContainer<N> {
    MutableRestrictionContainer<N> addRestriction(GraphRestriction<N> restriction);
    default MutableRestrictionContainer<N> addAllRestrictions(Collection<GraphRestriction<N>> restrictions) {
        for(GraphRestriction<N> restriction : restrictions) {
            addRestriction(restriction);
        }
        return this;
    }
    MutableRestrictionContainer<N> removeRestriction(GraphRestriction<N> restriction);
    default MutableRestrictionContainer<N> removeAllRestrictions() {
        for(GraphRestriction<N> restriction : getRestrictions()) {
            removeRestriction(restriction);
        }
        return this;
    }
}
