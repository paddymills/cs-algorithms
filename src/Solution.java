/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #1: Solution
 */

import java.io.*;
import java.util.*;

public class Solution {
    /*
     * This solution uses the K-means algorithm
     * 
     * I found this while stumped on bettering my solution. I used ChatGPT, which utilized
     * the K-means algorithm, which led me to doing some futher research as I had never heard
     * of this before.
     * 
     * Although I did not think up the K-means algorithm, I only researched the concept. All code
     * below is of my own. I did not copy/transcribe any code from ChatGPT or other internet sites.
    */

    class KCluster {
        int centroid;
        ArrayList<Integer> points;

        public KCluster(int point) {
            this.centroid = point;
            this.addPoint(point);
        }

        public void addPoint(int point) {
            this.points.add(point);
        }

        public int spread() {
            if ( this.points.size() == 1 )
                return 0;

            int min = Collections.min(this.points);
            int max = Collections.max(this.points);

            return max - min;
        }

        public int distanceFromCentroid(int num) {
            return Math.abs(this.centroid - num);
        }

        public boolean computeCentroid() {
            boolean centroidHasChanged = false;

            // compute average of points
            double average = this.points
                .stream()
                .mapToDouble(p -> p)
                .average()
                .getAsDouble();

            // update centroid if any point is closer to the average than the centroid
            double centroidDistanceFromAverage = Math.abs(average - this.centroid);
            for (int i = 0; i < this.points.size(); i++) {
                int point = this.points.get(i);

                // skip if point is the same as the centroid (same value)
                if (this.centroid != point) {
                    double distanceFromAverage = Math.abs(average - point);
                    if ( distanceFromAverage < centroidDistanceFromAverage) {
                        // update centroid
                        this.centroid = point;
    
                        // update return value
                        centroidHasChanged = true;
    
                        // since centroid changed, calculate new distance from average
                        centroidDistanceFromAverage = Math.abs(average - this.centroid);
                    }
                }
            }

            // return whether the centroid changed or not
            return centroidHasChanged;
        }
    }

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

    
}