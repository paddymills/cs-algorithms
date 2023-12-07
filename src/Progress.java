

public class Progress {
    int NUM_STEPS = 20;

    double complete;
    double total;
    String desc, info;

    public Progress(String desc, double total) {
        this.complete = 0;
        this.total = total;
        this.desc = desc;
        this.info = new String();
    }

    public void update() {
        if (complete == total)
            total += total * 0.10;

        update(complete+1);
    }

    public void info(String info) {
        this.info = info;
    }

    public void update(double complete) {
        this.complete = complete;

        if (complete > total) {
            throw new IllegalArgumentException();
        }

        if (complete == total) {
            finish();
            return;
        }
        
        StringBuilder bar = new StringBuilder("\r" + desc);
        if (info.length() > 0) 
            bar.append("(" + info + ")");
        bar.append(" |");
        
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
