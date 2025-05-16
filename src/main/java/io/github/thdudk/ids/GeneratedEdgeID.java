package io.github.thdudk.ids;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public record GeneratedEdgeID(NodeID start, NodeID end, short occurrenceNumber) implements EdgeID {
}
