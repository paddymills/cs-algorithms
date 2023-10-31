
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #4: Chasing Oscars
 * Approach: Top-Down
 */

package TopDown;


import java.io.*;
import java.util.*;

public class Solution {

    class Movie {
        int id;     // movie id
        int start;  // start time
        int end;    // time just after end (movie2.start == movie1.end is acceptable)
        float ev;   // expected numbers of oscars to be won

        public Movie(String line) {
            String[] vals = line.split("\\s+");

            this.id    = Integer.parseInt( vals[0] );
            this.start = Integer.parseInt( vals[1] );
            this.end   = Integer.parseInt( vals[2] );
            this.ev    = Float.parseFloat( vals[3] );
        }
    }

    public static void main(String[] args) {
        ArrayList<Movie> movies = readInput();

        // impl
    }

    public static ArrayList<Movie> readInput() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);

        // first line: number of test cases
        int n = Integer.parseInt( scanner.nextLine() );

        ArrayList<Movie> movies = new ArrayList();
        for (int i = 0; i < n; i++) {
            movies.add( new Movie(scanner.nextLine()) );
        }

        scanner.close();

        return movies
    }
}