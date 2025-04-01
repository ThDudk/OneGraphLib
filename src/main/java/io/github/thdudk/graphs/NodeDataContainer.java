package io.github.thdudk.graphs;

import io.github.thdudk.ids.NodeID;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.*;
import java.util.Map.Entry;

@EqualsAndHashCode
@ToString
public class NodeDataContainer<N> {
    private final Map<NodeID, N> nodeData;
    private final Map<N, List<NodeID>> dataToID;

    public NodeDataContainer(Map<NodeID, N> nodeData) {
        this.nodeData = nodeData;

        dataToID = new HashMap<>();
        for(Entry<NodeID, N> entry : nodeData.entrySet()) {
            dataToID.putIfAbsent(entry.getValue(), new ArrayList<>());
            dataToID.get(entry.getValue()).add(entry.getKey());
        }
    }

    public N getData(NodeID id) {
        return nodeData.get(id);
    }
    public Collection<NodeID> getIDs(N data) {
        if(!dataToID.containsKey(data)) throw new RuntimeException("Data not found: " + data);

        return dataToID.get(data);
    }
}
