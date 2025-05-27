package io.github.thdudk.algorithms;

import io.github.thdudk.builders.paths.PathBuilder;
import io.github.thdudk.builders.paths.PathBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.node.BreadthFirstIterator;

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
        List<NodeID> path = new ArrayList<>();
        path.add(curr);

        while(!curr.equals(start)) {
            curr = parentTree.get(curr);
            path.add(curr);
        }

        path = path.reversed();

        PathBuilder<N> pathBuilder = new PathBuilderImpl<>();
        pathBuilder.addStartNode(path.getFirst(), graph.getNodeData(path.getFirst()));

        for(int i = 1; i < path.size(); i++) {
            EdgeID edge = graph.getAnyEdgeBetween(path.get(i - 1), path.get(i)).orElseThrow();
            pathBuilder.nextNode(path.get(i), graph.getNodeData(path.get(i)), edge);
        }

        return pathBuilder.build();
    }
}
