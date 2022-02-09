package com.example.mareu.ui.add_reservation;

import java.util.ArrayList;

public class AddReservationState {
    private Boolean isValid = false;
    private Boolean isMailValid = false;
    private Boolean isNameValid = false;

    private Boolean isSpinnerInit = false;
    private ArrayList<String> roomNames = new ArrayList<>();

    public AddReservationState(Boolean isValid) {
        this.isValid = isValid;
        this.roomNames = roomNames;
    }

    public Boolean getValid() {
        return isValid;
    }

    public ArrayList<String> getRoomNames() {
        return roomNames;
    }

    public void setRoomNames(ArrayList<String> roomNames) {
        this.roomNames = roomNames;
    }

    public Boolean getMailValid() {
        return isMailValid;
    }

    public void setMailValid(Boolean mailValid) {
        isMailValid = mailValid;
    }

    public Boolean getNameValid() {
        return isNameValid;
    }

    public void setNameValid(Boolean nameValid) {
        isNameValid = nameValid;
    }

    public Boolean getSpinnerInit() {
        return isSpinnerInit;
    }
    public void setSpinnerInit(Boolean spinnerInit) {
        isSpinnerInit = spinnerInit;
    }

    public void setValid(Boolean vacancy) {
        this.isValid = vacancy;
    }
}
