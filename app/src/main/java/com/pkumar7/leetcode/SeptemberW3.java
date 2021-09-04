package com.pkumar7.leetcode;

import com.pkumar7.datastructures.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

public class SeptemberW3 {

    public static void main(String[] args) {
        SeptemberW3 w3 = new SeptemberW3();
        //int maxUniValLength = w3.numDecodings("123");
        //System.out.println(maxUniValLength);

        int res = w3.numDecodingsII("**");
        System.out.println("res "+ res);
    }

    /**
     * https://leetcode.com/problems/maximum-number-of-balloons/
     * 1189. Maximum Number of Balloons
     * */
    public int maxNumberOfBalloons(String text) {
        String balloon = "balloon";
        int []freq = new int[26];
        for (int i = 0; i < text.length(); i++) {
            char curr = text.charAt(i);
            freq[curr - 'a']++;
        }
        freq['l' - 'a'] = freq['l' - 'a'] / 2;
        freq['o' - 'a'] = freq['o' - 'a'] / 2;

        int min = Integer.MAX_VALUE;
        for (char curr_char : balloon.toCharArray()) {
            min = Math.min(min,freq[curr_char - 'a']);
        }
        return min;

        /*Map<Character, Integer> map = new HashMap<>();
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            char ch = arr[i];
            if(balloon.indexOf(ch) >= 0) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
        }
        if(map.isEmpty()) return 0;
        int i = 0;
        StringBuilder builder = new StringBuilder();
        while (true){
            char curr_char = balloon.charAt(i++);
            Integer freq = map.get(curr_char);
            if(freq == null || freq == 0){
                break;
            }
            map.put(curr_char, freq - 1);

            builder.append(curr_char);

            String formed = builder.toString();
            int n = formed.length();
            int m = balloon.length();
            if(m == n && formed.equals(balloon)){
                count++;
                i = 0;
                builder = new StringBuilder();
            }
        }
        System.out.println(count);
        return count;
        */
    }

    /**
     * 1190. Reverse Substrings Between Each Pair of Parentheses
     * https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
     *
     * "(ed(et(oc))el)" -> "leetcode"
     * "(u(love)i)" -> "iloveu"
     *
     * **/
    public String reverseParentheses(String text) {
        int begin = 0;
        int end = 0;

        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == '(')
                begin = i;
            if(text.charAt(i) == ')') {
                end = i;
                String temp = text.substring(begin + 1, end);
                StringBuilder builder = new StringBuilder(temp);
                builder = builder.reverse();
                return reverseParentheses(text.substring(0, begin) + builder.toString() + text.substring(end + 1));
            }
        }
        return text;
    }

    /**
     * 328. Odd Even Linked List
     *
     * https://leetcode.com/problems/odd-even-linked-list/
     *
     * Input: 1->2->3->4->5->NULL
     * Output: 1->3->5->2->4->NULL
     *
     * */
    public ListNode oddEvenList(ListNode head) {
        if(head == null) return head;

        ListNode odd = head, even = head.next, evenhead = even;

        while (even != null && even.next != null){
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenhead;
        return head;
    }

    /** 62. Unique Paths
     * https://leetcode.com/problems/unique-paths/
     * **/
    public int uniquePaths(int m, int n) {
        int[][] memo = new int[m + 1][n + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(memo[i], -1);
        }

        return countPathsMemo(n - 1, m - 1, memo);
        //countPathsRec(n - 1, m - 1, 0, 0, 0);

       // System.out.println(paths_count);

        //return paths_count;
    }

    int paths_count = 0;
    public int countPathsMemo(int row, int cols, int[][] memo){
        if(row < 0 || cols < 0){
           return 0;
        }

        else if(row == 0 && cols == 0){
            return 1;
        }else if(memo[row][cols] > 0){
            return memo[row][cols];
        }else {
            memo[row][cols] = countPathsMemo(row - 1, cols, memo) + countPathsMemo(row, cols - 1, memo);
            return memo[row][cols];
        }
    }

    //int paths_count = 0;
    public void countPathsRec(int row, int cols, int curr_x, int curr_y, int paths){
        if(curr_x == row && curr_y == cols){
            paths_count++;
        }

        if(curr_x + 1 <= row && curr_y <= cols) {
            countPathsRec(row, cols, curr_x + 1, curr_y, paths);
        }

        if(curr_x <= row && curr_y + 1 <= cols) {
            countPathsRec(row, cols, curr_x, curr_y + 1, paths);
        }
    }

    /*
    * https://leetcode.com/problems/decode-ways-ii/
    * 639. Decode Ways II
    * */
    public int numDecodingsII(String s) {
        HashMap<String,Integer> starMap = new HashMap<>();
        starMap.put("1*", 9);
        starMap.put("2*", 6);
        starMap.put("**", 15);
        starMap.put("*0", 2);
        starMap.put("*1", 2);
        starMap.put("*2", 2);
        starMap.put("*3", 2);
        starMap.put("*4", 2);
        starMap.put("*5", 2);
        starMap.put("*6", 2);
        starMap.put("*7", 1);
        starMap.put("*8", 1);
        starMap.put("*9", 1);

        int n = s.length();
        long[] dp = new long[n + 1];
        dp[n] = 1;
        int mod = (int)1e9 + 7;
        for (int i = n-1; i >= 0 ; i--) {
            if(s.charAt(i) == '0'){
                dp[i] = 0;
            }else{
                dp[i] = ((s.charAt(i) == '*') ? 9 : 1) * dp[i + 1];
                if(i + 2 <= n){
                    String sub = s.substring(i, i + 2);
                    if(sub.indexOf("*") >= 0) {
                        dp[i] = (dp[i] + starMap.getOrDefault(sub, 0) * dp[i + 2]) % mod;
                    }else if(s.charAt(i) == '1' || s.charAt(i) == '2' && s.charAt(i + 1) < '7'){
                        dp[i] = (dp[i] + dp[i + 2]) % mod;
                    }
                }
            }
        }
        return (int)dp[0];
    }

    /**
     * https://leetcode.com/problems/decode-ways/
     * http://www.gorecursion.com/algorithm/2016/11/20/1d-dynamic1.html
     * 'A' -> 1
     * 'B' -> 2
     * ...
     * 'Z' -> 26
     *
     * **/
    public int numDecodings(String string) {
        /*int[] memo = new int[string.length() + 1];
        Arrays.fill(memo, -1);
        return numWaysDecodingMemo(string, 0, memo);*/
        //return numWaysDecodingDFS(string, 0);
        return waysToDecodeDP(string);
    }

    public int waysToDecodeDP(String s){
        int n = s.length();
        int[] dp = new int[n + 1];

        dp[0] = 1;
        //dp[i] : ways to decode string s of len n ends at i

        for(int i = 1; i <= n; i++){
            if(s.charAt(i - 1) != '0'){
                dp[i] += dp[i - 1];
            }
            if(i - 2 >= 0) {
                int val = Integer.parseInt(s.substring(i - 2, i));
                if(val >= 10 && val <= 26){
                    dp[i] += dp[i - 2];
                }
            }
        }
        return dp[n];
    }

    public int numWaysDecodingMemo(String string, int pos, int[] memo){
        if(pos < string.length() && string.charAt(pos) == '0'){
            return 0;
        }
        if(pos == string.length()){
            return 1;
        }
        if(memo[pos] != -1){
            return memo[pos];
        }

        memo[pos] = numWaysDecodingMemo(string, pos + 1, memo);
        int val = 0;
        if(pos + 1 < string.length()){
            val = Integer.parseInt(string.substring(pos, pos + 2));
        }
        if(val >= 10 && val <= 26){
            memo[pos] += numWaysDecodingMemo(string, pos + 2, memo);
        }
        return memo[pos];
    }

    public int numWaysDecodingDFS(String string, int pos){
        if(pos < string.length() && string.charAt(pos) == '0'){
            return 0;
        }
        if(pos == string.length()){
            return 1;
        }
        int ways = numWaysDecodingDFS(string, pos + 1);
        int val = 0;
        if(pos  + 1 < string.length()){
            val = Integer.parseInt(string.substring(pos, pos + 2));
        }
        if(val >= 10 && val <= 26){
            ways += numWaysDecodingDFS(string , pos + 2);
        }
        return ways;
    }

    public int numWaysDecodingBFS(String string){
        Queue<Integer> mQueue = new LinkedList<>();
        mQueue.offer(0);

        int ways = 0;

        while (!mQueue.isEmpty()){
            int pos = mQueue.poll();
            if(pos == string.length()){
                ways++;
                continue;
            }
            if(string.charAt(pos) == '0'){
                continue;
            }
            mQueue.offer(pos + 1);

            int val = 0;
            if(pos + 1 < string.length()){
                val = Integer.parseInt(string.substring(pos, pos + 2));
            }
            if(val >= 10 && val <= 26){
                mQueue.offer(pos + 2);
            }
        }
        return ways;
    }


    /**
     * Input: arr = [900,950,800,1000,700,800]
     * Output: 5
     * Explanation: The sum of weights of the 6 apples exceeds 5000 so we choose any 5 of them.
     *
     * Input: arr = [100,200,150,1000]
     * Output: 4
     * Explanation: All 4 apples can be carried by the basket since their sum of weights is 1450.
     * https://leetcode.com/contest/biweekly-contest-9/problems/how-many-apples-can-you-put-into-the-basket/
     *
     */

    public int maxNumberOfApples(int[] arr) {
        Arrays.sort(arr);
        int allowed = 5000;
        int sum = 0;
        int i = 0;
        for (; i < arr.length; i++) {
            sum += arr[i];

            if(sum > allowed){
                return i - 1;
            }
        }
        return -1;
    }

    /***
     * Input: mat = [[1,2,3,4,5],[2,4,5,8,10],[3,5,7,9,11],[1,3,5,7,9]]
     * Output: 5
     *
     * https://leetcode.com/contest/biweekly-contest-9/problems/find-smallest-common-element-in-all-rows/
     *
     */
    public int smallestCommonElement(int[][] mat) {
        int minIdx = -1;
        int res = -1;
        for (int i = 0; i < mat[0].length; i++) {
            int ele = mat[0][i];

            for (int j = 1; j < mat.length; j++) {
                minIdx = Arrays.binarySearch(mat[j], ele);
                if(minIdx < 0){
                    break;
                }else{
                    res = mat[j][minIdx];
                }
            }
            if(minIdx >= 0){
                return res;
            }
        }
        return -1;
    }

    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        Arrays.sort(arr);
        List<List<Integer>> res = new ArrayList<>();

        int min_ab_diff = Integer.MAX_VALUE;

        int min_diff = Integer.MAX_VALUE;

        for (int i = 0; i < arr.length -1; i++) {
            if(arr[i + 1] - arr[i] < min_ab_diff){
                min_ab_diff = arr[i+1] - arr[i];
                if(min_diff > min_ab_diff){
                    List<Integer> curr = new ArrayList<>();
                    curr.add(arr[i+1]);
                    curr.add(arr[i]);

                    res.clear();

                    res.add(curr);
                    min_diff = min_ab_diff;
                }else if(min_diff == min_ab_diff){
                    List<Integer> curr = new ArrayList<>();
                    curr.add(arr[i+1]);
                    curr.add(arr[i]);
                    res.add(curr);
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/smallest-string-with-swaps/
     * Input: s = "dcab", pairs = [[0,3],[1,2],[0,2]]
     * Output: "abcd"
     * */
    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        int N = s.length();
        UnionFind unionFind = new UnionFind(N);
        for (List<Integer> swap : pairs) {
            unionFind.union(swap.get(0), swap.get(1));
        }
        Map<Integer, PriorityQueue<Character>> graph = new HashMap<>();

        for (int i = 0; i < N; i++) {
            int head = unionFind.find(i);
            PriorityQueue<Character> characters = graph.computeIfAbsent(head, (dummy) -> new PriorityQueue<>());
            characters.add(s.charAt(i));
        }

        StringBuilder sb = new StringBuilder(N);
        for (int i = 0; i < N ; i++) {
            PriorityQueue<Character> characters = graph.get(unionFind.find(i));
            char characterMin = characters.poll();
            sb.append(characterMin);
        }
        return sb.toString();
    }

    /**
     * 162. Find Peak Element
     * https://leetcode.com/problems/find-peak-element/solution/
     * */
    public int findPeakElement(int[] nums) {
        return findPeakElementBinarySearch(nums, 0, nums.length-1);
       /* for (int i = 0; i < nums.length -1; i++) {
                if (nums[i] > nums[i + 1]) {
                    return i;
                }
            }
        return nums.length -1;*/
    }

    public int findPeakElementBinarySearch(int[] nums, int l, int r) {
        if(l == r){
            return l;
        }
        int mid = (l + r)/2;
        if(nums[mid] > nums[mid + 1]){
            return findPeakElementBinarySearch(nums, l, mid);
        }return findPeakElementBinarySearch(nums, mid +1, r);
    }

    class UnionFind {
        public int[] size;
        public int[] parent;
        public UnionFind(int count){
            size = new int[count];
            parent = new int[count];
            for (int i = 0; i < count; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int p){
            while(p != parent[p]){
                parent[p] = parent[parent[p]];
                p = parent[p];
            }
            return p;
        }

        public int union(int p, int q) {
            int pRoot = find(p);
            int qRoot = find(q);

            if(pRoot == qRoot){
                size[pRoot] += size[pRoot];
                parent[qRoot] = pRoot;
                return size[qRoot];
            }else{
                parent[pRoot] = qRoot;
                size[qRoot] += size[pRoot];
                return size[qRoot];
            }
        }
    }

    /***
     * https://leetcode.com/problems/ugly-number-iii/
     */
    public int nthUglyNumber(int n, int a, int b, int c) {
        int left = 0;
        int right = Integer.MAX_VALUE;
        int result = 0;

        while (left <= right){
            int mid = left + ( right - left) / 2;
            if(count(mid, a, b, c) >= n){
                result = mid;
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return result;
    }

    private int count(int num, int a, int b, int c) {
        return (int)(num/a + num/b + num/c - num/lcm(a,b) - num/lcm(b,c) - num/lcm(a,c) + num/lcm(a, lcm(b, c)));
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

    private long gcd(long a, long b) {
        if(a == 0){
            return b;
        }
        return gcd(b % a, a);
    }

    private int isUglyNumber(int no, int a, int b, int c) {
        if(no%a == 0){
            return 1;
        }else if(no%b == 0){
            return 1;
        }else if(no%c == 0){
            return 1;
        }
        return 0;
    }
}
