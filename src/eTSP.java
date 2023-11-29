
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

        // TODO: implement
        // ideas:
        //  - Djikstra

        Tour tour = new Tour(points);
        tour.calculateTour();

        for (int i = 0; i < 5; i++) {
            System.out.println(String.format("%.07f", tour.length));
            // for (int stop : tour.stops) {
            //     System.out.println(points[stop].toString());
            // }
    
            System.out.println("Your grade is: " + String.format("%.02f%%", grade(tour.length)));

            System.out.println("+++++++++++++++++++++");
            if (!tour.shiftOneStop()) break;
        }
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