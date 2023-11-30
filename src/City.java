
public class City {
    int x, y;

    public City(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public City(String line) {
        String[] vals = line.split("\\s+");

        this.x = Integer.parseInt( vals[0] );
        this.y = Integer.parseInt( vals[1] );
    }

    // euclidean distance from docs
    public double distanceTo(City other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return this.x + " " + this.y;
    }
}
