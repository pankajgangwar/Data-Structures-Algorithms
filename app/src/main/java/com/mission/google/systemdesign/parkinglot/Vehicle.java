package com.mission.google.systemdesign.parkinglot;

public abstract class Vehicle {
    private String licenseNumber;
    private final VehicleType type;
    private ParkingTicket ticket;

    public Vehicle(VehicleType type) {
        this.type = type;
    }
    public void assignTicket(ParkingTicket ticket){
        this.ticket = ticket;
    }

    public ParkingTicket getTicket() {
        return ticket;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public VehicleType getType() {
        return type;
    }
}
