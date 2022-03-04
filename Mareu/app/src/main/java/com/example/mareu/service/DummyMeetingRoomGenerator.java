package com.example.mareu.service;

import com.example.mareu.R;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DummyMeetingRoomGenerator {
    public static ArrayList<MeetingRoom> generateMeetingRooms() {
        ArrayList<MeetingRoom> meetingRooms = new ArrayList<>();
        for (int i = 0; i<=9; i++){
            meetingRooms.add(new MeetingRoom(i));
        }
        return meetingRooms;
    }
    public enum MeetingRoomName{
        ROOM_ZERO("Mario",0),
        ROOM_ONE("Luigi", 1),
        ROOM_TWO("Peach", 2),
        ROOM_THREE("Daisy", 3),
        ROOM_FOUR("Donkey Kong", 4),
        ROOM_FIVE("Kirby", 5),
        ROOM_SIX("MetaKnight", 6),
        ROOM_SEVEN("Link", 7),
        ROOM_EIGHT("Ganondorf", 8),
        ROOM_NINE("Zelda", 9);

        private final String name;
        private final int id;
        private static Map<Integer, String> idToNameMapping;
        MeetingRoomName(String name, int id) {
            this.name = name;
            this.id = id;
        }
        public static String getName(Integer id){
            if(idToNameMapping == null){
                initMapping();
            }
            return idToNameMapping.get(id);
        }

        private static void initMapping() {
            idToNameMapping = new HashMap<>();
            for(MeetingRoomName val : values()){
                idToNameMapping.put(val.id,val.name);
            }
        }
    }
}
