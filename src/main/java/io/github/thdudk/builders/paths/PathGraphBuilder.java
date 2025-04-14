package io.github.thdudk.builders.paths;

import io.github.thdudk.builders.GraphBuilder;
import io.github.thdudk.graphs.unweighted.PathGraph;

public interface PathGraphBuilder<N> extends GraphBuilder<N> {
    PathGraph<N> build();
}
