package com.pkumar7.design;

/**
 * Created by Pankaj Kumar on 16/November/2020
 * https://leetcode.com/problems/design-parking-system/
 */
class ParkingSystem {
    int[] parkinglot = new int[4];
    int big, medium, small;

    public ParkingSystem(int big, int medium, int small) {
        this.big = big;
        this.medium = medium;
        this.small = small;
    }

    public boolean addCar(int carType) {
        switch (carType) {
            case 1:
                if (parkinglot[carType] == big) {
                    return false;
                }
                parkinglot[carType]++;
                break;
            case 2:
                if (parkinglot[carType] == medium) {
                    return false;
                }
                parkinglot[carType]++;
                break;
            case 3:
                if (parkinglot[carType] == small) {
                    return false;
                }
                parkinglot[carType]++;
                break;
        }
        return true;
    }
}
