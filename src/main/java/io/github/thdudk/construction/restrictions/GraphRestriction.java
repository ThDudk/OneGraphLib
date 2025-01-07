package io.github.thdudk.construction.restrictions;

// TODO: Change functions to throw specialize GraphRestrictionException instead of RuntimeException

import io.github.thdudk.graphs.unweighted.Graph;

/**
 * Restriction applied to graphs that is validated when actions are performed by the builder.
 * @param <N> The type of node contained in the builder
 */
public interface GraphRestriction<N> {
    /**
     * Called when the builder attempts to build a graph.
     * @throws RuntimeException The restriction is not yet satisfied.
     */
    void onBuild(Graph<N> graph);
}
