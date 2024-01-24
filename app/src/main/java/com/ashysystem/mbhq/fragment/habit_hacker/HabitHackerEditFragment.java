package com.ashysystem.mbhq.fragment.habit_hacker;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.dialog.CustomReminderDialogForEditHabit;
import com.ashysystem.mbhq.dialog.CustomReminderDialogForHabitOtherReminder;
import com.ashysystem.mbhq.model.habit_hacker.CurrentDayTask;
import com.ashysystem.mbhq.model.habit_hacker.GetUserHabitSwapModel;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactory;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForHabitCalendar;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitCalendarViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitEditViewModel;
import com.ashysystem.mbhq.util.AlarmReceiver;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.Constants;
import com.ashysystem.mbhq.util.MyTimePickerDialog;
import com.ashysystem.mbhq.util.SetLocalNotificationForHabit;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.TimePicker;
import com.ashysystem.mbhq.util.TimePickerControllerWithTextView;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HabitHackerEditFragment extends Fragment {

    TextView txtBack,txtHabitName,txtFrequencyValue,txtWhenValue,txtHowLongValue,txtHabitReminder,txtWhereNewHabit,txtCueNewAction,txtBeSpecific,txtSeen,txtFunReward,txtMoreAccountable,txtBreakName,txtBreakBeSpecific,txtChangeSomething,txtReframe,txtWhenValue2,txtWhereNewHabitQuestion,txtCueNewHabitQuestion,txtSpecificNewActionQuestion,txtSeenNewHabitQuestion,txtRewardNewHabitQuestion,txtAccountableHabitQuestion,txtBreakOldActionQuestion,txtHarderBreakQuestion,txtReframeBreakHabitQuestion;
    ImageView imgEditHabit,imgDeleteHabit,imgInProgress,imgCompleted,imgHidden;
    CheckBox chkSetReminder;
    RelativeLayout rlStats,rlSaveHabit;
    LinearLayout llBottom,llForTwiceDaily;
    Toolbar toolbar;
    Bundle globalBundle;
    View globalView;

    Integer HABIT_ID;
    SharedPreference sharedPreference;
    FinisherServiceImpl finisherService;
    GetUserHabitSwapModel globalHabitSwap;

    JSONObject rootJsonInner;
    boolean dialogOpenOnceForEdit=false;

    String[] arrFreq={"Daily"/*,"Twice Daily"*/,"Weekly","Fortnightly","Monthly"/*,"Yearly"*/};

    Integer HABIT_STATUS_ALL = 0;
    Integer HABIT_STATUS_ACTIVE = 1;
    Integer HABIT_STATUS_COMPLETED = 2;
    Integer HABIT_STATUS_SNOOZE = 3;
    Integer HABIT_RESTART_FROM_TODAY = 4;

    boolean boolEditButtonClicked = false;

    Integer timePickerHour = 0;
    Integer timePickerMinute = 0;

    private ViewModelFactory mViewModelFactory;
    private HabitEditViewModel mViewModel;
    private ViewModelFactoryForHabitCalendar mViewModelFactoryCalendar;
    private HabitCalendarViewModel mViewModelCalendar;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    String PREVIOUS_SET_NOTIFICATION = "";

    JSONObject globalHabitInner = null;
    private boolean boolHiddenIsClicked = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(globalView == null)
        {
            globalView = inflater.inflate(R.layout.fragment_habit_edit,null);

            funToolBar();

            rlStats = globalView.findViewById(R.id.rlStats);
            txtBack = globalView.findViewById(R.id.txtBack);
            txtHabitName = globalView.findViewById(R.id.txtHabitName);
            txtFrequencyValue = globalView.findViewById(R.id.txtFrequencyValue1);
            txtWhenValue = globalView.findViewById(R.id.txtWhenValue);
            txtHowLongValue = globalView.findViewById(R.id.txtHowLongValue);
            txtHabitReminder = globalView.findViewById(R.id.txtHabitReminder);
            txtWhereNewHabit = globalView.findViewById(R.id.txtWhereNewHabit);
            txtCueNewAction = globalView.findViewById(R.id.txtCueNewAction);
            txtBeSpecific = globalView.findViewById(R.id.txtBeSpecific);
            txtSeen = globalView.findViewById(R.id.txtSeen);
            txtFunReward = globalView.findViewById(R.id.txtFunReward);
            txtMoreAccountable = globalView.findViewById(R.id.txtMoreAccountable);
            txtBreakName = globalView.findViewById(R.id.txtBreakName);
            txtBreakBeSpecific = globalView.findViewById(R.id.txtBreakBeSpecific);
            txtChangeSomething = globalView.findViewById(R.id.txtChangeSomething);
            txtReframe = globalView.findViewById(R.id.txtReframe);
            imgEditHabit = globalView.findViewById(R.id.imgEditHabit);
            imgDeleteHabit = globalView.findViewById(R.id.imgDeleteHabit);
            imgInProgress = globalView.findViewById(R.id.imgInProgress);
            imgCompleted = globalView.findViewById(R.id.imgCompleted);
            imgHidden = globalView.findViewById(R.id.imgHidden);
            chkSetReminder = globalView.findViewById(R.id.chkSetReminder);
            rlSaveHabit = globalView.findViewById(R.id.rlSaveHabit);
            llBottom = globalView.findViewById(R.id.llBottom);
            llForTwiceDaily = globalView.findViewById(R.id.llForTwiceDaily);
            txtWhenValue2 = globalView.findViewById(R.id.txtWhenValue2);
            txtWhereNewHabitQuestion = globalView.findViewById(R.id.txtWhereNewHabitQuestion);
            txtCueNewHabitQuestion = globalView.findViewById(R.id.txtCueNewHabitQuestion);
            txtSpecificNewActionQuestion = globalView.findViewById(R.id.txtSpecificNewActionQuestion);
            txtSeenNewHabitQuestion = globalView.findViewById(R.id.txtSeenNewHabitQuestion);
            txtRewardNewHabitQuestion = globalView.findViewById(R.id.txtRewardNewHabitQuestion);
            txtAccountableHabitQuestion = globalView.findViewById(R.id.txtAccountableHabitQuestion);
            txtBreakOldActionQuestion = globalView.findViewById(R.id.txtBreakOldActionQuestion);
            txtHarderBreakQuestion = globalView.findViewById(R.id.txtHarderBreakQuestion);
            txtReframeBreakHabitQuestion = globalView.findViewById(R.id.txtReframeBreakHabitQuestion);

            sharedPreference = new SharedPreference(getActivity());
            finisherService = new FinisherServiceImpl(getActivity());

            if(getArguments()!=null)
            {
                globalBundle = getArguments();
                if(getArguments().containsKey("id"))
                {
                    HABIT_ID = getArguments().getInt("id");
                }
                if(getArguments().containsKey("NOTIFICATIONID"))
                {
                    HABIT_ID = getArguments().getInt("NOTIFICATIONID");
                }
             /*   if (getArguments().containsKey("new_action1")) {
                    breakName = getArguments().getString("new_action1");
                }else{
                    breakName="";
                }


                if (getArguments().containsKey("new_action1")) {
                    breakName = getArguments().getString("new_action1");
                }else{
                    breakName="";
                }
                if (getArguments().containsKey("swap_action1")) {
                    breakName = getArguments().getString("swap_action1");
                }else{
                    breakName="";
                }
                if (getArguments().containsKey("swap_action2")) {
                    breakName = getArguments().getString("swap_action2");
                }else{
                    breakName="";
                }*/
                Log.e("HABIT_ID",HABIT_ID + ">>>>>>>>");
            }

            mViewModelFactory = Injection.provideViewModelFactory(getActivity());
            mViewModel =new ViewModelProvider(this, mViewModelFactory).get(HabitEditViewModel.class);
            mViewModelFactoryCalendar = Injection.provideViewModelFactoryHabitCalendar(getActivity());
            mViewModelCalendar =new ViewModelProvider(this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);

            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////


            /*if(PREVIOUS_SET_NOTIFICATION.equals("TRUE")){
                chkSetReminder.setChecked(true);
               // openpopup1();
            }else {
                chkSetReminder.setChecked(false);
            }*/
            ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



            rlStats.setOnClickListener(view1 -> {
                ((MainActivity)getActivity()).loadFragment(new HabbitDetailsCalendarFragment(),"HabbitDetailsCalendar",globalBundle);
            });

            txtBack.setOnClickListener(view -> {
                Util.backtotodayhabit="";
                Constants.clicknotification=false;

                if(getArguments()!=null && getArguments().containsKey("NOTIFICATIONID"))
                {
                    ((MainActivity)getActivity()).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",null);
                }else {
                    if(getArguments()!=null && getArguments().containsKey("FROM_SIMPLE_HABIT_LIST"))
                    {
                        ((MainActivity)getActivity()).loadFragment(new MbhqTodayTwoFragment(),"MbhqTodayTwo",null);
                    }else if(getArguments()!=null && getArguments().containsKey("FROM_HABIT_LIST"))
                    {
                        ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                    }
                    else {
                        ((MainActivity)getActivity()).loadFragment(new HabbitDetailsCalendarFragment(),"HabbitDetailsCalendar",globalBundle);
                    }
                }
            });

            txtFrequencyValue.setOnClickListener(view -> {
                CustomReminderDialogForEditHabit customReminderDialog = new CustomReminderDialogForEditHabit();
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                bundle.putString("MODEL",gson.toJson(globalHabitSwap.getHabitSwap().getNewAction()));
                bundle.putString("HABIT_HACKER_TASK", "TRUE");
                customReminderDialog.setArguments(bundle);
                customReminderDialog.mListener = new CustomReminderDialogForEditHabit.onSubmitListener() {
                    @Override
                    public void setOnSubmitListener(JSONObject arg) {

                        if(arg!=null)
                        {
                            Log.e("HABITHACKER", arg.toString() + "?");
                            globalHabitInner = arg;
                            if (globalHabitInner != null && globalHabitInner.has("FrequencyId")) {
                                try {
                                    if (globalHabitInner.getInt("FrequencyId") == 1) {
                                        txtFrequencyValue.setText("Daily");
                                        llForTwiceDaily.setVisibility(View.GONE);
                                    } else if (globalHabitInner.getInt("FrequencyId") == 2) {
                                        txtFrequencyValue.setText("Twice Daily");
                                        llForTwiceDaily.setVisibility(View.VISIBLE);
                                    } else if (globalHabitInner.getInt("FrequencyId") == 3) {
                                        String strValue = "Weekly (";
                                        if(globalHabitInner.has("Sunday") && globalHabitInner.getBoolean("Sunday"))
                                        {
                                            strValue = strValue + "Sunday ";
                                        }
                                        if(globalHabitInner.has("Monday") && globalHabitInner.getBoolean("Monday"))
                                        {
                                            strValue = strValue + "Monday ";
                                        }
                                        if(globalHabitInner.has("Tuesday") && globalHabitInner.getBoolean("Tuesday"))
                                        {
                                            strValue = strValue + "Tuesday ";
                                        }
                                        if(globalHabitInner.has("Wednesday") && globalHabitInner.getBoolean("Wednesday"))
                                        {
                                            strValue = strValue + "Wednesday ";
                                        }
                                        if(globalHabitInner.has("Thursday") && globalHabitInner.getBoolean("Thursday"))
                                        {
                                            strValue = strValue + "Thursday ";
                                        }
                                        if(globalHabitInner.has("Friday") && globalHabitInner.getBoolean("Friday"))
                                        {
                                            strValue = strValue + "Friday";
                                        }
                                        if(globalHabitInner.has("Saturday") && globalHabitInner.getBoolean("Saturday"))
                                        {
                                            strValue = strValue + "Saturday ";
                                        }
                                        strValue = strValue + ")";
                                        txtFrequencyValue.setText(strValue);
                                        llForTwiceDaily.setVisibility(View.GONE);
                                    } else if (globalHabitInner.getInt("FrequencyId") == 4) {
                                        String strValue = "Weekly (";
                                        if(globalHabitInner.has("Sunday") && globalHabitInner.getBoolean("Sunday"))
                                        {
                                            strValue = strValue + "Sunday ";
                                        }
                                        if(globalHabitInner.has("Monday") && globalHabitInner.getBoolean("Monday"))
                                        {
                                            strValue = strValue + "Monday ";
                                        }
                                        if(globalHabitInner.has("Tuesday") && globalHabitInner.getBoolean("Tuesday"))
                                        {
                                            strValue = strValue + "Tuesday ";
                                        }
                                        if(globalHabitInner.has("Wednesday") && globalHabitInner.getBoolean("Wednesday"))
                                        {
                                            strValue = strValue + "Wednesday ";
                                        }
                                        if(globalHabitInner.has("Thursday") && globalHabitInner.getBoolean("Thursday"))
                                        {
                                            strValue = strValue + "Thursday ";
                                        }
                                        if(globalHabitInner.has("Friday") && globalHabitInner.getBoolean("Friday"))
                                        {
                                            strValue = strValue + "Friday";
                                        }
                                        if(globalHabitInner.has("Saturday") && globalHabitInner.getBoolean("Saturday"))
                                        {
                                            strValue = strValue + "Saturday ";
                                        }
                                        strValue = strValue + ")";
                                        txtFrequencyValue.setText(strValue);
                                        llForTwiceDaily.setVisibility(View.GONE);
                                    } else if (globalHabitInner.getInt("FrequencyId") == 5) {
                                        String strValue = "Monthly (";
                                        if(globalHabitInner.has("MonthReminder"))
                                        {
                                            strValue = strValue + globalHabitInner.getInt("MonthReminder") + " Day of Month )";
                                            txtFrequencyValue.setText(strValue);
                                        }else {
                                            txtFrequencyValue.setText("Monthly");
                                        }
                                        llForTwiceDaily.setVisibility(View.GONE);
                                    } else if (globalHabitInner.getInt("FrequencyId") == 6) {
                                        txtFrequencyValue.setText("Yearly");
                                        llForTwiceDaily.setVisibility(View.GONE);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };
                customReminderDialog.show(getFragmentManager(), "");
            });

            txtHabitReminder.setOnClickListener(view -> {
                if(chkSetReminder.isChecked() && txtHabitReminder.getText().toString().equals("View Reminder"))
                {
                    CustomReminderDialogForHabitOtherReminder customReminderDialog = new CustomReminderDialogForHabitOtherReminder();
                    Gson gson=new Gson();
                    Bundle bundle=new Bundle();
                    bundle.putString("MODEL",gson.toJson(globalHabitSwap.getHabitSwap().getNewAction()));
                    bundle.putString("MODELNAME","GRATITUDE");
                    bundle.putBoolean("PUSH_NOTIFICATION",globalHabitSwap.getHabitSwap().getNewAction().getPushNotification());
                    customReminderDialog.setArguments(bundle);
                    customReminderDialog.mListener = new CustomReminderDialogForHabitOtherReminder.onSubmitListener() {
                        @Override
                        public void setOnSubmitListener(JSONObject arg) {
                            if(arg !=null)
                            {
                                Log.e("print jj",arg.toString()+"?");
                                /////////////////////////////////////////////////////////////////////////////////////////////
                                try {
                                    Log.e("SET_PUSH", "setOnSubmitListener: " + arg.getBoolean("PushNotification"));
                                    if(!arg.getBoolean("PushNotification")){
                                        chkSetReminder.setChecked(false); ////////////
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                /////////////////////////////////////////////////////////////////////////////////////////////
                                dialogOpenOnceForEdit=true;
                                //globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                                rootJsonInner=arg;
                                try {
                                    Log.e("INNER_FREQUENCY_ID_1",rootJsonInner.getInt("FrequencyId")+">>>>");
                                    globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(rootJsonInner.getInt("ReminderOption"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                                    globalHabitSwap.getHabitSwap().getNewAction().setEmail(rootJsonInner.getBoolean("Email"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setSunday(rootJsonInner.getBoolean("Sunday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setMonday(rootJsonInner.getBoolean("Monday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setTuesday(rootJsonInner.getBoolean("Tuesday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setWednesday(rootJsonInner.getBoolean("Wednesday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setThursday(rootJsonInner.getBoolean("Thursday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setFriday(rootJsonInner.getBoolean("Friday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setSaturday(rootJsonInner.getBoolean("Saturday"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setJanuary(rootJsonInner.getBoolean("January"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setFebruary(rootJsonInner.getBoolean("February"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setMarch(rootJsonInner.getBoolean("March"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setApril(rootJsonInner.getBoolean("April"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setMay(rootJsonInner.getBoolean("May"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setJune(rootJsonInner.getBoolean("June"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setAugust(rootJsonInner.getBoolean("August"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setSeptember(rootJsonInner.getBoolean("September"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setOctober(rootJsonInner.getBoolean("October"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setNovember(rootJsonInner.getBoolean("November"));
                                    globalHabitSwap.getHabitSwap().getNewAction().setDecember(rootJsonInner.getBoolean("December"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }else {
                                //chkSetReminder.setChecked(false);
                            }
                        }
                    };
                    customReminderDialog.show(getFragmentManager(), "");
                }
            });

            chkSetReminder.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if(isChecked)
                {
                    if(!txtHabitName.getText().toString().equals(""))
                    {
                        txtHabitReminder.setText("View Reminder");

                        CustomReminderDialogForHabitOtherReminder customReminderDialog = new CustomReminderDialogForHabitOtherReminder();
                        Gson gson=new Gson();
                        Bundle bundle=new Bundle();
                        bundle.putString("MODEL",gson.toJson(globalHabitSwap.getHabitSwap().getNewAction()));
                        bundle.putString("HABIT_NAME",globalHabitSwap.getHabitSwap().getHabitName());
                        bundle.putString("MODELNAME","GRATITUDE");
                        bundle.putBoolean("PUSH_NOTIFICATION",globalHabitSwap.getHabitSwap().getNewAction().getPushNotification());
                        customReminderDialog.setArguments(bundle);
                        customReminderDialog.mListener= new CustomReminderDialogForHabitOtherReminder.onSubmitListener() {
                            @Override
                            public void setOnSubmitListener(JSONObject arg) {

                                if(arg != null)
                                {
                                    Log.e("print jj",arg.toString()+"?");
                                    /////////////////////////////////////////////////////////////////////////////////////////////
                                    try {
                                        Log.e("SET_PUSH", "setOnSubmitListener: " + arg.getBoolean("PushNotification"));
                                        if(!arg.getBoolean("PushNotification")){
                                            chkSetReminder.setChecked(false); ////////////
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    /////////////////////////////////////////////////////////////////////////////////////////////
                                    dialogOpenOnceForEdit=true;
                                    globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                                    rootJsonInner=arg;

                                    try {
                                        Log.e("INNER_FREQUENCY_ID_2",rootJsonInner.getInt("FrequencyId")+">>>>");
                                        globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(rootJsonInner.getInt("ReminderOption"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                                        globalHabitSwap.getHabitSwap().getNewAction().setEmail(rootJsonInner.getBoolean("Email"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setSunday(rootJsonInner.getBoolean("Sunday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setMonday(rootJsonInner.getBoolean("Monday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setTuesday(rootJsonInner.getBoolean("Tuesday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setWednesday(rootJsonInner.getBoolean("Wednesday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setThursday(rootJsonInner.getBoolean("Thursday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setFriday(rootJsonInner.getBoolean("Friday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setSaturday(rootJsonInner.getBoolean("Saturday"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setJanuary(rootJsonInner.getBoolean("January"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setFebruary(rootJsonInner.getBoolean("February"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setMarch(rootJsonInner.getBoolean("March"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setApril(rootJsonInner.getBoolean("April"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setMay(rootJsonInner.getBoolean("May"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setJune(rootJsonInner.getBoolean("June"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setAugust(rootJsonInner.getBoolean("August"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setSeptember(rootJsonInner.getBoolean("September"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setOctober(rootJsonInner.getBoolean("October"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setNovember(rootJsonInner.getBoolean("November"));
                                        globalHabitSwap.getHabitSwap().getNewAction().setDecember(rootJsonInner.getBoolean("December"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    chkSetReminder.setChecked(false);
                                    globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(false);
                                }
                            }
                        };
                        customReminderDialog.show(getFragmentManager(), "");
                    }else {
                        //Toast.makeText(getActivity(),"Please enter Habit name",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if(PREVIOUS_SET_NOTIFICATION.equals("TRUE"))
                    {
                        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
                        dialog.setContentView(R.layout.dialog_turnoff_edit_reminder);
                        dialog.setCancelable(false);

                        RelativeLayout rlEditReminder = dialog.findViewById(R.id.rlEditReminder);
                        RelativeLayout rlTurnOffReminder = dialog.findViewById(R.id.rlTurnOffReminder);
                        RelativeLayout rlTop = dialog.findViewById(R.id.rlTop);
                        RelativeLayout rlBottom = dialog.findViewById(R.id.rlBottom);

                        rlTop.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION = "FALSE";
                            txtHabitReminder.setText("Reminder");
                        });

                        rlBottom.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION = "FALSE";
                            txtHabitReminder.setText("Reminder");
                        });

                        rlEditReminder.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(true);
                        });

                        rlTurnOffReminder.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION = "FALSE";
                            txtHabitReminder.setText("Reminder");
                        });

                        dialog.show();
                    }else {
                        txtHabitReminder.setText("Reminder");
                    }

                }
            });

            imgEditHabit.setOnClickListener(view -> {
                if(!boolEditButtonClicked)
                {
                    setAllClickableTrue();
                    boolEditButtonClicked = true;
                    imgEditHabit.setImageResource(R.drawable.mbhq_edit_active);
                    llBottom.setVisibility(View.GONE);
                    rlSaveHabit.setVisibility(View.VISIBLE);
                }else {
                    setAllClickableFalse();
                    boolEditButtonClicked = false;
                    imgEditHabit.setImageResource(R.drawable.mbhq_edit_inactive);
                    llBottom.setVisibility(View.GONE);
                    rlSaveHabit.setVisibility(View.GONE);
                }
            });

            txtWhenValue.setOnClickListener(view1 -> {
                openDialogForNOSETTIMEHABIT("FIRST");
            });

            txtWhenValue2.setOnClickListener(view1 -> {
                openDialogForNOSETTIMEHABIT("SECOND");
            });

            txtHowLongValue.setOnClickListener(view1 -> {
                openDialogForNOSETTIMEHOWLONG();
            });

            txtHabitName.setOnClickListener(view -> {
                openDialogForAddSomething("Habit Name :",txtHabitName);
            });

            txtWhereNewHabit.setOnClickListener(view -> {
                openDialogForAddSomething(txtWhereNewHabitQuestion.getText().toString(),txtWhereNewHabit);
            });

            txtCueNewAction.setOnClickListener(view -> {
                openDialogForAddSomething(txtCueNewHabitQuestion.getText().toString(),txtCueNewAction);
            });

            txtBeSpecific.setOnClickListener(view -> {
                openDialogForAddSomething(txtSpecificNewActionQuestion.getText().toString(),txtBeSpecific);
            });

            txtSeen.setOnClickListener(view -> {
                openDialogForAddSomething(txtSeenNewHabitQuestion.getText().toString(),txtSeen);
            });

            txtFunReward.setOnClickListener(view -> {
                openDialogForAddSomething(txtRewardNewHabitQuestion.getText().toString(),txtFunReward);
            });

            txtMoreAccountable.setOnClickListener(view -> {
                openDialogForAddSomething(txtAccountableHabitQuestion.getText().toString(),txtMoreAccountable);
            });

            txtBreakName.setOnClickListener(view -> {
                openDialogForAddSomething("Break Name :",txtBreakName);
            });

            txtBreakBeSpecific.setOnClickListener(view -> {
                openDialogForAddSomething(txtBreakOldActionQuestion.getText().toString(),txtBreakBeSpecific);
            });

            txtChangeSomething.setOnClickListener(view -> {
                openDialogForAddSomething(txtHarderBreakQuestion.getText().toString(),txtChangeSomething);
            });

            txtReframe.setOnClickListener(view -> {
                openDialogForAddSomething(txtReframeBreakHabitQuestion.getText().toString(),txtReframe);
            });

            imgInProgress.setOnClickListener(view -> {
                updateHabitStatusApiCall(HABIT_STATUS_ACTIVE,imgInProgress);
            });

            imgCompleted.setOnClickListener(view -> {
                updateHabitStatusApiCall(HABIT_STATUS_COMPLETED,imgCompleted);
            });

            imgHidden.setOnClickListener(view -> {
                updateHabitStatusApiCall(HABIT_STATUS_SNOOZE,imgHidden);
            });

            imgDeleteHabit.setOnClickListener(view -> {
                openDialogForHabitDelete();
            });

            rlSaveHabit.setOnClickListener(view -> {

                setValuesForHabitSave("");

            });


            mDisposable.add(mViewModel.getCount(HABIT_ID)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if(integer == 0)
                        {
                            Log.e("DATABASE", "IF-PART");
                            getUserHabitSwapApiCall();
                        }else {
                            Log.e("DATABASE", "ELSE-PART");
                            mDisposable.add(mViewModel.getHabitDetails(HABIT_ID)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(habitHackerEdit -> {
                                        Log.e("DATABASE","INSERT_TRUE " + habitHackerEdit.getHabitDetails()+">>>");
                                        Gson gson = new Gson();
                                        globalHabitSwap = gson.fromJson(habitHackerEdit.getHabitDetails(),GetUserHabitSwapModel.class);
                                        Log.e("PUSHHHH_TAGG", "onCreateView: " +  globalHabitSwap.getHabitSwap().getNewAction().getPushNotification());
                                        if(globalHabitSwap.getHabitSwap().getNewAction().getPushNotification())
                                        {
                                            PREVIOUS_SET_NOTIFICATION = "TRUE";
                                        }else {
                                            PREVIOUS_SET_NOTIFICATION = "FALSE";
                                        }
                                        setPopUpForReminder(PREVIOUS_SET_NOTIFICATION);
                                        setAllClickableFalse();
                                        setValuesForhabitDetails(globalHabitSwap.getHabitSwap());
                                        // openpopup();
                                    },throwable -> {
                                        Log.e("DATABASE","INSERT_FALSE");
                                    }));
                        }
                    },throwable -> Log.e("DATABASE", "Unable to load habit details", throwable)));




            return globalView;
        }else {
            return globalView;
        }
    }

    private void setPopUpForReminder(String previous_set_notification) {
        Log.e("DATAA_TAG", "setPopUpForReminder: " + previous_set_notification);
        if(previous_set_notification.equals("TRUE")){
            chkSetReminder.setChecked(true);
            openpopup1();
        }else {
            chkSetReminder.setChecked(false);
        }

    }

    public  void openDialogForHabitDelete() {

        final Dialog dialog=new Dialog(getActivity(),R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_delete_habit_alert);

        TextView txtDelete = dialog.findViewById(R.id.txtDelete);
        TextView txtRestartFromToday = dialog.findViewById(R.id.txtRestartFromToday);
        TextView txtPauseHide = dialog.findViewById(R.id.txtPauseHide);
        TextView txtNo = dialog.findViewById(R.id.txtNo);

        txtDelete.setOnClickListener(view -> {
            dialog.dismiss();
            deleteHabitSwapApiCall();
        });

        txtRestartFromToday.setOnClickListener(view -> {
            dialog.dismiss();
            updateHabitStatusApiCall(HABIT_RESTART_FROM_TODAY,imgInProgress);
        });

        txtPauseHide.setOnClickListener(view -> {
            dialog.dismiss();
            updateHabitStatusApiCall(HABIT_STATUS_SNOOZE,imgHidden);
        });

        txtNo.setOnClickListener(view -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    private void deleteHabitSwapApiCall() {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("HabitId", HABIT_ID);
            hashReq.put("Key", Util.KEY);
            hashReq.put("AbbbcUserId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
            hashReq.put("AbbbcUserSessionId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> swapModelCall = finisherService.deleteHabitSwap(hashReq);
            swapModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if(response.body()!=null && response.body().get("SuccessFlag").getAsBoolean())
                    {
                        Util.showToast(getActivity(),"Successfully deleted");
                        ((MainActivity)getActivity()).callTaskStatusForDateAPI("");

                        Completable.fromAction(() -> {
                                    mViewModel.deleteByHabitId(HABIT_ID);
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("DATABASE","DELETE_SUCCESSFULL");
                                    Completable.fromAction(() -> {
                                                mViewModelCalendar.deleteHabitCalendarById(HABIT_ID);
                                            }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                Util.isReloadTodayMainPage = true;
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                                ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                            },throwable -> {
                                                Util.isReloadTodayMainPage = true;
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                                ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                            });
                                },throwable -> {
                                    Log.e("DATABASE","DELETE_UN_SUCCESSFULL");
                                    Util.isReloadTodayMainPage = true;
                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                    ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                });
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else {
            Util.showToast(getActivity(),Util.networkMsg);
        }

    }

    private void updateHabitStatusApiCall(Integer habitStatus,ImageView imageView) {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("HabitId", HABIT_ID);
            hashReq.put("Key", Util.KEY);
            hashReq.put("HabitStatus", habitStatus);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> swapModelCall = finisherService.updateHabitStatus(hashReq);
            swapModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if(response.body()!=null && response.body().get("SuccessFlag").getAsBoolean())
                    {
                        if(habitStatus == HABIT_STATUS_ACTIVE)
                        {
                            globalHabitSwap.getHabitSwap().setStatus(HABIT_STATUS_ACTIVE);
                            imgInProgress.setImageResource(R.drawable.mbhq_ic_tick);
                            imgCompleted.setImageResource(R.drawable.mbhq_ic_untick);
                            imgHidden.setImageResource(R.drawable.mbhq_ic_untick);

                            boolHiddenIsClicked = false;

                        }else if(habitStatus == HABIT_STATUS_COMPLETED)
                        {
                            globalHabitSwap.getHabitSwap().setStatus(HABIT_STATUS_COMPLETED);
                            imgInProgress.setImageResource(R.drawable.mbhq_ic_untick);
                            imgCompleted.setImageResource(R.drawable.mbhq_ic_tick);
                            imgHidden.setImageResource(R.drawable.mbhq_ic_untick);

                            boolHiddenIsClicked = false;

                        }else if(habitStatus == HABIT_STATUS_SNOOZE)
                        {
                            globalHabitSwap.getHabitSwap().setStatus(HABIT_STATUS_SNOOZE);
                            imgInProgress.setImageResource(R.drawable.mbhq_ic_untick);
                            imgCompleted.setImageResource(R.drawable.mbhq_ic_untick);
                            imgHidden.setImageResource(R.drawable.mbhq_ic_tick);

                            boolHiddenIsClicked = true;

                            setValuesForHabitSave("HABIT_STATUS_SNOOZE");

                        }else if(habitStatus == HABIT_RESTART_FROM_TODAY)
                        {
                            globalHabitSwap.getHabitSwap().setStatus(HABIT_STATUS_ACTIVE);
                            imgInProgress.setImageResource(R.drawable.mbhq_ic_tick);
                            imgCompleted.setImageResource(R.drawable.mbhq_ic_untick);
                            imgHidden.setImageResource(R.drawable.mbhq_ic_untick);

                            boolHiddenIsClicked = false;

                        }

                        Gson gson = new Gson();
                        String detailValues = gson.toJson(globalHabitSwap);
                        HabitHackerEdit habitHackerEdit = new HabitHackerEdit(HABIT_ID,detailValues,false);
                        /*mDisposable.add(mViewModel.insertUpdateUserName(habitHackerEdit)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("HABIT_INSERT","SUCCESSFULL");
                                },throwable -> {
                                    Log.e("HABIT_INSERT","NOT_SUCCESSFULL");
                                }));*/
                        Completable.fromAction(() -> {
                                    mViewModel.deleteByHabitId(HABIT_ID);
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("DATABASE","DELETE_SUCCESSFULL");
                                },throwable -> {
                                    Log.e("DATABASE","DELETE_UN_SUCCESSFULL");
                                });

                        Completable.fromAction(() -> {
                                    mViewModelCalendar.deleteHabitCalendarById(HABIT_ID);
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {

                                },throwable -> {

                                });
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else {
            Util.showToast(getActivity(),Util.networkMsg);
        }

    }

    private void openDialogForAddSomething(String heading,TextView txtAddValue) {

        final Dialog dialog=new Dialog(getActivity(),R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_add_habit);

        TextView txtBack = dialog.findViewById(R.id.txtBack);
        TextView tstSelectedOption = dialog.findViewById(R.id.tstSelectedOption);
        EditText edtAchieve = dialog.findViewById(R.id.edtAchieve);
        RelativeLayout rlSave = dialog.findViewById(R.id.rlSave);
        RelativeLayout rlShare = dialog.findViewById(R.id.rlShare);

        rlShare.setVisibility(View.GONE);

        txtBack.setOnClickListener(view -> {
            dialog.dismiss();
        });

        tstSelectedOption.setText(heading);

        edtAchieve.setText(txtAddValue.getText().toString());

        rlSave.setOnClickListener(view -> {
            txtAddValue.setText(edtAchieve.getText().toString());
            dialog.dismiss();
        });

        dialog.show();
    }

    private void setAllClickableFalse() {

        txtHabitName.setClickable(false);
        txtFrequencyValue.setClickable(false);
        txtWhenValue.setClickable(false);
        txtWhenValue2.setClickable(false);
        txtHowLongValue.setClickable(false);
        txtHabitReminder.setClickable(false);
        chkSetReminder.setEnabled(false);
        txtWhereNewHabit.setClickable(false);
        txtCueNewAction.setClickable(false);
        txtBeSpecific.setClickable(false);
        txtSeen.setClickable(false);
        txtFunReward.setClickable(false);
        txtMoreAccountable.setClickable(false);
        txtBreakName.setClickable(false);
        txtBreakBeSpecific.setClickable(false);
        txtChangeSomething.setClickable(false);
        txtReframe.setClickable(false);
        imgInProgress.setClickable(false);
        imgCompleted.setClickable(false);
        imgHidden.setClickable(false);

    }

    private void setAllClickableTrue() {

        txtHabitName.setClickable(true);
        txtFrequencyValue.setClickable(true);
        txtWhenValue.setClickable(true);
        txtWhenValue2.setClickable(true);
        txtHowLongValue.setClickable(true);
        txtHabitReminder.setClickable(true);
        chkSetReminder.setEnabled(true);
        txtWhereNewHabit.setClickable(true);
        txtCueNewAction.setClickable(true);
        txtBeSpecific.setClickable(true);
        txtSeen.setClickable(true);
        txtFunReward.setClickable(true);
        txtMoreAccountable.setClickable(true);
        txtBreakName.setClickable(true);
        txtBreakBeSpecific.setClickable(true);
        txtChangeSomething.setClickable(true);
        txtReframe.setClickable(true);
        imgInProgress.setClickable(true);
        imgCompleted.setClickable(true);
        imgHidden.setClickable(true);

    }


    private void getUserHabitSwapApiCall() {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("HabitId", HABIT_ID);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<GetUserHabitSwapModel> swapModelCall = finisherService.getUserHabitSwap(hashReq);
            swapModelCall.enqueue(new Callback<GetUserHabitSwapModel>() {
                @Override
                public void onResponse(Call<GetUserHabitSwapModel> call, Response<GetUserHabitSwapModel> response) {
                    progressDialog.dismiss();

                    if(response.body()!=null && response.body().getSuccessFlag())
                    {
                        globalHabitSwap = response.body();
                        try {
                            if(globalHabitSwap.getHabitSwap().getNewAction().getPushNotification())
                            {
                                PREVIOUS_SET_NOTIFICATION = "TRUE";
                            }else {
                                PREVIOUS_SET_NOTIFICATION = "FALSE";
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        setAllClickableFalse();
                        setValuesForhabitDetails(response.body().getHabitSwap());
                        Gson gson = new Gson();
                        String detailValues = gson.toJson(globalHabitSwap);
                        HabitHackerEdit habitHackerEdit = new HabitHackerEdit(HABIT_ID,detailValues,false);
                        mDisposable.add(mViewModel.insertUpdateUserName(habitHackerEdit)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("HABIT_INSERT","SUCCESSFULL");
                                },throwable -> {
                                    Log.e("HABIT_INSERT","NOT_SUCCESSFULL");
                                }));
                    }

                }

                @Override
                public void onFailure(Call<GetUserHabitSwapModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else {
            Util.showToast(getActivity(),Util.networkMsg);
        }

    }
    private void setValuesForHabitSave(String HIDDEN_OR_NOT) {

        globalHabitSwap.getHabitSwap().setHabitName(txtHabitName.getText().toString());

        if(txtFrequencyValue.getText().toString().contains(arrFreq[0]))
        {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(1);
        }else if(txtFrequencyValue.getText().toString().contains(arrFreq[1]))
        {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(3);
        }else if(txtFrequencyValue.getText().toString().contains(arrFreq[2]))
        {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(4);
        }else if(txtFrequencyValue.getText().toString().contains(arrFreq[3]))
        {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(5);
        }else if(txtFrequencyValue.getText().toString().contains(arrFreq[4]))
        {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(5);
        }


        if(globalHabitInner != null)
        {
            try {
                if(globalHabitInner.has("Sunday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsSundayTask(globalHabitInner.getBoolean("Sunday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("Monday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsMondayTask(globalHabitInner.getBoolean("Monday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("Tuesday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsTuesdayTask(globalHabitInner.getBoolean("Tuesday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("Wednesday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsWednesdayTask(globalHabitInner.getBoolean("Wednesday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("Thursday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsThursdayTask(globalHabitInner.getBoolean("Thursday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("Friday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsFridayTask(globalHabitInner.getBoolean("Friday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("Saturday"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsSaturdayTask(globalHabitInner.getBoolean("Saturday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }



            try {
                if(globalHabitInner.has("January"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsJanuaryTask(globalHabitInner.getBoolean("January"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("February"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsFebruaryTask(globalHabitInner.getBoolean("February"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("March"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsMarchTask(globalHabitInner.getBoolean("March"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("April"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsAprilTask(globalHabitInner.getBoolean("April"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("May"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsMayTask(globalHabitInner.getBoolean("May"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("June"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsJuneTask(globalHabitInner.getBoolean("June"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("July"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsJulyTask(globalHabitInner.getBoolean("July"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("August"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsAugustTask(globalHabitInner.getBoolean("August"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("September"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsSeptemberTask(globalHabitInner.getBoolean("September"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("October"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsOctoberTask(globalHabitInner.getBoolean("October"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("November"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsNovemberTask(globalHabitInner.getBoolean("November"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if(globalHabitInner.has("December"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsDecemberTask(globalHabitInner.getBoolean("December"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if(globalHabitInner.has("MonthReminder"))
                {
                    globalHabitSwap.getHabitSwap().getNewAction().setTaskMonthReminder(globalHabitInner.getInt("MonthReminder"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        try {
            if (!txtWhenValue.getText().toString().equals("")) {

                Integer totatSecond = 0;
                if(txtWhenValue.getText().toString().equals("NO SET TIME"))
                {
                    totatSecond = 0;
                }else {
                    int hour = 0;
                    int minute = 0;
                    if (txtWhenValue.getText().toString().contains("PM")) {
                        String str = txtWhenValue.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("PM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if(hour != 12)
                        {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);
                    } else {
                        String str = txtWhenValue.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("AM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if(hour == 12)
                        {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);
                    }
                    totatSecond = ((hour * 60) + minute) * 60;
                }

                globalHabitSwap.getHabitSwap().getNewAction().setTaskReminderAt1(totatSecond);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (txtWhenValue2.getVisibility() == View.VISIBLE && !txtWhenValue2.getText().toString().equals("")) {

                Integer totatSecond = 0;
                if(txtWhenValue2.getText().toString().equals("NO SET TIME"))
                {
                    totatSecond = 0;
                }else {
                    int hour = 0;
                    int minute = 0;
                    if (txtWhenValue2.getText().toString().contains("PM")) {
                        String str = txtWhenValue2.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("PM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if(hour != 12)
                        {
                            hour = hour + 12;
                        }
                        minute = Integer.parseInt(strMin);

                    } else {
                        String str = txtWhenValue2.getText().toString();
                        String[] arrTime = str.split(":");
                        String strHour = arrTime[0];
                        String strMinAm = arrTime[1];
                        String[] arrMin = strMinAm.split("AM");
                        String strMin = arrMin[0].trim();
                        hour = Integer.parseInt(strHour);
                        if(hour == 12)
                        {
                            hour = hour +12;
                        }
                        minute = Integer.parseInt(strMin);
                    }
                    totatSecond = ((hour * 60) + minute) * 60;
                }
                globalHabitSwap.getHabitSwap().getNewAction().setTaskReminderAt2(totatSecond);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!txtHowLongValue.getText().toString().equals("")) {
            /*String[] strTime = txtHowLongValue.getText().toString().split("hrs");
            int hour = Integer.parseInt(strTime[0].trim());
            int minute = Integer.parseInt(strTime[1]);
            //int second = Integer.parseInt(strTime[2]);

            int hourToMIN = hour * 60;
            hourToMIN = hourToMIN + minute;
            int minToSecond = hourToMIN * 60;
            int totalSecond = minToSecond;*/

            int hourToMIN = timePickerHour * 60;
            hourToMIN = hourToMIN + timePickerMinute;
            int minToSecond = hourToMIN * 60;
            int totalSecond = minToSecond;

            globalHabitSwap.getHabitSwap().getNewAction().setDuration(totalSecond);

        }

        if (chkSetReminder.isChecked()) {
            if (rootJsonInner == null) {
                Log.e("INNER_FREQUENCY_ID_3",1+">>>>");
                /*globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(1);
                globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(0);
                globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(1);
                globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(43200);
                globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(43200);
                globalHabitSwap.getHabitSwap().getNewAction().setEmail(false);
                globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                globalHabitSwap.getHabitSwap().getNewAction().setSunday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setMonday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setTuesday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setWednesday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setThursday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setFriday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setSaturday(false);
                globalHabitSwap.getHabitSwap().getNewAction().setJanuary(false);
                globalHabitSwap.getHabitSwap().getNewAction().setFebruary(false);
                globalHabitSwap.getHabitSwap().getNewAction().setMarch(false);
                globalHabitSwap.getHabitSwap().getNewAction().setApril(false);
                globalHabitSwap.getHabitSwap().getNewAction().setMay(false);
                globalHabitSwap.getHabitSwap().getNewAction().setJune(false);
                globalHabitSwap.getHabitSwap().getNewAction().setJuly(false);
                globalHabitSwap.getHabitSwap().getNewAction().setAugust(false);
                globalHabitSwap.getHabitSwap().getNewAction().setSeptember(false);
                globalHabitSwap.getHabitSwap().getNewAction().setOctober(false);
                globalHabitSwap.getHabitSwap().getNewAction().setNovember(false);
                globalHabitSwap.getHabitSwap().getNewAction().setDecember(false);*/
            } else {
                try {
                    Log.e("INNER_FREQUENCY_ID_4",rootJsonInner.getInt("FrequencyId")+">>>>");
                    globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                    globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                    globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(rootJsonInner.getInt("ReminderOption"));
                    globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                    globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(rootJsonInner.getInt("ReminderAt2"));
                    globalHabitSwap.getHabitSwap().getNewAction().setEmail(rootJsonInner.getBoolean("Email"));
                    globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                    globalHabitSwap.getHabitSwap().getNewAction().setSunday(rootJsonInner.getBoolean("Sunday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setMonday(rootJsonInner.getBoolean("Monday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setTuesday(rootJsonInner.getBoolean("Tuesday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setWednesday(rootJsonInner.getBoolean("Wednesday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setThursday(rootJsonInner.getBoolean("Thursday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setFriday(rootJsonInner.getBoolean("Friday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setSaturday(rootJsonInner.getBoolean("Saturday"));
                    globalHabitSwap.getHabitSwap().getNewAction().setJanuary(rootJsonInner.getBoolean("January"));
                    globalHabitSwap.getHabitSwap().getNewAction().setFebruary(rootJsonInner.getBoolean("February"));
                    globalHabitSwap.getHabitSwap().getNewAction().setMarch(rootJsonInner.getBoolean("March"));
                    globalHabitSwap.getHabitSwap().getNewAction().setApril(rootJsonInner.getBoolean("April"));
                    globalHabitSwap.getHabitSwap().getNewAction().setMay(rootJsonInner.getBoolean("May"));
                    globalHabitSwap.getHabitSwap().getNewAction().setJune(rootJsonInner.getBoolean("June"));
                    globalHabitSwap.getHabitSwap().getNewAction().setJuly(rootJsonInner.getBoolean("July"));
                    globalHabitSwap.getHabitSwap().getNewAction().setAugust(rootJsonInner.getBoolean("August"));
                    globalHabitSwap.getHabitSwap().getNewAction().setSeptember(rootJsonInner.getBoolean("September"));
                    globalHabitSwap.getHabitSwap().getNewAction().setOctober(rootJsonInner.getBoolean("October"));
                    globalHabitSwap.getHabitSwap().getNewAction().setNovember(rootJsonInner.getBoolean("November"));
                    globalHabitSwap.getHabitSwap().getNewAction().setDecember(rootJsonInner.getBoolean("December"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }else {
            Log.e("INNER_FREQUENCY_ID_5",1+">>>>");
            globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(1);
            globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(0);
            globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(1);
            globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(43200);
            globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(43200);
            globalHabitSwap.getHabitSwap().getNewAction().setEmail(false);
            globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(false);
            globalHabitSwap.getHabitSwap().getNewAction().setSunday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setMonday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setTuesday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setWednesday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setThursday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setFriday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setSaturday(false);
            globalHabitSwap.getHabitSwap().getNewAction().setJanuary(false);
            globalHabitSwap.getHabitSwap().getNewAction().setFebruary(false);
            globalHabitSwap.getHabitSwap().getNewAction().setMarch(false);
            globalHabitSwap.getHabitSwap().getNewAction().setApril(false);
            globalHabitSwap.getHabitSwap().getNewAction().setMay(false);
            globalHabitSwap.getHabitSwap().getNewAction().setJune(false);
            globalHabitSwap.getHabitSwap().getNewAction().setJuly(false);
            globalHabitSwap.getHabitSwap().getNewAction().setAugust(false);
            globalHabitSwap.getHabitSwap().getNewAction().setSeptember(false);
            globalHabitSwap.getHabitSwap().getNewAction().setOctober(false);
            globalHabitSwap.getHabitSwap().getNewAction().setNovember(false);
            globalHabitSwap.getHabitSwap().getNewAction().setDecember(false);
        }

        globalHabitSwap.getHabitSwap().setActionWhere(txtWhereNewHabit.getText().toString());
        globalHabitSwap.getHabitSwap().setCue(txtCueNewAction.getText().toString());
        globalHabitSwap.getHabitSwap().setRoutine(txtBeSpecific.getText().toString());
        globalHabitSwap.getHabitSwap().setMoreSeen(txtSeen.getText().toString());
        globalHabitSwap.getHabitSwap().setReward(txtFunReward.getText().toString());
        globalHabitSwap.getHabitSwap().setAccountabilityNotes(txtMoreAccountable.getText().toString());
        globalHabitSwap.getHabitSwap().getSwapAction().setName(txtBreakName.getText().toString());
        globalHabitSwap.getHabitSwap().setOldActionBreak(txtBreakBeSpecific.getText().toString());
        globalHabitSwap.getHabitSwap().setNoteHidden(txtChangeSomething.getText().toString());
        globalHabitSwap.getHabitSwap().setNoteHorrible(txtReframe.getText().toString());

        if(!HIDDEN_OR_NOT.equals("") || boolHiddenIsClicked)
        {
            globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(false);
        }

        saveNewBreakHabit();

    }

    private void setValuesForhabitDetails(HabitSwap habitSwap) {

        try {

            if(habitSwap.getNewAction().getPushNotification())
            {
                chkSetReminder.setChecked(true);
                txtHabitReminder.setText("View Reminder");
            }else {
                chkSetReminder.setChecked(false);
                txtHabitReminder.setText("Reminder");
            }

            txtHabitName.setText(habitSwap.getHabitName());

            if(habitSwap.getActionWhere()!=null)
            {
                txtWhereNewHabit.setText(habitSwap.getActionWhere());
            }else {
                txtWhereNewHabit.setText("");
            }

            if(habitSwap.getCue()!=null)
            {
                txtCueNewAction.setText(habitSwap.getCue());
            }else {
                txtCueNewAction.setText("");
            }

            if(habitSwap.getRoutine()!=null)
            {
                txtBeSpecific.setText(habitSwap.getRoutine());
            }else {
                txtBeSpecific.setText("");
            }

            if(habitSwap.getMoreSeen()!=null)
            {
                txtSeen.setText(habitSwap.getMoreSeen());
            }else {
                txtSeen.setText("");
            }

            if(habitSwap.getReward()!=null)
            {
                txtFunReward.setText(habitSwap.getReward());
            }else {
                txtFunReward.setText("");
            }

            if(habitSwap.getAccountabilityNotes()!=null)
            {
                txtMoreAccountable.setText(habitSwap.getAccountabilityNotes());
            }else {
                txtMoreAccountable.setText("");
            }

            if(habitSwap.getSwapAction().getName()!=null)
            {
                txtBreakName.setText(habitSwap.getSwapAction().getName());
            }else {
                txtBreakName.setText("");
            }

            if(habitSwap.getOldActionBreak()!=null)
            {
                txtBreakBeSpecific.setText(habitSwap.getOldActionBreak());
            }else {
                txtBreakBeSpecific.setText("");
            }

            if(habitSwap.getNoteHidden()!=null)
            {
                txtChangeSomething.setText(habitSwap.getNoteHidden());
            }else {
                txtChangeSomething.setText("");
            }

            if(habitSwap.getNoteHorrible()!=null)
            {
                txtReframe.setText(habitSwap.getNoteHorrible());
            }else {
                txtReframe.setText("");
            }

            if(habitSwap.getStatus() == HABIT_STATUS_ACTIVE)
            {
                imgInProgress.setImageResource(R.drawable.mbhq_ic_tick);
                boolHiddenIsClicked = false;
            }else if(habitSwap.getStatus() == HABIT_STATUS_COMPLETED)
            {
                imgCompleted.setImageResource(R.drawable.mbhq_ic_tick);
                boolHiddenIsClicked = false;
            }else if(habitSwap.getStatus() == HABIT_STATUS_SNOOZE)
            {
                imgHidden.setImageResource(R.drawable.mbhq_ic_tick);
                boolHiddenIsClicked = true;
            }

    /*if(getArguments() != null && (getArguments().containsKey("FROM_SIMPLE_HABIT_LIST") || getArguments().containsKey("FROM_HABIT_LIST")))
    {
        imgEditHabit.performClick();
    }*/
            imgEditHabit.performClick();


            //txtFrequencyValue.setText(arrFreq[habitSwap.getNewAction().getTaskFrequencyTypeId()-1]);
            if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 1){
                txtFrequencyValue.setText(arrFreq[0]);
            }else if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 3){
                String strValue = arrFreq[1] + " (";
                if(habitSwap.getNewAction().getIsSundayTask())
                {
                    strValue = strValue + "Sunday ";
                }
                if(habitSwap.getNewAction().getIsMondayTask())
                {
                    strValue = strValue + "Monday ";
                }
                if(habitSwap.getNewAction().getIsTuesdayTask())
                {
                    strValue = strValue + "Tuesday ";
                }
                if(habitSwap.getNewAction().getIsWednesdayTask())
                {
                    strValue = strValue + "Wednesday ";
                }
                if(habitSwap.getNewAction().getIsThursdayTask())
                {
                    strValue = strValue + "Thursday ";
                }
                if(habitSwap.getNewAction().getIsFridayTask())
                {
                    strValue = strValue + "Friday ";
                }
                if(habitSwap.getNewAction().getIsSaturdayTask())
                {
                    strValue = strValue + "Saturday ";
                }
                strValue = strValue + ")";
                txtFrequencyValue.setText(strValue);
            }else if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 4){
                String strValue = arrFreq[2] + " (";
                if(habitSwap.getNewAction().getIsSundayTask())
                {
                    strValue = strValue + "Sunday ";
                }
                if(habitSwap.getNewAction().getIsMondayTask())
                {
                    strValue = strValue + "Monday ";
                }
                if(habitSwap.getNewAction().getIsTuesdayTask())
                {
                    strValue = strValue + "Tuesday ";
                }
                if(habitSwap.getNewAction().getIsWednesdayTask())
                {
                    strValue = strValue + "Wednesday ";
                }
                if(habitSwap.getNewAction().getIsThursdayTask())
                {
                    strValue = strValue + "Thursday ";
                }
                if(habitSwap.getNewAction().getIsFridayTask())
                {
                    strValue = strValue + "Friday ";
                }
                if(habitSwap.getNewAction().getIsSaturdayTask())
                {
                    strValue = strValue + "Saturday ";
                }
                strValue = strValue + ")";
                txtFrequencyValue.setText(strValue);
            }else if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 5){
                String strValue = arrFreq[3] + " (" + habitSwap.getNewAction().getTaskMonthReminder() + " Day of Month )";
                txtFrequencyValue.setText(strValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Integer totalSecond = habitSwap.getNewAction().getTaskReminderAt1();
            if(totalSecond == 0)
            {
                txtWhenValue.setText("NA");
            }else {
                Integer second = totalSecond % 60;
                Integer totalMinute = totalSecond / 60;
                Integer minute = totalMinute % 60;
                Integer hour = totalMinute / 60;
                String strTime ="";
                boolean boolPm = false;
                if(hour == 12)
                {
                    hour = 12;
                    boolPm = true;
                }else if(hour>12)
                {
                    hour = hour - 12;
                    boolPm = true;
                }

                strTime = pad(hour) + ":" + pad(minute);

                if(boolPm)
                {
                    strTime = strTime + " PM";
                }else {
                    strTime = strTime + " AM";
                }
                txtWhenValue.setText(strTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 2)
            {
                llForTwiceDaily.setVisibility(View.VISIBLE);

                Integer totalSecond2 = habitSwap.getNewAction().getTaskReminderAt2();

                if(totalSecond2 == 0)
                {
                    txtWhenValue2.setText("NA");
                }else {
                    Integer second2 = totalSecond2 % 60;
                    Integer totalMinute2 = totalSecond2 / 60;
                    Integer minute2 = totalMinute2 % 60;
                    Integer hour2 = totalMinute2 / 60;
                    String strTime2 ="";
                    boolean boolPm2 = false;
                    if(hour2 == 12)
                    {
                        hour2 = 12;
                        boolPm2 = true;
                    }else if(hour2>12)
                    {
                        hour2 = hour2 - 12;
                        boolPm2 = true;
                    }

                    strTime2 = pad(hour2) + ":" + pad(minute2);

                    if(boolPm2)
                    {
                        strTime2 = strTime2 + " PM";
                    }else {
                        strTime2 = strTime2 + " AM";
                    }
                    txtWhenValue2.setText(strTime2);
                }
            }else {
                llForTwiceDaily.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Integer hwLongSecondTotal = habitSwap.getNewAction().getDuration();
            if(hwLongSecondTotal == 0)
            {
                txtHowLongValue.setText("NA");
            }else {
                Integer hwLongSecond = hwLongSecondTotal % 60;
                Integer hwLongMinuteTotal = hwLongSecondTotal / 60;
                Integer hwLongMinute = hwLongMinuteTotal % 60;
                Integer hwLongHour = hwLongMinuteTotal / 60;

                timePickerHour = hwLongHour;
                timePickerMinute = hwLongMinute;

                txtHowLongValue.setText(hwLongHour + " hrs " + hwLongMinute + " mins");
            }
        } catch (Exception e) {
            e.printStackTrace();
            txtHowLongValue.setText("00 hrs 00 mins");
        }


    }

    private void saveNewBreakHabit() {
        CurrentDayTask currentDayTask=new CurrentDayTask();
        currentDayTask.setIsDone(globalBundle.getBoolean("new_action"));
        CurrentDayTask currentDayTask1=new CurrentDayTask();
        currentDayTask1.setIsDone(globalBundle.getBoolean("swap_action"));
        Log.i("lstHabitToday1111111111111111","10");

        globalHabitSwap.getHabitSwap().getNewAction().setCurrentDayTask(currentDayTask);
        Log.i("lstHabitToday1111111111111111","11");

        globalHabitSwap.getHabitSwap().getNewAction().setCurrentDayTask2(currentDayTask);
        Log.i("lstHabitToday1111111111111111","12");

        globalHabitSwap.getHabitSwap().getSwapAction().setCurrentDayTask(currentDayTask1);
        Log.i("lstHabitToday1111111111111111","13");

        globalHabitSwap.getHabitSwap().getNewAction().setCurrentDayTask2(currentDayTask1);
        Log.i("lstHabitToday1111111111111111","14");


        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Habit",globalHabitSwap.getHabitSwap());
            Log.e("req","reqdata"+new Gson().toJson(hashReq));
            Call<JsonObject> jsonObjectCall = finisherService.addUpdateHabitSwap(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if(response.body()!=null && response.body().get("SuccessFlag").getAsBoolean())
                    {
                        if(response.code()==500){
                            Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_SHORT);
                            return;
                        }
                        Constants.clicknotification=false;
                        Util.showToast(getActivity(),"Successfully Saved");
                        if(chkSetReminder.isChecked())
                        {
                            /*commented by sahenita temporary*/
                            SetLocalNotificationForHabit.setNotificationForHabit(globalHabitSwap.getHabitSwap(),getActivity());

                          //  SetLocalNotificationForHabit.setAlarm(getActivity());

                        }else {
                            /*commented by sahenita temporary*/
                            try {
                                AlarmManager am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
                                Intent intent = new Intent(getContext(), AlarmReceiver.class);
                                intent.setAction(globalHabitSwap.getHabitSwap().getNewAction().getId()+"HABIT");
                                PendingIntent sender = PendingIntent.getBroadcast(getActivity(),0, intent, PendingIntent.FLAG_NO_CREATE);
                                am.cancel(sender);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        Gson gson = new Gson();
                        String detailValues = gson.toJson(globalHabitSwap);
                        HabitHackerEdit habitHackerEdit = new HabitHackerEdit(HABIT_ID,detailValues,false);

                        Completable.fromAction(() -> {
                                    mViewModel.deleteByHabitId(HABIT_ID);
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("DATABASE","DELETE_SUCCESSFULL");
                                    Completable.fromAction(() -> {
                                                mViewModelCalendar.deleteHabitCalendarById(HABIT_ID);
                                            }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                // Util.showToast(getActivity(),Util.backtotodayhabit+"goto");
                                                if("yes".equalsIgnoreCase( Util.backtotodayhabit)){
                                                    Util.backtotodayhabit="";
                                                    ArrayList<HabitSwap> habitSwapList = new ArrayList<>();

                                                    //habitSwapList.clear();
                                                    sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                                                    if(null!=getActivity()){
                                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                                    }
                                                }else{
                                                    Util.isReloadTodayMainPage = true;
                                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                    ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                                }

                                            },throwable -> {
                                                // Util.showToast(getActivity(),Util.backtotodayhabit+"goto");
                                                if("yes".equalsIgnoreCase( Util.backtotodayhabit)){
                                                    Util.backtotodayhabit="";
                                                    ArrayList<HabitSwap> habitSwapList = new ArrayList<>();

                                                    //habitSwapList.clear();
                                                    sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                                                    if(null!=getActivity()){
                                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                                    }
                                                }else{
                                                    Util.isReloadTodayMainPage = true;
                                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                    ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                                }

                                            });
                                },throwable -> {
                                    //  Util.showToast(getActivity(),Util.backtotodayhabit+"goto");
                                    if("yes".equalsIgnoreCase( Util.backtotodayhabit)){
                                        Util.backtotodayhabit="";
                                        ArrayList<HabitSwap> habitSwapList = new ArrayList<>();

//                                        habitSwapList.clear();
                                        sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                                        if(null!=getActivity()){
                                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                        }
                                    }else{
                                        Log.e("DATABASE","DELETE_UN_SUCCESSFULL");
                                        Util.isReloadTodayMainPage = true;
                                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                        ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                    }

                                });
                    }else{
                        Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Constants.clicknotification=false;
                    Toast.makeText(getContext(),"Something Went Wrong",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });

        }

    }



    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).funTabBarforHabits();
      //  ((MainActivity) getActivity()).funDrawer();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    if(getArguments()!=null && getArguments().containsKey("NOTIFICATIONID"))
                    {
                        ((MainActivity)getActivity()).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",null);
                    }else {
                        if(getArguments()!=null && getArguments().containsKey("FROM_SIMPLE_HABIT_LIST"))
                        {
                            ((MainActivity)getActivity()).loadFragment(new MbhqTodayTwoFragment(),"MbhqTodayTwo",null);
                        }else if(getArguments()!=null && getArguments().containsKey("FROM_HABIT_LIST"))
                        {
                            ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                        }
                        else {
                            ((MainActivity)getActivity()).loadFragment(new HabbitDetailsCalendarFragment(),"HabbitDetailsCalendar",globalBundle);
                        }
                    }

                    return true;

                }

                return false;
            }
        });
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
        frameNotification.setVisibility(View.GONE);
        imgFilter.setVisibility(View.GONE);
        imgFilter.setBackgroundResource(R.drawable.mbhq_filter);
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
                ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendar", globalBundle);
            }
        });
    }

    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
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
            if(TYPE.equals("FIRST"))
            {
                txtWhenValue.setText("NO SET TIME");
            }else {
                txtWhenValue2.setText("NO SET TIME");
            }
        });

        rlSetTime.setOnClickListener(view -> {
            dialog.dismiss();
            if(TYPE.equals("FIRST"))
            {
                TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtWhenValue, "", "");
            }else {
                TimePickerControllerWithTextView pickerController = new TimePickerControllerWithTextView(getActivity(), txtWhenValue2, "", "");
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
            txtHowLongValue.setText("NO SET TIME");
            timePickerHour = 0;
            timePickerMinute = 0;
        });

        rlSetTime.setOnClickListener(view -> {
            dialog.dismiss();
            MyTimePickerDialog mTimePicker = new MyTimePickerDialog(getActivity(), new MyTimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                    // TODO Auto-generated method stub
                    txtHowLongValue.setText(String.format("%02d", hourOfDay) + " hrs " + String.format("%02d", minute) + " mins"/*+ ":" + String.format("%02d", seconds)*/);
                    timePickerHour = hourOfDay;
                    timePickerMinute = minute;
                }
            }, 0, 0, 0, true);
            mTimePicker.show();
        });

        dialog.show();
    }


    private void openpopup() {

        if(true== Constants.clicknotification){

//    if(!txtHabitName.getText().toString().equals(""))
//    {
            txtHabitReminder.setText("View Reminder");
            chkSetReminder.setChecked(true);
            CustomReminderDialogForHabitOtherReminder customReminderDialog = new CustomReminderDialogForHabitOtherReminder();
            Gson gson1=new Gson();
            Bundle bundle=new Bundle();
            bundle.putString("MODEL",gson1.toJson(globalHabitSwap.getHabitSwap().getNewAction()));
            bundle.putString("MODELNAME","GRATITUDE");
            bundle.putBoolean("PUSH_NOTIFICATION",globalHabitSwap.getHabitSwap().getNewAction().getPushNotification());
            customReminderDialog.setArguments(bundle);
            customReminderDialog.mListener= new CustomReminderDialogForHabitOtherReminder.onSubmitListener() {
                @Override
                public void setOnSubmitListener(JSONObject arg) {

                    if(arg != null)
                    {
                        Log.e("print jj",arg.toString()+"?");
                        dialogOpenOnceForEdit=true;
                        globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                        rootJsonInner=arg;

                        try {
                            Log.e("INNER_FREQUENCY_ID_2",rootJsonInner.getInt("FrequencyId")+">>>>");
                            globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                            globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                            globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(rootJsonInner.getInt("ReminderOption"));
                            globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                            globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                            globalHabitSwap.getHabitSwap().getNewAction().setEmail(rootJsonInner.getBoolean("Email"));
                            globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                            globalHabitSwap.getHabitSwap().getNewAction().setSunday(rootJsonInner.getBoolean("Sunday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setMonday(rootJsonInner.getBoolean("Monday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setTuesday(rootJsonInner.getBoolean("Tuesday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setWednesday(rootJsonInner.getBoolean("Wednesday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setThursday(rootJsonInner.getBoolean("Thursday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setFriday(rootJsonInner.getBoolean("Friday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setSaturday(rootJsonInner.getBoolean("Saturday"));
                            globalHabitSwap.getHabitSwap().getNewAction().setJanuary(rootJsonInner.getBoolean("January"));
                            globalHabitSwap.getHabitSwap().getNewAction().setFebruary(rootJsonInner.getBoolean("February"));
                            globalHabitSwap.getHabitSwap().getNewAction().setMarch(rootJsonInner.getBoolean("March"));
                            globalHabitSwap.getHabitSwap().getNewAction().setApril(rootJsonInner.getBoolean("April"));
                            globalHabitSwap.getHabitSwap().getNewAction().setMay(rootJsonInner.getBoolean("May"));
                            globalHabitSwap.getHabitSwap().getNewAction().setJune(rootJsonInner.getBoolean("June"));
                            globalHabitSwap.getHabitSwap().getNewAction().setAugust(rootJsonInner.getBoolean("August"));
                            globalHabitSwap.getHabitSwap().getNewAction().setSeptember(rootJsonInner.getBoolean("September"));
                            globalHabitSwap.getHabitSwap().getNewAction().setOctober(rootJsonInner.getBoolean("October"));
                            globalHabitSwap.getHabitSwap().getNewAction().setNovember(rootJsonInner.getBoolean("November"));
                            globalHabitSwap.getHabitSwap().getNewAction().setDecember(rootJsonInner.getBoolean("December"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else {
                        chkSetReminder.setChecked(false);
                        globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(false);
                    }
                }
            };
            customReminderDialog.show(getFragmentManager(), "");
   /* }else {
        //Toast.makeText(getActivity(),"Please enter Habit name",Toast.LENGTH_SHORT).show();
    }*/


        }


    }

    private void openpopup1() {

        txtHabitReminder.setText("View Reminder");
        chkSetReminder.setChecked(true);
        CustomReminderDialogForHabitOtherReminder customReminderDialog = new CustomReminderDialogForHabitOtherReminder();
        Gson gson1=new Gson();
        Bundle bundle=new Bundle();
        bundle.putString("MODEL",gson1.toJson(globalHabitSwap.getHabitSwap().getNewAction()));
        bundle.putString("MODELNAME","GRATITUDE");
        bundle.putBoolean("PUSH_NOTIFICATION",globalHabitSwap.getHabitSwap().getNewAction().getPushNotification());
        customReminderDialog.setArguments(bundle);
        customReminderDialog.mListener= new CustomReminderDialogForHabitOtherReminder.onSubmitListener() {
            @Override
            public void setOnSubmitListener(JSONObject arg) {

                if(arg != null)
                {
                    Log.e("print jj",arg.toString()+"?");
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    try {
                        Log.e("SET_PUSH", "setOnSubmitListener: " + arg.getBoolean("PushNotification"));
                        if(!arg.getBoolean("PushNotification")){
                            chkSetReminder.setChecked(false); ////////////
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    /////////////////////////////////////////////////////////////////////////////////////////////
                    dialogOpenOnceForEdit=true;
                    globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(true);
                    rootJsonInner=arg;

                    try {
                        Log.e("INNER_FREQUENCY_ID_2",rootJsonInner.getInt("FrequencyId")+">>>>");
                        globalHabitSwap.getHabitSwap().getNewAction().setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                        globalHabitSwap.getHabitSwap().getNewAction().setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                        globalHabitSwap.getHabitSwap().getNewAction().setReminderOption(rootJsonInner.getInt("ReminderOption"));
                        globalHabitSwap.getHabitSwap().getNewAction().setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                        globalHabitSwap.getHabitSwap().getNewAction().setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                        globalHabitSwap.getHabitSwap().getNewAction().setEmail(rootJsonInner.getBoolean("Email"));
                        globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                        globalHabitSwap.getHabitSwap().getNewAction().setSunday(rootJsonInner.getBoolean("Sunday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setMonday(rootJsonInner.getBoolean("Monday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setTuesday(rootJsonInner.getBoolean("Tuesday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setWednesday(rootJsonInner.getBoolean("Wednesday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setThursday(rootJsonInner.getBoolean("Thursday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setFriday(rootJsonInner.getBoolean("Friday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setSaturday(rootJsonInner.getBoolean("Saturday"));
                        globalHabitSwap.getHabitSwap().getNewAction().setJanuary(rootJsonInner.getBoolean("January"));
                        globalHabitSwap.getHabitSwap().getNewAction().setFebruary(rootJsonInner.getBoolean("February"));
                        globalHabitSwap.getHabitSwap().getNewAction().setMarch(rootJsonInner.getBoolean("March"));
                        globalHabitSwap.getHabitSwap().getNewAction().setApril(rootJsonInner.getBoolean("April"));
                        globalHabitSwap.getHabitSwap().getNewAction().setMay(rootJsonInner.getBoolean("May"));
                        globalHabitSwap.getHabitSwap().getNewAction().setJune(rootJsonInner.getBoolean("June"));
                        globalHabitSwap.getHabitSwap().getNewAction().setAugust(rootJsonInner.getBoolean("August"));
                        globalHabitSwap.getHabitSwap().getNewAction().setSeptember(rootJsonInner.getBoolean("September"));
                        globalHabitSwap.getHabitSwap().getNewAction().setOctober(rootJsonInner.getBoolean("October"));
                        globalHabitSwap.getHabitSwap().getNewAction().setNovember(rootJsonInner.getBoolean("November"));
                        globalHabitSwap.getHabitSwap().getNewAction().setDecember(rootJsonInner.getBoolean("December"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    chkSetReminder.setChecked(false);
                    globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(false);
                }
            }
        };
        customReminderDialog.show(getFragmentManager(), "");


    }


}