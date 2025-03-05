package io.github.thdudk.iterators;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.builders.GraphBuilder;
import io.github.thdudk.builders.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AbstractQueueGraphIteratorTest {
    static Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIGraphNum(1);

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void getParent(AbstractQueueGraphIterator<Integer> iterator) {
        iterator.next(); // node 1 should have been polled
        iterator.next(); // node 2 should have been polled
        assertEquals(1, iterator.getParent());
    }

    @ParameterizedTest
    @MethodSource("implementationsToTest")
    void next(AbstractQueueGraphIterator<Integer> iterator) {
        // test that all nodes are visited exactly once
        Set<Integer> visited = new HashSet<>();

        while(iterator.hasNext()) {
            int node = iterator.next();
            if(visited.contains(node)) fail("node visited twice"); // node has already been visited
            visited.add(node);
        }

        // assert all nodes were visited
        assertEquals(graph.getNodes(), visited);
    }

    public static Collection<AbstractQueueGraphIterator<Integer>> implementationsToTest() {
        return List.of(
            new DepthFirstIterator<>(graph, 1),
            new BreadthFirstIterator<>(graph, 1)
        );
    }
}