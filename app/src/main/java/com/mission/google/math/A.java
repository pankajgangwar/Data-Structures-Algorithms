package com.mission.google.math;

/**
 * Created by Pankaj Kumar on 19/August/2020
 */
class A {

    /* 1041. Robot Bounded In Circle
    * https://leetcode.com/problems/robot-bounded-in-circle/
    * */
    public boolean isRobotBounded(String instructions) {
        //return isCircular(instructions);
        return isRobotBounded1(instructions);
    }

    public boolean isRobotBounded1(String instructions) {
        int[][] dirs = new int[][]{ {0, 1}, {1, 0}, {0, -1}, { -1, 0} };
        int x = 0, y = 0;
        char[] instr = instructions.toCharArray();
        int dir = 0;
        for (char ch : instr) {
            if (ch == 'R') {
                dir = (dir + 1) % 4;
            } else if (ch == 'L') {
                dir = (dir + 3) % 4;
            } else {
                x += dirs[dir][0];
                y += dirs[dir][1];
            }
        }
        return x == 0 && y == 0 || dir > 0;// If robot is not facing north
    }

    public boolean isCircular(String instructions) {
        char[] path = instructions.toCharArray();
        int x = 0, y = 0;
        int dir = 0;
        int run = 4;
        while(run-- > 0){
            for (int i=0; i < path.length; i++) {
                char move = path[i];
                if (move == 'R')
                    dir = (dir + 1) % 4;
                else if (move == 'L')
                    dir = (dir + 3) % 4;
                else {
                    if (dir == 0)
                        y++;
                    else if (dir == 1)
                        x++;
                    else if (dir == 2)
                        y--;
                    else
                        x--;
                }
            }
            run--;
        }
        return (x == 0 && y == 0);
    }
}
