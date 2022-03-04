package com.example.mareu.event;

import com.example.mareu.model.MeetingRoom;
import com.google.android.material.button.MaterialButton;

import java.util.Calendar;

public class RoomCheckAvailableEvent {
    public Calendar datePicked;
    public MaterialButton addbutton;
    public MeetingRoom meetingRoom;

    public RoomCheckAvailableEvent(Calendar datePicked, MaterialButton addButton, MeetingRoom roomSelected) {
        this.datePicked = datePicked;
        this.addbutton = addButton;
        this.meetingRoom = roomSelected;
        addButton.setEnabled(meetingRoom.getVacancy(datePicked));
    }
}
