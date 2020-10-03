package com.pkumar7.systemdesign;

import java.util.HashMap;

public class LRUCache {
	public static void main(String[] args) {
		LRUCacheDesign cache = new LRUCacheDesign(2);
		
		cache.put(1, 1);
		cache.put(2, 2);
		System.out.println(" get:1 " + cache.get(1));       // returns 1
		cache.put(3, 3);    // evicts key 2
		System.out.println(" get:2 " + cache.get(2));        // returns -1 (not found)
		cache.put(4, 4);    // evicts key 1
		System.out.println(" get:1 " + cache.get(1));        // returns -1 (not found)
		System.out.println(" get:3 " + cache.get(3));        // returns 3
		System.out.println(" get:4 " + cache.get(4));        // returns 4
	}
	
	//https://www.programcreek.com/2013/03/leetcode-lru-cache-java/
	//https://leetcode.com/problems/lru-cache/
	static class LRUCacheDesign{
		Node head;
		Node tail;
		HashMap<Integer, Node> mMap = null;
		int cap = 0;
		
		class Node {
			int key;
			int value;
			Node next;
			Node prev;
			
			public Node(int key, int val) {
				this.value = val;
				this.key = key;
			}
		}
		
		public LRUCacheDesign(int capacity) {
			cap = capacity;
			mMap = new HashMap<>();
		}
		
		public int get(int key) {
			if(mMap.get(key) == null) {
				return -1;
			}
			Node temp = mMap.get(key);
			removeNode(temp);
			addToTail(temp);
			
			return temp.value;
		}
		
		public void put(int key, int val) {
			if(mMap.containsKey(key)) {
				Node temp = mMap.get(key);
				temp.value = val;
				
				removeNode(temp);
				addToTail(temp);
			}else {
				if(mMap.size() >= cap) {
					mMap.remove(head.key);
					removeNode(head);
				}
				Node child = new Node(key,val);
				addToTail(child);
				mMap.put(key, child);
			}
		}

		private void addToTail(Node temp) {
			// TODO Auto-generated method stub
			if(tail != null) {
				tail.next = temp;
			}
			temp.prev = tail;
			temp.next = null;
			tail = temp;
			
			if(head == null) {
				head = tail;
			}
		}

		private void removeNode(Node temp) {
			// TODO Auto-generated method stub
			if(temp.prev != null) {
				temp.prev.next = temp.next;
			}else {
				//Remove from head
				head = temp.next;
			}
			
			if(temp.next != null) {
				temp.next.prev = temp.prev;
			}else {
				//Remove from tail
				tail = temp.prev;
			}
			
		}
		
	}
}
