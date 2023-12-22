package com.ashysystem.mbhq.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.GetWinTheWeekStatsResponse;

import java.util.List;


public class WinTheWeekStreakAdapter extends RecyclerView.Adapter<WinTheWeekStreakAdapter.ViewHolder> {

    public interface InteractionListener {
        void onClick(GetWinTheWeekStatsResponse.Wins.DayStatus data, boolean isWeekly);
    }

    private List<GetWinTheWeekStatsResponse.Wins> mData;
    private InteractionListener mListener;
    private Boolean isWeekly;

    public void setData(List<GetWinTheWeekStatsResponse.Wins> mData) {
        this.mData = mData;
    }

    public void setListener(InteractionListener mListener) {
        this.mListener = mListener;
    }

    public void setWeekly(Boolean weekly) {
        isWeekly = weekly;
    }

    public WinTheWeekStreakAdapter(List<GetWinTheWeekStatsResponse.Wins> data, Boolean isWeekly, InteractionListener listener) {
        setData(data);
        setListener(listener);
        setWeekly(isWeekly);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_win_the_week_streak_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItems(mData.get(position), mListener, isWeekly);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtStreakCount;
        private RecyclerView rvStreaks;

        public ViewHolder(View itemView) {
            super(itemView);
            txtStreakCount = itemView.findViewById(R.id.txtStreakCount);
            rvStreaks = itemView.findViewById(R.id.rvStreaks);
            rvStreaks.setLayoutManager(new LinearLayoutManager(itemView.getRootView().getContext()));
        }

        public void bindItems(GetWinTheWeekStatsResponse.Wins data, InteractionListener listener, boolean isWeekly) {

            if(data.getTotal().equals(1)){
                txtStreakCount.setVisibility(View.INVISIBLE);
            }else {
                txtStreakCount.setVisibility(View.VISIBLE);
            }
            txtStreakCount.setText(data.getTotal().toString());
            rvStreaks.setAdapter(new WinTheWeekStreakDayAdapter(data.getBrackets(), isWeekly, listener));
        }

    }
}
