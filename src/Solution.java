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

            int[] weights = Arrays.asList( new int[n_k[0]] )
                .stream()
                .mapToInt(a -> Integer.parseInt( scanner.nextLine() ))
                .toArray();
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