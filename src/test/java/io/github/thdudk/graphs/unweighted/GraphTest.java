package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.construction.GraphFactory;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class GraphTest {
    Graph<Integer> singleNode = new GraphFactory<Integer>().builder()
        .addNode(1).build();
    Graph<Integer> loop = new GraphFactory<Integer>().builder()
        .addDirNeighborChain(List.of(1, 2, 3, 4, 1)).build();
    Graph<Integer> disconnected = new GraphFactory<Integer>().builder()
        .addNode(1).addUndirNeighborChain(List.of(2, 3, 4, 2)).build();
    Graph<Integer> complex = new GraphFactory<Integer>().undirected().builder()
        .addUndirNeighborChain(List.of(1, 2, 3, 4, 5))
        .addUndirNeighborChain(List.of(1, 6, 4))
        .addNeighbor(2, 5)
        .addNeighbor(2, 6).build();

    @Test
    void jacksonSerialization() throws JsonProcessingException {
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(complex));
    }

    @Test
    void hasNode() {
        assertAll(
            () -> assertTrue(singleNode.hasNode(1)),
            () -> assertFalse(singleNode.hasNode(0))
        );
    }

    @Test
    void shortestPath() {
        System.out.println(complex);

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