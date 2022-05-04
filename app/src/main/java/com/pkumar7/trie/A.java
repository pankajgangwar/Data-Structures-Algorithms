package com.pkumar7.trie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Pankaj Kumar on 20/August/2020
 */
class A {

    /* 1948. Delete Duplicate Folders in System
     * https://leetcode.com/problems/delete-duplicate-folders-in-system/
     * */
    class Folder {
        String key = "";
        String name;
        List<Folder> children;
        HashMap<String, Folder> map;
        boolean toDelete = false;
        public Folder(String name){
            this.name = name;
            map = new HashMap<>();
            children = new ArrayList<>();
        }
    }

    Folder root = new Folder("/");
    HashMap<String, Integer> mapKey = new HashMap<>();
    public List<List<String>> deleteDuplicateFolder(List<List<String>> paths) {
        for (List<String> p : paths) {
            buildDirectories(p);
        }
        generateKey(root);
        markFolderToBeDeleted(root);
        List<List<String>> list = new ArrayList<>();
        for(List<String> path : paths){
            if(toDelete(path)) continue;
            list.add(path);
        }
        return list;
    }

    private boolean toDelete(List<String> path) {
        Folder curr = root;
        for(String p : path){
            curr = curr.map.get(p);
            if(curr.toDelete){
                return true;
            }
        }
        return false;
    }

    private void markFolderToBeDeleted(Folder root) {
        if(root.children.size() > 0 && mapKey.get(root.key) > 1){
            root.toDelete = true;
            return;
        }
        for(Folder f : root.children){
            markFolderToBeDeleted(f);
        }
    }

    private String generateKey(Folder root) {
        if(root.children.isEmpty()) return "";
        Collections.sort(root.children, Comparator.comparing(a -> a.name));
        StringBuilder sb = new StringBuilder();
        for(Folder child : root.children){
            sb.append("(")
                    .append(child.name)
                    .append(generateKey(child))
                    .append(")");
        }
        String key = sb.toString();
        root.key = key;
        mapKey.put(key, mapKey.getOrDefault(key, 0) + 1);
        return key;
    }

    private void buildDirectories(List<String> paths) {
        Folder curr = root;
        for(String folderName : paths){
            if(!curr.map.containsKey(folderName)){
                Folder folder = new Folder(folderName);
                curr.map.put(folderName, folder);
                curr.children.add(folder);
            }
            curr = curr.map.get(folderName);
        }
    }

    /* 1803. Count Pairs With XOR in a Range
    * https://leetcode.com/problems/count-pairs-with-xor-in-a-range/
    * */
    class Node {
        Node[] children;
        int count;
        public Node(){
            children = new Node[2];
        }
    }
    public void insert(Node root, int N){
        for (int i = 31; i >= 0 ; i--) {
            int x = (N >> i) & 1;
            if(root.children[x] == null){
                root.children[x] = new Node();
            }
            root.children[x].count += 1;
            root = root.children[x];
        }
    }

    public int countSmaller(Node root, int N, int K){
        int pairs = 0;
        for (int i = 31; i >= 0 && root != null; i--) {
            int x = (N >> i) & 1;
            int x_flip = x == 0 ? 1: 0;
            int y = (K >> i) & 1;
            if(y == 1){
                if(root.children[x] != null){
                    pairs += root.children[x].count;
                }
                root = root.children[x_flip];
            }else{
                root = root.children[x];
            }
        }
        return pairs;
    }
    public int countPairs(int[] nums, int low, int high) {
        int n = nums.length;
        Node root = new Node();
        int countPairs = 0;
        for (int i = 0; i < n; i++) {
            countPairs += countSmaller(root, nums[i], high + 1) - countSmaller(root, nums[i], low);
            insert(root, nums[i]);
        }
        return countPairs;
    }

    /* 1707. Maximum XOR With an Element From Array
     * https://leetcode.com/problems/maximum-xor-with-an-element-from-array/
     * */
    class TrieNode {
        TrieNode[] children;
        int prefixValue;
        public TrieNode(){
            children = new TrieNode[2];
        }
    }
    public int[] maximizeXor(int[] nums, int[][] queries) {
        int[] res = new int[queries.length];
        Arrays.sort(nums);
        int[][] newQueries = new int[queries.length][3];
        for (int i = 0; i < queries.length; i++) {
            newQueries[i][0] =  queries[i][0];
            newQueries[i][1] =  queries[i][1];
            newQueries[i][2] = i;
        }
        Arrays.sort(newQueries, (a,b)-> a[1] - b[1]);
        TrieNode root = new TrieNode();
        int j = 0;
        for (int i = 0; i < newQueries.length; i++) {
            int x = newQueries[i][0];
            int m = newQueries[i][1];
            while(j < nums.length && m >= nums[j]){
                insert(root, nums[j]);
                j++;
            }
            int ans = -1;
            if(j != 0) {
                ans = search(root, x);
            }
            res[newQueries[i][2]] = ans;
        }
        return res;
    }

    public int search(TrieNode root, int n){
        TrieNode temp = root;
        for (int i = 31; i >= 0 ; --i) {
            int bit = (n >> i) & 1;
            int bitFlip = bit == 0 ? 1 : 0;
            if(temp.children[bitFlip] != null){
                temp = temp.children[bitFlip];
            }else{
                temp = temp.children[bit];
            }
        }
        return temp.prefixValue ^ n;
    }

    public void insert(TrieNode root, int n){
        TrieNode temp = root;
        for (int i = 31; i >= 0 ; --i) {
            int bit = (n >> i) & 1;
            if(temp.children[bit] == null){
                temp.children[bit] = new TrieNode();
            }
            temp = temp.children[bit];
        }
        temp.prefixValue = n;
    }

    /* 1065. Index Pairs of a String
     * https://leetcode.com/problems/index-pairs-of-a-string/
     * */
    class Trie {
        Trie[] children = new Trie[26];
        boolean isEndOfWord = false;
    }
    public int[][] indexPairs(String text, String[] words) {
        List<int[]> res = new ArrayList<>();
        Trie root = new Trie();
        for(String w : words){
            Trie r = root;
            for(char ch : w.toCharArray()){
                if(r.children[ch - 'a'] == null){
                    r.children[ch - 'a'] = new Trie();
                }
                r = r.children[ch - 'a'];
            }
            r.isEndOfWord = true;
        }
        for (int i = 0; i < text.length(); i++) {
            Trie r = root;
            int j = i;
            if(r.children[text.charAt(i) - 'a'] != null){
                while (j < text.length() && r.children[text.charAt(j) - 'a'] != null){
                    r = r.children[text.charAt(j) - 'a'];
                    j++;

                    if(r.isEndOfWord){
                        res.add(new int[]{i, j - 1});
                    }
                }
            }
        }
        return res.toArray(new int[0][]);
    }
}
