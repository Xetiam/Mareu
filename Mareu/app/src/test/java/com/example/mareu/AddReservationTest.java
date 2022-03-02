package com.example.mareu;

import com.example.mareu.di.DI;
import com.example.mareu.factory.ViewModelFactory;
import com.example.mareu.service.ReservationApiService;
import com.example.mareu.ui.add_reservation.AddReservationState;
import com.example.mareu.ui.add_reservation.AddReservationViewModel;

import org.junit.Before;
import org.junit.Test;

public class AddReservationTest {
    private ReservationApiService service;
    private AddReservationViewModel viewModel;

    @Before
    public void setup() {
        service = DI.getNewInstanceApiService();
        viewModel = ViewModelFactory.getInstance().obtainViewModel(AddReservationViewModel.class);
    }

    //private void renderTest(AddReservationState addReservationState) {
    //}

    @Test
    public void addReservationWithSuccess(){

    }

    @Test
    public void addParticipantWithSuccess(){

    }

    @Test
    public void deleteParticipantWithSuccess(){

    }

    @Test
    public void listenerWithValidName(){
        String validName ="12345";
        viewModel.initListener(validName,false);
    }
    @Test
    public void listenerWithInvalidName(){
        String invalidName ="1234";
        viewModel.initListener(invalidName,false);
    }
    @Test
    public void listenerWithValidMail(){
        String validMail = "test@test.test";
        viewModel.initListener(validMail,true);
    }
    @Test
    public void listenerWithInvalidMail(){
        String invalidMail = "test@test";
        viewModel.initListener(invalidMail,true);
    }

    @Test
    public void spinnerGetRoomNamesWithSuccess(){

    }

    @Test
    public void returnTrueIfReservationisValid(){

    }

    @Test
    public void returnFalseIfReservationisInvalid(){

    }
}
