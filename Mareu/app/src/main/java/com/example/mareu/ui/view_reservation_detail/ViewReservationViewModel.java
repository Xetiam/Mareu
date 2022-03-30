package com.example.mareu.ui.view_reservation_detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;
import com.example.mareu.ui.add_reservation.AddReservationState;

import java.util.ArrayList;

public class ViewReservationViewModel extends ViewModel {
    private MutableLiveData<ViewReservationState> _state = new MutableLiveData<>();
    LiveData<ViewReservationState> state = _state;
    private ReservationApiService mApiService;
    private int index;
    private ArrayList<Reservation> mReservations;

    public ViewReservationViewModel(ReservationApiService mApiService) {
        this.mApiService = mApiService;
        this.mReservations = mApiService.getReservation();
    }

    public void addMeToMeeting() {
        Reservation mReservation = mReservations.get(index);
        if(!mReservation.getParticipants().contains(mApiService.getUserName())){
            mReservation.getParticipants().add(mApiService.getUserName());
            mReservations.set(index, mReservation);
            this._state.postValue(new ViewReservationStateListUpdated(mReservation.getParticipantsFormated(), true));
        }
        else{
            mReservation.getParticipants().remove(mApiService.getUserName());
            mReservations.set(index, mReservation);
            this._state.postValue(new ViewReservationStateListUpdated(mReservation.getParticipantsFormated(), false));
        }
    }

    public void initReservation(Reservation mReservation) {
        this.index = mReservations.indexOf(mReservation);
    }

    public void isMyMail(ArrayList<String> participants) {
        this._state.postValue(new ViewReservationStateInit(participants.contains(mApiService.getUserName())));
    }
}
