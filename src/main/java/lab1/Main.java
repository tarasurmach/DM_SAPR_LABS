package lab1;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraphBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String filepath = reader.readLine();

        MutableValueGraph<Integer, Integer> graph = ValueGraphBuilder.undirected().build();
        List<String> strings;
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
            strings = stream.filter(str -> str.length() > 2).collect(Collectors.toList());
        }

        //Fill graph with values
        for (int i = 0; i < strings.size(); i++) {
            String[] valuesFor0Node = strings.get(i).split(" ");
            for (int j = 0; j < valuesFor0Node.length; j++) {
                if (i != j && parseInt(valuesFor0Node[j]) > 0) {
                    graph.putEdgeValue(i, j, parseInt(valuesFor0Node[j]));
                }
            }
        }


        BoruvkaMST boruvkaMST = new BoruvkaMST(graph);
        MutableValueGraph<Integer, Integer> mst = boruvkaMST.getMST();

        System.out.println(mst.toString());
        System.out.println("Total weight: " + boruvkaMST.getTotalWeight());
        System.out.println("Edges size: " + mst.edges().size());
    }
}
