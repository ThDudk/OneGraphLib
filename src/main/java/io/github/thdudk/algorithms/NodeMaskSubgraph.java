package io.github.thdudk.algorithms;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.builders.unweighted.GraphBuilder;
import io.github.thdudk.builders.unweighted.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;

public class NodeMaskSubgraph {
    /// @param mask nodes to keep
    public <N> Graph<N> subgraph(Graph<N> graph, Collection<NodeID> mask) {
        ExplicitIdsGraphBuilder<N> builder = new ExplicitIdsGraphBuilderImpl<>();

        if(!graph.getNodes().containsAll(mask))
            throw new RuntimeException("mask contains nodes outside of the graph");

        for(NodeID id : mask) {
            builder.addNode(id, graph.getNodeData(id));
        }

        for(NodeID node : mask) {
            for(NodeID neighbour : graph.getNeighbours(node)) {
                for(EdgeID edgeID : graph.getEdgesBetween(node, neighbour)) {
                    if(!mask.contains(neighbour)) continue;

                    builder.addDirEdge(node, neighbour, edgeID);
                }
            }
        }

        Graph<N> subgraph = builder.build();
        subgraph.addSatisfiedRestrictions(graph.getRestrictions());

        return subgraph;
    }
}
