package com.pkumar7.heap;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Pankaj Kumar on 29/August/2020
 */
class A {

    /* 1801. Number of Orders in the Backlog
    * https://leetcode.com/problems/number-of-orders-in-the-backlog/
    * */
    public int getNumberOfBacklogOrders(int[][] orders) {
        PriorityQueue<int[]> sell = new PriorityQueue<>((a,b) -> (a[0] - b[0]) );// minPq
        PriorityQueue<int[]> buy = new PriorityQueue<>((a,b) -> (-a[0] + b[0])); // maxPq
        // price, amount
        int res = 0;
        for(int[] ord : orders){
            int price = ord[0];
            int amount = ord[1];
            int orderType = ord[2];
            if(orderType == 0){ // buy
                while (!sell.isEmpty() && sell.peek()[0] <= price && amount > 0){
                    if(sell.peek()[1] >= amount){
                        sell.peek()[1] -= amount;
                        if(sell.peek()[1] == 0) sell.poll();
                        amount = 0;
                    }else{
                        amount -= sell.poll()[1];
                    }
                }
                if(amount > 0){
                    buy.offer(new int[]{price, amount});
                }
            }else{ // sell
                while (!buy.isEmpty() && buy.peek()[0] >= price && amount > 0){
                    if(buy.peek()[1] >= amount){
                        buy.peek()[1] -= amount;
                        if(buy.peek()[1] == 0) buy.poll();
                        amount = 0;
                    }else{
                        amount -= buy.poll()[1];
                    }
                }
                if(amount > 0){
                    sell.offer(new int[]{price, amount});
                }
            }
        }
        int mod = (int)1e9 + 7;
        while (!buy.isEmpty()){
            res = (res + (buy.peek()[1])) % mod;
            buy.poll();
        }
        while (!sell.isEmpty()) {
            res = (res + (sell.peek()[1])) % mod;
            sell.poll();
        }
        return res;
    }

    /*
    * https://leetcode.com/problems/maximum-average-pass-ratio/
    * */
    public double maxAverageRatio(int[][] classes, int extraStudents) {
        double totalAvg = 0;
        int n = classes.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>((r1, r2) -> {
            double c1 = (double)(r1[0] + 1)/(r1[1] + 1) - (double)r1[0] / r1[1];
            double c2 = (double)(r2[0] + 1)/(r2[1] + 1) - (double)r2[0] / r2[1];
            return -Double.compare(c1, c2); // return max diff in ratio
        });
        for(int[] c : classes){
            double avg = (double) c[0] / c[1];
            totalAvg += avg;
            pq.offer(new int[]{c[0], c[1]});
        }
        while (extraStudents > 0){
            int[] min = pq.poll();
            double oldchange = (double)min[0] / min[1];
            min[0]+= 1;
            min[1]+= 1;
            double newchange = (double)min[0] / min[1];
            extraStudents -= 1;
            totalAvg += (newchange - oldchange);
            pq.offer(min);
        }
        return totalAvg / n;
    }

    /* 1705. Maximum Number of Eaten Apples
     * https://leetcode.com/problems/maximum-number-of-eaten-apples/
     * */
    public int eatenApples(int[] apples, int[] days) {
        int res = 0;
        //Entries with minimum expiry days should come first
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a,b) -> a[0] - b[0]);
        for (int d = 0; d <= 20000; d++) {
            if(d < days.length) {
                // Add days after which apples will rot and no. of apples
                pq.offer(new int[]{d + days[d] - 1, apples[d]});

            }
            while(!pq.isEmpty()){
                int[] curr = pq.peek();//check apples which are expiring soon
                if(curr[0] < d){
                    pq.poll(); // Remove the apples which are rotten till this day
                }else{
                    break;
                }
            }
            if(!pq.isEmpty()){
                int[] curr = pq.poll();
                curr[1]--;
                res++;
                if(curr[1] > 0){
                    pq.offer(curr);
                }
            }
        }
        return res;
    }

    /* Amazon
     * https://leetcode.com/discuss/interview-question/933383/
     * */
    public int fiveStar(List<List<Integer>> productRatings, int ratingThreshold){
        PriorityQueue<int[]> pq = new PriorityQueue<>((r1, r2) -> {
            int c1 = (r1[0] + 1 / r1[1] + 1) - (r1[0] / r1[1]);
            int c2 = (r2[0] + 1 / r2[1] + 1) - (r2[0] / r2[1]);
            return c1 - c2;
        });
        int res = 0;
        double current = 0.00;
        int n = productRatings.size();
        double threshold = (double)(ratingThreshold * n) / (double)100.0;
        for(List<Integer> list : productRatings){
            current += (double)list.get(0) / list.get(1) ;
            pq.offer(new int[]{list.get(0), list.get(1)});
        }
        while (current < threshold){
            int[] maxRating = pq.poll();
            double change = (double) maxRating[0] / maxRating[1];
            maxRating[0]++;
            maxRating[1]++;
            double newchange = (double)maxRating[0] / maxRating[1];
            current += newchange - change;
            pq.offer(maxRating);
            res++;
        }
        return res;
    };

    /*
    554. Brick Wall
    https://leetcode.com/problems/brick-wall/
    */
    public int leastBricks(List<List<Integer>> wall) {
        int res = Integer.MAX_VALUE;
        int size = wall.size();
        if(size == 0) return 0;
        PriorityQueue<int[]> minPq = new PriorityQueue<>((a,b) -> a[0] - b[0]); // [brick length, rowIdx, colIdx]
        for (int i = 0; i < wall.size(); i++) {
            List<Integer> w = wall.get(i);
            minPq.offer(new int[]{w.get(0), i, 0});
        }
        while (!minPq.isEmpty()){
            int count = 0;
            int currlen = minPq.peek()[0];
            while (!minPq.isEmpty() && minPq.peek()[0] == currlen){
                count++;
                int[] curr = minPq.poll();
                if((curr[2] + 1) < wall.get(curr[1]).size()){
                    minPq.offer(new int[]{currlen + wall.get(curr[1]).get(curr[2] + 1), curr[1], curr[2] + 1});
                }
            }
            if(!minPq.isEmpty()){
                res = Math.min(res, size - count);
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/minimize-deviation-in-array/
     * 1675. Minimize Deviation in Array
     * */
    public int minimumDeviation(int[] nums) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        int res = Integer.MAX_VALUE;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] % 2 != 0){
                maxHeap.offer(nums[i] * 2);
                min = Math.min(min, nums[i] * 2);
            }else{
                min = Math.min(min, nums[i]);
                maxHeap.offer(nums[i]);
            }
        }
        while (!maxHeap.isEmpty()){
            int currMax = maxHeap.poll();
            res = Math.min(res, currMax - min);
            if(currMax % 2 == 0){
                min = Math.min(min, currMax / 2);
                maxHeap.offer(currMax / 2);
            }else{
                break;
            }
        }
        return res;
    }

    /* 632. Smallest Range Covering Elements from K Lists
     * https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/
     * */
    public int[] smallestRange(List<List<Integer>> nums) {
        int[] res = new int[2];
        int max = Integer.MIN_VALUE;
        PriorityQueue<int[]> pq = new PriorityQueue<>((a,b) -> a[0] - b[0]);//[value, idx, row]
        for (int row = 0; row < nums.size(); row++) {
            pq.offer(new int[]{nums.get(row).get(0), 0, row});
            max = Math.max(nums.get(row).get(0), max);
        }
        int start = 0, end = Integer.MAX_VALUE;
        while (pq.size() == nums.size()){
            int[] curr = pq.poll();
            int val = curr[0];
            int idx = curr[1];
            int row = curr[2];
            if(end - start > max - val){
                start = val;
                end = max;
            }
            if(idx + 1 < nums.get(row).size()){
                int next_val = nums.get(row).get(idx + 1);
                pq.offer(new int[]{next_val, idx + 1, row});
                max = Math.max(max, next_val);
            }
        }
        res[0] = start;
        res[1] = end;
        return res;
    }

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
