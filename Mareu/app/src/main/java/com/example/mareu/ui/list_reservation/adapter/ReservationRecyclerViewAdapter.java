package com.example.mareu.ui.list_reservation.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.model.Reservation;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservationRecyclerViewAdapter extends RecyclerView.Adapter<ReservationRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mReservations;

    public ReservationRecyclerViewAdapter(List<Reservation> items) {
        mReservations = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_reservation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Reservation reservation = mReservations.get(position);
        holder.mReservationName.setText(reservation.getName());
        String participantsString = reservation.getParticipants().toString();
        holder.mParticipants.setText(participantsString);
        holder.mDeleteButton.setOnClickListener(v -> {
            EventBus.getDefault().post(new DeleteReservationEvent(reservation));
        });
        holder.itemView.setOnClickListener(v -> {
            //Intent intentViewReservation = new Intent();
            //intentViewReservation.putExtra("reservation", reservation1);
            //holder.mReservationColor.getContext().startActivity(intentViewReservation);
        });
    }


    @Override
    public int getItemCount() {
        return mReservations.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.participantsList)
        public TextView mParticipants;
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
