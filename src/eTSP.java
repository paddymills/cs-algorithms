
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #7: TSP
 */


// import java.io.*;
import java.util.*;

public class eTSP {

    public static void main(String[] args) {
        City[] cities = readInput();

        Tour tour = new Tour(cities);
        tour.calculateTour();
        tour.output();
    }

    public static City[] readInput() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);
        
        // first line: number of test cases
        int n = Integer.parseInt( scanner.nextLine() );

        City[] cities = new City[n];
        for (int i = 0; i < n; i++) {
            cities[i] = new City(scanner.nextLine());
        }

        scanner.close();

        return cities;
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