package com.example.mareu.ui.list_reservation;

import static com.example.mareu.ui.list_reservation.ListReservationViewModel.FILTER_MODE_CREATION;
import static com.example.mareu.ui.list_reservation.ListReservationViewModel.FILTER_MODE_DATE;
import static com.example.mareu.ui.list_reservation.ListReservationViewModel.FILTER_MODE_ROOM;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.factory.ViewModelFactory;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.DummyMeetingRoomGenerator;
import com.example.mareu.ui.add_reservation.AddReservationActivity;
import com.example.mareu.ui.list_reservation.adapter.ReservationRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListReservationActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener {
    @BindView(R.id.container)
    RecyclerView mRecyclerView;

    private ListReservationViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        viewModel = retrieveViewModel();
        viewModel._state.observe(this, this::render);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    private ListReservationViewModel retrieveViewModel() {
        return ViewModelFactory.getInstance().obtainViewModel(ListReservationViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.by_date:
                createCalendarDialog();
                return true;
            case R.id.by_room:
                createSpinnerDialog();
                return true;
            case R.id.by_creation:
                viewModel.filteringCall(FILTER_MODE_CREATION, "");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createSpinnerDialog() {
        ArrayList<String> roomList = initRoomList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, roomList);
        Spinner popupSpinner = new Spinner(this, Spinner.MODE_DIALOG);
        popupSpinner.setId(R.id.popup_Spinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        popupSpinner.setAdapter(adapter);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_room);
        builder.setCancelable(true);
        builder.setNegativeButton(getText(R.string.cancel_alert_button), null);
        builder.setPositiveButton(getText(R.string.validate_alert_button), (dialog, which) ->
                viewModel.filteringCall(FILTER_MODE_ROOM, String.valueOf(popupSpinner.getSelectedItemPosition())));
        builder.setView(popupSpinner);
        builder.create().show();
    }

    private void createCalendarDialog() {
        Calendar today = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                ListReservationActivity.this,
                today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setButton(DatePickerDialog.BUTTON_POSITIVE, getText(R.string.validate_alert_button), datePickerDialog);
        datePickerDialog.setButton(DatePickerDialog.BUTTON_NEGATIVE, getText(R.string.cancel_alert_button), datePickerDialog);
        datePickerDialog.getDatePicker().setId(R.id.popup_Date);
        datePickerDialog.show();
    }

    private ArrayList<String> initRoomList() {
        ArrayList<String> roomList = new ArrayList<>();
        for (int i = 0; i <= 9; i++) {
            roomList.add(DummyMeetingRoomGenerator.MeetingRoomName.getName(i));
        }
        return roomList;
    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.initList();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void render(ListReservationState listReservationState) {
        if (listReservationState instanceof ListReservationUpdated) {
            ListReservationUpdated state = (ListReservationUpdated) listReservationState;
            ArrayList<Reservation> reservations = state.reservations;
            ReservationRecyclerViewAdapter adapter = new ReservationRecyclerViewAdapter(reservations, this);
            mRecyclerView.setAdapter(adapter);
        }
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteReservationEvent event) {
        viewModel.deleteMeeting(event.reservation);
    }

    @OnClick(R.id.add_reservation)
    void addNeighbour() {
        AddReservationActivity.navigateToAddReservation(this);
    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        viewModel.filteringCall(FILTER_MODE_DATE, String.valueOf(i + i1 + i2));
        datePicker.removeAllViews();
    }
}
