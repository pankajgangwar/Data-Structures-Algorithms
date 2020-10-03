package com.pkumar7.leetcode;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

public class FirstUnique {
    public static void main(String[] args) {
        int[] nums = new int[]{2, 3, 5};
        FirstUnique firstUnique = new FirstUnique(nums);
        System.out.println("showFirstUnique() = " + firstUnique.showFirstUnique()); // return 2
        firstUnique.add(5);            // the queue is now [2,3,5,5]
        System.out.println("showFirstUnique() = " + firstUnique.showFirstUnique()); // return 2
        firstUnique.add(2);            // the queue is now [2,3,5,5,2]
        System.out.println("showFirstUnique() = " + firstUnique.showFirstUnique()); // return 3
        firstUnique.add(3);            // the queue is now [2,3,5,5,2,3]
        System.out.println("showFirstUnique() = " + firstUnique.showFirstUnique()); // return -1
    }

    Deque<Integer> q;
    HashMap<Integer, Integer> map;

    public FirstUnique(int[] nums) {
        q = new LinkedList<>();
        map = new HashMap<>();

        for (int num : nums) {
            add(num);
        }
    }

    public int showFirstUnique() {
        if (q.isEmpty()) return -1;
        int top = q.peek();
        if (map.get(top) > 1) return -1;
        return top;
    }

    public void add(int value) {
        if (!map.containsKey(value)) {
            q.addLast(value);
            map.put(value, 1);
            return;
        } else if (map.containsKey(value)) {
            map.put(value, map.get(value) + 1);
            if (!q.isEmpty() && q.peekFirst() == value) {
                q.pollFirst();
            }
        }
    }
}

