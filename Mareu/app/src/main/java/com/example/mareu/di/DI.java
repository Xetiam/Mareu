package com.example.mareu.di;

import com.example.mareu.service.DummyReservationApiService;
import com.example.mareu.service.ReservationApiService;


public class DI {

    private static final ReservationApiService service = new DummyReservationApiService();

    public static ReservationApiService getReservationApiService() {
        return service;
    }

    public static ReservationApiService getNewInstanceApiService() {
        return new DummyReservationApiService();
    }
}
