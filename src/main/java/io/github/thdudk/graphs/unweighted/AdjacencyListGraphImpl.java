package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.GraphValidator;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdjacencyListGraphImpl<N> extends AbstractRestrictedGraph<N> implements Graph<N> {
    private final Map<N, Set<N>> adjacencyList;

    @JsonCreator
    public AdjacencyListGraphImpl(
        @JsonProperty("restrictions") Collection<GraphRestriction<N>> restrictions,
        @JsonProperty("unweightedAdjacencyList") Map<N, Set<N>> adjacencyList
    ) {
        this.adjacencyList = adjacencyList;

        for(GraphRestriction<N> restriction : restrictions) {
            addRestriction(restriction);
        }
    }

    @Override
    public Set<N> getNodes() {
        return Set.copyOf(adjacencyList.keySet());
    }
    @Override
    public Set<N> getNeighbours(N root) {
        GraphValidator.requireContained(List.of(root), this);
        return Set.copyOf(adjacencyList.get(root));
    }
}
