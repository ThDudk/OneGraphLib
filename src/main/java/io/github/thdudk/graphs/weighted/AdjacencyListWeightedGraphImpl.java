package io.github.thdudk.graphs.weighted;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.graphs.GraphValidator;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdjacencyListWeightedGraphImpl<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements WeightedGraph<N, E> {
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
    public Set<E> getEdgesBetween(N start, N end) {
        return adjacencyList.get(start).stream()
            .filter(a -> a.getEndpoint().equals(end))
            .map(EdgeEndpointPair::getEdge)
            .collect(Collectors.toUnmodifiableSet());
    }
}
