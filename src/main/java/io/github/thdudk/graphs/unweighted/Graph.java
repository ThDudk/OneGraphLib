package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.thdudk.construction.builders.GraphBuilder;
import io.github.thdudk.iterators.BreadthFirstIterator;
import io.github.thdudk.iterators.DepthFirstIterator;
import lombok.val;

import java.util.*;
import java.util.function.Predicate;

import static io.github.thdudk.graphs.GraphValidator.requireContained;

/**
 * Representation of an unweighted graph.
 * @param <N> The Type of the nodes contained in the graph
 */
public interface Graph<N> {
    /**
     * @return a set of all nodes in this
     */
    @JsonIgnore
    Set<N> getNodes();
    /**
     * @param root The root node to get the neighbors of
     * @return all out neighbors of root
     * @throws IllegalArgumentException If root is not contained in this
     */
    Set<N> getOutNeighbours(N root);
    default Set<N> getInNeighbours(N root) {
        Set<N> inNeighbours = new HashSet<>();

        // find all nodes with out-neighbours towards root (aka root's in neighbours)
        for(N node : getNodes()) {
            if(node.equals(root)) continue;

            for(N neighbour : getOutNeighbours(node)) {
                if(neighbour.equals(root)) {
                    inNeighbours.add(node);
                    break;
                }
            }
        }
        return inNeighbours;
    }
    default Set<N> getNeighbours(N root) {
        Set<N> neighbors = new HashSet<>(getOutNeighbours(root));
        neighbors.addAll(getInNeighbours(root));
        return neighbors;
    }

    default int getOutDegree(N root) {
        return getOutNeighbours(root).size();
    }
    default int getInDegree(N root) {
        return getInNeighbours(root).size();
    }
    default int getDegree(N root) {
        return getNeighbours(root).size();
    }

    default Map<N, Set<N>> getUnweightedAdjacencyList() {
        Map<N, Set<N>> map = new HashMap<>();
        for(N node : getNodes()) map.put(node, getOutNeighbours(node));
        return map;
    }

    default Graph<N> filter(Predicate<N> predicate) {
        GraphBuilder<N> builder = GraphBuilder.of(this);
        // remove all nodes that do not meet the predicate
        for(N node : getNodes())
            if(!predicate.test(node))
                builder.removeNode(node);

        return builder.build();
    }

    /**
     * @param node Node to check
     * @return true if node is contained in this
     */
    default boolean hasNode(N node) {
        return getNodes().contains(node);
    }
    default Optional<N> findNode(Predicate<N> predicate) {
        for(N node : getNodes()) {
            if(predicate.test(node)) return Optional.of(node);
        }
        return Optional.empty();
    }

    // Algorithms
    /**
     * Finds the shortest path from start to end using the Breadth First Search algorithm.
     * <p>A path with the least number of edge traversals possible will always be returned, but there are likely other possible paths of equal distance.
     * <p>Note: It is NOT guaranteed that any two calls will result in the same path. Two calls to the function on the same object may and possibly will result in two distinct, shortest distance, paths being returned.
     * @param start starting node
     * @param end end node
     * @return the shortest paths from start to end, or an empty optional if no such path exists
     * @throws IllegalArgumentException If start or end are not contained in this
     */
    default Optional<List<N>> shortestPath(N start, N end) {
        // validates the graph contains start and end
        requireContained(List.of(start, end), this);

        BreadthFirstIterator<N> iterator = new BreadthFirstIterator<>(this, start);
        Map<N, N> shortestPathTo = new HashMap<>();
        shortestPathTo.put(start, null);
        while (!shortestPathTo.containsKey(end)) {
            if (!iterator.hasNext()) return Optional.empty();

            N curr = iterator.next();
            for (N neighbour : getOutNeighbours(curr))
                if (!shortestPathTo.containsKey(neighbour))
                    shortestPathTo.put(neighbour, curr);
        }

        // retrace the path
        List<N> path = new LinkedList<>();
        path.add(end);
        System.out.println(shortestPathTo);

        N curr = end;
        while(true) {
            if(shortestPathTo.get(curr) == null) return Optional.of(path);
            curr = shortestPathTo.get(curr);
            path.addFirst(curr);
        }
    }

    default Graph<N> getDisconnectedGraph(N anchor) {
        Set<N> visited = new HashSet<>();

        val iterator = new DepthFirstIterator<>(this, anchor);

        // find all nodes in the disconnected graph
        while(iterator.hasNext())
            visited.add(iterator.next());

        // remove all nodes not in the disconnected graph
        GraphBuilder<N> builder = GraphBuilder.of(this);
        for(N node : getNodes())
            if(!visited.contains(node))
                builder.removeNode(node);

        return builder.build();
    }
    /// note includes graphs that seem disconnected from within in directed graphs.
    default Set<? extends Graph<N>> getAllDisconnectedGraphs() {
        Set<Graph<N>> disconnectedGraphs = new HashSet<>();
        Set<N> visited = new HashSet<>();

        for(N node : getNodes()) {
            if(visited.contains(node)) continue;

            Graph<N> disconnectedGraph = getDisconnectedGraph(node);
            disconnectedGraphs.add(disconnectedGraph);

            // add all visited nodes
            visited.addAll(disconnectedGraph.getNodes());
        }
        return disconnectedGraphs;
    }

    /**
     * @param root The root node
     * @return the longest path from {@code root} to any other node in this
     */
    default Set<List<N>> allFullyExtendedPathsFrom(N root) {
        requireContained(List.of(root), this);
        return allFullyExtendedPathsFrom(root, new ArrayList<>(), new HashSet<>());
    }
    private Set<List<N>> allFullyExtendedPathsFrom(N curr, List<N> currPath, Set<N> visited) {
        Set<N> newVisited = new HashSet<>(visited);
        List<N> newCurrPath = new ArrayList<>(currPath);

        newVisited.add(curr);
        newCurrPath.add(curr);

        Set<List<N>> allPaths = new HashSet<>();

        List<N> validNeighbours = getOutNeighbours(curr).stream().filter(a -> !newVisited.contains(a)).toList();
        if(validNeighbours.isEmpty()) return Set.of(newCurrPath); // this is the farthest node in this path from the root

        for(N neighbour : validNeighbours)
            allPaths.addAll(allFullyExtendedPathsFrom(neighbour, newCurrPath, newVisited));

        // return the longest path
        return allPaths;
    }
}