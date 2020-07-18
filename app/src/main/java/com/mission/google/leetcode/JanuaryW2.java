package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeSet;

public class JanuaryW2 {

    public static void main(String[] args) {
        int INF = 2147483647;
        int[][] rooms = new int[][]{ 
        { INF , -1 , 0 , INF }, 
        { INF ,INF, INF,  -1 }, 
        { INF , -1 ,INF , -1 },
        { 0 , -1 ,INF ,INF }
        };

        JanuaryW2 w2 = new JanuaryW2();
        //w2.wallsAndGates(rooms);

        //w2.fractionToDecimal(1,2);
        //w2.jump(new int[]{1,1,3,1,1,4});
        //w2.countBinarySubstrings("00100");
        //w2.minFlips(2, 6, 5);
        //RollingHash rollingHash = new RollingHash();
        //rollingHash.distinctEchoSubstrings("abcabcabc");
        w2.distinctEchoSubstrings("abcabcabc");
    }

    /* https://leetcode.com/problems/walls-and-gates/
    *  Revisit : To understand why BFS/multi-ended BFS has better runtime than DFS
    * */
    public void wallsAndGates(int[][] rooms) {
        for(int i = 0; i < rooms.length; i++){
            for(int j = 0; j < rooms[i].length; j++){
                if(rooms[i][j] == 0){
                    helperDistanceDFS(rooms, i, j);
                }
            }            
        }
    }

    public void helperDistanceBFS(int[][] rooms, int x, int y){
        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[]{x, y});

        int m = rooms.length;
        int n = rooms[0].length;

        int[][] dirs = new int[][]{ {1,0}, {0,1}, {-1,0}, {0,-1} };

        while(!q.isEmpty()){
            
            int size = q.size();
            
            while(size-- > 0){

                int[]curr = q.poll();

                for (int i = 0; i < dirs.length; i++) {

                    int next_x = dirs[i][0] + curr[0];
                    int next_y = dirs[i][1] + curr[1];

                    if (next_x >= 0 && next_x < m && next_y >= 0 && next_y < n
                            && rooms[next_x][next_y] > rooms[curr[0]][curr[1]]) {

                        rooms[next_x][next_y] = rooms[curr[0]][curr[1]] + 1;

                        q.offer(new int[]{next_x, next_y});
                    }
                }
            }
        }
    }

     public void helperDistanceDFS(int[][] rooms, int curr_x, int curr_y) {
        int INF = 2147483647;
        int[][] dirs = new int[][]{ {1,0}, {0,1}, {-1,0}, {0,-1} };
        int m = rooms.length;
        int n = rooms[0].length;

        for(int i = 0; i < dirs.length; i++){
            int[] curr_d = dirs[i];

            int next_x = curr_d[0] + curr_x;
            int next_y = curr_d[1] + curr_y;

            if(next_x >= 0 && next_x < m && next_y >= 0 && next_y < n 
                && rooms[next_x][next_y] > rooms[curr_x][curr_y]){

                rooms[next_x][next_y] = rooms[curr_x][curr_y] + 1;

                helperDistanceDFS(rooms, next_x, next_y);
            }
        }
    }

    /* https://leetcode.com/problems/fraction-to-recurring-decimal/ */
    public String fractionToDecimal(int numerator, int denominator) {
        if(numerator == 0){
            return "0";
        }

        int rem = -1;
        StringBuilder builder = new StringBuilder();
        if((numerator > 0) ^ (denominator > 0)){
            builder.append("-");
        }

        long num = Math.abs((long)(numerator));
        long den = Math.abs((long)(denominator));

        boolean containsDup = false;

        //Integral part
        builder.append(num / den);
        num %= den;

        if(num == 0){
            return builder.toString();
        }

        //Fractional part
        builder.append(".");

        HashMap<Long, Integer> map = new HashMap<>();

        map.put(num, builder.length());

        while(num != 0){
            num = num * 10;

            builder.append(num / den);

            num = num % den;

            if(map.containsKey(num)){
                int idx = map.get(num);
                builder.insert(idx, "(");
                builder.append(")");
                break;
            }else{
                map.put(num, builder.length());
            }
        }

        return builder.toString();
    }

    /* https://leetcode.com/problems/shortest-word-distance/ */
    public int shortestDistance(String[] words, String word1, String word2) {
        int idx1 = -1;
        int idx2 = -1;
        
        int minDist = Integer.MAX_VALUE;
        
        for(int i = 0; i < words.length; i++){
            if(words[i].equals(word1)){
                idx1 = i;
            }
            if(words[i].equals(word2)){
                idx2 = i;
            }
            if(idx1 == -1 || idx2 == -1) continue;
            minDist = Math.min(minDist, Math.abs(idx1 - idx2) );
        }
        
        return minDist;
    }

    /* https://leetcode.com/problems/shortest-word-distance-iii/ */
    public int shortestWordDistance(String[] words, String word1, String word2) {

        long minDist = Integer.MAX_VALUE;

        long idx1 = minDist;
        long idx2 = -minDist;

        boolean isSame = word1.equals(word2);

        for (int i = 0; i < words.length; i++) {
            if (words[i].equals(word1)) {
                if (isSame) {
                    idx1 = idx2;
                    idx2 = i;
                } else {
                    idx1 = i;
                }
            } else if (words[i].equals(word2)) {
                idx2 = i;
            }
            minDist = Math.min(minDist, Math.abs(idx1 - idx2));
        }

        return (int)minDist;
    }

    /* https://leetcode.com/problems/shortest-word-distance-ii/ */
    class WordDistance {
        HashMap<String, List<Integer>> map = new HashMap<>();

        public WordDistance(String[] words) {

            for (int i = 0; i < words.length; i++) {
                List<Integer> list = new ArrayList<>();

                if(map.containsKey(words[i])){
                    list = map.get(words[i]);
                }
                list.add(i);
            }
        }

        public int shortest(String word1, String word2) {
            int minDist = Integer.MAX_VALUE;

            List<Integer> list1 = map.get(word1);
            List<Integer> list2 = map.get(word2);

            for (int i = 0, j = 0; i < list1.size() && j < list2.size(); ) {
                int idx1 = list1.get(i);
                int idx2 = list2.get(j);

                minDist = Math.min(minDist, Math.abs( idx1 - idx2));

                if(idx1 < idx2){
                    i++;
                }else{
                    j++;
                }
            }
            return minDist;
        }
    }

    /* https://leetcode.com/problems/strobogrammatic-number/submissions/ */
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

    /* https://leetcode.com/problems/jump-game-ii/ */

    /*public int jump(int[] nums) {
        int[][] memo = new int[nums.length][nums.length];

        for (int i = 0; i < nums.length; i++) {
            Arrays.fill(memo[i], -1);
        }

        HashMap<Integer,Integer> map = new HashMap<>();
        int minSteps = canJumpFromPosMemo(0, nums, map);
        System.out.println(minSteps);
        return minSteps;
    }*/

    public int jump(int[] nums) {
        if (nums.length <= 1) return 0;
        int curMax = 0; // to mark the last element in a level
        int level = 0, i = 0;
        while (i <= curMax) {
            int furthest = curMax; // to mark the last element in the next level
            for (; i <= curMax; i++) {
                furthest = Math.max(furthest, nums[i] + i);
                if (furthest >= nums.length - 1) return level + 1;
            }
            level++;
            curMax = furthest;
        }
        return -1; // if i < curMax, i can't move forward anymore (the last element in the array can't be reached)
    }

    public int canJumpFromPosBFS(int[] nums){
        Queue<Integer> q = new LinkedList<>();
        q.offer(0);
        int level = 0;

        boolean[] visited = new boolean[nums.length];
        visited[0] = true;

        while (!q.isEmpty()){
            int size = q.size();
            while (size-- > 0){
                int index = q.poll();
                int farthest = nums[index];
                for (int i = 1; i <= farthest; i++) {
                    int idx = index + i;
                    if(idx > nums.length - 1) break;
                    if(visited[idx]) continue;
                    q.offer(idx);
                    visited[idx] = true;
                }
            }
            level++;
        }
        return level;
    }

    public int canJumpFromPosMemo(int pos, int[] nums, HashMap<Integer,Integer> map){

        if(map.containsKey(pos)){
            return map.get(pos);
        }

        if(pos == nums.length - 1) {
            map.put(pos, 0);
            return 0;
        }

        int farthestJump = Math.min(pos + nums[pos], nums.length - 1);
        int min = Integer.MAX_VALUE;
        for (int nextPosition = farthestJump; nextPosition > pos ; nextPosition--) {
            min = Math.min(min, canJumpFromPosMemo(nextPosition, nums, map) + 1);
        }
        map.put(pos, min);
        return min;
    }

    /* https://leetcode.com/problems/convert-integer-to-the-sum-of-two-no-zero-integers/ */
    public int[] getNoZeroIntegers(int n) {
        int[] res = new int[2];
        for(int i = 1; i <= n; i++){
            if(!hasZero(i) && !hasZero(n - i)){
                res[0] = i;
                res[1] = n - i;
                return res;
            }
        }
        return res;
    }
    
    public boolean hasZero(int m){
        while(m != 0){
           if( m%10 == 0 ) {
               return true;
           }
            m /= 10;
        }
        return false;
    }

    /* https://leetcode.com/problems/minimum-flips-to-make-a-or-b-equal-to-c/ */
    public int minFlips(int a, int b, int c) {
        int ret = 0;
        for(int i = 0; i < 30; i++){
            int u = (a >> i) & 1;
            int v = (b >> i) & 1;
            int w = (c >> i) & 1;

            if(w == 0){
                ret+= u + v;
            }else{
                if(u == 0 && v == 0)
                    ret+= 1;
            }
        }
        return ret;
    }


    /* https://leetcode.com/problems/count-binary-substrings/
       Revisit
     */
   public int countBinarySubstrings(String s) {
        int prev = 0, curr = 1;
        int res = 0;
        
        for(int i = 1; i < s.length(); i++){
            if(s.charAt(i) == s.charAt(i - 1)) curr++;
            else{
                res += Math.min(prev, curr);
                prev = curr;
                curr = 1;
            }
        }
        return res + Math.min(prev, curr);
    }

    /* https://leetcode.com/problems/number-of-operations-to-make-network-connected/ */
    public int makeConnected(int n, int[][] connections) {
        if (connections.length < n - 1) return -1;

        int[] parent = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < connections.length; i++) {
            int xRoot = findRoot(parent, connections[i][0]);
            int yRoot = findRoot(parent, connections[i][1]);
            if(xRoot != yRoot){
                parent[xRoot] = yRoot;
            }
        }

        int connectionNeeded = 0;
        for (int i = 0; i < parent.length; i++) {
            if(parent[i] == i){
                connectionNeeded++;
            }
        }
        return connectionNeeded -1;
    }

    private int findRoot(int[] root, int i){
        while(root[i] != i){
            root[i] = root[root[i]];
            i = root[i];
        }
        return i;
    }

    /* https://leetcode.com/problems/decompress-run-length-encoded-list/ */
    public int[] decompressRLElist(int[] nums) {
        int i = 0;
        int n = nums.length;
        ArrayList<Integer> res = new ArrayList<>();
        for(; i < n; i++){
            int a = 0;
            if(2*i < n)
                a = nums[2*i];

            int b = 0;
            if(2*i+1 < n)
                b = nums[2*i+1];

            while(a > 0 ){
                res.add(b);
                a--;
            }
        }
        int[] ret = new int[res.size()];
        for (int j = 0; j < res.size(); j++) {
            ret[j] = res.get(j);
        }
        return ret;
    }

    /* https://leetcode.com/problems/matrix-block-sum/
    *  Revisit: Range sum query
    *  Similar : LC 304, 307, 308
       Fenwick Trees, BinaryIndexed trees
    * */
    public int[][] matrixBlockSum(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;

        int sum[][] = new int[m + 1][n + 1];
        for(int i = 0 ; i < m; i++){
            for(int j = 0 ; j < n; j++){
                sum[i + 1][j + 1] = sum[i+1][j] + sum[i][j+1] - sum[i][j] + mat[i][j];
            }
        }
        int [][]res = new int[m][n];

        for(int i = 0 ; i < m; i++){
            for(int j = 0 ; j < n; j++){
                int r1 = Math.max(0, i - k),r2 = Math.min(m, i + k + 1);
                int c1 = Math.max(0, j - k), c2 = Math.min(n, j + k + 1);
                res[i][j] = sum[r2][c2] - sum[r2][c1] - sum[r1][c2] + sum[r1][c1];
            }   
        }
        return res;
    }

    /* https://leetcode.com/problems/sum-of-nodes-with-even-valued-grandparent/ */
    int sum = 0;
    public int sumEvenGrandparent(TreeNode root) {
        sumEvenGrandparentRec(root, null, null);
        return sum;
    }

    public void sumEvenGrandparentRec(TreeNode current, TreeNode parent, TreeNode grandParent){
        if(current == null) return;
        if(grandParent != null && grandParent.val %2 == 0) sum += current.val;
        sumEvenGrandparentRec(current.left, current, parent);
        sumEvenGrandparentRec(current.right, current, parent);
    }

    /* https://leetcode.com/problems/distinct-echo-substrings/ 
       Rolling hash, Similar to Rabin Karp Algorithm
       Revisit
    */
    long BASE = 29L, MOD = 1000000007L;
    public int distinctEchoSubstrings(String str) { // Fast 182ms
        HashSet<Long> set = new HashSet<>();
        int n = str.length();
        long[] pow = new long[n + 1];
        long[] hash = new long[n + 1];
        pow[0] = 1;

        for(int i = 1; i <= n; ++i){
            hash[i] = (hash[i-1] * BASE + str.charAt(i - 1)) % MOD;
            pow[i] = pow[i-1] * BASE % MOD;
        }

        for(int i = 0; i < n; i++){
            for(int len = 2; i + len <= n; len+= 2){
                int mid = i + len / 2;
                long hash1 = getHash(i, mid, hash, pow);
                long hash2 = getHash(mid, i + len, hash, pow);
                if(hash1 == hash2){
                    set.add(hash1);
                }
            }
        }
        return set.size();
    }

    public long getHash(int l, int r, long[] hash, long[] pow ){
        return (hash[r] - hash[l] * pow[r - l] % MOD + MOD) % MOD;
    }

    static class RollingHash { //Slow 262 ms
        int A = 31, MX = (int)1e5;
        long mod = (long)1e9+7;
        long[] pow, ipow;
        long pow(long a, long p){
            long o =1;
            while(p>0){
                if((p&1)==1)o = (o*a)%mod;
                a = (a*a)%mod;
                p>>=1;
            }
            return o;
        }
        void preHash(){
            pow = new long[1+MX]; ipow = new long[1+MX];
            pow[0] = 1;int A = 31;
            for(int i = 1; i<= MX; i++)pow[i] = (pow[i-1]*A)%mod;
            ipow[MX] = pow(pow[MX], mod-2);
            for(int i = MX-1; i>0; i--)ipow[i] = (A*ipow[i+1])%mod;
            ipow[0] = 1;
        }
        long[] fhash;//, rhash;
        void makeHash(String t){
            int n = t.length();
            fhash = new long[1+n];
            for(int i = 1; i<= n; i++){
                fhash[i] = (pow[i]*(t.charAt(i-1)-'a'+1))%mod;
                fhash[i] += fhash[i-1];
                if(fhash[i]>=mod)fhash[i]-=mod;
            }
        }
        long getHash(int l, int r){
            return ((mod+fhash[r]-fhash[l-1])%mod*ipow[l])%mod;
        }

        /* Input: text = "abcabcabc"
           Output: 3
        */
        public int distinctEchoSubstrings(String text) {
            preHash();
            makeHash(text);
            System.out.println("PreComputation done !!");
            int ans = 0;
            for(int l = 1; l*2 <= text.length(); l++){
                TreeSet<Long> set = new TreeSet<>();
                for(int i = 1; i+2*l-1 <= text.length(); i++){
                    long hash = getHash(i, i+l-1), hash2 = getHash(i+l, i+2*l-1);
                    if(hash == hash2)set.add(hash);
                }
                ans += set.size();
            }
            System.out.println(ans);
            return ans;
        }
    }

}