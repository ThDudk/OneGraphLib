package io.github.thdudk;

import io.github.thdudk.algorithms.pathfinding.BreadthFirstShortestPathAlgorithm;
import io.github.thdudk.algorithms.DirectedToUndirectedGraphConverter;
import io.github.thdudk.algorithms.NodeMaskSubgraph;
import io.github.thdudk.algorithms.WeaklyConnectedSubgraph;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;
import java.util.Optional;

public abstract class GraphUtils {
    public static <N> Optional<PathGraph<N>> shortestPath(Graph<N> graph, NodeID start, NodeID end) {
        return new BreadthFirstShortestPathAlgorithm().shortestPath(graph, start, end);
    }
    public static <N> Graph<N> undirectedToDirectedGraph(Graph<N> directedGraph) {
        return new DirectedToUndirectedGraphConverter().convert(directedGraph);
    }
    public static <N> Graph<N> mask(Graph<N> graph, Collection<NodeID> containedNodes) {
        return new NodeMaskSubgraph().subgraph(graph, containedNodes);
    }
    public static <N> Graph<N> getWeaklyConnectedSubgraph(Graph<N> graph, NodeID anchor) {
        return new WeaklyConnectedSubgraph().subgraph(graph, anchor);
    }
}
