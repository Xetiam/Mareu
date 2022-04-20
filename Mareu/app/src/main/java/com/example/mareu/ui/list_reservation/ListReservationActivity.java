package com.example.mareu.ui.list_reservation;

import static com.example.mareu.ui.list_reservation.ListReservationViewModel.SORT_MODE_CREATION;
import static com.example.mareu.ui.list_reservation.ListReservationViewModel.SORT_MODE_DATE;
import static com.example.mareu.ui.list_reservation.ListReservationViewModel.SORT_MODE_ROOM;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.factory.ViewModelFactory;
import com.example.mareu.model.Reservation;
import com.example.mareu.ui.add_reservation.AddReservationActivity;
import com.example.mareu.ui.list_reservation.adapter.ReservationRecyclerViewAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ListReservationActivity extends AppCompatActivity {
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
        viewModel.state.observe(this, this::render);
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
                viewModel.sortingCall(SORT_MODE_DATE);
                return true;
            case R.id.by_room:
                viewModel.sortingCall(SORT_MODE_ROOM);
                return true;
            case R.id.by_creation:
                viewModel.sortingCall(SORT_MODE_CREATION);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    private void render(ListReservationState listReservationState){
        if(listReservationState instanceof ListReservationUpdated){
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


}
