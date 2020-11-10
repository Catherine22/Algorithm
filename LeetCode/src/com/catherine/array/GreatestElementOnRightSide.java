package com.catherine.array;

/**
 * @author : Catherine
 * @created : 10/11/2020
 * <p>
 * Replace Elements with Greatest Element on Right Side
 * <p>
 * Given an array arr, replace every element in that array with the greatest element among the elements to its right, and replace the last element with -1.
 * <p>
 * After doing so, return the array.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: arr = [17,18,5,4,6,1]
 * Output: [18,6,6,6,1,-1]
 * <p>
 * <p>
 * Constraints:
 * <p>
 * 1 <= arr.length <= 10^4
 * 1 <= arr[i] <= 10^5
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/511/in-place-operations/3259/
 */
public class GreatestElementOnRightSide {
    public int[] replaceElements(int[] arr) {
        int[] newArr = new int[arr.length];
        int j = 0;
        int max;
        int tmp;
        for (int i = 0; i < arr.length; i++) {
            j = i;
            max = arr[i];
            while (j < arr.length) {
                tmp = arr[j];
                if (tmp > max) {
                    max = tmp;
                }
                j++;
            }
            newArr[i] = max;
        }

        if (newArr.length - 1 >= 0) {
            System.arraycopy(newArr, 1, newArr, 0, newArr.length - 1);
        }
        newArr[newArr.length - 1] = -1;
        return newArr;
    }
}
