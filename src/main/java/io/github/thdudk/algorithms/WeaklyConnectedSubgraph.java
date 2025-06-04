package io.github.thdudk.algorithms;

import io.github.thdudk.GraphUtils;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.restrictions.UndirectedRestriction;
import io.github.thdudk.serialization.GraphVizExporter;

import java.util.HashSet;
import java.util.Set;

public class WeaklyConnectedSubgraph {
    public <N> Graph<N> subgraph(Graph<N> graph, NodeID anchor) {
        Graph<N> undirected;
        // convert to an undirected graph
        if(graph.hasRestriction(new UndirectedRestriction<>())) {
            undirected = graph;
        } else {
            undirected = GraphUtils.undirectedToDirectedGraph(graph); // TODO this is a lil sketchy...
        }

        // find all nodes contained in the island
        DepthFirstIterator iterator = new DepthFirstIterator(undirected, anchor);
        Set<NodeID> contained = new HashSet<>();

        while(iterator.hasNext()) {
            NodeID next = iterator.next();
            contained.add(next);
        }

        // create a subgraph including only nodes in the connected subgraph
        return GraphUtils.mask(graph, contained);

    }
}
