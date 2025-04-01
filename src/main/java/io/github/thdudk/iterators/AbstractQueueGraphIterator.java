package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

/// Iterates through a graph based on the offer order of a queue. This allows various types of queues, such as LIFO and FIFO to be used.
///
/// This iterator will only call {@link Queue#offer(Object)}, {@link Queue#poll()} and {@link Queue#peek()}
@RequiredArgsConstructor
public abstract class AbstractQueueGraphIterator implements GraphIterator {
    public record NodeParentPair(NodeID node, NodeID parent) {}

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

    /// removes all visited nodes from the front of the queue
    private void removeVisitedFrontNodes() {
        while(!queue.isEmpty() && visited.contains(queue.peek().node)) {
            queue.poll();
        }
    }

    @Override
    public NodeID next() {
        removeVisitedFrontNodes(); // technically not needed as hasNext() also calls this
        if(!hasNext()) throw new NoSuchElementException();

        NodeParentPair pair = queue.poll();
        assert pair != null; // to satisfy the compiler
        prevParent = pair.parent; // store the parent for getParent()
        visited.add(pair.node); // mark the current node as visited

        // add all unvisited neighbours to the queue
        for(NodeID neighbour : graph.getNeighbours(pair.node)) {
            if(visited.contains(neighbour)) continue;

            queue.offer(new NodeParentPair(neighbour, pair.node));
        }

        return pair.node;
    }
}
