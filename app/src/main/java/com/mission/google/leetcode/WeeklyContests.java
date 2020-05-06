package com.mission.google.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class WeeklyContests {

    public static void main(String arg[]){
        WeeklyContests contests = new WeeklyContests();
        int [][] trips = new int[][]{{3,2,8},{4,4,6},{10,8,9}};

        boolean status = contests.carPooling(trips,11);
        System.out.println(status);
    }

    /**
     * https://leetcode.com/contest/weekly-contest-142/problems/statistics-from-a-large-sample/
     *
     * Return the minimum, maximum, mean, median, and mode
     *
     * Input: count = [0,1,3,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     * 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     * 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     * 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     * 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
     * 0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
     *
     *  ~ final arr: [1,2,2,2,3,3,3,3]
     *
     * Output: [1.00000,3.00000,2.37500,2.50000,3.00000]
     *
     *
     * ***/
    public double[] sampleStats(int[] count) {
        int total = 0, mode = 0;
        double median = 0, min = -1, max = 0, mean = 0, sum = 0;
        for (int i = 0; i < 256; i++) {
             if(count[i] > 0){
                 total += count[i];
                 if(min < 0) min = i;
                 max = i;
                 sum += i * count[i];
                 if(count[i] > count[mode]) mode = i;
             }
        }

        mean = sum / total;
        if(total == 1) median = sum;
        int m1 = (total + 1 ) / 2, m2 = total / 2 + 1;
        for(int i = 0, cnt = 0; total > 1 && i < 256; ++i){
            if(cnt < m1 && cnt + count[i] >= m1)
                median += i / 2.0d;
            if(cnt < m2 && cnt + count[i] >= m2)
                median += i / 2.0d;

            cnt += count[i];
        }

        return new double[]{min, max, mean, median, mode};
    }

    /*** 1094. Car Pooling
     * https://leetcode.com/problems/car-pooling/
     *
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 4
     * Output: false
     *
     * Input: trips = [[2,1,5],[3,3,7]], capacity = 5
     * Output: true
     *
     * **/
    class Trip{
        int passengers ;
        int start_loc;
        int end_loc;
        public Trip(int pass, int startLoc, int endLoc){
            this.passengers = pass;
            this.start_loc = startLoc;
            this.end_loc = endLoc;
        }
    }
    public boolean carPooling(int[][] trips, int capacity) {
        //Sort the trips with start location
        ArrayList<Trip> all_trips = new ArrayList<>();

        for(int i = 0; i < trips.length; i++) {
            Trip myTrip = new Trip(trips[i][0],trips[i][1],trips[i][2]);
            all_trips.add(myTrip);
        }

        all_trips.sort((t1, t2) -> {
            if(t1.start_loc > t2.start_loc){
                return 1;
            }else if(t1.start_loc < t2.start_loc){
                return -1;
            }else{
                return 0;
            }
        });

        Queue<Trip> mQueue = new LinkedList<>();
        int capacity_left = capacity;

        for (int i = 0; i < all_trips.size(); i++) {
            Trip trip = all_trips.get(i);

            if(capacity_left == capacity){
                if(!mQueue.isEmpty()) {
                    capacity_left += updateQueue(mQueue, trip.start_loc);
                    if(capacity_left < trip.passengers){
                        return false;
                    }
                    mQueue.offer(trip);
                    capacity_left = capacity_left - trip.passengers;
                }else{
                    //No older trips to processed
                    mQueue.offer(trip);
                    capacity_left = capacity_left - trip.passengers;
                }
            }else{
                if(!mQueue.isEmpty()) {
                    capacity_left += updateQueue(mQueue, trip.start_loc);
                }
                //There is a room in the car
                if(capacity_left < trip.passengers){
                    return false;
                }

                mQueue.offer(trip);
                capacity_left = capacity_left - trip.passengers;
            }
        }
        return true;
    }

    public int updateQueue(Queue<Trip> queue,int start_loc){

        Iterator<Trip> iterator = queue.iterator();
        int total_pass_deboarded = 0;

        while(iterator.hasNext()) {
            Trip this_trip = iterator.next();
            if(start_loc >= this_trip.end_loc) {
                total_pass_deboarded += this_trip.passengers;
                iterator.remove();
            }
        }
        return total_pass_deboarded;
    }


}
