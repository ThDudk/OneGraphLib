package io.github.thdudk.restrictions;

import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaxInDegreeRestrictionTest {
    @Test
    void isSatisfiedByValidGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(0);
        builder.addDirEdge(1, 0);
        val graph1 = builder.build();

        builder.addDirEdge(2, 0);
        val graph2 = builder.build();

        assertAll(
            () -> assertTrue(new MaxInDegreeRestriction<Integer>(2).isSatisfied(graph1)),
            () -> assertTrue(new MaxInDegreeRestriction<Integer>(2).isSatisfied(graph2))
        );
    }

    @Test
    void isUnsatisfiedByInvalidGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(0);
        builder.addDirEdge(1, 0);
        builder.addDirEdge(2, 0);
        builder.addDirEdge(3, 0);

        val graph = builder.build();

        assertFalse(new MaxInDegreeRestriction<Integer>(2).isSatisfied(graph));
    }

    @Test
    void ignoresOutEdges() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(0);
        builder.addDirEdge(1, 0);
        builder.addDirEdge(2, 0);

        for(int i = 3; i < 100; i++) {
            builder.addDirEdge(0, i);
        }

        val graph = builder.build();

        assertTrue(new MaxInDegreeRestriction<Integer>(2).isSatisfied(graph));
    }
}