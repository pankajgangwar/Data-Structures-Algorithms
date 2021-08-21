package com.pkumar7;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;


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
    // https://leetcode.com/problems/remove-boxes/
    public static void main(String[] args) {
        Current current = new Current();

    }

    /*
     * https://leetcode.com/problems/find-the-longest-substring-containing-vowels-in-even-counts/
     * https://leetcode.com/problems/number-of-wonderful-substrings/
     * https://leetcode.com/problems/find-longest-awesome-substring
     * */
    public long wonderfulSubstrings(String word) {
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

    public String dfs(LinkedList<String> words){
        int n = words.size();
        if(n == 1) return words.get(0);
        String shortestStr = "";
        int max = -1;
        int index1 = 0, index2 = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                String merge = merge(words.get(i), words.get(j));
                int savedLen = words.get(i).length() + words.get(j).length() - merge.length();
                if(savedLen > max){
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

    public String merge(String s1, String s2){
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
            if(prefix.equals(suffix) && overlap1 < prefix.length()){
                overlap1 = prefix.length();
            }
        }
        int overlap2 = 0;
        for (int i = s1.length() - 1 ; i >= 0; i--) {
            String suffix = s1.substring(i);
            String prefix = s2.substring(len2 - i);
            if(prefix.equals(suffix) && overlap2 < prefix.length()){
                overlap2 = prefix.length();
            }
        }
        return shortest;
    }

    //"(name)is(age)yearsold"
    //[["name","bob"],["age","two"]]
    public String evaluate(String s, List<List<String>> knowledge) {
        HashMap<String, String> map = new HashMap<>();
        for(List<String> keys : knowledge){
            map.putIfAbsent(keys.get(0), keys.get(1));
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < s.length();) {
            if(s.charAt(i) == '('){
                StringBuilder key = new StringBuilder();
                int j = i+1;
                while (s.charAt(j) != ')'){
                    key.append(s.charAt(j));
                    j++;
                }
                out.append(map.getOrDefault(key.toString(), "?"));
                i = j + 1;
            }else{
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
            if(Character.isDigit(arr[i])){
                out.append(arr[i]);
            }else if(out.length() > 0){
                BigInteger bigInteger = new BigInteger(out.toString());
                //set.add(Integer.valueOf(out.toString()));
                set.add(bigInteger.longValue());
                out = new StringBuilder();
            }
        }
        if(out.length() > 0){
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
        for (int i = 1; i < hList.size() ; i++) {
            H = Math.max(H, (long)hList.get(i) - hList.get(i - 1));
        }
        long W = 0;
        for (int i = 1; i < vList.size() ; i++) {
            W = Math.max(W, (long)vList.get(i) - vList.get(i - 1));
        }
        return (int) (H * W % (int)(1e9 + 7));
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
