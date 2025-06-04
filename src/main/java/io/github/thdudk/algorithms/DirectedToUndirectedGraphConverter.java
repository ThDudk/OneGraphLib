package io.github.thdudk.algorithms;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.GeneratedEdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.Collection;

public class DirectedToUndirectedGraphConverter {
    public <N> Graph<N> convert(Graph<N> directed) {
        ExplicitIdsGraphBuilder<N> builder = new ExplicitIdsGraphBuilderImpl<>();

        // carry over all nodes
        for(NodeID node : directed.getNodes()) {
            builder.addNode(node, directed.getNodeData(node));
        }

        // convert all directed edges into undirected edges
        for(Graph.EdgeDescriptor edge : directed.getEdgeDescriptors()) {
            builder.addDirEdge(edge.start(), edge.end(), edge.id());

            // ensure the edge does not have an opposite edge (meaning it's not directed)
            if(!directed.getEdgesBetween(edge.end(), edge.start()).isEmpty()) continue;

            // add an opposite edge (with a generated ID)
            builder.addDirEdge(edge.end(), edge.start(), new GeneratedEdgeID(edge.start(), edge.end(), (short) 1));
        }

        return builder.build();
    }
}
