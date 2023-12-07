
import java.util.ArrayList;

public class Tour {

    static class Node {
        Point Point;
        Edge prev, next;

        public Node(Point Point) {
            this.Point = Point;
        }

        public Node(Point Point, Edge prev) {
            this.Point = Point;
            this.prev = prev;
        }

        public void setNext(Point next) {
            this.next = new Edge(this, new Node(next));
        }

        public void setNext(Node next) {
            this.next = new Edge(this, next);
        }

        public void setPrev(Point prev) {
            this.prev = new Edge(this, new Node(prev));
        }

        public void setPrev(Node prev) {
            this.prev = new Edge(this, prev);
        }
    }

    static class Edge {
        double distance;
        Node a, b;

        public Edge(Node a, Node b) {
            this.distance = a.Point.distanceTo(b.Point);
            
            this.a = a;
            this.b = b;

            // join nodes by this edge
            a.next = this;
            b.prev = this;
        }
        
        public Node nextNode(Node currentNode) {
            return currentNode == this.a ? this.b : this.a;  
        }
    }

    public ArrayList<Integer> stops;
    public double length;
    Node start;
    Point[] cities;

    public Tour(Point[] cities) {
        this.stops = new ArrayList<>();
        this.cities = cities;

        initTour();
    }

    public void output() {
        System.out.println(String.format("%.07f", length));
        for (int stop : stops) {
            System.out.println(cities[stop].toString());
        }
    }

    void initTour() {
        Node current;

        start = new Node(cities[0]);
        current = start;

        Progress bar = new Progress("init", cities.length);
        for (int i = 1; i < cities.length; i++) {
            bar.update(i);
            current.setNext(new Node(cities[i]));
            length += current.next.distance;
            current = current.next.nextNode(current);
        }
        current.setNext(start);
        length += current.next.distance;
        bar.finish();
    }

    public void calculateTour() {
        // initTour();
    }

    public boolean shiftOneStop() {
        boolean newTour = false;

        Progress bar = new Progress("Finding shift", Math.pow(cities.length, 2));
        for (int i = 0; i < cities.length; i++) {
            for (int j = 0; j < cities.length; j++) {
                bar.update(i*j);

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
        bar.finish();

        return newTour;
    }
}
