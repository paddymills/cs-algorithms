
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #4: Chasing Oscars
 * Approach: Top-Down
 */


import java.io.*;
import java.util.*;

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

        boolean[] results = topDown(movies);
        // boolean[] results = bottomUp(movies);
        for (int i=0; i<movies.length; i++) {
            if ( results[i] )
                System.out.println(movies[i].id);
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

    public static boolean[] topDown(Movie[] movies) {
        int numberOfTimes = movies[movies.length-1].end+1;
        Float[][] memo = new Float[movies.length+1][numberOfTimes];
        boolean[][] taken = new boolean[movies.length+1][numberOfTimes];

        topDownHelper(movies.length, movies[movies.length-1].end, movies, memo, taken);

        // printMemo(memo, taken);

        // calculate result
        boolean[] result = new boolean[movies.length];
        int i = memo.length-1;
        for (int j=memo[0].length-1; j>0;) {
            if ( taken[i][j] ) {
                result[i-1] = true;
                j = movies[i-1].start;
            }
            i--;

            if ( i < 0 )
                break;
        }

        return result;
    }

    public static float topDownHelper(int i, int t, Movie[] movies, Float[][] memo, boolean[][] taken) {
        if ( t < 0 ) return Float.MIN_VALUE;
        if ( t == 0 || i == 0 ) return 0f;

        if ( memo[i][t] != null ) return memo[i][t];

        // don't select movie
        float notSelected = topDownHelper(i-1, t, movies, memo, taken);

        // select movie
        float selected = 0f;
        if ( movies[i-1].end <= t ) {
            selected = movies[i-1].ev + topDownHelper(i-1, movies[i-1].start, movies, memo, taken);
        }

        if ( selected > notSelected ) {
            memo[i][t] = selected;
            taken[i][t] = true;
        } else {
            memo[i][t] = notSelected;
        }

        return memo[i][t];
    }

    public static boolean[] bottomUp(Movie[] movies) {
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

        // printMemo(memo, taken);

        // calculate result
        boolean[] result = new boolean[movies.length];
        int i = memo.length-1;
        for (int j=memo[0].length-1; j>0;) {
            if ( taken[i][j] ) {
                result[i-1] = true;
                j = movies[i-1].start;
            }
            i--;

            if ( i < 0 )
                break;
        }

        return result;
    }

    private static void printMemo(float[][] memo, boolean[][] taken) {
        for (int i=0; i<memo.length; i++) {
            System.err.print(i);
            for (int j=0; j<memo[0].length; j++) {
                String tstr = taken[i][j] ? "x" : "_";
                System.out.print(" | " + memo[i][j] + tstr);
            }
            System.out.println();
        }
    }

    private static void printMemo(Float[][] memo, boolean[][] taken) {
        for (int i=0; i<memo.length; i++) {
            System.err.print(i);
            for (int j=0; j<memo[0].length; j++) {
                String tstr = taken[i][j] ? "x" : "_";
                System.out.print(" | " + memo[i][j] + tstr);
            }
            System.out.println();
        }
    }
}