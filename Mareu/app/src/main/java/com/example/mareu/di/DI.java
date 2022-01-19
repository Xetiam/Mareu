package com.example.mareu.di;

import com.example.mareu.model.Reservation;
import com.example.mareu.service.DummyReservationApiService;
import com.example.mareu.service.ReservationApiService;


public class DI {

    private static final ReservationApiService service = new DummyReservationApiService();

    public static ReservationApiService getNeighbourApiService() {
        return service;
    }

    public static ReservationApiService getNewInstanceApiService() {
        return new DummyReservationApiService();
    }
}
