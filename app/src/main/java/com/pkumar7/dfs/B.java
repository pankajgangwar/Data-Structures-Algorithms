package com.pkumar7.dfs;

import java.util.HashSet;

/**
 * Created by Pankaj Kumar on 08/October/2020
 */
class B {

    /*
     *   1625. Lexicographically Smallest String After Applying Operations
     *   https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/
     *   https://leetcode.com/problems/lexicographically-smallest-string-after-applying-operations/discuss/899507/JAVA-Brute-force-%2B-DFS
     * */
    public String findLexSmallestString(String s, int a, int b) {
        res = s;
        HashSet<String> visited = new HashSet<>();
        helper(s, a, b, visited);
        return res;
    }

    String res ;
    public void helper(String s, int a, int b, HashSet<String> visited){
        if(visited.contains(s)) return;
        visited.add(s);
        if(s.compareTo(res) < 0){
            res = s;
        }
        int splitPoint = s.length() - b;
        String rotated = s.substring(splitPoint) + s.substring(0, splitPoint);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if(i % 2 != 0){
                int d = s.charAt(i) - '0';
                d = d + a;
                if(d > 9){
                    d = d % 10;
                }
                out.append(d);
            }else{
                out.append(s.charAt(i));
            }
        }
        String added = out.toString();
        helper(added, a, b, visited);
        helper(rotated, a, b, visited);
    }
    
    /* 526. Beautiful Arrangement
    * https://leetcode.com/problems/beautiful-arrangement/
    * */
    public int countArrangement(int n) {
        int[] nums = new int[n];
        
        for(int i = 1; i <= n; i++){
            nums[i-1] = i;
        }
        permute(nums, 0);
        return count;
    }
    int count = 0;
    
    public void permute(int[] nums, int l){
        int n = nums.length;
        if(l == n ){
            count++;
            return;
        }
        
        for(int i = l; i < n; i++){
            swap(nums, i, l);
            if(nums[l] % ( l + 1 ) == 0 || (l + 1) % nums[l] == 0){
                permute(nums, l + 1);
            }
            swap(nums, i, l);
        }
    }
    
    public void swap(int[] nums, int i, int l){
        int a = nums[i];
        nums[i] = nums[l];
        nums[l] = a;
    }

    /* 87. Scramble String
     * https://leetcode.com/problems/scramble-string/
     * */
    public boolean isScramble(String s1, String s2) {
        if(s1.length() != s2.length()) return false;
        if(s1.equals(s2)){
            return true;
        }
        int [] arr1 = new int[26];
        for (int i = 0; i < s1.length(); i++) {
            arr1[s1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < s2.length(); i++) {
            if(--arr1[s2.charAt(i) - 'a'] < 0) return false;
        }
        int len = s1.length();
        for (int i = 1; i <= len - 1; i++) {
            if(isScramble(s1.substring(0, i), s2.substring(0, i)) && isScramble(s1.substring(i), s2.substring(i))){
                return true;
            }
            if(isScramble(s1.substring(0, i), s2.substring(len - i)) && isScramble(s1.substring(i), s2.substring(0, len - i))){
                return true;
            }
        }
        return false;
    }
}
