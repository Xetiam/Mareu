package com.example.mareu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Reservation implements Serializable {
    //Attributes
    private int roomId;
    private Date meetingDate;
    private ArrayList<String> participants;
    private final String name;
    private int color;

    //Constructor
    public Reservation(int roomId, Date meetingDate, ArrayList<String> participants, String name, int color){
        this.roomId = roomId;
        this.meetingDate = meetingDate;
        this.participants = participants;
        this.name = name;
        this.color = color;
    }

    //Getter
    public int getRoomId(){
        return this.roomId;
    }

    public Date getMeetingDate(){
        return this.meetingDate;
    }

    public ArrayList<String> getParticipants(){
        return this.participants;
    }

    public String getName() {
        return this.name + " - " + this.roomId + " - " + this.getMeetingDate();
    }

    public int getColor() {
        return this.color;
    }

    public Date getEndMeetingDate() {
        Date date = this.getMeetingDate();
        int hour = date.getHours();
        int minute = date.getMinutes();
        if(minute>=14){
            hour = +1;
            minute = minute - 15;
        }
        else{
            minute = minute + 45;
        }
        return new Date(date.getYear(), date.getMonth(), date.getDate(),hour,minute);
    }
}
