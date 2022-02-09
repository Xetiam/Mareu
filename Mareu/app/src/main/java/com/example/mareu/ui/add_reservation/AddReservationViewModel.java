package com.example.mareu.ui.add_reservation;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.R;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;

import java.util.ArrayList;
import java.util.Date;

public class AddReservationViewModel extends ViewModel {
    private MutableLiveData<AddReservationState> _state = new MutableLiveData<>();
    LiveData<AddReservationState> state = _state;
    private ReservationApiService mApiService;
    private AddReservationState newState = new AddReservationState(false);

    public AddReservationViewModel(ReservationApiService mApiService) {
        this.mApiService = mApiService;
        this._state.postValue(newState);
    }

    public void isReservationValid(Date datePicked, int roomId, ArrayList<String> participants) {
        MeetingRoom meetingRoom = mApiService.getMeetingRooms().get(roomId);
        this._state.postValue(new AddReservationState(meetingRoom.getVacancy(datePicked)));

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

    public void initSpinner() {
        ArrayList<MeetingRoom> meetingRooms = mApiService.getMeetingRooms();
        String roomName = "";
        ArrayList<String> roomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms
        ) {
            roomNames.add(meetingRoom.getNameSpinner(roomName));
        }
        newState.setRoomNames(roomNames);
        this._state.postValue(newState);
    }

    public void initListener(String s, Boolean mailFormat) {
        if (mailFormat) {
            if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                newState.setMailValid(true);
            } else {
                newState.setMailValid(false);
            }
        } else {
            if (s.length() >= 5) {
                newState.setNameValid(true);
            } else {
                newState.setNameValid(false);
            }
        }
        this._state.postValue(newState);
    }
}
