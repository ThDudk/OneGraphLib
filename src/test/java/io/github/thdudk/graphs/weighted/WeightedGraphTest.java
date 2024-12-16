package io.github.thdudk.graphs.weighted;

import io.github.thdudk.construction.WeightedGraphFactory;
import io.github.thdudk.graphs.weighted.WeightedGraph.EdgeEndpointPair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class WeightedGraphTest {
    WeightedGraph<Integer, Integer> singleNode = new WeightedGraphFactory<Integer, Integer>().builder()
        .addNode(1).build();
    WeightedGraph<Integer, Integer> loop = new WeightedGraphFactory<Integer, Integer>().builder()
        .addUndirEdge(1, 2, 2)
        .addUndirEdge(2, 3, 3)
        .addUndirEdge(3, 2, 4)
        .addUndirEdge(4, 4, 1)
        .addUndirEdge(2, 1, 4)
        .build();
    WeightedGraph<Integer, Integer> disconnected = new WeightedGraphFactory<Integer, Integer>().builder()
        .addNode(1)
        .addUndirEdge(2, 1, 3)
        .addUndirEdge(3, 1, 4)
        .addUndirEdge(4, 1, 2)
        .build();
    WeightedGraph<Integer, Integer> loopZeroWeight = new WeightedGraphFactory<Integer, Integer>().builder()
        .addUndirEdge(1, 0, 2)
        .addUndirEdge(2, 0, 3)
        .addUndirEdge(3, 0, 4)
        .addUndirEdge(4, 0, 1)
        .build();

    @Test
    void hasEdge() {
        assertAll(
            () -> assertTrue(loop.hasEdge(1, 2, 2)),
            () -> assertFalse(loop.hasEdge(1, 3, 2)),
            () -> assertFalse(loop.hasEdge(1, 1, 3)),
            () -> assertThrows(IllegalArgumentException.class, () -> singleNode.hasEdge(1, 2, 100)),
            () -> assertThrows(IllegalArgumentException.class, () -> singleNode.hasEdge(-100, 2, 100))
        );
    }

    @Test
    void dijkstras() {
        BiFunction<Integer, EdgeEndpointPair<Integer, Integer>, Integer> compression =
            (c, a) -> {
                if(a.getEdge() == null) {
                    if (c == null) return 0;
                    return c;
                }
                if(c == null) return a.getEdge();
                return c + a.getEdge();
            };

        assertAll(
            () -> assertEquals(List.of(new EdgeEndpointPair<>(null, 1), new EdgeEndpointPair<>(2, 2), new EdgeEndpointPair<>(3, 3)),
                loop.dijkstras(1, 3, compression).orElseThrow()),
            () -> assertEquals(List.of(new EdgeEndpointPair<>(null, 1)), loop.dijkstras(1, 1, compression).orElseThrow()),
            () -> assertEquals(Optional.empty(), disconnected.dijkstras(1, 2, compression)),
            () -> assertThrows(IllegalArgumentException.class, () -> disconnected.dijkstras(1, 100, compression)),
            () -> assertThrows(IllegalArgumentException.class, () -> disconnected.dijkstras(-100, 100, compression))
        );
    }
}