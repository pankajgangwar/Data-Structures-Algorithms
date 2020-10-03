package com.pkumar7.datastructures;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class BinarySearchTree {
	
	public static void main(String[] args) {
		BinarySearchTree tree = new BinarySearchTree();
		
		int[] binaryTree = new int[]{20,30,5,25,2,7};
		
		for(int i=0;i<binaryTree.length;i++){
			tree.insert(binaryTree[i]);
		}
		
		//tree.printSumOfVerticals();
		
		/*int pre[] = new int[]{40,30,35,80,100};
		
		if(tree.canRepresentBST(pre)){
			System.out.println("********* CAN represent *********");
		}else{
			System.out.println("********* CAN NOT represent ********* ");
		}*/
		
		//tree.reverseInorder();
	}
	
	
	private void reverseInorder() {
		// TODO Auto-generated method stub
		Sum summ = new Sum();
		reverseInorderRec(root, summ);
		printInorder(root);
	}

	static class TreeNode{
		TreeNode left = null;
		TreeNode right = null;
		int val;
		public TreeNode(int value){
			val = value;
		}
	}
	TreeNode root;
	
	void insert(int key)
    {
        root = insertRec(root, key);
    }
	
	/* A recursive function to insert a new key in BST */
    TreeNode insertRec(TreeNode root, int key)
    {
 
        /* If the tree is empty, return a new node */
        if (root == null)
        {
            root = new TreeNode(key);
            return root;
        }
 
        /* Otherwise, recur down the tree */
        if (key < root.val)
            root.left = insertRec(root.left, key);
        else if (key > root.val)
            root.right = insertRec(root.right, key);
 
        /* return the (unchanged) node pointer */
        return root;
    }
    
    void delete(int key){
    	root = deleteRec(root, key);
    }
    
    /* A recursive function to insert a new key in BST */
    TreeNode deleteRec(TreeNode root, int key)
    {
        /* Base Case: If the tree is empty */
        if (root == null)  return root;
 
        /* Otherwise, recur down the tree */
        if (key < root.val)
            root.left = deleteRec(root.left, key);
        else if (key > root.val)
            root.right = deleteRec(root.right, key);
 
        // if key is same as root's key, then This is the node
        // to be deleted
        else
        {
            // node with only one child or no child
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;
 
            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            System.out.println("Node with 2 children");
            root.val = minValue(root.right);
 
            // Delete the inorder successor
            root.right = deleteRec(root.right, root.val);
        }
 
        return root;
    }
    
    int minValue(TreeNode root){
        int minv = root.val;
        while (root.left != null){
            minv = root.left.val;
            root = root.left;
        }
        return minv;
    }
    
 // This method mainly calls InorderRec()
    void inorder(){
    	System.out.println("############ Inorder ################");
        inorderRec(root);
    }
    
    void inorderWithKey(){
    	System.out.println("############ Inorder WITH KEYS ################");
    	Queue<Integer> mList = new LinkedList<>();
        inorderWithKeys(root,mList);
       
        for (Iterator iterator = mList.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			System.out.println("Value: " + integer);
		}
        
        preOrderMinHeap(root, mList);
        
        printInorder(root);
    }
    
 // A utility function to do inorder traversal of BST
    void inorderRec(TreeNode root){
        if (root != null){
            inorderRec(root.left);
            System.out.print(root.val + " ");
            inorderRec(root.right);
        }
    }
    
    //Use inorder traversal to get list in increasing order
    void inorderWithKeys(TreeNode root,Queue<Integer> mList){
    	if(root == null)
    		return;
    	
    	inorderWithKeys(root.left, mList);
    	System.out.println("Adding: " + root.val + " queue size: " + mList.size());
    	mList.add(root.val);
    	inorderWithKeys(root.right, mList);
    }
    
    //Use preorder traversal to create min heap
    void preOrderMinHeap(TreeNode root,Queue<Integer> list){
    	if(root == null || list.isEmpty())
    		return;
    	
    	System.out.println("Queue first: " + list.peek() + " size: " + list.size());
    	root.val = list.poll();
    	preOrderMinHeap(root.left, list);
    	preOrderMinHeap(root.right, list);
    }
    
    class Index {
        int index = 0;
    }
    
    Index index = new Index();
    
 // The main function to construct BST from given preorder traversal.
    // This function mainly uses constructTreeUtil()
    TreeNode constructTree(int pre[], int size) {
        return constructTreeUtil(pre, index, pre[0], Integer.MIN_VALUE,
                Integer.MAX_VALUE, size);
    }
    
    void constructTree(){
    	 int pre[] = new int[]{10, 5, 1, 7, 40, 50};
         int size = pre.length;
         TreeNode root = constructTree(pre, size);
         printInorder(root);
    }
    void printInorder(TreeNode root){
    	System.out.println("\n######## INORDER ################");
    	inorderRec(root);
    }
    
    TreeNode constructTreeUtil(int pre[], Index preIndex, int key,
            int min, int max, int size) {
 
        // Base case
        if (preIndex.index >= size) {
            return null;
        }
 
        TreeNode root = null;
 
        // If current element of pre[] is in range, then
        // only it is part of current subtree
        if (key > min && key < max) {
 
            // Allocate memory for root of this subtree and increment *preIndex
            root = new TreeNode(key);
            preIndex.index = preIndex.index + 1;
 
            if (preIndex.index < size) {
 
                // Contruct the subtree under root
                // All nodes which are in range {min .. key} will go in left
                // subtree, and first such node will be root of left subtree.
                root.left = constructTreeUtil(pre, preIndex, pre[preIndex.index],
                        min, key, size);
                
                // All nodes which are in range {key..max} will go in right
                // subtree, and first such node will be root of right subtree.
                root.right = constructTreeUtil(pre, preIndex, pre[preIndex.index],
                        key, max, size);
            }
        }
 
        return root;
    }
    
    class Sum {
    	int sum=0;
    }
    
    //Convert a BST to a Binary Tree such that sum of all greater keys is added to every key
    private void reverseInorderRec(TreeNode root,Sum sum){
        if (root == null)
        	return;
        
        reverseInorderRec(root.right,sum);
        sum.sum += root.val;
        root.val = sum.sum;
        reverseInorderRec(root.left,sum);
    }
    
    
    //Check if given array can represent preOrder traversal of BST
    private boolean canRepresentBST(int []pre){
    	int root = Integer.MIN_VALUE;
    	Stack<Integer> mStack = new Stack<>();
    	for (int i = 0; i < pre.length; i++) {
			
    		if(pre[i] < root){
    			return false;
    		}
    		
    		while(!mStack.isEmpty() && mStack.peek() < pre[i]){
    			root = mStack.pop();
    		}
    		mStack.push(pre[i]);
		}
    	return true;
    }
    
    //https://www.geeksforgeeks.org/print-binary-tree-vertical-order/
    private void printNodesVertical(){
    	
    	Values vMin = new Values();
    	Values vMax= new Values();
    	
    	findMinMax(root, vMin, vMax, 0);
    	
    	System.out.println("min: " + vMin.min + " max: " + vMax.max);
    	
    	for(int i=vMin.min;i<=vMax.max;i++){
    		TreeNode temp = root;
    		printVerticals(temp,i,0);
    		System.out.println();
    	}
    }
    
    class Values {
    	int min=0;
    	int max=0;
    }
    
    private void findMinMax(TreeNode root, Values vMin,Values vMax,int hd/*Horizontal distance*/){
    	
    	if(root == null){
    		return;
    	}
    	
    	if(hd > vMax.max){
    		vMax.max = hd;
    	}else if(hd < vMin.min){
    		vMin.min = hd;
    	}
    	
    	findMinMax(root.left, vMin, vMax, hd-1);
    	findMinMax(root.right, vMin, vMax, hd+1);
    }
    
    private void printVerticals(TreeNode root,int line_no,int hd){
    	if(root == null){
    		return;
    	}
    	if(line_no == hd){
    		System.out.print(root.val + " ");
    	}
    	printVerticals(root.left, line_no, hd-1);
    	printVerticals(root.right, line_no, hd+1);
    }
    
    //https://www.geeksforgeeks.org/vertical-sum-in-a-given-binary-tree/
    private void printSumOfVerticals(){
    	HashMap<Integer, Integer> mMap = new HashMap<>();
    	printVerticalsSum(root, mMap, 0);
    	System.out.println(mMap.entrySet());
    }
    
    void printVerticalsSum(TreeNode root,HashMap<Integer, Integer> mMap,int hd){
    	if(root == null){
    		return;
    	}
    	printVerticalsSum(root.left, mMap, hd-1);
    	
    	int previousSum = mMap.get(hd) == null ? 0 : mMap.get(hd);
    	mMap.put(hd, previousSum + root.val);
    	
    	printVerticalsSum(root.right, mMap, hd+1);
    }
    
    //https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/
    //Morris algorithm - for pictures and explanation in details please refer to here.
    //https://leetcode.com/problems/binary-search-tree-to-greater-sum-tree/discuss/286906/Java-3-iterative-and-recursive-codes-w-comments-and-explanation.
    class Solution {
        List<Integer> mSortedList = new ArrayList<>();
        
        public TreeNode bstToGst(TreeNode root) {
            sortIncreasing(root);
            TreeNode result = toGst(root);
            return result;
        }
        
        public TreeNode toGst(TreeNode root){
            if(root == null){
                return null;
            }
            toGst(root.left);
            root.val = getSum(root.val);
            toGst(root.right);
            
            return root;
        }
        
        public int getSum(int key){
            int i = mSortedList.indexOf(key);
            int sum = 0;
            for(; i < mSortedList.size(); i++){
                sum+= mSortedList.get(i);
            }
            return sum;
        }
        
        public void sortIncreasing(TreeNode root){
            if(root == null){
                return;
            }
            sortIncreasing(root.left);
            System.out.println("sortIncreasing " + root.val);
            mSortedList.add(root.val);
            sortIncreasing(root.right);
        }
    }
    
    
}
