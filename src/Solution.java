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
        int test_cases = Integer.parseInt( scanner.nextLine() );

        for (int i = 0; i < test_cases; i++) {
            int[] n_k = Arrays.asList( scanner.nextLine().split("\\s+") )    // split on whitespace
                .stream()
                .mapToInt(num -> {
                    try { return Integer.parseInt(num); }
                    finally {}  // ignore parse errors, shouldn't happen
                })
                .toArray();

            int n = n_k[0];
            int k = n_k[1];

            int[] weights = Arrays.asList( new int[n] )
                .stream()
                .mapToInt(a -> Integer.parseInt( scanner.nextLine() ))
                .sorted()
                .toArray();

            int sum_of_spreads;
            int group_length = n / k;
            int num_groups_with_extra_member = n % k;
            // traverse weights, step=group_length
            // find min spread of longest group length and commit it
        }

        scanner.close();
    }

    public int spread(int[] weights) {
        if ( weights.length == 1 )
            return 0;

        int max = Arrays.stream(weights).max().getAsInt();
        int min = Arrays.stream(weights).min().getAsInt();

        return max - min;
    }
}