package io.github.thdudk.builders.paths;

import io.github.thdudk.builders.unweighted.ExplicitIdsGraphBuilder;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.graphs.unweighted.PathGraph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.restriction_containers.MutableRestrictionContainer;

public interface PathBuilder<N> extends MutableRestrictionContainer<N> {
    void addStartNode(NodeID node, N data);
    void nextNode(NodeID node, N data, EdgeID edgeID);
    Graph.NodeDescriptor<N> removeLast();

    PathGraph<N> build();
}
