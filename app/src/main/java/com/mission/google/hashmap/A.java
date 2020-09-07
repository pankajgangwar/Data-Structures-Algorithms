package com.mission.google.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 06/September/2020
 */
class A {

    public static void main(String[] args) {

    }

    /* 1577. Number of Ways Where Square of Number Is Equal to Product of Two Numbers
     * https://leetcode.com/problems/number-of-ways-where-square-of-number-is-equal-to-product-of-two-numbers/
     * Two - Product similar to Two - Sum
     * */
    public int numTriplets(int[] nums1, int[] nums2) {
        int count = 0;
        for (long n : nums1) {
            count += twoProduct(n * n, nums2);
        }
        for (long n : nums2) {
            count += twoProduct(n * n, nums1);
        }
        System.out.println(count);
        return count;
    }

    public int twoProduct(long a, int[] nums){
        HashMap<Long, Integer> map = new HashMap<>();
        int count = 0;
        for (long x : nums) {
            if(a % x == 0) {
                count += map.getOrDefault(a / x, 0);
            }
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        return count;
    }

    /*
     * 149. Max Points on a Line
     * https://leetcode.com/problems/max-points-on-a-line/
     * */
    public int maxPoints(int[][] points) {
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            map.clear();
            int overlap = 0;
            int max = 0;
            for (int j = i + 1; j < points.length; j++) {
                int x = points[i][0] - points[j][0];
                int y = points[i][1] - points[j][1];
                if(x == 0 && y == 0){
                    overlap++;
                    continue;
                }
                int gcd = gcd(x, y);
                if(gcd != 0){
                    x = x / gcd;
                    y = y / gcd;
                }
                if(map.containsKey(x)){
                    HashMap<Integer,Integer> m = map.get(x);
                    if(m.containsKey(y)){
                        map.get(x).put(y, m.get(y) + 1);
                    }else{
                        m.put(y, 1);
                        map.put(x, m);
                    }
                }else{
                    HashMap<Integer, Integer> m = new HashMap<>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max = Math.max(max, map.get(x).get(y));
            }
            res = Math.max(res, max + overlap + 1);
        }
        return res;
    }

    public int gcd(int x, int y){
        if(y == 0) return x;
        return gcd(y,x % y);
    }

    /*
     * 760. Find Anagram Mappings
     * https://leetcode.com/problems/find-anagram-mappings/
     */
    public int[] anagramMappings(int[] A, int[] B) {
        int[] res = new int[A.length];
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();

        for(int i = 0; i < A.length; i++){
            map.putIfAbsent(A[i], new LinkedList<Integer>());
            map.get(A[i]).add(i);
        }

        for(int j = 0; j < B.length; j++){
            res[map.get(B[j]).pollFirst()] = j;
        }
        return res;
    }

    /*
     * 389. Find the Difference
     * https://leetcode.com/problems/find-the-difference/
     * */
    public char findTheDifference(String s, String t) {
        return UsingBitManipulation(s, t);
    }

    public char UsingBitManipulation(String s, String t){
        char c = 0;
        for(char ch : s.toCharArray()){
            c ^= ch;
        }
        for(char ch : t.toCharArray()){
            c ^= ch;
        }
        return c;
    }

    public char usingMap(String s, String t){
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        int[] diff = new int[26];

        for(char ch : a){
            diff[ch - 'a']++;
        }
        for(char ch : b){
            if(diff[ch - 'a'] > 0){
                diff[ch - 'a']--;
            }else{
                return ch;
            }
        }
        return 'a';
    }

    /*
    187. Repeated DNA Sequences
    https://leetcode.com/problems/repeated-dna-sequences/ */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if(s.length() < 10 ) return res;

        HashMap<String, Integer> map = new HashMap<>();

        String first = s.substring(0, 10);

        map.put(first, map.getOrDefault(first, 0) + 1);

        for(int i = 1; i + 9 < s.length(); i++){
            String curr = s.substring(i, i + 10);
            if(map.containsKey(curr)){
                if(!res.contains(curr)){
                    res.add(curr);
                }
            }else{
                map.put(curr, map.getOrDefault(curr, 0) + 1);
            }
        }
        return res;
    }
}
