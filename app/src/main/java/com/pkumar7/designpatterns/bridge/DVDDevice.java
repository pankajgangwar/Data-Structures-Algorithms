package com.pkumar7.designpatterns.bridge;

public class DVDDevice extends EntertainmentDevice {

    public DVDDevice(String newName, int newDeviceState, int newMaxSetting) {
        deviceState = newDeviceState;
        maxSetting = newMaxSetting;
        name = newName;
    }

    @Override
    public void buttonFivePressed() {
        System.out.println(" DVD Down " );
        deviceState--;
    }

    @Override
    public void buttonSixPressed() {
        System.out.println(" DVD UP " );
        deviceState++;
    }
}
