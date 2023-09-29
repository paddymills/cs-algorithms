package Assignment1;
/*
 * Patrick Miller
 * CMPSC 463
 * Assignment #1: Solution
 */

import java.util.*;

public class Solution {


    public static void main(String[] args) {
        System.out.println(
            String.format("%.0f", getResult( getNumbers() ))
        );
    }

    public static double getResult(double[] numbers) {
        return helper(numbers, 1, new ArrayList<Character>());
    }

    public static double[] getNumbers() {
        // read input from stdin
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        scanner.close();
        
        double[] nums = Arrays.asList( input.split("\\s+") )    // split on whitespace
            .stream()
            .mapToDouble(num -> {
                try { return Double.parseDouble(num); }
                finally {}  // ignore parse errors, shouldn't happen
            })
            .toArray();

        return nums;
    }

    public static double evalutateEquation(double[] nums, ArrayList<Character> ops) {
        double result = nums[0];

        int direction = 1;
        for (int i = 0; i<ops.size(); i++) {
            char op = ops.get(i);
            switch ( op ) {
                case '-':
                    result -= nums[i+1] * direction;
                    break;
                case '+':
                    result += nums[i+1] * direction;
                    break;
                case '=':
                    direction *= -1;    // flip direction
                    result += nums[i+1] * direction;

                    break;
            
                default:
                    break;
            }
        }

        return result;
    }

    public static double helper(double[] nums, int index, ArrayList<Character> ops) {
        if ( index >= nums.length ) {
            // equals must be used on inputs of more than 1 number
            if (nums.length > 1 && !ops.contains('='))
                return Double.MAX_VALUE;

            return Math.abs(evalutateEquation(nums, ops));
        }

        // calculate using minus tree
        ops.add('-');
        double minus = helper(nums, index+1, ops);
        ops.remove(ops.size()-1);

        // calculate using plus tree
        ops.add('+');
        double plus = helper(nums, index+1, ops);
        ops.remove(ops.size()-1);

        // calculate min
        double result = Math.min(minus, plus);

        // calculate using equals, if not used yet
        if (!ops.contains('=')) {
            ops.add('=');
            result = Math.min(result, helper(nums, index+1, ops));
            ops.remove(ops.size()-1);
        }

        return result;
    }
}