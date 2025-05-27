package io.github.thdudk.builders.unweighted;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.IntNodeID;
import io.github.thdudk.ids.LongEdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.UndirectedRestriction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ExplicitIdsGraphBuilderTest {
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addNodes(ExplicitIdsGraphBuilder<Integer> builder) {
        // assert nodes are added successfully
        builder.addNode(new IntNodeID(1), 1);
        builder.addNode(new IntNodeID(2), 2);
        builder.addNode(new IntNodeID(3), 3);

        assertAll(
            () -> assertEquals(new HashSet<>(Set.of(new IntNodeID(1), new IntNodeID(2), new IntNodeID(3))), new HashSet<>(builder.build().getNodes())),
            () -> assertEquals(new HashSet<>(Set.of(1, 2, 3)), new HashSet<>(builder.build().getNodeDataMap().values()))
        );

    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void duplicateNodeIDsThrowExceptions(ExplicitIdsGraphBuilder<Integer> builder) {
        builder.addNode(new IntNodeID(1), 1);
        builder.addNode(new IntNodeID(2), 2);

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> builder.addNode(new IntNodeID(1), 1)),
            () -> assertEquals(2, builder.build().getNodes().size())
        );
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addDirEdge(ExplicitIdsGraphBuilder<Integer> builder) {
        NodeID one = new IntNodeID(1);
        NodeID two = new IntNodeID(2);
        NodeID three = new IntNodeID(3);
        builder.addNode(one, 1);
        builder.addNode(two, 2);
        builder.addNode(three, 3);

        // assert directed edges are added
        builder.addDirEdge(one, two, new LongEdgeID(1));
        builder.addDirEdge(two, three, new LongEdgeID(2));
        assertAll(
            () -> assertArrayEquals(Set.of(two).toArray(), builder.build().getNeighbours(one).toArray()),
            () -> assertArrayEquals(Set.of(three).toArray(), builder.build().getNeighbours(two).toArray()),
            () -> assertArrayEquals(Set.of().toArray(), builder.build().getNeighbours(three).toArray()),
            () -> assertArrayEquals(List.of(new LongEdgeID(1)).toArray(), builder.build().getEdgesBetween(one, two).toArray()),
            () -> assertArrayEquals(List.of(new LongEdgeID(2)).toArray(), builder.build().getEdgesBetween(two, three).toArray())
        );
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void addUndirEdge(ExplicitIdsGraphBuilder<Integer> builder) {
        NodeID one = new IntNodeID(1);
        NodeID two = new IntNodeID(2);
        NodeID three = new IntNodeID(3);
        builder.addNode(one, 1);
        builder.addNode(two, 2);
        builder.addNode(three, 3);

        // assert directed edges are added
        builder.addUndirEdge(one, two, new LongEdgeID(1), new LongEdgeID(2));
        builder.addUndirEdge(two, three, new LongEdgeID(3), new LongEdgeID(4));
        assertAll(
            () -> assertArrayEquals(Set.of(two).toArray(), builder.build().getNeighbours(one).toArray()),
            () -> assertArrayEquals(Set.of(one, three).toArray(), builder.build().getNeighbours(two).toArray()),
            () -> assertArrayEquals(Set.of(two).toArray(), builder.build().getNeighbours(three).toArray()),
            () -> assertArrayEquals(List.of(new LongEdgeID(1)).toArray(), builder.build().getEdgesBetween(one, two).toArray()),
            () -> assertArrayEquals(List.of(new LongEdgeID(2)).toArray(), builder.build().getEdgesBetween(two, one).toArray()),
            () -> assertArrayEquals(List.of(new LongEdgeID(3)).toArray(), builder.build().getEdgesBetween(two, three).toArray()),
            () -> assertArrayEquals(List.of(new LongEdgeID(4)).toArray(), builder.build().getEdgesBetween(three, two).toArray())
        );
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void duplicateEdgeIDsThrowExceptions(ExplicitIdsGraphBuilder<Integer> builder) {
        NodeID one = new IntNodeID(1);
        NodeID two = new IntNodeID(2);
        NodeID three = new IntNodeID(3);
        builder.addNode(one, 1);
        builder.addNode(two, 2);
        builder.addNode(three, 3);

        builder.addDirEdge(one, two, new LongEdgeID(1));

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> builder.addDirEdge(one, two, new LongEdgeID(1))),
            () -> assertThrows(RuntimeException.class, () -> builder.addDirEdge(two, one, new LongEdgeID(1))),
            () -> assertThrows(RuntimeException.class, () -> builder.addDirEdge(two, three, new LongEdgeID(1))),
            () -> assertEquals(1, builder.build().getEdgesBetween(one, two).size()),
            () -> assertEquals(0, builder.build().getEdgesBetween(two, one).size())
        );
    }

    void testCopyOf() {
        Graph<Integer> testGraph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);
        ExplicitIdsGraphBuilder<Integer> builder = ExplicitIdsGraphBuilder.copyOf(testGraph);

        assertEquals(testGraph, builder.build());
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void enforcesRestrictionsOnBuild(ExplicitIdsGraphBuilder<Integer> builder) {
        builder.addRestriction(new UndirectedRestriction<>());

        NodeID one = new IntNodeID(1);
        NodeID two = new IntNodeID(2);
        builder.addNode(one, 1);
        builder.addNode(two, 2);

        // assert directed edges are added
        builder.addDirEdge(one, two, new LongEdgeID(1));

        assertThrows(RuntimeException.class, builder::build);

        builder.addDirEdge(two, one, new LongEdgeID(2));

        assertDoesNotThrow(builder::build);
    }

    public static Collection<ExplicitIdsGraphBuilder<Integer>> implementationsToTest() {
        return List.of(
            new ExplicitIdsGraphBuilderImpl<>()
        );
    }
}