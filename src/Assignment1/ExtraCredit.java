package Assignment1;
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #1: Extra Credit
 */


import java.util.ArrayList;

public class ExtraCredit {
    public static void main(String[] args) {
        double[] numbers = Solution.getNumbers();

        double[] vals = new double[numbers.length+1];
        System.arraycopy(numbers, 0, vals, 0, numbers.length);
        vals[vals.length-1] = Solution.getResult(numbers);
        
        getAllSolutions(vals);
    }

    public static void getAllSolutions(double[] numbers) {
        assert numbers.length > 1;
        
        ArrayList<String> eq = new ArrayList<String>();
        ecHelper(numbers, 1, new ArrayList<Character>(), eq);
        for (String equation : eq)
            System.out.println(equation);
    }
    
    public static void ecHelper(double[] nums, int index, ArrayList<Character> ops, ArrayList<String> equations) {
        if ( index >= nums.length ) {
            // equals must be used on inputs of more than 1 number
            if (ops.contains('=')) {
                // System.out.println( String.format("%s = %.0f", ops.toString(), Solution.evalutateEquation(nums, ops)) );
                if (Solution.evalutateEquation(nums, ops) == 0) {
                    StringBuilder str = new StringBuilder(Double.toString(nums[0]));

                    for (int i = 0; i<ops.size(); i++) {
                        str.append(
                            String.format(" %s %s", ops.get(i), Double.toString(nums[i+1]))
                        );
                    }

                    equations.add(str.toString());
                }
            }

            return;
        }

        // calculate using minus tree
        ops.add('-');
        ecHelper(nums, index+1, ops, equations);
        ops.remove(ops.size()-1);

        // calculate using plus tree
        ops.add('+');
        ecHelper(nums, index+1, ops, equations);
        ops.remove(ops.size()-1);

        // calculate using equals, if not used yet
        if (!ops.contains('=')) {
            ops.add('=');
            ecHelper(nums, index+1, ops, equations);
            ops.remove(ops.size()-1);
        }
    }
}
