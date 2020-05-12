package com.mission.google.systemdesign.parkinglot;

public abstract class ParkingSpot {
    private String number;
    private boolean free;
    private Vehicle vehicle;
    private final ParkingSpotType type;


    protected ParkingSpot(ParkingSpotType type) {
        this.type = type;
    }
    public void assignVehicle(Vehicle vehicle){
        this.vehicle = vehicle;
        free = false;
    }

    public void removeVehicle(){
        this.vehicle = null;
        free = true;
    }

    public ParkingSpotType getType(){
        return type;
    }

    public String getNumber() {
        return number;
    }

    public boolean isFree() {
        return free;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }
}
