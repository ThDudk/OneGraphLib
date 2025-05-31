package io.github.thdudk.builders;

import io.github.thdudk.builders.weighted.WeightedGraphBuilder;
import io.github.thdudk.builders.weighted.WeightedGraphBuilderImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.serialization.GraphVizExporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class WeightedGraphBuilderTest {
    // TODO add functional tests
    // TODO test restrictions are satisfied

    // -- basic tests --
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addNodes(WeightedGraphBuilder<Integer, Integer> builder) {
        // assert nodes are added successfully
        NodeID one = builder.addNode(1);
        NodeID two = builder.addNode(2);
        NodeID three = builder.addNode(3);
        assertEquals(Set.of(one, two, three), new HashSet<>(builder.build().getNodes()));
        assertEquals(Set.of(1, 2, 3), new HashSet<>(builder.build().getNodeDataMap().values()));

        // assert duplicate inputs are added
        NodeID one2 = builder.addNode(1);
        assertEquals(Set.of(one, two, three, one2), new HashSet<>(builder.build().getNodes()));
        assertEquals(Set.of(1, 2, 3), new HashSet<>(builder.build().getNodeDataMap().values()));
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addDirEdge(WeightedGraphBuilder<Integer, Integer> builder) {
        NodeID one = builder.addNode(1);
        NodeID two = builder.addNode(2);
        NodeID three = builder.addNode(3);

        // assert directed edges are added
        builder.addDirEdge(one, two, 4);
        builder.addDirEdge(two, three, 5);

        WeightedGraph<Integer, Integer> graph = builder.build();
        assertAll(
            () -> assertEquals(Set.of(two), new HashSet<>(graph.getNeighbours(one))),
            () -> assertEquals(Set.of(three), new HashSet<>(graph.getNeighbours(two))),
            () -> assertEquals(Set.of(4), graph.getEdgesBetween(one, two).stream().map(graph::getEdgeData).collect(Collectors.toSet())),
            () -> assertEquals(Set.of(5), graph.getEdgesBetween(two, three).stream().map(graph::getEdgeData).collect(Collectors.toSet()))
        );

        // assert that duplicate edges are added
        builder.addDirEdge(one, two, 4);
        assertEquals(List.of(4, 4), builder.build().getEdgesBetween(one, two).stream().map(graph::getEdgeData).toList());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addUndirEdge(WeightedGraphBuilder<Integer, Integer> builder) {
        NodeID one = builder.addNode(1);
        NodeID two = builder.addNode(2);
        NodeID three = builder.addNode(3);

        // assert directed edges are added
        builder.addUndirEdge(one, two, 4);
        builder.addUndirEdge(two, three, 5);

        WeightedGraph<Integer, Integer> graph = builder.build();
        assertAll(
            () -> assertEquals(Set.of(two), new HashSet<>(graph.getNeighbours(one))),
            () -> assertEquals(Set.of(one, three), new HashSet<>(graph.getNeighbours(two))),
            () -> assertEquals(Set.of(two), new HashSet<>(graph.getNeighbours(three))),
            () -> assertEquals(List.of(4), graph.getEdgesBetween(two, one).stream().map(graph::getEdgeData).toList()),
            () -> assertEquals(List.of(5), graph.getEdgesBetween(two, three).stream().map(graph::getEdgeData).toList())
        );

        // assert that duplicate edges are added
        builder.addUndirEdge(one, two, 4);
        assertEquals(List.of(4, 4), builder.build().getEdgesBetween(one, two).stream().map(graph::getEdgeData).toList());
    }

    public static Collection<WeightedGraphBuilder<Integer, Integer>> implementationsToTest() {
        return List.of(
            new WeightedGraphBuilderImpl<>()
        );
    }
}