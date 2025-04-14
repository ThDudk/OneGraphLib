package io.github.thdudk;

import io.github.thdudk.builders.DistinctDataGraphBuilderImpl;
import io.github.thdudk.builders.GraphBuilderImpl;
import io.github.thdudk.builders.WeightedGraphBuilderImpl;
import io.github.thdudk.builders.paths.PathGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.weighted.WeightedGraph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphEdgeRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import io.github.thdudk.restrictions.MaxDegreeRestriction;
import io.github.thdudk.restrictions.UndirectedRestriction;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeightedRestrictedGraphTest {
    static class NullEdgeRestriction<N, E> implements GraphEdgeRestriction<N, E> {
        @Override
        public boolean isSatisfied(WeightedGraph<N, E> graph) {
            return true;
        }
    }
    static class ImpossibleEdgeRestriction<N, E> implements GraphEdgeRestriction<N, E> {
        @Override
        public boolean isSatisfied(WeightedGraph<N, E> graph) {
            return false;
        }
    }


    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void storesRestrictions(WeightedRestrictedGraph<Object, Object> graph) {
        GraphEdgeRestriction<Object, Object> nullRestriction = new NullEdgeRestriction<>();
        GraphEdgeRestriction<Object, Object> impossible = new ImpossibleEdgeRestriction<>();

        graph.addRestriction(nullRestriction);
        graph.addRestriction(impossible);

        // ensure graph contains the added restrictions
        assertTrue(graph.getEdgeRestrictions().containsAll(List.of(nullRestriction, impossible)));
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void removingRestrictions(WeightedRestrictedGraph<Object, Object> graph) {
        GraphEdgeRestriction<Object, Object> nullRestriction = new NullEdgeRestriction<>();

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