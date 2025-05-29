package io.github.thdudk.restrictions;

import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MaxDegreeRestrictionTest {
    @Test
    void isSatisfiedByConformingGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(1);
        builder.addDirEdge(1, 2);
        builder.addDirEdge(2, 3); // now has degree 2
        val graph = builder.build();

        assertTrue(new MaxDegreeRestriction<Integer>(2).isSatisfied(graph));
    }
    @Test
    void isUnsatisfiedByNonconformingGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(1);
        builder.addDirEdge(1, 2);
        builder.addDirEdge(1, 3);
        builder.addDirEdge(1, 4); // now has degree 3
        val graph = builder.build();

        assertFalse(new MaxDegreeRestriction<Integer>(2).isSatisfied(graph));
    }
}