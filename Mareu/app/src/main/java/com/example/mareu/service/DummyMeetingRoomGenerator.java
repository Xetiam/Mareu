package com.example.mareu.service;

import com.example.mareu.R;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class DummyMeetingRoomGenerator {
    public static ArrayList<MeetingRoom> generateMeetingRooms() {
        ArrayList<MeetingRoom> meetingRooms = new ArrayList<>();
        for (int i = 0; i<=9; i++){
            meetingRooms.add(new MeetingRoom(i));
        }
        return meetingRooms;
    }
}
