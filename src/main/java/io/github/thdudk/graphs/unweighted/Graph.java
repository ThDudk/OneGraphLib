package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.restriction_containers.RestrictionContainer;
import lombok.Value;

import java.util.*;
import java.util.stream.Collectors;

/// @param <N> The Type of the nodes contained in the graph
@JsonDeserialize(builder = ExplicitIdsGraphBuilderImpl.class)
public interface Graph<N> extends RestrictionContainer<N> {
    /// Pair of an edge and it's endpoint
    @Value
    class EdgeEndpointPair {
        EdgeID edge;
        NodeID endpoint;
    }

    record NodeDescriptor<N> (NodeID id, N data) { }
    record EdgeDescriptor (NodeID start, NodeID end, EdgeID id) { }

    default boolean hasNode(NodeID id) {
        return getNodes().contains(id);
    }
    /// Finds the edge by iterating over all edges.
    /// It's recommended this gets overridden with a more efficient implementation in subclasses.
    default boolean hasEdge(EdgeID id) {
        for(NodeID node : getNodes()) {
            for(NodeID neighbour : getNeighbours(node)) {
                if(getEdgesBetween(node, neighbour).contains(id)) {
                    return true;
                }
            }
        }
        return false;
    }

    @JsonIgnore
    Collection<NodeID> getNodes();
    /// @return all out-neighbours of node
    /// @throws IllegalArgumentException If `node` is not contained in this
    Collection<NodeID> getNeighbours(NodeID node);
    /// If start and end are not neighbours, an empty set should be returned.
    ///
    /// @return the data of all edges between start and end.
    Collection<EdgeID> getEdgesBetween(NodeID start, NodeID end);
    default Optional<EdgeID> getAnyEdgeBetween(NodeID start, NodeID end) {
        return getEdgesBetween(start, end).stream().findAny();
    }
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

    @JsonIgnore
    default Map<NodeID, Collection<EdgeEndpointPair>> getAdjacencyList() {
        Map<NodeID, Collection<EdgeEndpointPair>> adjacencyList = new HashMap<>();

        for(NodeID node : getNodes()) {
            adjacencyList.put(node, new HashSet<>());
        }

        for(EdgeDescriptor edge : getEdgeDescriptors()) {
            adjacencyList.get(edge.start()).add(new EdgeEndpointPair(edge.id(), edge.end()));
        }

        return adjacencyList;
    }

    // -- Node data functions --
    N getNodeData(NodeID id);
    /// If the data is found in multiple places, a random instance is picked
    Collection<NodeID> nodeIdsWithData(N data);
    default NodeID anyNodeIdWithData(N data) {
        return nodeIdsWithData(data).stream().findAny().orElseThrow();
    };
    @JsonIgnore
    default Map<NodeID, N> getNodeDataMap() {
        return getNodeDescriptors().stream().collect(Collectors.toMap(NodeDescriptor::id, NodeDescriptor::data));
    }

    // -- serialization functions --
    @JsonProperty("nodes")
    default Collection<NodeDescriptor<N>> getNodeDescriptors() {
        Collection<NodeDescriptor<N>> collection = new ArrayList<>();
        for(NodeID node : getNodes()) {
            collection.add(new NodeDescriptor<>(node, getNodeData(node)));
        }
        return collection;
    }
    @JsonProperty("edges")
    default Collection<EdgeDescriptor> getEdgeDescriptors() {
        Collection<EdgeDescriptor> collection = new ArrayList<>();
        for(NodeID node : getNodes()) {
            for(NodeID neighbour : getNeighbours(node)) {
                for(EdgeID edge : getEdgesBetween(node, neighbour)) {
                    collection.add(new EdgeDescriptor(node, neighbour, edge));
                }
            }
        }
        return collection;
    }
}