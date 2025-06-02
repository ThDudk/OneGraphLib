package io.github.thdudk.iterators;

import io.github.thdudk.ids.NodeID;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface GraphNodeIterator extends Iterator<NodeID> {
    /// @return the parent of the last item polled from {@link #next()}
    /// @throws NoSuchElementException if `next()` has not been called.
    NodeID getParent();
}