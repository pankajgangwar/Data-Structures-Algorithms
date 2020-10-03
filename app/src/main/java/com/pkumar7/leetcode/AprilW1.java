package com.pkumar7.leetcode;

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
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;

public class AprilW1 {

    /* https://leetcode.com/problems/redundant-connection-ii/  */
    /* Revisit */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        int n = edges.length;
        int[] root = new int[n+1];

        int[] candidate1 = new int[]{-1,-1};
        int[] candidate2 = new int[]{-1,-1};

        for(int[] edge : edges){
            int father = edge[0];
            int child = edge[1];

            if(root[child] == 0){
                root[child] = father;
            }else if(root[child] != 0){ //Node with 2 parents
                candidate2 = new int[]{father, child}; // 2 possible candidates
                candidate1 = new int[]{root[child], child};
                edge[1] = 0; // Set second edge invalid
            }
        }

        for(int i = 0; i < n+1; i++ ){
            root[i] = i;
        }

        for(int i = 0; i < n; i++){
            int father = edges[i][0];
            int child = edges[i][1];
            int fathersRoot = findParent(root, father);
            int childsRoot = findParent(root, child);

            if(fathersRoot == childsRoot){ // We found a cycle
                if(candidate1[0] == -1){ // No valid candidate
                    return edges[i]; // return current edge
                }else{
                    return candidate1; // we have valid candidate
                }
            }
            root[child] = father;
        }
        //Tree is valid tree with no cycle
        return candidate2;
    }

    public int findParent(int[] root, int x){
        while(x != root[x]){
            root[x] = root[root[x]];
            x = root[x];
        }
        return x;
    }

    static class LFUCache {
        HashMap<Integer, Integer> vals;//cache K and V
        HashMap<Integer, Integer> counts;//K and counters
        HashMap<Integer, LinkedHashSet<Integer>> lists;//Counter and item
        int cap;
        int min = -1;
        public LFUCache(int capacity) {
            cap = capacity;
            vals = new HashMap<>();
            counts = new HashMap<>();
            lists = new HashMap<>();
            lists.put(1, new LinkedHashSet<>());
        }

        public int get(int key) {
            if (!vals.containsKey(key))
                return -1;
            // Get the count from counts map
            int count = counts.get(key);
            // increase the counter
            counts.put(key, count + 1);
            // remove the element from the counter to linkedhashset
            lists.get(count).remove(key);

            // when current min does not have any data, next one would be the min
            if (count == min && lists.get(count).size() == 0)
                min++;
            if (!lists.containsKey(count + 1))
                lists.put(count + 1, new LinkedHashSet<>());
            lists.get(count + 1).add(key);
            return vals.get(key);
        }

        public void put(int key, int value) {
            if (cap <= 0)
                return;
            // If key does exist, we are returning from here
            if (vals.containsKey(key)) {
                vals.put(key, value);
                get(key);
                return;
            }
            if (vals.size() >= cap) {
                int evit = lists.get(min).iterator().next();
                lists.get(min).remove(evit);
                vals.remove(evit);
                counts.remove(evit);
            }
            // If the key is new, insert the value and current min should be 1 of course
            vals.put(key, value);
            counts.put(key, 1);
            min = 1;
            lists.get(1).add(key);
        }
    }

    /* https://leetcode.com/problems/concatenated-words/
    * Revisit */
    public List<String> findAllConcatenatedWordsInADict(String[] words) {
        Set<String> dict = new HashSet<>();
        List<String> result = new ArrayList<>();
        Arrays.sort(words, (a,b) -> a.length() - b.length());
        System.out.println(Arrays.toString(words));
        for(String word : words){
            if(helper(word, dict)){
                result.add(word);
            }
            dict.add(word);
        }
        System.out.println(result.toString());
        return result;
    }

    public boolean helper(String word,Set<String> dict){
        if(dict.isEmpty()) return false;
        int n = word.length();
        boolean[] dp = new boolean[n+1];
        dp[0] = true;
        for(int i = 1; i <= n; i++){
            for(int j = 0; j < i; j++){
                if(dp[j] && dict.contains(word.substring(j, i))){
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n];
    }

    /* https://leetcode.com/problems/frog-jump/ */
     public boolean canCross(int[] stones) {
        int n = stones.length;
        int last = stones[n-1];
        HashSet<Integer> stonePos = new HashSet<>();
        for(int stone : stones){
            stonePos.add(stone);
        }
        if(stones[1] != 1) return false;
        if(n == 1 || n == 2 ) return true;
        for(int i = 3; i < n; i++){
            if(i > 3 && stones[i] > stones[i-1]*2) return false;
        }
        return canCrossDFS(stonePos, last, 1, 1 );
    }
    public boolean canCrossStack(int[] stones) {
        for(int i = 3; i < stones.length; i++){
            if(stones[i] > stones[i - 1] * 2){
                return false;
            }
        }
        Stack<Integer> jumps = new Stack<>();
        Stack<Integer> positions = new Stack<>();
        
        HashSet<Integer> stonePos = new HashSet<>();
        for(int stone : stones){
            stonePos.add(stone);
        }
        
        int lastStone = stones[stones.length -1];
        positions.add(0);
        jumps.add(0);
        
        while(!positions.isEmpty()){
            int currPos = positions.pop();
            int jumpDistance = jumps.pop();
            for(int i = jumpDistance -1; i <= jumpDistance +1; i++){
                if(i <= 0) continue;
                int nextPos = currPos + i;
                if(lastStone == nextPos){
                    return true;
                }else if(stonePos.contains(nextPos)){
                    positions.add(nextPos);
                    jumps.add(i);
                }
            }
        }
        return false;
        //return canCrossDFS(stonePos, lastStone, 0, 0);
    }

    public boolean canCrossDFS(Set<Integer> stones, int lastStone, int pos, int jumps){
        if(lastStone == jumps + pos || lastStone == jumps + pos - 1 || lastStone == jumps + pos + 1){
            return true;
        }
        if(stones.contains(jumps + pos + 1)){
            if(canCrossDFS(stones, lastStone, jumps + pos + 1, jumps + 1)){
                return true;
            }
        }
        if(jumps > 1 && stones.contains(jumps + pos - 1)){
            if(canCrossDFS(stones, lastStone, jumps + pos - 1, jumps - 1)){
                return true;
            }
        }
        if(stones.contains(jumps + pos)){
            if(canCrossDFS(stones, lastStone, jumps + pos, jumps)){
                return true;
            }
        }
        return false;
    }

    /* https://leetcode.com/problems/add-digits/ */
    public int addDigits(int num) {
        String digit = String.valueOf(num);
        while(digit.length() != 1){
            int curr = Integer.valueOf(digit);
            int sum = getNext(curr);
            digit = String.valueOf(sum);
        }
        return Integer.valueOf(digit);
    }
    public int getNext(int n){
        int sum = 0;
        while( n > 0 ){
            sum += n % 10;
            n = n / 10;
        }
        return sum;
    }

    /* https://leetcode.com/problems/super-washing-machines/
       Revisit
     */
    public int findMinMoves(int[] machines) {
        int sum = 0;
        for(int i = 0; i < machines.length; i++){
            sum += machines[i];
        }
        if(sum % machines.length != 0) return -1;
        int goal = sum / machines.length;
        int n = machines.length;
        
        int left[] = new int[n];
        int right[] = new int[n];

        for(int i = 1; i < n; i++){
            left[i] = left[i-1] + machines[i-1];
        }
        for(int i = n-2; i >= 0; i--){
            right[i] = right[i+1] + machines[i+1];
        }
        int max = 0;
        for(int i = 0; i < n; i++){
            int l = i * goal - left[i];
            int r = (n - i - 1) * goal - right[i];
            if(l > 0 && r > 0){
                max = Math.max(max, l + r);
            }else{
                max = Math.max(max, Math.max(l , r));
            }
        }
        return max;
    }

    /* https://leetcode.com/problems/gas-station/ */
    public int canCompleteCircuit(int[] gas, int[] cost) {
        for(int i = 0; i < gas.length; i++){
            if(isValidIndex(i, gas, cost)){
                return i;
            }
        }
        return -1;
    }

    public boolean isValidIndex(int start, int[] gas, int[] cost){
        int tank = gas[start] - cost[start];
        int n = gas.length;
        int i  = start + 1;
        while(i != start){
            if(tank < 0) return false;
            tank += gas[i%n];
            tank -= cost[i%n];
            i++;
            i = i % n;
        }
        return tank >= 0;
    }


    /* https://leetcode.com/problems/number-of-steps-to-reduce-a-number-in-binary-representation-to-one/ */
    public int numSteps(String s) {
        BigInteger n = new BigInteger(s, 2);
        int step = 0;
        while(!n.equals(BigInteger.ONE)){
            if(n.testBit(0)){
                n = n.add(BigInteger.ONE);
            }else{
                n = n.shiftRight(1);
            }
            step++;
        }
        return step;
    }

    /* https://leetcode.com/problems/longest-happy-string/ */
    public String longestDiverseStringI(int a, int b, int c) {
        HashMap<Character, Integer> map = new  HashMap<>();
        if(a > 0)
            map.put('a', a);
        if(b > 0)
            map.put('b', b);
        if(c > 0)
            map.put('c', c);

        PriorityQueue<Map.Entry<Character, Integer>> pq =
                new PriorityQueue<>( (e1,e2) -> -Integer.compare(e1.getValue(),e2.getValue()));

        for(Map.Entry<Character, Integer> entry : map.entrySet()){
            pq.offer(entry);
        }

        StringBuilder res = new StringBuilder();
        while(pq.size() > 1){
            Map.Entry<Character, Integer> first = pq.poll();
            Map.Entry<Character, Integer> second = pq.poll();

            if(first.getValue() >= 2){
                res.append(first.getKey());
                res.append(first.getKey());
                first.setValue(first.getValue()-2);
            }else{
                res.append(first.getKey());
                first.setValue(first.getValue()-1);
            }

            if(second.getValue() >= 2 && second.getValue() > first.getValue()){
                res.append(second.getKey());
                res.append(second.getKey());
                second.setValue(second.getValue()-2);
            }else{
                res.append(second.getKey());
                second.setValue(second.getValue()-1);
            }

            if(first.getValue() > 0){
                pq.offer(first);
            }
            if(second.getValue() > 0){
                pq.offer(second);
            }
        }

        if(!pq.isEmpty()){
            Map.Entry<Character, Integer> first = pq.poll();
            if(first.getKey() != res.charAt(res.length() -1)){
                if(first.getValue() >= 2){
                    res.append(first.getKey());
                    res.append(first.getKey());
                    first.setValue(first.getValue()-2);
                }else{
                    res.append(first.getKey());
                    first.setValue(first.getValue()-1);
                }
            }
        }
        return res.toString();
    }

   /* String generate(int a, int b, int c, String aa, String bb, String cc) {
        if (a < b)
            return generate(b, a, c, bb, aa, cc);
        if (b < c)
            return generate(a, c, b, aa, cc, bb);
        if (b == 0)
            return aa.repeat(Math.min(2, a));
        int use_a = Math.min(2, a), use_b = a - use_a >= b ? 1 : 0;
        return aa.repeat(use_a) + bb.repeat(use_b) +
                generate(a - use_a, b - use_b, c, aa, bb, cc);
    }*/

    public String longestDiverseString(int a, int b, int c) {
        //return generate(a, b, c, "a", "b", "c");
        return "";
    }

    /* https://leetcode.com/problems/string-without-aaa-or-bbb/ */
    public String strWithout3a3bIterative(int A, int B) {
        StringBuilder builder = new StringBuilder(A + B);
        while (A + B > 0){
            String curr = builder.toString();
            if (curr.endsWith("aa")){
                builder.append("b");
                --B;
            }else if(curr.endsWith("bb")){
                builder.append("a");
                --A;
            }else if(A > B){
                builder.append("a");
                --A;
            }else {
                builder.append("b");
                --B;
            }
        }
        return builder.toString();
    }

    public String strWithout3a3b(int a, int b) {
        if(a == 0 || b == 0){
            return String.join("", Collections.nCopies(a+b, a == 0 ? "b" : "a"));
        }
        if(a == b){
            return "ab" + strWithout3a3b(a-1, b-1);
        }else if(a > b){
            return "aab" + strWithout3a3b(a-2, b-1);
        }else{
            return "bba" + strWithout3a3b(a-1, b-2);
        }
    }

    /* https://leetcode.com/problems/my-calendar-i/ */
    class MyCalendar {
        TreeMap<Integer, Integer> calendar;
        public MyCalendar() {
            calendar = new TreeMap<>();
        }
        public boolean book(int start, int end) {
            Integer floorKey = calendar.floorKey(start);
            if(floorKey != null && calendar.get(floorKey) > start ) return false;
            Integer ceilingKey = calendar.ceilingKey(start);
            if(ceilingKey != null && ceilingKey < end ) return false;

            calendar.put(start, end);
            return true;
        }
    }

    /* 5 0 1 4 2 3*/
    /* 0 1 2 3 4*/
    public boolean validateSequence(int[] arr , int n){
        Stack<Integer> stack = new Stack<>();
        int idx = 0;
        for (int i = 0; i <= n; i++) {
            stack.push(i);
            while(idx < arr.length && !stack.isEmpty() && stack.peek() == arr[idx]){
                stack.pop();
                idx++;
            }
        }
        return stack.isEmpty();
    }

    /* https://leetcode.com/problems/backspace-string-compare/ */
    public boolean backspaceCompare(String S, String T) {
        StringBuilder s = new StringBuilder();
        StringBuilder t = new StringBuilder();

        for(int i = 0; i < S.length(); i++){
            if(S.charAt(i) == '#'){
                if(s.length() > 0){
                    s.deleteCharAt(s.length()-1);
                }
            }else{
                s.append(S.charAt(i));
            }
        }

        for(int i = 0; i < T.length(); i++){
            if(T.charAt(i) == '#'){
                if(t.length() > 0){
                    t.deleteCharAt(t.length()-1);
                }
            }else{
                t.append(T.charAt(i));
            }
        }
        System.out.println(s + " " + t);
        return s.toString().equals(t.toString());
    }

    public static void main(String[] args) {
        AprilW1 w1 = new AprilW1();
        int[][] edges = new int[][]{
                {1,2},{1,3},{4,5},{2,4},{5,1}
        };
        /* 1111011110000011100000110001011011110010111001010111110001
           1111110011101010110011100100101110010100101110111010111110110010
        * */
        String s = "1101";
        //String res = w1.strWithout3a3b(4, 1);
        //System.out.println("res = " + res);
        boolean status = w1.validateSequence(new int[]{4,3,5,1,2}, 5);
        System.out.println("status = " + status);

        //w1.PredictTheWinner(new int[]{1, 5, 2});
       // w1.findMinMoves(new int[]{0,3,0});

        //w1.findAllConcatenatedWordsInADict(new String[]{"cat","cats","catsdogcats","dog","dogcatsdog",
        // "hippopotamuses","rat","ratcatdogcat"});
        //w1.canCompleteCircuit(new int[]{2,3,4}, new int[]{3,4,3});
        /*LFUCache lfuCache = new LFUCache(2);

        lfuCache.put(1, 1);
        lfuCache.put(2, 2);
        System.out.println(" = " + lfuCache.get(1));       // returns 1
        lfuCache.put(3, 3);    // evicts key 2
        System.out.println(" = " + lfuCache.get(2));       // returns -1 (not found)
        System.out.println(" = " + lfuCache.get(3));       // returns 3.
        lfuCache.put(4, 4);    // evicts key 1.
        System.out.println(" = " + lfuCache.get(1));       // returns -1 (not found)
        System.out.println(" = " + lfuCache.get(3));       // returns 3
        System.out.println(" = " + lfuCache.get(4));       // returns 4*/

        //w1.findRedundantDirectedConnection(edges);
    }
}
