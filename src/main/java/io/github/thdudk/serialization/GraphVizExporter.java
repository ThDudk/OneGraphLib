package io.github.thdudk.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.DistinctDataRestriction;
import io.github.thdudk.restrictions.UndirectedRestriction;

public class GraphVizExporter {

    public <T> String serializeGraph(Graph<T> graph) throws JsonProcessingException {
        StringBuilder serializationBuilder = new StringBuilder();

        boolean hasDistinctData = graph.hasRestriction(new DistinctDataRestriction<>());

        if(graph.hasRestriction(new UndirectedRestriction<>())) {
            serializationBuilder.append("strict graph {\n");
        } else {
            serializationBuilder.append("strict digraph {\n");
        }

        for(NodeID node : graph.getNodes()) {
            serializationBuilder
                .append(serializeNode(node, graph))
                .append(";\n");
        }

        for(NodeID node : graph.getNodes()) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                for(EdgeID edge : graph.getEdgesBetween(node, neighbour)) {
                    // add edge start and end
                    serializationBuilder
                        .append(serializeNode(node, graph))
                        .append("->") // edge operator
                        .append(serializeNode(neighbour, graph))
                        .append(";\n");
                }
            }
        }

        serializationBuilder.append('}');

        return serializationBuilder.toString();
    }

    private <T> String serializeNode(NodeID id, Graph<T> graph) {
        if(graph.hasRestriction(new DistinctDataRestriction<>())) {
            return graph.getNodeData(id).toString();
        } else {
            return '"' + id.toString() + '{' + graph.getNodeData(id) + '}' + '"';
        }
    }

    public static <T> String serialize(Graph<T> graph) throws JsonProcessingException {
        return new GraphVizExporter().serializeGraph(graph);
    }
}
