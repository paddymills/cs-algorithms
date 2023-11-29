
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

// euclidean distance from docs
        public double distanceTo(Point other) {
            return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
        }

        @Override
        public String toString() {
            return this.x + " " + this.y;
        }
    }

    static class Tour {
        public ArrayList<Integer> stops;
        public double length;
        Point[] cities;

        public Tour(Point[] points) {
            this.stops = new ArrayList<>();
            this.cities = points;
        }

        public void calculateTour() {
            initTour();
        }

        void initTour() {
            HashSet<Integer> visitedCities = new HashSet<>();

            stops.add((int) (Math.random() * stops.size()));
            visitedCities.add(stops.get(0));

            for (int i = 1; i < cities.length; i++) {
                int id;
                do {
                    id = (int) (Math.random() * cities.length);
                } while (visitedCities.contains(id));

                visitedCities.add(id);
                stops.add(id);
                length += cities[stops.get(i - 1)].distanceTo(cities[stops.get(i)]);
            }

            this.length += cities[stops.get(0)].distanceTo(cities[stops.get(stops.size() - 1)]);
        }

        public boolean shiftOneStop() {
            boolean newTour = false;

            for (int i = 0; i < cities.length; i++) {
                for (int j = 0; j < cities.length; j++) {
                    // move stops[i] to stops[j]
                    if (i == j) continue;
                    
                    // calculate move
                    double score = length;
                    // remove end if i or j are at ends
                    if (i == 0 || i == stops.size()-1 || j == 0 || j == stops.size()-1)
                        score -= cities[stops.get(0)].distanceTo(cities[stops.get(stops.size() - 1)]);

                    // subtract current node i distances
                    if (i > 0)  // remove i.left
                        score -= cities[stops.get(i-1)].distanceTo(cities[stops.get(i)]);
                    if (i < cities.length-1)    // remove i.right
                        score -= cities[stops.get(i)].distanceTo(cities[stops.get(i+1)]);

                    // subtract current j node distances
                    if (j > 0 && j - 1 != i )  // remove j.left, if was not i
                        score -= cities[stops.get(j-1)].distanceTo(cities[stops.get(j)]);

                    // add (i).distanceTo(j-1)
                    // add (i).distanceTo(j)
                    // add end if needed (part of last 2?)
                    
                    if (score < length) {
                        // move items
                        Integer elem = stops.get(i);
                        stops.remove(elem);
                        stops.add(j, elem);

                        length = score;

                        newTour = true;
                    }

                    // cleanup stdout
                    System.out.print("\r");
                }
            }

            return newTour;
        }
    }

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