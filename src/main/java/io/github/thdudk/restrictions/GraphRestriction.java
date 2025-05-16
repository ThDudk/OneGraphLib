package io.github.thdudk.restrictions;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import io.github.thdudk.graphs.unweighted.Graph;

@JsonTypeInfo(use= JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = DirectedRestriction.class, name = "DIRECTED"),
    @JsonSubTypes.Type(value = UndirectedRestriction.class, name = "UNDIRECTED"),
    @JsonSubTypes.Type(value = MaxDegreeRestriction.class, name = "MAX_DEGREE"),
    @JsonSubTypes.Type(value = NoMultiEdgesRestriction.class, name = "NO_MULTI_EDGES"),
    @JsonSubTypes.Type(value = WeaklyConnectedGraphRestriction.class, name = "WEAKLY_CONNECTED"),
})
public interface GraphRestriction<N> {
    boolean isSatisfied(Graph<N> graph);
}
