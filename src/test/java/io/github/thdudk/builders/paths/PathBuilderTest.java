package io.github.thdudk.builders.paths;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.IntNodeID;
import io.github.thdudk.ids.LongEdgeID;
import io.github.thdudk.serialization.GraphVizExporter;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class PathBuilderTest {

    @Test
    void removeLast() {
        PathBuilder<Integer> builder = new PathBuilderImpl<>();

        builder.addStartNode(new IntNodeID(1), 1);
        builder.nextNode(new IntNodeID(2), 2, new LongEdgeID(1));

        assertAll(
            () -> assertEquals(new Graph.NodeDescriptor<>(new IntNodeID(2), 2), builder.removeLast()),
            () -> System.out.println(builder),
            () -> assertEquals(new Graph.NodeDescriptor<>(new IntNodeID(1), 1), builder.removeLast()),
            () -> System.out.println(builder),
            () -> assertThrows(NoSuchElementException.class, builder::removeLast)
        );
    }
}