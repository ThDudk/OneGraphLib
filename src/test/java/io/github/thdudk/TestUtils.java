package io.github.thdudk;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilder;
import io.github.thdudk.builders.unweighted.DistinctDataGraphBuilderImpl;
import io.github.thdudk.graphs.unweighted.Graph;
import io.github.thdudk.serialization.GraphVizExporter;

import java.io.*;

public abstract class TestUtils {
    public static void main(String[] args) throws Exception {
        System.out.println(GraphVizExporter.serialize(TestGraphs.getCSESShortestRoutesIUnweightedGraph(1)));
    }

    static void serializeCSESAsUnweightedTestGraph(int num) throws IOException {
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
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src/test/resources/jackson-serialized/unweighted/cses-problem-sets/shortest-routes-I/" + num + ".json"), graph);
    }
}
