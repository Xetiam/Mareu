package com.example.mareu.ui.list_reservation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.di.DI;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;

import java.util.ArrayList;

public class ListReservationViewModel extends ViewModel {
    private ReservationApiService mApiService;
    private ArrayList<Reservation> mReservations;
    MutableLiveData<ListReservationState> state = new MutableLiveData<>();

    public ListReservationViewModel(ReservationApiService mApiService) {
        this.mApiService = DI.getReservationApiService();
        this.mReservations = mApiService.getReservation();
    }

    public void initList() {
        mReservations = mApiService.getReservation();
        if (mReservations != null) {
            state.postValue(new ListReservationUpdated(mReservations));
        }
        else{
            state.postValue(new ListReservationUpdated(new ArrayList<>()));
        }
    }

    public void deleteMeeting(Reservation reservation) {
        mApiService.deleteMeeting(reservation);
        state.postValue(new ListReservationUpdated(mReservations));
    }
}
