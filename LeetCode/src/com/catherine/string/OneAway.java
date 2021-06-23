package com.catherine.string;

/**
 * @author : Catherine
 * @created : 23/06/2021
 *
 * From "Cracking the Coding Interview"
 *
 * 1.5 One Away: There are three types of edits that can be performed on strings: insert a character, remove a character, or replace a character. Given two strings, write a function to check if they are one edit (or zero edits) away.
 * EXAMPLE
 * pale, ple -> true pales,
 * pale -> true pale,
 * bale -> true pale,
 * bake -> false
 */
public class OneAway {
    public boolean isOneAway(String str1, String str2) {
        char[] charArr1 = str1.toCharArray();
        char[] charArr2 = str2.toCharArray();

        int idx1 = 0;
        int idx2 = 0;
        int diff = 0;
        while (idx1 < charArr1.length && idx2 < charArr2.length) {
            if (charArr1[idx1] != charArr2[idx2]) {
                diff++;
                if (charArr1.length > charArr2.length) {
                    idx1++;
                } else if (charArr1.length < charArr2.length) {
                    idx2++;
                } else {
                    idx1++;
                    idx2++;
                }
            } else {
                idx1++;
                idx2++;
            }

            if (diff > 1) {
                return false;
            }
        }
        return true;
    }
}
