package com.example.mareu.event;

import com.example.mareu.model.Reservation;


public class DeleteReservationEvent {
    public final Reservation reservation;

    public DeleteReservationEvent(Reservation reservation) {
        this.reservation = reservation;
    }
}
