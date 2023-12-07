import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Test {
    
    public static void main(String[] args) {
        Point[] points = eTSP.readInput();
        // getRandomKmeansSetup(points);

        // double length = usingTour(points);
        double length = usingKMeans(points);
        System.out.println("Your grade is: " + String.format("%.02f%%", eTSP.grade(length)));
    }

    static double usingTour(Point[] points) {
        Tour tour = new Tour(points);
        tour.calculateTour();

        return tour.length;
    }

    static double usingKMeans(Point[] points) {
        // a reasonable number I found during testing, given a KMeans with k=100
        double rebalanceThreshold = 1000;

        KMeans km = new KMeans(1000);
        km.init(points);

        double th;
        int count = 0;
        do {
            th = km.rebalance();
            count++;
            System.out.println("Rebalance delta: " + th);
        } while (th > rebalanceThreshold);
        System.out.println("Rebalanced: " + count);

        Progress p = new Progress("Test", 100);
        double grade;
        for (int i = 0; i < 100; i++) {
            km.swapTwo();
            km.shiftOne();
            // System.out.println("\t> Shift Section: " + km.shiftSection());
            // System.out.println("\t> Reverse: " + km.reverse());

            
            grade = eTSP.grade(km.finalDistance());
            p.update(i);
            if (i % 10 == 0) {
                p.finish();
                System.out.println(">> Your grade is: " + String.format("%.02f%%", grade));
                p = new Progress("Test", 100);
            }
        }
        p.finish();

        return km.finalDistance();
    }

    static void getRandomKmeansSetup(Point[] points) {
        int minRebalances = Integer.MAX_VALUE;
        // ArrayList<Integer> bestIndex = null;
        Progress p = new Progress("KMeans sim", 100);
        for (int i = 0; i < 100; i++) {
            p.update();

            KMeans km = new KMeans(1000);
            ArrayList<Integer> index = km.testInit(points);

            int rebalances = 0;
            do {
                rebalances++;
            } while (km.rebalance() > 5000);

            if (rebalances < minRebalances) {
                minRebalances = rebalances;

                try {
                    FileWriter writer = new FileWriter("kmeans_index.txt");
                    writer.write(index.toString());
                    writer.close();
                    
                } catch (IOException e) {
                    System.out.println("An error occurred: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        }
        p.finish();

        
    }
}
