package com.pkumar7.datastructures;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


public class NAryTree {

	public static void main(String[] args) {
		
	}
	
	/**
	 * https://leetcode.com/problems/n-ary-tree-level-order-traversal/
	 * 
	 * **/
	
	public List<List<Integer>> levelOrder(Node root) {
		  Queue<Node> mQueue = new LinkedList<>();
	        List<List<Integer>> final_list = new ArrayList<List<Integer>>();
	        
	        if(root == null){
	            return final_list;
	        }
			mQueue.offer(root);

	        
			while(!mQueue.isEmpty()) {
	            ArrayList<Integer> current_level = new ArrayList<>();
	            int len = mQueue.size();
	            for(int i = 0; i < len; i++){
	                Node current = mQueue.poll();
	                current_level.add(current.val);
	                System.out.print("Adding: " + current.val + " for level " + final_list.size());
	               
	                for(Node child : current.children) {
	                    mQueue.offer(child);
				    }
	            }
	            System.out.print("Adding " + current_level.size());
				final_list.add(current_level);
			}
			
			return final_list;
    }

	class Node {
		public int val;
		public List<Node> children;

		public Node() {}

		public Node(int _val,List<Node> _children) {
			val = _val;
			children = _children;
		}
	};
}
