package com.example.mareu;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import com.example.mareu.di.DI;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.DummyMeetingRoomGenerator;
import com.example.mareu.service.ReservationApiService;
import com.example.mareu.ui.list_reservation.adapter.ReservationRecyclerViewAdapter;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import java.util.Date;

@RunWith(JUnit4.class)
public class ReservationServiceTest {

    private ReservationApiService service;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void getReservationsWithSuccess() {
        ArrayList<Reservation> reservations = service.getReservation();
        ArrayList<Reservation> expectedReservations = new ArrayList<Reservation>();
        Reservation r1 = new Reservation(0,new Date(),new ArrayList<String>(),"test1", 6);
        service.createMeeting(r1);
        expectedReservations.add(r1);
        Reservation r2 = new Reservation(1,new Date(),new ArrayList<String>(),"test2", 6);
        service.createMeeting(r2);
        expectedReservations.add(r2);
        Reservation r3 = new Reservation(2,new Date(),new ArrayList<String>(),"test3", 6);
        service.createMeeting(r3);
        expectedReservations.add(r3);
        assertThat(reservations, IsIterableContainingInAnyOrder.containsInAnyOrder(expectedReservations.toArray()));
    }
    @Test
    public void getMeetingRoomWithSuccess() {
        ArrayList<MeetingRoom> meetingRooms = service.getMeetingRooms();
        ArrayList<MeetingRoom> expectedMeetingRooms = DummyMeetingRoomGenerator.generateMeetingRooms();
        assertEquals(meetingRooms, expectedMeetingRooms);
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Reservation r1 = new Reservation(0,new Date(),new ArrayList<String>(),"test1", 6);
        Reservation r2 = new Reservation(1,new Date(),new ArrayList<String>(),"test2", 6);
        Reservation r3 = new Reservation(2,new Date(),new ArrayList<String>(),"test3", 6);
        service.createMeeting(r1);
        service.createMeeting(r2);
        service.createMeeting(r3);
        Reservation reservationToDelete = service.getReservation().get(0);
        service.deleteMeeting(reservationToDelete);
        assertFalse(service.getReservation().contains(reservationToDelete));
    }

    @Test
    public void CreateNeighbourWithSuccess() {
        Reservation reservationToCreate = new Reservation(0,new Date(),new ArrayList<String>(),"test1", 6);
        service.createMeeting(reservationToCreate);
        assertTrue(service.getReservation().contains(reservationToCreate));
    }
}
