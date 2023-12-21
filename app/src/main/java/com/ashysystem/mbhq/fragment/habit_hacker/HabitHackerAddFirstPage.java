package com.ashysystem.mbhq.fragment.habit_hacker;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;



import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.dialog.CustomReminderDialogAddGratitude;
import com.ashysystem.mbhq.fragment.MbhqTodayMainFragment;
import com.ashysystem.mbhq.fragment.MbhqTodayTwoFragment;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.model.habit_hacker.NewAction;
import com.ashysystem.mbhq.model.habit_hacker.SwapAction;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitHackerAddFirstPage extends Fragment {

    ImageView imgBack;
    RelativeLayout rlNext;
    EditText edtNewHabit;
    TextView txtHowOftenTime;
    JSONObject[] rootJsonInner = {null};
    Toolbar toolbar;
    Button btnPopularHabits;
    HabitSwap globalHabitSwapModel; //////////////////////////////////////////////


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_hacker_add_first_page, null);

        funToolBar();

        imgBack = view.findViewById(R.id.imgBack);
        rlNext = view.findViewById(R.id.rlNext);
        edtNewHabit = view.findViewById(R.id.edtNewHabit);
        Log.i("pop","1");
        edtNewHabit.setText(Util.frompopularhabitlist);
        txtHowOftenTime = view.findViewById(R.id.txtHowOftenTime);
        btnPopularHabits = view.findViewById(R.id.btnPopularHabits);

        initJson();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.HABIT_ADDED_FROM_TODAY_PAGE){
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                }else {
                    ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                }

            }
        });

        btnPopularHabits.setOnClickListener(view1 -> {
            Util.frompopularhabitlist="";
            ((MainActivity)getActivity()).loadFragment(new PopularHabitsListFragment(),"PopularHabitsList",null);
        });

        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtNewHabit.getText().toString().equals("")) {
                    Util.showToast(getActivity(), "Please enter Habit Name");
                } else if (txtHowOftenTime.getText().toString().equals("")) {
                    Util.showToast(getActivity(), "Please enter Habit Frequency");
                } else {
                    makeHabitModelForAdd();
                }
            }
        });

        txtHowOftenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if("".equalsIgnoreCase(edtNewHabit.getText().toString())){
                    AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("MBHQ", "Please enter a habit name", false);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {

                        }

                        @Override
                        public void onCancel(String title) {

                        }
                    });
                }else{
                    CustomReminderDialogAddGratitude customReminderDialog = new CustomReminderDialogAddGratitude();
                    Bundle bundle = new Bundle();
                    bundle.putString("HABIT_HACKER_TASK", "TRUE");
                    bundle.putString("HABIT_NAME", edtNewHabit.getText().toString());
                    customReminderDialog.setArguments(bundle);
                    customReminderDialog.mListener = new CustomReminderDialogAddGratitude.onSubmitListener() {
                        @Override
                        public void setOnSubmitListener(JSONObject arg) {
                            Log.e("HABITHACKER", arg.toString() + "?");
                            rootJsonInner[0] = arg;
                            if (rootJsonInner[0] != null && rootJsonInner[0].has("FrequencyId")) {
                                try {
                                    if (rootJsonInner[0].getInt("FrequencyId") == 1) {
                                        txtHowOftenTime.setText("Daily");
                                    } else if (rootJsonInner[0].getInt("FrequencyId") == 2) {
                                        txtHowOftenTime.setText("Twice Daily");
                                    } else if (rootJsonInner[0].getInt("FrequencyId") == 3) {
                                        txtHowOftenTime.setText("Weekly");
                                    } else if (rootJsonInner[0].getInt("FrequencyId") == 4) {
                                        txtHowOftenTime.setText("Fortnightly");
                                    } else if (rootJsonInner[0].getInt("FrequencyId") == 5) {
                                        txtHowOftenTime.setText("Monthly");
                                    } else if (rootJsonInner[0].getInt("FrequencyId") == 6) {
                                        txtHowOftenTime.setText("Yearly");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    };
                    customReminderDialog.show(getFragmentManager(), "");
                }

            }
        });


        return view;
    }

    private void initJson() {
        try {
            rootJsonInner[0] = new JSONObject();
            rootJsonInner[0].put("FrequencyId",1);
            rootJsonInner[0].put("MonthReminder",0);
            rootJsonInner[0].put("ReminderOption",1);
            rootJsonInner[0].put("ReminderAt1","12");
            rootJsonInner[0].put("ReminderAt2",0);
            rootJsonInner[0].put("Email",false);
            rootJsonInner[0].put("PushNotification",true);
            rootJsonInner[0].put("Sunday",false);
            rootJsonInner[0].put("Monday",false);
            rootJsonInner[0].put("Tuesday",false);
            rootJsonInner[0].put("Wednesday",false);
            rootJsonInner[0].put("Thursday",false);
            rootJsonInner[0].put("Friday",false);
            rootJsonInner[0].put("Saturday",false);
            rootJsonInner[0].put("January",false);
            rootJsonInner[0].put("February",false);
            rootJsonInner[0].put("March",false);
            rootJsonInner[0].put("April",false);
            rootJsonInner[0].put("May",false);
            rootJsonInner[0].put("June",false);
            rootJsonInner[0].put("July",false);
            rootJsonInner[0].put("August",false);
            rootJsonInner[0].put("September",false);
            rootJsonInner[0].put("October",false);
            rootJsonInner[0].put("November",false);
            rootJsonInner[0].put("December",false);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void makeHabitModelForAdd() {

        HabitSwap habitSwap = new HabitSwap();

        habitSwap.setHabitName(edtNewHabit.getText().toString());
        habitSwap.setCue("");
        habitSwap.setReward("");
        habitSwap.setNoteHidden("");
        habitSwap.setUserId(Integer.parseInt(new SharedPreference(getActivity()).read("UserID", "")));
        habitSwap.setAccountabilityNotes("");
        habitSwap.setMoreSeen("");
        habitSwap.setNoteHorrible("");
        habitSwap.setOldActionBreak("");
        habitSwap.setActionWhere("");

        NewAction newAction = new NewAction();
        SwapAction swapAction = new SwapAction();

        try {
            newAction.setFrequencyId(1);
            newAction.setTaskFrequencyTypeId(rootJsonInner[0].getInt("FrequencyId"));
            newAction.setTuesday(false);
            newAction.setMonday(false);
            newAction.setNovember(false);
            newAction.setDecember(false);
            newAction.setMonthReminder(1);
            newAction.setReminderOption(1);
            newAction.setReminderAt1(43200);
            newAction.setSunday(false);
            newAction.setEmail(false);
            newAction.setName("");
            newAction.setOctober(false);
            newAction.setMarch(false);
            newAction.setWednesday(false);
            newAction.setDueDate("");
            newAction.setSaturday(false);
            newAction.setAugust(false);
            newAction.setPushNotification(false);
            newAction.setSeptember(false);
            newAction.setReminderTillDate("");
            newAction.setJuly(false);
            newAction.setMay(false);
            newAction.setThursday(false);
            newAction.setApril(false);
            newAction.setFebruary(false);
            newAction.setFriday(false);
            newAction.setJanuary(false);
            newAction.setJune(false);
            newAction.setReminderAt2(43200);

            swapAction.setFrequencyId(1);
            swapAction.setTaskFrequencyTypeId(rootJsonInner[0].getInt("FrequencyId"));
            swapAction.setTuesday(false);
            swapAction.setMonday(false);
            swapAction.setNovember(false);
            swapAction.setDecember(false);
            swapAction.setMonthReminder(1);
            swapAction.setReminderOption(1);
            swapAction.setReminderAt1(43200);
            swapAction.setSunday(false);
            swapAction.setEmail(false);
            swapAction.setName("Not completed");
            swapAction.setOctober(false);
            swapAction.setMarch(false);
            swapAction.setWednesday(false);
            swapAction.setDueDate("");
            swapAction.setSaturday(false);
            swapAction.setAugust(false);
            swapAction.setPushNotification(false);
            swapAction.setSeptember(false);
            swapAction.setReminderTillDate("");
            swapAction.setJuly(false);
            swapAction.setMay(false);
            swapAction.setThursday(false);
            swapAction.setApril(false);
            swapAction.setFebruary(false);
            swapAction.setFriday(false);
            swapAction.setJanuary(false);
            swapAction.setJune(false);
            swapAction.setReminderAt2(43200);

            if (rootJsonInner[0].getInt("FrequencyId") == 1 || rootJsonInner[0].getInt("FrequencyId") == 2)//Daily && //twice Daily
            {
                newAction.setIsFebruaryTask(false);
                newAction.setIsMayTask(false);
                newAction.setIsSeptemberTask(false);
                newAction.setIsDecemberTask(false);
                newAction.setTaskReminderAt2(12);
                newAction.setTaskMonthReminder(1);
                newAction.setIsSundayTask(false);
                newAction.setIsJulyTask(false);
                newAction.setIsAprilTask(false);
                newAction.setIsWednesdayTask(false);
                newAction.setIsSaturdayTask(false);
                newAction.setTaskReminderAt1(12);
                newAction.setIsAugustTask(false);
                newAction.setIsJuneTask(false);
                newAction.setIsNovemberTask(false);
                newAction.setIsOctoberTask(false);
                newAction.setIsMarchTask(false);
                newAction.setIsJanuaryTask(false);
                newAction.setIsMondayTask(false);
                newAction.setIsThursdayTask(false);
                newAction.setIsTuesdayTask(false);
                newAction.setIsFridayTask(false);

                swapAction.setName("Not completed");
                swapAction.setIsFebruaryTask(false);
                swapAction.setIsMayTask(false);
                swapAction.setIsSeptemberTask(false);
                swapAction.setIsDecemberTask(false);
                swapAction.setTaskReminderAt2(12);
                swapAction.setTaskMonthReminder(1);
                swapAction.setIsSundayTask(false);
                swapAction.setIsJulyTask(false);
                swapAction.setIsAprilTask(false);
                swapAction.setIsWednesdayTask(false);
                swapAction.setIsSaturdayTask(false);
                swapAction.setTaskReminderAt1(12);
                swapAction.setIsAugustTask(false);
                swapAction.setIsJuneTask(false);
                swapAction.setIsNovemberTask(false);
                swapAction.setIsOctoberTask(false);
                swapAction.setIsMarchTask(false);
                swapAction.setIsJanuaryTask(false);
                swapAction.setIsMondayTask(false);
                swapAction.setIsThursdayTask(false);
                swapAction.setIsTuesdayTask(false);
                swapAction.setIsFridayTask(false);

            } else if (rootJsonInner[0].getInt("FrequencyId") == 3 || rootJsonInner[0].getInt("FrequencyId") == 4)//weekly && //fortnightly
            {
                newAction.setIsFebruaryTask(false);
                newAction.setIsMayTask(false);
                newAction.setIsSeptemberTask(false);
                newAction.setIsDecemberTask(false);
                newAction.setTaskReminderAt2(12);
                newAction.setTaskMonthReminder(1);

                swapAction.setName("Not completed");
                swapAction.setIsFebruaryTask(false);
                swapAction.setIsMayTask(false);
                swapAction.setIsSeptemberTask(false);
                swapAction.setIsDecemberTask(false);
                swapAction.setTaskReminderAt2(12);
                swapAction.setTaskMonthReminder(1);

                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Sunday")) {
                    newAction.setIsSundayTask(true);
                    swapAction.setIsSundayTask(true);
                } else {
                    newAction.setIsSundayTask(false);
                    swapAction.setIsSundayTask(false);
                }
                newAction.setIsJulyTask(false);
                newAction.setIsAprilTask(false);

                swapAction.setIsJulyTask(false);
                swapAction.setIsAprilTask(false);

                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Wednesday")) {
                    newAction.setIsWednesdayTask(true);
                    swapAction.setIsWednesdayTask(true);
                } else {
                    newAction.setIsWednesdayTask(false);
                    swapAction.setIsWednesdayTask(false);
                }
                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Saturday")) {
                    newAction.setIsSaturdayTask(true);
                    swapAction.setIsSaturdayTask(true);
                } else {
                    newAction.setIsSaturdayTask(false);
                    swapAction.setIsSaturdayTask(false);
                }
                newAction.setTaskReminderAt1(12);
                newAction.setIsAugustTask(false);
                newAction.setIsJuneTask(false);
                newAction.setIsNovemberTask(false);
                newAction.setIsOctoberTask(false);
                newAction.setIsMarchTask(false);
                newAction.setIsJanuaryTask(false);
                swapAction.setName("Not completed");
                swapAction.setTaskReminderAt1(12);
                swapAction.setIsAugustTask(false);
                swapAction.setIsJuneTask(false);
                swapAction.setIsNovemberTask(false);
                swapAction.setIsOctoberTask(false);
                swapAction.setIsMarchTask(false);
                swapAction.setIsJanuaryTask(false);

                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Monday")) {
                    newAction.setIsMondayTask(true);
                    swapAction.setIsMondayTask(true);
                } else {
                    newAction.setIsMondayTask(false);
                    swapAction.setIsMondayTask(false);
                }
                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Thursday")) {
                    newAction.setIsThursdayTask(true);
                    swapAction.setIsThursdayTask(true);
                } else {
                    newAction.setIsThursdayTask(false);
                    swapAction.setIsThursdayTask(false);
                }
                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Tuesday")) {
                    newAction.setIsTuesdayTask(true);
                    swapAction.setIsTuesdayTask(true);
                } else {
                    newAction.setIsTuesdayTask(false);
                    swapAction.setIsTuesdayTask(false);
                }
                if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("Friday")) {
                    newAction.setIsFridayTask(true);
                    swapAction.setIsFridayTask(true);
                } else {
                    newAction.setIsFridayTask(false);
                    swapAction.setIsFridayTask(false);
                }
            } else if (rootJsonInner[0].getInt("FrequencyId") == 5)//monthly
            {
                newAction.setIsFebruaryTask(false);
                newAction.setIsMayTask(false);
                newAction.setIsSeptemberTask(false);
                newAction.setIsDecemberTask(false);
                newAction.setTaskReminderAt2(12);
                swapAction.setName("Not completed");
                swapAction.setIsFebruaryTask(false);
                swapAction.setIsMayTask(false);
                swapAction.setIsSeptemberTask(false);
                swapAction.setIsDecemberTask(false);
                swapAction.setTaskReminderAt2(12);

                if (rootJsonInner[0].has("MonthReminder")) {
                    newAction.setTaskMonthReminder(rootJsonInner[0].getInt("MonthReminder"));
                    swapAction.setTaskMonthReminder(rootJsonInner[0].getInt("MonthReminder"));
                } else {
                    newAction.setTaskMonthReminder(1);
                    swapAction.setTaskMonthReminder(1);
                }
                newAction.setIsSundayTask(false);
                newAction.setIsJulyTask(false);
                newAction.setIsAprilTask(false);
                newAction.setIsWednesdayTask(false);
                newAction.setIsSaturdayTask(false);
                newAction.setTaskReminderAt1(12);
                newAction.setIsAugustTask(false);
                newAction.setIsJuneTask(false);
                newAction.setIsNovemberTask(false);
                newAction.setIsOctoberTask(false);
                newAction.setIsMarchTask(false);
                newAction.setIsJanuaryTask(false);
                newAction.setIsMondayTask(false);
                newAction.setIsThursdayTask(false);
                newAction.setIsTuesdayTask(false);
                newAction.setIsFridayTask(false);
                swapAction.setName("Not completed");
                swapAction.setIsSundayTask(false);
                swapAction.setIsJulyTask(false);
                swapAction.setIsAprilTask(false);
                swapAction.setIsWednesdayTask(false);
                swapAction.setIsSaturdayTask(false);
                swapAction.setTaskReminderAt1(12);
                swapAction.setIsAugustTask(false);
                swapAction.setIsJuneTask(false);
                swapAction.setIsNovemberTask(false);
                swapAction.setIsOctoberTask(false);
                swapAction.setIsMarchTask(false);
                swapAction.setIsJanuaryTask(false);
                swapAction.setIsMondayTask(false);
                swapAction.setIsThursdayTask(false);
                swapAction.setIsTuesdayTask(false);
                swapAction.setIsFridayTask(false);

            } else if (rootJsonInner[0].getInt("FrequencyId") == 6)//yearly
            {
                if (rootJsonInner[0].getBoolean("February")) {
                    newAction.setIsFebruaryTask(true);
                    swapAction.setIsFebruaryTask(true);
                } else {
                    newAction.setIsFebruaryTask(false);
                    swapAction.setIsFebruaryTask(false);
                }
                if (rootJsonInner[0].getBoolean("May")) {
                    newAction.setIsMayTask(true);
                    swapAction.setIsMayTask(true);
                } else {
                    newAction.setIsMayTask(false);
                    swapAction.setIsMayTask(false);
                }
                if (rootJsonInner[0].getBoolean("September")) {
                    newAction.setIsSeptemberTask(true);
                    swapAction.setIsSeptemberTask(true);
                } else {
                    newAction.setIsSeptemberTask(false);
                    swapAction.setIsSeptemberTask(false);
                }
                if (rootJsonInner[0].getBoolean("December")) {
                    newAction.setIsDecemberTask(true);
                    swapAction.setIsDecemberTask(true);
                } else {
                    newAction.setIsDecemberTask(false);
                    swapAction.setIsDecemberTask(false);
                }
                newAction.setTaskReminderAt2(12);
                newAction.setTaskMonthReminder(1);
                newAction.setIsSundayTask(false);
                newAction.setIsJulyTask(rootJsonInner[0].getBoolean("July"));
                newAction.setIsAprilTask(rootJsonInner[0].getBoolean("April"));
                newAction.setIsWednesdayTask(false);
                newAction.setIsSaturdayTask(false);
                newAction.setTaskReminderAt1(12);
                newAction.setIsAugustTask(rootJsonInner[0].getBoolean("August"));
                newAction.setIsJuneTask(rootJsonInner[0].getBoolean("June"));
                newAction.setIsNovemberTask(rootJsonInner[0].getBoolean("November"));
                newAction.setIsOctoberTask(rootJsonInner[0].getBoolean("October"));
                newAction.setIsMarchTask(rootJsonInner[0].getBoolean("March"));
                newAction.setIsJanuaryTask(rootJsonInner[0].getBoolean("January"));
                newAction.setIsMondayTask(false);
                newAction.setIsThursdayTask(false);
                newAction.setIsTuesdayTask(false);
                newAction.setIsFridayTask(false);
                swapAction.setName("Not completed");
                swapAction.setTaskReminderAt2(12);
                swapAction.setTaskMonthReminder(1);
                swapAction.setIsSundayTask(false);
                swapAction.setIsJulyTask(rootJsonInner[0].getBoolean("July"));
                swapAction.setIsAprilTask(rootJsonInner[0].getBoolean("April"));
                swapAction.setIsWednesdayTask(false);
                swapAction.setIsSaturdayTask(false);
                swapAction.setTaskReminderAt1(12);
                swapAction.setIsAugustTask(rootJsonInner[0].getBoolean("August"));
                swapAction.setIsJuneTask(rootJsonInner[0].getBoolean("June"));
                swapAction.setIsNovemberTask(rootJsonInner[0].getBoolean("November"));
                swapAction.setIsOctoberTask(rootJsonInner[0].getBoolean("October"));
                swapAction.setIsMarchTask(rootJsonInner[0].getBoolean("March"));
                swapAction.setIsJanuaryTask(rootJsonInner[0].getBoolean("January"));
                swapAction.setIsMondayTask(false);
                swapAction.setIsThursdayTask(false);
                swapAction.setIsTuesdayTask(false);
                swapAction.setIsFridayTask(false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        habitSwap.setNewAction(newAction);

        habitSwap.setSwapAction(swapAction);

        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        globalHabitSwapModel = gson.fromJson(gson.toJson(habitSwap), HabitSwap.class);//////////////////////////////////
        openDialogForQuidAddOrFullSetUp(gson.toJson(globalHabitSwapModel));////////////////////////
    }


    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        //((MainActivity) getActivity()).funTabBarforHabits();
        //((MainActivity) getActivity()).funDrawer();

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
    }

    @Override
    public void onPause() {
        super.onPause();
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
    }

    private void funToolBar() {
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

        /*imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).showPromotionalDialogs();

                if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {
                    ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                } else {
                    ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);
                }
            }
        });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null && getArguments().containsKey("NEWHOME")) {
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", getArguments());
                } else
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            }
        });
    }
    private void openDialogForQuidAddOrFullSetUp(String s) {

        final boolean[] boolQuickAdd = {true};

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_quickadd_fullsetup);

        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlQuickAdd = dialog.findViewById(R.id.rlQuickAdd);
        RelativeLayout rlFullSetUp = dialog.findViewById(R.id.rlFullSetUp);
        RelativeLayout rlTop = dialog.findViewById(R.id.rlTop);
        RelativeLayout rlBottom = dialog.findViewById(R.id.rlBottom);
        ImageView imgQuickAdd = dialog.findViewById(R.id.imgQuickAdd);
        ImageView imgFullSetUp = dialog.findViewById(R.id.imgFullSetUp);
        RelativeLayout rlCreateNowContinue = dialog.findViewById(R.id.rlCreateNowContinue);
        TextView txtCreateNowContinue = dialog.findViewById(R.id.txtCreateNowContinue);

        imgCross.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlQuickAdd.setOnClickListener(view -> {
            boolQuickAdd[0] = true;
            imgQuickAdd.setImageResource(R.drawable.mbhq_rd_chk);
            imgFullSetUp.setImageResource(R.drawable.mbhq_rd_uchk);
            txtCreateNowContinue.setText("Create Now");
        });

        rlFullSetUp.setOnClickListener(view -> {
            boolQuickAdd[0] = false;
            imgQuickAdd.setImageResource(R.drawable.mbhq_rd_uchk);
            imgFullSetUp.setImageResource(R.drawable.mbhq_rd_chk);
            txtCreateNowContinue.setText("Continue");
        });

        rlCreateNowContinue.setOnClickListener(view -> {
            try{
                dialog.dismiss();
                if (boolQuickAdd[0]) {
                    saveNewBreakHabit();
                } else {
                    Gson gson = new Gson();
                    Bundle bundle = new Bundle();


                    // globalHabitSwapModel.getSwapAction().setName(edtHabitBreak.getText().toString());////////////////
                    bundle.putString("HABIT_HACKER_ADD_MODEL", gson.toJson(globalHabitSwapModel));
//                ((MainActivity)getActivity()).loadFragment(new HabitHackerAddOne(),"HabitHackerAddOne",bundle);
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddMainFragment());

                    ((MainActivity) getActivity()).loadFragment(new HabitHackerAddMainFragment(), "HabitHackerAddMainFragment", bundle);/////////////////////////////
                    // ((MainActivity) getActivity()).loadFragment(new HabitHackerAddSecondPage(), "HabitHackerAddSecondPage", bundle);///////////////////////////
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        });

        rlTop.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlBottom.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }
    private void saveNewBreakHabit() {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Util.edittext="";
            Util.pushnotification=false;
            Util.edittext1="";
            Util.edittext2="";
            Util.edittext3="";
            Util.edittext4="";
            Util.edittext5="";
            Util.edittext6="";
            Util.edittext7="";
            Util.edittext8="";
            Util.edittext9="";
            globalHabitSwapModel.getSwapAction().setName("Not Completed");

            //  globalHabitSwapModel.getSwapAction().setName(edtHabitBreak.getText().toString());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Habit", globalHabitSwapModel);

            Call<JsonObject> jsonObjectCall = finisherService.addUpdateHabitSwap(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        Util.showToast(getActivity(), "Successfully Saved");
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        if (Util.HABIT_ADDED_FROM_TODAY_PAGE) {
                            sharedPreference.clear("my_list");
                            Log.e("today","yes");
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        } else {
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }

    }
}
