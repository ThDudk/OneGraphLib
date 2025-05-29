package io.github.thdudk.restrictions;

import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import lombok.val;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DistinctDataRestrictionTest {

    @Test
    void isSatisfiedByDistinctDataGraph() {
        GraphBuilder<Integer> builder = new GraphBuilderImpl<>();
        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);
        val graph = builder.build();

        assertTrue(new DistinctDataRestriction<Integer>().isSatisfied(graph));
    }
    @Test
    void isUnsatisfiedByIndistinctDataGraph() {
        GraphBuilder<Integer> builder = new GraphBuilderImpl<>();
        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(2);
        val graph = builder.build();

        assertFalse(new DistinctDataRestriction<Integer>().isSatisfied(graph));
    }
}