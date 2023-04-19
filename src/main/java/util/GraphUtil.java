package util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GraphUtil {
    public static int[][] fillAdjMatrixFromFile(String filepath) throws IOException {
        List<String> strings;
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
            strings = stream.filter(str -> str.length() > 2).collect(Collectors.toList());
        }
        int verticesCount = strings.size();
        int[][] matrix = new int[verticesCount][verticesCount];

        //Fill graph with values
        for (int i = 0; i < verticesCount; i++) {
            String[] valuesFor0Node = strings.get(i).split(" ");
            for (int j = 0; j < valuesFor0Node.length; j++) {
                matrix[i][j] = Integer.parseInt(valuesFor0Node[j]);
            }
        }
        return matrix;
    }
}
