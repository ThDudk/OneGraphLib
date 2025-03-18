package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.LinkedList;

/// Iterates through a graph on a first come, first served basis (LIFO).
///
/// In other words, it will iterate through a path until it no longer can, then backtrack to take the next available path.
public class DepthFirstIterator<N> extends AbstractQueueGraphIterator<N> {
    private static class LIFOQueue<N> extends LinkedList<N> {
        @Override
        public N poll() {
            return pollLast();
        }
        @Override
        public N peek() {
            return peekLast();
        }
    }

    public DepthFirstIterator(Graph<N> graph, N root) {
        super(new LIFOQueue<>(), graph, root);
    }
}
