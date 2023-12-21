package com.ashysystem.mbhq.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.GuidedMeditationModel;

import java.util.ArrayList;

public class GuidedMeditationAdapter extends RecyclerView.Adapter<GuidedMeditationAdapter.ViewHolder> {

    public interface OnItemClickListener{
        void onItemClick(GuidedMeditationModel data);
    }

    ArrayList<GuidedMeditationModel> data = new ArrayList<>();
    OnItemClickListener listener = null;


    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData(ArrayList<GuidedMeditationModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GuidedMeditationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_guided_meditation_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GuidedMeditationAdapter.ViewHolder holder, int position) {
        holder.bindItem(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup clRoot;
        private ImageView imgTickGuided;
        private TextView txtMedName;

        public ViewHolder(View itemView) {
            super(itemView);

            clRoot = itemView.findViewById(R.id.clRoot);
            imgTickGuided = itemView.findViewById(R.id.imgTickGuided);
            txtMedName = itemView.findViewById(R.id.txtMedName);

        }

        public void bindItem(GuidedMeditationModel dataItem, int position) {

            String heading = dataItem.getWebinar().getEventName();

            heading += "\n(" + dataItem.getWebinar().getPresenterName();

            if(dataItem.getWebinar().getDuration() != 0){
                heading +=  ", " + dataItem.getWebinar().getDuration() + "min";
            }

            heading += ")";

            txtMedName.setText(heading);

            if(dataItem.isSelected()){
                imgTickGuided.setImageResource(R.drawable.mbhq_green_check);

            }else {
                imgTickGuided.setImageResource(R.drawable.mbhq_circle_green);
            }

            clRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dataItem.setSelected(!dataItem.isSelected());

                    if(listener != null){
                        listener.onItemClick(dataItem);
                    }

                    notifyItemChanged(position);
                }
            });

        }

    }
}
