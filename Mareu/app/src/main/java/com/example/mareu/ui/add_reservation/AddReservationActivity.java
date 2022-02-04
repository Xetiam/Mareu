package com.example.mareu.ui.add_reservation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.RoomCheckAvailableEvent;
import com.example.mareu.model.MeetingRoom;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AddReservationActivity extends AppCompatActivity {
    @BindView(R.id.nameLyt)
    TextInputLayout nameInput;
    @BindView(R.id.participantsLyt)
    TextInputLayout participantsInput;
    @BindView(R.id.roomList)
    Spinner roomList;
    @BindView(R.id.date_picker)
    DatePicker datePicker;
    @BindView(R.id.time_picker)
    TimePicker timePicker;
    @BindView(R.id.create)
    MaterialButton addButton;
    @BindView(R.id.warning)
    ImageView warning;

    private AddReservationViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        ButterKnife.bind(this);
        viewModel = new AddReservationViewModel(DI.getReservationApiService());
        viewModel.state.observe(this, this::render);
        ArrayList<String> roomNames = viewModel.initSpinner();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,roomNames);
        roomList.setAdapter(adapter);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Date datePicked = formatDate(datePicker, timePicker);
                viewModel.isReservationValid(datePicked, roomList.getSelectedItemPosition(), formatParticipants(participantsInput));
            }
        });

    }

    private void render(AddReservationState addReservationState){
        if(addReservationState instanceof AddReservationStateValidRerservation){
            addButton.setEnabled(true);
            warning.setVisibility(View.INVISIBLE);
        }
        else if(addReservationState instanceof AddReservationStateInvalidRerservation){
            addButton.setEnabled(false);
            warning.setVisibility(View.VISIBLE);
        }
    }

    private ArrayList<String> formatParticipants(TextInputLayout participantsInput) {
        String[] temp = Objects.requireNonNull(participantsInput.getEditText()).getText().toString().split(" ");
        ArrayList<String> participants = new ArrayList<>();
        participants.addAll(Arrays.asList(temp));
        return participants;
    }


    @OnClick(R.id.create)
    void addButton() {
        ArrayList<String> participants = formatParticipants(participantsInput);
        int roomId = roomList.getSelectedItemPosition();
        Date datePicked = formatDate(datePicker, timePicker);
        String name = Objects.requireNonNull(nameInput.getEditText()).getText().toString();
        viewModel.addReservation(roomId, datePicked, name, participants);
        finish();
    }

    private Date formatDate(DatePicker datePicker, TimePicker timePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        return new Date(year, month, day, hour, minute);
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, AddReservationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
