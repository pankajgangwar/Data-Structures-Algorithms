package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Stream;

class JuneW2 {
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/
    /* https://www.codechef.com/problems/COUPON */
    /* https://leetcode.com/problems/brick-wall/ */

    /* DP on trees */
    /*
       https://codeforces.com/blog/entry/20935
       https://www.spoj.com/problems/PT07X/
       https://leetcode.com/problems/sum-of-distances-in-tree/
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */

    public static void main(String[] args) {
        JuneW2 obj = new JuneW2();
        int[][] pairs = new int[][]{
                {1,2},
                {2,3},
                {3,4}
        };
        //obj.findLongestChain(pairs);
        //obj.printLIS(new int[]{10,9,2,5,3,7,101,18});
        obj.largestDivisibleSubset(new int[]{2,8,18,19,20,4,16});
    }

    /* https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */

    /* LC : 743
    https://leetcode.com/problems/network-delay-time/
    */

    /*
    LC : 920
    https://leetcode.com/problems/number-of-music-playlists/
    */
    public int numMusicPlaylists(int N, int L, int K) {
        return 0;
    }

    /*
    LC : 285
    https://leetcode.com/problems/inorder-successor-in-bst/
    */
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        return inorderSuccessorRec(root, p);
    }
    public TreeNode inorderSuccessorRec(TreeNode root, TreeNode p){
        if(root ==null) return null;

        if(root.val <= p.val){
            return inorderSuccessorRec(root.right, p);
        }else{
            TreeNode left = inorderSuccessorRec(root.left, p);
            return (left != null) ? left : root;
        }
    }

    public TreeNode inorderSuccessorIterative(TreeNode root, TreeNode p) {
        if(p.right != null){
            p = p.right;
            while (p.left != null){
                p = p.left;
            }
            return p;
        }
        Stack<TreeNode> stack = new Stack<>();
        int inorder = Integer.MAX_VALUE;
        while (!stack.isEmpty() || root != null){
            while (root != null){
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if(inorder == p.val){
                return root;
            }
            inorder = root.val;
            root = root.right;
        }
        return null;
    }

    /*
    LC : 510
    https://leetcode.com/problems/inorder-successor-in-bst-ii/
    */
    public Node inorderSuccessor(Node node) {
        return null;
    }

    /*
    LC : 368
    https://leetcode.com/problems/largest-divisible-subset/
    Solution: https://leetcode.com/problems/largest-divisible-subset/discuss/683681/JAVA-DP-solution-lessLISgreater
    https://cp-algorithms.com/sequences/longest_increasing_subsequence.html
    */
    public List<Integer> largestDivisibleSubset(int[] nums) {
        if(nums.length == 0){
            return new ArrayList<>();
        }
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 1);
        int[] parent = new int[nums.length];
        Arrays.fill(parent, -1);
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                if((nums[i] % nums[j] == 0) && dp[i] < dp[j] + 1){
                    dp[i] = dp[j] + 1;
                    parent[i] = j;
                }
            }
        }
        int max = dp[0], pos = 0;
        for (int i = 1; i < nums.length ; i++) {
            if(dp[i] > max){
                max = dp[i];
                pos = i;
            }
        }
        List<Integer> res = new ArrayList<>();
        while (pos != -1){
            res.add(nums[pos]);
            pos = parent[pos];
        }
        Collections.reverse(res);
        return res;
    }

    /*
    LC : 300
    https://leetcode.com/problems/longest-increasing-subsequence/
    */
    public int lengthOfLIS(int[] nums) {
        return lengthOfLISPatienceSort(nums);
    }
    public int lengthOfLisDP(int[] nums) {
        int LIS[] = new int[nums.length];

        for(int i = 0; i < nums.length; i++){
            LIS[i] = 1;
        }

        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[i] > nums[j]){
                    LIS[i] = Math.max(LIS[i], LIS[j]+1);
                }
            }
        }

        int max = 0;
        for(int i = 0; i < LIS.length; i++){
            if(max < LIS[i]){
                max = LIS[i];
            }
        }
        return max;
    }

    public int lengthOfLISPatienceSort(int[] nums) {
        List<Integer> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            int pile = Collections.binarySearch(piles, num);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
        }
        return piles.size();
    }
    class Node implements Comparable<Node> {
        int val;
        Node prev;
        public Node(int val){
            this.val = val;
        }

        @Override
        public int compareTo(Node that) {
            return Integer.compare(this.val, that.val);
        }
    }

    public void printLIS(int[] nums){
        List<Node> piles = new ArrayList<>(nums.length);
        for (int num : nums) {
            Node node = new Node(num);
            int pile = Collections.binarySearch(piles, node);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT, Bitwise Complement
            }
            if(pile != 0){
                node.prev = piles.get(pile-1);
            }
            if (pile == piles.size()) {
                piles.add(node);
            } else {
                piles.set(pile, node);
            }
        }
        extractLIS(piles);
    }

    private void extractLIS(List<Node> piles) {
        List<Integer> result = new ArrayList<>(piles.size());
        for (Node curr = piles.isEmpty() ? null : piles.get(piles.size() - 1); curr != null; curr = curr.prev) {
            result.add(curr.val);
        }
        Collections.reverse(result);
        System.out.println("result = " + result);
    }

    /*
    LC : 792
    https://leetcode.com/problems/number-of-matching-subsequences/
    */
    public int numMatchingSubseq(String target, String[] words) {
        int count = 0;
        HashMap<String, Boolean> map = new HashMap<>();
        for(String source : words){
            if(map.containsKey(source)){
                if(map.get(source)){
                    count++;
                }
                continue;
            }
            if(isSubSequence(source, target)){
                count++;
                map.put(source, true);
            }else{
                map.put(source, false);
            }
        }
        return count;
    }

    public boolean isSubSequence(String s, String t){
        int i = 0, j = 0;
        while (i < s.length() && j < t.length()){
            if(s.charAt(i) == t.charAt(j)){
                i++;
            }
            j++;
        }
        return i == s.length();
    }

    /* 
    LC : 1055
    https://leetcode.com/problems/shortest-way-to-form-string/
    */
    public int shortestWay(String source, String target) {
        for (int i = 0; i < target.length(); i++) {
            String ch = target.charAt(i)+ "";
            if(!source.contains(ch)){ // This character doesn't exists
                return -1;
            }
        }
        Queue<String> q = new LinkedList<>();
        q.offer(target);
        int min_steps = 0;
        while (!q.isEmpty()){
            String curr = q.poll();
            int i = 0, j = 0;
            while (i < curr.length() && j < source.length()){
                if(curr.charAt(i) == source.charAt(j)){
                    i++;
                }
                j++;
            }
            if(i < curr.length()){
                String rem = curr.substring(i);
                q.offer(rem);
            }
            if(i == curr.length()){
                break;
            }
            min_steps++;
        }
        System.out.println("min_steps = " + min_steps);
        return min_steps + 1;
    }


    /* 
    LC : 1253
    https://leetcode.com/problems/reconstruct-a-2-row-binary-matrix/
    */
    public List<List<Integer>> reconstructMatrix(int upper, int lower, int[] colsum) {
        List<List<Integer>> res = new ArrayList<>();
        
        int[][] matrix = new int[2][colsum.length];
        int upCount = 0, lowCount = 0;
        
        for(int i = 0; i < colsum.length; i++){
            if(colsum[i] == 2){
                matrix[0][i] = 1;
                matrix[1][i] = 1;
                upper--;
                lower--;
            }
        }
        
        for(int i = 0; i < colsum.length; i++){
            if(colsum[i] == 1){
                if(upper > 0){
                    matrix[0][i] = 1;    
                    upper--;
                }else if(lower > 0){
                    matrix[1][i] = 1;
                    lower--;
                }else{
                    return res;
                }
            }
        }
        if(upper != 0 || lower != 0){
            return res;
        }
        
        for(int i = 0; i < 2; i++){
            List<Integer> row = new ArrayList<>();
            for(int j = 0; j < colsum.length; j++){
                row.add(matrix[i][j]);
            }
            res.add(row);
        }

        return res;  
    }


    /* 
    LC : 809
    https://leetcode.com/discuss/interview-question/679321/google-onsite-determine-if-word-is-typo-because-of-stuck-key
    https://leetcode.com/problems/expressive-words/
    */
    public int expressiveWords(String s, String[] words) {
        int ans = 0;
        for(String word : words){
            if(isStretchy(s, word)){
                ans++;
            }
        }
        System.out.println("ans = " + ans);
        return ans;
    }

    public boolean isStretchy(String stretchy, String word){
        int wordPtr = 0;
        int strPtr = 0;
        int m = word.length();
        int n = stretchy.length();
        while (true){
            if(wordPtr == m && strPtr == n) return true;
            if(wordPtr == m || strPtr == n) return false;
            if(word.charAt(wordPtr) != stretchy.charAt(strPtr)) return false;
            int prevwordPtr = wordPtr;
            while(wordPtr < m-1 && word.charAt(wordPtr) == word.charAt(wordPtr + 1)){
                wordPtr++;
            }
            int wLen = wordPtr - prevwordPtr + 1;

            int prevstrPtr = strPtr;
            while(strPtr < n-1 && stretchy.charAt(strPtr) == stretchy.charAt(strPtr + 1)){
                strPtr++;
            }

            int sLen = strPtr - prevstrPtr + 1;
            if(sLen == wLen){
                wordPtr++;
                strPtr++;
                continue;
            }
            if(sLen < 3 || sLen < wLen){
                return false;
            }
        }
    }

    /*
    LC : 916
    https://leetcode.com/problems/word-subsets/
    */
    public List<String> wordSubsets(String[] A, String[] B) {
        HashMap<String, HashMap<Character,Integer>> mapA = new HashMap<>();
        for (String a : A){
            char[] arr =  a.toCharArray();
            HashMap<Character, Integer> freqMap = new HashMap<>();
            for (char ch : arr ){
                freqMap.put(ch, freqMap.getOrDefault(ch, 0) + 1);
            }
            mapA.put(a, freqMap);
        }

        HashMap<Character,Integer> mapB = new HashMap<>();
        for (String b : B){
            char[] arr =  b.toCharArray();
            int[] freq = new int[26];
            for (char ch : arr ){
                freq[ch - 'a']++;
            }
            for (int i = 0; i < 26; i++) {
                char curr = (char)(i + 'a');
                int maxFreq = Math.max(freq[i], mapB.getOrDefault(curr, 0));
                if(maxFreq > 0) {
                    mapB.put(curr, maxFreq);
                }
            }
        }

        List<String> res = new ArrayList<>();
        for (int i = 0; i < A.length; i++) {
            String str = A[i];
            HashMap<Character, Integer> freqA = mapA.get(str);
            boolean found = true;
            for (Map.Entry<Character, Integer> entry : mapB.entrySet()){
                Character ch = entry.getKey();
                int freq = entry.getValue();
                if(!freqA.containsKey(ch) || freqA.get(ch) < freq){
                    found = false;
                    break;
                }
            }
            if(found) {
                res.add(A[i]);
            }
        }
        return res;
    }


    /* 
    LC : 1170
    https://leetcode.com/problems/compare-strings-by-frequency-of-the-smallest-character/
    */
    public int[] numSmallerByFrequency(String[] queries, String[] words) {
        ArrayList<Integer> res = new ArrayList<Integer>();

        for(String q : queries){
            int qFreq = func(q);
            int ans = 0;
            for(String word : words){
                if(qFreq < func(word)){
                    ans++;
                }
            }
            res.add(ans);
        }
        return res.stream().mapToInt(i->i).toArray();
    }

    public int func(String s){
        char[] arr = s.toCharArray();
        Arrays.sort(arr);
        char smallest = arr[0];
        int i = 0;
        while(i < arr.length && smallest == arr[i]){
            i++;
        }
        return i + 1;
    }   

    /*
    LC : 1152
    https://leetcode.com/problems/analyze-user-website-visit-pattern/
    */
    public List<String> mostVisitedPattern(String[] username, int[] timestamp, String[] website) {
        HashMap<String, ArrayList<String[]>> userMap = new HashMap<>();
        int n = username.length;
        for (int i = 0; i < n; i++) {
            String user = username[i];
            String url = website[i];
            String time = String.valueOf(timestamp[i]);
            userMap.putIfAbsent(user, new ArrayList<>());
            userMap.get(user).add(new String[]{url, time});
        }
        Map<String, Integer> count = new HashMap<>();
        String res = "";
        for (String key : userMap.keySet()) {
            HashSet<String> sets = new HashSet<>();
            List<String[]> list = userMap.get(key);
            Collections.sort(list, (a, b) -> Integer.parseInt(a[1]) - Integer.parseInt(b[1]));

            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    for (int k = j + 1; k < list.size(); k++) {
                        String str = list.get(i)[0] + " " + list.get(j)[0] + " " + list.get(k)[0];
                        if (!sets.contains(str)) {
                            count.put(str, count.getOrDefault(str, 0) + 1);
                            sets.add(str);
                        }
                        if (res.equals("") || count.get(res) < count.get(str) || (count.get(str) == count.get(res) && res.compareTo(str) > 0)) {
                            res = str;
                        }
                    }
                }
            }
        }
        System.out.println("res = " + res);
        String[] arr = res.split(" ");
        return new ArrayList<>(Arrays.asList(arr));
    }

    /*
    LC : 354
    https://leetcode.com/problems/russian-doll-envelopes
    Longest Increasing subsequence(LIS)
    */
    public int maxEnvelopes(int[][] envelopes) {
        Arrays.sort(envelopes, (a,b) -> a[0] - b[0]);
        int n = envelopes.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        for (int i = 1; i < envelopes.length; i++) {
            for (int j = 0; j < i; j++) {
                if(envelopes[i][0] == envelopes[j][0] || envelopes[i][1] == envelopes[j][1])
                    continue;
                if(envelopes[i][1] > envelopes[j][1]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
        }
        int max = 0;
        for (int i = 0; i < dp.length; i++) {
            if(max < dp[i]){
                max = dp[i];
            }
        }
        return max;
    }

    /*
    LC : 334
    https://leetcode.com/problems/increasing-triplet-subsequence/
    */
    public boolean increasingTriplet(int[] nums) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        for(int n : nums){
            if(a >= n){
                a = n;
            }else if(b >= n){
                b = n;
            }else{
                return true;
            }
        }
        return false;
    }

    /*
    LC : 646
    https://leetcode.com/problems/maximum-length-of-pair-chain/
    Longest Increasing subsequence(LIS)
    */
    public int findLongestChain(int[][] pairs) {
        Arrays.sort(pairs, (a,b) -> a[0] - b[0]);
        return findLongestChainDP(pairs);
    }

    public int findLongestChainDP(int[][] nums) {
        int LIS[] = new int[nums.length];
        for(int i = 0; i < nums.length; i++){
            LIS[i] = 1;
        }
        for(int i = 1; i < nums.length; i++){
            for(int j = 0; j < i; j++){
                if(nums[j][1] < nums[i][0]){
                    LIS[i] = Math.max(LIS[i], LIS[j]+1);
                }
            }
        }
        int max = 0;
        for(int i = 0; i < LIS.length; i++){
            if(max < LIS[i]){
                max = LIS[i];
            }
        }
        return max;
    }

    /*
    LC : 740
    https://leetcode.com/problems/delete-and-earn/
    */
    public int deleteAndEarn(int[] nums) {
        Arrays.sort(nums);
        HashMap<Integer, Integer> map = new HashMap<>();
        Set<Integer> sets = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
            sets.add(nums[i]);
        }
        int[] temp = sets.stream().mapToInt(i -> i).toArray();
        int[] memo = new int[temp.length];
        Arrays.fill(memo, -1);
        return helperMaxMemo(temp, temp.length - 1, map, memo);
    }

    public int helperMaxMemo(int[] nums, int n, HashMap<Integer, Integer> map, int[] memo) {
        if (n < 0) {
            return 0;
        }
        if(n == 0){
            return nums[n] * map.getOrDefault(nums[n], 1);
        }
        if(memo[n] != -1){
            return memo[n];
        }
        int curr = nums[n];
        int prev = nums[n - 1];
        int max = 0;
        if (curr == prev + 1) {
            max = Math.max((nums[n] * map.getOrDefault(nums[n], 1)) + helperMaxMemo(nums, n - 2, map, memo), helperMaxMemo(nums, n - 1, map, memo));
        } else {
            max = (nums[n] * map.getOrDefault(nums[n], 1)) + Math.max(helperMaxMemo(nums, n - 1, map, memo), helperMaxMemo(nums, n - 2, map, memo));
        }
        return memo[n] = max;
    }

    public int helperMax(int[] nums, int n, HashMap<Integer, Integer> map) {
        if (n < 0) {
            return 0;
        }
        if(n == 0){
            return nums[n] * map.getOrDefault(nums[n], 1);
        }
        int curr = nums[n];
        int prev = nums[n - 1];

        if (curr == prev + 1) {
            return Math.max((nums[n] * map.getOrDefault(nums[n], 1)) + helperMax(nums, n - 2, map), helperMax(nums, n - 1, map));
        } else {
            return (nums[n] * map.getOrDefault(nums[n], 1)) + Math.max(helperMax(nums, n - 1, map), helperMax(nums, n - 2, map));
        }
    }

    /*
     LC : 361
     https://leetcode.com/problems/bomb-enemy/
     */
    class Location{
        int top;
        int bottom;
        int left;
        int right;
    }

    public int maxKilledEnemies(char[][] grid) {
        if(grid == null || grid.length == 0) return 0;
        int m = grid.length;
        int n = grid[0].length;
        Location[][] matrix = new Location[m][n];

        for(int i = 0; i < m; i++ ){
            for(int j = 0; j < n; j++){
                matrix[i][j] = new Location();
                if(grid[i][j] == 'W') continue;
                matrix[i][j].top = (i == 0 ? 0 : matrix[i-1][j].top) + (grid[i][j] == 'E' ? 1 : 0);
                matrix[i][j].left = (j == 0 ? 0 : matrix[i][j-1].left) + (grid[i][j] == 'E' ? 1 : 0);
            }
        }

        int maxCount = 0;
        for(int i = m - 1; i >= 0; i--){
            for(int j = n - 1; j >= 0; j--){
                if(grid[i][j] == 'W') continue;
                matrix[i][j].bottom = (i == m -1 ? 0 : matrix[i+1][j].bottom) + (grid[i][j] == 'E' ? 1 : 0);
                matrix[i][j].right = (j == n -1 ? 0 : matrix[i][j+1].right) + (grid[i][j] == 'E' ? 1 : 0);   

                if(grid[i][j] == '0'){
                    maxCount = Math.max(maxCount, matrix[i][j].top + matrix[i][j].bottom + matrix[i][j].left + matrix[i][j].right);
                }
            }
        }
        return maxCount;
    }

    /*
    LC : 311
    https://leetcode.com/problems/sparse-matrix-multiplication/
    */
    public int[][] multiply(int[][] A, int[][] B) {
        int rowsA = A.length;
        int colsA = A[0].length;
        int colsB = B[0].length;

        int[][] res = new int[rowsA][colsB];

        for (int i = 0; i < rowsA; i++) {
            for (int j = 0; j < colsA; j++) {
                if(A[i][j] != 0) {
                    for (int k = 0; k < colsB; k++) {
                        if (B[j][k] != 0) res[i][k] += A[i][j] * B[j][k];
                    }
                }
            }
        }
        return res;
    }

    /*
    LC : 838
    https://leetcode.com/problems/push-dominoes/
    */
    public String pushDominoes(String dominoes) {
        int right = -1;
        int left = -1;
        char[] out = dominoes.toCharArray();
        int n = dominoes.length();
        for (int i = 0; i <= n ; i++) {
            if(i == n || dominoes.charAt(i) == 'R'){
                if(right > left) {// R...R, ...R
                    while (right < i){
                        out[right++] = 'R';
                    }
                }
                right = i;
            }else if(dominoes.charAt(i) == 'L'){
                if(left > right || (right  == -1 && left == -1)){ // L...L, ...L
                    while (left < i){
                        out[++left] = 'L';
                    }
                }else{ // R...L
                    left = i;
                    int mid = right + (left - right) / 2;
                    if((left - right + 1) % 2 == 1){
                        out[mid] =  '.';
                    }
                    int low = right + 1, high = left - 1;
                    while (low  < high){
                        out[low] = 'R';
                        out[high] = 'L';
                        low++;
                        high--;
                    }
                }
            }
        }
        String res = new String(out);
        System.out.println("out = " + res);
        return res;
    }
}
