package com.ashysystem.mbhq.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.AvailableCourseModel;

import java.util.List;

public class OnlyProgramPurchasedAdapter extends RecyclerView.Adapter<OnlyProgramPurchasedAdapter.ViewHolder> {

    List<AvailableCourseModel.Course> lstData;

    public OnlyProgramPurchasedAdapter(List<AvailableCourseModel.Course> lstData) {
        this.lstData = lstData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_adapter_restriction_item, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.programName.setText(lstData.get(position).getCourseName());
    }

    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView programName;

        public ViewHolder(View itemView) {
            super(itemView);
            this.programName = (TextView) itemView.findViewById(R.id.programName);
        }
    }
}
