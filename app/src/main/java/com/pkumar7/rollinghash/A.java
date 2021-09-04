package com.pkumar7.rollinghash;

import java.util.HashMap;
import java.util.HashSet;

public class A {
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
