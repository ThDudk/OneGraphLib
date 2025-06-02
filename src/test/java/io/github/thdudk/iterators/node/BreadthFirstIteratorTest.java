package io.github.thdudk.iterators.node;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstIteratorTest {
    static Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);

    @Test
    void testOrder() {
        BreadthFirstIterator iterator = new BreadthFirstIterator(graph, graph.anyNodeIdWithData(3));
        iterator.next(); // read the root

        Set<NodeID> topLayerNexts = new HashSet<>();
        topLayerNexts.add(iterator.next());
        topLayerNexts.add(iterator.next());
        topLayerNexts.add(iterator.next());

        assertEquals(Set.of(graph.anyNodeIdWithData(2), graph.anyNodeIdWithData(4), graph.anyNodeIdWithData(5)), topLayerNexts);
    }

}