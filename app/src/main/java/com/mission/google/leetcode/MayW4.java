package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

public class MayW4 {

    // TODO: 5/15/2020  
    /* https://leetcode.com/problems/minimum-cost-for-tickets/ */

    /* https://leetcode.com/problems/couples-holding-hands/ */
    /* https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/*/
    /* https://www.codechef.com/problems/COUPON */
    /* https://leetcode.com/problems/brick-wall/ */

    /* DP on trees */
    /* 
       https://codeforces.com/blog/entry/20935
       https://www.spoj.com/problems/PT07X/
       https://leetcode.com/problems/sum-of-distances-in-tree/ 
       https://leetcode.com/problems/minimum-cost-tree-from-leaf-values/
       https://leetcode.com/problems/unique-binary-search-trees-ii/
    */

    public static void main(String[] args) {
        MayW4 test = new MayW4();
        int[][] grid = new int[][]{
                {0,-3}
        };
        
        //boolean res = test.isPalindrome("");
        int[][] t = new int[][]{{0,1,10},{2,0,5}};
        FileSystem fileSystem = new FileSystem();
        /* ["FileSystem","mkdir","ls","ls","mkdir","ls","ls","addContentToFile","readContentFromFile","ls","readContentFromFile"]
           [[],["/zijzllb"],["/"],["/zijzllb"],["/r"],["/"],["/r"],["/zijzllb/hfktg","d"],["/zijzllb/hfktg"],["/"],["/zijzllb/hfktg"]]
*/
        /*fileSystem.mkdir("/goowmfn");
        fileSystem.ls("/goowmfn");
        fileSystem.ls("/");
        fileSystem.mkdir("/z");
        fileSystem.ls("/");
        fileSystem.ls("/");
        fileSystem.addContentToFile("/goowmfn/c", "shetopcy");
        fileSystem.ls("/z");
        fileSystem.ls("/goowmfn/c");
        fileSystem.readContentFromFile("/goowmfn");*/

        //fileSystem.addContentToFile("/a/b/c/d", "hello");
        //fileSystem.ls("/");
        //fileSystem.readContentFromFile("/a/b/c/d");

        //int score = test.scoreOfParentheses("(())()");
        int[][] prerequisites = new int[][]{{2,3},{2,1},{0,3},{0,1}};
        int[][] queries = new int[][]{{0,1},{0,3},{2,3},{3,0},{2,0},{0,2}};
        test.checkIfPrerequisite(5, prerequisites, queries);
        //System.out.println("score = " + score);
    }

    /*
    LC : 797
    https://leetcode.com/problems/all-paths-from-source-to-target
    Input: [[1,2], [3], [3], []]
    */
    public List<List<Integer>> allPathsSourceTarget(int[][] edges) {
        int n = edges.length;
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n; i++) {
            graph[i] = new LinkedList<>();
        }

        for (int i = 0; i < n; i++) {
            int[] curr = edges[i];
            for (int j = 0; j < curr.length; j++) {
                graph[i].add(curr[j]);
            }
        }
        boolean[] visited = new boolean[n];
        findPaths(graph, 0, n-1, new ArrayList<>(), visited);
        return allPaths;
    }

    List<List<Integer>> allPaths = new ArrayList<>();
    public void findPaths(LinkedList<Integer>[] graph, int src, int dest, ArrayList<Integer> curr, boolean[] visited){
        visited[src] = true;
        curr.add(src);
        if(src == dest){
            allPaths.add(new ArrayList<>(curr));
            return;
        }
        for (int next : graph[src]){
            if(visited[next] && next != dest) continue;
            findPaths(graph, next, dest, curr, visited);
            curr.remove(curr.size()-1);
            visited[next] = false;
        }
    }

    /*
    LC : 1465
    https://leetcode.com/problems/maximum-area-of-a-piece-of-cake-after-horizontal-and-vertical-cuts/
    */
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        List<Integer> hList = new ArrayList<>();
        hList.add(0);
        hList.add(h);
        for (int x : horizontalCuts){
            hList.add(x);
        }
        Collections.sort(hList);

        List<Integer> vList = new ArrayList<>();
        vList.add(0);
        vList.add(w);

        for (int x : verticalCuts){
            vList.add(x);
        }
        Collections.sort(vList);
        long H = 0;
        for (int i = 1; i < hList.size() ; i++) {
            H = Math.max(H, (long)hList.get(i) - hList.get(i-1));
        }
        long W = 0;
        for (int i = 1; i < vList.size() ; i++) {
            W = Math.max(W, (long)vList.get(i) - vList.get(i-1));
        }
        return (int) (H * W % (int)(1e9+7));
    }

    /*
     LC : 1466
     https://leetcode.com/problems/reorder-routes-to-make-all-paths-lead-to-the-city-zero/
     */
    public int minReorder(int n, int[][] connections) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < connections.length; i++) {
            int src = connections[i][0];
            int dst = connections[i][1];
            map.putIfAbsent(src, new ArrayList<>());
            map.get(src).add(dst);
            map.putIfAbsent(dst, new ArrayList<>());
            map.get(dst).add(-src);
        }

        boolean[] visited = new boolean[n];
        return helper(map, 0, visited);
    }

    public int helper(HashMap<Integer, List<Integer>> map, int src, boolean[] visited){
        int change = 0;
        visited[src] = true;
        for (int next : map.get(src)){
            if(!visited[Math.abs(next)]){
                change += helper(map, Math.abs((next)), visited);
                change += next > 0 ? 1 : 0;
            }
        }
        return change;
    }

    /*
    LC : 1464
    https://leetcode.com/problems/maximum-product-of-two-elements-in-an-array/
    */
    public int maxProduct(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        return (nums[n-1]-1) * (nums[n-2]-1);
    }

    /*
    LC : 1463
    https://leetcode.com/problems/cherry-pickup-ii/
    */
    public int cherryPickupII(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        return help(grid, 0, 0, n - 1, m ,n, new Integer[m][n][n]);
    }

    public int help(int[][] grid, int r1, int c1, int c2, int m, int n, Integer dp[][][]){
        if(c1 < 0 || c1 == n || c2 < 0 || c2 == n){
            return Integer.MIN_VALUE;
        }

        if(r1 == m){
            return 0;
        }

        if(dp[r1][c1][c2] != null){
            return dp[r1][c1][c2];
        }

        int cherries = 0;

        if(c1 == c2){
            cherries = grid[r1][c1];
        }else{
            cherries = grid[r1][c1] + grid[r1][c2];
        }

        int ans = 0;
        for (int i = -1; i < 2 ; i++) {
            for (int j = -1; j < 2 ; j++) {
                ans = Math.max(ans, help(grid, r1 + 1, c1 + i, c2 + j, m, n, dp ));
            }
        }

        cherries += ans;

        dp[r1][c1][c2] = new Integer(cherries);

        return dp[r1][c1][c2];
    }


    /*
    LC : 741
    https://leetcode.com/problems/cherry-pickup/
    */
    public int cherryPickup(int[][] grid) {
        if(grid == null || grid.length == 0 || grid[0].length == 0 )
            return 0;

        int m = grid.length;
        int n = grid[0].length;

        if(grid[0][0] == -1 || grid[m -1][n-1] == -1){
            return 0;
        }

        int[][] paths = new int[][]{{1,0},{0,1}};

        int[][] reverse_paths = new int[][]{{-1,0},{0,-1}};

        //return Math.max(cherrypickup(grid, 0, 0, 0, 0, m ,n), 0);

        return Math.max(cherrypickupMemo(grid, 0, 0, 0, 0, m ,n, new Integer[n][n][n][n]), 0);
        //int cherries = helperBackTracking(grid, 0, 0, paths, 0) + helperBackTracking(grid, m-1, n-1, reverse_paths, 0);
        //return cherries;
    }

    public int cherrypickupMemo(int[][] grid, int r1, int c1, int r2, int c2, int m, int n, Integer dp[][][][]){
        if(r1 < 0 || r1 >= m || c1 < 0 || c1 >= n || grid[r1][c1] == -1 || r2 < 0 || r2 >= m || c2 < 0 || c2 >= n || grid[r2][c2] == -1){
            return Integer.MIN_VALUE;
        }

        if(dp[r1][c1][r2][c2] != null){
            return dp[r1][c1][r2][c2];
        }

        if(r1 == m - 1 && c1 == n-1){
            return grid[r1][c1];
        }

        if(r2 == m - 1 && c2 == n-1){
            return grid[r2][c2];
        }

        int cherries = 0;

        if(r1 == r2 && c1 == c2){
            cherries = grid[r1][c1];
        }else{
            cherries = grid[r1][c1] + grid[r2][c2];
        }

        cherries += Math.max(Math.max(cherrypickupMemo(grid, r1 + 1, c1, r2 + 1, c2, m, n, dp),
                cherrypickupMemo(grid, r1 + 1, c1, r2, c2 + 1, m, n, dp)),
                Math.max(cherrypickupMemo(grid, r1, c1 + 1, r2 + 1, c2, m, n, dp),
                        cherrypickupMemo(grid, r1, c1 + 1, r2, c2 + 1, m, n, dp)));

        dp[r1][c1][r2][c2] = new Integer(cherries);

        return dp[r1][c1][r2][c2];
    }

    // TLE
    public int cherrypickup(int[][] grid, int r1, int c1, int r2, int c2, int m, int n){
        if(r1 < 0 || r1 >= m || c1 < 0 || c1 >= n || grid[r1][c1] == -1 || r2 < 0 || r2 >= m || c2 < 0 || c2 >= n || grid[r2][c2] == -1){
            return Integer.MIN_VALUE;
        }

        if(r1 == m - 1 && c1 == n-1){
            return grid[r1][c1];
        }

        if(r2 == m - 1 && c2 == n-1){
            return grid[r2][c2];
        }

        int cherries = 0;

        if(r1 == r2 && c1 == c2){
            cherries = grid[r1][c1];
        }else{
            cherries = grid[r1][c1] + grid[r2][c2];
        }

        cherries += Math.max(Math.max(cherrypickup(grid, r1 + 1, c1, r2 + 1, c2, m, n), cherrypickup(grid, r1 + 1, c1, r2, c2 + 1, m, n)),
                Math.max(cherrypickup(grid, r1, c1 + 1, r2 + 1, c2, m, n), cherrypickup(grid, r1, c1 + 1, r2, c2 + 1, m, n)));

        return cherries;
    }

    int max_cherries = 0;

    // Won't work
    public int helperBackTracking(int[][] grid, int curr_x, int curr_y, int[][] paths, int cherries){
        int m = grid.length;
        int n = grid[0].length;

        if(curr_x < 0 || curr_x >= m || curr_y < 0 || curr_y >= n || grid[curr_x][curr_y] == -1){
            return cherries;
        }

        int max_cherries = cherries;

        if(grid[curr_x][curr_y] == 1 || grid[curr_x][curr_y] == 0){
            int tmp = grid[curr_x][curr_y];

            cherries += tmp;

            grid[curr_x][curr_y] = 0;

            cherries = Math.max(helperBackTracking(grid, curr_x + paths[0][0], curr_y + paths[0][1], paths, cherries),
                    helperBackTracking(grid, curr_x + paths[1][0], curr_y + paths[1][1], paths, cherries));

            if(cherries > max_cherries) {
                return cherries;
            }

            grid[curr_x][curr_y] = tmp;
        }
        return Math.max(max_cherries, cherries);
    }

    /*
    LC : 1462
    https://leetcode.com/problems/course-schedule-iv/
    */
    public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
        boolean[][] connected = new boolean[n][n];
        for (int[] p : prerequisites)
            connected[p[0]][p[1]] = true; // p[0] -> p[1]
        for (int k = 0; k < n; k++)
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    connected[i][j] = connected[i][j] || connected[i][k] && connected[k][j];

        List<Boolean> ans = new ArrayList<>();
        for (int[] q : queries)
            ans.add(connected[q[0]][q[1]]);
        return ans;
    }

    /*
    LC : 1460
    https://leetcode.com/problems/make-two-arrays-equal-by-reversing-sub-arrays/
    */
    public boolean canBeEqual(int[] target, int[] arr) {
        if(target.length != arr.length) return false;
        Arrays.sort(target);
        Arrays.sort(arr);
        if(!Arrays.equals(target, arr)){
            return false;
        }
        return true;
    }

    /* LC :
       https://leetcode.com/problems/check-if-a-string-contains-all-binary-codes-of-size-k/
     */
    public boolean hasAllCodes(String s, int k) {
        HashSet<String> allComb = new HashSet<>();
        for (int i = 0; i <= s.length() - k ; i++) {
            allComb.add(s.substring(i, i + k));
        }
        return allComb.size() == Math.pow(2, k);
    }

    /*
    LC : 856
    https://leetcode.com/problems/score-of-parentheses/
    */
    public int scoreOfParentheses(String s) {
        return helper(s);
    }

    public int helper(String s){
        int sum = 0;
        for(int i = 0; i < s.length(); ){
            if(s.charAt(i) == '(' && s.charAt(i+1) == ')'){
                sum += 1;
                i += 2;
            }else{
                Stack<Character> stack = new Stack<>();
                stack.push(s.charAt(i));
                int start = i;
                while(!stack.isEmpty()){
                    char ch = s.charAt(++start);
                    if(stack.peek() == '(' && ch == ')'){
                        stack.pop();
                    }else {
                        stack.push(ch);
                    }
                }
                String sub = s.substring(i + 1, start);
                int res = helper(sub);
                sum += res * 2;
                i = start + 1;
            }
        }
        return sum;
    }

    /*
    LC : 635
    https://leetcode.com/problems/design-log-storage-system/
    */
    class LogSystem {
        List<String[]> timeStamps;
        List<String> units = Arrays.asList("Year", "Month","Day","Hour","Minutes","Second");
        int[] indices = new int[]{4, 7, 10, 13, 16, 19};
        public LogSystem() {
            timeStamps = new LinkedList<>();
        }

        public void put(int id, String timestamp) {
            timeStamps.add(new String[]{Integer.toString(id), timestamp});
        }

        public List<Integer> retrieve(String s, String e, String gra) {
            int idx = indices[units.indexOf(gra)];
            List<Integer> res = new ArrayList<>();
            for (String[] curr : timeStamps){
                if((curr[1].substring(0, idx).compareTo(s.substring(0, idx)) >= 0) &&
                        curr[1].substring(0, idx).compareTo(e.substring(0, idx)) <= 0 ){
                    res.add(Integer.parseInt(curr[0]));
                }
            }
            return res;
        }
    }

    /*
    LC : 588
    https://leetcode.com/problems/design-in-memory-file-system/
    */
    static class FileSystem {
        class File {
            String name;
            boolean isFolder = false;
            Set<File> directories;
            StringBuilder content;

            public File(String name, boolean isFolder){
                this.name = name;
                this.isFolder = isFolder;
                directories = new HashSet<>();
                content = new StringBuilder();
            }

            public void addFolder(File directory){
                directories.add(directory);
            }

            public boolean isFolder(){
                return isFolder;
            }

            public void addContentToFile(String newContent){
                content.append(newContent);
            }

            public String getContent(){
                return content.toString();
            }

            public Set<File> listFiles(){
                return directories;
            }

            public String getName(){
                return name;
            }

            public void addFile(File file){
                directories.add(file);
            }
        }

        File root;
        public FileSystem() {
            root = new File("/", true);
        }

        public List<String> ls(String path) {
            List<String> res = new ArrayList<>();
            if(path.equals(root.getName())){
                for(File file : root.listFiles()){
                    res.add(file.getName());
                }
                Collections.sort(res);
                return res;
            }
            String[] arr = path.split("/");
            String[] newArr = Arrays.copyOfRange(arr, 1, arr.length);
            File ans = getDirectory(newArr, 0, root);
            if(!ans.isFolder()){
                res.add(ans.getName());
            }else{
                for(File file : ans.listFiles()){
                    res.add(file.getName());
                }
            }
            Collections.sort(res);
            return res;
        }

        public File getDirectory(String[] files, int idx, File root){
            if(idx == files.length){
                return root;
            }
            String curr = files[idx];
            File folder = null;
            File file = null;
            for(File subFolder : root.listFiles()){
                if(subFolder.getName().equals(curr) && subFolder.isFolder()){
                    folder = subFolder;
                }else if(subFolder.getName().equals(curr) && !subFolder.isFolder()){
                    file = subFolder;
                }
            }
            if(file != null){
                return file;
            }
            return getDirectory(files, idx + 1, folder);
        }

        public void mkdir(String path) {
            String[] arr = path.split("/");
            String[] newArr = Arrays.copyOfRange(arr, 1, arr.length);
            makeDirHelper(newArr, 0, root);
        }

        public void makeDirHelper(String[] files, int idx, File currFolder){
            if(idx == files.length){
                return;
            }
            String currFile = files[idx];
            File newFolder = null;
            for(File curr : currFolder.listFiles()){
                if(curr.getName().equals(currFile) && curr.isFolder()){
                    newFolder = curr;
                    break;
                }
            }
            if(newFolder == null){
                newFolder = new File(currFile, true);
            }
            currFolder.addFolder(newFolder);
            makeDirHelper(files, idx + 1, newFolder);
        }

        public void addContentToFile(String filePath, String content) {
            String[] arr = filePath.split("/");
            String[] newArr = Arrays.copyOfRange(arr, 1, arr.length - 1);
            File curr = getDirectory(newArr, 0, root);
            String fileToAddContent = arr[arr.length -1];

            boolean found = false;
            for(File file : curr.listFiles()){
                if(file.getName().equals(fileToAddContent)){
                    file.addContentToFile(content);
                    curr.addFile(file);
                    found = true;
                    break;
                }
            }
            if(!found){
                File newFile = new File(fileToAddContent, false);
                newFile.addContentToFile(content);
                curr.addFile(newFile);
            }
        }

        public String readContentFromFile(String filePath) {
            String[] arr = filePath.split("/");
            String[] newArr = Arrays.copyOfRange(arr, 1, arr.length - 1);
            File curr = getDirectory(newArr, 0, root);
            String fileToReadContent = arr[arr.length - 1];
            String content = "";

            for (File file : curr.listFiles()) {
                if (file.getName().equals(fileToReadContent)) {
                    content = file.getContent();
                    break;
                }
            }
            return content;
        }
    }

    /*
    LC : 317
    https://leetcode.com/problems/shortest-distance-from-all-buildings/
    */
    public int shortestDistance(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0){
            return 0;
        }

        int row = grid.length;
        int col = grid[0].length;

        int buildingCount = 0;

        int[][] distance = new int[row][col];
        int[][] reach = new int[row][col];
        int[][] dirs = new int[][]{ {1,0},
                                    {-1,0}, 
                                    {0,1}, 
                                    {0,-1}
                                };

        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1){
                    buildingCount++;
                    Queue<int[]> queue = new LinkedList<>();
                    boolean[][] visited = new boolean[row][col];
                    int level = 1;

                    queue.offer(new int[]{i,j});
                    while(!queue.isEmpty()){
                        int size = queue.size();
                        while(size-- > 0){
                            int[] curr = queue.poll();
                            for(int[] dir : dirs){
                                int next_x = curr[0] + dir[0];
                                int next_y = curr[1] + dir[1];

                                if(next_x >= 0 && next_x < row && next_y >=0 && next_y < col
                                    && !visited[next_x][next_y] && grid[next_x][next_y] == 0){
                                    distance[next_x][next_y] += level;
                                    reach[next_x][next_y]++;

                                    visited[next_x][next_y] = true;
                                    queue.offer(new int[]{next_x, next_y});
                                }
                            }
                        }
                        level++;
                    }
                }
            }
        }
        int shortestDistance = Integer.MAX_VALUE;
        for (int i = 0; i < row; i++ ) {
            for (int j = 0; j < col; j++) {
                if(grid[i][j] == 0 && reach[i][j] == buildingCount){
                    shortestDistance = Math.min(shortestDistance, distance[i][j]);
                }
            }
        }
        return shortestDistance == Integer.MAX_VALUE ? -1 : shortestDistance;
    }

    /* https://leetcode.com/problems/minimum-moves-to-equal-array-elements-ii/ */
    public int minMoves2(int[] nums) {
        Arrays.sort(nums);
        int i = 0;
        int j = nums.length - 1;
        int res = 0;
        while(i < j){
            res += nums[j--] - nums[i++];
        }
        return res;
    }
    

    /*
    LC : 296
    https://leetcode.com/problems/best-meeting-point/
    */
    public int minTotalDistance(int[][] grid) {
        List<Integer> rowList = new ArrayList<Integer>();
        List<Integer> colList = new ArrayList<Integer>();

        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                if(grid[i][j] == 1){
                    rowList.add(i);
                    colList.add(j);    
                }
            }
        }
        return getminDistance(rowList) +  getminDistance(colList);
    }

    public int getminDistance(List<Integer> list){
        int i = 0;
        int j = list.size() -1;
        Collections.sort(list);
        int res = 0;
        while(i < j){
            res += list.get(j--) - list.get(i++);
        }
        return res;
    }

    /* 
    LC : 465
    https://leetcode.com/problems/optimal-account-balancing/
    Revisit
    */

    public int minTransfers(int[][] transactions) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < transactions.length; i++) {
            int from = transactions[i][0];
            int to = transactions[i][1];
            int cost = transactions[i][2];
            map.put(from, -cost + map.getOrDefault(from, 0));
            map.put(to, cost + map.getOrDefault(to, 0));
        }
        List<Integer> list = new ArrayList<>();
        for(int value : map.values()){
            if(value != 0){
                list.add(value);
            }
        }
        return dfs(0, list);
    }

    public int dfs(int start, List<Integer> list){
        if(start == list.size()) return 0;
        int curr = list.get(start);
        if(curr == 0){
            return dfs(start + 1, list);
        }

        int min = Integer.MAX_VALUE;
        for(int i = start + 1; i < list.size(); i++){
            int next = list.get(i);
            if(curr * next < 0){
                list.set(i, curr + next);
                min = Math.min(min, 1 + dfs(start + 1, list));
                list.set(i, next);

                if(curr + next == 0){
                    break;
                }
            }
        }
        return min;
    }

    /*
    LC : 1060
    https://leetcode.com/problems/missing-element-in-sorted-array/
    https://yy-zhou.github.io/2020/01/lc-1060-missing-element-in-sorted-array/
    */
    public int missingElement(int[] nums, int k) {
        return binarySearch(nums, k);
    }

    public int binarySearch(int[] nums, int k){
        int low = 0;
        int high = nums.length - 1;

        int n = nums.length - 1;
        int totalMissingInArray = nums[n] - nums[0] - n;

        /* Corner case */
        if (totalMissingInArray < k) {   // if missing # is less than k, then first missing # is out of array
            return nums[n] + k - totalMissingInArray;
        }

        while(low < high - 1){ // mid exists in at least 3 elements
            int mid = low + (high  - low) / 2;
            int missing = nums[mid] - nums[low] - (mid - low); // count missing # under current subarray
            if(missing >= k){ // narrow search range if current subarray has at least k missing #
                high = mid;
            }else {
                low = mid;
                k = k - missing;
            }
        }
        return nums[low] + k; // If gap is large between final elements
    }


    /*
    991. Broken Calculator
    https://leetcode.com/problems/broken-calculator/
    Revisit
    */
    public int brokenCalc(int x, int y) {
        int ans = 0;
        while(y > x){
            ans++;
            if(y % 2 == 1){
                y++;
            }else{
                y /= 2;
            }
        }
        return ans + x - y;
    }

    /* 
    LC : 593. Valid Square
    https://leetcode.com/problems/valid-square/
    */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        // Using pythagoras theorem
        double d1 = Math.sqrt((int)Math.pow(p1[0] - p2[0], 2) + (int) Math.pow(p1[1] - p2[1], 2) );
        double d2 = Math.sqrt(Math.pow(p3[0] - p4[0], 2) +  Math.pow(p3[1] - p4[1], 2) );
        double d3 = Math.sqrt(Math.pow(p1[0] - p3[0], 2) +  Math.pow(p1[1] - p3[1], 2) );
        double d4 = Math.sqrt(Math.pow(p3[0] - p2[0], 2) +  Math.pow(p3[1] - p2[1], 2) );
        double d5 = Math.sqrt(Math.pow(p4[0] - p2[0], 2) +  Math.pow(p4[1] - p2[1], 2) );
        double d6 = Math.sqrt(Math.pow(p4[0] - p1[0], 2) +  Math.pow(p4[1] - p1[1], 2) );

        HashSet<Double> sets = new HashSet<>();
        sets.add(d1);
        sets.add(d2);
        sets.add(d3);
        sets.add(d4);
        sets.add(d5);
        sets.add(d6);

        if(sets.contains(0d)){ // Zero distance
            return false;
        }

        return sets.size() == 2; // we should get Only 2 unique distance i.e side and diagonal
    }

    /*
    LC : 1302
    Q : https://leetcode.com/problems/deepest-leaves-sum/
    A : https://leetcode.com/problems/deepest-leaves-sum/discuss/652763/JAVA-O(n)-DFS-traversal
    */
    public int deepestLeavesSum(TreeNode root) {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        helper(root, list, 0);
        int lastlevel = list.size() - 1;
        List<Integer> mList = list.get(lastlevel);
        int ans = 0;
        for (int i : mList) {
            ans += i;
        }
        return ans;
    }

    public void helper(TreeNode root, ArrayList<ArrayList<Integer>> list, int level){
        if(root == null) return;
        if(list.size() == level){
            ArrayList<Integer> levelList = new ArrayList<>();
            levelList.add(root.val);
            list.add(levelList);
        }else{
            ArrayList<Integer> levelList = list.get(level);
            levelList.add(root.val);
            list.set(level, levelList);
        }
        helper(root.left, list, level + 1);
        helper(root.right, list, level + 1);
    }

    /*
    LC : 834
    https://leetcode.com/problems/sum-of-distances-in-tree/
    DP on trees
    */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        if (N == 1) {
            return new int[N];
        }
        if (N == 2) {
            return new int[]{1, 1};
        }

        List<int[]>[] graph = new ArrayList[N];
        for(int i = 0; i < N; i++){
            graph[i] = new ArrayList<>();
        }

        for(int i = 0; i < edges.length; i++){
            graph[edges[i][0]].add(new int[]{edges[i][1], 0, 0});
            graph[edges[i][1]].add(new int[]{edges[i][0], 0, 0});
        }

        int[] result = new int[N];
        boolean[]seen = new boolean[N];

        for(int i = 0; i < N; i++){
            result[i] = dfs(graph, i, seen)[0];
        }
        return result;
    }

    private int[] dfs(List<int[]>[] graph, int i, boolean[] seen){
        seen[i] = true;
        int count = 1;
        int sum = 0;

        for(int[] edge : graph[i]){
            if(seen[edge[0]]) continue;
            if(edge[1] == 0 || edge[2] == 0){
                int[] ans = dfs(graph, edge[0], seen);
                edge[1] = ans[0];
                edge[2] = ans[1];
            }
            count += edge[2];
            sum += (edge[1] + edge[2]);
        }
        seen[i] = false;
        int[] res = new int[]{sum, count};
        return res;
    }

    /*
        LC : 432
        https://leetcode.com/problems/all-oone-data-structure/
        Revisit
    */
    class AllOne {
        class Bucket {
            Bucket pre;
            Bucket next;
            int count;
            Set<String> keySet;
            public Bucket(int count){
                this.count = count;
                keySet = new HashSet<>();
            }
        }

        /** Initialize your data structure here. */
        HashMap<String, Integer> fmap;
        HashMap<Integer, Bucket> countBucketMap;
        private Bucket head;
        private Bucket tail;

        public AllOne() {
            head = new Bucket(Integer.MIN_VALUE);
            tail = new Bucket(Integer.MAX_VALUE);
            head.next = tail;
            tail.pre = head;
            fmap = new HashMap<>();
            countBucketMap = new HashMap<>();
        }
        
        /** Inserts a new key <Key> with value 1. Or increments an existing key by 1. */
        public void inc(String key) {
            if(fmap.containsKey(key)){
                changeKey(key, 1);
            }else{
                fmap.put(key, 1);
                if(head.next.count != 1){
                    addBucketAfter(new Bucket(1), head);
                }
                head.next.keySet.add(key);
                countBucketMap.put(1, head.next);
            }
        }

        /** Decrements an existing key by 1. If Key's value is 1, remove it from the data structure. */
        public void dec(String key) {
            if(fmap.containsKey(key)) {
                int count = fmap.get(key);
                if (count == 1) {
                    fmap.remove(key);
                    removeKeyFromBucket(countBucketMap.get(count), key);
                } else {
                    changeKey(key, -1);
                }
            }
        }
        
        /** Returns one of the keys with maximal value. */
        public String getMaxKey() {
            return tail.pre == head ? "" : tail.pre.keySet.iterator().next();
        }
        
        /** Returns one of the keys with Minimal value. */
        public String getMinKey() {
            return head.next == tail ? "" : head.next.keySet.iterator().next();
        }

        private void changeKey(String key, int offset) {
            int count = fmap.get(key);
            fmap.put(key, count + offset);
            Bucket oldBucket = countBucketMap.get(count);
            Bucket newBucket;
            if(countBucketMap.containsKey(count + offset)){
                newBucket = countBucketMap.get(count + offset);
            }else{
                newBucket = new Bucket(count + offset);
                countBucketMap.put(count + offset, newBucket);
                addBucketAfter(newBucket, offset == 1 ? oldBucket : oldBucket.pre);
            }
            newBucket.keySet.add(key);
            removeKeyFromBucket(oldBucket, key);
        }

        private void removeKeyFromBucket(Bucket bucket, String key) {
            bucket.keySet.remove(key);
            if(bucket.keySet.isEmpty()){
                removeBucketFromList(bucket);
                countBucketMap.remove(bucket.count);
            }
        }

        private void removeBucketFromList(Bucket bucket)  {
            Bucket prev = bucket.pre;
            Bucket next = bucket.next;
            prev.next = next;
            next.pre = prev;
            bucket.pre = null;
            bucket.next = null;
        }

        private void addBucketAfter(Bucket newBucket, Bucket prevBucket) {
            newBucket.pre = prevBucket;
            newBucket.next = prevBucket.next;
            prevBucket.next.pre = newBucket;
            prevBucket.next = newBucket;
        }
    }

    /* 
    LC : 1035
    https://leetcode.com/problems/uncrossed-lines/
    */
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] dp = new int[A.length][B.length];
        for(int i = 0; i < A.length; i++){
            Arrays.fill(dp[i], -1);
        }
        //return helper(dp, A, B, 0, 0 );
        return dphelper(A, B);
    }

    public int dphelper(int[]a , int[] b){
        int m = a.length;
        int n = b.length;

        int[][] dp = new int[m+1][n+1];
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                if(a[i-1] == b[j-1]){
                    dp[i][j] = 1 + dp[i-1][j-1];
                }else{
                    dp[i][j] = Math.max(dp[i][j-1], dp[i-1][j]);
                }
            }
        }
        return dp[m][n];
    }

    public int helper(int[][] dp, int[] a, int [] b, int x, int y){
        if(x == a.length || y == b.length){
            return 0;
        }
        if(dp[x][y] != -1){
            return dp[x][y];
        }
        int max = 0;
        if(a[x] == b[y]){
            max = Math.max(helper(dp, a, b, x + 1, y + 1) + 1, max);
        }else{
            max = Math.max(helper(dp, a, b, x + 1, y), max);// Skip x
            max = Math.max(helper(dp, a, b, x, y + 1), max);// Skip y
        }

        dp[x][y] = max;
        return dp[x][y];
    }

    /* 
       LC : 886
       https://leetcode.com/problems/possible-bipartition/
       https://leetcode.com/problems/possible-bipartition/discuss/650930/JAVA-Union-find
       Graph Coloring, Union-find
    */
    public boolean possibleBipartitionI(int n, int[][] dislikes) {
        int[] colors = new int[n];
        LinkedList<Integer>[] graph = new LinkedList[n];

        for (int i = 0; i < n ; i++ ) {
            graph[i] = new LinkedList<>();
        }

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i = 0; i < n; i++ ){
            if(colors[i] != 0) continue;
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(i);
            colors[i] = 1;

            while(!queue.isEmpty()){
                int curr = queue.poll();

                for(int adj : graph[curr]){
                    if(colors[adj] == 0){
                        colors[adj] = -colors[curr];
                        queue.offer(adj);
                    }else if(colors[adj] != -colors[curr]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean possibleBipartition(int n, int[][] dislikes) {
        int[] root = new int[n];
        for (int i = 0; i < root.length; i++) {
            root[i] = i;
        }
        LinkedList<Integer>[] graph = new LinkedList[n];
        for (int i = 0; i < n ; i++ ) {
            graph[i] = new LinkedList<Integer>();
        }

        for(int[] dislike : dislikes){
            int a = dislike[0] - 1;
            int b = dislike[1] - 1;
            graph[a].add(b);
            graph[b].add(a);
        }

        for(int i = 0; i < n; i++){
            LinkedList<Integer> adjList = graph[i];
            for(int adj : adjList) {
                int xRoot = find(i, root);
                int yRoot = find(adj, root);
                if(xRoot != yRoot){
                    root[find(adjList.get(0), root)] = yRoot;
                }else{
                    return false;
                }
            }
        }
        return true;
    }

    public int find(int x, int[] root){
        while(x != root[x]){
            root[x] = root[root[x]];
            x = root[x];
        }
        return x;
    }


}