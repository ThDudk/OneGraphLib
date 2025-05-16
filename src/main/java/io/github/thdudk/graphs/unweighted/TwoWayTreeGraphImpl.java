package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphIterator;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.restrictions.*;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TwoWayTreeGraphImpl<N> extends AdjacencyListGraphImpl<N> implements TreeGraph<N> {
    @Getter
    private final NodeID root;
    private final Map<NodeID, NodeID> parentMap;

    public TwoWayTreeGraphImpl(Collection<GraphRestriction<N>> restrictions, NodeID root, Map<NodeID, Set<EdgeEndpointPair>> adjacencyList, Map<NodeID, N> data) {
        super(restrictions, adjacencyList, data);
        addRestriction(new DirectedRestriction<>());
        addRestriction(new MaxInDegreeRestriction<>(1));
        addRestriction(new NoMultiEdgesRestriction<>());
        addRestriction(new WeaklyConnectedGraphRestriction<>());

        this.root = root;

        this.parentMap = new HashMap<>();
        GraphIterator iterator = new DepthFirstIterator(this, root);
        while(iterator.hasNext()) {
            parentMap.put(iterator.next(), iterator.getParent());
        }

        throwIfRestrictionsNotSatisfied();
    }

    @Override
    public NodeID getParent(NodeID node) {
        return parentMap.get(node);
    }

    @Override
    public Collection<NodeID> getChildren(NodeID node) {
        return getNeighbours(node);
    }
}
