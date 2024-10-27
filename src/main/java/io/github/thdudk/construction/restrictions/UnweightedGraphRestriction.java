package io.github.thdudk.construction.restrictions;

public interface UnweightedGraphRestriction<N> extends GraphRestriction<N> {
    /**
     * Called when a directed neighbor facing away from start is added.
     * <p>Undirected neighbors are usually achieved by calling this function twice, going both ways
     * @param start The start node
     * @param end The end node
     * @throws RuntimeException The added out neighbor violates the restriction.
     */
    default void onOutNeighborAdded(N start, N end) {};

}
