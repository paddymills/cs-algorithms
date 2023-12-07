import java.util.ArrayList;

public class Test {
    
    public static void main(String[] args) {
        ArrayList<Point> points = eTSP.readInput();

        // double length = usingTour(points);
        double length = usingKMeans(points);
        System.out.println("Your grade is: " + String.format("%.02f%%", eTSP.grade(length)));
    }

    static double usingTour(Point[] points) {
        Tour tour = new Tour(points);
        tour.calculateTour();

        return tour.length;
    }

    static double usingKMeans(ArrayList<Point> points) {
        // a reasonable number I found during testing, given a KMeans with k=100
        // double rebalanceThreshold = 1000;
        int[] thresholds = {50000, 10000, 7500, 5000, 2500, 1000, 750, 500, 250, 100};

        double distance = 0;
        KMeans km = new KMeans(1000);
        for (int i = 0; i < thresholds.length; i++) {
            // re-init and set neighborhoods(clusters)
            km = new KMeans(km.k, points);

            // threshold will increase by 10% each time
            km.rebalanceUntil(thresholds[i]);
            km.sortClusters();

            Progress p = new Progress("Test", 100);
            for (int index = 0; index < 10; index++) {
                p.update(i* 10 + index);
                km.swapTwo();
                km.shiftOne();
                // km.shiftSection();
                // km.reverse();
            }
            p.finish();


            distance = km.finalDistance();
            System.out.println(">> Your grade is: " + String.format("%.02f%%", eTSP.grade(distance)));

            points = km.getPoints();
        }

        return distance;
    }
}
