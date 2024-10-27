package io.github.thdudk.construction.restrictions;

// TODO: Change functions to throw specialize GraphRestrictionException instead of RuntimeException
/**
 * Restriction applied to graphs that is validated when actions are performed by the builder.
 * @param <N> The type of node contained in the builder
 */
public interface GraphRestriction<N> {
    /**
     * Called when a node is added.
     * @param node The node added
     * @throws RuntimeException The added node violates the restriction.
     */
    default void onNodeAdded(N node) {};
    /**
     * Called when the builder attempts to build a graph.
     * @throws RuntimeException The restriction is not yet satisfied.
     */
    default void onBuild() {};
}
