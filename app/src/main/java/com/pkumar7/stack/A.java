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
        DinnerPlates D = new DinnerPlates(2);  // Initialize with capacity = 2
        D.push(1);
        D.push(2);
        D.push(3);
        //D.push(4);
        //D.push(5);
        D.popAtStack(1);
        /*D.push(20);
        D.push(21);
        D.popAtStack(0);
        D.popAtStack(2);*/
        D.pop();
        D.pop();
        /*D.pop();
        D.pop();
        D.pop();*/
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
            if(available.isEmpty()){
                Map.Entry<Integer, Stack<Integer>> e = stacks.lastEntry();
                if(e!= null){
                    index = e.getKey() + 1;
                }
                available.add(index);
            }else{
                index = available.first();
            }
            Stack<Integer> curr = stacks.getOrDefault(index, new Stack<>());
            curr.push(val);
            if(curr.size() == cap){
                available.remove(index);
            }
            stacks.put(index, curr);
        }

        public int pop() {
            if(stacks.isEmpty()){
                return -1;
            }
            return popAtStack(stacks.lastKey());
        }

        public int popAtStack(int index) {
            if(!stacks.containsKey(index)){
                return -1;
            }
            Stack<Integer> curr = stacks.get(index);
            int res = curr.pop();
            if(curr.isEmpty()){
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
            if(logs[i].equals("../")){
                if(steps > 0){
                    steps--;
                }
            }else if(logs[i].equals("./")){
                steps = steps;
            }else{
                steps++;
            }
        }
        return steps;
    }
}
