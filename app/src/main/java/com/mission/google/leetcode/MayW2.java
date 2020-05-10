package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class MayW2 {
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */
    /* https://leetcode.com/problems/all-oone-data-structure/ */
    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/increasing-subsequences/ */
    /* https://leetcode.com/problems/partition-array-into-three-parts-with-equal-sum/ */

    public static void main(String[] args) {
        MayW2 w2 = new MayW2();
        //w2.leastBricks(new ArrayList<>());
        boolean status = w2.isPerfectSquare(8);
        System.out.println("status = " + status);
    }

    /* https://leetcode.com/problems/minimum-time-to-collect-all-apples-in-a-tree/ */
     public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        LinkedList<Integer> adjList[] = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            adjList[i] = new LinkedList<>();
        }
        for(int[] edge : edges){
            int src = edge[0];
            int dst = edge[1];
            adjList[src].add(dst);
            adjList[dst].add(src);
        }
        HashSet<Integer> apples = new HashSet<>();
        for (int i = 0; i < hasApple.size(); i++) {
            if(hasApple.get(i)){
                apples.add(i);
            }
        }
        boolean[] visited = new boolean[n];
        return helper(adjList, 0, apples, visited);
    }

    public int helper(LinkedList<Integer>[] graph, int src, HashSet<Integer> apples, boolean[] visited){
        visited[src] = true;
        int totalTime = 0;
        for (int i = 0; i < graph[src].size(); i++) {
            int dst = graph[src].get(i);
            if(visited[dst]) continue;
            totalTime += helper(graph, dst, apples, visited);
        }

        if((totalTime > 0 || apples.contains(src)) && src != 0) totalTime += 2;
        return totalTime;
    }

    /* https://leetcode.com/problems/count-triplets-that-can-form-two-arrays-of-equal-xor/
    *  Revisit
    * */
    public int countTriplets(int[] arr) {
        int n = arr.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int a = arr[i];
            for (int j = i + 1; j < n; j++) {
                a ^= arr[j];
                if(a == 0){
                    res += j - i;
                }
            }
        }
        return res;
    }

    public int countTripletsI(int[] arr) {
        int n = arr.length + 1;
        int[] prefix = new int[n];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i-1] ^ arr[i-1];
        }
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if(prefix[i] == prefix[j]){
                    res += j - i - 1;
                }
            }
        }
        return res;
    }

    public int solutionTLE(int[] arr) {
        int res = 0;
        int n = arr.length;
        for (int i = 0; i < n ; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j; k < n; k++) {
                    int a = 0;
                    int b = 0;
                    for (int l = i; l <= j - 1 ; l++) {
                        a ^= arr[l];
                    }
                    for (int l = j; l <= k ; l++) {
                        b ^= arr[l];
                    }
                    if(a == b) res++;
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/build-an-array-with-stack-operations/ */
    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        Stack<Integer> stack = new Stack<>();
        int idx = 0;
        for (int i = 1; i <= n && idx < target.length; i++) {
            stack.push(i);
            res.add("Push");
            if(stack.peek() != target[idx]){
                stack.pop();
                res.add("Pop");
            }else{
                idx++;
            }
        }
        return res;
    }


    /* https://leetcode.com/problems/valid-perfect-square/ */
    public boolean isPerfectSquare(int n) {
        String number = String.valueOf(n);
        int last = number.charAt(number.length()-1) - '0';
        if(last == 2 || last == 3 || last == 7 || last == 8){
            return false;
        }
        for (int i = 1; i * i <= n; i++) {
            if(n % i == 0 && (n / i  == i)) return true;
        }
        return false;
    }

    public boolean binarySearch(int num){
        if(num < 2) return true;
        long left = 2;
        long right = num / 2;
        while (left <= right){
            long mid = left + (right - left) / 2;
            long guess = mid * mid;
            if(guess == num) return true;
            if(guess > num){
                right = mid - 1;
            }else{
                left = mid + 1;
            }
        }
        return false;
    }

    /* https://leetcode.com/problems/brick-wall/ */
    public int leastBricks(List<List<Integer>> wall) {
        return 0;
    }
}
