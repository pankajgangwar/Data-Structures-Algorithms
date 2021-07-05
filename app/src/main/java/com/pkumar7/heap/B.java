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
