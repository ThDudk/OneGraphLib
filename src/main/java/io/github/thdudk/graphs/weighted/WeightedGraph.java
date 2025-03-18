package io.github.thdudk.graphs.weighted;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.Value;

import java.util.Set;

/**
 * Representation of the Graph data structure with edges containing data.
 * @param <N> Type of the nodes contained in the graph
 * @param <E> Type of the edges contained in the graph
 */
public interface WeightedGraph<N, E> extends Graph<N>, WeightedRestrictedGraph<N, E> {
    /// Pair of an edge and it's endpoint
    /// @param <N> Type of the Endpoint
    /// @param <E> Type of the Edge
    @Value
    class EdgeEndpointPair<N, E> {
        E edge;
        N endpoint;
    }

    /// If start and end are not neighbours, an empty set should be returned.
    ///
    /// @return the data of all edges between start and end.
    Set<E> getEdgesBetween(N start, N end);
    /// If there are more than one edges between start and end, the returned edge value is arbitrary.
    ///
    /// If there is the possibility of more than one edge being present,
    /// {@link #getEdgesBetween(Object, Object) getEdgesBetween(start, end)} should be used instead.
    ///
    /// @return the data of the edge between start and end
    /// @throws IllegalArgumentException if there are no edges between start and end
    default E getEdgeBetween(N start, N end) throws IllegalArgumentException {
        return getEdgesBetween(start, end)
            .stream()
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("Cannot find edge between nodes that are not neighbours"));
    }
}