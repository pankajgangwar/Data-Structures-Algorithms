package com.mission.google.leetcode;



import android.util.Pair;

import com.mission.google.TreeNode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.TreeMap;

public class FebruaryW4 {
    
    /* https://leetcode.com/problems/number-of-days-between-two-dates/ */
    public int daysBetweenDates(String date1, String date2) {
        String[] arr1 = date1.split("-");
        String[] arr2 = date2.split("-");
        String dateBeforeString = "";
        String dateAfterString = "";

        for(int i = 0; i < arr1.length && i < arr2.length; i++){
            if(Integer.parseInt(arr1[i]) < Integer.parseInt(arr2[i]) ){
                dateBeforeString = date1;
                dateAfterString = date2;
                break;
            }
        }

        if(dateAfterString.isEmpty() && dateBeforeString.isEmpty()){
            dateBeforeString = date1;
            dateAfterString = date2;
        }

        LocalDate dateBefore = LocalDate.parse(dateBeforeString);
        LocalDate dateAfter = LocalDate.parse(dateAfterString);

        //calculating number of days in between
        long days = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        return  Math.abs((int)days);
    }

    /* https://leetcode.com/problems/validate-binary-tree-nodes/ */
    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        int node = 0;
        HashMap<Integer, int[]> map = new HashMap<>();
        if(leftChild[0] == -1 && rightChild[0] == -1) return false;


        map.put(node, new int[]{leftChild[0], rightChild[0]});

        if(leftChild[0] != -1) {
            map.putIfAbsent(leftChild[0], new int[]{-1, -1});
        }
        if(rightChild[0] != -1) {
            map.putIfAbsent(rightChild[0], new int[]{-1, -1});
        }

        for (int i = 1; i < leftChild.length && i < rightChild.length; i++) {
            int left = leftChild[i];
            int right= rightChild[i];
            node++;

            if(left == -1 && right == -1) continue;

            if(map.containsKey(left) || map.containsKey(right)) return false;
            if(!map.containsKey(node)) return false;


            if(left != -1) {
                map.putIfAbsent(left, new int[]{-1, -1});
            }
            if(right != -1) {
                map.putIfAbsent(right, new int[]{-1, -1});
            }
            node++;
        }
        return true;
    }

    /* https://leetcode.com/problems/closest-divisors/ */
    public int[] closestDivisors(int num) {
        int[] res1 = printDivisors(num + 1);
        int[] res2 = printDivisors(num + 2);

        int diff1 = Math.abs(res1[0] -  res1[1]);
        int diff2 = Math.abs(res2[0] -  res2[1]);
        if(diff1 < diff2){
            return res1;
        }else{
            return res2;
        }
    }

    public int[] printDivisors(int n) {
        int diff = Integer.MAX_VALUE;
        int[] res = new int[2];
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                int curr = Math.abs(i - (n/i));
                if (n / i == i){
                    res[0] = i;
                    res[1] = i;
                    return res;
                }else if(curr < diff){
                    diff = curr;
                    res[0] = i;
                    res[1] = n / i;
                }
            }
        }
        return res;
    }

    public String largestMultipleOfThree(int[] digits) {
        Arrays.sort(digits);
        int sum = 0;
        int[] count = new int[10];
        for (int num : digits) {
            sum += num;
            count[num]++;
        }
        if (sum == 0) {
            return "0";
        }
        if (sum % 3 == 0) {
            return solve(count, sum, 0);
        }
        if (sum % 3 == 1) {
            return solve(count, sum, 1);
        }
        if (sum % 3 == 2) {
            return solve(count, sum, 2);
        }
        return "";
    }

    private String solve(int[] count, int sum, int num) {
        if (num == 0) {
            return buildStr(count);
        }
        int mod = num;
        while (sum % 3 != 0) {
            if (count[mod]-- > 0) {
                sum -= mod;
            }
            if (mod % 3 == num || count[mod] <= 0) {
                mod += 3;
            }
            if (mod > 9) {
                mod = (mod + mod) % 3;
            }
        }
        return buildStr(count);
    }

    private String buildStr(int[] count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 9; i >= 0; i--) {
            while (count[i]-- > 0) {
                sb.append(i);
            }
        }
        return sb.toString();
    }

    /* Revisit below 2 problems*/
    /* https://leetcode.com/problems/distribute-coins-in-binary-tree/ */
    int moves = 0;
    public int distributeCoins(FebruaryW3.TreeNode root) {
        helper(root);
        return moves;
    }

    public int helper(FebruaryW3.TreeNode root){
        if(root == null){
            return 0;
        }
        int left = helper(root.left);
        int right = helper(root.right);
        moves += Math.abs(left) + Math.abs(right);
        return left + right + root.val - 1;
    }

    /* https://leetcode.com/problems/binary-tree-cameras/ */
    public int minCameraCover(FebruaryW3.TreeNode root) {
        Status status = installCameras(root);
        if(status.state == State.NOT_MONITORED){
            status.cameras++;
        }
        return status.cameras;
    }

    private Status installCameras(FebruaryW3.TreeNode root) {
        if(root == null) return new Status(State.MONITORED, 0);

        Status left = installCameras(root.left);
        Status right = installCameras(root.right);

        Status curr = new Status(State.NOT_MONITORED, left.cameras + right.cameras);

        if(left.state == State.NOT_MONITORED || right.state == State.NOT_MONITORED){
            curr.cameras++;
            curr.state = State.CAMERA;
        }else if(left.state == State.CAMERA || right.state == State.CAMERA){
            curr.state = State.MONITORED;
        }
        return curr;
    }

    enum State {
        MONITORED, NOT_MONITORED, CAMERA
    }

    class Status {
        int cameras;
        State state;
        public Status(State state, int cameras){
            this.cameras = cameras;
            this.state = state;
        }
    }

    /* Input: [30,20,150,100,40]
    Output: 3
    Explanation: Three pairs have a total duration divisible by 60:
    (time[0] = 30, time[2] = 150): total duration 180
    (time[1] = 20, time[3] = 100): total duration 120
    (time[1] = 20, time[4] = 40): total duration 60

    https://leetcode.com/problems/pairs-of-songs-with-total-durations-divisible-by-60/

    */
    public int numPairsDivisibleBy60(int[] time) {
        HashMap<Integer, Integer> hMap = new HashMap<>();
        for (int i = 0; i < time.length; i++) {
            time[i] = time[i] % 60;
        }

        int count = 0;

        for (int i = 0; i < time.length; i++) {
            int curr = time[i];
            int d = Math.abs(60 - curr) % 60;
            count += hMap.getOrDefault(d, 0);
            hMap.put(curr, hMap.getOrDefault(curr, 0) + 1);
        }

        return count;
    }

    /* https://leetcode.com/problems/parallel-courses/ */
    public int minimumSemesters(int N, int[][] relations) {
        int[] indegree = new int[N+1];

        HashMap<Integer, ArrayList<Integer>> graph = new HashMap<>();

        for(int[] curr : relations){
            graph.putIfAbsent(curr[0], new ArrayList<Integer>());
            graph.get(curr[0]).add(curr[1]);
            indegree[curr[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();

        for(int i = 1; i <= N ; i++){
            if(indegree[i] == 0){
                q.offer(i);
            }
        }

        int semesters = 0;
        while(!q.isEmpty()){
            int size = q.size();

            while(size-- > 0){
                --N;
                int curr = q.poll();
                if(!graph.containsKey(curr)) continue;
                List<Integer> adj = graph.get(curr);
                for(int i = 0; i < adj.size(); i++){
                    int next = adj.get(i);
                    if(--indegree[next] == 0){
                        q.offer(next);
                    }
                }
            }
            semesters++;
        }
        return N == 0 ? semesters : -1;
    }

    /* https://leetcode.com/problems/closest-binary-search-tree-value/ */
    public int closestValue(TreeNode root, double target) {
        int a = root.val;
        TreeNode kid = root.val < target ? root.right : root.left;
        if(kid == null) return a;
        int b = closestValue(kid, target);
        return Math.abs(a - target) < Math.abs(b - target) ? a : b;
    }

    /* https://leetcode.com/problems/closest-binary-search-tree-value-ii/ */
    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        LinkedList<Integer> list = new LinkedList<>();
        helper(root, target, k, list);
        return list;
    }

    private boolean helper(TreeNode root, double target, int k, LinkedList<Integer> list) {
        if(root == null )return false;

        if(helper(root.left, target, k, list)){
            return true;
        }

        if(list.size() == k){
            if(Math.abs(target - list.peekFirst()) < Math.abs(root.val - target)){
                return true;
            }else{
                list.removeFirst();
            }
        }
        list.addLast(root.val);

        return helper(root.right, target, k, list);
    }

    public static void main(String[] args) {
        FebruaryW4 w4 = new FebruaryW4();
        int[][] relations = new int[][]{
                {1,2},
                {2,3},
                {3,1}
        };

        //w4.removeDuplicates("deeedbbcccbdaa", 3);
        int[][] mat = new int[][] {
                {1,1,3,2,4,3,2},
                {1,1,3,2,4,3,2},
                {1,1,3,2,4,3,2},
        };

        int[][] mat1 = new int[][] {
                {18,70},
                {61,1},
                {25,85},
                {14,40},
                {11,96},
                {97,96},
                {63,45},
        };

        w4.maxSideLength(mat, 4);
    }


    /* https://leetcode.com/problems/remove-all-adjacent-duplicates-in-string-ii/ */
    public String removeDuplicatesRec(String s, int k) {
        StringBuilder builder = new StringBuilder(s);
        int end = 0;
        int start = 0;
        StringBuilder newBuilder = new StringBuilder(s);
        int count = 0;
        for(int i = 0; i < builder.length(); i++){
            end = i;
            start = i;
            int tmp = k;
            while(end < builder.length() && builder.charAt(start) == builder.charAt(end) && tmp > 0){
                tmp--;
                end++;
            }
            if(tmp == 0){
                newBuilder.delete(start - count*k, end - count*k);
                i = end;
                count++;
            }
        }
        //System.out.println(newBuilder.toString());
        if(builder.toString().equals(newBuilder.toString())){
            return s;
        }
        return removeDuplicatesRec(newBuilder.toString(), k);
    }

    public String removeDuplicates(String s, int k) {
        Deque<Pair> stack = new ArrayDeque<>();
        stack.push(new Pair('#', 0));

        for(char ch : s.toCharArray()){
            if(stack.peek().first != ch){
                stack.push(new Pair(ch, 1));
            }else{
                int curr = stack.peek().second + 1;
                stack.pop();
                if(curr != k){
                    stack.push(new Pair(ch, curr));
                }
            }
        }

        StringBuilder res = new StringBuilder();
        while (stack.size() > 1){
            Pair pair = stack.pop();
            for (int i = 0; i < pair.second; i++) {
                res.append(pair.first);
            }
        }
        return res.reverse().toString();
    }

    class Pair {
        Character first;
        Integer second;
        public Pair(Character ch, Integer val){
            first = ch;
            second = val;
        }
    }

    /* https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/description/ 
        Revisit : DP(Dynamic Programming)
    */
     public int maxSideLength(int[][] mat, int threshold) {
            int m = mat.length;
            int n = mat[0].length;

            int[][] sum = new int[m + 1][n + 1];

            for(int i = 1; i <= m; i++){
                for(int j = 1; j <=n; j++){
                    //minus of diagonal as its added twice
                    sum[i][j] = sum[i-1][j] + sum[i][j-1] - sum[i-1][j-1] + mat[i-1][j-1];
                }
            }

            int low = 0, high = Math.min(m, n);

            while(low <= high){
                int mid = (low + high) / 2;
                if(squareExists(mid, sum, threshold)){
                    low = mid + 1;
                }else{
                    high = mid - 1;
                }
            }
            return high;
     }

     public boolean squareExists(int len, int[][] sum, int threshold){
            for(int i = len; i < sum.length ; i++){
                for(int j = len; j < sum[0].length; j++){
                    if(sum[i][j] - sum[i-len][j] - sum[i][j-len] + sum[i-len][j-len] >= threshold) return true;
                }
            }
            return false;
     }

    /* https://leetcode.com/problems/interleaving-string/ */
    public boolean isInterleave(String s1, String s2, String s3) {
        char[] ch1 = s1.toCharArray();
        char[] ch2 = s2.toCharArray();
        char[] ch3 = s3.toCharArray();

        boolean[][] dp = new boolean[ch1.length + 1][ch2.length + 1];
        for(int i = 0; i < dp.length; i++){
            for(int j = 0; j < dp[i].length; j++){
                int l = i + j - 1;
                if(i == 0 && j == 0){
                    dp[i][j] = true;
                }else if(i == 0){
                    if(ch2[j-1] == ch3[l]){
                        dp[i][j] = dp[i][j-1];
                    }
                }else if(j == 0){
                    if(ch1[i-1] == ch3[l]){
                        dp[i][j] = dp[i-1][j];
                    }
                }else{
                    dp[i][j] = (ch3[l] == ch1[i-1] ? dp[i-1][j] : false || (ch2[j-1] == ch3[l] ? dp[i][j-1] : false));
                }
            }
        }
        return dp[ch1.length][ch2.length];
    }
}