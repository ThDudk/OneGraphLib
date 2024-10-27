package io.github.thdudk.construction.restrictions;

/**
 * Enforces that all edges are undirected. Directed parts of the edge must be added in sequence and
 * @param <N> The type of nodes
 * @param <E> The type of edges
 */
public class UndirectedRestriction<N, E> implements UnweightedGraphRestriction<N>, WeightedGraphRestriction<N, E> {
    private N lastStart = null;
    private N lastEnd = null;

    @Override
    public void onEdgeAdded(N start, E edge, N end) {
        onOutNeighborAdded(start, end);
    }
    @Override
    public void onOutNeighborAdded(N start, N end) {
        if(lastEnd != null) {
            if(!start.equals(lastEnd) || !end.equals(lastStart))
                throw new RuntimeException("Cannot guarantee the graph is undirected. Ensure reciprocal edges are added in sequence and without any other actions between.");
            lastStart = null;
            lastEnd = null;
            return;
        }
        lastStart = start;
        lastEnd = end;
    }
    @Override
    public void onBuild() {
        if(lastEnd != null)
            throw new RuntimeException("Cannot guarantee the graph is undirected. Ensure reciprocal edges are added in sequence and without any other actions between.");
    }
}
