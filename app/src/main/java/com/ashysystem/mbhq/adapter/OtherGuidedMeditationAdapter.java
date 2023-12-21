package com.ashysystem.mbhq.adapter;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.GuidedMeditationModel;

import java.util.ArrayList;

public class OtherGuidedMeditationAdapter extends RecyclerView.Adapter<OtherGuidedMeditationAdapter.ViewHolder>{

    public interface OnItemClickListener{
        void onItemClick(GuidedMeditationModel data);
        void onItemRemoved(GuidedMeditationModel data);
        void onItemAdded();
    }

    ArrayList<GuidedMeditationModel> data = new ArrayList<>();
    OnItemClickListener listener = null;

    private String TAG = OtherGuidedMeditationAdapter.class.getSimpleName();


    public void setListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public void setData(ArrayList<GuidedMeditationModel> data) {
        this.data = data;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_other_guided_meditation_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItem(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ViewGroup clRoot;
        private ImageView imgAddGuided, imgRemoveGuided;
        private TextView txtMedName;
        private CheckBox chkbox;

        public ViewHolder(View itemView) {
            super(itemView);

            clRoot = itemView.findViewById(R.id.clRoot);
            imgAddGuided = itemView.findViewById(R.id.imgAddGuided);
            imgRemoveGuided = itemView.findViewById(R.id.imgRemoveGuided);
            txtMedName = itemView.findViewById(R.id.txtMedName);
            chkbox = itemView.findViewById(R.id.chkbox);

        }

        public void bindItem(GuidedMeditationModel dataItem, int position) {

            txtMedName.setText(dataItem.getWebinar().getEventName());

            chkbox.setChecked(dataItem.isSelected());

            imgAddGuided.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onItemAdded();
                    }
                }
            });

            imgRemoveGuided.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        Log.i(TAG, "position: "+ position + " data size: "+ data.size());
                        data.remove(position);
                        listener.onItemRemoved(dataItem);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, data.size());
                        Log.i(TAG, "position: "+ position + " data size: "+ data.size());
                    }
                }
            });

            clRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    dataItem.setSelected(!dataItem.isSelected());

                    if(listener != null){
                        listener.onItemClick(dataItem);
                    }

                    //notifyDataSetChanged();
                    //notifyItemChanged(position);
                }
            });

        }

    }
}
