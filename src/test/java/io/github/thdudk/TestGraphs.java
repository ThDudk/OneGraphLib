package io.github.thdudk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.graphs.unweighted.Graph;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.io.IOException;

public abstract class TestGraphs {
    static final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows(IOException.class)
    /// @param num graph number (1-5 inclusive)
    public static Graph<Integer> getCSESShortestRoutesIGraphNum( int num) {
        return mapper.readValue(
            new FileReader("src/test/resources/jackson-serialized/cses-problem-sets/shortest-routes-I/unweighted/" + num + ".json"),
            new TypeReference<>() {}
        );
    }
}
