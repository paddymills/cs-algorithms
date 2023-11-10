package Assignment4;

/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #4: Chasing Oscars
 * Approach: Bottom-Up
 */


import java.io.*;
import java.util.*;

import Assignment4.Solution.Movie;

public class Solution {

    static class Movie {
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
        Movie[] movies = readInput();
        indexMovies(movies);

        for (Integer id : bottomUp(movies)) {
            System.out.println(id);
        }
    }

    public static Movie[] readInput() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);

        // first line: number of test cases
        int n = Integer.parseInt( scanner.nextLine() );

        Movie[] movies = new Movie[n];
        for (int i = 0; i < n; i++) {
            movies[i] = new Movie(scanner.nextLine());
        }

        scanner.close();

        return movies;
    }

    static void indexMovies(Movie[] movies) {
        TreeMap<Integer, Integer> times = new TreeMap<>();

        // collect all start and end times
        for (Movie m : movies) {
            times.put(m.start, 0);
            times.put(m.end, 0);
        }

        // store times and their seqential index mapping for quick access
        int i = 0;
        for (Integer time : times.keySet()) {
            times.put(time, i);
            i++;
        }

        // update movie start and end times with sequential indexes
        for (Movie m : movies) {
            m.start = times.get(m.start);
            m.end = times.get(m.end);
        }
    }

    public static ArrayList<Integer> bottomUp(Movie[] movies) {
        int numberOfTimes = movies[movies.length-1].end+1;
        float[][] memo = new float[movies.length+1][numberOfTimes];
        boolean[][] taken = new boolean[movies.length+1][numberOfTimes];

        for (int i=1; i<memo.length; i++) {
            for (int j=movies[0].end; j<numberOfTimes; j++) {
                float ev = movies[i-1].ev + memo[i-1][movies[i-1].start];
                if ( movies[i-1].end <= j && ev > memo[i-1][j] ) {
                    memo[i][j] = ev;
                    taken[i][j] = true;
                } else {
                    memo[i][j] = memo[i-1][j];
                }
            }
        }

        // calculate result
        ArrayList<Integer> result = new ArrayList<>();
        int i = memo.length-1;
        for (int j=memo[0].length-1; j>0;) {
            if ( taken[i][j] ) {
                result.add(0, movies[i-1].id);
                j = movies[i-1].start;
            }
            i--;

            if ( i < 0 )
                break;
        }

        return result;
    }
}