package com.ashysystem.mbhq.fragment.habit_hacker;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.LogInActivity;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.activity.UserPaidStatusActivity;
import com.ashysystem.mbhq.activity.WebViewActivity;
import com.ashysystem.mbhq.adapter.mbhq_today.HabitHistoryAdapter;
import com.ashysystem.mbhq.adapter.mbhq_today.TodayHabitHackerAdpapter;
import com.ashysystem.mbhq.fragment.achievement.MyAchievementsFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitCalendarTickUntickFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitDetailsCalendarFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddFirstPage;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerListFragment;
import com.ashysystem.mbhq.model.GetGratitudeCacheExpiryTimeResponse;
import com.ashysystem.mbhq.model.GetGratitudeListModelInner;
import com.ashysystem.mbhq.model.GetPrompt;
import com.ashysystem.mbhq.model.GetUserPaidStatusModel;
import com.ashysystem.mbhq.model.TodayPage.GetAppHomePageValuesResponseModel;
import com.ashysystem.mbhq.model.habit_hacker.HabitBreakTickUntickModel;
import com.ashysystem.mbhq.model.habit_hacker.HabitSwap;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactory;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGratitude;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForHabitCalendar;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitCalendarViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.HabitEditViewModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MbhqTodayTwoFragment extends Fragment {
   /* String accesstype="3";
    String habit_access="true";
    String eq_access="true";
    String medi_access="true";
    String forum_access="true";
    String Live_access="true";
    String Test_acess="true";
    String Course_access="true";
*/

    String accesstype="";
    String habit_access="";
    String eq_access="";
    String medi_access="";
    String forum_access="";
    String Live_access="";
    String Test_acess="";
    String Course_access="";

    View globalView;
    String getprompt = "";


    ImageView imgAddHabit, imgWinTheWeekStats, selectAll;
    RecyclerView recyclerTodayHabit;
    RelativeLayout rlHabitList;

    ViewGroup rlSaveHabitCancel, rlCancel, rlSaveHabitMultiple;

    public ViewModelFactoryForHabitCalendar mViewModelFactory;
    public HabitCalendarViewModel mViewModel;
    public ViewModelFactory mViewModelFactoryEdit;
    public HabitEditViewModel mViewModelEdit;

    public CompositeDisposable mDisposable = new CompositeDisposable();

    TextView txtLoading, txtHeading;
    ImageView imgBigPlus;
    SimpleDateFormat strDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    SimpleDateFormat headingDateFormat = new SimpleDateFormat("MMM dd", Locale.ENGLISH);

    private ViewModelFactory mViewModelFactoryAno;
    private HabitEditViewModel mViewModelDel;
    private ViewModelFactoryForHabitCalendar mViewModelFactoryCalendar;
    private HabitCalendarViewModel mViewModelCalendar;

    private String forDate = null;
    private String forDate_optionclick = null;
    boolean isHabitDateCurrentDate = true;

    ArrayList<HabitBreakTickUntickModel> habitBreakTickUntickModels = new ArrayList<>();


    ArrayList<HabitSwap> habitSwapList = new ArrayList<>();

    private SwipeRefreshLayout swipeLayout;

    private String TAG = "MbhqTodayTwoFragment";

    //added  by jyotirmoy patra 17.10.22
    SharedPreference sharedPreference;
    File mFile;
    EditText edtAchieveDialog;
    FinisherServiceImpl finisherService;
    private GratitudeViewModel gratitudeViewModel;
    private ViewModelFactoryForGratitude factoryForGratitude;
    private String strDialogSelectionType = "";
    RelativeLayout rlTextOverPicInnerTOP;
    CardView cardViewBackgroundPicTOP;
    ImageView imgBackgroundPicTOP;
    LinearLayout llAddPicTOP;
    Button buttonChangeBackgroundImageTOP;
    Button buttonMoveTextBoxTOP;
    List<GetGratitudeListModelInner> lstShowAll, lstToday, lstThisMonth, lstSixMonthsAgo, lstOneYearAgo, lstPicture;
    private boolean refresh = false;
    File out;
    private String imgPath = "";
    SharedPreferences mPrefGratitude;
    SharedPreferences mPref;
    SharedPreferences sp;
    List<Date> datesList = new ArrayList<>();


    private boolean isTodayHabitShowing = false;
    public boolean allChecked = false;
    public boolean allCross = false;
    public boolean allUnchecked = false;
    TodayHabitHackerAdpapter habitHackerAdpapter;

    private String firstLoginTime = ""; /////////
    private String currentTime = ""; /////////
    private String habbitFirstTime = "";
    private String habbitFirstTime_firstpopup = "";
    ImageView img_notaccess;
    FrameLayout frm_notaccess;
    Button txt_notaccess;
    private void getAchievementsFromDB(){

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String gratitudeExpirationTime = sharedPreference.read("HABIT_EXPIRATION_DATE_TIME", "");
        String gratitudeInsertionTime = sharedPreference.read("HABIT_INSERTION_DATE_TIME", "");

        Log.i(TAG, "GRATITUDE_EXPIRATION_DATE_TIME => " + gratitudeExpirationTime);
        Log.i(TAG, "GRATITUDE_INSERTION_DATE_TIME => " + gratitudeInsertionTime);
        Log.i(TAG, "shouldGratitudeRenew" + "false");
        boolean shouldGratitudeRenew = false;//false;

        try {
            if ("2020-1-1 00:00:00".equalsIgnoreCase(gratitudeExpirationTime)
                // !dateFormatter.parse(gratitudeExpirationTime).after(dateFormatter.parse(gratitudeInsertionTime))
            ) {
                shouldGratitudeRenew = false;//true;
                Log.i(TAG, "shouldGratitudeRenew" + "false");
            }else{
                shouldGratitudeRenew = true;
                Log.i(TAG, "shouldGratitudeRenew" + "true");
            }

        } catch (Exception ex) {
            Log.i(TAG, "shouldGratitudeRenew" + "nill");
            ex.printStackTrace();
        }

        if (!shouldGratitudeRenew) {

            if(null!=getActivity()){
                SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
                String json= sharedPreference.read("my_list", "");
                ArrayList<HabitSwap> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<HabitSwap>>(){}.getType());
                if(null!=habitSwapList_ ){
                    if(habitSwapList_.size()>0 && Util.callhabitdatabase==true){

                        try{
                            habbitFirstTime = sharedPreference.read("habbitFirstTime","");
                            Log.e("habbitFirstTime", "onCreateView: " + habbitFirstTime );
                            firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
                            Log.e("FFLLLIIII", "onCreateView: " + firstLoginTime );

                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Calendar calendar = Calendar.getInstance();
                            currentTime = simpleDateFormat.format(calendar.getTime());
                            Log.e("CURRRTIIII", "onCreateView: " + currentTime );

                            Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
                            Date dateCurrentTime = simpleDateFormat.parse(currentTime);

                            if (dateCurrentTime != null) {
                                if(dateCurrentTime.after(dateFirstLoginDt) && "true".equals(habbitFirstTime)){
                                    Log.e("CHHHHTIMMMMMM", "onCreateView: " + "TRUE" );
                                    sharedPreference.write("habbitFirstTime", "", "false");

                                    // show popup here
                                    openInfoDialog();

                                }else {
                                    Log.e("CHHHHTIMMMMMM", "onCreateView: " + "FALSE" );
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }


                        Log.e("called_d","yes");
                        loadAdapter(habitSwapList_);
                        gratitudeDailyPopupCall();
                    }else{
                        Log.e("called_d","no");
                        Util.callhabitdatabase=true;
                        getGrowthHomapageApiCall(true);
                    }
                } else{
                    Log.e("called_d","no");
                    Util.callhabitdatabase=true;
                    getGrowthHomapageApiCall(true);

                }
            }

        }else{
            getGrowthHomapageApiCall(true);
        }

        if(null!=getActivity()){
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.GONE);
        }

        todayPageUpdateHandler.removeCallbacks(todayPageUpdateTimeTask);
        todayPageUpdateHandler.postDelayed(todayPageUpdateTimeTask, 100);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("called_order", "1");
        //  getdatelist();
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){
            }else{
                ((MainActivity) getActivity()).funDrawer1();

                checkGratitudeCacheExpiration();
                GetJounalPromptofDay();
            }
        }else{
            ((MainActivity) getActivity()).funDrawer1();

            checkGratitudeCacheExpiration();
            GetJounalPromptofDay();
        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){

            }else{
                if(null!=getActivity()){
                    getActivity().unregisterReceiver(networkChangeReceiver);

                }
            }
        }else{
            if(null!=getActivity()){
                getActivity().unregisterReceiver(networkChangeReceiver);

            }
        }


    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("state1111111111111111111111111111","onCreate2");


        sharedPreference = new SharedPreference(getActivity());
        habit_access=sharedPreference.read("HabitAccess","");
//        habit_access="false";
        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
//        accesstype="3";
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");


        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){
/*
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("User don't have access to use Habit") .setTitle("Efc");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if("Eq".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                            Util.isReloadTodayMainPage = true;
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).fungratitudeicon();
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                        }else if("meditation".equalsIgnoreCase(Util.sourcepage)){
                            Util.refresh_meditation="yes";
                            Util.refresh_gratitude="yes";
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).funMeditation();
                            Util.refresh_gratitude="yes";
                        }else if("course".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funDrawer1();
                            Util.refresh_gratitude="yes";
                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                            ((MainActivity) getActivity()).funCourse();
                        }else if("test".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).funDrawer1();

                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                            ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);

                        }else if("Live".equalsIgnoreCase(Util.sourcepage)){
                            ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                        }

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();*/
            }else{

                Util.sourcepage="habit";
                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                if(null!=getActivity()){
                    getActivity().registerReceiver(networkChangeReceiver, filter);
                }

                if (getArguments() != null && getArguments().containsKey("FOR_DATE")) {
                    forDate = getArguments().getString("FOR_DATE");
                    Log.i("forDate",forDate);
                } else {
                    Calendar calendar = Calendar.getInstance();
                    forDate = strDateFormat.format(calendar.getTime());
                    Log.i("forDate",forDate);
                }


                //added by jyotirmoy patra 19.10.22

                if(null!=getActivity()){
                    mPrefGratitude = PreferenceManager.getDefaultSharedPreferences(getActivity()); //gratitude popup

                    mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());  //checking daily popup setting

                }
                if(null!=getContext()){
                    sp = getContext().getSharedPreferences("firstName", 0); //get username

                }
            }
        }else{
            Util.sourcepage="habit";
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            if(null!=getActivity()){
                getActivity().registerReceiver(networkChangeReceiver, filter);
            }

            if (getArguments() != null && getArguments().containsKey("FOR_DATE")) {
                forDate = getArguments().getString("FOR_DATE");
                Log.i("forDate",forDate);
            } else {
                Calendar calendar = Calendar.getInstance();
                forDate = strDateFormat.format(calendar.getTime());
                Log.i("forDate",forDate);
            }


            //added by jyotirmoy patra 19.10.22

            if(null!=getActivity()){
                mPrefGratitude = PreferenceManager.getDefaultSharedPreferences(getActivity()); //gratitude popup

                mPref = PreferenceManager.getDefaultSharedPreferences(getActivity());  //checking daily popup setting

            }
            if(null!=getContext()){
                sp = getContext().getSharedPreferences("firstName", 0); //get username

            }
        }


    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreference = new SharedPreference(getActivity());
        habit_access=sharedPreference.read("HabitAccess","");
//        habit_access="false";
        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");

        accesstype=sharedPreference.read("accesstype","");
//        accesstype="3";
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");



        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){
                globalView = inflater.inflate(R.layout.fragment_todaytwo_mbhq, null);
                img_notaccess = globalView.findViewById(R.id.img_notaccess_habit);
                frm_notaccess = globalView.findViewById(R.id.frm_notaccess_habit);
                txt_notaccess = globalView.findViewById(R.id.txt_notaccess_habit);
                swipeLayout = globalView.findViewById(R.id.swipeLayout);
                img_notaccess.setVisibility(View.VISIBLE);
                frm_notaccess.setVisibility(View.VISIBLE);
                txt_notaccess.setVisibility(View.VISIBLE);
                swipeLayout.setVisibility(View.GONE);
                img_notaccess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url=sharedPreference.read("HabitPurchaseUrl","");

                        Uri uri = Uri.parse(url); // missing 'http://' will cause crashed
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });

                txt_notaccess.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        getUserPaidStatusApiCall();
                    }
                });
                return globalView;
            }else
            {
                if (globalView == null) {
                    Log.e("called_order", "1");

                    // globalView = inflater.inflate(R.layout.fragment_todaytwo_mbhq, null);
                    globalView = inflater.inflate(R.layout.fragment_todaytwo_mbhq, null);
                    img_notaccess = globalView.findViewById(R.id.img_notaccess_habit);
                    frm_notaccess = globalView.findViewById(R.id.frm_notaccess_habit);
                    txt_notaccess = globalView.findViewById(R.id.txt_notaccess_habit);
                    swipeLayout = globalView.findViewById(R.id.swipeLayout);
                    img_notaccess.setVisibility(View.GONE);
                    frm_notaccess.setVisibility(View.GONE);
                    txt_notaccess.setVisibility(View.GONE);
                    swipeLayout.setVisibility(View.VISIBLE);
                    imgAddHabit = globalView.findViewById(R.id.imgAddHabit);
                    imgWinTheWeekStats = globalView.findViewById(R.id.imgWinTheWeekStats);
                    recyclerTodayHabit = globalView.findViewById(R.id.recyclerTodayHabit);
                    rlHabitList = globalView.findViewById(R.id.rlHabitList);
                    txtLoading = globalView.findViewById(R.id.txtLoading);
                    txtHeading = globalView.findViewById(R.id.txtHeading);
                    imgBigPlus = globalView.findViewById(R.id.imgBigPlus);

                    selectAll = globalView.findViewById(R.id.selectAll);

                    rlSaveHabitCancel = globalView.findViewById(R.id.rlSaveHabitCancel);

                    rlCancel = globalView.findViewById(R.id.rlCancel);

                    rlSaveHabitMultiple = globalView.findViewById(R.id.rlSaveHabitMultiple);
                    if(null!=getActivity()){
                        mViewModelFactory = Injection.provideViewModelFactoryHabitCalendar(getActivity());

                    }
//                    mViewModel = ViewModelProviders.of(MbhqTodayTwoFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);
                    mViewModel = new ViewModelProvider(MbhqTodayTwoFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);

                    if(null!=getActivity()) {
                        mViewModelFactoryEdit = Injection.provideViewModelFactory(getActivity());

                    }
                  //  mViewModelEdit = ViewModelProviders.of(MbhqTodayTwoFragment.this, mViewModelFactoryEdit).get(HabitEditViewModel.class);
                    mViewModelEdit = new ViewModelProvider(MbhqTodayTwoFragment.this, mViewModelFactoryEdit).get(HabitEditViewModel.class);

                    if(null!=getActivity()) {
                        mViewModelFactoryAno = Injection.provideViewModelFactory(getActivity());

                    }
//                    mViewModelDel = ViewModelProviders.of(this, mViewModelFactoryAno).get(HabitEditViewModel.class);
                    mViewModelDel = new ViewModelProvider(this, mViewModelFactoryAno).get(HabitEditViewModel.class);

                    if(null!=getActivity()) {
                        mViewModelFactoryCalendar = Injection.provideViewModelFactoryHabitCalendar(getActivity());

                    }
                    //mViewModelCalendar = ViewModelProviders.of(this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);
                    mViewModelCalendar = new ViewModelProvider(this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);

                    if(null!=getActivity()) {
                        recyclerTodayHabit.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }

                    if(null!=getActivity()) {
                        finisherService = new FinisherServiceImpl(getActivity()); //added by jyotirmoy patra 17.10.22
                        sharedPreference = new SharedPreference(getActivity());
                        factoryForGratitude = Injection.provideViewModelFactoryGratitude(getActivity());
                    }

                   // gratitudeViewModel = ViewModelProviders.of(this, factoryForGratitude).get(GratitudeViewModel.class);
                    gratitudeViewModel = new ViewModelProvider(this, factoryForGratitude).get(GratitudeViewModel.class);

                    lstShowAll = new ArrayList<>();
                    lstToday = new ArrayList<>();
                    lstThisMonth = new ArrayList<>();
                    lstSixMonthsAgo = new ArrayList<>();
                    lstOneYearAgo = new ArrayList<>();
                    lstPicture = new ArrayList<>();


                    try {

                        Calendar today = Calendar.getInstance();

                        long dateDiffInMillis = today.getTime().getTime() - strDateFormat.parse(forDate).getTime();
                        int dateDiffInDays = (int) TimeUnit.MILLISECONDS.toDays(dateDiffInMillis);

                        switch (dateDiffInDays) {
                            case 1:
                                txtHeading.setText("Yesterday's Habits");
                                isTodayHabitShowing = false;
                                break;
                            case 0:
                                txtHeading.setText("Today's Habits");
                                isTodayHabitShowing = true;
                                todayPageUpdateHandler.removeCallbacks(todayPageUpdateTimeTask);
                                todayPageUpdateHandler.postDelayed(todayPageUpdateTimeTask, 100);
                                break;
                            default:
                                txtHeading.setText(headingDateFormat.format(
                                        strDateFormat.parse(forDate)
                                ) + "'s Habits");
                                isTodayHabitShowing = false;

                        }
                    } catch (ParseException e) {
                        Log.e("block", "6");
                        e.printStackTrace();
                    }

                    //  addItemsFromJSON();
                    //  getGrowthHomapageApiCall();
                    txtHeading.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            openHabitHistoryDialog();
                        }
                    });

                    rlHabitList.setOnClickListener(view -> {
                        if(null!=getActivity()) {
                            if (Util.checkConnection(getActivity())) {
                                Log.e("Hiiiiiiiiiiiiiiii","100005");
                                new SharedPreference(getActivity()).write("achieve_nav", "", "habit_hacker");
                                new SharedPreference(getActivity()).write("achieve_nav_pos", "", 0 + "");
                                ((MainActivity) getActivity()).funHabits();
                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                            } else {
                                Util.showToast(getActivity(), Util.networkMsg);
                            }
                        }


                    });

                    imgAddHabit.setOnClickListener(view -> {
                        Util.edittext = "";

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
                        Util.HABIT_ADDED_FROM_TODAY_PAGE = true;

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

                    imgWinTheWeekStats.setOnClickListener(view -> {

                        if(null!=getActivity()) {
                            if (Util.checkConnection(getActivity())) {
                                ((MainActivity) getActivity()).loadFragment(WinTheWeekStatsFragment.newInstance(), "WinTheWeekStatsFragment", null);
                            } else {
                                Util.showToast(getActivity(), Util.networkMsg);
                            }
                        }

                    });

                    imgBigPlus.setOnClickListener(view -> {
                        Util.HABIT_ADDED_FROM_TODAY_PAGE = true;
                        if(null!=getActivity()) {
                            if (Util.checkConnection(getActivity())) {
                                //((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
                                ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
                            } else {
                                Util.showToast(getActivity(), Util.networkMsg);
                            }
                        }

                    });

                    rlSaveHabitMultiple.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            updateMultipleTaskStatusApiCall();
                        }
                    });

                    rlCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            loadAdapter(habitSwapList);
                            rlSaveHabitCancel.setVisibility(View.GONE);
                        }
                    });

                    swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {

                            try {
                                //   getGrowthHomapageApiCall(false);
                                habitSwapList.clear();
                                sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                       /* if(null!=getActivity()){
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }*/
                                Calendar c = Calendar.getInstance();
                                Date currentDate = c.getTime();
                                if(null==forDate_optionclick){
                                    Log.i("forDate","0");
                                    if(null!=getActivity()){
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                    }

                                }else{

                                    if (strDateFormat.format(currentDate).equals(forDate_optionclick)) {
                                        Log.i("forDate","1");
                                        if(null!=getActivity()){
                                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                        }
                                    } else{
                                        Log.i("forDate","1");
                                        Bundle bundle = new Bundle();

                                        bundle.putString("FOR_DATE", forDate_optionclick);

                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());

                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", bundle);

                                    }
                                }
                            } catch (Exception e) {
                                Log.e("block", "5");
                                e.printStackTrace();
                            }

                            swipeLayout.setRefreshing(false);

                        }
                    });

                    //   getGrowthHomapageApiCall(); ///// 15.11
                    //   getGrowthHomapageApiCall();
                    Log.e("calledhabit1", "8");
                    return globalView;
                } else {
                    Log.e("calledhabit1", "9");
                    img_notaccess = globalView.findViewById(R.id.img_notaccess_habit);
                    frm_notaccess = globalView.findViewById(R.id.frm_notaccess_habit);
                    txt_notaccess = globalView.findViewById(R.id.txt_notaccess_habit);
                    swipeLayout = globalView.findViewById(R.id.swipeLayout);
                    img_notaccess.setVisibility(View.GONE);
                    frm_notaccess.setVisibility(View.GONE);
                    txt_notaccess.setVisibility(View.GONE);
                    swipeLayout.setVisibility(View.VISIBLE);
                    return globalView;
                }
            }

        }else{
            if (globalView == null) {
                Log.e("called_order", "1");

                // globalView = inflater.inflate(R.layout.fragment_todaytwo_mbhq, null);
                globalView = inflater.inflate(R.layout.fragment_todaytwo_mbhq, null);
                img_notaccess = globalView.findViewById(R.id.img_notaccess_habit);
                frm_notaccess = globalView.findViewById(R.id.frm_notaccess_habit);
                txt_notaccess = globalView.findViewById(R.id.txt_notaccess_habit);
                swipeLayout = globalView.findViewById(R.id.swipeLayout);
                img_notaccess.setVisibility(View.GONE);
                frm_notaccess.setVisibility(View.GONE);
                txt_notaccess.setVisibility(View.GONE);
                swipeLayout.setVisibility(View.VISIBLE);
                imgAddHabit = globalView.findViewById(R.id.imgAddHabit);
                imgWinTheWeekStats = globalView.findViewById(R.id.imgWinTheWeekStats);
                recyclerTodayHabit = globalView.findViewById(R.id.recyclerTodayHabit);
                rlHabitList = globalView.findViewById(R.id.rlHabitList);
                txtLoading = globalView.findViewById(R.id.txtLoading);
                txtHeading = globalView.findViewById(R.id.txtHeading);
                imgBigPlus = globalView.findViewById(R.id.imgBigPlus);

                selectAll = globalView.findViewById(R.id.selectAll);

                rlSaveHabitCancel = globalView.findViewById(R.id.rlSaveHabitCancel);

                rlCancel = globalView.findViewById(R.id.rlCancel);

                rlSaveHabitMultiple = globalView.findViewById(R.id.rlSaveHabitMultiple);
                if(null!=getActivity()){
                    mViewModelFactory = Injection.provideViewModelFactoryHabitCalendar(getActivity());

                }
               // mViewModel = ViewModelProviders.of(MbhqTodayTwoFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);
                mViewModel = new ViewModelProvider(MbhqTodayTwoFragment.this, mViewModelFactory).get(HabitCalendarViewModel.class);

                if(null!=getActivity()) {
                    mViewModelFactoryEdit = Injection.provideViewModelFactory(getActivity());

                }
               // mViewModelEdit = ViewModelProviders.of(MbhqTodayTwoFragment.this, mViewModelFactoryEdit).get(HabitEditViewModel.class);
                mViewModelEdit = new ViewModelProvider(this, mViewModelFactoryEdit).get(HabitEditViewModel.class);

                if(null!=getActivity()) {
                    mViewModelFactoryAno = Injection.provideViewModelFactory(getActivity());

                }
              //  mViewModelDel = ViewModelProviders.of(this, mViewModelFactoryAno).get(HabitEditViewModel.class);
                mViewModelDel = new ViewModelProvider(this, mViewModelFactoryAno).get(HabitEditViewModel.class);

                if(null!=getActivity()) {
                    mViewModelFactoryCalendar = Injection.provideViewModelFactoryHabitCalendar(getActivity());

                }
//                mViewModelCalendar = ViewModelProviders.of(this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);
                mViewModelCalendar = new ViewModelProvider(this, mViewModelFactoryCalendar).get(HabitCalendarViewModel.class);

                if(null!=getActivity()) {
                    recyclerTodayHabit.setLayoutManager(new LinearLayoutManager(getActivity()));
                }

                if(null!=getActivity()) {
                    finisherService = new FinisherServiceImpl(getActivity()); //added by jyotirmoy patra 17.10.22
                    sharedPreference = new SharedPreference(getActivity());
                    factoryForGratitude = Injection.provideViewModelFactoryGratitude(getActivity());
                }

              //  gratitudeViewModel = ViewModelProviders.of(this, factoryForGratitude).get(GratitudeViewModel.class);
                gratitudeViewModel = new ViewModelProvider(this, factoryForGratitude).get(GratitudeViewModel.class);

                lstShowAll = new ArrayList<>();
                lstToday = new ArrayList<>();
                lstThisMonth = new ArrayList<>();
                lstSixMonthsAgo = new ArrayList<>();
                lstOneYearAgo = new ArrayList<>();
                lstPicture = new ArrayList<>();

        /*    if ("no".equalsIgnoreCase(Util.calldstatic)) {
                Util.calldstatic="";
            }else{
                 setInteractionDisabled(true);
                 addItemsFromJSON();
            }*/

                //  getGrowthHomapageApiCall();

                // getGrowthHomapageApiCall();
/*
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    try{
                        getGrowthHomapageApiCall();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

            }, 100);
*/

                try {

                    Calendar today = Calendar.getInstance();

                    long dateDiffInMillis = today.getTime().getTime() - strDateFormat.parse(forDate).getTime();
                    int dateDiffInDays = (int) TimeUnit.MILLISECONDS.toDays(dateDiffInMillis);

                    switch (dateDiffInDays) {
                        case 1:
                            txtHeading.setText("Yesterday's Habits");
                            isTodayHabitShowing = false;
                            break;
                        case 0:
                            txtHeading.setText("Today's Habits");
                            isTodayHabitShowing = true;
                            todayPageUpdateHandler.removeCallbacks(todayPageUpdateTimeTask);
                            todayPageUpdateHandler.postDelayed(todayPageUpdateTimeTask, 100);
                            break;
                        default:
                            txtHeading.setText(headingDateFormat.format(
                                    strDateFormat.parse(forDate)
                            ) + "'s Habits");
                            isTodayHabitShowing = false;

                    }
                } catch (ParseException e) {
                    Log.e("block", "6");
                    e.printStackTrace();
                }

                //  addItemsFromJSON();
                //  getGrowthHomapageApiCall();
                txtHeading.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openHabitHistoryDialog();
                    }
                });

                rlHabitList.setOnClickListener(view -> {
                    if(null!=getActivity()) {
                        if (Util.checkConnection(getActivity())) {
                            Log.e("Hiiiiiiiiiiiiiiii","100005");
                            new SharedPreference(getActivity()).write("achieve_nav", "", "habit_hacker");
                            new SharedPreference(getActivity()).write("achieve_nav_pos", "", 0 + "");
                            ((MainActivity) getActivity()).funHabits();
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                        } else {
                            Util.showToast(getActivity(), Util.networkMsg);
                        }
                    }


                });

                imgAddHabit.setOnClickListener(view -> {
                    Util.edittext = "";

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
                    Util.HABIT_ADDED_FROM_TODAY_PAGE = true;

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

                imgWinTheWeekStats.setOnClickListener(view -> {

                    if(null!=getActivity()) {
                        if (Util.checkConnection(getActivity())) {
                            ((MainActivity) getActivity()).loadFragment(WinTheWeekStatsFragment.newInstance(), "WinTheWeekStatsFragment", null);
                        } else {
                            Util.showToast(getActivity(), Util.networkMsg);
                        }
                    }

                });

                imgBigPlus.setOnClickListener(view -> {
                    Util.HABIT_ADDED_FROM_TODAY_PAGE = true;
                    if(null!=getActivity()) {
                        if (Util.checkConnection(getActivity())) {
                            //((MainActivity)getActivity()).loadFragment(new HabitHackerListFragment(),"HabitHackerList",null);
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
                            ((MainActivity) getActivity()).loadFragment(new HabitHackerAddFirstPage(), "HabitHackerAddFirstPage", null);
                        } else {
                            Util.showToast(getActivity(), Util.networkMsg);
                        }
                    }

                });

                rlSaveHabitMultiple.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateMultipleTaskStatusApiCall();
                    }
                });

                rlCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadAdapter(habitSwapList);
                        rlSaveHabitCancel.setVisibility(View.GONE);
                    }
                });

                swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        try {
                            //   getGrowthHomapageApiCall(false);
                            habitSwapList.clear();
                            sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                       /* if(null!=getActivity()){
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                        }*/
                            Calendar c = Calendar.getInstance();
                            Date currentDate = c.getTime();
                            if(null==forDate_optionclick){
                                Log.i("forDate","0");
                                if(null!=getActivity()){
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                }

                            }else{

                                if (strDateFormat.format(currentDate).equals(forDate_optionclick)) {
                                    Log.i("forDate","1");
                                    if(null!=getActivity()){
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                    }
                                } else{
                                    Log.i("forDate","1");
                                    Bundle bundle = new Bundle();

                                    bundle.putString("FOR_DATE", forDate_optionclick);

                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());

                                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", bundle);

                                }
                            }
                        } catch (Exception e) {
                            Log.e("block", "5");
                            e.printStackTrace();
                        }

                        swipeLayout.setRefreshing(false);

                    }
                });

                //   getGrowthHomapageApiCall(); ///// 15.11
                //   getGrowthHomapageApiCall();
                Log.e("calledhabit1", "8");
                return globalView;
            } else {
                Log.e("calledhabit1", "9");
                img_notaccess = globalView.findViewById(R.id.img_notaccess_habit);
                frm_notaccess = globalView.findViewById(R.id.frm_notaccess_habit);
                txt_notaccess = globalView.findViewById(R.id.txt_notaccess_habit);
                swipeLayout = globalView.findViewById(R.id.swipeLayout);
                img_notaccess.setVisibility(View.GONE);
                frm_notaccess.setVisibility(View.GONE);
                txt_notaccess.setVisibility(View.GONE);
                swipeLayout.setVisibility(View.VISIBLE);
                return globalView;
            }
        }


    }

    //added by Jyotirmoy Patra 16.09.22
    private void showDailyGratitudePrompt(String name,String txtPrompt,String txtAdd) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.daily_prompt_gratitude);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        ImageView closePrompt = (ImageView) dialog.findViewById(R.id.GratitudePromptClose);
        closePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView selectPrompt = (TextView) dialog.findViewById(R.id.addGratitude);
        selectPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // openDialogForAchieveAddWithOption(null);

                Bundle b = new Bundle();
                Util.grateFul_popup_from_habit = false;

                Util.isReloadTodayMainPage = true;

                b.putString("openaddpopup", "yes");


                ((MainActivity) getActivity()).fungratitudeicon();
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", b);


                dialog.dismiss();
            }
        });

        TextView txtName = (TextView) dialog.findViewById(R.id.txtName);
        String userName = "Hi " + name;

        TextView tvWhat = dialog.findViewById(R.id.tvWhat);
        tvWhat.setText(txtPrompt);
        selectPrompt.setText(txtAdd);

        //for bold user name
        SpannableString ss = new SpannableString(userName);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 3, userName.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtName.setText(ss);


        dialog.show();
    }
/*commented by sahenita previously unused*/
/*
    private void openDialogForAchieveAddWithOption(String gratitudeToday) {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_add);

        TextView txtBack = dialog.findViewById(R.id.txtBack);
        TextView tstSelectedOption = dialog.findViewById(R.id.tstSelectedOption);
        edtAchieveDialog = dialog.findViewById(R.id.edtAchieve);
        RelativeLayout rlSave = dialog.findViewById(R.id.rlSave);
        RelativeLayout rlShare = dialog.findViewById(R.id.rlShare);

        tstSelectedOption.setVisibility(View.GONE);
        if(null!=getActivity()){
            if (Util.checkConnection(getActivity())) {
                rlShare.setVisibility(View.VISIBLE);
            } else {
                rlShare.setVisibility(View.GONE);
            }
        }


        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null && getArguments().containsKey("ADD_GRATITUDE_FROM_TODAY")) {
                    dialog.dismiss();
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                } else {
                    dialog.dismiss();
                }

            }
        });

        edtAchieveDialog.setFocusable(true);
        edtAchieveDialog.setClickable(true);

        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtAchieveDialog.getText().toString().equals("")) {
                    AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("Alert", "Please enter some text", false);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {

                        }

                        @Override
                        public void onCancel(String title) {

                        }
                    });
                } else {
                    dialog.dismiss();

                    GetGratitudeListModelInner getGratitudeListModelInner = makeGratitudeModelForAdd(edtAchieveDialog, null);

                    HashMap<String, Object> requestHash = new HashMap<>();
                    requestHash.put("model", getGratitudeListModelInner);
                    requestHash.put("Key", Util.KEY);
                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                    saveAddGratitudeData(requestHash, getGratitudeListModelInner, edtAchieveDialog.getText().toString(), false, dialog);
                }
            }
        });

        rlShare.setOnClickListener(view -> {
            if (edtAchieveDialog.getText().toString().equals("")) {
                AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                alertDialogCustom.ShowDialog("Alert", "Please enter some text", false);
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {

                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });
            } else {
                GetGratitudeListModelInner getGratitudeListModelInner = makeGratitudeModelForAdd(edtAchieveDialog, null);

                HashMap<String, Object> requestHash = new HashMap<>();
                requestHash.put("model", getGratitudeListModelInner);
                requestHash.put("Key", Util.KEY);
                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                saveAddGratitudeData(requestHash, getGratitudeListModelInner, edtAchieveDialog.getText().toString(), true, dialog);
            }
        });

        dialog.show();
        if (gratitudeToday != null) {
            edtAchieveDialog.setText(gratitudeToday);
            rlShare.performClick();
        }
    }
*/

    private GetGratitudeListModelInner makeGratitudeModelForAdd(EditText edtAchieve, Integer gratitudeID) {

        GetGratitudeListModelInner getGratitudeListModelInner = new GetGratitudeListModelInner();
        if (gratitudeID == null) {
            getGratitudeListModelInner.setId(0);
        } else {
            getGratitudeListModelInner.setId(gratitudeID);
        }
        getGratitudeListModelInner.setName(edtAchieve.getText().toString());
        getGratitudeListModelInner.setDescription("");
        //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
        getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
        getGratitudeListModelInner.setCreatedBy(3);
        getGratitudeListModelInner.setStatus(false);
        getGratitudeListModelInner.setIsDeleted(false);

        //getGratitudeListModelInner.setSong(songPathSelect);
                /*if(chkSetReminder.isChecked())
                {
                    getGratitudeListModelInner.setPushNotification(true);
                    getGratitudeListModelInner.setFrequencyId(1);
                    getGratitudeListModelInner.setReminderOption(1);
                    getGratitudeListModelInner.setReminderAt1("12");
                }*/

        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1("43200");
        getGratitudeListModelInner.setReminderAt2("0");
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setSunday(false);
        getGratitudeListModelInner.setMonday(false);
        getGratitudeListModelInner.setTuesday(false);
        getGratitudeListModelInner.setWednesday(false);
        getGratitudeListModelInner.setThursday(false);
        getGratitudeListModelInner.setFriday(false);
        getGratitudeListModelInner.setSaturday(false);
        getGratitudeListModelInner.setJanuary(false);
        getGratitudeListModelInner.setFebruary(false);
        getGratitudeListModelInner.setMarch(false);
        getGratitudeListModelInner.setApril(false);
        getGratitudeListModelInner.setMay(false);
        getGratitudeListModelInner.setJune(false);
        getGratitudeListModelInner.setJuly(false);
        getGratitudeListModelInner.setAugust(false);
        getGratitudeListModelInner.setSeptember(false);
        getGratitudeListModelInner.setOctober(false);
        getGratitudeListModelInner.setNovember(false);
        getGratitudeListModelInner.setDecember(false);
        getGratitudeListModelInner.setUploadPictureImgBase64("");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        getGratitudeListModelInner.setCreatedAt(simpleDateFormat.format(calendar.getTime()));

        return getGratitudeListModelInner;

    }
/*commented by sahenita*/
/*
    private void saveAddGratitudeData(HashMap<String, Object> reqhash, GetGratitudeListModelInner getGratitudeListModelInner, String gratitudeName, boolean isShare, Dialog dialogForSave) {

        SimpleDateFormat simpleDateFormat100 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar100 = Calendar.getInstance();
        calendar100.set(Calendar.HOUR, 0);
        calendar100.set(Calendar.MINUTE, 0);
        calendar100.set(Calendar.SECOND, 0);
        if (sharedPreference.read("I_M_GRATEFUL_DATE", "").equals("")) {
            sharedPreference.write("I_M_GRATEFUL", "", gratitudeName);
            String date = simpleDateFormat100.format(calendar100.getTime());
            sharedPreference.write("I_M_GRATEFUL_DATE", "", date);
        } else {
            try {
                String date = simpleDateFormat100.format(calendar100.getTime());
                if (!sharedPreference.read("I_M_GRATEFUL_DATE", "").equals(date)) {
                    sharedPreference.write("I_M_GRATEFUL", "", gratitudeName);
                    sharedPreference.write("I_M_GRATEFUL_DATE", "", date);
                }
            } catch (Exception e) {
                Log.e("block", "4");
                e.printStackTrace();
            }
        }
        if(null!=getActivity()){
            if (Util.checkConnection(getActivity())) {

                //progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

                final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
                ProgressRequestBody fileBody = null;
                if (mFile != null) {
                    fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                        @Override
                        public void onProgressUpdate(int percentage) {
                            progressDialog.setProgress(percentage);
                        }

                        @Override
                        public void onError() {

                        }

                        @Override
                        public void onFinish() {
                            progressDialog.setProgress(100);
                        }
                    }, 1);

                }
                progressDialog.show();

                Call<AddUpdateGratitudeModel> jsonObjectCall = finisherService.addUpdateGratitudeWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
                jsonObjectCall.enqueue(new Callback<AddUpdateGratitudeModel>() {
                    @Override
                    public void onResponse(Call<AddUpdateGratitudeModel> call, final Response<AddUpdateGratitudeModel> response) {

                        progressDialog.setProgress(100);
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (response.body() != null) {
                                    if (response.body().getSuccessFlag()) {
                                        Util.isReloadTodayMainPage = true;

                                        /////////////////////////
                                        saveGratitudeLocally(true, response.body().getDetails());
                                        //////////////////////////

                                        Leanplum.track("Appreciate_Android_Saved a gratitude");
                                        Toast.makeText(getActivity(), "Data successfully saved", Toast.LENGTH_SHORT).show();
                                        SetLocalNotificationForGratitude.setNotificationForGratitude(response.body().getDetails(), getActivity());
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new GratitudeMyListFragment());
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayMainFragment());
                                        ((MainActivity) getActivity()).refershGamePoint(getActivity());

                                        //SAVE LOCALLY GRATITUDE AFTER SAVING TO SERVER
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                        Calendar calendar = Calendar.getInstance();
                                        String todatDate = simpleDateFormat.format(calendar.getTime());
                                        Gson gson = new Gson();
                                        String gratitudeModel = gson.toJson(getGratitudeListModelInner);
                                        String timeStamp = String.valueOf(calendar.getTimeInMillis());
                                        GratitudeEntity gratitudeEntity = new GratitudeEntity(response.body().getDetails().getId(), todatDate, gratitudeModel, timeStamp, true);
                                        mDisposable.add(gratitudeViewModel.insertGratitude(gratitudeEntity)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(() -> {
                                                    Log.e("GRATITUDE_SAVE", "SUCCESSFULL");
                                                    if (!isShare) {
                                                        ((MainActivity) getActivity()).loadFragment(new GratitudeMyListFragment(), "GratitudeMyList", null);
                                                    } else {
                                                        openDialogForGratitudeShare(dialogForSave, gratitudeName, response.body().getDetails().getId());
                                                    }
                                                }, throwable -> {
                                                    Log.e("GRATITUDE_SAVE", "NOT_SUCCESSFULL");
                                                }));

                                        ///////////////////


                                    }
                                }
                            }
                        };
                        new Handler().postDelayed(runnable, 500);

                    }

                    @Override
                    public void onFailure(Call<AddUpdateGratitudeModel> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            } else {
                //Util.showToast(getActivity(),Util.networkMsg);

                saveGratitudeLocally(false, null);

            }
        }


    }
*/
/*commented by sahenita*/
/*
    private void openDialogForGratitudeShare(Dialog dialogForSave, String gratitudeName, Integer gratitudeID) {

        strDialogSelectionType = "";

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gratitude_share_options);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        RelativeLayout rlTestAndPic = dialog.findViewById(R.id.rlTestAndPic);
        RelativeLayout rlTextAndPicEx = dialog.findViewById(R.id.rlTextAndPicEx);
        RelativeLayout rlTextOverPic = dialog.findViewById(R.id.rlTextOverPic);
        RelativeLayout rlTextOverPicEx = dialog.findViewById(R.id.rlTextOverPicEx);
        RelativeLayout rlTextOnly = dialog.findViewById(R.id.rlTextOnly);
        RelativeLayout rlTextOnlyEx = dialog.findViewById(R.id.rlTextOnlyEx);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
            dialogForSave.dismiss();
            ((MainActivity) getActivity()).loadFragment(new GratitudeMyListFragment(), "GratitudeMyList", null);
        });

        rlTestAndPic.setOnClickListener(view -> {
            strDialogSelectionType = "textAndPic";
            dialog.dismiss();
            dialogForSave.dismiss();
            openDialogForTextOverPicOption(gratitudeName, gratitudeID, "textAndPic");
        });

        rlTextAndPicEx.setOnClickListener(view -> {
            openDialogForShareExamples("textAndPic");
        });

        rlTextOverPic.setOnClickListener(view -> {
            strDialogSelectionType = "textOverPic";
            dialog.dismiss();
            dialogForSave.dismiss();
            openDialogForTextOverPicOption(gratitudeName, gratitudeID, "textOverPic");
        });

        rlTextOverPicEx.setOnClickListener(view -> {
            openDialogForShareExamples("textOverPic");
        });

        rlTextOnly.setOnClickListener(view -> {
            strDialogSelectionType = "textOnly";
            dialog.dismiss();
            dialogForSave.dismiss();
            openDialogForTextOverPicOption(gratitudeName, gratitudeID, "textOnly");
        });

        rlTextOnlyEx.setOnClickListener(view -> {
            openDialogForShareExamples("textOnly");
        });

        dialog.show();
    }
*/
/*commented by sahenita*/
/*
    private void openDialogForShareExamples(String TYPE) {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gratitude_share_examples);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView imgExample = dialog.findViewById(R.id.imgExample);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        if (TYPE.equals("textAndPic")) {
            imgExample.setImageResource(R.drawable.text_and_pic);
        } else if (TYPE.equals("textOverPic")) {
            imgExample.setImageResource(R.drawable.text_over_pic);
        } else {
            imgExample.setImageResource(R.drawable.text_only);
        }

        dialog.show();

    }
*/
/*commented by sahenita*/
/*
    private void openDialogForTextOverPicOption(String gratitudeName, Integer gratitudeID, String TYPE) {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_text_over_pic_shareability);

        RelativeLayout rlSharableSectionTOP = dialog.findViewById(R.id.rlSharableSection);
        RelativeLayout rlPicSectionTOP = dialog.findViewById(R.id.rlPicSection);
        cardViewBackgroundPicTOP = dialog.findViewById(R.id.cardViewBackgroundPic);
        imgBackgroundPicTOP = dialog.findViewById(R.id.imgBackgroundPic);
        llAddPicTOP = dialog.findViewById(R.id.llAddPic);
        buttonChangeBackgroundImageTOP = dialog.findViewById(R.id.buttonChangeBackgroundImage);
        buttonMoveTextBoxTOP = dialog.findViewById(R.id.buttonMoveTextBox);
        RelativeLayout rlBorderTOP = dialog.findViewById(R.id.rlBorder);
        CheckBox chkBorderNoBorderTOP = dialog.findViewById(R.id.chkBorderNoBorder);
        Spinner spTextSizeTOP = dialog.findViewById(R.id.spTextSize);
        Button btnLeftAlignmentTOP = dialog.findViewById(R.id.btnLeftAlignment);
        Button btnCenterAlignmentTOP = dialog.findViewById(R.id.btnCenterAlignment);
        Button btnRightAlignmentTOP = dialog.findViewById(R.id.btnRightAlignment);
        RelativeLayout rlWhiteBackgroundBlackTextTOP = dialog.findViewById(R.id.rlWhiteBackgroundBlackText);
        RelativeLayout rlBlackBackgroundWhiteTextTOP = dialog.findViewById(R.id.rlBlackBackgroundWhiteText);
        RelativeLayout rlTransparentBackgroundBlackTextTOP = dialog.findViewById(R.id.rlTransparentBackgroundBlackText);
        RelativeLayout rlTransparentBackgroundWhiteTextTOP = dialog.findViewById(R.id.rlTransparentBackgroundWhiteText);
        ImageView imgWhiteBackgroundBlackTextTOP = dialog.findViewById(R.id.imgWhiteBackgroundBlackText);
        ImageView imgBlackBackgroundWhiteTextTOP = dialog.findViewById(R.id.imgBlackBackgroundWhiteText);
        ImageView imgTransparentBackgroundBlackTextTOP = dialog.findViewById(R.id.imgTransparentBackgroundBlackText);
        ImageView imgTransparentBackgroundWhiteTextTOP = dialog.findViewById(R.id.imgTransparentBackgroundWhiteText);
        rlTextOverPicInnerTOP = dialog.findViewById(R.id.rlTextOverPicInner);
        EditText edtTextOverPicInnerTOP = dialog.findViewById(R.id.edtTextOverPicInner);
        TextView txtGratefulFor = dialog.findViewById(R.id.txtGratefulFor);
        CustomScrollView scroll_view = dialog.findViewById(R.id.scroll_view);
        FrameLayout frameLayout = dialog.findViewById(R.id.frameLayout);
        RelativeLayout rootlayout = dialog.findViewById(R.id.rootlayout);
        TextView txtTextOverPicOwner = dialog.findViewById(R.id.txtTextOverPicOwner);
        ImageView imgMindBodyHq = dialog.findViewById(R.id.imgMindBodyHq);
        RelativeLayout rlShareGratitude = dialog.findViewById(R.id.rlShareGratitude);
        RelativeLayout rlCancelGratitude = dialog.findViewById(R.id.rlCancelGratitude);
        RelativeLayout rlTextAndPic = dialog.findViewById(R.id.rlTextAndPic);
        EditText edtTextANDPic = dialog.findViewById(R.id.edtTextANDPic);

        final boolean[] boolWhiteBackgroundBlackText = {true};
        final boolean[] boolBlackBackgroundWhiteText = {false};
        final boolean[] boolTransparentBackgroundBlackText = {false};
        final boolean[] boolTransparentBackgroundWhiteText = {false};

        if (TYPE.equals("textAndPic")) {
            rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextAndPic.setVisibility(View.VISIBLE);
            rlWhiteBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlBlackBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundWhiteTextTOP.setVisibility(View.GONE);
        } else if (TYPE.equals("textOnly")) {
            rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextAndPic.setVisibility(View.VISIBLE);
            rlWhiteBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlBlackBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlPicSectionTOP.setVisibility(View.GONE);
        }

        edtTextOverPicInnerTOP.setText(gratitudeName);
        txtTextOverPicOwner.setText(gratitudeName);
        edtTextANDPic.setText(gratitudeName);

        rlBorderTOP.setOnClickListener(view -> {
            if (chkBorderNoBorderTOP.isChecked()) {
                chkBorderNoBorderTOP.setChecked(false);
            } else {
                chkBorderNoBorderTOP.setChecked(true);
            }
        });
        chkBorderNoBorderTOP.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                if (strDialogSelectionType.equals("textAndPic") || strDialogSelectionType.equals("textOnly")) {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.round_corner_black);
                    rlTextAndPic.setBackgroundResource(R.drawable.edittext_background_black_border);
                } else {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.round_corner_black);
                    if (boolWhiteBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black);
                    } else if (boolBlackBackgroundWhiteText[0]) {

                    } else if (boolTransparentBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
                    } else {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
                    }
                }
            } else {
                if (strDialogSelectionType.equals("textAndPic") || strDialogSelectionType.equals("textOnly")) {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    rlTextAndPic.setBackgroundResource(R.drawable.edittext_background_white_border);
                } else {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    if (boolWhiteBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    } else if (boolBlackBackgroundWhiteText[0]) {

                    } else if (boolTransparentBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
                    } else {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
                    }
                }
            }
        });
        rlBorderTOP.performClick();
        llAddPicTOP.setOnClickListener(view -> {
            pickImageFromGallery();
        });
        buttonChangeBackgroundImageTOP.setOnClickListener(view -> {
            pickImageFromGallery();
        });
        ArrayList<Integer> lstTextSize = new ArrayList<>();
        for (int i = 12; i <= 48; i = i + 2) {
            lstTextSize.add(i);
        }
        ArrayAdapter<Integer> adapterFlag = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, lstTextSize);
        adapterFlag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTextSizeTOP.setAdapter(adapterFlag);
        spTextSizeTOP.setSelection(4);

        spTextSizeTOP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                } else if (i == 1) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                } else if (i == 2) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                } else if (i == 3) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                } else if (i == 4) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                } else if (i == 5) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                } else if (i == 6) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                } else if (i == 7) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                } else if (i == 8) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                } else if (i == 9) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                } else if (i == 10) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                } else if (i == 11) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                } else if (i == 12) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                } else if (i == 13) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                } else if (i == 14) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                } else if (i == 15) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                } else if (i == 16) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                } else if (i == 17) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                } else if (i == 18) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLeftAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.LEFT);
            edtTextANDPic.setGravity(Gravity.LEFT);
            txtTextOverPicOwner.setGravity(Gravity.LEFT);
        });

        btnCenterAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.CENTER);
            edtTextANDPic.setGravity(Gravity.CENTER);
            txtTextOverPicOwner.setGravity(Gravity.CENTER);
        });

        btnRightAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.RIGHT);
            edtTextANDPic.setGravity(Gravity.RIGHT);
            txtTextOverPicOwner.setGravity(Gravity.RIGHT);
        });

        rlWhiteBackgroundBlackTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.white));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.black));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.black));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.black));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounded_corner_white);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.logo_mbhq);
            boolWhiteBackgroundBlackText[0] = true;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlBlackBackgroundWhiteTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.black));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.white));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.white));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.white));
            rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_filled_black);
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.mndbody_logo_white);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = true;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlTransparentBackgroundBlackTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.white));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.black));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.black));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.black));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.logo_mbhq);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = true;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlTransparentBackgroundWhiteTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_green_check);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.black));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.white));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.white));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.white));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.mndbody_logo_white);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = true;
        });

        final boolean[] boolMoveText = {false};
        buttonMoveTextBoxTOP.setOnClickListener(view -> {
            if (!boolMoveText[0]) {
                boolMoveText[0] = true;
                scroll_view.setEnableScrolling(false);
                scroll_view.setFocusableInTouchMode(false);
                buttonMoveTextBoxTOP.setText("lock text box");
                buttonMoveTextBoxTOP.setBackgroundResource(R.drawable.rounded_corner_green);
                buttonMoveTextBoxTOP.setTextColor(getResources().getColor(R.color.white));
                txtTextOverPicOwner.setText(edtTextOverPicInnerTOP.getText().toString());
                edtTextOverPicInnerTOP.setVisibility(View.GONE);
                txtTextOverPicOwner.setVisibility(View.VISIBLE);
                rlTextOverPicInnerTOP.setOnTouchListener(new OnDragTouchListener(rlTextOverPicInnerTOP, frameLayout));
            } else {
                boolMoveText[0] = false;
                scroll_view.setEnableScrolling(true);
                scroll_view.setFocusableInTouchMode(true);
                buttonMoveTextBoxTOP.setText("move text box");
                buttonMoveTextBoxTOP.setBackgroundResource(R.drawable.rounded_corner_green_border_white_inside);
                buttonMoveTextBoxTOP.setTextColor(getResources().getColor(R.color.colorPrimary));
                edtTextOverPicInnerTOP.setVisibility(View.VISIBLE);
                txtTextOverPicOwner.setVisibility(View.GONE);
                rlTextOverPicInnerTOP.setOnTouchListener(null);
            }
        });

        rlCancelGratitude.setOnClickListener(view -> {
            dialog.dismiss();
            ((MainActivity) getActivity()).loadFragment(new GratitudeMyListFragment(), "GratitudeMyList", null);
        });

        rlShareGratitude.setOnClickListener(view -> {
            if (mFile == null) {
                final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                alertDialogCustom.ShowDialog("Alert!", "Please add your pic to share", true);
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {

                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });
            } else {
                dialog.dismiss();
                if (strDialogSelectionType.equals("textOverPic")) {
                    funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextOverPicInnerTOP);
                } else {
                    funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextANDPic);
                }
            }
        });

        dialog.show();
    }
*/
/*commented by sahenita*/
/*
    private void funcForShareImageGratitudeSharability(RelativeLayout rlPicSection, Integer gratitudeID, EditText edtAchieveDialog) {
        rlPicSection.setDrawingCacheEnabled(true);
        Bitmap bitmap = getScreenShot(rlPicSection);
        File shareFile = createFolder();
        try {
            if (!shareFile.exists()) {
                shareFile.createNewFile();
            }
            FileOutputStream ostream = new FileOutputStream(shareFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            ostream.close();
            rlPicSection.invalidate();
        } catch (Exception e) {
            Log.e("block", "3");
            e.printStackTrace();
        } finally {
            rlPicSection.setDrawingCacheEnabled(false);
        }
        GetGratitudeListModelInner getGratitudeListModelInner = makeGratitudeModelForAdd(edtAchieveDialog, gratitudeID);

        HashMap<String, Object> requestHash = new HashMap<>();
        requestHash.put("model", getGratitudeListModelInner);
        requestHash.put("Key", Util.KEY);
        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

        saveAddGratitudeDataForShare(requestHash, shareFile);


    }
*/
/*commented by sahenita*/
/*
    public void saveAddGratitudeDataForShare(HashMap<String, Object> reqhash, File shareFile) {
        if(null!=getActivity()){
            if (Util.checkConnection(getActivity())) {

                final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
                ProgressRequestBody fileBody = null;
                if (mFile != null) {
                    fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                        @Override
                        public void onProgressUpdate(int percentage) {
                            progressDialog.setProgress(percentage);
                        }

                        @Override
                        public void onError() {

                        }

                        @Override
                        public void onFinish() {
                            progressDialog.setProgress(100);
                        }
                    }, 1);

                }
                progressDialog.show();

                Call<AddUpdateGratitudeModel> jsonObjectCall = finisherService.addUpdateGratitudeWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
                jsonObjectCall.enqueue(new Callback<AddUpdateGratitudeModel>() {
                    @Override
                    public void onResponse(Call<AddUpdateGratitudeModel> call, final Response<AddUpdateGratitudeModel> response) {

                        progressDialog.setProgress(100);
                        Runnable runnable = new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                if (response.body() != null) {
                                    if (response.body().getSuccessFlag()) {
                                        lstShowAll.clear();
                                        lstToday.clear();
                                        lstThisMonth.clear();
                                        lstSixMonthsAgo.clear();
                                        lstOneYearAgo.clear();
                                        lstPicture.clear();
                                        refresh = true;

                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new GratitudeMyListFragment());
                                        // getGratitudeList();

                                        ((MainActivity) getActivity()).loadFragment(new GratitudeMyListFragment(), "GratitudeMyListFragment", null);

                                        Intent share = new Intent(Intent.ACTION_SEND);
                                        share.setType("image/jpeg");
                                        Log.e("FILE_PATH", shareFile.getPath() + ">>>>");
                                        Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" + ".fileprovider", shareFile);
                                        share.putExtra(Intent.EXTRA_STREAM, photoURI);
                                        startActivity(Intent.createChooser(share, "Share Image"));
                                    }
                                }
                            }
                        };
                        new Handler().postDelayed(runnable, 500);

                    }

                    @Override
                    public void onFailure(Call<AddUpdateGratitudeModel> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });
            } else {
                Util.showToast(getActivity(), Util.networkMsg);
            }

        }

    }
*/
    /*commented by sahenita*/
/*
    private File createFolder() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Squad");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(getActivity(), "Failed to create directory SQUAD  Directory.",
                        Toast.LENGTH_LONG).show();

                Log.d("Laylam Path Create", "Prep UP");
                return null;

            }
        }
        // Create a media file name
        // For unique file name appending current timeStamp with file name
        java.util.Date date = new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());
        File mediaFile;
        // For unique video file name appending current timeStamp with file name
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }
*/
    /*commented by sahenita*/
/*
    public Bitmap getScreenShot(RelativeLayout view) {
        if (null != view) {
            int height = 0;
            //Get the ScrollView correctly
            for (int i = 0; i < view.getChildCount(); i++) {
                height += view.getChildAt(i).getHeight();
            }
            if (height < view.getHeight()) {
                height = view.getHeight();

            }
            if (height > 0) {
                //Create a cache to save the cache
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.RGB_565);
                //Can easily understand Canvas as a drawing board and bitmap is a block canvas
                Canvas canvas = new Canvas(bitmap);
                //Draw the contents of the view to the specified canvas Canvas
                view.draw(canvas);
                return bitmap;
            }
        }
        return null;
    }
*/
    /*commented by sahenita*/
/*
    private void pickImageFromGallery() {
        final Dialog dlg = new Dialog(getActivity());
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dlg.setContentView(R.layout.dialog_browse_bottom);

        Window window = dlg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        RelativeLayout rlGal = (RelativeLayout) dlg.findViewById(R.id.rlGal);
        RelativeLayout rlCancel = (RelativeLayout) dlg.findViewById(R.id.rlCancel);
        RelativeLayout rlCam = (RelativeLayout) dlg.findViewById(R.id.rlCam);
        rlCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission()) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        out = createFolder();
                        imgPath = out.getAbsolutePath();
                        if(null!=getActivity()){
                            Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" + ".fileprovider", out);
                            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                            getActivity().startActivityForResult(cameraIntent, ((MainActivity) getActivity()).CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                        }

                    } else {
                        if(null!=getActivity()){
                            if (!Settings.System.canWrite(getActivity())) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ((MainActivity) getActivity()).CAMERA_PIC_REQUEST_CODE_FROM_GRATITUDE_LIST);
                            }
                        }

                    }

                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    out = createFolder();
                    imgPath = out.getAbsolutePath();
                    if(null!=getActivity()){
                        Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" + ".fileprovider", out);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        getActivity().startActivityForResult(cameraIntent, ((MainActivity) getActivity()).CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);
                    }

                }
                //   startActivity(new Intent(getActivity(), CameraActivity.class));
            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        rlGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if(null!=getActivity()){
                        if (hasGalleryPermission()) {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);
                        } else {
                            if (!Settings.System.canWrite(getActivity())) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                        Manifest.permission.READ_EXTERNAL_STORAGE}, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_FROM_GRATITUDE_LIST);
                            }
                        }
                    }


                } else {
                    if(null!=getActivity()){
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);
                    }

                }

            }
        });
        dlg.show();

    }
*/
    /*commented by sahenita*/
/*
    private boolean hasGalleryPermission() {
        int hasPermissionWrite = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }
*/
/*commented by sahenita*/
/*
    private boolean hasCameraPermission() {
        int hasPermissionWrite = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasPermissionCamera = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED && hasPermissionCamera == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }
*/
    /*commented by sahenita*/
/*
    private void saveGratitudeLocally(boolean isNetAvailbale, GetGratitudeListModelInner modelInner) {

        if (isNetAvailbale) {
            GetGratitudeListModelInner getGratitudeListModelInner = modelInner;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String todatDate = simpleDateFormat.format(calendar.getTime());
            Gson gson = new Gson();
            String gratitudeModel = gson.toJson(getGratitudeListModelInner);
            String timeStamp = String.valueOf(calendar.getTimeInMillis());
            GratitudeEntity gratitudeEntity = new GratitudeEntity(getGratitudeListModelInner.getId(), todatDate, gratitudeModel, timeStamp, true);
            mDisposable.add(gratitudeViewModel.insertGratitude(gratitudeEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.e("GRATITUDE_SAVE", "SUCCESSFULL");
                        */
/*Util.isReloadTodayMainPage = false;
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new MbhqTodayMainFragment());
                        ((MainActivity)getActivity()).loadFragment(new MbhqTodayMainFragment(),"MbhqTodayMain",null);*//*

                    }, throwable -> {
                        Log.e("GRATITUDE_SAVE", "NOT_SUCCESSFULL");
                    }));
        } else {
            GetGratitudeListModelInner getGratitudeListModelInner = makeGratitudeModelForAdd(edtAchieveDialog, null);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String todatDate = simpleDateFormat.format(calendar.getTime());
            Gson gson = new Gson();
            String gratitudeModel = gson.toJson(getGratitudeListModelInner);
            String timeStamp = String.valueOf(calendar.getTimeInMillis());
            GratitudeEntity gratitudeEntity = new GratitudeEntity(0, todatDate, gratitudeModel, timeStamp, false);
            mDisposable.add(gratitudeViewModel.insertGratitude(gratitudeEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.e("GRATITUDE_SAVE", "SUCCESSFULL" + todatDate);
                        Util.GRATITUDE_SAVE_OFFLINE_COUNTER++;
                        Util.isReloadTodayMainPage = false;

                        openDialogForGratitudeSaveLocally();

                    }, throwable -> {
                        Log.e("GRATITUDE_SAVE", "NOT_SUCCESSFULL");
                    }));
        }
    }
*/
    /*commented by sahenita*/
/*
    private void openDialogForGratitudeSaveLocally() {

        try {
            Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_save_gratitude_local_success);

            RelativeLayout rlOk = dialog.findViewById(R.id.rlOk);

            rlOk.setOnClickListener(view -> {
                dialog.dismiss();
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayMainFragment());
                ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            });

            dialog.show();
        } catch (Exception e) {
            Log.e("block", "2");
            e.printStackTrace();
        }
    }
*/


    @Override
    public void onPause() {
        super.onPause();
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){
            }else{
                todayPageUpdateHandler.removeCallbacks(todayPageUpdateTimeTask);

            }
        }else{
            todayPageUpdateHandler.removeCallbacks(todayPageUpdateTimeTask);

        }


    }

    private void loadAdapter(ArrayList<HabitSwap> habitSwapList) {

        if (habitSwapList.size() > 0) {
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.GONE);
            recyclerTodayHabit.setVisibility(View.VISIBLE);

        } else {
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.VISIBLE);
            recyclerTodayHabit.setVisibility(View.GONE);
        }

        // recyclerTodayHabit.setAdapter(null);


//        for(int i=0;i<habitSwapList.size();i++){
//
//          Log.e("listHabit","HabitAll"+ new Gson().toJson(habitSwapList.get(i)));
//        }

        habitHackerAdpapter =
                new TodayHabitHackerAdpapter(
                        getActivity(),
                        habitSwapList,
                        true,
                        false,
                        new TodayHabitHackerAdpapter.OnMultipleHabitTaskListener() {
                            @Override
                            public void onMultipleHabitTaskSelected(Integer taskId, boolean isDone, Integer habitId, Integer tickingMode) {

                                boolean taskIdAlreadyInTheList = false;

                                Log.i("called_habit_section", "habit id =>" + habitId + " tickingMode => " + tickingMode + " taskId => " + taskId + " isDone => " + isDone);
                                //  habitBreakTickUntickModels.clear();

                                if(habitBreakTickUntickModels.size()>0){
                                    for (int index = 0; index < habitBreakTickUntickModels.size(); index++) {
                                        if(habitBreakTickUntickModels.get(index).getHabitId()==habitId){
                                            habitBreakTickUntickModels.remove(index);
                                        }
                                    }
                                }



                          /*      for (int index = 0; index < habitBreakTickUntickModels.size(); index++) {

                                  //  if (habitBreakTickUntickModels.get(index).getTaskId().equals(taskId)) {
                                        Log.i("called_habit_section","caled_for_loop");
                                       // Log.i(TAG, "habit id =>" + habitId);
                                        habitBreakTickUntickModels.get(index).setTaskId(taskId);
                                        habitBreakTickUntickModels.get(index).setHabitId(habitId);
                                        habitBreakTickUntickModels.get(index).setIsDone(isDone);
                                        habitBreakTickUntickModels.get(index).setTickingMode(tickingMode);
                                        //taskIdAlreadyInTheList = true;
                                   // }

                                }*/

                                if (!taskIdAlreadyInTheList) {
                                    Log.i("called_habit_section","not_caled_for_loop");
                                    HabitBreakTickUntickModel habitToBeAdded = new HabitBreakTickUntickModel();
                                    Log.i(TAG, "habit id2 =>" + habitId);
                                    habitToBeAdded.setHabitId(habitId);
                                    habitToBeAdded.setTaskId(taskId);
                                    habitToBeAdded.setIsDone(isDone);
                                    habitToBeAdded.setTickingMode(tickingMode);
                                    habitBreakTickUntickModels.add(habitToBeAdded);
                                }

                                if (habitBreakTickUntickModels.isEmpty()) {
                                    rlSaveHabitCancel.setVisibility(View.GONE);
                                } else {
                                    rlSaveHabitCancel.setVisibility(View.VISIBLE);
                                }

                            }

                            @Override
                            public void onAllCheck(String tag) {
                                Log.e("allcheckList", "listallcheck----------->");
                                if (tag.equals(Util.TAG_ALLCHECKED)) {
                                    // allChecked=true;
                                    selectAll.setTag(Util.TAG_ALLCHECKED);
                                    selectAll.setImageResource(R.drawable.mbhq_green_check);


                                } else if (tag.equals(Util.TAG_CROSS)) {
                                    selectAll.setTag(Util.TAG_CROSS);
                                    selectAll.setImageResource(R.drawable.ic_close_gray);

                                } else if (tag.equals(Util.TAG_UNCHECKED)) {
                                    selectAll.setTag(Util.TAG_UNCHECKED);
                                    selectAll.setImageResource(R.drawable.mbhq_circle_green);

                                }
                                sharedPreference.write("selectall_habit", "", selectAll.getTag().toString());

                            }
                        },
                        MbhqTodayTwoFragment.this);


        recyclerTodayHabit.setAdapter(habitHackerAdpapter);
        Log.e("totalRow", "totalRow2");

        //added by jyotirmoy 22.09.22
        selectAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (habitSwapList != null) {

                    Log.e("imgName", "imagename---------------->" + selectAll.getResources().getResourceName(R.drawable.mbhq_circle_green));
                    Log.e("imgName", "imagename---------------->" + selectAll.getDrawable().getConstantState());
                    Log.e("imgName", "drawable---------------->" + getResources().getDrawable(R.drawable.mbhq_circle_green).getConstantState());

                    Log.e("Tag unCheck", "Tag Uncheck------------------>" + selectAll.getTag());
                    Log.i("called_habit_section","0");
                    Log.i("habit_section",selectAll.getTag().toString());
                    if (selectAll.getTag().toString().equals(Util.TAG_UNCHECKED)) {
                        Log.i("habit_section","TAG_UNCHECKED");
                        selectAll.setTag(Util.TAG_ALLCHECKED);

                        selectAll.setImageResource(R.drawable.mbhq_green_check);


                        Log.e("select all", "allcheck----------->");

                        for (int i = 0; i < habitSwapList.size(); i++) {

                            HabitSwap data = habitSwapList.get(i);

                            if (data.getNewAction().getCurrentDayTask() != null) {

                                data.getNewAction().getCurrentDayTask().setIsDone(true);

                                habitHackerAdpapter.updateTask(data.getNewAction().getCurrentDayTask().getTaskMasterId(), true, habitSwapList.get(i).getHabitId(), 0);

                            } else if (data.getNewAction().getCurrentDayTask2() != null) {

                                data.getNewAction().getCurrentDayTask2().setIsDone(true);

                                habitHackerAdpapter.updateTask(data.getNewAction().getCurrentDayTask2().getTaskMasterId(), true, habitSwapList.get(i).getHabitId(), 0);

                            }

                        }
                        rlSaveHabitCancel.setVisibility(View.VISIBLE);
                        //allChecked=true;
                    } else if (selectAll.getTag().toString().equals(Util.TAG_ALLCHECKED)) {
                        Log.i("habit_section","TAG_ALLCHECKED");
                        Log.i("called_habit_section","uncheck_1");
                        Log.e("Tag Cross", "all close----------->");

                        selectAll.setTag(Util.TAG_CROSS);

                        selectAll.setImageResource(R.drawable.ic_close_gray);
                        ;

                        for (int i = 0; i < habitSwapList.size(); i++) {

                            HabitSwap data = habitSwapList.get(i);

                            if (data.getSwapAction().getCurrentDayTask() != null) {

                                data.getSwapAction().getCurrentDayTask().setIsDone(true);

                                if (data.getNewAction().getCurrentDayTask() != null) {
                                    data.getNewAction().getCurrentDayTask().setIsDone(false);
                                } else if (data.getNewAction().getCurrentDayTask2() != null) {
                                    data.getNewAction().getCurrentDayTask2().setIsDone(false);
                                }

                                habitHackerAdpapter.updateTask(data.getSwapAction().getCurrentDayTask().getTaskMasterId(), true, habitSwapList.get(i).getHabitId(), 1);

                            } else if (data.getSwapAction().getCurrentDayTask2() != null) {

                                data.getSwapAction().getCurrentDayTask2().setIsDone(true);

                                if (data.getNewAction().getCurrentDayTask() != null) {
                                    data.getNewAction().getCurrentDayTask().setIsDone(false);
                                } else if (data.getNewAction().getCurrentDayTask2() != null) {
                                    data.getNewAction().getCurrentDayTask2().setIsDone(false);
                                }

                                habitHackerAdpapter.updateTask(data.getSwapAction().getCurrentDayTask2().getTaskMasterId(), true, habitSwapList.get(i).getHabitId(), 1);

                            }

                        }

                        rlSaveHabitCancel.setVisibility(View.VISIBLE);
                        //allCross=true;
                    } else if (selectAll.getTag().toString().equals(Util.TAG_CROSS)) {
                        Log.i("habit_section","TAG_CROSS");
                        Log.i("called_habit_section","blankcheck_2");
                        Log.e("all unchecked", "all unchecked----------->");

                        selectAll.setTag(Util.TAG_UNCHECKED);
                        selectAll.setImageResource(R.drawable.mbhq_circle_green);

                        for (int i = 0; i < habitSwapList.size(); i++) {

                            HabitSwap data = habitSwapList.get(i);

                            if (data.getSwapAction().getCurrentDayTask() != null) {

                                data.getSwapAction().getCurrentDayTask().setIsDone(false);

                            } else if (data.getSwapAction().getCurrentDayTask2() != null) {

                                data.getSwapAction().getCurrentDayTask2().setIsDone(false);

                            }

                            if (data.getNewAction().getCurrentDayTask() != null) {

                                data.getNewAction().getCurrentDayTask().setIsDone(false);

                                habitHackerAdpapter.updateTask(data.getNewAction().getCurrentDayTask().getTaskMasterId(), false, habitSwapList.get(i).getHabitId(), 2);


                            } else if (data.getNewAction().getCurrentDayTask2() != null) {

                                data.getNewAction().getCurrentDayTask2().setIsDone(false);

                                habitHackerAdpapter.updateTask(data.getNewAction().getCurrentDayTask2().getTaskMasterId(), false, habitSwapList.get(i).getHabitId(), 2);

                            }

                        }

                        rlSaveHabitCancel.setVisibility(View.VISIBLE);
                        //    allChecked=false;
                        // allUnchecked=true;
                    }
                    habitHackerAdpapter.notifyDataSetChanged();


                }


            }
        });


    }

    public void getGrowthHomapageApiCall(boolean dailypopup) {
        Log.i("called_habit_all_time","1");
        if(dailypopup){
            txtLoading.setVisibility(View.VISIBLE);
        }

        habitSwapList.clear();
        SharedPreferences.Editor editor=null;


        //  ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait..."); /////////////
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
        int userid = 0;
        int session = 0;
        if ("".equalsIgnoreCase(sharedPreference.read("UserID", ""))) {
            userid = Integer.parseInt(Util.userid);

        } else {
            userid = Integer.parseInt(sharedPreference.read("UserID", ""));

        }
        if ("".equalsIgnoreCase(sharedPreference.read("UserSessionID", ""))) {
            session = Integer.parseInt(Util.session);
        } else {
            session = Integer.parseInt(sharedPreference.read("UserSessionID", ""));
        }
        HashMap<String, Object> hashReq = new HashMap<>();
        hashReq.put("UserId", userid);
        hashReq.put("Key", Util.KEY);
        hashReq.put("ForDate", forDate);
        Log.d("FRRRRDDDDDD", "getGrowthHomapageApiCall: " + forDate);
        hashReq.put("UserSessionID", session);

        Calendar c = Calendar.getInstance();
        Date currentDate = c.getTime();
        Call<GetAppHomePageValuesResponseModel> appHomePageValuesResponseModelCall = null;
        Call<JsonElement> appHomePageValuesResponseModelCall_new = null;

        if (strDateFormat.format(currentDate).equals(forDate)) {
            forDate_optionclick=forDate;
            appHomePageValuesResponseModelCall = finisherService.getGrowthHomapage(hashReq);
            isHabitDateCurrentDate = true;
        } else {
            forDate_optionclick=forDate;
            appHomePageValuesResponseModelCall = finisherService.getGrowthHomePageHabitOnly(hashReq);
            isHabitDateCurrentDate = false;
        }

        Log.e("req", "Reqst" + new Gson().toJson(hashReq));
        //  GetAppHomePageValuesResponseModel

        SharedPreferences.Editor finalEditor = editor;
        appHomePageValuesResponseModelCall.enqueue(new Callback<GetAppHomePageValuesResponseModel>() {
            @Override
            public void onResponse(Call<GetAppHomePageValuesResponseModel> call, Response<GetAppHomePageValuesResponseModel> response) {
                // progressDialog.dismiss(); //////////////
                if (response.body() != null) {
                    Gson gson = new Gson();
                    Log.e("resp", "respbdy" + response.body());
                }
                if (response.body() != null && response.body().getSuccessFlag()) {
                    //  setInteractionDisabled(false);
                    Gson gson = new Gson();


                    Type type = new TypeToken<ArrayList<HabitSwap>>() {
                    }.getType();
                    habitSwapList = gson.fromJson(gson.toJson(response.body().getHabits()), type);

                    if (null!= habitSwapList  && habitSwapList.size() > 0) {

                        if (null != getActivity()) {

                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                            String formattedDate = dateFormatter.format(new Date());
                            Log.i(TAG, "Habit insert time => " + formattedDate);
                            sharedPreference.write("HABIT_INSERTION_DATE_TIME", "", formattedDate);
                            //save the list to SharedPreferences

                            sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));

                        }


                        loadAdapter(habitSwapList);
                        Log.d("RESSSSSSMM", "onResponse: " + response.body().toString());
                    } else {
                        txtLoading.setVisibility(View.GONE);
                        imgBigPlus.setVisibility(View.VISIBLE);
                        recyclerTodayHabit.setVisibility(View.GONE);
                    }




                    try{
                        Log.i("called_habit_all_time","2");

                        habbitFirstTime = sharedPreference.read("habbitFirstTime_1","");
                        habbitFirstTime_firstpopup=sharedPreference.read("habbitFirstTime_firstpopup","");


                        firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
                        Log.e("FFLLLIIII----1", "onCreateView: " + firstLoginTime );

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                        Calendar calendar = Calendar.getInstance();
                        currentTime = simpleDateFormat.format(calendar.getTime());
                        Log.e("CURRRTIIII----1", "onCreateView: " + currentTime );

                        Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
                        Date dateCurrentTime = simpleDateFormat.parse(currentTime);

                        if (dateCurrentTime != null) {
                            if(dateCurrentTime.after(dateFirstLoginDt) && "true".equals(habbitFirstTime)){
                                Log.e("CHHHHTIMMMMMM----1", "onCreateView: " + "TRUE" );
                                sharedPreference.write("habbitFirstTime_1", "", "false");


                                // show popup here
                                openInfoDialog();

                            }else {
                                Log.e("CHHHHTIMMMMMM", "onCreateView: " + "FALSE" );
                            }
                        }
                        Log.i("called_habit_all_time","3");

                        gratitudeDailyPopupCall();
                    }catch (Exception e){
                        e.printStackTrace();
                    }




                }
            }


            @Override
            public void onFailure(Call<GetAppHomePageValuesResponseModel> call, Throwable t) {
                // progressDialog.dismiss() ; //////////////////
            }
        });



    }


    public void deleteHabitSwapApiCall(Integer HABIT_ID) {
        if (null != getActivity()) {

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
//                            ((MainActivity) getActivity()).callTaskStatusForDateAPI();
                            sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));

                            Completable.fromAction(() -> {
                                        mViewModelDel.deleteByHabitId(HABIT_ID);
                                    })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        habitSwapList.clear();
                                        sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));

                                        Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                        Completable.fromAction(() -> {
                                                    mViewModelCalendar.deleteHabitCalendarById(HABIT_ID);
                                                }).subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(() -> {
                                                    habitSwapList.clear();
                                                    sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                                                    Util.isReloadTodayMainPage = true;
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                                }, throwable -> {
                                                    habitSwapList.clear();
                                                    sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                                                    Util.isReloadTodayMainPage = true;
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                                });
                                    }, throwable -> {
                                        habitSwapList.clear();
                                        sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));

                                        Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                        Util.isReloadTodayMainPage = true;
                                        habitSwapList.clear();
                                        sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitCalendarTickUntickFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
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

    }

    private void openInfoDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_habbit); //////////////////////////////////////////////////////
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        //ImageView imgHabitAdd = dialog.findViewById(R.id.imgHabitAdd);
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
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerAddFirstPage());
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

        //added by jyotirmoy patra 18.10.22
        //check dialog dismis or not
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //gratitudeDailyPopupCall();


            }
        });
    }

    private void gratitudeDailyPopupCall() {
        //added by Jyotirmoy Patra 16.09.22


        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Calendar calendar = Calendar.getInstance();


                Log.e("called_d","yes1");
                //get current user name
                String userName = sp.getString("firstName", "");
                userName = userName.toUpperCase();

                if(("").equals(getprompt)){
                    getprompt = "Prompt of the day";
                }
                Log.e("PROMMMPT", "run: " + getprompt );
                String txtPrmpt = "";
                String txtAdd = "";

                try{
                    firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
                    habbitFirstTime=sharedPreference.read("habbitFirstTime", "");

//                    firstLoginTime = "2023/06/16";
                    Log.e("FFLLLIIII", "onCreateView: " + firstLoginTime );

                    @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    Calendar cal = Calendar.getInstance();
                    currentTime = simpleDateFormat.format(cal.getTime());
                    Log.e("CURRRTIIII", "onCreateView: " + currentTime );

                    if("true".equals(habbitFirstTime)){
                        sharedPreference.write("Last_open_habit", "", currentTime);
                    }
                    Log.e("LASTTTTTT", "onCreateView: " + sharedPreference.read("Last_open_habit", "") );


                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    long daysDiffForDialog=0;
                    try {

                        Date d1 = sdf.parse(firstLoginTime);
                        Date d2 = sdf.parse(currentTime);
                        Date d3 = sdf.parse(sharedPreference.read("Last_open_habit", ""));

                        if(Objects.requireNonNull(d2).after(d3)){
                            sharedPreference.write("Last_open_habit", "", currentTime);
                            sharedPreference.write("habbitFirstTime", "", "true");
                            Log.e("LASTTTTTT", "onCreateView: " + sharedPreference.read("Last_open_habit", "") );
                            // habbitFirstTime = "true";
                        }else{
                            Log.e("LASTTTTTT", "onCreateView: " + sharedPreference.read("Last_open_habit", "") );

                            // habbitFirstTime = "false";
                        }

                        long differenceInMilliseconds = Math.abs(d2.getTime() - d1.getTime());
                        daysDiffForDialog = TimeUnit.MILLISECONDS.toDays(differenceInMilliseconds);
                        daysDiffForDialog++;
                        System.out.println("Difference in days: " + daysDiffForDialog);
                        Log.d("DDAYYYDIFFFFF", "run: " + daysDiffForDialog);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    habbitFirstTime=sharedPreference.read("habbitFirstTime", "");
                    Log.e("LASTTTTTT------1", "onCreateView: " + sharedPreference.read("habbitFirstTime", "") );

                    if("true".equals(habbitFirstTime)){
                        sharedPreference.write("habbitFirstTime", "", "false");
                        if(daysDiffForDialog % 2 == 0){
                            // even case

                            txtPrmpt = getprompt;
                            txtAdd = "Let’s Journal";

                        }else{
                            // odd case
                            txtPrmpt = "What are your grateful for today?";
                            txtAdd = "I’m grateful for";
                        }
                        showDailyGratitudePrompt(userName,txtPrmpt,txtAdd);

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }

                ////////////////////////////////////////////////////////////////////////////////////////
            }
        }, 100);


    }
    private void openHabitHistoryDialog() {
        Date currentDate = new Date();

        Dialog dialog = new Dialog(getContext(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_habit_history);

        View closeModal = dialog.findViewById(R.id.closeModal);
        RecyclerView habitHistoryRecycler = dialog.findViewById(R.id.habitHistoryRecycler);

        ViewCompat.setNestedScrollingEnabled(habitHistoryRecycler, false);

        habitHistoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        HabitHistoryAdapter habitHistoryAdapter = new HabitHistoryAdapter(currentDate, 10, new HabitHistoryAdapter.OnHabitHistoryDateClickListener() {
            @Override
            public void onDateClicked(Date date) {

                Log.i(TAG, "clicked date : " + date.toString());

                Log.i(TAG, "clicked formatted date : " + strDateFormat.format(date));
                forDate_optionclick=strDateFormat.format(date);
                dialog.dismiss();
                habitSwapList.clear();
                sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                Bundle bundle = new Bundle();

                bundle.putString("FOR_DATE", strDateFormat.format(date));

                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());

                ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", bundle);

            }
        });
        habitHistoryRecycler.setAdapter(habitHistoryAdapter);


        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();

    }




/*commented by sahenita*/
/*
    private String readJSONDataFromFile() throws IOException {

        InputStream inputStream = null;
        StringBuilder builder = new StringBuilder();

        try {

            String jsonString = null;
            inputStream = getResources().openRawResource(R.raw.holidays);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, "UTF-8"));

            while ((jsonString = bufferedReader.readLine()) != null) {
                builder.append(jsonString);
            }

        } finally {
            Log.e("block", "8");
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return new String(builder);
    }
*/


/*commented by sahenita*/
/*
    private void setInteractionDisabled(Boolean disabled) {
        if (disabled) {
            if (null == getActivity()) {

            } else {
                getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        } else {
            if (null == getActivity()) {

            } else {
                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

            }
        }
    }
*/


    private Runnable todayPageUpdateTimeTask = new Runnable() {
        public void run() {

            if (isTodayHabitShowing) {

                try {

                    Calendar today = Calendar.getInstance();

                    String todayDate = strDateFormat.format(today.getTime());

                    long todayTimeStamp = strDateFormat.parse(todayDate).getTime();

                    long forDateTimeStamp = strDateFormat.parse(forDate).getTime();

                    long dateDiffInMillis = todayTimeStamp - forDateTimeStamp;
                    int dateDiffInDays = (int) TimeUnit.MILLISECONDS.toDays(dateDiffInMillis);

                    Log.i(TAG, "today date: " + todayDate + " today timestamp: " + todayTimeStamp + " for date: " + forDate + " for date time stamp:" + forDateTimeStamp);

                    if (dateDiffInDays == 1) {
                        Log.e("calledhabit", "10");
                        Log.i(TAG, "Date diff is 1.");
                        habitSwapList.clear();
                        sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));
                        Bundle bundle = new Bundle();

                        bundle.putString("FOR_DATE", strDateFormat.format(today.getTime()));

                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());

                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", bundle);

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("block", "7");
                    Log.i(TAG, "an error occured. error in today update time task:" + e.getMessage());
                }

            }

            todayPageUpdateHandler.removeCallbacks(todayPageUpdateTimeTask);
            todayPageUpdateHandler.postDelayed(todayPageUpdateTimeTask, 30000);

        }
    };

    private Handler todayPageUpdateHandler = new Handler();






    private void checkGratitudeCacheExpiration() {

        if (Connection.checkConnection(getContext())) {

            HashMap<String, Object> hashReq = new HashMap<>();
            //hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getContext());
            Call<GetGratitudeCacheExpiryTimeResponse> serverCall = finisherService.GetGratitudeCacheExpiryTime(hashReq);
            serverCall.enqueue(new Callback<GetGratitudeCacheExpiryTimeResponse>() {
                @Override
                public void onResponse(Call<GetGratitudeCacheExpiryTimeResponse> call, Response<GetGratitudeCacheExpiryTimeResponse> response) {
                    //progressDialog.dismiss();
                    GetGratitudeCacheExpiryTimeResponse responseBody = response.body();
                    if (responseBody != null && responseBody.isSuccessFlag()) {
                        try {
                            sharedPreference.write("GRATITUDE_EXPIRATION_DATE_TIME", "", responseBody.getEqJournalExpiryDateTime());
                            sharedPreference.write("HABIT_EXPIRATION_DATE_TIME", "", responseBody.getHabitExpiryDateTime());
                            ///////////////////
                            rlSaveHabitCancel.setVisibility(View.GONE);
                            String tag="";
                            tag = sharedPreference.read("selectall_habit", "");
                          /* if(null!=sharedPreference.read("selectall_habit", "") ){
                               Log.i("habit_ckicked_value","1");
                               if (tag.equals(Util.TAG_ALLCHECKED)) {
                                   Log.i("habit_ckicked_value","2");

                                   // allChecked=true;
                                   selectAll.setTag(Util.TAG_ALLCHECKED);
                                   selectAll.setImageResource(R.drawable.mbhq_green_check);


                               } else if (tag.equals(Util.TAG_CROSS)) {

                                   Log.i("habit_ckicked_value","3");
                                   selectAll.setTag(Util.TAG_CROSS);
                                   selectAll.setImageResource(R.drawable.ic_close_gray);

                               } else if (tag.equals(Util.TAG_UNCHECKED)) {

                                   Log.i("habit_ckicked_value","4");
                                   selectAll.setTag(Util.TAG_UNCHECKED);
                                   selectAll.setImageResource(R.drawable.mbhq_circle_green);

                               }
                           }else{

                               Log.i("habit_ckicked_value","5");
                               selectAll.setTag(Util.TAG_UNCHECKED);
                               selectAll.setImageResource(R.drawable.mbhq_circle_green);

                           }*/


                            getAchievementsFromDB();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetGratitudeCacheExpiryTimeResponse> call, Throwable t) {
                    //progressDialog.dismiss();
                    t.printStackTrace();

                }
            });

        }

    }


    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {

                if("3".equalsIgnoreCase(accesstype)&&"false".equalsIgnoreCase(eq_access)){

                }else{
                    Util.calledgratitudeafterinternatecomming = false;
                    ((MainActivity) getActivity()).fungratitudeicon();
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                    Util.isReloadTodayMainPage = true;
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                    //((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                    // Internet connection is lost
                }

            }else{
                Util.calledgratitudeafterinternatecomming=true;

            }
        }
    };


    public void updateMultipleTaskStatusApiCall() {

        if(null!=getActivity()){
            if (Connection.checkConnection(getActivity())) {
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);

                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String formattedDate = df.format(c);

                SharedPreference sharedPreference = new SharedPreference(getActivity());
                FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

                ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");


                HashMap<String, Object> hashReq = new HashMap<>();

                hashReq.put("TaskDoneStatuses", habitBreakTickUntickModels);
                hashReq.put("Key", Util.KEY);
                hashReq.put("DateTicked", formattedDate);
                hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                Call<JsonObject> userHabitSwapsModelCall = finisherService.UpdateMultipleTaskStatus(hashReq);

                userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        progressDialog.dismiss();
                        if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                            // Util.showToast(getActivity(), "Saved Successfully");

                            List<Integer> habitIds = new ArrayList<Integer>();
                            for (int index = 0; index < habitBreakTickUntickModels.size(); index++) {
                                if (!habitIds.contains(habitBreakTickUntickModels.get(index).getHabitId())) {
                                    habitIds.add(habitBreakTickUntickModels.get(index).getHabitId());
                                }
                            }
                            Completable.fromAction(() -> {
                                        mViewModel.deleteHabitCalendarByIds(habitIds);
                                    }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        // Util.showToast(getActivity(), "Saved Successfully1111111111111111111111");
                                    }, throwable -> {

                                    });

                            Completable.fromAction(() -> {
                                        mViewModelEdit.deleteByHabitIds(habitIds);
                                    })
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        //Util.showToast(getActivity(), "Saved Successfully0000000000000000000000");
                                        Log.e("DATABASE", "DELETE_SUCCESSFULL");
                                    }, throwable -> {
                                        Log.e("DATABASE", "DELETE_UN_SUCCESSFULL");
                                    });


                            try {
                                sharedPreference.write("selectall_habit", "", selectAll.getTag().toString());

                                //   getGrowthHomapageApiCall(false);
                                habitBreakTickUntickModels.clear();
                                rlSaveHabitCancel.setVisibility(View.GONE);
                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabbitDetailsCalendarFragment());
                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new HabitHackerListFragment());
                                Util.isReloadTodayMainPage = true;
                                if(null==forDate_optionclick){
                                    ((MainActivity) getActivity()).callTaskStatusForDateAPI("");
                                    Log.i("forDate","0");
                                    Log.i(TAG, habitIds.toString());

                                    habitSwapList.clear();
                                    sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));

                                    if(null!=getActivity()){
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                    }
                                }else{
                                    Log.e("called_order","200");
                                    SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
                                    Calendar calendar = Calendar.getInstance();
                                    String dayNameDateFormat=  dayFormat.format(calendar.getTime());
                                    Log.e("called_order",dayFormat.format(calendar.getTime()));

                                    Log.e("called_order","210");
                                    if("Mon".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));


                                        Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 6; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }


                                    }else if("Tue".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));

                                        for (int i = 1; i <= 1; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, -1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }

                                        Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 5; i++) {
                                            calendar2.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar2.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            Log.e("called_order", "called");
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }
                                    }else if("Wed".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));

                                        for (int i = 1; i <= 2; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, -1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }

                                        Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 4; i++) {
                                            calendar2.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar2.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }
                                    }else if("Thu".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));

                                        for (int i = 1; i <= 3; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, -1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }

                                        Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 3; i++) {
                                            calendar2.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar2.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }
                                    }else if("Fri".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));

                                        for (int i = 1; i <= 4; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, -1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }

                                        Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 2; i++) {
                                            calendar2.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar2.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }
                                    }else if("Sat".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));

                                        for (int i = 1; i <= 5; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, -1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }

                                        Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 1; i++) {
                                            calendar2.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar2.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }
                                    }else if("Sun".equalsIgnoreCase(dayNameDateFormat)){
                                        Log.e("called_order","202");
                                        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                        List<String> datesList_str = new ArrayList<>();
                                        // Get the current date
                                        Calendar calendar1 = Calendar.getInstance();
                                        Date currentDate1 = calendar1.getTime();
                                        datesList_str.add(dateFormat1.format(currentDate1));

                                        for (int i = 1; i <= 6; i++) {
                                            calendar1.add(Calendar.DAY_OF_YEAR, -1); // Move to the previous day
                                            Date previousDate = calendar1.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }

                                      /*  Calendar calendar2 = Calendar.getInstance();
                                        Date currentDate2 = calendar2.getTime();
                                        for (int i = 1; i <= 1; i++) {
                                            calendar2.add(Calendar.DAY_OF_YEAR, 1); // Move to the previous day
                                            Date previousDate = calendar2.getTime();
                                            datesList_str.add(dateFormat1.format(previousDate));
                                        }*/
                                        Log.e("called_order", String.valueOf(datesList_str.size()));
                                        Log.e("called_order", String.valueOf(datesList_str.get(0)+"   0"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(1)+ "  1"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(2)+"   2"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(3)+ "  3"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(4)+"   4"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(5)+ "  5"));
                                        Log.e("called_order", String.valueOf(datesList_str.get(6)+"   6"));
                                        Log.e("called_order", forDate_optionclick);

                                        if(datesList_str.contains(forDate_optionclick)){
                                            ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);

                                        }
                                    }



                                    Log.i(TAG, habitIds.toString());

                                    habitSwapList.clear();
                                    sharedPreference.write("my_list", "", new Gson().toJson(habitSwapList));

                                    Calendar c = Calendar.getInstance();
                                    Date currentDate = c.getTime();
                                    if (strDateFormat.format(currentDate).equals(forDate_optionclick)) {
                                        Log.i("forDate","1");
                                        if(null!=getActivity()){
                                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                        }
                                    } else{
                                        Log.i("forDate","2");
                                        Bundle bundle = new Bundle();

                                        bundle.putString("FOR_DATE", forDate_optionclick);

                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());

                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", bundle);

                                    }

                                }
                                // ((MainActivity) getActivity()).callTaskStatusForDateAPI(forDate_optionclick);






                            } catch (Exception e) {
                                Log.e("block", "5");
                                e.printStackTrace();
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

    }

    private void GetJounalPromptofDay() {
        if (Connection.checkConnection(getActivity())) {
            SharedPreference sharedPreference = new SharedPreference(getActivity());
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            Log.e("userSession", "sessionid>>>" + sharedPreference.read("UserSessionID", ""));
            Log.e("key", "key>>>" + Util.KEY);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetPrompt> promptCall = finisherService.getJounalPromptofDay(hashReq);
            promptCall.enqueue(new Callback<GetPrompt>() {
                @Override
                public void onResponse(Call<GetPrompt> call, Response<GetPrompt> response) {

                    if (response.isSuccessful()) {
                        String prompt = response.body().getPromptOftheDay().toString();
                        Log.e("Promptoftheday", "Prompt of the day>>>>>>>" + prompt);
                        if (getActivity() != null) {
                            getprompt = prompt;
                            Util.prompt=getprompt;
                            // SharedPreferences promptPref = getActivity().getSharedPreferences("getpromptoftheday1", MODE_PRIVATE);
                            // SharedPreferences.Editor myEditPrompt = promptPref.edit();
                            // myEditPrompt.putString("promptname", prompt);
                            // myEditPrompt.commit();
                        } else {
                            // Handle the case when getActivity() returns null
                        }
                    } else {
                        Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                    }

                }

                @Override
                public void onFailure(Call<GetPrompt> call, Throwable t) {
                    Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                }
            });
        }

    }


    private void getUserPaidStatusApiCall() {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Email", sharedPreference.read("USEREMAIL", ""));
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetUserPaidStatusModel> paidStatusModelCall = finisherService.getUserPaidStatusApi(hashReq);
            paidStatusModelCall.enqueue(new Callback<GetUserPaidStatusModel>() {
                @Override
                public void onResponse(Call<GetUserPaidStatusModel> call, Response<GetUserPaidStatusModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().getSuccessFlag()) {

                        Integer accesstype=response.body().getMbhqAccessType();
                        Log.i("printttttttttttttttttttttttttttttt",String.valueOf(accesstype));
                        Log.i("111111",String.valueOf(accesstype));
                        Boolean HabitAccess=response.body().getHabitAccess();
                        Log.i("111111",String.valueOf(HabitAccess));
                        Boolean EqJournalAccess=response.body().getEqJournalAccess();
                        Log.i("111111",String.valueOf(EqJournalAccess));
                        Boolean MeditationAccess=response.body().getMeditationAccess();
                        Log.i("111111",String.valueOf(MeditationAccess));
                        Boolean ForumAccess=response.body().getForumAccess();
                        Log.i("111111",String.valueOf(ForumAccess));
                        Boolean LiveChatAccess=response.body().getLiveChatAccess();
                        Log.i("111111",String.valueOf(LiveChatAccess));
                        Boolean TestsAccess=response.body().getTestsAccess();
                        Log.i("111111",String.valueOf(TestsAccess));
                        Boolean CourseAccess=response.body().getCourseAccess();
                        Log.i("111111",String.valueOf(CourseAccess));
                        sharedPreference.write("accesstype", "", String.valueOf(accesstype));
                        sharedPreference.write("HabitAccess", "", String.valueOf(HabitAccess));
                        sharedPreference.write("EqJournalAccess", "", String.valueOf(EqJournalAccess));
                        sharedPreference.write("MeditationAccess", "", String.valueOf(MeditationAccess));
                        sharedPreference.write("ForumAccess", "", String.valueOf(ForumAccess));
                        sharedPreference.write("LiveChatAccess", "", String.valueOf(LiveChatAccess));
                        sharedPreference.write("TestsAccess", "", String.valueOf(TestsAccess));
                        sharedPreference.write("CourseAccess", "", String.valueOf(CourseAccess));
                        String accesstype1=sharedPreference.read("accesstype","");
                        String habit_access=sharedPreference.read("HabitAccess","");
                        String eq_access=sharedPreference.read("EqJournalAccess","");
                        String medi_access=sharedPreference.read("MeditationAccess","");
                        String forum_access=sharedPreference.read("ForumAccess","");
                        String Live_access=sharedPreference.read("LiveChatAccess","");
                        String Test_acess=sharedPreference.read("TestsAccess","");
                        String Course_access=sharedPreference.read("CourseAccess","");

                        Log.i("1111111100",eq_access);
                        Log.i("2222222200",medi_access);
                        Log.i("3333333300",accesstype1);
                        Log.i("4444444400",habit_access);
                        Log.i("5555555500",forum_access);
                        Log.i("6666666600",Live_access);
                        Log.i("7777777700",Test_acess);
                        Log.i("8888888800",Course_access);

                        showpopup_upgrade();


                    }
                }

                @Override
                public void onFailure(Call<GetUserPaidStatusModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    private void showpopup_upgrade( ){
        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
        alertDialogCustom.ShowDialog("Efc", "Your Access Check has been Completed.", false);
        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
            @Override
            public void onDone(String title) {

                if(null!=getActivity()){
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                }

            }

            @Override
            public void onCancel(String title) {

            }
        });

    }


}