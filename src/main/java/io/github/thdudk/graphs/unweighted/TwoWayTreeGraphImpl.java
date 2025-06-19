package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.GraphUtils;
import io.github.thdudk.graphs.TwoWayDataContainer;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphNodeIterator;
import io.github.thdudk.iterators.edge.BreadthFirstEdgeIterator;
import io.github.thdudk.iterators.node.BreadthFirstIterator;
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
    private final Map<NodeID, NodeID> parentMap; // for fast parent lookups
    private final Map<NodeID, Integer> depthMap; // for fast node depth lookups

    public TwoWayTreeGraphImpl(Collection<GraphRestriction<N>> restrictions, NodeID root, Map<NodeID, Collection<EdgeEndpointPair>> adjacencyList, Map<NodeID, N> data) {
        super(restrictions, adjacencyList, data);

        this.root = root;

        // construct parent map and depth map
        this.parentMap = new HashMap<>();
        this.depthMap = new HashMap<>();
        parentMap.put(root, null);
        depthMap.put(root, 0);

        GraphNodeIterator iterator = new BreadthFirstIterator(this, root);
        iterator.next(); // ignore the root

        while(iterator.hasNext()) {
            NodeID next = iterator.next();
            parentMap.put(next, iterator.getParent());
            depthMap.put(next, depthMap.get(iterator.getParent()) + 1);
        }
    }

    /// @return the parent of `node` or null if `node` is the root
    @Override
    public NodeID getParent(NodeID node) {
        return parentMap.get(node);
    }

    /// @return all children of node or an empty collection if there are none
    @Override
    public Collection<NodeID> getChildren(NodeID node) {
        return getNeighbours(node);
    }

    /// @return the depth of `node` aka it's distance from the root. Calling this on the root node will return 0
    @Override
    public int getDepth(NodeID node) {
        return depthMap.get(node);
    }
}
