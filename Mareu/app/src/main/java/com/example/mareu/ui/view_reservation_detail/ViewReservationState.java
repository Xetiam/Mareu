package com.example.mareu.ui.view_reservation_detail;

public class ViewReservationState {}
class ViewReservationStateListUpdated extends ViewReservationState {
    String formatedListPart = "";
    Boolean isMyMail;

    public ViewReservationStateListUpdated(String formatedListPart, Boolean isMyMail){
        this.formatedListPart = formatedListPart;
        this.isMyMail = isMyMail;
    }

    public String getFormatedListPart() {
        return formatedListPart;
    }
}