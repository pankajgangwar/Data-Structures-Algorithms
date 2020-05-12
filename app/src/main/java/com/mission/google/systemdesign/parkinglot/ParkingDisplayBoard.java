package com.mission.google.systemdesign.parkinglot;

public class ParkingDisplayBoard {
    private String id;
    private HandicappedSpot handicappedSpot;
    private CompactSpot compactSpot;
    private LargeSpot largeSpot;
    private ElectricSpot electricSpot;
    private MotorBikeSpot motorBikeSpot;

    public ParkingDisplayBoard(String id, HandicappedSpot handicappedSpot,
                               CompactSpot compactSpot, LargeSpot largeSpot,
                               ElectricSpot electricSpot, MotorBikeSpot motorBikeSpot) {
        this.id = id;
        this.handicappedSpot = handicappedSpot;
        this.compactSpot = compactSpot;
        this.largeSpot = largeSpot;
        this.electricSpot = electricSpot;
        this.motorBikeSpot = motorBikeSpot;
    }

    public void showEmptySpotNumber(){
        String message = "";
        if(handicappedSpot.isFree()){
            message += "Free Handicapped: " + handicappedSpot.getNumber();
        } else {
            message +=  "Handicapped is full ";
        }
        message += System.lineSeparator();

        if(compactSpot.isFree()){
            message += "Free Compact: " + compactSpot.getNumber();
        } else {
            message +=  "Compact is full ";
        }
        message += System.lineSeparator();

        if(largeSpot.isFree()){
            message += "Free Large : " + largeSpot.getNumber();
        } else {
            message +=  "Large is full ";
        }
        message += System.lineSeparator();

        if(motorBikeSpot.isFree()){
            message += "Free motorbike : " + motorBikeSpot.getNumber();
        } else {
            message +=  "Motorbike is full ";
        }
        message += System.lineSeparator();

        if(electricSpot.isFree()){
            message += "Free electric : " + electricSpot.getNumber();
        } else {
            message +=  "Electric is full ";
        }
        message += System.lineSeparator();
        showMessage(message);
    }

    public void showMessage(String message){
        System.out.println("message = " + message);
    }

    public ParkingSpot getHandicappedFreeSpot() {
        return handicappedSpot;
    }

    public void setHandicappedFreeSpot(HandicappedSpot spot){
        handicappedSpot = spot;
    }

    public void setCompactFreeSpot(CompactSpot spot){
        compactSpot = spot;
    }

    public CompactSpot getCompactFreeSpot(){
        return compactSpot;
    }
}
