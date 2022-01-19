package com.example.mareu.service;

import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;

import java.util.ArrayList;

public class DummyReservationApiService implements ReservationApiService {
    private final ArrayList<MeetingRoom> meetingRooms = DummyMeetingRoomGenerator.generateMeetingRooms();
    private final ArrayList<Reservation> reservations = null;

    @Override
    public ArrayList<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    @Override
    public void deleteMeeting(Reservation reservation) {
        reservations.remove(reservation);
    }

    @Override
    public void createMeeting(Reservation reservation) {
        int roomId = reservation.getRoomId();
        MeetingRoom meetingRoom = meetingRooms.get(roomId);
        if(meetingRoom.getVacancy()){
            meetingRoom.setVacancy(false);
            meetingRoom.setLastReservation(reservation.getMeetingDate());
            meetingRoom.setParticipants(reservation.getParticipants());
        }
        reservations.add(reservation);
    }
}
