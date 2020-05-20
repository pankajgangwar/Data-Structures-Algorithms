package com.mission.google.designpatterns.bridge;

public abstract class RemoteButton {
    private EntertainmentDevice theDevice;
    protected String remoteName;
    public RemoteButton(EntertainmentDevice newDevice) {
        this.theDevice = newDevice;
        this.remoteName = newDevice.name;
    }

    public void buttonFivePressed(){
        theDevice.buttonFivePressed();
    }

    public void buttonSixPressed(){
        theDevice.buttonSixPressed();
    }

    public void deviceFeedback(){
        theDevice.deviceFeedback();
    }

    public abstract void buttonNinePressed();

}
