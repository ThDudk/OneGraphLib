package io.github.thdudk.algorithms;

import io.github.thdudk.builders.PathGraphBuilder;
import io.github.thdudk.builders.PathGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.BreadthFirstIterator;

import java.awt.*;
import java.util.*;

public class BreadthFirstShortestPathAlgorithm implements ShortestPathAlgorithm {
    @Override
    public <N> Optional<PathGraph<N>> shortestPath(Graph<N> graph, NodeID start, NodeID end) {
        Map<NodeID, NodeID> parentTree = new HashMap<>();
        BreadthFirstIterator iterator = new BreadthFirstIterator(graph, start);

        parentTree.put(iterator.next(), null); // store the root value (from)
        while(iterator.hasNext()) {
            NodeID next = iterator.next();
            parentTree.put(next, iterator.getParent());

            if(next.equals(end)) {
                return Optional.of(backtrack(graph, start, end, parentTree));
            }
        }
        // there is no path from "from" to "to"
        return Optional.empty();
    }

    private <N> PathGraph<N> backtrack(Graph<N> graph, NodeID start, NodeID end, Map<NodeID, NodeID> parentTree) {
        NodeID curr = end;
        PathGraphBuilder<N> pathBuilder = new PathGraphBuilderImpl<>();
        pathBuilder.addNode(graph.getNodeData(curr));

        while(true) {
            curr = parentTree.get(curr);
            pathBuilder.addNode(graph.getNodeData(curr));

            if(curr.equals(start)) {
                return pathBuilder.build();
            }
        }
    }
}
