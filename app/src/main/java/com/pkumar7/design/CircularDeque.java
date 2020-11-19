package com.pkumar7.design;

/**
 * Created by Pankaj Kumar on 16/November/2020
 * https://leetcode.com/problems/design-circular-deque/
 */
class CircularDeque {

    /** Initialize your data structure here. Set the size of the deque to be k. */
    int[] deque;
    int front = -1, rear = -1;
    final int size;
    public CircularDeque(int k) {
        deque = new int[k];
        front = -1;
        size = k;
        rear = 0;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if(isFull()) return false;
        if(front == -1){
            front = rear = 0;
        }else if(front == 0){
            front = size - 1;
        }else{
            front -= 1;
        }
        deque[front] = value;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if(isFull()) return false;
        if(front == -1){
            front = rear = 0;
        }else if(rear == size - 1){
            rear = 0;
        }else{
            rear += 1;
        }
        deque[rear] = value;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if(isEmpty()) return false;
        deque[front] = -1;
        if(front == rear){
            front = rear = -1;
        }else if(front == size - 1){
            front = 0;
        }else{
            front += 1;
        }
        return true;
    }

    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if(isEmpty()) return false;
        deque[rear] = -1;
        if(front == rear){
            front = rear = -1;
        }else if(rear == 0){
            rear = size - 1;
        }else{
            rear -= 1;
        }
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if(isEmpty() || front < 0){
            return -1;
        }
        return deque[front];
    }

    /** Get the last item from the
     * deque. */
    public int getRear() {
        if(isEmpty() || rear < 0){
            return -1;
        }
        return deque[rear];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        if(front == -1) return true;
        return false;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        if((front == 0 && rear == size - 1) ||
                front == rear + 1){
            return true;
        }
        return false;
    }
}
