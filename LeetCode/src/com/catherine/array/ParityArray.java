package com.catherine.array;

/**
 * @author : Catherine
 * @created : 10/11/2020
 * <p>
 * Sort Array By Parity
 * <p>
 * Given an array A of non-negative integers, return an array consisting of all the even elements of A, followed by all the odd elements of A.
 * <p>
 * You may return any answer array that satisfies this condition.
 * <p>
 * <p>
 * <p>
 * Example 1:
 * <p>
 * Input: [3,1,2,4]
 * Output: [2,4,3,1]
 * The outputs [4,2,3,1], [2,4,1,3], and [4,2,1,3] would also be accepted.
 * <p>
 * <p>
 * Note:
 * <p>
 * 1 <= A.length <= 5000
 * 0 <= A[i] <= 5000
 * <p>
 * https://leetcode.com/explore/learn/card/fun-with-arrays/511/in-place-operations/3260/
 */
public class ParityArray {
    public int[] sortArrayByParity(int[] A) {
        int evenNums = 0;
        for (int n : A) {
            if (n % 2 == 0) {
                evenNums++;
            }
        }

        int evenPtr = 0;
        int oddPtr = 0;
        int[] evenArr = new int[evenNums];
        int[] oddArr = new int[A.length - evenNums];
        for (int n : A) {
            if (n % 2 == 0) {
                evenArr[evenPtr++] = n;
            } else {
                oddArr[oddPtr++] = n;
            }
        }

        int[] result = new int[A.length];
        System.arraycopy(evenArr, 0, result, 0, evenArr.length);
        System.arraycopy(oddArr, 0, result, evenArr.length, oddArr.length);
        return result;
    }
}
