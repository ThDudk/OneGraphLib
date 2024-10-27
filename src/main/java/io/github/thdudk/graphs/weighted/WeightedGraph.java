package io.github.thdudk.graphs.weighted;

import static io.github.thdudk.graphs.GraphValidator.requireContained;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.Value;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.*;
import java.util.function.Function;

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
        return getEdges(root).contains(new EdgeEndpointPair<>(edge, endpoint));
    }

    // TODO add a shortest path algorithm that supports negative weights
    /**
     * Finds the shortest path from start to end taking weights into account using Dijkstra's algorithm.
     * <p>Note: Dijkstra's algorithm does NOT support negative weights and having a negative weight may result in stack overflow.
     * There are cases, however, where a negative weight will not result in a StackOverflowError.
     * <p>if there is a possibility of a negative weight appearing, an algorithm that supports negative weights should be used instead.
     * @param start starting node
     * @param end end node
     * @param weightFunction Function that takes in an EdgeEndpointPair and returns a Numerical weight
     * @return The shortest path from start to end
     * @throws StackOverflowError A negative cycle is formed.
     * @throws IllegalArgumentException If start or end are not contained in this
     * @see #shortestPath
     */
    default Optional<Pair<List<N>, Double>> dijkstras(N start, N end, Function<EdgeEndpointPair<N, E>, Double> weightFunction) {
        // validates the graph contains start and end
        requireContained(List.of(start, end), this);

        // algorithms starts here
        // (node, (distance, edge to, root))
        Map<N, Triple<Double, E, N>> distances = new HashMap<>();
        PriorityQueue<N> queue = new PriorityQueue<>(Comparator.comparing((N e) -> distances.get(e).getLeft()));
        queue.offer(start);
        distances.put(start, Triple.of(0.0, null, null));

        while(true) {
            if(queue.isEmpty()) return Optional.empty();
            N curr = queue.poll();

            if(curr.equals(end)) break;

            for(EdgeEndpointPair<N, E> edge : getEdges(curr)) {
                double dist = distances.get(curr).getLeft() + weightFunction.apply(edge);
                if(distances.containsKey(edge.getEndpoint()) && distances.get(edge.getEndpoint()).getLeft() <= dist) continue;

                distances.put(edge.getEndpoint(), Triple.of(dist, edge.getEdge(), curr));
                queue.offer(edge.getEndpoint());
            }
        }

        List<N> path = new LinkedList<>();
        path.add(end);
        while(!path.get(0).equals(start)) {
            path.add(0, distances.get(path.get(0)).getRight());
        }

        return Optional.of(Pair.of(path, distances.get(end).getLeft()));
    }
}