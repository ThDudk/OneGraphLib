package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.GraphUtils;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphNodeIterator;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.restrictions.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@EqualsAndHashCode(callSuper = true)
@ToString
public class TwoWayTreeGraphImpl<N> extends AdjacencyListGraphImpl<N> implements TreeGraph<N> {
    @Getter
    private final NodeID root;
    private final Map<NodeID, NodeID> parentMap;

    public TwoWayTreeGraphImpl(Collection<GraphRestriction<N>> restrictions, NodeID root, Map<NodeID, Set<EdgeEndpointPair>> adjacencyList, Map<NodeID, N> data) {
        super(restrictions, adjacencyList, data);

        this.root = root;

        this.parentMap = new HashMap<>();
        GraphNodeIterator iterator = new DepthFirstIterator(this, root);
        while(iterator.hasNext()) {
            parentMap.put(iterator.next(), iterator.getParent());
        }
    }

    @Override
    public NodeID getParent(NodeID node) {
        return parentMap.get(node);
    }

    @Override
    public Collection<NodeID> getChildren(NodeID node) {
        return getNeighbours(node);
    }

    @Override
    public int getDepth(NodeID node) {
        return GraphUtils.shortestPath(this, root, node)
            .orElseThrow()
            .asList()
            .size();
    }
}
