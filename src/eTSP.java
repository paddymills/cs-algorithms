
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #7: TSP
 */


// import java.io.*;
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

        /*
         * √[(𝑥1 −𝑥2)^2 +(𝑦1 −𝑦2)^2]
         */
        public double distanceTo(Point other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }

        @Override
        public String toString() {
            return this.x + " " + this.y;
        }
    }

    static class Tour {
        public int[] stops;
        public double length;
        Point[] cities;

        public Tour(Point[] points) {
            this.stops = new int[points.length];
            this.cities = points;
        }

        /*
         * init tour by repeatitively selecting the next closest city
         * short circuits when it finds a distance closer than a given delta
        */
        void initTour() {
            HashSet<Integer> visitedCities = new HashSet<>();
            double delta = 100;

            // initialize first stop to be first city
            stops[0] = 0;
            visitedCities.add(0);
            
            for (int i = 1; i < stops.length; i++) {
                progressPercentage(i, stops.length);
                int closestCity = stops.length;
                double closestCityDistance = Double.MAX_VALUE;
                for (int j = 0; j < cities.length; j++) {
                    if (visitedCities.contains(j)) continue;

                    double dist = cities[stops[i-1]].distanceTo(cities[j]);

                    if (dist < closestCityDistance) {
                        closestCity = j;
                        closestCityDistance = dist;
                    }

                    if (closestCityDistance < delta)
                        break;
                }

                // shouldn't happen, but lets check that a city was selected
                if (closestCity == stops.length)
                    System.err.println("tourInit reached an interation with no closest city selected");

                // commit closest city
                visitedCities.add(closestCity);
                stops[i] = closestCity;
                length += closestCityDistance;
            }

            // add distance from first stop to last stop
            this.length += cities[stops[0]].distanceTo(cities[stops[stops.length-1]]);
        }

        public void calculateTour() {
            initTour();
        }
    }

    public static void main(String[] args) {
        Point[] points = readInput();

        // TODO: implement
        // ideas:
        //  - Djikstra

        Tour tour = new Tour(points);
        tour.calculateTour();

        System.out.println(String.format("%.07f", tour.length));
        // for (int stop : tour.stops) {
        //     System.out.println(points[stop].toString());
        // }

        System.out.println("Your grade is: " + String.format("%.02f%%", grade(tour.length)));
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

    public static void progressPercentage(int complete, int total) {
        if (complete > total) {
            throw new IllegalArgumentException();
        }
        
        StringBuilder bar = new StringBuilder("\r|");
        
        int completePerc = (complete/total) * 10;
        for (int i=0; i<=100; i+=10) {
            if (i<completePerc) bar.append("=");
            else bar.append("-");
        }
        
        bar.append("| " + complete + "/" + total);
        System.out.print(bar);
        if (complete == total) {
            System.out.print("\n");
        }
    }
}