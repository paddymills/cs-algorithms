
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #7: TSP
 */


// import java.io.*;
import java.util.*;

public class eTSP {

    public static void main(String[] args) {
        ArrayList<Point> points = readInput();

        Tour tour = new Tour((Point[])points.toArray());
        tour.calculateTour();
        tour.output();
    }

    public static ArrayList<Point> readInput() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);
        
        // first line: number of test cases
        int n = Integer.parseInt( scanner.nextLine() );

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add( new Point(scanner.nextLine()) );
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