package com.example.mareu.ui.list_reservation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;

import java.util.ArrayList;

public class ListReservationViewModel extends ViewModel {
    private ReservationApiService mApiService;
    private ArrayList<Reservation> mReservations;
    MutableLiveData<ListReservationState> state = new MutableLiveData<>();
    public static final int SORT_MODE_DATE = 0;
    public static final int SORT_MODE_ROOM = 1;
    public static final int SORT_MODE_CREATION = 2;

    public ListReservationViewModel(ReservationApiService mApiService) {
        this.mApiService = mApiService;
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

    public void sortingList(ListReservationCallback callback) {
        ArrayList<Reservation> reservations = mReservations;
        for (int i = 0; i < reservations.size() - 1; i++)
        {
            int index = i;
            for (int j = i + 1; j < reservations.size(); j++)
            {
                if (callback.sortCondition(mReservations,index,j)){
                    index = j;
                }
            }
            Reservation min = reservations.get(index);
            reservations.set(index,reservations.get(i));
            reservations.set(i, min);
        }
        state.postValue(new ListReservationUpdated(reservations));
    }

    public void sortingCall(int sortMode) {
        switch(sortMode){
            case SORT_MODE_DATE:
                sortingList((reservations, index, j) -> reservations.get(j).getMeetingCalendar().before(reservations.get(index).getMeetingCalendar()));
                break;
            case SORT_MODE_ROOM:
                sortingList((reservations, index, j) -> reservations.get(j).getRoomId() < reservations.get(index).getRoomId());
                break;
            case SORT_MODE_CREATION:
                sortingList((reservations, index, j) -> reservations.get(j).getCreationCalendar().before(reservations.get(index).getCreationCalendar()));
                break;
        }
    }
}
