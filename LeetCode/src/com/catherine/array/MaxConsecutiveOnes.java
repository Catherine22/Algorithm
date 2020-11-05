package com.catherine.array;

/**
 * @author : Catherine
 * @created : 04/11/2020
 * <p>
 * Given a binary array, find the maximum number of consecutive 1s in this array.
 * <p>
 * Example 1:
 * Input: [1,1,0,1,1,1]
 * Output: 3
 * Explanation: The first two digits or the last three digits are consecutive 1s.
 * The maximum number of consecutive 1s is 3.
 * <p>
 * Note:
 * <p>
 * The input array will only contain 0 and 1.
 * The length of input array is a positive integer and will not exceed 10,000
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/521/introduction/3238/
 */
public class MaxConsecutiveOnes {
    public int findMaxConsecutiveOnes(int[] nums) {
        if (nums.length == 0 || nums.length > 10000) {
            return 0;
        }

        int count = 0;
        int max = 0;
        int prev = -1;

        for (int num : nums) {

            if (num != prev) {
                count = num == 1 ? 1 : 0;
            } else {
                if (num == 1) {
                    count++;
                }
            }

            if (count > max) {
                max = count;
            }
            prev = num;
        }

        return max;
    }
}
