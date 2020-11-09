package com.pkumar7.binarysearch;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 10/August/2020
 */
class A {
    public static void main(String[] args) {
        A w2 = new A();
        w2.kthSmallestPrimeFraction(new int[]{1, 2, 3, 5}, 3);
    }

    /* 1631. Path With Minimum Effort
    * https://leetcode.com/problems/path-with-minimum-effort/
    * */
    public int minimumEffortPathBinarySearch(int[][] matrix) {
        int low = 0, high = 1000_000;
        while (low < high){
            int mid = low + ((high - low) / 2);
            if(helper(mid, matrix)){
                high = mid;
            }else{
                low = mid + 1;
            }
        }
        return low;
    }

    public boolean helper(int minEffort, int[][] matrix){
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{0, 0});
        visited[0][0] = true;
        while (!queue.isEmpty()){
            int size = queue.size();
            while (size-- > 0){
                int[] curr = queue.poll();
                if(curr[0] == m - 1 && curr[1] == n - 1){
                    return true;
                }
                for (int[] d : dirs){
                    int next_x = d[0] + curr[0];
                    int next_y = d[1] + curr[1];
                    if(next_x >= 0 && next_x < m && next_y >= 0 & next_y < n && !visited[next_x][next_y]){
                        if(Math.abs(matrix[curr[0]][curr[1]] - matrix[next_x][next_y]) <= minEffort){
                            queue.offer(new int[]{next_x, next_y});
                            visited[next_x][next_y] = true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /* 633. Sum of Square Numbers
     * https://leetcode.com/problems/minimum-insertions-to-balance-a-parentheses-string/
     */
    public boolean judgeSquareSum(int c) {
        int low = 0;
        int high = (int)Math.sqrt(c);
        while (low <= high){
            int curr = low * low + high * high;
            if(curr == c) return true;
            if(curr < c){
                low++;
            }else{
                high--;
            }
        }
        return false;
    }

    /*
    1552. Magnetic Force Between Two Balls
    https://leetcode.com/problems/magnetic-force-between-two-balls/
    */
    public int maxDistance(int[] position, int m) {
        Arrays.sort(position);
        int low = 1, high = (int)1e9;
        while (high - low > 1){
            int mid = low + (high - low) / 2;
            if(check(position, mid, m)){
                low = mid;
            }else{
                high = mid;
            }
        }
        return low;
    }

    public boolean check(int[] arr,int mid, int m){
        int r = -1;
        int count = 0;
        for (int x : arr){
            if(r <= x){
                r = x + mid;
                count++;
            }
        }
        return count >= m;
    }

    /*
    668. Kth Smallest Number in Multiplication Table
    https://leetcode.com/problems/kth-smallest-number-in-multiplication-table/
    Time : O(n * log(m*n))
    */
    public int findKthNumber(int m, int n, int k) {
        int low = 1;
        int high = m * n + 1;

        while(low < high){
            int mid = low + (high - low) / 2;
            int cnt = count(mid, m, n);
            if(cnt < k){
                low = mid + 1;
            }else if(cnt >= k){
                high = mid;
            }
        }
        return low;
    }

    public int count(int mid, int m, int n){
        //Number of elements less than mid
        int count = 0;
        for(int i = 1; i <= m; i++){
            int tmp = Math.min(mid / i, n);
            count += tmp;
        }
        return count;
    }

    /*
    719. Find K-th Smallest Pair Distance
    https://leetcode.com/problems/find-k-th-smallest-pair-distance/
    Time : O(n * log(high))
    */
    public int smallestDistancePair(int[] nums, int k) {
        Arrays.sort(nums);
        int n = nums.length;
        int low = 0;
        int high = nums[n - 1] - nums[0];
        
        while(low < high){
            int mid = low + (high - low) / 2;
            int cnt = countBinarySearch(nums, mid);
            if(cnt < k){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    public int countBinarySearch(int[] nums, int mid){
        int cnt = 0;
        int n = nums.length;
        for(int i = 0; i < n; i++){
            cnt += countPairs(nums, i, n - 1, nums[i] + mid) - i - 1;
        }
        return cnt;
    }

    public int countPairs(int[] nums, int low, int high, int key){
        if(nums[high] <= key) return high + 1;
        while(low < high){
            int mid = low + (high - low) / 2;
            if(key >= nums[mid]){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    public int count(int[] nums, int mid){
        int cnt = 0;
        int n = nums.length;
        for(int i = 0, j = i ; i < n; i++){
            while(j < n && (nums[j]  - nums[i]) <= mid ){
                j++;
                cnt += (j - i - 1);
            }
        }
        return cnt;
    }

    /*
    * 786. K-th Smallest Prime Fraction
    * https://leetcode.com/problems/k-th-smallest-prime-fraction/
    https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
    */
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        return kthSmallestPrimeFractionHeap(arr, k);
    }

    /* Time : O((n + k) * logn) */
    public int[] kthSmallestPrimeFractionHeap(int[] arr, int k) {
        int n = arr.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(arr[a[0]] * arr[n - 1 - b[1]], arr[n - 1 - a[1]] * arr[b[0]] );
            }
        });

        for(int i = 0; i < n; i++){
            pq.offer(new int[]{i, 0});
        }

        while(--k > 0){
            int[] a = pq.poll();
            if(++a[1] < n){
                pq.offer(a);
            }
        }
        return new int[]{ arr[pq.peek()[0]], arr[n - 1 - pq.peek()[1]] };
    }

    /* Time : O( n * log(max^2) ) */
    public int[] kthSmallestPrimeFractionBinarySearch(int[] arr, int k) {
        double low = 0, high = 1;
        int p = 0, q = 1;

        for(int n = arr.length, cnt = 0; true ; cnt = 0, p = 0 ){
            double mid = (low + high) / 2;
            for(int i = 0 , j = n - 1; i < n; i++) {
                while(j >= 0 && arr[i] > mid * arr[n - 1 - j]) {
                    j--;
                }
                cnt += (j  + 1);
                if(j >= 0 && p * arr[n -1 - j] < q * arr[i]) {
                    p = arr[i];
                    q = arr[n - 1 - j];
                }
            }
            if(cnt < k) {
                low = mid;
            }else if(cnt > k) {
                high = mid;
            }else{
                return new int[]{p, q};
            }
        }
    }
}
