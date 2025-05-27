package io.github.thdudk;

import io.github.thdudk.builders.weighted.WeightedGraphBuilderImpl;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.restrictions.GraphEdgeRestriction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeightedRestrictedGraphTest {
    static class AlwaysSatisfiedEdgeRestriction<N, E> implements GraphEdgeRestriction<N, E> {
        @Override
        public boolean isSatisfied(WeightedGraph<N, E> graph) {
            return true;
        }
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void storesRestrictions(WeightedRestrictedGraph<Object, Object> graph) {
        GraphEdgeRestriction<Object, Object> nullRestriction = new AlwaysSatisfiedEdgeRestriction<>();

        graph.addRestriction(nullRestriction);

        assertTrue(graph.getEdgeRestrictions().contains(nullRestriction));
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void removingRestrictions(WeightedRestrictedGraph<Object, Object> graph) {
        GraphEdgeRestriction<Object, Object> nullRestriction = new AlwaysSatisfiedEdgeRestriction<>();

        graph.addRestriction(nullRestriction);

        // ensure the graph no longer has the restriction
        graph.removeRestriction(nullRestriction);
        assertFalse(graph.hasEdgeRestriction(nullRestriction));

        // test that the restriction can still be added again
        graph.addRestriction(nullRestriction);
        assertTrue(graph.hasEdgeRestriction(nullRestriction));
    }

    public static Collection<WeightedRestrictedGraph<Object, Object>> implementationsToTest() {
        return List.of(
            new WeightedGraphBuilderImpl<>(),
            new WeightedGraphBuilderImpl<>().build()
        );
    }
}