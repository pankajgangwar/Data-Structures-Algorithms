package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;
import com.pkumar7.datastructures.ListNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class MarchW3 {


    /* https://leetcode.com/problems/count-all-valid-pickup-and-delivery-options/ */
    /* https://leetcode.com/problems/number-of-islands-ii/ */
    /* https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/description/ */

    public String sortString(String s) {
        char[] arr = new char[26];

        for(char ch : s.toCharArray()){
            arr[ch - 'a']++;
        }

        StringBuilder result = new StringBuilder();
        boolean flag = true, flip = true;
        while(flag){
            flag = false;
            if(flip){
                for (char ch = 'a'; ch <= 'z'; ch++) {
                    if(arr[ch - 'a'] > 0){
                        flag = true;
                        result.append(ch);
                        --arr[ch - 'a'];
                    }
                }
                flip = false;
            }else{
                for (char ch = 'z'; ch >= 'a'; ch--) {
                    if(arr[ch - 'a'] > 0){
                        flag = true;
                        result.append(ch);
                        --arr[ch - 'a'];
                    }
                }
                flip = true;
            }
        }
        return result.toString();
    }

    /* https://leetcode.com/contest/biweekly-contest-21/problems/find-the-longest-substring-containing-vowels-in-even-counts/ */

    /* https://leetcode.com/problems/longest-zigzag-path-in-a-binary-tree/ */
    public int longestZigZag(TreeNode root) {
        helper(root, false);
        return globalMax;
    }
    
    int globalMax = 0;
    public int helper(TreeNode root, boolean isRight){
        if(root == null){
            return 0;
        }
        int l = helper(root.left, true);
        int r = helper(root.right, false);

        globalMax = Math.max(globalMax, l);
        globalMax = Math.max(globalMax, r);

        return 1 + (isRight ? r : l);
    }

    /* https://leetcode.com/problems/maximum-sum-bst-in-binary-tree/ */
    private int maxSum = 0;
    public int maxSumBST(TreeNode root) {
        postOrderTraverse(root);
        return maxSum;
    }
    private int[] postOrderTraverse(TreeNode root) {
        if (root == null) return new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0}; // {min, max, sum}, initialize min=MAX_VALUE, max=MIN_VALUE
        int[] left = postOrderTraverse(root.left);
        int[] right = postOrderTraverse(root.right);
        // The BST is the tree:
        if (!(     left != null             // the left subtree must be BST
                && right != null            // the right subtree must be BST
                && root.val > left[1]       // the root's key must greater than maximum keys of the left subtree
                && root.val < right[0]))    // the root's key must lower than minimum keys of the right subtree
            return null;
        int sum = root.val + left[2] + right[2]; // now it's a BST make `root` as root
        maxSum = Math.max(maxSum, sum);
        int min = Math.min(root.val, left[0]);
        int max = Math.max(root.val, right[1]);
        return new int[]{min, max, sum};
    }

    /* https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
    * https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/discuss/534135/C%2B%2BJava-with-picture
    * */
    public int findTheLongestSubstring(String s) {
        int state = 0, res = 0;

        HashMap<Character, Integer> vowelsMap = new HashMap<>();
        vowelsMap.put('a',0);
        vowelsMap.put('e',1);
        vowelsMap.put('i',2);
        vowelsMap.put('o',3);
        vowelsMap.put('u',4);

        HashMap<Integer, Integer> stateToIndex = new HashMap<>();
        stateToIndex.put(state, -1);

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(vowelsMap.containsKey(ch)){
                int bit = vowelsMap.get(ch);
                int shift = (1 << bit);
                state = state ^ shift;
            }
            stateToIndex.putIfAbsent(state, i);
            res = Math.max(res, i - stateToIndex.get(state) );
        }
        return res;
    }

    public int findTheLongestSubstringSlow(String s) {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put('a',0);
        map.put('e',0);
        map.put('i',0);
        map.put('o',0);
        map.put('u',0);

        int start = 0;
        int maxLength = 0;

        for(int end = 0; end < s.length(); end++) {
            char curr = s.charAt(end);

            if(map.containsKey(curr)){
                map.put(curr, map.get(curr) + 1);
            }
            HashMap<Character, Integer> temp = new HashMap<>();
            temp.put('a', map.get('a'));
            temp.put('e', map.get('e'));
            temp.put('i', map.get('i'));
            temp.put('o', map.get('o'));
            temp.put('u', map.get('u'));

            while(temp.get('a')%2 == 1 || temp.get('e')%2 == 1 || temp.get('i')%2 == 1 || temp.get('o')%2 == 1 ||
                temp.get('u')%2 == 1 ){

                char startCh = s.charAt(start);
                if(temp.containsKey(startCh)){
                    temp.put(startCh, temp.get(startCh) -1);
                }
                start++;
            }
            start = 0;
            maxLength = Math.max(maxLength, end - start + 1);
        }
        return maxLength;
    }

    /* https://leetcode.com/problems/generate-a-string-with-characters-that-have-odd-counts/ */
    public String generateTheString(int n) {
        if(n ==1){
            return "a";
        }
        if(n ==2){
            return "ab";
        }

        if(n % 2 == 1){
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < n - 2; i++){
                builder.append('a');
            }
            builder.append('b');
            builder.append('c');
            return builder.toString();
        }else{
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < n - 1; i++){
                builder.append('a');
            }
            builder.append('b');
            return builder.toString();
        }
    }

    public int bulbSwitch(int n) {
        return (int)Math.sqrt(n);
    }

    public int bulbSwitchSlow(int n) {
        int[] m = new int[n+1];
        Arrays.fill(m, 1);
        m[0] = 0;

        for(int i = 2; i <= n; i++ ){
            int tmp = i;
            int j = 1;
            while(tmp <= n){
                m[tmp] = m[tmp] ^ 1;
                j++;
                tmp = i*j;
            }
        }

        int count = 0;
        for(int i = 1; i <= n; i++){
            if(m[i] == 1) count++;
        }
        return count;
    }

    /* https://leetcode.com/problems/cracking-the-safe/ 
        De Bruijn Sequence
        https://en.wikipedia.org/wiki/De_Bruijn_sequence
        Revisit
    */
    public String crackSafe(int n, int k) {
        String strPwd = String.join("", Collections.nCopies(n, "0"));
        HashSet<String> visitedComb = new HashSet<>();
        StringBuilder sbPwd = new StringBuilder(strPwd);

        visitedComb.add(strPwd);

        int targetNumberVisited = (int)Math.pow(k, n);

        crackSafeAfter(sbPwd, visitedComb, targetNumberVisited, n, k );

        return sbPwd.toString();
    }

    public boolean crackSafeAfter(StringBuilder pwd, Set<String> visitedComb, int targetNumberVisited, int n, int k){
        if(visitedComb.size() == targetNumberVisited){
            return true;
        }

        String lastDigits = pwd.substring(pwd.length() - n + 1);

        for(char ch = '0'; ch < '0'+ k; ch++ ){
            String newComb = lastDigits + ch;
            if(!visitedComb.contains(newComb)){
                visitedComb.add(newComb);
                pwd.append(ch);

                if(crackSafeAfter(pwd, visitedComb, targetNumberVisited, n, k)){
                    return true;
                }

                pwd.deleteCharAt(pwd.length() - 1);
                visitedComb.remove(newComb);
            }
        }
        return false;
    }

    /* https://leetcode.com/problems/fruit-into-baskets/ 
    
    Input: [1,2,3,2,2]
    Output: 4
    Explanation: We can collect [2,3,2,2].
    If we started at the first tree, we would only collect [1, 2].

    */
    public int totalFruit(int[] tree) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int start = 0;
        int end = 0;

        int maxLength = 0;

        while(end < tree.length){
            int curr = tree[end];
            map.put(curr, map.getOrDefault(curr, 0) + 1);
            end++;
            while(map.size() > 2){
                int begin = tree[start];
                map.put(begin, map.get(begin) - 1);
                if(map.get(begin) == 0) map.remove(begin);
                start++;
            }

            maxLength = Math.max(maxLength, end - start);
        }
        return maxLength;
    }

    /* https://leetcode.com/problems/missing-ranges/ */
    public List<String> findMissingRanges(int[] nums, int lower, int upper) {
        List<String> result = new ArrayList<>();
        
        for (int i = 0; i <= nums.length; i++) {
            long prev = i == 0 ? lower : nums[i - 1] + 1L;
            long current = i == nums.length ? upper : nums[i] - 1L;
            appendMissing(prev, current, result);
        }
        
        return result;
    }
    
    void appendMissing(long prev, long current, List<String> result) {
        long diff = current - prev; 
        
        if (diff == 0) {
            result.add(String.valueOf(prev));
        } else if (diff >= 1) {
            result.add(prev + "->" + current);    
        }
    }


    /* https://leetcode.com/problems/summary-ranges/ */
    public List<String> summaryRanges(int[] nums) {
        
        List<String> res = new ArrayList<>();
        
        if(nums.length == 0) return res;
        
        int start = nums[0];
        int end = nums[0];
        
        for(int i = 1; i < nums.length; i++){
            if(nums[i] == end + 1){
                end++;
            }else{
                if(start == end){
                    res.add(""+start);
                }else{
                    res.add(start + "->" + end);
                }
                start = end = nums[i];
            }
        }
        if(start == end){
            res.add(""+start);
        }else{
            res.add(start + "->" + end);
        }
        
        return res;
    }

    /* https://leetcode.com/problems/student-attendance-record-i/ */
    public boolean checkRecord(String s) {
        int absent = 0;
        int late = 0;
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) == 'A'){
                absent++;
                late = 0;
                if(absent > 1) return false;
            }else if(s.charAt(i) == 'L'){
                late++;
                if(late > 2) return false;
            }else{
                late = 0;
            }
        }
        return true;
    }

    /* https://leetcode.com/problems/student-attendance-record-ii/ */
    long mod = (long)1e9+7;
    List<String> res = new ArrayList<>();
    public int checkRecord(int n) {
        char[] arr = new char[]{'A','P','L'};
        checkRecordRec(n, new StringBuilder(), arr);
        return res.size();
    }
    
    public void checkRecordRec(int n, StringBuilder builder, char[] arr) {
        if(n == builder.length()){
            res.add(builder.toString());
            return;
        }
        
        for(int i = 0; i < arr.length; i++){
            char ch = arr[i];
            
            if(builder.indexOf("A") >= 0 && ch == 'A') continue;
            
            if(builder.length() >= 2 
               && builder.charAt(builder.length() - 1 ) == 'L' 
               && builder.charAt(builder.length() - 2 ) == 'L'
               && ch == 'L') continue;
            
            builder.append(ch);
            checkRecordRec(n, builder, arr);
            builder.deleteCharAt(builder.length() - 1);
        }
    }

    /* https://leetcode.com/problems/linked-list-cycle-ii/ */
    public ListNode detectCycle(ListNode head) {
        
        if(head == null) return null;
        
        ListNode tortoise = head;
        ListNode hare = head;
        
        while(hare != null && hare.next != null){
            tortoise = tortoise.next;
            hare = hare.next.next;
            if(tortoise == hare){
                break;
            }
        }
        
        if(hare == null || hare.next == null) return null;
        
        tortoise = head;
        
        while(tortoise != hare){
            tortoise = tortoise.next;
            hare = hare.next;
        }
        return tortoise;
    }

    /* https://leetcode.com/problems/find-the-duplicate-number/ */
    public int findDuplicateI(int[] nums) {
        if (nums.length <= 1) {
            return -1;
        }
        int tortoise = nums[0];
        int hare = nums[0];
        
        do{
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        }while(tortoise != hare);
        
        tortoise = nums[0];
        
        while(tortoise != hare){
            tortoise = nums[tortoise];
            hare = nums[hare];
        }
        return tortoise;
    }

    /* https://leetcode.com/problems/snapshot-array/ */
    class SnapshotArray {

        TreeMap<Integer, Integer>[] tmap;

        public SnapshotArray(int length) {
            tmap = new TreeMap[length];
            for(int i = 0; i < length; i++){
                tmap[i] = new TreeMap<Integer, Integer>();
                tmap[i].put(0,0);
            }
        }
        int snap_id = 0;
        public void set(int index, int val) {
            tmap[index].put(snap_id, val);
        }

        public int snap() {
            return snap_id++;
        }

        public int get(int index, int sId) {
            return tmap[index].floorEntry(sId).getValue();
        }
    }

    /* https://leetcode.com/problems/teemo-attacking/ */
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        if(timeSeries == null || timeSeries.length == 0) return 0;

        int[][] intervals = new int[timeSeries.length][2];

        for(int i = 0; i < timeSeries.length; i++){
            int[] currInt = new int[2];
            currInt[0] = timeSeries[i];
            currInt[1] = timeSeries[i] + duration;

            intervals[i] = currInt;
        }

        int res = 0;
        int[] curr = intervals[0];
        for(int[] interval : intervals){
            if(curr[1] >= interval[0]){
                curr[1] = Math.max(curr[1], interval[1]);
            }else{
                res += curr[1] - curr[0];
                curr = interval;
            }
        }
        res += curr[1] - curr[0];
        return res;
    }

    /* https://leetcode.com/problems/create-target-array-in-the-given-order/ */
    public int[] createTargetArray(int[] nums, int[] index) {
        List<Integer> target = new ArrayList<>();
        for (int i = 0; i < nums.length && i < index.length; i++) {
            int idx = index[i];
            int val = nums[i];
            target.add(idx, val);
        }
        int[] res = new int[target.size()];
        for (int i = 0; i < res.length; i++) {
            res[i] = target.get(i);
        }
        return res;
    }

    /* https://leetcode.com/problems/four-divisors/ */
    public int sumFourDivisors(int[] nums) {
        int res = 0;
        for(int i = 0; i < nums.length; i++){
            int curr = nums[i];
            int sum = helper(curr);
            if(sum > 0) {
                res += sum;
            }
        }
        return res;
    }
    public int helper(int n){
        int count = 0;
        int sum = 0;
        for(int i = 1; i <= Math.sqrt(n); i++){
            if(n%i == 0){
                if(n/i == i){
                    sum += i;
                    count += 1;;
                }else{
                    sum += i;
                    sum += n/i;
                    count += 2;
                }
                if(count > 4){
                    return -1;
                }
            }
        }
        if(count == 4){
            return sum;
        }else{
            return -1;
        }
    }

    /* https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/ */
    public boolean hasValidPath(int[][] grid) {
        Queue<int[]> bfs = new LinkedList<>();
        bfs.offer(new int[]{0,0});
        int[][][] dirs = new int[][][]{
                {{0,-1},{0,1}},
                {{-1,0},{1,0}},
                {{0,-1},{1,0}},
                {{0,1},{1,0}},
                {{-1,0},{0,-1}},
                {{-1,0},{0,1}},
        };

        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        visited[0][0] = true;

        while (!bfs.isEmpty()){
            int curr[] = bfs.poll();
            int x = curr[0], y = curr[1];
            int num = grid[x][y] -1;
            for (int[] dir: dirs[num]){
                int nx = x + dir[0], ny = y + dir[1];
                if(nx < 0 || nx >= rows || ny < 0 || ny >= cols || visited[nx][ny]) continue;

                boolean portConnect = false;
                for (int[] backDir : dirs[grid[nx][ny] -1]){
                    if(nx + backDir[0] == x && ny + backDir[1] == y) portConnect = true;
                    if(portConnect) {
                        visited[nx][ny] = true;
                        bfs.offer(new int[]{nx, ny});
                    }
                }
            }
        }
        return visited[rows-1][cols-1];
    }

    /* https://leetcode.com/problems/dota2-senate/ */
    public String predictPartyVictory(String senate) {
        return "Dire";
    }

    /* https://leetcode.com/problems/data-stream-as-disjoint-intervals/ */
    class SummaryRanges {

    /** Initialize your data structure here. */
    TreeMap<Integer, int[]> tMap;
    public SummaryRanges() {
        tMap = new TreeMap<Integer, int[]>();
    }
    
    public void addNum(int val) {
        if(tMap.containsKey(val)) return;
        Integer lowerKey = tMap.lowerKey(val);
        Integer higherKey = tMap.higherKey(val);

        if(lowerKey != null && higherKey != null && tMap.get(lowerKey)[1]+1 == val 
            && tMap.get(higherKey)[0]-1 == val){
            tMap.get(lowerKey)[1] = tMap.get(higherKey)[1];
            tMap.remove(higherKey);
        }else if(lowerKey != null && tMap.get(lowerKey)[1] + 1 <= val){
            tMap.get(lowerKey)[1] = Math.max(val, tMap.get(lowerKey)[1]);
        }else if(higherKey != null && tMap.get(higherKey)[0] - 1 == val){
            tMap.put(val, new int[]{val, tMap.get(higherKey)[1]});
            tMap.remove(higherKey);
        }else{
            tMap.put(val, new int[]{val, val});
        }
    }
    
    public int[][] getIntervals() {
        int[][]res = new int[tMap.size()][2];
        int i = 0;
        for(int[] curr : tMap.values()){
            res[i++] = curr;
        }
        return res;
    }
}

    /* https://leetcode.com/problems/find-right-interval/ */
    public int[] findRightInterval(int[][] intervals) {
            TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
            for(int i = 0; i < intervals.length; i++){
                map.put(intervals[i][0], i);
            }

            int[]res = new int[intervals.length];
            for(int i = 0; i < intervals.length; i++){
                Integer ceilingKey = map.ceilingKey(intervals[i][1]);
                if(ceilingKey != null){
                    res[i] = map.get(ceilingKey);
                }else{
                    res[i] = -1;
                }
            }
            return res;
        }

        /* https://leetcode.com/problems/guess-number-higher-or-lower/  */
        class GuessGame {
           public int guess(int pick){
               return 5;
           }
        }

    public class Solution extends GuessGame {
        public int guessNumber(int n) {

            int low = 1;
            int high = n;

            while(low <= high){
                int mid = low + (high - low)/2;
                int guess = guess(mid);
                if(guess == 0){
                    return mid;
                }else if(guess < 0){
                    high = mid-1;
                }else{
                    low = mid+1;
                }
            }
            return -1;
        }
    }

    /* https://leetcode.com/problems/guess-number-higher-or-lower-ii/ */
    public int getMoneyAmount(int n) {
        int[][] dp = new int[n+1][n+1];
        
        return getAmountDP(dp, 1, n);
    }
    
    public int getAmountDP(int[][] dp, int start, int end ){
        if(start >= end) return 0;
        if(dp[start][end] != 0 ) return dp[start][end];
        
        int res = Integer.MAX_VALUE;
        
        for(int x = start; x <= end; x++ ){
            int max = x + Math.max(getAmountDP(dp, start, x - 1), 
                              getAmountDP(dp, x + 1, end));
            res = Math.min(res, max);
        }
        dp[start][end] = res;
        return res;
    }

    /* https://leetcode.com/problems/confusing-number/ */
    public boolean confusingNumber(int N) {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(8, 8);
        map.put(9, 6);
        map.put(6, 9);
        map.put(1, 1);
        map.put(8, 8);
        map.put(0, 0);
        
        String number = String.valueOf(N);
        
        StringBuilder builder = new StringBuilder();
        
        
        for(int i = number.length()-1; i >= 0; --i) {
            int val = number.charAt(i) - '0';
            if(!map.containsKey(val)) return false;
            else builder.append(map.get(val));
        }
        if(builder.toString().equals(number)) return false;
        
        return true;
    }

    /*https://leetcode.com/problems/split-array-into-consecutive-subsequences/ */
    public boolean isPossible(int[] nums) {
        Map<Integer, PriorityQueue<Integer>> map = new HashMap<Integer, PriorityQueue<Integer>>();
        
        for(int curr : nums){
            if(map.containsKey(curr - 1) && map.get(curr - 1).size() > 0){
                PriorityQueue<Integer> pq = map.get(curr -1);
                int len = pq.poll();
                if(map.get(curr) == null){
                    map.put(curr, new PriorityQueue<>());
                }
                map.get(curr).add(len+1);
            }else {
                if(map.get(curr) == null){
                    map.put(curr, new PriorityQueue<>());
                }
                map.get(curr).add(1);
            }
        }
        
        for(PriorityQueue<Integer> pq : map.values()){
            while(pq.size() > 0){
                if(pq.poll() < 3){
                    return false;
                }
            }
        }
        return true;
    }

    /* https://leetcode.com/problems/longest-absolute-file-path/ */
    public int lengthLongestPath(String input) {
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        
        int maxLen = 0;
        
        for(String str : input.split("\n")){
            int level = str.lastIndexOf("\t") + 1;
            int numOfTabs = level + 1;
            while(numOfTabs < stack.size())
                stack.pop();

            int len = stack.peek() + str.length() - level + 1;
            stack.push(len);
            
            if(str.contains(".")){
                maxLen = Math.max(maxLen, len-1);
            }
        }
        return maxLen;
    }


    /* https://leetcode.com/problems/guess-the-word/ */

    interface Master {
        int guess(String guess);
    }

    public void findSecretWord(String[] wordlist, Master master) {
        for(int i = 0, x = 0; i < 10 && x < 6; i++){
            String guess = wordlist[new Random().nextInt(wordlist.length)];
            x = master.guess(guess);
            
            List<String> list = new ArrayList<>();
            for(String s : wordlist){
                if(match(s, guess) == x){
                    list.add(s);
                }
            }
            wordlist = list.toArray(new String[list.size()]);
        }
    }
    
    public int match(String a, String b){
        int match = 0;
        for(int i = 0; i < a.length(); i++){
            if(a.charAt(i) == b.charAt(i)){
                match++;
            }
        }
        return match;
    }

    public static void main(String[] args) {
        MarchW3 w3 = new MarchW3();
        w3.findTheLongestSubstring("leetcodueisgreat");
        //w3.findPoisonedDuration(new int[]{1,4}, 2);
       // w3.binarianShortestArray();
    }

    /* https://coderanch.com/t/680417/java/Find-shortest-array-condition */
    public int binarianShortestArray() {
        int[] A = new int[]{1,0,2,0,0,2};
        double binarian = 0;
        for (int i = 0; i < A.length; i++) {
            binarian = binarian + Math.pow(2, A[i]);
        }
        int binarian1 = (int) binarian;
        List<Integer> res = new ArrayList<>();
        while (binarian1 > 0) {
            res.add(binarian1 % 2);
            binarian1 = binarian1 / 2;
        }
        int minLength = 0;
        int sum = 0;
        int j = res.size();
        List<Integer> list = new ArrayList<>();
        for(int i = res.size()-1; i >=0; --i){
            j--;
            if(res.get(i) == 0) continue;
            sum = sum + (res.get(i) * (int) Math.pow(2, j));
            minLength++;
            list.add(j);
        }
        System.out.println("minLength = " + minLength + " sum " + sum + " list " + list);
        return minLength;
    }


    /* https://leetcode.com/problems/stone-game-ii/ */
    int[] sums;
    int[][] hash;
    public int stoneGameII(int[] piles) {
        if(piles == null || piles.length == 0) return 0;
        int n = piles.length;

        sums = new int[n];
        sums[n-1] = piles[n-1];
        for(int i = n-2; i >= 0; i--){
            sums[i] = sums[i+1] + piles[i];
        }
        hash = new int[n][n];
        return helper(piles, 0, 1);
    }

    public int helper(int[] a, int i, int M){
        if(i == a.length) return 0;
        if(2*M >= a.length -i){
            return sums[i];
        }
        if(hash[i][M] != 0) return hash[i][M];
        int min = Integer.MAX_VALUE;
        for(int x = 1; x <= 2*M; x++){
            min = Math.min(min, helper(a, i+x, Math.max(M, x)));
        }
        hash[i][M] = sums[i] - min;
        return hash[i][M];
    }

}
