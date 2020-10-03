package com.pkumar7.designpatterns.bridge;

public abstract class EntertainmentDevice {
    public int deviceState;
    public int maxSetting;
    public int volumeSetting = 0;
    private int volumeLevel = 0;
    public String name;

    public abstract void buttonFivePressed();
    public abstract void buttonSixPressed();
    public void deviceFeedback(){
        if(deviceState > maxSetting || deviceState < 0){
            deviceState = 0;
        }
        System.out.println(" On " + deviceState);
    }

    public void buttonSevenPressed(){
        volumeLevel++;
        System.out.println(" Volume at: " + volumeLevel);
    }

    public void buttonEightPressed(){
        volumeLevel--;
        System.out.println(" Volume at: " + volumeLevel);
    }
}
