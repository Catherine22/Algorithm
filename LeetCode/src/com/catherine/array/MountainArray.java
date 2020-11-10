package com.catherine.array;

/**
 * @author : Catherine
 * @created : 08/11/2020
 * <p>
 * Valid Mountain Array
 * <p>
 * Given an array of integers arr, return true if and only if it is a valid mountain array.
 * <p>
 * Recall that arr is a mountain array if and only if:
 * <p>
 * arr.length >= 3
 * There exists some i with 0 < i < arr.length - 1 such that:
 * arr[0] < arr[1] < ... < arr[i - 1] < A[i]
 * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 * <p>
 * Example 1:
 * <p>
 * Input: arr = [2,1]
 * Output: false
 * Example 2:
 * <p>
 * Input: arr = [3,5,5]
 * Output: false
 * Example 3:
 * <p>
 * Input: arr = [0,3,2,1]
 * Output: true
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= arr.length <= 104
 * 0 <= arr[i] <= 104
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/527/searching-for-items-in-an-array/3251/
 */
public class MountainArray {
    public boolean validMountainArray(int[] arr) {

        if (arr.length == 1) {
            return false;
        }

        boolean increased = false;
        boolean decreased = false;
        int prev = arr[0];
        int current;
        for (int i = 1; i < arr.length; i++) {
            current = arr[i];
            if (current == prev) {
                return false;
            } else if (current > prev) {
                increased = true;
                if (decreased) {
                    return false;
                }
            } else {
                decreased = true;
                if (!increased) {
                    return false;
                }
            }
            prev = current;
        }
        return decreased;
    }
}
