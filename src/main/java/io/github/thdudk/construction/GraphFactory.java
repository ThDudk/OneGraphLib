package io.github.thdudk.construction;

import io.github.thdudk.construction.builders.GraphBuilder;
import io.github.thdudk.construction.builders.GraphBuilderImpl;
import io.github.thdudk.construction.restrictions.GraphRestriction;
import io.github.thdudk.construction.restrictions.UndirectedRestriction;

import java.util.HashSet;
import java.util.Set;

/**
 * {@link GraphBuilder} factory that composes a graph given a list of restrictions.
 * @param <N> Type of the nodes contained in the to be built graph
 */
public class GraphFactory<N> {
    private final Set<GraphRestriction<N>> restrictions = new HashSet<>();

    /**
     * @param restriction The restriction to add
     * @return this to allow chaining
     */
    public GraphFactory<N> addRestriction(GraphRestriction<N> restriction) {
        restrictions.add(restriction);
        return this;
    }
    /**
     * Restricts the graph to being undirected. Equivalent to calling <code>addRestriction(new UndirectedRestriction<>())</code>.
     * @return this to allow chaining
     * @see #addRestriction
     * @see UndirectedRestriction
     */
    public GraphFactory<N> undirected() {
        return addRestriction(new UndirectedRestriction<>());
    }
    /**
     * @return a builder with the given restrictions
     */
    public GraphBuilder<N> builder() {
        return new GraphBuilderImpl<>(restrictions);
    };
}
