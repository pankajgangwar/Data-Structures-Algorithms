package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

public class MarchW4 {

    private int[][]dir = new int[][]{{-2,-1},{-1,-2},{1,-2},{2,-1},{2,1},{1,2},{-1,2},{-2,1}};

    public double knightProbability(int N, int K, int r, int c) {
        //return helper(N, K, r, c);
        double[][][] memo = new double[N][N][K+1];
        return helperMemo(N, K, r, c, memo);
    }

    public double helperMemo(int N, int K, int curr_x, int curr_y, double[][][] memo){
        if(K == 0){
            return 1;
        }
        if(memo[curr_x][curr_y][K] > 0 ){
            return memo[curr_x][curr_y][K];
        }

        int[] x_move = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] y_move = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };

        double res = 0;
        for(int i = 0; i < x_move.length; i++) {
            int next_x = curr_x + x_move[i];
            int next_y = curr_y + y_move[i];

            if(next_x < 0 || next_x >= N || next_y < 0 || next_y >= N) continue;

            res += 0.125 * helperMemo(N, K-1, next_x, next_y, memo);
        }
        memo[curr_x][curr_y][K] = res;
        return res;
    }

    public double helper(int N, int K, int curr_x, int curr_y){
        if(K == 0){
            return 1;
        }
        int[] x_move = new int[] { 2, 1, -1, -2, -2, -1, 1, 2 };
        int[] y_move = new int[] { 1, 2, 2, 1, -1, -2, -2, -1 };

        double res = 0;
        for(int i = 0; i < x_move.length; i++ ){
            int next_x = curr_x + x_move[i];
            int next_y = curr_y + y_move[i];

            if(next_x < 0 || next_x >= N || next_y < 0 || next_y >= N) continue;

            res += 0.125 * helper(N, K-1, next_x, next_y);
        }
        return res;
    }

    /* https://leetcode.com/problems/number-of-islands-ii/ */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<Integer>();
        if(m <= 0 || n <= 0) return res;
        int[] parent = new int[n * m];
        Arrays.fill(parent, -1);

        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        int count = 0;
        for(int[] pos : positions){
            int curr_x = pos[0];
            int curr_y = pos[1];
            int currRoot = n * curr_x + curr_y;

            if(parent[currRoot] != -1){
                res.add(count);
                continue;
            }
            parent[currRoot] = currRoot;
            count++;

            for (int[] dir : dirs) {
                int next_x = dir[0] + pos[0];
                int next_y = dir[1] + pos[1];
                int neighborId = n * next_x + next_y;
                if (next_x < 0 || next_x >= m || next_y < 0 || next_y >= n || parent[neighborId] == -1)
                    continue;

                int neighborRoot = find(parent, neighborId);
                if (currRoot != neighborRoot) {
                    parent[currRoot] = neighborRoot;
                    currRoot = neighborRoot;
                    count--;
                }
            }
            res.add(count);
        }
        return res;
    }

    public int find(int[] parent, int id) {
        while (id != parent[id]) {
            parent[id] = parent[parent[id]];    // path compression by halving
            id = parent[id];
        }
        return id;
    }

    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        TreeSet<Integer> set = new TreeSet<>();

        for(int n : arr2){
            set.add(n);
        }

        int count = 0;
        for(int n : arr1){
            Integer higher = set.ceiling(n);
            Integer lower = set.floor(n);
            int diff = 0;

            if(higher == null){
                diff = Math.abs(lower - n);
            }else if(lower == null){
                diff = Math.abs(higher - n);
            }else{
                diff = Math.min(higher - n, n - lower);
            }
            if(diff > d){
                System.out.println(n);
                count++;
            }
        }
        return count;
    }

    class WorkerRatio {
        double ratio;
        double quality;

        public WorkerRatio(double ratio, double quality) {
            this.ratio = ratio;
            this.quality = quality;
        }
    }
    public double mincostToHireWorkers(int[] quality, int[] wage, int K) {
        int n = quality.length;
        List<WorkerRatio> list = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            WorkerRatio workerRatio = new WorkerRatio((double)wage[i]/quality[i], (double)quality[i]);
            list.add(workerRatio);
        }

        Collections.sort(list, (w1, w2) -> Double.compare(w1.ratio, w2.ratio));

        double sum = 0, ans = Double.MAX_VALUE;
        PriorityQueue<Double> pq = new PriorityQueue<>();
        for (WorkerRatio worker: list) {
            sum += worker.quality;
            pq.add(-worker.quality);
            if (pq.size() > K) sum += pq.poll();
            if (pq.size() == K) ans = Math.min(ans, sum * worker.ratio);
        }
        return ans;
    }

    /* https://leetcode.com/problems/sum-of-subsequence-widths/
    * Revisit
    * */

    public int sumSubseqWidths(int[] A) {
        int n = A.length;
        long pow[] = new long[n];
        
        Arrays.sort(A);
        
        int mod = (int)1e9+7;
        
        pow[0] = 1;
        for(int i = 1; i < n; i++ ){
            pow[i] = 2*pow[i-1] % mod;
        }
        
        long res = 0;
        for(int i = 0; i < n; i++){
            res = (res + (pow[i] - pow[n-i-1]) * A[i] ) % mod;
        }
        return (int)res;
    }


    /* https://leetcode.com/problems/nested-list-weight-sum/ */
    // This is the interface that allows for creating nested lists.
    // You should not implement it, or speculate about its implementation
    public interface NestedInteger {
        // @return true if this NestedInteger holds a single integer, rather than a nested list.
        public boolean isInteger();
            // @return the single integer that this NestedInteger holds, if it holds a single integer
        // Return null if this NestedInteger holds a nested list
        public Integer getInteger();
            // Set this NestedInteger to hold a single integer.
        public void setInteger(int value);
            // Set this NestedInteger to hold a nested list and adds a nested integer to it.
        public void add(NestedInteger ni);
            // @return the nested list that this NestedInteger holds, if it holds a nested list
        // Return null if this NestedInteger holds a single integer
        public List<NestedInteger> getList();
    }

    public int depthSum(List<NestedInteger> nestedList) {
        return helper(nestedList, 1);
    }

    public int helper(List<NestedInteger> nestedList, int depth) {
        int sum = 0;
        for(int i = 0; i < nestedList.size(); i++){
            NestedInteger curr = nestedList.get(i);
            if(curr.isInteger()){
                sum += curr.getInteger() * depth;
            }else if(curr.getList() != null && !curr.getList().isEmpty()){
                sum += helper(curr.getList(), depth + 1);
            }
        }
        return sum;
    }

    /* https://leetcode.com/problems/nested-list-weight-sum-ii/ */
    public int depthSumInverse(List<NestedInteger> nestedList) {
        int height = getHeight(nestedList);
        return depthSumBottomUp(nestedList, height);
    }

    public int depthSumBottomUp(List<NestedInteger> nestedList, int height) {
        return helperBottomUp(nestedList, height);
    }

    public int helperBottomUp(List<NestedInteger> nestedList, int depth) {
        int sum = 0;
        for(int i = 0; i < nestedList.size(); i++){
            NestedInteger curr = nestedList.get(i);
            if(curr.isInteger()){
                sum += curr.getInteger() * depth;
            }else if(curr.getList() != null && !curr.getList().isEmpty()){
                sum += helperBottomUp(curr.getList(), depth -1);
            }
        }
        return sum;
    }

    public int getHeight(List<NestedInteger> nestedList){
        if(nestedList == null){
            return 0;
        }
        int max = 1;
        for(int i = 0; i < nestedList.size(); i++){
            NestedInteger curr = nestedList.get(i);
            if(curr.isInteger()) continue;

            if(!curr.isInteger())
                max = Math.max( max, 1 + getHeight(curr.getList()));
        }
        return max;
    }

    /* Revisit Excellent BFS approach */
    public int depthSumInverseBFS(List<NestedInteger> nestedList){
        Queue<List<NestedInteger>> q = new LinkedList<>();
        q.offer(nestedList);
        int sum = 0;
        int levelSum = 0;
        while(!q.isEmpty()){
            int size = q.size();
            while(size-- > 0){
                List<NestedInteger> list = q.poll();
                for(NestedInteger n : list){
                    if(n.isInteger()){
                        levelSum += n.getInteger();
                    }else{
                        q.offer(n.getList());
                    }
                }
            }
            sum += levelSum;
        }
        return sum;
    }

    /* https://leetcode.com/problems/array-nesting/ */
    public int arrayNesting(int[] nums) {
        HashSet<Integer> sets = new HashSet<>();
        int len = 0;
        for(int i = 0; i < nums.length; i++){
            int n = nums[i];
            int currLen = 0;
            if(!sets.contains(i)){
                do{
                    sets.add(n);
                    n = nums[n];
                    currLen++;
                }while(n != nums[i]);
            }
            len = Math.max(len, currLen);
        }
        return len;
    }

    /* https://leetcode.com/problems/flatten-nested-list-iterator/ */
    public class NestedIterator implements Iterator<Integer> {

        Stack<NestedInteger> stack = new Stack<>();
        public NestedIterator(List<NestedInteger> nestedList) {
            buildStack(nestedList);
        }

        @Override
        public Integer next() {
            return stack.pop().getInteger();
        }

        @Override
        public boolean hasNext() {
            while(!stack.isEmpty()){
                NestedInteger curr = stack.peek();
                if(curr.isInteger()){
                    return true;
                }else{
                    List<NestedInteger> list = stack.pop().getList();
                    for(int i = list.size() -1; i >= 0; --i){
                        NestedInteger ni = list.get(i);
                        stack.push(ni);
                    }
                }
            }
            return false;
        }

        public void buildStack(List<NestedInteger> list){
            for(int i = list.size() -1; i >= 0; --i){
                NestedInteger ni = list.get(i);
                stack.push(ni);
            }
        }
    }

    /* https://leetcode.com/problems/flatten-2d-vector/ */
    class Vector2D {
        int x = 0, y = 0;
        int[][] grid;
        public Vector2D(int[][] v) {
            grid = v;
        }

        public int next() {
            if(!hasNext()) throw new NoSuchElementException();
            return grid[x][y++];
        }

        public boolean hasNext() {
            while(x < grid.length){
                if(y < grid[x].length){
                    return true;
                }else {
                    x++;
                    y = 0;
                }
            }
            return false;
        }
    }

    /* https://leetcode.com/problems/zigzag-iterator/ */
    public class ZigzagIterator {
        Queue<Iterator<Integer>> queue;
        public ZigzagIterator(List<Integer> v1, List<Integer> v2) {
            queue = new LinkedList<>();
            if(!v1.isEmpty()) queue.offer(v1.iterator());
            if(!v2.isEmpty()) queue.offer(v2.iterator());
        }

        public int next() {
            int res = -1;
            Iterator<Integer> curr = queue.poll();
            res = curr.next();
            if(curr.hasNext()) queue.offer(curr);
            return res;
        }

        public boolean hasNext() {
            return !queue.isEmpty();
        }
    }

    /* https://leetcode.com/problems/flatten-a-multilevel-doubly-linked-list/ */
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    };

    public Node flatten(Node head) {
        if(head == null) return head;
        Node p = head;

        while(p != null){
            if(p.child == null) {
                p = p.next;
                continue;
            }

            Node child = p.child;
            while(child.next != null){
                child = child.next;
            }
            child.next = p.next;//Connect tail with current next

            if(p.next != null) p.next.prev = child; //Point current next.prev to child
            p.next = p.child;
            p.child.prev = p;
            p.child = null;
        }
        return head;
    }

    /* https://leetcode.com/problems/flatten-binary-tree-to-linked-list/ */
    public void flatten(TreeNode root) {
        helper(root);
    }
    
    public TreeNode helper(TreeNode root){
        if(root == null) return null;
        if(root.left == null && root.right == null) return root;
        
        TreeNode leftTail = helper(root.left);
        TreeNode rightTail = helper(root.right);
        
        if(leftTail != null){
            leftTail.right = root.right;
            root.right = root.left;
            root.left = null;
        }
        return rightTail == null ? leftTail : rightTail;
    }

    public int findLonelyPixel(char[][] picture) {
        HashMap<Integer, Integer> mapR = new HashMap<>();
        HashMap<Integer, Integer> mapC = new HashMap<>();

        int m = picture.length;
        int n = picture[0].length;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B'){
                    mapR.put(i, mapR.getOrDefault(i, 0) + 1);
                    mapC.put(j, mapC.getOrDefault(j, 0) + 1);
                }
            }
        }

        int res = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B' && mapR.containsKey(i) && mapC.containsKey(j)){
                    int countR = mapR.getOrDefault(i, 0);
                    int countC = mapC.getOrDefault(j, 0);
                    if(countR == 1 && countC == 1){
                        res++;
                    }
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/lonely-pixel-ii/ */
    public int findBlackPixel(char[][] picture, int N) {
        HashMap<Integer, Integer> mapR = new HashMap<>();
        HashMap<Integer, Integer> mapC = new HashMap<>();

        int m = picture.length;
        int n = picture[0].length;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B'){
                    mapR.put(i, mapR.getOrDefault(i, 0) + 1);
                    mapC.put(j, mapC.getOrDefault(j, 0) + 1);
                }
            }
        }

        int res = 0;
        for(int i = 0; i < m; i++){
            for(int j = 0; j < n; j++){
                if(picture[i][j] == 'B' && mapR.containsKey(i) && mapC.containsKey(j)){
                    int countR = mapR.getOrDefault(i, 0);
                    int countC = mapC.getOrDefault(j, 0);
                    if((countR == N && countC == N )&& (countR == countC)){
                        res++;
                    }
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/24-game/ */
    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for(int i = 0; i < nums.length; i++){
            list.add((double)nums[i]);
        }
        return helper(list);
    }

    public boolean helper(List<Double> list){
        if(list.size() == 1){
            return Math.abs(list.get(0) - 24) <= 0.1;
        }else {
            for(int i = 0; i < list.size(); i++){
                for(int j = 0; j < i; j++){
                    double a = list.get(i);
                    double b = list.get(j);

                    List<Double> vals = new ArrayList<>();
                    vals.addAll(Arrays.asList(a + b, a - b, b - a, a * b, a / b, b / a));

                    List<Double> copy = new ArrayList<>(list);
                    copy.remove(i);
                    copy.remove(j);

                    for(double d : vals){
                        copy.add(d);
                        if(helper(list)) return true;
                        copy.remove(list.size()-1);
                    }
                }
            }
        }
        return false;
    }

    /* https://leetcode.com/problems/find-lucky-integer-in-an-array/ */
    public int findLucky(int[] arr) {
        HashMap<Integer, Integer> freq = new HashMap<>();

        for(int n : arr){
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        int max = -1;
        for(Map.Entry<Integer,Integer> entry : freq.entrySet()){
            int ele = entry.getKey();
            int val = entry.getValue();

            if(ele == val){
                max = Math.max(max, ele);
            }
        }
        return max;
    }

    public int numOfIncSubseqOfSizeK(int arr[], int n, int k) {
        int dp[][] = new int[k][n], sum = 0;

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int l = 1; l < k; l++) {
            for (int i = l; i < n; i++) {
                dp[l][i] = 0;
                for (int j = l - 1; j < i; j++) {
                    if (arr[j] < arr[i]) {
                        dp[l][i] += dp[l - 1][j];
                    }
                }
            }
        }
        for (int i = k - 1; i < n; i++) {
            sum += dp[k - 1][i];
        }
        return sum;
    }

    public int numOfDecSubseqOfSizeK(int arr[], int n, int k) {
        int dp[][] = new int[k][n], sum = 0;

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int l = 1; l < k; l++) {
            for (int i = l; i < n; i++) {
                dp[l][i] = 0;
                for (int j = l - 1; j < i; j++) {
                    if (arr[j] > arr[i]) {
                        dp[l][i] += dp[l - 1][j];
                    }
                }
            }
        }
        for (int i = k - 1; i < n; i++) {
            sum += dp[k - 1][i];
        }
        return sum;
    }

    /* https://leetcode.com/problems/count-number-of-teams/ */
    public int numTeams(int[] rating) {
        int res = 0;
        for (int i = 1; i < rating.length - 1; ++i) {
            int less[] = new int[2], greater[] = new int[2];
            for (int j = 0; j < rating.length; ++j) {
                if (rating[i] < rating[j])
                    ++less[j > i ? 1 : 0];
                if (rating[i] > rating[j])
                    ++greater[j > i ? 1 : 0];
            }
            res += less[0] * greater[1] + greater[0] * less[1];
        }
        return res;
    }

    /* https://leetcode.com/problems/design-underground-system/ */
    class UndergroundSystem {
        HashMap<String, PairCheckout> checkoutMap = new HashMap<>(); // Route, <Totaltime, Count>
        HashMap<Integer, PairCheckin> checkinMap = new HashMap<>(); // Id, <SourceStation, timeCheckin>

        public UndergroundSystem() {}

        public void checkIn(int id, String stationName, int t) {
            checkinMap.put(id, new PairCheckin(stationName, t));
        }

        public void checkOut(int id, String stationName, int t) {
            PairCheckin checkinPair = checkinMap.get(id);
            int totalTime = t - checkinPair.getValue();
            String route = checkinPair.getKey() + "->" + stationName;
            PairCheckout checkoutPair = checkoutMap.getOrDefault(route, new PairCheckout(0, 0));

            checkoutMap.put(route, new PairCheckout(checkoutPair.getKey() + totalTime, checkoutPair.getValue() + 1));
        }

        public double getAverageTime(String startStation, String endStation) {
            String route = startStation + "_" + endStation;
            PairCheckout pair = checkoutMap.get(route);
            return (double) pair.getKey() / pair.getValue();
        }
    }

    class PairCheckin{
        String key;
        Integer val;
        public PairCheckin(String key, Integer val){
            this.key = key;
            this.val = val;
        }
        public String getKey() {return key;}

        public Integer getValue() {return val;}
    }

    class PairCheckout{
        Integer key;
        Integer val;
        public PairCheckout(Integer key, Integer val){
            this.key = key;
            this.val = val;
        }
        public Integer getKey() {return key;}

        public Integer getValue() {return val;}
    }

    /* https://leetcode.com/problems/binary-tree-vertical-order-traversal/ */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        TreeMap<Integer, TreeMap<Integer,ArrayList<Integer>>> map = new TreeMap<>();

        findHorizontalDistance(root, 0, 0, map);

        List<List<Integer>> result = new ArrayList<>();

        for(TreeMap<Integer,ArrayList<Integer>> yValues : map.values()){
            result.add(new ArrayList<>());
            for(ArrayList<Integer> pq : yValues.values()){
                for(int ele : pq){
                    result.get(result.size()-1).add(ele);
                }
            }
        }

        return result;
    }

    public void findHorizontalDistance(TreeNode root, int x, int y, TreeMap<Integer, TreeMap<Integer,ArrayList<Integer>>> map){
        if(root == null){
            return;
        }
        if(map.get(x) == null){
            map.put(x, new TreeMap<>(Collections.reverseOrder()));
        }
        if(map.get(x).get(y) == null){
            map.get(x).put(y, new ArrayList<>());
        }

        map.get(x).get(y).add(root.val);
        findHorizontalDistance(root.left, x - 1, y - 1, map);
        findHorizontalDistance(root.right, x + 1, y - 1, map);
    }

    /* https://leetcode.com/problems/rotate-image/ */
    public void rotate(int[][] matrix) {
        int s = 0;
        int e = matrix.length-1;

        while(s < e){ //Swap rows from top and bottom
            int[] temp = matrix[s];
            matrix[s] = matrix[e];
            matrix[e] = temp;
            s++;
            e--;
        }

        //Swap in symmetry
        for(int i = 0; i < matrix.length; i++){
            for(int j = i + 1; j < matrix[i].length; j++){
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
    }

    /* https://leetcode.com/problems/basic-calculator/ */
    public int calculate(String s) {
        Stack<Integer> stack = new Stack<>();
        int number = 0;
        int res = 0;
        int sign = 1;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(Character.isDigit(ch)){
                number = 10 * number + (int)ch - '0';
            }else if(ch == '+'){
                res += sign* number;
                sign = 1;
                number = 0;
            }else if(ch == '-'){
                res += sign * number;
                sign = -1;
                number = 0;
            }else if(ch == '('){
                stack.push(res);
                stack.push(sign);
                sign = 1;
                res = 0;
            }else if(ch == ')'){
                res += sign * number;
                number = 0;
                res = res * stack.pop();//sign
                res = res + stack.pop();//old result
            }
        }
        if(number != 0) res += sign * number;
        return res;
    }

    /* https://leetcode.com/problems/basic-calculator-ii/ */
    public int calculateII(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        int res = 0;
        char sign = '+';
        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                num = num*10 + s.charAt(i) - '0';
            }
            if((!Character.isDigit(s.charAt(i)) && s.charAt(i) != ' ') || i == s.length()-1) {
                if(sign == '+'){
                    stack.push(num);
                    num = 0;
                }else if(sign == '-'){
                    stack.push(-num);
                    num = 0;
                }else if(sign == '/'){
                    stack.push(stack.pop() / num);
                    num = 0;
                }else if(sign == '*'){
                    stack.push(stack.pop() * num);
                    num = 0;
                }
                sign = s.charAt(i);
            }
        }

        for (int ele : stack){
            res += ele;
        }
        return res;
    }

    /*  https://leetcode.com/problems/basic-calculator-iii/
        "1 + 1" = 2
        " 6-4 / 2 " = 4
        " 2*(5+5*2)/3+(6/2+8)" = 21
        "(2+6* 3+5- (3*14/7+2)*5)+3"=-12
   */
    public int calculateIII(String s) {
        Stack<Integer> stack = new Stack<>();
        int num = 0;
        char sign = '+';
        for(int i = 0; i < s.length(); i++){
            if(Character.isDigit(s.charAt(i))){
                num = num * 10 + s.charAt(i) - '0';
            }
            if(s.charAt(i) == '('){
                int count = 0;
                int j = i;
                for(; i < s.length(); ++i){
                    if(s.charAt(i) == '(') ++count;
                    if(s.charAt(i) == ')') --count;

                    if(count == 0) break;
                }
                String sub = s.substring(j+1, i);
                num = calculate(sub);
            }
            if(s.charAt(i) != ' ' && !Character.isDigit(s.charAt(i)) || i == s.length()-1){
                if(sign == '+'){
                    stack.push(num);
                    num = 0;
                }else if(sign == '-'){
                    stack.push(-num);
                    num = 0;
                }else if(sign == '/'){
                    stack.push(stack.pop() / num);
                    num = 0;
                }else if(sign == '*'){
                    stack.push(stack.pop() * num);
                    num = 0;
                }
                sign = s.charAt(i);
            }
        }
        int res = 0;
        for (int ele : stack){
            res += ele;
        }
        return res;
    }

    /* https://leetcode.com/problems/evaluate-reverse-polish-notation/ */
    public int evalRPN(String[] tokens) {
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < tokens.length; i++){
            String curr = tokens[i];
            if(curr.equals("+")){
                int second = stack.pop();
                int first = stack.pop();
                int res = first + second;
                stack.push(res);
            }else if(curr.equals("-")){
                int second = stack.pop();
                int first = stack.pop();
                int res = first - second;
                stack.push(res);
            }else if(curr.equals("/")){
                int second = stack.pop();
                int first = stack.pop();
                int res = first / second;
                stack.push(res);
            }else if(curr.equals("*")){
                int first = stack.pop();
                int second = stack.pop();
                int res = first * second;
                stack.push(res);
            }else{
                stack.push(Integer.parseInt(curr));
            }
        }
        return stack.pop();
    }

    /* https://leetcode.com/problems/remove-duplicates-from-sorted-list-ii/ */
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null) return null;
        
        if(head.next != null && head.val == head.next.val){
            
            while(head.next != null && head.val == head.next.val){
                head = head.next;               
            }
            return deleteDuplicates(head.next);
        }else{
            head.next = deleteDuplicates(head.next);
        }
        return head;
    }

    /* https://leetcode.com/problems/add-two-numbers-ii/ */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int len1 = 0, len2 = 0;

        Stack<Integer> s1 = new Stack<>();
        Stack<Integer> s2 = new Stack<>();

        ListNode t1 = l1, t2 = l2;

        while(l1 != null) {
            s1.push(l1.val);
            l1 = l1.next;
        }
        while(l2 != null) {
            s2.push(l2.val);
            l2 = l2.next;
        }

        return helper(s1, s2);
    }

    int sum = 0;
    ListNode head = new ListNode(0);
    public ListNode helper(Stack<Integer> s1, Stack<Integer> s2){
        while(!s1.isEmpty() || !s2.isEmpty()){
            if(!s1.isEmpty()) sum += s1.pop();
            if(!s2.isEmpty()) sum += s2.pop();
            head.val = sum  % 10;
            ListNode n = new ListNode(sum / 10);
            n.next = head;
            head = n;
            sum /= 10;
        }
        return head.val == 0 ? head.next : head;
    }


    /* https://leetcode.com/problems/sliding-puzzle/ */
    class BoardInfo {
        int[][] board;
        int x, y;
        int num_moves;

        public BoardInfo(int[][] board, int x, int y, int num_moves){
            this.board = board;
            this.x = x;
            this.y = y;
            this.num_moves = num_moves;
        }
    }
        public String genString(int[][] board){
            String res = "";
            for(int i = 0; i < board.length; i++){
                for(int j = 0; j < board[i].length; j++){
                    res += board[i][j];
                }
            }
            return res;
        }

        public int slidingPuzzle(int[][] board) {
            int[][] solved = new int[][]{{1, 2, 3}, {4, 5, 0}};
            String solvedStr = genString(solved);
            Queue<BoardInfo> q = new LinkedList<>();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] == 0) {
                        q.offer(new BoardInfo(board, i, j, 0));
                    }
                }
            }

            int[][] dirs = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};
            Set<String> visited = new HashSet<>();
            visited.add(Arrays.deepToString(board));
            while (!q.isEmpty()) {
                int size = q.size();
                while (size-- > 0){
                    BoardInfo info = q.poll();
                    String currStr = genString(info.board);
                    if(currStr.equals(solvedStr)){
                        return info.num_moves;
                    }
                    int curr_x = info.x;
                    int curr_y = info.y;
                    for (int[] dir : dirs) {
                        int next_x = dir[0] + curr_x;
                        int next_y = dir[1] + curr_y;

                        if (next_x >= 0 && next_x < board.length && next_y >= 0 && next_y < board[0].length) {
                            int[][] copy = copyBoard(info.board);
                            swap(copy, curr_x, curr_y, next_x, next_y);

                            String genString = genString(copy);

                            if (!visited.contains(genString)) {
                                q.offer(new BoardInfo(copy, next_x, next_y, info.num_moves + 1));
                                visited.add(genString);
                            }
                        }
                    }
                }
            }
            return 0;
        }

        public int[][] copyBoard(int[][] orgBoard){
            int[][] copy = new int[orgBoard.length][orgBoard[0].length];
            for (int i = 0; i < orgBoard.length; i++) {
                for (int j = 0; j < orgBoard[i].length; j++) {
                    copy[i][j] = orgBoard[i][j];
                }
            }
            return copy;
        }

        public void swap(int[][] board, int x, int y, int targetx, int targety) {
            int temp = board[x][y];
            board[x][y] = board[targetx][targety];
            board[targetx][targety] = temp;
        }

    int[][] dirs = new int[][]{{1,0},/*right*/
            {0,1},/* top*/
            {-1,0}, /* left */
            {0,-1}, /* bottom */
    };


    /* https://leetcode.com/problems/the-maze/ */
    public boolean hasPath(int[][] maze, int[] s, int[] d) {
        int m = maze.length;
        int n = maze[0].length;

        boolean[][] visited = new boolean[m][n];
        return bfs(maze, visited, s, d);
        //return helper(maze, visited, s, d);
    }

    public boolean bfs(int[][] maze, boolean[][] visited, int[] s, int[] d){
        Queue<int[]> q = new LinkedList<>();
        q.offer(s);

        while(!q.isEmpty()){
            int[] curr = q.poll();
            if(Arrays.equals(curr, d)) return true;
            visited[curr[0]][curr[1]] = true;

            for(int[] dir : dirs) {
                int next_x = dir[0];
                int next_y = dir[1];

                int[] next = roll(maze, curr[0], curr[1], next_x, next_y);
                if(visited[next[0]][next[1]]) continue;
                q.offer(next);
            }
        }
        return false;
    }

    public boolean helper(int[][] maze, boolean[][] visited, int[] s, int[] d){
        if(visited[s[0]][s[1]]) return false;
        if(Arrays.equals(s, d)) return true;

        visited[s[0]][s[1]] = true;

        for(int[] dir :  dirs){
            int next_x = dir[0];
            int next_y = dir[1];

            int[] newStart = roll(maze, s[0], s[1], next_x, next_y);
            if(helper(maze, visited, newStart, d)){
                return true;
            }
        }
        return false;
    }

    public int[] roll(int[][] maze, int x, int y, int rowInc, int colInc){
        while(ifCanRoll(maze, x + rowInc, y + colInc)){
            x += rowInc;
            y += colInc;
        }
        return new int[]{x, y}; // Return new start
    }

    public boolean ifCanRoll(int[][] maze, int x, int y){
        int m = maze.length;
        int n = maze[0].length;
        if(x >=0 && x < m && y >= 0 && y < n && maze[x][y] == 0)
            return true;

        return false;
    }

    /* https://leetcode.com/problems/the-maze-ii/ */
    public int shortestDistance(int[][] maze, int[] s, int[] d) {
        int m = maze.length;
        int n = maze[0].length;

        boolean[][] visited = new boolean[m][n];
        int minSteps = bfsShortestDistance(maze, visited, s, d);
        return minSteps;
    }

    public int bfsShortestDistance(int[][] maze, boolean[][] visited, int[] s, int[] d){
        // Pop the coordinates which has less no. of steps
        PriorityQueue<int[]> q = new PriorityQueue<>( (a,b) -> a[2] - b[2]); // min PriorityQueue

        q.offer(new int[]{s[0], s[1], 0});

        int m = maze.length;
        int n = maze[0].length;

        int[][] dist = new int[m][n];

        while(!q.isEmpty()){
            int[] curr = q.poll();
            if(curr[0] == d[0] && curr[1] == d[1]) return curr[2];
            visited[curr[0]][curr[1]] = true;

            for(int[] dir : dirs) {
                int next_x = dir[0];
                int next_y = dir[1];
                int[] next = roll(maze, curr[0], curr[1], next_x, next_y, curr[2]);
                if(visited[next[0]][next[1]]) continue;
                if(dist[next[0]][next[1]] == 0 || dist[next[0]][next[1]] > next[2]){
                    dist[next[0]][next[1]] = next[2];
                    q.offer(next);
                }
            }
        }
        return -1;
    }

    public int[] roll(int[][] maze, int x, int y, int rowInc, int colInc, int steps){
        while(ifCanRoll(maze, x + rowInc, y + colInc)){
            x += rowInc;
            y += colInc;
            steps = steps + 1;
        }
        return new int[]{x, y, steps}; // Return new start
    }

    public static void main(String[] args) {
        MarchW4 w4 = new MarchW4();
        int[] q = new int[]{10, 20, 5};
        int[] w = new int[]{70, 50, 30};
        int K = 2;
        int[][] board = new int[][]{{0, 0, 1, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 1}, {0, 0, 0, 0, 0}};
        int distance = w4.shortestDistance(board, new int[]{0, 4}, new int[]{4, 4});
        System.out.println("distance = " + distance);
    }
}
