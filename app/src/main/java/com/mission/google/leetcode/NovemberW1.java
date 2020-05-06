package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

public class NovemberW1 {
    public static void main(String[] args) {
        NovemberW1 w1 = new NovemberW1();
        int[] nums = new int[]{2147483647,2147483647};
        w1.medianSlidingWindow(nums, 2);
    }

    public double[] medianSlidingWindow(int[] nums, int k) {
        PriorityQueue<Integer> lowers = new PriorityQueue<Integer>((a,b)->(b.compareTo(a)));
        PriorityQueue<Integer> higher = new PriorityQueue<Integer>();

        int h = nums.length - k + 1;

        List<Double> result = new ArrayList<Double>();

        for (int i = 0; i < k ; i++ ) {
            addNumber(lowers, higher, nums[i]);
            rebalanceHeaps(lowers, higher);
        }

        findMedian(lowers, higher, result);

        int j = 0;
        for (int i = k; i < nums.length; i++) {
            int ele = nums[j++];
            if(!lowers.remove(ele)){
                higher.remove(ele);
            }
            if(lowers.isEmpty()){
                if(!higher.isEmpty())
                    lowers.offer(higher.poll());
            }
            addNumber(lowers, higher, nums[i]);
            rebalanceHeaps(lowers, higher);
            findMedian(lowers, higher, result);
        }
        double[] res = new double[result.size()];

        for (int i = 0; i < result.size() ; i++ ) {
            res[i] = result.get(i);
        }
        return res;
    }

    public void addNumber(PriorityQueue<Integer> lowers, PriorityQueue<Integer> higher, int num){
        if(lowers.size() == 0 || num < lowers.peek()){
            lowers.add(num);
        }else{
            higher.add(num);
        }
    }

    public void rebalanceHeaps(PriorityQueue<Integer> lowers, PriorityQueue<Integer> higher){
        PriorityQueue<Integer> bigger = higher.size() > lowers.size() ? higher : lowers;
        PriorityQueue<Integer> smaller = higher.size() < lowers.size() ? higher : lowers;

        if(bigger.size() - smaller.size() >= 2){
            smaller.add(bigger.poll());
        }
    }

    public void findMedian(PriorityQueue<Integer> lowers, PriorityQueue<Integer> higher, List<Double> result){
        PriorityQueue<Integer> bigger = higher.size() > lowers.size() ? higher : lowers;
        PriorityQueue<Integer> smaller = lowers.size() < higher.size() ? lowers : higher;
        if(bigger.size() == smaller.size()){
            double median = (double)bigger.peek() + (double)smaller.peek();
            System.out.println(median + " --- ");
            median = median /2;
            System.out.println(median + " #### " );

            result.add((double)(bigger.peek() + smaller.peek()) / 2);
        }else{
            result.add((double)bigger.peek());
        }
    }

    /**

     128. Longest Consecutive Sequence

     Input: [100, 4, 200, 1, 3, 2]
     Output: 4
     Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.

     https://leetcode.com/problems/longest-consecutive-sequence/

     **/

    class Solution {
        public int longestConsecutive(int[] nums) {
            Set<Integer> mSets = new HashSet<Integer>();

            for(int n : nums){
                mSets.add(n);
            }

            int maxLength = 0;
            for (int i = 0; i < nums.length; i++ ) {

                if(!mSets.contains(nums[i] - 1)){
                    int j = nums[i];

                    while(mSets.contains(j)){
                        j++;
                    }

                    maxLength = Math.max(maxLength, j - nums[i]);
                }
            }
            return maxLength;
        }
    }
    /**

     1243. Array Transformation

     https://leetcode.com/problems/array-transformation/

     Input: arr = [6,2,3,4]
     Output: [6,3,3,4]
     Explanation:
     On the first day, the array is changed ftrom [6,2,3,4] to [6,3,3,4].
     No more operations can be done to this array.

     **/
    public List<Integer> transformArray(int[] arr) {
        List<Integer> result = new ArrayList<Integer>();

        int[] ans = new int[arr.length];

        while (!Arrays.equals(ans, arr)) {
            ans = arr.clone();

            for (int i = 1; i < arr.length - 1; i++) {
                if (ans[i] > ans[i + 1] && ans[i] > ans[i - 1]) {
                    arr[i]--;
                } else if (ans[i] < ans[i + 1] && ans[i] < ans[i - 1]) {
                    arr[i]++;
                }
            }
        }

        for (int ele : arr) {
            result.add(ele);
        }

        return result;
    }

    /**
     Input: s = "lee(t(c)o)de)"
     Output: "lee(t(c)o)de"

     Input: s = "a)b(c)d"
     Output: "ab(c)d"

     Input: s = "))(("
     Output: ""
     Explanation: An empty string is also valid.

     https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
     */
    public String minRemoveToMakeValid(String s) {
        Stack<Integer> st = new Stack<Integer>();
        StringBuilder sb = new StringBuilder(s);
        for(int i = 0; i < sb.length(); i++){
            if(sb.charAt(i) == '('){
                st.push(i);
            }else if(sb.charAt(i) == ')'){
                if(!st.isEmpty()){
                    st.pop();
                }else{
                    sb.setCharAt(i,'*');
                }
            }
        }

        while(!st.isEmpty()){
            sb.setCharAt(st.peek(),'*');
            st.pop();
        }

        return sb.toString().replaceAll("\\*","");
    }

    /**

    213. House Robber II
    https://leetcode.com/problems/house-robber-ii/

    Input: [2,3,2]
    Output: 3
    Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
                 because they are adjacent houses.

    **/
    public int rob(int[] nums) {
        if(nums.length == 1) return nums[0];
        return Math.max(robDP0(nums), robDP1(nums));
    }

    public int robDP0(int[] nums){
        if(nums.length == 0) return 0;

        int prev1 = 0;
        int prev2 = 0;

        for (int i = 0; i < nums.length -1; i++) {
            int tmp = prev1;
            prev1 = Math.max(prev2 + nums[i], prev1);
            prev2 = tmp;
        }
        return prev1;
    }

    public int robDP1(int[] nums){
        if(nums.length == 0) return 0;

        int prev1 = 0;
        int prev2 = 0;

        for (int i = 1; i < nums.length; i++) {
            int tmp = prev1;
            prev1 = Math.max(prev2 + nums[i], prev1);
            prev2 = tmp;
        }
        return prev1;
    }

    public int robRec(int[] nums, int n){
        if(n < 0){
            return 0;
        }
        return Math.max(robRec(nums, n-2) + nums[n], robRec(nums, n-1));
    }



    /**
        https://leetcode.com/problems/house-robber-iii/
        https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem
        337. House Robber III

        Input: [3,2,3,null,3,null,1]

             3
            / \
           2   3
            \   \ 
             3   1

        Output: 7 
        Explanation: Maximum amount of money the thief can rob = 3 + 3 + 1 = 7.

    **/

    public int rob(TreeNode root) {
        //robRec(root);
        //return robMemo(root, new HashMap<TreeNode,Integer>());
        int[] res = robDP(root);
        return Math.max(res[0], res[1]);
    }

    public int[] robDP(TreeNode root){
        if(root == null){
            return new int[2];
        }
        int[] left = robDP(root.left);
        int[] right = robDP(root.right);

        int[] res = new int[2];
        res[0] = Math.max(left[0] , left[1]) + Math.max(right[0], right[1]);
        res[1] = root.val + left[0] + right[0];

        return res;
    }

    public int robMemo(TreeNode root, Map<TreeNode,Integer> map){
        if(root == null) return 0;
        int val = 0;

        if(map.containsKey(root)){
            return map.get(root);
        }

        if(root.left != null){
            val+= robRec(root.left.left) + robRec(root.left.right);
        }
        if(root.right != null){
            val+= robRec(root.right.left) + robRec(root.right.right);
        }

        val = Math.max(root.val + val , robRec(root.left) + robRec(root.right));

        map.put(root, val);

        return val;
    }

    public int robRec(TreeNode root){
        if(root == null) return 0;
        int val = 0;
        if(root.left != null){
            val+= robRec(root.left.left) + robRec(root.left.right);
        }
        if(root.right != null){
            val+= robRec(root.right.left) + robRec(root.right.right);
        }

        return Math.max(root.val + val , robRec(root.left) + robRec(root.right));
    }

    /**
    563. Binary Tree Tilt
    https://leetcode.com/problems/binary-tree-tilt/

    Input: 
         5
       /   \
      2     3
    Output: 1
    
    Explanation: 
    Tilt of node 2 : 0
    Tilt of node 3 : 0
    Tilt of node 1 : |2-3| = 1
    Tilt of binary tree : 0 + 0 + 1 = 1

    **/
    int result = 0;
    public int findTilt(TreeNode root) {
        findTiltRec(root);
        return result;
    }   

    public int findTiltRec(TreeNode root){
        if(root == null){
            return 0;
        }
        int leftVal = findTiltRec(root.left);
        int rightVal = findTiltRec(root.right);

        result += Math.abs(leftVal - rightVal);

        return leftVal + rightVal + root.val;
    }

    /**
    https://leetcode.com/problems/roman-to-integer/

    Input: "LVIII"
    Output: 58
    Explanation: L = 50, V= 5, III = 3

    Input: "MCMXCIV"
    Output: 1994
    Explanation: M = 1000, CM = 900, XC = 90 and IV = 4

    **/
    public int romanToInt(String romanString) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I',1);
        map.put('V',5);
        map.put('X',10);
        map.put('L',50);
        map.put('C',100);
        map.put('D',500);
        map.put('M',1000);

        char[] arr = romanString.toCharArray();

        int val = map.get(arr[arr.length -1]);
        for (int i = arr.length - 2; i >= 0; --i) {
            if(map.get(arr[i]) < map.get(arr[i+1])){
                val -= map.get(arr[i]);
            }else{
                val += map.get(arr[i]);
            }
        }
        return val;
    }

    /**
    https://leetcode.com/problems/implement-trie-prefix-tree/
    **/
    class TrieNode {
        boolean isEndOfWord = false;
        int TOTAL_ALPHABETS = 26;
        TrieNode[] children = new TrieNode[TOTAL_ALPHABETS];
        public TrieNode(){
            for(int i = 0; i < TOTAL_ALPHABETS; i++){
                children[i] = null;
            }
        }
    }

    class Trie {

        /** Initialize your data structure here. */  
        TrieNode root;
        public Trie() {
            root = new TrieNode();
        }
        
        /** Inserts a word into the trie. */
        public void insert(String word) {
            int n = word.length();
            TrieNode pCrawl = root;
            for (int level = 0; level < n ; level++ ) {
                int idx = word.charAt(level) - 'a';
                if(pCrawl.children[idx] == null){
                    pCrawl.children[idx] = new TrieNode();
                }
                pCrawl = pCrawl.children[idx];
            }
            pCrawl.isEndOfWord = true;
        }
        
        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            TrieNode pCrawl = root;
            int length = word.length();

            for (int level = 0; level < length ; level++ ) {
                int idx = word.charAt(level) - 'a';
                if(pCrawl.children[idx] == null){
                    return false;
                }
                pCrawl = pCrawl.children[idx];
            }
            return (pCrawl != null && pCrawl.isEndOfWord);
        }
        
        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            int length = prefix.length();
            TrieNode pCrawl = root;
            for (int level = 0; level < length ; level++) {
                int idx = prefix.charAt(level) - 'a';
                if(pCrawl.children[idx] == null){
                    return false;
                }
                pCrawl = pCrawl.children[idx];
            }
            return true;
        }
    }

    /**
    https://leetcode.com/problems/add-and-search-word-data-structure-design/

    211. Add and Search Word - Data structure design

    addWord("bad")
    addWord("dad")
    addWord("mad")
    search("pad") -> false
    search("bad") -> true
    search(".ad") -> true
    search("b..") -> true

    **/
    
    class WordDictionary {
    
        TrieNode root;
        /** Initialize your data structure here. */
        public WordDictionary() {
            root = new TrieNode();
        }
        
        /** Adds a word into the data structure. */
        public void addWord(String word) {
            int length = word.length();
            TrieNode pCrawl = root;
            for (int level = 0; level < length ; level++ ) {
                int idx = word.charAt(level) - 'a';
                if(pCrawl.children[idx] == null){
                    pCrawl.children[idx] = new TrieNode();
                }
                pCrawl = pCrawl.children[idx];
            }
            pCrawl.isEndOfWord = true;
        }
        
        /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
        public boolean search(String word) {
            return searchRec(word, 0, root);
        }

        public boolean searchRec(String word, int idx, TrieNode pCrawl){
            if(word.length() == idx){
                return pCrawl != null && pCrawl.isEndOfWord;
            }
            if(word.charAt(idx) != '.'){
                pCrawl = pCrawl.children[word.charAt(idx) - 'a'];
                if(pCrawl != null && searchRec(word, idx + 1, pCrawl)){
                    return true;
                }else{
                    return false;
                }
            }else{
                for(int i = 0; i < pCrawl.children.length; i++){
                    if(pCrawl.children[i] != null ){
                        if(searchRec(word, idx + 1, pCrawl.children[i])){
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

}
