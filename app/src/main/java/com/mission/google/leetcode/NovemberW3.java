package com.mission.google.leetcode;
import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
import java.util.function.BiFunction;

public class NovemberW3 {
    
    public static void main(String[] args){
        NovemberW3 w3 = new NovemberW3();
        int sum = w3.maxSumDivThree(new int[]{3,6,5,1,8});
        System.out.println("Map: " + sum);
    }

    /* 1049. Last Stone Weight II
    https://leetcode.com/problems/last-stone-weight-ii/
        Re-visit , to-do, Important
    */
     public int lastStoneWeightII(int[] stones) {
        return lastStoneWeightRec(stones, 0, 0);
     }

     
     int[][] dp = new int[30][6000];
     public int lastStoneWeightRec(int[] nums, int pos, int sum){
        if(nums.length == pos){
            return sum < 0 ? 100 : sum;
        }
        if(dp[pos][sum + 3000] == 0){
            dp[pos][sum + 3000] = 1 + Math.min(lastStoneWeightRec(nums, pos + 1, sum + nums[pos]), 
                 lastStoneWeightRec(nums, pos + 1, sum - nums[pos]));
        }
        return dp[pos][sum + 3000] - 1;
     }

    /* 210. Course Schedule II
        https://leetcode.com/problems/course-schedule-ii/

        Input: 4, [[1,0],[2,0],[3,1],[3,2]]
        Output: [0,1,2,3] or [0,2,1,3]
        Explanation: There are a total of 4 courses to take. To take course 3 you should have finished both     
             courses 1 and 2. Both courses 1 and 2 should be taken after you finished course 0. 
             So one correct course order is [0,1,2,3]. Another correct ordering is [0,2,1,3] .
    
    */

    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Graph graph = buildGraphOfCourse(numCourses, prerequisites);

        Course[] courseOrder = checkCourseOrder(graph.getTreeNodes());

        int[] courseArr = new int[courseOrder.length];
        int n = courseOrder.length -1;
        for (int i = courseOrder.length -1 ; i >= 0;  --i ) {
            courseArr[n - i] = courseOrder[i].getCourseNumber();
        }
        return courseArr;
    }

    public Course[] checkCourseOrder(List<Course> courseList){
        Course[] order = new Course[courseList.size()];

        int endOfList = addNonDependentCourse(order, courseList, 0);
        int toBeProcessed = 0;

        while(toBeProcessed < order.length){
            Course course = order[toBeProcessed];
            if(course == null){
                return new Course[0];
            }
            ArrayList<Course> children = course.getChildren();
            for(Course child : children){
                child.decrementDependency();
            }
            
            endOfList = addNonDependentCourse(order, courseList, endOfList);
            toBeProcessed++;
        }
        return order;
    }

    public int addNonDependentCourse(Course[] order, List<Course> courseList, int offset){
        for(Course course : courseList){
            if(course.getNumberOfDependencies() == 0){
                if(offset == courseList.size()){
                    return offset;
                }
                if(!course.isVisited()){
                    course.setVisited();
                    order[offset] = course;
                    ++offset;
                }
            }
        }
        return offset;
    }

    public Graph buildGraphOfCourse(int numCourses, int[][] prerequisites){
        Graph graph = new Graph();
        for (int i = 0; i < numCourses; ++i ) {
            graph.getOrCreateCourse(i);
        }

        for(int[] pre : prerequisites){
            graph.addEgde(pre[0], pre[1]);
        }

        return graph;
    }

    class Graph{
        ArrayList<Course> TreeNodes = new ArrayList<>();
        HashMap<Integer, Course> map = new HashMap<>();

        public Course getOrCreateCourse(int course){
            if(map.containsKey(course)){
                return map.get(course);
            }else{
                Course courseObj = new Course(course);
                map.put(course, courseObj);
                TreeNodes.add(courseObj);
                return courseObj;
            }
        }

        public void addEgde(int src, int dst){
            Course first = getOrCreateCourse(src);
            Course second = getOrCreateCourse(dst);
            first.addNeighbour(second);
        }

        public List<Course> getTreeNodes(){
            return TreeNodes;
        }
    }

    class Course {
        int dependencies;
        ArrayList<Course> list = new ArrayList<>();
        Map<Integer, Course> map = new HashMap<>();
        int courseNumber;
        boolean isVisited;

        public Course(int course){
            courseNumber = course;
        }

        public void addNeighbour(Course child){
            if(!map.containsKey(child.getCourseNumber())){
                map.put(child.getCourseNumber(), child);
                list.add(child);
                child.incrementDependency();
            }
        }

        public ArrayList<Course> getChildren(){
            return list;
        }

        public void setVisited(){
            isVisited = true;
        }
        public boolean isVisited(){
            return isVisited;
        }

        public int getCourseNumber(){
            return courseNumber;
        }

        public int getNumberOfDependencies(){
            return dependencies;
        }

        public void incrementDependency(){
            dependencies++;
        }
        public void decrementDependency(){
            dependencies--;
        }
    }

    /*
    https://leetcode.com/problems/unique-binary-search-trees-ii/
    */
    public List<TreeNode> generateTrees(int n) {
        return generateTreesRec(1, n);
    }

    public List<TreeNode> generateTreesRec(int start, int end){
        List<TreeNode> list = new ArrayList<>();
        if(start > end){
            list.add(null);
        }
        List<TreeNode> lList, rList;
        for(int idx = start; idx <= end; idx++){
            lList = generateTreesRec(start, idx -1);
            rList = generateTreesRec(idx + 1, end);

            for(TreeNode lTreeNode : lList){
                for(TreeNode rTreeNode : rList){
                    TreeNode root = new TreeNode(idx);
                    root.left = lTreeNode;
                    root.right = rTreeNode;
                    list.add(root);
                }
            }
        }
        return list;
    }


    /*
        https://leetcode.com/problems/remove-element/ 

        27. Remove Element

        [3,2,2,3] : 3
        [0,1,2,2,3,0,4,2] : 2
    */
    public int removeElement(int[] nums, int val) {
        int i = 0;

        for(int k = 0; k < nums.length; k++){
            if(nums[k] != val){
                nums[i++] = nums[k];
            }
        }
        return i;

        /*if(nums.length == 0) return 0;
        int i = 0;
        int n = nums.length;
        while(i < n){
            if(nums[i] == val){
                nums[i] = nums[n -1];
                n--;
            }else{
                i++;
            }
        }
        return n;*/
    }
    
    /*  
        Maximum rain water trapped
    */
    public int trap(int[] height) {
        int[] lMax = new int[height.length];
        int[] rMax = new int[height.length];

        int n = height.length;
        if(n == 0){
            return 0;
        }

        lMax[0] = height[0];

        for(int i = 1; i < n; i++){
            lMax[i] = Math.max(lMax[i-1], height[i]);
        }

        rMax[n-1] = height[n-1];

        for (int i = n-2; i >= 0 ; --i) {
            rMax[i] = Math.max(rMax[i+1], height[i]);
        }

        int max_water_trapped = 0;
        for(int i = 0; i < n; i++){
            int min_water = Math.min(rMax[i], lMax[i]);

            if(min_water > height[i]){
                max_water_trapped += min_water - height[i];
            }
        }
        return max_water_trapped;
    }

    /* Tree traversal spiral order using Stack*/

    void printSpiral(TreeNode TreeNode) {
           // Your code here
           Stack<TreeNode> s1 = new Stack<>();
           Stack<TreeNode> s2 = new Stack<>();
           
           s1.push(TreeNode);
           
           List<Integer> list = new ArrayList<>();
           
           while(!s1.isEmpty() || !s2.isEmpty()){
               while(!s1.isEmpty()){
                   TreeNode curr = s1.pop();
                   list.add(curr.val);
                   if(curr.right != null){
                       s2.push(curr.right);
                   }
                   if(curr.left != null){
                       s2.push(curr.left);
                   }
               }
               
               while(!s2.isEmpty()){
                   TreeNode curr = s2.pop();
                   list.add(curr.val);
                   if(curr.left != null){
                       s1.push(curr.left);
                   }
                   if(curr.right != null){
                       s1.push(curr.right);
                   }
               }
           }
           
           for(int ele : list){
               System.out.print(ele + " ");
           }
      }

      public void printSpiralWithDeque(TreeNode root){
          Deque<TreeNode> deque = new LinkedList<>();
          if(root == null){
              return;
          }
          deque.offer(null);
          deque.offerFirst(root);

          while(deque.size() > 1){
              deque.peekFirst();

            }

          String str = String.valueOf(10);
       }

       /*Input:
        regions = [["Earth","North America","South America"],
        ["North America","United States","Canada"],
        ["United States","New York","Boston"],
        ["Canada","Ontario","Quebec"],
        ["South America","Brazil"]],
        region1 = "Quebec",
        region2 = "New York"
        Output: "North America"

        https://leetcode.com/problems/smallest-common-region/

        */
       public String findSmallestRegion(List<List<String>> regions, String region1, String region2) {
            Map<String,String> map = new HashMap<String,String>();
            Set<String> ancestors = new HashSet<>();

            for(List<String> region : regions){
                for(int i = 1; i < region.size(); ++i){
                    map.put(region.get(i),region.get(0));
                }
            }

            while(region1 != null){
                ancestors.add(region1);
                region1 = map.get(region1);
            }

            while(!ancestors.contains(region2)){
                region2 = map.get(region2);
            }
            return region2;
        }

        /*
         Input:
        synonyms = [["happy","joy"],["sad","sorrow"],["joy","cheerful"]],
        text = "I am happy today but was sad yesterday"
        Output:
        ["I am cheerful today but was sad yesterday",
        ​"I am cheerful today but was sorrow yesterday",
        "I am happy today but was sad yesterday",
        "I am happy today but was sorrow yesterday",
        "I am joy today but was sad yesterday",
        "I am joy today but was sorrow yesterday"]

        https://leetcode.com/problems/synonymous-sentences/

         */

        public List<String> generateSentences(List<List<String>> synonyms, String text) {
            Queue<String> bfs = new LinkedList<>();
            bfs.offer(text);
            Set<String> result = new HashSet<>();

            result.add(text);

            while(!bfs.isEmpty()){
                String curr = bfs.poll();
                String[] arr = curr.split(" ");

                for (int i = 0; i < arr.length; i++) {
                    String word = arr[i];
                    for (List<String> syn : synonyms) {
                        if (word.equals(syn.get(0))) {
                            arr[i] = syn.get(1);
                        }else if(word.equals(syn.get(1))){
                            arr[i] = syn.get(0);
                        }
                        StringBuilder builder = new StringBuilder();
                        for (String newStr : arr) {
                            builder.append(newStr);
                            builder.append(" ");
                        }
                        String finalAns = builder.toString().trim();
                        if(result.add(finalAns)){
                            bfs.offer(finalAns);
                        }
                    }
                    arr[i] = word;
                }
            }

            ArrayList<String> finalRes = new ArrayList<>(result);

            Collections.sort(finalRes);
            return finalRes;
        }

    /*
    https://leetcode.com/problems/greatest-sum-divisible-by-three/
    Revisit, Important
    https://leetcode.com/problems/greatest-sum-divisible-by-three/discuss/431095/JAVA-C%2B%2B-O(N)-Time-O(1)-Space-using-DP
    https://stackoverflow.com/questions/13511885/finding-greatest-sum-of-elements-of-array-which-is-divisible-by-a-given-number

    Recurrance relation: dp[i][k] = max(dp[i+1][0], dp[i+1][(k - A[i]) % 3]
    
    */
     public int maxSumDivThree(int[] nums) {
        int[] dp = new int[3];
        dp[1] = dp[2] = Integer.MIN_VALUE;

        for(int ele : nums){
            int[] dpNext = new int[3];
            dpNext[0] = Math.max(dp[ele%3] + ele , dp[0]);
            dpNext[1] = Math.max(dp[(ele+1)%3] + ele , dp[1]);
            dpNext[2] = Math.max(dp[(ele+2)%3] + ele , dp[2]);
            dp = dpNext;
        }
        return dp[0];
    }

    public int maxSumRec(int[] nums, int sum, int n, int k){
        int max_sum = 0;
        if(n == 0){
            return 0;
        }
        int curr_sum = 0;
        if((nums[n] + sum) % k == 0){
            curr_sum = maxSumRec(nums, sum + nums[n], n-1, k);
        }
        max_sum = Math.max(curr_sum, maxSumRec(nums, sum, n-1, k));

        return max_sum;
    }

    /*https://leetcode.com/problems/greatest-sum-divisible-by-three/discuss/431108/Java-O(N)-solution-Simple-Math-O(1)-space*/
    public int maxSumDivK(int[] nums, int k){
        if(k==0) return -1;
        int[] dp = new int[k];
        Arrays.fill(dp,Integer.MIN_VALUE);
        dp[0] = 0;
        for(int num : nums){
            int[] tmp = new int[k];
            for(int i=0;i<k;i++){
                tmp[(num+i)%k] = Math.max(dp[(num+i)%k],num+dp[i]);
            }
            dp = tmp;
        }
        return dp[0];
    }

    /*
        https://leetcode.com/problems/find-elements-in-a-contaminated-binary-tree/
     */
    class FindElements {
        Set<Integer> treeSet = new HashSet<>();
        TreeNode mRoot;
        public FindElements(TreeNode root) {
            mRoot = root;
            recover(root);
        }

        public void recover(TreeNode root){
            Queue<TreeNode> q = new LinkedList<>();
            root.val = 0;
            q.offer(root);
            treeSet.add(0);

            while(!q.isEmpty()){
                int size = q.size();
                while(size-- > 0){
                    TreeNode curr = q.poll();
                    int x = curr.val;
                    if(curr.left != null){
                        curr.left.val = 2*x + 1;
                        q.offer(curr.left);
                        treeSet.add(2*x + 1);
                    }

                    if(curr.right != null){
                        curr.right.val = 2*x + 2;
                        q.offer(curr.right);
                        treeSet.add(2*x + 2);
                    }
                }
            }

        }

        public boolean find(int target) {
            return treeSet.contains(target);
        }

        public boolean findRec(TreeNode root, int target){
            if(root == null){
                return false;
            }
            if(root.val == target){
                return true;
            }
            return findRec(root.left, target) || findRec(root.right, target);
        }
    }
     

     /* 
        https://leetcode.com/problems/recover-binary-search-tree/
        Recover Binary Search Tree, Morris Inorder Traversal, correct the swapped nodes
        https://leetcode.com/problems/recover-binary-search-tree/discuss/32559/Detail-Explain-about-How-Morris-Traversal-Finds-two-Incorrect-Pointer
     */
    public void recoverTree(TreeNode root) {
        fixSwappedNodes(root);
    }

    public void fixSwappedNodes(TreeNode root){
        TreeNode curr = null,prev = null, first = null,second = null;
        while(root != null){
            if(root.left != null){
                //Connect threadings for root 
                curr = root.left;

                while(curr.right != null && curr.right != root){
                    curr = curr.right;
                }

                if(curr.right != null){ //Threading already established
                    if(prev != null && prev.val > root.val){
                        if(first == null){
                            first = prev;
                        }
                        second = root;
                    }
                    prev = root;

                    curr.right = null;
                    //System.out.println(curr.val);
                    root = root.right;
                }else{
                    //Construct the threading
                    curr.right = root;
                    root = root.left;
                }
            }else{
                if(prev != null && prev.val > root.val){
                        if(first == null){
                            first = prev;
                        }
                        second = root;
                }
                //System.out.println(curr.val);
                prev = root;

                root = root.right;
            }
        }
        if(first != null && second != null){
            int t = first.val;
            first.val =second.val;
            second.val = t;
        }
    }

   /* https://leetcode.com/problems/binary-tree-inorder-traversal/*/
    public void morrisTraversal(TreeNode root){
        TreeNode curr = null;
        while(root != null){
            
            if(root.left != null){//Connect threadings for root
                curr = root.left;
                while(curr.right != null && curr.right != root){
                    curr = curr.right;
                }
                if(curr.right != null){ //Threading already established
                    curr.right = null;
                    System.out.println(root.val);
                    root = root.right;  
                }else{
                    //Construct the threading
                    curr.right = root;
                    root = root.left;
                }
            }else{
                System.out.println(root.val);
                root = root.right;
            }
        }
    }


    /* 
    String[] arr = {};
    */
    public void groupAnagramsWithCount(List<String> list){
        Map<String, List<String>> map = new HashMap<>();
        for(String word : list){
            int[] count = new int[26];
            Arrays.fill(count, 0);

            char[] arr = word.toCharArray();
            
            for(int idx = 0; idx < 26; idx++){
                count[arr[idx] - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for(int idx = 0; idx < 26; idx++){
                builder.append(count[idx]);
                builder.append("#");
            }
            String key = builder.toString();
            if(!map.containsKey(key)){
                List<String> anagramList = new ArrayList<>();
                anagramList.add(word);
                map.put(key, anagramList);
            }else{
                List<String> storedList = map.get(key);
                storedList.add(word);
                Collections.sort(storedList);
                map.put(key, storedList);
            }
        }
    }

    /* https://leetcode.com/problems/permutations-ii/
        https://github.com/mission-peace/interview/blob/master/src/com/interview/recursion/StringPermutation.java
        47. Permutations II
    */
    public List<List<Integer>> permuteUnique(int[] nums) {
        Map<Integer, Integer> freq_map = new HashMap<>();
        int n = nums.length;

        for(int ele : nums){
            freq_map.compute(ele, (key,val) -> {
                if(val ==  null){
                    return 1;
                }else{
                    return val + 1;
                }
            });
        }

        int[] freq = new int[freq_map.size()];
        int[] eles = new int[freq_map.size()];
        int idx = 0;
        for (Map.Entry<Integer, Integer> entry : freq_map.entrySet()) {
            freq[idx] = entry.getValue();
            eles[idx] = entry.getKey();
            idx++;
        }

        permUtil(eles, freq, n, new ArrayList<>(), 0);
        return all_permutations;
    }

    List<List<Integer>> all_permutations = new ArrayList<>();
    public void permUtil(int[] eles, int[] freq, int n, List<Integer> result, int level){
        if(level == n) {
            all_permutations.add(new ArrayList<>(result));
            return;
        }
        for(int idx = 0; idx < eles.length; idx++) {
            if(freq[idx] == 0) continue;
            result.add(eles[idx]);
            freq[idx]--;
            permUtil(eles, freq, n, result, level + 1);
            result.remove(result.size() -1);
            freq[idx]++;
        }
    }

    /* https://leetcode.com/problems/swap-nodes-in-pairs/
    Given 1->2->3->4, you should return the list as 2->1->4->3.
    https://leetcode.com/problems/swap-nodes-in-pairs/discuss/11046/My-simple-JAVA-solution-for-share
    */
    public ListNode swapPairs(ListNode head) {
        //return swapRecursion(head);
        return swapRecEasy(head);
    }

    public ListNode swapIterative(ListNode head){
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        ListNode point = dummy;
        ListNode swap1 = null;
        ListNode swap2 = null;

        while(point.next != null && point.next.next != null){
            swap1 = point.next;
            swap2 = point.next.next;

            point.next = swap2;
            swap1.next = swap2.next;
            swap2.next = swap1;
            point = swap1;
        }
        return dummy.next;
    }

    public ListNode swapRecEasy(ListNode head){
        if(head == null || head.next == null){
            return head;
        }
        ListNode n = head.next;
        head.next = swapRecEasy(head.next.next);
        n.next = head;
        return n;
    }

    public ListNode swapRecursion(ListNode head){
        if(head == null || head.next == null)
            return head;

        ListNode remaining = head.next.next;

        ListNode newhead = head.next;
        
        head.next.next = head;
        
        head.next = swapRecursion(remaining);
        
        return newhead;
    }

    /*
    https://leetcode.com/problems/next-greater-element-i/

    Input: nums1 = [4,1,2], nums2 = [1,3,4,2].
    Output: [-1,3,-1]

    */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        Stack<Integer> stack = new Stack<>();
        Map<Integer, Integer> map = new HashMap<>();

        for(int ele : nums2){
            while(!stack.isEmpty() && stack.peek() < ele)
                map.put(stack.pop(), ele);
            stack.push(ele);
        }

        for(int i = 0; i < nums1.length; i++){
            int ele = nums1[i];
            nums1[i] = map.getOrDefault(ele, -1);
        }

        return nums1;
    }

    /*
        https://leetcode.com/problems/unique-number-of-occurrences/
        1207. Unique Number of Occurrences
    */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer,Integer> freq_map = new HashMap<>();
        for(int i = 0 ; i < arr.length; i++){
            int ele = arr[i];
            freq_map.put(ele, freq_map.getOrDefault(ele, 0) + 1);
        }

        return freq_map.size() == new HashSet<>(freq_map.values()).size();
    }

    /* https://leetcode.com/problems/uncommon-words-from-two-sentences/
    884. Uncommon Words from Two Sentences
    Input: A = "this apple is sweet", B = "this apple is sour"
    Output: ["sweet","sour"]

    */
    public String[] uncommonFromSentences(String str1, String str2) {

        String[] str1Arr = str1.split("\\s+");
        String[] str2Arr = str2.split("\\s+");

        Map<String,Integer> freq_map = new HashMap<>();

        for(String word : str1Arr){
            freq_map.put(word, freq_map.getOrDefault(word, 0) + 1);
        }

        List<String> result = new ArrayList<>();
        for(String word : str2Arr){
            freq_map.put(word, freq_map.getOrDefault(word, 0) + 1);
        }

        String[] result_arr = new String[result.size()];
        for(Map.Entry<String,Integer> entry : freq_map.entrySet()){
            if(entry.getValue() == 1){
                result.add(entry.getKey());
            }
        }

        return result.stream().toArray(String[]::new);
    }

    /*https://leetcode.com/problems/grumpy-bookstore-owner/
    Input: customers = [1,0,1,2,1,1,7,5], grumpy = [0,1,0,1,0,1,0,1], X = 3
    Output: 16
    */
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        int max_satisfied = 0;
        int already_satisfied = 0;
        int making_cust_satisfied = 0;
        for(int i = 0; i < grumpy.length; i++){
            if(grumpy[i] == 0){
                already_satisfied += customers[i];
            }else{
                making_cust_satisfied  += customers[i];
            }
            if(i >= X && grumpy[i - X] == 1){
                making_cust_satisfied  -= customers[i - X];
            }
            max_satisfied = Math.max(max_satisfied, making_cust_satisfied );
        }
        return max_satisfied + already_satisfied;
    }

    
    /*
        https://leetcode.com/problems/maximum-binary-tree/

        Input: [3,2,1,6,0,5]
        Output: return the tree root node representing the following tree:

              6
            /   \
           3     5
            \    / 
             2  0   
               \
                1
    */ 
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return constructRec(nums, 0, nums.length );
    }

    public TreeNode constructUsingStack(int[] nums){
        Deque<TreeNode> stack = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            while(!stack.isEmpty() && stack.peek().val < nums[i]){
                curr.left = stack.pop();
            }
            if(!stack.isEmpty()){
                stack.peek().right = curr;
            }
            stack.push(curr);
        }
        return stack.isEmpty() ? null : stack.removeLast();
    }

    public TreeNode constructRec(int[] nums, int left, int right){
        if(left >= right){
            return null;
        }
        int max = Integer.MIN_VALUE;
        int max_idx = -1;
        for(int idx = left; idx < right; idx++){
            if(nums[idx] > max){
                max = nums[idx];
                max_idx = idx;
            }
        }

        TreeNode root = new TreeNode(max);
        root.left = constructRec(nums, left, max_idx -1);
        root.right = constructRec(nums, max_idx + 1, right);
        return root;
    }

    /* https://leetcode.com/problems/construct-binary-search-tree-from-preorder-traversal/
    Input: [8,5,1,7,10,12]
    Output: [8,5,10,1,7,null,12]
    */
    public TreeNode bstFromPreorder(int[] preorder) {
        return formBstStack(preorder);
    }

    public TreeNode formBstStack(int[] preOrder){
        Stack<TreeNode> stack = new Stack<>();
        TreeNode root = new TreeNode(preOrder[0]);
        stack.push(root);
        for(int idx = 1; idx < preOrder.length; idx++){
            int curr_ele = preOrder[idx];
            TreeNode curr_node = new TreeNode(preOrder[idx]);
            if(!stack.isEmpty() && curr_ele < stack.peek().val){
                stack.peek().left = curr_node;
            }else{
                TreeNode parent = stack.peek();
                while(!stack.isEmpty() && curr_ele > stack.peek().val){
                    parent = stack.pop();
                }
                parent.right = curr_node;
            }
            stack.push(curr_node);
        }
        return root;
    }
    
    /*
    Input: [8,5,1,7,10,12]
    Output: [8,5,10,1,7,null,12]
    */
    int idx = 0;
    public TreeNode formBstFromRec(int[] preOrder, int bound){
        if(idx == preOrder.length || preOrder[idx] > bound ){
            return null;
        }
        TreeNode curr = new TreeNode(preOrder[idx++]);
        curr.left = formBstFromRec(preOrder, curr.val);
        curr.right = formBstFromRec(preOrder, bound);
        return curr;
    }

    /* https://leetcode.com/problems/first-bad-version/ */
    public int firstBadVersion(int n) {
        int l = 1;
        int r = n;
        
        while(l < r){
            int mid = l + (r - l) /2;
            if(!isBadVersion(mid)){
                l = mid + 1;
            }else{
                r = mid;
            }
        }
        return l;
    }

    public boolean isBadVersion(int n){
        if(n >= 4){
            return true;
        }
        return false;
    }

    /*https://leetcode.com/problems/longest-repeating-character-replacement
    https://leetcode.com/problems/longest-repeating-character-replacement/discuss/91285/Sliding-window-similar-to-finding-longest-substring-with-k-distinct-characters
    424. Longest Repeating Character Replacement
    */

    public int characterReplacement(String s, int k) {
        int maxCharCount = 0;
        int start = 0;
        int end = 0;
        int[] count = new int[26];
        int n = s.length();
        int result = 0;

        for(end = 0; end < n; end++){
            count[s.charAt(end) - 'A']++;
            if(maxCharCount < count[s.charAt(end) - 'A']){
                maxCharCount = count[s.charAt(end) - 'A'];
            }

            while(end - start + 1 - maxCharCount > k){
                count[s.charAt(start) - 'A']--;
                start++;
                for(int i = 0; i < 26; i++){
                    if(maxCharCount < count[i]){
                        maxCharCount = count[i];
                    }
                }
            }

            result = Math.max(result, end - start + 1);
        }
        return result;
    }
}