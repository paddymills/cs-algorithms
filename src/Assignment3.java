
/*
* Patrick Miller
* CMPSC 463
* Assignment #3
*/

import java.util.Random;

public class Assignment3 {
    static class Pair {
        int left, right;
        Pair(int left, int right) {
            this.left = left;
            this.right = right;
        }
    }
    
    /**
     *  Return a Pair of indices, such that 
     *    Pair.left <= Pair.right, and 
     *    for p <= i < Pair.left, array[i] < array[Pair.left], and
     *    for Pair.left <= i <= Pair.right, array[i] == array[Pair.left], and 
     *    for Pair.right < i < q, array[Pair.right] < array[i]     
     *   
     *   @param array the array of values
     *   @param p the first index in the array fragment of interest
     *   @param q one past the last index in the array fragment of interest
     *   @return see above
     */
    static Pair partition(int[] array, int p, int q) {
        int i = p + 1;
        Pair pair = new Pair(p,p);  // pivot element(s)
        
        // Loop invariants:
        // 1) All values in array[p .. pair.left-1] are less than array[Pair.left]
        // 2) All values in array[pair.left .. pair.right] are equal
        // 3) All values in array[pair.right+1 .. i] are greater than array[Pair.right]
        while (i < q) {
            // TODO: Complete the code in the loop so that:
            //       1) The loop invariant is maintained, even as we increment i
            //       2) The loop runs in O(n) time where n = q - p

            // element is less than pair
            if ( array[i] < array[pair.left]) {
                // essentially, move element before pair and shift pair down one

                // swap current element with pair.left to put element before pair
                swap(array, i, pair.left);
                pair.left++;

                // item at i was the first element of pair, so swap it with first element after pair
                swap(array, pair.right+1, i);
                pair.right++;
            }

            // element equals pair, so absorb it into pair
            if ( array[i] == array[pair.left] ) {
                swap(array, i, pair.right+1);
                pair.right++;
            }

            // element is greater than pair -> where it needs to be so do nothing

            i++;
        }
        
        return pair;
    }

    /**
     * Sort values in array[p..q)
     * @param array the array of values to be sorted
     * @param p the index of the first element in the array to be sorted
     * @param q one past index of the last element in the array to be sorted
     */
    static void quicksort(int[] array, int p, int q) {
        // TODO: Complete the code to use the version of partition that you
        // completed above
        if ( p < q ) {
            Pair r = partition(array, p, q);

            quicksort(array, p, r.left);
            quicksort(array, r.right+1, q);
        }
    }
    
    /**
     * Ensure that the element at index k is in its final position, were the
     * entire array to be sorted. Note: This is the element that is in array[k] after
     * sortOne completes, not the element that is in array[k] when the method is
     * first called.
     * 
     * Note this algorithm should run in O(n) average case running time.
     * 
     * @param array the array containing the values to be sorted
     * @param k the element of interest
     * @return array[k]
     */
    static int sortOne(int[] array, int k) {
        return sortOne(array, k, 0, array.length);
    }
    
    static int sortOne(int[] array, int k, int p, int q) {
        // TODO: Complete this method so that sortOne runs in O(n) in the average case
        
    }

    static void swap(int[] array, int a, int b) {
        int temp;

        temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
    
    // Testing code, do not modify
    static Random prng = new Random(42);
    public static void main(String[] args) {
        
        int test = 0;
        for (int size : new int[] {10, 100, 1000, 10000, 100_000, 1_000_000}) {
            for (int duplicates : new int[] {1, 2, 5, 10, 100}) {
                ++test;
                if (!testSort(size, duplicates)) {
                    System.err.printf("Failed sorting test %d, with array size of %d and number of duplicates %d\n", test, size, duplicates);
                    return;
                }
                ++test;
                if (!testSortOne(size, duplicates)) {
                    System.err.printf("Failed sort one test %d, with array size of %d and number of duplicates %d\n", test, size, duplicates);
                    return;
                }
            }
        }        
        System.err.println("Passed all tests");
    }
    
    public static boolean testSort(int size, int duplicates) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = i / duplicates;
        }
        for (int i = 0; i < size; i++) {
            int ndx = prng.nextInt(size);
            int tmp = array[i];
            array[i] = array[ndx];
            array[ndx] = tmp;
        }
        quicksort(array, 0, array.length);
        for (int i = 0; i < size; i++) {
            if (array[i] != i / duplicates) return false;
        }
        return true;        
    }

    public static boolean testSortOne(int size, int duplicates) {
        for (int t = 0; t < 10; t++) {
            int[] array = new int[size];
            for (int i = 0; i < size; i++) {
                array[i] = i / duplicates;
            }
            for (int i = 0; i < size; i++) {
                int ndx = prng.nextInt(size);
                int tmp = array[i];
                array[i] = array[ndx];
                array[ndx] = tmp;
            }
            int ndx = prng.nextInt(size);
            int value = sortOne(array, ndx);
            if (value != ndx / duplicates) return false;
        }
        return true;        
    }

}