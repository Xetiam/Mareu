package com.example.mareu.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.model.MeetingRoom;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


public class AddReservationActivity extends AppCompatActivity {
    private ReservationApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        MaterialButton addButton = findViewById(R.id.create);
        addButton.setEnabled(true);
        TextInputLayout nameInput = findViewById(R.id.nameLyt);
        TextInputLayout participantsInput = findViewById(R.id.participantsLyt);
        Spinner roomList = findViewById(R.id.roomList);
        mApiService = DI.getReservationApiService();
        init(roomList);

        addButton.setOnClickListener(v -> {
            ArrayList<String> participants = formatParticipants(participantsInput);
            int roomId = roomList.getSelectedItemPosition();
            MeetingRoom meetingRoomSelected = mApiService.getMeetingRooms().get(roomId);
            if(meetingRoomSelected.getVacancy()){
                Reservation newReservation = new Reservation(meetingRoomSelected.getRoomId(),
                        new Date(),
                        participants,
                        Objects.requireNonNull(nameInput.getEditText()).getText().toString(),R.color.red);
                mApiService.createMeeting(newReservation);
            }
            finish();
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
        ArrayList<String> roomListAdapter = new ArrayList<>();
        String roomName = "";
        for(int i = 0 ; i < meetingRooms.size() ; i++){
            roomName = "Salle n°" + meetingRooms.get(i).getRoomId();
            roomListAdapter.add(roomName);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, roomListAdapter);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomList.setAdapter(adapter);
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, AddReservationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
