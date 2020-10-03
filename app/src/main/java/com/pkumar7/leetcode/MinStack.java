package com.pkumar7.leetcode;

import java.util.PriorityQueue;
import java.util.Stack;

public class MinStack {
    Stack<Integer> mStack = new Stack<>();
    int  minEle = Integer.MAX_VALUE;

    public MinStack() {

    }

    public void push(int x) {
        if(x <= minEle){
            mStack.push(x);
            minEle = x;
        }
        mStack.push(x);
    }

    public void pop() {
        if(mStack.pop() == minEle){
            minEle = mStack.pop();
        }
    }

    public int top() {
        return mStack.peek();
    }

    public int getMin() {
        return minEle;
    }
}
