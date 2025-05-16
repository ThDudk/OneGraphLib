package io.github.thdudk.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;

public record IntegerNodeID(int id) implements NodeID {
    public IntegerNodeID incremented() {
        return new IntegerNodeID(id + 1);
    }
}
