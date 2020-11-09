package com.pkumar7.hashmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Pankaj Kumar on 06/September/2020
 */
class A {

    public static void main(String[] args) {

    }

    /* 954. Array of Doubled Pairs
     * https://leetcode.com/problems/array-of-doubled-pairs/
     * */
    public boolean canReorderDoubled(int[] arr) {
        TreeMap<Integer, Integer> freqMap = new TreeMap<>();
        for(int x : arr){
            freqMap.put(x, freqMap.getOrDefault(x, 0) + 1);
        }
        for(int x : freqMap.keySet()){
            if(freqMap.get(x) == 0) continue;
            int need = x < 0 ? (x / 2) : (x * 2);
            if(x < 0 && x % 2 != 0 || freqMap.get(x) > freqMap.getOrDefault(need, 0)){
                return false;
            }
            freqMap.put(need, freqMap.get(need) - freqMap.get(x));
        }
        return true;
    }


    /* 1138. Alphabet Board Path
     * https://leetcode.com/problems/alphabet-board-path/
     * */
    public String alphabetBoardPath(String target) {
        char[][] board = new char[6][5];
        char ch = 'a';
        HashMap<Character, int[]> locMap = new HashMap<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ch++;
                locMap.put(board[i][j], new int[]{i, j});
                if(ch > 'z')break;
            }
        }
        StringBuilder in = new StringBuilder(target);
        StringBuilder out = new StringBuilder();
        int i = 0, j = 0;
        while (in.length() > 0){
            char cur = in.charAt(0);
            in.deleteCharAt(0);
            int[] dest = locMap.get(cur);
            while (i != dest[0] || j != dest[1]){
                while (i < dest[0] && isValid(i + 1, j, board) ){
                    out.append("D");
                    i++;
                }
                while (dest[0] < i && isValid(i - 1, j, board)){
                    out.append("U");
                    i--;
                }
                while (j < dest[1] && isValid(i, j + 1, board)){
                    out.append("R");
                    j++;
                }
                while (dest[1] < j && isValid(i, j - 1, board)){
                    out.append("L");
                    j--;
                }
            }
            if(i == dest[0] && j == dest[1]){
                out.append("!");
            }
        }
        return out.toString();
    }

    public boolean isValid(int i, int j, char[][] board){
        if(board[i][j] >= 'a' && board[i][j] <= 'z') return true;
        return false;
    }

    /* 791. Custom Sort String
     * https://leetcode.com/problems/custom-sort-string/
     * */
    public String customSortString(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        StringBuilder fromT = new StringBuilder();
        for (int i = 0; i < t.length(); i++) {
            map.put(t.charAt(i), map.getOrDefault(t.charAt(i), 0) + 1);
            if(s.indexOf(t.charAt(i)) < 0){
                fromT.append(t.charAt(i));
            }
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if(map.containsKey(s.charAt(i))){
                int freq = map.get(s.charAt(i));
                while (freq-- > 0){
                    out.append(s.charAt(i));
                }
            }
        }
        out.append(fromT);
        return out.toString();
    }


    /*
    * 933. Number of Recent Calls
    * https://leetcode.com/problems/number-of-recent-calls/
    * */
    static class RecentCounter {
        TreeMap<Integer, Integer> tm;
        public RecentCounter(){
            tm = new TreeMap<>();
        }

        public int ping(int t) {
            tm.put(t, 1 + tm.size());
            return tm.tailMap(t - 3000).size();
        }
    }

    /*
     * 1583. Count Unhappy Friends
     * https://leetcode.com/problems/count-unhappy-friends/
     * */
    public int unhappyFriends(int n, int[][] preferences, int[][] pairs) {
        int res = 0;
        int[] map = new int[n];
        for (int i = 0; i < pairs.length; i++) {
            int x = pairs[i][0];
            int y = pairs[i][1];
            map[x] = y;
            map[y] = x;
        }
        Map<Integer, Integer>[] prefs = new HashMap[n];
        for (int i = 0; i < preferences.length; i++) {
            for (int j = 0; j < preferences[i].length; j++) {
                if(prefs[i] == null) prefs[i] = new HashMap<>();
                prefs[i].put(preferences[i][j], j);
            }
        }

        for (int i = 0; i < n; i++) {
            for(int j : preferences[i]){
                if(prefs[j].get(i) < prefs[j].get(map[j]) && prefs[i].get(map[i]) > prefs[i].get(j)){
                    res++;
                    break;
                }
            }
        }
        return res;
    }

    /* 1577. Number of Ways Where Square of Number Is Equal to Product of Two Numbers
     * https://leetcode.com/problems/number-of-ways-where-square-of-number-is-equal-to-product-of-two-numbers/
     * Two - Product similar to Two - Sum
     * */
    public int numTriplets(int[] nums1, int[] nums2) {
        int count = 0;
        for (long n : nums1) {
            count += twoProduct(n * n, nums2);
        }
        for (long n : nums2) {
            count += twoProduct(n * n, nums1);
        }
        System.out.println(count);
        return count;
    }

    public int twoProduct(long a, int[] nums){
        HashMap<Long, Integer> map = new HashMap<>();
        int count = 0;
        for (long x : nums) {
            if(a % x == 0) {
                count += map.getOrDefault(a / x, 0);
            }
            map.put(x, map.getOrDefault(x, 0) + 1);
        }
        return count;
    }

    /*
     * 149. Max Points on a Line
     * https://leetcode.com/problems/max-points-on-a-line/
     * */
    public int maxPoints(int[][] points) {
        HashMap<Integer, HashMap<Integer, Integer>> map = new HashMap<>();
        int res = 0;
        for (int i = 0; i < points.length; i++) {
            map.clear();
            int overlap = 0;
            int max = 0;
            for (int j = i + 1; j < points.length; j++) {
                int x = points[i][0] - points[j][0];
                int y = points[i][1] - points[j][1];
                if(x == 0 && y == 0){
                    overlap++;
                    continue;
                }
                int gcd = gcd(x, y);
                if(gcd != 0){
                    x = x / gcd;
                    y = y / gcd;
                }
                if(map.containsKey(x)){
                    HashMap<Integer,Integer> m = map.get(x);
                    if(m.containsKey(y)){
                        map.get(x).put(y, m.get(y) + 1);
                    }else{
                        m.put(y, 1);
                        map.put(x, m);
                    }
                }else{
                    HashMap<Integer, Integer> m = new HashMap<>();
                    m.put(y, 1);
                    map.put(x, m);
                }
                max = Math.max(max, map.get(x).get(y));
            }
            res = Math.max(res, max + overlap + 1);
        }
        return res;
    }

    public int gcd(int x, int y){
        if(y == 0) return x;
        return gcd(y,x % y);
    }

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
