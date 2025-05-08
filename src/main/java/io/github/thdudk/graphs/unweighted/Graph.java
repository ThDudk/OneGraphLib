package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.RestrictedGraph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.restrictions.GraphRestriction;

import java.util.*;

/**
 * Representation of an unweighted graph.
 * Graph only deals with neighbours, rather than edges. Consequently, implementations should NOT allow multiple edges between two nodes (multiedges).
 * If multiedges are possible, {@link io.github.thdudk.graphs.weighted.WeightedGraph WeightedGraph} should be used instead, as it does support multiedges.
 *
 * @param <N> The Type of the nodes contained in the graph
 */
@JsonDeserialize(as = AdjacencyListGraphImpl.class)
public interface Graph<N> extends RestrictedGraph<N> {
    @JsonIgnore
    Collection<NodeID> getNodes();
    /// @return all out-neighbours of node
    /// @throws IllegalArgumentException If node is not contained in this
    Collection<NodeID> getNeighbours(NodeID node);

    /// Returns all nodes with an edge going into node.
    /// This includes undirected edges.
    ///
    /// The default implementation should be overridden if possible.
    /// @return neighbours with edges going into node
    default Collection<NodeID> getInNeighbours(NodeID node) {
        Set<NodeID> nodes = new HashSet<>();
        for(NodeID curr : getNodes())
            if(getNeighbours(curr).contains(node))
                nodes.add(curr);

        return nodes;
    }

    default int getDegree(NodeID node) {
        return getNeighbours(node).size();
    }
    default int getInDegree(NodeID node) {
        return getInNeighbours(node).size();
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

    default void throwIfRestrictionsNotSatisfied() {
        List<GraphRestriction<N>> unsatisfied = getRestrictions().stream().filter(a -> !a.isSatisfied(this)).toList();

        if(!unsatisfied.isEmpty()) throw new RuntimeException("Graph failed to satisfy restrictions: " + unsatisfied);
    }
    default void addSatisfiedRestrictions(Collection<GraphRestriction<N>> restrictions) {
        for(GraphRestriction<N> restriction : restrictions) {
            if(!restriction.isSatisfied(this)) continue;
            addRestriction(restriction);
        }
    }
}