package io.github.thdudk.builders.weighted;

import io.github.thdudk.AbstractWeightedRestrictedGraph;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.weighted.AdjacencyListWeightedGraphImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class ExplicitIdsWeightedGraphBuilderImpl<N, E> extends AbstractWeightedRestrictedGraph<N, E> implements ExplicitIdsWeightedGraphBuilder<N, E> {
    private final ExplicitIdsGraphBuilder<N> graphBuilder = new ExplicitIdsGraphBuilderImpl<>();
    private final Map<EdgeID, E> edgeData = new HashMap<>();

    public ExplicitIdsWeightedGraphBuilderImpl(WeightedGraph<N, E> graph) {
        for(NodeID node : graph.getNodes()) {
            addNode(node, graph.getNodeData(node));
        }

        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                for(EdgeID edge : graph.getEdgesBetween(node, neighbour)) {
                    addDirEdge(node, neighbour, edge, graph.getEdgeData(edge));
                }
            }
        }
    }

    @Override
    public void addNode(NodeID id, N data) {
        graphBuilder.addNode(id, data);
    }

    @Override
    public void addDirEdge(NodeID start, NodeID end, EdgeID edgeID, E edgeData) {
        graphBuilder.addDirEdge(start, end, edgeID);
        this.edgeData.put(edgeID, edgeData);
    }

    @Override
    public WeightedGraph<N, E> build() {
        Graph<N> graph = graphBuilder.build();
        return new AdjacencyListWeightedGraphImpl<>(getRestrictions(), getEdgeRestrictions(), graph.getAdjacencyList(), graph.getNodeDataMap(), edgeData);
    }
}
