package com.example.mareu.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class MeetingRoom {
    //Attributes
    private final Integer roomId;

    private final ArrayList<Reservation> reservations = new ArrayList<>();

    //Constructor
    public MeetingRoom(Integer roomId) {
        this.roomId = roomId;
    }

    //Getter/Setter
    public int getRoomId() {
        return this.roomId;
    }

    public ArrayList<Reservation> getReservations() {
        return reservations;
    }

    public Boolean getVacancy(Calendar datePicked) {
        for (Reservation reservation : reservations
        ) {
            if ((datePicked.after(reservation.getMeetingCalendar()) && datePicked.before(reservation.getEndMeetingDate())) ||
                    (datePicked.after(reservation.getBeforeMeetingDate()) && datePicked.before(reservation.getMeetingCalendar())) ||
                    (datePicked.equals(reservation.getMeetingCalendar()))) {
                return false;
            }
        }
        return true;
    }

    public String getNameSpinner(String roomName) {
        String spinnerRow;
        spinnerRow = "Salle " + roomName;
        return spinnerRow;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MeetingRoom that = (MeetingRoom) o;
        return Objects.equals(roomId, that.roomId) && Objects.equals(reservations, that.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, reservations);
    }
}
