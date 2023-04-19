package lab4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static util.GraphUtil.fillAdjMatrixFromFile;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        String filepath = reader.readLine();

        int[][] matrix = fillAdjMatrixFromFile(filepath);

        int from = 0;
        int to = matrix[0].length - 1;
        FordFulkersonAlgorithm fordFulkersonAlgorithm = new FordFulkersonAlgorithm();
        int maxFlor = fordFulkersonAlgorithm.maxFlow(matrix, from, to);
        System.out.println("Maximum flow from source " + from + " to sink " + to + " is " + maxFlor);
    }
}
