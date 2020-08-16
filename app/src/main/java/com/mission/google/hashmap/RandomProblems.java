package com.mission.google.hashmap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pankaj Kumar on 11/August/2020
 */
class RandomProblems {

    /*
    * 760. Find Anagram Mappings
    * https://leetcode.com/problems/find-anagram-mappings/
    */
    public int[] anagramMappings(int[] A, int[] B) {
        int[] res = new int[A.length];
        HashMap<Integer, LinkedList<Integer>> map = new HashMap<>();
        
        for(int i = 0; i < A.length; i++){
            map.putIfAbsent(A[i], new LinkedList<Integer>());
            map.get(A[i]).add(i);
        }
        
        for(int j = 0; j < B.length; j++){
            res[map.get(B[j]).pollFirst()] = j;
        }
        return res;
    }

    /*
    * 389. Find the Difference
    * https://leetcode.com/problems/find-the-difference/
    * */
    public char findTheDifference(String s, String t) {
        return UsingBitManipulation(s, t);
    }

    public char UsingBitManipulation(String s, String t){
        char c = 0;
        for(char ch : s.toCharArray()){
            c ^= ch;
        }
        for(char ch : t.toCharArray()){
            c ^= ch;
        }
        return c;
    }

    public char usingMap(String s, String t){
        char[] a = s.toCharArray();
        char[] b = t.toCharArray();
        int[] diff = new int[26];

        for(char ch : a){
            diff[ch - 'a']++;
        }
        for(char ch : b){
            if(diff[ch - 'a'] > 0){
                diff[ch - 'a']--;
            }else{
                return ch;
            }
        }
        return 'a';
    }

    /* 
    187. Repeated DNA Sequences
    https://leetcode.com/problems/repeated-dna-sequences/ */
    public List<String> findRepeatedDnaSequences(String s) {
        List<String> res = new ArrayList<>();
        if(s.length() < 10 ) return res;
        
        HashMap<String, Integer> map = new HashMap<>();
        
        String first = s.substring(0, 10);
        
        map.put(first, map.getOrDefault(first, 0) + 1);
        
        for(int i = 1; i + 9 < s.length(); i++){
            String curr = s.substring(i, i + 10);
            if(map.containsKey(curr)){
                if(!res.contains(curr)){
                    res.add(curr);
                }
            }else{
                map.put(curr, map.getOrDefault(curr, 0) + 1);
            }
        }
        return res;
    }
}
