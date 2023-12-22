package com.ashysystem.mbhq.fragment.habit_hacker;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.ashysystem.mbhq.adapter.HabbitCalendarTickUntickAdapter;
import com.ashysystem.mbhq.model.habit_hacker.HabbitCalendarModel;
import com.ashysystem.mbhq.model.habit_hacker.HabitBreakTickUntickModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactory;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForHabitCalendar;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitCalendarViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitEditViewModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HabbitCalendarTickUntickFragment extends Fragment {
    private RecyclerView rvList;
    HabbitCalendarModel habbitCalendarModel;
    RelativeLayout rlBack, rlEdit;
    TextView txtHabbitName, txtBreakName;
    String habbitName = "", breakName = "";
    int habbitId;
    private ImageView imgFilter;

    public boolean isNoteOpen() {
        return isNoteOpen;
    }

    public void setNoteOpen(boolean noteOpen) {
        isNoteOpen = noteOpen;
    }

    private boolean isNoteOpen = false;
    Toolbar toolbar;
    View vi = null;
    String strObj = "";

    public ViewModelFactoryForHabitCalendar mViewModelFactory;
    public HabitCalendarViewModel mViewModel;
    public ViewModelFactory mViewModelFactoryEdit;
    public HabitEditViewModel mViewModelEdit;

    public CompositeDisposable mDisposable = new CompositeDisposable();

    ArrayList<HabitBreakTickUntickModel> lstHabitBreakTickUntick;

    public RelativeLayout rlSaveHabitCancel,rlCancel,rlSaveHabitMultiple;

    Integer TASK_FREQUENCY = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (vi == null) {
            vi = inflater.inflate(R.layout.fragment_calendar_habiit_tick_untick, container, false);
            if (getArguments() != null) {
                if (getArguments().containsKey("data")) {
                    Gson gson = new Gson();
                    String strObj = getArguments().getString("data");
                    habbitCalendarModel = gson.fromJson(strObj, HabbitCalendarModel.class);
                }
                if (getArguments().containsKey("habbit_name")) {
                    habbitName = getArguments().getString("habbit_name");
                }
                if (getArguments().containsKey("break_name")) {
                    breakName = getArguments().getString("break_name");
                }
                if (getArguments().containsKey("id")) {
                    habbitId = getArguments().getInt("id");
                }
                if(getArguments().containsKey("TASK_FREQUENCY"))
                {
                    TASK_FREQUENCY = getArguments().getInt("TASK_FREQUENCY");
                }
            }

            mViewModelFactory = Injection.provideViewModelFactoryHabitCalendar(getActivity());
            mViewModel =new ViewModelProvider(HabbitCalendarTickUntickFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);
            mViewModelFactoryEdit = Injection.provideViewModelFactory(getActivity());
            mViewModelEdit =new ViewModelProvider(HabbitCalendarTickUntickFragment.this, mViewModelFactoryEdit).get(HabitEditViewModel.class);

            lstHabitBreakTickUntick = new ArrayList<>();

            initView(vi);
            funToolBar();
            return vi;
        } else {
            funToolBar();
            return vi;
        }

    }

    private void initView(View vi) {

        imgFilter = vi.findViewById(R.id.imgFilter);
        rlBack = vi.findViewById(R.id.rlBack);
        rvList = vi.findViewById(R.id.rvList);
        rlEdit = vi.findViewById(R.id.rlEdit);
        txtBreakName = vi.findViewById(R.id.txtBreakName);
        txtHabbitName = vi.findViewById(R.id.txtHabbitName);
        rlSaveHabitCancel = vi.findViewById(R.id.rlSaveHabitCancel);
        rlCancel = vi.findViewById(R.id.rlCancel);
        rlSaveHabitMultiple = vi.findViewById(R.id.rlSaveHabitMultiple);

        txtHabbitName.setText(habbitName);
        txtBreakName.setText(breakName);

        rlEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNoteOpen) {
                    setNoteOpen(false);
                    imgFilter.setImageResource(R.drawable.ic_down_arrow_green);
                } else {
                    setNoteOpen(true);
                    imgFilter.setImageResource(R.drawable.ic_up_arrow_green);
                }
                loadAdapter();

            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lstHabitBreakTickUntick.size() > 0)
                {
                    AlertDialogWithCustomButton customButton = new AlertDialogWithCustomButton(getActivity());
                    customButton.ShowDialog("Save Changes","Your changes will be lost if you don't save them",true,"SAVE","DON'T SAVE");

                    customButton.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {
                            updateMultipleTaskStatusApiCall("TRUE");
                        }

                        @Override
                        public void onCancel(String title) {
                            ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendarFragment", getArguments());
                        }
                    });

                }else
                {
                    ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendarFragment", getArguments());
                }
            }
        });

        rlCancel.setOnClickListener(view -> {
            rlSaveHabitCancel.setVisibility(View.GONE);
        });

        rlSaveHabitMultiple.setOnClickListener(view -> {
            updateMultipleTaskStatusApiCall("");
        });

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        loadAdapter();

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
                    ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendarFragment", getArguments());
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
                    if(lstHabitBreakTickUntick.size() > 0)
                    {
                        AlertDialogWithCustomButton customButton = new AlertDialogWithCustomButton(getActivity());
                        customButton.ShowDialog("Save Changes","Your changes will be lost if you don't save them",true,"SAVE","DON'T SAVE");

                        customButton.setAlertAction(new AlertDialogCustom.AlertResponse() {
                            @Override
                            public void onDone(String title) {
                                updateMultipleTaskStatusApiCall("TRUE");
                            }

                            @Override
                            public void onCancel(String title) {
                                ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendarFragment", getArguments());
                            }
                        });

                    }else
                    {
                        ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendarFragment", getArguments());
                    }

                    return true;

                }

                return false;
            }
        });
    }

    private void loadAdapter() {
        new AsyncTaskRunner(getActivity()).execute();
    }

    public void setHabitBreakTickUntick(Integer taskId, Boolean isDone)
    {
        boolean isAlredayInList = false;

        if(lstHabitBreakTickUntick.size() == 0)
        {
            HabitBreakTickUntickModel tickUntickModel = new HabitBreakTickUntickModel();
            tickUntickModel.setTaskId(taskId);
            tickUntickModel.setIsDone(isDone);
            lstHabitBreakTickUntick.add(tickUntickModel);
        }else
        {
            for(int i = 0; i < lstHabitBreakTickUntick.size();i++)
            {
                if(lstHabitBreakTickUntick.get(i).getTaskId() == taskId)
                {
                    isAlredayInList = true;
                    lstHabitBreakTickUntick.get(i).setIsDone(isDone);
                }
            }
            if(!isAlredayInList)
            {
                HabitBreakTickUntickModel tickUntickModel = new HabitBreakTickUntickModel();
                tickUntickModel.setTaskId(taskId);
                tickUntickModel.setIsDone(isDone);
                lstHabitBreakTickUntick.add(tickUntickModel);
            }
        }
    }

    public void updateMultipleTaskStatusApiCall(String isBackCLicked)
    {
        if (Connection.checkConnection(getActivity())) {

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("TaskDoneStatuses", lstHabitBreakTickUntick);
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> userHabitSwapsModelCall = finisherService.UpdateMultipleTaskStatus(hashReq);

            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if(response.body() != null && response.body().get("SuccessFlag").getAsBoolean())
                    {
                        Util.showToast(getActivity(),"Saved Successfully");
                        lstHabitBreakTickUntick.clear();
                        rlSaveHabitCancel.setVisibility(View.GONE);
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity) getActivity()).clearHashMapHabbit(habbitId);
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity) getActivity()).callTaskStatusForDateAPI("");

                        Completable.fromAction(() -> {
                            mViewModel.deleteHabitCalendarById(habbitId);
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {

                                }, throwable -> {

                                });

                        Completable.fromAction(() -> {
                            mViewModelEdit.deleteByHabitId(habbitId);
                        })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                }, throwable -> {
                                    Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                });

                        if(isBackCLicked.equals("TRUE"))
                        {
                            ((MainActivity) getActivity()).loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendarFragment", getArguments());
                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        public AsyncTaskRunner(Context context) {

        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            String str = "";


            Boolean hasCurrentDateEvent = false;
            Date dtNextEventDate = null;
            Boolean oneTime = false;
if(null!=habbitCalendarModel.getHabitStats()){
    for(int i = 0; i< habbitCalendarModel.getHabitStats().size(); i++)
    {
        SimpleDateFormat sdfG= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        String strCurrentDate = sdfG.format(new Date());

        String dateRcvd = habbitCalendarModel.getHabitStats().get(i).getTaskDate();
        Date dateObj=null;
        try {
            dateObj=sdfG.parse(dateRcvd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date finalDateObj = dateObj;

        if(!oneTime && finalDateObj.after(new Date()))
        {
            dtNextEventDate = finalDateObj;
            oneTime = true;
        }

        String strTaskDate = null;
        try {
            strTaskDate = sdfG.format(DateFormat.parse(habbitCalendarModel.getHabitStats().get(i).getTaskDate()));
            if(strCurrentDate.equals(strTaskDate))
            {
                hasCurrentDateEvent = true;
                break;
            }else
            {
                hasCurrentDateEvent = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
            if(hasCurrentDateEvent)
            {
                if(null!=habbitCalendarModel.getHabitStats()){
                    for(int i = 0; i< habbitCalendarModel.getHabitStats().size(); i++)
                    {
                        String dateRcvd = habbitCalendarModel.getHabitStats().get(i).getTaskDate();
                        Log.e("TASK_DATE",habbitCalendarModel.getHabitStats().get(i).getTaskDate() + ">>>>>>");
                        SimpleDateFormat sdfG=new SimpleDateFormat("yyyy-MM-dd");
                        Date dateObj=null;
                        try {
                            dateObj=sdfG.parse(dateRcvd);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Date finalDateObj = dateObj;

                        if(finalDateObj.after(new Date()))
                        {
                            habbitCalendarModel.getHabitStats().remove(i);
                            habbitCalendarModel.getBreakHabitStats().remove(i);
                            i = i - 1;
                        }
                    }

                }
            }else
            {
                if(dtNextEventDate != null)
                {

                    if(null!=habbitCalendarModel.getHabitStats()){
                        for(int i = 0; i< habbitCalendarModel.getHabitStats().size(); i++)
                        {
                            String dateRcvd = habbitCalendarModel.getHabitStats().get(i).getTaskDate();
                            Log.e("TASK_DATE",habbitCalendarModel.getHabitStats().get(i).getTaskDate() + ">>>>>>");
                            SimpleDateFormat sdfG=new SimpleDateFormat("yyyy-MM-dd");
                            Date dateObj=null;

                            try {
                                dateObj=sdfG.parse(dateRcvd);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date finalDateObj = dateObj;

                            if(finalDateObj.after(dtNextEventDate))
                            {
                                habbitCalendarModel.getHabitStats().remove(i);
                                habbitCalendarModel.getBreakHabitStats().remove(i);
                                i = i - 1;
                            }
                        }

                    }
                }
            }

            Collections.reverse(habbitCalendarModel.getHabitStats());
            Collections.reverse(habbitCalendarModel.getBreakHabitStats());
            String monthName = "";
            SimpleDateFormat sdfG = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMM");

            if(null!=habbitCalendarModel.getHabitStats()){
                for(int i = 0; i< habbitCalendarModel.getHabitStats().size(); i++)
                {
                    String dateRcvd = habbitCalendarModel.getHabitStats().get(i).getTaskDate();
                    Date date = null;
                    try {
                        date = sdfG.parse(dateRcvd);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    String monthString = monthFormat.format(date);
                    if(monthName.equals(""))
                    {
                        monthName = monthString;
                    }
                    else if(!monthName.equals(monthString))
                    {
                        monthName = monthString;
                        habbitCalendarModel.getHabitStats().get(i - 1).setShowThickView(true);
                    }
                }

            }


            return str;
        }

        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
            rvList.setAdapter(null);
            HabbitCalendarTickUntickAdapter habbitCalendarTickUntickAdapter = new HabbitCalendarTickUntickAdapter(getActivity(), habbitCalendarModel, isNoteOpen(), habbitId, strObj, HabbitCalendarTickUntickFragment.this);
            rvList.setAdapter(habbitCalendarTickUntickAdapter);
        }

        @Override
        protected void onProgressUpdate(String... text) {

        }
    }



}