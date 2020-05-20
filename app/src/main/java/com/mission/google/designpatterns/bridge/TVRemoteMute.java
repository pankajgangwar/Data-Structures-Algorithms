package com.mission.google.designpatterns.bridge;

public class TVRemoteMute extends RemoteButton {

    EntertainmentDevice currentDevice;
    public TVRemoteMute(EntertainmentDevice newDevice) {
        super(newDevice);
        currentDevice = newDevice;
    }

    @Override
    public void buttonNinePressed() {
        System.out.println( remoteName + " was muted ");
    }
}
