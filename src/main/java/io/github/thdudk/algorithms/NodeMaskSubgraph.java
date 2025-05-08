package io.github.thdudk.algorithms;

import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;

public class NodeMaskSubgraph {
    /// @param mask nodes to keep
    public <N> Graph<N> subgraph(Graph<N> graph, Collection<NodeID> mask) {
        GraphBuilder<N> builder = new GraphBuilderImpl<>();

        if(!graph.getNodes().containsAll(mask))
            throw new RuntimeException("mask contains nodes outside of the graph");

        for(NodeID id : mask) {
            builder.addNode(id, graph.getNodeData(id));
        }

        for(NodeID node : mask) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                if(!mask.contains(neighbour)) continue;

                builder.addDirEdge(node, neighbour);
            }
        }

        Graph<N> subgraph = builder.build();
        subgraph.addSatisfiedRestrictions(graph.getRestrictions());

        return subgraph;
    }
}
