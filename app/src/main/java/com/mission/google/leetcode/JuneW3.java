package com.mission.google.leetcode;

import com.mission.google.TreeNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.OptionalInt;
import java.util.Queue;
import java.util.stream.IntStream;

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
        JuneW3 w3 = new JuneW3();
        //w3.validIPAddress("2001:0db8:85a3:0:0:8A2E:0370:asda:");
        //w3.shipWithinDays(new int[]{1,2,3,1,1}, 4);
    }

    /* https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/ */


    /* Binary search problems*/
    /* https://leetcode.com/problems/sum-of-mutated-array-closest-to-target/ */
    /* https://leetcode.com/problems/k-th-smallest-prime-fraction/ */
    /* https://leetcode.com/problems/preimage-size-of-factorial-zeroes-function/ */

    
    /* https://leetcode.com/problems/divide-chocolate/ */
    /* https://leetcode.com/problems/integer-replacement/ */

    /*
    LC : 920
    https://leetcode.com/problems/number-of-music-playlists/
    */
    public int numMusicPlaylists(int N, int L, int K) {
        return 0;
    }

    /*
     LC : 663
     https://leetcode.com/problems/equal-tree-partition/ */
    List<Integer> subtreesum;
    public boolean checkEqualTree(TreeNode root) {
        if(root.left == null && root.right == null) return false;
        subtreesum = new ArrayList<>();
        int sum = getTotal(root);
        if(sum % 2 != 0 ) return false;
        subtreesum.remove(subtreesum.size()-1);
        return subtreesum.contains(sum / 2);
    }

    public int getTotal(TreeNode root){
        if(root == null) return 0;
        int sum = getTotal(root.left) + getTotal(root.right) + root.val;
        subtreesum.add(sum);
        return sum;
    }

    /*
    LC : 397
    https://leetcode.com/problems/integer-replacement/ */
    public int integerReplacement(int n) {
        //return helper(n);
        Queue<Long> list = new LinkedList<>();
        list.offer((long)n);
        return shortestPath(list, 0);
    }

    public int shortestPath(Queue<Long> oldQ, int level){
        Queue<Long> newQ = new LinkedList<>();

        while(!oldQ.isEmpty()){
            Long curr = oldQ.poll();
            if(curr == 1) return level;
            if((curr & 1) == 0){
                newQ.offer(curr >> 1);
            }else{
                newQ.offer(curr + 1);
                newQ.offer(curr - 1);
            }
        }
        return shortestPath(newQ, level + 1);
    }

    public int helper(int n){
        if(n == Integer.MAX_VALUE) return 32;
        if(n == 1) return 0;
        if(n % 2 == 0){
            return 1 + helper(n / 2);
        }else{
            return 1 + Math.min(helper(n - 1), helper(n + 1));
        }
    }

    /*
    LC : 774
    Binary Search
    https://leetcode.com/problems/minimize-max-distance-to-gas-station/ */
    public double minmaxGasDist(int[] stations, int K) {
        double low = 0;
        double high = stations[stations.length - 1] - stations[0];
        while(high - low >= 1e-6){
            double mid = low + (high - low) / 2;
            int cnt = 0;
            for(int i = 0; i < stations.length -1 ; i++){
                cnt += Math.ceil((stations[i+1] - stations[i]) / mid) - 1;
            }
            if(cnt <= K){
                high = mid;
            }else{
                low = mid ;
            }
        }
        return low;
    }

    /*
     LC : 875
     https://leetcode.com/problems/koko-eating-bananas/ 
     Binary Search
     */
    public int minEatingSpeed(int[] piles, int H) {
        int low = 1;
        int high = (int)1e9; // Arrays.stream(piles).max().getAsInt();
        while(low < high){
            int mid = low + (high - low) / 2;
            if(!canEatAll(piles, mid, H)){
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }
    public boolean canEatAll(int[] piles, int k, int h) {
        int hours = 0;
        for (int pile : piles) {
            hours += pile / k;
            if (pile % k != 0) {
                hours++;
            }
        }
        return hours <= h;
    }

    /* LC: 896
     https://leetcode.com/problems/monotonic-array/ */
    public boolean isMonotonic(int[] arr) {
        boolean incr = false;
        boolean decr = false;
        for (int i = 1; i < arr.length; i++) {
            if(arr[i] == arr[i-1]){
                continue;
            }
            if(arr[i] > arr[i-1]){
                if(incr) return false;
                decr = true;
            }else{
                if(decr) return false;
                incr = true;
            }
        }
        return true;
    }
    
    /*
    LC : 1011
    https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/
    Binary Search
    */
    public int shipWithinDays(int[] weights, int D) {
        IntStream stream = Arrays.stream(weights);
        int totalSum = stream.sum();
        OptionalInt min = Arrays.stream(weights).max();
        int low = Math.max(totalSum / weights.length , min.getAsInt());
        int high = totalSum;
        while(low < high){
            int mid = low + (high - low) / 2;
            int sum = 0;
            int count = 1;
            for (int i = 0 ; i < weights.length ; i++) {
                if(sum + weights[i] > mid){
                    count++;
                    sum = 0;
                }
                sum += weights[i];
            }
            if(count > D) {
                low = mid + 1;
            }else{
                high = mid;
            }
        }
        return low;
    }

    /*
    LC : 1482
    https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/
    Binary Search
    */
    public int minDays(int[] bloomDay, int m, int k) {
        int n = bloomDay.length;
        if(m * k > n) return -1;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            min = Math.min(min, bloomDay[i]);
            max = Math.max(max, bloomDay[i]);
        }
        int left = min;
        int right = max;
        while (left < right){
            int mid = left + (right - left) / 2;
            if(getBouq(bloomDay, mid, k) < m){
                left = mid + 1;
            }else{
                right = mid;
            }
        }
        return left;
    }

    public int getBouq(int[] bloomDay, int day, int k) {
        int flow = 0, bouq = 0, n = bloomDay.length;
        for (int i = 0; i < n; i++) {
            if(bloomDay[i] > day){
                flow = 0;
            }else if(++flow == k){
                bouq++;
                flow = 0;
            }
        }
        return bouq;
    }

    /*
    LC : 468
    https://leetcode.com/problems/validate-ip-address/
    */
    public String validIPAddress(String ipaddress) {
        String[] ipv4 = ipaddress.split("\\.", -1);
        String[] ipv6 = ipaddress.split("\\:", -1);
        if(ipaddress.chars().filter(ch -> ch == '.').count() == 3){
            for (String s : ipv4){
                if(isIpV4(s))continue;
                else{
                    return "Neither";
                }
            }
            return "IPv4";
        }
        if(ipaddress.chars().filter(ch -> ch == '.').count() == 7){
            for (String s : ipv6){
                if(isIpV6(s))continue;
                else{
                    return "Neither";
                }
            }
            return "IPv6";
        }
        return "Neither";
    }

    private boolean isIpV4(String s) {
        try{
            return String.valueOf(Integer.valueOf(s)).equals(s) &&
                    Integer.parseInt(s) >= 0 && Integer.parseInt(s) <= 255;
        }catch (NumberFormatException ex){
            return false;
        }
    }

    private boolean isIpV6(String s) {
        if(s.length() > 4) return false;
        try {
            return Integer.parseInt(s, 16) >= 0 && s.charAt(0) != '-';
        }catch (NumberFormatException ex){
            return false;
        }
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
