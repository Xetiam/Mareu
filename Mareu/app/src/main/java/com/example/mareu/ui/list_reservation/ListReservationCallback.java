package com.example.mareu.ui.list_reservation;

import com.example.mareu.model.Reservation;

import java.util.ArrayList;

public interface ListReservationCallback {
    Boolean filterCondition(ArrayList<Reservation> reservations,int i);
}
