package io.github.thdudk.builders.unweighted;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.restriction_containers.MutableRestrictionContainer;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Graph builder that automatically handles assigning node IDs.
 * @param <N> Node data type
 */
public interface GraphBuilder<N> extends MutableRestrictionContainer<N> {
    NodeID addNode(N node);
    EdgeID addDirEdge(NodeID root, NodeID neighbour);
    /// Pair(1 -> 2, 2 -> 1)
    default Pair<EdgeID, EdgeID> addUndirEdge(NodeID node1, NodeID node2) {
        return Pair.of(addDirEdge(node1, node2), addDirEdge(node2, node1));
    }

    Graph<N> build();

    /// Constructs a builder from `graph`
    static <N> GraphBuilder<N> of(Graph<N> graph) {
        return new GraphBuilderImpl<>(graph);
    }
}
