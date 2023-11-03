
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
        Movie[]  movies = readInput();

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

    public static ArrayList<Integer> topDown(Movie[] movies) {
        Float[][] memo = new Float[movies.length+1][movies[movies.length-1].end];
        boolean[][] taken = new boolean[movies.length+1][movies[movies.length-1].end];

        topDownHelper(movies.length, movies[movies.length-1].end, movies, memo, taken);

        ArrayList<Integer> result = new ArrayList<>();

        // calculate result
        int i = movies.length-1;
        for (int t=movies[movies.length-1].end; t>0;) {
            if ( taken[i][t] ) {
                result.add(0, movies[i].id);
                i--;
            }
        }

        return result;
    }

    public static Float topDownHelper(int i, int t, Movie[] movies, Float[][] memo, boolean[][] taken) {
        if ( t < 0 ) return Float.MIN_VALUE;
        if ( t == 0 || i == 0 ) return 0f;

        if ( memo[i][t] > 0 ) return memo[i][t];

        // don't select movie
        float notSelected = topDownHelper(i-1, t, movies, memo, taken);

        // select movie
        float selected = topDownHelper(i-1, movies[i].start, movies, memo, taken);

        if ( selected > notSelected )
            taken[i][t] = true;

        memo[i][t] = Math.max(notSelected, selected);

        return memo[i][t];
    }

    public static ArrayList<Integer> bottomUp(Movie[] movies) {
        float[][] memo = new float[movies.length+1][movies[movies.length-1].end+1];
        boolean[][] taken = new boolean[movies.length+1][movies[movies.length-1].end+1];

        for (int i=1; i<memo.length; i++) {
            // start at first end, since everything is 0's before then
            for (int j=movies[0].end; j<memo[0].length; j++) {
                float ev = movies[i-1].ev + memo[i-1][movies[i-1].start];
                if ( movies[i-1].end <= j && ev > memo[i-1][j] ) {
                    memo[i][j] = ev;
                    taken[i][j] = true;
                } else {
                    memo[i][j] = memo[i-1][j];
                }
            }
        }

        printMemo(memo, taken);

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
}