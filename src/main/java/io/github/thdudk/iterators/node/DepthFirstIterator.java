package io.github.thdudk.iterators.node;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.NodeParentPair;

import java.util.LinkedList;

/// Iterates through a graph on a first come, first served basis (LIFO).
///
/// In other words, it will iterate through a path until it no longer can, then backtrack to take the next available path.
public class DepthFirstIterator extends AbstractQueueGraphIterator {
    public static class LIFOQueue<T> extends LinkedList<T> {
        @Override
        public T poll() {
            return pollLast();
        }
        @Override
        public T peek() {
            return peekLast();
        }
    }

    public DepthFirstIterator(Graph<?> graph, NodeID root) {
        super(new LIFOQueue<>(), graph, root);
    }
}
