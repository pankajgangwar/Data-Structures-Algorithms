package com.mission.google.leetcode;

import com.mission.google.TreeNode;

class JuneW3 {

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

    }

    /* https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */

    /*
    LC : 920
    https://leetcode.com/problems/number-of-music-playlists/
    */
    public int numMusicPlaylists(int N, int L, int K) {
        return 0;
    }

    /* https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/ */
    public int minDays(int[] bloomDay, int m, int k) {
        return 0;
    }

    /*
    LC : 1120
    https://leetcode.com/problems/maximum-average-subtree/ */
    public double maximumAverageSubtree(TreeNode root) {
        helper(root);
        return maxAvg;
    }

    double maxAvg = 0;
    public int[] helper(TreeNode root){
        if(root == null) return new int[]{0,0}; // sum, count
        int[] left = helper(root.left);
        int[] right = helper(root.right);
        int[] curr = new int[2];
        curr[0] = root.val + left[0] + right[0];//sum of nodes value
        curr[1] = left[1] + right[1] + 1;//count of nodes
        double currAvg = (double) curr[0] / curr[1];
        maxAvg = Math.max(currAvg, maxAvg);
        return curr;
    }

}
