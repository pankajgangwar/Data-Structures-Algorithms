package com.mission.google.datastructures;

/**
 * Created by Pankaj Kumar on 19/September/2020
 */

import java.util.Random;

/**
 * Implements a treap.
 * Note that all "matching" is based on the compareTo method.
 * https://users.cs.fiu.edu/~weiss/dsaajava2/code/Treap.java
 *
 */
public class Treap<T extends Comparable<? super T>> {
    /**
     * Construct the treap.
     */
    public Treap() {
        nullNode = new TreapNode<T>(null);
        nullNode.left = nullNode.right = nullNode;
        nullNode.priority = Integer.MAX_VALUE;
        root = nullNode;
    }

    /**
     * Insert into the tree. Does nothing if x is already present.
     *
     * @param x the item to insert.
     */
    public void insert(T x) {
        root = insert(x, root);
    }

    /**
     * Remove from the tree. Does nothing if x is not found.
     *
     * @param x the item to remove.
     */
    public void remove(T x) {
        root = remove(x, root);
    }

    /**
     * Find the smallest item in the tree.
     *
     * @return the smallest item, or throw UnderflowException if empty.
     */
    public T findMin() {
        if (isEmpty())
            throw new RuntimeException("Underflow");

        TreapNode<T> ptr = root;

        while (ptr.left != nullNode)
            ptr = ptr.left;

        return ptr.data;
    }

    /**
     * Find the largest item in the tree.
     *
     * @return the largest item, or throw UnderflowException if empty.
     */
    public T findMax() {
        if (isEmpty())
            throw new RuntimeException("Underflow");

        TreapNode<T> ptr = root;

        while (ptr.right != nullNode)
            ptr = ptr.right;

        return ptr.data;
    }

    /**
     * Find an item in the tree.
     *
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean search(T x) {
        TreapNode<T> current = root;
        nullNode.data = x;

        for (; ; ) {
            int compareResult = x.compareTo(current.data);

            if (compareResult < 0)
                current = current.left;
            else if (compareResult > 0)
                current = current.right;
            else
                return current != nullNode;
        }
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty() {
        root = nullNode;
    }

    /**
     * Test if the tree is logically empty.
     *
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty() {
        return root == nullNode;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public StringBuilder toString(TreapNode curr) {
        StringBuilder builder = new StringBuilder();
        if(curr == null) return builder;
        builder.append(curr.data);
        if(curr.left != nullNode){
            builder.append("/");
            builder.append(toString(curr.left));
        }
        if(curr.right != nullNode){
            builder.append("\\");
            builder.append(toString(curr.right));
        }
        return builder;
    }

    public void traversePreOrder(StringBuilder sb, String padding, String pointer, TreapNode node) {
        if (node != nullNode) {
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.data);
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.right != nullNode) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.left);
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.right);
        }
    }

    public void printTree() {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", this.root);
        System.out.println(" " + sb.toString() );
    }

    /**
     * Internal method to insert into a subtree.
     *
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private TreapNode<T> insert(T x, TreapNode<T> t) {
        if (t == nullNode)
            return new TreapNode<T>(x, nullNode, nullNode);

        int compareResult = x.compareTo(t.data);

        if (compareResult < 0) {//If key is smaller than root
            t.left = insert(x, t.left); // Insert in left subtree
            //If left child has more priority than root, right rotate the tree
            if (t.left.priority > t.priority)
                t = rightRotate(t);
        } else if (compareResult > 0) {
            t.right = insert(x, t.right);
            //If right child has more priority than root, left rotate the tree
            if (t.right.priority < t.priority)
                t = leftRotate(t);
        }
        // Otherwise, it's a duplicate; do nothing
        return t;
    }

    /**
     * Internal method to remove from a subtree.
     *
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private TreapNode<T> remove(T x, TreapNode<T> t) {
        if (t != nullNode) {
            int compareResult = x.compareTo(t.data);

            if (compareResult < 0)
                t.left = remove(x, t.left);
            else if (compareResult > 0)
                t.right = remove(x, t.right);
            else {
                // Match found
                if (t.left.priority < t.right.priority)
                    t = rightRotate(t);
                else
                    t = leftRotate(t);

                if (t != nullNode)     // Continue on down
                    t = remove(x, t);
                else
                    t.left = nullNode;  // At a leaf
            }
        }
        return t;
    }

    /**
     * Internal method to print a subtree in sorted order.
     *
     * @param t the node that roots the tree.
     */
    private void printTree(TreapNode<T> t) {
        if (t != t.left) {
            printTree(t.left);
            System.out.println(t.data.toString());
            printTree(t.right);
        }
    }

    /**
     * Rotate binary tree node with left child.
     */
    private TreapNode<T> rightRotate(TreapNode<T> k2) {
        TreapNode<T> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     */
    private TreapNode<T> leftRotate(TreapNode<T> k1) {
        TreapNode<T> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        return k2;
    }

    private static class TreapNode<T> {
        // Constructors
        TreapNode(T data) {
            this(data, null, null);
        }

        TreapNode(T data, TreapNode<T> lt, TreapNode<T> rt) {
            this.data = data;
            left = lt;
            right = rt;
            priority = randomObj.nextInt();
        }

        // Friendly data; accessible by other package routines
        T data;      // The data in the node
        TreapNode<T> left;         // Left child
        TreapNode<T> right;        // Right child
        int priority;     // Priority

        private static Random randomObj = new Random();

    }

    private TreapNode<T> root;
    private TreapNode<T> nullNode;


    // Test program
    public static void main(String[] args) {
        Treap<Integer> t = new Treap<Integer>();
        final int n = 10;

        System.out.println("Checking... (no bad output means success)");

        for (int i = 0; i < n; i++) {
            t.insert(i);
        }

        System.out.println("Inserts complete");

        t.remove(3);
        System.out.println("Removing 3");
        t.remove(4);
        System.out.println("Removing 4");
        t.remove(5);
        System.out.println("Removing 5");

        t.printTree();
        int min = t.findMin();
        int max = t.findMax();
        System.out.println("max " + max + " min " + min);

        if(t.search(4)){
            System.out.println("Element 4 exists in the tree ");
        }else{
            System.out.println("Element 4 not found");
        }
        if(t.search(2)){
            System.out.println("Element 2 exists in the tree ");
        }
    }
}
