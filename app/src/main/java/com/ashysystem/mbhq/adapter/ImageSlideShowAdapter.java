package com.ashysystem.mbhq.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ashysystem.mbhq.R;

import java.util.ArrayList;


public class ImageSlideShowAdapter extends PagerAdapter {

    @DrawableRes
    ArrayList<Integer> sliderImageIds;

    public ImageSlideShowAdapter(ArrayList<Integer> sliderImageIds) {

        this.sliderImageIds = sliderImageIds;

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.layout_community_help_slide, null, false);

        ImageView imgSlide = view.findViewById(R.id.imgSlide);

        imgSlide.setImageDrawable(container.getContext().getDrawable(sliderImageIds.get(position)));

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return sliderImageIds.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (View) object);
    }
}
