package com.example.mareu.ui.list_reservation;

import com.example.mareu.model.Reservation;

import java.util.ArrayList;

public class ListReservationState {
}

class ListReservationUpdated extends ListReservationState {
    public ArrayList<Reservation> reservations;

    public ListReservationUpdated(ArrayList<Reservation> reservations) {
        this.reservations = reservations;
    }
}
