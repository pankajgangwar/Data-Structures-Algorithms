package com.pkumar7.slidingwindow;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pankaj Kumar on 21/August/2020
 */
class A {

    /* 2271. Maximum White Tiles Covered by a Carpet
     * Sliding Window
     * https://leetcode.com/problems/maximum-white-tiles-covered-by-a-carpet/
     * */
    public int maximumWhiteTiles(int[][] t, int len) {
        if(t.length == 1)  {
            return Math.min(len, t[0][1] - t[0][0] + 1);
        }
        Arrays.sort(t, Comparator.comparingInt(a -> a[0]));
        int res = 0;
        int j = 0, cover = 0;
        for (int i = 0; res < len && i < t.length; ) {
            if(j == i || t[j][0] + len > t[i][1]){
                cover += Math.min(len, t[i][1] - t[i][0] + 1);
                res = Math.max(res, cover);
                ++i;
            }else{
                int partial = Math.max(0, t[j][0]+len - t[i][0]);
                res = Math.max(res, cover + partial);
                cover -= (t[j][1] - t[j][0] + 1);
            }
        }
        return res;
    }

    /* 1151. Minimum Swaps to Group All 1's Together
     * https://leetcode.com/problems/minimum-swaps-to-group-all-1s-together/
     * */
    public int minSwaps(int[] arr) {
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == 1) count++;
        }
        int zeroInWindow = 0;
        for (int i = 0; i < count; i++) {
            if(arr[i] == 0) zeroInWindow++;
        }
        int res = zeroInWindow;
        int start = 0;
        for (int i = count; i < arr.length ; i++) {
            if(arr[i] == 0) zeroInWindow++;
            if(arr[start++] == 0) zeroInWindow--;
            res = Math.min(res, zeroInWindow);
        }
        return res;
    }

    /*
     * 1838. Frequency of the Most Frequent Element
     * https://leetcode.com/problems/frequency-of-the-most-frequent-element/
     * */
    public int maxFrequency(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int ans = 1;
        int start = 0, end = 0;
        int sum = 0;
        for(; end < n; ++end){
            sum += nums[end];
            while ((end - start + 1) * nums[end] - sum > k) sum -= nums[start++];
            ans = Math.max(ans, end - start + 1);
        }
        return ans;
    }

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
