package io.github.thdudk.builders.paths;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.graphs.unweighted.PathGraphImpl;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.DirectedRestriction;
import io.github.thdudk.restrictions.MaxDegreeRestriction;
import io.github.thdudk.restrictions.NoMultiEdgesRestriction;
import io.github.thdudk.restrictions.restriction_containers.AbstractMutableRestrictionContainer;
import io.github.thdudk.serialization.GraphVizExporter;
import lombok.ToString;

import java.util.*;

@ToString
public class PathBuilderImpl<N> extends AbstractMutableRestrictionContainer<N> implements PathBuilder<N> {
    private NodeID root;
    private final List<Graph.EdgeEndpointPair> edges = new ArrayList<>();
    private final Map<NodeID, N> nodeData = new HashMap<>();

    public PathBuilderImpl() {
        addRestriction(new NoMultiEdgesRestriction<>());
        addRestriction(new DirectedRestriction<>());
        addRestriction(new MaxDegreeRestriction<>(1));
    }

    @Override
    public void addStartNode(NodeID node, N data) {
        if(root != null) throw new RuntimeException("cannot have multiple roots");

        root = node;
        nodeData.put(node, data);
    }

    @Override
    public void nextNode(NodeID node, N data, EdgeID edgeID) {
        if(root == null) throw new RuntimeException("Must add root node first.");

        edges.add(new Graph.EdgeEndpointPair(edgeID, node));
        nodeData.put(node, data);
    }

    @Override
    public Graph.NodeDescriptor<N> removeLast() {
        if(root == null) throw new NoSuchElementException();

        if(edges.isEmpty()) {
            NodeID temp = root;
            root = null;
            return new Graph.NodeDescriptor<>(temp, nodeData.remove(temp));
        }

        NodeID removed = edges.removeLast().getEndpoint();
        return new Graph.NodeDescriptor<>(removed, nodeData.remove(removed));
    }

    @Override
    public PathGraph<N> build() {
        ExplicitIdsGraphBuilder<N> builder = new ExplicitIdsGraphBuilderImpl<>();
        builder.addAllRestrictions(getRestrictions());

        NodeID prev = root;
        builder.addNode(root, nodeData.get(root));
        for(Graph.EdgeEndpointPair edge : edges) {
            builder.addNode(edge.getEndpoint(), nodeData.get(edge.getEndpoint()));
            builder.addDirEdge(prev, edge.getEndpoint(), edge.getEdge());
            prev = edge.getEndpoint();
        }

        Graph<N> graph = builder.build();

        return new PathGraphImpl<>(getRestrictions(), graph.getAdjacencyList(), graph.getNodeDataMap(), root, edges.getLast().getEndpoint());
    }
}
