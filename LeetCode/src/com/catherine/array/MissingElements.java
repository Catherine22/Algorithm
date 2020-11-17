package com.catherine.array;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : Catherine
 * @created : 16/11/2020
 * <p>
 * Find All Numbers Disappeared in an Array
 * <p>
 * Given an array of integers where 1 ≤ a[i] ≤ n (n = size of array), some elements appear twice and others appear once.
 * <p>
 * Find all the elements of [1, n] inclusive that do not appear in this array.
 * <p>
 * Could you do it without extra space and in O(n) runtime? You may assume the returned list does not count as extra space.
 * <p>
 * Example:
 * <p>
 * Input:
 * [4,3,2,7,8,2,3,1]
 * <p>
 * Output:
 * [5,6]
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/523/conclusion/3270/
 */
public class MissingElements {
    public List<Integer> findDisappearedNumbers(int[] nums) {
        List<Integer> missingElements = new LinkedList<>();
        boolean[] temp = new boolean[nums.length];

        for (int num : nums) {
            temp[num - 1] = true;
        }

        for (int i = 0; i < temp.length; i++) {
            if (!temp[i]) {
                missingElements.add(i + 1);
            }
        }

        return missingElements;
    }
}
