package com.mission.google.leetcode;

import com.mission.google.TreeNode;
import com.mission.google.datastructures.ListNode;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.IntConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FebruaryW2 {

    public static boolean checkIfExist(int[] arr) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < arr.length; i++) {
            map.putIfAbsent(arr[i], new ArrayList<>());
            map.get(arr[i]).add(i);
        }

        for(int i = 0; i < arr.length; i++){
            int curr = arr[i] * 2;
            if(map.containsKey(curr)){
                List<Integer> indices = map.get(curr);
                for (int j = 0; j < indices.size(); j++) {
                    if(indices.get(j) != i){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int maxStudents(char[][] seats) {
        int colWise = 0;
        int m = seats.length;
        int n = seats[0].length;
        char[][] copy = new char[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = seats[i][j];
            }
        }

        for (int j = 0; j < seats[0].length; j++) {
            for (int i = 0; i < seats.length; i++) {
                if(seats[i][j] == '.'){
                    if(ifSafeToPlace(seats, i, j, m, n)){
                        colWise++;
                        seats[i][j] = 's';
                    }
                }
            }
        }

        int rowWise = 0;
        for (int i = 0; i < seats.length; i++) {
            for (int j = 0; j < seats[0].length; j++) {
                if(copy[i][j] == '.'){
                    if(ifSafeToPlace(copy, i, j, m, n)){
                        rowWise++;
                        copy[i][j] = 's';
                    }
                }
            }
        }
        return Math.max(rowWise, colWise);
    }

    private boolean ifSafeToPlace(char[][] seats, int i, int j, int m, int n) {
        int[][] dirs = new int[][]{ {0,1},{0,-1},{-1,1},{1,-1}, {1,1} ,{-1,-1} };
        for (int k = 0; k < dirs.length; k++) {
            int next_x = dirs[k][0] + i;
            int next_y = dirs[k][1] + j;
            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n){
                if(seats[next_x][next_y] == 's'){
                    return false;
                }
            }
        }
        return true;
    }

    public int minSteps(String s, String t) {
        return countManipulations(s, t);
    }

    int countManipulations(String s1, String s2) {
        int count = 0;

        // store the count of character
        int char_count[] = new int[26];

        // iterate though the first String and update
        // count
        for (int i = 0; i < s1.length(); i++)
            char_count[s1.charAt(i) - 'a']++;

        // iterate through the second string
        // update char_count.
        // if character is not found in char_count
        // then increase count
        for (int i = 0; i < s2.length(); i++)
            if (char_count[s2.charAt(i) - 'a']-- <= 0)
                count++;

        return count;
    }

    /* https://leetcode.com/problems/find-all-duplicates-in-an-array/ */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> res = new ArrayList<>();
        
        for(int i = 0 ; i < nums.length; i++){
            int idx = Math.abs(nums[i]);
            if(nums[idx-1] > 0){
                nums[idx-1] = -nums[idx-1];
            }else{
                res.add(Math.abs(nums[i]));
            }
        }
        
        return res;
    }


    public int solve(ArrayList<Integer> A, int k) {
        int[] arr = new int[A.size()];
        int n = arr.length;
        for (int i = 0; i < A.size(); i++) {
            arr[i] = A.get(i);
        }

        if (n == 1)
            return 0;

        // Sort all elements
        Arrays.sort(arr);

        // Initialize result
        int ans = arr[n - 1] - arr[0];

        // Handle corner elements
        int small = arr[0] + k;
        int big = arr[n - 1] - k;
        int temp = 0;

        if (small > big) {
            temp = small;
            small = big;
            big = temp;
        }

        // Traverse middle elements
        for (int i = 1; i < n - 1; i++) {
            int subtract = arr[i] - k;
            int add = arr[i] + k;

            if (subtract >= small || add <= big)
                continue;


            if (big - subtract <= add - small)
                small = subtract;
            else
                big = add;
        }

        return Math.min(ans, big - small);
    }


    /* https://leetcode.com/problems/triangle/ */
    public int minimumTotal(List<List<Integer>> triangle){
        int n = triangle.size();
        int[] minlen = new int[n];

        for(int i = 0; i < triangle.get(n-1).size(); i++){
            minlen[i] = triangle.get(n-1).get(i);
        }

        for(int layer = n - 2 ; layer >= 0; layer--){
            for(int i = 0; i <= layer; i++){
                minlen[i] = Math.min(minlen[i], minlen[i+1]) + triangle.get(layer).get(i);
            }
        }
        return minlen[0];
    }


    public int minimumTotalI(List<List<Integer>> triangle) {
        int rows = triangle.size();
        int cols = triangle.size();

        int[][] matrix = new int[rows][cols];

        int zero = 0;

        for (int i = rows -1; i >= 0 ; --i) {
            List<Integer> list = triangle.get(i);
            int idx = 0;
            for (int j = zero; j < cols; j++) {
                int x = list.get(idx++);
                matrix[i][j] = x;
            }
            zero++;
        }

        int[][] dirs = new int[][] { {1,-1}, {1, 0} , {1,1}};
        int curr_x = 0, curr_y = 0;
        int minSum = 0;
        for(int i = 0; i < matrix[0].length; i++){
            if(matrix[0][i] != 0){
                curr_x = 0;
                curr_y = i;
                minSum += matrix[0][i];
            }
        }

        Queue<int[]> q = new LinkedList<>();
        
        q.offer(new int[]{curr_x, curr_y});
        
        while(!q.isEmpty()){
            int[] curr = q.poll();
            int min = Integer.MAX_VALUE;
            int []next =  new int[2];
            Arrays.fill(next, -1);

            for(int i = 0 ; i < dirs.length; i++) {
                int next_x = curr[0] + dirs[i][0];
                int next_y = curr[1] + dirs[i][1];
                if(next_x >= 0 && next_x < rows && next_y >= 0 && next_y < cols){
                    if(min > matrix[next_x][next_y]){
                        min = matrix[next_x][next_y];
                        next[0] = next_x;
                        next[1] = next_y;
                    }
                }
            }
            if(next[0] == -1 && next[1] == -1){
                break;
            }
            minSum += min;
            q.offer(next);
        }
        return minSum;
    }


    public static void main(String[] args) {
        FebruaryW2 curr = new FebruaryW2();
        int[] mat = new int[]{3, 1, 4, 1, 5}; //[[8,1,6],[3,5,7],[4,9,2]]
        //curr.fullJustify(new String[]{"Don't","go","around","saying","the","world","owes","you","a",
        //        "living;","the","world","owes","you","nothing;","it","was","here","first."}, 30);
        //curr.partitionLabels("ababcbacadefegdehijhklij");
        /*int[][] grid = new int[][] {
            [4,3,2,-1],
            [3,2,1,-1],
            [1,1,-1,-2],
            [-1,-1,-2,-3]*/
        //System.out.println("res = " + res);
        curr.maxProductIII(new int[]{2,3,-2,4});
    }

    /*
     LC : 152
     https://leetcode.com/problems/maximum-product-subarray/ */
    public int maxProduct(int[] nums) {
       return maxProductII(nums);
    }

    public int maxProductI(int[] nums) {
        if(nums.length == 0) return -1;
        int curr_max = nums[0];
        int global_max = nums[0];
        int curr_min = nums[0];

        for(int i = 1; i < nums.length; i++){
            int temp_max = curr_max;
            curr_max = Math.max(Math.max(curr_max * nums[i], curr_min * nums[i]), nums[i]);
            curr_min = Math.min(Math.min(temp_max * nums[i], curr_min * nums[i]), nums[i]);

            global_max = Math.max(global_max, curr_max);
        }
        return global_max;
    }

    public int maxProductIII(int[] nums) {
        int n = nums.length;
        int r = nums[0];
        for(int i = 1, max = r, min = r; i < n; i++ ){
            if(nums[i] < 0){  // multiplied by a negative makes big number smaller, small number bigger
                int tmp = min;
                min = max;
                max = tmp;
            }

            min = Math.min(nums[i], min * nums[i]);
            max = Math.max(nums[i], max * nums[i]);

            r = Math.max(r, max);
        }
        return r;
    }

    public int maxProductII(int[] nums) {
        int r = 0;
        int n = nums.length;
        int[] prefixProd = new int[n];
        int[] suffixProd = new int[n];

        prefixProd[0] = nums[0];
        suffixProd[n-1] = nums[n-1];

        r = nums[0];

        for(int i = 1; i < n; i++){
            prefixProd[i] = (prefixProd[i - 1] == 0 ? 1 : prefixProd[i-1]) * nums[i];
        }

        for(int i = n-2; i >= 0; i--){
            suffixProd[i] = (suffixProd[i + 1] == 0 ? 1 : suffixProd[i + 1]) * nums[i];
        }

        for(int i = 0; i < n; i++){
            r = Math.max(r, Math.max(suffixProd[i], prefixProd[i]));
        }
        return r;
    }

    /* https://leetcode.com/problems/subarray-product-less-than-k/ */
    public int numSubarrayProductLessThanK(int[] nums, int k) {
        if(k == 0) return 0;
        int p = 1;
        int count = 0;
        for(int i = 0, j = 0; j < nums.length; j++){
            p = p * nums[j];
            while(i <= j && p >= k){
                p /= nums[i++];
            }
            count += j - i + 1;
        }
        return count;
    }

    /* https://leetcode.com/problems/two-sum-less-than-k/ */
    public int twoSumLessThanKI(int[] arr, int k) {
        TreeMap<Integer, Integer> map = new TreeMap<>();
        int maxSum = 0;
        for(int i = 0; i < arr.length; i++){
            int rem = k - arr[i];
            Integer key = map.lowerKey(rem);
            if(key != null){
                maxSum = Math.max(maxSum, map.get(key) + arr[i]);
            }
            map.put(arr[i], arr[i]);
        }
        return maxSum == 0 ? -1 : maxSum;
    }

    public int twoSumLessThanK(int[] arr, int k) {
        TreeSet<Integer> set = new TreeSet<>();
        int maxSum = 0;
        for(int i = 0; i < arr.length; i++){
            int rem = k - arr[i];
            Integer key = set.lower(rem);
            if(key != null){
                maxSum = Math.max(maxSum, key + arr[i]);
            }
            set.add(arr[i]);
        }
        return maxSum == 0 ? -1 : maxSum;
    }

    /*
     LC : 190
     https://leetcode.com/problems/reverse-bits/submissions/ */
    public int reverseBits(int n) {
        if(n == 0) return n;
        if(n > 0){
            int[] arr = new int[32];
            int start = arr.length - 1;
            while (n != 1){
                int rem = n % 2;
                arr[start] = rem;
                start--;
                n /= 2;
            }
            arr[start] = n;
            // Convert primitive array to List non-primitive obj
            List<Integer> list=  Arrays.stream(arr).mapToObj(i -> i).collect(Collectors.toList());
            Collections.reverse(list);
            arr = list.stream().mapToInt(i -> i).toArray();

            /*int low = 0, high = arr.length - 1;
            while (low < high){
                int temp = arr[low];
                arr[low] = arr[high];
                arr[high] = temp;
                low++;
                high--;
            }*/
            int res = 0;
            for (int i = 0; i < arr.length; i++) {
                int num = arr[i];
                res = res * 2 + num;
            }
            return res;
        }else{
            String binaryStr = Integer.toBinaryString(n);
            StringBuilder builder = new StringBuilder(binaryStr);
            builder.reverse();
            int res = 0;
            for (int i = 0; i < builder.length(); i++) {
                int num = Integer.parseInt(builder.charAt(i) + "");
                res = res * 2 + num;
            }
            return res;
        }
    }

    /* https://leetcode.com/problems/zigzag-conversion/ */
    public String convert(String s, int numRows) {
        List<StringBuilder> list = new ArrayList<>(numRows);
        
        for(int i = 0; i < numRows; i++){
            list.add(new StringBuilder());
        }
        
        StringBuilder input = new StringBuilder(s);
        
        int i = 0;
        boolean flip = false;
        while(input.length() > 0){
            if(!flip){
                StringBuilder stored = list.get(i++);
                stored.append(input.charAt(0));
                if(i == numRows){
                    flip = true;
                    if(numRows == 1){
                        i = 0;
                    }else{
                       i = numRows - 2;
                    }
                }
            }else {
                StringBuilder stored = list.get(i--);
                stored.append(input.charAt(0));
                if(i == -1){
                    flip = false;
                    i = 1;
                    if(numRows == 1){
                        i = 0;
                    }else{
                       i = 1;  
                    }
                }
            }
            input.deleteCharAt(0);
        }
        
        StringBuilder finalStr = new StringBuilder();
        for(int j = 0 ; j < list.size(); j++){
            finalStr.append(list.get(j).toString());
        }
        return finalStr.toString();
    }

    /* https://leetcode.com/problems/3sum-closest/ */
    public int threeSumClosest(int[] nums, int t) {
        Arrays.sort(nums);
        int n = nums.length;
        int res = nums[0] + nums[1] + nums[n-1];
        
        for(int i = 0; i < n - 2; i++){
            int start = i + 1;
            int end = n - 1;
            while(start < end){
                int a = nums[i];
                int b = nums[start];
                int c = nums[end];

                int sum = a + b + c;

                if(sum < t){
                    start++;
                }else {
                    end--;
                }
                if(Math.abs(res - t) > Math.abs(sum - t)){
                    res = sum;
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/3sum-smaller/ */
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);

        int n = nums.length;
        int res = 0;

        for(int i = 0; i < n -2; i++){
            int start = i + 1;
            int end = n - 1;

            while(start < end){
                int a = nums[i];
                int b = nums[start];
                int c = nums[end];

                int cSum = a + b + c;

                if(cSum < target){
                    res += end - start;
                    start++;
                }else{
                    end--;
                }
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/valid-triangle-number/ */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);

        int n = nums.length;
        int res = 0;

        for(int i = 2; i < n ; i++){
            int start = 0;
            int end = i - 1;

            while(start < end){
                int a = nums[i];
                int b = nums[start];
                int c = nums[end];

                if(a < b + c){
                    res += end - start;
                    end--;
                }else{
                    start++;
                }
            }
        }
        return res;
    }

    /*  https://leetcode.com/problems/k-diff-pairs-in-an-array/ */
    public int findPairs(int[] nums, int k) {
        if(nums == null || nums.length == 0 || k < 0) return 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for(int i = 0; i < nums.length; i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }
        for(Map.Entry<Integer, Integer> entry : map.entrySet()){
            if(k == 0){
                if(map.get(entry.getKey()) > 1){
                    count++;
                }
            }else{
                if(map.containsKey(entry.getKey() + k)){
                    count++;
                }
            }
        }
        return count;
    }

    /* https://leetcode.com/problems/magic-squares-in-grid/ */
    public int numMagicSquaresInside(int[][] grid) {
        int magicGrid = 0;
        for (int i = 0; i < grid.length - 2; i++) {
            for (int j = 0; j < grid[0].length - 2; j++) {
                if(isMagicGrid(grid, i, j)) {
                    magicGrid++;
                }
            }
        }
        return magicGrid;
    }

    public boolean isMagicGrid(int[][] grid, int row, int col){
        int[] record = new int[10];
        for (int i = row; i < row + 3; i++) {
            for (int j = col; j < col + 3; j++) {
                if(grid[i][j] < 1 || grid[i][j] > 9 || record[grid[i][j]] > 0){
                    return false;
                }
                record[grid[i][j]] = 1;
            }
        }

        int sum1 = grid[row][col+2] + grid[row+1][col+1] + grid[row+2][col];
        int sum2 = grid[row][col] + grid[row+1][col+1] + grid[row+2][col+2];
        if(sum1 != sum2) {
            return false;
        }

        for (int k = 0; k < 3; k++) {
            if(grid[row + k][col] + grid[row + k][col+1] + grid[row + k][col+2] != sum1){
                return false;
            }

            if(grid[row][col + k] + grid[row + 1][col + k] + grid[row + 2][col + k] != sum1){
                return false;
            }
        }
        return true;
    }

    /* https://leetcode.com/problems/majority-element-ii/ */
    //Using Boyer - moore voting algorithm
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums == null || nums.length == 0) return res;
        int num1 = nums[0], num2 = nums[0], count1 = 0, count2 = 0;
        int k = nums.length / 3;
        for(int val : nums){
            if(val == num1){
                count1++;
            }else if(val == num2){
                count2++;
            }else if(count1 == 0){
                num1 = val;
                count1++;
            }else if(count2 == 0){
                num2 = val;
                count2++;
            }else{
                count1--;
                count2--;
            }
        }
        count1 = 0;
        count2 = 0;
        for(int val : nums){
            if(val == num1){
                count1++;
            }else if(val == num2){
                count2++;
            }
        }
        
        if(count1 > k){
            res.add(num1);
        }
        if(count2 > k){
            res.add(num2);
        }
        return res;
    }

    /* https://leetcode.com/problems/check-if-a-number-is-majority-element-in-a-sorted-array/ */
    public boolean isMajorityElement(int[] nums, int target) {
        if(nums == null || nums.length == 0) return false;
        int count = 0;
        int val = nums[0];
        
        int k = nums.length / 2;
        
        for(int ele : nums){
            if(ele == val){
                count++;
            }else if(count == 0){
                val = ele;
                count++;
            }
            else{
                count--;
            }
        }
        count = 0;
        for(int i : nums){
            if(i == val){
                count++;
            }
        }
        if(count > k && val == target){
            return true;
        }
        return false;
        
    }

    /* https://leetcode.com/problems/toeplitz-matrix/ */
    public boolean isToeplitzMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        
        int startCol = n - 1;
        int startRow = 0;
        
        while(startCol >= 0 && startRow < m){
            int next_x = startRow;
            int next_y = startCol;
            int curr = matrix[next_x][next_y];
            while(next_x < m && next_y < n){
                 int next = matrix[next_x][next_y];
                 if(next != curr){
                    return false;
                 }
                 next_x = next_x + 1;
                 next_y = next_y + 1;
            }
            if(startCol > 0){
                startCol--;    
            }else{
                startRow++;
            }
        }
        return true;
    }

    /* https://leetcode.com/problems/valid-word-square/ */
    public boolean validWordSquare(List<String> words) {
        int m = words.size();
        int row = 0;
        int col = 0;
        while(row < m ){
            String rowStr = words.get(row);
            StringBuilder colStr = new StringBuilder();
            for(int i = 0; i < m; i++){
                if(words.get(i).length() <= col){
                    continue;
                }
                colStr.append(words.get(i).charAt(col));
            }
            
            if(!rowStr.equals(colStr.toString())){
                return false;
            }
            row++;
            col++;
        }
        return true;
    }

    /* Input: S = "ababcbacadefegdehijhklij"
       Output: [9,7,8]

       https://leetcode.com/problems/partition-labels/
    */
    public List<Integer> partitionLabels(String s) {
        HashMap<Character, Integer> map = new HashMap<>();

        int n = s.length();

        for(int i = 0; i < s.length(); i++){
            char ch = s.charAt(i);
            map.put(ch, i);
        }

        List<Integer> res = new ArrayList<>();
        int start = 0;
        int end = map.get(s.charAt(0));
        for(int i = 0; i < s.length(); i++){
            end = Math.max(end, map.get(s.charAt(i)));
            if(i == end){
                int len = end - start + 1;
                res.add(len);
                start = i+1;
                end = map.get(s.charAt(i));
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/ */
    public int countNegatives(int[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = grid[i].length -1; j >= 0 ; j--) {
                if(grid[i][j] < 0){
                    count++;
                }else{
                    break;
                }
            }
        }
        return count;
    }

    /* https://leetcode.com/problems/product-of-the-last-k-numbers/ */
    class ProductOfNumbers {
        List<Integer> prod;
        int p;
        public ProductOfNumbers() {
            prod = new ArrayList<>();
            p = 1;
        }

        public void add(int num) {
            if(num == 0){
                prod = new ArrayList<>();
                p = 1;
                return;
            }
            p = p * num;
            prod.add(p);
        }

        public int getProduct(int k) {
            if(prod.size() < k) return 0;
            int ans = prod.get(prod.size()-1);
            if(prod.size() == k) return ans;
            return ans / prod.get(prod.size()-1 - k);
        }
    }


    /* https://leetcode.com/problems/text-justification/ */
    public List<String> fullJustify(String[] words, int maxWidth) {
        StringBuilder builder = new StringBuilder();
        int currW = 0;
        builder.append(words[0]);
        List<Integer> idx = new ArrayList<>();

        List<String> res = new ArrayList<>();

        for(int i = 1; i < words.length; i++){
            currW = builder.length();
            int rem = maxWidth - currW;
            if( rem > 0 && rem >= words[i].length()+1){
                builder.append(" ");
                idx.add(builder.length());
                builder.append(words[i]);
            }else if(rem > 0 && idx.size() > 0){
                int currIdx = 0;
                while (rem-- > 0){
                    builder.insert(idx.get(currIdx) + currIdx, " ");
                    idx.set(currIdx, idx.get(currIdx)+1);
                    currIdx++;
                    if(currIdx == idx.size()) currIdx = 0;
                }

                res.add(builder.toString());
                builder.setLength(0);
                builder.append(words[i]);
                idx.clear();
            }else if(rem > 0){
                while (rem-- > 0){
                    builder.append(" ");
                }
                res.add(builder.toString());
                builder.setLength(0);
                builder.append(words[i]);
                idx.clear();
            }else{
                res.add(builder.toString());
                builder.setLength(0);
                builder.append(words[i]);
                idx.clear();
            }
        }
        int rem = maxWidth - builder.length();
        while (rem-- > 0){
            builder.append(" ");
        }
        res.add(builder.toString());
        return res;
    }

    /* https://leetcode.com/problems/number-of-segments-in-a-string/ */
    public int countSegments(String s) {
        if(s == null || s.length() == 0) return 0;
        String[] arr = s.split("\\s+");
        int count = 0;
        for(int i = 0; i < arr.length; i++){
            if(arr[i].length() > 0){
                count++;
            }
        }
        return count;
    }

    class Interval {
        public int start;
        public int end;

        public Interval() {}

        public Interval(int _start, int _end) {
            start = _start;
            end = _end;
        }
    };

    /* https://leetcode.com/problems/employee-free-time/ */
    public List<Interval> employeeFreeTime(List<List<Interval>> schedule) {
        int len = 0;
        
        int idx = 0;
        
        for(int i = 0; i < schedule.size(); i++ ){
            List<Interval> list = schedule.get(i);
            len += list.size();
        }
        
        int[][]intervals = new int[len][2];
        
        for(int i = 0; i < schedule.size(); i++ ){
            List<Interval> list = schedule.get(i);
            for(Interval val : list){
                int[] curr = new int[2];
                curr[0] = val.start;
                curr[1] = val.end;
                intervals[idx++] = curr;
            }
        }
        
        Arrays.sort(intervals, (a,b) -> (a[0] == b[0]) ? b[1] - a[1] : a[0] - b[0]);
        
        int[] curr = intervals[0];
        int max = curr[1];
        List<Interval> res = new ArrayList<>();
        for(int[] next : intervals){
            if(curr[1] >= next[0]){
                max = Math.max(curr[1], next[1]);
                curr[1] = max;
            }else{
                int start = curr[1];
                int end = next[0];
                Interval inter = new Interval(start, end);
                res.add(inter);
                curr = next;
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/reverse-linked-list/ */
    public ListNode reverseList(ListNode head) {
         ListNode newHead = null;         
         while (head != null) {
            ListNode next = head.next;
            head.next = newHead;
            newHead = head;
            head = next;
        }
        return newHead;
    }

    /* https://leetcode.com/problems/reverse-nodes-in-k-group/ */
    public ListNode reverseKGroup(ListNode head, int k) {
        return reverseKGroupRec(head, k);
    }
    
    public ListNode reverseKGroupRec(ListNode head, int k){
        ListNode fastPtr = head;
        int i = 0;
        for(; i < k && fastPtr != null; i++){
            fastPtr = fastPtr.next;
        }
        if(i == k){
            fastPtr = reverseKGroupRec(fastPtr, k);
            
            while (i-- > 0) {
                ListNode next = head.next;
                head.next = fastPtr;
                fastPtr = head;
                head = next;
            }
            head = fastPtr;
        }
        return head;
    }

    /* https://leetcode.com/problems/reaching-points/ 
       Revisit
     */
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while(tx >= sx && ty >= sy){
            if(tx == ty) break;
            if(tx > ty){
                if(ty > sy){
                    tx = tx % ty;
                }
                else return (tx - sx) % ty == 0;
            }else{
                if(tx > sx ){
                    ty = ty % tx;
                }
                else return (ty - sy) % tx == 0;
            }
        }
        return (tx == sx && ty == sy);
    }

    /* https://leetcode.com/problems/count-of-smaller-numbers-after-self/ */
    public List<Integer> countSmallerI(int[] nums) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> tmpHeap = new PriorityQueue<>();

        List<Integer> res = new ArrayList<>(); 
        for(int i = nums.length - 1; i >= 0; --i){
            while(!minHeap.isEmpty() && minHeap.peek() < nums[i]){
                tmpHeap.offer(minHeap.poll());
            }
            res.add(0, tmpHeap.size());

            while(!tmpHeap.isEmpty()){
                minHeap.offer(tmpHeap.poll());
            }
            minHeap.offer(nums[i]);
        }
        return res;
    }

    class TreeNode {
        TreeNode left = null;
        TreeNode right = null;
        int sum, val, dup = 1;
        public TreeNode(int val, int prefixSum){
            this.sum = prefixSum;
            this.val = val;
        }
    }
    public List<Integer> countSmallerTree(int[] nums) {
        int n = nums.length;
        Integer[] res = new Integer[n];
        TreeNode root = null;
        for(int i = n -1; i >= 0; --i){
            root = insert(nums[i], root, res, i, 0);
        }

        return Arrays.asList(res);
    }

    public TreeNode insert(int val, TreeNode node, Integer[] res, int i, int prefixSum){
        if(node == null){
            node = new TreeNode(val, 0);
            res[i] = prefixSum;
        }else if(node.val > val){
            node.sum++;
            node.left = insert(val, node.left, res, i, prefixSum);
        }else if(node.val == val){
            node.dup++;
            res[i] = prefixSum + node.sum;
        }else{
            node.right = insert(val, node.right, res, i, prefixSum + node.dup + node.sum);
        }
        return node;
    }

    //Using merge-sort
    class Pair {
        int val, idx;
        public Pair(int val, int idx){
            this.val = val;
            this.idx = idx;
        }
    }

    public List<Integer> countSmaller(int[] nums) {
        int n = nums.length;
        Pair[] arr = new Pair[n];

        for(int i = 0; i < n; i++){
            Pair p = new Pair(i, nums[i]);
            arr[i] = p;
        }

        Integer[] res = new Integer[n];

        mergeSort(arr, res);

        return Arrays.asList(res);
    }

    public Pair[] mergeSort(Pair[] nums, Integer[] res){
        if(nums.length <= 1){
            return nums;
        }
        int mid = nums.length / 2;
        Pair[] left = mergeSort(Arrays.copyOfRange(nums, 0, mid), res);
        Pair[] right = mergeSort(Arrays.copyOfRange(nums, mid + 1, nums.length), res);

        for(int i = 0, j = 0; i < left.length && j < right.length;){
            if(j == right.length || left[i].val <= right[j].val){
                nums[i + j] = left[i];
                res[left[i].idx] += j;
                i++;
            }else{
                nums[i + j] = right[j];
                j++;
            }
        }
        return nums;
    }
}