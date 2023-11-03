
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

        public String toString() {
            return "Movie<" + this.id + "|" + this.start + "-" + this.end + ":" + this.ev + ">";
        }
    }

    static class IntPair {
        int a, b;

        public IntPair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public boolean equals(Object other) {
            return ((IntPair) other).a == this.a && ((IntPair) other).b == this.b;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.a, this.b);
        }

        public String toString() {
            return "(" + this.a + ", " + this.b + ")";
        }
    }

    public static void main(String[] args) {
        Movie[] movies = readInput();

        display(movies, topDown(movies));
        // System.out.println("======================");
        // display(movies, bottomUp(movies));
        
    }

    public static void display(Movie[] movies, boolean[] results) {
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
        for (int i=0; i<movies.length; i++) {
            times.put(movies[i].start, 0);
            times.put(movies[i].end,   0);
        }

        // store times and their seqential index mapping for quick access
        int i = 0;
        for (Integer time : times.keySet()) {
            times.put(time, i);
            i++;
        }

        // update movie start and end times with sequential indexes
        for (i=0; i<movies.length; i++) {
            movies[i].start = times.get(movies[i].start);
            movies[i].end   = times.get(movies[i].end);
        }
    }

    public static boolean[] topDown(Movie[] movies) {
        HashMap<IntPair, Float> memo = new HashMap<>();

        int end = movies[movies.length-1].end;
        topDownHelper(movies.length-1, end, memo, movies);

        // calculate result
        boolean[] result = new boolean[movies.length];
        
        // calc result
        for (int i=movies.length-1; i>=0; i--) {
            if ( i == 0 ) {
                if ( movies[i].end <= end )
                    result[i] = true;
            }
            else if ( memo.get(new IntPair(i-1, end)) < memo.get(new IntPair(i, end)) ) {
                result[i] = true;
                end = movies[i].start;
            }
        }

        printMemo(memo, result);

        return result;
    }

    private static float topDownHelper(int i, int t, HashMap<IntPair, Float> memo, Movie[] movies) {
        if ( t <= 0 ) return 0f;
        if ( i < 0 ) return 0f;

        IntPair key = new IntPair(i, t);

        if ( memo.get(key) != null ) return memo.get(key);

        float notSelected = topDownHelper(i-1, t, memo, movies);

        float selected = 0;
        if ( movies[i].end <= t )
            selected = movies[i].ev + topDownHelper(i-1, movies[i].start, memo, movies);

        memo.put(key, Math.max(notSelected, selected));

        return memo.get(key);
    }

    public static boolean[] bottomUp(Movie[] movies) {
        indexMovies(movies);

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

        printMemo(movies, memo, taken);

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

    private static void printMemo(Movie[] movies, float[][] memo, boolean[][] taken) {
        if (System.getenv("GDK_BACKEND") != null) {
            for (Movie m : movies)
                System.out.println(m);

            for (int i=0; i<memo.length; i++) {
                System.err.print(i);
                for (int j=0; j<memo[0].length; j++) {
                    String tstr = taken[i][j] ? "x" : "_";
                    System.out.print(String.format(" | %.1f%s", memo[i][j], tstr));
                }
                System.out.println();
            }
        }
    }

    private static void printMemo(HashMap<IntPair, Float> memo, boolean[] taken) {
        if (System.getenv("GDK_BACKEND") != null) {
            System.out.println(memo);

            for (boolean b : taken)
                System.out.print("|" + b);
            System.out.println("|");
        }
    }
}