package com.example.mareu.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mareu.R;



public class AddReservationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reservation);
    }
    public static void navigate(FragmentActivity activity) {
        Intent intent = new Intent(activity, AddReservationActivity.class);
        ActivityCompat.startActivity(activity, intent, null);
    }
}
