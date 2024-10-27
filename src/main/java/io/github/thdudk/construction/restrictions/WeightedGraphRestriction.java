package io.github.thdudk.construction.restrictions;

/**
 * Restriction applied to graphs that is validated when actions are performed by the builder.
 * @param <N> The type of node contained in the builder
 * @param <E> The type of edge contained in the builder
 */
public interface WeightedGraphRestriction<N, E> extends GraphRestriction<N>{
    /**
     * Called when a directed edge facing away from start is added.
     * <p>Undirected edges are usually achieved by calling this function twice, going both ways
     * @param start The start node
     * @param end The end node
     * @throws RuntimeException The added edge violates the restriction.
     */
    default void onEdgeAdded(N start, E edge, N end) {};
}
