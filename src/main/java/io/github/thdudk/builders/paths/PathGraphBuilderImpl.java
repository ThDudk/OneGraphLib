package io.github.thdudk.builders.paths;

import io.github.thdudk.builders.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.graphs.unweighted.ListPathGraphImpl;
import io.github.thdudk.ids.NodeID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class PathGraphBuilderImpl<N> extends GraphBuilderImpl<N> implements PathGraphBuilder<N> {
    private NodeID root;
    @Override
    public void addDirEdge(NodeID root, NodeID neighbour) {
        throw new UnsupportedOperationException();
    }

    private List<NodeID> asList() {
        // return early if there are no nodes
        if(nodeData.isEmpty()) return Collections.emptyList();

        List<NodeID> list = new ArrayList<>();
        NodeID curr = root;
        list.add(curr);

        while(true) {
            Set<NodeID> neighbours = adjacencyList.get(curr);
            if(neighbours.isEmpty()) return list;

            curr = neighbours.stream().findAny().orElseThrow();
            list.add(curr);
        }
    }

    @Override
    public PathGraph<N> build() {
        return new ListPathGraphImpl<>(getRestrictions(), asList(), nodeData);
    }
}
