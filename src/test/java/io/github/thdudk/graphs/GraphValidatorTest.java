package io.github.thdudk.graphs;

import io.github.thdudk.construction.builders.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GraphValidatorTest {
    Graph<Integer> graph = new GraphBuilderImpl<Integer>()
        .addDirNeighborChain(List.of(1, 2, 3, 4)).build();

    @Test
    void requireContained() {
        List<Integer> nullList = new ArrayList<>();
        nullList.add(null);
        assertAll(
            () -> assertThrows(RuntimeException.class, () -> GraphValidator.requireContained(List.of(0), graph)),
            () -> assertThrows(RuntimeException.class, () -> GraphValidator.requireContained(nullList, graph)),
            () -> assertThrows(RuntimeException.class, () -> GraphValidator.requireContained(List.of(0, 1), graph)),
            () -> assertDoesNotThrow(() -> GraphValidator.requireContained(List.of(1), graph)),
            () -> assertDoesNotThrow(() -> GraphValidator.requireContained(List.of(), graph))
        );
    }
}