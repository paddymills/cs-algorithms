
import java.util.ArrayList;
import java.util.HashSet;

public class Tour {
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
