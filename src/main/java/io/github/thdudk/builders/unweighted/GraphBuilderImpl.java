package io.github.thdudk.builders.unweighted;

import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.unweighted.AdjacencyListGraphImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.IntegerNodeID;
import io.github.thdudk.ids.NodeID;

import java.util.*;

public class GraphBuilderImpl<N> extends AbstractRestrictedGraph<N> implements GraphBuilder<N> {
    protected final Map<NodeID, Set<NodeID>> adjacencyList = new HashMap<>();
    protected final Map<NodeID, N> nodeData = new HashMap<>();
    private NodeID prevNode;

    /// Calls {@link GraphBuilderImpl#GraphBuilderImpl(NodeID) this(new IntegerNodeID(0))}
    public GraphBuilderImpl() {
        this(new IntegerNodeID(0));
    }
    /// Sets the first node ID to `firstNodeID`
    public GraphBuilderImpl(NodeID firstNodeID) {
        prevNode = firstNodeID;
    }
    /// Calls {@link GraphBuilderImpl#GraphBuilderImpl(Graph, NodeID) this(graph, new IntegerNodeID(0))}
    public GraphBuilderImpl(Graph<N> graph) {
        this(graph, new IntegerNodeID(0));
    }
    /// Creates a builder with all the nodes of `graph` and sets the first node ID to `firstNodeID`
    public GraphBuilderImpl(Graph<N> graph, NodeID firstNodeID) {
        this(firstNodeID);

        // create a builder with all the given graph's nodes and neighbours
        Map<NodeID, NodeID> graphIDToBuilderID = new HashMap<>();

        // add nodes
        for(NodeID node : graph.getNodes()) {
            graphIDToBuilderID.put(node, addNode(graph.getNodeData(node)));
        }

        // add neighbours
        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                addDirEdge(graphIDToBuilderID.get(node), graphIDToBuilderID.get(neighbour));
            }
        }
    }

    @Override
    public NodeID addNode(N data) {
        NodeID id = nextNodeID();
        addNode(id, data);
        return id;
    }

    @Override
    public Optional<N> addNode(NodeID id, N data) {
        N replaced = null;
        if(nodeData.containsKey(id)) {
            replaced = nodeData.get(id);
        }
        adjacencyList.putIfAbsent(id, new HashSet<>());
        nodeData.put(id, data);

        return Optional.ofNullable(replaced);
    }

    @Override
    public void addDirEdge(NodeID root, NodeID neighbour) {
        adjacencyList.get(root).add(neighbour);
    }

    /// Returns the next available nodeID.
    private NodeID nextNodeID() {
        NodeID next;
        do {
            next = prevNode.incremented();
        } while (nodeData.containsKey(next));

        prevNode = next;
        return next;
    }

    @Override
    public Graph<N> build() {
        return new AdjacencyListGraphImpl<>(getRestrictions(), adjacencyList, nodeData);
    }
}
