package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.IntStream;

public class AprilW2 {

    public static void main(String[] args) {
        AprilW2 w2 = new AprilW2();
        //w2.stringMatching(new String[]{"leetcoder","leetcode","od","hamlet","am"});
        w2.entityParser("&amp; is an HTML entity but &ambassador; is not.");
    }

    /* https://leetcode.com/problems/find-permutation/ */


    /* https://leetcode.com/problems/my-calendar-ii/ */
    class MyCalendarTwo {
    
    List<int[]> calendar;
    public MyCalendarTwo() {
        calendar = new ArrayList<>();
    }
    
     public boolean book(int start, int end) {
         //System.out.println();
         TreeMap<Integer, Integer> overlap = new TreeMap<>();
         for(int[] curr : calendar){
             int res[] = getOverlap(curr, start, end);
             //System.out.print(res[0] + "," + res[1] + "\t");
             if(res[0] >= 0){
                 if(hasOverlap(overlap, res[0], res[1])) return false;
                 overlap.put(res[0], res[1]);
             }
         }
         //System.out.println();
         //System.out.print("Adding to calendar " + start + " , " + end);
         calendar.add(new int[]{start, end});
         return true;
     }
    
     public int[] getOverlap(int[] interval, int start, int end){
        int[] res = new int[]{-1,-1};
        if(start >= interval[1] || end <= interval[0]) return res;
        res[0] = Math.max(start, interval[0]);
        res[1] = Math.min(end, interval[1]);
        return res;
    }
    
    public boolean hasOverlap(TreeMap<Integer, Integer> calendar, int start, int end) {
        Integer floorKey = calendar.floorKey(start);
        if(floorKey != null && calendar.get(floorKey) > start ) {
            return true;
        }
        Integer ceilingKey = calendar.ceilingKey(start);
        if(ceilingKey != null && ceilingKey < end ) {
            return true;
        }
        return false;
    }
    
    TreeMap<Integer, Integer> map = new TreeMap<>();
    public boolean book1(int start, int end) {
        map.put(start, map.getOrDefault(start, 0) + 1);
        map.put(end, map.getOrDefault(end, 0) - 1);
        System.out.println();
        int active = 0;
        for(int d : map.values()){
            active += d;
            System.out.print(active + "\t");
            if(active > 2){
                map.put(start, map.get(start) - 1);
                if(map.get(start) == 0){
                    map.remove(start);
                }
                map.put(end, map.get(end) + 1);
                if(map.get(end) == 0){
                    map.remove(end);
                }
                return false;
            }
        }
        return true;
    }
    
}


    /* https://leetcode.com/explore/featured/card/30-day-leetcoding-challenge/529/week-2/3299/ */
    public String stringShift(String s, int[][] shift) {
        StringBuilder builder = new StringBuilder(s);
        
        int leftShifts = 0;
        for(int[] curr : shift){
            if(curr[0] == 1){
                curr[1] = -curr[1];
            }
            leftShifts += curr[1];
        }
        int shifts = Math.floorMod(leftShifts, s.length());
        s = s.substring(shifts) + s.substring(0, shifts);
        return s;
    }

    public String stringShiftSlow(String s, int[][] shift) {
        StringBuilder builder = new StringBuilder(s);
        
        for(int[] curr : shift){
            int dir = curr[0];
            int amount = curr[1];
            
            if(dir == 0) {
                //left
                while(amount > 0) {
                    char ch = builder.charAt(0);
                    builder.deleteCharAt(0);
                    builder.append(ch);
                    amount--;
                }
            }else{//right
               while(amount > 0) {
                   int last = builder.length();
                   char ch = builder.charAt(last-1);
                   builder.deleteCharAt(last-1);
                   builder.insert(0, ch);
                   amount--;
                } 
            }
        }
        return builder.toString();
    }

    /* https://leetcode.com/problems/contiguous-array/ */
    public int findMaxLength(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, -1);

        int count = 0;
        int max = 0;
        for(int i = 0; i < nums.length; i++){
            if(nums[i] == 0) count += -1;
            if(nums[i] == 1) count += 1;

            if(map.containsKey(count)){
                max = Math.max(max, i - map.get(count));
            }else{
                map.put(count, i);
            }
        }
        return max;
    }

    /* https://leetcode.com/problems/html-entity-parser/ */
    public String entityParser(String text) {
        HashMap<String, Character> map = new HashMap<>();
        map.put("&quot;",'\"');
        map.put("&apos;",'\'');
        map.put("&amp;",'&');
        map.put("&gt;",'>');
        map.put("&lt;",'<');
        map.put("&frasl;",'/');

        StringBuilder res = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == '&'){
                int j = i;
                while(i < text.length() && text.charAt(i) != ';'){
                    i++;
                }
                String entities = text.substring(j, i+1);
                Character entity = map.get(entities);
                if(entity == null){
                    res.append(entities);
                }else{
                    res.append(entity);
                }
            }else{
                res.append(text.charAt(i));
            }
        }
        return res.toString();
    }

    /* https://leetcode.com/problems/string-matching-in-an-array/ */
    public List<String> stringMatching(String[] words) {
        int n = words.length;
        Arrays.sort(words, (a,b) -> a.length() - b.length());
        HashSet<String> res = new HashSet<>();
        for(int i = n-1; i >= 0 ; --i){
            int j = 0;
            String curr = words[i];
            while(j < i){
                String comp = words[j];
                if(curr.contains(comp)){
                    res.add(words[j]);
                }
                j++;
            }
        }
        return new ArrayList<>(res);
    }

    /* https://leetcode.com/problems/queries-on-a-permutation-with-key/ */
    public int[] processQueries(int[] queries, int m) {
        LinkedList<Integer> p = new LinkedList<>();
        for(int i = 0; i < m; i++ ){
            p.add(i+1);
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int q = queries[i];
            int index = p.indexOf(q);
            res[i] = index;
            p.remove(index);
            p.addFirst(q);
        }
        return res;
    }

    /* https://leetcode.com/problems/closest-leaf-in-a-binary-tree/ */
    public int findClosestLeaf(TreeNode root, int k) {
        HashMap<TreeNode,  ArrayList<TreeNode>> map = new HashMap<>();
        buildGraph(root, null, map);
        TreeNode target = dfs(root, k);

        HashSet<TreeNode> visited = new HashSet<>();
        visited.add(target);

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(target);

        while(!q.isEmpty()){
            TreeNode curr = q.poll();
            if(curr.left == null && curr.right == null){
                return curr.val;
            }
            for(TreeNode neig : map.get(curr)){
                if(visited.contains(neig)) continue;
                q.offer(neig);
                visited.add(neig);
            }
        }
        return -1;
    }

    public TreeNode dfs(TreeNode root, int k){
        if(root == null) return null;
        if(root.val == k) return root;
        TreeNode left = dfs(root.left, k);
        TreeNode right = dfs(root.right, k);
        if(left != null){
            return left;
        }else {
            return right;
        }
    }

    public void buildGraph(TreeNode child, TreeNode parent,
                           HashMap<TreeNode,  ArrayList<TreeNode>> map )  {
        if(child == null) return;
        if(!map.containsKey(child)){
            map.put(child, new ArrayList<TreeNode>());

            if(parent != null){
                map.get(child).add(parent);
                map.get(parent).add(child);
            }

            buildGraph(child.left, child, map);
            buildGraph(child.right, child, map);
        }
    }

    public int findIndex(LinkedList<Integer> p, int t) {
        int len = p.size();
        return IntStream.range(0, len)
                .filter(i -> t == p.get(i))
                .findFirst() // first occurrence
                .orElse(-1); // No element found
    }



    

    /* https://leetcode.com/problems/design-snake-game/ */
    class SnakeGame {
        LinkedList<int[]> snake;
        int[][] food;
        int width;
        int height;
        int foodIdx;
        /** Initialize your data structure here.
         @param width - screen width
         @param height - screen height
         @param food - A list of food positions
         E.g food = [[1,1], [1,0]] means the first food is positioned at [1,1], the second is at [1,0]. */
        public SnakeGame(int width, int height, int[][] food) {
            this.food = food;
            this.width = width;
            this.height = height;
            this.foodIdx = 0;
            snake = new LinkedList<int[]>();
            snake.addFirst(new int[]{0,0});
        }

        /** Moves the snake.
         @param direction - 'U' = Up, 'L' = Left, 'R' = Right, 'D' = Down
         @return The game's score after the move. Return -1 if game over.
         Game over when snake crosses the screen boundary or bites its body. */
        public int move(String direction) {
            int[] head = snake.getFirst();
            int[] nextMove = new int[]{head[0], head[1]};
            switch (direction){
                case  "U":
                    nextMove[0]--;
                    break;
                case "L":
                    nextMove[1]--;
                    break;
                case "R":
                    nextMove[1]++;
                    break;
                case "D":
                    nextMove[0]++;
                    break;
            }
            if(nextMove[0] < 0 || nextMove[0] >= height || nextMove[1] < 0 || nextMove[1] >= width ) return -1;
            int[] tail = snake.getLast();
            snake.removeLast();
            if(isDead(nextMove[0], nextMove[1])) return -1;
            snake.addFirst(nextMove);
            if(food.length > foodIdx && nextMove[0] == food[foodIdx][0]
                    && nextMove[1] == food[foodIdx][1] ){
                foodIdx++;
                snake.add(tail);
                return foodIdx;
            }
            return foodIdx;
        }

        public boolean isDead(int x, int y){
            for (int i = snake.size()-1; i >= 0 ; i--) {
                if(x == snake.get(i)[0] && y == snake.get(i)[1]){
                    return true;
                }
            }
            return false;
        }
    }

    /* https://leetcode.com/problems/dice-roll-simulation/ */
    int ans = 0;
    public  void dfs(int dieLeft, int[] rollMax, int last, int curlen){
        if(dieLeft == 0) {
            ans++;
            return;
        }
        for(int i=0; i<6; i++){
            if(i == last && curlen == rollMax[i]) continue;
            dfs(dieLeft - 1, rollMax, i, i == last ? curlen + 1 : 1);
        }
    }
    public int dieSimulator(int n, int[] rollMax) {
        dfs(n ,rollMax, -1, 0);
        return ans;
    }


    public int dieSimulatorDP(int n, int[] rollMax) {
        int mod = (int)1e9 + 7;

        int[][] dp = new int[n+1][6];

        for(int a = 0; a < 6; ++a){
            for(int len = 1; len <= Math.min(n, rollMax[a]); ++len){
                dp[len][a]++;
            }
        }

        for(int i = 1; i < n; i++){
            for(int prev = 0; prev < 6; ++prev){
                for(int next = 0; next < 6; ++next){
                    if(prev == next) continue;

                    for(int len = 1; len <= rollMax[next] && i + len <= n; ++len){
                        dp[i+len][next] = (dp[i+len][next] + dp[i][prev])  % mod ;
                    }
                }
            }
        }

        int ways = 0;
        for(int a = 0; a < 6; ++a){
            ways = ( ways + dp[n][a] ) % mod;
        }
        return ways;
    }

    /* https://leetcode.com/problems/daily-temperatures/*/
    public int[] dailyTemperatures(int[] arr) {
        int n = arr.length;
        int[] res = new int[n];
        Stack<Integer> stack = new Stack<>();
        for(int i = 0; i < n; i++){
            while(!stack.isEmpty() && arr[stack.peek()] < arr[i]){
                res[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            stack.push(i);
        }
        return res;
    }

    /* https://leetcode.com/problems/simplify-path/ */
    public String simplifyPath(String path) {
        Stack<String> stack = new Stack<>();
        String[] arr = path.split("/");

        for(int i = 0; i < arr.length; i++){
            if(arr[i].equals(".")) continue;
            if(arr[i].equals("..")) {
                if(!stack.isEmpty()){
                    stack.pop();
                }
            }else if(arr[i].length() > 0){
                stack.push(arr[i]);
            }
        }

        StringBuilder builder = new StringBuilder();
        for(String s : stack){
            builder.append("/");
            builder.append(s);
        }
        return builder.length() == 0 ? "/" : builder.toString();
    }

    /* https://leetcode.com/problems/remove-duplicate-letters/ */
    /* Revisit below, Important stack problem */
    public String smallestSubsequence(String s) {
        int[] seen = new int[26];
        int[] last = new int[26];

        Stack<Integer> stack = new Stack<>();

        for(int i = 0; i < s.length(); i++){
            last[s.charAt(i) - 'a'] = i;
        }
        for (int i = 0; i < s.length(); i++ ) {
            int ch = s.charAt(i) - 'a';
            if(seen[ch]++ > 0) continue;
            while(!stack.isEmpty() && stack.peek() > ch && last[stack.peek()] > i ){
                seen[stack.peek()] = 0;
                stack.pop();
            }
            stack.push(ch);
        }

        StringBuilder builder = new StringBuilder();
        for(int n : stack){
            builder.append((char)('a' + n));
        }
        return builder.toString();
    }

    public String removeDuplicateLettersRec(String s) {
        int[] cnt = new int[26];
        
        for(int i = 0; i < s.length(); i++ ){
            ++cnt[s.charAt(i) - 'a'];
        }
        
        int pos = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) < s.charAt(pos)) pos = i;
            if(--cnt[s.charAt(i) - 'a'] == 0) break;
        }
        
        return s.length() == 0 ? "" : s.charAt(pos) + removeDuplicateLettersRec(s.substring(pos+1).replaceAll(""+s.charAt(pos), ""));
    }

    /* https://leetcode.com/problems/smallest-subsequence-of-distinct-characters/ */
    public String smallestSubsequenceRec(String s) {
        int[] cnt = new int[26];
        
        for(int i = 0; i < s.length(); i++ ){
            ++cnt[s.charAt(i) - 'a'];
        }
        
        int pos = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) < s.charAt(pos)) pos = i;
            if(--cnt[s.charAt(i) - 'a'] == 0) break;
        }
        
        return s.length() == 0 ? "" : s.charAt(pos) + smallestSubsequenceRec(s.substring(pos+1).replaceAll(""+s.charAt(pos), ""));
    }
}