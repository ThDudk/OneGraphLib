package io.github.thdudk.iterators.node;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphIterator;
import io.github.thdudk.iterators.NodeParentPair;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/// Iterates through a graph based on the offer order of a queue. This allows various types of queues, such as LIFO and FIFO, to be used.
///
/// This iterator will only call {@link Queue#offer(Object)}, {@link Queue#poll()} and {@link Queue#peek()}
@RequiredArgsConstructor
public abstract class AbstractQueueGraphIterator implements GraphIterator {
    private final Queue<NodeParentPair> queue;
    private final Set<NodeID> visited = new HashSet<>();
    private NodeID prevParent = null; // parent of the previously polled node
    private final Graph<?> graph;

    public AbstractQueueGraphIterator(Queue<NodeParentPair> queue, Graph<?> graph, NodeID root) {
        this(queue, graph);
        queue.offer(new NodeParentPair(root, null));
    }

    @Override
    public NodeID getParent() {
        return prevParent;
    }
    @Override
    public boolean hasNext() {
        removeVisitedFrontNodes();
        return !queue.isEmpty();
    }
    @Override
    public NodeID next() {
        removeVisitedFrontNodes(); // technically not needed as hasNext() also calls this
        if(!hasNext()) throw new NoSuchElementException();

        NodeParentPair pair = queue.poll();
        assert pair != null; // to satisfy the compiler
        prevParent = pair.parent(); // store the parent for getParent()
        markVisited(pair);

        // add all unvisited neighbours to the queue
        for(NodeID neighbour : graph.getNeighbours(pair.node())) {
            if(visited(new NodeParentPair(neighbour, pair.node()))) continue;

            queue.offer(new NodeParentPair(neighbour, pair.node()));
        }

        return pair.node();
    }
    
    /// removes all visited nodes from the front of the queue
    protected void removeVisitedFrontNodes() {
        while(!queue.isEmpty() && visited(queue.peek())) {
            queue.poll();
        }
    }
    protected void markVisited(NodeParentPair pair) {
        visited.add(pair.node());
    }
    protected boolean visited(NodeParentPair pair) {
        return visited.contains(pair.node());
    }
}
