package com.example.mareu.ui.list_reservation.adapter;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareu.R;
import com.example.mareu.di.DI;
import com.example.mareu.event.DeleteReservationEvent;
import com.example.mareu.model.Reservation;
import com.example.mareu.service.ReservationApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReservationRecyclerViewAdapter extends RecyclerView.Adapter<ReservationRecyclerViewAdapter.ViewHolder> {

    private final List<Reservation> mReservations;
    private final Context mContext;
    private final ReservationApiService mAPiService;

    public ReservationRecyclerViewAdapter(List<Reservation> items, Context context) {
        mReservations = items;
        mContext = context;
        mAPiService = DI.getReservationApiService();
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
        holder.mReservationName.setText(String.format(mContext.getString(R.string.card_view_format),
                reservation.getName(),
                reservation.getMeetingDateCorrectlyFormatted(),
                mAPiService.getMeetingRoomName(reservation.getRoomId())));
        String participantsString = reservation.getParticipants().toString().substring(1,reservation.getParticipants().toString().length()-1);
        holder.mReservationColor.setColorFilter(reservation.getColor(), PorterDuff.Mode.MULTIPLY);
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
        @BindView(R.id.reservationColor)
        public ImageView mReservationColor;
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
