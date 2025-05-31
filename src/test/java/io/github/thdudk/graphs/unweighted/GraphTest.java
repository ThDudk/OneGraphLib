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
    // basic functionality is not tested as it should be caught by functional tests / test projects

    public static Collection<Graph<Integer>> implementationsToTest() {
        return List.of(
            TestGraphs.getCSESShortestRoutesIUnweightedGraph(1)
        );
    }
}