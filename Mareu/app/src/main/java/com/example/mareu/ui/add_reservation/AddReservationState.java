package com.example.mareu.ui.add_reservation;

import java.util.ArrayList;

public class AddReservationState {}
class AddReservationStateInit extends AddReservationState {
    private final ArrayList<String> roomNames;


    public AddReservationStateInit(ArrayList<String> roomNames) {
        this.roomNames = roomNames;

    }

    public ArrayList<String> getRoomNames() {
        return roomNames;
    }


}
class AddReservationStateUpdated extends AddReservationState {
    private final Boolean isValid;
    private final Boolean isMailValid;
    private final Boolean isNameValid;

    public AddReservationStateUpdated(Boolean isValid, Boolean isMailValid, Boolean isNameValid) {
        this.isValid = isValid;
        this.isMailValid = isMailValid;
        this.isNameValid = isNameValid;
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
    public AddReservationStateAddPart() {
    }
}
class AddReservationStateDeletePart extends AddReservationState{
    private final String participant;
    public AddReservationStateDeletePart(String participant) {
        this.participant = participant;
    }

    public String getParticipant() {
        return participant;
    }
}
