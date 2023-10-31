
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #4: Chasing Oscars
 * Approach: Top-Down
 */


import java.io.*;
import java.util.*;

public class TopDown {

    class Movie {
        int id, startTime, endTime;
        float ev;

        public Movie(String line) {
            String[] vals = line.split("\\s+");
            int id = Integer.parseInt( vals[0] );
            int startTime = Integer.parseInt( vals[1] );
            int endTime = Integer.parseInt( vals[2] );
            float ev = Float.parseFloat( vals[3] );
        }
    }

    public static void main(String[] args) {
        /* Enter your code here. Read input from STDIN. Print output to STDOUT. Your class should be named Solution. */

    }

    public static Movie[] readInput() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);

        // first line: number of test cases
        int n = Integer.parseInt( scanner.nextLine() );

        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            // second line: values for n and k, respectively, space delimited
            movies[i] = new Movie( scanner.nextLine() );
        }

        scanner.close();

        return movies
    }
}