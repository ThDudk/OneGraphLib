package io.github.thdudk.graphs.weighted;

import com.fasterxml.jackson.annotation.JsonValue;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.iterators.DijkstraIterator;
import lombok.Value;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.github.thdudk.graphs.GraphValidator.requireContained;

/**
 * Representation of the Graph data structure with edges containing data.
 * @param <N> Type of the nodes contained in the graph
 * @param <E> Type of the edges contained in the graph
 */
public interface WeightedGraph<N, E> extends Graph<N> {
    /**
     * Pair of an edge and it's endpoint
     * @param <N> Type of the Endpoint
     * @param <E> Type of the Edge
     */
    @Value
    class EdgeEndpointPair<N, E> {
        E edge;
        N endpoint;
    }

    /**
     * @param root The node to get all the edges extending from
     * @return A set of pairs containing the edges extending from root and their corresponding endpoints
     * @throws IllegalArgumentException If root is not contained in this
     */
    Set<EdgeEndpointPair<N, E>> getEdges(N root);
    @JsonValue
    default Map<N, Set<EdgeEndpointPair<N, E>>> getWeightedAdjacencyList() {
        Map<N, Set<EdgeEndpointPair<N, E>>> map = new HashMap<>();
        for(N node : getNodes()) map.put(node, getEdges(node));
        return map;
    }
    /**
     * Any true call to this function does not guarantee that the inverse call is true as well.
     * <p>If hasEdge(1, e, 2) is true, hasEdge(2, e, 1) may not be.
     * @param root Root node
     * @param edge Edge to check
     * @param endpoint endpoint of edge
     * @return true if edge connects root to endpoint
     * @throws IllegalArgumentException If root or endpoint are not contained in this
     */
    default boolean hasEdge(N root, E edge, N endpoint) {
        requireContained(List.of(root, endpoint), this);
        return getEdges(root).contains(new EdgeEndpointPair<>(edge, endpoint));
    }

    // TODO add a shortest path algorithm that supports negative weights
    /**
     * Finds the shortest path from start to end taking weights into account using Dijkstra's algorithm.
     * <p>Note: Dijkstra's algorithm does NOT support weights <= 0 and these cases will often result in an infinite loop.
     * There are cases, however, where a negative weight will not result in a StackOverflowError.
     * <p>if there is a possibility of a negative weight appearing, an algorithm that supports negative weights should be used instead.
     * @param start starting node
     * @param end end node
     * @param pathCompressor Function that converts a path's weight and a new edge into a new weight. Must handle null values.
     * @return The shortest path from start to end
     * @throws StackOverflowError A negative cycle is formed.
     * @throws IllegalArgumentException If start or end are not contained in this
     * @see #shortestPath
     */
    default <C extends Comparable<C>> Optional<List<EdgeEndpointPair<N, E>>> dijkstras(N start, N end, BiFunction<C, EdgeEndpointPair<N, E>, C> pathCompressor) {
        // validates the graph contains start and end
        requireContained(List.of(start, end), this);

        DijkstraIterator<N, E, C> iterator = new DijkstraIterator<>(this, start, pathCompressor, C::compareTo);

        Map<N, Pair<E, N>> paths = new HashMap<>();
        paths.put(start, Pair.of(null, null));

        // get the shortest distance paths
        // cannot have an early exit because an early exit may result in
        while (iterator.hasNext()) {
            N curr = iterator.next();
            paths.put(curr, iterator.getPrev(curr));
        }

        // there is no path
        if(!paths.containsKey(end))
            return Optional.empty();

        // retrace the path
        List<EdgeEndpointPair<N, E>> path = new LinkedList<>();
        N prev = end;
        while(true) { // while start has not been encountered
            Pair<E, N> pair = paths.get(prev);
            if(pair == null) break;

            path.addFirst(new EdgeEndpointPair<>(pair.getLeft(), prev));
            prev = pair.getRight();
        }
        path.addFirst(new EdgeEndpointPair<>(null, start));

        return Optional.of(path);
    }
}