package io.github.thdudk;

import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.builders.weighted.WeightedGraphBuilderImpl;
import io.github.thdudk.builders.paths.PathGraphBuilderImpl;
import io.github.thdudk.restrictions.GraphRestriction;
import io.github.thdudk.restrictions.MaxDegreeRestriction;
import io.github.thdudk.restrictions.UndirectedRestriction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestrictedGraphTest {
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void storesRestrictions(RestrictedGraph<Object> graph) {
        GraphRestriction<Object> undir = new UndirectedRestriction<>();
        GraphRestriction<Object> maxDegree = new MaxDegreeRestriction<>(3);

        graph.addRestriction(undir);
        graph.addRestriction(maxDegree);

        // ensure graph contains the added restrictions
        assertTrue(graph.getRestrictions().containsAll(List.of(undir, maxDegree)));
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void removingRestrictions(RestrictedGraph<Object> graph) {
        GraphRestriction<Object> undir = new UndirectedRestriction<>();

        graph.addRestriction(undir);

        // ensure the graph no longer has the restriction
        graph.removeRestriction(undir);
        assertFalse(graph.hasRestriction(undir));

        // test that the restriction can still be added again
        graph.addRestriction(undir);
        assertTrue(graph.hasRestriction(undir));
    }

    public static Collection<RestrictedGraph<Object>> implementationsToTest() {
        return List.of(
            new GraphBuilderImpl<>(),
            new DistinctDataGraphBuilderImpl<>(),
            new WeightedGraphBuilderImpl<>(),
            new PathGraphBuilderImpl<>(),

            new GraphBuilderImpl<>().build(),
            new DistinctDataGraphBuilderImpl<>().build(),
            new WeightedGraphBuilderImpl<>().build(),
            new PathGraphBuilderImpl<>().build()
        );
    }
}