package io.github.thdudk.ids;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = IntegerNodeID.class, keyAs = IntegerNodeID.class, keyUsing = IntegerNodeID.keyDeserializer.class)
public interface NodeID {
    @JsonIgnore
    NodeID incremented();
}
