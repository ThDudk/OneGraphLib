package io.github.thdudk.iterators;

import java.util.Iterator;

public interface GraphIterator<N> extends Iterator<N> {
    N getParent();
}