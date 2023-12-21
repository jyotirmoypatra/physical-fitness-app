package com.ashysystem.mbhq.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitDetailsCalendarFragment;
import com.ashysystem.mbhq.model.habit_hacker.HabbitCalendarModel;

import java.util.List;

public class HabitQuaterAdapter extends RecyclerView.Adapter<HabitQuaterAdapter.MyViewHolder> {

    Context context;
    List<HabbitCalendarModel.HabitQuarterlyStat> lstHabitQuarter;
    HabbitDetailsCalendarFragment calendarFragment;

    public HabitQuaterAdapter(Context context, List<HabbitCalendarModel.HabitQuarterlyStat> lstHabitQuarter, HabbitDetailsCalendarFragment calendarFragment)
    {
        this.context = context;
        this.lstHabitQuarter = lstHabitQuarter;
        this.calendarFragment = calendarFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_habbit_quarter_year, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.txtQuarterYear.setText(calendarFragment.hashQuarterValue.get(lstHabitQuarter.get(position).getQuarter()) + " " + lstHabitQuarter.get(position).getYear());
        holder.txtPercentage.setText(lstHabitQuarter.get(position).getStatsQuarterlyPercentage() + "%");
    }


    @Override
    public int getItemCount() {
        return lstHabitQuarter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtQuarterYear,txtPercentage;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtQuarterYear = itemView.findViewById(R.id.txtQuarterYear);
            this.txtPercentage =  itemView.findViewById(R.id.txtPercentage);
        }
    }
}
