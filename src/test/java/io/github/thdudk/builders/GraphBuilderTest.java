package io.github.thdudk.builders;

import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.ids.NodeID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GraphBuilderTest {
    GraphBuilder<Integer> builderWithNodes(int numNodes) {
        GraphBuilder<Integer> builder = new GraphBuilderImpl<>();

        IntStream.range(0, numNodes).forEach(builder::addNode);
        return builder;
    }

    // TODO test restrictions are satisfied

    // -- functionality tests --

    // -- basic tests --

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addNodes(GraphBuilder<Integer> builder) {
        // assert nodes are added successfully
        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);
        assertArrayEquals(List.of(1, 2, 3).toArray(), builder.build().getNodeDataMap().values().toArray());

        // assert duplicate inputs are added
        builder.addNode(1);
        System.out.println(builder.build().getNodes());
        assertEquals(4, builder.build().getNodes().size());
        assertEquals(Set.of(1, 2, 3), new HashSet<>(builder.build().getNodeDataMap().values()));
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addDirEdge(GraphBuilder<Integer> builder) {
        NodeID one = builder.addNode(1);
        NodeID two = builder.addNode(2);
        NodeID three = builder.addNode(3);

        // assert directed edges are added
        builder.addDirEdge(one, two);
        builder.addDirEdge(two, three);
        assertAll(
            () -> assertEquals(Set.of(two), builder.build().getNeighbours(one)),
            () -> assertEquals(Set.of(three), builder.build().getNeighbours(two)),
            () -> assertEquals(Set.of(), builder.build().getNeighbours(three))
        );
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addUndirEdge(GraphBuilder<Integer> builder) {
        NodeID one = builder.addNode(1);
        NodeID two = builder.addNode(2);
        NodeID three = builder.addNode(3);

        // assert directed edges are added
        builder.addUndirEdge(one, two);
        builder.addUndirEdge(two, three);
        assertAll(
            () -> assertEquals(Set.of(two), builder.build().getNeighbours(one)),
            () -> assertEquals(Set.of(one, three), builder.build().getNeighbours(two)),
            () -> assertEquals(Set.of(two), builder.build().getNeighbours(three))
        );
    }

    public static Collection<GraphBuilder<Integer>> implementationsToTest() {
        return List.of(
            new GraphBuilderImpl<>()
        );
    }
}