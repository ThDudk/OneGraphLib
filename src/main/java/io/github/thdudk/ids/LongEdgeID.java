package io.github.thdudk.ids;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode
@RequiredArgsConstructor
public class LongEdgeID implements EdgeID {
    private final long id;

    @Override
    public EdgeID incremented() {
        if(id == Long.MAX_VALUE) throw new RuntimeException("Ran out of possible EdgeIDs");
        return new LongEdgeID(id + 1);
    }

    @Override
    public String toString() {
        return Long.toString(id);
    }
}
