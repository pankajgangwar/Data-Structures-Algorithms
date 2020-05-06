package com.mission.google.systemdesign;

import java.util.PriorityQueue;
import java.util.Stack;

public class MinStack {

	/** initialize your data structure here. */
	/**
	 *  MinStack minStack = new MinStack();
		minStack.push(-2);
		minStack.push(0);
		minStack.push(-3);
		minStack.getMin();   --> Returns -3.
		minStack.pop();
		minStack.top();      --> Returns 0.
		minStack.getMin();   --> Returns -2.
	 * */
	Stack<Integer> mStack;
	PriorityQueue<Integer> mMinHeap ;
    public MinStack() {
    	mMinHeap = new PriorityQueue<>();
    	mStack = new Stack<>();
    }
    
    public void push(int x) {
    	//Push element x onto stack. 
        mStack.push(x);
        mMinHeap.offer(x);
    }
    
    public void pop() {
    	// Removes the element on top of the stack. 
       int ele = mStack.pop();
       mMinHeap.remove(ele);
    }
    
    public int top() {
    	//Get the top element. 
        return mStack.peek();
    }
    
    public int getMin() {
    	//Retrieve the minimum element in the stack. 
        return mMinHeap.peek();
    }
    
    /*
      int min = Integer.MAX_VALUE;
    Stack<Integer> stack = new Stack<Integer>();
    public void push(int x) {
        // only push the old minimum value when the current 
        // minimum value changes after pushing the new value x
        if(x <= min){          
            stack.push(min);
            min=x;
        }
        stack.push(x);
    }

    public void pop() {
        // if pop operation could result in the changing of the current minimum value, 
        // pop twice and change the current minimum value to the last minimum value.
        if(stack.pop() == min) min=stack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return min;
    }
    */
    
}
