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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
//    private List<MyItem> itemList;
    Context context;
    MyAchievementsFragment.OnItemClickListener onItemClickListener;
    ArrayList<UserEqFolder> myData;
    public MyAdapter(Context context /*List<MyItem> itemList*/, MyAchievementsFragment.OnItemClickListener onItemClickListener, ArrayList<UserEqFolder> myData) {
        this.context=context;
        this.onItemClickListener=onItemClickListener;
        this.myData=myData;
//        this.itemList = itemList;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       // MyItem item = itemList.get(position);
        holder.textView.setText(myData.get(position).getFolderName());
      /*  if(myData.get(position).isDefault()){
            myData.get(position).setChecked(true);
        }else{
            myData.get(position).setChecked(false);
        }*/

        if (myData.get(position).getIsDefaultView()){

            holder.imageView.setBackgroundResource(R.drawable.mbhq_ic_tick);

        }else{
            holder.imageView.setBackgroundResource(R.drawable.mbhq_ic_untick);

        }

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               if(myData.get(position).getIsDefaultView()){
                   myData.get(position).setIsDefaultView(false);
                   onItemClickListener.onItemClick_remove(position,myData.get(position).getUserEqFolderId(),myData.get(position).getFolderName(),myData.get(position).getIsDefaultView());
                  // notifyItemChanged(position);
               }else{
                   myData.get(position).setIsDefaultView(true);
                   onItemClickListener.onItemClick_add(position,myData.get(position).getUserEqFolderId(),myData.get(position).getFolderName(),myData.get(position).getIsDefaultView());
                 //  notifyItemChanged(position);
               }
                //MyAdapter.this.notifyDataSetChanged(position);


            }
        });
    }

    @Override
    public int getItemCount() {
        return myData.size();
//        return 3;

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

    // Define the OnItemClickListener interface within the adapter

}