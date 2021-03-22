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
        w2.kthSmallestPrimeFraction(new int[] {1, 2, 3, 5}, 3);
    }

    /*
     * https://leetcode.com/problems/maximum-value-at-a-given-index-in-a-bounded-array
     * */
    public int maxValue(int n, int index, int maxSum) {
        maxSum -= n;
        int left = 0, right = maxSum, mid;
        while (left < right) {
            mid = (left + right + 1) / 2;
            if (test(n, index, mid) <= maxSum)
                left = mid;
            else
                right = mid - 1;
        }
        return left + 1;
    }

    private long test(int n, int index, int a) {
        int b = Math.max(a - index, 0);
        long res = (long)(a + b) * (a - b + 1) / 2;
        b = Math.max(a - ((n - 1) - index), 0);
        res += (long)(a + b) * (a - b + 1) / 2;
        return res - a;
    }

    /* 778. Swim in Rising Water
        https://leetcode.com/problems/swim-in-rising-water/
    */
    public int swimInWater(int[][] grid) {
        int n = grid.length;
        int low = grid[0][0], high = n * n - 1;
        while (low < high) {
            int mid = (low + high) / 2;
            if (isReachable(mid, grid)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return high;
    }

    public boolean isReachable(int time, int[][] grid) {
        int n = grid.length;
        Queue<int[]> q = new LinkedList<int[]>();
        q.offer(new int[] {0 , 0});
        int[] dx = new int[] {0, 0, 1, -1};
        int[] dy = new int[] {1, -1, 0, 0};
        boolean[][] visited = new boolean[n][n];
        visited[0][0] = true;

        while (!q.isEmpty()) {
            int[] curr = q.poll();
            int x = curr[0];
            int y = curr[1];
            if (x == n - 1 && y == n - 1) {
                return true;
            }
            for (int i = 0; i < dx.length; i++) {
                int nx = x + dx[i];
                int ny = y + dy[i];
                if (nx >= 0 && nx < n && ny >= 0 && ny < n
                        && grid[nx][ny] <= time && !visited[nx][ny]) {
                    visited[nx][ny] = true;
                    q.offer(new int[] {nx, ny});
                }
            }
        }
        return false;
    }

    /*
     * https://leetcode.com/problems/minimum-limit-of-balls-in-a-bag/
     * */
    public int minimumSize(int[] nums, int maxOperations) {
        int max = Arrays.stream(nums).max().getAsInt();
        int low = 1, high = max;
        while (low <= high) {
            int mid = (high + low) / 2;
            if (isPossible(mid, nums, maxOperations)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private boolean isPossible(int bagSize, int[] nums, int operations) {
        for (int i = 0; i < nums.length; i++) {
            if (operations < 0) return false;
            if (nums[i] > bagSize ) {
                operations -= ((nums[i] - 1) / bagSize);
            }
        }
        return operations >= 0;
    }

    /* 1712. Ways to Split Array Into Three Subarrays
     * https://leetcode.com/problems/ways-to-split-array-into-three-subarrays/
     * */
    public int waysToSplit(int[] nums) {
        int ans = 0;
        int mod = (int)1e9 + 7;
        int n = nums.length;
        int[] prefix = new int[n];
        prefix[0] = nums[0];
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        for (int i = 1; i < n; i++) {
            int left = search(prefix, prefix[i - 1], i , true);
            int right = search(prefix, prefix[i - 1], i , false);
            if (left == -1 || right == -1) continue;
            ans = (ans + (right - left + 1) % mod) % mod;
        }
        return ans;
    }

    public int search(int[] prefix, int leftSum, int index, boolean searchLeft) {
        int N = prefix.length;
        int l = index, r = N - 2;
        int res = -1;
        while (l <= r) {
            int mid = l + (r - l) / 2;
            int midSum = prefix[mid] - prefix[index - 1];
            int rightSum = prefix[N - 1] - prefix[mid];
            if (leftSum <= midSum && midSum <= rightSum) {
                res = mid;
                if (searchLeft) r = mid - 1;
                else l = mid + 1;
            } else if (midSum < leftSum) {
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }

    /* 34. Find First and Last Position of Element in Sorted Array
    * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/
    * */
    public int[] searchRange(int[] nums, int target) {
        int n = nums.length;
        int l = 0, r = n - 1;
        int left = first(nums, l, r, target);
        int right = last(nums, l, r, target);
        return new int[] {left, right};
    }

    public int last(int[] nums, int l, int r, int target) {
        int n = nums.length;
        if (l > r) return -1;
        int mid = l + (r - l) / 2;
        if ((mid + 1 < n && (nums[mid + 1] > target && nums[mid] == target))
                || (mid == n - 1 && nums[mid] == target)) {
            return mid;
        } else if (nums[mid] == target || nums[mid] < target) {
            return last(nums, mid + 1, r, target);
        } else {
            return last(nums, l, mid - 1, target);
        }
    }

    public int first(int[] nums, int l, int r, int target) {
        int n = nums.length;
        if (l > r) return -1;
        int mid = l + (r - l) / 2;
        if ( (mid > 0 && (nums[mid - 1] < target) && nums[mid] == target)
                || (mid == 0 && nums[mid] == target)) {
            return mid;
        } else if (nums[mid] == target || nums[mid] > target) {
            return first(nums, l, mid - 1, target);
        } else {
            return first(nums, mid + 1, r, target);
        }
    }

    /* 1665. Minimum Initial Energy to Finish Tasks
     * https://leetcode.com/problems/minimum-initial-energy-to-finish-tasks/
     * */
    public int minimumEffort(int[][] tasks) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>((a, b) -> -(a[1] - a[0]) + (b[1] - b[0]));
        int low = 1, high = 1000_000_00_7;
        for (int[] t : tasks) {
            maxHeap.offer(t);
        }
        while (low < high) {
            int mid = (low + high) / 2;
            PriorityQueue<int[]> tempHeap = new PriorityQueue<>(maxHeap);
            if (isValid(mid, tempHeap)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    private boolean isValid(int minReqEnergy, PriorityQueue<int[]> maxHeap) {
        while (!maxHeap.isEmpty()) {
            int[] t = maxHeap.poll();
            if (minReqEnergy < t[1]) {
                return false;
            }
            minReqEnergy -= (t[0]);
        }
        return true;
    }

    /* 1631. Path With Minimum Effort
    * https://leetcode.com/problems/path-with-minimum-effort/
    * */
    public int minimumEffortPathBinarySearch(int[][] matrix) {
        int low = 0, high = 1000_000;
        while (low < high) {
            int mid = low + ((high - low) / 2);
            if (helper(mid, matrix)) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }

    public boolean helper(int minEffort, int[][] matrix) {
        int[][] dirs = new int[][] {{ -1, 0}, {1, 0}, {0, 1}, {0, -1}};
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {0, 0});
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] curr = queue.poll();
                if (curr[0] == m - 1 && curr[1] == n - 1) {
                    return true;
                }
                for (int[] d : dirs) {
                    int next_x = d[0] + curr[0];
                    int next_y = d[1] + curr[1];
                    if (next_x >= 0 && next_x < m && next_y >= 0 & next_y < n && !visited[next_x][next_y]) {
                        if (Math.abs(matrix[curr[0]][curr[1]] - matrix[next_x][next_y]) <= minEffort) {
                            queue.offer(new int[] {next_x, next_y});
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
        while (low <= high) {
            int curr = low * low + high * high;
            if (curr == c) return true;
            if (curr < c) {
                low++;
            } else {
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
        while (high - low > 1) {
            int mid = low + (high - low) / 2;
            if (check(position, mid, m)) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public boolean check(int[] arr, int mid, int m) {
        int r = -1;
        int count = 0;
        for (int x : arr) {
            if (r <= x) {
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

        while (low < high) {
            int mid = low + (high - low) / 2;
            int cnt = count(mid, m, n);
            if (cnt < k) {
                low = mid + 1;
            } else if (cnt >= k) {
                high = mid;
            }
        }
        return low;
    }

    public int count(int mid, int m, int n) {
        //Number of elements less than mid
        int count = 0;
        for (int i = 1; i <= m; i++) {
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

        while (low < high) {
            int mid = low + (high - low) / 2;
            int cnt = countBinarySearch(nums, mid);
            if (cnt < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public int countBinarySearch(int[] nums, int mid) {
        int cnt = 0;
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            cnt += countPairs(nums, i, n - 1, nums[i] + mid) - i - 1;
        }
        return cnt;
    }

    public int countPairs(int[] nums, int low, int high, int key) {
        if (nums[high] <= key) return high + 1;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (key >= nums[mid]) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    public int count(int[] nums, int mid) {
        int cnt = 0;
        int n = nums.length;
        for (int i = 0, j = i ; i < n; i++) {
            while (j < n && (nums[j]  - nums[i]) <= mid ) {
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

        for (int i = 0; i < n; i++) {
            pq.offer(new int[] {i, 0});
        }

        while (--k > 0) {
            int[] a = pq.poll();
            if (++a[1] < n) {
                pq.offer(a);
            }
        }
        return new int[] { arr[pq.peek()[0]], arr[n - 1 - pq.peek()[1]] };
    }

    /* Time : O( n * log(max^2) ) */
    public int[] kthSmallestPrimeFractionBinarySearch(int[] arr, int k) {
        double low = 0, high = 1;
        int p = 0, q = 1;

        for (int n = arr.length, cnt = 0; true ; cnt = 0, p = 0 ) {
            double mid = (low + high) / 2;
            for (int i = 0 , j = n - 1; i < n; i++) {
                while (j >= 0 && arr[i] > mid * arr[n - 1 - j]) {
                    j--;
                }
                cnt += (j  + 1);
                if (j >= 0 && p * arr[n - 1 - j] < q * arr[i]) {
                    p = arr[i];
                    q = arr[n - 1 - j];
                }
            }
            if (cnt < k) {
                low = mid;
            } else if (cnt > k) {
                high = mid;
            } else {
                return new int[] {p, q};
            }
        }
    }
}
