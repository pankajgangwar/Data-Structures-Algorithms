package com.pkumar7.trees;

import com.pkumar7.TreeNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class MinTimeToBurnAllNodes {

    public static void main(String args[] ) throws Exception {
        /*
         * Minimum time to burn all nodes of the tree
         * */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        int elementsCount = Integer.parseInt(bufferedReader.readLine().trim());
        HashMap<Integer, TreeNode> map = new HashMap<>();
        TreeNode root = null;
        for (int i = 1; i <= elementsCount - 1; i++) {
            String pair = bufferedReader.readLine();
            int u = Integer.parseInt(pair.split(" ")[0]);
            int v = Integer.parseInt(pair.split(" ")[1]);
            if(i == 1){
                root = new TreeNode(u);
                root.left = new TreeNode(v);
                map.put(u, root);
                map.put(v, root.left);
            }else{
                TreeNode node = map.getOrDefault(u, null);
                TreeNode newNode = new TreeNode(v);
                if(node != null){
                    if(node.left == null){
                        node.left = newNode;
                    }else if(node.right == null){
                        node.right = newNode;
                    }
                    map.put(v, newNode);
                }
            }
        }
        int start = Integer.parseInt(bufferedReader.readLine().trim());
        HashMap<TreeNode, TreeNode> parentMap = new HashMap<>();
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()){
            TreeNode curr = q.poll();
            if(curr.left != null){
                parentMap.put(curr.left, curr);
                q.offer(curr.left);
            }
            if(curr.right != null){
                parentMap.put(curr.right, curr);
                q.offer(curr.right);
            }
        }

        Queue<TreeNode> burningQ = new LinkedList<>();
        TreeNode startNode = map.get(start);
        burningQ.offer(startNode);

        HashMap<TreeNode, Integer> visited = new HashMap<>();
        visited.put(startNode, 1);

        int minimumTime = 0;
        while (!burningQ.isEmpty()){
            int size = burningQ.size();
            int currTime = 0;
            while (size-- > 0){
                TreeNode curr = burningQ.poll();
                if(curr.left != null && visited.get(curr.left) == null){
                    visited.put(curr.left, 1);
                    burningQ.offer(curr.left);
                    currTime = 1;
                }
                if(curr.right != null && visited.get(curr.right) == null){
                    visited.put(curr.right, 1);
                    burningQ.offer(curr.right);
                    currTime = 1;
                }
                if(parentMap.get(curr) != null && visited.get(parentMap.get(curr)) == null){
                    visited.put(parentMap.get(curr), 1);
                    burningQ.offer(parentMap.get(curr));
                    currTime = 1;
                }
            }
            if(currTime == 1){
                minimumTime += 1;
            }
        }

        //out.write("" + minimumTime);
        System.out.println(minimumTime);

        bufferedReader.close();
        out.flush();
        out.close();
    }
}
