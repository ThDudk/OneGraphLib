package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.LinkedList;

/// Iterates through a graph on a first come first serve basis.
///
/// In other words, it will iterate through a path until it no longer can, then backtrack to take the next available path.
public class DepthFirstIterator<N> extends AbstractQueueGraphIterator<N> {
    public DepthFirstIterator(Graph<N> graph, N root) {
        super(new LinkedList<>(), graph, root);
    }
}
