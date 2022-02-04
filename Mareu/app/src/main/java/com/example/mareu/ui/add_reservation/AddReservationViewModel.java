package com.example.mareu.ui.add_reservation;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.R;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;

import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class AddReservationViewModel extends ViewModel {
    MutableLiveData<AddReservationState> state = new MutableLiveData<>();
    private ReservationApiService mApiService;
    public AddReservationViewModel(ReservationApiService mApiService){
        this.mApiService = mApiService;
    }
    public ArrayList<String> initSpinner() {
        ArrayList<MeetingRoom> meetingRooms = mApiService.getMeetingRooms();
        String roomName = "";
        ArrayList<String> roomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms
        ) {
            roomNames.add(meetingRoom.getNameSpinner(roomName));
        }
        return roomNames;
    }

    public void isReservationValid(Date datePicked, int roomId, ArrayList<String> participants) {
        MeetingRoom meetingRoom = mApiService.getMeetingRooms().get(roomId);
        if(meetingRoom.getVacancy(datePicked)){
            this.state.postValue(new AddReservationStateValidRerservation());
        }
        else{
            this.state.postValue(new AddReservationStateInvalidRerservation());
        }
    }

    public void addReservation(int roomId, Date datePicked, String name, ArrayList<String> participants) {
        MeetingRoom meetingRoomSelected = mApiService.getMeetingRooms().get(roomId);
        Reservation newReservation = null;
        newReservation = new Reservation(meetingRoomSelected.getRoomId(),
                datePicked,
                participants,
                name, R.color.red);
        mApiService.createMeeting(newReservation);
    }
}
