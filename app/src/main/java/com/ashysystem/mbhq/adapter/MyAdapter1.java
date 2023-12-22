package com.ashysystem.mbhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.fragment.achievement.MyAchievementsFragment;
import com.ashysystem.mbhq.model.eqfolder.UserEqFolder;

import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.MyViewHolder> {
    Context context;
    ArrayList<UserEqFolder> myData;
    int selectedPosition = -1;
    MyAchievementsFragment.OnItemClickListener onItemClickListener;

    public MyAdapter1(Context context, MyAchievementsFragment.OnItemClickListener onItemClickListener, ArrayList<UserEqFolder> myData) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.myData = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText(myData.get(position).getFolderName());

       /* if (position == selectedPosition) {
            holder.imageView.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            holder.imageView.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }*/

        if (myData.get(position).getIsDefaultView()){

            holder.imageView.setBackgroundResource(R.drawable.mbhq_ic_tick);

        }else{

            holder.imageView.setBackgroundResource(R.drawable.mbhq_ic_untick);

        }

        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick_add(position, myData.get(position).getUserEqFolderId(), myData.get(position).getFolderName(), myData.get(position).getIsDefaultView());
            }
           /* if (selectedPosition != position) {
                if (selectedPosition != -1) {
                    myData.get(selectedPosition).setIsDefaultView(false);
                    notifyItemChanged(selectedPosition);
                }
                selectedPosition = position;
                myData.get(position).setIsDefaultView(true);
                notifyItemChanged(position);
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick_add(position, myData.get(position).getUserEqFolderId(), myData.get(position).getFolderName(), myData.get(position).getIsDefaultView());
                }
            }*/
        });
    }

    @Override
    public int getItemCount() {
        return myData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgF);
            textView = itemView.findViewById(R.id.textF);
        }
    }
}