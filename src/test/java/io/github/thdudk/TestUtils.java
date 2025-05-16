package io.github.thdudk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public abstract class TestUtils {
    // used quickly to generate jackson serialized instances of the CSES Shortest Routes I graphs
    public static void main(String[] args) throws IOException {
        doTheThing(1);
    }
    static void doTheThing(int num) throws IOException {
        // read in graph
        DistinctDataGraphBuilder<String> builder = new DistinctDataGraphBuilderImpl<>();
        BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/raw/cses-problem-sets/shortest-routes-I/" + num + ".in"));
        reader.readLine();

        int lineNum = 0;
        for(String[] line : reader.lines().map(a -> a.split(" ")).toList()) {
            builder.addDirEdge(line[0], line[1]);
            if(lineNum % 10000 == 0) System.out.println("finished: " + lineNum);
            lineNum++;
        }
        Graph<String> graph = builder.build();

        // convert to JSON format
        ObjectMapper mapper = new ObjectMapper();
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/test/resources/jackson-serialized/cses-problem-sets/shortest-routes-I/unweighted/" + num + ".json"), graph);
    }
}
