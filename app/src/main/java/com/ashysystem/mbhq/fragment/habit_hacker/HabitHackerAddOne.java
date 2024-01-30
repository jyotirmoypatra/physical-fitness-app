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
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.dialog.CustomReminderDialogForEditHabit;
import com.ashysystem.mbhq.dialog.HabitHackerReminderAddDialog;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.MyTimePickerDialog;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.TimePickerControllerWithTextView;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabitHackerAddOne extends Fragment {

    ImageView imgBack;
    Toolbar toolbar;
    RelativeLayout rlNext;
    TextView txtNewHabitTime, txtHowLongTime, txtNewHabitTimeTwice;
    CheckBox chkSetReminder;
    LinearLayout llForTwice;
    HabitSwap globalHabitSwapModel;
    JSONObject jsonFromDialog;

    Integer timePickerHour = 0;
    Integer timePickerMinute = 5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habit_hacker_add_one, null);

        funToolBar();

        imgBack = view.findViewById(R.id.imgBack);
        rlNext = view.findViewById(R.id.rlNext);
        txtNewHabitTime = view.findViewById(R.id.txtNewHabitTime);
        txtHowLongTime = view.findViewById(R.id.txtHowLongTime);
        chkSetReminder = view.findViewById(R.id.chkSetReminder);
        llForTwice = view.findViewById(R.id.llForTwice);
        txtNewHabitTimeTwice = view.findViewById(R.id.txtNewHabitTimeTwice);

        /*if (getArguments() != null && getArguments().containsKey("HABIT_HACKER_ADD_MODEL")) {
            Gson gson = new Gson();
            globalHabitSwapModel = gson.fromJson(getArguments().getString("HABIT_HACKER_ADD_MODEL"), HabitSwap.class);
        }*/
        globalHabitSwapModel = ((HabitHackerAddMainFragment) getParentFragment()).getGlobalHabitSwapModel();

        if (globalHabitSwapModel != null && globalHabitSwapModel.getNewAction().getFrequencyId() == 2) {
            llForTwice.setVisibility(View.VISIBLE);
        }

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogWithCustomButton customButton = new AlertDialogWithCustomButton(getActivity());
                customButton.ShowDialog("Alert!", "Are you sure you want to exit?", true, "SAVE MY HABIT NOW", "YES");
                customButton.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {
                        setValuesForSave("BACK_CLICKED");
                    }

                    @Override
                    public void onCancel(String title) {
                        if (Util.HABIT_ADDED_FROM_TODAY_PAGE) {
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        } else {
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        }
                    }
                });
            }
        });

        txtNewHabitTime.setOnClickListener(view1 -> {
            openDialogForNOSETTIMEHABIT("FIRST");
        });

        txtNewHabitTimeTwice.setOnClickListener(view1 -> {
            openDialogForNOSETTIMEHABIT("SECOND");
        });

        txtHowLongTime.setOnClickListener(view1 -> {
            openDialogForNOSETTIMEHOWLONG();
        });

        chkSetReminder.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
               /* HabitHackerReminderAddDialog customReminderDialog = new HabitHackerReminderAddDialog();
                customReminderDialog.mListener = new HabitHackerReminderAddDialog.onSubmitListener() {
                    @Override
                    public void setOnSubmitListener(JSONObject arg) {
                        Log.e("HABIT_REMINDER", arg.toString() + "?");
                        jsonFromDialog = arg;
                        try {
                            Util.pushnotification=jsonFromDialog.getBoolean("PushNotification");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                customReminderDialog.show(getFragmentManager(), "");*/




                CustomReminderDialogForEditHabit customReminderDialog = new CustomReminderDialogForEditHabit();
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
               // bundle.putString("MODEL",gson.toJson(globalHabitSwap.getHabitSwap().getNewAction()));
               // bundle.putString("HABIT_HACKER_TASK", "TRUE");
                customReminderDialog.setArguments(bundle);
                customReminderDialog.mListener = new CustomReminderDialogForEditHabit.onSubmitListener() {
                    @Override
                    public void setOnSubmitListener(JSONObject arg) {

                        if(arg!=null)
                        {
                            try {
                                Log.e("HABITHACKER", arg.toString() + "?");
                                jsonFromDialog = arg;
                                Util.pushnotification=jsonFromDialog.getBoolean("PushNotification");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                };
                customReminderDialog.show(getFragmentManager(), "");
            }

        });

        rlNext.setOnClickListener(view1 -> {
            setValuesForSave("");
        });

        return view;
    }

    private void openDialogForNOSETTIMEHABIT(String TYPE) {

        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_no_set_time);
        dialog.setCancelable(false);

        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlNoSetTime = dialog.findViewById(R.id.rlNoSetTime);
        RelativeLayout rlSetTime = dialog.findViewById(R.id.rlSetTime);

        imgCross.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlNoSetTime.setOnClickListener(view -> {
            dialog.dismiss();
            if (TYPE.equals("FIRST")) {
                txtNewHabitTime.setText("NO SET TIME");
            } else {
                txtNewHabitTimeTwice.setText("NO SET TIME");
            }
        });

        rlSetTime.setOnClickListener(view -> {
            dialog.dismiss();
            if (TYPE.equals("FIRST")) {
                TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtNewHabitTime, "", "");
            } else {
                TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtNewHabitTimeTwice, "", "");
            }
        });

        dialog.show();
    }

    private void openDialogForNOSETTIMEHOWLONG() {

        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_no_set_time);
        dialog.setCancelable(false);

        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlNoSetTime = dialog.findViewById(R.id.rlNoSetTime);
        RelativeLayout rlSetTime = dialog.findViewById(R.id.rlSetTime);

        imgCross.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlNoSetTime.setOnClickListener(view -> {
            dialog.dismiss();
            txtHowLongTime.setText("NO SET TIME");
            timePickerHour = 0;
            timePickerMinute = 0;
        });

        rlSetTime.setOnClickListener(view -> {
            dialog.dismiss();
            MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), (view1, hourOfDay, minute, seconds) -> {
                // TODO Auto-generated method stub
                txtHowLongTime.setText(String.format("%02d", hourOfDay) + " hrs " + String.format("%02d", minute) + " mins"/*+ ":" + String.format("%02d", seconds)*/);
                timePickerHour = hourOfDay;
                timePickerMinute = minute;
            }, 0, 0, 0, true);
            mTimePicker.show();
        });

        dialog.show();
    }

    private void setValuesForSave(String TYPE) {

        if (!txtNewHabitTime.getText().toString().equals("")) {

            Integer totatSecond = 0;
            if (txtNewHabitTime.getText().toString().equals("NO SET TIME")) {
                totatSecond = 0;
            } else {
                int hour = 0;
                int minute = 0;
                if (txtNewHabitTime.getText().toString().contains("PM")) {
                    String str = txtNewHabitTime.getText().toString();
                    String[] arrTime = str.split(":");
                    String strHour = arrTime[0];
                    String strMinAm = arrTime[1];
                    String[] arrMin = strMinAm.split("PM");
                    String strMin = arrMin[0].trim();
                    hour = Integer.parseInt(strHour);
                    if (hour != 12) {
                        hour = hour + 12;
                    }
                    minute = Integer.parseInt(strMin);
                } else {
                    String str = txtNewHabitTime.getText().toString();
                    String[] arrTime = str.split(":");
                    String strHour = arrTime[0];
                    String strMinAm = arrTime[1];
                    String[] arrMin = strMinAm.split("AM");
                    String strMin = arrMin[0].trim();
                    hour = Integer.parseInt(strHour);
                    if (hour == 12) {
                        hour = hour + 12;
                    }
                    minute = Integer.parseInt(strMin);
                }
                totatSecond = ((hour * 60) + minute) * 60;
            }
            Log.e("REMINDERAT1", totatSecond + ">>>>>>>>");
            globalHabitSwapModel.getNewAction().setTaskReminderAt1(totatSecond);

        }

        if (!txtNewHabitTimeTwice.getText().toString().equals("")) {

            Integer totatSecond = 0;
            if (txtNewHabitTimeTwice.getText().toString().equals("NO SET TIME")) {
                totatSecond = 0;
            } else {
                int hour = 0;
                int minute = 0;
                if (txtNewHabitTimeTwice.getText().toString().contains("PM")) {
                    String str = txtNewHabitTimeTwice.getText().toString();
                    String[] arrTime = str.split(":");
                    String strHour = arrTime[0];
                    String strMinAm = arrTime[1];
                    String[] arrMin = strMinAm.split("PM");
                    String strMin = arrMin[0].trim();
                    hour = Integer.parseInt(strHour);
                    if (hour != 12) {
                        hour = hour + 12;
                    }
                    minute = Integer.parseInt(strMin);

                } else {
                    String str = txtNewHabitTimeTwice.getText().toString();
                    String[] arrTime = str.split(":");
                    String strHour = arrTime[0];
                    String strMinAm = arrTime[1];
                    String[] arrMin = strMinAm.split("AM");
                    String strMin = arrMin[0].trim();
                    hour = Integer.parseInt(strHour);
                    if (hour == 12) {
                        hour = hour + 12;
                    }
                    minute = Integer.parseInt(strMin);
                }
                totatSecond = ((hour * 60) + minute) * 60;
            }

            globalHabitSwapModel.getNewAction().setTaskReminderAt2(totatSecond);

        }

        if (!txtHowLongTime.getText().toString().equals("")) {
            /*String[] strTime = txtHowLongTime.getText().toString().split(":");
            int hour = Integer.parseInt(strTime[0]);
            int minute = Integer.parseInt(strTime[1]);

            int hourToMIN = hour * 60;
            hourToMIN = hourToMIN + minute;
            int minToSecond = hourToMIN * 60;
            int totalSecond = minToSecond;*/

            int hourToMIN = timePickerHour * 60;
            hourToMIN = hourToMIN + timePickerMinute;
            int minToSecond = hourToMIN * 60;
            int totalSecond = minToSecond;

            globalHabitSwapModel.getNewAction().setDuration(totalSecond);

        }

        if (chkSetReminder.isChecked()) {
            if (jsonFromDialog == null) {
                globalHabitSwapModel.getNewAction().setFrequencyId(1);
                globalHabitSwapModel.getNewAction().setMonthReminder(0);
                globalHabitSwapModel.getNewAction().setReminderOption(1);
                globalHabitSwapModel.getNewAction().setReminderAt1(43200);
                globalHabitSwapModel.getNewAction().setReminderAt2(43200);
                globalHabitSwapModel.getNewAction().setEmail(false);
                globalHabitSwapModel.getNewAction().setPushNotification(true);
                globalHabitSwapModel.getNewAction().setPushNotification(true);
                globalHabitSwapModel.getNewAction().setSunday(false);
                globalHabitSwapModel.getNewAction().setMonday(false);
                globalHabitSwapModel.getNewAction().setTuesday(false);
                globalHabitSwapModel.getNewAction().setWednesday(false);
                globalHabitSwapModel.getNewAction().setThursday(false);
                globalHabitSwapModel.getNewAction().setFriday(false);
                globalHabitSwapModel.getNewAction().setSaturday(false);
                globalHabitSwapModel.getNewAction().setJanuary(false);
                globalHabitSwapModel.getNewAction().setFebruary(false);
                globalHabitSwapModel.getNewAction().setMarch(false);
                globalHabitSwapModel.getNewAction().setApril(false);
                globalHabitSwapModel.getNewAction().setMay(false);
                globalHabitSwapModel.getNewAction().setJune(false);
                globalHabitSwapModel.getNewAction().setJuly(false);
                globalHabitSwapModel.getNewAction().setAugust(false);
                globalHabitSwapModel.getNewAction().setSeptember(false);
                globalHabitSwapModel.getNewAction().setOctober(false);
                globalHabitSwapModel.getNewAction().setNovember(false);
                globalHabitSwapModel.getNewAction().setDecember(false);
            } else {
                try {
                    globalHabitSwapModel.getNewAction().setFrequencyId(jsonFromDialog.getInt("FrequencyId"));
                    globalHabitSwapModel.getNewAction().setMonthReminder(jsonFromDialog.getInt("MonthReminder"));
                    globalHabitSwapModel.getNewAction().setReminderOption(jsonFromDialog.getInt("ReminderOption"));
                    globalHabitSwapModel.getNewAction().setReminderAt1(jsonFromDialog.getInt("ReminderAt1"));
                    globalHabitSwapModel.getNewAction().setReminderAt2(jsonFromDialog.getInt("ReminderAt2"));
                    globalHabitSwapModel.getNewAction().setEmail(jsonFromDialog.getBoolean("Email"));
                    globalHabitSwapModel.getNewAction().setPushNotification(jsonFromDialog.getBoolean("PushNotification"));
                    globalHabitSwapModel.getNewAction().setSunday(jsonFromDialog.getBoolean("Sunday"));
                    globalHabitSwapModel.getNewAction().setMonday(jsonFromDialog.getBoolean("Monday"));
                    globalHabitSwapModel.getNewAction().setTuesday(jsonFromDialog.getBoolean("Tuesday"));
                    globalHabitSwapModel.getNewAction().setWednesday(jsonFromDialog.getBoolean("Wednesday"));
                    globalHabitSwapModel.getNewAction().setThursday(jsonFromDialog.getBoolean("Thursday"));
                    globalHabitSwapModel.getNewAction().setFriday(jsonFromDialog.getBoolean("Friday"));
                    globalHabitSwapModel.getNewAction().setSaturday(jsonFromDialog.getBoolean("Saturday"));
                    globalHabitSwapModel.getNewAction().setJanuary(jsonFromDialog.getBoolean("January"));
                    globalHabitSwapModel.getNewAction().setFebruary(jsonFromDialog.getBoolean("February"));
                    globalHabitSwapModel.getNewAction().setMarch(jsonFromDialog.getBoolean("March"));
                    globalHabitSwapModel.getNewAction().setApril(jsonFromDialog.getBoolean("April"));
                    globalHabitSwapModel.getNewAction().setMay(jsonFromDialog.getBoolean("May"));
                    globalHabitSwapModel.getNewAction().setJune(jsonFromDialog.getBoolean("June"));
                    globalHabitSwapModel.getNewAction().setJuly(jsonFromDialog.getBoolean("July"));
                    globalHabitSwapModel.getNewAction().setAugust(jsonFromDialog.getBoolean("August"));
                    globalHabitSwapModel.getNewAction().setSeptember(jsonFromDialog.getBoolean("September"));
                    globalHabitSwapModel.getNewAction().setOctober(jsonFromDialog.getBoolean("October"));
                    globalHabitSwapModel.getNewAction().setNovember(jsonFromDialog.getBoolean("November"));
                    globalHabitSwapModel.getNewAction().setDecember(jsonFromDialog.getBoolean("December"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        if (TYPE.equals("BACK_CLICKED")) {
            saveNewBreakHabit();
        } else {
            if (chkSetReminder.isChecked()) {

            }
           /* Gson gson = new Gson();
            Bundle bundle = new Bundle();
            bundle.putString("HABIT_HACKER_ADD_MODEL", gson.toJson(globalHabitSwapModel));
            ((MainActivity) getActivity()).loadFragment(new HabitHackerAddTwo(), "HabitHackerAddTwo", bundle);*/

            ((HabitHackerAddMainFragment) getParentFragment()).saveAndNext(globalHabitSwapModel);
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("Resume","onResume");
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
                    if (Util.HABIT_ADDED_FROM_TODAY_PAGE) {
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                    } else {
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
        Log.i("pause","pause");
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

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

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
                        Util.isReloadTodayMainPage = true;
                        Util.showToast(getActivity(), "Successfully Saved");
                        try {
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (Util.HABIT_ADDED_FROM_TODAY_PAGE) {
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
