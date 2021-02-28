package com.pkumar7.stack;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Created by Pankaj Kumar on 27/September/2020
 */
class A {
    public static void main(String[] args) {
        A d = new A();
    }

    /*
        https://leetcode.com/problems/car-fleet-ii/
    */
    public double[] getCollisionTimes(int[][] cars) {
        int n = cars.length;
        Stack<double[]> stack = new Stack<>(); // ["distance", "speed", "time"]
        double[] times = new double[n];
        for (int i = n - 1;  i >= 0; i--) {
            int[] car = cars[i];
            int x = car[0];
            int speed = car[1];
            while (!stack.isEmpty() && stack.peek()[1] >= speed) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                times[i] = -1;
                stack.push(new double[] {x, speed, Double.POSITIVE_INFINITY});
                continue;
            }
            while (!stack.isEmpty()) {
                double[] top = stack.peek();
                double dx = top[0] - x;
                double dv = speed - top[1];
                double currentTime = dx / dv;
                double lasTime = top[2];
                if (currentTime > lasTime) {
                    // Car crashed in past can't agian crash in future
                    stack.pop();
                } else {
                    times[i] = currentTime;
                    stack.push(new double[] {x, speed, currentTime});
                    break;
                }
            }
        }
        return times;
    }

    /*
     * 1673. Find the Most Competitive Subsequence
     * https://leetcode.com/problems/find-the-most-competitive-subsequence/
     */
    public int[] mostCompetitive(int[] nums, int k) {
        int len = nums.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            int num = nums[i];
            int rest = len - i - 1;
            while ((rest + stack.size()) >= k && !stack.isEmpty() && stack.peek() > num) {
                stack.pop();
            }
            if (stack.size() < k) {
                stack.push(num);
            }
        }
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[k - i - 1] = stack.pop();
        }
        return res;
    }

    /* 1172. Dinner Plate Stacks
    * https://leetcode.com/problems/dinner-plate-stacks/
    * */
    static class DinnerPlates {
        TreeMap<Integer, Stack<Integer>> stacks = new TreeMap<>();
        TreeSet<Integer> available = new TreeSet<>();
        final int cap;
        public DinnerPlates(int capacity) {
            cap = capacity;
        }

        public void push(int val) {
            int index = 0;
            if (available.isEmpty()) {
                Map.Entry<Integer, Stack<Integer>> e = stacks.lastEntry();
                if (e != null) {
                    index = e.getKey() + 1;
                }
                available.add(index);
            } else {
                index = available.first();
            }
            Stack<Integer> curr = stacks.getOrDefault(index, new Stack<>());
            curr.push(val);
            if (curr.size() == cap) {
                available.remove(index);
            }
            stacks.put(index, curr);
        }

        public int pop() {
            if (stacks.isEmpty()) {
                return -1;
            }
            return popAtStack(stacks.lastKey());
        }

        public int popAtStack(int index) {
            if (!stacks.containsKey(index)) {
                return -1;
            }
            Stack<Integer> curr = stacks.get(index);
            int res = curr.pop();
            if (curr.isEmpty()) {
                stacks.remove(index);
            }
            available.add(index);
            return res;
        }
    }

    /* 1598. Crawler Log Folder
    * https://leetcode.com/problems/crawler-log-folder/
    * */
    public int minOperations(String[] logs) {
        int steps = 0;
        for (int i = 0; i < logs.length; i++) {
            if (logs[i].equals("../")) {
                if (steps > 0) {
                    steps--;
                }
            } else if (logs[i].equals("./")) {
                steps = steps;
            } else {
                steps++;
            }
        }
        return steps;
    }
}
