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
        double rebalanceThreshold = 1000;

        KMeans km = new KMeans(100);
        km.init(points);

        double th;
        do {
            th = km.rebalance();
            System.out.println("Rebalance delta: " + th);
        } while (th > rebalanceThreshold);

        for (int i = 0; i < 1000; i++) {
            System.out.println("\t> Swap Two: " + km.swapTwo());
            System.out.println("\t> Shift One: " + km.shiftOne());
            // System.out.println("\t> Shift Section: " + km.shiftSection());
            // System.out.println("\t> Reverse: " + km.reverse());
            System.out.println(">> Your grade is: " + String.format("%.02f%%", eTSP.grade(km.finalDistance())));
            System.out.println("======================================");
        }

        return km.finalDistance();
    }
}
