package com.pkumar7.twopointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pankaj Kumar on 21/August/2020
 */
class A {

    /*
     * https://leetcode.com/problems/longest-word-in-dictionary-through-deleting/
     * 524. Longest Word in Dictionary through Deleting
     * */
    public String findLongestWord(String s, List<String> dictionary) {
        Collections.sort(dictionary, Comparator.comparing(a -> -a.length()));
        String max = "";
        for(String dict : dictionary){
            int m = s.length();
            int n = dict.length();
            int i = 0, j = 0;
            while (i < m && j < n){
                if(s.charAt(i) == dict.charAt(j)){
                    i += 1;
                    j += 1;
                }else {
                    i += 1;
                }
            }
            if(j == n){
                if(dict.length() >= max.length()){
                    if(max.length() == dict.length()){
                        if(dict.compareTo(max) < 0){
                            max = dict;
                        }
                    }else{
                        max = dict;
                    }
                }else{
                    break;
                }
            }
        }
        return max;
    }

    /*
    * https://leetcode.com/problems/get-the-maximum-score/
    * */
    public int maxSum(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int i = n - 1;
        int j = m - 1;
        long sum1 = 0;
        long sum2 = 0;
        int mod = (int)1e9 + 7;
        while(i >= 0 || j >= 0){
            if(i >= 0 && (j < 0 || nums1[i] > nums2[j])) {
                sum1 += nums1[i--];
            }else if(j >= 0 && (i < 0 || nums2[j] > nums1[i])) {
                sum2 += nums2[j--];
            }else {
                sum1 = sum2 = Math.max(sum1, sum2) + nums1[i];
                i -= 1;
                j -= 1;
            }
        }
        return (int)(Math.max(sum1, sum2) % mod);
    }

    /* 1570. Dot Product of Two Sparse Vectors
    * https://leetcode.com/problems/dot-product-of-two-sparse-vectors/
    * */
    class SparseVector {
        List<int[]> data = new ArrayList<>();
        SparseVector(int[] nums) {
            for (int i = 0; i < nums.length; i++) {
                if(nums[i] != 0){
                    data.add(new int[]{i, nums[i]});
                }
            }
        }

        // Return the dotProduct of two sparse vectors
        public int dotProduct(SparseVector vec) {
            List<int[]> otherData = vec.getData();
            int prod = 0;
            int i = 0, j = 0;
            while (i < data.size() && j < otherData.size()){
                int[] d = data.get(i);
                int[] o = otherData.get(j);
                if(d[0] < o[0]){
                    i += 1;
                }else if(d[0] > o[0]){
                    j += 1;
                }else{
                    prod += (d[1] * o[1]);
                    i += 1;
                    j += 1;
                }
            }
            return prod;
        }

        public List<int[]> getData(){
            return data;
        }
    }


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

    public static void main(String[] args) {
        A a = new A();
        a.uniqueLetterString("XAX");
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

    public int uniqueLetterStringStr(String s) {
        int n = s.length();
        ArrayList<Integer>[] list = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            list[i] = new ArrayList<>();
        }
        for (int i = 0; i < n; i++) {
            list[s.charAt(i)].add(i);
        }
        long result = 0, mod = (int)1e9+7;;
        for (int i = 0; i < 26; i++) {
            int size = list[i].size();
            for (int j = 0; j < size; j++) {
                int currIndex = list[i].get(j);
                int left = j == 0 ? -1 : list[i].get(j - 1);
                int right = (j == size - 1) ? n : list[i].get(j + 1);
                result += (long) (currIndex - left) * (right - currIndex);
                result %= mod;
            }
        }
        return (int)result;
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
