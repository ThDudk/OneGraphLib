package io.github.thdudk.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record LongEdgeID(long id) implements EdgeID {
    public LongEdgeID incremented() {
        return new LongEdgeID(id + 1);
    }
}
