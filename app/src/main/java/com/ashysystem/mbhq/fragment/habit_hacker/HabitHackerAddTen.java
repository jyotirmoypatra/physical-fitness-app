package com.ashysystem.mbhq.fragment.habit_hacker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.util.AlarmReceiver;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SetLocalNotificationForHabit;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HabitHackerAddTen extends Fragment {

    EditText edtHorrible;
    ImageView imgBack;
    RelativeLayout rlCreateHabitNow;
    HabitSwap globalHabitSwapModel;
    Toolbar toolbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habithackeradd_ten,null);

        funToolBar();

        imgBack = view.findViewById(R.id.imgBack);
        edtHorrible = view.findViewById(R.id.edtHorrible);
        rlCreateHabitNow = view.findViewById(R.id.rlCreateHabitNow);

        /*if (getArguments() != null && getArguments().containsKey("HABIT_HACKER_ADD_MODEL")) {
            Gson gson = new Gson();
            globalHabitSwapModel = gson.fromJson(getArguments().getString("HABIT_HACKER_ADD_MODEL"), HabitSwap.class);
        }*/

        globalHabitSwapModel = ((HabitHackerAddMainFragment) getParentFragment()).getGlobalHabitSwapModel();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogWithCustomButton customButton = new AlertDialogWithCustomButton(getActivity());
                customButton.ShowDialog("Alert!","Are you sure you want to exit?",true,"SAVE MY HABIT NOW","YES");
                customButton.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {
                        saveNewBreakHabit();
                    }

                    @Override
                    public void onCancel(String title) {
                        if(Util.HABIT_ADDED_FROM_TODAY_PAGE){
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }else {
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        }
                    }
                });
            }
        });
        Util.edittext9=edtHorrible.getText().toString();
        edtHorrible.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                String s=arg0.toString();
                Util.edittext9=s;
                globalHabitSwapModel.setNoteHorrible(s);
            }
        });
        rlCreateHabitNow.setOnClickListener(view1 -> {
            saveNewBreakHabit();
        });

        return view;
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
                    if(Util.HABIT_ADDED_FROM_TODAY_PAGE){
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                    }else {
                        ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                    }

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

    private void saveNewBreakHabit() {
        // Log.e("name",globalHabitSwapModel.getSwapAction().getName());
        // Log.e("name",Util.edittext);

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            globalHabitSwapModel.getSwapAction().setName(Util.edittext);

            globalHabitSwapModel.setActionWhere(Util.edittext1);
            globalHabitSwapModel.setCue(Util.edittext2);
            globalHabitSwapModel.setRoutine(Util.edittext3);
            globalHabitSwapModel.setMoreSeen(Util.edittext4);
            globalHabitSwapModel.setReward(Util.edittext5);
            globalHabitSwapModel.setAccountabilityNotes(Util.edittext6);
            globalHabitSwapModel.setOldActionBreak(Util.edittext7);
            globalHabitSwapModel.setNoteHidden(Util.edittext8);
            globalHabitSwapModel.getNewAction().setPushNotification(Util.pushnotification);

            if(!edtHorrible.getText().toString().equals(""))
            {
                globalHabitSwapModel.setNoteHorrible(edtHorrible.getText().toString());
            }

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Habit",globalHabitSwapModel);

            Call<JsonObject> jsonObjectCall = finisherService.addUpdateHabitSwap(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    if(Util.pushnotification)
                    {

                        Util.pushnotification=false;
                        /*commented by sahenita temporary*/
                        SetLocalNotificationForHabit.setNotificationForHabit(globalHabitSwapModel,getActivity());

                        //  SetLocalNotificationForHabit.setAlarm(getActivity());

                    }else {
                        Util.pushnotification=false;
                        /*commented by sahenita temporary*/
                        try {
                            AlarmManager am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                            Intent intent = new Intent(getContext(), AlarmReceiver.class);
                            intent.setAction(globalHabitSwapModel.getNewAction().getId()+"HABIT");
                            PendingIntent sender = PendingIntent.getBroadcast(getActivity(),0, intent, PendingIntent.FLAG_NO_CREATE);
                            am.cancel(sender);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }


                    Util.edittext="";

                    Util.edittext1="";
                   // Util.pushnotification=false;
                    Util.edittext2="";
                    Util.edittext3="";
                    Util.edittext4="";
                    Util.edittext5="";
                    Util.edittext6="";
                    Util.edittext7="";
                    Util.edittext8="";
                    Util.edittext9="";

                    progressDialog.dismiss();
                    if(response.body()!=null && response.body().get("SuccessFlag").getAsBoolean())
                    {
                        Util.edittext="";

                        Util.edittext1="";
                       // Util.pushnotification=false;
                        Util.edittext2="";
                        Util.edittext3="";
                        Util.edittext4="";
                        Util.edittext5="";
                        Util.edittext6="";
                        Util.edittext7="";
                        Util.edittext8="";
                        Util.edittext9="";

                        Util.isReloadTodayMainPage = true;
                        Util.showToast(getActivity(),"Successfully Saved");
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        if(Util.HABIT_ADDED_FROM_TODAY_PAGE){
                            sharedPreference.clear("my_list");
                            Log.e("today","yes");
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }else {
                            Log.e("today","no");
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Util.edittext="";

                    Util.edittext1="";
                    Util.pushnotification=false;
                    Util.edittext2="";
                    Util.edittext3="";
                    Util.edittext4="";
                    Util.edittext5="";
                    Util.edittext6="";
                    Util.edittext7="";
                    Util.edittext8="";
                    Util.edittext9="";
                    progressDialog.dismiss();
                }
            });

        }

    }

}
