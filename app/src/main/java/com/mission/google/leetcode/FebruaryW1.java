package com.mission.google.leetcode;

import android.webkit.HttpAuthHandler;

import com.mission.google.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.function.IntConsumer;

public class FebruaryW1 {

    static int countSpecialElements(List<List<Integer>> matrix) {
        int rows = matrix.size();
        int cols = matrix.get(0).size();
        int[][] mat = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mat[i][j] = matrix.get(i).get(j);
            }
        }

        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(!set.add(mat[i][j])){
                    return -1;
                }
            }
        }

        int rowmax[] = new int[rows];
        int rowmin[] = new int[rows];
        int colmax[] = new int[cols];
        int colmin[] = new int[cols];

        for (int i = 0; i < rows; i++) {
            int rowMin = Integer.MAX_VALUE;
            int rowMax = Integer.MIN_VALUE;
            for (int j = 0; j < cols; j++) {
                rowMax = Math.max(rowMax, mat[i][j]);
                rowMin = Math.min(rowMin, mat[i][j]);
            }
            rowmax[i] = rowMax;
            rowmin[i] = rowMin;
        }

        for (int j = 0; j < cols; j++) {
            int colMin = Integer.MAX_VALUE;
            int colMax = Integer.MIN_VALUE;
            for (int i = 0; i < rows; i++) {
                colMax = Math.max(colMax, mat[i][j]);
                colMin = Math.min(colMin, mat[i][j]);
            }

            colmax[j] = colMax;
            colmin[j] = colMin;
        }

        int distinct_ele = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((mat[i][j] == rowmax[i])|| (mat[i][j] == rowmin[i])|| (mat[i][j] == colmax[j])|| (mat[i][j] == colmin[j])) {
                    distinct_ele++;
                }
            }
        }
        return distinct_ele;
    }

    /* 119205 */
    public long calculatePossibleCombinations(String inputStr) {
        int n = inputStr.length();
        long[] memo = new long[n+1];
        Arrays.fill(memo, -1);
        return combinations(inputStr, 0, memo);
    }

    public long combinations(String string, int pos, long[] memo){
        if(pos < string.length() && string.charAt(pos) == '0'){
            return 0;
        }
        if(pos == string.length()){
            return 1;
        }
        if(memo[pos] != -1){
            return memo[pos];
        }

        memo[pos] = combinations(string, pos + 1, memo);
        long val = 0;
        if(pos + 1 < string.length()){
            val = Integer.parseInt(string.substring(pos, pos + 2));
        }
        if(val >= 10 && val <= 26){
            memo[pos] += combinations(string, pos + 2, memo);
        }
        return memo[pos];
    }

    /* https://leetcode.com/problems/basic-calculator/
    *  Input: "(1+(4+5+2)-3)+(6+8)"
       Output: 23
    *  */
    public int calculate(String s) {
        s = s.replaceAll("\\s+", "");
        StringBuilder builder = new StringBuilder(s);
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0 ; i < builder.length(); i++){

        }
        return 0;
    }

    /* Input: "(1+(4+5+2)-3)+(6+8)" */
    public int evaluate(StringBuilder builder, int start){

        int num = 0;
        int eval = 0;
        char sign = '+';
        int n = builder.length();
        for (int i = start; i <  builder.length(); i++) {
            if(builder.charAt(i) == '('){
                num = evaluate(builder, i+1);
            }
            if(builder.charAt(i) == ')'){
                return eval;
            }
            while(i < n &&
                    Character.isDigit(builder.charAt(i))){
                num = num*10 + builder.charAt(i) - '0';
                i++;
            }
            if (sign == '+') {
                eval = eval + num;
                num = 0;
            } else if (sign == '-') {
                eval = eval - num;
                num = 0;
            }

            if(i < n && builder.charAt(i) == '+'){
                sign = '+';
            }

            if(i < n && builder.charAt(i) == '-'){
                sign = '-';
            }
        }
        return eval;
    }

    /* https://leetcode.com/problems/search-in-rotated-sorted-array/ */
    public int search(int[] nums, int target) {
        if(nums.length == 0) return -1;

        int n = nums.length;
        if(n == 1){
            if(nums[0] == target){
                return 0;
            }else{
                return -1;
            }
        }
        int[] copy = new int[nums.length*2];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = nums[i%n];
        }

        int low = -1;
        int high = -1;
        for (int i = 1; i < copy.length; i++) {
            if(copy[i] < copy[i-1]){
                if(low == -1){
                    low = i;
                }else{
                    high = i - 1;
                }
            }
        }

        if(low == -1 || high == -1){ //Not rotated
            low = 0;
            high = n -1;
        }

        while (low <= high){
            int mid = low + (high - low) / 2;
            if(copy[mid] == target){
                if(mid >= n){
                    return mid - n;
                }else{
                    return mid;
                }
            }else if(copy[mid] > target){
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return -1;
    }

    /* https://leetcode.com/problems/find-minimum-in-rotated-sorted-array-ii/ */
    public int findMin(int[] nums) {
        int low = 0;
        int high = nums.length -1;
        int mid = 0;

        while (low < high){
            mid = low + (high - low) / 2;
            if(nums[mid] > nums[high]){
                low = mid + 1;
            }else if(nums[mid] < nums[high]){
                high = mid ;
            }else{
                high--;
            }
        }
        return nums[low];
    }

    public int findMinI(int[] nums) {
        if(nums.length == 0) return -1;

        int n = nums.length;
        if(n == 1){
            return nums[0];
        }
        int[] copy = new int[nums.length*2];
        for (int i = 0; i < copy.length; i++) {
            copy[i] = nums[i%n];
        }

        int low = -1;
        int high = -1;
        for (int i = 1; i < copy.length; i++) {
            if(copy[i] < copy[i-1]){
                if(low == -1){
                    low = i;
                }else{
                    high = i - 1;
                }
            }
        }

        if(low == -1 || high == -1){ //Not rotated
            low = 0;
            high = n -1;
        }

        return copy[low];
    }

    /* https://leetcode.com/problems/positions-of-large-groups/
    * Input: "abbxxxxzzy"
    * Input: "abcdddeeeeaabbbcd"
    *  */
    public List<List<Integer>> largeGroupPositions(String S) {
        char[] arr = S.toCharArray();
        int max = 3;
        int i = 0;
        int n = arr.length;
        List<List<Integer>> res = new ArrayList<>();
        while(i < n){
            int count = 0;
            int start = i;
            char ch = arr[i];
            while(i < n && arr[i] == ch){
                count++;
                i++;
            }
            int end = i-1;
            if(count >= 3){
                res.add(Arrays.asList(start, end));
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/minimum-size-subarray-sum/ 
        Input: s = 7, nums = [2,3,1,2,4,3]
        Output: 2
        Explanation: the subarray [4,3] has the minimal length under the problem constraint.
    */
    public int minSubArrayLenI(int targetSum, int[] nums) {
        if(nums.length == 0) return 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0, j = 0; i < nums.length ; i++) {
            int currSum = 0;
            j = i;
            while(j < nums.length && currSum < targetSum){
                currSum += nums[j++];
            }
            if(currSum >= targetSum){
                min = Math.min(min, j - i);
            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    public int minSubArrayLen(int targetSum, int[] nums) {
        if(nums.length == 0) return 0;
        int i = 0;
        int j = 0;
        int min = Integer.MAX_VALUE;
        int currSum = nums[j++];
        int n = nums.length;
        while(i < n){
            while(currSum < targetSum && j < n){
                currSum += nums[j++];
            }
            if(currSum >= targetSum){
                min = Math.min(min, j - i);
            }
            currSum -= nums[i++];
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }

    /* https://leetcode.com/problems/maximum-size-subarray-sum-equals-k/
    *  Revisit */
    public int maxSubArrayLen(int[] nums, int targetSum) {
        if(nums.length == 0) return 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int max = Integer.MIN_VALUE;
        int currSum = 0;
        for (int i = 0; i < nums.length; i++) {
            currSum += nums[i];
            if(currSum == targetSum) max = i + 1;
            else if(map.containsKey(currSum - targetSum)){
                max = Math.max(max, i - map.get(currSum - targetSum));
            }
            if(!map.containsKey(currSum)){
                map.put(currSum, i);
            }
        }
        return max;
    }


    /* https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/ */
    public int[] kWeakestRows(int[][] mat, int k) {
        //Count, Idx
        TreeMap<Integer, List<Integer>> map = new TreeMap<>();
        for (int i = 0; i < mat.length; i++) {
            int count = 0;
            for (int j = 0; j < mat[i].length; j++) {
                if(mat[i][j] == 1) count++;
            }
            List<Integer> res = new ArrayList<>();
            if(map.containsKey(count)){
                res = map.get(count);
            }
            res.add(i);
            map.put(count, res);
        }

        int[] result = new int[k];
        int idx = 0;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()){
            List<Integer> res = entry.getValue();
            Collections.sort(res);
            for (int i = 0; i < res.size(); i++) {
                if(k > 0) {
                    result[idx++] = res.get(i);
                    k--;
                }
            }
        }
        return result;
    }

    /*
     https://leetcode.com/problems/reduce-array-size-to-the-half/
     Input: arr = [3,3,3,3,5,5,5,2,2,7]
     Output: 2
     */
    public int minSetSize(int[] arr) {
        TreeMap<Integer, List<Integer>> tMap = new TreeMap<>(Collections.reverseOrder());
        HashMap<Integer, Integer> hMap = new HashMap<>();

        int n = arr.length;
        for (int i = 0; i < arr.length; i++) {
            hMap.put(arr[i], hMap.getOrDefault(arr[i], 0) + 1);
        }

        if(hMap.size() == n){
            return n/2;
        }

        List<Integer> list = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : hMap.entrySet()){
            List<Integer> tmp = new ArrayList<>();
            if(tMap.containsKey(entry.getValue())){
                tmp = tMap.get(entry.getValue());
            }
            tmp.add(entry.getKey());
            tMap.put(entry.getValue(), tmp);
        }

        for (Map.Entry<Integer, List<Integer>> entry : tMap.entrySet()){
            list.addAll(entry.getValue());
        }

        int i = 0;
        int min = 0;
        int count = 0;
        while(count < n/2){
            min++;
            int ele = list.get(i++);
            count += hMap.get(ele);
        }
        System.out.println("min = " + min);
        return min;
    }

    /* https://leetcode.com/problems/maximum-product-of-splitted-binary-tree/ */
    public long getSum(TreeNode root){
        if(root == null) return 0;
        return getSum(root.left) + getSum(root.right) + root.val;
    }

    public int maxProduct(TreeNode root) {
        if(root == null) return 0;
        long sum = getSum(root);
        checkMax(root, sum);
        return (int)(maxProd % ((int)Math.pow(10, 9) + 7));
    }

    long maxProd = 0;
    public long checkMax(TreeNode root, long sum){
        if(root == null) return 0;
        long left = checkMax(root.left, sum);
        long right = checkMax(root.right, sum);

        maxProd = Math.max(maxProd, (left + right + root.val) * (sum - left - right - root.val));
        return left + right + root.val;
    }

    /* https://leetcode.com/problems/high-five/ */
    /* Input: [[1,91],[1,92],[2,93],[2,97],[1,60],[2,77],[1,65],[1,87],[1,100],[2,100],[2,76]]
       Output: [[1,87],[2,88]]
    */
    public int[][] highFive(int[][] items) {
        HashMap<Integer, Integer> fMap = new HashMap<>();
        HashMap<Integer, PriorityQueue<Integer>> scoreMap = new HashMap<>();

        for(int[] curr : items){
            int id = curr[0];
            int score = curr[1];

            fMap.put(id, fMap.getOrDefault(id, 0) + 1);
            PriorityQueue<Integer> pq = new PriorityQueue<>(5);//minHeap
            if(scoreMap.containsKey(id)){
                pq = scoreMap.get(id);
                pq.offer(score);
                while(pq.size() > 5){
                    pq.poll();
                }
            }else{
                pq.offer(score);
            }
            scoreMap.put(id, pq);
        }
        int[][] result = new int[scoreMap.size()][2];
        int idx = 0;
        for(Map.Entry<Integer, PriorityQueue<Integer>> entry : scoreMap.entrySet()){
            int id = entry.getKey();
            PriorityQueue<Integer> pq = entry.getValue();
            int freq = pq.size();
            int totalScore = 0;
            while(!pq.isEmpty()){
                totalScore += pq.poll();
            }
            int[] res = new int[]{id, totalScore / freq};
            result[idx++] = res;
        }
        return result;
    }

    /* https://leetcode.com/problems/shortest-subarray-with-sum-at-least-k/  */
    public int shortestSubarray(int[] arr, int k) {
        int n = arr.length;
        int[] prefixSum = new int[n+1];
        int res = n + 1;

        for(int i = 0; i < n; i++) {
            prefixSum[i+1] = prefixSum[i] + arr[i];
        }
        Deque<Integer> dq = new ArrayDeque<>();
        for(int i = 0; i < n+1; i++) {
            while(!dq.isEmpty() && prefixSum[i] - prefixSum[dq.peekFirst()] >= k)
                res = Math.min(res, i - dq.pollFirst());

            while(!dq.isEmpty() && prefixSum[i] <= prefixSum[dq.peekLast()])
                dq.pollLast();

            dq.addLast(i);
        }
        return res <= n ? res : -1;
    }



    /* https://leetcode.com/problems/two-sum-bsts/ */
    public boolean twoSumBSTs(TreeNode root1, TreeNode root2, int target) {
        if(root1 == null){
            return false;
        }
        int key = target - root1.val;
        if(findKey(root2, key)){
            return true;
        }
        return twoSumBSTs(root1.left, root2, target) || twoSumBSTs(root1.right, root2, target);
    }

    public boolean findKey(TreeNode root, int target){
        if(root == null){
            return false;
        }
        if(root.val == target){
            return true;
        }else if(root.val < target){
            return findKey(root.right, target);
        }else{
            return findKey(root.left, target);
        }
    }

    List<Integer> mSortedList = new ArrayList<>();
    
    List<Integer> list = new ArrayList<>();
    public TreeNode bstToGst(TreeNode root) {
        updateList();
        System.out.println(list.toString());
        updateTree(root);
        return root;
    }
    
    int idx = 0;
    public void updateTree(TreeNode root){
        if(root == null) return;
        updateTree(root.left);
        root.val = list.get(idx++);
        updateTree(root.right);
    }
    
    public void updateList(){
        int n = mSortedList.size();
        for(int i = n - 1 ; i >= 0; --i){
            if(i == n - 1) continue;

            mSortedList.set(i, mSortedList.get(i+1) + mSortedList.get(i));
        }
    }
    
    public TreeNode bstToGstI(TreeNode root) {
        sortIncreasing(root);
        TreeNode result = toGst(root);
        return result;
    }
    
    public TreeNode toGst(TreeNode root){
        if(root == null){
            return null;
        }
        toGst(root.left);
        int sum = getSum(root.val);
        //System.out.println(" Sum " + sum);
        root.val = sum;
        toGst(root.right);
        
        return root;
    }
    
    public int getSum(int key){
        int i = mSortedList.indexOf(key);
        int sum = 0;
        for(; i < mSortedList.size(); i++){
            sum+= mSortedList.get(i);
        }
        return sum;
    }
    
    public void sortIncreasing(TreeNode root){
        if(root == null){
            return;
        }
        sortIncreasing(root.left);
        mSortedList.add(root.val);
        sortIncreasing(root.right);
    }

    /* https://leetcode.com/problems/group-shifted-strings/ 
       Input: ["abc", "bcd", "acef", "xyz", "az", "ba", "a", "z"]

    */
    public List<List<String>> groupStrings(String[] strings) {
        List<List<String>> result = new ArrayList<>();
        HashMap<String, List<String>> sortedmap = new HashMap<>();
        for(int i = 0; i < strings.length; i++){
            String s = strings[i];
            String key = getKey(s);
            List<String> list = sortedmap.getOrDefault(key, new ArrayList<>());
            list.add(s);
            sortedmap.put(key, list);
        }
        
        result.addAll(sortedmap.values());
        return result;
    }

    public String getKey(String str){
        char[] tmpArr = str.toCharArray();
        String key = "";
        for(int j = 1; j < tmpArr.length; ++j){
            int diff = tmpArr[j-1] - tmpArr[j];
            key += diff < 0 ? diff + 26 : diff;
            key += ",";
        }
        return key;
    }


    /* https://leetcode.com/problems/print-zero-even-odd/ */
    class ZeroEvenOdd {
        private int n;
        private int sem = 0;
        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 0; i < n; ++i){
                while(sem != 0)
                    wait();

                printNumber.accept(0);
                sem = i%2 == 0 ? 1 : 2;
                notifyAll();
            }
        }

        public synchronized void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2; i <= n; i += 2){
                while(sem != 2)
                    wait();

                printNumber.accept(i);
                sem = 0;
                notifyAll();
            }
        }

        public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i += 2){
                while(sem != 1)
                    wait();

                printNumber.accept(i);
                sem = 0;
                notifyAll();
            }
        }
    }

    /* https://leetcode.com/problems/print-in-order/ */
    class Foo {
        int flag;
        public Foo() {
            flag = 1;
        }

        public synchronized void first(Runnable printFirst) throws InterruptedException {
            while(flag != 1)
                wait();
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            flag = 2;
            notifyAll();
        }

        public synchronized void second(Runnable printSecond) throws InterruptedException {
            while(flag != 2)
                wait();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            flag = 3;
            notifyAll();
        }

        public synchronized void third(Runnable printThird) throws InterruptedException {
            while(flag != 3)
                wait();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            flag = 3;
            notifyAll();
        }
    }

    /* https://leetcode.com/problems/print-foobar-alternately/ */
    class FooBar {
        private int n;
        volatile boolean flag = true;

        public FooBar(int n) {
            this.n = n;
        }

        public synchronized void foo(Runnable printFoo) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while(!flag){
                    wait();
                }
                // printFoo.run() outputs "foo". Do not change or remove this line.
                printFoo.run();
                flag = false;
                notifyAll();
            }
        }

        public synchronized void bar(Runnable printBar) throws InterruptedException {
            for (int i = 0; i < n; i++) {
                while(flag) {
                    wait();
                }
                // printBar.run() outputs "bar". Do not change or remove this line.
                printBar.run();
                flag = true;
                notifyAll();
            }
        }
    }

    /* https://leetcode.com/problems/string-to-integer-atoi/ */
    public int myAtoi(String str) {
        //str = str.replaceAll("\\s+", "");
        System.out.println(str);
        int index = 0, sign = 1, total = 0;
        //1. Empty string
        if(str.length() == 0) return 0;

        //2. Remove Spaces
        while(index < str.length() && str.charAt(index) == ' ')
            index++;

        if(index == str.length()) return total;
        
        //3. Handle signs
        if(str.charAt(index) == '+' || str.charAt(index) == '-'){
            sign = str.charAt(index) == '+' ? 1 : -1;
            index ++;
        }

        //4. Convert number and avoid overflow
        while(index < str.length()){
            int digit = str.charAt(index) - '0';
            if(digit < 0 || digit > 9) break;

            //check if total will be overflow after 10 times and add digit
            if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
                return sign == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;

            total = 10 * total + digit;
            index ++;
        }
        return total * sign;
    }

    /* https://leetcode.com/problems/fizz-buzz-multithreaded/ */
class FizzBuzz {
    private int n;
    private volatile int i = 1;

    public FizzBuzz(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public synchronized void fizz(Runnable printFizz) throws InterruptedException {
        for(; i <= n ; ){
            if(i % 3 != 0 || i % 5 == 0){
                wait();
                continue;
            }
            
            //System.out.println(i + " fizz");
             printFizz.run();
             i += 1;
             notifyAll();
        }
    }

    // printBuzz.run() outputs "buzz".
    public synchronized void buzz(Runnable printBuzz) throws InterruptedException {
        for( ; i <= n ;){
            
            if(i % 5 != 0 || i % 3 == 0) {
                wait();
                continue;
            }
            
            //System.out.println(i + " buzz");
            printBuzz.run();
            i += 1;
            notifyAll();
            
        }
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public synchronized void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        for( ; i <= n ;){
            
            if(i % 15 != 0) {
                wait();
                continue;
            }
            
            //System.out.println(i + " fizzbuzz");
            printFizzBuzz.run();
            i += 1;
            notifyAll();
        }
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public synchronized void number(IntConsumer printNumber) throws InterruptedException {
        for(; i <= n ;){
            //System.out.println(i + " number");
            if(i % 3 == 0 || i % 5 == 0){
                wait();
                continue;
            }
            
            //System.out.println(i + " number");
            printNumber.accept(i);
            i += 1;
            notifyAll();
        }
    }
}

    /* https://leetcode.com/problems/wildcard-matching/ */
     public boolean isMatch(String s, String p) {
        //replace multiple * with one *
        //e.g a**b***c --> a*b*c

         int writeIndex = 0;
         boolean isFirst = true;
         char[] pattern = p.toCharArray();
         for ( int i = 0 ; i < pattern.length; i++) {
             if (pattern[i] == '*') {
                 if (isFirst) {
                     pattern[writeIndex++] = pattern[i];
                     isFirst = false;
                 }
             } else {
                 pattern[writeIndex++] = pattern[i];
                 isFirst = true;
             }
         }

        int m = s.length();
        p = new String(pattern);
        boolean[][] dp = new boolean[m+1][writeIndex+1];

        if(p.charAt(0) == '*'){
            dp[0][1] = true;
        }
        dp[0][0] = true;
        for(int i = 1; i < m; i++){
            for (int j = 1; j < dp[0].length ; j++ ) {
                if(p.charAt(j-1) == '?' || p.charAt(j-1) == s.charAt(i-1)){
                    dp[i][j] = dp[i-1][j-1];
                } else if(p.charAt(j-1) == '*'){
                    dp[i][j] = dp[i-1][j] || dp[i][j-1];
                }
            }
        }
        return dp[m][writeIndex];
     }

     /* https://leetcode.com/problems/valid-word-abbreviation/ */
     public boolean validWordAbbreviation(String word, String abbr) {
         return word.matches(abbr.replaceAll("[1-9]\\d*", ".{$0}"));
     }

    public boolean validWordAbbreviationI(String word, String abbr) {
        int len = 0;
        int number = 0;
        for(int i = 0; i < abbr.length(); i++){
            if(Character.isDigit(abbr.charAt(i))){
                if(abbr.charAt(i) == '0' && number == 0){
                    return false;
                }
                number = number * 10 + (abbr.charAt(i) - '0');
            }else{
                len += number;
                number = 0;
                len++;
            }
        }
        if(number > 0){
            len += number;
        }
        if(number == 0 && abbr.charAt(abbr.length() -1) != word.charAt(word.length() -1))
            return false;

        return len == word.length();
    }

    public int findShortestSubArrayI(int[] nums) {
        TreeMap<Integer, List<List<Integer>>> tMap = new TreeMap<>(Collections.reverseOrder());
        HashMap<Integer, Integer> hMap = new HashMap<>();
        HashMap<Integer, List<Integer>> idxMap = new HashMap<>();
        for(int i = 0; i < nums.length; i++){
            if(hMap.containsKey(nums[i])){
                List<Integer> list = idxMap.get(nums[i]);
                list.add(i);
                idxMap.put(nums[i], list);
                hMap.put(nums[i], hMap.get(nums[i]) + 1);
            }else{
                List<Integer> list = new ArrayList<>();
                list.add(i);
                idxMap.put(nums[i], list);
                hMap.put(nums[i], 1);
            }
        }

        for(Map.Entry<Integer, Integer> entry : hMap.entrySet()){
            if(tMap.containsKey(entry.getValue())){
                List<List<Integer>> list = tMap.get(entry.getValue());
                list.add(idxMap.get(entry.getKey()));
            }else {
                List<List<Integer>> list = new ArrayList<>();
                list.add(idxMap.get(entry.getKey()));
                tMap.put(entry.getValue(), list); // freq, list of indexes
            }
        }

        int min = Integer.MAX_VALUE;
        int max_freq = Integer.MIN_VALUE;
        for(Map.Entry<Integer, List<List<Integer>>> entry : tMap.entrySet()){
            int freq = entry.getKey();
            if(max_freq < freq){
                max_freq = freq;
                List<List<Integer>> idxList = tMap.get(entry.getKey());
                for (int i = 0; i < idxList.size(); i++) {
                    List<Integer> list = idxList.get(i);
                    min = Math.min(min, list.get(list.size() -1) - list.get(0));
                }
            }
        }
        min++;
        System.out.println("min = " + min);

        return min;
    }

    public int findShortestSubArray(int[] A) {
        HashMap<Integer, Integer> count = new HashMap<>();
        HashMap<Integer, Integer> first = new HashMap<>();

        int res = 0, degree = 0;

        for(int i = 0; i < A.length; i++){
            first.putIfAbsent(A[i], i);
            count.put(A[i], count.getOrDefault(A[i], 0) + 1);
            if(count.get(A[i]) > degree){
                degree = count.get(A[i]);
                res = i - first.get(A[i]) + 1;
            }else if(count.get(A[i]) == degree){
                res = Math.min(res, i - first.get(A[i]) + 1);
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/maximum-average-subarray-i/ */
    public double findMaxAverage(int[] nums, int k) {
        double max_avg = Integer.MIN_VALUE;
        int sum = 0;
        for(int i = 0; i < k; i++){
            sum += nums[i];
        }
        max_avg = Math.max(max_avg, (double) sum / k);
        
        for(int i = k; i < nums.length; i++){
            sum -= nums[ i - k ];
            sum += nums[i];
            double curr_avg = (double) sum / k;
            
            max_avg = Math.max(max_avg, (double) sum / k);
        }
        return max_avg;
    }

    /* https://leetcode.com/problems/maximum-average-subarray-ii/ */
    public double findMaxAverageII(int[] nums, int k) {
        int n = nums.length;
        int[] prefixSum = new int[nums.length + 1];

        Deque<Integer> dq = new ArrayDeque<>();
        for(int i = 0; i < nums.length; i++){
            prefixSum[i+1] = prefixSum[i] + nums[i];
        }

        double max_avg = Integer.MIN_VALUE;
        max_avg = (double) prefixSum[k] / k;

        for(int i = 0; i < k + 1; i++){
            dq.offer(i);
        }

        for(int i = k + 1 ; i < n + 1 ; i++){
            dq.addLast(i);

            while (!dq.isEmpty() && dq.peekLast() - dq.peekFirst() >= k) {
                double currSum = prefixSum[dq.peekLast()] - prefixSum[dq.peekFirst()];
                double currAvg = currSum / (dq.peekLast() - dq.peekFirst());
                if (max_avg <= currAvg) {
                    dq.pollFirst();
                    max_avg = currAvg;
                }
            }
        }
        return max_avg;
    }

    /* https://leetcode.com/problems/1-bit-and-2-bit-characters/ */
    public boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        
        if(bits[n-1] != 0) return false;
        
        if(n == 1 && bits[n-1] == 0) return true;
        
        int count = 0;
        for(int i = n-2; i >= 0 && bits[i] != 0 ; --i){
            count++;
        }
        return count % 2 == 0;
    }

    /* https://leetcode.com/problems/logger-rate-limiter/  */
    class Logger {

    /** Initialize your data structure here. */
    HashMap<String, Integer > map;
    public Logger() {
        map = new HashMap<>();
    }
    
    /** Returns true if the message should be printed in the given timestamp, otherwise returns false.
        If this method returns false, the message will not be printed.
        The timestamp is in seconds granularity. */
        public boolean shouldPrintMessage(int timestamp, String message) {
            if(map.containsKey(message)){
                int lasttime = map.get(message);
                if((timestamp - lasttime ) >= 10 ){
                    map.put(message, timestamp);
                    return true;
                }else{
                    return false;
                }
            }else{
                map.put(message, timestamp);
                return true;
            }
        }
    }

    class HitCounter {

    /** Initialize your data structure here. */
    Queue<Integer> q;
    int count = 0;
    public HitCounter() {
        q = new LinkedList<>();
    }
    
    /** Record a hit.
        @param timestamp - The current timestamp (in seconds granularity). */
    public void hit(int timestamp) {
        q.offer(timestamp);
    }
    
    /** Return the number of hits in the past 5 minutes.
        @param timestamp - The current timestamp (in seconds granularity). */
    public int getHits(int timestamp) {
        while(!q.isEmpty() && timestamp - q.peek() >= 300){
            q.poll();
        }
        return q.size();
    }
}

    /* https://leetcode.com/problems/jump-game-iv/ */
    public int minJumps(int[] arr) {
        int n = arr.length;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        HashSet<Integer> sets = new HashSet<>();

        for(int i = 0 ; i < n; i++){
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }

        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        int dist[] = new int[n];

        Arrays.fill(dist, -1);
        dist[0] = 0;

        while(!q.isEmpty()){
            int curr = q.poll();
            if(curr == n - 1){
                return dist[curr];
            }
            if(curr + 1 < n && dist[curr + 1] < 0){
                dist[curr + 1] = dist[curr] + 1;
                q.offer(curr + 1);
            }
            if(curr - 1 >= 0 && dist[curr - 1] < 0){
                dist[curr - 1] = dist[curr] + 1;
                q.offer(curr - 1);
            }
            if(!sets.contains(arr[curr])){
                sets.add(curr);
                List<Integer> list = map.get(arr[curr]);
                for(int idx : list){
                    if(dist[idx] < 0){
                        dist[idx] = dist[curr] + 1;
                        q.offer(idx);
                    }
                }
            }
        }
        return -1;
    }

    /* https://leetcode.com/problems/number-of-steps-to-reduce-a-number-to-zero/ */
    public int numberOfSteps (int num) {
        int steps = 0;
        while(num != 0){
            if(num % 2 == 0){
                num = num /2;
            }else{
                num = num -1;
            }
            steps++;
        }
        return steps;
    }

    /* https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/ */
    public int numOfSubarrays(int[] nums, int k, int t) {
        int sum = 0;
        int count = 0;
        for(int i = 0; i < k; i++){
            sum += nums[i];
        }
        double avg = (double) (sum / k);
        if(avg >= t){
            count++;
        }
        
        for(int i = k; i < nums.length; i++){
            sum -= nums[ i - k ];
            sum += nums[i];
            double curr_avg = (double) sum / k;
            if(curr_avg >= t){
                count++;
            }
        }
        return count;
    }

    /* https://leetcode.com/problems/angle-between-hands-of-a-clock/ */
    public double angleClock(int hour, int minutes) {
        return calcAngle(Double.valueOf(hour), Double.valueOf(minutes));
    }
    
    double calcAngle(double h, double m) { 
        // validate the input 
        if (h <0 || m < 0 || h >12 || m > 60) 
            System.out.println("Wrong input"); 
  
        if (h == 12) 
            h = 0; 
        if (m == 60)  
            m = 0; 
  
        // Calculate the angles moved by hour and minute hands 
        // with reference to 12:00
        double hour_angle = (double)(0.5 * (h*60 + m)); 
        double minute_angle = (double)(6*m); 
  
        // Find the difference between two angles
        double angle = Math.abs(hour_angle - minute_angle); 
  
        // smaller angle of two possible angles 
        angle = Math.min(360-angle, angle); 
  
        return angle; 
    } 



    public static void main(String[] args) {
        FebruaryW1 w1 = new FebruaryW1();
        //int val = w1.evaluate(new StringBuilder("-45+45+15+10"), 0);
        //int val = w1.shortestSubarray(new int[]{17,85,93,-45,-21}, 150);

        //int val = w1.minSetSize(new int[]{2,28,92,30,100,52,28,48,91,27,66,19,11,53,91,95,74,51,65,65,96,81,
        //        21,55,98,3,2,89,99,57,78,34,50,2,57,76,23,90,89,36,53,22,73,59,95,45});
        //w1.isMatch("axb","a*****b****c");
        //w1.findShortestSubArray(new int[]{1,2,2,3,1});
        //w1.findMaxAverageII(new int[]{1,3,1,4,2}, 4);
        //w1.isOneBitCharacter(new int[]{1,0,1,1,0});
        w1.minJumps(new int[]{100,-23,-23,404,100,23,23,23,3,404});

        //System.out.println("val = " + val);
    }


    /* https://leetcode.com/problems/basic-calculator-ii/
     * Input: "3 + 2 * 2"
       Output: 7
   **/
    public int calculateII(String s) {
        return 0;
    }

    /* https://leetcode.com/problems/range-sum-query-2d-mutable/ */

    /* https://leetcode.com/problems/jump-game-v/ */

    /* https://leetcode.com/problems/binary-subarrays-with-sum/ */

    /*
        https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/
        https://leetcode.com/discuss/interview-question/algorithms/443621/google-phone-subtract-two-integers-represented-as-array
    */

    /*
      System Design: Resume: https://github.com/donnemartin/system-design-primer#availability-patterns
    */

    /*  https://leetcode.com/problems/minimum-moves-to-move-a-box-to-their-target-location/
     */


    /*
        https://leetcode.com/discuss/career/216554/From-0-to-clearing-UberAppleAmazonLinkedInGoogle
        https://leetcode.com/discuss/career/229177/My-System-Design-Template
    */


    /*https://leetcode.com/problems/number-of-islands/ Solve with Union-find now */

    /*
        https://leetcode.com/problems/snakes-and-ladders/
        https://www.codechef.com/problems/MM1801
        https://www.codechef.com/CODER11/problems/SLADDERS
        https://www.hackerrank.com/challenges/the-quickest-way-up/problem
    */
    public int snakesAndLadders(int[][] board) {
        return -1;
    }


    /*
        https://leetcode.com/problems/serialize-and-deserialize-bst/
        https://leetcode.com/problems/prefix-and-suffix-search/
        https://leetcode.com/problems/expression-add-operators/
        https://leetcode.com/problems/split-array-largest-sum/
        https://leetcode.com/problems/unique-paths-iii/
        https://medium.com/@dimko1/alien-dictionary-6cf2da24bf3c
        https://www.geeksforgeeks.org/given-sorted-dictionary-find-precedence-characters/
        https://github.com/jiemingxin/LeetCode/blob/master/tutorials/graph/AlienDict.java
        https://leetcode.com/problems/global-and-local-inversions/
        https://leetcode.com/problems/convert-sorted-list-to-binary-search-tree/
        https://leetcode.com/problems/remove-invalid-parentheses/
        https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/

        https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
        https://www.programcreek.com/2013/02/longest-substring-which-contains-2-unique-characters/
        https://leetcode.com/discuss/career/216554/From-0-to-clearing-UberAppleAmazonLinkedInGoogle


     /**
     * https://leetcode.com/problems/non-overlapping-intervals/
     https://leetcode.com/discuss/interview-experience/456432/nda-google-l3-bangalore-dec-2019-offer
     */
    /**
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/
     * https://leetcode.com/problems/subarray-product-less-than-k/
     * https://leetcode.com/problems/permutations-ii/
     * https://www.geeksforgeeks.org/find-the-number-of-islands-set-2-using-disjoint-set/
     * https://www.geeksforgeeks.org/find-number-of-islands/
     * https://leetcode.com/problems/number-of-islands/discuss/56588/Standard-BFS-java-solution
     * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/
     * https://leetcode.com/problems/design-file-system/
     * https://leetcode.com/discuss/interview-question/236898
     * (Google | Check if a node exists in a complete tree)
     * https://leetcode.com/problems/decode-string/
     * https://leetcode.com/discuss/interview-experience/365405/Google-or-L3-or-Seattle-or-Aug-2019-Reject
     * https://leetcode.com/problems/house-robber-ii/
     * */

    public int eraseOverlapIntervals(int[][] intervals) {
        return -1;
    }


    /**
     * 508. Most Frequent Subtree Sum
     * https://www.programcreek.com/2014/01/leetcode-most-frequent-subtree-sum-java//
     * https://leetcode.com/problems/most-frequent-subtree-sum/
     * **/
    public int[] findFrequentTreeSum(TreeNode root) {
        return new int[]{1};
    }

}
