package io.github.thdudk.iterators.edge;

import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.iterators.GraphEdgeIterator;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.serialization.GraphVizExporter;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstEdgeIteratorTest {
    @Test
    void iteratesOnADepthFirstBasis() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);
        builder.addNode(4);
        builder.addNode(5);

        List<EdgeID> path1Edges = builder.addDirEdgeChain(List.of(1, 2, 3));
        List<EdgeID> path2Edges = builder.addDirEdgeChain(List.of(1, 4, 5));

        Graph<Integer> graph = builder.build();

        GraphEdgeIterator iterator = new DepthFirstEdgeIterator(graph, graph.anyNodeIdWithData(1));

        List<EdgeID> path = new ArrayList<>();
        path.add(iterator.next());
        path.add(iterator.next());

        assertTrue(path.equals(path1Edges) || path.equals(path2Edges));
    }
}