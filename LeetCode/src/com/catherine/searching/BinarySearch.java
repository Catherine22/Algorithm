package com.catherine.searching;

/**
 * @author : Catherine
 * @created : 28/06/2021
 */
public class BinarySearch {
    public int search(int[] array, int n) {
        int low = 0;
        int high = array.length;
        int mid = array.length / 2;

        while (high > low) {
            if (array[mid] > n) {
                high = mid;
                mid = (low + high) / 2;
            } else if (array[mid] < n) {
                low = mid;
                mid = (low + high) / 2;
            } else {
                return mid;
            }
        }

        return -1;
    }
}
