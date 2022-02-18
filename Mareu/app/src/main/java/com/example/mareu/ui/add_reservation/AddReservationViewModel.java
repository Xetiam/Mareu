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
    //private AddReservationState newState = new AddReservationState(false);

    private  ArrayList<String> roomNames = new ArrayList<>();
    private Date datePicked = new Date();
    private ArrayList<String> participants = new ArrayList<>();

    private Boolean isValid = false;
    private Boolean isMailValid = false;
    private Boolean isNameValid = false;
    private Boolean isSpinnerInit = false;


    public AddReservationViewModel(ReservationApiService mApiService) {
        this.mApiService = mApiService;
        this._state.postValue(new AddReservationStateInit(roomNames));
    }

    public void isReservationValid(Date datePicked, int roomId) {
        MeetingRoom meetingRoom = mApiService.getMeetingRooms().get(roomId);
        if(participants.size() > 1 && isNameValid){
            isValid = meetingRoom.getVacancy(datePicked);
            this._state.postValue(new AddReservationStateUpdated(isValid, isMailValid, isNameValid, roomNames));
        }
    }

    public void addReservation(int roomId, Date datePicked, String name) {
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
        ArrayList<String> roomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms
        ) {
            roomNames.add(meetingRoom.getNameSpinner(""+meetingRoom.getRoomId()));
        }
        if(!isSpinnerInit){
            isSpinnerInit = true;
            this._state.postValue(new AddReservationStateInit(roomNames));
        }
    }

    public void initListener(String s, Boolean mailFormat) {
        if (mailFormat) {
            if (Patterns.EMAIL_ADDRESS.matcher(s).matches()) {
                isMailValid = true;
            } else {
                isMailValid = false;
            }
        } else {
            if (s.length() >= 5) {
                isNameValid = true;
            } else {
                isNameValid = false;
            }
        }
        this._state.postValue(new AddReservationStateUpdated(isValid, isMailValid, isNameValid, roomNames));
    }

    public void addParticipant(String newParticipant) {
        this.participants.add(newParticipant);
        this._state.postValue(new AddReservationStateAddPart(participants));
    }

    public void deleteParticipant(String participant) {
        if(participants.contains(participant)){
            participants.remove(participant);
            this._state.postValue(new AddReservationStateDeletePart(participant));
        }
    }
}