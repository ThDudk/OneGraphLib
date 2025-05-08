package io.github.thdudk.algorithms;

import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;

public class DirectedToUndirectedGraphConverter {
    public <N> Graph<N> convert(Graph<N> directed) {
        GraphBuilder<N> builder = new GraphBuilderImpl<>();

        // carry over all nodes
        for(NodeID node : directed.getNodes()) {
            builder.addNode(node, directed.getNodeData(node));
        }

        // convert all directed edges into undirected edges
        for(NodeID node : directed.getNodes()) {
            for(NodeID inNeighbour : directed.getInNeighbours(node)) {
                builder.addUndirEdge(node, inNeighbour);
            }
            for(NodeID outNeighbour : directed.getNeighbours(node)) {
                builder.addUndirEdge(node, outNeighbour);
            }
        }

        Graph<N> graph = builder.build();
        graph.addSatisfiedRestrictions(directed.getRestrictions());

        return graph;
    }
}
