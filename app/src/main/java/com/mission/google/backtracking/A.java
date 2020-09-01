package com.mission.google.backtracking;

import java.util.List;

/**
 * Created by Pankaj Kumar on 29/August/2020
 */
class A {
    /* 1239. Maximum Length of a Concatenated String with Unique Characters
     * https://leetcode.com/problems/maximum-length-of-a-concatenated-string-with-unique-characters/
     * */
    public int maxLength(List<String> arr) {
        helper(arr, "", 0);
        return max;
    }

    int max = 0;
    public void helper(List<String> arr, String prev, int idx){
        for (int i = idx; i < arr.size(); i++) {
            String curr = arr.get(i);
            if(!ifStringUnique(curr)) continue;
            if(prev.isEmpty()){
                helper(arr, curr, i + 1);
            }
            if(isAllUnique(curr, prev)){
                prev += curr;
                helper(arr, prev, i + 1);
                prev = prev.substring(0, prev.length() - curr.length());
            }
        }
        if(max < prev.length()){
            max = Math.max(prev.length(), max);
        }
    }

    public boolean ifStringUnique(String a){
        char[] freqA = new char[26];
        for (char ch : a.toCharArray()){
            if(freqA[ch - 'a'] > 0) return false;
            freqA[ch - 'a']++;
        }
        return true;
    }

    public boolean isAllUnique(String a, String b){
        char[] freqA = new char[26];
        for (char ch : a.toCharArray()){
            if(freqA[ch - 'a'] > 0) return false;
            freqA[ch - 'a']++;
        }
        char[] freqB = new char[26];
        for (char ch : b.toCharArray()){
            if(freqB[ch - 'a'] > 0) return false;
            freqB[ch - 'a']++;
        }
        for (char ch : b.toCharArray()){
            if(freqA[ch - 'a'] > 0){
                return false;
            }
        }
        return true;
    }
}
