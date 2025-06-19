package io.github.thdudk.builders.trees;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.TreeGraph;
import io.github.thdudk.graphs.unweighted.TwoWayTreeGraphImpl;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.*;
import io.github.thdudk.restrictions.restriction_containers.AbstractMutableRestrictionContainer;
import io.github.thdudk.restrictions.restriction_containers.AbstractRestrictionContainer;

import java.util.*;
import java.util.stream.Collectors;

public class TreeBuilderImpl<N> extends AbstractMutableRestrictionContainer<N> implements TreeBuilder<N> {
    private final ExplicitIdsGraphBuilder<N> builder = new ExplicitIdsGraphBuilderImpl<>();

    private NodeID root = null;

    public TreeBuilderImpl() {
        this(List.of());
    }

    public TreeBuilderImpl(Collection<GraphRestriction<N>> graphRestrictions) {
        super(graphRestrictions);
        addRestriction(new DirectedRestriction<>());
        addRestriction(new MaxInDegreeRestriction<>(1));
        addRestriction(new NoMultiEdgesRestriction<>());
        addRestriction(new WeaklyConnectedGraphRestriction<>());
    }

    @Override
    public void addRoot(NodeID id, N data) {
        if(root != null) throw new IllegalArgumentException("Cannot assign multiple roots");

        builder.addNode(id, data);
        root = id;
    }

    @Override
    public void addChild(NodeID parent, NodeID child, N childData, EdgeID edgeID) {
        if(root == null) throw new IllegalArgumentException("Must assign root before children");

        builder.addNode(child, childData);
        builder.addDirEdge(parent, child, edgeID);
    }

    @Override
    public TreeGraph<N> build() {
        builder.addAllRestrictions(getRestrictions());
        Graph<N> graph = builder.build();

        // convert from Map<NodeID, Collection<Graph.EdgeEndpointPair>> to Map<NodeID, Set<Graph.EdgeEndpointPair>>
        Map<NodeID, Collection<Graph.EdgeEndpointPair>> adjacencyList = graph.getAdjacencyList()
            .entrySet()
            .stream()
            .map(a -> Map.entry(a.getKey(), new HashSet<>(a.getValue())))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new TwoWayTreeGraphImpl<>(getRestrictions(), root, adjacencyList, graph.getNodeDataMap());
    }
}
