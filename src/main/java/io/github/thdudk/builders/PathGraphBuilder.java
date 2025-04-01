package io.github.thdudk.builders;

import io.github.thdudk.graphs.unweighted.PathGraph;

public interface PathGraphBuilder<N> extends GraphBuilder<N> {
    PathGraph<N> build();
}
