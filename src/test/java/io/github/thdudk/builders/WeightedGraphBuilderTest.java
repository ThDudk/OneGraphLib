package io.github.thdudk.builders;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class WeightedGraphBuilderTest {
    // TODO add functional tests
    // TODO test restrictions are satisfied

    // -- basic tests --
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addNodes(WeightedGraphBuilder<Integer, Integer> builder) {
        // assert nodes are added successfully
        builder.addNode(1).addNode(2).addNode(3);
        assertEquals(Set.of(1, 2, 3), builder.getNodes());

        // assert duplicate inputs do not produce duplicate nodes
        builder.addNode(1);
        assertEquals(Set.of(1, 2, 3), builder.getNodes());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void removeNodes(WeightedGraphBuilder<Integer, Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        // assert nodes are removed successfully
        builder.removeNode(1).removeNode(2);
        assertEquals(Set.of(3), builder.getNodes());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getNodes(WeightedGraphBuilder<Integer, Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        assertAll(
            () -> assertEquals(Set.of(1, 2, 3), builder.getNodes()),
            () -> assertThrows(RuntimeException.class, () -> builder.getNodes().add(4))
        );
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addDirEdge(WeightedGraphBuilder<Integer, Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        // assert directed edges are added
        builder.addDirEdge(1, 4, 2);
        builder.addDirEdge(2, 5, 3);
        assertAll(
            () -> assertEquals(Set.of(2), builder.build().getNeighbours(1)),
            () -> assertEquals(Set.of(3), builder.build().getNeighbours(2)),
            () -> assertEquals(Set.of(4), builder.build().getEdgesBetween(1, 2)),
            () -> assertEquals(Set.of(5), builder.build().getEdgesBetween(2, 3))
        );
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addUndirEdge(WeightedGraphBuilder<Integer, Integer> builder) {
        builder.addNode(1).addNode(2).addNode(3);

        // assert directed edges are added
        builder.addUndirEdge(1, 4, 2);
        builder.addUndirEdge(2, 5, 3);
        assertAll(
            () -> assertEquals(Set.of(2), builder.build().getNeighbours(1)),
            () -> assertEquals(Set.of(1, 3), builder.build().getNeighbours(2)),
            () -> assertEquals(Set.of(2), builder.build().getNeighbours(3)),
            () -> assertEquals(Set.of(4), builder.build().getEdgesBetween(2, 1)),
            () -> assertEquals(Set.of(5), builder.build().getEdgesBetween(3, 2))
        );
    }

    public static Collection<WeightedGraphBuilder<Integer, Integer>> implementationsToTest() {
        return List.of(
            new WeightedGraphBuilderImpl<>()
        );
    }
}