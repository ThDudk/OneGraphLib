package io.github.thdudk.construction;

import io.github.thdudk.construction.builders.WeightedGraphBuilder;
import io.github.thdudk.construction.builders.WeightedGraphBuilderImpl;
import io.github.thdudk.construction.restrictions.GraphRestriction;
import io.github.thdudk.construction.restrictions.UndirectedRestriction;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link WeightedGraphBuilder} factory that composes a graph given a list of restrictions.
 * @param <N> Type of the nodes contained in the to be built graph
 */
public class WeightedGraphFactory<N, E> {
    private final Set<GraphRestriction<N>> restrictions = new HashSet<>();

    /**
     * @param restriction The restriction to add
     * @return this to allow chaining
     */
    public WeightedGraphFactory<N, E> addRestriction(GraphRestriction<N> restriction) {
        restrictions.add(restriction);
        return this;
    }
    /**
     * Restricts the graph to being undirected. Equivalent to calling <code>addRestriction(new UndirectedRestriction<>())</code>.
     * @return this to allow chaining
     * @see #addRestriction
     * @see UndirectedRestriction
     */
    public WeightedGraphFactory<N, E> undirected() {
        return addRestriction(new UndirectedRestriction<>());
    }
    /**
     * @return a builder with the given restrictions
     */
    public WeightedGraphBuilder<N, E> builder() {
        return new WeightedGraphBuilderImpl<>(restrictions);
    };
}
