package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

@RequiredArgsConstructor
public abstract class AbstractQueueGraphIterator<N> implements GraphIterator<N> {
    public record NodeParentPair<N>(N node, N parent) {}

    private final Queue<NodeParentPair<N>> queue;
    private final Set<N> visited = new HashSet<>();
    private N prevParent = null; // parent of the previously polled node
    private final Graph<N> graph;

    public AbstractQueueGraphIterator(Queue<NodeParentPair<N>> queue, Graph<N> graph, N root) {
        this(queue, graph);
        queue.offer(new NodeParentPair<>(root, null));
    }

    @Override
    public N getParent() {
        return prevParent;
    }

    @Override
    public boolean hasNext() {
        removeVisitedFrontNodes();
        return !queue.isEmpty();
    }

    private void removeVisitedFrontNodes() {
        while(!queue.isEmpty() && visited.contains(queue.peek().node)) {
            queue.poll();
        }
    }

    @Override
    public N next() {
        removeVisitedFrontNodes(); // technically not needed as hasNext() also calls this
        if(!hasNext()) return null;

        NodeParentPair<N> pair = queue.poll();
        prevParent = pair.parent; // store the parent for getParent()
        visited.add(pair.node); // mark the current node as visited

        // add all unvisited neighbours to the queue
        for(N neighbour : graph.getNeighbours(pair.node)) {
            if(visited.contains(neighbour)) continue;

            queue.offer(new NodeParentPair<>(neighbour, pair.node));
        }

        return pair.node;
    }
}
