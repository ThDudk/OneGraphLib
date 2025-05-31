package io.github.thdudk.ids;

public record LongEdgeID(long id) implements EdgeID {
    public LongEdgeID incremented() {
        return new LongEdgeID(id + 1);
    }
}
