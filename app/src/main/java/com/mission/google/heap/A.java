package com.mission.google.heap;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeSet;

/**
 * Created by Pankaj Kumar on 29/August/2020
 */
class A {

    /*
     * 1578. Minimum Deletion Cost to Avoid Repeating Letters
     * https://leetcode.com/problems/minimum-deletion-cost-to-avoid-repeating-letters/
     * */
    public int minCost(String s, int[] cost) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = s.length();
        int minCost = 0;
        for (int i = 0; i < n; i++) {
            int count = 0;
            int j = i;
            pq.clear();
            while (j < n && s.charAt(i) == s.charAt(j)){
                pq.offer(cost[j]);
                j++;
                count++;
            }
            if(count > 1){
                while (pq.size() > 1){
                    minCost += pq.poll();
                }
                i = j - 1;
            }
        }
        return minCost;
    }

    /*
    * 313. Super Ugly Number
    * https://leetcode.com/problems/super-ugly-number/
    * */
    public int nthSuperUglyNumber1(int n, int[] primes) {
        int[] ugly = new int[n];
        int[] idx = new int[primes.length];
        ugly[0] = 1;
        for (int i = 1; i < n; i++) {
            ugly[i] = Integer.MAX_VALUE;
            for (int j = 0; j < primes.length; j++) {
                ugly[i] = Math.min(ugly[i], primes[j] * ugly[idx[j]]);
            }
            for (int j = 0; j < primes.length; j++) {
                while (primes[j] * ugly[idx[j]] <= ugly[i]) idx[j]++;
            }
        }
        return ugly[n - 1];
    }

    public int nthSuperUglyNumber(int n, int[] primes) {
        PriorityQueue<Long> pq = new PriorityQueue<>();
        pq.offer(1L);
        while(n > 0){
            long curr = pq.poll();
            while(!pq.isEmpty() && pq.peek() == curr) pq.poll();
            if(--n == 0 )return (int)curr;
            for(int x : primes){
                pq.offer((long)curr * x);
            }
        }
        return -1;
    }

    /*264. Ugly Number II
    * https://leetcode.com/problems/ugly-number-ii/
    */
    public int nthUglyNumber(int n) {
        Queue<Long> q = new PriorityQueue<>();
        q.offer(1L);
        while (n > 0){
            long curr = q.poll();
            while (!q.isEmpty() && curr == q.peek()) q.poll();
            if(--n == 0){
                return (int)curr;
            }
            q.offer((long)curr * 2);
            q.offer((long)curr * 3);
            q.offer((long)curr * 5);
        }
        return -1;
    }

}
