package io.github.thdudk.iterators;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.graphs.unweighted.Graph;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BreadthFirstIteratorTest {
    static Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIGraphNum(1);

    @Test
    void testOrder() {
        BreadthFirstIterator<Integer> iterator = new BreadthFirstIterator<>(graph, 3);
        iterator.next(); // read the root

        Set<Integer> topLayerNexts = new HashSet<>();
        topLayerNexts.add(iterator.next());
        topLayerNexts.add(iterator.next());
        topLayerNexts.add(iterator.next());

        assertEquals(Set.of(2, 4, 5), topLayerNexts);
    }

}