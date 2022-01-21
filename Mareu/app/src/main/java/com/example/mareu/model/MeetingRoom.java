package com.example.mareu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MeetingRoom {
    //Attributes
    private Integer roomId;
    private ArrayList<String> participants;
    private Date lastReservation;
    private Boolean isVacancy;

    //Constructor
    public MeetingRoom(Integer roomId){
        this.roomId = roomId;
        this.participants = new ArrayList();
        this.lastReservation = new Date();
        this.isVacancy = true;
    }

    //Getter/Setter
    public int getRoomId(){
        return this.roomId;
    }

    public ArrayList<String> getParticipants(){
        return this.participants;
    }

    public Date getLastReservation(){ return this.lastReservation; }

    public Boolean getVacancy() {
        return this.isVacancy;
    }

    public void setVacancy(Boolean isVacancy){
        this.isVacancy = isVacancy;
    }

    public void setParticipants(ArrayList<String> participants){
        this.participants = participants;
    }

    public void setLastReservation(Date lastReservation){
        this.lastReservation = lastReservation;
    }
}
