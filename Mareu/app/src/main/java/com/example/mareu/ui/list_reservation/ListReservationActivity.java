package com.example.mareu.ui.list_reservation;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.factory.ViewModelFactory;
import com.example.mareu.model.Reservation;
import com.example.mareu.ui.add_reservation.AddReservationViewModel;
import com.example.mareu.ui.list_reservation.adapter.ReservationRecyclerViewAdapter;
import com.example.mareu.ui.add_reservation.AddReservationActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;

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
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this), DividerItemDecoration.VERTICAL));
        viewModel = retrieveViewModel();
        viewModel.state.observe(this, this::render);
        viewModel.initList();
    }

    private ListReservationViewModel retrieveViewModel() {
        return ViewModelFactory.getInstance().obtainViewModel(ListReservationViewModel.class);
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
        AddReservationActivity.navigate(this);
    }


}
