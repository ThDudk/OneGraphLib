package io.github.thdudk.builders;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.IntegerNodeID;
import io.github.thdudk.ids.NodeID;

import java.util.HashMap;
import java.util.Map;

public class DistinctDataGraphBuilderImpl<N> extends GraphBuilderImpl<N> implements DistinctDataGraphBuilder<N> {
    private final Map<N, NodeID> dataToId = new HashMap<>();

    public DistinctDataGraphBuilderImpl() {
        super();
    }
    public DistinctDataGraphBuilderImpl(NodeID firstNodeID) {
        super(firstNodeID);
    }
    public DistinctDataGraphBuilderImpl(Graph<N> graph) {
        this(graph, new IntegerNodeID(0));
    }
    public DistinctDataGraphBuilderImpl(Graph<N> graph, NodeID firstNodeID) {
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
        if(contains(data))
            return idOf(data);

        NodeID id = super.addNode(data);
        dataToId.put(data, id);

        return id;
    }
    public boolean contains(N data) {
        return super.nodeData.containsValue(data);
    }

    @Override
    public void addDirEdge(N start, N end) {
        NodeID startID = addNode(start);
        NodeID endID = addNode(end);

        addDirEdge(startID, endID);
    }

    private NodeID idOf(N data) {
        return dataToId.get(data);
    }
}
