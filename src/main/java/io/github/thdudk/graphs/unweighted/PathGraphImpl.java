package io.github.thdudk.graphs.unweighted;

import com.fasterxml.jackson.databind.deser.std.CollectionDeserializer;
import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphNodeIterator;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.restrictions.GraphRestriction;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.*;

@Getter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PathGraphImpl<N> extends AdjacencyListGraphImpl<N> implements PathGraph<N> {
    private final NodeID root;
    private final NodeID end;

    public PathGraphImpl(
        Collection<GraphRestriction<N>> graphRestrictions,
        Map<NodeID, Collection<EdgeEndpointPair>> adjacencyList,
        Map<NodeID, N> nodeData,
        NodeID root,
        NodeID end
    ) {
        super(graphRestrictions, adjacencyList, nodeData);

        this.root = root;
        this.end = end;
    }

    @Override
    public List<NodeID> asList() {
        GraphNodeIterator iterator = new DepthFirstIterator(this, root);
        List<NodeID> list = new ArrayList<>();

        while(iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }
}
