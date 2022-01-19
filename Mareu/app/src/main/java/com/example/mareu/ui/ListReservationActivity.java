package com.example.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.mareu.R;

import butterknife.OnClick;

public class ListReservationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservation);
    }
    @OnClick(R.id.add_reservation)
    void addReservation() {
        AddReservationActivity.navigate(this);
    }
}
