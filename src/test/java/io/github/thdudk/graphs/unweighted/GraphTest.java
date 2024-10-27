package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.construction.GraphFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph<Integer> singleNode = new GraphFactory<Integer>().builder()
        .addNode(1).build();
    Graph<Integer> loop = new GraphFactory<Integer>().builder()
        .addNeighborChain(List.of(1, 2, 3, 4, 1), false).build();
    Graph<Integer> disconnected = new GraphFactory<Integer>().builder()
        .addNode(1).addNeighborChain(List.of(2, 3, 4, 2), true).build();
    Graph<Integer> complex = new GraphFactory<Integer>().undirected().builder()
        .addNeighborChain(List.of(1, 2, 3, 4, 5), true)
        .addNeighborChain(List.of(1, 6, 4), true)
        .addNeighbor(1, 5)
        .addNeighbor(2, 4).build();

    @Test
    void hasNode() {
        assertAll(
            () -> assertTrue(singleNode.hasNode(1)),
            () -> assertFalse(singleNode.hasNode(0))
        );
    }

    @Test
    void isNeighbor() {
        assertAll(
            () -> assertFalse(singleNode.isNeighbor(1, 1)),
            () -> assertFalse(singleNode.isNeighbor(1, null)),
            () -> assertTrue(loop.isNeighbor(1, 2)),
            () -> assertFalse(loop.isNeighbor(2, 1)),
            () -> assertThrows(RuntimeException.class, () -> singleNode.isNeighbor(1, 0)),
            () -> assertThrows(RuntimeException.class, () -> singleNode.isNeighbor(0, 2))
        );
    }

    @Test
    void shortestPath() {
        assertAll(
            () -> assertEquals(List.of(1), singleNode.shortestPath(1, 1).orElse(List.of())),
            () -> assertEquals(List.of(1, 2, 3, 4), loop.shortestPath(1, 4).orElse(List.of())),
            () -> assertEquals(Optional.empty(), disconnected.shortestPath(1, 2)),
            () -> assertThrows(RuntimeException.class, () -> singleNode.shortestPath(1, 0)),
            () -> assertThrows(RuntimeException.class, () -> singleNode.shortestPath(0, 2)),
            () -> assertEquals(List.of(1, 6, 4), complex.shortestPath(1, 4).orElse(List.of()))
        );
    }
}