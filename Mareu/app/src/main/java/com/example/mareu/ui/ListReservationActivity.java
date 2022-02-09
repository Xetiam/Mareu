package com.example.mareu.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;
import com.example.mareu.ui.adapter.ReservationRecyclerViewAdapter;

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

    private ReservationApiService mApiService = DI.getReservationApiService();
    private ArrayList<Reservation> mReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this), DividerItemDecoration.VERTICAL));

    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
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

    private void initList() {
        mReservations = mApiService.getReservation();
        if (mReservations != null) {
            mRecyclerView.setAdapter(new ReservationRecyclerViewAdapter(mReservations));
        }
    }

    @Subscribe
    public void onDeleteNeighbour(DeleteReservationEvent event) {
        mApiService.deleteMeeting(event.reservation);
        initList();
    }

    @OnClick(R.id.add_reservation)
    void addNeighbour() {
        AddReservationActivity.navigate(this);
    }

}
