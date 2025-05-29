package io.github.thdudk.restrictions;

import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DirectedRestrictionTest {
    @Test
    void failsOnUndirectedEdge() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addDirEdgeChain(List.of(1, 2, 3));
        builder.addUndirEdge(2, 3);
        val graph = builder.build();

        assertFalse(new DirectedRestriction<Integer>().isSatisfied(graph));
    }
    @Test
    void isSatisfiedByDirectedGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addDirEdgeChain(List.of(1, 2, 3));
        val graph = builder.build();

        assertTrue(new DirectedRestriction<Integer>().isSatisfied(graph));
    }
    @Test
    void isSatisfiedByATrivialGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(1);
        val graph = builder.build();

        assertTrue(new DirectedRestriction<Integer>().isSatisfied(graph));
    }
    @Test
    void isSatisfiedByVoidGraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        val graph = builder.build();

        assertTrue(new DirectedRestriction<Integer>().isSatisfied(graph));
    }
    @Test
    void isUnsatisfiedByMultigraph() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addDirEdgeChain(List.of(1, 2, 1, 2));
        val graph = builder.build();

        assertFalse(new DirectedRestriction<Integer>().isSatisfied(graph));
    }
}