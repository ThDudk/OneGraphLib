package io.github.thdudk.iterators.edge;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.node.AbstractQueueGraphIterator;
import io.github.thdudk.iterators.NodeParentPair;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

public abstract class AbstractQueueGraphEdgeIterator extends AbstractQueueGraphIterator {
    private final Set<NodeParentPair> visited = new HashSet<>();

    public AbstractQueueGraphEdgeIterator(Queue<NodeParentPair> queue, Graph<?> graph) {
        super(queue, graph);
    }
    public AbstractQueueGraphEdgeIterator(Queue<NodeParentPair> queue, Graph<?> graph, NodeID root) {
        super(queue, graph, root);
    }

    @Override
    protected void markVisited(NodeParentPair pair) {
        visited.add(pair);
    }
    @Override
    protected boolean visited(NodeParentPair pair) {
        return visited.contains(pair);
    }
}
