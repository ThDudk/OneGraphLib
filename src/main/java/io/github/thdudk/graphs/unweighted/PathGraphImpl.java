package io.github.thdudk.graphs.unweighted;

import io.github.thdudk.ids.NodeID;
import io.github.thdudk.iterators.GraphIterator;
import io.github.thdudk.iterators.node.DepthFirstIterator;
import io.github.thdudk.restrictions.DirectedRestriction;
import io.github.thdudk.restrictions.GraphRestriction;
import io.github.thdudk.restrictions.MaxDegreeRestriction;
import io.github.thdudk.restrictions.NoMultiEdgesRestriction;
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
        Map<NodeID, Set<EdgeEndpointPair>> adjacencyList,
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
        GraphIterator iterator = new DepthFirstIterator(this, root);
        List<NodeID> list = new ArrayList<>();

        while(iterator.hasNext()) {
            list.add(iterator.next());
        }

        return list;
    }
}
