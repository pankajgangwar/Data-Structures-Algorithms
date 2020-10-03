package com.pkumar7.datastructures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class BinaryTree {
	//https://en.wikipedia.org/wiki/Tree_traversal

	public static class TreeNode {
		int val;
		TreeNode left, right;

		public TreeNode(int data) {
			val = data;
			left = null;
			right = null;
		}
	}

	public static void main(String[] args) {
		BinaryTree tree = new BinaryTree();
		tree.createTree();
		// tree.maxSumInPath();

		//tree.recoverFromPreorder();
		Solution solution = new Solution();
		//solution.hasPathSum(root, 24 );
		
		//tree.inorderTraversal(root);
		
		//boolean status = tree.isValidBST(root);
		//System.out.println(status);
		
		/**
		 * 			  4
		 * 		2		   7
		 * 	  1   3		6	  9
		 * */
		
		TreeNode tree2= new TreeNode(4);

		tree2.left = new TreeNode(2);
		tree2.left.left = new TreeNode(1);
		tree2.left.right = new TreeNode(3);
		
		tree2.right = new TreeNode(7);
		tree2.right.left = new TreeNode(6);
		tree2.right.right = new TreeNode(9);
		
		//tree.mergeTrees(root, tree2);
		
		//TreeNode result = tree.invertTree(tree2);
		//tree.inOrderRec(tree2);
		System.out.println("*******************");
		//tree.printLevelOrder(result);
		
		//tree.printFringes(tree2);
		
		tree.compareFringes();
		
	}

	static TreeNode root;

	public void createTree() {

		/*
		 * 25 / \ 15 50 / \ / \ 10 22 35 70 / \ / / 4 12 19 66 / / \ 11 16 68 \ 17 \ 18
		 */

		root = new TreeNode(25);

		root.left = new TreeNode(15);
		root.left.left = new TreeNode(10);
		root.left.right = new TreeNode(27);

		/*root.left.right.left = new TreeNode(19);
		root.left.right.left.left = new TreeNode(16);
		root.left.right.left.left.right = new TreeNode(17);
		root.left.right.left.left.right.right = new TreeNode(18);

		root.left.left.left = new TreeNode(4);
		root.left.left.right = new TreeNode(12);
		root.left.left.right.right = new TreeNode(11);*/

		root.right = new TreeNode(50);
		//root.right.left = new TreeNode(35);
		//root.right.right = new TreeNode(70);
	/*	root.right.left.left = new TreeNode(66);
		root.right.left.left.right = new TreeNode(68);*/
	}

	private void height() {
		int height = getHeight(root);
		System.out.println("Height of Tree: " + height);
	}

	private void printDiameter() {
		int diameter = diameter();
		// int diameter = getDiameter(root);
		System.out.println("Diameter of Tree: " + diameter);
		/*
		 * int[] result = calDiameter(root); System.out.println("Diameter of Tree: " +
		 * result[0]); System.out.println("Height of Tree: " + result[1]);
		 */

	}

	private void printSizeOfTree() {
		System.out.println("Size of Tree: " + getSize(root));
	}

	private int getSize(TreeNode root) {
		if (root == null)
			return 0;
		return getSize(root.left) + getSize(root.right) + 1;
	}

	private void printLevelOrderUsingQueue() {
		// Breadth First Traversal (Level Order traversal)
		Queue<TreeNode> queue = new LinkedList<TreeNode>();
		queue.add(root);
		while (!queue.isEmpty()) {
			TreeNode tempNode = queue.poll();
			System.out.println(tempNode.val + " ");

			if (tempNode.left != null) {
				queue.add(tempNode.left);
			}
			if (tempNode.right != null) {
				queue.add(tempNode.right);
			}
		}
	}

	private void printLevelOrder(TreeNode root) {
		// Breadth First Traversal (Level Order traversal)
		int height = getHeight(root);
		for (int level = 1; level <= height; level++) {
			printGivenLevel(root, level);
		}
	}

	private void printGivenLevel(TreeNode root, int level) {
		// TODO Auto-generated method stub
		if (root == null) {
			System.out.print("null,");
			return;
		}
		if (level == 1)
			System.out.print(root.val+",");
		else if (level > 1) {
			printGivenLevel(root.left, level - 1);
			printGivenLevel(root.right, level - 1);
		}
	}

	class Height {
		int h;
	}

	private int diameter() {
		// Possible to find diameter using DFS
		Height height = new Height();
		return diameterOpt(root, height);
	}

	private int getMaxWidth() {
		// Using level order traversing, Get Maximum with using Queue , Time complexity
		// is O(n)
		TreeNode currentNode = root;
		Queue<TreeNode> q = new LinkedList<TreeNode>();
		int maxWidth = 0;
		q.add(currentNode);

		while (!q.isEmpty()) {
			int count = q.size();
			maxWidth = Math.max(maxWidth, count);

			while (count-- > 0) {
				TreeNode temp = q.remove();
				if (temp.left != null) {
					q.add(temp.left);
				}
				if (temp.right != null) {
					q.add(temp.right);
				}
			}
		}
		return maxWidth;
	}

	private int[] calDiameter(TreeNode root) {
		// Time complexity O(n)
		int[] result = new int[] { 0/* Diameter */, 0/* Height */ };
		if (root == null) {
			return result;
		}
		int[] leftResult = calDiameter(root.left);
		int[] rightResult = calDiameter(root.right);

		int height = Math.max(leftResult[1], rightResult[1]) + 1;
		int rootDiameter = leftResult[1] + rightResult[1] + 1;
		int leftDiameter = leftResult[0];
		int rightDiameter = rightResult[0];

		result[0] = Math.max(rootDiameter, Math.max(leftDiameter, rightDiameter));
		result[1] = height;
		return result;
	}

	private int diameterOpt(TreeNode root, Height height) {
		// Time complexity O(n)
		Height lHeight = new Height();
		Height rHeight = new Height();
		if (root == null) {
			height.h = 0;
			return 0;
		}
		int lDiameter = diameterOpt(root.left, lHeight);
		int rDiameter = diameterOpt(root.right, rHeight);

		height.h = Math.max(lHeight.h, rHeight.h) + 1;

		return Math.max(lHeight.h + rHeight.h + 1, Math.max(lDiameter, rDiameter));
	}

	private int getDiameter(TreeNode root) {
		// Time complexity O(n^2)
		if (root == null) {
			return 0;
		}
		int height = 1 + getHeight(root.left) + getHeight(root.right);

		int lDiameter = getDiameter(root.left);
		int rDiameter = getDiameter(root.right);

		return Math.max(height, Math.max(lDiameter, rDiameter));
	}

	private int getHeight(TreeNode root) {
		if (root == null)
			return 0;
		int lHeight = 1 + getHeight(root.left);
		int rHeight = 1 + getHeight(root.right);
		return Math.max(lHeight, rHeight);
		// return lHeight > rHeight ? lHeight : rHeight;
	}

	private void createSmallTree() {
		root = new TreeNode(1);
		root.left = new TreeNode(2);
		root.left.left = new TreeNode(-3);
		root.left.right = new TreeNode(-7);

		root.right = new TreeNode(5);
		root.right.right = new TreeNode(3);
	}

	private void maxSumInPath() {
		// TODO Auto-generated method stub
		System.out.println("Max sum possible: " + maxPathSum(root, new sum()));
	}

	class sum {
		int value = 0;
	}

	private int maxPathSum(TreeNode root, sum sum) {
		if (root == null) {
			sum.value = 0;
			return 0;
		}
		sum leftSum = new sum();
		sum rightSum = new sum();

		// sum.value += root.value;
		System.out.println("Node value: " + root.val);
		leftSum.value += maxPathSum(root.left, leftSum);
		rightSum.value += maxPathSum(root.left, rightSum);

		sum.value += Math.max(leftSum.value, rightSum.value) + root.val;

		// System.out.println("Sum val: " + sum.value + " leftSum: " + leftSum + "
		// rightSum: " + rightSum);
		return sum.value;
	}

	// https://leetcode.com/contest/weekly-contest-132/problems/recover-a-tree-from-preorder-traversal/
	public void recoverFromPreorder() {
		String S = "1-2--3---4-5--6---7";// 1-2--3---4-5--6---7, 1-2--3--4-5--6--7, 1-401--349---90--88
		int level, val;
		Stack<TreeNode> stack = new Stack<>();
		for (int i = 0; i < S.length();) {
			for (level = 0; S.charAt(i) == '-'; i++) {
				level++;
			}
			for (val = 0; i < S.length() && S.charAt(i) != '-'; i++) {
				val = val * 10 + (S.charAt(i) - '0');
			}
			while (stack.size() > level) {
				stack.pop();
			}

			TreeNode node = new TreeNode(val);
			if (!stack.isEmpty()) {
				if (stack.peek().left == null) {
					stack.peek().left = node;
				} else {
					stack.peek().right = node;
				}
			}
			stack.add(node);
		}
		while (stack.size() > 1) {
			stack.pop();
		}
		
		printLevelOrder(stack.pop());
	}
	
	Set<Integer> mSet = new HashSet<>();
    public int findSecondMinimumValue(TreeNode root) {
        preOrderTraversal(root);
        
        if(mSet.isEmpty()) {
        	return -1;
        }
        
        int []setArr = new int[mSet.size()];
        Iterator<Integer> iterator = mSet.iterator();
        
        int index = 0;

        while(iterator.hasNext()) {
        	setArr[index] = iterator.next();
        	index++;
        }
        
        int min = setArr[0];
        
        int secondMin = min;
        
        for(int i = 1; i < setArr.length; i++) {
        	if(setArr[i] < min) {
        		secondMin = min;
        		min = setArr[i];
        	}
        }
        return secondMin;
        
    }
    
    public void preOrderTraversal(TreeNode root){
        if(root == null){
            return;
        }
        
        preOrderTraversal(root.left);
        mSet.add(root.val);
        preOrderTraversal(root.right);
    }
    
    static class Solution {
        public boolean hasPathSum(TreeNode root, int sum) {
           return pathSumRec(root,sum);
        }
        
        public boolean pathSumRec(TreeNode root, int actualSum) {
           if(root == null){
                return false;
           }
            
           if(root.left == null && root.right == null && actualSum - root.val == 0) return true;
                
           return pathSumRec(root.left,actualSum - root.val) || pathSumRec(root.right,actualSum - root.val); 
        }
    }
    
    //https://leetcode.com/problems/binary-tree-inorder-traversal/
    
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> mStack = new Stack<>();
        
        List<Integer> mList = new ArrayList<>();
        TreeNode cur = root;
        
        while(cur != null || !mStack.isEmpty()) {
        	while(cur != null) {
        		mStack.push(cur);
        		cur = cur.left;
        	}
        	
        	cur = mStack.pop();
        	mList.add(cur.val);
        	System.out.print(cur.val + "\t");
        	cur = cur.right;
        }
        
        return mList;
    }
    
    //https://leetcode.com/problems/validate-binary-search-tree/
    //https://leetcode.com/problems/validate-binary-search-tree/discuss/32112/Learn-one-iterative-inorder-traversal-apply-it-to-multiple-tree-questions-(Java-Solution)
    /*   5
   		/ \
  	   1   4
     	  / \
    	 3   6

	Input: [5,1,4,null,null,3,6]
	Output: false
     
     * */
    List<Integer> mSortedList = new ArrayList<>();
    
    public boolean isValidBST(TreeNode root) {
    	inorderRec(root);
    	for(int i = 0; i < mSortedList.size() -1; i++) {
    		if(mSortedList.get(i) > mSortedList.get(i+1)) {
    			return false;
    		}
    	}
    	return true;
    }
    
    public void inorderRec(TreeNode root) {
    	if(root == null) {
    		return;
    	}
    	
    	inorderRec(root.left);
    	
    	System.out.println(root.val);
    	mSortedList.add(root.val);
    	
    	inorderRec(root.right);
    }
    
    //https://leetcode.com/problems/binary-tree-preorder-traversal/
    List<Integer> mPreorderedList = new ArrayList<>();
    
    public List<Integer> preOrderIterative(TreeNode root) {
    	if(root == null) {
    		return mPreorderedList;
    	}
    	Stack<TreeNode> mStack = new Stack<>();
    	TreeNode cur = root;
    	mStack.push(cur);
    	
    	while(!mStack.isEmpty()) {
    		cur = mStack.pop();
    		System.out.print(cur.val + "\t");
    		mPreorderedList.add(cur.val);
    		
    		if(cur.right != null) {
    			mStack.push(cur.right);
    		}
    		if(cur.left != null) {
    			mStack.push(cur.left);
    		}
    		
    	}
    	return mPreorderedList;
    }
    
    //https://leetcode.com/problems/merge-two-binary-trees/
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
    	 if (t1 == null && t2 == null) return null;
         
         int val = (t1 == null ? 0 : t1.val) + (t2 == null ? 0 : t2.val);
         TreeNode newNode = new TreeNode(val);
         
         newNode.left = mergeTrees(t1 == null ? null : t1.left, t2 == null ? null : t2.left);
         newNode.right = mergeTrees(t1 == null ? null : t1.right, t2 == null ? null : t2.right);
         
         return newNode;
    }
    
    //https://leetcode.com/problems/invert-binary-tree/
    public TreeNode invertTree(TreeNode tree) {
    	/*
    	 *    4
   			/   \
  		   2     7
 		  / \   / \
		 1   3 6   9

			 4
   		   /   \
  		  7     2
 		 / \   / \
		9   6 3   1
		
    	 */
    	Queue<TreeNode> mQueue = new LinkedList<>();
    	
    	mQueue.offer(tree);
    	while(!mQueue.isEmpty()) {
    		TreeNode thisNode = mQueue.poll();
    		System.out.println(thisNode.val);
    		TreeNode left = thisNode.left;
    		
    		thisNode.left = thisNode.right;
    		thisNode.right = left;
    		
    		if(thisNode.left != null) {
    			mQueue.offer(thisNode.left);
    		}
    		
    		if(thisNode.right != null) {
    			mQueue.offer(thisNode.right);
    		}
    		
    	}
    	return tree;
    }
    
    //https://www.geeksforgeeks.org/print-leaf-nodes-left-right-binary-tree/
    public void printFringes(TreeNode root) {
    	if(root == null) {
    		return;
    	}
    	if(root.left == null && root.right == null) {
    		System.out.print(root.val + " ");
    		return;
    	}
    	
    	printFringes(root.left);
    	printFringes(root.right);
    }
    
    //https://www.geeksforgeeks.org/print-all-leaf-nodes-of-a-binary-tree-from-left-to-right-set-2-iterative-approach/
    public void compareFringes() {
    	
    	TreeNode t1 = new TreeNode(5);
    	t1.left = new TreeNode(4);
    	t1.left.left = new TreeNode(1);
    	t1.left.right = new TreeNode(2);
    	
    	t1.right = new TreeNode(3);
    	t1.right.left = new TreeNode(6);
    	t1.right.right = new TreeNode(9);
    	
    	
    	TreeNode t2 = new TreeNode(7);
    	t2.left = new TreeNode(8);
    	t2.left.left = new TreeNode(1);
    	t2.left.right = new TreeNode(11);
    	t2.left.right.left = new TreeNode(2);
    	
    	t2.right = new TreeNode(3);
    	t2.right.left = new TreeNode(6);
    	t2.right.right = new TreeNode(9);

    	
    	CompareFringes fringes = new CompareFringes(t1, t2);
    	while(fringes.tree1hasNext() && fringes.tree2hasNext()) {
    		int leaf1 = fringes.getLeafNodeTree1();
    		int leaf2 = fringes.getLeafNodeTree2();
    		System.out.println(leaf1 + " : " + leaf2);
    		if(leaf1 != leaf2) {
    			return;
    		}
    	}
    }
    
    class CompareFringes {
    	Stack<TreeNode> mStack1 = new Stack<>();
    	Stack<TreeNode> mStack2 = new Stack<>();
    	TreeNode tree1;
    	TreeNode tree2;
    	
    	public CompareFringes(TreeNode t1, TreeNode t2) {
    		mStack1.push(t1);
    		mStack2.push(t2);
    		tree1 = t1;
    		tree2 = t2;
    	}
    	
		public int getLeafNodeTree1() {
			while (!mStack1.isEmpty()) {

				TreeNode curr = mStack1.pop();

				if (curr.right != null) {
					mStack1.push(curr.right);
				}

				if (curr.left != null) {
					mStack1.push(curr.left);
				}
				if (curr.left == null && curr.right == null) {
					return curr.val;
				}
			}
			return -1;
		}
        
        public int getLeafNodeTree2() {
        	while(!mStack2.isEmpty()) {
        		
        		TreeNode curr = mStack2.pop();
        		
        		if(curr.right != null) {
        			mStack2.push(curr.right);	
        		}
        		
        		if(curr.left != null) {
        			mStack2.push(curr.left);	
        		}
        		if(curr.left == null && curr.right == null) {
        			return curr.val;
        		}
        	}
        	return -1;
        }
        
        public boolean tree1hasNext() {
        	return !mStack1.isEmpty();
        }
        
        public boolean tree2hasNext() {
        	return !mStack2.isEmpty();
        }
    }
    
    
}
