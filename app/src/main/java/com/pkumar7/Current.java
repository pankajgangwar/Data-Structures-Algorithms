package com.pkumar7;

import java.math.BigInteger;
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
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

import sun.reflect.generics.tree.Tree;


/**
 * Created by Pankaj Kumar on 14/August/2020
 */
class Current {
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

    //https://leetcode.com/problems/odd-even-jump/
    //https://leetcode.com/problems/remove-boxes/

    public static void main(String[] args) {
        Current current = new Current();
    }

    public int numTilings(int n) {
        return 0;
    }

    /* 923. 3Sum With Multiplicity
    * https://leetcode.com/problems/3sum-with-multiplicity/
    * */
    public int threeSumMulti(int[] arr, int target) {
        int mod = (int)1e9 + 7;
        int n = arr.length;
        int res = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            res = (res + map.getOrDefault(target - arr[i], 0)) % mod;
            for (int j = 0; j < i; j++) {
                int temp = arr[i] + arr[j];
                map.put(temp, map.getOrDefault(temp, 0) + 1);
            }
        }
        return res % mod;
    }

    /* 902. Numbers At Most N Given Digit Set
    * https://leetcode.com/problems/numbers-at-most-n-given-digit-set/
    * */
    public int atMostNGivenDigitSet(String[] digits, int n) {
        String s = String.valueOf(n);
        int k = s.length();
        int[] dp = new int[k + 1];
        dp[k] = 1;

        int len = digits.length;

        for(int i = k - 1; i >= 0; --i){
            int si = s.charAt(i) - '0';
            for(String d : digits){
                if(Integer.parseInt(d) < si){
                    dp[i] += Math.pow(len, k - i - 1);
                }else if(Integer.parseInt(d) == si){
                    dp[i] += dp[i + 1];
                }
            }
        }

        for(int i = 1; i < k; i++){
            dp[0] += Math.pow(len, i);
        }
        return dp[0];
    }

    /*
    * https://leetcode.com/problems/minimum-health-to-beat-game/
    * */
    public long minimumHealth(int[] damage, int armor) {
        long sum = 0L;
        for (int i = 0; i < damage.length ; i++) {
            sum += damage[i];
        }
        Arrays.sort(damage);
        int index = Arrays.binarySearch(damage, armor);
        if(index < 0) index = ~index;

        if(index == damage.length) index = damage.length - 1;

        if(armor < damage[index]){
            return sum + 1 - armor;
        }
        return sum - (damage[index]) + 1;
    }

    static long getcount(int[] arr) {
        int ans = arr.length;
        int count = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i - 1] - arr[i] == 1)
                count++;
            else
                count = 0;
            ans = ans + count;
        }
        return ans;
    }

    // 1,3,1,3
    // 3,1,2
    public int minimumOperations(int[] nums) {
        int n = nums.length;
        HashMap<Integer, Integer> map1 = new HashMap<>();
        HashMap<Integer, Integer> map2 = new HashMap<>();

        for (int i = 0; i < nums.length; i+=2) {
            map1.put(nums[i], map1.getOrDefault(nums[i], 0) + 1);
        }
        for (int i = 1; i < nums.length; i+=2) {
            map2.put(nums[i], map2.getOrDefault(nums[i], 0) + 1);
        }
        int count1 = 0;
        int count2 = 0;
        List<int[]> list1 = new ArrayList<>();
        for(Map.Entry<Integer, Integer> e : map1.entrySet()){
            count1 += e.getValue();
        }
        List<int[]> list2 = new ArrayList<>();
        for(Map.Entry<Integer, Integer> e : map2.entrySet()){
            count2 += e.getValue();
        }
        for(int[] a : list1 ){
            for(int[]b : list2){
                if(a[0] != b[0]){
                  //  return count1 - a[1] + count2 - b[1];
                }
            }
        }
        return Math.min(count1, count2);
    }


    public long maxTaxiEarnings(int n, int[][] rides) {
        Arrays.sort(rides, (a, b) -> a[0] == b[0] ? -a[1] + b[1] : a[0] - b[0]);
        int[] curr = rides[0];
        long res = 0;
        long max = curr[1] - curr[0] + curr[2];
        for (int[] r : rides) {
            if (r[0] < curr[1]) {
                long xx = r[1] - r[0] + r[2];
                max = Math.max(max, xx);
                curr[1] = Math.max(curr[1], r[1]);
            } else {
                //System.out.println(Arrays.toString(curr) + " " + max);
                res += max;
                long xx = r[1] - r[0] + r[2];
                max = xx;
                curr = r;
            }
        }
        return res + max;
    }

    /*
     * https://leetcode.com/problems/smallest-missing-genetic-value-in-each-subtree/
     * */
    /*
     * https://leetcode.com/problems/longest-common-subpath/
     * */
    long BASE = 100007L, MOD = 1000000007L;
    public int longestCommonSubpath(int n, int[][] paths) {
        int ans = 0;
        int high = 1, low = 1;
        for (int i = 0; i < paths.length; i++) {
            high = Math.min(ans, paths[i].length);
        }

        long[] pow = new long[high + 1];

        pow[0] = 1;
        for (int i = 1; i <= high; ++i) {
            pow[i] = pow[i - 1] * BASE % MOD;
        }

        while (low <= high) {
            int mid = (low + high) >> 1;
            if (pathExistsWithCurrLen(mid, paths)) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    private boolean pathExistsWithCurrLen(int mid, int[][] paths) {
        return true;
    }

    public int rollingHashSol(int[] a, int[] b) {
        int min = Math.min(a.length, b.length);
        int max = Math.max(a.length, b.length);
        long[] pow = new long[max + 1];
        long[] hash_a = new long[a.length + 1];
        long[] hash_b = new long[b.length + 1];

        pow[0] = 1;
        for (int i = 1; i <= max; ++i) {
            pow[i] = pow[i - 1] * BASE % MOD;
        }

        for (int i = 1; i <= a.length; i++) {
            hash_a[i] = (hash_a[i - 1] * BASE + a[i - 1]) % MOD;
        }

        for (int i = 1; i <= b.length; i++) {
            hash_b[i] = (hash_b[i - 1] * BASE + b[i - 1]) % MOD;
        }

        int low = 1, high = min;
        int ans = 0;
        while (low <= high) {
            int mid = (low + high) / 2;
            HashSet<Long> seen = new HashSet<>();
            for (int i = 1; i + mid - 1 <= a.length; i++) {
                long hashA = getHash(i - 1, i + mid - 1, hash_a, pow);
                seen.add(hashA);
            }
            boolean found = false;
            for (int i = 1; i + mid - 1 <= b.length; i++) {
                long hashB = getHash(i - 1, i + mid - 1, hash_b, pow);
                if (seen.contains(hashB)) {
                    found = true;
                    break;
                }
            }
            if (found) {
                ans = mid;
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return ans;
    }

    public long getHash(int l, int r, long[] hash, long[] pow) {
        return (hash[r] - hash[l] * pow[r - l] % MOD + MOD) % MOD;
    }

    public int dpSol(int n, int[][] paths) {
        int p = paths.length;
        int max = Integer.MAX_VALUE;
        for (int i = 0; i < p - 1; i++) {
            int[] a = paths[i];
            int[] b = paths[i + 1];
            int res = dpFindMaxCommon(a, b);
            max = Math.min(max, res);
        }
        return max == Integer.MAX_VALUE ? 0 : max;
    }

    public int dpFindMaxCommon(int[] nums1, int[] nums2) {
        int n = nums1.length;
        int m = nums2.length;
        int[][] dp = new int[n + 1][m + 1];

        //dp[i][j] = max lcs that ends with i and j
        int length = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                if (nums1[i - 1] == nums2[j - 1]) {
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                }
                length = Math.max(length, dp[i][j]);
            }
        }
        return length;
    }

    /*
    * 1703. Minimum Adjacent Swaps for K Consecutive Ones
    * https://leetcode.com/problems/minimum-adjacent-swaps-for-k-consecutive-ones/
    * */
    public int minMoves(int[] nums, int k) {
        if(k == 1) return 0;
        List<Integer> ones = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if(nums[i] == 1){
                ones.add(i);
            }
        }
        int totalOnes = ones.size();
        List<Integer> pref = new ArrayList<>(totalOnes);
        pref.add(ones.get(0));

        for (int i = 1; i < totalOnes; i++) {
            pref.add(pref.get(i -1) + ones.get(i));
        }

        int ans = Integer.MAX_VALUE;
        for (int mid = (k - 1) / 2; mid < totalOnes - k/ 2; mid++) {
            int radius = (k - 1) / 2;
            int right = (k%2 == 0) ? pref.get(mid + radius + 1) - pref.get(mid) - ones.get(mid) :
                    pref.get(mid + radius) - pref.get(mid);
            int left = (mid == 0) ? 0 : pref.get(mid - 1) - (mid- radius == 0 ? 0 : pref.get(mid - radius - 1));
            int save = (radius + 1) * radius + (k%2 == 0 ? radius + 1 : 0);
            ans = Math.min(ans, right - left - save);
        }
        return ans;
    }

    public int minOperations(int[] nums) {
        int n = nums.length;
        int op = n;

        HashSet<Integer> sets = new HashSet<>();
        for (int i = 0; i < n; i++) {
            sets.add(nums[i]);
        }
        List<Integer> list = new ArrayList<>(sets);
        Collections.sort(list);

        int m = list.size();
        int j = 0;
        for (int i = 0; i < m; i++) {
            while (j < m && list.get(j) < list.get(i) + n) ++j;
            op = Math.min(op, n - (j - i));
        }
        return op;
    }


    public int minDeletion(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length -1 ; i++) {
            if(nums[i] == nums[i + 1] && (i - ans) % 2 == 0){
                ans += 1;
            }
        }
        if((nums.length - ans) % 2 == 1){
            ans += 1;
        }
        return ans;
    }

    public int subarraysWithMoreZerosThanOnes(int[] nums) {
        int mod = (int)1e9 + 7;
        return 0;
    }

    public List<List<Integer>> findWinners(int[][] matches) {
        HashMap<Integer, Integer> indegree = new HashMap<>();
        for(int[] match : matches){
            int winner = match[0];
            int loser = match[1];
            indegree.put(loser, indegree.getOrDefault(loser, 0) + 1);
            indegree.put(winner, indegree.getOrDefault(winner, 0));
        }

        List<List<Integer>> res = new ArrayList<>();
        List<Integer> a = new ArrayList<>();
        List<Integer> b = new ArrayList<>();
        for(Map.Entry<Integer, Integer> e : indegree.entrySet()){
            int player = e.getKey();
            int timesLoser = e.getValue();
            if(timesLoser == 0){
                a.add(player);
            }else if(timesLoser == 1){
                b.add(player);
            }
        }
        Collections.sort(a);
        Collections.sort(b);
        res.add(a);
        res.add(b);
        return res;
    }

    public int maximumTop(int[] nums, int k) {
        int n = nums.length;
        int max = Integer.MIN_VALUE;
        if(n == 1 && k % 2 == 1) return -1;
        for (int i = 0; i < Math.min(n, k - 1); i++){
            max = Math.max(max, nums[i]);
        }
        if(k < n){
            max = Math.max(max, nums[k]);
        }
        return max;
    }

    public List<Integer> replaceNonCoprimes(int[] nums) {
        int n = nums.length;
        LinkedList<Integer> list = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            int a = nums[i];
            while (true){
                int last = list.isEmpty() ? 1 : list.peekLast();
                int x = __gcd(last,a);
                if(x == 1) break;
                a *= list.pollLast() / x;
            }
            list.add(a);
        }
        return list;
    }


    static int __gcd(int a, int b) {
        return b > 0 ? __gcd(b, a % b) : a;
    }

    public long minimumTime(int[] time, int totalTrips) {
        long low = 1;
        long high = (long)1e14;
        long minTime = 1;
        while (low <= high){
            long mid = (low + high) / 2;
            if(canCompleteTrips(time, totalTrips, mid)){
                minTime = mid;
                high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return minTime;
    }

    public boolean canCompleteTrips(int[] time, long totalTrips, long endTime){
        long currTripCount = 0;
        for (int t : time) {
            currTripCount += (endTime / (long)t);
        }
        return currTripCount >= totalTrips;
    }

    public int minSteps(String s, String t) {
        int[] a = new int[26];
        int[] b = new int[26];
        for (int i = 0; i < s.length(); i++) {
            a[(s.charAt(i) - 'a')] += 1;
        }
        for (int i = 0; i < t.length(); i++) {
            b[(t.charAt(i) - 'a')] += 1;
        }
        int steps = 0;
        for (int i = 0; i < a.length; i++) {
            steps += Math.abs(a[i] - b[i]);
        }
        return steps;
    }

    public String subStrHash(String s, int power, int modulo, int k, int hashValue) {
        for (int i = 0; i < s.length() && i + k <= s.length(); i++) {
            String sub = s.substring(i, i + k);
            int hash = computeHash(sub, power, modulo, k);
            if(hash == hashValue){
                return sub;
            }
        }
        return s.substring(0, k + 1);
    }

    private int computeHash(String sub, int power, int modulo, int k) {
        double hash = 0;
        for (int i = 0; i < sub.length(); i++) {
            int charIdx = sub.charAt(i) - 'a' + 1;
            double currHash = (charIdx * Math.pow(power, i));
            currHash = currHash % modulo;
            hash = hash + currHash;
            hash = hash % modulo;
        }
        return (int)hash;
    }

    public List<Integer> maxScoreIndices(int[] nums) {
        int n = nums.length;
        int[] zeroFromLeft = new int[n];
        int[] oneFromRight = new int[n];
        if(nums[0] == 0 ){
            zeroFromLeft[0] = 1;
        }
        if(nums[n - 1] == 1){
            oneFromRight[n - 1] = 1;
        }
        for (int i = 1; i < n; i++) {
            if(nums[i] == 0){
                zeroFromLeft[i] = zeroFromLeft[i - 1] + 1;
            }else{
                zeroFromLeft[i] = zeroFromLeft[i - 1];
            }
        }
        for (int i = n - 2; i >= 0; i--) {
            if(nums[i] == 1){
                oneFromRight[i] = oneFromRight[i + 1] + 1;
            }else{
                oneFromRight[i] = oneFromRight[i + 1];
            }
        }
        TreeMap<Integer, List<Integer>> count = new TreeMap<>(Collections.reverseOrder());
        for (int i = 0; i <= n; i++) {
            int sum = 0;
            if(i == n){
                sum = zeroFromLeft[i -1];
            }else if(i > 0){
                sum = oneFromRight[i] + zeroFromLeft[i - 1];
            }else{
                sum = oneFromRight[i];
            }
            count.putIfAbsent(sum, new ArrayList<>());
            count.get(sum).add(i);
        }
        int max = count.firstKey();
        List<Integer> res = count.get(max);
        return res;
    }

    public int maximumGood(int[][] statements) {
        HashMap<Integer, int[]> reviewsMap = new HashMap<>();
        for (int i = 0; i < statements.length; i++) {
            for (int j = 0; j < statements[i].length; j++) {
                if(i == j) continue;
                int statement = statements[i][j];
                int[] review = reviewsMap.getOrDefault(j, new int[]{0,0});
                if(statement == 0){ // bad
                    review[1] += 1;
                }else if(statement == 1){ // good
                    review[0] += 1;
                }
                reviewsMap.put(j, review);
            }
        }
        int count = 0;
        for (Integer person : reviewsMap.keySet()) {
            int[] reviews = reviewsMap.get(person);
            if(reviews[0] > reviews[1]){
                count += 1;
            }
        }
        return count;
    }

    public static List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> mNum = new ArrayList<>();
        for (int ele : nums) {
            mNum.add(ele);
        }
        subsetsRec(result, new ArrayList<>(), nums, 0);
        return result;
    }

    public static void subsetsRec(List<List<Integer>> result, List<Integer> tempList, int[] nums, int start) {
        result.add(new ArrayList<>(tempList));
        for (int i = start; i < nums.length; i++) {
            tempList.add(nums[i]);
            subsetsRec(result, tempList, nums, i + 1);
            tempList.remove(tempList.size() - 1);
        }
    }

    public int minSpaceWastedKResizing(int[] nums, int k) {
        return 0;
    }

    public String shortestSuperstring(String[] words) {
        LinkedList<String> list = new LinkedList<>(Arrays.asList(words));
        return dfs(list);
    }

    // pre - 3, 9, 20, 15, 7
    // in - 9, 3, 15, 20, 7
    // in - 9, 3, 15, 20, 7

    public static String shortestSuperstring1(String[] A) {
        int n = A.length;
        int[][] graph = new int[n][n];

        // Build the graph
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = calc(A[i], A[j]);
                graph[j][i] = calc(A[j], A[i]);
            }
        }

        // Creating dp array
        int[][] dp = new int[1 << n][n];

        // Creating path array
        int[][] path = new int[1 << n][n];
        int last = -1, min = Integer.MAX_VALUE;

        // start TSP DP
        for (int i = 1; i < (1 << n); i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);

            // Iterate j from 0 to n - 1
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) > 0) {
                    int prev = i - (1 << j);

                    // Check if prev is zero
                    if (prev == 0) {
                        dp[i][j] = A[j].length();
                    } else {
                        // Iterate k from 0 to n - 1
                        for (int k = 0; k < n; k++) {
                            if (dp[prev][k] < Integer.MAX_VALUE &&
                                    dp[prev][k] + graph[k][j] < dp[i][j]) {
                                dp[i][j] = dp[prev][k] + graph[k][j];
                                path[i][j] = k;
                            }
                        }
                    }
                }
                if (i == (1 << n) - 1 && dp[i][j] < min) {
                    min = dp[i][j];
                    last = j;
                }
            }
        }

        // Build the path
        StringBuilder sb = new StringBuilder();
        int cur = (1 << n) - 1;

        // Creating a stack
        Stack<Integer> stack = new Stack<>();

        // Untill cur is zero
        // push last
        while (cur > 0) {
            stack.push(last);
            int temp = cur;
            cur -= (1 << last);
            last = path[temp][last];
        }

        // Build the result
        int i = stack.pop();
        sb.append(A[i]);

        // Untill stack is empty
        while (!stack.isEmpty()) {
            int j = stack.pop();
            sb.append(A[j].substring(A[j].length() -
                                     graph[i][j]));
            i = j;
        }
        return sb.toString();
    }

    public static int calc(String a, String b) {
        for (int i = 1; i < a.length(); i++) {
            if (b.startsWith(a.substring(i))) {
                return b.length() - a.length() + i;
            }
        }
        return b.length();
    }

    public String dfs(LinkedList<String> words) {
        int n = words.size();
        if (n == 1) return words.get(0);
        String shortestStr = "";
        int max = -1;
        int index1 = 0, index2 = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                String merge = merge(words.get(i), words.get(j));
                int savedLen = words.get(i).length() + words.get(j).length() - merge.length();
                if (savedLen > max) {
                    max = savedLen;
                    shortestStr = merge;
                    index1 = i;
                    index2 = j;
                }
            }
        }
        String s1 = words.get(index1);
        String s2 = words.get(index2);
        words.remove(s1);
        words.remove(s2);
        words.add(shortestStr);
        return dfs(words);
    }

    public String merge(String s1, String s2) {
        String shortest = "";
        int len1 = s1.length();
        int len2 = s2.length();
        int shortLen = Integer.MAX_VALUE;
        // pankaj
        // ajpan -> ajpankaj, pankajpan
        int overlap1 = 0;
        for (int i = 1; i < len1 && i < len2; i++) {
            String prefix = s1.substring(0, i);
            String suffix = s2.substring(len2 - i);
            if (prefix.equals(suffix) && overlap1 < prefix.length()) {
                overlap1 = prefix.length();
            }
        }
        int overlap2 = 0;
        for (int i = s1.length() - 1; i >= 0; i--) {
            String suffix = s1.substring(i);
            String prefix = s2.substring(len2 - i);
            if (prefix.equals(suffix) && overlap2 < prefix.length()) {
                overlap2 = prefix.length();
            }
        }
        return shortest;
    }

    //"(name)is(age)yearsold"
    //[["name","bob"],["age","two"]]
    public String evaluate(String s, List<List<String>> knowledge) {
        HashMap<String, String> map = new HashMap<>();
        for (List<String> keys : knowledge) {
            map.putIfAbsent(keys.get(0), keys.get(1));
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length(); ) {
            if (s.charAt(i) == '(') {
                StringBuilder key = new StringBuilder();
                int j = i + 1;
                while (s.charAt(j) != ')') {
                    key.append(s.charAt(j));
                    j++;
                }
                out.append(map.getOrDefault(key.toString(), "?"));
                i = j + 1;
            } else {
                out.append(s.charAt(i));
                i = i + 1;
            }
        }
        return out.toString();
    }

    public int numDifferentIntegers(String word) {
        StringBuilder out = new StringBuilder();
        HashSet<Long> set = new HashSet<>();
        char[] arr = word.toCharArray();

        for (int i = 0; i < arr.length; i++) {
            if (Character.isDigit(arr[i])) {
                out.append(arr[i]);
            } else if (out.length() > 0) {
                BigInteger bigInteger = new BigInteger(out.toString());
                //set.add(Integer.valueOf(out.toString()));
                set.add(bigInteger.longValue());
                out = new StringBuilder();
            }
        }
        if (out.length() > 0) {
            BigInteger bigInteger = new BigInteger(out.toString());
            //set.add(Integer.valueOf(out.toString()));
            set.add(bigInteger.longValue());
        }
        return set.size();
    }

    /* 1726. Tuple with Same Product
     * https://leetcode.com/problems/tuple-with-same-product/
     * */
    public int tupleSameProduct(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < i; j++) {
                int prod = nums[i] * nums[j];
                res += 8 * map.getOrDefault(prod, 0);
                map.put(prod, map.getOrDefault(prod, 0) + 1);
            }
        }
        return res;
    }

    public static List<Long> getMaxArea(int w, int h, List<Boolean> isVertical, List<Integer> distance) {
        List<Long> res = new ArrayList<Long>();
        List<Integer> hList = new ArrayList<>();
        hList.add(0);
        hList.add(h);
        Collections.sort(hList);

        List<Integer> vList = new ArrayList<>();
        vList.add(0);
        vList.add(w);
        Collections.sort(vList);
        for (int i = 0; i < isVertical.size(); i++) {
            if (isVertical.get(i)) {
                vList.add(distance.get(i));
                Collections.sort(vList);
            } else {
                hList.add(distance.get(i));
                Collections.sort(hList);
            }
            long maxArea = maxArea(hList, vList);
            res.add(maxArea);
        }
        return res;
    }

    public static int maxArea(List<Integer> hList, List<Integer> vList) {
        long H = 0;
        for (int i = 1; i < hList.size(); i++) {
            H = Math.max(H, (long) hList.get(i) - hList.get(i - 1));
        }
        long W = 0;
        for (int i = 1; i < vList.size(); i++) {
            W = Math.max(W, (long) vList.get(i) - vList.get(i - 1));
        }
        return (int) (H * W % (int) (1e9 + 7));
    }

    /* HackerRank problems*/
    public static long taskOfPairing(List<Long> freq) {
        long pairs = 0L;
        LinkedList<Long> freqs = new LinkedList<>(freq);

        Long prevWeight = 0L;
        while (!freqs.isEmpty()) {
            Long dumbbells = freqs.pollFirst() + prevWeight;
            if (dumbbells % 2 == 0) {
                pairs += dumbbells / 2;
                prevWeight = 0L;
            } else if (dumbbells > 2) {
                prevWeight = 1L;
                pairs += dumbbells / 2;
            } else {
                prevWeight = dumbbells;
            }
        }
        BigInteger big = BigInteger.valueOf(pairs);
        return big.longValue();
    }

    /* 1642. Furthest Building You Can Reach
     * https://leetcode.com/problems/furthest-building-you-can-reach/
     * */


    /* 1505. Minimum Possible Integer After at Most K Adjacent Swaps On Digits
     * https://leetcode.com/problems/minimum-possible-integer-after-at-most-k-adjacent-swaps-on-digits/
     * */
    public String minInteger(String num, int k) {
        return null;
    }

    /* 447. Number of Boomerangs
     * https://leetcode.com/problems/number-of-boomerangs/
     * */
    public int numberOfBoomerangs(int[][] points) {
        int n = points.length;
        if (n == 1) return 0;
        return n;
    }

    /*
     * https://leetcode.com/problems/count-subtrees-with-max-distance-between-cities/
     * */
    public int[] countSubgraphsForEachDiameter(int n, int[][] edges) {
        int[] res = new int[n];
        return res;
    }

    /* 1000. Minimum Cost to Merge Stones
     * https://leetcode.com/problems/minimum-cost-to-merge-stones/
     * */
    public int mergeStones(int[] stones, int K) {
        return 0;
    }


}
