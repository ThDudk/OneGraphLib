package io.github.thdudk.builders.trees;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.TreeGraph;
import io.github.thdudk.ids.IntNodeID;
import io.github.thdudk.ids.LongEdgeID;
import io.github.thdudk.restrictions.DistinctDataRestriction;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TreeBuilderTest {
    @Test
    void throwsIfMultipleRootsAdded() {
        TreeBuilder<Integer> builder = new TreeBuilderImpl<>();
        builder.addRoot(new IntNodeID(1), 1);

        assertThrows(IllegalArgumentException.class, () -> builder.addRoot(new IntNodeID(2), 2));
    }

    @Test
    void failsIfNullIdIsPassed() {
        TreeBuilder<Integer> builder = new TreeBuilderImpl<>();
        assertThrows(NullPointerException.class, () -> builder.addRoot(null, 1));
    }

    @Test
    void throwsWhenAddingExistingNodeAsAChild() {
        TreeBuilder<Integer> builder = new TreeBuilderImpl<>();
        builder.addRoot(new IntNodeID(1), 1);

        builder.addChild(new IntNodeID(1), new IntNodeID(2), 2, new LongEdgeID(1));
        builder.addChild(new IntNodeID(1), new IntNodeID(3), 3, new LongEdgeID(2));
        assertThrows(IllegalArgumentException.class, () -> builder.addChild(new IntNodeID(2), new IntNodeID(3), 3, new LongEdgeID(3)));
    }
    @Test
    void throwsWhenAddingNullNodeIDAsChild() {
        TreeBuilder<Integer> builder = new TreeBuilderImpl<>();
        builder.addRoot(new IntNodeID(1), 1);

        assertThrows(NullPointerException.class, () -> builder.addChild(new IntNodeID(1), null, 1, new LongEdgeID(1)));
    }

    @Test
    void basicGraph() {
        TreeBuilder<Integer> builder = new TreeBuilderImpl<>();
        builder.addRestriction(new DistinctDataRestriction<>());

        builder.addRoot(new IntNodeID(1), 1);
        builder.addChild(new IntNodeID(1), new IntNodeID(2), 2, new LongEdgeID(1));
        builder.addChild(new IntNodeID(1), new IntNodeID(3), 3, new LongEdgeID(2));
        builder.addChild(new IntNodeID(2), new IntNodeID(4), 4, new LongEdgeID(3));
        builder.addChild(new IntNodeID(2), new IntNodeID(5), 5, new LongEdgeID(4));

        TreeGraph<Integer> tree = builder.build();

        assertAll(
            () -> assertEquals(Set.of(1, 2, 3, 4, 5), new HashSet<>(tree.getNodeDescriptors().stream().map(Graph.NodeDescriptor::data).collect(Collectors.toSet()))),
            () -> assertEquals(4, tree.getEdgeDescriptors().size()),
            () -> assertEquals(Set.of(new IntNodeID(2), new IntNodeID(3)), tree.getNeighbours(new IntNodeID(1))),
            () -> assertEquals(Set.of(new IntNodeID(4), new IntNodeID(5)), tree.getNeighbours(new IntNodeID(2))),
            () -> assertEquals(Set.of(), tree.getNeighbours(new IntNodeID(3))),
            () -> assertEquals(Set.of(), tree.getNeighbours(new IntNodeID(4))),
            () -> assertEquals(Set.of(), tree.getNeighbours(new IntNodeID(5)))
        );
    }
}