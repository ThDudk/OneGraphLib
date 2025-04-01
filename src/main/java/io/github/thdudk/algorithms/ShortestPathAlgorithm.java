package io.github.thdudk.algorithms;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.ids.NodeID;

import java.util.Optional;

public interface ShortestPathAlgorithm {
    /// @return the shortest path from `start` to `end` or an empty optional if there is no path.
    <N> Optional<PathGraph<N>> shortestPath(Graph<N> graph, NodeID start, NodeID end);
}
