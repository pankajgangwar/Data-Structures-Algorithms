package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import sun.reflect.generics.tree.Tree;

/**
 * Created by Pankaj Kumar on 3/Aug/2020
 */
class AugustW1 {

    public static void main(String[] args) {
        AugustW1 w1 = new AugustW1();
        //boolean status = w1.canConvertString("atmtxzjkz","tvbtjhvjd", 35);
        int status = w1.findMaximumXOR(new int[]{3,10,5,25,2,8});
        System.out.println(status);
    }

    /*
    950. Reveal Cards In Increasing Order
    https://leetcode.com/problems/reveal-cards-in-increasing-order/
    */
    public int[] deckRevealedIncreasing(int[] deck) {
        LinkedList<Integer> q = new LinkedList<>();
        Arrays.sort(deck);
        
        int n = deck.length;
        int[] res = new int[n];
        for(int i = 0; i < deck.length; i++){
            q.add(i);
        }
        
        for(int i = 0; i < deck.length; i++){
            res[q.poll()] = deck[i];
            q.offer(q.poll());
        }
        return res;
    }

    /*
    624. Maximum Distance in Arrays
    https://leetcode.com/problems/maximum-distance-in-arrays/ */
    public int maxDistance(List<List<Integer>> arrays) {
        List<Integer> first = arrays.get(0);
        int max = first.get(first.size()-1);
        int min = first.get(0);
        int res = 0;
        for(int i = 1; i < arrays.size(); i++) {
            List<Integer> a = arrays.get(i);

            res = Math.max(res, Math.abs(a.get(a.size() - 1) - min));
            res = Math.max(res, Math.abs(max - a.get(0)));

            min = Math.min(min, a.get(0));
            max = Math.max(max, a.get(a.size()-1));
        }
        return res;
    }

    /* LC  Weekly Contest 201
    * https://leetcode.com/contest/weekly-contest-201
    */

    /*1547. Minimum Cost to Cut a Stick
    * https://leetcode.com/problems/minimum-cost-to-cut-a-stick/
    * */
    public int minCost(int n, int[] cuts) {
        List<Integer> list = new ArrayList<>();
        for(int a : cuts){
            list.add(a);
        }
        list.add(0);
        list.add(n);
        Collections.sort(list);

        int[][] memo = new int[102][102];

        for(int i = 0; i < 102; i++) {
            Arrays.fill(memo[i], -1);
        }

        return helper(list, 0, list.size() -1, memo);
    }

    public int helper(List<Integer> cuts, int i, int j, int[][] memo){
        if(j - i <= 1) return 0;
        if(memo[i][j] == -1) {
            memo[i][j] = Integer.MAX_VALUE;
            for(int k = i + 1; k < j; k++) {
                memo[i][j] = Math.min(memo[i][j], cuts.get(j) - cuts.get(i) + helper(cuts, i, k, memo) +
                        helper(cuts, k, j, memo));
            }
        }
        return memo[i][j];
    }

    /*
    * 1546. Maximum Number of Non-Overlapping Subarrays With Sum Equals Target
    * https://leetcode.com/problems/maximum-number-of-non-overlapping-subarrays-with-sum-equals-target/
    * */
    public int maxNonOverlapping(int[] nums, int target){
        return solution2(nums, target);
    }

    public int solution2(int[] nums, int target){
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);

        int res = 0;
        int sum = 0;

        for (int i = 0; i < nums.length; ++i) {
            sum += nums[i];
            if (map.containsKey(sum - target)) {
                res = Math.max(res, map.get(sum - target) + 1);
            }
            map.put(sum, res);
        }
        return res;
    }

    public int solution1(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);
        int prefSum = 0;
        int res = 0;
        TreeSet<Integer> sets = new TreeSet<>();
        for (int i = 0; i < nums.length; i++) {
            prefSum += nums[i];
            if(map.containsKey(prefSum - target)){
                int prevIdx = map.get(prefSum - target) + 1;
                if(!sets.isEmpty()){
                    int first = sets.first();
                    int last = sets.last();
                    if(prevIdx > first && prevIdx > last) {
                        sets.add(prevIdx);
                        sets.add(i);
                        res++;
                    }
                }else{
                    sets.add(prevIdx);
                    sets.add(i);
                    res++;
                }
            }
            map.put(prefSum, i);
        }
        return res;
    }

    /*
    * 1545. Find Kth Bit in Nth Binary String
    * https://leetcode.com/problems/find-kth-bit-in-nth-binary-string/
    * */
    public char findKthBit(int n, int k) {
        if(n <= 1){
            return '0';
        }
        String res = helper(n);
        return res.charAt(k - 1);
    }

    public String helper(int n){
        if(n == 1){
            return "0";
        }
        StringBuilder out = new StringBuilder();
        String prev = helper(n - 1);
        out.append(prev).append("1");
        StringBuilder inv = new StringBuilder();
        for (int i = 0; i < prev.length(); i++) {
            inv.append(prev.charAt(i) == '0' ? '1' : '0');
        }
        inv.reverse();
        out.append(inv.toString());
        return out.toString();
    }


    /*
    * 1544. Make The String Great
    * https://leetcode.com/problems/make-the-string-great/
    */
    public String makeGood(String s) {
        Stack<Character> stack = new Stack<>();
        char[] arr = s.toCharArray();
        for (int i = 0; i < arr.length ; i++) {
            if(!stack.isEmpty() && Math.abs(stack.peek() - arr[i]) == 32 ){
                stack.pop();
            }else{
                stack.push(arr[i]);
            }
        }
        char[] res = new char[stack.size()];
        int index = stack.size() - 1;
        while (!stack.isEmpty()){
            res[index--] = stack.pop();
        }
        return new String(res);
    }


    public int minInsertions(String s) {
        char[] arr = s.toCharArray();
        int res = 0;
        int open = 0;
        int close = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] == '('){
                res += 2;
            }else if(res <= 0){
                if(arr[i] == arr[i+1]){
                    res--;
                }
            }
        }
        if(res < 0) {
            if(Math.abs(res) % 2 == 0 ){
                return Math.abs(res) / 2;
            }else{
                int ans = Math.abs(res);
                while (ans > 0){
                    if(ans >= 2){
                        ans = ans - 2;
                        open++;
                    }else{
                        ans = ans - 1;
                        open++;
                        close++;
                    }
                }
                return open + close;
            }
        }else{
            return res;
        }
    }

    public boolean canConvertString(String s, String t, int k) {
        int n = s.length();
        int m = t.length();
        if(m != n) return false;
        int[] count = new int[26];
        for (int i = 0; i < n; i++) {
            int x = ( t.charAt(i) - s.charAt(i) + 26 ) % 26;
            if(x > 0 && x + count[x] * 26 > k ){
                return false;
            }
            ++count[x];
        }
        return s.length() == t.length();
    }

    /*
    1539. Kth Missing Positive Number
    https://leetcode.com/problems/kth-missing-positive-number/ */
    public int findKthPositive(int[] arr, int k) {
        int l = 0, r = arr.length;
        while(l < r){
            int mid = l + (r - l) / 2;
            if(arr[mid] - 1 - mid < k){
                l = mid + 1;
            }else{
                r = mid;
            }
        }
        //return r + k;
        return l + k;
    }

    /*
    LC : 1300. Sum of Mutated Array Closest to Target
    https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/ */
    public int findBestValue(int[] arr, int target) {
        int max = Arrays.stream(arr).max().getAsInt();
        int sum = Arrays.stream(arr).sum();
        if(sum == target) return max;

        int low = 0;
        int high = max;
        int n = arr.length;
        int res = 1;
        int diff = Integer.MAX_VALUE;
        while (low <= high ){
            int mid = low + (high - low) / 2;
            int [] copy = Arrays.copyOf(arr, n);
            for (int i = 0; i < n; i++) {
                if(copy[i] > mid){
                    copy[i] = mid;
                }
            }
            int currsum = Arrays.stream(copy).sum();
            if(currsum > target) {
                high = mid - 1;
            }else{
                low = mid + 1;
            }

            if((Math.abs(currsum - target) < diff || (Math.abs(currsum - target) == diff) )
                    && mid < res){
                res = mid;
                diff = Math.abs(currsum - target);
            }
        }
        return res;
    }

    /* 
    900. RLE Iterator
    https://leetcode.com/problems/rle-iterator/
    Sol: https://leetcode.com/problems/rle-iterator/discuss/779590/JAVA-O(1)-lookup-using-ArrayDeque
    */
    class RLEIterator {
        /*ArrayDeque<Integer> qNumber;
        ArrayDeque<Integer> qOccurance;*/
        int[] arr;
        public RLEIterator(int[] arr) {
            this.arr = arr;
            /*qNumber = new ArrayDeque<>();
            qOccurance = new ArrayDeque<>();
            for(int i = 0; i < arr.length - 1; i += 2) {
                int times = arr[i];
                int num = arr[i+1];

                qNumber.add(num);
                qOccurance.add(times);
            }*/
        }

        int start = 0;
        public int next(int n) {
            while(n > 0 && start < arr.length ) {
                int occ = arr[start];
                if(n > occ){
                    n = n - occ;
                    start += 2;
                }else{
                    occ = occ - n;
                    arr[start] = occ;
                    return arr[start+1];
                }
            }
            return -1;
            /*if(qOccurance.isEmpty()) return -1;

            while(n > 0 && !qNumber.isEmpty()) {
                int number = qNumber.pollFirst();
                int occ = qOccurance.pollFirst();

                if(n > occ){
                    n = n - occ;// n is bigger than occ, check next occ with remaining n
                }else{
                    occ = occ - n;//Add the remaining occ back to queue
                    qOccurance.addFirst(occ);
                    qNumber.addFirst(number);
                    return number;
                }
            }
            return -1;*/
        }
    }


    /*
     1493. Longest Subarray of 1's After Deleting One Element
     https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/
     Similar : https://leetcode.com/problems/max-consecutive-ones-iii/
     Sliding Window
     */
    public int longestSubarray(int[] nums) {
        int n = nums.length;
        int max = 0;
        int count = 1;
        int start = 0;
        
        for(int i = 0; i < n; i++){
            if(nums[i] == 0) count--;
            
            while(count < 0){
                if(nums[start] == 0){
                    count++;            
                }
                start++;
            }
            max = Math.max(max, i - start);
        }
        return max;
    }

    /*
     1231. Divide Chocolate
     https://leetcode.com/problems/divide-chocolate/
     Binary search
     */
    public int maximizeSweetness(int[] arr, int k) {
        //return maximizeSweetnessDP(arr, k + 1);
        return maximizeSweetnessBinarySearch(arr, k + 1);
    }

    public int maximizeSweetnessBinarySearch(int[] nums, int k){
        int min = Arrays.stream(nums).min().getAsInt();
        int sum = Arrays.stream(nums).sum();

        long left = min;
        long right = sum;
        while(left < right) { // Binary search over sweetness value
            long mid = (right + left + 1) / 2;
            if(valid(mid, nums, k) < k ) {
                right = mid - 1;
            }else{
                left = mid;
            }
        }
        return (int)left;
    }

    public int valid(long minSweet, int[] arr, int k) {
        int people = 0;
        int sweet = 0;
        for(int a : arr){
            sweet += a;
            if((sweet ) >= minSweet){
                sweet = 0;
                if(++people > k) break;
            }
        }
        return people;
    }

    /*
    Similar https://leetcode.com/problems/split-array-largest-sum/
    TLE
    */
    public int maximizeSweetnessDP(int[] nums, int m) {
        int n = nums.length;
        int[][] f = new int[n + 1][m + 1];
        int sub[] = new int[n + 1];

        for(int i = 0; i <= n; i++){
            for (int j = 0; j <= m; j++) {
                f[i][j] = Integer.MIN_VALUE;
            }
        }
        for(int i = 0; i < n; i++){
            sub[i+1] = sub[i] + nums[i];
        }

        f[0][0] = Integer.MAX_VALUE;

        for(int i = 1; i <= n; i++){
            for(int j = 1; j <= m; j++){
                for(int k = 0; k < i; k++){
                    f[i][j] = Math.max(f[i][j], Math.min(f[k][j-1], sub[i] - sub[k]));
                }
            }
        }
        return f[n][m];
    }

    /*
    421. Maximum XOR of Two Numbers in an Array
    https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */
    public int findMaximumXOR(int[] nums) {
        return findMaximumXORUsingTrie(nums);
        //return findMaximumXORUsingSets(nums);
    }
    
    class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        public TrieNode() { }
    }

    public int findMaximumXORUsingTrie(int[] nums){
        int max = nums[0];
        for(int n : nums) max = Math.max(max, n);

        int l = Integer.toBinaryString(max).length();
        int len = nums.length, bitMask = 1 << l;

        String[] strNums = new String[len];

        for(int i = 0; i < len; ++i){
            strNums[i] = Integer.toBinaryString(bitMask | nums[i]).substring(1);
        }

        int max_xor = 0;
        TrieNode root = new TrieNode();
        for(String n : strNums) {
            TrieNode curr = root, xorNode = root;
            int curr_xor = 0;
            for(Character bit : n.toCharArray()) {
                if(curr.children.containsKey(bit)) {
                    curr = curr.children.get(bit);
                }else{
                    TrieNode node = new TrieNode();
                    curr.children.put(bit, node);
                    curr = node;
                }

                Character bitFlip = bit == '0' ? '1' : '0';

                if(xorNode.children.containsKey(bitFlip)) {
                    curr_xor = (curr_xor << 1) | 1;
                    xorNode = xorNode.children.get(bitFlip);
                }else {
                    curr_xor = curr_xor << 1;
                    xorNode = xorNode.children.get(bit);
                }
                max_xor = Math.max(max_xor, curr_xor);
            }
        }
        return max_xor;
    }
    
    public int findMaximumXORUsingSets(int[] nums) {
        int max_xor = 0, curr_xor = 0;
        HashSet<Integer> prefixes = new HashSet<>();
        int max = 0;
        for(int n : nums) max = Math.max(max, n);

        int l = Integer.toBinaryString(max).length();
    
        for(int i = l - 1; i >=0 ; --i){
            max_xor = max_xor << 1; // make space of right most bit

            curr_xor = max_xor | 1; // add 1 to right most bit

            prefixes.clear();
            for(int n : nums){
                prefixes.add(n >> i); // add ith bit to prefixes
            }

            for(int n : prefixes) {
                // p1^p2 = curr
                // curr ^ p2 = p1
                if(prefixes.contains(curr_xor ^ n)){ 
                    max_xor = curr_xor;
                    break;
                }
            }
        }
        return max_xor;
    }

    /*
    939. Minimum Area Rectangle
    https://leetcode.com/problems/minimum-area-rectangle/ */
    public int minAreaRect(int[][] points) {
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        for(int i = 0; i < points.length; i++) {
            if(!map.containsKey(points[0])) {
                map.putIfAbsent(points[i][0], new HashSet<Integer>());
            }
            map.get(points[i][0]).add(points[i][1]);
        }

        int min = Integer.MAX_VALUE;
        for(int[] p1 : points) {
            for(int[] p2 : points) {
                if(p1[0] == p2[0] || p1[1] == p2[1]) continue;
                if(map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1]) ){
                    min = Math.min(min, Math.abs(p1[0] - p2[0]) *  Math.abs(p1[1] - p2[1]) );
                }
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /* 
    907. Sum of Subarray Minimums
    https://leetcode.com/problems/sum-of-subarray-minimums/ 
    Revisit
    */
    public int sumSubarrayMins(int[] arr) {
        int start = 0;
        int end = arr.length - 1;
        seen = new HashSet<>();
        //return helper(arr, start, end);
        return usingMonotoneStack(arr);
    }

    public int usingMonotoneStack(int[] arr) {
        Stack<int[]> stk_p = new Stack<>();
        Stack<int[]> stk_n = new Stack<>();
        int n = arr.length;
        int[] left = new int[n];
        int[] right = new int[n];

        for(int i = 0; i < n; i++) {
            left[i] = i + 1;
            right[i] = n - i;
        }

        for(int i = 0; i < n; i++) {
            while(!stk_p.isEmpty() && stk_p.peek()[0] > arr[i]) {
                stk_p.pop();
            }
            left[i] = stk_p.isEmpty() ? (i + 1) : (i - stk_p.peek()[1]);
            stk_p.push(new int[]{arr[i], i});
        }

        for(int i = 0; i < n; i++) {
            while(!stk_n.isEmpty() && stk_n.peek()[0] > arr[i]) {
                int[] top = stk_n.pop();
                right[top[1]] = i - top[1];
            }
            stk_n.push(new int[]{arr[i], i});
        }

        int ans = 0;
        int mod = (int)1e9 + 7;
        for(int i = 0; i < n; i++){
            ans = (ans + arr[i] * left[i] * right[i]) % mod;
        }
        return ans;
    }
    
    HashSet<String> seen;
    public int helper(int[] arr, int start, int end) { // TLE
        int mod = (int)1e9 + 7;
        String s = start + "," + end;
        if(!seen.add(s)) {
            return 0;
        }
        int n = arr.length;
        if(start > end) return 0;
        if(start == end ) return arr[start];
        
        int min = Integer.MAX_VALUE;
        
        for(int i = start; i <= end; i++) {
            if(min > arr[i]) {
                min = arr[i];
            }
        }
        int l = helper(arr, start, end - 1) % mod;
        int r = helper(arr, start + 1, end) % mod;
        int res = (l + r + min) % mod;
        return res;
    }

    /* 
    LC : 260. Single Number III
    https://leetcode.com/problems/single-number-iii/ */
    public int[] singleNumber(int[] nums) {
        int diff = 0;
        for(int n : nums){
            diff = diff ^ n; 
        }

        diff = diff & -diff; // Get the LSB, power of 2
        int[] res = new int[]{ 0, 0 };

        for(int n : nums){
            int idx = diff & n;
            res[idx] = res[idx] ^ n;
        }
        return res;
    }

}