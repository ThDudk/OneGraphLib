package io.github.thdudk.iterators.node;

import io.github.thdudk.TestGraphs;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class DepthFirstIteratorTest {

    @Test
    void iteratesOnADepthFirstBasis() {
        DistinctDataGraphBuilder<Integer> builder = new DistinctDataGraphBuilderImpl<>();
        builder.addNode(1);
        builder.addNode(2);
        builder.addNode(3);
        builder.addNode(4);
        builder.addNode(5);

        builder.addDirEdge(1, 2);
        builder.addDirEdge(2, 3);

        builder.addDirEdge(1, 4);
        builder.addDirEdge(4, 5);

        Graph<Integer> graph = builder.build();

        DepthFirstIterator iterator = new DepthFirstIterator(graph, graph.anyNodeIdWithData(1));
        iterator.next(); // read the root

        Set<Integer> path = new HashSet<>();
        path.add(graph.getNodeData(iterator.next()));
        path.add(graph.getNodeData(iterator.next()));

        assertTrue(new HashSet<>(path).equals(Set.of(2, 3)) || new HashSet<>(path).equals(Set.of(4, 5)));
    }
}
