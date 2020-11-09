package com.pkumar7.heap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Pankaj Kumar on 29/August/2020
 */
class A {

    /* 1629. Slowest Key
     * https://leetcode.com/problems/slowest-key/
     * */

    class Pair{
        int time;
        char ch;
        public Pair(int time, char ch){
            this.ch = ch;
            this.time = time;
        }
    }
    public char slowestKey(int[] releaseTimes, String keysPressed) {
        int maxTime = releaseTimes[0];
        PriorityQueue<Pair> pq = new PriorityQueue<>((a,b) -> a.time == b.time ? b.ch - a.ch : b.time - a.time);
        pq.offer(new Pair(maxTime, keysPressed.charAt(0)));
        char ch = 'a';
        for (int i = 1; i < keysPressed.length(); i++) {
            int curr = releaseTimes[i] - releaseTimes[i - 1];
            if(curr >= maxTime){
                pq.offer(new Pair(curr, keysPressed.charAt(i)));
            }
        }
        return pq.peek().ch;
    }


    /* 1647. Minimum Deletions to Make Character Frequencies Unique
    *https://leetcode.com/problems/minimum-deletions-to-make-character-frequencies-unique/
    * */
    class Entry {
        int freq;
        LinkedList<Character> charsList;
        public Entry(int currF, LinkedList<Character> list){
            this.freq = currF;
            this.charsList = list;
        }
    }
    public int minDeletions(String s) {
        TreeMap<Integer, LinkedList<Character>> map = new TreeMap<>(Collections.reverseOrder());
        HashMap<Character, Integer> freqMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            freqMap.put(s.charAt(i), freqMap.getOrDefault(s.charAt(i), 0) + 1);
        }
        PriorityQueue<Entry> pq = new PriorityQueue<Entry>((a,b) -> b.freq - a.freq);

        for (Map.Entry<Character, Integer> entry : freqMap.entrySet()) {
            int fr = entry.getValue();
            char ch = entry.getKey();
            map.putIfAbsent(fr, new LinkedList<>());
            map.get(fr).add(ch);
        }
        for(Map.Entry<Integer, LinkedList<Character>> entry : map.entrySet()){
            pq.offer(new Entry(entry.getKey(), entry.getValue()));
        }
        int deletion = 0;
        while (!pq.isEmpty()){
            Entry highest = pq.poll();
            int currFreq = highest.freq;
            LinkedList<Character> chars = highest.charsList;
            if(chars.size() > 1){
                char removed = chars.pollFirst();
                if(!pq.isEmpty() && pq.peek().freq == currFreq - 1){
                    Entry second = pq.poll();
                    second.charsList.add(removed);
                    pq.offer(second);
                }else if(currFreq > 1){
                    LinkedList<Character> secList = new LinkedList<>();
                    secList.add(removed);
                    pq.offer(new Entry(currFreq - 1,  secList));
                }
                pq.offer(highest);
                deletion++;
            }
        }
        return deletion;
    }

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
