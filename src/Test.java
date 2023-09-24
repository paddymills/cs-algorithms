
import java.util.*;

public class Test {
    int n, k;
    int[] vals;
    int result;

    public Test(int k, int[] vals, int result) {
        this.k = k;
        this.vals = vals;
        this.result = result;
    }

    public static void main(String[] args) {
        Test[] tests = new Test[] {
            new Test( 2, new int[] {3,5,1,1}, 2 ),
            new Test( 4, new int[] {30,40,20,41,50}, 1 ),
        };
        
        for (Test test : tests) {
            String vals = Arrays.toString(test.vals);
            int result = Solution.getSumOfSpreads(test.k, test.vals);

            System.out.print(String.format("Test `%s` ", vals));
            if (result == test.result) {
                System.out.println("succeeded");;
            }
            else
                System.out.println(String.format("failed: %s != %s", test.result, result));
        }
    }
}
