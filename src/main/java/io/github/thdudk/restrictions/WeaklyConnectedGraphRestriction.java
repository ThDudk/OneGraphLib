package io.github.thdudk.restrictions;

import com.fasterxml.jackson.annotation.JsonTypeName;
import io.github.thdudk.GraphUtils;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
public class WeaklyConnectedGraphRestriction<N> implements GraphRestriction<N> {
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        Graph<N> connectedSubgraph = GraphUtils.getWeaklyConnectedSubgraph(graph, graph.getNodes().stream().findAny().orElseThrow());
        return connectedSubgraph.getNodes().size() == graph.getNodes().size();
    }
}
