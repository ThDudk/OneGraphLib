package io.github.thdudk.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public interface GraphIterator<N> extends Iterator<N> {
    /// @return the parent of the last item polled from {@link #next()}
    /// @throws NoSuchElementException if `next()` has not been called.
    N getParent();
}