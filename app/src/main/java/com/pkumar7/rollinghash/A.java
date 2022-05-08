package com.pkumar7.rollinghash;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class A {

    /*
     * https://leetcode.com/problems/k-divisible-elements-subarrays/
     * 2261. K Divisible Elements Subarrays
     * */
    class Trie {
        HashMap<Integer, Trie> map = new HashMap<>();
        int cnt = 0;
        public int insert(int[] nums, int i, int k, int p){
            if(i == nums.length || k - ((nums[i] % p == 0) ? 1 : 0) < 0){
                return 0;
            }
            if(!map.containsKey(nums[i])){
                map.put(nums[i], new Trie());
            }
            int kk = nums[i] %  p == 0 ? 1 : 0;
            int a = ++map.get(nums[i]).cnt == 1 ? 1 : 0;
            return a + map.get(nums[i]).insert(nums, i + 1, k - kk, p);
        }
    }
    public int countDistinct1(int[] nums, int k, int p) {
        int res = 0;
        Trie t = new Trie();
        for (int i = 0; i < nums.length; i++) {
            res += t.insert(nums, i, k, p);
        }
        return res;
    }

    public int countDistinctUsingRollingHash(int[] nums, int k, int p) {
        int n = nums.length;
        HashSet<Long> sets = new HashSet<>();
        int max = Arrays.stream(nums).max().getAsInt();
        long mod = (long)1e10+7;
        for (int i = 0; i < n; i++) {
            int count = 0;
            long hash = 0;
            for (int j = i; j < n; j++) {
                hash = (hash * (max + 1) + nums[j]) % mod;
                if(nums[j] % p == 0) count++;
                if(count > k) break;
                sets.add(hash);
            }
        }
        return sets.size();
    }

    long BASE = 100007L, MOD = (long) (1e11 + 7);
    public int longestCommonSubpath(int n, int[][] paths) {
        int high = Integer.MAX_VALUE;
        for (int i = 0; i < paths.length; i++) {
            high = Math.min(high, paths[i].length);
        }
        long[] pow = new long[high + 10];
        pow[0] = 1;
        for (int i = 1; i <= high; ++i) {
            pow[i] = (pow[i - 1] * n) % MOD;
        }
        int low = 0;
        while (low < high) {
            int mid = (low + high + 1) / 2;
            if (works(mid, paths, pow, n)) {
                low = mid;
            } else {
                high = mid - 1;
            }
        }
        return low;
    }

    public boolean works(int L, int[][] paths, long[] POW, int n) {
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].length < L) {
                return false;
            }
        }
        HashMap<Long, Integer> count = new HashMap<>();
        for (int i = 0; i < paths.length; i++) {
            HashSet<Long> currHash = new HashSet<>();
            long H = 0L;
            for (int j = 0; j <= L - 1; j++) {
                H += (paths[i][j] * POW[L - 1 - j]) % MOD;
                H %= MOD;
            }
            currHash.add(H);
            for (int j = L; j < paths[i].length; j++) {
                H -= paths[i][j - L] * POW[L - 1] % MOD;
                H = (H * n + paths[i][j]) % MOD;
                H %= MOD;
                if(H < 0) H += MOD;
                currHash.add(H);
            }
            for(long hh : currHash){
                count.put(hh, count.getOrDefault(hh, 0) + 1);
            }
        }
        for(long key : count.keySet()){
            if(count.get(key) == paths.length) return true;
        }
        return false;
    }
}
