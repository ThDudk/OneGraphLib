package io.github.thdudk.ids;

public record IntNodeID(int id) implements NodeID {
    public IntNodeID incremented() {
        return new IntNodeID(id + 1);
    }
}
