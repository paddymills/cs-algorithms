public class Test {
    
    public static void main(String[] args) {
        Point[] points = eTSP.readInput();

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
        double rebalanceThreshold = 500;

        KMeans km = new KMeans(1000);
        km.init(points);

        double th;
        do {
            th = km.rebalance();
            System.out.println("Rebalance delta: " + th);
        } while (th > rebalanceThreshold);

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
}
