package io.github.thdudk.builders.unweighted;

import io.github.thdudk.ids.NodeID;
import io.github.thdudk.restrictions.DistinctDataRestriction;

import java.util.HashMap;
import java.util.Map;

public class DistinctDataGraphBuilderImpl<N> extends GraphBuilderImpl<N> implements DistinctDataGraphBuilder<N> {
    private final Map<N, NodeID> dataToId = new HashMap<>();

    public DistinctDataGraphBuilderImpl() {
        super();
        addRestriction(new DistinctDataRestriction<>());
    }

    @Override
    public NodeID addNode(N data) {
        if(contains(data)) throw new RuntimeException("Expected distinct data but got a duplicate.");

        NodeID id = super.addNode(data);
        dataToId.put(data, id);

        return id;
    }
    private boolean contains(N data) {
        return dataToId.containsKey(data);
    }

    @Override
    public void addDirEdge(N start, N end) {
        // add start and end if they're not already contained
        if(!contains(start)) addNode(start);
        if(!contains(end)) addNode(end);

        // get their ids
        NodeID startID = idOf(start);
        NodeID endID = idOf(end);

        addDirEdge(startID, endID);
    }

    private NodeID idOf(N data) {
        return dataToId.get(data);
    }
}
