package com.pkumar7.twopointers;

import java.util.Arrays;

/**
 * Created by Pankaj Kumar on 21/August/2020
 */
class A {

    /* 1750. Minimum Length of String After Deleting Similar Ends
     * https://leetcode.com/problems/minimum-length-of-string-after-deleting-similar-ends/
     * */
    public int minimumLength(String s) {
        int i = 0, j = s.length() - 1;
        int n = s.length();
        while (i < j && s.charAt(i) == s.charAt(j)){
            char start = s.charAt(i);
            while(i <= j && s.charAt(i) == start){
                i++;
            }
            while(i <= j && s.charAt(j) == start){
                --j;
            }
        }
        return j - i + 1;
    }

    /* 881. Boats to Save People
     * https://leetcode.com/problems/boats-to-save-people/
     * */
    public int numRescueBoats(int[] arr, int limit) {
        int n = arr.length;
        Integer[] people = new Integer[n];
        for (int i = 0; i < n; i++) {
            people[i] = arr[i];
        }
        Arrays.sort(people, (a, b) -> b - a);
        int boats = 0;
        for (int i = 0, j = n - 1; i <= j; ) {
            if (people[i] + people[j] <= limit) {
                i += 1;
                j -= 1;
            } else if (people[i] + people[j] > limit) {
                i += 1;
            }
            boats += 1;
        }
        return boats;
    }

    /* 1861. Rotating the Box
     *  https://leetcode.com/problems/rotating-the-box/
     * */
    public char[][] rotateTheBox(char[][] box) {
        int m = box.length;
        int n = box[0].length;
        char[][] res = new char[n][m];
        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = n - 1; j >= 0;) {
                int start = j;
                while (start >= 0 && box[i][start] != '*'){
                    if(box[i][start] == '#'){
                        count++;
                    }
                    start--;
                }
                while (count-- > 0){
                    res[j--][m - i] = '#';
                }
                while (j > start){
                    res[j--][m - i] = '.';
                }
                if(start >= 0){
                    res[start][m - i] = '*';
                    j = start - 1;
                    count = 0;
                }
            }
        }
        return res;
    }

    /*
    828. Count Unique Characters of All Substrings of a Given String
    https://leetcode.com/problems/count-unique-characters-of-all-substrings-of-a-given-string/
    */
    public int uniqueLetterString(String s) {
        int n = s.length();
        int[][] index = new int[26][2];
        for (int i = 0; i < 26; i++) {
            Arrays.fill(index[i], -1);
        }
        int res = 0, mod = (int)1e9+7;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'A';
            res = (res + (i - index[c][1]) * (index[c][1] - index[c][0]) % mod) % mod;
            index[c][0] = index[c][1];
            index[c][1] = i;
        }
        for (int i = 0; i < 26; i++) {
            res = (res + (n - index[i][1]) * (index[i][1] - index[i][0]) % mod ) % mod;
        }
        return res;
    }

    /* 977. Squares of a Sorted Array
    * https://leetcode.com/problems/squares-of-a-sorted-array/
    */
    public int[] sortedSquares(int[] A) {
        int n = A.length;
        int i = 0, j = n - 1;
        int[] result = new int[A.length];
        for (int p = n - 1; p >= 0; --p) {
            if(Math.abs(A[i]) > Math.abs(A[j])) {
                result[p] = A[i] * A[i];
                i++;
            }else{
                result[p] = A[j] * A[j];
                j--;
            }
        }
        return result;
    }
}
