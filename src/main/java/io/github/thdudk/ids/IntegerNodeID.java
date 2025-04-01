package io.github.thdudk.ids;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.databind.util.StdConverter;

import java.io.IOException;

@JsonDeserialize(converter = IntegerNodeID.converter.class, keyUsing = IntegerNodeID.keyDeserializer.class)
@JsonSerialize(using = IntegerNodeID.serializer.class)
public record IntegerNodeID(int id) implements NodeID {
    public static class keyDeserializer extends KeyDeserializer {
        @Override
        public Object deserializeKey(String key, DeserializationContext ctxt) throws IOException {
            return new IntegerNodeID(Integer.parseInt(key));
        }
    }
    public static class converter extends StdConverter<Integer, IntegerNodeID> {
        @Override
        public IntegerNodeID convert(Integer value) {
            return new IntegerNodeID(value);
        }
    }
    public static class serializer extends StdSerializer<IntegerNodeID> {
        public serializer() {
            super(IntegerNodeID.class);
        }

        @Override
        public void serialize(IntegerNodeID value, JsonGenerator gen, SerializerProvider provider) throws IOException {
            gen.writeNumber(value.id);
        }
    }

    @Override
    public NodeID incremented() {
        if (id == Integer.MAX_VALUE) throw new RuntimeException("Ran out of possible Node IDs");
        return new IntegerNodeID(id + 1);
    }

    @Override
    public String toString() {
        return Integer.toString(id);
    }
}
