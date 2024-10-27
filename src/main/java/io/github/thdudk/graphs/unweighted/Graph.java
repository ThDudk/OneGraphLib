package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.iterators.BreadthFirstIterator;

import java.util.*;

import static io.github.thdudk.graphs.GraphValidator.requireContained;

/**
 * Representation of an unweighted graph.
 * @param <N> The Type of the nodes contained in the graph
 */
public interface Graph<N> {
    /**
     * @return a set of all nodes in this
     */
    Set<N> getNodes();
    /**
     * @param root The root node to get the neighbors of
     * @return all out neighbors of root
     * @throws IllegalArgumentException If root is not contained in this
     */
    Set<N> getNeighbours(N root);

    /**
     * @param node Node to check
     * @return true if node is contained in this
     */
    default boolean hasNode(N node) {
        return getNodes().contains(node);
    }
    /**
     * @param root The root node
     * @param neighbor The possible neighbor
     * @return true if neighbor is an out neighbor from root
     * @throws IllegalArgumentException If root or neighbor are not contained in this
     */
    default boolean isNeighbor(N root, N neighbor) {
        requireContained(List.of(root, neighbor), this);
        return getNeighbours(root).contains(neighbor);
    }

    /**
     * Finds the shortest path from start to end using the Breadth First Search algorithm.
     * <p>A path with the least number of edge traversals possible will always be returned, but there are likely other possible paths of equal distance.
     * @param start starting node
     * @param end end node
     * @return the shortest paths from start to end, or an empty optional if no such path exists
     * @throws IllegalArgumentException If start or end are not contained in this
     */
    default Optional<List<N>> shortestPath(N start, N end) {
        // validates the graph contains start and end
        requireContained(List.of(start, end), this);

        // algorithms starts here
        BreadthFirstIterator<N> iterator = new BreadthFirstIterator<>(this, start);
        Map<N, N> shortestPathTo = new HashMap<>();
        N prev = null;
        while(prev == null || !prev.equals(end)) {
            if(!iterator.hasNext()) return Optional.empty();

            N curr = iterator.next();
            shortestPathTo.put(start, prev);
            prev = curr;
        }

        // retrace the path
        List<N> path = new LinkedList<>();
        path.add(end);

        N curr = end;
        while(true) {
            if(shortestPathTo.get(curr) == null) return Optional.of(path);
            curr = shortestPathTo.get(curr);
            path.addFirst(curr);
        }
    }
}