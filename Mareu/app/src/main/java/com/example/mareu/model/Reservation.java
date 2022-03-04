package com.example.mareu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

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
        this.creationCalendar = creationDate;
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
        if(this.name.length() > 20){
            return this.name.substring(0,19)+"...";
        }
        return this.name;
    }

    public int getColor() {
        return this.color;
    }

    public Date getEndMeetingDate() {
        Date date = this.getMeetingDate();
        int hour = date.getHours();
        int minute = date.getMinutes();
        if(minute>=14){
            hour = hour + 1;
            minute = minute - 15;
        }
        else{
            minute = minute + 45;
        }
        return new Date(date.getYear(), date.getMonth(), date.getDate(),hour,minute);
    }

    public Date getBeforeMeetingDate() {
        Date date = this.getMeetingDate();
        int hour = date.getHours();
        int minute = date.getMinutes();
        if(minute<=15){
            hour = hour - 1;
            minute = minute + 15;
        }
        else{
            minute = minute - 45;
        }
        return new Date(date.getYear(), date.getMonth(), date.getDate(),hour,minute);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return roomId == that.roomId && color == that.color && Objects.equals(meetingDate, that.meetingDate) && Objects.equals(participants, that.participants) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, meetingDate, participants, name, color);
    }

    public String getMeetingDateString() {
        Calendar today = Calendar.getInstance();
        if(getMeetingDate().getDate() == today.get(Calendar.DAY_OF_MONTH) && getMeetingDate().getMonth() == today.get(Calendar.MONTH) && getMeetingDate().getYear() == today.get(Calendar.YEAR)){
            if(getMeetingDate().getMinutes() >= 10){
                return getMeetingDate().getHours() + "h" + getMeetingDate().getMinutes();
            }
            else{
                return getMeetingDate().getHours() + "h0" + getMeetingDate().getMinutes();
            }
        }
        else{
            return String.valueOf(getMeetingDate().getDate()) + "/" + String.valueOf(getMeetingDate().getMonth()) + "/" + String.valueOf(getMeetingDate().getYear());
        }
    }

    public Calendar getCreationCalendar() {
        return creationCalendar;
    }
}
