package com.ashysystem.mbhq.fragment.habit_hacker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;


import android.graphics.Color;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.mbhq_today.TodayViewPagerAdapter;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;

import com.ashysystem.mbhq.util.CustomViewPager;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

public class HabitHackerAddMainFragment extends Fragment {

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    CustomViewPager viewPagerHabit;
    CirclePageIndicator indicator;
    Toolbar toolbar;
    HabitSwap globalHabitSwapModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_hacker_add_main, null);

        funToolBar();
        try{

            viewPagerHabit = view.findViewById(R.id.viewPagerHabit);
            indicator = view.findViewById(R.id.indicator);
            if (getArguments() != null && getArguments().containsKey("HABIT_HACKER_ADD_MODEL")) {
                Gson gson = new Gson();
                globalHabitSwapModel = gson.fromJson(getArguments().getString("HABIT_HACKER_ADD_MODEL"), HabitSwap.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        setupViewPager(viewPagerHabit);



        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        try{
            TodayViewPagerAdapter adapter = new TodayViewPagerAdapter(getChildFragmentManager());
            adapter.addFragment(new HabitHackerAddSecondPage(),"NEW_ONE");///////////
            adapter.addFragment(new HabitHackerAddOne(), "ONE");
            adapter.addFragment(new HabitHackerAddTwo(), "TWO");
            adapter.addFragment(new HabitHackerAddThree(), "THREE");
            adapter.addFragment(new HabitHackerAddFour(), "FOUR");
            adapter.addFragment(new HabitHackerAddFive(), "FIVE");
            adapter.addFragment(new HabitHackerAddSix(), "SIX");
            adapter.addFragment(new HabitHackerAddSeven(), "SEVEN");
            adapter.addFragment(new HabitHackerAddEight(), "EIGHT");
            adapter.addFragment(new HabitHackerAddNine(), "NINE");
            adapter.addFragment(new HabitHackerAddTen(), "TEN");
            viewPager.setAdapter(adapter);

            indicator.setViewPager(viewPager);
            viewPager.setOffscreenPageLimit(11);

            final float density = getResources().getDisplayMetrics().density;

            //Set circle indicator radius
            indicator.setRadius(3 * density);

            NUM_PAGES = 2;

            viewPager.setCurrentItem(currentPage++, true);


            // Pager listener over indicator
            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    currentPage = position;
                }

                @Override
                public void onPageScrolled(int pos, float arg1, int arg2) {

                }


                @Override
                public void onPageScrollStateChanged(int pos) {


                }
            });
            viewPagerHabit.setCurrentItem(0);
        }catch (Exception e){
            e.printStackTrace();
        }


    }


    @Override
    public void onResume() {
        super.onResume();
        try{
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.GONE);
            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                        // handle back button
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);

                        return true;

                    }

                    return false;
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.VISIBLE);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private void funToolBar() {
        try{
            toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            ImageView imgLogo = (ImageView) toolbar.findViewById(R.id.imgLogo);
            ImageView imgLeftBack = (ImageView) toolbar.findViewById(R.id.imgLeftBack);
            ImageView imgRightBack = (ImageView) toolbar.findViewById(R.id.imgRightBack);
            ImageView imgFilter = (ImageView) toolbar.findViewById(R.id.imgFilter);
            ImageView imgCircleBack = (ImageView) toolbar.findViewById(R.id.imgCircleBack);
            ImageView imgHelp = (ImageView) toolbar.findViewById(R.id.imgHelp);
            ImageView imgCalender = (ImageView) toolbar.findViewById(R.id.imgCalender);
            FrameLayout frameNotification = (FrameLayout) toolbar.findViewById(R.id.frameNotification);
            TextView txtPageHeader = (TextView) toolbar.findViewById(R.id.txtPageHeader);

            imgRightBack.setClickable(true);
            imgRightBack.setEnabled(true);
            frameNotification.setVisibility(View.GONE);
            imgFilter.setVisibility(View.GONE);
            //imgFilter.setBackgroundResource(R.drawable.filter);
            imgRightBack.setVisibility(View.GONE);
            txtPageHeader.setVisibility(View.GONE);
            imgLeftBack.setVisibility(View.GONE);
            if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                if (Util.isSevenDayOver(getActivity())) {
                    imgCircleBack.setVisibility(View.VISIBLE);
                } else {
                    imgCircleBack.setVisibility(View.VISIBLE);
                }
            } else {
                if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                    imgCircleBack.setVisibility(View.VISIBLE);
                } else {
                    imgCircleBack.setVisibility(View.VISIBLE);
                }
            }
            imgHelp.setVisibility(View.GONE);
            imgCalender.setVisibility(View.GONE);
            toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));


            imgRightBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getArguments() != null && getArguments().containsKey("NEWHOME")) {
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", getArguments());
                    } else
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                }
            });
        }catch(Exception e){
            e.printStackTrace();
        }

    }


    public void saveAndNext(HabitSwap globalHabitSwapModel) {
        this.globalHabitSwapModel = globalHabitSwapModel;
        viewPagerHabit.setCurrentItem(viewPagerHabit.getCurrentItem() + 1);
    }


    public HabitSwap getGlobalHabitSwapModel() {
        return globalHabitSwapModel;
    }


}