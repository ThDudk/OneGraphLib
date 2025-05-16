package io.github.thdudk.graphs.weighted;

import io.github.thdudk.WeightedRestrictedGraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;

/**
 * Representation of the Graph data structure with edges containing data.
 * @param <N> Type of the nodes contained in the graph
 * @param <E> Type of the edges contained in the graph
 */
public interface WeightedGraph<N, E> extends Graph<N>, WeightedRestrictedGraph<N, E> {
    /// If start and end are not neighbours, an empty set should be returned.
    ///
    /// @return the data of all edges between start and end.
    Collection<EdgeID> getEdgesBetween(NodeID start, NodeID end);
    /// If there are more than one edges between start and end, the returned edge value is arbitrary.
    ///
    /// If there is the possibility of more than one edge being present,
    /// {@link #getEdgesBetween(NodeID, NodeID)} should be used instead.
    ///
    /// @return the data of the edge between start and end
    /// @throws IllegalArgumentException if there are no edges between start and end
    default EdgeID getEdgeBetween(NodeID start, NodeID end) throws IllegalArgumentException {
        return getEdgesBetween(start, end)
            .stream()
            .findAny()
            .orElseThrow(() -> new IllegalArgumentException("Cannot find edge between nodes that are not neighbours"));
    }

    E getEdgeData(EdgeID id);
    Collection<EdgeID> edgeIdsWithData(E data);
    default EdgeID anyEdgeIdWithData(E data) {
        return edgeIdsWithData(data).stream().findAny().orElseThrow();
    }
}