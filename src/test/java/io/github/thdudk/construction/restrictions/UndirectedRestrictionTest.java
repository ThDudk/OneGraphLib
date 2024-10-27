package io.github.thdudk.construction.restrictions;

import io.github.thdudk.construction.GraphFactory;
import lombok.val;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UndirectedRestrictionTest {
    @Test
    void unweightedNoMisfires() {
        assertAll(
            () -> assertDoesNotThrow(
                () -> new GraphFactory<>().undirected().builder().addNeighbor(1, 2)),
            () -> assertDoesNotThrow(
                () -> new GraphFactory<>().undirected().builder().addNeighbor(2, 3)),
            () -> assertDoesNotThrow(
                () -> new GraphFactory<>().undirected().builder().addNeighbor(3, 1)),
            () -> assertDoesNotThrow(
                () -> new GraphFactory<>().undirected().builder().addOutNeighbor(3, 1).addNode(2).addOutNeighbor(1, 3).build()),
            () -> assertDoesNotThrow(
                () -> new GraphFactory<>().undirected().builder().addOutNeighbor(1, 0)),
            () -> assertDoesNotThrow(
                () -> new GraphFactory<>().undirected().builder().addOutNeighbor(0, 1))
        );
    }

    @Test
    void unweightedAndFiresWhenViolated() {
        assertAll(
            () -> assertThrows(Exception.class,
                () -> new GraphFactory<>().undirected().builder().addOutNeighbor(1, 2).build()),
            () -> assertThrows(Exception.class,
                () -> new GraphFactory<>().undirected().builder().addOutNeighbor(3, 1).addOutNeighbor(3, 2)),
            () -> assertThrows(Exception.class,
                () -> new GraphFactory<>().undirected().builder().addOutNeighbor(1, 2).addOutNeighbor(3, 2)),
            () -> assertThrows(Exception.class,
                () -> new GraphFactory<>().undirected().builder().addNeighbor(2, 1).addOutNeighbor(2, 3).build())
        );
    }
}