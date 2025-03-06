package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.LinkedList;
import java.util.List;

/// Graph iterator that iterates through nodes by increasing distance.
///
/// In other words, it will explore all nodes 1 edge away, then 2 edges away, and so on.
public class BreadthFirstIterator<N> extends AbstractQueueGraphIterator<N> {
    private static class LIFOQueue<N> extends LinkedList<N> {
        @Override
        public boolean offer(N n) {
            return super.offerFirst(n);
        }
    }

    public BreadthFirstIterator(Graph<N> graph, N root) {
        super(new LIFOQueue<>(), graph, root);
    }
}
