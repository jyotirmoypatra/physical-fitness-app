package com.ashysystem.mbhq.fragment.habit_hacker;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.PopularHabitsAdapter;
import com.ashysystem.mbhq.dialog.CustomReminderDialogForPopularHabits;
import com.ashysystem.mbhq.model.habit_hacker.DemoHabitTemplateModel;
import com.ashysystem.mbhq.model.habit_hacker.GetHabitTemplatesResponseModel;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.model.habit_hacker.NewAction;
import com.ashysystem.mbhq.model.habit_hacker.SwapAction;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularHabitsListFragment extends Fragment {

    View globalView = null;
    TextView txtBack,txtLoading;
    RelativeLayout rlLoading,rlRecycler;
    RecyclerView recyclerPopularHabit;

    ProgressDialog progressDialog;
    SharedPreference sharedPreference;
    FinisherServiceImpl finisherService;

    Toolbar toolbar;
    JSONObject[] rootJsonInner = {null};

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(globalView == null) {
            globalView = inflater.inflate(R.layout.fragment_popular_habits_list, null);

            funToolBar();

            txtBack = globalView.findViewById(R.id.txtBack);
            txtLoading = globalView.findViewById(R.id.txtLoading);
            rlLoading = globalView.findViewById(R.id.rlLoading);
            rlRecycler = globalView.findViewById(R.id.rlRecycler);
            recyclerPopularHabit = globalView.findViewById(R.id.recyclerPopularHabit);

            sharedPreference = new SharedPreference(getActivity());
            finisherService = new FinisherServiceImpl(getActivity());

            recyclerPopularHabit.setLayoutManager(new LinearLayoutManager(getActivity()));

            txtBack.setOnClickListener(view -> {
                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
            });

            getHabitTemplatesApiCall();

            return globalView;
        }else {
            funToolBar();
            return globalView;
        }
    }

    public void getHabitTemplatesApiCall()
    {
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<GetHabitTemplatesResponseModel> modelCall = finisherService.getHabitTemplates(hashReq);

            modelCall.enqueue(new Callback<GetHabitTemplatesResponseModel>() {
                @Override
                public void onResponse(Call<GetHabitTemplatesResponseModel> call, Response<GetHabitTemplatesResponseModel> response) {
                    progressDialog.dismiss();

                    if(response.body() != null && response.body().getSuccessFlag())
                    {
                        ArrayList<DemoHabitTemplateModel> demoHabitTemplateModels = new ArrayList<>();
                        if(response.body().getHabitTemplates() != null && response.body().getHabitTemplates().size() > 0)
                        {
                            rlRecycler.setVisibility(View.VISIBLE);
                            for(int i = 0; i< response.body().getHabitTemplates().size(); i++)
                            {
                                for(int j = 0; j<response.body().getHabitTemplates().get(i).getTemplateDetails().size(); j++)
                                {
                                    DemoHabitTemplateModel templateModel = new DemoHabitTemplateModel();
                                    if(j == 0)
                                    {
                                        templateModel.setHabitTemplate(response.body().getHabitTemplates().get(i).getTemplateDetails().get(j));
                                        templateModel.setSection(response.body().getHabitTemplates().get(i).getSection());
                                        templateModel.setShowHeader(true);
                                    }else
                                    {
                                        templateModel.setHabitTemplate(response.body().getHabitTemplates().get(i).getTemplateDetails().get(j));
                                        templateModel.setSection("");
                                        templateModel.setShowHeader(false);
                                    }
                                    demoHabitTemplateModels.add(templateModel);
                                }
                            }
                            loadAdapter(demoHabitTemplateModels);
                        }else if(response.body().getHabitTemplates() != null && response.body().getHabitTemplates().size() == 0)
                        {
                            txtLoading.setText("No Items Found");
                            rlRecycler.setVisibility(View.GONE);
                        }else
                        {
                            txtLoading.setText("Something went wrong");
                            rlRecycler.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetHabitTemplatesResponseModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else {
            Util.showToast(getActivity(),Util.networkMsg);
        }
    }

    private void loadAdapter(ArrayList<DemoHabitTemplateModel> demoHabitTemplateModels) {

        recyclerPopularHabit.setAdapter(null);
        PopularHabitsAdapter popularHabitsAdapter = new PopularHabitsAdapter(getActivity(),demoHabitTemplateModels,this);
        recyclerPopularHabit.setAdapter(popularHabitsAdapter);

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
                if (getArguments() != null && getArguments().containsKey("NEWHOME")) {
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", getArguments());
                } else
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).funTabBarforHabits();
       // ((MainActivity) getActivity()).funDrawer(); //commented by jyotirmoy

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);

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

    public void openDialogForPopularHabits(DemoHabitTemplateModel demoHabitTemplateModel)
    {
      /*  Util.frompopularhabitlist=demoHabitTemplateModel.getHabitTemplate().getHabitToCreate();
        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
        ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
*/
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_popular_habits_save);

        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        EditText edtNewHabit = dialog.findViewById(R.id.edtNewHabit);
        RelativeLayout rlHowOften = dialog.findViewById(R.id.rlHowOften);
        TextView txtHowOftenTime = dialog.findViewById(R.id.txtHowOftenTime);
        EditText edtNewBreak = dialog.findViewById(R.id.edtNewBreak);
        RelativeLayout rlSave = dialog.findViewById(R.id.rlSave);

        imgCross.setOnClickListener(view -> {
            dialog.dismiss();
        });

        edtNewHabit.setText(demoHabitTemplateModel.getHabitTemplate().getHabitToCreate());
        edtNewBreak.setText(demoHabitTemplateModel.getHabitTemplate().getHabitToBreak());

        if(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 1)
        {
            txtHowOftenTime.setText("Daily");
        }else if(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 2)
        {
            txtHowOftenTime.setText("Twice Daily");
        }else if(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 3)
        {
            txtHowOftenTime.setText("Weekly");
        }else if(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 4)
        {
            txtHowOftenTime.setText("Fortnightly");
        }else if(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 5)
        {
            txtHowOftenTime.setText("Monthly");
        }else if(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 6)
        {
            txtHowOftenTime.setText("Yearly");
        }

        txtHowOftenTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CustomReminderDialogForPopularHabits customReminderDialog = new CustomReminderDialogForPopularHabits();
                Bundle bundle = new Bundle();
                Gson gson = new Gson();
                bundle.putString("MODEL",gson.toJson(demoHabitTemplateModel.getHabitTemplate()));
                bundle.putString("HABIT_HACKER_TASK", "TRUE");
                customReminderDialog.setArguments(bundle);
                customReminderDialog.mListener = new CustomReminderDialogForPopularHabits.onSubmitListener() {
                    @Override
                    public void setOnSubmitListener(JSONObject arg) {
                        if(arg != null)
                        {
                            Log.e("HABITHACKER", arg.toString() + "?");
                            rootJsonInner[0] = arg;
                            JSONObject rootJsonInner = arg;
                            if (rootJsonInner != null && rootJsonInner.has("FrequencyId")) {
                                try {
                                    if (rootJsonInner.getInt("FrequencyId") == 1) {
                                        txtHowOftenTime.setText("Daily");
                                    } else if (rootJsonInner.getInt("FrequencyId") == 2) {
                                        txtHowOftenTime.setText("Twice Daily");
                                    } else if (rootJsonInner.getInt("FrequencyId") == 3) {
                                        txtHowOftenTime.setText("Weekly");
                                    } else if (rootJsonInner.getInt("FrequencyId") == 4) {
                                        txtHowOftenTime.setText("Fortnightly");
                                    } else if (rootJsonInner.getInt("FrequencyId") == 5) {
                                        txtHowOftenTime.setText("Monthly");
                                    } else if (rootJsonInner.getInt("FrequencyId") == 6) {
                                        txtHowOftenTime.setText("Yearly");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                };
                customReminderDialog.show(getFragmentManager(), "");
            }
        });

        rlSave.setOnClickListener(view -> {
            if(edtNewHabit.getText().toString().equals(""))
            {
                Util.showToast(getActivity(),"Please Enter a Habit Name");
            }else if(edtNewBreak.getText().toString().equals(""))
            {
                Util.showToast(getActivity(),"Please Enter a Break Name");
            }else {
                dialog.dismiss();
                // makeHabitModelForAdd(edtNewHabit,demoHabitTemplateModel,edtNewBreak);
                makeHabitModelForAdd(edtNewHabit,demoHabitTemplateModel);
            }
        });

        dialog.show();
    }


    private void makeHabitModelForAdd(EditText edtNewHabit,DemoHabitTemplateModel demoHabitTemplateModel) {

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
            if(rootJsonInner[0] != null)
            {
                newAction.setTaskFrequencyTypeId(rootJsonInner[0].getInt("FrequencyId"));
            }else {
                newAction.setTaskFrequencyTypeId(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId());
            }
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
            swapAction.setName("habit to break");////////////////////////////////
            swapAction.setFrequencyId(1);
            if(rootJsonInner[0] != null)
            {
                swapAction.setTaskFrequencyTypeId(rootJsonInner[0].getInt("FrequencyId"));
            }else {
                swapAction.setTaskFrequencyTypeId(demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId());
            }
            swapAction.setTuesday(false);
            swapAction.setMonday(false);
            swapAction.setNovember(false);
            swapAction.setDecember(false);
            swapAction.setMonthReminder(1);
            swapAction.setReminderOption(1);
            swapAction.setReminderAt1(43200);
            swapAction.setSunday(false);
            swapAction.setEmail(false);
            // swapAction.setName(edtNewBreak.getText().toString());
            swapAction.setName("habit to break");////////////////////////////////
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


            if(rootJsonInner[0] == null)
            {
                newAction.setIsSundayTask(demoHabitTemplateModel.getHabitTemplate().getIsSundayTask());
                newAction.setIsMondayTask(demoHabitTemplateModel.getHabitTemplate().getIsMondayTask());
                newAction.setIsTuesdayTask(demoHabitTemplateModel.getHabitTemplate().getIsTuesdayTask());
                newAction.setIsWednesdayTask(demoHabitTemplateModel.getHabitTemplate().getIsWednesdayTask());
                newAction.setIsThursdayTask(demoHabitTemplateModel.getHabitTemplate().getIsThursdayTask());
                newAction.setIsFridayTask(demoHabitTemplateModel.getHabitTemplate().getIsFridayTask());
                newAction.setIsSaturdayTask(demoHabitTemplateModel.getHabitTemplate().getIsSaturdayTask());
                if(demoHabitTemplateModel.getHabitTemplate().getTaskMonthly() != null)
                {
                    newAction.setTaskMonthReminder(demoHabitTemplateModel.getHabitTemplate().getTaskMonthly());
                }else
                {
                    newAction.setTaskMonthReminder(1);
                }
                swapAction.setName("habit to break");////////////////////////////////
                swapAction.setIsSundayTask(demoHabitTemplateModel.getHabitTemplate().getIsSundayTask());
                swapAction.setIsMondayTask(demoHabitTemplateModel.getHabitTemplate().getIsMondayTask());
                swapAction.setIsTuesdayTask(demoHabitTemplateModel.getHabitTemplate().getIsTuesdayTask());
                swapAction.setIsWednesdayTask(demoHabitTemplateModel.getHabitTemplate().getIsWednesdayTask());
                swapAction.setIsThursdayTask(demoHabitTemplateModel.getHabitTemplate().getIsThursdayTask());
                swapAction.setIsFridayTask(demoHabitTemplateModel.getHabitTemplate().getIsFridayTask());
                swapAction.setIsSaturdayTask(demoHabitTemplateModel.getHabitTemplate().getIsSaturdayTask());
                if(demoHabitTemplateModel.getHabitTemplate().getTaskMonthly() != null)
                {
                    swapAction.setTaskMonthReminder(demoHabitTemplateModel.getHabitTemplate().getTaskMonthly());
                }else
                {
                    swapAction.setTaskMonthReminder(1);
                }

            }else
            {
                if ((rootJsonInner[0] != null && (rootJsonInner[0].getInt("FrequencyId") == 1 || rootJsonInner[0].getInt("FrequencyId") == 2)) /*|| (demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 1 || demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 2)*/)//Daily && //twice Daily
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
                    swapAction.setName("habit to break");////////////////////////////////

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

                } else if ((rootJsonInner[0] != null && rootJsonInner[0].getInt("FrequencyId") == 3 || rootJsonInner[0].getInt("FrequencyId") == 4) /*|| (demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 3 || demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 4)*/)//weekly && //fortnightly
                {
                    newAction.setIsFebruaryTask(false);
                    newAction.setIsMayTask(false);
                    newAction.setIsSeptemberTask(false);
                    newAction.setIsDecemberTask(false);
                    newAction.setTaskReminderAt2(12);
                    newAction.setTaskMonthReminder(1);
                    swapAction.setName("habit to break");////////////////////////////////

                    swapAction.setIsFebruaryTask(false);
                    swapAction.setIsMayTask(false);
                    swapAction.setIsSeptemberTask(false);
                    swapAction.setIsDecemberTask(false);
                    swapAction.setTaskReminderAt2(12);
                    swapAction.setTaskMonthReminder(1);

                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Sunday") && rootJsonInner[0].getBoolean("Sunday")) {
                        newAction.setIsSundayTask(true);
                        swapAction.setIsSundayTask(true);
                    } else if(demoHabitTemplateModel.getHabitTemplate().getIsSundayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsSundayTask()){
                        newAction.setIsSundayTask(true);
                        swapAction.setIsSundayTask(true);
                    }else {
                        newAction.setIsSundayTask(false);
                        swapAction.setIsSundayTask(false);
                    }
                    newAction.setIsJulyTask(false);
                    newAction.setIsAprilTask(false);

                    swapAction.setIsJulyTask(false);
                    swapAction.setIsAprilTask(false);

                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Wednesday") && rootJsonInner[0].getBoolean("Wednesday")) {
                        newAction.setIsWednesdayTask(true);
                        swapAction.setIsWednesdayTask(true);
                    }else if(demoHabitTemplateModel.getHabitTemplate().getIsWednesdayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsWednesdayTask()){
                        newAction.setIsSundayTask(true);
                        swapAction.setIsSundayTask(true);
                    } else {
                        newAction.setIsWednesdayTask(false);
                        swapAction.setIsWednesdayTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Saturday") && rootJsonInner[0].getBoolean("Saturday")) {
                        newAction.setIsSaturdayTask(true);
                        swapAction.setIsSaturdayTask(true);
                    }else if(demoHabitTemplateModel.getHabitTemplate().getIsSaturdayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsSaturdayTask()){
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

                    swapAction.setTaskReminderAt1(12);
                    swapAction.setIsAugustTask(false);
                    swapAction.setIsJuneTask(false);
                    swapAction.setIsNovemberTask(false);
                    swapAction.setIsOctoberTask(false);
                    swapAction.setIsMarchTask(false);
                    swapAction.setIsJanuaryTask(false);

                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Monday") && rootJsonInner[0].getBoolean("Monday")) {
                        newAction.setIsMondayTask(true);
                        swapAction.setIsMondayTask(true);
                    }else if(demoHabitTemplateModel.getHabitTemplate().getIsMondayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsMondayTask()){
                        newAction.setIsMondayTask(true);
                        swapAction.setIsMondayTask(true);
                    } else {
                        newAction.setIsMondayTask(false);
                        swapAction.setIsMondayTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Thursday") && rootJsonInner[0].getBoolean("Thursday")) {
                        newAction.setIsThursdayTask(true);
                        swapAction.setIsThursdayTask(true);
                    }else if(demoHabitTemplateModel.getHabitTemplate().getIsThursdayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsThursdayTask()){
                        newAction.setIsThursdayTask(true);
                        swapAction.setIsThursdayTask(true);
                    } else {
                        newAction.setIsThursdayTask(false);
                        swapAction.setIsThursdayTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Tuesday") && rootJsonInner[0].getBoolean("Tuesday")) {
                        newAction.setIsTuesdayTask(true);
                        swapAction.setIsTuesdayTask(true);
                    }else if(demoHabitTemplateModel.getHabitTemplate().getIsTuesdayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsTuesdayTask()){
                        newAction.setIsTuesdayTask(true);
                        swapAction.setIsTuesdayTask(true);
                    } else {
                        newAction.setIsTuesdayTask(false);
                        swapAction.setIsTuesdayTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].has("Friday") && rootJsonInner[0].getBoolean("Friday")) {
                        newAction.setIsFridayTask(true);
                        swapAction.setIsFridayTask(true);
                    }else if(demoHabitTemplateModel.getHabitTemplate().getIsFridayTask() != null && demoHabitTemplateModel.getHabitTemplate().getIsFridayTask()){
                        newAction.setIsFridayTask(true);
                        swapAction.setIsFridayTask(true);
                    } else {
                        newAction.setIsFridayTask(false);
                        swapAction.setIsFridayTask(false);
                    }
                } else if ((rootJsonInner[0] != null && rootJsonInner[0].getInt("FrequencyId") == 5) || demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 5)//monthly
                {
                    newAction.setIsFebruaryTask(false);
                    newAction.setIsMayTask(false);
                    newAction.setIsSeptemberTask(false);
                    newAction.setIsDecemberTask(false);
                    newAction.setTaskReminderAt2(12);
                    swapAction.setName("habit to break");////////////////////////////////

                    swapAction.setIsFebruaryTask(false);
                    swapAction.setIsMayTask(false);
                    swapAction.setIsSeptemberTask(false);
                    swapAction.setIsDecemberTask(false);
                    swapAction.setTaskReminderAt2(12);

                    if (rootJsonInner[0] != null && rootJsonInner[0].has("MonthReminder")) {
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

                } else if ((rootJsonInner[0] != null && rootJsonInner[0].getInt("FrequencyId") == 6) /*|| demoHabitTemplateModel.getHabitTemplate().getHabitFrequencyId() == 6*/)//yearly
                {
                    swapAction.setName("habit to break");////////////////////////////////

                    if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("February")) {
                        newAction.setIsFebruaryTask(true);
                        swapAction.setIsFebruaryTask(true);
                    } else {
                        newAction.setIsFebruaryTask(false);
                        swapAction.setIsFebruaryTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("May")) {
                        newAction.setIsMayTask(true);
                        swapAction.setIsMayTask(true);
                    } else {
                        newAction.setIsMayTask(false);
                        swapAction.setIsMayTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("September")) {
                        newAction.setIsSeptemberTask(true);
                        swapAction.setIsSeptemberTask(true);
                    } else {
                        newAction.setIsSeptemberTask(false);
                        swapAction.setIsSeptemberTask(false);
                    }
                    if (rootJsonInner[0] != null && rootJsonInner[0].getBoolean("December")) {
                        newAction.setIsDecemberTask(true);
                        swapAction.setIsDecemberTask(true);
                    } else {
                        newAction.setIsDecemberTask(false);
                        swapAction.setIsDecemberTask(false);
                    }
                    newAction.setTaskReminderAt2(12);
                    newAction.setTaskMonthReminder(1);
                    newAction.setIsSundayTask(false);
                    if(rootJsonInner[0] != null)
                    {
                        newAction.setIsJulyTask(rootJsonInner[0].getBoolean("July"));
                        newAction.setIsAprilTask(rootJsonInner[0].getBoolean("April"));
                        newAction.setIsAugustTask(rootJsonInner[0].getBoolean("August"));
                        newAction.setIsJuneTask(rootJsonInner[0].getBoolean("June"));
                        newAction.setIsNovemberTask(rootJsonInner[0].getBoolean("November"));
                        newAction.setIsOctoberTask(rootJsonInner[0].getBoolean("October"));
                        newAction.setIsMarchTask(rootJsonInner[0].getBoolean("March"));
                        newAction.setIsJanuaryTask(rootJsonInner[0].getBoolean("January"));
                    }else {
                        newAction.setIsJulyTask(false);
                        newAction.setIsAprilTask(false);
                        newAction.setIsAugustTask(false);
                        newAction.setIsJuneTask(false);
                        newAction.setIsNovemberTask(false);
                        newAction.setIsOctoberTask(false);
                        newAction.setIsMarchTask(false);
                        newAction.setIsJanuaryTask(false);
                    }
                    newAction.setIsWednesdayTask(false);
                    newAction.setIsSaturdayTask(false);
                    newAction.setTaskReminderAt1(12);
                    newAction.setIsMondayTask(false);
                    newAction.setIsThursdayTask(false);
                    newAction.setIsTuesdayTask(false);
                    newAction.setIsFridayTask(false);

                    swapAction.setTaskReminderAt2(12);
                    swapAction.setTaskMonthReminder(1);
                    swapAction.setIsSundayTask(false);
                    if(rootJsonInner[0] != null)
                    {
                        swapAction.setIsJulyTask(rootJsonInner[0].getBoolean("July"));
                        swapAction.setIsAprilTask(rootJsonInner[0].getBoolean("April"));
                        swapAction.setIsAugustTask(rootJsonInner[0].getBoolean("August"));
                        swapAction.setIsJuneTask(rootJsonInner[0].getBoolean("June"));
                        swapAction.setIsNovemberTask(rootJsonInner[0].getBoolean("November"));
                        swapAction.setIsOctoberTask(rootJsonInner[0].getBoolean("October"));
                        swapAction.setIsMarchTask(rootJsonInner[0].getBoolean("March"));
                        swapAction.setIsJanuaryTask(rootJsonInner[0].getBoolean("January"));
                    }else {
                        swapAction.setIsJulyTask(false);
                        swapAction.setIsAprilTask(false);
                        swapAction.setIsAugustTask(false);
                        swapAction.setIsJuneTask(false);
                        swapAction.setIsNovemberTask(false);
                        swapAction.setIsOctoberTask(false);
                        swapAction.setIsMarchTask(false);
                        swapAction.setIsJanuaryTask(false);
                    }

                    swapAction.setIsWednesdayTask(false);
                    swapAction.setIsSaturdayTask(false);
                    swapAction.setTaskReminderAt1(12);
                    swapAction.setIsMondayTask(false);
                    swapAction.setIsThursdayTask(false);
                    swapAction.setIsTuesdayTask(false);
                    swapAction.setIsFridayTask(false);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        habitSwap.setNewAction(newAction);

        habitSwap.setSwapAction(swapAction);

        saveNewBreakHabit(habitSwap);

    }
    private void saveNewBreakHabit(HabitSwap habitSwap) {

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
            //  globalHabitSwapModel.getSwapAction().setName("Not Completed");

            //  globalHabitSwapModel.getSwapAction().setName(edtHabitBreak.getText().toString());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Habit", habitSwap);

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

/*
    private void saveNewBreakHabit(HabitSwap habitSwap) {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());


            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Habit",habitSwap);

            Call<JsonObject> jsonObjectCall = finisherService.addUpdateHabitSwap(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if(response.body()!=null && response.body().get("SuccessFlag").getAsBoolean())
                    {
                        Util.showToast(getActivity(),"Successfully Saved");
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());

                        if(Util.HABIT_ADDED_FROM_TODAY_PAGE){
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }else {
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
*/


}