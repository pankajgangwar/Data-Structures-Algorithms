package com.pkumar7.leetcode;

import com.pkumar7.TreeNode;
import com.pkumar7.datastructures.ListNode;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class SeptemberW1 {

    public static void main(String[] args) {

        SeptemberW1 w1 = new SeptemberW1();
        String dayOfWeek = w1.dayOfTheWeek(31, 8, 2019);
        System.out.print(dayOfWeek);
    }

    /**
     * Input: calories = [1,2,3,4,5], k = 1, lower = 3, upper = 3
     * Output: 0
     * Explaination: calories[0], calories[1] < lower and calories[3], calories[4] > upper, total points = 0.
     *
     * Input: calories = [3,2], k = 2, lower = 0, upper = 1
     * Output: 1
     * Explaination: calories[0] + calories[1] > upper, total points = 1.
     *
     * [3,6,5,4,11,10]
     * 1
     * 0
     * 5
     *
     * op : 3
     *
     * */
    public static int dietPlanPerformance(int[] calories, int k, int lower, int upper) {
        int total_points = 0;
        int win = 0;
        for (int i = -1, j = 0; j < calories.length ; j++) {
            win += calories[j];

            if(j - i > k){
                win -= calories[++i];
            }
            if(j - i < k) continue;
            if (win > upper){
                total_points++;
            }else if(win < lower){
                total_points--;
            }
        }
        return total_points;

       /*
        int total_points = 0;

        for (int i = 0; i < calories.length;) {
            int temp = k;
            int calories_consumed = 0;

            while ( i < calories.length && temp-- > 0){
                calories_consumed += calories[i++];
            }
            if(calories_consumed > upper){
                total_points++;
            }else if(calories_consumed < lower){
                total_points--;
            }
        }
         System.out.println(total_points);
        return total_points;
        */
    }

    /**
     * Input: n = 5
     * Output: 12
     * Explanation: For example [1,2,5,4,3]
     * [1,2,3,4,5,6,7] is a valid permutation,
     * but [5,2,3,4,1] is not because the prime number 5 is at index 1.
     * */
    public int numPrimeArrangements(int n) {

        int total_primes = countTotalPrimes(n);
        BigInteger x_primes = countPermutations(total_primes);
        int non_primes = n - total_primes;
        BigInteger y_non_primes = countPermutations(non_primes);

        int res  = y_non_primes.multiply(x_primes).mod(BigInteger.valueOf(mod)).intValue();
        return res;
    }

    int mod = 10000007;
    public BigInteger countPermutations(int n){
        int res = 1;
        BigInteger factorial = BigInteger.ONE;

        for (int i = 2; i <= n; i++) {
            factorial = factorial.multiply(BigInteger.valueOf(i));
            res = res * i;
        }
        return factorial.mod(BigInteger.valueOf(mod));
    }

    public int countTotalPrimes(int n){
        boolean[] primes = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            primes[i] = true;
        }

        for (int p = 2; p*p <= n ; p++) {
            if(primes[p]){
                for (int i = p*p; i <= p ; i += p) {
                    primes[i] = false;
                }
            }
        }

        int total_primes = 0;
        for (int i = 2; i <= n; i++) {
            if(primes[i]){
                total_primes++;
            }
        }
        return total_primes;
    }

    /**
     * Input: s = "abcda", queries = [[3,3,0],[1,2,0],[0,3,1],[0,3,2],[0,4,1]]
     * Output: [true,false,false,true,true]
     * Explanation:
     * queries[0] : substring = "d", is palidrome.
     * queries[1] : substring = "bc", is not palidrome.
     * queries[2] : substring = "abcd", is not palidrome after replacing only 1 character.
     * */
    public List<Boolean> canMakePaliQueries(String s, int[][] queries) {
        int total_queries = queries.length;

        List<Boolean> result = new ArrayList<>();

        for (int i = 0; i < total_queries; i++) {
            int[] query = queries[i];

            int left = query[0];
            int right = query[1];
            int k = query[2];

            String toQuery = s.substring(left, right);

            boolean status = canFormPalindrome(toQuery, k);
            result.add(status);
        }
        return result;
    }

    private int NO_OF_CHARS = 256;
    private boolean canFormPalindrome(String str, int k) {

        // Create a count array and initialize all
        // values as 0
        int count[] = new int[NO_OF_CHARS];
        Arrays.fill(count, 0);

        // For each character in input strings,
        // increment count in the corresponding
        // count array
        for (int i = 0; i < str.length(); i++)
            count[(int)(str.charAt(i))]++;

        // Count odd occurring characters
        int odd = 0;
        for (int i = 0; i < NO_OF_CHARS; i++) {
            if ((count[i] & 1) == 1)
                odd++;

        }

        if(odd <= 1) return true;


        if(odd > 1){
            if(odd % 2 == 0){
                int allowed = odd / 2;
                if(k == allowed){
                    return true;
                }else{
                    return false;
                }
            }else{
                odd--;
                int allowed = odd / 2;
                if(k == allowed){
                    return true;
                }else{
                    return false;
                }
            }
        }

        // Return true if odd count is 0 or 1,
        return true;
    }
    /** 3. Longest Substring Without Repeating Characters
     * https://leetcode.com/problems/longest-substring-without-repeating-characters/
     *
     * Input: "abcabcbb"
     * Output: 3
     * Explanation: The answer is "abc", with the length of 3.
     *
     * Input: "pwwkew"
     * Output: 3
     * Explanation: The answer is "wke", with the length of 3.
     *              Note that the answer must be a substring, "pwke" is a subsequence and not a substring.
     *
     * "dvdf"
     *
     * **/
    public int lengthOfLongestSubstring(String s) {
        Map<Character, Integer> map = new HashMap<>();
        if(s.length() == 0) return 0;
        int max = 0;
        for (int i = 0, j = 0; i < s.length(); i++) {
            char curr_char = s.charAt(i);

            if(map.containsKey(curr_char)){
                j = Math.max(map.get(curr_char) + 1, j);
            }
            map.put(curr_char, i);
            max = Math.max(max, i - j + 1);
        }
        return max;
    }

    /**
     * Important
     *
     * 198. House Robber
     *
     * https://leetcode.com/problems/house-robber/
     * https://leetcode.com/problems/house-robber/discuss/156523/From-good-to-great.-How-to-approach-most-of-DP-problems.
     * **/
    public int rob(int[] nums) {
        //return robRec(nums, nums.length - 1);

        int[] memo = new int[nums.length + 1];

        Arrays.fill(memo, -1);

        return robMemo(nums, memo, nums.length);
    }

    public int robDP(int[] nums){
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

    public int robMemoIter(int[] nums){
        if(nums.length == 0 ) return 0;

        int memo[] = new int[nums.length + 1];
        Arrays.fill(memo, -1);
        memo[0] = 0;
        memo[1] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            int val = nums[i];
            memo[i + 1] = Math.max(memo[i], memo[i - 1] + val);
        }
        return memo[nums.length];
    }

    public int robMemo(int[] nums, int[] memo, int i){
        if(i < 0){
            return 0;
        }
        if(memo[i] != -1){
            return memo[i];
        }
        memo[i] = Math.max(robRec(nums, i - 2 ) + nums[i], robRec(nums, i - 1));
        return memo[i];
    }

    public int robRec(int[] nums, int i){
        if(i < 0){
            return 0;
        }
        return Math.max(robRec(nums, i - 2 ) + nums[i], robRec(nums, i - 1));
    }

    /**
     * https://leetcode.com/problems/partition-equal-subset-sum/
     *
     * 416. Partition Equal Subset Sum
     *  NP-Hard
     * **/
    public boolean canPartition(int[] nums) {
        /*int sum = 0;

        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }*/

        int sum = Arrays.stream(nums).sum();

        if(sum % 2 == 0){
            boolean[] memo = new boolean[nums.length + 1];
            return isSubSetMemo(nums, nums.length, sum/2, memo);
            //return isSubSetRec(nums, nums.length, sum/2);
        }else{
            return false;
        }
    }

    public boolean subsetDP(int [] arr, int n, int sum) {
        boolean [][] dp = new boolean [sum+1][n+1];

        for(int i = 0; i <= n; i++)
            dp[0][i] = true;//If Sum is zero, answer is true

        for(int i = 0; i <= sum; i++)
            dp[i][0] = false;//If sum is not zero and set is empty then return false

        for(int i = 1; i <= sum; i++) {
            for(int j = 1; j <= n; j++) {
                dp[i][j] = dp[i][j-1];
                if(i >= arr[j-1]) {
                    dp[i][j] = dp[i][j] || dp[i - arr[j-1]][j-1];
                }
            }
        }

        return dp[sum][n];
    }

    public boolean isSubSetMemoIterative(int[] nums, int n, int sum){
        boolean[] memo = new boolean[n + 1];
        Arrays.fill(memo, false);
        memo[0] = true;

        for (int i = 1; i < nums.length ; i++) {
            memo[i + 1] = memo[i];
        }
        return false;
    }

    public boolean isSubSetMemo(int[] nums, int n, int sum, boolean [] memo){
        if(sum == 0){
            return true;
        }

        if(n == 0 && sum != 0){
            return false;
        }

        if(nums[n-1] > sum) {
            return isSubSetMemo(nums, n, sum, memo);
        }

        if(memo[n-1]) {
            return true;
        }

        memo[n-1] = isSubSetMemo(nums, n-1, sum, memo) ||
                isSubSetMemo(nums, n-1, sum - nums[n-1], memo);

        return memo[n-1];
    }

    public boolean isSubSetRec(int []nums, int n, int sum){
        if(sum == 0){
            return true;
        }

        if(n == 0 && sum != 0){
            return false;
        }

        if(nums[n-1] > sum) {
            return isSubSetRec(nums, n - 1, sum);
        }

        return isSubSetRec(nums, n -1, sum - nums[n-1]) ||
                isSubSetRec(nums, n -1, sum);
    }



    /**
     * https://leetcode.com/problems/convert-bst-to-greater-tree/
     *
     * Input: The root of a Binary Search Tree like this:
     *               5
     *             /   \
     *            2     13
     *
     * Output: The root of a Greater Tree like this:
     *              18
     *             /   \
     *           20     13
     *
     * **/
    int sum = 0;
    public TreeNode convertBST(TreeNode root) {
        if(root != null){
            convertBST(root.right);
            sum += root.val;
            root.val = sum;
            convertBST(root.left);
        }
        return root;
    }

    /** 322. Coin Change
     * https://leetcode.com/problems/coin-change/
     *
     * Input: coins = [1, 2, 5], amount = 11
     * Output: 3
     *
     * */
    public int coinChange(int[] coins, int amount) {
        int[] memo = new int [amount + 1];
        Arrays.fill(memo, Integer.MAX_VALUE);
        int res = coinChangeMemo(coins, amount, coins.length, memo);
        //int res = coinChangeDP(coins, amount, coins.length);
        if(res == Integer.MAX_VALUE) {
            return -1;
        }
        return res;
    }

    public int coinChangeDP(int[] coins, int amount, int n) {
        int table[] = new int[ amount + 1];

        table[0] = 0;

        for (int i = 1; i <= amount; i++) {
            table[i] = Integer.MAX_VALUE;
        }

        for (int i = 1; i <= amount ; i++) {
            for (int j = 0; j < n; j++) {
                if(coins[j] <= i){
                    int sub_res = table[i - coins[j]];
                    if(sub_res != Integer.MAX_VALUE && sub_res + 1 < table[i]){
                        table[i] = sub_res + 1;
                    }
                }
            }
        }
        return table[amount];
    }

    public int coinChangeMemo(int[] coins, int amount, int n, int[] memo) {
        if(amount == 0){
            return 0;
        }
        if(memo[amount] < Integer.MAX_VALUE) {
            return memo[amount];
        }

        for (int i = 0; i < n; i++) {
            if(coins[i] <= amount) {
                int sub_res = coinChangeMemo(coins, amount - coins[i], n, memo);
                if(sub_res != Integer.MAX_VALUE && sub_res + 1 < memo[amount]){
                    memo[amount] = sub_res  + 1;
                }
            }
        }
        return memo[amount];
    }

    public int coinChangeRec(int[] coins, int amount, int n) {
        if(amount == 0){
            return 0;
        }

        int res = Integer.MAX_VALUE;

        for (int i = 0; i < n; i++) {
            if(coins[i] <= amount) {
                int res_sub = coinChangeRec(coins, amount - coins[i], n);

                if (res_sub != Integer.MAX_VALUE && res_sub + 1 < res) {
                    res = res_sub + 1;
                }
            }
        }

        return res;
    }

    /**
     * https://leetcode.com/problems/coin-change-2/
     * https://www.geeksforgeeks.org/understanding-the-coin-change-problem-with-dynamic-programming/
     *
     * Input: amount = 5, coins = [1, 2, 5]
     * Output: 4
     * Explanation: there are four ways to make up the amount:
     * 5=5
     * 5=2+2+1
     * 5=2+1+1+1
     * 5=1+1+1+1+1
     *
     * */
    public int change(int amount, int[] coins) {
        //return coinChangeIIRec(amount, coins, coins.length);
        /*int[] memo = new int[amount + 1];
        Arrays.fill(memo, -1);
        memo[0] = 1;
        int res = coinChangeIIMemo(amount, coins, coins.length, memo);
        return res;*/

        return coinChangeIIDP(amount, coins, coins.length);
    }

    public int coinChangeIIDP(int amount, int[] coins, int n) {
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, 0);
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            for (int j = coins[i]; j <= amount; j++) {
                if(coins[i] <= j) {
                    dp[j] = dp[j] + dp[j - coins[i]];
                }
            }
        }
        return dp[n];
    }

    public int coinChangeIIMemo(int amount, int[] coins, int n, int[] memo) {
        if(memo[amount] == 0){
            return memo[amount];
        }
        if(amount < 0){
            return 0;
        }
        if(amount >= 1 && n <= 0){
            return 0;
        }

        if(memo[amount] != -1){
            return memo[amount];
        }

        memo[amount] = coinChangeIIRec(amount - coins[n - 1], coins, n ) +
                        coinChangeIIRec(amount , coins,n -1);

        return memo[amount];
    }

    public int coinChangeIIRec(int amount, int[] coins, int n) {
        if(amount == 0){
            return 1;
        }
        if(amount < 0){
            return 0;
        }
        if(amount >= 1 && n <= 0){
            return 0;
        }

        return coinChangeIIRec(amount - coins[n - 1], coins, n ) +
                coinChangeIIRec(amount , coins,n -1);
    }

    /**
     * https://leetcode.com/problems/3sum/
     * https://en.wikipedia.org/wiki/3SUM
     *
     * **/
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        Set<List<Integer>> result = new HashSet<>();
        int n = nums.length;
        for (int i = 0; i < n - 2; i++) {
            int start = i + 1;
            int end = n - 1;

            while (start < end){
                int a = nums[i];
                int b = nums[start];
                int c = nums[end];

                int sum = a + b + c;
                if(sum == 0){
                    List<Integer> pairs = new ArrayList<>();
                    pairs.add(a);pairs.add(b);pairs.add(c);

                    result.add(pairs);

                    start++;
                    end--;
                }else if(sum > 0) {
                    end--;
                }else {
                    start++;
                }
            }
        }

        List<List<Integer>> final_res = new ArrayList<>();
        final_res.addAll(result);
        return final_res;
    }

    /**
     * https://leetcode.com/problems/count-primes/
     * **/
    public int countPrimes(int n) {
        boolean prime[] = new boolean[n + 1];
        for (int i = 0; i < n; i++) {
            prime[i] = true;
        }
        for (int p = 2; p*p <= n; p++) {
            if(prime[p]){
                for (int i = p*p ; i <= n ; i+= p) {
                    prime[i] = false;
                }
            }
        }

        int total_primes = 0;
        for (int i = 2; i <= n; i++) {
            if(prime[i]){
                System.out.println("Prime found : " + i);
                total_primes++;
            }
        }
        return total_primes;
    }

    /**
     * https://www.geeksforgeeks.org/convert-ternary-expression-binary-tree/
     * https://leetcode.com/discuss/interview-question/124826/ternary-expression-to-binary-tree
     *
     * Input: "a?b:c"
     * Output:
     *       a
     *      /  \
     *     b    c
     *
     * Input: "a?b?c:d:e"
     * Output:
     *          a
     *         / \
     *        b   e
     *       / \
     *      c   d
     *
     * **/
    public TreeNode convert(char[] expr) {
        if (expr.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(expr[0]);
        Stack<TreeNode> stack = new Stack<>();
        for (int i = 1; i < expr.length; i += 2) {
            TreeNode node = new TreeNode(expr[i + 1]);
            if (expr[i] == '?') {
                stack.peek().left = node;
            }
            if (expr[i] == ':') {
                stack.pop();
                while (stack.peek().right != null) {
                    stack.pop();
                }
                stack.peek().right = node;
            }
            stack.push(node);
        }
        return root;
    }

    public class Node {

        public char val;
        public Node left, right;

        public Node(char data) {
            val = data;
            left = null;
            right = null;
        }
    }

    public Node convertExp(String str, int i) {

        if(i > str.length()){
            return null;
        }
        Node born = new Node(str.charAt(i));
        i++;
        if(i < str.length() && str.charAt(i) == '?'){
            born.left = convertExp(str, i + 1);
        }
        if(i < str.length() && str.charAt(i) == ':'){
            born.right = convertExp(str, i + 1);
        }
        return born;
    }

    /**
     * https://leetcode.com/problems/ones-and-zeroes/
     *
     * 474. Ones and Zeroes
     *
     * **/
    public int findMaxForm(String[] strs, int m, int n) {
        //return findMaxFormRec(strs, m, n, 0);
        //int[] memo = new int[strs.length  +1];
        //Arrays.fill(memo, -1);
        //return findMaxFormMemo(strs, m, n, 0, memo);
        return findMaxFormDP(strs, m, n);
    }

    public int findMaxFormDP(String[] strs, int m, int n) {
        int[][][] dp = new int[strs.length + 1][m + 1][n + 1];
        int l = strs.length;

        for (int i = 0; i < l + 1; i++) {
            int[] nums = new int[]{0, 0};
            if(i > 0 ){
                nums = count(strs[i - 1]);
            }
            for (int j = 0; j < m + 1; j++) {
                for (int k = 0; k < n + 1; k++) {
                    if(i == 0){
                        dp[i][j][k] = 0;
                    }else if(j >= nums[0] && k >= nums[1]){
                        dp[i][j][k] = Math.max(dp[i-1][j][k], dp[i-1][j - nums[0]][k - nums[1]] + 1 );
                    }else{
                        dp[i][j][k] = dp[i-1][j][k];
                    }

                }
            }
        }
        return dp[l][m][n];
    }

    public int[] count(String str){
        int[] freq = new int[2];
        Arrays.fill(freq, 0);

        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '0')
                freq[0]++;
            else
                freq[1]++;

        }
        return freq;
    }

    public int findMaxFormMemo(String[] strs, int m, int n, int idx, int[] memo) {
        if(idx == strs.length || (m + n == 0 )){
            return 0;
        }

        if(memo[idx] != -1){
            return memo[idx];
        }

        int totalStringWithCurr = 0;
        String curr = strs[idx];
        int zero = countZeros(curr);
        int ones = curr.length() - zero;

        if(m >= zero && n >= ones){
            totalStringWithCurr = 1 + findMaxFormRec(strs, m - zero, n - ones, idx + 1);
        }

        int totalStringWithoutCur = findMaxFormRec(strs, m, n, idx + 1);

        int max =  Math.max(totalStringWithCurr, totalStringWithoutCur);
        memo[idx] = max;
        return memo[idx];
    }

    public int findMaxFormRec(String[] strs, int m, int n, int idx) {
        if(idx == strs.length || (m + n == 0 )){
            return 0;
        }

        int totalStringWithCurr = 0;
        String curr = strs[idx];
        int zero = countZeros(curr);
        int ones = curr.length() - zero;

        if(m >= zero && n >= ones){
            totalStringWithCurr = 1 + findMaxFormRec(strs, m - zero, n - ones, idx + 1);
        }

        int totalStringWithoutCur = findMaxFormRec(strs, m, n, idx + 1);

        return Math.max(totalStringWithCurr, totalStringWithoutCur);
    }

    public int countZeros(String str){
        int zeros = 0;
        for (int i = 0; i < str.length(); i++) {
            if(str.charAt(i) == '0')
                zeros++;
        }
        return zeros;
    }

    public int findMaxFormNaive(String[] strs, int m, int n) {
        Arrays.sort(strs, (s1, s2) -> s1.length() - s2.length());

        printSortedString(strs);

        int total_string = 0;
        for (int i = 0; i < strs.length; i++) {
            String curr = strs[i];
            int len = curr.length();
            int j = 0;
            if(m == 0 && n == 0){ //Can't add any more strings here
                break;
            }
            for (; j < len; j++) {
                if(curr.charAt(j) == '1' && n > 0){
                    n--;
                }else if(curr.charAt(j) == '0' && m > 0){
                    m--;
                }
            }
            if(j == len){ //Entire string is added
                total_string++;
            }
        }
        System.out.println(total_string);
        return total_string;
    }


    private void printSortedString(String[] strs) {
        for (int i = 0; i < strs.length; i++) {
            System.out.print(strs[i] + " \t");
        }
    }

    /** 190. Reverse Bits
     * https://leetcode.com/problems/reverse-bits/
     * **/
    public int reverseBits(int n) {
        String binaryStr = Integer.toBinaryString(n);
        System.out.println( "binaryStr " + binaryStr);
        StringBuilder builder = new StringBuilder(binaryStr);
        builder.reverse();
        System.out.println("Reverse " + builder.toString());
        return Integer.parseInt(builder.toString());
    }

    /** 172. Factorial Trailing Zeroes
     * https://leetcode.com/problems/factorial-trailing-zeroes/
     * **/
    public int trailingZeroes(int n) {
      /*  if(n == 0){
            return 0;
        }
        int fac = factorial(n);
        System.out.println(fac);
        return  getTrailingZero(n);*/
        return n == 0 ? 0 : n / 5 + trailingZeroes(n / 5);
    }

    public int getTrailingZero(int n){
        String ele = String.valueOf(n);
        int start = ele.indexOf('0');
        if(start < 0){
            return 0;
        }
        int end = ele.length();
        int count = 0;
        while (start < end && ele.charAt(start) == '0'){
            start++;
            count++;
        }
        return count;
    }

    public int factorial(int n){
        if( n == 1){
            return 1;
        }
        if(n == 0){
            return 0;
        }
        return n * factorial(n -1);
    }

    /** 189. Rotate Array
     * https://leetcode.com/problems/rotate-array/
     * **/
    public void rotate(int[] nums, int k) {
        if(k > nums.length){
            k = k - nums.length;
        }
        int len = nums.length;
        int rotation_point = len - k;

        int end = len - 1;
        int start = rotation_point;
        while(start <= end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }

        end = rotation_point - 1;
        start = 0;

        while(start <= end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }

        start = 0;
        end = len - 1;

        while(start <= end){
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;
            start++;
            end--;
        }
    }

    /** 326. Power of Three
     * https://leetcode.com/problems/power-of-three/
     * **/
    public boolean isPowerOfThree(int n) {
        if(n == 1){
            return true;
        }
        //System.out.println("n " + n + " mod "+ n % 3);

        if(n == 0 || n % 3 != 0){
            return false;
        }
        return isPowerOfThree(n / 3);
    }

    /**
     * https://leetcode.com/problems/power-of-four/
     * **/
    public boolean isPowerOfFour(int num) {
        if(num == 1) return true;

        if(num == 0 || num % 4 != 0) return false;

        return isPowerOfFour(num / 4);
    }

    /** 61. Rotate List
     * https://leetcode.com/problems/rotate-list/
     *
     * Input: 1->2->3->4->5->NULL, k = 2
     * Output: 4->5->1->2->3->NULL
     *
     * */

    public ListNode rotateRight(ListNode head, int k) {
        if(head == null || head.next == null ) return head;
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode slow = dummy, fast = dummy;

        int i;
        for(i = 0; fast.next != null; i++ ){
            fast = fast.next;
        }

        int j;
        for(j = i - k % i; j > 0; j--){
            slow = slow.next;
        }

        fast.next = dummy.next;
        dummy.next = slow.next;
        slow.next = null;

        return dummy.next;
    }

    /* 
       279. Perfect Squares
       https://leetcode.com/problems/perfect-squares/
    */
    public int numSquares(int n) {
       // return numSquaresRec(n);
        /*int[] memo = new int[n + 1];
        Arrays.fill(memo, -1);
        return numSquaresMemo(n , memo);*/

        return numSquaresDP(n);
        //return numSquaresBfs(n);
    }

   public int numSquaresBfs(int n){
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(n);

        int res = 0;
        while(!queue.isEmpty()) {
            int size = queue.size();
            while(size-- > 0 ) {
                int curr = queue.poll();
                for(int i = 1; i * i <= n; i++ ) {
                    int rem = curr - (i * i);
                    if(rem < 0) continue;

                    if(rem == 0) return res + 1;

                    queue.offer(rem);
                }
            }
            res++;
        }
        return res;
    }
    
    public int numSquaresDP(int n){
        if(n <= 3){
            return n;
        }
        
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 3;
        
        for (int i = 4; i <= n ; i++) {
            int min = Integer.MAX_VALUE;
            for (int x = 1; x * x <= i; x++) {
                int temp = x * x;
                min = Math.min( min, 1 + dp[i - temp] );
            }
            dp[i] = min;
        }
        return dp[n];
    }
    
    public int numSquaresMemo(int n, int[] memo){
        if(n <= 3){
            return n;
        }

        if(memo[n] != -1){
            return memo[n];
        }

        int res = n;
        for (int i = 1; i * i <= n; i++) {
            int temp =  i * i;
            res = Math.min(res, 1 + numSquaresMemo(n - temp, memo));
        }
        memo[n] = res;
        return memo[n];
    }


    public int numSquaresRec(int n){
        if(n <= 3){
            return n;
        }
        int res = n;
        for (int i = 1; i <= n; i++) {
            int temp =  i * i;
            if(temp > n){
                break;
            }
            res = Math.min(res, 1 + numSquaresRec(n - temp));
        }
        return res;
    }

    /**
     * https://leetcode.com/contest/weekly-contest-153/problems/day-of-the-week/
     * **/
    public String dayOfTheWeek(int day, int month, int year) {
        int count = 0;
        int tempDay = day;
        while (tempDay != 0) {
            tempDay = tempDay / 10;
            ++count;
        }

        String datStr = new String();
        if(count < 2){
            datStr = "0"+ day;
        }else{
            datStr = "" + day;
        }


        int tempMonth = month;
        int monthCnt = 0;
        while (tempMonth!= 0) {
            tempMonth = tempMonth / 10;
            ++monthCnt;
        }

        String monthStr = new String();
        if(monthCnt < 2){
            monthStr = "0"+ month;
        }else{
            monthStr = "" + month;
        }

        System.out.print(monthStr + " " + datStr);
        String input_date = datStr+"/" + monthStr +"/" + String.valueOf(year);

        SimpleDateFormat format1=new SimpleDateFormat("dd/MM/yyyy");
        Date dt1= null;
        try {
            dt1 = format1.parse(input_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat format2=new SimpleDateFormat("EEEE");
        String finalDay=format2.format(dt1);
        return finalDay;
    }

    /** 5182. Maximum Subarray Sum with One Deletion
     * https://leetcode.com/contest/weekly-contest-153/problems/maximum-subarray-sum-with-one-deletion/
     * */
    public int maximumSum(int[] arr) {
        // Maximum sum subarrays in forward and
        // backward directions
        int n = arr.length;

        int fw[] = new int[n];
        int bw[] = new int[n];

        // Initialize current max and max so far.
        int cur_max = arr[0], max_so_far = arr[0];

        // calculating maximum sum subarrays in forward
        // direction
        fw[0] = arr[0];

        for (int i = 1; i < n; i++) {

            cur_max = Math.max(arr[i], cur_max + arr[i]);
            max_so_far = Math.max(max_so_far, cur_max);

            // storing current maximum till ith, in
            // forward array
            fw[i] = cur_max;
        }

        // calculating maximum sum subarrays in backward
        // direction
        cur_max = max_so_far = bw[n - 1] = arr[n - 1];

        for (int i = n - 2; i >= 0; i--) {

            cur_max = Math.max(arr[i], cur_max + arr[i]);
            max_so_far = Math.max(max_so_far, cur_max);

            // storing current maximum from ith, in
            // backward array
            bw[i] = cur_max;
        }

        /* Initializing final maxUniValLength by max_so_far so that,
        case when no element is removed to get max sum
        subarray is also handled */
        int fans = max_so_far;

        // choosing maximum ignoring ith element
        for (int i = 1; i < n - 1; i++)
            fans = Math.max(fans, fw[i - 1] + bw[i + 1]);

        return fans;
    }

    /**
     * https://leetcode.com/problems/distance-between-bus-stops/
     *
     * Input: distance = [1,2,3,4], start = 0, destination = 1
     * Output: 1
     * Explanation: Distance between 0 and 1 is 1 or 9, minimum is 1.
     *
     * */
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {

        int clock_wise_dist = 0;
        int n  = distance.length;
        int k = start;
        while(k != destination){
            clock_wise_dist += distance[k];
            k++;
            k = k % n;
        }

        System.out.println(clock_wise_dist);
        int total_distance = 0;
        for (int i = 0; i < n; i++) {
            total_distance+= distance[i];
        }

        System.out.println(total_distance);

        int min = Math.min(total_distance - clock_wise_dist, clock_wise_dist);
        return min;
    }

}
