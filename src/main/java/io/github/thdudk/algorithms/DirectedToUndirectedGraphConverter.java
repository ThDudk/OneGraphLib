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
        for(NodeID node : directed.getNodes()) {
            for(NodeID neighbour : directed.getNeighbours(node)) {
                // continue if the edge is undirected
                if(directed.getNeighbours(neighbour).contains(node)) continue;

                // add an opposite edge for all directed edges
                Collection<EdgeID> edgesBetween = directed.getEdgesBetween(node, neighbour);
                short occurrenceNum = (short) (edgesBetween.size() + 1);

                for(EdgeID edgeID : edgesBetween) {
                    // use a generated edge for the edgeID
                    builder.addDirEdge(neighbour, node, new GeneratedEdgeID(node, neighbour, occurrenceNum));
                    occurrenceNum++;
                }
            }
        }

        return builder.build();
    }
}
