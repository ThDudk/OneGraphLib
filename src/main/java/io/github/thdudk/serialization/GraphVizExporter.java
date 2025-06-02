package io.github.thdudk.serialization;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.DistinctDataRestriction;
import io.github.thdudk.restrictions.UndirectedRestriction;

public class GraphVizExporter {

    public <T> String serializeGraph(Graph<T> graph) {
        StringBuilder serializationBuilder = new StringBuilder();

        boolean hasDistinctData = graph.hasRestriction(new DistinctDataRestriction<>());

        if(graph.hasRestriction(new UndirectedRestriction<>())) {
            serializationBuilder.append("strict graph {\n");
        } else {
            serializationBuilder.append("strict digraph {\n");
        }

        for(NodeID node : graph.getNodes()) {
            serializationBuilder
                .append(serializeNodeWithContext(node, graph))
                .append(";\n");
        }

        for(Graph.EdgeDescriptor edge : graph.getEdgeDescriptors()) {
            // add edge start and end
            serializationBuilder
                .append(serializeNodeWithContext(edge.start(), graph))
                .append("->") // edge operator
                .append(serializeNodeWithContext(edge.end(), graph))
                .append(";\n");
        }

        serializationBuilder.append('}');

        return serializationBuilder.toString();
    }
    public <T> String serializeExplicitly(Graph<T> graph) {
        StringBuilder serializationBuilder = new StringBuilder();
        serializationBuilder.append("strict digraph {\n");

        for(NodeID node : graph.getNodes()) {
            serializationBuilder
                .append(serializeNodeWithID(node, graph))
                .append(";\n");
        }

        for(Graph.EdgeDescriptor edge : graph.getEdgeDescriptors()) {
            // add edge start and end
            serializationBuilder
                .append(serializeNodeWithID(edge.start(), graph))
                .append("->") // edge operator
                .append(serializeNodeWithID(edge.end(), graph))
                .append("[label=\"").append(edge.id()).append("\"]")
                .append(";\n");
        }

        serializationBuilder.append('}');

        return serializationBuilder.toString();

    }

    private <T> String serializeNodeWithContext(NodeID id, Graph<T> graph) {
        if(graph.hasRestriction(new DistinctDataRestriction<>())) {
            return graph.getNodeData(id).toString();
        } else {
            return serializeNodeWithID(id, graph);
        }
    }
    private <T> String serializeNodeWithID(NodeID id, Graph<T> graph) {
        return '"' + id.toString() + '{' + graph.getNodeData(id) + '}' + '"';
    }

    public static <T> String serialize(Graph<T> graph) throws JsonProcessingException {
        return new GraphVizExporter().serializeGraph(graph);
    }
}
