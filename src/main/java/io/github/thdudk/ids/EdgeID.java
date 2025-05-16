package io.github.thdudk.ids;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
    @JsonSubTypes.Type(value = LongEdgeID.class, name = "LONG"),
    @JsonSubTypes.Type(value = GeneratedEdgeID.class, name = "GENERATED")
})
public interface EdgeID {}