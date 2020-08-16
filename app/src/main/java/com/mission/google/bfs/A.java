package com.mission.google.bfs;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Pankaj Kumar on 16/August/2020
 */
class A {

    /*1553. Minimum Number of Days to Eat N Oranges
    * https://leetcode.com/problems/minimum-number-of-days-to-eat-n-oranges/
    */
    public int minDaysBfs(int n) {
        Queue<Integer> dq = new LinkedList<>();
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(n, 0);
        dq.add(n);
        while (true){
            int curr = dq.poll();
            if(curr == 0) return map.get(curr);
            int days = map.get(curr);
            if(!map.containsKey(curr - 1)){
                map.put(curr -1 , days + 1);
                dq.add(curr - 1);
            }
            if(curr % 2 == 0 && !map.containsKey(curr / 2)){
                map.put(curr / 2, days + 1 );
                dq.add(curr / 2);
            }

            if(curr % 3 == 0 && !map.containsKey( curr / 3)){
                map.put(curr / 3, days + 1 );
                dq.add(curr / 3);
            }
        }
    }
}
