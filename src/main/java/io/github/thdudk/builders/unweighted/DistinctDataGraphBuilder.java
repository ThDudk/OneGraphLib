package io.github.thdudk.builders.unweighted;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public interface DistinctDataGraphBuilder<N> extends GraphBuilder<N> {
    void addDirEdge(N start, N end);
    default void addUndirEdge(N node1, N node2) {
        addDirEdge(node1, node2);
        addDirEdge(node2, node1);
    }
    default void addDirEdgeChain(List<N> nodes) {
        Iterator<N> iterator = nodes.iterator();

        if(!iterator.hasNext()) return;

        N prev = iterator.next();
        addNode(prev); // in case the collection has only 1 item

        while(iterator.hasNext()) {
            N curr = iterator.next();
            addDirEdge(prev, curr);
            prev = curr;
        }
    }
    default void addUndirEdgeChain(List<N> nodes) {
        Iterator<N> iterator = nodes.iterator();

        if(!iterator.hasNext()) return;

        N prev = iterator.next();
        addNode(prev); // in case the collection has only 1 item

        while(iterator.hasNext()) {
            N curr = iterator.next();
            addUndirEdge(prev, curr);
            prev = curr;
        }
    }
}
