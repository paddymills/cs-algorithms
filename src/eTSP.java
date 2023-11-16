
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #7: TSP
 */


import java.io.*;
import java.util.*;

public class eTSP {
    static class Point {
        int x, y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(String line) {
            String[] vals = line.split("\\s+");

            this.x = Integer.parseInt( vals[0] );
            this.y = Integer.parseInt( vals[1] );
        }

        public double distanceTo(Point other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }
    }

    public static void main(String[] args) {
        Point[] points = readInput();
        System.out.println("Points: " + points.length);

        // TODO: implement
        // ideas:
        //  - Djikstra
    }

    public static Point[] readInput() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);

        // first line: number of test cases
        int n = Integer.parseInt( scanner.nextLine() );

        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(scanner.nextLine());
        }

        scanner.close();

        return points;
    }
}