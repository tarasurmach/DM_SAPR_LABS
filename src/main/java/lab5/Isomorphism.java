package lab5;

import java.util.Arrays;

public class Isomorphism {

    public boolean isIsomorphic(int[][] graph1, int[][] graph2){
        int n1 = graph1.length;
        int n2 = graph2.length;

        if(n1 != n2 || graph1[0].length != graph2[0].length){
            return false;
        }

        int[] degree1 = new int[n1];
        int[] degree2 = new int[n2];
        for (int i = 0; i < n1; i++){
            for (int j = 0; j < graph1[0].length; j++){
                if(graph1[i][j] > 0){
                    degree1[i]++;
                }

                if(graph2[i][j] > 0){
                    degree2[i]++;
                }
            }
        }

        return equalArrays(degree1, degree2);
    }
    // return how often n appears in an array
    public static int count(int n, int[] array) {
        int appearedNTimes = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] == n) {
                appearedNTimes++;
            }
        }
        return appearedNTimes;
    }

    public static boolean equalArrays(int[] arr1, int[] arr2) {
        for (int i: arr1) {
            if (count(i, arr1) != count(i, arr2)) return false;
        }
        return arr1.length == arr2.length;
    }
}
