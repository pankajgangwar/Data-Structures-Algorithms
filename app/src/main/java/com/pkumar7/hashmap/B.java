package com.pkumar7.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Currency;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pankaj Kumar on 27/December/2020
 */
class B {

    public static void main(String[] args) {
        B curr = new B();
        String[] arr1 = new String[]{"a","b","ba","bca","bda","bdca"};
        String[] arr2 = new String[]{"xbc","pcxbcf","xb","cxbc","pcxbc"};
        int res = curr.longestStrChainDp(arr2);
        System.out.println("Res : "+ res);
    }
    /*
    * https://leetcode.com/problems/longest-string-chain/
    * 1048. Longest String Chain
    * */
    public int longestStrChainDp(String[] words) {
        int len = words.length;
        if(len <= 1) return len;
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }
        int[] dp = new int[words.length + 1];
        Arrays.fill(dp, 1);

        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words[i].length(); j++) {
                String prev = words[i].substring(0, j) + words[i].substring(j+1);
                if(map.containsKey(prev)){
                    int prevIdx = map.get(prev);
                    if(prevIdx < i){
                        dp[i] = Math.max(dp[i], dp[prevIdx] + 1);
                    }
                }
            }
        }
        return Arrays.stream(dp).max().getAsInt();
    }

    public int longestStrChain(String[] words) {
        int len = words.length;
        if(len <= 1) return len;
        Arrays.sort(words, (a, b) -> a.length() - b.length());
        int res = 0;
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            int best = 0;
            for (int j = 0; j < words[i].length(); j++) {
                String prev = words[i].substring(0, j) + words[i].substring(j+1);
                best = Math.max(best, map.getOrDefault(prev, 0) + 1);
            }
            map.put(words[i], best);
            res = Math.max(res, best);
        }
        return res;
    }

    /* 1865. Finding Pairs With a Certain Sum
    * https://leetcode.com/problems/finding-pairs-with-a-certain-sum/
    * */
    class FindSumPairs {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] nums1, nums2;
        public FindSumPairs(int[] nums1, int[] nums2) {
            for (int xx : nums2) {
                map.put(xx, map.getOrDefault(xx, 0) + 1);
            }
            this.nums1 = nums1;
            this.nums2 = nums2;
        }
        public void add(int index, int val) {
            map.put(nums2[index], map.getOrDefault(nums2[index], 0) - 1);
            nums2[index] += val;
            map.put(nums2[index], map.getOrDefault(nums2[index], 0) + 1);
        }
        public int count(int tot) {
            int pairs = 0;
            for (int xx : nums1) {
                if(xx <= tot && map.containsKey(tot - xx)){
                    pairs += map.get(tot - xx);
                }
            }
            return pairs;
        }
    }

    /*1742. Maximum Number of Balls in a Box
     * https://leetcode.com/problems/maximum-number-of-balls-in-a-box/
     * */
    public int countBalls(int lowLimit, int highLimit) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = 0;
        int count = 0;
        for (int i = lowLimit; i <= highLimit; i++) {
            int sum = getSumOfDigits(i);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
            int cnt = map.get(sum);
            if (cnt > max) {
                max = cnt;
                count = cnt;
            }
        }
        return count;
    }

    public int getSumOfDigits(int digit) {
        int sum = 0;
        while (digit > 0) {
            sum += digit % 10;
            digit = digit / 10;
        }
        return sum;
    }

    /* 1711. Count Good Meals
     * https://leetcode.com/problems/count-good-meals/
     * */
    public int countPairs(int[] arr) {
        int mod = (int) 1e9 + 7;
        long res = 0;
        HashMap<Long, Integer> hashMap = new HashMap<>();
        for (int j = 0; j < arr.length; j++) {
            long a = arr[j];
            for (int i = 0; i < 32; i++) {
                long sum = (long) Math.pow(2, i);
                long key = sum - a;
                if (hashMap.containsKey(key)) {
                    res += hashMap.getOrDefault(key, 0);
                    res = res % mod;
                }
            }
            hashMap.put(a, hashMap.getOrDefault(a, 0) + 1);
        }
        return (int) res;
    }

    /* 1124. Longest Well-Performing Interval
     * https://leetcode.com/problems/longest-well-performing-interval/
     * */
    public int longestWPI(int[] h) {
        int n = h.length;
        int sum = 0;
        int maxlen = 0;
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < n; i++) {
            sum += h[i] > 8 ? 1 : -1;
            if (sum > 0) {
                maxlen = i + 1;
            } else {
                map.putIfAbsent(sum, i);
                if (map.containsKey(sum - 1)) {
                    maxlen = Math.max(maxlen, i - map.get(sum - 1));
                }
            }
        }
        return maxlen;
    }

    /* https://leetcode.com/problems/sort-array-by-increasing-frequency/
     * https://leetcode.com/problems/sort-array-by-increasing-frequency/
     * */
    public int[] frequencySort(int[] nums) {
        TreeMap<Integer, LinkedList<Integer>> map = new TreeMap<>();
        HashMap<Integer, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            freqMap.put(nums[i], freqMap.getOrDefault(nums[i], 0) + 1);
        }
        for (Map.Entry<Integer, Integer> entry : freqMap.entrySet()) {
            int number = entry.getKey();
            int freq = entry.getValue();
            map.putIfAbsent(freq, new LinkedList<>());
            map.get(freq).add(number);
        }
        int[] res = new int[nums.length];
        int idx = 0;
        for (Map.Entry<Integer, LinkedList<Integer>> entry : map.entrySet()) {
            LinkedList<Integer> list = entry.getValue();
            Collections.sort(list, Collections.reverseOrder());
            for (int i = 0; i < list.size(); i++) {
                int tempFreq = entry.getKey();
                while (tempFreq-- > 0) {
                    res[idx++] = list.get(i);
                }
            }
        }
        return res;
    }
}
