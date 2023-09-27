/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #2: Solution
 */

import java.io.*;
import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);

        // first line: number of test cases
        int testCases = Integer.parseInt( scanner.nextLine() );

        for (int i = 0; i < testCases; i++) {
            // second line: values for n and k, respectively, space delimited
            String[] nk = scanner.nextLine().split("\\s+");
            int n = Integer.parseInt( nk[0] );
            int k = Integer.parseInt( nk[1] );

            // next n lines: weights
            int[] weights = new int[n];
            for (int j = 0; j < n; j++) {
                weights[j] = Integer.parseInt(scanner.nextLine());
            }

            // output calculated sum of spreads
            System.out.println(getSumOfSpreads(k, weights));
        }

        scanner.close();
    }

    public static int getSumOfSpreads(int k, int[] weights) {
        /*
         * My approach is to
         * 1) sort the weights
         * 2) calculate the distance between adjacent values
         * 3) choose the k-1 partitions by selecting the k-1 positions with the greatest distance
         * 
         * for example:
         *  an array [1, 3, 4, 8, 9] would have distances of [2, 1, 4, 1]
         *  if k=3, there are k-1=2 partitions, which would be picked in order
         *  1) distance of 4 between 4 and 8
         *  2) distance of 2 between 1 and 3
         * 
         * Theoretically, this can, in its simplest terms be calculated by
         * 1) creating an array of distances
         * 2) removing the k greatest values
         * 3) summing the remaining values
         * 
         * or, as I have done
         * 1) creating an array of distances
         * 2) sorting the distances
         * 3) taking all but the last k values
         * 4) summing the remaining values
         */

        // sort values
        Arrays.sort(weights);

        // calculate distances
        ArrayList<Integer> distances = new ArrayList<Integer>();
        for (int i=1; i<weights.length; i++) {
            distances.add( weights[i] - weights[i-1] );
        }

        // retain all but k greatest distances
        //  and sum them
        return distances
            .stream()
            // sort the distances so that the greatest values are at the end
            .sorted()
            // take all but last k values, not k-1
            //  because were iterating distances, not weights
            .limit(weights.length - k)
            // sum the remaining distances
            .reduce(0, Integer::sum);
    }
}