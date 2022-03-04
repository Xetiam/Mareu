package com.example.mareu.ui.add_reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.VisibleForTesting;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.app.ActivityCompat;

import com.example.mareu.R;
import com.example.mareu.factory.ViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
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
    @BindView(R.id.listTitle)
    TextView listTitle;
    @BindView(R.id.participantsList)
    LinearLayout partList;
    @BindView(R.id.participantButton)
    ImageButton partButton;
    @BindView(R.id.warning)
    ImageView warning;
    @BindView(R.id.warningPart)
    ImageView warningPart;
    @BindView(R.id.warningName)
    ImageView warningName;

    private AddReservationViewModel viewModel;
    private ArrayList<TextView> listPartView = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
        ButterKnife.bind(this);
        viewModel = retrieveViewModel();
        viewModel.initSpinner();
        viewModel.state.observe(this, this::render);
        initListeners();
    }

    private void initListeners() {
        myListener(nameInput, false);
        myListener(participantsInput, true);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                viewModel.isReservationValid(formatDate(datePicker, timePicker), roomList.getSelectedItemPosition());
            }
        });
    }

    private void myListener(TextInputLayout input, Boolean mailFormat) {
        Objects.requireNonNull(input.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.initListener(s.toString(), mailFormat);
                viewModel.isReservationValid(formatDate(datePicker, timePicker), roomList.getSelectedItemPosition());
            }
        });
    }

    @VisibleForTesting
    public AddReservationViewModel retrieveViewModel() {
        return ViewModelFactory.getInstance().obtainViewModel(AddReservationViewModel.class);
    }

    private void render(AddReservationState addReservationState) {
        if (addReservationState instanceof AddReservationStateUpdated) {
            AddReservationStateUpdated state = (AddReservationStateUpdated) addReservationState;
            if (state.getNameValid() && state.getValid()) {
                addButton.setEnabled(true);
                warning.setVisibility(View.INVISIBLE);
            } else {
                addButton.setEnabled(false);
                warning.setVisibility(View.VISIBLE);
            }
            if (state.getMailValid()) {
                partButton.setEnabled(true);
                warningPart.setVisibility(View.INVISIBLE);
            } else {
                partButton.setEnabled(false);
                warningPart.setVisibility(View.VISIBLE);
            }
            if (state.getNameValid()) {
                nameInput.setBackgroundColor(getResources().getColor(R.color.green));
                warningName.setVisibility(View.INVISIBLE);
            } else {
                warningName.setVisibility(View.VISIBLE);
                nameInput.setBackgroundColor(getResources().getColor(R.color.red));
            }
        }
        if (addReservationState instanceof AddReservationStateInit) {
            AddReservationStateInit state = (AddReservationStateInit) addReservationState;
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state.getRoomNames());
            roomList.setAdapter(adapter);
        }
        if(addReservationState instanceof AddReservationStateAddPart){
            createPartView();
        }
        if(addReservationState instanceof AddReservationStateDeletePart){
            AddReservationStateDeletePart state = (AddReservationStateDeletePart) addReservationState;
            for (TextView view: listPartView
                 ) {
                if(view.getText() == state.getParticipant()){
                    partList.removeView((View) view.getParent());
                }
            }
        }
    }

    private void createPartView() {
        ConstraintLayout part = new ConstraintLayout(this);
        part.setLayoutParams(new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        TextView partName = createTextView();
        ImageButton deletePart = createButton(partName.getText().toString());
        ConstraintSet set = new ConstraintSet();
        part.addView(partName);
        part.addView(deletePart);
        set.clone(part);

        //Contrainte du mail du participant
        set.connect(partName.getId(),ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
        set.connect(partName.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
        set.connect(partName.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM);
        //Contrainte du bouton delete
        set.connect(deletePart.getId(),ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END);
        set.connect(deletePart.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
        set.connect(deletePart.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM);
        set.applyTo(part);

        partList.addView(part);
        partList.setVisibility(View.VISIBLE);
        listPartView.add(partName);
        participantsInput.getEditText().setText("");
        listTitle.setVisibility(View.VISIBLE);
        viewModel.isReservationValid(formatDate(datePicker, timePicker), roomList.getSelectedItemPosition());
    }


    private TextView createTextView() {
        TextView partName = new TextView(this);
        partName.setText(Objects.requireNonNull(participantsInput.getEditText()).getText().toString());
        partName.setTextSize(16F);
        partName.setPadding(32,0,0,8);
        partName.setId(View.generateViewId());
        return partName;
    }

    private ImageButton createButton(String participant) {
        ImageButton newButton = new ImageButton(this);
        newButton.setImageDrawable(getDrawable(R.drawable.ic_baseline_delete_24));
        newButton.setLayoutParams(new CardView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        newButton.setOnClickListener(v -> {
            viewModel.deleteParticipant(participant);
        });
        newButton.setId(View.generateViewId());
        return newButton;
    }

    private Calendar formatDate(DatePicker datePicker, TimePicker timePicker) {
        int year = datePicker.getYear();
        int month = datePicker.getMonth();
        int day = datePicker.getDayOfMonth();
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day, hour, minute);
        return calendar;
    }

    public static void navigate(Activity activity) {
        Intent intent = new Intent(activity, AddReservationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
    @OnClick(R.id.warning)
    void warningButton() {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle(R.string.alert_title);
        alert.setMessage(getString(R.string.alert_message));
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alert.show();
    }

    @OnClick(R.id.participantButton)
    void participantButton() {
        viewModel.addParticipant(Objects.requireNonNull(participantsInput.getEditText()).getText().toString());
    }

    @OnClick(R.id.create)
    void addButton() {
        int roomId = roomList.getSelectedItemPosition();
        Calendar datePicked = formatDate(datePicker, timePicker);
        String name = Objects.requireNonNull(nameInput.getEditText()).getText().toString();
        viewModel.addReservation(roomId, datePicked, name);
        finish();
    }
}