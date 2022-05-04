package com.pkumar7.heap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.PriorityQueue;

public class B {

    /*
     * https://leetcode.com/problems/furthest-building-you-can-reach/
     * 1642. Furthest Building You Can Reach
     * */
    public int furthestBuilding(int[] heights, int bricks, int ladders) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        int n = heights.length;
        for (int i = 0; i < n - 1; i++) {
            int d = heights[i + 1] - heights[i];
            if(d > 0){
                pq.offer(d);
                if(!pq.isEmpty() && pq.size() > ladders){
                    bricks -= pq.poll();
                }
            }
            if(bricks < 0){
                return i;
            }
        }
        return n - 1;
    }

    /* 2102. Sequentially Ordinal Rank Tracker
     * https://leetcode.com/problems/sequentially-ordinal-rank-tracker/
     * */
    class SORTracker {

        int query = 1;
        PriorityQueue<Item> top = new PriorityQueue<>((a,b) -> a.score == b.score ? -a.name.compareTo(b.name) : a.score - b.score);
        PriorityQueue<Item> bottom = new PriorityQueue<>((a,b) -> a.score == b.score ? a.name.compareTo(b.name) : -a.score + b.score);
        public SORTracker() {

        }

        public void add(String name, int score) {
            top.offer(new Item(score,name));
            if(top.size() >= query){
                bottom.offer(top.poll());
            }
        }

        public String get() {
            query += 1;
            Item it = bottom.poll();
            top.offer(it);
            return it.name;
        }
    }

    class Item{
        int score;
        String name;
        public Item(int score, String name){
            this.score = score;
            this.name = name;
        }
    }

    /*
    * https://leetcode.com/problems/maximum-product-after-k-increments/
    * 2233. Maximum Product After K Increments
    * */
    public int maximumProduct(int[] nums, int k) {
        int mod = 1000_000_000 + 7;
        if(k == 0){
            int prod = 1;
            for(int a : nums){
                prod *= a;
                prod %= mod;
            }
            System.out.println(prod);
            return prod;
        }
        int n = nums.length;
        if(n == 1){
            nums[0] += k;
            return nums[0] % mod;
        }
        PriorityQueue<Long> minHeap = new PriorityQueue<>();
        for(int a : nums){
            minHeap.offer((long)a);
        }
        while (k > 0){
            long a = minHeap.poll();
            long b = minHeap.poll();
            long incr = Math.min(k, b - a + 1);
            a += incr;
            k -= incr;
            minHeap.offer(a);
            minHeap.offer(b);
        }
        long prod = 1l;
        while (!minHeap.isEmpty()){
            long a = minHeap.poll();
            prod *= a;
            prod = prod % mod;
        }
        return (int)prod;
    }

    /* 1942. The Number of the Smallest Unoccupied Chair
     * https://leetcode.com/problems/the-number-of-the-smallest-unoccupied-chair/
     * */
    public int smallestChair(int[][] times, int targetFriend) {
        int[][] xx = new int[times.length][3];
        for (int i = 0; i < times.length; i++) {
            xx[i] = new int[]{times[i][0], times[i][1], i};
        }
        Arrays.sort(xx, (a,b) -> a[0] - b[0]);
        PriorityQueue<int[]> occupied = new PriorityQueue<>((a,b) -> a[0] - b[0]);// [endtime, seatNumber]
        PriorityQueue<Integer> available = new PriorityQueue<>();
        int chair = 0;
        for (int i = 0; i < xx.length; i++) {
            int[] a = xx[i];
            int start = a[0];
            int end = a[1];
            int person = a[2];
            while (!occupied.isEmpty() && occupied.peek()[0] <= start){
                available.offer(occupied.poll()[1]);
            }
            if(!available.isEmpty()){
                int minSeat = available.poll();
                if(person == targetFriend){
                    return minSeat;
                }
                occupied.offer(new int[]{end, minSeat});
            }else{
                if(person == targetFriend){
                    return chair;
                }
                occupied.offer(new int[]{end, chair++});
            }
        }
        return -1;//Should not happen
    }

    /*
    https://leetcode.com/problems/eliminate-maximum-number-of-monsters/
    1921. Eliminate Maximum Number of Monsters
    * */
    public int eliminateMaximum(int[] dist, int[] speed) {
        int n = dist.length;
        Double[][] arr = new Double[n][3];
        for (int i = 0; i < n; i++) {
            double time = (double)dist[i] / speed[i];
            arr[i] = new Double[]{time, (double) dist[i], (double) speed[i]};
        }
        Arrays.sort(arr, (a, b) -> Double.compare(a[0], b[0]));
        double time = 0;
        int res = 0;
        if(arr[0][0] == time) return res;
        int next = 0;
        while(next < n && time < arr[next][0]){
            time++;
            next++;res++;
        }
        return res;
    }

    /* 355. Design Twitter
     * https://leetcode.com/problems/design-twitter/
     * Similar : Merge k sorted lists
     * */
    static class Tweets{
        int tweetId;
        int userId;
        int time;
        public Tweets(int tweetId, int userId, int time) {
            this.tweetId = tweetId;
            this.userId = userId;
            this.time = time;
        }
    }

    static class Twitter {

        /** Initialize your data structure here. */
        HashMap<Integer, List<Tweets>> tweetMap = new HashMap<>();
        HashMap<Integer, LinkedHashSet<Integer>> followers = new HashMap<>();
        int time = 0;
        public Twitter() {

        }

        /** Compose a new tweet. */
        public void postTweet(int userId, int tweetId) {
            tweetMap.putIfAbsent(userId, new ArrayList<>());
            tweetMap.get(userId).add(new Tweets(tweetId, userId, ++time));
            followers.putIfAbsent(userId, new LinkedHashSet<>());
            followers.get(userId).add(userId);
        }

        /** Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be
         * posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent. */
        public List<Integer> getNewsFeed(int userId) {
            if(!followers.containsKey(userId)){
                return Collections.emptyList();
            }
            LinkedHashSet<Integer> follows = followers.get(userId);
            if(follows.isEmpty()) return Collections.emptyList();

            HashMap<Integer, Integer> indexes = new HashMap<>();
            PriorityQueue<Tweets> topTweets = new PriorityQueue<>(10, (a, b) -> -a.time + b.time);
            List<Integer> tweets = new ArrayList<>();

            while (tweets.size() < 10) {
                int size = follows.size();
                for (int followerId : follows) {
                    int lastIdx = tweetMap.containsKey(followerId) ? tweetMap.get(followerId).size() - 1 : -1;
                    lastIdx = indexes.containsKey(followerId) ? indexes.get(followerId) : lastIdx;
                    if (lastIdx == -1) {
                        if (--size == 0) break;
                        continue;
                    }
                    Tweets t = tweetMap.get(followerId).get(lastIdx);
                    topTweets.offer(t);
                    indexes.put(followerId, lastIdx - 1);
                }
                if (topTweets.isEmpty()) break;
                Tweets recentTweet = topTweets.poll();
                tweets.add(recentTweet.tweetId);
            }
            return tweets;
        }

        /** Follower follows a followee. If the operation is invalid, it should be a no-op. */
        public void follow(int followerId, int followeeId) {
            followers.putIfAbsent(followerId, new LinkedHashSet<>());
            followers.get(followerId).add(followeeId);
        }

        /** Follower unfollows a followee. If the operation is invalid, it should be a no-op. */
        public void unfollow(int followerId, int followeeId) {
            if(followers.get(followerId).contains(followeeId)){
                followers.get(followerId).remove(followeeId);
            }
        }
    }
}
