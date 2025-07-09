package io.github.thdudk.builders.unweighted;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.thdudk.graphs.unweighted.AdjacencyListGraphImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;
import io.github.thdudk.restrictions.restriction_containers.AbstractMutableRestrictionContainer;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class ExplicitIdsGraphBuilderImpl<N> extends AbstractMutableRestrictionContainer<N> implements ExplicitIdsGraphBuilder<N> {
    // TODO re-evaluate these guys being protected. Protected helper functions may be better
    protected final Map<NodeID, Collection<Graph.EdgeEndpointPair>> adjacencyList = new HashMap<>();
    protected final Map<NodeID, N> nodeData = new HashMap<>();
    protected final Set<EdgeID> edgeIds = new HashSet<>();

    /// Creates a builder copy of the given graph.
    ///
    /// NodeIDs and edgeIDs are conserved.
    public ExplicitIdsGraphBuilderImpl(Graph<N> graph) {
        addAllRestrictions(graph.getRestrictions());

        // add nodes
        for(NodeID node : graph.getNodes()) {
            addNode(node, graph.getNodeData(node));
        }

        // add neighbours
        for(Graph.EdgeDescriptor edge : graph.getEdgeDescriptors()) {
            addDirEdge(edge.start(), edge.end(), edge.id());
        }
    }

    @JsonCreator
    public ExplicitIdsGraphBuilderImpl(
        @JsonProperty("restrictions") List<GraphRestriction<N>> restrictions,
        @JsonProperty("nodes") List<Graph.NodeDescriptor<N>> nodes,
        @JsonProperty("edges") List<Graph.EdgeDescriptor> edges
    ) {
        addAllRestrictions(restrictions);

        for(Graph.NodeDescriptor<N> node : nodes) {
            addNode(node.id(), node.data());
        }

        for(Graph.EdgeDescriptor edge : edges) {
            addDirEdge(edge.start(), edge.end(), edge.id());
        }
    }

    @Override
    public void addNode(NodeID id, N data) {
        Objects.requireNonNull(id);

        if(nodeData.containsKey(id)) {
            throw new IllegalArgumentException("id " + id +  " is already contained in this graph");
        }
        adjacencyList.putIfAbsent(id, new HashSet<>());
        nodeData.put(id, data);
    }

    @Override
    public N removeNode(NodeID id) {
        adjacencyList.remove(id);
        for(Map.Entry<NodeID, Collection<Graph.EdgeEndpointPair>> node : adjacencyList.entrySet()) {
            node.getValue().removeIf(pair -> pair.getEndpoint().equals(id));
        }
        return nodeData.remove(id);
    }

    @Override
    public void addDirEdge(NodeID root, NodeID neighbour, EdgeID edgeId) {
        Objects.requireNonNull(edgeId);

        if(!nodeData.containsKey(root) || !nodeData.containsKey(neighbour)) throw new IllegalArgumentException("Nodes must exist in graph.");

        if(edgeIds.contains(edgeId)) throw new RuntimeException("cannot have duplicate edgeIDs");
        edgeIds.add(edgeId);

        adjacencyList.get(root).add(new Graph.EdgeEndpointPair(edgeId, neighbour));
    }

    @Override
    public Graph.EdgeDescriptor removeEdge(EdgeID id) {
        Graph<N> graph = buildWithoutCheckingRestrictions();

        Graph.EdgeDescriptor edge = graph.getEdgeDescriptors()
            .stream()
            .filter(pair -> pair.id().equals(id))
            .findAny()
            .orElseThrow();

        edgeIds.remove(id);
        adjacencyList.get(edge.start()).remove(new Graph.EdgeEndpointPair(edge.id(), edge.end()));
        return edge;
    }

    @Override
    public Graph<N> build() {
        Graph<N> graph = buildWithoutCheckingRestrictions();

        for(GraphRestriction<N> restriction : getRestrictions()) {
            if(!restriction.isSatisfied(graph)) throw new RuntimeException("Graph does not satisfy restriction: " + restriction);
        }

        return graph;
    }

    private Graph<N> buildWithoutCheckingRestrictions() {
        return new AdjacencyListGraphImpl<>(getRestrictions(), adjacencyList, nodeData);
    }
}
