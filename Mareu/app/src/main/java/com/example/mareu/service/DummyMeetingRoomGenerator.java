package com.example.mareu.service;

import com.example.mareu.model.MeetingRoom;

import java.util.ArrayList;

public class DummyMeetingRoomGenerator {
    public static ArrayList<MeetingRoom> generateMeetingRooms() {
        ArrayList<MeetingRoom> meetingRooms = null; 
        for (int i = 0; i<=9; i++){
            meetingRooms.add(new MeetingRoom(i));
        }
        return meetingRooms;
    }
}
