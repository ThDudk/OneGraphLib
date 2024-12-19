package io.github.thdudk.iterators;

import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;
import lombok.NonNull;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.function.BiFunction;

public class DijkstraIterator<N, E, C> implements Iterator<N> {
    private final WeightedGraph<N, E> graph;
    private final BiFunction<C, EdgeEndpointPair<N, E>, C> pathCompressor;
    private final Comparator<C> comparator;
    // (endpoint, (weight, from))
    Map<N, Triple<C, E, N>> weights = new HashMap<>();
    private final PriorityQueue<N> queue;
    private N curr;

    public DijkstraIterator(@NonNull WeightedGraph<N, E> graph, @NonNull N root, @NonNull BiFunction<C, EdgeEndpointPair<N, E>, C> pathCompressor, @NonNull Comparator<C> comparator) {
        this.graph = graph;
        this.pathCompressor = pathCompressor;

        // setup queue
        queue = new PriorityQueue<>((a, b) -> comparator.compare(weights.get(a).getLeft(), weights.get(b).getLeft()));
        queue.add(root);
        this.comparator = comparator;

        // set root to have no path
        weights.put(root, null);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public N next() {
        // take the next shortest path
        // look at all edges extending that lead to another node with a shorter path than that node's current shortest path
        if(queue.isEmpty()) return null;

        curr = queue.poll();

        for(EdgeEndpointPair<N, E> pair : graph.getEdges(curr)) {
            N endpoint = pair.getEndpoint();
            E edge = pair.getEdge();

            // add path to unvisited nodes
            if(!weights.containsKey(endpoint)) {
                weights.put(endpoint, Triple.of(pathCompressor.apply(null, pair), edge, curr));
                queue.add(endpoint);
                continue;
            }

            // ignore paths to the root
            if(weights.get(endpoint) == null)
                continue;

            // add path if the current distance is less than the previously shortest distance to the endpoint
            C dist = pathCompressor.apply(weights.get(curr).getLeft(), pair);
            if(comparator.compare(weights.get(endpoint).getLeft(), dist) > 0) {
                weights.put(endpoint, Triple.of(dist, edge, curr));
                queue.add(endpoint);
            }
        }

        return curr;
    }
    public Pair<E, N> getPrev(N node) {
        if(weights.get(node) == null) return null;

        return Pair.of(weights.get(node).getMiddle(), weights.get(node).getRight());
    }
}
