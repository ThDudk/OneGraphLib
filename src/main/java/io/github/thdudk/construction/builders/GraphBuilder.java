package io.github.thdudk.construction.builders;

import io.github.thdudk.graphs.unweighted.Graph;

import java.util.List;

public interface GraphBuilder<N> {
    GraphBuilder<N> addNode(N node);
    GraphBuilder<N> addOutNeighbor(N root, N neighbor);
    default GraphBuilder<N> addNeighbor(N node1, N node2) {
        addOutNeighbor(node1, node2);
        addOutNeighbor(node2, node1);
        return this;
    }
    default GraphBuilder<N> addNeighborChain(List<N> nodes, boolean undirected) {
        N prev = null;
        for(N node : nodes) {
            if(prev == null) addNode(node);
            else if(undirected) addNeighbor(prev, node);
            else addOutNeighbor(prev, node);
            prev = node;
        }
        return this;
    }
    Graph<N> build();
}
