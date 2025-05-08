package io.github.thdudk.builders.weighted;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.IntegerNodeID;
import io.github.thdudk.ids.LongEdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;

public class WeightedGraphBuilderImpl<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements WeightedGraphBuilder<N, E> {
    private final Map<NodeID, Set<EdgeEndpointPair>> adjacencyList = new HashMap<>();
    private final Map<NodeID, N> nodeData = new HashMap<>();
    private final Map<EdgeID, E> edgeData = new HashMap<>();
    private NodeID prevNode;
    private EdgeID prevEdge;

    public WeightedGraphBuilderImpl() {
        this(new IntegerNodeID(0), new LongEdgeID(0));
    }
    public WeightedGraphBuilderImpl(NodeID firstNodeID, EdgeID firstEdgeID) {
        prevNode = firstNodeID;
        prevEdge = firstEdgeID;
    }
    public WeightedGraphBuilderImpl(WeightedGraph<N, E> graph) {
        this(graph, new IntegerNodeID(0), new LongEdgeID(0));
    }
    public WeightedGraphBuilderImpl(WeightedGraph<N, E> graph, NodeID firstNodeID, EdgeID firstEdgeID) {
        this(firstNodeID, firstEdgeID);

        // create a builder with all the given graph's nodes and neighbours
        Map<NodeID, NodeID> graphIDToBuilderID = new HashMap<>();

        // add nodes
        for(NodeID node : graph.getNodes()) {
            graphIDToBuilderID.put(node, addNode(graph.getNodeData(node)));
        }

        // add neighbours
        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                addDirEdge(graphIDToBuilderID.get(node),
                    graph.getEdgeData(graph.getEdgeBetween(node, neighbour)),
                    graphIDToBuilderID.get(neighbour)
                );
            }
        }
    }

    @Override
    public NodeID addNode(N data) {
        NodeID id = nextNodeID();
        adjacencyList.put(id, new HashSet<>());
        nodeData.put(id, data);
        return id;
    }

    @Override
    public void addDirEdge(NodeID start, E edge, NodeID end) {
        EdgeID id = nextEdgeID();
        adjacencyList.get(start).add(new EdgeEndpointPair(id, end));
        edgeData.put(id, edge);
    }


    private NodeID nextNodeID() {
        NodeID next = prevNode.incremented();
        prevNode = next;
        return next;
    }
    private EdgeID nextEdgeID() {
        EdgeID next = prevEdge.incremented();
        prevEdge = next;
        return next;
    }

    @Override
    public WeightedGraph<N, E> build() {
        return new AdjacencyListWeightedGraphImpl<>(getRestrictions(), getEdgeRestrictions(), adjacencyList, nodeData, edgeData);
    }
}
