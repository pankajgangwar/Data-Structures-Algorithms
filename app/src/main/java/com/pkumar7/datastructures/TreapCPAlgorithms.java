package com.pkumar7.datastructures;

/**
 * Created by Pankaj Kumar on 18/September/2020
 */
class TreapCPAlgorithms {
    /*
    Treap (Cartesian tree)
    https://cp-algorithms.com/data_structures/treap.html
    */
    class TreapNode {
        int key, priority;
        TreapNode left, right;

        public TreapNode(int key, int priority) {
            this.key = key;
            this.priority = priority;
            left = right = null;
        }
    }

    public void split(TreapNode root, int key, TreapNode left, TreapNode right) {
        if (root == null) {
            left = right = null;
        } else if (key < root.key) {
            split(root.left, key, left, root.left);
            right = root;
        } else {
            split(root.right, key, root.right, right);
            left = root;
        }
    }

    public TreapNode insert(TreapNode root, TreapNode newNode) {
        if (root == null) {
            root = newNode;
        } else if (root.priority < newNode.priority) {
            split(root, newNode.key, newNode.left, newNode.right);
            root = newNode;
        } else {
            root = insert(newNode.key < root.key ? root.left : root.right, newNode);
        }
        return root;
    }

    public TreapNode merge(TreapNode root, TreapNode left, TreapNode right) {
        if (left == null || right == null) {
            root = left != null ? left : right;
        } else if (left.priority > right.priority) {
            root = merge(left.right, left.right, right);
        } else {
            root = merge(right.left, left, right.left);
        }
        return root;
    }

    public void erase(TreapNode root, int key) {
        if (root.key == key) {
            merge(root, root.left, root.right);
        } else {
            erase(key < root.key ? root.left : root.right, key);
        }
    }

    public TreapNode union(TreapNode left, TreapNode right) {
        if (left == null || right == null) {
            return left != null ? left : right;
        }
        if (left.priority < right.priority) {
            swap(left, right);
        }
        split(right, left.key, left, right);
        left.left = union(left.left, left);
        left.right = union(left.right, right);
        return left;
    }

    private void swap(TreapNode left, TreapNode right) {
        TreapNode temp = left;
        left = right;
        right = temp;
    }

}
