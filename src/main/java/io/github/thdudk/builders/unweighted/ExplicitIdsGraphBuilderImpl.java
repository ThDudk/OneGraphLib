package io.github.thdudk.builders.unweighted;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.unweighted.AdjacencyListGraphImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class ExplicitIdsGraphBuilderImpl<N> extends AbstractRestrictedGraph<N> implements ExplicitIdsGraphBuilder<N> {
    // TODO re-evaluate these guys being protected. Protected helper functions may be better
    protected final Map<NodeID, Set<Graph.EdgeEndpointPair>> adjacencyList = new HashMap<>();
    protected final Map<NodeID, N> nodeData = new HashMap<>();

    /// Creates a builder with all the nodes of `graph`
    public ExplicitIdsGraphBuilderImpl(Graph<N> graph) {
        // add nodes
        for(NodeID node : graph.getNodes()) {
            addNode(node, graph.getNodeData(node));
        }

        // add neighbours
        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                for(EdgeID edgeID : graph.getEdgesBetween(node, neighbour)) {
                    addDirEdge(node, neighbour, edgeID);
                }
            }
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
        if(nodeData.containsKey(id)) {
            throw new IllegalArgumentException("id " + id +  " is already contained in this graph");
        }
        adjacencyList.putIfAbsent(id, new HashSet<>());
        nodeData.put(id, data);
    }

    @Override
    public void addDirEdge(NodeID root, NodeID neighbour, EdgeID edgeId) {
        adjacencyList.get(root).add(new Graph.EdgeEndpointPair(edgeId, neighbour));
    }

    @Override
    public Graph<N> build() {
        return new AdjacencyListGraphImpl<>(getRestrictions(), adjacencyList, nodeData);
    }
}
