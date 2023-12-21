package com.ashysystem.mbhq.fragment.live_chat;

import android.content.res.ColorStateList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.model.livechat.Chat;
import com.ashysystem.mbhq.model.livechat.Tag;
import com.ashysystem.mbhq.util.Util;
import com.beloo.widget.chipslayoutmanager.ChipsLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class LiveChatAdapter extends RecyclerView.Adapter<LiveChatAdapter.ViewHolder> {

    public interface ItemInteractionListener {
        void onItemClicked(Chat data);
    }

    private ArrayList<Chat> data = new ArrayList<Chat>();
    private ItemInteractionListener listener = null;

    private String TAG = this.getClass().getSimpleName();

    public void setData(List<Chat> data, ItemInteractionListener listener) {
        setAdapter(listener);
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setData(List<Chat> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void setAdapter(ItemInteractionListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View viewBinding = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_live_chat_item, parent, false);

        return new ViewHolder(viewBinding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindItems(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgVideoPreview;
        private TextView txtSubHeader, txtPresenter;
        private RecyclerView chipGroupTagContainer;
        private ViewGroup clRoot;
        ColorStateList blueColorState, whiteColorState;

        public ViewHolder(@NonNull View itemView, ItemInteractionListener listener) {
            super(itemView);

            imgVideoPreview = itemView.findViewById(R.id.imgVideoPreview);
            txtSubHeader = itemView.findViewById(R.id.txtSubHeader);
            txtPresenter = itemView.findViewById(R.id.txtPresenter);
            chipGroupTagContainer = itemView.findViewById(R.id.chipGroupTagContainer);
            clRoot = itemView.findViewById(R.id.clRoot);

            blueColorState =
                    ContextCompat.getColorStateList(clRoot.getContext(), R.color.live_chat_blue2);
            whiteColorState =
                    ContextCompat.getColorStateList(clRoot.getContext(), R.color.white);

        }

        public void bindItems(Chat dataItem) {

            txtPresenter.setText(dataItem.getPresenterName());
            txtSubHeader.setText(dataItem.getEventName());
            Log.i(TAG, "url: "+ dataItem.getImageUrl());
            Util.downloadphotowithGlide(imgVideoPreview.getContext(), imgVideoPreview, dataItem.getImageUrl());

            clRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onItemClicked(dataItem);
                    }
                }
            });

            ChipsLayoutManager chipsLayoutManager = ChipsLayoutManager.newBuilder(chipGroupTagContainer.getContext())
                    .setScrollingEnabled(true)
                    .setMaxViewsInRow(4)
                    .setOrientation(ChipsLayoutManager.HORIZONTAL)
                    .setRowStrategy(ChipsLayoutManager.STRATEGY_DEFAULT)
                    .withLastRow(true)
                    .build();

            chipGroupTagContainer.setLayoutManager(chipsLayoutManager);

            LiveChatChipsAdapter liveChatChipsAdapter = new LiveChatChipsAdapter();

            chipGroupTagContainer.setAdapter(liveChatChipsAdapter);

            ArrayList<Tag> tagList = new ArrayList<Tag>();

            for (int index = 0; index < dataItem.getTags().size(); index++) {
                Tag tag = new Tag(dataItem.getTags().get(index), -1, -1, true);
                tagList.add(tag);
            }

            liveChatChipsAdapter.setData(tagList, null);


        }


    }
}
