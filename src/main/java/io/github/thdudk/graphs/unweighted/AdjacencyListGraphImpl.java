package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.thdudk.AbstractRestrictedGraph;
import io.github.thdudk.graphs.GraphValidator;
import io.github.thdudk.graphs.NodeDataContainer;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AdjacencyListGraphImpl<N> extends AbstractRestrictedGraph<N> implements Graph<N> {
    private final Map<NodeID, Set<NodeID>> adjacencyList;
    private final NodeDataContainer<N> nodeData;

    @JsonCreator
    public AdjacencyListGraphImpl(
        @JsonProperty("restrictions") Collection<GraphRestriction<N>> restrictions,
        @JsonProperty("unweightedAdjacencyList") Map<NodeID, Set<NodeID>> adjacencyList,
        @JsonProperty("nodeDataMap") Map<NodeID, N> nodeData
    ) {
        super(restrictions);
        this.adjacencyList = adjacencyList;
        this.nodeData = new NodeDataContainer<>(nodeData);

        throwIfRestrictionsNotSatisfied(this);
    }

    @Override
    public Collection<NodeID> getNodes() {
        return Set.copyOf(adjacencyList.keySet());
    }
    @Override
    public Collection<NodeID> getNeighbours(NodeID root) {
        GraphValidator.requireContained(List.of(root), this);

        return Set.copyOf(adjacencyList.get(root));
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
