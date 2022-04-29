package com.example.mareu.ui.list_reservation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;

import java.util.ArrayList;
import java.util.Calendar;

public class ListReservationViewModel extends ViewModel {
    private final ReservationApiService mApiService;
    private ArrayList<Reservation> mReservations;
    final MutableLiveData<ListReservationState> _state = new MutableLiveData<>();
    final LiveData<ListReservationState> state = _state;
    public static final int FILTER_MODE_DATE = 0;
    public static final int FILTER_MODE_ROOM = 1;
    public static final int FILTER_MODE_CREATION = 2;

    public ListReservationViewModel(ReservationApiService mApiService) {
        this.mApiService = mApiService;
        this.mReservations = mApiService.getReservation();
    }

    public void initList() {
        mReservations = mApiService.getReservation();
        if (mReservations != null) {
            _state.postValue(new ListReservationUpdated(mReservations));
        } else {
            _state.postValue(new ListReservationUpdated(new ArrayList<>()));
        }
    }

    public void deleteMeeting(Reservation reservation) {
        mApiService.deleteMeeting(reservation);
        _state.postValue(new ListReservationUpdated(mReservations));
    }

    public void filteringList(ListReservationCallback callback) {
        ArrayList<Reservation> reservations = mReservations;
        ArrayList<Reservation> newReservation = new ArrayList<>();
        for(int i = 0 ; i <= reservations.size()-1 ; i++){
            if(callback.filterCondition(reservations,i)){
                newReservation.add(reservations.get(i));
            }
        }

        _state.postValue(new ListReservationUpdated(newReservation));
    }

    public void filteringCall(int filterMode, String filterValue) {
        switch (filterMode) {
            case FILTER_MODE_DATE:
                filteringList((reservations,i) -> String.valueOf(reservations.get(i).getMeetingCalendar().get(Calendar.YEAR)
                        + reservations.get(i).getMeetingCalendar().get(Calendar.MONTH)
                        + reservations.get(i).getMeetingCalendar().get(Calendar.DAY_OF_MONTH)).equals( filterValue));
                break;
            case FILTER_MODE_ROOM:
                filteringList((reservations,i) -> String.valueOf(reservations.get(i).getRoomId()) == filterValue);
                break;
            case FILTER_MODE_CREATION:
                filteringList((reservations,i) -> true);
                break;
        }
    }
}
