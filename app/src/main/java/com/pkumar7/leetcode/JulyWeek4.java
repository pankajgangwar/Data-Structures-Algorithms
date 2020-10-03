package com.pkumar7.leetcode;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import com.pkumar7.TreeNode;

public class JulyWeek4 {


    /**
     * 437. Path Sum III
     * https://leetcode.com/problems/path-sum-iii/
     *
     * The path does not need to start or end at the root or a leaf,
     * but it must go downwards
     *
     * sum = 8
     *
     *       10
     *      /  \
     *     5   -3
     *    / \    \
     *   3   2   11
     *  / \   \
     * 3  -2   1
     *
     * ***/
    public int pathSum(TreeNode root, int sum) {
        if(root == null){
            return 0;
        }
        return pathSumFromAnyNode(root, sum) + pathSum(root.left, sum)
                + pathSum(root.right, sum);
    }

    public int pathSumFromAnyNode(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }
        return (root.val == sum ? 1 : 0) +
                pathSumFromAnyNode(root.left, sum - root.val) +
                pathSumFromAnyNode(root.right, sum - root.val);
    }

    /**
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/
     * **/
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        
        //recurseTree(root, p, q);
       // return result;
        TreeNode lca = recurseTreeAnother(root, p, q);
        int d1 = getShortestDistance(lca, p, 0);
        int d2 = getShortestDistance(lca, q, 0);
        int totalDistance = d1 + d2;
        System.out.println("totalDistance: " + totalDistance);
        return lca;
    }
    
    private int getShortestDistance(TreeNode lca, TreeNode p, int level) {
        if(lca == null) return -1;
        if(lca.val == p.val) return level;
        
        int left = getShortestDistance(lca.left, p, level + 1);
        int right = getShortestDistance(lca.right, p, level + 1);
        
        return Math.max(left, right);
    }
    
    TreeNode result;
    
    public TreeNode recurseTreeAnother(TreeNode currentNode, TreeNode p, TreeNode q){
        if(currentNode == null) return null;
        
        if(currentNode.val == p.val || currentNode.val == q.val) return currentNode;
        
        TreeNode right = recurseTreeAnother(currentNode.left, p, q);
        TreeNode left = recurseTreeAnother(currentNode.right, p, q);
        
        if(right != null && left != null){
            return currentNode;
        }
        
        if(right == null && left != null){
            return left;
        }
        
        if(right != null && left == null){
            return right;
        }
        
        return null;
    }
    
    public boolean recurseTree(TreeNode currentNode, TreeNode p, TreeNode q){
        if(currentNode == null){
            return false;
        }
        int left = recurseTree(currentNode.left, p, q) ? 1 : 0;
        int right = recurseTree(currentNode.right, p, q) ? 1 : 0;
        int middle = (currentNode == p || currentNode == q) ? 1 : 0;
        if(middle + left + right >= 2)
            result = currentNode;

        return (middle + left + right) > 0;
    }
    
    public boolean findPath(TreeNode root, TreeNode n, ArrayList<Integer> paths){
        if(root == null)
            return false;

        paths.add(root.val);

        if(root.val == n.val)
            return true;

        if(root.left != null && findPath(root.left, n, paths) )
            return true;

        if(root.right != null && findPath(root.right, n, paths) )
            return true;

        paths.remove(paths.size() -1);//BackTrack to prev arr

        return false;
    }

    public TreeNode lowestCommonAncestorApproach1(TreeNode root, TreeNode p, TreeNode q) {
        ArrayList<Integer> mPath1 = new ArrayList<>();
        ArrayList<Integer> mPath2 = new ArrayList<>();

        if(!findPath(root, p, mPath1) || !findPath(root, q, mPath2)){

            System.out.println((mPath1.size() > 0) ? "n1 is present" : "n1 is missing");
            System.out.println((mPath2.size() > 0) ? "n2 is present" : "n2 is missing");
            return new TreeNode(-1);
        }

        int i = 0;
        for (; i < mPath1.size() && i < mPath2.size(); i++) {

            // System.out.println(path1.get(i) + " " + path2.get(i));
            if (!mPath1.get(i).equals(mPath2.get(i)))
                break;
        }

        return new TreeNode(mPath1.get(i-1));

    }

    /**
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/
     * **/
    public TreeNode lcaBST(TreeNode currentNode, TreeNode p, TreeNode q) {
        if(currentNode.val > p.val && currentNode.val > q.val){
            return lcaBST(currentNode.left, p, q);
        }else if(currentNode.val < p.val && currentNode.val < q.val){
            return lcaBST(currentNode.right, p, q);
        }else{
            return currentNode;
        }
    }

    int[] dp;
    public void fill_dp(int n){
         dp = new int[n+1];
         dp[0] = 0;
         dp[1] = 1;
         dp[2] = 1;
    }

    public int call_tribonacci(int n){
        if(n == 0 ){
            return 0;
        }else if(n == 1 || n == 2){
            return 1;
        }
        fill_dp(n);
        for (int i = 3; i <= n; i++){
            dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
        }
        return dp[n];
    }
    public int tribonacci(int n) {
        return call_tribonacci(n);
    }

    public static void main(String[] args) {
       //char ch = maximumOccurringCharacter("bbaaaca");
       //System.out.println("char: " + ch);
        JulyWeek4 julyWeek4 = new JulyWeek4();
        int x = julyWeek4.tribonacci(25);
        //System.out.println(x);

        int[][] intervals = new int[][]{{1,3},{6,9}};
        int[] new_inter = new int[]{2,5};
        //julyWeek4.insert(intervals, new_inter);

        //julyWeek4.selfDividingNumbers(1,22);
        julyWeek4.checkPerfectNumber(28);

       //largestUniqueNumber();
    }

    public static char maximumOccurringCharacter(String text) {
        // Write your code here
        char[] arr = text.toCharArray();
        Map<Character,Integer> freq = new HashMap<>();
        for (char ch : arr) {
            if(freq.get(ch) == null){
                freq.put(ch, 1);
            }else{
                freq.put(ch, freq.get(ch) +1);
            }
        }

        Set<Character> mChar = freq.keySet();
        int max_freq = 0;
        int min_index = 0;
        Iterator<Character> it = mChar.iterator();
        while(it.hasNext()){
            char ch = it.next();
            int curr_freq = freq.get(ch);
            if(max_freq < curr_freq){
                max_freq = curr_freq;
                min_index = text.indexOf(ch);
            }else if(max_freq == curr_freq){
                int curr_index = text.indexOf(ch);
                if(min_index > curr_index){
                    min_index = text.indexOf(ch);
                }
            }
        }
        return text.charAt(min_index);
    }

    /**
     * Input: [5,7,3,9,4,9,8,3,1]
     * Output: 8
     * Explanation:
     * The maximum integer in the array is 9 but it is repeated. The number 8 occurs only once, so it's the answer.
     *
     * **/
    public static int largestUniqueNumber() {
        int[] A = new int[]{5,7,3,9,4,9,8,3,1};

        ArrayList<Integer> mList = new ArrayList<>();
        for (int x : A) {
            mList.add(x);
        }

        Collections.sort(mList, Collections.reverseOrder());

        Map<Integer,Integer> freq = new HashMap<>();
        for (int i = 0; i < mList.size(); i++) {
            System.out.println(mList.get(i) + " <-- " );
            freq.put(mList.get(i), freq.getOrDefault(mList.get(i),0)+1);
        }

        Set<Integer> keys = freq.keySet();
        Iterator<Integer> it = keys.iterator();

        while(it.hasNext()){
            int val = it.next();
            System.out.println(val + " : " + freq.get(val));
            if(freq.get(val) == 1){
               // return val;
            }
        }
        return A[0];

    }

    /****
     * https://leetcode.com/discuss/interview-question/344650/amazon-online-assessment-questions-2019
     *
     * Most Common Word
     * Prison Cells After N Days
     * K Closest Points to Origin
     * Reorder Log Files
     * Partition Labels
     * Min Cost to Add New Roads
     * Min distance to remove the obstacle
     * Roll Dice
     * Min Cost to Connect Ropes (Merge Files)
     * Optimal Aircraft Utilization
     * Longest string without 3 consecutive characters
     * Movies on Flight
     * Sort Center
     * Longest string made up of only vowels
     * Substrings of size K with K distinct chars
     */
    /**
    * https://leetcode.com/problems/reorganize-string/
     * Given a string S, check if the letters can be rearranged so that two characters
     * that are adjacent to each other are not the same.
     * If possible, output any possible ans.  If not possible, return the empty string.
     *
     * Input: S = "aab"
     * Output: "aba"
     *
     * Input: S = "aaab"
     * Output: ""
     * */
    class Element{
        char aChar;
        int freq;
        public Element(char thisChar){
            aChar = thisChar;
        }
    }
    public String reorganizeString(String S) {
        char [] arr = S.toCharArray();
        Map<Character, Integer> mMap = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            mMap.put(arr[i], mMap.getOrDefault(arr[i], 0) + 1);
        }
        PriorityQueue<Element> mMaxHeap = new PriorityQueue<>((e1, e2) -> {
            if(e1.freq > e2.freq){
                return -1;
            }else if(e1.freq < e2.freq){
                return 1;
            }else {
                return 0;
            }
        });

        for (Character key : mMap.keySet()){
            int this_freq = mMap.get(key);
            Element ele = new Element(key);
            ele.freq = this_freq;

            mMaxHeap.offer(ele);
        }

        StringBuilder builder = new StringBuilder();

        Element prev = new Element('#');
        while(!mMaxHeap.isEmpty()){
            Element curr = mMaxHeap.poll();

            builder.append(curr.aChar);

            if(prev.freq > 0){
                mMaxHeap.offer(prev);
            }

            curr.freq--;
            prev = curr;
        }

        return builder.length() == S.length() ? builder.toString() : "";
    }
    /**
     * 57. Insert Interval
     * https://leetcode.com/problems/insert-interval/
     * ***/
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int m = intervals.length;

        ArrayList<int[]> intervalList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            intervalList.add(intervals[i]);
        }
        intervalList.add(newInterval);


        Collections.sort(intervalList, (o1, o2) -> o1[0] - o2[0]);

        for (int[] curr : intervalList) {
            System.out.println(" intervals " + curr[0] + ","+ curr[1]);
        }

        System.out.println("Total intervals " + intervalList.size());

        List<int[]> resultList = new ArrayList<>();

        int[] mergedInterval = intervalList.get(0);

        resultList.add(mergedInterval);

        for (int[] interval : intervalList) {
            if(interval[0] <= mergedInterval[1]){
                mergedInterval[1] = Math.max(mergedInterval[1], interval[1]);
            }else{
                mergedInterval = interval;
                resultList.add(mergedInterval);
            }
        }

        for (int i = 0; i < resultList.size(); i++) {
            System.out.println("Merged intervals " + resultList.get(i)[0] + "," + resultList.get(i)[1]);
        }
        return resultList.toArray(new int[resultList.size()][2]);
    }

    /**
     * 728. Self Dividing Numbers
     * https://leetcode.com/problems/self-dividing-numbers/
     *
     * Input:
     * left = 1, right = 22
     * Output: [1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 12, 15, 22]
     *
     * **/

    public List<Integer> selfDividingNumbers(int left, int right) {
        List<Integer> result = new ArrayList<>();
        for (int i = left; i <= right ; i++) {
            int number = i;
            boolean can_be_added = true;
            while(number > 0){
                int digit = number % 10;
                if(digit == 0){
                    can_be_added = false;
                    break;
                }
                if(i % digit != 0){
                    can_be_added = false;
                }
                number = number / 10;
            }
            if(can_be_added){
                System.out.println("Adding: " + i);
                result.add(i);
            }
        }
        return result;
    }
    /**
     * 507. Perfect Number
     * https://leetcode.com/problems/perfect-number/
     *
     * Input: 28
     * Output: True
     * Explanation: 28 = 1 + 2 + 4 + 7 + 14
     * **/
    public boolean checkPerfectNumber(int num) {
        //Brute force with Time Limit Exceeded
        /*if(num <= 0){
            return false;
        }

        int sum = 0;
        for (int i = 1; i < num; i++) {
            if(num % i == 0)
                sum += i;

            if(sum > num)
                return false;
        }
        return sum == num;*/

        //num = sqrt(num)*sqrt(num)
        /*if(num <= 0){
            return false;
        }

        int sum = 0;
        for (int i = 1; i*i <= num; i++) {
            if(num % i == 0) {
                sum += i;
                if(i*i != num) {
                    sum += num / i;
                }
            }
        }
        boolean status = sum - num == num;
        System.out.println(status);
        return sum - num == num;*/

        int primes[]= new int[]{2,3,5,7,13,19,31};
        for (int prime: primes) {
            if(merseenePrimes(prime) == num)
                return true;
        }

        return false;

    }

    public int merseenePrimes(int p){
        return (1 << (p -1)) * ((1 << p) - 1 );
    }

    /**832. Flipping an Image
     *
     * https://leetcode.com/problems/flipping-an-image/
     *
     * Input: [[1,1,0],[1,0,1],[0,0,0]]
     * Output: [[1,0,0],[0,1,0],[1,1,1]]
     * Explanation: First reverse each row: [[0,1,1],[1,0,1],[0,0,0]].
     * Then, invert the image: [[1,0,0],[0,1,0],[1,1,1]]
     * ***/
    public int[][] flipAndInvertImage(int[][] A) {
        for (int[] row: A) {
            int l = 0, r = row.length-1;
            while (l < r){
                int temp = row[l];
                row[l] = row[r];
                row[r] = temp;
                l++;
                r--;
            }
        }

        for (int[] row : A) {
            int i = 0;
            for (; i < row.length; i++) {
                if(row[i] == 0){
                    row[i] = 1;
                }else{
                    row[i] = 0;
                }
            }
        }
        return A;
    }

    /**
     * https://leetcode.com/discuss/interview-question/347457/amazon-oa-2019-treasure-island
     *
     */

    StringBuilder preOrderTrav = new StringBuilder();
    public boolean isSubTreePreOrder(TreeNode s, TreeNode t){
        preOrder(s);
        String source = preOrderTrav.toString();
        preOrderTrav.setLength(0);

        preOrder(t);
        String target = preOrderTrav.toString();

        System.out.println(source + " : " + target);

        return source.contains(target);
    }

    public void preOrder(TreeNode root){
        if(root == null){
            preOrderTrav.append("#");
            return;
        }
        preOrderTrav.append(root.val);
        preOrder(root.left);
        preOrder(root.right);
    }

    public boolean isSubtree(TreeNode s, TreeNode t) {
        if(s == null){
            return false;
        }

        if(ifIdentical(s, t)){
            return true;
        }

        return isSubtree(s.left, t) || isSubtree(s.right, t);

    }

    public boolean ifIdentical(TreeNode source, TreeNode target){
        if(source == null && target == null){
            return true;
        }
        if(source == null || target == null){
            return false;
        }

        if(source.val != target.val){
            return false;
        }

        return ( ifIdentical(source.left, target.left)
            && ifIdentical(source.right, target.right));
    }

}
