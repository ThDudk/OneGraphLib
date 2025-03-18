package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.LinkedList;

/// Graph iterator that iterates through nodes by increasing distance (FIFO).
///
/// In other words, it will explore all nodes 1 edge away, then 2 edges away, and so on.
public class BreadthFirstIterator<N> extends AbstractQueueGraphIterator<N> {
    public BreadthFirstIterator(Graph<N> graph, N root) {
        super(new LinkedList<>(), graph, root);
    }
}
