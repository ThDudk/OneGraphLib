package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.thdudk.graphs.GraphValidator;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode
@ToString
public class AdjacencyListGraphImpl<N> implements Graph<N> {
    private final Map<N, Set<N>> adjacencyList;

    public AdjacencyListGraphImpl(@JsonProperty("adjacencyList") Map<N, Set<N>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    @Override
    public Set<N> getNodes() {
        return Set.copyOf(adjacencyList.keySet());
    }
    @Override
    public Set<N> getOutNeighbours(N root) {
        GraphValidator.requireContained(List.of(root), this);
        return Set.copyOf(adjacencyList.get(root));
    }
}
