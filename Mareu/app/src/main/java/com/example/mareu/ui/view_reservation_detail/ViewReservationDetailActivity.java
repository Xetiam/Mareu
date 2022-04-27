package com.example.mareu.ui.view_reservation_detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mareu.R;
import com.example.mareu.factory.ViewModelFactory;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.DummyMeetingRoomGenerator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewReservationDetailActivity extends AppCompatActivity {
    public static final String RESERVATION = "reservation";
    @BindView(R.id.detail_reservation_name)
    TextView reservationName;
    @BindView(R.id.detail_reservation_color)
    ImageView reservationColor;
    @BindView(R.id.participate_button)
    FloatingActionButton partButton;
    @BindView(R.id.detail_room_picked)
    TextView roopPicked;
    @BindView(R.id.detail_participants_list)
    TextView partList;
    @BindView(R.id.detail_date_picked)
    TextView datePicked;
    @BindView(R.id.detail_subject)
    TextView subject;

    private ViewReservationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reservation_detail);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Reservation mReservation = (Reservation) intent.getExtras().get(RESERVATION);
        reservationName.setText(mReservation.getNameDetail());
        reservationColor.setColorFilter(mReservation.getColor());
        roopPicked.setText(getDetailRoom(DummyMeetingRoomGenerator.MeetingRoomName.getName(mReservation.getRoomId())));
        partList.setText(mReservation.getParticipantsFormated());
        viewModel = retrieveViewModel();
        viewModel.initReservation(mReservation);
        viewModel.state.observe(this, this::render);
        datePicked.setText(mReservation.getMeetingCalendarFormated());
        subject.setText(mReservation.getSubject());
        viewModel.isMyMail(mReservation.getParticipants());
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private String getDetailRoom(String name) {
        return "Salle de réunion : " + name;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private void render(ViewReservationState viewReservationState) {
        if (viewReservationState instanceof ViewReservationStateListUpdated) {
            ViewReservationStateListUpdated state = (ViewReservationStateListUpdated) viewReservationState;
            partList.setText(state.getFormatedListPart());
            setDrawable(state.isMyMail());
        }
        if (viewReservationState instanceof ViewReservationStateInit) {
            ViewReservationStateInit state = (ViewReservationStateInit) viewReservationState;
            setDrawable(state.isMyMail());
        }
    }

    private void setDrawable(Boolean myMail) {
        if (myMail) {
            partButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_remove_24));
        } else {
            partButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_baseline_add_24));
        }
    }

    @VisibleForTesting
    private ViewReservationViewModel retrieveViewModel() {
        return ViewModelFactory.getInstance().obtainViewModel(ViewReservationViewModel.class);
    }

    @OnClick(R.id.participate_button)
    void setPartButton() {
        viewModel.addMeToMeeting();
    }

    public static void navigateToDetail(Activity activity, Reservation reservation) {
        Intent intent = new Intent(activity, ViewReservationDetailActivity.class);
        intent.putExtra(RESERVATION, reservation);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
