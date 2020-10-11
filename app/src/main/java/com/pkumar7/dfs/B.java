package com.pkumar7.dfs;

/**
 * Created by Pankaj Kumar on 08/October/2020
 */
class B {
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
