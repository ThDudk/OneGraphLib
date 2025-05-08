package io.github.thdudk.restrictions;

import io.github.thdudk.GraphUtils;
import io.github.thdudk.graphs.unweighted.Graph;

public class WeaklyConnectedGraphRestriction<N> implements GraphRestriction<N> {
    @Override
    public boolean isSatisfied(Graph<N> graph) {
        Graph<N> connectedSubgraph = GraphUtils.getWeaklyConnectedSubgraph(graph, graph.getNodes().stream().findAny().orElseThrow());
        return connectedSubgraph.getNodes().size() == graph.getNodes().size();
    }
}
