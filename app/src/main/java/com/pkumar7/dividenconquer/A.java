package com.pkumar7.dividenconquer;

import java.util.HashSet;

public class A {
    /*
     * https://leetcode.com/problems/longest-nice-substring/
     * */
    public String longestNiceSubstring(String s) {
        HashSet<Character> set = new HashSet<>();
        for(char ch : s.toCharArray()){
            set.add(ch);
        }
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(set.contains(Character.toUpperCase(ch)) && set.contains(Character.toLowerCase(ch))) continue;
            String first = longestNiceSubstring(s.substring(0, i));
            String second = longestNiceSubstring(s.substring(i + 1));
            return first.length() >= second.length() ? first : second;
        }
        return s;
    }
}
