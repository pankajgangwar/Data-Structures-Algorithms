package com.mission.google.systemdesign.parkinglot;

public abstract class Admin extends Account {
    public abstract boolean addParkingFloor(ParkingFloor floor);
    public abstract boolean addParkingSpot(String floorName, ParkingSpot spot);
    public abstract boolean addParkingDisplayBoard(String floorName, ParkingDisplayBoard displayBoard);
    public abstract boolean addCustomerInfoPanel(String floorName, CustomerInfoPanel infoPanel);
    public abstract boolean addEntrancePanel(EntrancePanel entrancePanel);
    public abstract boolean addExitPanel(ExitPanel panel);
}
