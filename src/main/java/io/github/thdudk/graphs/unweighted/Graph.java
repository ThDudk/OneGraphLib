package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.RestrictedGraph;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Representation of an unweighted graph.
 * @param <N> The Type of the nodes contained in the graph
 */
@JsonDeserialize(as = AdjacencyListGraphImpl.class)
public interface Graph<N> extends RestrictedGraph<N> {
    @JsonIgnore
    Set<N> getNodes();
    /// @return all out neighbors of root
    /// @throws IllegalArgumentException If root is not contained in this
    Set<N> getNeighbours(N root);

    /// Returns all nodes with an edge going into root.
    /// This includes undirected edges.
    ///
    /// The default implementation should be overridden if possible.
    /// @return neighbours with edges going into root
    default Set<N> getInNeighbours(N root) {
        Set<N> nodes = new HashSet<>();
        for(N node : getNodes())
            if(getNeighbours(node).contains(root))
                nodes.add(node);

        return nodes;
    }

    default int getDegree(N root) {
        return getNeighbours(root).size();
    }
    default int getInDegree(N root) {
        return getInNeighbours(root).size();
    }

    /// Constructs an adjacency list representing this
    default Map<N, Set<N>> getUnweightedAdjacencyList() {
        Map<N, Set<N>> map = new HashMap<>();
        for(N node : getNodes()) map.put(node, getNeighbours(node));
        return map;
    }
}