package com.example.mareu.service;

import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;

import java.util.ArrayList;

public class DummyReservationApiService implements ReservationApiService {
    private final ArrayList<MeetingRoom> meetingRooms = DummyMeetingRoomGenerator.generateMeetingRooms();
    private final ArrayList<Reservation> reservations = new ArrayList<>();

    @Override
    public ArrayList<MeetingRoom> getMeetingRooms() {
        return meetingRooms;
    }

    @Override
    public ArrayList<Reservation> getReservation() {
        return reservations;
    }

    @Override
    public void deleteMeeting(Reservation reservation) {
        int id = reservation.getRoomId();
        meetingRooms.get(id).getReservations().remove(reservation);
        reservations.remove(reservation);
    }

    @Override
    public void createMeeting(Reservation reservation) {
        int roomId = reservation.getRoomId();
        MeetingRoom meetingRoom = meetingRooms.get(roomId);
        meetingRoom.addReservation(reservation);
        reservations.add(reservation);
    }

    @Override
    public String getMeetingRoomName(int roomId) {
        return DummyMeetingRoomGenerator.MeetingRoomName.getName(roomId);
    }

    @Override
    public String getUserName() {
        return "Xetiam@gmail.com";
    }
}
