package io.github.thdudk.iterators.edge;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphEdgeIterator;
import io.github.thdudk.iterators.node.BreadthFirstIterator;
import io.github.thdudk.serialization.GraphVizExporter;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstEdgeIteratorTest {
    static Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);

    @Test
    void testOrder() {
        GraphEdgeIterator iterator = new BreadthFirstEdgeIterator(graph, graph.anyNodeIdWithData(1));
        iterator.next(); // read the edge from 1 to 2

        // test the first layer
        Set<EdgeID> visitedEdges = new HashSet<>();
        visitedEdges.add(iterator.next());

        Set<EdgeID> firstLayerNodes = Set.of(
            graph.getAnyEdgeBetween(graph.anyNodeIdWithData(2), graph.anyNodeIdWithData(3)).orElseThrow()
        );
        assertEquals(firstLayerNodes, visitedEdges);

        visitedEdges.clear();

        // test the second layer
        visitedEdges.add(iterator.next());
        visitedEdges.add(iterator.next());
        visitedEdges.add(iterator.next());

        Set<EdgeID> secondLayerNodes = Set.of(
            graph.getAnyEdgeBetween(graph.anyNodeIdWithData(3), graph.anyNodeIdWithData(2)).orElseThrow(),
            graph.getAnyEdgeBetween(graph.anyNodeIdWithData(3), graph.anyNodeIdWithData(4)).orElseThrow(),
            graph.getAnyEdgeBetween(graph.anyNodeIdWithData(3), graph.anyNodeIdWithData(5)).orElseThrow()
        );
        assertEquals(secondLayerNodes, visitedEdges);
    }
}