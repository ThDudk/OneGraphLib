package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.TestGraphs;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTest {

    // TODO add functionality tests

    // -- basic tests --
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getNodes(Graph<Integer> graph, Map<String, Object> properties) {
        assertEquals(properties.get("nodes"), graph.getNodes());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getNeighbours(Graph<Integer> graph, Map<String, Object> properties) {
        assertEquals(properties.get("neighboursOf1"), graph.getNeighbours(1));
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getInNeighbours(Graph<Integer> graph, Map<String, Object> properties) {
        assertEquals(properties.get("inNeighboursOf1"), graph.getInNeighbours(1));
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getDegree(Graph<Integer> graph, Map<String, Object> properties) {
        assertEquals(((Set<?>) properties.get("neighboursOf1")).size(), graph.getDegree(1));
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getInDegree(Graph<Integer> graph, Map<String, Object> properties) {
        assertEquals(((Set<?>) properties.get("inNeighboursOf1")).size(), graph.getInDegree(1));
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getUnweightedAdjacencyList(Graph<Integer> graph) {}

    public static Collection<Object[]> implementationsToTest() {
        // map properties
        Set<Integer> tenNodes = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toSet());
        return Arrays.asList(new Object[][] {
            {TestGraphs.getCSESShortestRoutesIGraphNum(1), Map.of("nodes", tenNodes, "neighboursOf1", Set.of(2), "inNeighboursOf1", Set.of())},
            {TestGraphs.getCSESShortestRoutesIGraphNum(2), Map.of("nodes", tenNodes, "neighboursOf1", Set.of(2), "inNeighboursOf1", Set.of(5))},
            {TestGraphs.getCSESShortestRoutesIGraphNum(3), Map.of("nodes", tenNodes, "neighboursOf1", Set.of(2, 4, 5), "inNeighboursOf1", Set.of())},
            {TestGraphs.getCSESShortestRoutesIGraphNum(4), Map.of("nodes", tenNodes, "neighboursOf1", Set.of(2), "inNeighboursOf1", Set.of(2, 5))},
            {TestGraphs.getCSESShortestRoutesIGraphNum(5), Map.of("nodes", tenNodes, "neighboursOf1", Set.of(2), "inNeighboursOf1", Set.of(4))}
        });
    }

    // -- Serialization tests --
    @Test
    void PrimitiveDataSerialization() throws JsonProcessingException {
        Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIGraphNum(1);

        ObjectMapper mapper = new ObjectMapper();

        // serialize the graph
        String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(graph);

        // deserialize the graph
        Graph<Integer> deserialized = mapper.readValue(serialized, new TypeReference<>() {});

        assertEquals(graph, deserialized);
    }
    @ParameterizedTest
    @MethodSource("serializationTimeTrialGraphs")
    void primitiveDataSerializationTimed(Graph<Integer> graph) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

        // serialize the graph
        long serializationStart = System.nanoTime();
        String serialized = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(graph);
        long serializationEnd = System.nanoTime();
        double serializationTime = (serializationEnd - serializationStart) / 10e+7; // convert to ms

        // deserialize the graph
        long deserializationStart = System.nanoTime();
        Graph<Integer> deserialized = mapper.readValue(serialized, new TypeReference<>() {});
        long deserializationEnd = System.nanoTime();
        double deserializationTime = (deserializationEnd - deserializationStart) / 10e+7; // convert to ms

        // report results
        System.out.println("Trial: nodes: " + graph.getNodes().size() + ", serialization time (ms): " + serializationTime + ", deserialization time (ms): " + deserializationTime);
    }

    public static Collection<Graph<Integer>> serializationTimeTrialGraphs() {
        return List.of(
            TestGraphs.getCSESShortestRoutesIGraphNum(1),
            TestGraphs.getCSESShortestRoutesIGraphNum(2),
            TestGraphs.getCSESShortestRoutesIGraphNum(3),
            TestGraphs.getCSESShortestRoutesIGraphNum(4),
            TestGraphs.getCSESShortestRoutesIGraphNum(5)
        );
    }
}