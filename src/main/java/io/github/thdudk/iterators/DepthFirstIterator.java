package io.github.thdudk.iterators;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

public class DepthFirstIterator<N> implements Iterator<N> {
    private final Graph<N> graph;
    private final Stack<N> stack = new Stack<>();
    private final Set<N> visited = new HashSet<>();

    public DepthFirstIterator(Graph<N> graph, N root) {
        this.graph = graph;
        stack.add(root);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public N next() {
        N curr = stack.pop();
        visited.add(curr);

        // add all unvisited neighbors of curr to the stack
        stack.addAll(graph.getNeighbours(curr)
            .stream().filter(a -> !visited.contains(a))
            .collect(Collectors.toList()));

        return curr;
    }
}
