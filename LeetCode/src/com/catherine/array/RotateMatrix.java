package com.catherine.array;

/**
 * @author : Catherine
 * @created : 23/06/2021
 *
 * From "Cracking the Coding Interview"
 *
 * 1.7 Rotate Matrix: Given an image represented by an NxN matrix, where each pixel in the image is 4 bytes, write a method to rotate the image by 90 degrees. Can you do this in place?
 * EXAMPLE
 * 1, 2, 3
 * 4, 5, 6
 *
 * 4, 1
 * 5, 2
 * 6, 3
 */
public class RotateMatrix {

    public int[][] rotate90Degrees(int[][] matrix, boolean isRight) {
        // skip input validation
        int y = matrix.length;
        int x = matrix[0].length;
        System.out.println("x:" + x + ", y:" + y);
        int[][] rotatedMatrix = new int[x][y];
        System.out.println(rotatedMatrix.length);
        if (isRight) {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    rotatedMatrix[i][y - j - 1] = matrix[j][i];
                }
            }
        } else {
            for (int i = 0; i < x; i++) {
                for (int j = 0; j < y; j++) {
                    rotatedMatrix[i][j] = matrix[j][x - i - 1];
                }
            }
        }

//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[i].length; j++) {
//                System.out.print(matrix[i][j] + " ");
//            }
//            System.out.print("\n");
//        }
//
//        for (int i = 0; i < rotatedMatrix.length; i++) {
//            for (int j = 0; j < rotatedMatrix[i].length; j++) {
//                System.out.print(rotatedMatrix[i][j] + " ");
//            }
//            System.out.print("\n");
//        }

        return rotatedMatrix;
    }
}
