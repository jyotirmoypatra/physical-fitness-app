package com.ashysystem.mbhq.adapter.mbhq_today;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class HabitHistoryAdapter extends RecyclerView.Adapter<HabitHistoryAdapter.ViewHolder> {

    private Date todayDate;

    private Integer numberOfRequiredPastDates;

    private OnHabitHistoryDateClickListener listener;

    public interface OnHabitHistoryDateClickListener {
        void onDateClicked(Date date);
    }


    public HabitHistoryAdapter(Date todayDate, Integer requiredPastDates, OnHabitHistoryDateClickListener listener) {
        this.todayDate = todayDate;
        this.numberOfRequiredPastDates = requiredPastDates;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_habit_history, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItems(todayDate, position, listener);
    }

    @Override
    public int getItemCount() {
        return numberOfRequiredPastDates;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Button habitHistoryBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            habitHistoryBtn = itemView.findViewById(R.id.habitHistoryBtn);
        }

        public void bindItems(Date todayDate, int position, OnHabitHistoryDateClickListener listener) {

            Calendar c = Calendar.getInstance();
            c.setTime(todayDate);
            c.add(Calendar.DATE, (-position));

            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM dd", Locale.ENGLISH);

            switch (position) {
                case 0:
                    habitHistoryBtn.setText("Today");
                    break;
                case 1:
                    habitHistoryBtn.setText("Yesterday");
                    break;
                default:
                    habitHistoryBtn.setText(
                            dateFormat.format(c.getTime())
                    );
            }

            habitHistoryBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDateClicked(c.getTime());
                }
            });

        }

    }

}
