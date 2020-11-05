package com.catherine.array;

import java.util.Arrays;

/**
 * @author : Catherine
 * @created : 05/11/2020
 * <p>
 * Squares of a Sorted Array
 * <p>
 * Given an array of integers A sorted in non-decreasing order, return an array of the squares of each number, also in sorted non-decreasing order.
 * <p>
 * Example 1:
 * <p>
 * Input: [-4,-1,0,3,10]
 * Output: [0,1,9,16,100]
 * <p>
 * Example 2:
 * <p>
 * Input: [-7,-3,2,3,11]
 * Output: [4,9,9,49,121]
 * <p>
 * Note:
 * <p>
 * 1 <= A.length <= 10000
 * -10000 <= A[i] <= 10000
 * A is sorted in non-decreasing order.
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/521/introduction/3240/
 */
public class SortedSquares {
    public int[] sortedSquares(int[] A) {
        int[] squares = new int[A.length];
        System.arraycopy(A, 0, squares, 0, A.length);

        for (int i = 0; i < squares.length; i++) {
            squares[i] = (int) Math.pow(squares[i], 2);
        }

        Arrays.sort(squares);
        return squares;
    }
}
