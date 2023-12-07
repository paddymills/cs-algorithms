
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #7: TSP
 */


// import java.io.*;
import java.util.*;

public class eTSP {

    public static void main(String[] args) {
        Point[] points = readInput();

        Tour tour = new Tour(points);
        tour.calculateTour();
        tour.output();
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

    /*
     * Grade calculation from rubric
     * 
     * grade = -1.009 * 10^-5 * tourLength + 224
    */
    public static double grade(double tourLength) {
        return -1.009 * Math.pow(10, -5) * tourLength + 224;
    }
}