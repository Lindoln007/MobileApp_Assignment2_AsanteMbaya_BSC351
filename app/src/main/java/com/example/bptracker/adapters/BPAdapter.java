package com.example.bptracker.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bptracker.R;
import com.example.bptracker.database.BPReading;
import com.example.bptracker.utils.AlertUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BPAdapter extends RecyclerView.Adapter<BPAdapter.BPViewHolder> {
    private List<BPReading> bpReadings;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault());

    public BPAdapter(List<BPReading> bpReadings) {
        this.bpReadings = bpReadings;
    }

    @NonNull
    @Override
    public BPViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bp_reading, parent, false);
        return new BPViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BPViewHolder holder, int position) {
        BPReading reading = bpReadings.get(position);

        holder.tvSystolic.setText(String.valueOf(reading.getSystolic()));
        holder.tvDiastolic.setText(String.valueOf(reading.getDiastolic()));
        holder.tvPulse.setText(String.valueOf(reading.getPulse()));
        holder.tvDateTime.setText(dateFormat.format(new Date(reading.getDateTime())));

        if (reading.getNotes() != null && !reading.getNotes().isEmpty()) {
            holder.tvNotes.setText(reading.getNotes());
            holder.tvNotes.setVisibility(View.VISIBLE);
        } else {
            holder.tvNotes.setVisibility(View.GONE);
        }

        // Set background color based on BP category
        int bgColor = AlertUtils.getBPColor(reading.getSystolic(), reading.getDiastolic());
        holder.itemView.setBackgroundColor(bgColor);
    }

    @Override
    public int getItemCount() {
        return bpReadings.size();
    }

    public void setReadings(List<BPReading> readings) {
        this.bpReadings = readings;
        notifyDataSetChanged();
    }

    static class BPViewHolder extends RecyclerView.ViewHolder {
        TextView tvSystolic, tvDiastolic, tvPulse, tvDateTime, tvNotes;

        public BPViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSystolic = itemView.findViewById(R.id.tvSystolic);
            tvDiastolic = itemView.findViewById(R.id.tvDiastolic);
            tvPulse = itemView.findViewById(R.id.tvPulse);
            tvDateTime = itemView.findViewById(R.id.tvDateTime);
            tvNotes = itemView.findViewById(R.id.tvNotes);
        }
    }
}