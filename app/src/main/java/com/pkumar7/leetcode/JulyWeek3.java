package com.pkumar7.leetcode;


import com.pkumar7.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


public class JulyWeek3 {

    /**
     * 961. N-Repeated Element in Size 2N Array
    ** https://leetcode.com/problems/n-repeated-element-in-size-2n-array
     *
     * Input: [2,1,2,5,3,2]
     * Output: 2
     *
     * Input: [5,1,5,2,5,3,5,4]
     * Output: 5
    */

    public int repeatedNTimes(int[] A) {
        Set<Integer> mSets = new HashSet<>();
        for (int i = 0; i < A.length; i++) {
            if(!mSets.add(A[i])){
                return A[i];
            }
        }
        return -1;
    }

    /**
     * 169. Majority Element
     * https://leetcode.com/problems/majority-element/
     *
     * element which appears more than n/2 times.
     *
     * Input: [2,2,1,1,1,2,2]
     * Output: 2
     *
     * **/
    public int majorityElement(int[] nums) {
        Map<Integer,Integer> mMap = new HashMap<>();
        int occurrence = nums.length / 2;

        for (int i = 0; i < nums.length; i++) {
            mMap.put(nums[i],mMap.getOrDefault(nums[i],0)+1);
        }

        Set<Integer> mKeys = mMap.keySet();
        Iterator<Integer> it = mKeys.iterator();
        while (it.hasNext()){
            int key = it.next();
            int freq = mMap.get(key);
            if(freq > occurrence){
                return key;
            }
        }
        return -1;
    }

    /**
     * https://leetcode.com/problems/jump-game/
     * https://leetcode.com/articles/jump-game/
     * 55. Jump Game
     *
     * Input: [2,3,1,1,4]
     * Output: true
     * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
     *
     * ***/
    public boolean canJumpFromPosition(int position, int[] nums) {
        if (position == nums.length - 1) {
            return true;
        }

        int furthestJump = Math.min(position + nums[position], nums.length - 1);
        //for (int nextPosition = furthestJump; nextPosition > position; nextPosition--)
        //Can check from right to left
        for (int nextPosition = position + 1; nextPosition <= furthestJump; nextPosition++) {
            if (canJumpFromPosition(nextPosition, nums)) {
                return true;
            }
        }

        return false;
    }

    public boolean canJump(int[] nums) {
        memo = new Index[nums.length];

        for (int i = 0; i < nums.length; i++) {
            memo[i] = Index.UNKOWN;
        }
        return canJumpFromPosMemo(0, nums);
        //return canJumpFromPosition(0, nums);
    }

    public static boolean canJumpFromPosLinearTime(int[] nums) {
        int max = 0;
        for(int i =0; i < nums.length; i++){
            if(i > max) {
                return false;
            }
            max = Math.max(max, nums[i] + i);
        }
        return true;
    }

    enum Index {
        GOOD, BAD, UNKOWN;
    }

    Index[] memo;
    public boolean canJumpFromPosMemo(int pos, int[] nums){
        if(memo[pos] != Index.UNKOWN){
            return memo[pos] == Index.GOOD;
        }

        int farthestJump = Math.min(pos + nums[pos], nums.length - 1);
        for (int nextPosition = farthestJump; nextPosition > pos  ; nextPosition --) {

            if(canJumpFromPosMemo(nextPosition, nums)){
                memo[pos] = Index.GOOD;
                return true;
            }

        }
        memo[pos] = Index.BAD;
        return false;
    }

    /**
     * https://leetcode.com/problems/jump-game-ii/
     *
     * Input: [2,3,1,1,4]
     * Output: 2
     * Explanation: The minimum number of jumps to reach the last index is 2.
     *              Jump 1 step from index 0 to 1, then 3 steps to the last index.
     *
     * **/
    public int jump(int[] nums) {
        return -1;
    }

    class Pair implements Comparable<Pair>{
        int first;
        int second;
        public Pair(int first, int second){
            this.first = first;
            this.second = second;
        }

        @Override
        public int compareTo(Pair pair) {
            if(first == pair.first && second == pair.second){
                return 0;
            }else if(first >= pair.first && second > pair.second){
                return 1;
            }else if(first <= pair.first && second < pair.second) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return first+","+second;
        }
    }


    /****
     * https://leetcode.com/problems/number-of-equivalent-domino-pairs/
     */
    public int numEquivDominoPairs(int[][] dominoes) {
        List<Pair> mPairs = new ArrayList<Pair>();
        for (int i = 0; i < dominoes.length; i++) {
            int first = dominoes[i][0];
            int second = dominoes[i][1];

            int larger = Math.max(first,second);
            int smaller = Math.min(first,second);

            Pair pair = new Pair(smaller, larger);
            mPairs.add(pair);
        }

        Collections.sort(mPairs);

        for (Pair pair : mPairs) {
            System.out.println(pair);
        }

        //dominoes[i] = [a, b]
        // dominoes[j] = [c, d] if and only if either (a==c and b==d), or (a==d and b==c
        Pair first = mPairs.get(0);
        int countPairs = 0;
        for (int i = 1; i < mPairs.size() -1 ; i++) {
            Pair second = mPairs.get(i);
            //System.out.println(first + " : " + second);
            if(first.first == second.first && first.second == second.second){
                //System.out.println(" Pair found: " + first.first +","+first.second + " and " + second.first +","+second.second);
                countPairs++;
            }else if(first.first == second.second && first.second == second.first){
                //System.out.println(" Pair found: " + first.first +","+first.second + " and " + second.first +","+second.second);
                countPairs++;
            }
            first = second;
        }
        return countPairs;
    }

    public int numEquivDominoPairsLinearTime(int[][] dominoes) {
        Map<Integer,Integer> count = new HashMap<>();
        int res = 0;
        for (int[] d : dominoes) {
            int key = Math.min(d[0],d[1]) * 10 + Math.max(d[0],d[1]);

            int pairs = count.getOrDefault(key, 0);
            res += pairs;

            count.put(key,pairs + 1);
        }
        return res;
    }

    /****
     * 1129. Shortest Path with Alternating Colors
     * https://leetcode.com/problems/shortest-path-with-alternating-colors/
     *
     * Input: n = 3, red_edges = [[0,1]], blue_edges = [[1,2]]
     * Output: [0,1,2]
     *
     */
    public int[] shortestAlternatingPaths(int n, int[][] red_edges, int[][] blue_edges) {
        int[] ans = new int[n];
        Arrays.fill(ans, -1);
        ans[0] = 0;
        int d = 1;
        boolean[] visited = new boolean[2 * n];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        queue.offer(null);

        while(!queue.isEmpty()){
            Integer cur = queue.poll();
            if(cur == null){
                if(!queue.isEmpty()){
                    queue.offer(null);
                    d++;
                }
            }else if(!visited[cur + n ]){
                visited[cur + n] = true;
                if(cur >= 0){
                    for (int[]red : red_edges){
                        if(red[0] == cur){
                            if(ans[red[1]] == -1){
                                ans[red[1]] = d;
                            }
                            queue.offer(-red[1]);
                        }
                    }
                }
                if(cur <= 0){
                    for (int[]blue : blue_edges){
                        if(blue[0] == -cur){
                            if(ans[blue[1]] == -1){
                                ans[blue[1]] = d;
                            }
                            queue.offer(blue[1]);
                        }
                    }
                }
            }
        }
        return ans;
    }

    //https://leetcode.com/contest/weekly-contest-146/problems/minimum-cost-tree-from-leaf-values/
    public int mctFromLeafValues(int[] arr) {
        return 0;
    }

    public static void main(String args[]){
        JulyWeek3 julyWeek3 = new JulyWeek3();
        int[] result = julyWeek3.diStringMatch("IDID");
        for (int i : result) {
            System.out.print(i);
        }
    }

    public static int[][] intervals = new int[][]{
            {8,10},{15,18},{2,6},{1,3}
    };

    /**
     * https://leetcode.com/problems/binary-tree-paths/
     * **/
    public List<String> binaryTreePaths(TreeNode root) {
        ArrayList<String> all_unique_paths = new ArrayList<>();
        allPathBinaryTree(root,"",all_unique_paths);
        return all_unique_paths;
    }

    public void allPathBinaryTree(TreeNode root, String path,ArrayList<String> allPaths){
        if(root == null){
            return;
        }
        if(root.left == null && root.right == null){
            allPaths.add(path + root.val);
        }
        if(root.left != null) {
            allPathBinaryTree(root.left, path + root.val + "->", allPaths);
        }
        if(root.right != null) {
            allPathBinaryTree(root.right, path + root.val + "->", allPaths);
        }
    }

    /**
     * 113. Path Sum II
     * https://leetcode.com/problems/path-sum-ii/
     * **/

    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> mAllPaths = new LinkedList<>();
        List<Integer> path = new LinkedList<>();
        findPathsWithSum(root, sum, 0, path, mAllPaths);
        return mAllPaths;
    }

    public void findPathsWithSum(TreeNode root, int targetSum, int actualSum, List<Integer> paths,
                                 List<List<Integer>> allPaths){
        if(root == null){
            return;
        }

        paths.add(root.val);
        actualSum += root.val;
        if(root.left == null && root.right == null){
            if(targetSum == actualSum){
                allPaths.add(new LinkedList<>(paths));
            }
        }

        if(root.left != null){
            findPathsWithSum(root.left, targetSum, actualSum , paths, allPaths);
        }

        if(root.right != null){
            findPathsWithSum(root.right, targetSum, actualSum , paths, allPaths);
        }

        paths.remove(paths.size() -1);
    }

    /**
     * 942. DI String Match
     * https://leetcode.com/problems/di-string-match/
     *
     * Given a string S that only contains "I" (increase) or "D" (decrease), let N = S.length.
     *
     * Return any permutation A of [0, 1, ..., N] such that for all i = 0, ..., N-1:
     *
     * If S[i] == "I", then A[i] < A[i+1]
     * If S[i] == "D", then A[i] > A[i+1]
     *
     * Input: "IDID"
     * Output: [0,4,1,3,2]
     *
     * **/

    public int[] diStringMatch(String S) {
        S = "DDI";
        char[] input = S.toCharArray();
        int max = input.length;
        int min = 0;

        int[] result = new int[max + 1];

        //IDID
        for (int i = 0; i < input.length; i++) {

            if(input[i] == 'D'){
                result[i] = max--;
            }else{
                result[i] = min++;
            }
        }
        result[input.length] = min;
        return result;
    }

}
