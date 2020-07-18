package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

class JuneW4 {




    public static void main(String[] args) {
        JuneW4 obj = new JuneW4();
        //obj.getFolderNames(new String[]{"onepiece","onepiece(8)","onepiece(2)","onepiece(3)","onepiece","onepiece","onepiece"});
        //obj.singleNumber(new int[]{5,5,5,3});
        //obj.longestRepeatingSubstring("abbaba");
        //obj.patternMatchingWithBinarySearch();
        int[][] grid = new int[4][4];
        grid[0] = new int[]{1,1,0,0};
        grid[1] = new int[]{1,1,0,0};
        grid[2] = new int[]{0,0,1,1};
        grid[3] = new int[]{0,0,1,1};
        int res = obj.longestSubstring("abcdedghijklmnopqrstuvwxyz", 2);
        System.out.println("res = " + res);
    }

    /*
    LC : 395
    https://leetcode.com/problems/longest-substring-with-at-least-k-repeating-characters/ */
    public int longestSubstring(String s, int k) {
        return longestSubstringMemo(s, k, new HashMap<>());
    }

    public int helper(String s, int k){
        if(k < 1 || s.length() < k){
            return 0;
        }
        if(k == 1) return s.length();
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        HashSet<Character> sets = new HashSet<>();
        for (char ch : map.keySet()){
            if(map.get(ch) < k) {
                sets.add(ch);
            }
        }
        if(sets.isEmpty()) return s.length();
        int maxLen = 0;
        int i = 0, j = 0;
        while (j < s.length()){
            char ch = s.charAt(j);
            if(sets.contains(ch)){
                if(j != i){
                    maxLen = Math.max(maxLen, helper(s.substring(i, j), k));
                }
                i = j + 1;
            }
            j++;
        }
        if(i != j){
            maxLen = Math.max(maxLen, helper(s.substring(i, j), k));
        }
        return maxLen;
    }

    //TLE
    public int longestSubstringMemo(String s, int k, Map<String, Integer> dp) {
        if(dp.containsKey(s)) return dp.get(s);

        if(s.length() == 1 && k == 1){
            return 1;
        }
        if(s.length() < k){
            return 0;
        }
        System.out.println("s = " + s);
        Map<Character, Integer> map = new HashMap<>();
        int maxlen = 0;
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < s.length(); i++) {
            if (map.get(s.charAt(i)) < k) {
                String firstHalf = s.substring(0, i);
                String secondHalf = s.substring(i+1);
                if (!firstHalf.isEmpty()) {
                    if (!firstHalf.equals(s)) {
                        maxlen = Math.max(maxlen, longestSubstringMemo(firstHalf, k, dp));
                    }
                }
                if(!secondHalf.isEmpty()) {
                    if(!secondHalf.equals(s)){
                        maxlen = Math.max(maxlen, longestSubstringMemo(secondHalf, k, dp));
                    }
                }
            }
        }
        if(maxlen == 0){
            for (int i = 0; i < s.length(); i++) {
                if(map.get(s.charAt(i)) < k){
                    dp.put(s, 0);
                    return 0;
                }
            }
            dp.put(s, s.length());
            return s.length();
        }
        dp.put(s, maxlen);
        return maxlen;
    }

    /*
     LC : 427
     https://leetcode.com/problems/construct-quad-tree/ */
    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;
        public Node() {
            this.val = false;
            this.isLeaf = false;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = null;
            this.topRight = null;
            this.bottomLeft = null;
            this.bottomRight = null;
        }

        public Node(boolean val, boolean isLeaf, Node topLeft, Node topRight, Node bottomLeft, Node bottomRight) {
            this.val = val;
            this.isLeaf = isLeaf;
            this.topLeft = topLeft;
            this.topRight = topRight;
            this.bottomLeft = bottomLeft;
            this.bottomRight = bottomRight;
        }
    }

    public Node construct(int[][] grid) {
        return helper(grid, 0, 0, grid.length);
        //return dfs(grid);
    }

    public Node helper(int[][] grid, int x, int y, int len){
        if(len == 1){
            return new Node(grid[x][y] != 0, true, null,null,null,null);
        }
        Node root = new Node(true, false);
        Node topLeft = helper(grid, x, y, len / 2);
        Node topRight = helper(grid, x, y + len / 2, len / 2);
        Node bottomLeft = helper(grid, x + len / 2, y, len / 2);
        Node bottomRight = helper(grid, x + len / 2, y + len / 2, len / 2);
        if(topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf &&
            topLeft.val == topRight.val && topRight.val == bottomLeft.val && bottomLeft.val == bottomRight.val){
            root.val = topLeft.val;
            root.isLeaf = true;
        }else{
            root.topLeft = topLeft;
            root.bottomLeft = bottomLeft;
            root.topRight = topRight;
            root.bottomRight = bottomRight;
        }
        return root;
    }

    private Node dfs(int[][] grid) {
        int n = grid.length;
        int start = grid[0][0];
        boolean flag = true;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(start != grid[i][j]){
                    flag = false;
                    break;
                }
            }
        }
        if(flag){
            //If all values in this grid are same
            Node root = new Node(start == 1, true, null, null, null, null );
            return root;
        }else{
            //break into 4 sub-square
            //bottom-left
            Node root = new Node(true, false);
            int half = n / 2;
            int[][] topLeft = new int[n / 2 ][n / 2];
            int[][] topRight = new int[n / 2 ][n / 2];
            int[][] bottomLeft = new int[n / 2 ][n / 2];
            int[][] bottomRight = new int[n / 2 ][n / 2];
            for (int k = 0; k < n / 2; k++) {
                for (int l = 0; l < n / 2; l++) {
                    topLeft[k][l] = grid[k][l];
                }
            }
            root.topLeft = dfs(topLeft);

            for (int m = 0; m < n / 2; m++) {
                for (int l = n / 2; l < n ; l++) {
                    topRight[m][l % half] = grid[m][l];
                }
            }
            root.topRight = dfs(topRight);

            for (int k = n / 2; k < n; k++) {
                for (int m = 0; m < n / 2 ; m++) {
                    bottomLeft[k % half][m % half] = grid[k][m];
                }
            }
            root.bottomLeft = dfs(bottomLeft);

            for (int p = n / 2; p < n; p++) {
                for (int m = n / 2; m < n ; m++) {
                    bottomRight[p % half][m % half] = grid[p][m];
                }
            }
            root.bottomRight = dfs(bottomRight);
            return root;
        }
    }

    /*
    LC : 482
    https://leetcode.com/problems/license-key-formatting/ */
    public String licenseKeyFormatting(String S, int K) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < S.length(); i++){
            if(S.charAt(i) == '-')continue;
            builder.append(S.charAt(i));
        }
        if(builder.length() == 0) return "";

        String str = builder.toString().toUpperCase();
        StringBuilder res = new StringBuilder();

        int rem = str.length() % K;

        int i = 0;
        if(rem > 0){
            while (rem > 0){
                res.append(str.charAt(i++));
                rem--;
            }
            res.append("-");
        }
        for (; i < str.length(); i += K){
            res.append(str.subSequence(i, i + K));
            res.append("-");
        }
        res.deleteCharAt(res.length() -1);
        return res.toString();
    }

    /*
     LC : 636
     https://leetcode.com/problems/exclusive-time-of-functions/ */
    public int[] exclusiveTime(int n, List<String> logs) {
        Stack<Integer> stack = new Stack<>();
        int prevTime = 0;
        int[] res = new int[n];
        for (int i = 0; i < logs.size(); i++){
            String curr = logs.get(i);
            String[] arr =  curr.split(":");
            int functionId =  Integer.parseInt(arr[0]);
            int time = Integer.parseInt(arr[2]);
            if(arr[1].equals("start")){
                if(!stack.isEmpty()) res[stack.peek()] += time - prevTime;
                stack.push(functionId);
                prevTime = time;
            }else if(arr[1].equals("end")){
                res[stack.pop()] += time - prevTime + 1;
                prevTime = time + 1;
            }
        }
        return res;
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
