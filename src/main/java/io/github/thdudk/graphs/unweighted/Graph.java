package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.RestrictedGraph;
import io.github.thdudk.ids.NodeID;

import java.util.*;

/**
 * Representation of an unweighted graph.
 * @param <N> The Type of the nodes contained in the graph
 */
@JsonDeserialize(as = AdjacencyListGraphImpl.class)
public interface Graph<N> extends RestrictedGraph<N> {
    @JsonIgnore
    Collection<NodeID> getNodes();
    /// @return all out-neighbours of root
    /// @throws IllegalArgumentException If root is not contained in this
    Collection<NodeID> getNeighbours(NodeID root);

    /// Returns all nodes with an edge going into root.
    /// This includes undirected edges.
    ///
    /// The default implementation should be overridden if possible.
    /// @return neighbours with edges going into root
    default Collection<NodeID> getInNeighbours(NodeID root) {
        Set<NodeID> nodes = new HashSet<>();
        for(NodeID node : getNodes())
            if(getNeighbours(node).contains(root))
                nodes.add(node);

        return nodes;
    }

    default int getDegree(NodeID root) {
        return getNeighbours(root).size();
    }
    default int getInDegree(NodeID root) {
        return getInNeighbours(root).size();
    }

    N getNodeData(NodeID id);
    /// If the data is found in multiple places, a random instance is picked
    Collection<NodeID> nodeIdsWithData(N data);
    default NodeID anyNodeIdWithData(N data) {
        return nodeIdsWithData(data).stream().findAny().orElseThrow();
    };

    /// Constructs an adjacency list representing this
    default Map<NodeID, Collection<NodeID>> getUnweightedAdjacencyList() {
        Map<NodeID, Collection<NodeID>> map = new HashMap<>();
        for(NodeID node : getNodes()) map.put(node, getNeighbours(node));
        return map;
    }
    default Map<NodeID, N> getNodeDataMap() {
        Map<NodeID, N> map = new HashMap<>();
        for(NodeID id : getNodes()) {
            map.put(id, getNodeData(id));
        }
        return map;
    };
}