package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class DecemberW3 {
    
    /*  399. Evaluate Division
        https://leetcode.com/problems/evaluate-division/

        Given a / b = 2.0, b / c = 3.0.
        queries are: a / c = ?, b / a = ?, a / e = ?, a / a = ?, x / x = ? .
        return [6.0, 0.5, -1.0, 1.0, -1.0 ]

        equations = [ ["a", "b"], ["b", "c"] ],
        values = [2.0, 3.0]
        queries = [ ["a", "c"], ["b", "a"], ["a", "e"], ["a", "a"], ["x", "x"] ]. 
    */
    public double[] calcEquation(List<List<String>> e, double[] v, List<List<String>> q) {
        double[] result = new double[q.size()];
        HashMap<String, Double> dist = new HashMap<>();
        HashMap<String, String> root = new HashMap<>();      

        for(int i = 0; i < e.size(); i++){
            List<String> curr = e.get(i);
            String a = curr.get(0);
            String b = curr.get(1);

            String parent_a = findParent(a, dist, root);
            String parent_b = findParent(b, dist, root);

            root.put(parent_a, parent_b);
            dist.put(parent_a, dist.get(b) * v[i] / dist.get(a));
        }

        for(int i =0; i < q.size(); i++){
            List<String> curr = q.get(i);
            
            String a = curr.get(0);
            String b = curr.get(1);

            if(!root.containsKey(a) || !root.containsKey(b)){
                result[i] = -1.0;
                continue;
            }

            String parent_a = findParent(a, dist, root);
            String parent_b = findParent(b, dist, root);

            if(!parent_a.equals(parent_b)){ //Not in same set
                result[i] = -1.0;
                continue;
            }

            result[i] = (double) dist.get(a) / dist.get(b);
        }

        return result;
    }

    public String findParent(String s, HashMap<String, Double> dist, HashMap<String, String> root){
        if(!root.containsKey(s)){
            dist.put(s, 1.0);
            root.put(s, s);
            return s;
        }
        if(root.get(s).equals(s)) return s;
        String last_parent = root.get(s);
        String parent = findParent(last_parent, dist, root);
        root.put(s, parent);
        dist.put(s, dist.get(s) * dist.get(last_parent));
        return parent;
    }

    public static void main(String[] args) {

        DecemberW3 w3 = new DecemberW3();
        /*String num = "123";
        int val = 0;
        for(char ch : num.toCharArray()){
            val = val*10 + ch - '0';
            System.out.println("ch "+ ch + " val " + val);
        }*/
        //String res = w3.decodeString("3[z]2[2[y]pq4[2[jk]e1[f]]]ef");
        //String res = w3.numberToWords(1234567);
        //String[] word = new String[]{"wr","er","rf"};
        //w3.alienOrder(word);

        //w3.maxFreq("aababcaab", 2, 3, 4);
        //w3.isAlienSorted(new String[]{"word","world","row"}, "worldabcefghijkmnpqstuvxyz");

        String str = "ABC";
        int n = str.length();

        //w3.getPermutationString(4, 4);
        //w3.restoreIpAddresses("25525511135");
        //w3.restoreIpAddressesNew("25525511135");


        //System.out.println(ans + " ");


        //HashSet<Character> sets = new HashSet<>();
        //w3.getPermutationAnother(3, 3);
        //w3.helper(n, str, new StringBuilder(), sets);

        //w3.isMatch("aab","a*b");

        //System.out.println(res);

        //w3.grayCodeRec(3, new ArrayList<>());
        //w3.findAnagrams("cbaebabacd","abc");

        String[] words = new String[]{"good","best","word"};

        //w3.findSubstring("wordgoodgoodgoodbestword", words);
        //w3.sumZero(6);
        //int[] nums = new int[]{3,0,2,1,2};
        //w3.canReach(nums, 2);
        int[][] grid = new int[][]{ {1,1,1,1},
                                    {1,1,1,1},
                                    {1,1,1,1},
                                    {1,1,1,1} };

        //w3.colorBorder(grid, 1,1, 2);
        w3.asteroidCollision(new int[]{5, 10, -5});
    }

    /* 
        https://leetcode.com/problems/decode-string/

        s = "3[a]2[bc]", return "aaabcbc".
        s = "3[a2[c]]", return "accaccacc".
        s = "2[abc]3[cd]ef", return "abcabccdcdcdef".
    */
    class IntegerRef {
        int val;
        public IntegerRef(int ele){
            val = ele;
        }
    }
    public String decodeString(String s) {
        IntegerRef pos = new IntegerRef(0);
        return decodeStringRec(s, pos);
    }

    public String decodeStringRec(String s, IntegerRef idx) {
        StringBuilder builder = new StringBuilder();

        int num = 0;

        for(; idx.val < s.length(); idx.val++){
            char ch = s.charAt(idx.val);
            if(ch == '['){ // a - z
                idx.val++;
                String toAppend = decodeStringRec(s, idx);
                for(; num > 0; num--)
                    builder.append(toAppend);
                
            }else if(ch >= '0' && ch <= '9'){ // 1,2,3 , ..
                num = num*10 + ch - '0';
            }else if(ch == ']'){
                return builder.toString();
            }else{ // ']'
                builder.append(ch);
            }
        }
        return builder.toString();
    }

    /* https://leetcode.com/problems/integer-to-english-words/ 
        num = 1234567891, 1234567, 12345

    */
    String[] LESS_THAN_20 = new String[]{"Zero","One","Two","Three",
            "Four","Five","Six","Seven","Eight","Nine","Ten","Eleven","Twelve","Thirteen","Forteen",
            "Fiften","Sixteen","Seventeen","Eighteen","Ninteen"};
    String[] TENS = new String[]{"","Ten","Twenty","Thirty","Forty","Fifty",
            "Sixty","Seventy","Eighty","Ninety"};
    String[] THOUSANDS = new String[]{"","Thousand","Million","Billion"};

    public String numberToWords(int num) {

        String words = "";

        int idx = 0;

        while(num > 0){

            if(num % 1000 != 0){
                words = helper(num % 1000) + THOUSANDS[idx] + " " + words;
            }
            num /= 1000;
            idx++;
        }
        return words.trim();
    }

    public String helper(int num){
        if(num == 0)
            return "";
        else if(num < 20)
            return LESS_THAN_20[num] + " ";
        else if(num < 100)
            return TENS[num / 10] + " " + helper(num % 10);
        else
            return LESS_THAN_20[num / 100] + " Hundred " + helper(num % 100);
    }

    /* https://leetcode.com/problems/alien-dictionary/
       Topological Sorting
       Revisit : Important
    */
    public String alienOrder(String[] words) {
        Map<Character, ArrayList<Character>> adjList = new HashMap<>();
        Map<Character, Integer> degrees = new HashMap<>();
        for (String word : words) {
            for (char ch : word.toCharArray()) {
                degrees.put(ch, 0);
                adjList.putIfAbsent(ch, new ArrayList<>());
            }
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i];
            String second = words[i + 1];
            if(first.length() > second.length() && first.startsWith(second)){
                return "";
            }
            int length = Math.min(first.length(), second.length());
            for (int j = 0; j < length; j++) {
                char curr = first.charAt(j);
                char neig = second.charAt(j);
                if (curr != neig) {
                    adjList.get(curr).add(neig);
                    degrees.put(neig, degrees.get(neig) + 1);
                    break;
                }
            }
        }
        Queue<Character> q = new LinkedList<>();
        for (char ch : degrees.keySet()) {
            if (degrees.get(ch) == 0) {
                q.offer(ch);
            }
        }
        while (!q.isEmpty()) {
            char curr = q.poll();
            builder.append(curr);
            if (adjList.containsKey(curr)) {
                for (char ch : adjList.get(curr)) {
                    degrees.put(ch, degrees.get(ch) - 1);
                    if (degrees.get(ch) == 0) {
                        q.offer(ch);
                    }
                }
            }
        }
        return builder.toString().length() == degrees.size() ? builder.toString() : "";
    }

    /* https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
        [
          [9,9,4],
          [6,6,8],
          [2,1,1]
        ] 

        [
          [3,4,5],
          [3,2,6],
          [2,2,1]
        ] 
     */
    public int longestIncreasingPath(int[][] matrix) {
        if(matrix == null || matrix.length == 0) return 0;

        int longestIncreasingPath = 0;
        
        for(int i = 0; i < matrix.length; i++){
            for(int j = 0; j < matrix[0].length; j++){
                longestIncreasingPath = Math.max(longestIncreasingPath, dfs(matrix, i, j));
            }
        }
        return longestIncreasingPath;
    }

    public int dfsmemo(int[][] matrix, int curr_x, int curr_y, int[][] memo){
        if(memo[curr_x][curr_y] > 0)
            return memo[curr_x][curr_y];
        
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[][] dirs = new int[][]{{1,0}, {0,1}, {0,-1},{-1,0}};
        
        int curr = matrix[curr_x][curr_y];

        int max = 0;
        
        for(int[] dir :  dirs) {
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;

            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n){
                if(curr < matrix[next_x][next_y]){
                    max = Math.max(max, dfsmemo(matrix, next_x, next_y, memo));
                }
            }
        }
        memo[curr_x][curr_y] = max + 1;
        return memo[curr_x][curr_y];
    }

    public int dfs(int[][] matrix, int curr_x, int curr_y){
        int m = matrix.length;
        int n = matrix[0].length;
        
        int[][] dirs = new int[][]{{1,0}, {0,1}, {0,-1},{-1,0}};
        
        int curr = matrix[curr_x][curr_y];

        int max = 0;

        for(int[] dir :  dirs) {
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;

            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n){
                if(curr < matrix[next_x][next_y]){
                    max = Math.max(max, dfs(matrix, next_x, next_y));
                }
            }
        }
        return max + 1;
    }

    /* 
        https://leetcode.com/problems/sequence-reconstruction/

        Topological Sort

        Input:
        org: [1,2,3], seqs: [[1,2],[1,3]]
    */
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {

        Map<Integer, Integer> indegree = new HashMap<Integer, Integer>();

        Map<Integer, HashSet<Integer>> dep = new HashMap<Integer, HashSet<Integer>>();

        Set<Integer> unique = new HashSet<>();

        if(seqs.isEmpty()) return false;
        if(org.length == 0) return false;

        for(List<Integer> list : seqs){

            if(list.isEmpty()) continue;

            int first_ele = list.get(0);

            unique.add(first_ele);

            if(!indegree.containsKey(first_ele)){
                indegree.put(first_ele, 0);
            }
            for(int i = 1; i < list.size(); i++){
                HashSet<Integer> sets = new HashSet<>();
                int curr = list.get(i);
                int prev = list.get(i - 1);

                unique.add(curr);
                unique.add(prev);

                if(dep.containsKey(prev)){
                    sets = dep.get(prev);
                }
                if(!sets.contains(curr)){
                    sets.add(curr);
                    dep.put(prev, sets);

                    indegree.put(curr, indegree.getOrDefault(curr, 0) + 1);
                }
            }
        }

        Queue<Integer> q = new LinkedList<>();

        for(Integer curr : indegree.keySet()){
            if(indegree.get(curr) == 0){
                q.offer(curr);
            }
        }

        int idx = 0;
        while(!q.isEmpty()){
            int size = q.size();
            if(size > 1) return false;
            while(size-- > 0){
                int curr = q.poll();

                if(idx == org.length || curr != org[idx++]) return false;

                HashSet<Integer> dependencies = dep.get(curr);
                if(dependencies == null) continue;
                for(Integer next : dependencies){
                    int degree = indegree.get(next);
                    degree--;
                    indegree.put(next, degree);
                    if(degree == 0){
                        q.offer(next);
                    }
                }
            }
        }
        return idx == org.length && idx == unique.size();
    }

    

    /* 
        https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/ 

        Revisit: To understand how bit shifting is working here.. !!
    */
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        int n = s.length();

        Map<String, Integer> map = new HashMap<>();

        for(int i = 0; i < n; i++){
            for(int j = minSize; j <= maxSize && i+j <= n; j++ ){
                String ss = s.substring(i, i+j);

                Set<Character> set = new HashSet<>();
                int f = 0;
                for(char ch : ss.toCharArray()){
                    //set.add(ch);
                    f |= 1 << ch; 
                }
                if(Integer.bitCount(f) <= maxLetters){ //if(set.size() <= maxLetters){
                    if(!map.containsKey(ss)){
                        map.put(ss, 1);
                    }else{
                        map.put(ss, map.get(ss) + 1);
                    }
                }else{
                    break;
                }
            }
        }

        int max = 0;
        for(Map.Entry<String, Integer> entry : map.entrySet()){
            max = Math.max(max, entry.getValue());
        }
        return max;
    }

    public int maxFreqFailed(String s, int maxLetters, int minSize, int maxSize) {
        int n = s.length();

        int maxsub = 0;

        for(int i = 0 ; i < n; i++){
            int maxAllowed = maxSize;
            Set<Character> set = new HashSet<>();
            int j = i;
            StringBuilder builder = new StringBuilder();
            while(maxAllowed-- > 0 && j < n){
                set.add(s.charAt(j));
                builder.append(s.charAt(j));
                j++;
                if(set.size() > maxLetters){
                    break;
                }
            }
            String sub = builder.toString();
            if(sub.length() >= minSize && sub.length() <= maxSize){
                maxsub = Math.max(maxsub, KMPSearch(sub, s));
            }
        }
        return maxsub;
    }

    public int KMPSearch(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();

        int lps[] = new int[M];
        int j = 0; // index for pat[]

        int occurances = 0;
        computeLPSArray(pat, M, lps);

        int i = 0;
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                System.out.println("Found pattern "
                        + "at index " + (i - j));
                occurances++;
                j = lps[j - 1];
            }

            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
        return occurances;
    }

    void computeLPSArray(String pat, int M, int lps[]) {
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
    }

    public boolean isPossibleDivide(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k == 0)
            return false;
        
        if(nums.length % k != 0){
            return false;
        }
        Arrays.sort(nums);
        boolean[] visited = new boolean[nums.length];
        
        List<Integer> res = new ArrayList<>();
        for(int i = 0; i < nums.length; i++){
            if(!visited[i]){
                if(!helper(nums, k, i, visited, res)){
                     //stem.out.println("Returning false !!");
                     return false;
                }else{
                    res = new ArrayList<>();    
                }
            }
        }
        return true;
    }

    /*
        https://leetcode.com/problems/divide-array-in-sets-of-k-consecutive-numbers/
    */

    public boolean isPossibleDivideSol(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k == 0)
            return false;

        if(nums.length % k != 0){
            return false;
        }
        Arrays.sort(nums);

        Map<Integer, Integer> freq = new HashMap<>();

        for(int ele : nums){
            freq.put(ele, freq.getOrDefault(ele, 0) + 1 );
        }

        for(int i = 0; i < nums.length; i++){
            if(freq.get(nums[i]) > 0){
                for(int j = 0; j < k; j++){
                    if(!freq.containsKey(nums[i] + j)){
                        return false;
                    }
                    if(freq.get(nums[i] + j) <= 0){
                        return false;
                    }
                    freq.put(nums[i] + j, freq.get(nums[i] + j) -1);
                }
            }
        }
        return true;
    }
    
    public boolean helper(int[] nums, int k, int i, boolean[] visited, List<Integer> res){
        if(res.size() == k){
            return true;
        }
        
        if(i == nums.length){
            return false;
        }
        
        if(res.isEmpty()){
            res.add(nums[i]);
            visited[i] = true;
            i++;
        }
        
        if(i == nums.length){
            return false;
        }
        
        if(res.get(res.size()-1)+1 == nums[i] && !visited[i]){
            res.add(nums[i]);
            visited[i] = true;
        }
        
        return helper(nums, k, i+1, visited, res);
    }

    /* 
        https://leetcode.com/problems/element-appearing-more-than-25-in-sorted-array/ 
    */
    public int findSpecialInteger(int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        
        int n = arr.length;

        int freq = n / 4;
        
        for(int ele : arr){
            map.put(ele, map.getOrDefault(ele, 0) + 1);
            if(map.get(ele) > freq){
                return ele;
            }
        }
        
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            
            double perc = (double)entry.getValue() / n;
            
            if((perc*100) > 25){ 
                return entry.getKey();
            }
        }
        return -1;
    }

    /* 
        https://leetcode.com/problems/remove-covered-intervals/
        Line-Sweeping
        Intervals
    */
    public int removeCoveredIntervalsPass(int[][] intervals) {
        Arrays.sort(intervals, (a,b) -> (a[0] - b[0]));

        int left = -1, right = -1;
        int uncovered = 0;

        for(int[] interval : intervals){
            if(interval[0] > left && interval[1] > right) {
                uncovered++;
                left = interval[0];
            }
            right = Math.max(right, interval[1]);
        }

        return uncovered;
    }
    
    public int removeCoveredIntervals(int[][] intervals){
        Arrays.sort(intervals, (a,b) -> a[0] - b[0]);

        int n = intervals.length;

        int[] curr = intervals[0];
        int removals = 0;
        for(int[] interval : intervals){
            if(curr[0] <= interval[0] && curr[1] >= interval[1]){
                removals++;
            }else{
                curr = interval;    
            }
        }

        return n - removals + 1;//First interval is also removed
    }

    /* 
        https://leetcode.com/problems/remove-interval/ 
    */
    public List<List<Integer>> removeInterval(int[][] intervals, int[] toBeRemoved) {
        List<List<Integer>> res = new ArrayList<>();

        for(int[] interval : intervals){
            if(interval[1] > toBeRemoved[0] && interval[0] < toBeRemoved[0]){
                List<Integer> curr = new ArrayList<>();
                curr.add(interval[0]);
                curr.add(toBeRemoved[0]);
                res.add(curr);
                if(interval[1] > toBeRemoved[1]){
                    List<Integer> another = new ArrayList<>();
                    another.add(toBeRemoved[1]);
                    another.add(interval[1]);
                    res.add(another);
                }
            }else if(interval[1] > toBeRemoved[1] && interval[0] < toBeRemoved[1]){
                List<Integer> curr = new ArrayList<>();
                curr.add(toBeRemoved[1]);
                curr.add(interval[1]);
                res.add(curr);
            }else if(interval[0] >= toBeRemoved[1] || interval[1] <= toBeRemoved[0]){
                List<Integer> curr = new ArrayList<>();
                curr.add(interval[0]);
                curr.add(interval[1]);
                res.add(curr);
            }
        }
        return res;
    }

    /*  https://leetcode.com/problems/verifying-an-alien-dictionary/
        Input: words = ["hello","leetcode"], order = "hlabcdefgijkmnopqrstuvwxyz"
        Output: true
     */
   public boolean isAlienSorted(String[] words, String order) {
        for(int i = 0; i < words.length - 1; i++){
            String first = words[i];
            String second = words[i + 1];

            int len = Math.min(first.length(), second.length());

            for(int j = 0; j < len; j++){
                char c1 = first.charAt(j);
                char c2 = second.charAt(j);
                
                if(c1 == c2) continue;
                
                int idx_f = order.indexOf(c1);
                int idx_s = order.indexOf(c2);
                
                if(idx_f < idx_s)
                    break;
                else
                    return false;
            }
        }
        return true;
    }

    /* https://leetcode.com/problems/find-numbers-with-even-number-of-digits/ */
    public int findNumbers(int[] nums) {
        int even = 0;
        
        for(int i = 0; i < nums.length; i++){
            
            int num = nums[i];
            int count = 0;
            while(num > 0){
                count++;
                num /= 10;
            }
            if(count % 2 == 0){
                even++;
            }
        }
        return even;
    }

    /* https://leetcode.com/problems/iterator-for-combination/ */
    class CombinationIterator {

        PriorityQueue<String> q = new PriorityQueue<>();
        public CombinationIterator(String s, int k) {
            generateComb(s, k, 0, new StringBuilder());
        }

        public void generateComb(String s, int k, int start, StringBuilder builder){
            if(k == 0){
                q.offer(builder.toString());
                return;
            }

            for(int i = start; i <= s.length() - k; i++){
                builder.append(s.charAt(i));
                generateComb(s, k -1, i + 1, builder);
                builder.deleteCharAt(builder.length()-1);
            }
        }

        public String next() {
            if(!q.isEmpty()){
                return q.poll();
            }
            return "";
        }

        public boolean hasNext() {
            return !q.isEmpty();
        }
    }

    /* 
        https://leetcode.com/problems/combinations/submissions/ 
    */
    public List<List<Integer>> combine(int n, int k) {
        helper(1, n, k, new ArrayList<>());
        
        return all_comb;
    }
    
    List<List<Integer>> all_comb = new ArrayList<>();
    
    public void helper(int start, int n, int k, List<Integer> res){
        if(k == 0){
            all_comb.add(new ArrayList<Integer>(res));
            return;
        }
        
        for(int i = start; i <= n; i++){
            
            res.add(i);
            
            helper(i + 1, n, k-1, res);
            
            res.remove(res.size()-1);
        }
    }

    /* 
        https://leetcode.com/problems/permutation-sequence/
        Permutations, BackTracking
    */
    public String getPermutation(int n, int k) {
        StringBuilder sb = new StringBuilder();
        ArrayList<Integer> num = new ArrayList<Integer>();
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
            num.add(i);
        }
        for (int i = 0, l = k - 1; i < n; i++) {
            fact /= (n - i);
            int index = (l / fact);
            sb.append(num.remove(index));
            l -= index * fact;
        }
        return sb.toString();
    }

    public String getPermutationAnother(int n, int k) {
        LinkedList<Integer> nums = new LinkedList<>();

        int[] fact = new int[n];

        fact[0] = 1;

        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }

        for (int i = 1; i < n; i++) {
            fact[i] = i * fact[i-1];
        }

        k = k -1;

        StringBuilder builder = new StringBuilder();
        for (int i = n; i > 0; --i) {
            int idx = k / fact[i -1];
            k = k % fact[i-1];
            builder.append(nums.remove(idx));
        }
        return builder.toString();
    }

    public String getPermutationInt(int n, int k) {
        HashSet<Integer> sets = new HashSet<>();

        List<List<Integer>> res = new ArrayList<>();

        helperPerm(n, k, sets, new ArrayList<Integer>(), res);

        System.out.println(ans);

        return ans;
    }

    private void helperPerm(int n, int k, HashSet<Integer> sets, ArrayList<Integer> res, List<List<Integer>> allPerm) {

        if (allPerm.size() > k) {
            return;
        }

        if (res.size() == n) {
            allPerm.add(new ArrayList<Integer>(res));
            if (k == allPerm.size() && ans.length() == 0) {
                List<Integer> finalans = allPerm.get(allPerm.size() - 1);
                for (int ele : finalans) ans += ele;
            }
            return;
        }

        for (int i = 1; i <= n ; i++) {
            if (allPerm.size() > k) break;

            if (sets.contains(i)) continue;

            sets.add(i);
            res.add(i);

            helperPerm(n, k, sets, res, allPerm);

            res.remove(res.size() - 1);
            sets.remove(i);
        }
    }

    public String getPermutationString(int n, int k) {
        HashSet<Character> sets = new HashSet<>();

        List<List<Character>> res = new ArrayList<>();

        helperString(n, k, sets, new ArrayList<Character>(), res);

        System.out.println(ans);

        return ans;
    }

    String ans = "";

    public void helperString(int n, int k, HashSet<Character> sets, ArrayList<Character> res, List<List<Character>> allPerm) {

        if (allPerm.size() > k) {
            return;
        }

        if (res.size() == n) {
            allPerm.add(new ArrayList<>(res));
            if (k == allPerm.size() && ans.length() == 0) {
                List<Character> finalans = allPerm.get(allPerm.size() - 1);
                for (char ele : finalans) ans += ele;
            }
            return;
        }

        for (char i = 'A'; i <= 'D' ; i++) {
            if (allPerm.size() > k) break;

            if (sets.contains(i)) continue;

            sets.add(i);
            res.add(i);

            helperString(n, k, sets, res, allPerm);

            res.remove(res.size() - 1);
            sets.remove(i);
        }
    }

    /*  Revisit: Important to understand recursion
        Permutation and combination
        https://leetcode.com/problems/regular-expression-matching/submissions/ 
    */
    public boolean isMatch(String s, String p) {
        return isMatchRegEx(s, p);
    }

    public boolean isMatchRegEx(String s, String p) {
        if (p.length() == 0) {
            return s.length() == 0;
        }

        if (p.length() > 1 && p.charAt(1) == '*') {
            if (isMatchRegEx(s, p.substring(2))) {
                return true;
            }
            if (s.length() > 0 && ((p.charAt(0) == '.') || s.charAt(0) == p.charAt(0))) {
                return isMatchRegEx(s.substring(1), p);
            }
            return false;
        } else {
            if (s.length() > 0 && (p.charAt(0) == '.' || s.charAt(0) == p.charAt(0))) {
                return isMatchRegEx(s.substring(1), p.substring(1));
            }
            return false;
        }
    }

    /* https://leetcode.com/playground/WoCRnny8
       String Permutations */
    public void permutations(){
        String str = "ABC";
        int n = str.length();
        helper(n, str, new StringBuilder(), new boolean[n]);
    }

    public void helper(int len, String str, StringBuilder builder, boolean[] visited){
        if(str.length() == builder.length()){
            System.out.println(builder.toString());
            return;
        }
        for(int i = 0; i < len; i++){
            if(i > 0 && str.charAt(i) == str.charAt(i -1) && !visited[i-1] ) continue;

            if(visited[i]) continue;

            builder.append(str.charAt(i));

            visited[i] = true;

            helper(len, str, builder, visited);

            builder.deleteCharAt(builder.length() -1);

            visited[i] = false;
        }
    }

    /* https://leetcode.com/problems/restore-ip-addresses/ 

        Permutations
        Revisit
        Input: "25525511135"
        Output: ["255.255.11.135", "255.255.111.35"]
    */
    public List<String> restoreIpAddresses(String s) {
        int n = s.length();
        List<String> res = new ArrayList<>();
        if(s.length() == 0)
            return res;

        for(int i = 1; i < n && i < 4; i++) {
            String first = s.substring(0, i);
            if(!isValidAddress(first))
                continue;
            for(int j = 1; i + j < n && j < 4; j++) {
                String second = s.substring(i, i + j);
                if(!isValidAddress(second))
                    continue;

                for(int k = 1; i + j + k < n && k < 4; k++) {
                    String third = s.substring(i + j, i + j + k);
                    String fourth = s.substring(i + j + k);

                    if(!isValidAddress(third))
                        continue;

                    if(!isValidAddress(fourth))
                        continue;

                    res.add(first + "." + second + "." + third + "." + fourth);
                }
            }
        }

        System.out.println(res);
        return res;
    }

    public boolean isValidAddress(String s){
        if(s.length() > 3){
            return false;
        }

        //0 is valid, but 00, 01 etc not valid
        if(s.startsWith("0") && s.length() > 1){
            return false;
        }

        int val = Integer.parseInt(s);

        return val >= 0 && val <= 255;
    }

    public void restoreIpAddressesNew(String s){

        List<String> result = new ArrayList<>();
        restoreIpAddressesRec("", s, 0 , result );

        System.out.println(result);
    }

    public void restoreIpAddressesRec(String path , String s, int segment, List<String> result) {
        if(s.isEmpty() || segment == 4){
            if(s.isEmpty() && segment == 4)
                result.add(path.substring(1));

            return;
        }
        int from = 1;
        int to = s.charAt(0) == '0' ? 1 : 3;

        for(int i = from; i <= to && i <= s.length(); i++ ){
            String part = s.substring(0, i);
            if(isValidAddress(part)){
                restoreIpAddressesRec(path + "." + part, s.substring(i),  segment + 1, result);
            }
        }
    }

    /* https://leetcode.com/problems/diagonal-traverse/ */
    public int[] findDiagonalOrder(int[][] matrix) {
        Stack<int[]> s1 = new Stack<>();
        Stack<int[]> s2 = new Stack<>();

        if(matrix == null || matrix.length == 0 ) return new int[0];

        s1.push(new int[]{0, 0});

        int idx = 0;

        int[][] dirs = new int[][]{{0, 1}, {1, 0}};

        int m = matrix.length;
        int n = matrix[0].length;

        int[] result = new int[m * n];

        boolean[][] visited = new boolean[m][n];

        visited[0][0] = true;

        while (!s1.isEmpty() || !s2.isEmpty()) {

            while (!s1.isEmpty()) {
                int[] curr = s1.pop();

                result[idx++] = matrix[curr[0]][curr[1]];

                for (int i = dirs.length - 1; i >= 0; --i) {

                    int next_x = curr[0] + dirs[i][0];
                    int next_y = curr[1] + dirs[i][1];

                    if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && !visited[next_x][next_y]) {
                        s2.push(new int[]{next_x, next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }

            while (!s2.isEmpty()) {
                int[] curr = s2.pop();

                result[idx++] = matrix[curr[0]][curr[1]];

                for (int[] dir : dirs) {

                    int next_x = curr[0] + dir[0];
                    int next_y = curr[1] + dir[1];

                    if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && !visited[next_x][next_y]) {
                        s1.push(new int[]{next_x, next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }
        }
        return result;
    }

    /*
        https://leetcode.com/problems/gray-code/
    */
    public List<Integer> grayCode(int n) {

        List<Integer> result = new ArrayList<>();

        result.add(0);

        for(int i = 0; i < n; i++){
            int inc = 1 << i;

            for(int j = result.size() -1 ; j >= 0; --j){
                result.add(result.get(j) + inc);
            }
        }

        return result;
    }

    public void grayCodeRec(int n, List<Integer> result) {

        if(n == 0){
            result.add(0);
            return;
        }
        if(n == 1){
            result.add(0);
            result.add(1);
            return;
        }

        grayCodeRec(n -1, result);

        for(int i = result.size() -1; i >= 0; --i ){
            result.add(result.get(i));
        }

        int size = result.size();

        for(int i = size/2; i < size; i++ ){
            String binaryStr = Integer.toBinaryString(result.get(i));
            while(binaryStr.length() < n - 1){
                binaryStr  = "0" + binaryStr;
            }
            binaryStr = "1" + binaryStr;
            int ele = Integer.parseInt(binaryStr, 2);
            result.set(i, ele);
        }
    }

    /* https://leetcode.com/problems/reconstruct-itinerary/ 

        Revisit , Important */

    public List<String> findItinerary(List<List<String>> tickets) {
           
           HashMap<String, PriorityQueue<String>> map = new HashMap<String, PriorityQueue<String>>();

           for(List<String> list : tickets ){
                String src = list.get(0);
                String dst = list.get(1);

                PriorityQueue<String> pq = new PriorityQueue<>();

                map.putIfAbsent(src, new PriorityQueue<>());
                map.get(src).offer(dst);
           }

           helperItinerary(map, "JFK");

           return route;
    }

    LinkedList<String> route = new LinkedList<>();

    public void helperItinerary(HashMap<String, PriorityQueue<String>> map, String src){
        while(map.containsKey(src) && !map.get(src).isEmpty()){
            String adj = map.get(src).poll();
            helperItinerary(map, adj);
        }

        route.addFirst(src);
    }

    /* https://leetcode.com/problems/find-all-anagrams-in-a-string/submissions/ */
    public List<Integer> findAnagramsTLE(String s, String p) {

        HashMap<Character, Integer> p_map = new HashMap<Character, Integer>();

        for(char ch : p.toCharArray()){
            p_map.put(ch, p_map.getOrDefault(ch, 0) + 1 );
        }

        HashMap<Character, Integer> s_map = new HashMap<Character, Integer>();

        List<Integer> result = new ArrayList<>();

        int n = s.length();

        for(int i = 0; i < s.length(); i++) {

            int j = i;

            s_map.putAll(p_map);
            char next_char = s.charAt(j);

            while(p_map.containsKey(next_char) && p_map.get(next_char) > 0){
                p_map.put(next_char, p_map.get(next_char) -1);
                j++;
                if(j == n)
                    break;

                next_char = s.charAt(j);
            }

            Iterator<Character> it = p_map.keySet().iterator();

            boolean found = true;

            while(it.hasNext()){
                Character ch = it.next();
                if(p_map.get(ch) > 0){
                    found = false;
                    break;
                }
            }

            p_map.clear();

            if(found){
                result.add(i);
            }

            p_map.putAll(s_map);
        }

        return result;
    }

    /*
    *   s: "cbaebabacd" p: "abc"
    * https://leetcode.com/problems/find-all-anagrams-in-a-string/
    * Sliding Window Problem
    */
    public List<Integer> findAnagrams(String s, String p) {
        int plen = p.length();
        int slen = s.length();

        HashMap<Character, Integer> p_map = new HashMap<Character, Integer>();

        for(char ch : p.toCharArray()){
            p_map.put(ch, p_map.getOrDefault(ch, 0) + 1 );
        }

        int counter = p_map.size();
        int begin = 0, end = 0;
        List<Integer> result = new ArrayList<>();

        while(end < slen){
            char ch = s.charAt(end);
            if(p_map.containsKey(ch)){
                p_map.put(ch, p_map.get(ch) - 1);
                if(p_map.get(ch) == 0) counter--;
            }
            end++;

            while (counter == 0){
                char tempch = s.charAt(begin);
                if(p_map.containsKey(tempch)){
                    p_map.put(tempch, p_map.get(tempch) + 1);
                    if(p_map.get(tempch) > 0) counter++;
                }

                if(end - begin == plen)
                    result.add(begin);

                begin++;
            }
        }
        return result;
    }

    /*
    * Sliding window template
    * */
    public List<Integer> slidingWindowTemplate(String s, String t) {
        //init a collection or int value to save the ans according the question.
        List<Integer> result = new LinkedList<>();
        if(t.length()> s.length()) return result;

        //create a hashmap to save the Characters of the target substring.
        //(K, V) = (Character, Frequence of the Characters)
        Map<Character, Integer> map = new HashMap<>();
        for(char c : t.toCharArray()){
            map.put(c, map.getOrDefault(c, 0) + 1);
        }
        //maintain a counter to check whether match the target string.
        int counter = map.size();//must be the map size, NOT the string size because the char may be duplicate.

        //Two Pointers: begin - left pointer of the window; end - right pointer of the window
        int begin = 0, end = 0;

        //the length of the substring which match the target string.
        int len = Integer.MAX_VALUE;

        //loop at the begining of the source string
        while(end < s.length()){

            char c = s.charAt(end);//get a character

            if( map.containsKey(c) ){
                map.put(c, map.get(c)-1);// plus or minus one
                if(map.get(c) == 0) counter--;//modify the counter according the requirement(different condition).
            }
            end++;

            //increase begin pointer to make it invalid/valid again
            while(counter == 0 /* counter condition. different question may have different condition */){

                char tempc = s.charAt(begin);//***be careful here: choose the char at begin pointer, NOT the end pointer
                if(map.containsKey(tempc)){
                    map.put(tempc, map.get(tempc) + 1);//plus or minus one
                    if(map.get(tempc) > 0) counter++;//modify the counter according the requirement(different condition).
                }

                /* save / update(min/max) the ans if find a target*/
                // ans collections or ans int value

                begin++;
            }
        }
        return result;
    }

    /*
       Sliding Window
    *  Input: "eceba"
    *  https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/
    *
    * */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        int maxLength = 0;

        Map<Character, Integer> map = new HashMap<>();

        int counter = 0;
        int begin = 0, end = 0;

        int len = Integer.MAX_VALUE;

        while(end < s.length()){

            char c = s.charAt(end);

            map.put(c, map.getOrDefault(c, 0) + 1);

            if(map.get(c) == 1) counter++;

            end++;

            while(counter > 2){

                char tempc = s.charAt(begin);
                map.put(tempc, map.get(tempc) - 1);
                if(map.get(tempc) == 0) counter--;
                begin++;
            }
            maxLength = Math.max(maxLength, end - begin);
        }
        return maxLength;
    }

    /*
        Sliding Window
        https://leetcode.com/problems/longest-substring-with-at-most-k-distinct-characters/
    */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int maxLength = 0;

        Map<Character, Integer> map = new HashMap<>();

        int counter = 0;

        int begin = 0, end = 0;

        int len = Integer.MAX_VALUE;

        while(end < s.length()){

            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);

            if(map.get(c) == 1) counter++;

            end++;

            while(counter > k){
                char tempc = s.charAt(begin);
                map.put(tempc, map.get(tempc) - 1);
                if(map.get(tempc) == 0) counter--;
                begin++;
            }
            maxLength = Math.max(maxLength, end - begin);
        }
        return maxLength;
    }

    /* https://leetcode.com/problems/substring-with-concatenation-of-all-words/ 
       Revisit
    */
    public List<Integer> findSubstring(String S, String[] L) {
        
        List<Integer> result = new LinkedList<>();

        if(S == null || S.length() == 0 || L == null || L.length == 0 || S.length() < L.length * L[0].length())
            return result;

        int M = L.length;

        int N = S.length();

        int wl = L[0].length();

        Map<String, Integer> map = new HashMap<>(), currMap = new HashMap<>();

        for(String word : L ) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }

        int counter = 0, begin = 0, end = 0;

        int len = Integer.MAX_VALUE;

        for(int i = 0; i < wl; i++){
            counter = 0;
            begin = i;

            for(int r = i; r+wl <= N; r += wl){

                String curr = S.substring(r, r + wl);
                
                if(map.containsKey(curr)){
                    currMap.put(curr, currMap.getOrDefault(curr, 0) + 1);
                    if(currMap.get(curr) <= map.get(curr)) counter++;

                    while(currMap.get(curr) > map.get(curr)){
                        String tmp = S.substring(begin, begin + wl);
                        currMap.put(tmp, currMap.get(tmp) - 1);
                        begin += wl;

                        if(currMap.get(tmp) < map.get(tmp)) counter--;
                    }

                    if(counter == M){
                        result.add(begin);
                        String tmp = S.substring(begin, begin + wl);
                        currMap.put(tmp, currMap.get(tmp) - 1);
                        begin += wl;
                        counter--;
                    }

                } else {
                    currMap.clear();
                    counter = 0;
                    begin = r + wl;
                }
            }
            currMap.clear();
        }
        return result;
    }

    /* https://leetcode.com/problems/flood-fill/ */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if(image == null || image.length == 0 || image[0].length == 0){
            return image;
        }
        int m = image.length;
        int n = image[0].length;

        if(sr < 0 || sr >= m || sc < 0 || sr >= n ){
            return image;
        }

        int oldColor = image[sr][sc];
        
        if(oldColor == newColor){
            return image;
        }

        floodFillDFS(image, sr, sc, oldColor, newColor);

        return image;
    }

    public void floodFillDFS(int[][] image, int curr_x, int curr_y, int oldColor, int newColor){
        int m = image.length;
        int n = image[0].length;
        
        image[curr_x][curr_y] = newColor;
        
        int[][] dirs = new int[][]{{1,0},{-1,0},{0,-1},{0,1}};

        for(int []dir : dirs){
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;

            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n ) {
                if(image[next_x][next_y] == oldColor) {
                    floodFillDFS(image, next_x, next_y, oldColor, newColor);
                }
            }
        }
    }

    /* 
        https://leetcode.com/problems/island-perimeter/ 
        Revisit: Important

    */
    public int islandPerimeter(int[][] grid) {
        
        int m = grid.length;
        int n = grid[0].length;
        
        boolean [][]visited = new boolean[m][n];
        
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1){
                    return dfsIsland(grid, i, j, visited);
                }
            }
        }
        return 0;
    }

    public int dfsIsland(int[][] grid, int curr_x, int curr_y, boolean[][] visited){
        
        int m = grid.length;
        int n = grid[0].length;

        if(curr_x < 0 || curr_x >= m || curr_y < 0 || curr_y >= n ) {
            return 1;
        }

        if(grid[curr_x][curr_y] == 0){
            return 1;
        }

        
        if(visited[curr_x][curr_y]){
            return 0;
        }

        visited[curr_x][curr_y] = true;

        return dfsIsland(grid, curr_x + 1, curr_y, visited) +
                dfsIsland(grid, curr_x , curr_y + 1, visited) +
                dfsIsland(grid, curr_x - 1, curr_y, visited) + 
                dfsIsland(grid, curr_x , curr_y - 1, visited);
    }

    /* 
        https://leetcode.com/problems/coloring-a-border/ 
    */
    public int[][] colorBorder(int[][] grid, int r0, int c0, int color) {
        if(grid == null || grid.length == 0 || grid[0].length == 0)
            return grid;

        int m = grid.length;
        int n = grid[0].length;

        boolean visited[][] = new boolean[m][n];

        if(grid[r0][c0] == color){
            return grid;
        }
        
        int oldColor = grid[r0][c0];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] != oldColor){
                    grid[i][j] = -grid[i][j];
                }
            }
        }

        colorBorderDFS(grid, r0, c0, grid[r0][c0], color, visited);
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if(grid[i][j] < 0){
                    grid[i][j] = Math.abs(grid[i][j]);
                }
            }
        }

        return grid;
    }
    
    public void colorBorderDFS(int[][] grid, int curr_x, int curr_y, int oldColor, int newColor, boolean[][] visited){
        int m = grid.length;
        int n = grid[0].length;
        
        visited[curr_x][curr_y] = true;
        
        int[][] dirs = new int[][]{{1,0},{-1,0},{0,-1},{0,1}};

        for(int []dir : dirs) {
            int next_x = dir[0] + curr_x;
            int next_y = dir[1] + curr_y;

            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && grid[next_x][next_y] == oldColor
                    && !visited[next_x][next_y]) {
                colorBorderDFS(grid, next_x, next_y, oldColor, newColor, visited);
            }else if(next_x < 0 || next_x >= m || next_y < 0 || next_y >= n || (grid[next_x][next_y] != oldColor && grid[next_x][next_y] != newColor)){
                grid[curr_x][curr_y] = newColor;
            }
        }
    }

    /* https://leetcode.com/problems/find-n-unique-integers-sum-up-to-zero/ */
    public int[] sumZero(int n) {

        if(n == 1) return new int[]{0};

        int[] result = new int[n];

        List<Integer> res = new ArrayList<>();
        
        int idx = 0;
        for(int i = 1; i <= n/2; ++i){
            result[idx++] = i;
            result[idx++] = -i;
        }
        if(n % 2 == 1){
            result[idx++] = 0;
        }
        return result;
    }

    /* https://leetcode.com/problems/all-elements-in-two-binary-search-trees/ */
    List<Integer> l1 = new ArrayList<>();
    List<Integer> l2 = new ArrayList<>();
    List<Integer> res = new ArrayList<>();

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {

        inOrder(root1, l1);
        inOrder(root2, l2);

        int len = Math.max(l1.size(), l2.size());

        int i = 0, j = 0;

        while (i < l1.size() || j < l2.size()){
            if(j == l2.size() || i < l1.size() && l1.get(i) <= l2.get(j)) {
                res.add(l1.get(i));
                i++;
            }else {
                l2.size();
                res.add(l2.get(j));
                j++;
            }
        }
        //Collections.sort(ans);
        return res;
    }

    public void inOrder(TreeNode root, List<Integer> list){
        if(root == null)
            return;

        inOrder(root.left, list);
        list.add(root.val);
        inOrder(root.right, list);
    }


    /* 
        https://leetcode.com/problems/jump-game-iii/ 
    */
    enum Index {
        GOOD, BAD, UNKOWN;
    }

    Index[] memo;

    public boolean canReach(int[] nums, int start) {

        memo = new Index[nums.length];

        for (int i = 0; i < nums.length; i++) {
            memo[i] = Index.UNKOWN;
        }

        boolean[] visited = new boolean[nums.length];

        return canJumpFromPosMemo(start, nums, visited);
    }

    public boolean canJumpFromPosMemo(int pos, int[] nums, boolean[] visited){

        if(pos >= nums.length ){
            return false;
        }

        if(pos < 0){
            return false;
        }

        if(visited[pos]){
            return false;
        }

        visited[pos] = true;

        if(memo[pos] != Index.UNKOWN){
            return memo[pos] == Index.GOOD;
        }

        if(nums[pos] == 0){

            memo[pos] = Index.GOOD;
            return true;
        }

        int prevJump = pos - nums[pos];
        int nextJump = pos + nums[pos];

        if(canJumpFromPosMemo(prevJump, nums, visited) || canJumpFromPosMemo(nextJump, nums, visited) ){
            memo[pos] = Index.GOOD;
            return true;
        }

        memo[pos] = Index.BAD;
        return false;
    }

    /* https://leetcode.com/problems/asteroid-collision/ */

    public int[] asteroidCollision(int[] a) {
        LinkedList<Integer> stack = new LinkedList<>();
        for (int i :a ) {
            if(i > 0) {
                stack.add(i);
            }else {
                while (!stack.isEmpty() && stack.peekLast() > 0 && stack.peekLast() < -i)
                    stack.pollLast();
                if(!stack.isEmpty() && stack.peekLast() == -i)
                    stack.pollLast();
                else if(stack.isEmpty() || stack.peekLast() < 0)
                    stack.add(i);
            }
        }

        return stack.stream().mapToInt(i -> i).toArray();
    }

    public int[] asteroidCollisionTLE(int[] asteroids) {
        LinkedList<Integer> list = new LinkedList<>();

        int first = Integer.MIN_VALUE;
        int second = Integer.MAX_VALUE;

        int idx = -1;

        for(int i = 0; i < asteroids.length; i++){
            list.offer(asteroids[i]);
            if(i-1 >= 0 && (asteroids[i] < 0 && asteroids[i-1] >= 0)){
                first = asteroids[i-1];
                second = asteroids[i];
                idx = i;
            }
        }

        if(first != Integer.MIN_VALUE && second != Integer.MAX_VALUE){
            first = Math.abs(first);
            second = Math.abs(second);

            if(first == second){
                list.remove(idx);
                list.remove(idx - 1);
            }else if(second > first){
                list.set(idx, -second);
                list.remove(idx-1);
            }else{
                list.set(idx, first);
                list.remove(idx);
            }

            int[] res = new int[list.size()];

            for(int i = 0; i < list.size(); i++){
                res[i] = list.get(i);
            }
            return asteroidCollision(res);
        }else{
            return asteroids;
        }
    }
    
}