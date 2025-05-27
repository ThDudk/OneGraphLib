package io.github.thdudk.algorithms;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.thdudk.GraphUtils;
import io.github.thdudk.TestGraphs;
import io.github.thdudk.builders.paths.PathBuilder;
import io.github.thdudk.builders.paths.PathBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.graphs.unweighted.PathGraphImpl;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.serialization.GraphVizExporter;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShortestPathAlgorithms {

    @Test
    void shortestPath() throws JsonProcessingException {
        Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);
        PathGraph<Integer> shortestPath = pathGraphFromNodeDataList(graph, List.of(1, 2, 3, 5, 6, 7, 10));
        NodeID start = graph.anyNodeIdWithData(1);
        NodeID end = graph.anyNodeIdWithData(10);

        Graph<Integer> result = new BreadthFirstShortestPathAlgorithm().shortestPath(graph, start, end).orElseThrow();

        assertAll(
            () -> assertEquals(new HashSet<>(shortestPath.getNodeDescriptors()), new HashSet<>(result.getNodeDescriptors())),
            () -> assertEquals(new HashSet<>(shortestPath.getEdgeDescriptors()), new HashSet<>(result.getEdgeDescriptors()))
        );
    }

    @Test
    void returnsEmptyOptionalForImpossiblePaths() {
        Graph<Integer> graph = TestGraphs.getCSESShortestRoutesIUnweightedGraph(1);
        NodeID start = graph.anyNodeIdWithData(5);
        NodeID end = graph.anyNodeIdWithData(1);

        assertTrue(new BreadthFirstShortestPathAlgorithm().shortestPath(graph, start, end).isEmpty());
    }

    static <N> PathGraph<N> pathGraphFromNodeDataList(Graph<N> graph, List<N> nodes) {
        return pathGraphFromNodeList(graph, nodes.stream().map(graph::anyNodeIdWithData).toList());
    }
    static <N> PathGraph<N> pathGraphFromNodeList(Graph<N> graph, List<NodeID> nodes) {
        PathBuilder<N> builder = new PathBuilderImpl<>();
        builder.addStartNode(nodes.getFirst(), graph.getNodeData(nodes.getFirst()));
        for(int startIdx = 0, endIDx = 1; endIDx < nodes.size(); startIdx++, endIDx++) {
            NodeID startId = nodes.get(startIdx);
            NodeID endId = nodes.get(endIDx);
            builder.nextNode(endId, graph.getNodeData(endId), graph.getEdgesBetween(startId, endId).stream().findAny().orElseThrow());
        }
        return builder.build();
    }
}