package io.github.thdudk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.builders.GraphBuilder;
import io.github.thdudk.builders.GraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class TestUtils {
    // used quickly to generate jackson serialized instances of the cses shortest routes I graphs
    public static void main(String[] args) throws IOException {
//        doTheThing(6);
    }
    static void doTheThing(int num) throws IOException {
        // read in graph
        GraphBuilder<String> builder = new GraphBuilderImpl<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/raw/cses-problem-sets/shortest-routes-I/" + num + ".in"));
        reader.readLine();

        for(String[] line : reader.lines().map(a -> a.split(" ")).toList()) {
            builder.addDirEdge(line[0], line[1]);
        }
        Graph<String> graph = builder.build();

        // convert to JSON format
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/test/resources/jackson-serialized/cses-problem-sets/shortest-routes-I/unweighted/" + num + ".json"), graph);
    }
}
