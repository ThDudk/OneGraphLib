package io.github.thdudk.iterators;

import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.Iterator;

public interface GraphEdgeIterator extends Iterator<EdgeID> {
    NodeID getStart();
    NodeID getEnd();
}
