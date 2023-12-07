
import java.util.*;

public class KMeans {
    int k;
    List<Cluster> clusters;

    public KMeans(int k) {
        this.k = k;
        this.clusters = new ArrayList<>();
    }

    public void init(Point[] points) {
        Random rand = new Random();
        HashSet<Integer> usedCentroids = new HashSet<>();

        // assign centroids
        for (int i = 0; i < k; i++) {
            int n;
            do {
                n = rand.nextInt(points.length);
            } while (usedCentroids.contains(n));

            clusters.add(new Cluster(points[n]));
            usedCentroids.add(n);
        }

        addPoints(points);
        for (Cluster cluster : clusters) {
            cluster.initialOrdering();
        }

        // sort clusters by distance
        ArrayList<Cluster> newClusters = new ArrayList<>();
        newClusters.add(clusters.remove(0));
        while (clusters.size() > 0) {
            int closestCluster = 0;
            double minDistance = Double.MAX_VALUE;
            for (int i = 0; i < clusters.size(); i++) {
                double dist = newClusters.get(newClusters.size()-1).lastPoint().distanceTo(clusters.get(i).firstPoint());
                if (dist < minDistance) {
                    minDistance = dist;
                    closestCluster = i;
                }
            }

            newClusters.add( clusters.remove(closestCluster) );
        };

        clusters = newClusters;
    }

    public void addPoints(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            addPoint(points[i]);
        }
    }

    public void addPoints(ArrayList<Point> points) {
        for (Point point : points) {
            addPoint(point);
        }
    }

    public void addPoint(Point point) {
        int closestCluster = -1;
        double minDistance = Double.MAX_VALUE;
        for (int j = 0; j < clusters.size(); j++) {
            double distance = point.distanceTo(clusters.get(j).centroid);
            if (distance < minDistance) {
                minDistance = distance;
                closestCluster = j;
            }
        }

        clusters.get(closestCluster).addPoint(point);
    }

    public double rebalance() {
        for (Cluster cluster : clusters) {
            addPoints(cluster.drainPoints());
        }

        double rebalanceDelta = 0;
        for (Cluster cluster : clusters) {
            rebalanceDelta += cluster.updateCentroid();
        }

        return rebalanceDelta;
    }

    public double distance() {
        double distance = clusters.get(0).distance();

        for (int i = 1; i < clusters.size(); i++) {
            distance += clusters.get(i).distance() + clusters.get(i-1).lastPoint().distanceTo(clusters.get(i).firstPoint());
        }

        distance += clusters.get(0).firstPoint().distanceTo(clusters.get(clusters.size()-1).lastPoint());

        return distance;
    }

    public double finalDistance() {
        double distance = clusters.get(0).distanceRecalculate();

        for (int i = 1; i < clusters.size(); i++) {
            distance += clusters.get(i).distanceRecalculate() + clusters.get(i-1).lastPoint().distanceTo(clusters.get(i).firstPoint());
        }

        distance += clusters.get(0).firstPoint().distanceTo(clusters.get(clusters.size()-1).lastPoint());

        return distance;
    }

    public int swapTwo() {
        int swaps = 0;
        for (Cluster cluster : clusters) {
            if (cluster.swapTwo())
                swaps++;
        }

        return swaps;
    }

    public int shiftOne() {
        int shifts = 0;
        for (Cluster cluster : clusters) {
            if (cluster.shiftOne())
                shifts++;
        }

        return shifts;
    }

    public int shiftSection() {
        int shifts = 0;
        for (Cluster cluster : clusters) {
            if (cluster.shiftSection())
                shifts++;
        }

        return shifts;
    }

    public int reverse() {
        int reversals = 0;
        for (Cluster cluster : clusters) {
            if (cluster.reverse())
                reversals++;
        }

        return reversals;
    }
}
