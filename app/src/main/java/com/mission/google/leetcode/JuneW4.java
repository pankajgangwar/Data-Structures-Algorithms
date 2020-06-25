package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

class JuneW4 {
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/
    /* https://www.codechef.com/problems/COUPON */
    /* https://leetcode.com/problems/brick-wall/ */

    /* DP on trees */
    /*
       https://codeforces.com/blog/entry/20935
       https://www.spoj.com/problems/PT07X/
       https://leetcode.com/problems/sum-of-distances-in-tree/
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */
    /* Binary search problems*/
    /* https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/ */
    /* https://leetcode.com/problems/k-th-smallest-prime-fraction/ */
    /* https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/ */


    /* https://leetcode.com/problems/divide-chocolate/ */
    /* https://leetcode.com/problems/integer-replacement/ */
    /* https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */
    /*
    LC : 920
    https://leetcode.com/problems/number-of-music-playlists/
    https://www.youtube.com/watch?v=rhKuVZSsU_Q
    */
    public int numMusicPlaylists(int N, int L, int K) {
        return 0;
    }

    public static void main(String[] args) {
        JuneW4 obj = new JuneW4();
        //obj.getFolderNames(new String[]{"onepiece","onepiece(8)","onepiece(2)","onepiece(3)","onepiece","onepiece","onepiece"});
        //obj.singleNumber(new int[]{5,5,5,3});
        obj.longestRepeatingSubstring("abbaba");
        //obj.patternMatchingWithBinarySearch();
    }

    /*
     LC : 1062
     https://leetcode.com/problems/longest-repeating-substring/ */
    public int longestRepeatingSubstring(String s) {
       // return lcpConstructionUsingKasai("banana ");
        //return usingLCPConstructionNaive("banana");
        /*SuffixArray sa = new SuffixArray(s);
        System.out.println("sa.lrs(); = " + sa.lrs());
        return sa.lrs().first().length();*/
        return usingRabinKarp(s);
    }

    //Rabin Karp with Rolling hash and Binary search
    public int usingRabinKarp(String s){
        int n = s.length();
        int low = 0, high = n - 1;
        int BASE = 26;
        long MOD = (long)Math.pow(2, 32);
        long[] pow = new long[n+1];
        pow[0] = 1L;
        for(int i = 1; i < n; ++i){
            pow[i] = pow[i-1] * BASE % MOD;
        }
        String maxRes = "";
        while (low <= high){
            int mid = low + (high - low) / 2;
            String res = search(s, mid, pow);
            if(res.length() > maxRes.length()){
                maxRes = res;
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        int maxLen = maxRes.length();
        System.out.println("maxLen = " + maxRes);
        return maxLen;
    }

    public String search(String s, int len, long[] POW){
        if(len == 0) return "";
        int BASE = 26;
        long MOD = (long)Math.pow(2, 32);
        long curr = 0;
        int n = s.length();
        for (int i = 0; i < len; i++) {
            curr = (curr * BASE + (s.charAt(i) - 'a'))% MOD;
        }
        HashSet<Long> seen = new HashSet<>();
        seen.add(curr);
        for (int i = len; i < n; i++) {
            curr = ((curr - ((s.charAt(i - len) - 'a') * POW[len - 1])) % MOD + MOD) % MOD;
            curr = (curr * BASE + (s.charAt(i) - 'a')) % MOD;
            if(seen.contains(curr)){
                return s.substring(i - len, i);
            }
            seen.add(curr);
        }
        return "";
    }

    /* Time O(nlogn)*/
    public int usingBinarySearch(String s){
        int n = s.length();
        int left = 1;
        int right = n;
        while (left <= right){
            int mid = left + (right - left) / 2;
            if(search(s, mid) != -1) left = mid + 1;
            else right = mid - 1;
        }
        return left - 1;
    }

    private int search(String s, int mid) {
        int n = s.length();
        HashSet<Long> seen = new HashSet<>();
        for (int i = 0; i < n - mid + 1; i++) {
            long hash = s.substring(i, i + mid).hashCode();
            if(!seen.add(hash)) return i;
        }
        return -1;
    }

    class Suffix implements Comparable<Suffix>{
        final int orgIdx;
        final String suffix;
        final int len;
        public Suffix(String suffix, int orgIdx){
            this.len = suffix.length();
            this.orgIdx = orgIdx;
            this.suffix = suffix;
        }

        @Override
        public int compareTo(Suffix other) {
            if (this == other) return 0;
            int min_len = Math.min(len, other.len);
            for (int i = 0; i < min_len; i++) {
                char otherCh = other.suffix.charAt(i);
                char thisCh = suffix.charAt(i);
                if (thisCh < otherCh) return -1;
                if (thisCh > otherCh) return 1;
            }
            return len - other.len;
        }

        @Override
        public String toString() {
            return new String(suffix.toCharArray(), orgIdx, len);
        }
    }

    /* Ans : https://leetcode.com/problems/longest-repeating-substring/discuss/704285/JAVA-Solution-using-Kasai's-Algorithm-to-construct-LCP*/
    /* Time O(n^2logn)*/
    public int lcpConstructionUsingKasai(String s){
        int n = s.length();
        Suffix[] suffixArray = new Suffix[n];
        for (int i = 0; i < s.length(); i++) {
            suffixArray[i] = new Suffix(s.substring(i), i);
        }
        Arrays.sort(suffixArray);

        int[] sa = new int[n];
        for (int i = 0; i < n; i++) {
            sa[i] = suffixArray[i].orgIdx;
            suffixArray[i] = null;
        }
        suffixArray = null;

        int[] lcp = new int[n];
        int[] rank = new int[n];
        for (int i = 0; i < n; i++) {
            rank[sa[i]] = i;
        }

        int k = 0;
        for(int i = 0; i < n; i++) {
            if(rank[i] == n - 1) {
                k = 0;
                continue;
            }
            if(rank[i] > 0) {
                int j = sa[rank[i] + 1];
                while (i + k < n && j + k < n && s.charAt(i + k) == s.charAt(j + k)) {
                    k++;
                }
                lcp[rank[i] - 1] = k;
                if (k > 0) {
                    k--;
                }
            }
        }
        int maxLen = 0;
        for (int i = 0; i < n; i++) {
            maxLen = Math.max(maxLen, lcp[i]);
        }
        System.out.println("maxLcp = " + maxLen);
        return maxLen;
    }

    public void patternMatchingWithBinarySearch(){
        String pat = "ana";
        String text = "banana";
        int n = text.length();
        Suffix[] suffixes = new Suffix[n];
        for (int i = 0; i < n; i++) {
            Suffix suffix = new Suffix(text.substring(i), i);
            suffixes[i] = suffix;
        }
        Arrays.sort(suffixes, (a,b) -> a.suffix.compareTo(b.suffix));
        int l = 0, r = n - 1;
        while (l <= r){
            int mid = l + (r - l) / 2;
            int res = pat.compareTo(suffixes[mid].suffix);
            if(res == 0 || suffixes[mid].suffix.startsWith(pat) && suffixes[mid].suffix.length() > pat.length()){
                System.out.println("Pattern " + pat + " found at idx = " + suffixes[mid].orgIdx);
                return;
            }
            if(res < 0){
                r = mid - 1;
            }else{
                l = mid + 1;
            }
        }
        System.out.println("Pattern not found = " + pat);
    }

    /* Time : O(n^2logn)*/
    public int usingLCPConstructionNaive(String s){
        List<String> res = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            res.add(s.substring(i));
        }
        Collections.sort(res);
        int[] lcp = new int[res.size()];
        lcp[0] = 0;
        int maxLCP = 0;
        for (int i = 1; i < res.size(); i++) {
            String prev = res.get(i-1);
            String curr = res.get(i);
            int currLCP = 0;
            for(int j = 0; j < Math.min(prev.length(), curr.length()); j++){
                if(prev.charAt(j) == curr.charAt(j)){
                    currLCP++;
                }else{
                    break;
                }
            }
            maxLCP = Math.max(maxLCP, currLCP);
            lcp[i] = currLCP;
        }
        int ans = 0;
        for (int i = 1; i < lcp.length; i++) {
            if(lcp[i] == maxLCP){
                String curr = res.get(i).substring(0, maxLCP);
                System.out.println("curr = " + curr);
            }
        }
        System.out.println("maxLCP = " + maxLCP);
        return maxLCP;
    }

    /*
    LC : 137
    https://leetcode.com/problems/single-number-ii/ */
    public int singleNumber(int[] nums){
        /* https://www.youtube.com/watch?v=ZbTXZ2_YAgI */
        int ones = 0;// Bits with occurrence ones
        int twos = 0; //Bits with occurrence twice
        int thrice = 0; // Bits with occurrence thrice
        for(int n : nums){
            // (ones & n) // ones has bits occurring once and n has bits set gives bits occurring twice
            // twos also contains bits occurring thrice
            twos = twos | (ones & n);// Keep the bits occurred twice and add new bits from (ones & n)
            ones = ones ^ n; // Remove bits occurring twice
            thrice = ones & twos; // Get the bits which occurred thrice from ones and twos
            ones = ones & ~thrice; // remove bits from ones which are occurring thrice
            twos = twos & ~thrice; // remove bits from twos which are occurring thrice
        }
        return ones;
    }

    public int singleNumberI(int[] nums){
        int[] bits = new int[32];
        for (int i = 0; i < 32; i++) {
            for(int n : nums){
                bits[i] += n >> i & 1 ; // Set the lsb
                bits[i] %= 3; // If occurs more than 3 and reset to 0
            }
        }
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res |= (bits[i] << i); //Convert the bits to decimal number
        }
        return res;
    }

    /*
    LC : 594
    https://leetcode.com/problems/longest-harmonious-subsequence/  */
    public int findLHS(int[] nums) {
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.putIfAbsent(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }
        Map.Entry<Integer, ArrayList<Integer>> first = null;
        int max = 0;
        for(Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()){
            if (first != null) {
                if (entry.getKey() - first.getKey() == 1) {
                    max = Math.max(max, first.getValue().size() + entry.getValue().size());
                }
            }
            first = entry;
        }
        return max;
    }

    /*
    LC : 1044
    https://leetcode.com/problems/longest-duplicate-substring/
    Rabin-Karp Algorithm
    */
    public String longestDupSubstring(String s) {
        int n = s.length();
        int low = 0, high = n - 1;
        int BASE = 26;
        long MOD = (long)Math.pow(2, 32);
        long[] pow = new long[n+1];
        pow[0] = 1L;
        for(int i = 1; i < n; ++i){
            pow[i] = pow[i-1] * BASE % MOD;
        }
        String maxString = "";
        while (low <= high){
            int mid = low + (high - low) / 2;
            String curr = possible(s, mid, pow);
            if(curr.length() > maxString.length()){
                maxString = curr;
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return maxString;
    }

    public String possible(String s, int len, long[] POW){
        if(len == 0) return "";
        int BASE = 26;
        long MOD = (long)Math.pow(2, 32);
        long curr = 0;
        int n = s.length();
        for (int i = 0; i < len; i++) {
            curr = (curr * BASE + (s.charAt(i) - 'a'))% MOD;
        }
        HashMap<Long, List<Integer>> map = new HashMap<>();
        map.putIfAbsent(curr, new ArrayList<>());
        map.get(curr).add(0);

        for (int i = len; i < n; i++) {
            curr = ((curr - ((s.charAt(i - len) - 'a') * POW[len - 1])) % MOD + MOD) % MOD;
            curr = (curr * BASE + (s.charAt(i) - 'a')) % MOD;
            if(!map.containsKey(curr)){
                map.putIfAbsent(curr, new ArrayList<>());
            }else{
                for(int index : map.get(curr)){
                    int begin = i - len + 1;
                    int end = begin + len;
                    String str = s.substring(begin, end);
                    String prev = s.substring(index, index + len);
                    if(prev.equals(str)){
                        return prev;
                    }
                }
            }
            map.get(curr).add(i - len + 1);
        }
        return "";
    }

    /* https://leetcode.com/problems/making-file-names-unique/ */
    public String[] getFolderNames(String[] names) {
        HashMap<String, Integer> map = new HashMap<>();
        String[] res = new String[names.length];
        Set<String> all = new HashSet<>();
        for (int i = 0; i < names.length; i++) {
            if(all.add(names[i])){
                res[i] = names[i];
            }else{
                int g = map.getOrDefault(names[i], 0);
                while (true){
                    g++;
                    String nm = names[i] +"("+g+")";
                    if(all.add(nm)){
                        res[i] = nm;
                        map.put(names[i], g);
                        break;
                    }
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/avoid-flood-in-the-city/ */
    public int[] avoidFlood(int[] rains) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int[] ans = new int[rains.length];
        TreeSet<Integer> dry = new TreeSet<>();
        for (int i = 0; i < rains.length; i++) {
            if(rains[i] == 0){
                dry.add(i);
            }else{
                if(map.containsKey(rains[i])){
                    Integer curr = dry.ceiling(map.get(rains[i]));
                    if(curr == null) return new int[0];
                    ans[curr] = rains[i];
                    dry.remove(curr);
                }
                ans[i] = -1;
                map.put(rains[i], i);
            }
        }
        for (int i : dry){
            ans[i] = 1;
        }
        return ans;
    }

    /* https://leetcode.com/problems/xor-operation-in-an-array/ */
    public int xorOperation(int n, int start) {
        int xor = 0;
        for (int i = 0; i < n; i++) {
            xor = xor ^ start + 2 * i;
        }
        return xor;
    }


}
