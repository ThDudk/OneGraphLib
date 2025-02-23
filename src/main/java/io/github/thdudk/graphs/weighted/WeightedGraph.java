package io.github.thdudk.graphs.weighted;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.Value;

import java.util.Optional;
import java.util.Set;

/**
 * Representation of the Graph data structure with edges containing data.
 * @param <N> Type of the nodes contained in the graph
 * @param <E> Type of the edges contained in the graph
 */
public interface WeightedGraph<N, E> extends Graph<N>, WeightedRestrictedGraph<N, E> {
    // TODO remove
    /**
     * Pair of an edge and it's endpoint
     * @param <N> Type of the Endpoint
     * @param <E> Type of the Edge
     */
    @Value
    class EdgeEndpointPair<N, E> {
        E edge;
        N endpoint;
    }

    Set<E> getEdgesBetween(N start, N end);
    default Optional<E> getEdgeBetween(N start, N end) {
        return getEdgesBetween(start, end).stream().findAny();
    }
}