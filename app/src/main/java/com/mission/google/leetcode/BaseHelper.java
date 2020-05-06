package com.mission.google.leetcode;

public abstract class BaseHelper {

    public boolean isPalindrome(String s){
        int low = 0;
        int high = s.length() -1;
        while(low <= high){
            if(s.charAt(low) != s.charAt(high)) {
                return false;
            }
            low++;
            --high;
        }
        return true;
    }
}
