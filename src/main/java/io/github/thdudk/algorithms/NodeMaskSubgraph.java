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

        for(Graph.EdgeDescriptor edge : graph.getEdgeDescriptors()) {
            if(!mask.contains(edge.end())) continue;

            builder.addDirEdge(edge.start(), edge.end(), edge.id());
        }

        Graph<N> subgraph = builder.build();
        subgraph.addSatisfiedRestrictions(graph.getRestrictions());

        return subgraph;
    }
}
