package io.github.thdudk.iterators.edge;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class AbstractQueueGraphEdgeIteratorTest {
    static Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getStart(AbstractQueueGraphEdgeIterator iterator) {
        iterator.next(); // edge between 1 and 2 should have been polled
        assertEquals(graph.anyNodeIdWithData(1), iterator.getStart());
    }
    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getEnd(AbstractQueueGraphEdgeIterator iterator) {
        iterator.next(); // edge between 1 and 2 should have been polled
        assertEquals(graph.anyNodeIdWithData(2), iterator.getEnd());
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void next(AbstractQueueGraphEdgeIterator iterator) {
        // test that all nodes are visited exactly once
        Set<EdgeID> visited = new HashSet<>();

        while(iterator.hasNext()) {
            EdgeID edge = iterator.next();
            if(visited.contains(edge)) fail("edge " + edge + " visited twice"); // edge has already been visited
            visited.add(edge);
        }

        // assert all nodes were visited
        Set<EdgeID> edges = graph.getEdgeDescriptors().stream().map(Graph.EdgeDescriptor::id).collect(Collectors.toSet());
        assertEquals(edges, visited);
    }

    public static Collection<AbstractQueueGraphEdgeIterator> implementationsToTest() {
        return List.of(
            new DepthFirstEdgeIterator(graph, graph.anyNodeIdWithData(1)),
            new BreadthFirstEdgeIterator(graph, graph.anyNodeIdWithData(1))
        );
    }
}