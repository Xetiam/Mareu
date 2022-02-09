package com.example.mareu.ui.add_reservation;

import static java.sql.DriverManager.println;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.RoomCheckAvailableEvent;
import com.example.mareu.factory.ViewModelFactory;
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
    @BindView(R.id.participantButton)
    ImageButton partButton;
    @BindView(R.id.warningPart)
    ImageView warningPart;

    private AddReservationViewModel viewModel;
    private ArrayList<String> participants = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        ButterKnife.bind(this);
        viewModel = retrieveViewModel();
        viewModel.state.observe(this, this::render);
        viewModel.initSpinner();
        initListeners();
    }

    private void initListeners() {
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Date datePicked = formatDate(datePicker, timePicker);
                viewModel.isReservationValid(datePicked, roomList.getSelectedItemPosition(), participants);
            }
        });
        myListener(nameInput,false);
        myListener(participantsInput,true);
    }

    private void myListener(TextInputLayout input, Boolean mailFormat) {
        Objects.requireNonNull(input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                viewModel.initListener(s.toString(),mailFormat);
            }
        });
    }

    private AddReservationViewModel retrieveViewModel() {
        return ViewModelFactory.getInstance().obtainViewModel(AddReservationViewModel.class);
    }

    private void render(AddReservationState addReservationState){
        if(addReservationState.getValid()){
            addButton.setEnabled(true);
            warning.setVisibility(View.INVISIBLE);
        }
        else{
            addButton.setEnabled(false);
            warning.setVisibility(View.VISIBLE);
            ArrayList<String> roomNames = addReservationState.getRoomNames();
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,roomNames);
            roomList.setAdapter(adapter);
        }
        if(addReservationState.getMailValid()){
            partButton.setEnabled(true);
            if(!(participantsInput.getEditText().toString().length()==0)){
                warningPart.setVisibility(View.INVISIBLE);
            }
        }
        else {
            partButton.setEnabled(false);
            warningPart.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.participantButton)
    void participantButton(){
        participants.add(Objects.requireNonNull(participantsInput.getEditText()).getText().toString());
        participantsInput.getEditText().setText("");
    }

    @OnClick(R.id.create)
    void addButton() {
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
