package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Pankaj Kumar on 26/July/2020
 */
class JulyW4 {

    public static void main(String[] args) {
        JulyW4 w4 = new JulyW4();
        String res = w4.largestTimeFromDigits(new int[]{1,5,2,5});
        System.out.println("res = " + res);
    }

    /*
    869. Reordered Power of 2
    https://leetcode.com/problems/reordered-power-of-2/
    Sol : https://leetcode.com/problems/reordered-power-of-2/discuss/762832/JAVA-Backtracking
    */
    public boolean reorderedPowerOf2(int n) {
        if(Integer.bitCount(n) == 1) return true;
        char[] arr = String.valueOf(n).toCharArray();
        return helper(arr, new StringBuilder(), new boolean[arr.length], new HashSet<>());
    }

    /*
    LC : 949. Largest Time for Given Digits
    https://leetcode.com/problems/largest-time-for-given-digits/ */
    public String largestTimeFromDigits(int[] arr) {
        String ans = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    for (int l = 0; l < 4; l++) {
                        if(i == j || j == k || i == k) continue;
                        if(i == l || k == l || j == l) continue;

                        String hr = arr[i]+""+arr[j];
                        String min = arr[k] + "" + arr[l];
                        String t = hr + ":" + min;
                        if(hr.compareTo("24") < 0 && min.compareTo("60") < 0 && ans.compareTo(t) < 0){
                            ans = t;
                        }
                    }
                }
            }
        }
        return ans;
    }

    public boolean helper(char[] arr, StringBuilder out, boolean[] visited, HashSet<String> vis){
        if(out.length() > 0){
            if(!vis.add(out.toString())) return false;
        }
        if(out.length() >= arr.length) {
            if(out.charAt(0) == '0') return false;
            int curr = Integer.valueOf(out.toString());
            return Integer.bitCount(curr) == 1;
        }
        for(int i = 0; i < arr.length; i++) {
            if(visited[i]) continue;
            out.append(arr[i]);
            visited[i] = true;
            if(helper(arr, out, visited, vis)) {
                return true;
            }
            visited[i] = false;
            out.deleteCharAt(out.length() - 1);
        }
        return false;
    }

    /*
    * 754. Reach a Number
    * https://leetcode.com/problems/reach-a-number/
    * Revisit
    * */
    public int reachNumber(int target) {
        //return helper(target, 0, 0);
        return iterative(target);
    }
    
    public int iterative(int target){
        target = Math.abs(target);
        int sum = 0;
        int steps = 0;
        
        while(sum < target){
            steps++;
            sum += steps;
        }
        
        //  Because of symmetry over the line we 
        //  find the even difference and divide by two
        while((sum - target) % 2 != 0 ){ 
            steps++;
            sum += steps;
        }
        return steps;
    }
    
    public int helper(int t, int n, int c ) {
        if(Math.abs(c) > t) return Integer.MAX_VALUE;
        if(c == t) {
            return n;
        }
        int left = c - n - 1;
        int right = c + n + 1;
        return Math.min( helper(t, n + 1, left ), helper(t, n + 1, right ) );
    }

    /*
    29. Divide Two Integers
    https://leetcode.com/problems/divide-two-integers/
    to-do: Read the article
    */
    public int divide(int dividend, int divisor) {
        int sign = 1;
        if(dividend < 0 && divisor > 0 || dividend > 0 && divisor < 0){
            sign = -1;
        }
        if(divisor == 0) return 0;
        if(dividend > Integer.MAX_VALUE){

        }
        long ans = helper(Math.abs((long)dividend), Math.abs((long)divisor));
        if(ans > Integer.MAX_VALUE){
            return (int)sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }else{
            return (int)ans * sign;
        }
    }

    public long helper(long div, long divs){
        if(div < divs){
            return 0;
        }
        long sum = divs;
        long mul = 1;

        while((sum << 1) <= div){ // sum + sum <= div
            sum = sum << 1;
            mul = mul << 1;
            //sum += sum;
            //mul += mul;
        }
        return mul + helper(div - sum, divs);
    }

    /* 
    849. Maximize Distance to Closest Person
    https://leetcode.com/problems/maximize-distance-to-closest-person/
    Ans : https://leetcode.com/problems/maximize-distance-to-closest-person/discuss/760296/JAVA-O(n)-With-comments
    */
    public int maxDistToClosest(int[] s) {
        int d = 0;
        int n = s.length;
        
        int low = -1;
        int high = -1;
        for(int i = 0; i < n; i++) {
            if(s[i] == 0) continue;
            if(s[i] == 1 && low == -1) {
                low = i;
                d = Math.max(d, low ); // distance of first seat from 0 [0,0,0,1,0,1]
            }else {
                high = i;
                d = Math.max(d, (high - low ) / 2); // distance between two seats [1,0,0,1,0,1]
                low = i;
            }
        }
        
        if(high == -1) {
            d = Math.max(d, Math.max(low, n - 1 - low) ); // If only one seat is occupied [0,1,0,0,0] 
        }else if( high < n - 1) {
            d = Math.max(d, n - 1 - high); // distance of last seat from n - 1  [0,1,0,1,0,0]
        }
        return d;
    }

    /*
    1104. Path In Zigzag Labelled Binary Tree
    https://leetcode.com/problems/path-in-zigzag-labelled-binary-tree/
    */
    public List<Integer> pathInZigZagTree(int label) {
        int level = 1;
        int nodeCount = 1;
        int height = (int) (Math.log(label) / Math.log(2) + 1e-10);
        while(label >= nodeCount * 2) {
            level++;
            nodeCount = nodeCount * 2;
        }
        System.out.println("Level " + level + " height " + height);
        List<Integer> res = new ArrayList<>();
        while(label != 0) {
            res.add(label);
            int levelMax = (int)Math.pow(2,level) - 1;
            int levelMin = (int)Math.pow(2, level - 1);
            int x = (levelMax + levelMin) - label;
            label = x / 2;
            level--;
        }
        Collections.reverse(res);
        return res;
    }

    /*
    1244. Design A Leaderboard
    https://leetcode.com/problems/design-a-leaderboard/
    */
    class Leaderboard {
        HashMap<Integer, Integer> map;
        TreeMap<Integer, Integer> tMap;
        public Leaderboard() {
            map = new HashMap<>();
            tMap = new TreeMap<>(Collections.reverseOrder());
        }
        
        public void addScore(int pid, int score) {
            if(!map.containsKey(pid)){
                map.put(pid, score);
                tMap.put(score, tMap.getOrDefault(score, 0) + 1);
            }else{
                int prevScore = map.get(pid);
                tMap.put(prevScore, tMap.get(prevScore) - 1);
                if(tMap.get(prevScore) == 0){
                    tMap.remove(prevScore);
                }
                int newScore = prevScore + score;
                map.put(pid, newScore);
                tMap.put(newScore, tMap.getOrDefault(newScore, 0) + 1);
            }
        }
        
        public int top(int K) {
            Set<Integer> keys = tMap.keySet();
            int res = 0;
            Iterator<Integer> it = keys.iterator();
            while(it.hasNext()){
                Integer score = it.next();
                int players = tMap.get(score);
                for(int i = 0; i < players; i++){
                    res += score;
                    K--;
                    if(K == 0){
                        return res;
                    }
                }
            }
            return res;
        }
        
        public void reset(int pid) {
            int score = map.get(pid);
            map.remove(pid);
            tMap.put(score, tMap.get(score) - 1);
        }
    }

    /*
    855. Exam Room
    https://leetcode.com/problems/exam-room/
    */
    class ExamRoom {

        ArrayList<Integer> list = new ArrayList<>();
        final int n;
        public ExamRoom(int N) {
            this.n = N;
        }

        public int seat() {
            if(list.isEmpty()){
                list.add(0);
                return 0;
            }
            int d = Math.max(list.get(0), n - 1 - list.get(list.size() - 1)); // max distance between first and last
            for(int i = 0; i < list.size() - 1; i++){
                int low = list.get(i);
                int high = list.get(i + 1);
                d = Math.max(d, (high - low) / 2); // max distance of middle of each seats
            }
            if(list.get(0) == d){ //if only one seat is occupied which is except first(0)
                list.add(0, 0);
                return 0;
            }
            for(int i = 0; i < list.size() - 1; i++){
                int low = list.get(i);
                int high = list.get(i + 1);
                if((high - low) / 2 == d ){
                    int mid = low + (high - low) / 2;
                    list.add(i + 1, mid);
                    return list.get(i + 1);
                }
            }
            list.add(n - 1);
            return n - 1;
        }

        public void leave(int p) {
            for(int i = 0; i < list.size(); i++){
                if(list.get(i) == p) {
                    list.remove(i);
                    break;
                }
            }
        }

    }


    /*
    LC : 969. Pancake Sorting
    https://leetcode.com/problems/pancake-sorting/
    */
    public List<Integer> pancakeSort(int[] A) {
        List<Integer> res = new ArrayList<>();
        int largest = A.length;
        int n = A.length;
        for (int i = 0; i < n; i++) {
            int idx = findLargest(A, largest);
            reverse(A, 0, idx);
            reverse(A, 0, largest - 1 );
            res.add(idx + 1);
            res.add(largest--);
        }
        return res;
    }

    public void reverse(int[] arr, int low, int high){
        while (low < high){
            int temp = arr[low];
            arr[low] = arr[high];
            arr[high] = temp;
            low++;
            high--;
        }
    }

    public int findLargest(int [] arr, int target){
        int n = arr.length;
        for(int i = 0; i < n; i++){
            if(arr[i] == target) return i;
        }
        return -1;
    }

    /* 
    LC : 926. Flip String to Monotone Increasing
    https://leetcode.com/problems/flip-string-to-monotone-increasing
    */
    public int minFlipsMonoIncr(String S) {
        char[] arr = S.toCharArray();
        int flipCount = 0;
        int oneCount = 0;
        for(int i = 0; i < arr.length; i++) {
            if(arr[i] == '0') {
                if(oneCount == 0) continue;
                flipCount++;
            }else {
                oneCount++;   
            }
            if(flipCount > oneCount) {
                flipCount = oneCount;
            }
        }
        return flipCount;
    }

    public int countOdds(int low, int high) {
        int ret = 0;
        ret += (high - low) / 2 ;
        if(low % 2 == 1 || high % 2 ==1 ) ret++;
        return ret;
    }

    /*
     LC : 1524. Number of Sub-arrays With Odd Sum
     https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum
     */
    public int numOfSubarrays(int[] arr) {
        int ans = 0;
        int mod = (int)1e9 + 7;
        int odd = 0, even = 0;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] % 2 == 1){
                int temp = odd;
                odd = even + 1;
                even = temp;
            }else{
                even++;
            }
            ans = (ans + odd)  % mod;
        }
        return ans;
    }

    /*
    * LC : 1530. Number of Good Leaf Nodes Pairs
    * https://leetcode.com/problems/number-of-good-leaf-nodes-pairs/
    * */
    int res = 0;
    public int countPairs(TreeNode root, int distance) {
        helper(root, distance);
        return res;
    }

    public List<Integer> helper(TreeNode root, int distance) {
        List<Integer> list = new ArrayList<>();
        if(root == null) return list;
        if(root.left == null && root.right == null) {
            list.add(1);
            return list;
        }
        List<Integer> left = helper(root.left, distance);
        List<Integer> right = helper(root.right, distance);
        for (int x : left) {
            for (int y :  right) {
                if(x + y <= distance ){
                    res++;
                }
            }
        }
        for(int x : left){
            if(x + 1 > distance)continue;
            list.add(x + 1);
        }
        for(int y : right){
            if(y + 1 > distance)continue;
            list.add(y + 1);
        }
        return list;
    }

    /*
    1529. Bulb Switcher IV
    https://leetcode.com/problems/bulb-switcher-iv/
    */
    public int minFlips(String target) {
        int flips = 0;
        for (int i = 0; i < target.length(); i++){
            if(i > 0 && target.charAt(i) != target.charAt(i-1)){
                flips++;
            }
            if(i == 0 && target.charAt(i) == '1'){
                flips++;
            }
        }
        return flips;
    }

    /*
    LC : 1528
    https://leetcode.com/problems/shuffle-string/ */
    public String restoreString(String s, int[] indices) {
        char[] arr = s.toCharArray();
        char[] copy = Arrays.copyOf(arr, arr.length);
        for (int i = 0; i < arr.length; i++) {
            copy[indices[i]] = arr[i];
        }
        return new String(copy);
    }

}
