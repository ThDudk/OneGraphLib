package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.LinkedList;

public class DepthFirstIterator<N> extends AbstractQueueGraphIterator<N> {
    public DepthFirstIterator(Graph<N> graph, N root) {
        super(new LinkedList<>(), graph, root);
    }
}
