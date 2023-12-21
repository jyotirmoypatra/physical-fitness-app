package com.ashysystem.mbhq.fragment.live_chat;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.livechat.Tag;

import java.util.ArrayList;
import java.util.List;

public class LiveChatChipsAdapter extends RecyclerView.Adapter<LiveChatChipsAdapter.ViewHolder> {

    public interface ChipInteractionListener {
        void onChipClicked(Tag data, int position);
    }

    private ArrayList<Tag> data = new ArrayList<Tag>();
    private ChipInteractionListener listener = null;

    public void setData(List<Tag> data, ChipInteractionListener listener) {
        setAdapter(listener);
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<Tag> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setAdapter(ChipInteractionListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewBinding = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_live_chat_chip, parent, false);

        return new ViewHolder(viewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindItems(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ViewGroup chipContainer;
        private TextView txtTagName;

        public ViewHolder(View itemView) {
            super(itemView);
            chipContainer = itemView.findViewById(R.id.chipContainer);
            txtTagName = itemView.findViewById(R.id.txtTagName);
        }

        public void bindItems(Tag dataItem, int position) {

            txtTagName.setText(dataItem.getTagName());

            if (dataItem.isSelected()) {
                chipContainer.setBackground(chipContainer.getContext().getDrawable(R.drawable.capsule_border_white_blue_deselected));
//                chipContainer.setBackground(chipContainer.getContext().getDrawable(R.drawable.capsule_border_white_blue1));

            } else {
                chipContainer.setBackground(chipContainer.getContext().getDrawable(R.drawable.capsule_border_white_blue1));
            }

            chipContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onChipClicked(dataItem, position);
                    }
                }
            });

        }

    }

}
