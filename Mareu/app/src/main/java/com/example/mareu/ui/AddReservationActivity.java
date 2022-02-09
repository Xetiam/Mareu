package com.example.mareu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.RoomCheckAvailableEvent;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import org.greenrobot.eventbus.EventBus;
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

    private ReservationApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        ButterKnife.bind(this);
        mApiService = DI.getReservationApiService();
        init(roomList);
        int roomId = roomList.getSelectedItemPosition();
        MeetingRoom meetingRoomSelected = mApiService.getMeetingRooms().get(roomId);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Date datePicked = formatDate(datePicker, timePicker);
                EventBus.getDefault().post(new RoomCheckAvailableEvent(datePicked, addButton, meetingRoomSelected));
            }
        });

    }

    private ArrayList<String> formatParticipants(TextInputLayout participantsInput) {
        String[] temp = Objects.requireNonNull(participantsInput.getEditText()).getText().toString().split(" ");
        ArrayList<String> participants = new ArrayList<>();
        participants.addAll(Arrays.asList(temp));
        return participants;
    }

    private void init(Spinner roomList) {
        ArrayList<MeetingRoom> meetingRooms = mApiService.getMeetingRooms();
        String roomName = "";
        ArrayList<String> roomNames = new ArrayList<>();
        for (MeetingRoom meetingRoom : meetingRooms
        ) {
            roomNames.add(meetingRoom.getNameSpinner(roomName));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roomNames);
        roomList.setAdapter(adapter);
    }

    @OnClick(R.id.create)
    void addButton() {
        ArrayList<String> participants = formatParticipants(participantsInput);
        int roomId = roomList.getSelectedItemPosition();
        MeetingRoom meetingRoomSelected = mApiService.getMeetingRooms().get(roomId);
        Date datePicked = formatDate(datePicker, timePicker);
        Reservation newReservation = null;
        newReservation = new Reservation(meetingRoomSelected.getRoomId(),
                datePicked,
                participants,
                Objects.requireNonNull(nameInput.getEditText()).getText().toString(), R.color.red);
        mApiService.createMeeting(newReservation);
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

    @Subscribe
    public void onRoomCheckAvailableEvent(RoomCheckAvailableEvent event) {
        //TODO: réaction à l'évènement
        //warning.setVisibility(View.VISIBLE);
    }
}
