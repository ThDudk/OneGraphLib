package io.github.thdudk.builders.unweighted;

import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public interface DistinctDataGraphBuilder<N> extends GraphBuilder<N> {
    /// @return the NodeID of the data. If the node was already contained, returns the existing nodeID with `data`
    @Override
    NodeID addNode(N node);

    /// adds a directed edge from start to end
    ///
    /// If start or end are not present in the graph, they will be created.
    EdgeID addDirEdge(N start, N end);
    /// Adds an undirected edge between node1 and node2.
    ///
    /// @return a pair of edges where the left is the edgeID from node1 to node2 and the right is the edgeID from node2 to node1
    default Pair<EdgeID, EdgeID> addUndirEdge(N node1, N node2) {
        return Pair.of(addDirEdge(node1, node2), addDirEdge(node2, node1));
    }
    /// Creates a chain of directed edges connecting all the given nodes.
    ///
    /// @return a list of EdgeIDs representing the edgeID between each node.
    /// Each edge ID corresponds to the edge between `nodes.get(i)` and `nodes.get(i + 1)`
    default List<EdgeID> addDirEdgeChain(List<N> nodes) {
        Iterator<N> iterator = nodes.iterator();
        List<EdgeID> edgeIds = new ArrayList<>(); // for the return value

        if(!iterator.hasNext()) return List.of();

        N prev = iterator.next();
        addNode(prev); // in case the collection has only 1 item

        while(iterator.hasNext()) {
            N curr = iterator.next();
            edgeIds.add(addDirEdge(prev, curr));
            prev = curr;
        }
        return edgeIds;
    }

    /// Creates a chain of undirected edges connecting all the given nodes.
    ///
    /// @return a list of edge ID pairs representing the undirected edges between each node.
    /// Each pair corresponds to the edge ids between `nodes.get(i)` and `nodes.get(i + 1)`.
    /// The left value of each pair is the edge id between `nodes.get(i)` and `nodes.get(i + 1)`
    /// and the right is the opposite.
    default List<Pair<EdgeID, EdgeID>> addUndirEdgeChain(List<N> nodes) {
        Iterator<N> iterator = nodes.iterator();
        List<Pair<EdgeID, EdgeID>> edgeIdPairs = new ArrayList<>();

        if(!iterator.hasNext()) return List.of();

        N prev = iterator.next();
        addNode(prev); // in case the collection has only 1 item

        while(iterator.hasNext()) {
            N curr = iterator.next();
            edgeIdPairs.add(addUndirEdge(prev, curr));
            prev = curr;
        }

        return edgeIdPairs;
    }
}
