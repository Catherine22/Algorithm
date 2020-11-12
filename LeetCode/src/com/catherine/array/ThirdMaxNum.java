package com.catherine.array;

import java.util.Arrays;

/**
 * @author : Catherine
 * @created : 12/11/2020
 * <p>
 * Third Maximum Number
 * <p>
 * Given a non-empty array of integers, return the third maximum number in this array. If it does not exist, return the maximum number. The time complexity must be in O(n).
 * <p>
 * Example 1:
 * Input: [3, 2, 1]
 * <p>
 * Output: 1
 * <p>
 * Explanation: The third maximum is 1.
 * Example 2:
 * Input: [1, 2]
 * <p>
 * Output: 2
 * <p>
 * Explanation: The third maximum does not exist, so the maximum (2) is returned instead.
 * Example 3:
 * Input: [2, 2, 3, 1]
 * <p>
 * Output: 1
 * <p>
 * Explanation: Note that the third maximum here means the third maximum distinct number.
 * Both numbers with value 2 are both considered as second maximum.
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/523/conclusion/3231/
 */
public class ThirdMaxNum {
    public int thirdMax(int[] nums) {
        int[] sortedNums = new int[nums.length];
        System.arraycopy(nums, 0, sortedNums, 0, nums.length);
        Arrays.sort(sortedNums);

        int diff = 0;
        int ptr = sortedNums.length - 2;
        int val;
        int prev = sortedNums[sortedNums.length - 1];
        int thirdMax = prev;

        while (ptr >= 0) {
            val = sortedNums[ptr];
            if (val != prev) {
                diff++;
            }

            if (diff == 2) {
                thirdMax = val;
                break;
            }

            prev = val;
            ptr--;
        }

        return thirdMax;
    }
}
