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

            List<Integer> weights = new ArrayList<Integer>();
            for (int j = 0; j < n; j++) {
                weights.add( Integer.parseInt(scanner.nextLine()) );
            }

            System.out.println(getSumOfSpreads(k, weights));
        }

        scanner.close();
    }

    public static int getSumOfSpreads(int k, List<Integer> weights) {
        // sort values
        Collections.sort(weights);

        int sumOfSpreads = 0;
        int groupLength = weights.size() / k;
        int numGroupsWithExtraMember = weights.size() % k;

        // traverse weights, step=group_length
        // find min spread of longest group length and commit it
        while (weights.size() > 1) {
            int groupLengthAdder = numGroupsWithExtraMember > 0 ? 1 : 0;
            int spreadStart = 0;
            int spreadLength = groupLength + groupLengthAdder;

            if (spreadLength > 1) {
                int minSpread = Integer.MAX_VALUE;
                int calculatedSpread = 0;
                for (int i=0; i<=weights.size()-spreadLength; i++) {
                    calculatedSpread = weights.get(i+spreadLength-1) - weights.get(i);
                    if ( calculatedSpread < minSpread ) {
                        minSpread = calculatedSpread;
                        spreadStart = i;
                    }
                }

                sumOfSpreads += minSpread;
            }

            // remove/commit smallest spread
            for (int i=0; i<spreadLength; i++) {
                weights.remove(spreadStart);
            }

            // decrement groups with extra member (if this was a group with an extra)
            numGroupsWithExtraMember -= groupLengthAdder;
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