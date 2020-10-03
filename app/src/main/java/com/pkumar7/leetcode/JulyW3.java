package com.pkumar7.leetcode;

import com.pkumar7.datastructures.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

/*
* Created on 19/July/2020
* */
class JulyW3 {

    public static void main(String[] args) {
        JulyW3 curr = new JulyW3();
        int[][] edges = new int[][]{{0,1}, {1,2}, {0,3}};
        curr.maxNumOfSubstrings("adefaddaccc");
    }

    /* 
    LC : 1019 Next Greater Node In Linked List
    Similar : https://leetcode.com/problems/next-greater-element-ii/
    https://leetcode.com/problems/next-greater-node-in-linked-list/ 
    */
    public int[] nextLargerNodes(ListNode head) {
        ArrayList<Integer> list = new ArrayList<>();
        while(head != null){
            list.add(head.val);
            head = head.next;
        }
        
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            while(!stack.isEmpty() && list.get(stack.peek()) < list.get(i)) {
                int idx = stack.pop();
                ans[idx] = list.get(i);
            }
            stack.push(i);
        }
        return ans;
    }

    /*
    LC : 1133. Largest Unique Number
    https://leetcode.com/problems/largest-unique-number/ */
    public int largestUniqueNumber(int[] A) {
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        for(int a : A){
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            if(entry.getValue() == 1){
                return entry.getKey();
            }
        }
        return -1;
    }

    /*
    LC : 1381
    https://leetcode.com/problems/design-a-stack-with-increment-operation/
    */
    class CustomStack {
        int[] inc;
        Stack<Integer> stack;
        final int n;
        public CustomStack(int n) {
            inc = new int[n];
            stack = new Stack<>();
            this.n = n;
        }

        public void push(int x) {
            if(stack.size() >= n) {
                return;
            }
            stack.push(x);
        }

        public int pop() {
            int i = stack.size() - 1;
            if(i < 0) return -1;
            if(i > 0) {
                inc[i-1] += inc[i];
            }
            int res = stack.pop() + inc[i];
            inc[i] = 0;
            return res;
        }

        public void increment(int k, int val) {
            int i = Math.min(stack.size(), k) - 1;
            if(i >= 0) inc[i] += val;
        }
    }

    /* 
    LC : 346
    https://leetcode.com/problems/moving-average-from-data-stream/
    */
    class MovingAverage {

        /** Initialize your data structure here. */
        final int size;
        int count = 0;
        Queue<Integer> list;
        public MovingAverage(int size) {
            this.size = size;
            list = new LinkedList<>();
        }
        
        int sum = 0;
        public double next(int val) {
            if(count != size) {
                ++count;
                sum += val;
                list.add(val);
            }else{
                sum -= list.poll();
                list.add(val);
                sum += val;
            }
            return (double)sum / count;
        }
    }

    /* 
    LC : 714. Best Time to Buy and Sell Stock with Transaction Fee
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-transaction-fee/
    Revisit
    */
    public int maxProfit(int[] prices, int fee) {
        return maxProfitI(prices, fee);
    }
    
    public int maxProfitI(int[] prices, int fee ){
        int n = prices.length;
        int profit = 0;
        
        int cash = 0; // max profit when stock is not held
        int hold = -prices[0]; // max profit when stock is held
        for(int i = 1; i < n; i++) {
            /* If I'm holding the stock after today, either I decided to continue holding the stock from yesterday 
            or that I held no stock */
            hold = Math.max(hold, cash - prices[i]); //continue holding the share from yesterday or buy the stock i

            /* If I'm not holding the stock after today, either I did not hold from yesterday
            or I had stock from yeterday and decided to sell it today */
            cash = Math.max(cash, hold + prices[i] - fee); 
        }
        return cash;
    }

    /*
      LC : 1520. Maximum Number of Non-Overlapping Substrings
      https://leetcode.com/problems/maximum-number-of-non-overlapping-substrings/ */
    public List<String> maxNumOfSubstrings(String s) {
        int LEN = 26;
        int n = s.length();
        int[] l = new int[LEN];
        int[] r = new int[LEN];
        Arrays.fill(l, n);
        for(int i = 0; i < s.length(); i++){
            int idx = s.charAt(i) - 'a';
            l[idx] = Math.min(l[idx], i);
            r[idx] = i;
        }

        List<String> res = new ArrayList<>();
        int right = -1;
        for(int i = 0; i < n; i++){
            if(i == l[s.charAt(i) - 'a']){
                int new_right = checkSubs(s, i, l, r);
                if(new_right != -1){
                    if(right < i){
                        res.add("");
                    }
                    right = new_right;
                    res.set(res.size() - 1, s.substring(i, right + 1) );
                }
            }
        }
        return res;
    }

    public int checkSubs(String s, int i, int[] l, int[] r){
        int right = r[s.charAt(i) - 'a'];

        for(int j = i; j <= right; j++){
            int idx = s.charAt(j) - 'a';
            if(l[idx] < i){
                return -1;
            }
            right = Math.max(right, r[s.charAt(j) - 'a']);
        }
        return right;
    }


    /* 
    86. Partition List
    https://leetcode.com/problems/partition-list/ */
    public ListNode partition(ListNode head, int x) {
        ListNode smallHead = new ListNode(0);
        ListNode biggerHead = new ListNode(0);
        ListNode smaller = smallHead;
        ListNode bigger = biggerHead;
        while(head != null){
            if(head.val < x){
                smaller.next = head;
                smaller = smaller.next;
            }else{
                bigger.next = head;
                bigger = bigger.next;
            }
            head = head.next;
        }
        smaller.next = biggerHead.next;
        bigger.next = null;
        return smallHead.next;
    }
    /* 
    670. Maximum Swap
    https://leetcode.com/problems/maximum-swap/
    */
    public int maximumSwap(int num) {
        String numStr = String.valueOf(num);
        int n = numStr.length();
        int maxIdx = n - 1;
        int left = -1;
        int right = -1;

        char[] a = numStr.toCharArray();

        for(int i = n - 1; i >= 0; i--){
            if(a[maxIdx] == a[i]) continue;
            if(a[maxIdx] < a[i]){
                maxIdx = i;
            }else{
                left = i;
                right = maxIdx;
            }
        }
        if(left == -1) return num;

        char temp = a[left];
        a[left] = a[right];
        a[right] = temp;

        return Integer.parseInt(new String(a));
    }

    public int maximumSwap1(int num){
        int[] bucket = new int[10];
        char[] arr = Integer.toString(num).toCharArray();
        int n = arr.length;
        for(int i = n - 1; i >= 0; i--){
            bucket[arr[i] - '0'] = i;
        }

        for(int i = 0; i < arr.length; i++){
            for(int j = bucket.length - 1; j > (arr[i] - '0'); j--){
                if( (arr[i] - '0') > j && i < bucket[j]){
                    char temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                    return Integer.parseInt(new String(arr));
                }
            }
        }
        return num;
    }



    /*
    309. Best Time to Buy and Sell Stock with Cooldown
    https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/
    */
    public int maxProfit(int[] prices) {
        int held = Integer.MIN_VALUE;
        int sold = Integer.MIN_VALUE;
        int reset = 0;
        for(int price : prices){
            int preSold = sold;
            sold = held + price;
            held = Math.max(held, reset - price);
            reset = Math.max(reset, preSold);
        }
        return Math.max(sold, reset);
    }

    public int maxProfitI(int[] prices) {
        int n = prices.length;
        if(n <= 1) return 0;
        int[] s0 = new int[n];
        int[] s1 = new int[n];
        int[] s2 = new int[n];
        
        s0[0] = 0;
        s1[0] = -prices[0];
        s2[0] = Integer.MIN_VALUE;
        
        for (int i = 1; i < n ; i++) {
            s0[i] = Math.max(s0[i-1], s2[i-1]); //Stay at s0 or reset from s2.
            s1[i] = Math.max(s0[i-1] - prices[i], s1[i-1]); // Buy from s0 or stay at s1
            s2[i] = s1[i-1] + prices[i]; // Only one way from s1
        }
            
        return Math.max(s0[n-1], s2[n-1]);
    }

    /*
    LC : 1031 Maximum Sum of Two Non-Overlapping Subarrays
    https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/ */
    public int maxSumTwoNoOverlap(int[] a, int l, int m) {
        return Math.max(maxSum(l, m, a),maxSum(m, l, a));
    }
    
    public int maxSum(int l, int m, int[]a){
        int res = 0;
        int n = a.length;
        int[] left = new int[n + 1];
        int[] right = new int[n + 1];
        
        int s_l = 0, s_r = 0;
        for(int i = 0, j = n - 1; i < n && j >= 0; i++, j--){
            s_l += a[i];
            s_r += a[j];
            left[i+1] = Math.max(left[i], s_l);
            right[j] = Math.max(right[j+1], s_r);
            
            if(i + 1 >= l) s_l -= a[i + 1 - l];
            if(i + 1 >= m) s_r -= a[j + m - 1];//This line not clear
        }
        
        for(int i = 0; i < n + 1; i++) {
            res = Math.max(left[i] + right[i], res);
        }
        return res;
    }

    /*
    LC : 622
    https://leetcode.com/problems/design-circular-queue/ */
    class MyCircularQueue {

        /** Initialize your data structure here. Set the size of the queue to be k. */
        int[] queue;
        int front;
        int rear;
        final int n;
        public MyCircularQueue(int n) {
            queue = new int[n];
            Arrays.fill(queue, -1);
            front = -1;
            rear = -1;
            this.n = n;
        }

        /** Insert an element into the circular queue. Return true if the operation is successful. */
        public boolean enQueue(int value) {
            if(isFull()){
                return false;
            }
            if(front == -1) front = 0;
            rear = ++rear % n;
            queue[rear] = value;
            return true;
        }

        /** Delete an element from the circular queue. Return true if the operation is successful. */
        public boolean deQueue() {
            if(isEmpty()) return false;

            queue[front] = -1;
            if(front == rear){
                front = -1;
                rear = -1;
            }else{
                front = ++front % n;
            }
            return true;
        }

        /** Get the front item from the queue. */
        public int Front() {
            if(isEmpty()) return -1;
            return queue[front];
        }

        /** Get the last item from the queue. */
        public int Rear() {
            if(isEmpty()) return -1;
            return queue[rear];
        }

        /** Checks whether the circular queue is empty or not. */
        public boolean isEmpty() {
            return front == -1;
        }

        /** Checks whether the circular queue is full or not. */
        public boolean isFull() {
            return (rear + 1) % n == front;
        }
    }

    /*
    LC : 1518. Water Bottles
    https://leetcode.com/problems/water-bottles/ */
    public int numWaterBottles(int n, int ne) {
        int d = 0;
        while(n >= ne){
            d += ne;
            n = n - ne + 1;
        }
        d += n;
        return d;
    }
    public int numWaterBottlesI(int n, int ne) {
        int ret = n;
        while (n >= ne) {
           int rem = n % ne;
           n = n / ne;
           ret += n; 
           n += rem;
        }
        return ret;
    }

    /*
    LC : 1519 Number of Nodes in the Sub-Tree With the Same Label
    https://leetcode.com/problems/number-of-nodes-in-the-sub-tree-with-the-same-label/ */
    public int[] countSubTrees(int n, int[][] edges, String labels) {
        LinkedList<Integer>[] tree = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            tree[i] = new LinkedList<>();
        }
        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dst = edges[i][1];
            tree[src].add(dst);
            tree[dst].add(src);
        }
        int[] res = new int[n];
        boolean[] visited = new boolean[n];
        dfs(tree, 0, visited, labels, res);
        //System.out.println("Arrays.toString(res) = " + Arrays.toString(res));
        return res;
    }

    public int[] dfs(LinkedList<Integer>[] tree, int src,
                     boolean[] visited, String lables, int[] res){
        int []cnt = new int[26];
        if(visited[src]) return cnt;
        visited[src] = true;
        char color = lables.charAt(src);
        for (int i = 0; i < tree[src].size(); i++) {
            int adj = tree[src].get(i);
            int[] sub = dfs(tree, adj, visited, lables, res);
            for (int j = 0; j < sub.length; j++) {
                cnt[j] += sub[j];
            }
        }
        cnt[color - 'a']++;
        res[src] = cnt[color - 'a'];
        return cnt;
    }

}
