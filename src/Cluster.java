import java.util.ArrayList;

public class Cluster {
    public Point centroid;
    ArrayList<Point> points;
    double distance;
    boolean[] algShortCircuit;

    public Cluster(Point centroid) {
        this.centroid = centroid;
        this.points = new ArrayList<>();

        distance = -1;
        algShortCircuit = new boolean[4];
    }

    public void addPoint(Point p) {
        // update distance if cached
        //  do this first, otherwise lastPoint() returns p
        if (points.size() > 0 && distance > -1) {
            distance += lastPoint().distanceTo(p);
        }
        
        points.add(p);
    }

    // returns the distance between the old and new centroid
    public double updateCentroid() {
        Point oldCentroid = centroid;
        centroid = new Point(0, 0);

        int n = 0;
        for (Point point : points) {
            centroid.addAssign(point);
            n++;
        }
        
        centroid.x = centroid.x / n;
        centroid.y = centroid.y / n;

        return oldCentroid.distanceTo(centroid);
    }

    public ArrayList<Point> drainPoints() {
        ArrayList<Point> pts = points;
        points = new ArrayList<>();

        // invalidate cached distance
        distance = -1;

        return pts;
    }

    public void initialOrdering() {
        // invalidate cached distance, in case this is called where it shouldn't be
        distance = -1;

        ArrayList<Point> pts = drainPoints();
        points.add( pts.remove(0) );

        while (pts.size() > 0) {

            int closestPoint = 0;
            double minDistance = Double.MAX_VALUE;
            for (int i = 0; i < pts.size(); i++) {
                double dist = lastPoint().distanceTo(pts.get(i));
                if (dist < minDistance) {
                    minDistance = dist;
                    closestPoint = i;
                }
            }

            points.add( pts.remove(closestPoint) );
        };
    }

    public Point firstPoint() {
        return points.get(0);
    }

    public Point lastPoint() {
        return points.get(points.size()-1);
    }

    public double distanceRecalculate() {
        distance = -1;
        return distance();
    }

    public double distance() {
        if (distance == -1) {   // distance is not cached from last calculation
            distance = 0;
    
            for (int i = 1; i < points.size(); i++) {
                distance += distanceBetweenPoints(i-1, i);
            }
        }

        return distance;
    }

    private double distanceBetweenPoints(int first, int second) {
        // ensure first < second
        if (first > second)
            return distanceBetweenPoints(second, first);

        // return 0 if first or second is an illegal index (or are the same)
        if (first < 0 || second >= points.size() || first == second)
            return 0;

        return points.get(first).distanceTo(points.get(second));
    }

    public boolean swapTwo() {
        // don't even try if last call did no swaps
        if (algShortCircuit[0])
            return false;

        double minDistance = distance;
        int swap1, swap2;
        swap1 = swap2 = -1;

        double tempDistance;
        for (int i = 0; i < points.size(); i++) {
            for (int j = points.size()-1; j > i; j--) {
                tempDistance = distance;

                // remove current distances
                tempDistance -= distanceBetweenPoints(i, i-1);
                tempDistance -= distanceBetweenPoints(i, i+1);
                if (j - 1 != i)  // i and j are not adjacent
                    tempDistance -= distanceBetweenPoints(j, j-1);
                tempDistance -= distanceBetweenPoints(j, j+1);

                // add new distances
                tempDistance += distanceBetweenPoints(j, i-1);
                tempDistance += distanceBetweenPoints(j, i+1);
                if (j - 1 != i)  // i and j are not adjacent
                    tempDistance += distanceBetweenPoints(i, j-1);
                tempDistance += distanceBetweenPoints(i, j+1);

                // save swap indexes if we have a new minimum distance
                if (tempDistance < minDistance) {
                    minDistance = tempDistance;
                    swap1 = i;
                    swap2 = j;
                }
            }
        }

        if (minDistance < distance) {
            distance = minDistance;

            // swap points
            Point temp = points.get(swap2);
            points.set(swap2, points.get(swap1));
            points.set(swap1, temp);
            return true;
        }

        // at this point, no swaps happened
        algShortCircuit[0] = true;
        return false;
    }

    public boolean shiftOne() {
        // don't even try if last call did no swaps
        if (algShortCircuit[1])
            return false;

        double minDistance = distance;
        int shiftFrom, shiftTo;
        shiftFrom = shiftTo = -1;

        double tempDistance;
        for (int i = 0; i < points.size(); i++) {
            for (int j = 0; j < points.size(); j++) {
                if (i == j)
                    continue;

                tempDistance = distance;

                // remove i
                tempDistance -= distanceBetweenPoints(i, i-1);
                tempDistance -= distanceBetweenPoints(i, i+1);

                // connect i's prev and next
                tempDistance += distanceBetweenPoints(i-1, i+1);

                // ==================================================================

                // remove j's link to it's previous
                tempDistance -= distanceBetweenPoints(j-1, j);

                // insert i before j
                tempDistance += distanceBetweenPoints(j-1, i);
                tempDistance += distanceBetweenPoints(i, j);

                // save swap indexes if we have a new minimum distance
                if (tempDistance < minDistance) {
                    minDistance = tempDistance;
                    shiftFrom = i;
                    shiftTo = j;
                }

                // if (minDistance < distance * 0.5) {
                //     j = i = points.size();
                // }
            }
        }

        if (minDistance < distance) {
            distance = minDistance;

            // move point
            points.add(shiftTo, points.remove(shiftFrom));

            return true;
        }

        // at this point, no shift happened
        algShortCircuit[1] = true;
        return false;
    }

    public boolean shiftSection() {

        // at this point, no shift happened
        algShortCircuit[2] = true;
        return false;
    }

    public boolean reverse() {

        // at this point, no reverse happened
        algShortCircuit[3] = true;
        return false;
    }
}
