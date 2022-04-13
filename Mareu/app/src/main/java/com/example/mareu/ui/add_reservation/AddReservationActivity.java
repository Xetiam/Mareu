package com.example.mareu.ui.add_reservation;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddReservationActivity extends AppCompatActivity {
    @BindView(R.id.nameLyt)
    TextInputLayout nameInput;
    @BindView(R.id.participantsLyt)
    TextInputLayout participantsInput;
    @BindView(R.id.subjectLyt)
    TextInputLayout subjectInput;
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
        timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> viewModel.isReservationValid(formatDate(datePicker, timePicker), roomList.getSelectedItemPosition()));
        roomList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.isReservationValid(formatDate(datePicker, timePicker), roomList.getSelectedItemPosition());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
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

    /**RENDERING : START**/
    private void render(AddReservationState addReservationState) {
        if (addReservationState instanceof AddReservationStateUpdated) {
            renderUpdated(addReservationState);
        }
        if (addReservationState instanceof AddReservationStateInit) {
            renderInit(addReservationState);
        }
        if(addReservationState instanceof AddReservationStateAddPart){
            renderPartView();
        }
        if(addReservationState instanceof AddReservationStateDeletePart){
            renderDeletePart(addReservationState);
        }
    }

    private void renderDeletePart(AddReservationState addReservationState) {
        AddReservationStateDeletePart state = (AddReservationStateDeletePart) addReservationState;
        for (TextView view: listPartView
        ) {
            if(view.getText() == state.getParticipant()){
                partList.removeView((View) view.getParent());
            }
        }
    }

    private void renderInit(AddReservationState addReservationState) {
        AddReservationStateInit state = (AddReservationStateInit) addReservationState;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, state.getRoomNames());
        roomList.setAdapter(adapter);
        partButton.setEnabled(false);
    }

    private void renderUpdated(AddReservationState addReservationState) {
        AddReservationStateUpdated state = (AddReservationStateUpdated) addReservationState;
        if (state.getValid()) {
            addButton.setEnabled(true);
            warning.setVisibility(View.INVISIBLE);
        } else {
            addButton.setEnabled(false);
            warning.setVisibility(View.VISIBLE);
        }
        renderInputText(nameInput, warningName, state);
        renderInputText(participantsInput,warningPart, state);
    }

    private void renderInputText(TextInputLayout input, ImageView imageView, AddReservationStateUpdated stateUpdated) {
        String s = input.getEditText().getText().toString();
        if(viewModel.noInput(s)){
            input.setBackgroundColor(Color.TRANSPARENT);
            imageView.setVisibility(View.INVISIBLE);
        }
        else{
            switch (input.getId()){
                case R.id.nameLyt:
                    renderField(input, imageView, stateUpdated.getNameValid());
                    break;
                case R.id.participantsLyt:
                    renderField(input,imageView,stateUpdated.getMailValid());
                    partButton.setEnabled(Patterns.EMAIL_ADDRESS.matcher(s).matches());
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + input.getId());
            }

        }
    }

    private void renderField(TextInputLayout input, ImageView imageView, Boolean isFieldValid) {
        if(isFieldValid){
            input.setBackgroundColor(getResources().getColor(R.color.green_faded));
            imageView.setVisibility(View.INVISIBLE);
        }
        else{
            imageView.setVisibility(View.VISIBLE);
            input.setBackgroundColor(getResources().getColor(R.color.red_faded));
        }
    }

    private void renderPartView() {
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
        //Constraints participant mail
        set.connect(partName.getId(),ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START);
        set.connect(partName.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
        set.connect(partName.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM);
        //Constraints delete button
        set.connect(deletePart.getId(),ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END);
        set.connect(deletePart.getId(),ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP);
        set.connect(deletePart.getId(),ConstraintSet.BOTTOM,ConstraintSet.PARENT_ID,ConstraintSet.BOTTOM);
        set.applyTo(part);
        deletePart.setId(viewModel.newDeleteId());

        partList.addView(part);
        partList.setVisibility(View.VISIBLE);
        listPartView.add(partName);
        participantsInput.getEditText().setText("");
        listTitle.setVisibility(View.VISIBLE);
        viewModel.isReservationValid(formatDate(datePicker, timePicker), roomList.getSelectedItemPosition());
    }
    /**RENDERING : END**/

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

    public static void navigateToAddReservation(Activity activity) {
        Intent intent = new Intent(activity, AddReservationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
    @OnClick(R.id.warningName)
    void warningButtonName(){
        waningDisplay(R.string.alert_title_name, R.string.alert_message_name);
    }
    @OnClick(R.id.warningPart)
    void warningButtonParticipant(){
        waningDisplay(R.string.alert_title_participant, R.string.alert_message_participant);
    }
    @OnClick(R.id.warning)
    void warningButton() {
        waningDisplay(R.string.alert_title_create, R.string.alert_message_create);
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
        String subject = Objects.requireNonNull(subjectInput.getEditText()).getText().toString();
        viewModel.addReservation(roomId, datePicked, name, subject);
        finish();
    }
    private void waningDisplay(int alertTitleId, int alertMessagId) {
        AlertDialog alert = new AlertDialog.Builder(this).create();
        alert.setTitle(getString(alertTitleId));
        alert.setMessage(getString(alertMessagId));
        alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                (dialog, which) -> dialog.dismiss());
        alert.show();
    }
}