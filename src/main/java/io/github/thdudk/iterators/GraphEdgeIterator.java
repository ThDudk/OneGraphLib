package io.github.thdudk.iterators;

import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;

import java.util.Iterator;

public interface GraphEdgeIterator extends Iterator<EdgeID> {
    /// @return the start node of the edge last returned by {@link #next()}.
    /// @throws java.util.NoSuchElementException if {@link #next()} has not been called or last threw NoSuchElementException
    /// (there is no start node to get)
    NodeID getStart();

    /// @return the end node of the edge last returned by {@link #next()}.
    /// @throws java.util.NoSuchElementException if {@link #next()} has not been called or last threw NoSuchElementException
    /// (there is no end node to get)
    NodeID getEnd();
}
