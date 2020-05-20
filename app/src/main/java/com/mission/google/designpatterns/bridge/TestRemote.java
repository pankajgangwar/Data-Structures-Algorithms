package com.mission.google.designpatterns.bridge;

public class TestRemote {
    public static void main(String[] args) {
        /* Structural patterns*/
        /* https://www.youtube.com/watch?v=9jIgSsIfh_8 */
        RemoteButton tvButton = new TVRemoteMute(new TVDevice("TV", 1, 200));
        RemoteButton dvdButton = new TVRemotePause(new DVDDevice("DVD", 1, 200));

        //RemoteButton theTV = new TVRemoteDVD(new TVDevice(1, 200));
        System.out.println("Test Live TV with Mute");
        tvButton.buttonFivePressed();
        tvButton.buttonSixPressed();
        tvButton.buttonNinePressed();
        tvButton.deviceFeedback();

        System.out.println("\n Test DVD Player with Pause ");

        dvdButton.buttonFivePressed();
        dvdButton.buttonSixPressed();
        dvdButton.buttonSixPressed();
        dvdButton.buttonSixPressed();
        dvdButton.buttonSixPressed();
        dvdButton.buttonNinePressed();
        dvdButton.deviceFeedback();
    }
}
