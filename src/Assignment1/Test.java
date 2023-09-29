package Assignment1;

import java.util.*;

public class Test {
    double[] vals;
    double result;

    public Test(double[] vals, double result) {
        this.vals = vals;
        this.result = result;
    }

    public static void main(String[] args) {
        Test[] tests = new Test[] {
            new Test(new double[] {7855988}, 7855988),
            new Test(new double[] {3618202, 1242552, 6861892}, 2001138),
            new Test(new double[] {3752797, 3301118, 8946152, 2027054, 3895471}, 23820)
        };
        
        for (Test test : tests) {
            double result = Solution.getResult(test.vals);

            System.out.print(String.format("Test `%s` ", Arrays.toString(test.vals)));
            if (Math.abs(result) == test.result) {
                System.out.println("succeeded");
                
                System.out.println("==== solutions: ");
                double[] vals = new double[test.vals.length+1];
                System.arraycopy(test.vals, 0, vals, 0, test.vals.length);
                vals[vals.length-1] = result;

                ExtraCredit.getAllSolutions(vals);
            }
            else
                System.out.println(String.format("failed: %s != %s", test.result, result));
        }
    }
}
