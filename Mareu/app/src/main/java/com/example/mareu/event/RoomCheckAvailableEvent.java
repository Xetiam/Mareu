package com.example.mareu.event;

import com.example.mareu.model.MeetingRoom;
import com.google.android.material.button.MaterialButton;

import java.util.Date;

public class RoomCheckAvailableEvent {
    public Date datePicked;
    public MaterialButton addbutton;
    public MeetingRoom meetingRoom;
    public RoomCheckAvailableEvent(Date datePicked, MaterialButton addButton, MeetingRoom roomSelected){
        this.datePicked = datePicked;
        this.addbutton = addButton;
        this.meetingRoom = roomSelected;
        if(meetingRoom.getVacancy(datePicked)){
            addButton.setEnabled(true);
        }
        else{
            addButton.setEnabled(false);
        }
    }
}
