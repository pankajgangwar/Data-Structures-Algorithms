package com.pkumar7.datastructures;

public class RopeDataStructure {

    int LEAF_LENGTH = 2;
    class Rope {
        Rope left, right, parent;
        String str;
        int weight;
    }

    public Rope createRopeStructure(Rope parent, String str, int l, int r){
        Rope tmp = new Rope();
        tmp.left = tmp.right = null;
        tmp.parent = parent;

        if(r-l > LEAF_LENGTH){
            tmp.str = null;
            tmp.weight = (r - l) / 2;
            int m = (l + r) / 2;
            tmp.left = createRopeStructure(tmp, str, l, m);
            tmp.right = createRopeStructure(tmp, str, m + 1, r);
        }else{
            tmp.weight = (r - l);
            StringBuilder builder = new StringBuilder();
            for (int i = l; i <= r ; i++) {
                builder.append(str.charAt(i));
            }
            tmp.str = builder.toString();
        }
        return tmp;
    }

    public Rope concatenate(Rope root1, Rope root2){
        Rope root = new Rope();
        root.parent = null;
        root.left = root1;
        root.right = root2;
        root1.parent = root2.parent = root;
        root.weight = root1.weight + root2.weight;
        root.str = null;
        return root;
    }

    public void ropeDriverCode(){

        String str = "Hi This is geeksforgeeks";
        Rope root1 = createRopeStructure(null, str, 0, str.length() - 1);

        String str2 = "You are welcome here";
        Rope root2 = createRopeStructure(null, str2, 0, str2.length() - 1);

        Rope root3 = concatenate(root1, root2);

        printString(root3);
    }

    private void printString(Rope node) {
        if(node == null) return;
        if(node.left == null && node.right == null)
            System.out.print(node.str);
        printString(node.left);
        printString(node.right);
    }

    public static void main(String[] args) {
        RopeDataStructure ropeDataStructure = new RopeDataStructure();
        ropeDataStructure.ropeDriverCode();
    }
}
