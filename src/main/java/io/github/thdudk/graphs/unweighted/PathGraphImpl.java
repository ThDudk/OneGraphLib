package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.NodeDataContainer;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.DirectedRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import io.github.thdudk.restrictions.MaxDegreeRestriction;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class PathGraphImpl<N> extends AbstractRestrictedGraph<N> implements PathGraph<N> {
    private final List<NodeID> path;
    private final NodeDataContainer<N> nodeData;

    public PathGraphImpl(Collection<GraphRestriction<N>> restrictions, List<NodeID> path, Map<NodeID, N> nodeData) {
        super(restrictions);
        addRestriction(new MaxDegreeRestriction<>(2));
        addRestriction(new DirectedRestriction<>());
        this.path = path;

        this.nodeData = new NodeDataContainer<>(nodeData);

        throwIfRestrictionsNotSatisfied(this);
    }

    @Override
    public NodeID getRoot() {
        return path.getFirst();
    }
    @Override
    public NodeID getEnd() {
        return path.getLast();
    }
    @Override
    public List<NodeID> asList() {
        return Collections.unmodifiableList(path);
    }

    @Override
    public Collection<NodeID> getNodes() {
        return path;
    }
    @Override
    public Collection<NodeID> getNeighbours(NodeID root) {
        return List.of(path.get(path.indexOf(root) + 1));
    }

    @Override
    public N getNodeData(NodeID id) {
        return nodeData.getData(id);
    }
    @Override
    public Collection<NodeID> nodeIdsWithData(N data) {
        return nodeData.getIDs(data);
    }
}
