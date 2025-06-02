package io.github.thdudk.iterators.node;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AbstractQueueGraphIteratorTest {
    static Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getParent(AbstractQueueGraphIterator iterator) {
        iterator.next(); // node 1 should have been polled
        iterator.next(); // node 2 should have been polled
        assertEquals(graph.anyNodeIdWithData(1), iterator.getParent());
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void next(AbstractQueueGraphIterator iterator) {
        // test that all nodes are visited exactly once
        Set<NodeID> visited = new HashSet<>();

        while(iterator.hasNext()) {
            NodeID node = iterator.next();
            if(visited.contains(node)) fail("node visited twice"); // node has already been visited
            visited.add(node);
        }

        // assert all nodes were visited
        assertEquals(graph.getNodes(), visited);
    }

    public static Collection<AbstractQueueGraphIterator> implementationsToTest() {
        return List.of(
            new DepthFirstIterator(graph, graph.anyNodeIdWithData(1)),
            new BreadthFirstIterator(graph, graph.anyNodeIdWithData(1))
        );
    }
}