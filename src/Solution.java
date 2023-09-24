/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #1: Solution
 */

import java.io.*;
import java.util.*;

public class Solution {
    
    public static void main(String[] args) {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);
        int testCases = Integer.parseInt( scanner.nextLine() );

        for (int i = 0; i < testCases; i++) {
            String[] nk = scanner.nextLine().split("\\s+");
            int n = Integer.parseInt( nk[0] );
            int k = Integer.parseInt( nk[1] );

            int[] weights = new int[n];
            for (int j = 0; j < n; j++) {
                weights[j] = Integer.parseInt(scanner.nextLine());
            }

            System.out.println(getSumOfSpreads(k, weights));
        }

        scanner.close();
    }

    public static int getSumOfSpreads(int k, int[] weights) {
        // sort values
        Arrays.sort(weights);

        TreeMap<Integer, ArrayList<Integer>> spreads = new TreeMap<Integer, ArrayList<Integer>>();
        for (int i=1; i<weights.length; i++) {
            int spread = weights[i] - weights[i-1];
            if (!spreads.containsKey(spread)) {
                spreads.put(spread, new ArrayList<Integer>());
            }
            spreads.get(spread).add(i);
        }

        int[] splits = new int[k+1];
        splits[0] = 0;
        for (int i = 1; i < k; i++) {
            ArrayList<Integer> index = spreads.lastEntry().getValue();
            splits[i] = index.remove(0);

            // remove spread list if empty
            if (index.size() == 0) {
                spreads.remove(spreads.lastKey());
            }
        }
        splits[splits.length-1] = weights.length;
        Arrays.sort(splits);

        int sumOfSpreads = 0;
        for (int i = 1; i < splits.length; i++) {
            if (splits[i] - splits[i-1] > 1) {
                sumOfSpreads += weights[splits[i]-1] - weights[splits[i-1]];
            }
        }

        return sumOfSpreads;
    }

    public int spread(int[] weights) {
        if ( weights.length == 1 )
            return 0;

        int max = Arrays.stream(weights).max().getAsInt();
        int min = Arrays.stream(weights).min().getAsInt();

        return max - min;
    }
}