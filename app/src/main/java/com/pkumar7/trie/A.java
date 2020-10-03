package com.pkumar7.trie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 20/August/2020
 */
class A {
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
