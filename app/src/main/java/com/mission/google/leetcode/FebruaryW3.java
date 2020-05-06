package com.mission.google.leetcode;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.TreeMap;

public class FebruaryW3 {
    /* https://leetcode.com/problems/count-of-smaller-numbers-after-self/ */
    class TreeNode {
        TreeNode left = null;
        TreeNode right = null;
        int sum, val, dup = 1;
        public TreeNode(int val, int prefixSum){
            this.sum = prefixSum;
            this.val = val;
        }
    }
    public List<Integer> countSmallerTree(int[] nums) {
        int n = nums.length;
        Integer[] res = new Integer[n];
        TreeNode root = null;
        for(int i = n -1; i >= 0; --i){
            root = insert(nums[i], root, res, i, 0);
        }

        return Arrays.asList(res);
    }

    public TreeNode insert(int val, TreeNode node, Integer[] res, int i, int prefixSum){
        if(node == null){
            node = new TreeNode(val, 0);
            res[i] = prefixSum;
        }else if(node.val > val){
            node.sum++;
            node.left = insert(val, node.left, res, i, prefixSum);
        }else if(node.val == val){
            node.dup++;
            res[i] = prefixSum + node.sum;
        }else{
            node.right = insert(val, node.right, res, i, prefixSum + node.dup + node.sum);
        }
        return node;
    }

    class Pair {
        int val, idx;
        public Pair(int val, int idx){
            this.val = val;
            this.idx = idx;
        }
    }

    public List<Integer> countSmallerMergeSort(int[] nums) {
        int n = nums.length;
        Pair[] arr = new Pair[n];

        for(int i = 0; i < n; i++){
            Pair p = new Pair(nums[i], i);
            arr[i] = p;
        }

        Integer[] res = new Integer[n];
        Arrays.fill(res, 0);

        mergeSort(arr, res);

        for(Pair p : arr){
            //System.out.print(p.val + "\t");
        }

        return Arrays.asList(res);
    }

    public Pair[] mergeSort(Pair[] nums, Integer[] res){
        if(nums.length <= 1){
            return nums;
        }
        int mid = nums.length / 2;
        Pair[] left = mergeSort(Arrays.copyOfRange(nums, 0, mid), res);
        Pair[] right = mergeSort(Arrays.copyOfRange(nums, mid, nums.length), res);

        for(int i = 0, j = 0; i < left.length || j < right.length;){
            if(j == right.length || i < left.length && left[i].val <= right[j].val){
                nums[i + j] = left[i];
                res[left[i].idx] += j;
                i++;
            }else{
                nums[i + j] = right[j];
                j++;
            }
        }
        return nums;
    }

    public List<Integer> countSmallerTLE(int[] nums) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> tmpHeap = new PriorityQueue<>();

        List<Integer> res = new ArrayList<>();
        for(int i = nums.length - 1; i >= 0; --i){
            while(!minHeap.isEmpty() && minHeap.peek() < nums[i]){
                tmpHeap.offer(minHeap.poll());
            }
            res.add(0, tmpHeap.size());

            while(!tmpHeap.isEmpty()){
                minHeap.offer(tmpHeap.poll());
            }
            minHeap.offer(nums[i]);
        }
        return res;
    }

    public List<Integer> countSmaller(int[] nums) {
        List<Integer> list = new ArrayList<>();
        Integer[] res = new Integer[nums.length];
        for (int i = nums.length -1; i >= 0; --i) {
            int expectedIdx = Math.abs(Collections.binarySearch(list, nums[i])) - 1;
            list.add(expectedIdx, nums[i]);
            res[i] = expectedIdx;
        }
        return Arrays.asList(res);
    }

    class TrieNode{
        boolean isEndOfWord = false;
        int TOTAL_ALPHABETS = 26;
        TrieNode[] children = new TrieNode[TOTAL_ALPHABETS];
        HashMap<String, Integer> counts = new HashMap<>();

        public TrieNode(){
            for(int i = 0; i < TOTAL_ALPHABETS; i++){
                children[i] = null;
            }
        }
    }

    class AutocompleteSystem {

        TrieNode root;
        StringBuilder builder;
        public AutocompleteSystem(String[] sentences, int[] times) {
            root = new TrieNode();
            builder = new StringBuilder();
            for(int i = 0; i < sentences.length && i < times.length ; i++){
                insert(sentences[i], times[i]);
            }
        }

        public void insert(String word, int times){
            TrieNode pCrawl = root;
            int n = word.length();
            for(int level = 0; level < n; level++){
                int idx = word.charAt(level) - 'a';
                System.out.println("idx = " + idx);
                TrieNode curr = pCrawl.children[idx];
                if(curr == null){
                    curr = new TrieNode();
                }

                curr.counts.put(word, curr.counts.getOrDefault(word, 0) + times);
                pCrawl = curr;
            }

            pCrawl.isEndOfWord = true;
        }

        class Pair {
            String s;
            int c;

            public Pair(String s, int c){
                this.s = s;
                this.c = c;
            }
        }

        public List<String> input(char c) {
            if(c == '#'){
                insert(builder.toString(), 1);
                builder = new StringBuilder();
                return new ArrayList<String>();
            }
            List<String> res = new ArrayList<>();
            builder.append(c);

            TrieNode pCrawl = root;

            String str = builder.toString();
            for (char ch : str.toCharArray()) {
                if(pCrawl.children[ch - 'a'] == null)
                    return new ArrayList<String>();

                pCrawl = pCrawl.children[ch - 'a'];
            }

            PriorityQueue<Pair> pq = new PriorityQueue<>( (a, b) ->
                    (a.c == b.c) ? a.s.compareTo(b.s) : b.c - a.c);

            for(String s: pCrawl.counts.keySet()){
                pq.offer(new Pair(s, pCrawl.counts.get(s)));
            }

            for(int i = 0; i < 3 && !pq.isEmpty() ; i++){
                Pair p = pq.poll();
                res.add(p.s);
            }
            return res;
        }
    }

    public int[] sortByBits(int[] arr) {
        TreeMap<Integer, ArrayList<Integer>> map = new TreeMap<>();
        for (int i = 0; i < arr.length; i++) {
            int count = Integer.bitCount(arr[i]);
            map.putIfAbsent(count, new ArrayList<>());
            map.get(count).add(arr[i]);
        }
        int[] res = new int[arr.length];
        int idx = 0;
        for (Map.Entry<Integer, ArrayList<Integer>> entry :  map.entrySet()){
            ArrayList<Integer> list = entry.getValue();
            for (int i = 0; i < list.size(); i++) {
                res[idx++] = list.get(i);
            }
        }
        return res;
    }

    public static void main(String[] args) {
        FebruaryW3 w3 = new FebruaryW3();
        //w3.largestMultipleOfThree(new int[]{8,6,7,1,0});
    }

    /* https://leetcode.com/problems/number-of-substrings-containing-all-three-characters/ */
    public int numberOfSubstrings(String s) {
        return helper(s);
    }

    public int helper(String s){
        if(s.length() == 0) {
            return 0;
        }
        int[] freq = new int[3];
        char[] arr = s.toCharArray();
        int start = 0;
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            ++freq[arr[i] - 'a'];

            while(freq[0] > 0 && freq[1] > 0 && freq[2] > 0){
                --freq[arr[start++] - 'a'];
            }
            count += start;
        }
        return count;
    }

    public int subarraysWithKDistinct(char[] A, int K) {
        int res = 0, prefix = 0;
        int[] m = new int[A.length + 1];
        for (int i = 0, j = 0, cnt = 0; i < A.length; ++i) {
            if (m[A[i]]++ == 0) ++cnt;
            if (cnt > K) {
                --m[A[j++]]; --cnt; prefix = 0;
            }
            while (m[A[j]] > 1) {
                ++prefix; --m[A[j++]];
            }
            if (cnt == K) res += prefix + 1;
        }
        return res;
    }
}
