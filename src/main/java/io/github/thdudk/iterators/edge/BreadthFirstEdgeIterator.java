package io.github.thdudk.iterators.edge;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

import java.util.LinkedList;

public class BreadthFirstEdgeIterator extends AbstractQueueGraphEdgeIterator {
    public BreadthFirstEdgeIterator(Graph<?> graph, NodeID root) {
        super(new LinkedList<>(), graph, root);
    }
}
