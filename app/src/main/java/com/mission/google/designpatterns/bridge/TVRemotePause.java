package com.mission.google.designpatterns.bridge;

public class TVRemotePause extends RemoteButton {

    public TVRemotePause(EntertainmentDevice newDevice) {
        super(newDevice);
    }

    @Override
    public void buttonNinePressed() {
        System.out.println(remoteName + " was Paused ");
    }
}
