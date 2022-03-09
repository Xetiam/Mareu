

package com.example.mareu.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.mareu.service.DummyReservationApiService;
import com.example.mareu.service.ReservationApiService;
import com.example.mareu.ui.add_reservation.AddReservationViewModel;
import com.example.mareu.ui.list_reservation.ListReservationViewModel;
import com.example.mareu.ui.view_reservation_detail.ViewReservationViewModel;


public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactory viewModelFactory;
    private static ReservationApiService reservationApiService;

    private ViewModelFactory(DummyReservationApiService reservationApiService) {
        ViewModelFactory.reservationApiService = reservationApiService;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ViewReservationViewModel.class)) {
            return (T) new ViewReservationViewModel(reservationApiService);
        } else if (modelClass.isAssignableFrom(AddReservationViewModel.class)) {
            return (T) new AddReservationViewModel(reservationApiService);
        } else if (modelClass.isAssignableFrom(ListReservationViewModel.class)) {
            return (T) new ListReservationViewModel(reservationApiService);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }

    public static ViewModelFactory getInstance() {
        if (viewModelFactory == null) {
            synchronized (ViewModelFactory.class) {
                if (viewModelFactory == null) {
                    DummyReservationApiService dummyReservationApiService = new DummyReservationApiService();
                    viewModelFactory = new ViewModelFactory(dummyReservationApiService);
                }
            }
        }
        return viewModelFactory;
    }

    public <T extends ViewModel> T obtainViewModel(Class<T> modelClass) {
        return viewModelFactory.create(modelClass);

    }

}