package com.mission.google.systemdesign.parkinglot;

import java.util.HashMap;

public class ParkingFloor {
    private String name;
    private HashMap<String, HandicappedSpot> handicappedSpots;
    private HashMap<String, CompactSpot> compactSpots;
    private HashMap<String, ElectricSpot> electricSpots;
    private HashMap<String, ElectricSpot> motorbikeSpots;
    private HashMap<String, LargeSpot> largeSpots;
    private HashMap<String, CustomerInfoPanel> customerInfoPanel;
    private ParkingDisplayBoard displayBoard;
    private int freeElectricSpotCount;
    private int freeHandicappedSpotCount;
    private int freeCompactSpotCount;
    private int freeLargeSpotCount;
    private int freeMotorbikeSpotCount;

    public ParkingFloor(String name){
        this.name = name;
    }

    public void addParkingSpot(ParkingSpot spot){
        switch (spot.getType()){
            case HANDICAPPED:
                handicappedSpots.put(spot.getNumber(), (HandicappedSpot) spot);
                break;
            case COMPACT:
                compactSpots.put(spot.getNumber(), (CompactSpot) spot);
                break;
            case LARGE:
                largeSpots.put(spot.getNumber(), (LargeSpot) spot);
                break;
            case MOTORBIKE:
                motorbikeSpots.put(spot.getNumber(), (ElectricSpot) spot);
                break;
            case ELECTRIC:
                electricSpots.put(spot.getNumber(), (ElectricSpot) spot);
                break;
            default:
                System.out.println("wrong parking spot type = " + spot);
        }
    }

    public void assignVehicleSpot(Vehicle vehicle, ParkingSpot spot){
        spot.assignVehicle(vehicle);
        switch (spot.getType()){
            case HANDICAPPED:
            case ELECTRIC:
            case MOTORBIKE:
            case LARGE:
            case COMPACT:
                updateDisplayBoardForHandicapped(spot);
                break;
            default:
                System.out.println("wrong parking spot type = " + spot);

        }
    }

    private void updateDisplayBoardForHandicapped(ParkingSpot spot) {
        if (this.displayBoard.getHandicappedFreeSpot().getNumber() == spot.getNumber()) {
            // find another free handicapped parking and assign to displayBoard
            for (String key : handicappedSpots.keySet()) {
                if (handicappedSpots.get(key).isFree()) {
                    this.displayBoard.setHandicappedFreeSpot(handicappedSpots.get(key));
                }
            }
            this.displayBoard.showEmptySpotNumber();
        }
    }

    private void updateDisplayBoardForCompact(ParkingSpot spot) {
        if (this.displayBoard.getCompactFreeSpot().getNumber() == spot.getNumber()) {
            // find another free compact parking and assign to displayBoard
            for (String key : compactSpots.keySet()) {
                if (compactSpots.get(key).isFree()) {
                    this.displayBoard.setCompactFreeSpot(compactSpots.get(key));
                }
            }
            this.displayBoard.showEmptySpotNumber();
        }
    }

    public void freeSpot(ParkingSpot spot) {
        spot.removeVehicle();
        switch (spot.getType()) {
            case HANDICAPPED:
                freeHandicappedSpotCount++;
                break;
            case COMPACT:
                freeCompactSpotCount++;
                break;
            case LARGE:
                freeLargeSpotCount++;
                break;
            case MOTORBIKE:
                freeMotorbikeSpotCount++;
                break;
            case ELECTRIC:
                freeElectricSpotCount++;
                break;
            default:
                System.out.println("Wrong parking spot type!");
        }
    }

    public boolean isFull() {
        if (freeCompactSpotCount + freeElectricSpotCount + freeHandicappedSpotCount + freeLargeSpotCount
                + freeMotorbikeSpotCount == 40) {
            return true;
        }
        return false;
    }

}
