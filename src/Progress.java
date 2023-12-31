

public class Progress {
    int NUM_STEPS = 20;

    double total;
    String desc;

    public Progress(String desc, double total) {
        this.total = total;
        this.desc = desc;
    }

    public void update(int complete) {
        if (complete > total) {
            throw new IllegalArgumentException();
        }

        if (complete == total) {
            finish();
            return;
        }
        
        StringBuilder bar = new StringBuilder("\r" + desc + " |");
        
        double percent = complete / total * 100;
        for (int i=0; i<100; i+=100/NUM_STEPS) {
            bar.append( i < percent ? "=" : "-" );
        }
        
        bar.append("| " + String.format("%.02f%%", percent).toString());
        System.out.print(bar.toString());
    }

    public void finish() {
        System.out.println("\r");
    }
}
