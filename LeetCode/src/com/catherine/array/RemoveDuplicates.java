package com.catherine.array;

/**
 * @author : Catherine
 * @created : 06/11/2020
 * <p>
 * Remove Duplicates from Sorted Array
 * <p>
 * Given a sorted array nums, remove the duplicates in-place such that each element appears only once and returns the new length.
 * <p>
 * Do not allocate extra space for another array, you must do this by modifying the input array in-place with O(1) extra memory.
 * <p>
 * Clarification:
 * <p>
 * Confused why the returned value is an integer but your answer is an array?
 * <p>
 * Note that the input array is passed in by reference, which means a modification to the input array will be known to the caller as well.
 * <p>
 * Internally you can think of this:
 * <p>
 * // nums is passed in by reference. (i.e., without making a copy)
 * int len = removeDuplicates(nums);
 * <p>
 * // any modification to nums in your function would be known by the caller.
 * // using the length returned by your function, it prints the first len elements.
 * for (int i = 0; i < len; i++) {
 * print(nums[i]);
 * }
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: nums = [1,1,2]
 * Output: 2, nums = [1,2]
 * Explanation: Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively. It doesn't matter what you leave beyond the returned length.
 * Example 2:
 * <p>
 * Input: nums = [0,0,1,1,1,2,2,3,3,4]
 * Output: 5, nums = [0,1,2,3,4]
 * Explanation: Your function should return length = 5, with the first five elements of nums being modified to 0, 1, 2, 3, and 4 respectively. It doesn't matter what values are set beyond the returned length.
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 0 <= nums.length <= 3 * 104
 * -104 <= nums[i] <= 104
 * nums is sorted in ascending order.
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/526/deleting-items-from-an-array/3248/
 */
public class RemoveDuplicates {
    public int removeDuplicates(int[] nums) {

        if (nums.length == 0) {
            return 0;
        }

        if (nums.length == 1) {
            return 1;
        }

        int len = 1; // the length of nums must more than 1
        int prev = nums[0];
        int val;
        for (int i = 1; i < nums.length; i++) {
            val = nums[i];
            if (val != prev) {
                len++;
                prev = val;
            }
        }

        int ptr = 1;
        int tmp[] = new int[len];
        prev = nums[0];
        tmp[0] = prev;
        for (int i = 1; i < nums.length; i++) {
            val = nums[i];
            if (val != prev) {
                tmp[ptr++] = val;
                prev = val;
            }
        }

        System.arraycopy(tmp, 0, nums, 0, len);
        return len;
    }
}
