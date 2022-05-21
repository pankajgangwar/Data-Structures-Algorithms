package com.pkumar7.design;

import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class A {

    /*
     * 2254. Design Video Sharing Platform
     * https://leetcode.com/problems/design-video-sharing-platform/
     * */
    class VideoSharingPlatform {

        int videoId = 0;
        HashMap<Integer, Integer> likes = new HashMap<>();
        HashMap<Integer, Integer> disLikes = new HashMap<>();
        HashMap<Integer, Integer> views = new HashMap<>();
        HashMap<Integer, String> videosOnCloud = new HashMap<>();
        TreeSet<Integer> removedVideoIds = new TreeSet<>();
        public VideoSharingPlatform() {

        }

        public int upload(String video) {
            if(!removedVideoIds.isEmpty()){
                int available = removedVideoIds.pollFirst();
                videosOnCloud.put(available, video);
                return available;
            }
            videosOnCloud.put(videoId++, video);
            return videoId - 1;
        }

        public void remove(int videoId) {
            if(!videosOnCloud.containsKey(videoId)) return;
            videosOnCloud.remove(videoId);
            removedVideoIds.add(videoId);
        }

        public String watch(int videoId, int startMinute, int endMinute) {
            if(!videosOnCloud.containsKey(videoId)){
                return "-1";
            }
            views.put(videoId, views.getOrDefault(videoId, 0) + 1);
            String video = videosOnCloud.get(videoId);
            return video.substring(startMinute, Math.min(endMinute + 1, video.length()));
        }

        public void like(int videoId) {
            if(!videosOnCloud.containsKey(videoId)) return;
            likes.put(videoId, likes.getOrDefault(videoId, 0) + 1);
        }

        public void dislike(int videoId) {
            if(!videosOnCloud.containsKey(videoId)) return;
            disLikes.put(videoId, disLikes.getOrDefault(videoId, 0) + 1);
        }

        public int[] getLikesAndDislikes(int videoId) {
            if(!videosOnCloud.containsKey(videoId)) return new int[]{-1};
            int[] res = new int[2];
            res[0] = likes.getOrDefault(videoId, 0);
            res[1] = disLikes.getOrDefault(videoId, 0);
            return res;
        }

        public int getViews(int videoId) {
            if(!videosOnCloud.containsKey(videoId)) return -1;
            return views.getOrDefault(videoId, 0);
        }
    }

    /*
     * https://leetcode.com/problems/tweet-counts-per-frequency/
     * 1348. Tweet Counts Per Frequency
     * */
    static class TweetCounts {

        HashMap<String, TreeMap<Integer, Integer>> map = new HashMap<>();
        public TweetCounts() {

        }

        public void recordTweet(String tweetName, int time) {
            map.putIfAbsent(tweetName, new TreeMap<>());
            TreeMap<Integer, Integer> timeMap = map.get(tweetName);
            timeMap.put(time, timeMap.getOrDefault(time, 0) + 1);
        }

        public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
            List<Integer> res = new ArrayList<>();
            if(!map.containsKey(tweetName)) return res;
            int interval = 0, size = 0;
            if(freq.equals("minute")){
                interval = 60;
            }else if(freq.equals("hour")){
                interval = 3600;
            }else {
                interval = 86400;
            }
            size = ((endTime - startTime) / interval) + 1;
            int[] bucket = new int[size];
            TreeMap<Integer, Integer> timeMap = map.get(tweetName);
            for(Map.Entry<Integer, Integer> e : timeMap.subMap(startTime, endTime + 1).entrySet()){
                int index = (e.getKey() - startTime) / interval;
                bucket[index] += e.getValue();
            }
            for(int count : bucket) res.add(count);
            return res;
        }
    }

    /* 1586. Binary Search Tree Iterator II
    * https://leetcode.com/problems/binary-search-tree-iterator-ii/
    * */
    static class BSTIterator {
        Stack<TreeNode> next = new Stack<>();
        List<Integer> prev = new ArrayList<>();
        int pos = -1;

        public BSTIterator(TreeNode root) {
            traverseLeft(root);
        }

        public void traverseLeft(TreeNode root){
            while (root != null){
                next.push(root);
                root = root.left;
            }
        }

        public boolean hasNext() {
            return !next.isEmpty() || pos + 1 < prev.size();
        }

        public int next() {
            if(++pos == prev.size()){
                TreeNode curr = next.pop();
                traverseLeft(curr.right);
                prev.add(curr.val);
            }
            return prev.get(pos);
        }

        public boolean hasPrev() {
            return pos > 0;
        }

        public int prev() {
            return prev.get(--pos);
        }
    }
}
