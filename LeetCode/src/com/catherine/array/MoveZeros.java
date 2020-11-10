package com.catherine.array;

/**
 * @author : Catherine
 * @created : 10/11/2020
 * <p>
 * Move Zeros
 * <p>
 * Given an array nums, write a function to move all 0's to the end of it while maintaining the relative order of the non-zero elements.
 * <p>
 * Example:
 * <p>
 * Input: [0,1,0,3,12]
 * Output: [1,3,12,0,0]
 * Note:
 * <p>
 * You must do this in-place without making a copy of the array.
 * Minimize the total number of operations.
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/511/in-place-operations/3157/
 */
public class MoveZeros {
    class Solution {
        public void moveZeroes(int[] nums) {
            int ptr = 0;
            int scanPtr = 0;
            int scanVal;
            while (scanPtr < nums.length) {
                scanVal = nums[scanPtr];
                if (scanVal != 0) {
                    nums[ptr] = scanVal;
                    if (ptr != scanPtr) {
                        nums[scanPtr] = 0;
                    }
                    ptr++;
                }
                scanPtr++;
            }
        }
    }
}
