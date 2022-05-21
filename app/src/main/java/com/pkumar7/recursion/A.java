package com.pkumar7.recursion;

import java.util.LinkedList;
import java.util.Stack;

public class A {

    /*
     * https://leetcode.com/problems/elimination-game/
     * Josephus problem
     * */
    public int lastRemaining(int n) {
        Stack<Integer> stack = new Stack<>();
        while (n > 1) {
            n /= 2;
            stack.push(n);
        }
        int result = 1;
        while (!stack.isEmpty()) {
            result = 2 * (1 + stack.pop() - result);
        }
        return result;
    }

    public int lastRemainingIterative(int n){
        boolean left = true;
        int rem = n;
        int step = 1;
        int head = 1;
        while (rem > 1){
            if(left || rem % 2 == 1){
                head = head + step;
            }
            rem = rem / 2;
            step = step * 2;
            left = !left;
        }
        return head;
    }

    public int lastRemainingRec(int n) {
        return n == 1 ? 1 : 2 * (1 + n / 2 - lastRemainingRec(n / 2));
    }

    /*
     * https://leetcode.com/problems/find-the-winner-of-the-circular-game/
     * 1823. Find the Winner of the Circular Game
     * */
    public int findTheWinner(int n, int k) {
        return josephus(n, k);
    }

    int josephus(int n, int k) {
        return n > 1 ? (josephus(n - 1, k) + k - 1) % n + 1 : 1;
    }

    int josephusIterative(int n, int k) {
        int res = 0;
        for (int i = 1; i <= n; ++i)
            res = (res + k) % i;
        return res + 1;
    }

    public int findTheWinner1(int n, int k) {
        LinkedList<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= n; i++) {
            queue.offer(i);
        }
        int start = 0;
        int end = start + k - 1;
        while (queue.size() > 1){
            queue.remove(end);
            start = end;
            end = (start + k - 1) % queue.size() ;
        }
        return queue.poll();
    }
}
