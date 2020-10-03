package com.pkumar7.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class AugustWeek1 {

     public static void main(String args[]){

        //movesToMakeZigzag(new int[]{1,2,3});
         //intersect(new int[]{8,3,9,5,4},new int[]{9,4,9,8,4});
         //longestPalindrome();
         //moveZeroes(new int[]{1});
         sumRange(0,2);
     }

    /***
     *https://leetcode.com/problems/elimination-game/
     */
    public int lastRemaining(int n) {
        String binaryStr = Integer.toBinaryString(n);
        char ch = binaryStr.charAt(0);
        StringBuilder builder = new StringBuilder(binaryStr);
        builder.deleteCharAt(0);
        builder.append(ch);

        return Integer.parseInt(builder.toString(),2);
    }

    /**
     * https://leetcode.com/problems/min-stack/
     */

    /**
     * Goldman sachs
     */
    public static int findDamagedToy(int N, int T, int D) {
        int[] arr = new int[N];
        for (int i = 0; i < N; i++) {
            arr[i] = i+1;
        }
        if(D > N){
            return 0;
        }
        leftRotate(arr, D - 1, N);

        for (int i = 0; i < N; i++) {
            System.out.print(arr[i] +"\t");
        }

        if(T > N){
            return arr[0];
        }
        return arr[T-1];
    }

    static void leftRotate(int arr[], int d, int n)  {
        int i, j, k, temp;
        int g_c_d = gcd(d, n);
        for (i = 0; i < g_c_d; i++) {
            temp = arr[i];
            j = i;
            while (true) {
                k = j + d;
                if (k >= n)
                    k = k - n;
                if (k == i) {
                    break;
                }
                arr[j] = arr[k];
                j = k;
            }
            arr[j] = temp;
        }
    }

    static int gcd(int a, int b) {
        if (b == 0) {
            return a;
        } else {
            return gcd(b, a % b);
        }
    }

    static int countConnections(List<List<Integer>> matrix) {

        int[][] array = new int[matrix.size()][];
        for (int i = 0; i < array.length; i++) {
            array[i] = new int[matrix.get(i).size()];
        }
        for(int i=0; i<matrix.size(); i++){
            for (int j = 0; j < matrix.get(i).size(); j++) {
                array[i][j] = matrix.get(i).get(j);
            }
        }

        int rows = array.length;
        int cols = array[0].length;
        System.out.println(rows + " : " + cols);

        int[] x_move = new int[] {1,1,1,0};
        int[] y_move = new int[] {0,1,-1,-1};

        int distinct_connections = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int val = array[i][j];
                System.out.println("Val " + val + " for: " + i + "," + j);
                if(val == 1){
                    for (int k = 0; k < x_move.length; k++) {
                        int next_x = x_move[k]+i;
                        int next_y = y_move[k]+j;
                        //System.out.println("next_x " + next_x + " next_y: " + next_y );
                        if(next_x >= 0 && next_x < rows && next_y >= 0 && next_y < cols &&
                                matrix.get(next_x).get(next_y) == 1){
                            distinct_connections++;
                        }
                    }
                }
            }
        }

        System.out.println(distinct_connections);
        return distinct_connections;
    }

    

    /**
     * https://leetcode.com/problems/unique-email-addresses/
     * @param emails
     * @return
     */
    public int numUniqueEmails(String[] emails) {
        Set<String> seen = new HashSet<>();
        for (int i = 0; i < emails.length; i++) {
            String email = emails[i];
            String localname = email.split("@")[0];
            String domain = email.split("@")[1];
            if(localname.contains("+")) {
                localname = localname.substring(0, localname.indexOf('+') );
            }
            localname = localname.replaceAll("\\.","");
            System.out.println(localname+"@"+domain);
            seen.add(localname+"@"+domain);
        }
        return seen.size();
    }

    /**
     * 1122. Relative Sort Array
     * https://leetcode.com/problems/relative-sort-array/
     */

    /**
     * 965. Univalued Binary Tree
     * https://leetcode.com/problems/univalued-binary-tree/
     * A binary tree is univalued if every node in the tree has the same value.
     *
     * Return true if and only if the given tree is univalued.
     *
     * */

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean isUnivalTree(TreeNode root) {
        return isUnivalTree(root, root.val);
    }

    public boolean isUnivalTree(TreeNode root, int val) {
        if(root == null ){
            return true;
        }
        if(val != root.val){
            return false;
        }

        return isUnivalTree(root.left, val) && isUnivalTree(root.right, val);
    }

    /**
     * https://leetcode.com/problems/reverse-words-in-a-string/
     * 151. Reverse Words in a String
     *
     * Input: "the sky is blue"
     * Output: "blue is sky the"
     *
     * Input: "  hello world!  "
     * Output: "world! hello"
     * */
    public String reverseWords(String s) {
        String[] parts = s.trim().split("\\s+");
        String result ="";

        for (int i = parts.length - 1; i >= 0; i++) {
            result += parts[i] + " ";
        }
        return result.trim();
    }

    /**
     * 1144. Decrease Elements To Make Array Zigzag
     * Input: nums = [1,2,3]
     *               [1,2,1]
     *
     * Output: 2
     * Explanation: We can decrease 2 to 0 or 3 to 1.
     */
    public static int movesToMakeZigzag(int[] nums) {
        int[] org_arr = Arrays.copyOf(nums , nums.length);
        boolean flag = true;
        int minimum_moves = 0;

        int temp =0;

        for (int i = 0; i <= nums.length - 2; i++) {
            if (flag)  {
                if (nums[i] > nums[i + 1]) {
                    temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                }

            } else {
                if (nums[i] < nums[i + 1]) {
                    // swap
                    temp = nums[i];
                    nums[i] = nums[i + 1];
                    nums[i + 1] = temp;
                }
            }
            flag = !flag;
        }

        for (int i = 0; i < org_arr.length; i++) {
            System.out.println(org_arr[i] + " " + nums[i]);
            if(org_arr[i] != nums[i]){
                minimum_moves += Math.abs(org_arr[i] - nums[i]);
            }
        }


        System.out.println("minimum_moves" + minimum_moves);

        return minimum_moves;
    }

    /**
     * 409. Longest Palindrome
     * https://leetcode.com/problems/longest-palindrome/
     * "abccccdd"
     *
     * */
    public static int longestPalindrome() {
        String s = "abccccdd";
        Set<Character> mSet = new HashSet<>();
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            if(mSet.contains(s.charAt(i))){
                mSet.remove(s.charAt(i));
                count++;
            }else{
                mSet.add(s.charAt(i));
            }
        }
        if(!mSet.isEmpty()) return count*2 + 1;
        return count*2;
       /* Map<Character, Integer> mMap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            mMap.put(s.charAt(i), mMap.getOrDefault(s.charAt(i),0)+1);
        }

        Set<Character> keys = mMap.keySet();
        Iterator<Character> mIt = keys.iterator();
        int palindromeLength = 0;
        boolean addOddOnce = false;
        while (mIt.hasNext()){
            Character ch = mIt.next();
            int freq = mMap.get(ch);
            if(freq % 2 == 0){
                palindromeLength += freq;
            }else if(freq > 1){
                palindromeLength += freq - 1;
            }else if(!addOddOnce && palindromeLength%2 == 0){
                palindromeLength ++;
                addOddOnce = true;
            }
        }
        System.out.println(palindromeLength);
        return palindromeLength;*/
    }

    /**
     * https://leetcode.com/problems/intersection-of-two-arrays-ii/
     * Input: nums1 = [1,2,2,1], nums2 = [2,2]
     * Output: [2,2]
     *
     * Input: nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * Output: [4,9]
     *
     * 350. Intersection of Two Arrays II
     */
    public static int[] intersect(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> map1 = new HashMap<>();
        for (int i = 0; i < nums1.length; i++) {
            int ele = nums1[i];
            map1.put(ele, map1.getOrDefault(ele,0) + 1);
        }

        HashMap<Integer, Integer> map2 = new HashMap<>();
        for (int i = 0; i < nums2.length; i++) {
            int ele = nums2[i];
            map2.put(ele, map2.getOrDefault(ele,0) + 1);
        }

        Set<Integer> keys = map1.keySet();
        Iterator<Integer> mIt = keys.iterator();
        List<Integer> mList = new ArrayList<>();
        while (mIt.hasNext()){
            int key = mIt.next();
            if(map1.get(key) == null)continue;
            int freq1 = map1.get(key);
            if(freq1 > 0){
                if(map2.get(key) == null)continue;
                int freq2 = map2.get(key);
                if(freq2 > 0){
                    System.out.println("freq1 " + freq1 + " freq2 " + freq2 + " key " + key);
                    int final_fre = freq1 < freq2 ? freq1 : freq2;
                    while (final_fre > 0){
                        mList.add(key);
                        final_fre--;
                    }
                }
            }
        }

        int[] result = new int[mList.size()];
        for (int i = 0; i < mList.size(); i++) {
            System.out.print(mList.get(i)+"\t");
            result[i] = mList.get(i);
        }
        return result;
    }

    /**
     * 283. Move Zeroes
     */
    public static void moveZeroes(int[] nums) {
        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            while (j < nums.length && nums[j] == 0){
                j++;
            }
            if(j >= nums.length){
                break;
            }
            int temp = nums[i];
            nums[i] = nums[j];
            nums[j] = temp;
            j++;
        }

        for (int i = 0; i < nums.length; i++) {
            System.out.print(nums[i]+"\t");
        }
    }

    /**
    * 73. Set Matrix Zeroes
    * https://leetcode.com/problems/set-matrix-zeroes/
    */

    /** 561. Array Partition I
     * https://leetcode.com/problems/array-partition-i/
     * */
    public int arrayPairSum(int[] nums) {
        if(nums.length %2 != 0){
            return 0;
        }
        Arrays.sort(nums);
        int sum = 0;
        for (int i = 0; i < nums.length; i+=2) {
            sum += Math.min(nums[i],nums[i+1]);
        }
        return sum;
        /*PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for (int i = 0; i < nums.length; i++) {
            minHeap.offer(nums[i]);
        }
        int sum = 0;
        while (!minHeap.isEmpty()){
            int a = minHeap.poll();
            int b = minHeap.poll();
            sum += Math.min(a,b);
        }
        return sum;*/
    }

    /**
     * 1122. Relative Sort Array
     * https://www.geeksforgeeks.org/counting-sort/
     * Input: arr1 = [2,3,1,3,2,4,6,7,9,2,19], arr2 = [2,1,4,3,9,6]
     * Output: [2,2,2,1,4,3,3,9,6,7,19]
     * */
    public int[] relativeSortArray(int[] arr1, int[] arr2) {
        int []cnt = new int[1001];
        for (int n : arr1) {
            cnt[n]++;
        }
        int i = 0;
        for (int n : arr2) {
            while (cnt[n]-- > 0){
                arr1[i++] = n;
            }
        }
        for (int j = 0; j < cnt.length; j++) {
            while (cnt[j]-- > 0){
                arr1[i++] = j;
            }
        }
        return arr1;
    }

    /**
     * https://leetcode.com/problems/range-sum-query-immutable/
     * 303. Range Sum Query - Immutable
     * */
    static int []arr = new int[]{-2, 0, 3, -5, 2, -1};

    public static int sumRange(int i, int j) {
        int sum = 0;
        if(i < 0 || j > arr.length){
            return sum;
        }

        for (; i <= j ; i++) {
            sum += arr[i];
        }
        System.out.print("Sum: --> " + sum);
        return sum;
    }

}
