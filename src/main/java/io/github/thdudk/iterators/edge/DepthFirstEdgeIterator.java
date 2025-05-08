package io.github.thdudk.iterators.edge;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.node.DepthFirstIterator;

public class DepthFirstEdgeIterator extends AbstractQueueGraphEdgeIterator {
    public DepthFirstEdgeIterator(Graph<?> graph, NodeID root) {
        super(new DepthFirstIterator.LIFOQueue(), graph, root);
    }
}
