package com.example.mareu.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class MeetingRoom {
    //Attributes
    private Integer roomId;

    private ArrayList<Reservation> reservations = new ArrayList<>();

    //Constructor
    public MeetingRoom(Integer roomId){
        this.roomId = roomId;
    }

    //Getter/Setter
    public int getRoomId(){
        return this.roomId;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public Boolean getVacancy(Date datePicked) {
        for (Reservation reservation: reservations
             ) {
            if(datePicked.after(reservation.getMeetingDate()) && datePicked.before(reservation.getEndMeetingDate())){
                return false;
            }
        }
        return true;
    }

    public String getNameSpinner(String roomName){
        int position = this.getRoomId();
        roomName = "Salle n°" + position;
        return  roomName;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }
}
