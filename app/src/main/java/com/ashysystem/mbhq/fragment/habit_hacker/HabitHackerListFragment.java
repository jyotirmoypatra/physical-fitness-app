package com.ashysystem.mbhq.fragment.habit_hacker;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.activity.WebViewActivity;
import com.ashysystem.mbhq.adapter.HabitHackerListAdapter;
import com.ashysystem.mbhq.adapter.HabitHackerListAdapter_completed;
import com.ashysystem.mbhq.adapter.HabitHackerListAdapter_hidden;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap_completed;
import com.ashysystem.mbhq.model.habit_hacker.SearchUserHabitSwapsModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactory;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForHabitCalendar;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitCalendarViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitEditViewModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.view.drag_drop.ItemMoveCallback;
import com.ashysystem.mbhq.view.drag_drop.StartDragListener;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HabitHackerListFragment extends Fragment  implements StartDragListener{

    public ViewModelFactoryForHabitCalendar mViewModelFactory;
    public HabitCalendarViewModel mViewModel;
    public ViewModelFactory mViewModelFactoryEdit;
    public HabitEditViewModel mViewModelEdit;
    public CompositeDisposable mDisposable = new CompositeDisposable();
    ImageView imgInfoHabit, imgFilterHabit, imgManualTick;
    RelativeLayout rl_active,rl_hidden,rl_completed,rlHabitPercentage, rlAddHabitSwap, rlLoadingNoDataFound, rlManualTick;
    TextView txtLoading;
    RelativeLayout todayHabbit;
    RecyclerView recyclerHabitList,recyclerHabitList_hidden,recyclerHabitList_completed;
    ImageView imgHabitAdd;
    ProgressDialog progressDialog;
    SharedPreference sharedPreference;
    FinisherServiceImpl finisherService;
    Toolbar toolbar;
    View globalView = null;
    String boolActive1 = "";
    String boolCompleted1 = "";
    String boolHidden1 = "";
    Integer HABIT_STATUS_ALL = 0;
    Integer HABIT_STATUS_ACTIVE = 1;
    Integer HABIT_STATUS_COMPLETED = 2;
    Integer HABIT_STATUS_SNOOZE = 3;
    Integer HABIT_PERCENT_ALLTIME = 0;
    Integer HABIT_PERCENT_CURRENTYEAR = 1;
    Integer HABIT_PERCENT_CURRENTQUARTER = 2;
    Integer HABIT_PERCENT_CURRENTMONTH = 3;
    List<HabitSwap> lstAll;
    List<HabitSwap> lstNowShowing;
    List<HabitSwap_completed> lstNowShowing_completed;
    List<HabitSwap> lstManualDrag;
    ItemTouchHelper touchHelper;
    boolean boolActive = false;
    boolean boolCompleted = false;
    boolean boolHidden = false;
    boolean boolHighToLow = false;
    boolean boolManual = false;
    boolean boolShowAllHabit = false;
    ImageView imgBigPlus;
    List<String> lstSavedSearchResults;
    SwipeRefreshLayout swipeLayout;
    ExpandableListView expandableListView;
    boolean globalWeeklyOverViewBool = false;
    boolean globalAutoCompleteBool = false;
    private boolean refresh = false;
    private ViewModelFactory mViewModelFactoryAno;
    private HabitEditViewModel mViewModelDel;
    private ViewModelFactoryForHabitCalendar mViewModelFactoryCalendar;
    private HabitCalendarViewModel mViewModelCalendar;

    public HabitHackerListFragment setLstManualDrag(List<HabitSwap> lstManualDrag) {
        this.lstManualDrag = lstManualDrag;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        if (globalView == null) {
            globalView = inflater.inflate(R.layout.fragment_habit_hacker_list, null);

            funToolBar();
            swipeLayout = globalView.findViewById(R.id.swipeLayout);
            imgInfoHabit = globalView.findViewById(R.id.imgInfoHabit);
            imgFilterHabit = globalView.findViewById(R.id.imgFilterHabit);
            rlHabitPercentage = globalView.findViewById(R.id.rlHabitPercentage);
            rlAddHabitSwap = globalView.findViewById(R.id.rlAddHabitSwap);
            rlLoadingNoDataFound = globalView.findViewById(R.id.rlLoadingNoDataFound);
            rlManualTick = globalView.findViewById(R.id.rlManualTick);
            txtLoading = globalView.findViewById(R.id.txtLoading);
            recyclerHabitList = globalView.findViewById(R.id.recyclerHabitList);
            recyclerHabitList_hidden= globalView.findViewById(R.id.recyclerHabitList_hidden);
            recyclerHabitList_completed= globalView.findViewById(R.id.recyclerHabitList_completed);
            rl_active= globalView.findViewById(R.id.rl_active);
            rl_hidden= globalView.findViewById(R.id.rl_hidden);
            rl_completed= globalView.findViewById(R.id.rl_completed);
            imgHabitAdd = globalView.findViewById(R.id.imgHabitAdd);
            imgManualTick = globalView.findViewById(R.id.imgManualTick);
            imgBigPlus = globalView.findViewById(R.id.imgBigPlus);

            todayHabbit = globalView.findViewById(R.id.todayHabbit);

            sharedPreference = new SharedPreference(getActivity());
            finisherService = new FinisherServiceImpl(getActivity());

            mViewModelFactory = Injection.provideViewModelFactoryHabitCalendar(getActivity());
           // mViewModel = ViewModelProviders.of(HabitHackerListFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);
            mViewModel = new ViewModelProvider(HabitHackerListFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);

            mViewModelFactoryEdit = Injection.provideViewModelFactory(getActivity());
           // mViewModelEdit = ViewModelProviders.of(HabitHackerListFragment.this, mViewModelFactoryEdit).get(HabitEditViewModel.class);
            mViewModelEdit = new ViewModelProvider(HabitHackerListFragment.this, mViewModelFactoryEdit).get(HabitEditViewModel.class);

            mViewModelFactoryAno = Injection.provideViewModelFactory(getActivity());
           // mViewModelDel = ViewModelProviders.of(this, mViewModelFactoryAno).get(HabitEditViewModel.class);
            mViewModelEdit = new ViewModelProvider(HabitHackerListFragment.this, mViewModelFactoryAno).get(HabitEditViewModel.class);

            mViewModelFactoryCalendar = Injection.provideViewModelFactoryHabitCalendar(getActivity());
           // mViewModelCalendar = ViewModelProviders.of(this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);
            mViewModelCalendar = new ViewModelProvider(HabitHackerListFragment.this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);

            lstAll = new ArrayList<>();

            recyclerHabitList.setNestedScrollingEnabled(false);
            recyclerHabitList.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerHabitList_completed.setNestedScrollingEnabled(false);
            recyclerHabitList_completed.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerHabitList_hidden.setNestedScrollingEnabled(false);
            recyclerHabitList_hidden.setLayoutManager(new LinearLayoutManager(getActivity()));


            imgInfoHabit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //openInfoDialog();
                }
            });
            rlAddHabitSwap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);

                }
            });

            imgHabitAdd.setOnClickListener(view -> {

                Util.edittext ="";
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
                Util.HABIT_ADDED_FROM_TODAY_PAGE = false;
//                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
//                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);

                if(null!=getActivity()) {
                    if (Util.checkConnection(getActivity())) {
                        Util.frompopularhabitlist="";
                        //((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
                        ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
                    } else {
                        Util.showToast(getActivity(), Util.networkMsg);
                    }
                }

            });

            imgBigPlus.setOnClickListener(view -> {
                Util.HABIT_ADDED_FROM_TODAY_PAGE = false;
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
            });

            imgFilterHabit.setOnClickListener(view -> {

                openDialogForFilter();

            });

            imgManualTick.setOnClickListener(view -> {
                if (lstManualDrag == null) {
                    Util.showToast(getActivity(), "Please sort a Habit Manually");
                } else {
                    updateHabitSwapManualOrderApiCall();
                }
            });

            //added by jyotirmoy patra 16.09.22

            todayHabbit.setOnClickListener(view -> {
                // Bundle bundle = new Bundle();
                ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwoFragment", null);
            });

            //ended by jyotirmoy patra 16.09.22

            if (!sharedPreference.read("SAVED_HABIT_FILTER_VALUES", "").equals("")) {
                Log.i("SAVED_HABIT_FILTER_VALUES","0");
                Gson gson = new Gson();
                lstSavedSearchResults = gson.fromJson(sharedPreference.read("SAVED_HABIT_FILTER_VALUES", ""), new TypeToken<List<String>>() {
                }.getType());

                if (lstSavedSearchResults.contains("ACTIVE")) {
                    Log.i("SAVED_HABIT_FILTER_VALUES","20");
                    boolActive = true;
                } else {
                    Log.i("SAVED_HABIT_FILTER_VALUES","21");
                    boolActive = false;
                }
                if (lstSavedSearchResults.contains("HIDDEN")) {
                    Log.i("SAVED_HABIT_FILTER_VALUES","22");
                    boolHidden = true;
                } else {
                    Log.i("SAVED_HABIT_FILTER_VALUES","23");
                    boolHidden = false;
                }
                if (lstSavedSearchResults.contains("COMPLETED")) {
                    Log.i("SAVED_HABIT_FILTER_VALUES","24");
                    boolCompleted = true;
                } else {
                    Log.i("SAVED_HABIT_FILTER_VALUES","25");
                    boolCompleted = false;
                }

            } else {

                Log.i("SAVED_HABIT_FILTER_VALUES","1");
                lstSavedSearchResults = new ArrayList<>();

                lstSavedSearchResults.add("ACTIVE");
                if (lstSavedSearchResults.contains("ACTIVE")) {
                    Log.i("SAVED_HABIT_FILTER_VALUES","10");
                    boolActive = true;
                } else {
                    Log.i("SAVED_HABIT_FILTER_VALUES","11");
                    boolActive = false;
                }

            }

         /*   if(null!=getActivity()){
                SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
                 boolActive1= sharedPreference.read("boolActive", "");
                 boolCompleted1= sharedPreference.read("boolCompleted", "");
                 boolHidden1= sharedPreference.read("boolHidden", "");

            }
            if("true".equalsIgnoreCase(boolActive1)){
                boolActive=true;
            }else if("false".equalsIgnoreCase(boolActive1)){
                boolActive=false;
            }else{
                boolActive=true;
            }

            if("true".equalsIgnoreCase(boolCompleted1)){
                boolCompleted=true;
            }else if("false".equalsIgnoreCase(boolCompleted1)){
                boolCompleted=false;
            }else{
                boolCompleted=false;
            }

            if("true".equalsIgnoreCase(boolHidden1)){
                boolHidden=true;
            }else if("false".equalsIgnoreCase(boolHidden1)){
                boolHidden=false;
            }else{
                boolHidden=false;
            }*/
            SearchUserHabitSwapsApiCall(HABIT_STATUS_ALL, boolActive, boolCompleted, boolHidden);
//            SearchUserHabitSwapsApiCall(HABIT_STATUS_ALL, boolActive, boolCompleted, boolHidden);

            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //webCommunity.loadUrl(new SharedPreference(getActivity()).read("COMMUNITY_URL",""));
                    try {
                        lstAll.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        if(null!=lstNowShowing)
                            lstNowShowing.clear();
                        if(null!=lstNowShowing_completed)
                            lstNowShowing_completed.clear();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    recyclerHabitList.setAdapter(null);
                    recyclerHabitList_hidden.setAdapter(null);
                    recyclerHabitList_completed.setAdapter(null);
                    refresh = true;
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                    SearchUserHabitSwapsApiCall(HABIT_STATUS_ALL, boolActive, boolCompleted, boolHidden);
                    swipeLayout.setRefreshing(false);
                }
            });

            rlHabitPercentage.setOnClickListener(view -> {
                openDialogForPercentage();
            });

            return globalView;
        } else {
            funToolBar();
            return globalView;
        }
    }

    private void openDialogForPercentage() {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_habit_percentage);
        dialog.setCancelable(false);

        RelativeLayout rlAllTime = dialog.findViewById(R.id.rlAllTime);
        RelativeLayout rlThisYear = dialog.findViewById(R.id.rlThisYear);
        RelativeLayout rlThisQuater = dialog.findViewById(R.id.rlThisQuater);
        RelativeLayout rlThisMonth = dialog.findViewById(R.id.rlThisMonth);
        RelativeLayout rlCancel = dialog.findViewById(R.id.rlCancel);
        RelativeLayout rlTopTransparent = dialog.findViewById(R.id.rlTopTransparent);
        ImageView imgAllTime = dialog.findViewById(R.id.imgAllTime);
        ImageView imgThisYear = dialog.findViewById(R.id.imgThisYear);
        ImageView imgThisQuater = dialog.findViewById(R.id.imgThisQuater);
        ImageView imgThisMonth = dialog.findViewById(R.id.imgThisMonth);

        if (sharedPreference.read("HABIT_PERCENTAGE_VALUE", "").equals("")) {
            sharedPreference.write("HABIT_PERCENTAGE_VALUE", "", 0 + "");
            imgAllTime.setImageResource(R.drawable.checked_blue);
            imgThisYear.setImageResource(R.drawable.unchecked_blue);
            imgThisQuater.setImageResource(R.drawable.unchecked_blue);
            imgThisMonth.setImageResource(R.drawable.unchecked_blue);
        } else {
            if (sharedPreference.read("HABIT_PERCENTAGE_VALUE", "").equals(HABIT_PERCENT_ALLTIME.toString())) {
                imgAllTime.setImageResource(R.drawable.checked_blue);
                imgThisYear.setImageResource(R.drawable.unchecked_blue);
                imgThisQuater.setImageResource(R.drawable.unchecked_blue);
                imgThisMonth.setImageResource(R.drawable.unchecked_blue);
            } else if (sharedPreference.read("HABIT_PERCENTAGE_VALUE", "").equals(HABIT_PERCENT_CURRENTYEAR.toString())) {
                imgAllTime.setImageResource(R.drawable.unchecked_blue);
                imgThisYear.setImageResource(R.drawable.checked_blue);
                imgThisQuater.setImageResource(R.drawable.unchecked_blue);
                imgThisMonth.setImageResource(R.drawable.unchecked_blue);
            } else if (sharedPreference.read("HABIT_PERCENTAGE_VALUE", "").equals(HABIT_PERCENT_CURRENTQUARTER.toString())) {
                imgAllTime.setImageResource(R.drawable.unchecked_blue);
                imgThisYear.setImageResource(R.drawable.unchecked_blue);
                imgThisQuater.setImageResource(R.drawable.checked_blue);
                imgThisMonth.setImageResource(R.drawable.unchecked_blue);
            } else if (sharedPreference.read("HABIT_PERCENTAGE_VALUE", "").equals(HABIT_PERCENT_CURRENTMONTH.toString())) {
                imgAllTime.setImageResource(R.drawable.unchecked_blue);
                imgThisYear.setImageResource(R.drawable.unchecked_blue);
                imgThisQuater.setImageResource(R.drawable.unchecked_blue);
                imgThisMonth.setImageResource(R.drawable.checked_blue);
            }
        }

        rlTopTransparent.setOnClickListener(view -> {
            dialog.dismiss();
        });
        rlCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        rlAllTime.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("HABIT_PERCENTAGE_VALUE", "", HABIT_PERCENT_ALLTIME.toString());
            updateHabitStatsPreferenceApiCall(HABIT_PERCENT_ALLTIME);
        });
        rlThisYear.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("HABIT_PERCENTAGE_VALUE", "", HABIT_PERCENT_CURRENTYEAR.toString());
            updateHabitStatsPreferenceApiCall(HABIT_PERCENT_CURRENTYEAR);
        });
        rlThisQuater.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("HABIT_PERCENTAGE_VALUE", "", HABIT_PERCENT_CURRENTQUARTER.toString());
            updateHabitStatsPreferenceApiCall(HABIT_PERCENT_CURRENTQUARTER);
        });
        rlThisMonth.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("HABIT_PERCENTAGE_VALUE", "", HABIT_PERCENT_CURRENTMONTH.toString());
            updateHabitStatsPreferenceApiCall(HABIT_PERCENT_CURRENTMONTH);
        });

        dialog.show();
    }

    private void updateHabitStatsPreferenceApiCall(Integer preferenceType) {
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("PreferenceType", preferenceType);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> updateHabitStatsPreference = finisherService.updateHabitStatsPreference(hashReq);
            updateHabitStatsPreference.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response != null && response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        try {
                            lstAll.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            lstNowShowing.clear();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        recyclerHabitList.setAdapter(null);
                        refresh = true;
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                        SearchUserHabitSwapsApiCall(HABIT_STATUS_ALL, boolActive, boolCompleted, boolHidden);
                        swipeLayout.setRefreshing(false);
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

    private void funRefresh(Boolean boolActive,Boolean boolCompleted,Boolean boolHidden) {
        if (boolActive || boolHidden || boolCompleted) {
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
        }

        if (boolHighToLow) {
            // imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_tick);
            boolHighToLow = true;
            boolManual = false;
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            rlHabitPercentage.setVisibility(View.VISIBLE);
            rlManualTick.setVisibility(View.GONE);
            lstManualDrag = null;
            // imgFilterHabit.setImageResource(R.drawable.mbhq_filter_green);
        } else {
            //imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolManual) {
            boolHighToLow = false;
            boolManual = true;

            rlHabitPercentage.setVisibility(View.GONE);
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            blink();
            //  imgFilterHabit.setImageResource(R.drawable.mbhq_filter_green);
        } else {
            //imgManual.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

    }


    private void updateHabitSwapManualOrderApiCall() {

        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            List<Integer> lstHabitIds = new ArrayList<>();
            for (int i = 0; i < lstManualDrag.size(); i++) {
                lstHabitIds.add(lstManualDrag.get(i).getHabitId());
            }


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("HabitIds", lstHabitIds);
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> userHabitSwapsModelCall = finisherService.updateHabitSwapManualOrder(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.showToast(getActivity(), "Successfully Saved");
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        }
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

    private void openDialogForFilter() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_habit_filter_listing);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        RelativeLayout rlInProgress = dialog.findViewById(R.id.rlInProgress);
        RelativeLayout rlHidden = dialog.findViewById(R.id.rlHidden);
        RelativeLayout rlCompleted = dialog.findViewById(R.id.rlCompleted);
        RelativeLayout rlHighToLow = dialog.findViewById(R.id.rlHighToLow);
        RelativeLayout rlManual = dialog.findViewById(R.id.rlManual);
        RelativeLayout rlShowResults = dialog.findViewById(R.id.rlShowResults);
        RelativeLayout rlClearAll = dialog.findViewById(R.id.rlClearAll);
        RelativeLayout rlTransparent = dialog.findViewById(R.id.rlTransparent);
        RelativeLayout rlPrint = dialog.findViewById(R.id.rlPrint);
        RelativeLayout rlSimple = dialog.findViewById(R.id.rlSimple);
        RelativeLayout rlCreateAndBreak = dialog.findViewById(R.id.rlCreateAndBreak);
        RelativeLayout rlWeeklyOverView = dialog.findViewById(R.id.rlWeeklyOverView);

        RelativeLayout rlShowAllHabits = dialog.findViewById(R.id.rlShowAllHabits); //added by jyotirmoy patra 10.10.22


        ImageView imgInProgress = dialog.findViewById(R.id.imgInProgress);
        ImageView imgHidden = dialog.findViewById(R.id.imgHidden);
        ImageView imgCompleted = dialog.findViewById(R.id.imgCompleted);
        ImageView imgHighToLow = dialog.findViewById(R.id.imgHighToLow);
        ImageView imgManual = dialog.findViewById(R.id.imgManual);
        ImageView imgSimple = dialog.findViewById(R.id.imgSimple);

        ImageView imgAllHabits = dialog.findViewById(R.id.imgAllHabits); //added by jyotirmoy patra 10.10.22


        ImageView imgCreateAndBreak = dialog.findViewById(R.id.imgCreateAndBreak);
        ImageView imgWeeklyOverView = dialog.findViewById(R.id.imgWeeklyOverView);
        RelativeLayout rlAutoComplete = dialog.findViewById(R.id.rlAutoComplete);
        ImageView imgAutoComplete = dialog.findViewById(R.id.imgAutoComplete);

        if (boolActive) {
            imgInProgress.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgInProgress.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolHidden) {
            imgHidden.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgHidden.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolCompleted) {
            imgCompleted.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgCompleted.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolHighToLow) {
            imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolManual) {
            imgManual.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgManual.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (globalWeeklyOverViewBool) {
            imgWeeklyOverView.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgWeeklyOverView.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (globalAutoCompleteBool) {
            imgAutoComplete.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgAutoComplete.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (sharedPreference.read("HABIT_VIEW_SIMPLE", "").equals("")) {
            imgSimple.setBackgroundResource(R.drawable.mbhq_ic_tick);
            imgCreateAndBreak.setBackgroundResource(R.drawable.mbhq_ic_untick);
        } else if (sharedPreference.read("HABIT_VIEW_SIMPLE", "").equals("TRUE")) {
            imgSimple.setBackgroundResource(R.drawable.mbhq_ic_tick);
            imgCreateAndBreak.setBackgroundResource(R.drawable.mbhq_ic_untick);
        } else if (sharedPreference.read("HABIT_VIEW_SIMPLE", "").equals("FALSE")) {
            imgSimple.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgCreateAndBreak.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }

        // Log.i("HABIT_VIEW_HIGH",sharedPreference.read("HABIT_VIEW_HIGH", ""));


        if (sharedPreference.read("HABIT_VIEW_HIGH", "").equals("")) {
            imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgManual.setBackgroundResource(R.drawable.mbhq_ic_untick);

        } else if (sharedPreference.read("HABIT_VIEW_HIGH", "").equals("TRUE")) {
            imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_tick);
            imgManual.setBackgroundResource(R.drawable.mbhq_ic_untick);
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            boolHighToLow = true;
            boolManual = false;
            rlHabitPercentage.setVisibility(View.VISIBLE);
            rlManualTick.setVisibility(View.GONE);
            lstManualDrag = null;

        } else if (sharedPreference.read("HABIT_VIEW_HIGH", "").equals("FALSE")) {
            imgHighToLow.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgManual.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            boolHighToLow = false;
            boolManual = true;
            sharedPreference.write("HABIT_VIEW_HIGH", "", "FALSE");
            rlHabitPercentage.setVisibility(View.GONE);
            blink();
        }



        rlHighToLow.setOnClickListener(view -> {
            boolHighToLow = true;
            boolManual = false;
            dialog.dismiss();
            sharedPreference.write("HABIT_VIEW_HIGH", "", "TRUE");
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            rlHabitPercentage.setVisibility(View.VISIBLE);
            rlManualTick.setVisibility(View.GONE);
            lstManualDrag = null;
            imgFilterHabit.setImageResource(R.drawable.mbhq_filter_green);
        });

        rlManual.setOnClickListener(view -> {
            boolHighToLow = false;
            boolManual = true;
            dialog.dismiss();
            sharedPreference.write("HABIT_VIEW_HIGH", "", "FALSE");
            rlHabitPercentage.setVisibility(View.GONE);
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            blink();
            imgFilterHabit.setImageResource(R.drawable.mbhq_filter_green);
        });



        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlTransparent.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlInProgress.setOnClickListener(view -> {
//            imgAllHabits.setBackgroundResource(R.drawable.mbhq_ic_untick);
//            boolShowAllHabit = false;
            if (!boolActive) {
                imgInProgress.setBackgroundResource(R.drawable.mbhq_ic_tick);
                boolActive = true;
            } else {
                imgInProgress.setBackgroundResource(R.drawable.mbhq_ic_untick);
                boolActive = false;
            }
        });

        //added  y jyotirmoy patra 10.10.22
        rlShowAllHabits.setOnClickListener(view -> {
            if (!boolShowAllHabit) {
                imgAllHabits.setBackgroundResource(R.drawable.mbhq_ic_tick);
                boolShowAllHabit = true;
            } else {
                imgAllHabits.setBackgroundResource(R.drawable.mbhq_ic_untick);
                boolShowAllHabit = false;
            }
        });

        rlHidden.setOnClickListener(view -> {
//            imgAllHabits.setBackgroundResource(R.drawable.mbhq_ic_untick);
//            boolShowAllHabit = false;
            if (!boolHidden) {
                imgHidden.setBackgroundResource(R.drawable.mbhq_ic_tick);
                boolHidden = true;
            } else {
                imgHidden.setBackgroundResource(R.drawable.mbhq_ic_untick);
                boolHidden = false;
            }
        });

        rlCompleted.setOnClickListener(view -> {
//            imgAllHabits.setBackgroundResource(R.drawable.mbhq_ic_untick);
//            boolShowAllHabit = false;
            if (!boolCompleted) {
                imgCompleted.setBackgroundResource(R.drawable.mbhq_ic_tick);
                boolCompleted = true;
            } else {
                imgCompleted.setBackgroundResource(R.drawable.mbhq_ic_untick);
                boolCompleted = false;
            }
        });



        rlSimple.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("HABIT_VIEW_SIMPLE", "", "TRUE");
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
        });

        rlCreateAndBreak.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("HABIT_VIEW_SIMPLE", "", "FALSE");
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
        });

        rlWeeklyOverView.setOnClickListener(view -> {
            updateWeeklyOverviewFlagApiCall(!globalWeeklyOverViewBool, imgWeeklyOverView);
        });

        rlAutoComplete.setOnClickListener(view -> {
            updateAutoCompleteHabitFlag(!globalAutoCompleteBool, imgAutoComplete);
        });

        rlShowResults.setOnClickListener(view -> {
            dialog.dismiss();
           /* boolHidden = true;
            boolCompleted = true;
            boolActive = true;*/

            if (boolActive) {
                lstSavedSearchResults.add("ACTIVE");
            } else {

            }
            if (boolHidden) {
                lstSavedSearchResults.add("HIDDEN");
            } else {

            }
            if (boolCompleted) {
                lstSavedSearchResults.add("COMPLETED");

            } else {

            }

            Gson gson = new Gson();
            sharedPreference.write("SAVED_HABIT_FILTER_VALUES", "", gson.toJson(lstSavedSearchResults));
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            imgFilterHabit.setImageResource(R.drawable.mbhq_filter_green);
        });

        rlClearAll.setOnClickListener(view -> {

            dialog.dismiss();
            sharedPreference.write("HABIT_VIEW_HIGH", "", "");
            boolActive = false;
            boolCompleted = false;
            boolHidden = false;
            boolHighToLow = false;
            boolManual = false;
            boolShowAllHabit = false;
            makeListAndShowResult(boolActive,boolCompleted,boolHidden);
            rlHabitPercentage.setVisibility(View.VISIBLE);
            rlManualTick.setVisibility(View.GONE);
            lstManualDrag = null;
            imgFilterHabit.setImageResource(R.drawable.mbhq_filter);
            lstSavedSearchResults.clear();

            Gson gson = new Gson();
            sharedPreference.write("SAVED_HABIT_FILTER_VALUES", "", gson.toJson(lstSavedSearchResults));
            if (lstSavedSearchResults.size() == 0)
                sharedPreference.clear("SAVED_HABIT_FILTER_VALUES");
        });

        rlPrint.setOnClickListener(view -> {

            dialog.dismiss();

            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("searchText", "");

            ArrayList<Integer> lstStatus = new ArrayList<>();

            if (boolActive && !boolHidden && !boolCompleted) {
                lstStatus.add(HABIT_STATUS_ACTIVE);
            } else if (!boolActive && boolHidden && !boolCompleted) {
                lstStatus.add(HABIT_STATUS_SNOOZE);
            } else if (!boolActive && !boolHidden && boolCompleted) {
                lstStatus.add(HABIT_STATUS_COMPLETED);
            } else if (boolActive && boolHidden && !boolCompleted) {
                lstStatus.add(HABIT_STATUS_ACTIVE);
                lstStatus.add(HABIT_STATUS_SNOOZE);
            } else if (boolActive && !boolHidden && boolCompleted) {
                lstStatus.add(HABIT_STATUS_ACTIVE);
                lstStatus.add(HABIT_STATUS_COMPLETED);
            } else if (!boolActive && boolHidden && boolCompleted) {
                lstStatus.add(HABIT_STATUS_SNOOZE);
                lstStatus.add(HABIT_STATUS_COMPLETED);
            } else if (!boolActive && !boolHidden && !boolCompleted) {

            } else if (boolActive && boolHidden && boolCompleted) {
                lstStatus.add(HABIT_STATUS_ACTIVE);
                lstStatus.add(HABIT_STATUS_SNOOZE);
                lstStatus.add(HABIT_STATUS_COMPLETED);
            }

            hashReq.put("Status", lstStatus);
            if (boolHighToLow) {
                hashReq.put("OrderBy", 0);
            } else if (boolManual) {
                hashReq.put("OrderBy", 1);
            } else {
                hashReq.put("OrderBy", 1);
            }
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            printHabitHackerApiCall(hashReq);
        });

        dialog.show();
    }

    private void updateWeeklyOverviewFlagApiCall(boolean boolWeekly, ImageView imgWeekly) {

        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("WeeklyOverview", boolWeekly);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> updateWeeklyOverviewFlag = finisherService.updateWeeklyOverviewFlag(hashReq);
            updateWeeklyOverviewFlag.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        globalWeeklyOverViewBool = boolWeekly;
                        if (boolWeekly) {
                            imgWeekly.setBackgroundResource(R.drawable.mbhq_ic_tick);
                        } else {
                            imgWeekly.setBackgroundResource(R.drawable.mbhq_ic_untick);
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

    private void updateAutoCompleteHabitFlag(boolean boolWeekly, ImageView imgWeekly) {

        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("AutCompleteHabit", boolWeekly);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> updateWeeklyOverviewFlag = finisherService.updateAutoCompleteHabitFlag(hashReq);
            updateWeeklyOverviewFlag.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        globalAutoCompleteBool = boolWeekly;
                        if (boolWeekly) {
                            imgWeekly.setBackgroundResource(R.drawable.mbhq_ic_tick);
                        } else {
                            imgWeekly.setBackgroundResource(R.drawable.mbhq_ic_untick);
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

    private void makeListAndShowResult(Boolean boolActive,Boolean boolCompleted,Boolean boolHidden) {

        lstNowShowing = new ArrayList<>();

        if (lstSavedSearchResults != null) {
            lstSavedSearchResults.clear();
        }
        //added by jyotirmoy patra
   /*     if(boolShowAllHabit){
            Toast.makeText(getContext(),"show all load",Toast.LENGTH_SHORT).show();
            for (int i = 0; i < lstAll.size(); i++) {
                    lstNowShowing.add(lstAll.get(i));
            }
        }*/

        if (boolActive && !boolHidden && !boolCompleted) {

            lstSavedSearchResults.add("ACTIVE");
            for (int i = 0; i < lstAll.size(); i++) {
                if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {

                    lstNowShowing.add(lstAll.get(i));
                }
            }
        } else if (!boolActive && boolHidden && !boolCompleted) {
            lstSavedSearchResults.add("HIDDEN");
            for (int i = 0; i < lstAll.size(); i++) {
                if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {


                    lstNowShowing.add(lstAll.get(i));
                }
            }
        } else if (!boolActive && !boolHidden && boolCompleted) {
            lstSavedSearchResults.add("COMPLETED");
            for (int i = 0; i < lstAll.size(); i++) {
                if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                    lstNowShowing.add(lstAll.get(i));
                }
            }
        } else if (boolActive && boolHidden && !boolCompleted) {
            lstSavedSearchResults.add("ACTIVE");
            lstSavedSearchResults.add("HIDDEN");
            for (int i = 0; i < lstAll.size(); i++) {
                if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {

                    lstNowShowing.add(lstAll.get(i));
                } else if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {

                    lstNowShowing.add(lstAll.get(i));
                }
            }
        } else if (boolActive && !boolHidden && boolCompleted) {
            lstSavedSearchResults.add("ACTIVE");
            lstSavedSearchResults.add("COMPLETED");
            for (int i = 0; i < lstAll.size(); i++) {
                if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {

                    lstNowShowing.add(lstAll.get(i));
                } else if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                    lstNowShowing.add(lstAll.get(i));
                }
            }
        } else if (!boolActive && boolHidden && boolCompleted) {
            lstSavedSearchResults.add("HIDDEN");
            lstSavedSearchResults.add("COMPLETED");
            for (int i = 0; i < lstAll.size(); i++) {
                if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {

                    lstNowShowing.add(lstAll.get(i));
                } else if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                    lstNowShowing.add(lstAll.get(i));
                }
            }
        } else if (!boolActive && !boolHidden && !boolCompleted) {
            for (int i = 0; i < lstAll.size(); i++) {
                lstNowShowing.add(lstAll.get(i));
            }
        } else if (boolActive && boolHidden && boolCompleted) {
            lstSavedSearchResults.add("ACTIVE");
            lstSavedSearchResults.add("HIDDEN");
            lstSavedSearchResults.add("COMPLETED");
            for (int i = 0; i < lstAll.size(); i++) {

                lstNowShowing.add(lstAll.get(i));
            }
        }

        if (boolHighToLow && !boolManual) {
            Collections.sort(lstNowShowing, new Comparator<HabitSwap>() {
                @Override
                public int compare(HabitSwap lhs, HabitSwap rhs) {
                    return lhs.getNewAction().getOverallPerformance().compareTo(rhs.getNewAction().getOverallPerformance());
                }
            });
            Collections.reverse(lstNowShowing);
        } else if (!boolHighToLow && boolManual) {

        }
       /* Gson gson1 = new GsonBuilder().create();
        JsonArray myCustomArray = gson1.toJsonTree(lstNowShowing).getAsJsonArray();

        Log.e("array",myCustomArray.toString());
*/
        loadAdapter(lstNowShowing, boolManual,boolActive,boolCompleted,boolHidden);


        Gson gson = new Gson();
        sharedPreference.write("SAVED_HABIT_FILTER_VALUES", "", gson.toJson(lstSavedSearchResults));
        if (lstSavedSearchResults.size() == 0)
            sharedPreference.clear("SAVED_HABIT_FILTER_VALUES");

    }

    private void printHabitHackerApiCall(HashMap hashReq) {

        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");


            Call<JsonObject> jsonObjectCall = finisherService.emailUserHabitSwaps(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null && response.body().has("SuccessFlag")) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.showToast(getActivity(), "Email Sent Successfully");
                        }
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

    private void SearchUserHabitSwapsApiCall(Integer STATUS, boolean inprogress, boolean completed, boolean hidden) {

        boolActive = inprogress;
        boolCompleted = completed;
        boolHidden = hidden;

        if(boolActive){
            sharedPreference.write("boolActive", "", "true");
        }else{
            sharedPreference.write("boolActive", "", "false");
        }
        if(boolCompleted){
            sharedPreference.write("boolCompleted", "", "true");
        }else{
            sharedPreference.write("boolCompleted", "", "false");
        }
        if(boolHidden){
            sharedPreference.write("boolHidden", "", "true");
        }else{
            sharedPreference.write("boolHidden", "", "false");
        }

        lstAll.clear();
        //  lstNowShowing.clear();
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("status", STATUS);
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("OrderBy", 1);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            //UserCurrentDate=2022-10-10
            hashReq.put("UserCurrentDate", "2022-10-10");
            Call<SearchUserHabitSwapsModel> userHabitSwapsModelCall = finisherService.searchUserHabitSwaps(hashReq);
            Log.e("req", "reqchk+" + hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<SearchUserHabitSwapsModel>() {
                @Override
                public void onResponse(Call<SearchUserHabitSwapsModel> call, Response<SearchUserHabitSwapsModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().getSuccessFlag()) {
                        if (response.body().getHabitSwaps() != null && response.body().getHabitSwaps().size() > 0) {

                            lstAll = response.body().getHabitSwaps();
                            Log.e("respns", "respn+" + response.body().toString());
                            if (refresh) {
                                if (!boolActive && !boolCompleted && !boolHidden && !boolHighToLow & !boolManual) {
                                    imgFilterHabit.setImageResource(R.drawable.mbhq_filter);
                                    loadAdapter(lstAll, false,boolActive,boolCompleted,boolHidden);
                                } else
                                    funRefresh(boolActive,boolCompleted,boolHidden);
                            } else {
                                if (!sharedPreference.read("SAVED_HABIT_FILTER_VALUES", "").equals("")) {
                                    imgFilterHabit.setImageResource(R.drawable.mbhq_filter_green);

                                    if (lstNowShowing == null) {
                                        lstNowShowing = new ArrayList<>();
                                    }


                                    if (boolActive && !boolHidden && !boolCompleted) {
//                                        lstSavedSearchResults.add("ACTIVE");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {
                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                        }
                                    } else if (!boolActive && boolHidden && !boolCompleted) {
//                                        lstSavedSearchResults.add("HIDDEN");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {
                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                        }
                                    } else if (!boolActive && !boolHidden && boolCompleted) {
//                                        lstSavedSearchResults.add("COMPLETED");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                        }
                                    } else if (boolActive && boolHidden && !boolCompleted) {
//                                        lstSavedSearchResults.add("ACTIVE");
//                                        lstSavedSearchResults.add("HIDDEN");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {

                                                lstNowShowing.add(lstAll.get(i));
                                            } else if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {

                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                        }
                                    } else if (boolActive && !boolHidden && boolCompleted) {
//                                        lstSavedSearchResults.add("ACTIVE");
//                                        lstSavedSearchResults.add("COMPLETED");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {

                                                lstNowShowing.add(lstAll.get(i));
                                            } else if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                        }
                                    } else if (!boolActive && boolHidden && boolCompleted) {
//                                        lstSavedSearchResults.add("HIDDEN");
//                                        lstSavedSearchResults.add("COMPLETED");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {

                                                lstNowShowing.add(lstAll.get(i));
                                            } else if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                        }
                                    } else if (!boolActive && !boolHidden && !boolCompleted) {
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            lstNowShowing.add(lstAll.get(i));
                                        }
                                    } else if (boolActive && boolHidden && boolCompleted) {
//                                        lstSavedSearchResults.add("ACTIVE");
//                                        lstSavedSearchResults.add("HIDDEN");
//                                        lstSavedSearchResults.add("COMPLETED");
                                        for (int i = 0; i < lstAll.size(); i++) {
                                            if (lstAll.get(i).getStatus() == HABIT_STATUS_SNOOZE) {

                                                lstNowShowing.add(lstAll.get(i));
                                            } else if (lstAll.get(i).getStatus() == HABIT_STATUS_COMPLETED) {

                                                lstNowShowing.add(lstAll.get(i));
                                            } else if (lstAll.get(i).getStatus() == HABIT_STATUS_ACTIVE) {

                                                lstNowShowing.add(lstAll.get(i));
                                            }
                                            //  lstNowShowing.add(lstAll.get(i));
                                        }
                                    }


                                    loadAdapter(lstNowShowing, false,boolActive,boolCompleted,boolHidden);

                                } else {
                                    imgFilterHabit.setImageResource(R.drawable.mbhq_filter);
                                    loadAdapter(lstAll, false,boolActive,boolCompleted,boolHidden);
                                }

                            }

                        } else {
                            txtLoading.setText("NO DATA FOUND");
                            txtLoading.setVisibility(View.GONE);
                            imgBigPlus.setVisibility(View.VISIBLE);
                            if (sharedPreference.read("habbitFirstTime", "").equals("")) {
                                //openInfoDialog();
                            }
                        }
                        getWeeklyOverviewFlagApiCall();
                        getAutoCompleteHabitFlag();
                    }

                }

                @Override
                public void onFailure(Call<SearchUserHabitSwapsModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void getWeeklyOverviewFlagApiCall() {

        if (Connection.checkConnection(getActivity())) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> getCall = finisherService.getWeeklyOverviewFlag(hashReq);
            getCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        try {
                            globalWeeklyOverViewBool = response.body().get("WeeklyOverview").getAsBoolean();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });


        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void getAutoCompleteHabitFlag() {

        if (Connection.checkConnection(getActivity())) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> getCall = finisherService.getAutoCompleteHabitFlag(hashReq);
            getCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                        try {
                            globalAutoCompleteBool = response.body().get("AutoCompleteHabit").getAsBoolean();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });


        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    public void makearray(List<HabitSwap> habitSwaps,boolean showManual,Boolean boolActive,Boolean boolCompleted,Boolean boolHidden){
        lstNowShowing_completed = new ArrayList<>();
        ArrayList<HabitSwap> lsthidden = new ArrayList<>();
        ArrayList<HabitSwap> lstcompleted = new ArrayList<>();
        ArrayList<HabitSwap> lstinprogress = new ArrayList<>();
        for (int i = 0; i < habitSwaps.size(); i++) {
            if (habitSwaps.get(i).getStatus() == HABIT_STATUS_SNOOZE) {

                lsthidden.add(habitSwaps.get(i));
                //lstAll.get(i).setHabitSwap_hiddens(lsthidden);
                // lstNowShowing.add(lstAll.get(i));
            } else if (habitSwaps.get(i).getStatus() == HABIT_STATUS_COMPLETED) {
                lstcompleted.add(habitSwaps.get(i));
                //lstAll.get(i).setHabitSwap_completeds(lstcompleted);
                //lstNowShowing.add(lstAll.get(i));
            } else if (habitSwaps.get(i).getStatus() == HABIT_STATUS_ACTIVE) {
                lstinprogress.add(habitSwaps.get(i));
                // lstAll.get(i).setHabitSwap_inprogresss(lstinprogress);
                // lstNowShowing.add(lstAll.get(i));
            }

        }

        HabitSwap_completed completed=new HabitSwap_completed();
        completed.setHabitSwap_completeds(lstcompleted);
        completed.setHabitSwap_hiddens(lsthidden);
        completed.setHabitSwap_inprogresss(lstinprogress);
        lstNowShowing_completed.add(completed);
        Log.e("size_",String.valueOf(lstNowShowing_completed.get(0).getHabitSwap_inprogresss().size()));
        Log.e("size_",String.valueOf(lstNowShowing_completed.get(0).getHabitSwap_hiddens().size()));
        Log.e("size_",String.valueOf(lstNowShowing_completed.get(0).getHabitSwap_completeds().size()));

        if(lstNowShowing_completed.get(0).getHabitSwap_inprogresss().size()>0
                ||lstNowShowing_completed.get(0).getHabitSwap_completeds().size()>0
                ||lstNowShowing_completed.get(0).getHabitSwap_hiddens().size()>0){
            Log.e("called","block1");

            if(lstNowShowing_completed.get(0).getHabitSwap_inprogresss().size()>0){
                Log.e("called","block10");
                if(!boolActive){
                    rl_active.setVisibility(View.GONE);
                    recyclerHabitList.setVisibility(View.GONE);
                    HabitHackerListAdapter habitHackerListAdapter = new HabitHackerListAdapter(getActivity(), lstNowShowing_completed.get(0).getHabitSwap_inprogresss(), this, showManual, HabitHackerListFragment.this);
                    ItemTouchHelper.Callback callback = new ItemMoveCallback(habitHackerListAdapter);
                    touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerHabitList);
                    recyclerHabitList.setAdapter(habitHackerListAdapter);
                }else{
                    rl_active.setVisibility(View.VISIBLE);
                    recyclerHabitList.setVisibility(View.VISIBLE);
                    HabitHackerListAdapter habitHackerListAdapter = new HabitHackerListAdapter(getActivity(), lstNowShowing_completed.get(0).getHabitSwap_inprogresss(), this, showManual, HabitHackerListFragment.this);
                    ItemTouchHelper.Callback callback = new ItemMoveCallback(habitHackerListAdapter);
                    touchHelper = new ItemTouchHelper(callback);
                    touchHelper.attachToRecyclerView(recyclerHabitList);
                    recyclerHabitList.setAdapter(habitHackerListAdapter);
                }


            }else{
                Log.e("called","block11");
                recyclerHabitList.setAdapter(null);
                rl_active.setVisibility(View.GONE);
                recyclerHabitList.setVisibility(View.GONE);
            }


            if(lstNowShowing_completed.get(0).getHabitSwap_hiddens().size()>0){
                if(!boolHidden){
                    Log.e("called","block12");
                    rl_hidden.setVisibility(View.GONE);
                    recyclerHabitList_hidden.setVisibility(View.GONE);
                    HabitHackerListAdapter_hidden habitHackerListAdapter_hidden = new HabitHackerListAdapter_hidden(getActivity(), lstNowShowing_completed.get(0).getHabitSwap_hiddens(), this, showManual, HabitHackerListFragment.this);
                    ItemTouchHelper.Callback callback1 = new ItemMoveCallback(habitHackerListAdapter_hidden);
                    touchHelper = new ItemTouchHelper(callback1);
                    touchHelper.attachToRecyclerView(recyclerHabitList_hidden);
                    recyclerHabitList_hidden.setAdapter(habitHackerListAdapter_hidden);
                }else{
                    Log.e("called","block12");
                    rl_hidden.setVisibility(View.VISIBLE);
                    recyclerHabitList_hidden.setVisibility(View.VISIBLE);
                    HabitHackerListAdapter_hidden habitHackerListAdapter_hidden = new HabitHackerListAdapter_hidden(getActivity(), lstNowShowing_completed.get(0).getHabitSwap_hiddens(), this, showManual, HabitHackerListFragment.this);
                    ItemTouchHelper.Callback callback1 = new ItemMoveCallback(habitHackerListAdapter_hidden);
                    touchHelper = new ItemTouchHelper(callback1);
                    touchHelper.attachToRecyclerView(recyclerHabitList_hidden);
                    recyclerHabitList_hidden.setAdapter(habitHackerListAdapter_hidden);
                }


            }else{
                Log.e("called","block13");
                recyclerHabitList_hidden.setAdapter(null);
                rl_hidden.setVisibility(View.GONE);
                recyclerHabitList_hidden.setVisibility(View.GONE);
            }

            if(lstNowShowing_completed.get(0).getHabitSwap_completeds().size()>0){
                if(!boolCompleted){
                    Log.e("called","block14");
                    rl_completed.setVisibility(View.GONE);
                    recyclerHabitList_completed.setVisibility(View.GONE);
                    HabitHackerListAdapter_completed habitHackerListAdapter_completed = new HabitHackerListAdapter_completed(getActivity(), lstNowShowing_completed.get(0).getHabitSwap_completeds(), this, showManual, HabitHackerListFragment.this);
                    ItemTouchHelper.Callback callback2 = new ItemMoveCallback(habitHackerListAdapter_completed);
                    touchHelper = new ItemTouchHelper(callback2);
                    touchHelper.attachToRecyclerView(recyclerHabitList_completed);
                    recyclerHabitList_completed.setAdapter(habitHackerListAdapter_completed);
                }else{
                    Log.e("called","block14");
                    rl_completed.setVisibility(View.VISIBLE);
                    recyclerHabitList_completed.setVisibility(View.VISIBLE);
                    HabitHackerListAdapter_completed habitHackerListAdapter_completed = new HabitHackerListAdapter_completed(getActivity(), lstNowShowing_completed.get(0).getHabitSwap_completeds(), this, showManual, HabitHackerListFragment.this);
                    ItemTouchHelper.Callback callback2 = new ItemMoveCallback(habitHackerListAdapter_completed);
                    touchHelper = new ItemTouchHelper(callback2);
                    touchHelper.attachToRecyclerView(recyclerHabitList_completed);
                    recyclerHabitList_completed.setAdapter(habitHackerListAdapter_completed);
                }


            }else{
                Log.e("called","block15");
                recyclerHabitList_completed.setAdapter(null);
                rl_completed.setVisibility(View.GONE);
                recyclerHabitList_completed.setVisibility(View.GONE);
            }

        }else{
            Log.e("called","block2");
            recyclerHabitList.setAdapter(null);
            recyclerHabitList_completed.setAdapter(null);
            recyclerHabitList_hidden.setAdapter(null);
            txtLoading.setText("NO DATA FOUND");
        }


    }
    private void loadAdapter(List<HabitSwap> habitSwaps, boolean showManual,Boolean boolActive,Boolean boolCompleted,Boolean boolHidden) {

        txtLoading.setVisibility(View.VISIBLE);
        imgBigPlus.setVisibility(View.GONE);

        if (habitSwaps.size() > 0) {
            if (sharedPreference.read("HABIT_VIEW_SIMPLE", "").equals("") || sharedPreference.read("HABIT_VIEW_SIMPLE", "").equals("TRUE")) {
                for (int i = 0; i < habitSwaps.size(); i++) {
                    habitSwaps.get(i).setBreakShowing(false);
                }
            } else if (sharedPreference.read("HABIT_VIEW_SIMPLE", "").equals("FALSE")) {
                for (int i = 0; i < habitSwaps.size(); i++) {
                    habitSwaps.get(i).setBreakShowing(true);
                }
            }

            txtLoading.setText("LOADING...");
            recyclerHabitList.setAdapter(null);
            recyclerHabitList_completed.setAdapter(null);
            recyclerHabitList_hidden.setAdapter(null);
            makearray(habitSwaps,showManual,boolActive,boolCompleted,boolHidden);
           /* HabitHackerListAdapter habitHackerListAdapter = new HabitHackerListAdapter(getActivity(), habitSwaps, this, showManual, HabitHackerListFragment.this);


            ItemTouchHelper.Callback callback = new ItemMoveCallback(habitHackerListAdapter);
            touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(recyclerHabitList);

            recyclerHabitList.setAdapter(habitHackerListAdapter);*/
        } else {
            recyclerHabitList.setAdapter(null);
            txtLoading.setText("NO DATA FOUND");
        }
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        if (sharedPreference.read("habbitFirstTime", "").equals("")) {
            //openInfoDialog();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).funTabBarforHabits();
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
    public void onStop() {
        super.onStop();
        mDisposable.clear();
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




    private void blink() {
        rlManualTick.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(500); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        imgManualTick.startAnimation(animation); //to start animation
    }

    private void openInfoDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_habbit); ///////////////////////////////////////
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        ImageView imgHabitAdd = dialog.findViewById(R.id.imgHabitAdd);
        RelativeLayout rlCreate = dialog.findViewById(R.id.rlCreate);
        RelativeLayout rlShowMeExamples = dialog.findViewById(R.id.rlShowMeExamples);
        RelativeLayout rlGotIt = dialog.findViewById(R.id.rlGotIt);

        rlShowMeExamples.setOnClickListener(view -> {
            dialog.dismiss();
        /*Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mindbodyhq.com/blogs/habit-hacking-inspo"));
        browserIntent.setPackage("com.android.chrome");
        startActivity(browserIntent);*/
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            String pdf = "https://squad-live.s3-ap-southeast-2.amazonaws.com/MbHQ+Happiness+Habit/Habit_change_examples.pdf";
            intent.putExtra("WEBVIEWURL", "http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
            startActivity(intent);
        });

        rlCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
            }
        });

        rlGotIt.setOnClickListener(view -> {
            dialog.dismiss();
        });

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
//        SharedPreference sharedPreference = new SharedPreference(getActivity());
//        sharedPreference.write("habbitFirstTime", "", "true");
    }

    public void saveNewBreakHabit(HabitSwap habitSwap) {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

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
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                        ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }

    }

    public void deleteHabitSwapApiCall(Integer HABIT_ID) {

        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

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
                                    mViewModelDel.deleteByHabitId(HABIT_ID);
                                })
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                    Completable.fromAction(() -> {
                                                mViewModelCalendar.deleteHabitCalendarById(HABIT_ID);
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


    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);
    }
}