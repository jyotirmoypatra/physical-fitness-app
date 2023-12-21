package com.ashysystem.mbhq.fragment.habit_hacker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.adapter.HabitBreakYearAdapter;
import com.ashysystem.mbhq.adapter.HabitQuarterBreakAdapter;
import com.ashysystem.mbhq.adapter.HabitQuaterAdapter;
import com.ashysystem.mbhq.adapter.HabitYearAdapter;
import com.ashysystem.mbhq.fragment.MbhqTodayMainFragment;
import com.ashysystem.mbhq.fragment.MbhqTodayTwoFragment;
import com.ashysystem.mbhq.model.habit_hacker.GetUserHabitSwapModel;
import com.ashysystem.mbhq.model.habit_hacker.HabbitCalendarModel;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.entity.HabitCalendarEntity;
import com.ashysystem.mbhq.roomDatabase.entity.HabitHackerEdit;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactory;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForHabitCalendar;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitCalendarViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitEditViewModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.Constants;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabbitDetailsCalendarFragment extends Fragment {
    LinearLayout llCalendar1, llCalendar2;
    Calendar globalCalendar = null;
    private ImageView imgBack, imgForward;
    private TextView txtMonthName, txtMonthStat, txtMonthStatBreak, txtQuaterHabbitName, txtQuaterBreakName, txtYearHabbitHeader, txtYearBreakHeader, txtRightHabbit, txtRightBreak, txtLeftBreak, txtLeftHabbit, txtNoData, txtRightHabbitQ1, txtRightHabbitQ2, txtRightHabbitQ3, txtRightHabbitQ4, txtRightBreakQ1, txtRightBreakQ2, txtRightBreakQ3, txtRightBreakQ4;
    private int globalMonthIndex = 0;
    ProgressDialog progressDialog;
    SharedPreference sharedPreference;
    FinisherServiceImpl finisherService;
    private HabbitCalendarModel globalList;
    Double statMonthlyHabbit;
    Double statMonthlyHabbitBreak;
    ArrayList<Double> arrWeeklyStatHabbit = new ArrayList<>();
    ArrayList<Double> arrWeeklyStatHabbitBreak = new ArrayList<>();
    private int habbitId = 0;
    private String habbitName = "", breakName = "";
    private TextView txtBreakHeader, txtHabbitHeader;
    private RelativeLayout rlBack;
    private ImageView imgFilter;
    int filterSelectedvalue = 0;
    boolean boolDateSelected = false, boolToday = false, boolThisMonth = false, boolSixMonth = false, boolOneYear = false;
    private RelativeLayout rlCalendarView, rlYearlyView, rlQuaterView, rlQuaterVal1, rlQuaterVal2, rlQuaterVal3, rlQuaterVal4, rlQuaterBreakVal1, rlQuaterBreakVal2, rlQuaterBreakVal3, rlQuaterBreakVal4, rlAction;
    private int myMonth = 0, myYear = 0;
    View view = null;
    View globalView;
    int userId;
    private static int counter = 1;
    Integer HABIT_ID;

    Toolbar toolbar;
    Bundle globalBundle;
    RelativeLayout rlHabitDetails;

    ImageView imgEditPencil, imgDeleteBtn, notifyBtn;
    boolean notificationActive = false;
    RecyclerView recyclerQuarterHabit, recyclerQuarterBreak, recyclerYearHabit, recyclerYearBreak;
    public HashMap<Integer, String> hashQuarterValue;

    private ViewModelFactoryForHabitCalendar mViewModelFactory;
    private HabitCalendarViewModel mViewModel;

    //added by jyotirmoy 29.09.22
    GetUserHabitSwapModel globalHabitSwap;
    private boolean boolHiddenIsClicked = false;
    TextView txtHabitName, txtFrequencyValue, txtWhenValue, txtWhenValue2, txtHowLongValue, txtWhereNewHabit, txtCueNewAction, txtBeSpecific, txtSeen, txtFunReward, txtMoreAccountable, txtBreakName, txtBreakBeSpecific, txtChangeSomething, txtReframe;
    String[] arrFreq = {"Daily"/*,"Twice Daily"*/, "Weekly", "Fortnightly", "Monthly"/*,"Yearly"*/};
    JSONObject globalHabitInner = null;
    JSONObject rootJsonInner;
    Integer timePickerHour = 0;
    Integer timePickerMinute = 0;
    CheckBox chkSetReminder;
    String PREVIOUS_SET_NOTIFICATION = "";
    Integer HABIT_STATUS_ALL = 0;
    Integer HABIT_STATUS_ACTIVE = 1;
    Integer HABIT_STATUS_COMPLETED = 2;
    Integer HABIT_STATUS_SNOOZE = 3;
    Integer HABIT_RESTART_FROM_TODAY = 4;
    private HabitEditViewModel mViewEdit;
    private ViewModelFactory mViewEditModelFactory;
    private boolean push_notification = false;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    Integer TASK_FREQUENCY = null;

    private boolean FROM_NOTIFICATION = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            globalBundle = getArguments();
            if (getArguments().containsKey("id")) {
                HABIT_ID = getArguments().getInt("id");
            }
            if (getArguments().containsKey("NOTIFICATIONID")) {
                HABIT_ID = getArguments().getInt("NOTIFICATIONID");
            }

            Log.e("HABIT_ID", HABIT_ID + ">>>>>>>>");
        }

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_habbit_details_calendar, container, false);
            sharedPreference = new SharedPreference(getActivity());
            finisherService = new FinisherServiceImpl(getActivity());
            userId = Integer.parseInt(sharedPreference.read("UserID", ""));
            /*if(!sharedPreference.read("habbitid","").equals(""))
              habbitId=Integer.parseInt(sharedPreference.read("habbitid",""));
            if(!sharedPreference.read("break_name","").equals(""))
                breakName=sharedPreference.read("break_name","");
            if(!sharedPreference.read("habbit_name","").equals(""))
                habbitName=sharedPreference.read("habbit_name","");*/
            if (getArguments() != null) {
                globalBundle = getArguments();
                if (getArguments().containsKey("id")) {
                    habbitId = getArguments().getInt("id");
                }
                if (getArguments().containsKey("habbit_name")) {
                    habbitName = getArguments().getString("habbit_name");
                }
                if (getArguments().containsKey("break_name")) {
                    breakName = getArguments().getString("break_name");
                }else{
                    breakName="";
                }
                if (getArguments().containsKey("id")) {
                    habbitId = getArguments().getInt("id");
                }
            //    Log.e("PUSHHHH_IMGTAG", "initview: " + getArguments().containsKey("push_notification"));
                if (getArguments().containsKey("push_notification")) {
                    push_notification = getArguments().getBoolean("push_notification");
                    Log.e("PUSHHHH_IMGTAGDATA", "initview: " + push_notification);
                }
                if (getArguments().containsKey("from_notification")) {
                    FROM_NOTIFICATION = getArguments().getBoolean("from_notification");
                }
                if (getArguments().containsKey("TASK_FREQUENCY")) {
                    TASK_FREQUENCY = getArguments().getInt("TASK_FREQUENCY");
                }
            }

            mViewModelFactory = Injection.provideViewModelFactoryHabitCalendar(getActivity());
            mViewModel =new ViewModelProvider(this, mViewModelFactory).get(HabitCalendarViewModel.class);


            hashQuarterValue = new HashMap<>();
            hashQuarterValue.put(1, "JANUARY - MARCH");
            hashQuarterValue.put(2, "APRIL - JUNE");
            hashQuarterValue.put(3, "JULY - SEPTEMBER");
            hashQuarterValue.put(4, "OCTOBER - DECEMBER");

            mViewEditModelFactory = Injection.provideViewModelFactory(getActivity());
           // mViewEdit = ViewModelProviders.of(this, mViewEditModelFactory).get(HabitEditViewModel.class);
            mViewEdit =new ViewModelProvider(this, mViewEditModelFactory).get(HabitEditViewModel.class);
            getUserHabitSwapApiCall();

            initview(view);
            funToolBar();
           /*HashMap<Integer,String> hashMapData=((MainActivity)getActivity()).getHashMapHabbit();
            if(hashMapData.containsKey(habbitId))
            {
                String data=hashMapData.get(habbitId);
                Gson gson = new Gson();
                globalList = gson.fromJson(data, HabbitCalendarModel.class);
                setData();
            }
            else
            {
                GetHabitStatsApi();
            }*/
            GetHabitStatsApi();
/*
            mDisposable.add(mViewModel.getCountForHabitCalendar(habbitId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if (integer == 0) {
                            Log.i("popcalenderdetails","0");
                            GetHabitStatsApi();
                        } else {
                            mDisposable.add(mViewModel.getHabitCalendar(habbitId)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(habitCalendarEntity -> {
                                        Gson gson = new Gson();
                                        globalList = gson.fromJson(habitCalendarEntity.getHabitDetails(), HabbitCalendarModel.class);
                                        Log.i("popcalenderdetails","1");
                                        setData();
                                    }, throwable -> {
                                        Log.e("DAYABASE", "CALENDAR_FAIL2");
                                    }));
                        }
                    }, throwable -> {
                        Log.e("DAYABASE", "CALENDAR_FAIL1");
                    }));
*/




            return view;
        } else {
            funToolBar();
            return view;
        }

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
                        Log.e("calReq","reqDataRev"+new Gson().toJson(response));
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
                       // setAllClickableFalse();
                        setValuesForhabitDetails(response.body().getHabitSwap());
                        Gson gson = new Gson();
                        String detailValues = gson.toJson(globalHabitSwap);
                        HabitHackerEdit habitHackerEdit = new HabitHackerEdit(HABIT_ID,detailValues,false);
                        mDisposable.add(mViewEdit.insertUpdateUserName(habitHackerEdit)
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

    private void setValuesForhabitDetails(HabitSwap habitSwap) {

        try {
            //txtFrequencyValue.setText(arrFreq[habitSwap.getNewAction().getTaskFrequencyTypeId()-1]);
            if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 1){
               // txtFrequencyValue.setText(arrFreq[0]);
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
              //  txtFrequencyValue.setText(strValue);
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
               // txtFrequencyValue.setText(strValue);
            }else if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 5){
                String strValue = arrFreq[3] + " (" + habitSwap.getNewAction().getTaskMonthReminder() + " Day of Month )";
              //  txtFrequencyValue.setText(strValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Integer totalSecond = habitSwap.getNewAction().getTaskReminderAt1();
            if(totalSecond == 0)
            {
               // txtWhenValue.setText("NA");
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
             //   txtWhenValue.setText(strTime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            if(habitSwap.getNewAction().getTaskFrequencyTypeId() == 2)
            {
               // llForTwiceDaily.setVisibility(View.VISIBLE);

                Integer totalSecond2 = habitSwap.getNewAction().getTaskReminderAt2();

                if(totalSecond2 == 0)
                {
                   // txtWhenValue2.setText("NA");
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
                    //txtWhenValue2.setText(strTime2);
                }
            }else {
              //  llForTwiceDaily.setVisibility(View.GONE); //addlater
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Integer hwLongSecondTotal = habitSwap.getNewAction().getDuration();
            if(hwLongSecondTotal == 0)
            {
               // txtHowLongValue.setText("NA");
            }else {
                Integer hwLongSecond = hwLongSecondTotal % 60;
                Integer hwLongMinuteTotal = hwLongSecondTotal / 60;
                Integer hwLongMinute = hwLongMinuteTotal % 60;
                Integer hwLongHour = hwLongMinuteTotal / 60;

                timePickerHour = hwLongHour;
                timePickerMinute = hwLongMinute;

               // txtHowLongValue.setText(hwLongHour + " hrs " + hwLongMinute + " mins");
            }
        } catch (Exception e) {
            e.printStackTrace();
           // txtHowLongValue.setText("00 hrs 00 mins");
        }

        if(habitSwap.getNewAction().getPushNotification())
        {
           // chkSetReminder.setChecked(true);
           // txtHabitReminder.setText("View Reminder");
        }else {
          //  chkSetReminder.setChecked(false);
          //  txtHabitReminder.setText("Reminder");
        }

       // txtHabitName.setText(habitSwap.getHabitName());

        if(habitSwap.getActionWhere()!=null)
        {
           // txtWhereNewHabit.setText(habitSwap.getActionWhere());
        }else {
           // txtWhereNewHabit.setText("");
        }

        if(habitSwap.getCue()!=null)
        {
          //  txtCueNewAction.setText(habitSwap.getCue());
        }else {
            //txtCueNewAction.setText("");
        }

        if(habitSwap.getRoutine()!=null)
        {
           // txtBeSpecific.setText(habitSwap.getRoutine());
        }else {
           // txtBeSpecific.setText("");
        }

        if(habitSwap.getMoreSeen()!=null)
        {
            //txtSeen.setText(habitSwap.getMoreSeen());
        }else {
          //  txtSeen.setText("");
        }

        if(habitSwap.getReward()!=null)
        {
           // txtFunReward.setText(habitSwap.getReward());
        }else {
           // txtFunReward.setText("");
        }

        if(habitSwap.getAccountabilityNotes()!=null)
        {
           // txtMoreAccountable.setText(habitSwap.getAccountabilityNotes());
        }else {
           // txtMoreAccountable.setText("");
        }

        if(habitSwap.getSwapAction().getName()!=null)
        {
            //txtBreakName.setText(habitSwap.getSwapAction().getName());
        }else {
           // txtBreakName.setText("");
        }

        if(habitSwap.getOldActionBreak()!=null)
        {
           // txtBreakBeSpecific.setText(habitSwap.getOldActionBreak());
        }else {
           // txtBreakBeSpecific.setText("");
        }

        if(habitSwap.getNoteHidden()!=null)
        {
           // txtChangeSomething.setText(habitSwap.getNoteHidden());
        }else {
            //txtChangeSomething.setText("");
        }

        if(habitSwap.getNoteHorrible()!=null)
        {
           // txtReframe.setText(habitSwap.getNoteHorrible());
        }else {
           // txtReframe.setText("");
        }

        if(habitSwap.getStatus() == HABIT_STATUS_ACTIVE)
        {
           // imgInProgress.setImageResource(R.drawable.mbhq_ic_tick);
            boolHiddenIsClicked = false;
        }else if(habitSwap.getStatus() == HABIT_STATUS_COMPLETED)
        {
           // imgCompleted.setImageResource(R.drawable.mbhq_ic_tick);
            boolHiddenIsClicked = false;
        }else if(habitSwap.getStatus() == HABIT_STATUS_SNOOZE)
        {
            //imgHidden.setImageResource(R.drawable.mbhq_ic_tick);
            boolHiddenIsClicked = true;
        }

        /*if(getArguments() != null && (getArguments().containsKey("FROM_SIMPLE_HABIT_LIST") || getArguments().containsKey("FROM_HABIT_LIST")))
        {
            imgEditHabit.performClick();
        }*/
       // imgEditHabit.performClick();
    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
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
                    ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerListFragment", null);
            }
        });
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
                    ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerListFragment", null);

                    return true;

                }

                return false;
            }
        });
    }

    private void loadHabbitCalendarNew() {
        Log.i("popcalenderdetails","3");
        llCalendar1.removeAllViews();
        Calendar cal = Calendar.getInstance();
        Log.e("print index--", globalMonthIndex + "???");
        cal.add(Calendar.MONTH, globalMonthIndex);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDayOfMonth = cal.getTime();
        int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        txtMonthName.setText(sdf.format(cal.getTime()));
        Log.e("total day-", monthMaxDays + "???");
        Log.e("First Day of Month: ", sdf.format(firstDayOfMonth) + "???");
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("week no: ", dayOfWeek + "??");
        myMonth = cal.get(Calendar.MONTH);
        myYear = cal.get(Calendar.YEAR);

        int fd = dayOfWeek;
        int dayCount = 1;
        fd--;
        ArrayList<String> arrDates = new ArrayList<>();
        ArrayList<Integer> arrDatesColor = new ArrayList<>();
        SimpleDateFormat sdfReq = new SimpleDateFormat("yyyy-MM-dd");


        if (dayOfWeek == 1) {
            int fact = 6;


            for (int f = fact; f >= 1; f--) {
                cal.add(Calendar.DATE, -1);
                arrDates.add(sdfReq.format(cal.getTime()));
                arrDatesColor.add(0);
                Log.e("print date h--", arrDates.get(arrDates.size() - 1));
            }
            Collections.reverse(arrDates);
            cal.add(Calendar.DATE, fact);
        } else if (dayOfWeek > 2) {
            //Calendar tmpCal = cal;
            int fact = dayOfWeek - 2;


            for (int f = fact; f >= 1; f--) {
                cal.add(Calendar.DATE, -1);
                arrDates.add(sdfReq.format(cal.getTime()));
                arrDatesColor.add(0);
                Log.e("print date h--", arrDates.get(arrDates.size() - 1));
            }
            Collections.reverse(arrDates);
            cal.add(Calendar.DATE, fact);

        }


        while (myMonth == cal.get(Calendar.MONTH)) {
            System.out.print(cal.getTime());
            arrDates.add(sdfReq.format(cal.getTime()));
            arrDatesColor.add(1);
            Log.e("print date p--", arrDates.get(arrDates.size() - 1));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }

        filterMonthData(myMonth + 1, myYear);
        Integer doubleToInt = 0;
        try {
            doubleToInt = (int) Math.round(statMonthlyHabbit);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtMonthStat.setText(doubleToInt + "%");


        cal.add(Calendar.DATE, -1);
        Log.e("print date y--", arrDates.get(arrDates.size() - 1));
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("week no after: ", dayOfWeek + "??");
        if (dayOfWeek != 1) {
            for (int y = dayOfWeek; y <= 7; y++) {
                cal.add(Calendar.DATE, 1);
                arrDates.add(sdfReq.format(cal.getTime()));
                arrDatesColor.add(0);
                Log.e("print date r--", arrDates.get(arrDates.size() - 1));
            }
        }

        int weekNo = 0;
        for (int x = 0; x < arrDates.size(); ) {
            View v = getLayoutInflater().inflate(R.layout.layout_calendar_habbit_details, null);
            TextView txtSun = v.findViewById(R.id.txtSun);
            TextView txtMon = v.findViewById(R.id.txtMon);
            TextView txtTue = v.findViewById(R.id.txtTue);
            TextView txtWed = v.findViewById(R.id.txtWed);
            TextView txtThus = v.findViewById(R.id.txtThus);
            TextView txtFri = v.findViewById(R.id.txtFri);
            TextView txtSat = v.findViewById(R.id.txtSat);

            ImageView imgSun = v.findViewById(R.id.imgSun);
            ImageView imgMon = v.findViewById(R.id.imgMon);
            ImageView imgTue = v.findViewById(R.id.imgTue);
            ImageView imgWed = v.findViewById(R.id.imgWed);
            ImageView imgThus = v.findViewById(R.id.imgThu);
            ImageView imgFri = v.findViewById(R.id.imgFri);
            ImageView imgSat = v.findViewById(R.id.imgSat);

            RelativeLayout rlSun = v.findViewById(R.id.rlSun);
            RelativeLayout rlMon = v.findViewById(R.id.rlMon);
            RelativeLayout rlTue = v.findViewById(R.id.rlTue);
            RelativeLayout rlWed = v.findViewById(R.id.rlWed);
            RelativeLayout rlThu = v.findViewById(R.id.rlThu);
            RelativeLayout rlFri = v.findViewById(R.id.rlFri);
            RelativeLayout rlSat = v.findViewById(R.id.rlSat);
            TextView txtStat = v.findViewById(R.id.txtStat);

            TextView[] arrTextView = {txtMon, txtTue, txtWed, txtThus, txtFri, txtSat, txtSun};
            ImageView[] arrImageView = {imgMon, imgTue, imgWed, imgThus, imgFri, imgSat, imgSun};
            RelativeLayout[] arrRelativeLayout = {rlMon, rlTue, rlWed, rlThu, rlFri, rlSat, rlSun};

            if (arrWeeklyStatHabbit.size() > 0 && weekNo < arrWeeklyStatHabbit.size()) {
                Integer dbToInt = 0;
                dbToInt = (int) Math.round(arrWeeklyStatHabbit.get(weekNo));
                txtStat.setText(dbToInt + "%");
            } else {
                txtStat.setText("0%");
            }
            weekNo++;

            for (int y = 0; y < 7 && x < arrDates.size(); y++, x++) {

                arrRelativeLayout[y].setVisibility(View.VISIBLE);
                arrTextView[y].setVisibility(View.VISIBLE);
                arrTextView[y].setText("");
                //arrTextView[y].setText(dayCount+"");
                String dateArray = arrDates.get(x);
                String[] apDays = dateArray.split("-");
                int colorCode = arrDatesColor.get(x);
                if (colorCode == 0) {
                    arrRelativeLayout[y].setAlpha(.5f);

                } else if (colorCode == 1) {
                    arrRelativeLayout[y].setAlpha(1);
                }

                ArrayList<HabbitCalendarModel.HabitStat> matchArray = new ArrayList<>();
                Log.i("popcalenderdetails","size11"+String.valueOf(globalList.getHabitStats().size()));

                for (int s = 0; s < globalList.getHabitStats().size(); s++) {
                    String apiDate = globalList.getHabitStats().get(s).getTaskDate();
                    String[] rcvdDate = apiDate.split("T");


                    if (rcvdDate[0].equals(dateArray)) {
                        matchArray.add(globalList.getHabitStats().get(s));
                    }


                }
                Log.i("popcalenderdetails","size"+String.valueOf(matchArray.size()));
                if (matchArray.size() == 2) {
                    Log.i("popcalenderdetails","30");
                    if (matchArray.get(0).getIsTaskDone() && matchArray.get(1).getIsTaskDone()) {
                        Log.i("popcalenderdetails","31");
                        //arrRelativeLayout[y].setBackgroundResource(R.drawable.mbhq_tick_green);
                        arrImageView[y].setImageResource(R.drawable.mbhq_tick_green);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);

                    } else if (matchArray.get(0).getIsTaskDone() || matchArray.get(1).getIsTaskDone()) {
                        Log.i("popcalenderdetails","32");
                        //arrRelativeLayout[y].setBackgroundResource(R.drawable.mbhq_half_green);
                        arrImageView[y].setImageResource(R.drawable.mbhq_half_green);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);
                    } else if (!matchArray.get(0).getIsTaskDone() && !matchArray.get(1).getIsTaskDone()) {
                        Log.i("popcalenderdetails","33");
                        arrImageView[y].setImageResource(R.drawable.mbhq_circle_green);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);
                    }
                } else if (matchArray.size() == 1) {
                    Log.i("popcalenderdetails","40");
                    if (matchArray.get(0).getIsTaskDone()) {
                        Log.i("popcalenderdetails","41");
                        arrImageView[y].setImageResource(R.drawable.mbhq_tick_green);
                        arrImageView[y].setVisibility(View.VISIBLE);
                        arrTextView[y].setVisibility(View.GONE);

                    } else if (!matchArray.get(0).getIsTaskDone()) {
                        Log.i("popcalenderdetails","42");
                        arrImageView[y].setImageResource(R.drawable.mbhq_circle_green);
                        arrImageView[y].setVisibility(View.VISIBLE);
                        arrTextView[y].setVisibility(View.GONE);

                    }
                } else if (matchArray.size() == 0) {

                    Log.i("popcalenderdetails","50");
                    arrImageView[y].setImageDrawable(null);
                    arrImageView[y].setVisibility(View.GONE);
                    arrTextView[y].setText(apDays[2] + "");
                    arrTextView[y].setVisibility(View.VISIBLE);

                }

            }
            llCalendar1.addView(v);
        }
    }

    private void loadBreakCalendarNew() {
        llCalendar2.removeAllViews();
        Calendar cal = Calendar.getInstance();
        Log.e("print index--", globalMonthIndex + "???");
        cal.add(Calendar.MONTH, globalMonthIndex);
        cal.set(Calendar.DAY_OF_MONTH, 1);

        Date firstDayOfMonth = cal.getTime();
        int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        txtMonthName.setText(sdf.format(cal.getTime()));
        Log.e("total day-", monthMaxDays + "???");
        Log.e("First Day of Month: ", sdf.format(firstDayOfMonth) + "???");
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("week no: ", dayOfWeek + "??");
        myMonth = cal.get(Calendar.MONTH);
        myYear = cal.get(Calendar.YEAR);

        int fd = dayOfWeek;
        int dayCount = 1;
        fd--;
        ArrayList<String> arrDates = new ArrayList<>();
        ArrayList<Integer> arrDatesColor = new ArrayList<>();
        SimpleDateFormat sdfReq = new SimpleDateFormat("yyyy-MM-dd");


        if (dayOfWeek == 1) {
            int fact = 6;


            for (int f = fact; f >= 1; f--) {
                cal.add(Calendar.DATE, -1);
                arrDates.add(sdfReq.format(cal.getTime()));
                arrDatesColor.add(0);
                //Log.e("print date h--",arrDates.get(arrDates.size()-1));
            }
            Collections.reverse(arrDates);
            cal.add(Calendar.DATE, fact);
        } else if (dayOfWeek > 2) {
            //Calendar tmpCal = cal;
            int fact = dayOfWeek - 2;


            for (int f = fact; f >= 1; f--) {
                cal.add(Calendar.DATE, -1);
                arrDates.add(sdfReq.format(cal.getTime()));
                arrDatesColor.add(0);
                //Log.e("print date h--",arrDates.get(arrDates.size()-1));
            }
            Collections.reverse(arrDates);
            cal.add(Calendar.DATE, fact);
            // Log.e("print date q--",arrDates.get(arrDates.size()-1));

        }


        while (myMonth == cal.get(Calendar.MONTH)) {
            System.out.print(cal.getTime());
            arrDates.add(sdfReq.format(cal.getTime()));
            // Log.e("print date p--",arrDates.get(arrDates.size()-1));
            cal.add(Calendar.DAY_OF_MONTH, 1);
            arrDatesColor.add(1);
        }

        filterMonthDataBreak(myMonth + 1, myYear);
        Integer dbToInt = 0;
        try {
            dbToInt = (int) Math.round(statMonthlyHabbitBreak);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtMonthStatBreak.setText(dbToInt + "%");


        cal.add(Calendar.DATE, -1);
        //Log.e("print date y--",arrDates.get(arrDates.size()-1));
        dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        // Log.e("week no after: " , dayOfWeek+"??");
        if (dayOfWeek != 1) {
            for (int y = dayOfWeek; y <= 7; y++) {
                cal.add(Calendar.DATE, 1);
                arrDates.add(sdfReq.format(cal.getTime()));
                arrDatesColor.add(0);
                //Log.e("print date r--",arrDates.get(arrDates.size()-1));
            }
        }



     /* filterMonthData(myMonth+1,myYear);
        txtMonthStat.setText(statMonthlyHabbit+"%");*/
        int weekNo = 0;
        for (int x = 0; x < arrDates.size(); ) {
            View v = getLayoutInflater().inflate(R.layout.layout_calendar_habbit_details, null);
            TextView txtSun = v.findViewById(R.id.txtSun);
            TextView txtMon = v.findViewById(R.id.txtMon);
            TextView txtTue = v.findViewById(R.id.txtTue);
            TextView txtWed = v.findViewById(R.id.txtWed);
            TextView txtThus = v.findViewById(R.id.txtThus);
            TextView txtFri = v.findViewById(R.id.txtFri);
            TextView txtSat = v.findViewById(R.id.txtSat);

            ImageView imgSun = v.findViewById(R.id.imgSun);
            ImageView imgMon = v.findViewById(R.id.imgMon);
            ImageView imgTue = v.findViewById(R.id.imgTue);
            ImageView imgWed = v.findViewById(R.id.imgWed);
            ImageView imgThus = v.findViewById(R.id.imgThu);
            ImageView imgFri = v.findViewById(R.id.imgFri);
            ImageView imgSat = v.findViewById(R.id.imgSat);

            RelativeLayout rlSun = v.findViewById(R.id.rlSun);
            RelativeLayout rlMon = v.findViewById(R.id.rlMon);
            RelativeLayout rlTue = v.findViewById(R.id.rlTue);
            RelativeLayout rlWed = v.findViewById(R.id.rlWed);
            RelativeLayout rlThu = v.findViewById(R.id.rlThu);
            RelativeLayout rlFri = v.findViewById(R.id.rlFri);
            RelativeLayout rlSat = v.findViewById(R.id.rlSat);
            TextView txtStat = v.findViewById(R.id.txtStat);

            TextView[] arrTextView = {txtMon, txtTue, txtWed, txtThus, txtFri, txtSat, txtSun};
            ImageView[] arrImageView = {imgMon, imgTue, imgWed, imgThus, imgFri, imgSat, imgSun};
            RelativeLayout[] arrRelativeLayout = {rlMon, rlTue, rlWed, rlThu, rlFri, rlSat, rlSun};
            // Log.e("weekly array--",arrWeeklyStatHabbit.size()+"??"+arrWeeklyStatHabbit.get(weekNo)+">>>"+weekNo);
            if (arrWeeklyStatHabbitBreak.size() > 0 && weekNo < arrWeeklyStatHabbitBreak.size()) {
                Integer dbToInt2 = 0;
                dbToInt2 = (int) Math.round(arrWeeklyStatHabbitBreak.get(weekNo));
                txtStat.setText(dbToInt2 + "%");
            } else {
                txtStat.setText("0%");
            }
            weekNo++;

            for (int y = 0; y < 7 && x < arrDates.size(); y++, x++) {

                arrRelativeLayout[y].setVisibility(View.VISIBLE);
                arrTextView[y].setVisibility(View.VISIBLE);
                arrTextView[y].setText("");
                //arrTextView[y].setText(dayCount+"");
                String dateArray = arrDates.get(x);
                String[] apDays = dateArray.split("-");

                int colorCode = arrDatesColor.get(x);
                if (colorCode == 0) {
                    arrRelativeLayout[y].setAlpha(.5f);

                } else if (colorCode == 1) {
                    arrRelativeLayout[y].setAlpha(1);
                }
                // arrTextView[y].setText(apDays[2]+"");

                ArrayList<HabbitCalendarModel.BreakHabitStat> matchArray = new ArrayList<>();
                for (int s = 0; s < globalList.getBreakHabitStats().size(); s++) {
                    String apiDate = globalList.getBreakHabitStats().get(s).getTaskDate();
                    String[] rcvdDate = apiDate.split("T");


                    if (rcvdDate[0].equals(dateArray)) {
                        matchArray.add(globalList.getBreakHabitStats().get(s));
                    }


                }
                if (matchArray.size() == 2) {
                    if (matchArray.get(0).getIsTaskDone() && matchArray.get(1).getIsTaskDone()) {

                        //arrRelativeLayout[y].setBackgroundResource(R.drawable.ic_close_gray);
                        arrImageView[y].setImageResource(R.drawable.ic_close_gray);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);

                    } else if (matchArray.get(0).getIsTaskDone() || matchArray.get(1).getIsTaskDone()) {
                        arrRelativeLayout[y].setBackgroundResource(R.drawable.mbhq_half_red);
                    } else if (!matchArray.get(0).getIsTaskDone() && !matchArray.get(1).getIsTaskDone()) {

                        //arrRelativeLayout[y].setBackgroundResource(R.drawable.ic_grey_circle);
                        arrImageView[y].setImageResource(R.drawable.ic_grey_circle);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);

                    }
                } else if (matchArray.size() == 1) {
                    if (matchArray.get(0).getIsTaskDone()) {

                        //arrRelativeLayout[y].setBackgroundResource(R.drawable.ic_close_gray);

                        arrImageView[y].setImageResource(R.drawable.ic_close_gray);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);

                    } else if (!matchArray.get(0).getIsTaskDone()) {
                        //arrRelativeLayout[y].setBackgroundResource(R.drawable.ic_grey_circle);

                        arrImageView[y].setImageResource(R.drawable.ic_grey_circle);
                        arrImageView[y].setVisibility(View.VISIBLE);

                        arrTextView[y].setVisibility(View.GONE);

                    }
                } else if (matchArray.size() == 0) {
                    arrTextView[y].setText(apDays[2] + "");
                }






                /*arrRelativeLayout[y].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Gson gson=new Gson();
                        String data=gson.toJson(globalList);
                        HabbitCalendarTickUntickFragment habbitCalendarTickUntickFragment=new HabbitCalendarTickUntickFragment();
                        Bundle bundle=new Bundle();
                        bundle.putString("data",data);
                        bundle.putString("habbit_name",habbitName);
                        bundle.putString("break_name",breakName);
                        bundle.putInt("id",habbitId);
                        habbitCalendarTickUntickFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(new HabbitCalendarTickUntickFragment(),"HabbitCalendarTickUntickFragment",bundle);
                    }
                });*/
            }
            llCalendar2.addView(v);
        }
    }

    private void filterMonthData(int myMonth, int myYear) {
        arrWeeklyStatHabbit.clear();
        statMonthlyHabbit = 0.0;
        Log.i("popcalenderdetails",String.valueOf(globalList.getHabitMonthlyStats().size()));

        for (int e = 0; e < globalList.getHabitMonthlyStats().size(); e++) {
            if (globalList.getHabitMonthlyStats().get(e).getMonth() == myMonth && globalList.getHabitMonthlyStats().get(e).getYear() == myYear) {
                statMonthlyHabbit = globalList.getHabitMonthlyStats().get(e).getStatsMonthlyPercentage();
                Log.i("popcalenderdetails",String.valueOf(globalList.getHabitMonthlyStats().size()));

                for (int d = 0; d < globalList.getHabitMonthlyStats().get(e).getWeeklyStats().size(); d++) {
                    arrWeeklyStatHabbit.add(globalList.getHabitMonthlyStats().get(e).getWeeklyStats().get(d).getStatsWeeklyPercentage());
                    Log.e("add week", arrWeeklyStatHabbit.get(arrWeeklyStatHabbit.size() - 1) + "???");
                }
            }
        }
    }

    private void filterMonthDataBreak(int myMonth, int myYear) {
        arrWeeklyStatHabbitBreak.clear();
        statMonthlyHabbitBreak = 0.0;
        for (int e = 0; e < globalList.getBreakMonthlyStats().size(); e++) {
            if (globalList.getBreakMonthlyStats().get(e).getMonth() == myMonth && globalList.getBreakMonthlyStats().get(e).getYear() == myYear) {
                statMonthlyHabbitBreak = globalList.getBreakMonthlyStats().get(e).getStatsMonthlyPercentage();

                for (int d = 0; d < globalList.getBreakMonthlyStats().get(e).getWeeklyStats().size(); d++) {
                    arrWeeklyStatHabbitBreak.add(globalList.getBreakMonthlyStats().get(e).getWeeklyStats().get(d).getStatsWeeklyPercentage());
                }
            }
        }
    }

    private void loadBreakCalendar() {
        llCalendar2.removeAllViews();
        Calendar cal = Calendar.getInstance();
        Log.e("print index--", globalMonthIndex + "???");
        cal.add(Calendar.MONTH, globalMonthIndex);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDayOfMonth = cal.getTime();
        int monthMaxDays = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        SimpleDateFormat sdf = new SimpleDateFormat("MMM yyyy");
        txtMonthName.setText(sdf.format(cal.getTime()));
        Log.e("total day-", monthMaxDays + "???");
        Log.e("First Day of Month: ", sdf.format(firstDayOfMonth) + "???");
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        Log.e("week no: ", dayOfWeek + "??");
        int myMonth = cal.get(Calendar.MONTH);
        int myYear = cal.get(Calendar.YEAR);

        int fd = dayOfWeek;
        int dayCount = 1;
        fd--;
        ArrayList<String> arrDates = new ArrayList<>();

        SimpleDateFormat sdfReq = new SimpleDateFormat("yyyy-MM-dd");
        while (myMonth == cal.get(Calendar.MONTH)) {
            System.out.print(cal.getTime());
            arrDates.add(sdfReq.format(cal.getTime()));
            // Log.e("print date--",arrDates.get(arrDates.size()-1));
            cal.add(Calendar.DAY_OF_MONTH, 1);
        }
        filterMonthDataBreak(myMonth + 1, myYear);
        Integer dbToInt = 0;
        try {
            dbToInt = (int) Math.round(statMonthlyHabbitBreak);
        } catch (Exception e) {
            e.printStackTrace();
        }
        txtMonthStatBreak.setText(dbToInt + "%");
        for (int x = 0; x < monthMaxDays; ) {
            View v = getLayoutInflater().inflate(R.layout.layout_calendar_habbit_details, null);
            TextView txtSun = v.findViewById(R.id.txtSun);
            TextView txtMon = v.findViewById(R.id.txtMon);
            TextView txtTue = v.findViewById(R.id.txtTue);
            TextView txtWed = v.findViewById(R.id.txtWed);
            TextView txtThus = v.findViewById(R.id.txtThus);
            TextView txtFri = v.findViewById(R.id.txtFri);
            TextView txtSat = v.findViewById(R.id.txtSat);
            RelativeLayout rlSun = v.findViewById(R.id.rlSun);
            RelativeLayout rlMon = v.findViewById(R.id.rlMon);
            RelativeLayout rlTue = v.findViewById(R.id.rlTue);
            RelativeLayout rlWed = v.findViewById(R.id.rlWed);
            RelativeLayout rlThu = v.findViewById(R.id.rlThu);
            RelativeLayout rlFri = v.findViewById(R.id.rlFri);
            RelativeLayout rlSat = v.findViewById(R.id.rlSat);
            TextView txtStat = v.findViewById(R.id.txtStat);

            TextView[] arrTextView = {txtSun, txtMon, txtTue, txtWed, txtThus, txtFri, txtSat};
            RelativeLayout[] arrRelativeLayout = {rlSun, rlMon, rlTue, rlWed, rlThu, rlFri, rlSat};
            Log.e("weekly array--", arrWeeklyStatHabbitBreak.size() + "??");
            if (x < arrWeeklyStatHabbitBreak.size()) {
                Integer dbToInt3 = 0;
                dbToInt3 = (int) Math.round(arrWeeklyStatHabbitBreak.get(x));
                txtStat.setText(dbToInt3 + "%");
            } else {
                txtStat.setText("0%");
            }

            for (int y = 0; y < 7; y++, x++) {
                if (dayCount > monthMaxDays)
                    break;

                if (dayCount > 1 || y == fd) {
                    arrRelativeLayout[y].setVisibility(View.VISIBLE);
                    arrTextView[y].setVisibility(View.VISIBLE);
                    arrTextView[y].setText(dayCount + "");
                    for (int z = 0; z < globalList.getBreakHabitStats().size(); z++) {
                        String dateRCvd = globalList.getBreakHabitStats().get(z).getTaskDate();
                        String[] dateSp = dateRCvd.split("T");
                        // String[] daySp=dateSp[0].split("-");
                        //Log.e("print split date--",dateSp[0]+"???");
                        if (dateSp[0].equals(arrDates.get(dayCount - 1))) {
                            /*if(globalList.getBreakHabitStats().get(z).getIsTaskDone())
                            {
                                arrRelativeLayout[y].setBackgroundResource(R.drawable.circle_light_green);
                            }
                            else
                                arrRelativeLayout[y].setBackgroundResource(R.drawable.circle_light_green_empty);*/


                            if (globalList.getBreakHabitStats().get(z).getIsTaskDone()) {
                                arrRelativeLayout[y].setBackgroundResource(R.drawable.mbhq_tick_red);
                            } else {
                                arrRelativeLayout[y].setBackgroundResource(R.drawable.mbhq_circle_red);
                            }


                            break;
                        }
                    }


                    dayCount++;
                } else {
                    arrRelativeLayout[y].setVisibility(View.INVISIBLE);
                    arrTextView[y].setVisibility(View.INVISIBLE);
                }


            }
            llCalendar2.addView(v);
        }
    }

    @SuppressLint("ResourceType")
    private void initview(View view) {
        rlQuaterVal1 = view.findViewById(R.id.rlQuaterVal1);
        rlQuaterVal2 = view.findViewById(R.id.rlQuaterVal2);
        rlQuaterVal3 = view.findViewById(R.id.rlQuaterVal3);
        rlQuaterVal4 = view.findViewById(R.id.rlQuaterVal4);
        rlQuaterBreakVal1 = view.findViewById(R.id.rlQuaterBreakVal1);
        rlQuaterBreakVal2 = view.findViewById(R.id.rlQuaterBreakVal2);
        rlQuaterBreakVal3 = view.findViewById(R.id.rlQuaterBreakVal3);
        rlQuaterBreakVal4 = view.findViewById(R.id.rlQuaterBreakVal4);
        txtRightHabbitQ1 = view.findViewById(R.id.txtRightHabbitQ1);
        txtRightHabbitQ2 = view.findViewById(R.id.txtRightHabbitQ2);
        txtRightHabbitQ3 = view.findViewById(R.id.txtRightHabbitQ3);
        txtRightHabbitQ4 = view.findViewById(R.id.txtRightHabbitQ4);
        txtRightBreakQ1 = view.findViewById(R.id.txtRightBreakQ1);
        txtRightBreakQ2 = view.findViewById(R.id.txtRightBreakQ2);
        txtRightBreakQ3 = view.findViewById(R.id.txtRightBreakQ3);
        txtRightBreakQ4 = view.findViewById(R.id.txtRightBreakQ4);
        txtNoData = view.findViewById(R.id.txtNoData);
        txtLeftBreak = view.findViewById(R.id.txtLeftBreak);
        txtLeftHabbit = view.findViewById(R.id.txtLeftHabbit);
        txtRightHabbit = view.findViewById(R.id.txtRightHabbit);
        txtRightBreak = view.findViewById(R.id.txtRightBreak);
        txtQuaterHabbitName = view.findViewById(R.id.txtQuaterHabbitName);
        txtQuaterBreakName = view.findViewById(R.id.txtQuaterBreakName);
        txtYearHabbitHeader = view.findViewById(R.id.txtYearHabbitHeader);
        txtYearBreakHeader = view.findViewById(R.id.txtYearBreakHeader);
        rlCalendarView = view.findViewById(R.id.rlCalendarView);
        rlYearlyView = view.findViewById(R.id.rlYearlyView);
        rlQuaterView = view.findViewById(R.id.rlQuaterView);
        imgFilter = view.findViewById(R.id.imgFilter);
        rlBack = view.findViewById(R.id.rlBack);
        txtMonthStat = view.findViewById(R.id.txtMonthStat);
        txtMonthStatBreak = view.findViewById(R.id.txtMonthStatBreak);
        llCalendar1 = view.findViewById(R.id.llCalendar1);
        llCalendar2 = view.findViewById(R.id.llCalendar2);
        imgBack = view.findViewById(R.id.imgBack);
        imgForward = view.findViewById(R.id.imgForward);
        txtMonthName = view.findViewById(R.id.txtMonthName);
        txtHabbitHeader = view.findViewById(R.id.txtHabbitHeader);
        txtBreakHeader = view.findViewById(R.id.txtBreakHeader);
        txtHabbitHeader.setText(habbitName.toUpperCase());
        txtQuaterHabbitName.setText(habbitName.toUpperCase());
        txtYearHabbitHeader.setText(habbitName.toUpperCase());
        if(null==breakName || "".equalsIgnoreCase(breakName)){

        }else{
            txtBreakHeader.setText(breakName.toUpperCase());
            txtQuaterBreakName.setText(breakName.toUpperCase());
            txtYearBreakHeader.setText(breakName.toUpperCase());
        }

        rlHabitDetails = view.findViewById(R.id.rlHabitDetails);
        rlAction = view.findViewById(R.id.rlAction);
        imgEditPencil = view.findViewById(R.id.imgEditPencil);
        imgDeleteBtn = view.findViewById(R.id.imgDeleteBtn);
        notifyBtn = view.findViewById(R.id.notifyBtn);
        recyclerQuarterHabit = view.findViewById(R.id.recyclerQuarterHabit);
        recyclerQuarterBreak = view.findViewById(R.id.recyclerQuarterBreak);
        recyclerYearHabit = view.findViewById(R.id.recyclerYearHabit);
        recyclerYearBreak = view.findViewById(R.id.recyclerYearBreak);

        recyclerQuarterHabit.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerQuarterHabit.setNestedScrollingEnabled(false);

        recyclerQuarterBreak.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerQuarterBreak.setNestedScrollingEnabled(false);

        recyclerYearHabit.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerYearHabit.setNestedScrollingEnabled(false);

        recyclerYearBreak.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerYearBreak.setNestedScrollingEnabled(false);



        if(push_notification){
            notifyBtn.setImageResource(R.drawable.mbhq_notification);
            notifyBtn.setColorFilter(getResources().getColor(R.color.colorPrimary));
        }else{

            notifyBtn.setImageResource(R.drawable.mbhq_notification);
            notifyBtn.setColorFilter(getResources().getColor(R.color.body_grey));

        }

        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForFilter();
            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.backtotodayhabit="";
                if (getArguments() != null && getArguments().containsKey("FROM_TODAY_PAGE")) {
                    //((MainActivity)getActivity()).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",null);
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                } else {
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                    ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabbitLIst", null);
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalMonthIndex -= 1;
                loadHabbitCalendarNew();
                loadBreakCalendarNew();

            }
        });
        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalMonthIndex += 1;
                loadHabbitCalendarNew();
                loadBreakCalendarNew();

            }
        });
        rlCalendarView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String data = gson.toJson(globalList);
                HabbitCalendarTickUntickFragment habbitCalendarTickUntickFragment = new HabbitCalendarTickUntickFragment();
                Bundle bundle = new Bundle();
                bundle.putString("data", data);
                bundle.putString("habbit_name", habbitName);
                if(null==breakName || "".equalsIgnoreCase(breakName)) {
                    bundle.putString("break_name", "");

                }else{
                    bundle.putString("break_name", breakName);

                }
                bundle.putInt("id", habbitId);
                if (getArguments() != null && getArguments().containsKey("FROM_TODAY_PAGE")) {
                    bundle.putString("FROM_TODAY_PAGE", "TRUE");
                }
                if (TASK_FREQUENCY != null) {
                    bundle.putInt("TASK_FREQUENCY", TASK_FREQUENCY);
                }
                habbitCalendarTickUntickFragment.setArguments(bundle);
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                ((MainActivity) getActivity()).loadFragment(new HabbitCalendarTickUntickFragment(), "HabbitCalendarTickUntickFragment", bundle);
            }
        });



        rlHabitDetails.setOnClickListener(view1 -> {
            Constants.clicknotification=false;
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerEditFragment());
            ((MainActivity) getActivity()).loadFragment(new HabitHackerEditFragment(), "HabitHackerEdit", globalBundle);


        });

        imgEditPencil.setOnClickListener(view1 -> {
            Constants.clicknotification=false;
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerEditFragment());
            ((MainActivity) getActivity()).loadFragment(new HabitHackerEditFragment(), "HabitHackerEdit", globalBundle);
        });
        //Added by Jyotirmoy Patra 16.09.22
        imgDeleteBtn.setOnClickListener(view1 -> {

            openDialogForHabitDelete();

        });



        notifyBtn.setOnClickListener(view1 -> {
            Constants.clicknotification=true;
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerEditFragment());
            ((MainActivity) getActivity()).loadFragment(new HabitHackerEditFragment(), "HabitHackerEdit", globalBundle);



        });
        //ended by Jyotirmoy Patra 16.09.22

    }

    public void openDialogForHabitDelete() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_delete_habit_alert);

        TextView txtDelete = dialog.findViewById(R.id.txtDelete);
        TextView txtRestartFromToday = dialog.findViewById(R.id.txtRestartFromToday);
        TextView txtPauseHide = dialog.findViewById(R.id.txtPauseHide);
        TextView txtNo = dialog.findViewById(R.id.txtNo);

        txtDelete.setOnClickListener(view -> {
            dialog.dismiss();
            deleteHabitSwapApiCall();
        });


        txtRestartFromToday.setVisibility(View.GONE);
        txtPauseHide.setVisibility(View.GONE);


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

                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        Util.showToast(getActivity(), "Successfully deleted");
                        ((MainActivity) getActivity()).callTaskStatusForDateAPI("");

                        Completable.fromAction(() -> {
                            mViewEdit.deleteByHabitId(HABIT_ID);
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                    Completable.fromAction(() -> {
                                        mViewModel.deleteHabitCalendarById(HABIT_ID);
                                    }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {
                                                Util.isReloadTodayMainPage = true;
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                                ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                                            }, throwable -> {
                                                Util.isReloadTodayMainPage = true;
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                                ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                                            });
                                }, throwable -> {
                                    Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                    Util.isReloadTodayMainPage = true;
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                    ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                                });
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void setValuesForHabitSave(String HIDDEN_OR_NOT) {

        globalHabitSwap.getHabitSwap().setHabitName(txtHabitName.getText().toString());

        if (txtFrequencyValue.getText().toString().contains(arrFreq[0])) {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(1);
        } else if (txtFrequencyValue.getText().toString().contains(arrFreq[1])) {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(3);
        } else if (txtFrequencyValue.getText().toString().contains(arrFreq[2])) {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(4);
        } else if (txtFrequencyValue.getText().toString().contains(arrFreq[3])) {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(5);
        } else if (txtFrequencyValue.getText().toString().contains(arrFreq[4])) {
            globalHabitSwap.getHabitSwap().getNewAction().setTaskFrequencyTypeId(5);
        }


        if (globalHabitInner != null) {
            try {
                if (globalHabitInner.has("Sunday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsSundayTask(globalHabitInner.getBoolean("Sunday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("Monday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsMondayTask(globalHabitInner.getBoolean("Monday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("Tuesday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsTuesdayTask(globalHabitInner.getBoolean("Tuesday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("Wednesday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsWednesdayTask(globalHabitInner.getBoolean("Wednesday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("Thursday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsThursdayTask(globalHabitInner.getBoolean("Thursday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("Friday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsFridayTask(globalHabitInner.getBoolean("Friday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("Saturday")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsSaturdayTask(globalHabitInner.getBoolean("Saturday"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                if (globalHabitInner.has("January")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsJanuaryTask(globalHabitInner.getBoolean("January"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("February")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsFebruaryTask(globalHabitInner.getBoolean("February"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("March")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsMarchTask(globalHabitInner.getBoolean("March"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("April")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsAprilTask(globalHabitInner.getBoolean("April"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("May")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsMayTask(globalHabitInner.getBoolean("May"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("June")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsJuneTask(globalHabitInner.getBoolean("June"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("July")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsJulyTask(globalHabitInner.getBoolean("July"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("August")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsAugustTask(globalHabitInner.getBoolean("August"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("September")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsSeptemberTask(globalHabitInner.getBoolean("September"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("October")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsOctoberTask(globalHabitInner.getBoolean("October"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("November")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsNovemberTask(globalHabitInner.getBoolean("November"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                if (globalHabitInner.has("December")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setIsDecemberTask(globalHabitInner.getBoolean("December"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                if (globalHabitInner.has("MonthReminder")) {
                    globalHabitSwap.getHabitSwap().getNewAction().setTaskMonthReminder(globalHabitInner.getInt("MonthReminder"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        try {
            if (!txtWhenValue.getText().toString().equals("")) {

                Integer totatSecond = 0;
                if (txtWhenValue.getText().toString().equals("NO SET TIME")) {
                    totatSecond = 0;
                } else {
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
                        if (hour != 12) {
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
                        if (hour == 12) {
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
                if (txtWhenValue2.getText().toString().equals("NO SET TIME")) {
                    totatSecond = 0;
                } else {
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
                        if (hour != 12) {
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
                        if (hour == 12) {
                            hour = hour + 12;
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
                Log.e("INNER_FREQUENCY_ID_3", 1 + ">>>>");
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
                    Log.e("INNER_FREQUENCY_ID_4", rootJsonInner.getInt("FrequencyId") + ">>>>");
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
        } else {
            Log.e("INNER_FREQUENCY_ID_5", 1 + ">>>>");
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

        if (!HIDDEN_OR_NOT.equals("") || boolHiddenIsClicked) {
            globalHabitSwap.getHabitSwap().getNewAction().setPushNotification(false);
        }

        //  saveNewBreakHabit();

    }
//    private void saveNewBreakHabit() {
//
//        if (Connection.checkConnection(getActivity())) {
//
//            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
//
//            SharedPreference sharedPreference = new SharedPreference(getActivity());
//            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
//
//            HashMap<String, Object> hashReq = new HashMap<>();
//            hashReq.put("Key", Util.KEY);
//            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//            hashReq.put("Habit",globalHabitSwap.getHabitSwap());
//
//            Call<JsonObject> jsonObjectCall = finisherService.addUpdateHabitSwap(hashReq);
//            jsonObjectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    progressDialog.dismiss();
//                    if(response.body()!=null && response.body().get("SuccessFlag").getAsBoolean())
//                    {
//                        Util.showToast(getActivity(),"Successfully Saved");
//                        if(chkSetReminder.isChecked())
//                        {
//                            SetLocalNotificationForHabit.setNotificationForHabit(globalHabitSwap.getHabitSwap(),getActivity());
//                        }else {
//                            try {
//                                AlarmManager am = (AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
//                                Intent intent = new Intent(getContext(), AlarmReceiver.class);
//                                intent.setAction(globalHabitSwap.getHabitSwap().getNewAction().getId()+"HABIT");
//                                PendingIntent sender = PendingIntent.getBroadcast(getActivity(),0, intent, PendingIntent.FLAG_NO_CREATE);
//                                am.cancel(sender);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        Gson gson = new Gson();
//                        String detailValues = gson.toJson(globalHabitSwap);
//                        HabitHackerEdit habitHackerEdit = new HabitHackerEdit(HABIT_ID,detailValues,false);
//                        /*mDisposable.add(mViewModel.insertUpdateUserName(habitHackerEdit)
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(() -> {
//                                    Log.e("HABIT_INSERT","SUCCESSFULL");
//                                },throwable -> {
//                                    Log.e("HABIT_INSERT","NOT_SUCCESSFULL");
//                                }));*/
//                        Completable.fromAction(() -> {
//                            mViewEdit.deleteByHabitId(HABIT_ID);
//                        })
//                                .subscribeOn(Schedulers.io())
//                                .observeOn(AndroidSchedulers.mainThread())
//                                .subscribe(() -> {
//                                    Log.e("DATABASE","DELETE_SUCCESSFULL");
//                                    Completable.fromAction(() -> {
//                                        mViewModel.deleteHabitCalendarById(HABIT_ID);
//                                    }).subscribeOn(Schedulers.io())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribe(() -> {
//                                                Util.isReloadTodayMainPage = true;
//                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
//                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
//                                                ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
//                                            },throwable -> {
//                                                Util.isReloadTodayMainPage = true;
//                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
//                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
//                                                ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
//                                            });
//                                },throwable -> {
//                                    Log.e("DATABASE","DELETE_UN_SUCCESSFULL");
//                                    Util.isReloadTodayMainPage = true;
//                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
//                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
//                                    ((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
//                                });
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    progressDialog.dismiss();
//                }
//            });
//
//        }
//
//    }

    private void GetHabitStatsApi() {
        Log.i("habit_idd",String.valueOf(habbitId));
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("HabitId", habbitId);
            //hashReq.put("HabitId", 94);

            Call<HabbitCalendarModel> userHabitSwapsModelCall = finisherService.GetHabitStats(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<HabbitCalendarModel>() {
                @Override
                public void onResponse(Call<HabbitCalendarModel> call, Response<HabbitCalendarModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().getSuccessFlag()) {
                        globalList = response.body();
                        Gson gson = new Gson();
                        String data = gson.toJson(globalList);
                        ((MainActivity) getActivity()).setHashMapHabbit(habbitId, data);


                        HabitCalendarEntity habitCalendarEntity = new HabitCalendarEntity(habbitId, data, false);
                        mDisposable.add(mViewModel.insertHabitCalendar(habitCalendarEntity)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("HABIT_CALENDAR_INSERT", "SUCCESSFULL");
                                }, throwable -> {
                                    Log.e("HABIT_CALENDAR_INSERT", "NOT_SUCCESSFULL");
                                }));
                        Log.i("popcalenderdetails","2");
                        setData();


                    }


                }

                @Override
                public void onFailure(Call<HabbitCalendarModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }


    private void setData() {
        loadHabbitCalendarNew();
        loadBreakCalendarNew();

        try {
            if (habbitName.equals("")) {
                txtHabbitHeader.setText(globalList.getHabitStats().get(0).getName());
                habbitName = globalList.getHabitStats().get(0).getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (null==breakName || "".equalsIgnoreCase(breakName)) {
                txtBreakHeader.setText(globalList.getBreakHabitStats().get(0).getName());
                breakName = globalList.getBreakHabitStats().get(0).getName();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int lastViewState = 0;
        String viewState = "";
        if (!sharedPreference.read("habbitview", "").equals("")) {
            Gson gson = new Gson();
            String storedHashMapString = sharedPreference.read("habbitview", "");
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            HashMap<String, String> sharedHabit = gson.fromJson(storedHashMapString, type);
            if (sharedHabit.containsKey(habbitId + ""))
                viewState = sharedHabit.get(habbitId + "");
        }


        if (!viewState.equals("")) {
            lastViewState = Integer.parseInt(viewState);
            imgFilter.setImageResource(R.drawable.mbhq_filter_green);
        }


        if (lastViewState == 0) {
            filterSelectedvalue = 0;
            txtNoData.setVisibility(View.GONE);
            rlYearlyView.setVisibility(View.GONE);
            rlQuaterView.setVisibility(View.GONE);
            rlCalendarView.setVisibility(View.VISIBLE);
            rlAction.setVisibility(View.VISIBLE);
        } else if (lastViewState == 1) {
            filterSelectedvalue = 1;
            txtNoData.setVisibility(View.GONE);
            rlCalendarView.setVisibility(View.GONE);
            rlYearlyView.setVisibility(View.GONE);
            rlQuaterView.setVisibility(View.VISIBLE);
            rlAction.setVisibility(View.GONE);
            filterQuaterData(myMonth + 1, myYear);
        } else if (lastViewState == 2) {
            filterSelectedvalue = 2;
            txtNoData.setVisibility(View.GONE);
            rlCalendarView.setVisibility(View.GONE);
            rlQuaterView.setVisibility(View.GONE);
            rlYearlyView.setVisibility(View.VISIBLE);
            rlAction.setVisibility(View.GONE);
            filterYearData(myMonth, myYear);

        }

        if (FROM_NOTIFICATION) {
            rlCalendarView.performClick();
        }
    }

    //////////////////
    private void openDialogForFilter() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_habbit_calendar_filter);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView imgExpandClose = dialog.findViewById(R.id.imgExpandClose);
        RelativeLayout rlShowAll = dialog.findViewById(R.id.rlShowAll);
        RelativeLayout rlToday = dialog.findViewById(R.id.rlToday);
        RelativeLayout rlThisMonth = dialog.findViewById(R.id.rlThisMonth);
        RelativeLayout rlSixMonth = dialog.findViewById(R.id.rlSixMonth);
        RelativeLayout rlOneYear = dialog.findViewById(R.id.rlOneYear);
        ImageView imgShowAllCheck = dialog.findViewById(R.id.imgShowAllCheck);
        ImageView imgTodayCheck = dialog.findViewById(R.id.imgTodayCheck);
        ImageView imgThisMonthCheck = dialog.findViewById(R.id.imgThisMonthCheck);
        ImageView imgSixMonthCheck = dialog.findViewById(R.id.imgSixMonthCheck);
        ImageView imgOneYearCheck = dialog.findViewById(R.id.imgOneYearCheck);
        ImageView imgSelectDateRange = dialog.findViewById(R.id.imgSelectDateRange);
        LinearLayout llCheckBoxOptions = dialog.findViewById(R.id.llCheckBoxOptions);
        RelativeLayout rlSelectDate = dialog.findViewById(R.id.rlSelectDate);
        TextView txtSelectDate = dialog.findViewById(R.id.txtSelectDate);
        RelativeLayout rlShowResults = dialog.findViewById(R.id.rlShowResults);
        RelativeLayout rlClearAll = dialog.findViewById(R.id.rlClearAll);
        RelativeLayout rlTransparent = dialog.findViewById(R.id.rlTransparent);
        TextView txtFromDate = dialog.findViewById(R.id.txtFromDate);
        TextView txtToDate = dialog.findViewById(R.id.txtToDate);
        LinearLayout llSecetDateWhole = dialog.findViewById(R.id.llSecetDateWhole);


        if (filterSelectedvalue == 0) {
            setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }
        if (filterSelectedvalue == 1) {
            setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
            imgTodayCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }
        if (filterSelectedvalue == 2) {
            setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
            imgThisMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }
        if (filterSelectedvalue == 3) {
            setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
            imgSixMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }
        if (filterSelectedvalue == 4) {
            setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
            imgOneYearCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }
        if (filterSelectedvalue == -1) {
            setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
            imgSelectDateRange.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }

        imgExpandClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llCheckBoxOptions.getVisibility() == View.VISIBLE) {
                    imgExpandClose.setBackgroundResource(R.drawable.mbhq_expand_more);
                    llCheckBoxOptions.setVisibility(View.GONE);
                } else {
                    imgExpandClose.setBackgroundResource(R.drawable.mbhq_expand_less);
                    llCheckBoxOptions.setVisibility(View.VISIBLE);
                }
            }
        });

        rlTransparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rlShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterSelectedvalue = 0;
                setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                llSecetDateWhole.setVisibility(View.GONE);
            }
        });

        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolToday) {
                    makeAllBoolFalse();
                    boolToday = true;
                    filterSelectedvalue = 1;
                    llSecetDateWhole.setVisibility(View.GONE);
                    setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                    imgTodayCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolToday = false;
                    rlShowAll.performClick();
                }
            }
        });

        rlThisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolThisMonth) {
                    makeAllBoolFalse();
                    boolThisMonth = true;
                    filterSelectedvalue = 2;
                    llSecetDateWhole.setVisibility(View.GONE);
                    setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                    imgThisMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolThisMonth = false;
                    rlShowAll.performClick();
                }
            }
        });

        rlSixMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolSixMonth) {
                    makeAllBoolFalse();
                    boolSixMonth = true;
                    filterSelectedvalue = 3;
                    llSecetDateWhole.setVisibility(View.GONE);
                    setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                    imgSixMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolSixMonth = false;
                    rlShowAll.performClick();
                }
            }
        });

        rlOneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolOneYear) {
                    makeAllBoolFalse();
                    boolOneYear = true;
                    llSecetDateWhole.setVisibility(View.GONE);
                    filterSelectedvalue = 4;
                    setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                    imgOneYearCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolOneYear = false;
                    rlShowAll.performClick();
                }
            }
        });

        rlSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!boolDateSelected) {
                    makeAllBoolFalse();
                    filterSelectedvalue = -1;
                    setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                    imgSelectDateRange.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    boolDateSelected = true;
                    llSecetDateWhole.setVisibility(View.VISIBLE);
                } else {
                    boolDateSelected = false;
                    rlShowAll.performClick();
                    llSecetDateWhole.setVisibility(View.GONE);
                }
            }
        });

        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar calendar = Calendar.getInstance();
                Date dtToday = calendar.getTime();
                // DatePickerForGratitude datePickerControllerFromTextView = new DatePickerForGratitude(getActivity(),txtFromDate,myDateFormat.format(dtToday),false,"",null);
            }
        });

        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar calendar = Calendar.getInstance();
                Date dtToday = calendar.getTime();
                // DatePickerForGratitude datePickerControllerFromTextView = new DatePickerForGratitude(getActivity(),txtToDate,myDateFormat.format(dtToday),false,"",null);
            }
        });

        rlShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFilter.setImageResource(R.drawable.mbhq_filter_green);
                SharedPreference sharedPreference = new SharedPreference(getActivity());
                if (filterSelectedvalue == 0) {
                    dialog.dismiss();
                    txtNoData.setVisibility(View.GONE);
                    rlYearlyView.setVisibility(View.GONE);
                    rlQuaterView.setVisibility(View.GONE);
                    rlCalendarView.setVisibility(View.VISIBLE);
                    rlAction.setVisibility(View.VISIBLE);
                    // sharedPreference.write("habbitview","","0");

                    Gson gson = new Gson();
                    if (sharedPreference.read("habbitview", "").equals("")) {
                        HashMap<String, String> sharedHabit = new HashMap<>();
                        sharedHabit.put(habbitId + "", 0 + "");
                        String hashMapString = gson.toJson(sharedHabit);
                        sharedPreference.write("habbitview", "", hashMapString);
                    } else {
                        String storedHashMapString = sharedPreference.read("habbitview", "");
                        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> sharedHabit = gson.fromJson(storedHashMapString, type);
                        sharedHabit.put(habbitId + "", 0 + "");
                        String hashMapString = gson.toJson(sharedHabit);
                        sharedPreference.write("habbitview", "", hashMapString);
                    }


                } else if (filterSelectedvalue == 1) {
                    dialog.dismiss();
                    txtNoData.setVisibility(View.GONE);
                    rlCalendarView.setVisibility(View.GONE);
                    rlYearlyView.setVisibility(View.GONE);
                    rlQuaterView.setVisibility(View.VISIBLE);
                    rlAction.setVisibility(View.GONE);
                    filterQuaterData(myMonth + 1, myYear);
                    //sharedPreference.write("habbitview","","1");
                    Gson gson = new Gson();
                    if (sharedPreference.read("habbitview", "").equals("")) {
                        HashMap<String, String> sharedHabit = new HashMap<>();
                        sharedHabit.put(habbitId + "", 1 + "");
                        String hashMapString = gson.toJson(sharedHabit);
                        sharedPreference.write("habbitview", "", hashMapString);
                    } else {
                        String storedHashMapString = sharedPreference.read("habbitview", "");
                        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> sharedHabit = gson.fromJson(storedHashMapString, type);
                        sharedHabit.put(habbitId + "", 1 + "");
                        String hashMapString = gson.toJson(sharedHabit);
                        sharedPreference.write("habbitview", "", hashMapString);
                    }


                } else if (filterSelectedvalue == 2) {
                    dialog.dismiss();
                    txtNoData.setVisibility(View.GONE);
                    rlCalendarView.setVisibility(View.GONE);
                    rlQuaterView.setVisibility(View.GONE);
                    rlYearlyView.setVisibility(View.VISIBLE);
                    rlAction.setVisibility(View.GONE);
                    filterYearData(myMonth, myYear);
                    // sharedPreference.write("habbitview","","2");
                    Gson gson = new Gson();
                    if (sharedPreference.read("habbitview", "").equals("")) {
                        HashMap<String, String> sharedHabit = new HashMap<>();
                        sharedHabit.put(habbitId + "", 2 + "");
                        String hashMapString = gson.toJson(sharedHabit);
                        sharedPreference.write("habbitview", "", hashMapString);
                    } else {
                        String storedHashMapString = sharedPreference.read("habbitview", "");
                        java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>() {
                        }.getType();
                        HashMap<String, String> sharedHabit = gson.fromJson(storedHashMapString, type);
                        sharedHabit.put(habbitId + "", 2 + "");
                        String hashMapString = gson.toJson(sharedHabit);
                        sharedPreference.write("habbitview", "", hashMapString);
                    }

                }

            }
        });

        rlClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFilter.setImageResource(R.drawable.mbhq_filter);
                filterSelectedvalue = 0;
                setAllImageViewUnchecked(imgShowAllCheck, imgTodayCheck, imgThisMonthCheck, imgSixMonthCheck, imgOneYearCheck, imgSelectDateRange);
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                llSecetDateWhole.setVisibility(View.GONE);
            }
        });


        dialog.show();
    }

    private void filterYearData(int myMonth, int myYear) {
        boolean dataFound = false;
        if (globalList.getHabitYearlyStats() != null && globalList.getHabitYearlyStats().size() > 0) {
            dataFound = true;
            List<HabbitCalendarModel.HabitYearlyStat> lstHabitQuarter = globalList.getHabitYearlyStats();
            Collections.reverse(lstHabitQuarter);
            loadHabitYearAdapter(lstHabitQuarter);
        }
        if (globalList.getBreakYearlyStats() != null && globalList.getBreakYearlyStats().size() > 0) {
            dataFound = true;
            List<HabbitCalendarModel.BreakYearlyStat> lstHabitQuarter = globalList.getBreakYearlyStats();
            Collections.reverse(lstHabitQuarter);
            loadBreakYearAdapter(lstHabitQuarter);
        }
        if (!dataFound) {
            txtNoData.setVisibility(View.VISIBLE);
            rlYearlyView.setVisibility(View.GONE);
        }
    }

    private void loadBreakYearAdapter(List<HabbitCalendarModel.BreakYearlyStat> lstHabitQuarter) {
        recyclerYearBreak.setAdapter(null);
        HabitBreakYearAdapter habitYearAdapter = new HabitBreakYearAdapter(getActivity(), lstHabitQuarter, HabbitDetailsCalendarFragment.this);
        recyclerYearBreak.setAdapter(habitYearAdapter);
    }

    private void loadHabitYearAdapter(List<HabbitCalendarModel.HabitYearlyStat> lstHabitQuarter) {
        recyclerYearHabit.setAdapter(null);
        HabitYearAdapter habitYearAdapter = new HabitYearAdapter(getActivity(), lstHabitQuarter, HabbitDetailsCalendarFragment.this);
        recyclerYearHabit.setAdapter(habitYearAdapter);

    }

    private void filterQuaterData(int myMonth, int myYear) {
        boolean dataFound = false;

        if (globalList.getHabitQuarterlyStats() != null && globalList.getHabitQuarterlyStats().size() > 0) {
            dataFound = true;
            List<HabbitCalendarModel.HabitQuarterlyStat> quarterlyStats = globalList.getHabitQuarterlyStats();
            Collections.reverse(quarterlyStats);
            loadHabitQuarterAdapter(quarterlyStats);
        }
        if (globalList.getBreakQuarterlyStats() != null && globalList.getBreakQuarterlyStats().size() > 0) {
            dataFound = true;
            List<HabbitCalendarModel.BreakQuarterlyStat> quarterlyStats = globalList.getBreakQuarterlyStats();
            Collections.reverse(quarterlyStats);
            loadBreakQuarterAdapter(quarterlyStats);
        }
        if (!dataFound) {
            txtNoData.setVisibility(View.VISIBLE);
            rlQuaterView.setVisibility(View.GONE);
        }
    }

    private void loadBreakQuarterAdapter(List<HabbitCalendarModel.BreakQuarterlyStat> quarterlyStats) {
        recyclerQuarterBreak.setAdapter(null);
        HabitQuarterBreakAdapter habitQuaterAdapter = new HabitQuarterBreakAdapter(getActivity(), quarterlyStats, HabbitDetailsCalendarFragment.this);
        recyclerQuarterBreak.setAdapter(habitQuaterAdapter);
    }

    private void loadHabitQuarterAdapter(List<HabbitCalendarModel.HabitQuarterlyStat> quarterlyStats) {
        recyclerQuarterHabit.setAdapter(null);
        HabitQuaterAdapter habitQuaterAdapter = new HabitQuaterAdapter(getActivity(), quarterlyStats, HabbitDetailsCalendarFragment.this);
        recyclerQuarterHabit.setAdapter(habitQuaterAdapter);
    }


    private void setAllImageViewUnchecked(ImageView imgShowAllCheck, ImageView imgTodayCheck, ImageView imgThisMonthCheck, ImageView imgSixMonthCheck, ImageView imgOneYearCheck, ImageView imgSelectDateRange) {

        imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgTodayCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgThisMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgSixMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgOneYearCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgSelectDateRange.setBackgroundResource(R.drawable.mbhq_ic_untick);

    }

    private void makeAllBoolFalse() {
        boolDateSelected = false;
        boolToday = false;
        boolThisMonth = false;
        boolSixMonth = false;
        boolOneYear = false;
    }

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

}