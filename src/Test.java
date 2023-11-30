public class Test {
    
    public static void main(String[] args) {
        City[] cities = eTSP.readInput();

        Tour tour = new Tour(cities);
        tour.calculateTour();
    
        System.out.println("Your grade is: " + String.format("%.02f%%", eTSP.grade(tour.length)));

    }

}
