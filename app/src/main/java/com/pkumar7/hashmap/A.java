package com.pkumar7.hashmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Pankaj Kumar on 06/September/2020
 */
class A {

    public static void main(String[] args) {

    }

    /*
    * 36. Valid Sudoku
    * https://leetcode.com/problems/valid-sudoku/
    * */
    public boolean isValidSudoku(char[][] board) {
        HashMap<Integer, HashSet<Integer>> colMap = new HashMap<>();
        HashMap<Integer, HashSet<Integer>> rowMap = new HashMap<>();

        for (int i = 0; i < board.length; i++) {
            rowMap.putIfAbsent(i, new HashSet<Integer>());
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == '.') continue;
                int num = board[i][j] - '0';

                colMap.putIfAbsent(j, new HashSet<>());
                if (colMap.get(j).contains(num)) return false;
                colMap.get(j).add(num);

                if (rowMap.get(i).contains(num)) return false;
                rowMap.get(i).add(num);
            }
        }

        for (int i = 0; i < board.length; i+= 3 ) {
            for (int j = 0; j < board[0].length; j+= 3) {
                if (!checkInBoxes(i, j, board)) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkInBoxes(int row, int col, char[][] board) {
        HashSet<Integer> boxes = new HashSet<>();
        for (int i = row; i < row + 3 ; i++ ) {
            for (int j = col; j < col + 3; j++) {
                if (board[i][j] == '.') continue;
                int num = board[i][j] - '0';
                if (boxes.contains(num)) {
                    return false;
                }
                boxes.add(num);
            }
        }
        return true;
    }

    /* 1640. Check Array Formation Through Concatenation
     * https://leetcode.com/problems/check-array-formation-through-concatenation/
     * */
    public boolean canFormArray(int[] arr, int[][] pieces) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        for(int[] p : pieces){
            int first = p[0];
            int idx1 = map.getOrDefault(first, -1);
            if(idx1 < 0) return false;
            for (int i = 1; i < p.length; i++) {
                int idx2 = map.getOrDefault(p[i], -1);
                if(idx2 < 0) return false;
                if(idx1 > idx2) return false;
                if(idx1 + i != idx2) return false;
            }
        }
        return true;
    }

    /* 1679. Max Number of K-Sum Pairs
     * https://leetcode.com/problems/max-number-of-k-sum-pairs/
     * */
    public int maxOperations(int[] nums, int k) {
        return solution1(nums, k);
    }

    public int solution1(int[] nums, int k) {
        Arrays.sort(nums);
        int res = 0;
        int p = nums.length - 1;
        for (int i = 0; i < nums.length; i++) {
            while (i < p && nums[i] + nums[p] > k){
                p--;
            }
            if(i < p && nums[i] + nums[p] == k){
                p--;
                res++;
            }
        }
        return res;
    }


    public int solution2(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            int ele = entry.getKey();
            int freq = entry.getValue();
            int other = k - ele;
            if (other == ele) {
                res += freq / 2;
                int rem = freq % 2;
                map.put(ele, rem);
            } else if (map.containsKey(k - ele)) {
                int other_freq = map.get(k - ele);
                if (other_freq > freq) {
                    map.put(k - ele, other_freq - freq);
                    map.put(ele, 0);
                    res += freq;
                } else {
                    map.put(ele, freq - other_freq);
                    map.put(k - ele, 0);
                    res += other_freq;
                }
            }
        }
        return res;
    }

    /* 825. Friends Of Appropriate Ages
     * https://leetcode.com/problems/friends-of-appropriate-ages/
     */
    public int numFriendRequests(int[] ages) {
        return numFriendRequestsMap(ages);
    }

    public int numFriendRequestsMap(int[] ages){
        HashMap<Integer, Integer> map = new HashMap<>();
        for(int a : ages){
            map.put(a, map.getOrDefault(a, 0) + 1);
        }
        int res =0;
        for(Map.Entry<Integer, Integer> entrya : map.entrySet()){
            for(Map.Entry<Integer, Integer> entryb : map.entrySet()){
                int a = entrya.getKey();
                int b = entryb.getKey();
                int countA = entrya.getValue();
                int countB = entryb.getValue();
                if(canMakeRequest(a, b)){
                    res += countA * countA - (countA == countB ? 1 : 0);
                }
            }
        }
        return res;
    }

    public boolean canMakeRequest(int a, int b){
        if(b <= 0.5 * a + 7) return false;
        if(b > a) return false;
        if(b > 100 && a < 100) return false;
        return true;
    }

    public int numFriendRequestsRollingSum(int[] ages) {
        int[] sumInAges = new int[121];
        int[] numInAges = new int[121];
        for (int i = 0; i < ages.length; i++) {
            int age = ages[i];
            numInAges[age]++;
        }
        for (int i = 1; i <= 120; i++) {
            sumInAges[i] = sumInAges[i - 1] + numInAges[i];
        }

        int res = 0;
        for (int i = 16; i < 121; i++) {
            int count = sumInAges[i] - sumInAges[i / 2 + 7];
            res += (count * numInAges[i]) - numInAges[i];
        }
        return res;
    }

    /*
     * https://leetcode.com/problems/k-empty-slots/
     * 683. K Empty Slots
     * */
    public int kEmptySlots(int[] bulbs, int k) {
        TreeSet<Integer> sets = new TreeSet<>();
        for (int i = 0; i < bulbs.length; i++) {
            Integer low = sets.lower(bulbs[i]);
            Integer high = sets.higher(bulbs[i]);
            if((low != null && bulbs[i] - low - 1 == k) ||
                    (high != null && high - bulbs[i] - 1 == k )){
                return i + 1;
            }
            sets.add(bulbs[i]);
        }
        return -1;
    }

    /* 1658. Minimum Operations to Reduce X to Zero
     * https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/
     * */
    public int minOperations(int[] nums, int x) {
        int target = -x;
        target += Arrays.stream(nums).sum();
        if(target == 0) return nums.length;
        HashMap<Integer, Integer> map = new HashMap<>();
        int sum = 0, res = Integer.MIN_VALUE;
        map.put(0, -1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if(map.containsKey(sum - target)){
                res = Math.max(res, i - map.get(sum - target));
            }
            map.put(sum, i);
        }
        return res == Integer.MIN_VALUE ? -1 : nums.length - res;
    }

    /* 1166. Design File System
     * https://leetcode.com/problems/design-file-system/
     * */
    class FileSystem {
        HashMap<String, Integer> map;
        public FileSystem() {
            map = new HashMap<>();
        }

        public boolean createPath(String path, int value) {
            if(map.containsKey(path)){
                return false;
            }
            String parent = path.substring(0, path.lastIndexOf("/"));
            if(parent.length() > 0 && !map.containsKey(parent)){
                return false;
            }
            map.put(path, value);
            return true;
        }

        public int get(String path) {
            return map.getOrDefault(path, -1);
        }
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
