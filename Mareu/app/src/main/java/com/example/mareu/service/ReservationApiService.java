package com.example.mareu.service;

import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;

import java.util.ArrayList;

public interface ReservationApiService {
    ArrayList<MeetingRoom> getMeetingRooms();
    void deleteMeeting(Reservation reservation);
    void createMeeting(Reservation reservation);
}
