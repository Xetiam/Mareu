package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;
import com.example.mareu.ui.adapter.ReservationRecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Objects;
import java.util.zip.Inflater;

import butterknife.BindView;
import butterknife.OnClick;

public class ListReservationActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private ReservationApiService mApiService = DI.getReservationApiService();
    private ArrayList<Reservation> mReservations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);
        mRecyclerView = findViewById(R.id.container);
        //mRecyclerView.setAdapter(new ReservationRecyclerViewAdapter(mApiService.getReservation()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(this),DividerItemDecoration.VERTICAL));
        initList();

        FloatingActionButton addNeighbour = findViewById(R.id.add_reservation);
        addNeighbour.setOnClickListener(v -> {
            AddReservationActivity.navigate(this);
        });

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

}
