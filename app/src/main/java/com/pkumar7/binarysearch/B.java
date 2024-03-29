package com.pkumar7.binarysearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class B {

    /*
     * https://leetcode.com/problems/find-the-index-of-the-large-integer/
     * 1533. Find the Index of the Large Integer
     * */
    interface ArrayReader {
        // Compares the sum of arr[l..r] with the sum of arr[x..y]
        // return 1 if sum(arr[l..r]) > sum(arr[x..y])
        // return 0 if sum(arr[l..r]) == sum(arr[x..y])
        // return -1 if sum(arr[l..r]) < sum(arr[x..y])
        int compareSub(int l, int r, int x, int y);
        int length();
    }

    public int getIndex(ArrayReader reader) {
        int n = reader.length();
        int l = 0, r = n - 1;
        while (l < r){
            int mid = l + (r-l)/2;
            if((r-l)%2 == 0){
                int ans = reader.compareSub(l, mid - 1, mid + 1, r);
                if(ans == 0){
                    return mid;
                }else if(ans < 0){
                    l = mid+1;
                }else{
                    r = mid-1;
                }
            }else{
                int ans = reader.compareSub(l, mid , mid + 1, r);
                if(ans == 1){
                    r = mid;
                }else{
                    l = mid+1;
                }
            }
        }
        return l;
    }

    /* 702. Search in a Sorted Array of Unknown Size
    * https://leetcode.com/problems/search-in-a-sorted-array-of-unknown-size/
    * */
    interface ArrayReader1 {
        int get(int index);
    }

    public int search(ArrayReader1 reader, int target) {
        int l = 0, r = 10000;
        while (l <= r){
            int mid = l + (r - l) / 2;
            if(reader.get(mid) == target) {
                return mid;
            }else if(reader.get(mid) < target){
                l = mid + 1;
            }else{
                r = mid - 1;
            }
        }
        return -1;
    }

    /*
     * https://leetcode.com/problems/plates-between-candles/
     * 2055. Plates Between Candles
     * */
    public int[] platesBetweenCandles(String s, int[][] queries) {
        int n = s.length();
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if(s.charAt(i) == '|'){
                list.add(i);
            }
        }
        int q = queries.length;
        int[] res = new int[q];
        int[] prefixSum = new int[list.size()];
        if(list.isEmpty()) return res;

        for (int i = 1; i < list.size() ; i++) {
            prefixSum[i] = prefixSum[i - 1] + list.get(i) - list.get(i - 1) -1;
        }

        for (int i = 0; i < q; i++) {
            int[] qq = queries[i];
            int left = Collections.binarySearch(list, qq[0]);
            if(left < 0) {
                left = ~left;
            }
            int right = Collections.binarySearch(list, qq[1]);
            if(right < 0) {
                right = ~right;
                right -= 1;
            }
            int plates = 0;
            if(left <= right){
                plates += prefixSum[right] - prefixSum[left];
            }
            res[i] = plates;
        }
        return res;
    }

    /*
     * https://leetcode.com/problems/maximum-candies-allocated-to-k-children/
     * 2226. Maximum Candies Allocated to K Children
     * */
    public int maximumCandies(int[] candies, long k) {
        int low = 0;
        int high = 10_000_000;
        while (low < high){
            int mid = (low + high + 1) / 2;
            if(canDistribute(candies, mid, k)){
                low = mid;
            }else{
                high = mid - 1;
            }
        }
        return (int)low;
    }

    public boolean canDistribute(int[] candies, long max, long k){
        int totalPiles = 0;
        for(int c : candies){
            totalPiles += (c / max);
        }
        return (totalPiles >= k);
    }

    /*
    * https://leetcode.com/problems/maximum-total-beauty-of-the-gardens/
    * 2234. Maximum Total Beauty of the Gardens
    * */
    public long maximumBeauty(int[] flowers, long newFlowers, int target, int full, int partial) {
        int n = flowers.length;

        Arrays.sort(flowers);
        long[] cost = new long[n];
        for (int i = 1; i < n ; i++) {
            flowers[i] = Math.min(flowers[i], target);
            cost[i] = cost[i - 1] + (long) i * ( flowers[i] - flowers[i - 1]);
        }

        if(flowers[0] == target) {
            return (long) full * flowers.length;
        }

        if(newFlowers >= cost[n - 1] + (long) (target - flowers[n - 1]) * n){
            return Math.max((long) full * n , (long) full * (n - 1) + (long)partial * (target - 1));
        }

        int j = n - 1;
        while (flowers[j] == target) j--;

        long ans = 0, left = newFlowers;
        while (left >= 0){
            int idx = Arrays.binarySearch(cost, 0, j + 1, left);
            if(idx < 0) idx = ~idx - 1;

            long bar = flowers[idx] + (left - cost[idx]) / (idx + 1);
            ans = Math.max(ans, bar * partial + (long) full * (n - j - 1));
            left -= (target - flowers[j]);
            j -= 1;
        }
        return ans;
    }

    /*
    * 1891. Cutting Ribbons
    * https://leetcode.com/problems/cutting-ribbons/
    * */
    public int maxLength(int[] ribbons, int k) {
        int high = Arrays.stream(ribbons).max().getAsInt();
        int low = 1;
        int res = 0;
        while(low <= high){
            int mid = (low + high) / 2;
            if(possible(mid, ribbons, k)){
                res = mid;
                low = mid + 1;
            }else{
                high = mid - 1;
            }
        }
        return res;
    }

    public boolean possible(int len, int[] ribbons, int k){
        int res = 0;
        for(int r : ribbons){
            res += (r / len);
        }
        return res >= k;
    }

    /*
     * https://leetcode.com/problems/last-day-where-you-can-still-cross/
     * 1970. Last Day Where You Can Still Cross
     * */
    public int latestDayToCrossBinarySearch(int row, int col, int[][] cells) {
        int low = 1;
        int high = cells.length;

        int res = 1;
        while (low <= high){
            int mid = (low + high) / 2;
            if(canWalkFromTopToBottom(mid, row, col, cells)){
                res = mid;
                low = mid + 1;
            }else{
                high = mid -1;
            }
        }
        return res;
    }

    private boolean canWalkFromTopToBottom(int mid, int row, int col, int[][] cells) {
        int[][] grid = new int[row][col];

        for (int i = 0; i < mid; i++) {
            int[] c = cells[i];
            grid[c[0] - 1][c[1] - 1] = 1;
        }
        Queue<int[]> q = new LinkedList<>();
        boolean[][] visited = new boolean[row][col];
        for (int i = 0; i < col; i++) {
            if(grid[0][i] == 0){
                visited[0][i] = true;
                q.offer(new int[]{0, i});
            }
        }
        return canWalk(q, grid, visited);
    }

    public boolean canWalk(Queue<int[]> q, int[][] grid, boolean[][] visited){
        int m = grid.length;
        int n = grid[0].length;

        while (!q.isEmpty()){
            int size = q.size();
            while (size-- > 0){
                int[] c = q.poll();
                if(c[0] == m - 1) return true;
                int[] dx = new int[] {0, 0, 1, -1};
                int[] dy = new int[] {1, -1, 0, 0};
                for (int i = 0; i < dx.length; i++) {
                    int next_x = dx[i] + c[0];
                    int next_y = dy[i] + c[1];
                    if(next_x >=0 && next_x < m && next_y >=0 && next_y < n
                            && grid[next_x][next_y] == 0 && !visited[next_x][next_y]){
                        q.offer(new int[]{next_x, next_y});
                        visited[next_x][next_y] = true;
                    }
                }
            }
        }
        return false;
    }

    /*
     * https://leetcode.com/problems/find-the-longest-valid-obstacle-course-at-each-position/
     * https://www.cs.princeton.edu/courses/archive/spring13/cos423/lectures/LongestIncreasingSubsequence.pdf
     * */
    public int[] longestObstacleCourseAtEachPosition(int[] obstacles) {
        return lisPatienceSort(obstacles);
    }

    public int[] lisPatienceSort(int[] nums) {
        List<Integer> piles = new ArrayList<>(nums.length);
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int pile = binarySearch(piles, num);
            if (pile < 0) {
                pile = ~pile;//Bitwise unary NOT
            }
            if (pile == piles.size()) {
                piles.add(num);
            } else {
                piles.set(pile, num);
            }
            res[i] = pile + 1;
        }
        return res;
    }

    public int binarySearch(List<Integer> piles, int num){
        int low = 0, high = piles.size() - 1;
        while(low <= high){
            int mid = (high + low) / 2;
            int mid_ele = piles.get(mid);
            if(mid_ele <= num){
                low = mid + 1;
            }else {
                high = mid - 1;
            }
        }
        return -(low + 1);
    }
}
