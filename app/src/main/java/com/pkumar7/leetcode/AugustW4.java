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
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeSet;

public class AugustW4 {

    /** https://leetcode.com/problems/invalid-transactions/
     * 1169. Invalid Transactions
     * */
    class Transaction {
        String name;
        int time;
        String city;
        String trans;

        public Transaction(String name, int time, String city, String trans) {
            this.name = name;
            this.time = time;
            this.city = city;
            this.trans = trans;
        }
    }
    public List<String> invalidTransactions(String[] transactions) {
        Set<String> res = new HashSet<>();

        Map<String, List<Transaction>> map = new HashMap<>();

        for (String trans : transactions) {
            String []curr_tra = trans.split(",");
            String name = curr_tra[0];
            int time = Integer.parseInt(curr_tra[1]);
            int amount = Integer.parseInt(curr_tra[2]);
            String city = curr_tra[3];

            if(amount > 1000){
                res.add(trans);
            }
            List<Transaction> otherTrans = map.get(name);

            if(otherTrans == null){
                otherTrans = new ArrayList<>();
                otherTrans.add(new Transaction(name, time, city, trans));
                map.put(name, otherTrans);
            }else{
                for (Transaction other : otherTrans) {
                    if(!other.city.equals(city) && Math.abs(other.time - time) <= 60){
                        res.add(other.trans);
                        res.add(trans);
                    }
                }

                otherTrans.add(new Transaction(name, time, city, trans));
            }
        }
        return new ArrayList<>(res);
    }


    ListNode res = null;
    ListNode curr = null;
    public ListNode removeZeroSumSublists(ListNode head) {
        res = new ListNode(0);
        removeSubElements(head);
        return res.next;
    }

    void removeSubElements(ListNode head) {
        ListNode start = head;
        ListNode end;

        while (start != null) {
            boolean mod = false;
            int sum = 0;
            end = start;
            while (end != null) {
                sum += end.val;
                if (sum == 0) {
                    start = end;
                    mod = true;
                    break;
                }
                end = end.next;
            }
            if (!mod) {
                if (curr == null) {
                    res.next = new ListNode(start.val);
                    curr = res.next;
                }else{
                    ListNode newNode = new ListNode(start.val);
                    curr.next = newNode;
                    curr = curr.next;
                }
            }
            start = start.next;
        }
    }

    /** Important
     * https://leetcode.com/problems/largest-rectangle-in-histogram/
     * 84. Largest Rectangle in Histogram
     * **/
    public int largestRectangleArea(int[] heights) {
        int i;
        int area = 0;
        int maxArea = 0;
        Stack<Integer> mStack = new Stack<>();

        for (i = 0; i < heights.length;) {
            if(mStack.isEmpty() || heights[mStack.peek()] <= heights[i]){
                mStack.push(i++);
            }else{
                int top = mStack.pop();
                if(mStack.isEmpty()){
                    area = heights[top] * i;
                }else{
                    area = heights[top] * (i - mStack.peek() - 1);
                }
                maxArea = Math.max(area, maxArea);
            }
        }

        while (!mStack.isEmpty()){
            int top = mStack.pop();
            if(mStack.isEmpty()){
                area = heights[top] * i;
            }else{
                area = heights[top] * (i - mStack.peek() - 1);
            }
            maxArea = Math.max(area, maxArea);
        }
        return maxArea;
    }

    /**
     * Important
     * 85. Maximal Rectangle
     * https://leetcode.com/problems/maximal-rectangle/
     *
     * Given a 2D binary matrix filled with 0's and 1's,
     * find the largest rectangle containing only 1's and return its area.
     *
     * */
    public int maximalRectangle(char[][] matrix) {
        if(matrix.length == 0){
            return 0;
        }
        int[] temp = new int[matrix[0].length];
        int maxArea = 0;
        int area = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < temp.length; j++) {
                if(matrix[i][j] == '0'){
                    temp[j] = 0;
                }else{
                    temp[j] += Integer.parseInt(""+matrix[i][j]);
                }
            }
            area = largestRectangleArea(temp);
            maxArea = Math.max(area, maxArea);
        }
        return maxArea;
    }


    /** 221. Maximal Square
     * https://leetcode.com/problems/maximal-square/
     *
     * Input:
     *
     * 1 0 1 0 0
     * 1 0 1 1 1
     * 1 1 1 1 1
     * 1 0 0 1 0
     *
     * Output: 4
     *
     * */
    public int maximalSquareDP(char[][] matrix) {
        if(matrix.length == 0) return 0;

        int rows = matrix.length;
        int cols = matrix[0].length;

        int[][] dp = new int[rows + 1 ][cols + 1];
        for (int i = 0; i < rows; i++) {
            dp[i][0] = 0;
        }
        for (int i = 0; i < cols; i++) {
            dp[0][i] = 0;
        }
        for (int i = 1; i < rows + 1; i++) {
            for (int j = 1; j < cols + 1; j++) {
                if(matrix[i-1][j-1] == '1') {
                    dp[i][j] = Math.min(dp[i][j - 1], Math.min(dp[i - 1][j], dp[i - 1][j - 1])) + 1;
                }else{
                    dp[i][j] = 0;
                }
            }
        }
        int max_area = 0;
        for (int i = 0; i < rows + 1; i++) {
            for (int j = 0; j < cols + 1; j++) {
                max_area = Math.max(max_area , dp[i][j]);
            }
        }
        return max_area * max_area;
    }

    public int maximalSquareBruteForce(char[][] matrix) {
        if(matrix.length == 0){
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int max_len = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if(matrix[i][j] == '1'){
                    int curr_len = 1;
                    boolean flag = true;
                    while (curr_len + i < rows && curr_len + j< cols && flag){
                        for (int k = j; k <= curr_len+j; k++) {
                            if(matrix[i + curr_len][k] == '0'){
                                flag = false;
                                break;
                            }
                        }
                        for (int k = i; k <= curr_len+i ; k++) {
                            if(matrix[k][curr_len + j] == '0'){
                                flag = false;
                                break;
                            }
                        }
                        if(flag){
                            curr_len++;
                        }
                    }
                    max_len = Math.max(curr_len, max_len);
                }
            }
        }
        return max_len * max_len;
    }

    /**
     * 88. Merge Sorted Array
     * https://leetcode.com/problems/merge-sorted-array/
     *
     * Input:
     * nums1 = [1,2,3,0,0,0], m = 3
     * nums2 = [2,5,6],       n = 3
     *
     * Output: [1,2,2,3,5,6]
     *
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;
        while (i >= 0 && j >= 0){
            nums1[k--] = nums1[i] > nums2[j] ? nums1[i--] : nums2[j--];
        }
        while (j >= 0){
            nums1[k--] = nums2[j--];
        }
       /* int i = m;
        int j = 0;
        while (i < m+n ){
            nums1[i] = nums2[j];
            j++;
            i++;
        }

        quickSort(nums1, 0, nums1.length - 1 );*/
    }
    public static void main(String args[]){
        AugustW4 w4 = new AugustW4();
        int[] arr =  new int[]{3,5,1,4,7,2,6};
        w4.quickSort(arr, 0, arr.length -1);
        System.out.println("arr = " + Arrays.toString(arr));
    }

    public void quickSort(int[] arr, int low, int high){
        if(low < high){
            int pivot = hoarePartition(arr, low, high);

            quickSort(arr, pivot + 1, high);
            quickSort(arr, low, pivot - 1);
        }
    }

    public int hoarePartition(int[] a, int low, int high){
        int pivot = a[low];
        int i = low;
        int j = high + 1;
        while(true){
            while(i < high && a[++i] < pivot);
            while(j > low && pivot < a[--j]);
            if(i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, low, j);
        return j;
    }

    public void swap(int[] a , int i, int j ){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /** 100. Same Tree
     * https://leetcode.com/problems/same-tree/
     * */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if(p == null && q == null){
            return true;
        }
        if(p == null || q == null){
            return false;
        }
        if(p.val != q.val){
            return false;
        }
        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
    /**
     * https://www.youtube.com/watch?v=yCQN096CwWM
     * https://leetcode.com/problems/max-sum-of-rectangle-no-larger-than-k/
     *
     * Space:  O(row)
     * Time :  O(col x col x row)
     * */
    public int maxSumSubmatrix(int[][] matrix, int max_sum_allowed) {
        if(matrix.length == 0){
            return 0;
        }

        int dp[] = new int[matrix.length];
        int rows = matrix.length;
        int cols = matrix[0].length;

        int left = 0, right = 0, max_curr = Integer.MIN_VALUE;
        for (left = 0; left < cols; left++) {
            Arrays.fill(dp, 0);
            for (right = left; right < cols; right++) {
                for (int i = 0; i < rows; i++) {
                    dp[i] += matrix[i][right];
                }
                max_curr = Math.max(max_curr,getMaxSumSubArrayKadanes(dp, max_sum_allowed));

            }
        }
        return max_curr;
    }

    /**
     * https://stackoverflow.com/questions/39084147/largest-sum-of-contiguous-subarray-no-larger-than-k
     * Max Sub-array sum no larger than k
     * */
    public int getMaxSumSubArrayKadanes(int[] nums, int allowed){
        int max = Integer.MIN_VALUE;
        int sum = 0;
        TreeSet<Integer> s = new TreeSet();
        s.add(0);

        for(int i = 0;i < nums.length; i ++){
            int t = sum + nums[i];
            sum = t;
            Integer gap = s.ceiling(sum - allowed);
            if(gap != null) max = Math.max(max, sum - gap);
            s.add(t);
        }

        return max;
    }

    /**
     * https://leetcode.com/problems/symmetric-tree/
     *     1
     *    / \
     *   2   2
     *  / \ / \
     * 3  4 4  3
     * **/
    public boolean isSymmetric(TreeNode root) {
        if(root == null){
            return true;
        }
        TreeNode left = root.left;
        TreeNode right = root.right;
        return isMirror(left, right);
    }

    public boolean isMirror(TreeNode left, TreeNode right){
        if(left == null || right == null){
            return left == right;
        }
        if(left.val != right.val){
            return false;
        }
        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }

    /**
     * https://leetcode.com/problems/implement-queue-using-stacks/
     * */
    class MyQueue {
        /** Initialize your data structure here. */
        Stack<Integer> s1;
        Stack<Integer> s2;
        int front = -1;
        public MyQueue() {
            s1 = new Stack<>();
            s2 = new Stack<>();
        }

        /** Push element x to the back of queue. */
        public void push(int x) {
            if(s1.isEmpty()){
                front = x;
            }
            while (!s1.isEmpty()){
                s2.push(s1.pop());
            }
            s2.push(x);
            while (!s2.isEmpty()){
                s1.push(s2.pop());
            }
        }

        /** Removes the element from in front of queue and returns that element. */
        public int pop() {
            int removed = s1.pop();
            if(!s1.isEmpty()) {
                front = s1.peek();
            }
            return removed;
        }

        /** Get the front element. */
        public int peek() {
            return front;
        }

        /** Returns whether the queue is empty. */
        public boolean empty() {
            return s1.isEmpty();
        }
    }

    /**
     * https://leetcode.com/problems/implement-stack-using-queues/
     * **/
    class MyStack {
        Queue<Integer> q1;
        Queue<Integer> q2;
        /** Initialize your data structure here. */
        int top = -1;
        public MyStack() {
            q1 = new LinkedList<>();
            q2 = new LinkedList<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            q1.offer(x);
            top = x;
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            while (!q1.isEmpty() && q1.size() > 1){
                top = q1.poll();
                q2.offer(top);
            }
            int removed = -1;
            if(!q1.isEmpty())
                removed = q1.poll();

            Queue<Integer> temp = new LinkedList<>();
            temp = q1;
            q1 = q2;
            q2 = temp;
            return removed;
        }

        /** Get the top element. */
        public int top() {
            return top;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return q1.isEmpty();
        }
    }

    /**
     * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
     * Input: [3,4,5,1,2]
     * Output: 1
     * */
    public int findMin(int[] nums) {
        //Use binary search
        return binarySearch(nums, 0, nums.length - 1);
    }
    public int binarySearch(int[] nums, int low, int high){
        // This condition is needed to handle the case when array
        // is not rotated at all
        if (high < low) {
            return nums[0];
        }
        // If there is only one element left
        if (high == low) {
            return nums[high];
        }
        int mid = low + ((high - low) / 2);
        if (mid > low && nums[mid] < nums[mid - 1]) {
            return nums[mid];
        }
        if (mid < high && nums[mid] > nums[mid + 1]) {
            return nums[mid + 1];
        }

        if(nums[mid] > nums[high])
            return binarySearch(nums, mid +1, high);
        else
            return binarySearch(nums, low, mid -1);
    }

    /**
     * https://leetcode.com/problems/search-in-rotated-sorted-array/
     * */
    public int search(int[] nums, int target) {
       int low = 0;
       int high = nums.length - 1;

       while (low <= high){
           int mid = low + (high - low) / 2;

           int num = nums[mid];

           if(nums[0] > nums[mid] && nums[0] > target){
               num = nums[mid];
           }else {
               num = nums[0] > target ? Integer.MIN_VALUE : Integer.MAX_VALUE;
           }
           if(num < target){
               low = mid + 1;
           }else if(num > target){
               high = mid - 1;
           }else {
               return mid;
           }
       }
       return -1;
    }

    /**
     * https://leetcode.com/problems/isomorphic-strings/
     *
     * Input: s = "egg", t = "add"
     * Output: true
     *
     * Input: s = "foo", t = "bar"
     * Output: false
     *
     * Input: s = "paper", t = "title"
     * Output: true
     *
     * "ab"
     * "aa"
     *
     * */
    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        char [] a = s.toCharArray();
        char [] b = t.toCharArray();

        if(a.length != b.length){
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            char char_a = a[i];
            char char_b = b[i];
            if(map.containsKey(char_a)){
                if(map.get(char_a).equals(char_b))
                    continue;
                else
                    return false;
            }else{
                if(!map.containsValue(char_b)){
                    map.put(char_a, char_b);
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * https://leetcode.com/problems/word-pattern/
     *
     * Input: pattern = "abba", str = "dog cat cat dog"
     * Output: true
     *
     * Input:pattern = "abba", str = "dog cat cat fish"
     * Output: false
     *
     * Input: pattern = "aaaa", str = "dog cat cat dog"
     * Output: false
     *
     * **/
    public boolean wordPattern(String pattern, String str) {
        Map<Character, String> mMap = new HashMap<>();
        String[] curr_pattern = str.split(" ");

        String[] words = str.split(" ");
        if (words.length != pattern.length())
            return false;
        Map index = new HashMap();
        for (Integer i=0; i<words.length; ++i)
            if (!Objects.equals(index.put(pattern.charAt(i), i),
                    index.put(words[i], i)))
                return false;
        return true;

        /*if(pattern.length() != curr_pattern.length){
            return false;
        }

        for (int i = 0; i < pattern.length(); i++) {
            char curr_char = pattern.charAt(i);
            if (mMap.containsKey(curr_char)) {
                String val = mMap.get(curr_char);
                if (!val.equals(curr_pattern[i])) {
                    return false;
                }
            }else{
                if(!mMap.containsValue(curr_pattern[i])) {
                    mMap.put(curr_char, curr_pattern[i]);
                }else{
                    return false;
                }
            }
        }
        return true;*/
    }

    /** 26. Remove Duplicates from Sorted Array
     * https://leetcode.com/problems/remove-duplicates-from-sorted-array/
     *
     * Given nums = [0,0,1,1,1,2,2,3,3,4],
     *
     * **/
    public int removeDuplicates(int[] nums) {
       /* int length = nums.length;
        int duplicates = 0;
        for (int i = 0; i < length - 1; i++) {
            if(nums[i] == nums[i+1]){
                duplicates++;
            }
        }

        int i = 0;
        int j = 0;
        while (j++  < length){
            while (nums[j] <= nums[i])
                j++;

            i++;
            int temp = nums[j];
            nums[j] = nums[i];
            nums[i] = temp;
        }

        return length - duplicates;*/

        int i = 0;
        for (int n : nums) {
            if(i == 0 || n > nums[i-1])
                nums[i++] = n;
        }
        return i;
    }

    /** 80. Remove Duplicates from Sorted Array II
     * https://leetcode.com/problems/remove-duplicates-from-sorted-array-ii/
     *  Given nums = [0,0,1,1,1,1,2,3,3],
     *  Op:           0,0,1,1,2,3,3
     * op: 5
     * */
    public int removeDuplicatesMedium(int[] nums) {
        int i = 0;
        for (int n : nums) {
            if(i < 2 || n > nums[i - 2]){
                nums[i++] = n;
            }
        }
        return i;
    }

    /** 122. Best Time to Buy and Sell Stock II
     * https://leetcode.com/problems/best-time-to-buy-and-sell-stock-ii/
     *
     * Input: [7,1,5,3,6,4]
     * Output: 7
     *
     * Input: [1,2,3,4,5]
     * Output: 4
     *
     * Input: [7,6,4,3,1]
     * Output: 0
     *
     * **/
    public int maxProfit(int[] prices) {
        int []profit = new int[prices.length];
        int max_profit = 0;
        for (int i = 0; i < prices.length -1; i++) {
            if(prices[i + 1] > prices[i]){
                max_profit += prices[i + 1] - prices[i];
            }
        }
        System.out.println(max_profit);
        return max_profit ;
    }

    /**
     * https://leetcode.com/problems/pascals-triangle/
     *
     * Input: 5
     * Output:
     * [
     *      [1],
     *     [1,1],
     *    [1,2,1],
     *   [1,3,3,1],
     *  [1,4,6,4,1]
     * ]
     *
     * **/
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> first_list = new ArrayList<>();

        first_list.add(1);
        result.add(first_list);

        if(numRows == 1){
            return result;
        }
        for (int i = 1; i < numRows; i++) {

            List<Integer> prev_list = result.get(i-1);

            List<Integer> curr_list = new ArrayList<>();
            curr_list.add(1);

            for (int j = 0; j < prev_list.size() - 1 ; j++) {
                curr_list.add(prev_list.get(j) + prev_list.get(j+1));
            }
            curr_list.add(1);

            result.add(curr_list);
        }
        return result;
    }

    /** 66. Plus One
     * https://leetcode.com/problems/plus-one/
     * Input: [1,2,3]
     * Output: [1,2,4]
     * Explanation: The array represents the integer 123.
     *
     * [0,0,7,9]
     * [0,0,8,0]
     *
     * [9,9,9,9,9]
     * [1,0,0,0,0,0]
     *
     *
     * */
    public int[] plusOne(int[] digits) {
        int n = digits.length;
        int []result_arr = new int[digits.length + 1];

        int carry = 0;
        for (int i = n - 1; i >= 0; i--) {
            int sum = 0;
            if(i == n -1){
                sum = digits[i] + 1;
            }else{
                sum = digits[i] + carry;
            }
            if(sum > 9){
                carry = sum / 9;
                result_arr[i] = 0;
            }else{
                result_arr[i] = sum;
                carry = 0;
            }
        }
        if(carry > 0){
            result_arr[0] = carry;
            return result_arr;
        }
        return Arrays.copyOfRange(result_arr,0,result_arr.length -1);
    }

    /**
     * Important
     * 43. Multiply Strings
     *
     * https://leetcode.com/problems/multiply-strings/
     *
     * Input: num1 = "2", num2 = "3"
     * Output: "6"
     *
     * Input: num1 = "123", num2 = "456"
     *
     *  123
     * x456
     * ------
     *     738
     *    615x
     *   492xx
     * -------
     *   56088
     *
     * Output: "56088"
     *
     * */
    public String multiply(String num1, String num2) {
        int n = num1.length();
        int m = num2.length();

        int[] pos = new int[n + m];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = n -1; j >= 0; j--) {
                int mul = (num1.charAt(i) - '0') * (num2.charAt(j) - '0');
                int p1 = i + j, p2 =  i + j + 1;
                int sum = mul + pos[p2];

                pos[p1] = sum / 10;
                pos[p2] = sum % 10;

                printArr(pos);
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int p : pos) {
            if(!(stringBuilder.length() == 0 && p == 0)){
                stringBuilder.append(p);
            }
        }
        return stringBuilder.length() == 0 ? "0" : stringBuilder.toString();
    }

    public void printArr(int[] arr){
        System.out.println();
        for (int ele : arr) {
            System.out.print(ele);
        }
    }

    /**
     * 415. Add Strings
     * https://leetcode.com/problems/add-strings/
     * **/
    public String addStrings(String num1, String num2) {

        if(num1.length() < num2.length()){
            String temp = num1;
            num1 = num2;
            num2 = temp;
        }
        int n = num1.length();
        int m = num2.length();

        int diff = n - m;

        StringBuilder num2builder = new StringBuilder();

        while (diff > 0){
            num2builder.append(0);
            diff--;
        }
        num2builder.append(num2);
        num2 = num2builder.toString();

        int carry = 0;
        StringBuilder builder = new StringBuilder();

        System.out.println(num1.length());
        System.out.println(num2.length());

        for (int i = n - 1; i >= 0; i--) {

            int sum = (num1.charAt(i) - '0') + (num2.charAt(i) - '0');
            sum += carry;

            carry = sum / 10;
            int mod = sum % 10;
            builder.append(mod);
        }
        if(carry > 0){
            builder.append(carry);
        }
        return builder.reverse().toString();
    }

    /**
     * 989. Add to Array-Form of Integer
     * https://leetcode.com/problems/add-to-array-form-of-integer/
     *
     * Input: A = [2,7,4], K = 181
     * Output: [4,5,5]
     * Explanation: 274 + 181 = 455
     *
     * **/
    public List<Integer> addToArrayForm(int[] A, int K) {
        String num1 = String.valueOf(K);
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < A.length; i++) {
            builder.append(A[i]);
        }

        String sum = addStrings(builder.toString(), num1);
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < sum.length(); i++) {
            res.add(sum.charAt(i) - '0');
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/pascals-triangle-ii/
     *
     * 119. Pascal's Triangle II
     *
     * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.
     *
     * Note that the row index starts from 0.
     *
     * **/
    public List<Integer> getRow(int rowIndex) {
       List<List<Integer>> res = generate(34);
       return res.get(rowIndex);
    }

}
