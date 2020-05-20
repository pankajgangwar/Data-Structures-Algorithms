package com.mission.google.designpatterns.bridge;

public class TVDevice extends EntertainmentDevice{

    public TVDevice(String newName, int newDeviceState, int newMaxSetting) {
        deviceState = newDeviceState;
        maxSetting = newMaxSetting;
        name = newName;
    }

    @Override
    public void buttonFivePressed() {
        System.out.println(" Channel Down " );
        deviceState--;
    }

    @Override
    public void buttonSixPressed() {
        System.out.println(" Channel Up " );
        deviceState++;
    }

}
