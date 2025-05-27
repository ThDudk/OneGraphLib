package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.DirectedRestriction;
import io.github.thdudk.restrictions.UndirectedRestriction;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class GraphTest {
    @Test
    void enforcesRestrictions() {
        GraphBuilder<Integer> builder = new GraphBuilderImpl<>();
        NodeID one = builder.addNode(1);
        NodeID two = builder.addNode(2);
        NodeID three = builder.addNode(3);

        builder.addDirEdge(one, two);
        builder.addDirEdge(two, three);
        builder.addDirEdge(three, one);

        Graph<Integer> graph = builder.build();

        assertAll(
            () -> assertThrows(RuntimeException.class, () -> graph.addRestriction(new UndirectedRestriction<>())),
            () -> assertDoesNotThrow(() -> graph.addRestriction(new DirectedRestriction<>()))
        );
    }

    // basic functionality is not tested as it should be caught by functional tests / test projects

    public static Collection<Graph<Integer>> implementationsToTest() {
        return List.of(
            TestGraphs.getCSESShortestRoutesIUnweightedGraph(1)
        );
    }
}