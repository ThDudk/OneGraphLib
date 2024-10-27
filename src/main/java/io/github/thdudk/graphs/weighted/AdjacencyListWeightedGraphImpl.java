package io.github.thdudk.graphs.weighted;

import io.github.thdudk.graphs.GraphValidator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AdjacencyListWeightedGraphImpl<N, E> implements WeightedGraph<N, E> {
    private final Map<N, Set<EdgeEndpointPair<N, E>>> adjacencyList;

    @Override
    public Set<N> getNodes() {
        return adjacencyList.keySet();
    }
    @Override
    public Set<N> getNeighbours(N root) {
        GraphValidator.requireContained(List.of(root), this);
        return adjacencyList.get(root).stream().map(EdgeEndpointPair::getEndpoint).collect(Collectors.toSet());
    }
    @Override
    public Set<EdgeEndpointPair<N, E>> getEdges(N root) {
        GraphValidator.requireContained(List.of(root), this);
        return adjacencyList.get(root);
    }
}
