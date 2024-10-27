package io.github.thdudk.graphs.weighted;

import io.github.thdudk.construction.WeightedGraphFactory;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

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
            () -> assertFalse(loop.hasEdge(1, 1, 8)),
            () -> assertThrows(RuntimeException.class, () -> singleNode.hasEdge(1, 2, 100)),
            () -> assertThrows(RuntimeException.class, () -> singleNode.hasEdge(-100, 2, 100))
        );
    }

    @Test
    void dijkstras() {
        assertAll(
            () -> assertEquals(Pair.of(List.of(1, 2, 3), 5d), loop.dijkstras(1, 3, a -> a.getEdge().doubleValue()).get()),
            () -> assertEquals(Pair.of(List.of(1), 0d), loop.dijkstras(1, 1, a -> a.getEdge().doubleValue()).get()),
            () -> assertEquals(Optional.empty(), disconnected.dijkstras(1, 2, a -> a.getEdge().doubleValue())),
            () -> assertThrows(RuntimeException.class, () -> disconnected.dijkstras(1, 100, a -> a.getEdge().doubleValue())),
            () -> assertThrows(RuntimeException.class, () -> disconnected.dijkstras(-100, 100, a -> a.getEdge().doubleValue()))
        );
    }
}