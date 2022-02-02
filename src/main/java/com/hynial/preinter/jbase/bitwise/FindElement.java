package com.hynial.preinter.jbase.bitwise;

public class FindElement {
    //    https://www.geeksforgeeks.org/find-the-element-that-appears-once/
    // Method to find the element that occur only once

    /**
     * https://www.careercup.com/question?id=7902674
     * ones - At any point of time, this variable holds XOR of all the elements which have appeared "only" once.
     * twos - At any point of time, this variable holds XOR of all the elements which have appeared "only" twice.
     *
     * So if at any point time,
     * 1. A new number appears - It gets XOR'd to the variable "ones".
     * 2. A number gets repeated(appears twice) - It is removed from "ones" and XOR'd to the variable "twice".
     * 3. A number appears for the third time - It gets removed from both "ones" and "twice".
     * @param arr
     * @param n
     * @return
     */
    static int getSingle(int arr[], int n) {
        int ones = 0, twos = 0;
        int common_bit_mask;

        for (int i = 0; i < n; i++) {
            /*"one & arr[i]" gives the bits that are there in
            both 'ones' and new element from arr[]. We
            add these bits to 'twos' using bitwise OR*/
            twos = twos | (ones & arr[i]);
//            twos = twos ^ (ones & arr[i]);

            /*"one & arr[i]" gives the bits that are
            there in both 'ones' and new element from arr[].
            We add these bits to 'twos' using bitwise OR*/
            ones = ones ^ arr[i];

            /* The common bits are those bits which appear third time
            So these bits should not be there in both 'ones' and 'twos'.
            common_bit_mask contains all these bits as 0, so that the bits can
            be removed from 'ones' and 'twos'*/
            // ~x = -x - 1 计算机内部采用2的补码（Two's Complement）表示负数。
            /**
             * not_threes = ~(ones & twos)
             * ones & = not_threes
             * twos & = not_threes
             * All it does is, common 1's between "ones" and "twos" are converted to zero.
             */
            common_bit_mask = ~(ones & twos);

            /*Remove common bits (the bits that appear third time) from 'ones'*/
            ones &= common_bit_mask;

            /*Remove common bits (the bits that appear third time) from 'twos'*/
            twos &= common_bit_mask;
        }
        return ones;
    }

    static final int INT_SIZE = 32;

    // Method to find the element that occur only once
    //  this approach won’t work for negative numbers
    static int getSingle2(int arr[], int n) {
        int result = 0;
        int x, sum;

        // Iterate through every bit
        for (int i = 0; i < INT_SIZE; i++) {
            // Find sum of set bits at ith position in all
            // array elements
            sum = 0;
            x = (1 << i);
            for (int j = 0; j < n; j++) {
                if ((arr[j] & x) != 0)
                    sum++;
            }
            // The bits with sum not multiple of 3, are the
            // bits of element with single occurrence.
            if ((sum % 3) != 0)
                result |= x;
        }
        return result;
    }


    // Driver method
    public static void main(String args[]) {
        int arr[] = {3, 4, 99, 3, 4, 3, 4};
        int n = arr.length;
        System.out.println("The element with single occurrence is " + getSingle(arr, n));
        System.out.println("The element with single occurrence is " + getSingle2(arr, n));

        int a = 3 ^ 4 ^ 3 ^ 5 ^ 6 ^ 7 ^ 5 ^ 6 ^ 4; // x^x = 0  0^x = x
        System.out.println(a);
    }
}
