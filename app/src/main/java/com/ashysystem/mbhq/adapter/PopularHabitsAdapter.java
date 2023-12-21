package com.ashysystem.mbhq.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.fragment.habit_hacker.PopularHabitsListFragment;
import com.ashysystem.mbhq.model.habit_hacker.DemoHabitTemplateModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

public class PopularHabitsAdapter extends RecyclerView.Adapter<PopularHabitsAdapter.MyViewHolder>{

    Context context;
    ArrayList<DemoHabitTemplateModel> habitTemplates;
    PopularHabitsListFragment habitsListFragment;

    public PopularHabitsAdapter(Context context,ArrayList<DemoHabitTemplateModel> habitTemplates,PopularHabitsListFragment habitsListFragment)
    {
        this.context = context;
        this.habitTemplates = habitTemplates;
        this.habitsListFragment = habitsListFragment;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_popular_habits, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(habitTemplates.get(position).getShowHeader())
        {
            holder.rlHeader.setVisibility(View.VISIBLE);
        }else
        {
            holder.rlHeader.setVisibility(View.GONE);
        }

        if(!habitTemplates.get(position).getSection().equals(""))
        {
            holder.txtHeader.setText(habitTemplates.get(position).getSection());
        }else {
            holder.txtHeader.setText("");
        }

        Glide.with(context).load(habitTemplates.get(position).getHabitTemplate().getImageUrl())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.empty_image_old)
                .error(R.drawable.empty_image_old)
                .dontTransform()
                .dontAnimate()
                .into(holder.imgPopularHabit);

        holder.txtHabitName.setText(habitTemplates.get(position).getHabitTemplate().getHabitToCreate());

        holder.llMainContent.setOnClickListener(view -> {
            habitsListFragment.openDialogForPopularHabits(habitTemplates.get(position));
        });

    }

    @Override
    public int getItemCount() {
        return habitTemplates.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtHeader,txtHabitName;
        private ImageView imgPopularHabit;
        RelativeLayout rlHeader;
        LinearLayout llMainContent;

        public MyViewHolder(View itemView) {
            super(itemView);
            this.txtHeader = itemView.findViewById(R.id.txtHeader);
            this.txtHabitName =  itemView.findViewById(R.id.txtHabitName);
            this.imgPopularHabit =  itemView.findViewById(R.id.imgPopularHabit);
            this.rlHeader =  itemView.findViewById(R.id.rlHeader);
            this.llMainContent =  itemView.findViewById(R.id.llMainContent);
        }
    }

}
