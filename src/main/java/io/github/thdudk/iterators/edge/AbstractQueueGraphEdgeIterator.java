package io.github.thdudk.iterators.edge;

import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.ids.EdgeID;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphEdgeIterator;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

@RequiredArgsConstructor
public abstract class AbstractQueueGraphEdgeIterator implements GraphEdgeIterator {
    private final Set<EdgeID> visited = new HashSet<>();
    private final Queue<Graph.EdgeDescriptor> queue;
    private final Graph<?> graph;
    private Graph.EdgeDescriptor prevEdge;

    public AbstractQueueGraphEdgeIterator(Queue<Graph.EdgeDescriptor> queue, Graph<?> graph, NodeID root) {
        this(queue, graph);

        if(graph.getDegree(root) == 0) throw new RuntimeException("root node has no edges extending from it. Root: " + root);

        queueAllUnvisitedEdgesFrom(root);
    }

    @Override
    public boolean hasNext() {
        removeVisitedFrontEdges();
        return !queue.isEmpty();
    }
    @Override
    public EdgeID next() {
        removeVisitedFrontEdges(); // technically not needed as hasNext() also calls this
        if(!hasNext()) {
            prevEdge = null; // so getStart() and getEnd() throw NoSuchElementException
            throw new NoSuchElementException();
        }

        Graph.EdgeDescriptor edge = queue.poll();
        assert edge != null; // to satisfy the compiler
        prevEdge = edge; // store the parent for getParent()
        visited.add(edge.id());

        queueAllUnvisitedEdgesFrom(edge.end());

        return edge.id();
    }
    private void queueAllUnvisitedEdgesFrom(NodeID start) {
        for(NodeID end : graph.getNeighbours(start)) {
            for (EdgeID newEdge : graph.getEdgesBetween(start, end)) {
                if (visited.contains(newEdge)) continue;

                queue.offer(new Graph.EdgeDescriptor(start, end, newEdge));
            }
        }
    }

    @Override
    public NodeID getStart() {
        if(prevEdge == null) throw new NoSuchElementException();

        return prevEdge.start();
    }
    @Override
    public NodeID getEnd() {
        if(prevEdge == null) throw new NoSuchElementException();

        return prevEdge.end();
    }

    /// removes all visited edges from the front of the queue
    protected void removeVisitedFrontEdges() {
        while(!queue.isEmpty() && visited.contains(queue.peek().id())) {
            queue.poll();
        }
    }
}
