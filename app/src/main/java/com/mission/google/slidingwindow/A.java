package com.mission.google.slidingwindow;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pankaj Kumar on 21/August/2020
 */
class A {
    /*
    * 1297. Maximum Number of Occurrences of a Substring
    * https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/
    * */
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int n = s.length();
        Map<String, Integer> map = new HashMap<>();
        char[] freq = new char[26];
        int start = 0, maxuniq = 0;
        int max = 0;
        for(int end = 0; end < n; end++){
            char endCh = s.charAt(end);
            if(freq[endCh - 'a'] == 0){
                maxuniq++;
            }
            freq[endCh - 'a']++;
            if((end - start + 1) > minSize) {
                char startCh = s.charAt(start);
                if(--freq[startCh - 'a'] == 0) maxuniq--;
                start++;
            }
            if((end - start + 1) == minSize && maxuniq <= maxLetters){
                String sub = s.substring(start, end + 1);
                map.put(sub, map.getOrDefault(sub, 0) + 1);
                max = Math.max(max, map.get(sub));
            }
        }
        return max;
    }

}
