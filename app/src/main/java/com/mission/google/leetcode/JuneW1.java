package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class JuneW1 {

    public static void main(String[] args) {
        JuneW1 obj = new JuneW1();
        //int ans = obj.openLock(new String[]{"0201","0101","0102","1212","2002"}, "0202");
        String[] words = {"ktittgzawn","dgphvfjniv","gceqobzmis","alrztxdlah","jijuevoioe","mawiizpkub","onwpmnujos","zszkptjgzj","zwfvzhrucv","isyaphcszn"};
        String pattern = "zdqmjnczma";
        //obj.findAndReplacePattern(words , pattern);
        //MapSum mapSum = new MapSum();
        //mapSum.insert("apple",3);
        //int res = mapSum.sum("ap");
        //System.out.println("ans = " + res);
    }

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

    /* LC : 1236
    https://leetcode.com/problems/web-crawler/
    */
    interface HtmlParser {
        List<String> getUrls(String url);
    }

    public List<String> crawl(String startUrl, HtmlParser htmlParser) {
        HashSet<String> visited = new HashSet<>();
        if(startUrl == null || startUrl.isEmpty()){
            System.out.println("parser is null");
            return new ArrayList<>();
        }
        dfsCrawl(startUrl, htmlParser, visited);
        return new ArrayList<>(res);
    }

    HashSet<String> res = new HashSet<>();
    public void dfsCrawl(String startUrl, HtmlParser parser, HashSet<String> visited){
        if(startUrl == null || visited.contains(startUrl)){
            return;
        }
        visited.add(startUrl);
        res.add(startUrl);
        List<String> urls = parser.getUrls(startUrl);
        for(String url : urls){
            String rootDomain = startUrl.split("/")[2];
            String currDomain = url.split("/")[2];
            if(rootDomain.equals(currDomain)){
                res.add(url);
                dfsCrawl(url, parser, visited);
            }
        }
    }

    /*
    LC : 616, 758
    https://leetcode.com/problems/add-bold-tag-in-string/
    https://leetcode.com/problems/bold-words-in-string/
    */
    public String addBoldTag(String s, String[] dict) {
        boolean[] bold = new boolean[s.length()];
        for (int i = 0, end = 0;  i < s.length(); i++) {
            for(String word : dict){
                if(s.startsWith(word, i)){
                    end = Math.max(end, i + word.length());
                }
            }
            bold[i] = end > i;
        }
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < bold.length; i++) {
            if(!bold[i]){
                res.append(s.charAt(i));
                continue;
            }
            int j = i;
            while (j < s.length() && bold[j])j++;
            res.append("<b>");
            res.append(s.substring(i, j));
            res.append("</b>");
            i = j - 1;
        }
        return res.toString();
    }

    /* 
    LC : 677
    https://leetcode.com/problems/map-sum-pairs/
    Sol: https://leetcode.com/problems/map-sum-pairs/discuss/666292/JAVA-Trie-Solution
    */
    static class MapSum {
        class Trie{
            Trie[] children = new Trie[26];
            int val = 0;
        }

        /** Initialize your data structure here. */
        Trie root;
        public MapSum() {
            root = new Trie();
        }
        
        public void insert(String key, int val) {
            Trie pCrawl = root;
            char[] arr = key.toCharArray();
            for (int i = 0; i < arr.length ; i++ ) {
                int idx = arr[i] - 'a';
                if(pCrawl.children[idx] == null){
                    pCrawl.children[idx] = new Trie();
                }
                pCrawl = pCrawl.children[idx];
            }
            pCrawl.val = val;
        }
        
        public int sum(String prefix) {
            Trie pCrawl = root;
            Trie prefixRoot = findRoot(pCrawl, prefix);
            return dfsSum(prefixRoot);
        }

        public Trie findRoot(Trie pCrawl, String prefix){
            for(int i = 0; i < prefix.length(); i++){
                int idx = prefix.charAt(i) - 'a';
                if(pCrawl.children[idx] != null){
                    pCrawl = pCrawl.children[idx];
                }else{
                    pCrawl = null;
                    break;
                }
            }
            return pCrawl;
        }

        public int dfsSum(Trie node){
            if(node == null){
                return 0;
            }
            int sum = node.val;
            for(int i = 0; i < 26; i++){
                sum += dfsSum(node.children[i]);
            }
            return sum;
        }
    }

    /*
    LC : 890
    https://leetcode.com/problems/find-and-replace-pattern/
    */
    public List<String> findAndReplacePattern(String[] words, String pattern) {
        List<String> res = new ArrayList<>();
        for(int i = 0; i < words.length; i++){
            String word = words[i];
            if(word.length() != pattern.length()){
                continue;
            }
            if(isMatch(word, pattern)){
                res.add(word);
            }
        }
        return res;
    }

    public boolean isMatch(String s, String p){
        HashMap<Character, Character> pattern = new HashMap<>();
        HashMap<Character, Character> word = new HashMap<>();

        for (int i = 0; i < p.length(); i++) {
            char c1 = s.charAt(i);
            char c2 = p.charAt(i);
            if(!pattern.containsKey(c2)){
                pattern.put(c2, c1);
            }
            if (!word.containsKey(c1)){
                word.put(c1, c2);
            }
            if(pattern.get(c2) != c1 || word.get(c1) != c2){
                return false;
            }
        }
        return true;
    }

    /* 
    LC : 848
    https://leetcode.com/problems/convert-a-number-to-hexadecimal/
    */
    public String shiftingLetters(String s, int[] shifts) {
        long sum = 0;
        int n = shifts.length;
        long[] shift = new long[n];
        for(int i = shifts.length - 1; i >= 0 ; i--){
            sum += shifts[i];
            shift[i] = sum;
        }

        char[] res = s.toCharArray();
        for(int i = 0; i < res.length; i++){
            char ch = res[i];
            long idx = ch - 'a' + shift[i];
            idx = idx % 26;
            ch = (char)((int)idx + 'a');
            res[i] = ch;
        }
        return new String(res);
    }

    /*
    LC : 186
    https://leetcode.com/problems/reverse-words-in-a-string-ii/
    */
    public void reverseWords(char[] s) {
        int start = 0;
        int end = 0;
        for(int i = 0; i < s.length; i++) {
            if(s[i] == ' ' || i == s.length - 1){
                if(i == s.length -1 ){
                    end = i;
                }else{
                    end = i - 1;
                }
                reverse(s, start, end);
                start = i + 1;
            }
        }
        reverse(s, 0, s.length -1);
    }

    public void reverse(char[] arr, int start, int end){
        while(start < end){
            char temp = arr[start];
            arr[start] = arr[end];
            arr[end] = temp;
            start++;
            end--;
        }
    }

    /*
    LC : 752
    https://leetcode.com/problems/open-the-lock/
    */
    public int openLock(String[] deadends, String target) {
        HashSet<String> dead_ends = new HashSet<>(Arrays.asList(deadends));

        HashSet<String> visited = new HashSet<>();
        visited.add("0000");

        Queue<String> queue = new LinkedList<>();
        queue.offer("0000");
        int level = 0;
        while (!queue.isEmpty()){
            int size = queue.size();
            while (size-- > 0 ){
                String curr_comb = queue.poll();
                if(curr_comb.equals(target)){
                    return level;
                }
                if(dead_ends.contains(curr_comb)) continue;

                for (int i = 0; i < 4; i++) {
                    int curr_ch = curr_comb.charAt(i) - '0';
                    String incStr = curr_comb.substring(0, i)
                            + String.valueOf(curr_ch == 9 ? 0 : curr_ch + 1)
                            + curr_comb.substring(i + 1);

                    String decStr = curr_comb.substring(0, i)
                            + (curr_ch == 0 ? 9 : curr_ch - 1)
                            + curr_comb.substring(i + 1);

                    if(!visited.contains(incStr) && !dead_ends.contains(incStr)){
                        queue.offer(incStr);
                        visited.add(incStr);
                    }

                    if(!visited.contains(decStr) && !dead_ends.contains(decStr)){
                        queue.offer(decStr);
                        visited.add(decStr);
                    }
                }
            }
            level++;
        }
        return -1;
    }

    /*
    LC : 1020
    https://leetcode.com/problems/number-of-enclaves/
    */
    public int numEnclaves(int[][] grid) {
        int ans = 0;
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 1 && !visited[i][j]){
                    ans += bfs(i, j, grid, visited);
                }
            }
        }
        return ans;
    }

    public int bfs(int i, int j, int[][] grid, boolean[][] visited){
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{i,j});

        visited[i][j] = true;

        int count = 0;
        boolean isOnBounary = false;
        if(isOnBoundary(i, j, grid)){
            isOnBounary = true;
        }

        while (!q.isEmpty()){
            int size = q.size();
            count+= size;
            while (size-- > 0 ){
                int[] curr = q.poll();
                int dir[][] = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
                for (int k = 0; k < dir.length; k++) {
                    int next_x = curr[0] + dir[k][0];
                    int next_y = curr[1] + dir[k][1];
                    if(next_x >= 0 && next_x < grid.length && next_y >= 0 && next_y < grid[0].length
                            && !visited[next_x][next_y]){
                        if(grid[next_x][next_y] == 1){
                            if(isOnBoundary(next_x, next_y, grid)){
                                isOnBounary = true;
                            }
                            q.offer(new int[]{next_x, next_y});
                            visited[next_x][next_y] = true;
                        }
                    }
                }
            }
        }
        return isOnBounary ? 0 : count;
    }

    public boolean isOnBoundary(int x, int y, int[][] grid){
        if(x == 0 || x == grid.length - 1 || y == 0 || y == grid[0].length -1){
            return true;
        }
        return false;
    }

    /*
    LC : 489
    https://leetcode.com/problems/robot-room-cleaner/
    Revisit
    */
    interface Robot {
        // Returns true if the cell in front is open and robot moves into the cell.
        // Returns false if the cell in front is blocked and robot stays in the current cell.
        public boolean move();

        // Robot will stay in the same cell after calling turnLeft/turnRight.
        // Each turn will be 90 degrees.
        public void turnLeft();
        public void turnRight();

        // Clean the current cell.
        public void clean();
    }

    public void cleanRoom(Robot robot) {
        dfs(robot, new HashSet<>(), 0, 0, 0);
        return;
    }

    public void dfs(Robot robot, Set<String> visited, int curr_x, int curr_y, int arrow){
        robot.clean();
        int dir[][] = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; //clockwise direction
        for (int i = 0; i < dir.length; i++) {
            int next_x = dir[arrow][0] + curr_x;
            int next_y = dir[arrow][1] + curr_y;
            String path = next_x + "->" + next_y;
            if(!visited.contains(path) && robot.move()){
                visited.add(path);
                dfs(robot, visited, next_x, next_y, arrow);
            }
            robot.turnRight();
            arrow = (arrow + 1) % 4;
        }
        robot.turnLeft();
        robot.turnLeft();
        robot.move();
        robot.turnLeft();
        robot.turnLeft();
    }
}
