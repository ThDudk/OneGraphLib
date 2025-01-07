package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.*;

public class BreadthFirstIterator<N> implements Iterator<N> {
    private final Graph<N> graph;
    private final Queue<N> queue = new LinkedList<>();
    private final Set<N> visited = new HashSet<>();

    public BreadthFirstIterator(Graph<N> graph, N root) {
        this.graph = graph;
        queue.add(root);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public N next() {
        N curr = queue.poll();
        visited.add(curr);

        // add all unvisited neighbors of curr to the stack
        queue.addAll(graph.getOutNeighbours(curr)
            .stream().filter(a -> !visited.contains(a))
            .toList());

        return curr;
    }
}
