package io.github.thdudk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SerializationAndDeserializationTest {
    @Test
    void PojoSerialization() throws JsonProcessingException {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();

        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);

        builder.addUndirEdge(1, 3);
        builder.addUndirEdge(2, 3);
        builder.addDirEdge(1, 2);

        Graph<Integer> graph = builder.build();
        Collection<GraphRestriction<Integer>> restrictions = graph.getRestrictions();

        for (GraphRestriction<Integer> restriction : restrictions) {
            graph.removeRestriction(restriction);
        }

        ObjectMapper mapper = new ObjectMapper();
        String serialized = mapper.writeValueAsString(graph);

        Graph<Integer> deserialized = mapper.readValue(serialized, new TypeReference<>() {});
        assertAll(
            () -> assertEquals(new HashSet<>(graph.getRestrictions()), new HashSet<>(deserialized.getRestrictions())),
            () -> assertEquals(new HashSet<>(graph.getNodeDescriptors()), new HashSet<>(deserialized.getNodeDescriptors())),
            () -> assertEquals(new HashSet<>(graph.getEdgeDescriptors()), new HashSet<>(deserialized.getEdgeDescriptors()))
        );
    }
}
