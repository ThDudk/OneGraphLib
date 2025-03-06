package io.github.thdudk.builders;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.util.Collection;
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
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void recreateCSESShortestRoutesIG1() {
        // nodes are implicitly created
        Graph<Integer> graph = new GraphBuilderImpl<Integer>()
            .addDirEdge(1, 2)
            .addUndirEdge(2, 3)
            .addDirEdge(3, 4).addUndirEdge(3, 5)
            .addUndirEdge(4, 5).addDirEdge(4, 2)
            .addDirEdge(5, 6).addDirEdge(5, 3)
            .addDirEdge(6, 7)
            .addUndirEdge(7, 10).addDirEdge(7, 8).addDirEdge(7, 9)
            .addUndirEdge(8, 9).addDirEdge(8, 5)
            .addUndirEdge(9, 10)
            .build();

        assertEquals(graph, TestGraphs.getCSESShortestRoutesIGraphNum(1));
    }

    // -- basic tests --

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addNodes(GraphBuilder<Integer> builder) {
        // assert nodes are added successfully
        builder.addNode(1).addNode(2).addNode(3);
        assertEquals(Set.of(1, 2, 3), builder.getNodes());

        // assert duplicate inputs do not produce duplicate nodes
        builder.addNode(1);
        assertEquals(Set.of(1, 2, 3), builder.getNodes());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void removeNodes(GraphBuilder<Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        // assert nodes are removed successfully
        builder.removeNode(1).removeNode(2);
        assertEquals(Set.of(3), builder.getNodes());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getNodes(GraphBuilder<Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        assertAll(
            () -> assertEquals(Set.of(1, 2, 3), builder.getNodes()),
            () -> assertThrows(RuntimeException.class, () -> builder.getNodes().add(4))
        );
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addDirEdge(GraphBuilder<Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        // assert directed edges are added
        builder.addDirEdge(1, 2);
        builder.addDirEdge(2, 3);
        assertAll(
            () -> assertEquals(Set.of(2), builder.build().getNeighbours(1)),
            () -> assertEquals(Set.of(3), builder.build().getNeighbours(2)),
            () -> assertEquals(Set.of(), builder.build().getNeighbours(3))
        );
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addUndirEdge(GraphBuilder<Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        // assert directed edges are added
        builder.addUndirEdge(1, 2);
        builder.addUndirEdge(2, 3);
        assertAll(
            () -> assertEquals(Set.of(2), builder.build().getNeighbours(1)),
            () -> assertEquals(Set.of(1, 3), builder.build().getNeighbours(2)),
            () -> assertEquals(Set.of(2), builder.build().getNeighbours(3))
        );
    }

    public static Collection<GraphBuilder<Integer>> implementationsToTest() {
        return List.of(
            new GraphBuilderImpl<>()
        );
    }
}