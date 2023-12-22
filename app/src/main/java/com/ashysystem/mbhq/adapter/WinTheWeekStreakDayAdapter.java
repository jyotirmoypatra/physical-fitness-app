package com.ashysystem.mbhq.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.GetWinTheWeekStatsResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class WinTheWeekStreakDayAdapter extends RecyclerView.Adapter<WinTheWeekStreakDayAdapter.ViewHolder> {

    private List<GetWinTheWeekStatsResponse.Wins.DayStatus> mData;
    private WinTheWeekStreakAdapter.InteractionListener mListener;
    private Boolean isWeekly;

    enum BRACKET_POSITION {
        TOP,
        MIDDLE,
        BOTTOM,
        NO_BRACKET
    }

    public void setData(List<GetWinTheWeekStatsResponse.Wins.DayStatus> data) {
        this.mData = data;
    }

    public void setListener(WinTheWeekStreakAdapter.InteractionListener listener) {
        this.mListener = listener;
    }

    public void setWeekly(Boolean weekly) {
        isWeekly = weekly;
    }

    public WinTheWeekStreakDayAdapter(List<GetWinTheWeekStatsResponse.Wins.DayStatus> data, Boolean isWeekly, WinTheWeekStreakAdapter.InteractionListener listener) {
        setData(data);
        setListener(listener);
        setWeekly(isWeekly);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_win_the_streak_single_item, parent, false);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BRACKET_POSITION currentBracketPosition;

        if(getItemCount() == 1){
            currentBracketPosition = BRACKET_POSITION.NO_BRACKET;
        }else if (position == 0) {
            currentBracketPosition = BRACKET_POSITION.TOP;
        } else if ((getItemCount() - 1) == position) {
            currentBracketPosition = BRACKET_POSITION.BOTTOM;
        } else {
            currentBracketPosition = BRACKET_POSITION.MIDDLE;
        }

        holder.bindItems(mData.get(position), mListener, isWeekly, currentBracketPosition);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView txtStreakDate;

        Drawable topBracket, midBracket, bottomBracket;

        ImageView imgBracket;

        private SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        private SimpleDateFormat shownDateFormat = new SimpleDateFormat("MMM dd", Locale.ENGLISH);

        private SimpleDateFormat dayOnlyDateFormat = new SimpleDateFormat("dd", Locale.ENGLISH);

        public ViewHolder(View itemView) {
            super(itemView);
            txtStreakDate = itemView.findViewById(R.id.txtStreakDate);
            imgBracket = itemView.findViewById(R.id.imgBracket);

            Context ctx = itemView.getRootView().getContext();

            topBracket = ContextCompat.getDrawable(ctx, R.drawable.thired_first_half_blue);
            midBracket = ContextCompat.getDrawable(ctx, R.drawable.thired_mid_half_blue);
            bottomBracket = ContextCompat.getDrawable(ctx, R.drawable.thired_last_half_blue);
        }

        public void bindItems(GetWinTheWeekStatsResponse.Wins.DayStatus data, WinTheWeekStreakAdapter.InteractionListener listener, boolean isWeekly, BRACKET_POSITION currentBracketPosition) {
            try {
                if (isWeekly) {

                    Date weekStartDate = apiDateFormat.parse(data.getWeekDate());

                    Calendar c = Calendar.getInstance();
                    c.setTime(weekStartDate);
                    c.add(Calendar.DATE, 6);

                    Date weekEndDate = c.getTime();

                    txtStreakDate.setText(
                            shownDateFormat.format(
                                    weekStartDate
                            ) + "-" + dayOnlyDateFormat.format(weekEndDate)

                    );


                } else {
                    txtStreakDate.setText(
                            shownDateFormat.format(
                                    apiDateFormat.parse(data.getWeekDate())
                            )
                    );
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            txtStreakDate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(data, isWeekly);
                }
            });

            switch (currentBracketPosition) {
                case TOP:
                    imgBracket.setImageDrawable(topBracket);
                    imgBracket.setVisibility(View.VISIBLE);
                    break;

                case BOTTOM:
                    imgBracket.setImageDrawable(bottomBracket);
                    imgBracket.setVisibility(View.VISIBLE);
                    break;

                case MIDDLE:
                    imgBracket.setImageDrawable(midBracket);
                    imgBracket.setVisibility(View.VISIBLE);
                    break;

                case NO_BRACKET:
                    imgBracket.setImageDrawable(topBracket);
                    imgBracket.setVisibility(View.INVISIBLE);
                    break;

                default:
                    imgBracket.setImageDrawable(null);
                    imgBracket.setVisibility(View.VISIBLE);
            }


        }

    }
}
