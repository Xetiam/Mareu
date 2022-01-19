package com.example.mareu.ui.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.model.Reservation;
import com.example.mareu.ui.ViewReservationDetailActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mreservation;

    public MyNeighbourRecyclerViewAdapter(List<Reservation> items) {
        mreservation = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_list_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Reservation reservation = mreservation.get(position);
        holder.mReservationName.setText(reservation.getName());
        holder.mReservationColor.setBackgroundColor(reservation.getColor());
        holder.mDeleteButton.setOnClickListener(v -> {
            EventBus.getDefault().post(new DeleteReservationEvent(reservation));
        });
        holder.itemView.setOnClickListener(v -> {
            Reservation reservation1 = mreservation.get(position);
            Intent intentViewNeighbour = new Intent(holder.mReservationColor.getContext(), ViewReservationDetailActivity.class);
            intentViewNeighbour.putExtra("reservation", reservation1);
            holder.mReservationColor.getContext().startActivity(intentViewNeighbour);
        });
    }


    @Override
    public int getItemCount() {
        return mreservation.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.reservationColor)
        public ImageView mReservationColor;
        @BindView(R.id.reservationTitle)
        public TextView mReservationName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
