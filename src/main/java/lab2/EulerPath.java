package lab2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;

public class EulerPath {

    private final int[][] adjacencyMatrix;
    private final int numberOfNodes;
    Map<String, Integer> edgeWeights;
    int tourCost = 0;


    public EulerPath(int numberOfNodes, int[][] adjacencyMatrix, Map<String, Integer> edgeWeights) {
        this.numberOfNodes = numberOfNodes;
        this.adjacencyMatrix = new int[numberOfNodes + 1][numberOfNodes + 1];
        for (int sourceVertex = 1; sourceVertex <= numberOfNodes; sourceVertex++) {
            for (int destinationVertex = 1; destinationVertex <= numberOfNodes; destinationVertex++) {
                this.adjacencyMatrix[sourceVertex][destinationVertex]
                        = adjacencyMatrix[sourceVertex][destinationVertex];
            }
        }
        this.edgeWeights = edgeWeights;
    }

    public static void main(String... arg) throws IOException {
        Map<String, Integer> weights = new HashMap<>();

        //////////
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        // Reading data using readLine
        String filepath = reader.readLine();

        List<String> strings;
        try (Stream<String> stream = Files.lines(Paths.get(filepath))) {
            strings = stream.filter(str -> str.length() > 2).collect(Collectors.toList());
        }
        int[][] adjacency_matrix = new int[strings.size() + 1][strings.size() + 1];

        //Fill graph with values
        for (int i = 0; i < strings.size(); i++) {
            String[] valuesFor0Node = strings.get(i).split(" ");
            for (int j = 0; j < valuesFor0Node.length; j++) {
                int indexI = i + 1;
                int indexJ = j + 1;
                if (parseInt(valuesFor0Node[j]) > 0) {

                    weights.put("" + indexI + indexJ, Integer.parseInt(valuesFor0Node[j]));
                    adjacency_matrix[indexI][indexJ] = 1;
                } else {
                    adjacency_matrix[indexI][indexJ] = 0;
                }
            }
        }

        //check if cycle exists
        for (int i = 1; i < strings.size() + 1; i++) {
            int nodeDegree = 0;
            for (int num : adjacency_matrix[i]) {
                if (num > 0) {
                    nodeDegree++;
                }
            }
            if (nodeDegree % 2 != 0) {
                System.out.println("Степінь вершини " + i + " не парна, отже ейлерового циклу не існує");
                System.exit(1);
            }
        }

        //Calculations
        EulerPath circuit = new EulerPath(strings.size(), adjacency_matrix, weights);
        circuit.printEulerTour();

    }

    public int degree(int vertex) {
        int degree = 0;
        for (int destinationvertex = 1; destinationvertex <= numberOfNodes; destinationvertex++) {
            if (adjacencyMatrix[vertex][destinationvertex] == 1
                    || adjacencyMatrix[destinationvertex][vertex] == 1) {
                degree++;
            }
        }
        return degree;
    }

    public int oddDegreeVertex() {
        int vertex = -1;
        for (int node = 1; node <= numberOfNodes; node++) {
            if ((degree(node) % 2) != 0) {
                vertex = node;
                break;
            }
        }
        return vertex;
    }

    public void printEulerTourUtil(int vertex) {

        for (int destination = 1; destination <= numberOfNodes; destination++) {
            if (adjacencyMatrix[vertex][destination] == 1 && isValidNextEdge(vertex, destination)) {
                String edgeWeightKey = vertex + String.valueOf(destination);
                Integer weight = edgeWeights.get(edgeWeightKey);
                tourCost += weight;
                System.out.println(" source : " + vertex + " destination " + destination + " edgeWeight: " + weight);
                removeEdge(vertex, destination);
                printEulerTourUtil(destination);
            }
        }
    }

    public void printEulerTour() {
        int vertex = 1;
        if (oddDegreeVertex() != -1) {
            vertex = oddDegreeVertex();
        }
        printEulerTourUtil(vertex);
        System.out.println("Euler path costs: " + tourCost);
    }

    public boolean isValidNextEdge(int source, int destination) {
        int count = 0;
        for (int vertex = 1; vertex <= numberOfNodes; vertex++) {
            if (adjacencyMatrix[source][vertex] == 1) {
                count++;
            }
        }

        if (count == 1) {
            return true;
        }

        int[] visited = new int[numberOfNodes + 1];
        int count1 = DFSCount(source, visited);

        removeEdge(source, destination);
        for (int vertex = 1; vertex <= numberOfNodes; vertex++) {
            visited[vertex] = 0;
        }

        int count2 = DFSCount(source, visited);
        addEdge(source, destination);

        return count1 <= count2;
    }

    /**
     * DFS, Depth First Search, is an edge-based technique.
     * It uses the Stack data structure and performs two stages,
     * first visited vertices are pushed into the stack,
     * and second if there are no vertices then visited vertices are popped.
     */
    public int DFSCount(int source, int[] visited) {
        visited[source] = 1;
        int count = 1;
        int destination = 1;

        while (destination <= numberOfNodes) {
            if (adjacencyMatrix[source][destination] == 1 && visited[destination] == 0) {
                count += DFSCount(destination, visited);
            }
            destination++;
        }
        return count;
    }

    public void removeEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = 0;
        adjacencyMatrix[destination][source] = 0;
    }

    public void addEdge(int source, int destination) {
        adjacencyMatrix[source][destination] = 1;
        adjacencyMatrix[destination][source] = 1;
    }
}
