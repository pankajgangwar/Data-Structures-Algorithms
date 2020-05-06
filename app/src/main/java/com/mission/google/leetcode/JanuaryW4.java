package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.IntConsumer;

public class JanuaryW4 extends BaseHelper {

    /* https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained */
    public int findKthLargest(int[] nums, int k) {

        k = nums.length - k;
        int lo = 0;
        int hi = nums.length - 1;
        while (lo < hi) {
            final int j = partition(nums, lo, hi);
            if(j < k) {
                lo = j + 1;
            } else if (j > k) {
                hi = j - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    public int quickSelect(int[] a, int k){
        int low = 0;
        int high = a.length - 1;

        k = a.length - k;

        while(low < high) {
            int pivot = hoaresPartition(a, low, high);
            if(pivot < k){
                low = pivot + 1;
            }else if(pivot > k){
                high = pivot - 1;
            }else{
                break;
            }
        }

        return a[k];
    }

    public int quickSelectRec(int[] a, int k, int low, int high){
        if(low < high){
            int pivot = hoaresPartition(a, low, high);
            if(pivot < k){
                low = pivot + 1;
            }else if (pivot > k){
                high = pivot - 1;
            }else{
                return a[k];
            }

            quickSelectRec(a, k, low, pivot - 1);
            quickSelectRec(a, k, pivot + 1, high);
        }

        return a[k];
    }

    public int hoaresPartition(int[] a, int low, int high){
        int pivot = a[low];
        int i = low;
        int j = high + 1;

        while(true){
            do {
                i++;
            }while (i < high && a[i] < pivot);

            do {
                j--;
            }while (j > low && pivot < a[j]);

            /*while(i < high && a[++i] < pivot);
            while(j > low && pivot < a[--j]);*/
            if(i >= j) {
                break;
            }
            swap(a, i, j);
        }
        swap(a, low, j);
        return j;
    }

    public void swap(int[] a , int i, int j ){
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    private int partition(int[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        while(true) {
            while(i < hi && a[++i] < a[lo]);
            while(j > lo && a[lo] < a[--j]);
            if(i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private void exch(int[] a, int i, int j) {
        final int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    /* https://leetcode.com/problems/wiggle-sort/ */
    public void wiggleSort(int[] nums) {
        for (int i=1; i<nums.length; i++) {
            int a = nums[i-1];
            boolean status1 = (i%2 == 1);
            boolean status2 = (a > nums[i]);
            if (status1 == status2) {
                nums[i-1] = nums[i];
                nums[i] = a;
            }
        }
        /*Arrays.sort(nums);

        for (int i = 1; i < nums.length; i+=2) {
            int tmp = nums[i];
            nums[i] = nums[i+1];
            nums[i+1] = tmp;
        }*/
    }


    /* https://leetcode.com/problems/wiggle-sort-ii/ */
    public void wiggleSortII(int[] nums) {
        Arrays.sort(nums);

        int[] copy = Arrays.copyOf(nums, nums.length);

        int n = nums.length;
        int left = n - (n+1) / 2;
        int right = nums.length - 1;

        for (int i = 0; i < nums.length; i++) {
            if(i%2 == 1){
                nums[i] = copy[right--];
            }else{
                nums[i] = copy[left--];
            }
        }
    }

    /* https://leetcode.com/problems/rank-transform-of-an-array/ */
    public int[] arrayRankTransform(int[] arr) {
        int[] copy = Arrays.copyOf(arr, arr.length);
        int[] result = new int[arr.length];
        Set<Integer> sets = new HashSet<Integer>();
        int dup = 0;
        for (int i = 0; i < copy.length; i++) {
            if(!sets.add(copy[i])){
                dup++;
            }
        }

        int[] res = new int[arr.length - dup];
        Iterator<Integer> it = sets.iterator();
        int idx = 0;
        while (it.hasNext()){
            res[idx] = it.next();
            idx++;
        }
        Arrays.sort(res);
        for (int i = 0; i < arr.length; i++) {
            int found = Arrays.binarySearch(res, arr[i]);
            result[i] = found + 1;
        }
        return result;
    }

    /* https://leetcode.com/problems/break-a-palindrome/ */
    public String breakPalindromeGreedy(String palindrome) {
        StringBuilder builder = new StringBuilder(palindrome);
        String result = "";
        for (int i = 0; i < palindrome.length(); i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                char tmp = builder.charAt(i);
                builder.setCharAt(i, j);
                if(result.length() > 0){
                    if( result.compareTo(builder.toString()) > 0){
                        if(!isPalindrome(builder.toString())){
                            result = builder.toString();
                        }
                    }
                }else{
                    if(!isPalindrome(builder.toString())){
                        result = builder.toString();
                    }
                }
                builder.setCharAt(i, tmp);
            }
        }
        return result;
    }

    public String breakPalindrome(String palindrome) {
        char[] arr = palindrome.toCharArray();
        int n = arr.length;
        for(int i =0; i < n / 2; i++){
            if(arr[i] != 'a'){
                arr[i] = 'a';
                return new String(arr);
            }
        }
        arr[n-1] = 'b';
        return n < 2 ? "" : new String(arr);
    }

    /* https://leetcode.com/problems/sort-the-matrix-diagonally/ */
    public int[][] diagonalShorter(int[][] A) {
        int m = A.length, n = A[0].length;
        HashMap<Integer, PriorityQueue<Integer>> d = new HashMap<>();
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                d.putIfAbsent(i - j, new PriorityQueue<>());
                d.get(i - j).add(A[i][j]);
            }
        }
        for (int i = 0; i < m; ++i)
            for (int j = 0; j < n; ++j)
                A[i][j] = d.get(i - j).poll();
        return A;
    }

    public int[][] diagonalSort(int[][] matrix) {
        Queue<int[]> q1 = new LinkedList<>();
        Queue<int[]> q2 = new LinkedList<>();

        if(matrix == null || matrix.length == 0 ) return new int[0][0];

        int[][] dirs = new int[][]{{-1, 0}, {0, 1}};

        int m = matrix.length;
        int n = matrix[0].length;

        q1.offer(new int[]{m-1, 0});

        int[][] result = new int[m][n];

        boolean[][] visited = new boolean[m][n];

        visited[m-1][0] = true;

        PriorityQueue<Integer> pq1 = new PriorityQueue<>();
        PriorityQueue<Integer> pq2 = new PriorityQueue<>();
        pq1.offer(matrix[m-1][0]);

        while (!q1.isEmpty() || !q2.isEmpty()) {
            while (!q1.isEmpty()) {
                int[] curr = q1.poll();
                int min = pq1.poll();
                result[curr[0]][curr[1]] = min;
                for (int[] dir : dirs) {
                    int next_x = curr[0] + dir[0];
                    int next_y = curr[1] + dir[1];

                    if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && !visited[next_x][next_y]) {
                        q2.offer(new int[]{next_x, next_y});
                        pq2.offer(matrix[next_x][next_y]);
                        visited[next_x][next_y] = true;
                    }
                }
            }
            while (!q2.isEmpty()) {
                int[] curr = q2.poll();
                int min = pq2.poll();
                result[curr[0]][curr[1]] = min;
                for (int[] dir : dirs) {
                    int next_x = curr[0] + dir[0];
                    int next_y = curr[1] + dir[1];
                    if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n && !visited[next_x][next_y]) {
                        q1.offer(new int[]{next_x, next_y});
                        pq1.offer(matrix[next_x][next_y]);
                        visited[next_x][next_y] = true;
                    }
                }
            }
        }
        return result;
    }

    /* https://leetcode.com/problems/remove-palindromic-subsequences/ */
    public int removePalindromeSub(String s) {
        if(s == null || s.length() == 0) return 0;
        if(s.length() == 1) return 1;
        if(isPalindrome(s)){
            return 1;
        }
        return 2;
    }

    /* https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/
    *  Floyd-Warshall
    * */
    public int findTheCity(int n, int[][] edges, int distanceThreshold) {
        int[][] dist = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(dist[i], (int)1e8); // Integer.MAX_VALUE
            dist[i][i] = 0;
        }

        for (int i = 0; i < edges.length; i++) {
            int[] edge = edges[i];
            int src = edge[0];
            int dst = edge[1];
            int minDist = edge[2];
            dist[src][dst] = minDist;
            dist[dst][src] = minDist;
        }

        for (int k = 0; k < n; k++) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    //if(dist[i][k] != Integer.MAX_VALUE && dist[k][j] != Integer.MAX_VALUE)
                        dist[i][j] = Math.min(dist[i][j], dist[i][k] + dist[k][j]);
                }
            }
        }

        int min = Integer.MAX_VALUE, res = -1;
        for (int i = 0; i < n; i++) {
            int reachable = 0;
            for (int j = 0; j < n; j++) {
                if(dist[i][j] <= distanceThreshold){
                    reachable++;
                }
            }
            if(reachable <= min){
                min = reachable;
                res = i;
            }
        }
        System.out.println("res = " + res);
        return res;
    }

    /* https://leetcode.com/problems/path-with-maximum-minimum-value/
    *  Maximum points collections
    *  Dijkstra's algo
    * */
    public int maximumMinimumPath(int[][] matrix) {
        return maximumMinimumPathUnionFind(matrix);
    }
    public int maximumMinimumPathDijkstra2(int[][] matrix) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        PriorityQueue<int[]> q = new PriorityQueue<>( (a,b) -> b[2] - a[2] ) ;
        q.offer(new int[]{0,0, matrix[0][0]});

        int rows = matrix.length;
        int cols = matrix[0].length;

        boolean[][] visited = new boolean[rows][cols];

        visited[0][0] = true;
        while (!q.isEmpty()){
            int[] curr = q.poll();

            if(curr[0] == rows-1 && curr[1] == cols-1)
                return curr[2];

            for (int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols || visited[next_x][next_y])
                    continue;

                visited[next_x][next_y] = true;
                q.offer(new int[]{next_x, next_y, Math.min(curr[2], matrix[next_x][next_y]) } );
            }
        }

        return -1;
    }

    public int maximumMinimumPathDijkstra1(int[][] matrix) {
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        PriorityQueue<int[]> q = new PriorityQueue<>( (a,b) -> b[2] - a[2] );
        q.offer(new int[]{0,0, matrix[0][0]});

        int rows = matrix.length;
        int cols = matrix[0].length;

        ArrayList<Integer> result = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];

        visited[0][0] = true;
        int maxScore = matrix[0][0];
        while (!q.isEmpty()){
            int[] curr = q.poll();

            result.add(matrix[curr[0]][curr[1]]);

            maxScore = Math.min(maxScore, curr[2]);

            if(curr[0] == rows-1 && curr[1] == cols-1) {
                System.out.println("visited = " + result.toString());
                return maxScore;
            }

            for (int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols || visited[next_x][next_y])
                    continue;

                visited[next_x][next_y] = true;
                q.offer(new int[]{next_x, next_y, matrix[next_x][next_y]});
            }
        }

        return -1;
    }

    public int maximumMinimumPathUnionFind(int[][] matrix) {
        List<int[]> coord = new ArrayList<>();

        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                coord.add(new int[]{i,j});
            }
        }
        Collections.sort(coord, (a,b) -> matrix[b[0]][b[1]] - matrix[a[0]][a[1]]);
        int[][] dirs = new int[][]{{-1,0},{1,0},{0,1},{0,-1}};
        boolean[][] visited = new boolean[rows][cols];

        UnionFind unionFind = new UnionFind(rows, cols);
        for (int[] curr : coord){
            visited[curr[0]][curr[1]] = true;

            for (int[] dir : dirs){
                int next_x = dir[0] + curr[0];
                int next_y = dir[1] + curr[1];

                if(next_x < 0 || next_x >= rows || next_y < 0 || next_y >= cols || !visited[next_x][next_y])
                    continue;

                unionFind.union(curr[0], curr[1], next_x, next_y);
            }

            if(unionFind.find(0,0) == unionFind.find(rows -1, cols -1)) return matrix[curr[0]][curr[1]];
        }
        return -1;
    }

    class UnionFind {
        private int[] parent, rank;
        int row, col;

        public UnionFind(int rows, int cols) {
            parent = new int[rows * cols];
            rank = new int[rows * cols];
            this.row = rows;
            this.col = cols;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    parent[i* cols + j] = i * cols + j;
                    rank[i * cols + j] = 1;
                }
            }
        }

        public int find(int x, int y) {
            int p = x * col + y;
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }

        public void union(int x1, int y1, int x2, int y2) {
            int rootP = find(x1, y1);
            int rootQ = find(x2, y2);
            if (rootP == rootQ) return;//connected
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            }
            else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
        }
    }

    /* https://leetcode.com/problems/stream-of-characters/
    * Aho-Corasick Algorithm implementation
    * */
    public class Node {
        private int NUMBER_OF_CHARACTER = 26;
        Node[] children = new Node[NUMBER_OF_CHARACTER];
        boolean isEndOfWord = false;

        public Node(){
            isEndOfWord = false;
            for(int i=0; i< NUMBER_OF_CHARACTER ; i++)
                children[i]=null;
        }

        public Node getNode(char c){
            return children[getCharIndex(c)];
        }

        public int getCharIndex(char c){
            return c-'a';
        }
    }

    class StreamChecker {
        Node root = new Node();;
        public void insert(String key){
            int length = key.length();
            int index;

            Node pCrawl = root;

            for(int i = length - 1; i >= 0; i-- ){
                index = key.charAt(i) - 'a';
                if(pCrawl.children[index] == null){
                    pCrawl.children[index] = new Node();
                }
                pCrawl = pCrawl.children[index];
            }
            pCrawl.isEndOfWord = true;
        }


        StringBuilder builder = new StringBuilder();

        public StreamChecker(String[] words) {
            for(String word : words){
                insert(word);
            }
        }

        public boolean query(char letter) {
            builder.append(letter);
            Node pCrawl = root;

            for (int i = builder.length() - 1; i >= 0 && pCrawl != null ; i--) {

                char c = builder.charAt(i);

                int index = c - 'a';

                pCrawl = pCrawl.children[index];

                if(pCrawl != null && pCrawl.isEndOfWord){
                    return true;
                }
            }
            return false;
        }
    }

    /* https://leetcode.com/problems/filter-restaurants-by-vegan-friendly-price-and-distance/ */
    public class Restaurants  {
        int id;
        int maxPrice;
        int maxDistance;
        int rating;
        int vegan;

        public Restaurants(int id, int rating, int vegan, int maxPrice, int maxDistance) {
            this.id = id;
            this.maxPrice = maxPrice;
            this.maxDistance = maxDistance;
            this.rating = rating;
            this.vegan = vegan;
        }
    }
    public List<Integer> filterRestaurants(int[][] restaurants, int veganFriendly, int maxPrice, int maxDistance) {
        List<Restaurants> resList = new ArrayList<>();
        for (int i = 0; i < restaurants.length; i++) {
            int[] res = restaurants[i];
            Restaurants resObj = new Restaurants(res[0], res[1], res[2], res[3], res[4]);
            resList.add(resObj);
        }
        Collections.sort(resList, (r1, r2) -> {
            int comp = Integer.compare(r1.rating, r2.rating);
            System.out.println("Rating are same " + r1.rating + " " + r2.rating + " for " + r1.id + " " + r2.id);
            if(comp == 0){
                return -Integer.compare(r1.id, r2.id);
            }
            return -comp;
        });

        List<Integer> res = new ArrayList<>();

        for (int i = 0; i < resList.size(); i++) {
            Restaurants obj = resList.get(i);
            if(obj.vegan == veganFriendly && obj.maxPrice <= maxPrice && obj.maxDistance <= maxDistance){
                res.add(obj.id);
            }
        }
        return res;
    }

    /* https://leetcode.com/problems/longest-line-of-consecutive-one-in-matrix/ */
    class FourDim {
        int h, v, d, ad;
    }

    public int longestLine(int[][] matrix) {
        if(matrix == null || matrix.length == 0) return 0;
        FourDim dp[][] = new FourDim[matrix.length+1][matrix[0].length+1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                FourDim dim = new FourDim();
                dim.ad = 0;
                dim.d = 0;
                dim.h = 0;
                dim.v = 0;
                dp[i][j] = dim;
            }
        }

        int max = 0;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if(matrix[i-1][j-1] == 0) continue;
                FourDim dim = new FourDim();
                dim.h = dp[i][j-1].h + 1;
                dim.v = dp[i-1][j].v + 1;
                dim.d = dp[i-1][j-1].d + 1;

                if(i >= 0 && j < dp[0].length - 1)
                    dim.ad = dp[i-1][j+1].ad + 1;

                dp[i][j] = dim;
                int max1 = Math.max(dim.h, dim.v);
                int max2 = Math.max(dim.d, dim.ad);
                int max3 = Math.max(max1, max2);

                max = Math.max(max3, max);
            }
        }
        return max;
    }

    /* https://leetcode.com/problems/h-index/
    *  Revisit
    * https://en.wikipedia.org/wiki/H-index
    * */
    public int hIndex(int[] arr) {
        int n = arr.length;
        int[] bucket = new int[n + 1];
        for (int a : arr){
            if(a >= n){
                bucket[n]++;
            }else{
                bucket[a]++;
            }
        }

        int count = 0;
        for (int i = n; i >= 0 ; --i) {
            count += bucket[i];
            if(count >= i){
                return i;
            }
        }
        return 0;
    }

    /* https://leetcode.com/problems/time-based-key-value-store/*/
    class TimeMap {

        /** Initialize your data structure here. */
        HashMap<String, TreeMap<Integer, String>> map;
        public TimeMap() {
            map = new HashMap<>();
        }

        public void set(String key, String value, int timestamp) {
            if(!map.containsKey(key)){
                map.put(key, new TreeMap<>());
            }
            map.get(key).put(timestamp, value);
        }

        public String get(String key, int timestamp) {
            TreeMap<Integer, String> timeMap = map.get(key);
            if(timeMap == null){
                return "";
            }
            Integer floorKey = timeMap.floorKey(timestamp);
            if(floorKey == null){
                return "";
            }
            return  timeMap.get(floorKey);
        }
    }

    /* https://leetcode.com/problems/shortest-distance-to-target-color/ */
    public List<Integer> shortestDistanceColor(int[] colors, int[][] queries) {
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<>();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < colors.length; i++) {
            ArrayList<Integer> list = new ArrayList<>();
            if(map.containsKey(colors[i])){
                list = map.get(colors[i]);
            }
            list.add(i);
        }

        for (int[] q : queries){
            int idx = q[0];
            int c = q[1];
            if(!map.containsKey(c)){
                res.add(-1);
            }else{
                res.add(binarySearch(map.get(c), idx));
            }
        }
        return res;
    }

    public int binarySearch(ArrayList<Integer> list, int searchIdx){
        int low = 0;
        int high = list.size() -1;

        while (low < high){
            int mid = low + (high - low ) / 2;
            if(list.get(mid) < searchIdx){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        int res = low;
        if(low - 1 >= 0 && searchIdx - list.get(low - 1) < list.get(searchIdx) - searchIdx ){
            res = low -1;
        }
        return Math.abs(list.get(res) - searchIdx);
    }


    public void quickSort(int[] arr, int low, int high){
        if(low < high){
            int p = hoaresPartition(arr, low, high);
            quickSort(arr, low, p -1 );
            quickSort(arr, p + 1, high);
        }
    }

    
    /*  https://leetcode.com/problems/strobogrammatic-number-ii/
        Input:  n = 2
        Output: ["11","69","88","96"]s
    */
    public List<String> findStrobogrammatic(int n) {
        return helper(n, n);
    }

    private List<String> helper(int n, int m) {
        if(n == 0) return new ArrayList<>(Arrays.asList(""));
        if(n == 1) return new ArrayList<>(Arrays.asList("0", "1" ,"8"));

        List<String> list = helper(n-2, m);
        List<String> res = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            String s = list.get(i);
            if(n != m) res.add("0" + s + "0");

            res.add("1" + s + "1");
            res.add("6" + s + "9");
            res.add("8" + s + "8");
            res.add("9" + s + "6");
        }
        return res;
    }

    /* https://leetcode.com/problems/strobogrammatic-number-iii/ */
    public int strobogrammaticInRange(String low, String high) {

        List<String> res = new ArrayList<>();
        for (int i = low.length(); i <= high.length() ; i++) {
            res.addAll(helper(i, i));
        }

        int count = 0;
        for (int i = 0; i < res.size(); i++) {
            String s1 = res.get(i);
            Integer cmp1 = new BigInteger(s1).compareTo(new BigInteger(low));
            Integer cmp2 = new BigInteger(s1).compareTo(new BigInteger(high));
            if(cmp1 >= 0 && cmp2 <= 0){
                count++;
            }
        }
        return count;
    }

    public boolean isStrobogrammatic(String num) {
        Map<Integer, Integer> map = new HashMap<>();

        map.put(1, 1);
        map.put(6, 9);
        map.put(8, 8);
        map.put(9, 6);
        map.put(0, 0);

        StringBuilder builder = new StringBuilder();

        for(int i = num.length()-1; i >= 0; --i){
            int curr = num.charAt(i) - '0';
            if(map.containsKey(curr)){
                builder.append(map.get(curr));
            }else{
                return false;
            }
        }
        return builder.toString().equals(num);
    }

    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/ */
    public int minimumDistance(String word) {
        return 0;
    }

    /* https://leetcode.com/problems/tree-diameter/ */
    public int treeDiameter(int[][] edges) {
        int n = edges.length;
        List<Integer>[] graph = new ArrayList[n+1];

        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges.length; i++) {
            int src = edges[i][0];
            int dst = edges[i][1];
            graph[src].add(dst);
            graph[dst].add(src);
        }
        diameterOfBinaryTree(graph, 0, -1);
        return diameter;
    }

    int diameter = 0;
    public int diameterOfBinaryTree(List<Integer>[] graph, int root, int parent) {
        maxDepth(graph, root, parent);
        return diameter;
    }

    private int maxDepth(List<Integer>[] graph, int root,int parent) {
        int maxDepth1st = 0, maxDepth2nd = 0;

        for (int i = 0; i < graph[root].size(); i++) {
            int child = graph[root].get(i);
            if(child == parent)continue;
            int childDepth = maxDepth(graph, child, root);
            if (childDepth > maxDepth1st) {
                maxDepth2nd = maxDepth1st;
                maxDepth1st = childDepth;
            } else if (childDepth > maxDepth2nd) {
                maxDepth2nd = childDepth;
            }
        }

        int longestPathThroughRoot = maxDepth1st + maxDepth2nd; // Sum of the top 2 highest depths is the longest path through this root
        diameter = Math.max(diameter, longestPathThroughRoot);
        return maxDepth1st + 1;
    }

    public static void main(String[] args) {
        JanuaryW4 w4 = new JanuaryW4();
        int[] nums = new int[]{3,5,2,1,6,4};
        //w4.findKthLargest(nums, 5);
        //w4.wiggleSort(nums);
        //w4.arrayRankTransform();
        //w4.breakPalindrome("abccba");
        int[][] mat = new int[][]{{3,3,1,1},{2,2,1,2},{1,1,1,2}};
        //w4.diagonalSort(mat);
        // w4.removePalindromeSub("");
        int[][] edges = new int[][]{
                {0,1,3},{1,2,1},{1,3,4},{2,3,1}
        };
        //w4.findTheCity(4, edges, 4 );
        int[][] matrix = new int[][]{
                {0,1,1,0},{0,1,1,0},{0,0,0,1}
        };
        //w4.maximumMinimumPath(matrix);
        //w4.longestLine(matrix);

        String num = "214813762935";
        int[] arr = new int[num.length()];

        for (int i = 0; i < num.length(); i++) {
            arr[i] = num.charAt(i) - '0';
        }
        //Arrays.sort(arr);
        //w4.quickSort(arr, 0,arr.length-1);
        Integer cmp = "69".compareTo("100");
        System.out.println("cmp = " + cmp);
        w4.strobogrammaticInRange("50","100");
    }

}
