package lab5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static util.GraphUtil.fillAdjMatrixFromFile;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Input filepath 1");
        String filepath1 = reader.readLine();
        System.out.println("Input filepath 2");
        String filepath2 = reader.readLine();

        int[][] matrix1 = fillAdjMatrixFromFile(filepath1);
        int[][] matrix2 = fillAdjMatrixFromFile(filepath2);

        Isomorphism isomorphism = new Isomorphism();
        boolean isTrue = isomorphism.isIsomorphic(matrix1, matrix2);
        if (isTrue) {
            System.out.println("These graphs are isomorphic");
        } else {
            System.out.println("These graphs aren't isomorphic");
        }
    }


}
