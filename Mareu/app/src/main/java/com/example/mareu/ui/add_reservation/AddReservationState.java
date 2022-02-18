package com.example.mareu.ui.add_reservation;

import java.util.ArrayList;

public class AddReservationState {

}
class AddReservationStateInit extends AddReservationState {
    private ArrayList<String> roomNames = new ArrayList<>();

    public AddReservationStateInit(ArrayList<String> roomNames) {
        this.roomNames = roomNames;
    }

    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(ArrayList<String> roomNames) {
        this.roomNames = roomNames;
    }
}
class AddReservationStateUpdated extends AddReservationState {
    private Boolean isValid = false;
    private Boolean isMailValid = false;
    private Boolean isNameValid = false;
    private ArrayList<String> roomNames = new ArrayList<>();

    public AddReservationStateUpdated(Boolean isValid, Boolean isMailValid, Boolean isNameValid, ArrayList<String> roomNames) {
        this.isValid = isValid;
        this.isMailValid = isMailValid;
        this.isNameValid = isNameValid;
        this.roomNames = roomNames;
    }

    public Boolean getValid() {
        return isValid;
    }

    public Boolean getMailValid() {
        return isMailValid;
    }


    public Boolean getNameValid() {
        return isNameValid;
    }
}
class AddReservationStateAddPart extends AddReservationState {
    private ArrayList<String> participants = new ArrayList<>();
    public AddReservationStateAddPart(ArrayList<String> participants) {
        this.participants = participants;
    }
}
class AddReservationStateDeletePart extends AddReservationState{
    private String participant = "";
    public AddReservationStateDeletePart(String participant) {
        this.participant = participant;
    }

    public String getParticipant() {
        return participant;
    }
}
