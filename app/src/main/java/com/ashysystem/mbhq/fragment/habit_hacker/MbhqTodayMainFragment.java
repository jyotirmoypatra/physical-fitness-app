package com.ashysystem.mbhq.fragment.habit_hacker;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.mbhq_today.TodayViewPagerAdapter;
import com.ashysystem.mbhq.fragment.MyAchievementsFragment;
import com.ashysystem.mbhq.fragment.QuestionariesFragment;
import com.ashysystem.mbhq.fragment.course.CourseFragment;
import com.ashysystem.mbhq.fragment.live_chat.LiveChatFragment;
import com.ashysystem.mbhq.model.GetGratitudeListModelInner;
import com.ashysystem.mbhq.model.TodayPage.GetAppHomePageValuesResponseModel;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;
import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGratitude;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGrowth;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForTodayPage;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.GrowthViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.TodayPageViewModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.CustomViewPager;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MbhqTodayMainFragment extends Fragment {

    CustomViewPager viewPagerToday;
    CirclePageIndicator indicator;
    TextView txtDate;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    List<GetGratitudeListModelInner> lstToday, lstShowAll;
    List<MyAchievementsListInnerModel> lstTodayAchieve, lstShowAllAchieve;
    SimpleDateFormat simpleDateFormatReq = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat strDtaeFormat = new SimpleDateFormat("yyyy-MM-dd");

    String SELECTED_DATE = "";

    private ViewModelFactoryForTodayPage modelFactoryForTodayPage;
    private TodayPageViewModel mViewModel;
    private ViewModelFactoryForGratitude factoryForGratitude;
    private GratitudeViewModel gratitudeViewModel;
    private ViewModelFactoryForGrowth factoryForGrowth;
    private GrowthViewModel growthViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    View globalView;

    Integer insideSetUpViewPager = 0;

    GetAppHomePageValuesResponseModel globaValuesResponseModel;

    SharedPreference sharedPreference;

    RelativeLayout rlWhiteView;
    /* String accesstype="3";
     String habit_access="true";
     String eq_access="false";
     String medi_access="true";
     String forum_access="true";
     String Live_access="true";
     String Test_acess="true";
     String Course_access="true";*/
    String accesstype="";
    String habit_access="";
    String eq_access="";
    String medi_access="";
    String forum_access="";
    String Live_access="";
    String Test_acess="";
    String Course_access="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        globalView = inflater.inflate(R.layout.fragment_todaymain_mbhq, null);
        sharedPreference = new SharedPreference(getActivity());

       /* sharedPreference.write("accesstype", "", String.valueOf(accesstype));
        sharedPreference.write("HabitAccess", "", String.valueOf(HabitAccess));
        sharedPreference.write("EqJournalAccess", "", String.valueOf(EqJournalAccess));
        sharedPreference.write("MeditationAccess", "", String.valueOf(MeditationAccess));
        sharedPreference.write("ForumAccess", "", String.valueOf(ForumAccess));
        sharedPreference.write("LiveChatAccess", "", String.valueOf(LiveChatAccess));
        sharedPreference.write("TestsAccess", "", String.valueOf(TestsAccess));
        sharedPreference.write("CourseAccess", "", String.valueOf(CourseAccess));*/

        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
        Course_access=sharedPreference.read("CourseAccess","");
        Log.i("printtttttttttttttttttttttttttt",Course_access);
        Log.i("printtttttttttttttttttttttttttt",medi_access);
        Log.i("printtttttttttttttttttttttttttt",accesstype);
        Log.i("printtttttttttttttttttttttttttt",habit_access);
        Log.i("printtttttttttttttttttttttttttt",forum_access);
        Log.i("printtttttttttttttttttttttttttt",Live_access);
        Log.i("printtttttttttttttttttttttttttt",Test_acess);
        ((MainActivity) getActivity()).selectTodayTabColor();
       // funToolBar();



        viewPagerToday = globalView.findViewById(R.id.viewPagerToday);
        indicator = globalView.findViewById(R.id.indicator);
        txtDate = globalView.findViewById(R.id.txtDate);
        rlWhiteView = globalView.findViewById(R.id.rlWhiteView);

        lstToday = new ArrayList<>();
        lstShowAll = new ArrayList<>();

        lstTodayAchieve = new ArrayList<>();
        lstShowAllAchieve = new ArrayList<>();

        clearCacheTodayPageFragments();

        if (getArguments() != null) {
            if (getArguments().containsKey("SELECTED_DATE")) {
                SELECTED_DATE = getArguments().getString("SELECTED_DATE");
                Util.strTodayPageSelectedDate = SELECTED_DATE;
            }
        }


        if (!SELECTED_DATE.equals("")) {
            txtDate.setText(SELECTED_DATE);

            Calendar calendar = Calendar.getInstance();
            Date dtToday = calendar.getTime();
            dtToday.setHours(0);
            dtToday.setMinutes(0);
            dtToday.setSeconds(0);

            if (SELECTED_DATE.equals(strDtaeFormat.format(dtToday))) {
                txtDate.setText("TODAY");
            }

        } else {
            Calendar calendar = Calendar.getInstance();
            Date dtToday = calendar.getTime();
            dtToday.setHours(0);
            dtToday.setMinutes(0);
            dtToday.setSeconds(0);

            SELECTED_DATE = strDtaeFormat.format(dtToday);
            Util.strTodayPageSelectedDate = SELECTED_DATE;
            txtDate.setText("TODAY");
        }

        modelFactoryForTodayPage = Injection.provideViewModelFactoryTodayPage(getActivity());
       // mViewModel = ViewModelProviders.of(this, modelFactoryForTodayPage).get(TodayPageViewModel.class);
        mViewModel = new ViewModelProvider(this, modelFactoryForTodayPage).get(TodayPageViewModel.class);
        factoryForGratitude = Injection.provideViewModelFactoryGratitude(getActivity());
       // gratitudeViewModel = ViewModelProviders.of(this, factoryForGratitude).get(GratitudeViewModel.class);
        gratitudeViewModel = new ViewModelProvider(this, factoryForGratitude).get(GratitudeViewModel.class);
        factoryForGrowth = Injection.provideViewModelFactoryGrowth(getActivity());
        //growthViewModel = ViewModelProviders.of(this, factoryForGrowth).get(GrowthViewModel.class);
        growthViewModel = new ViewModelProvider(this, factoryForGrowth).get(GrowthViewModel.class);

        txtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(Util.isReloadTodayMainPage) {
            Log.i("unique","00");
            Util.isReloadTodayMainPage = false;
            //getGrowthHomapageApiCall();
            sendToGratitudeOrGrowthPage();
        } else {
            Log.i("unique","01");
            //getDataFromDatabase();
            sendToGratitudeOrGrowthPage();
        }

        return globalView;
    }

    private void sendToGratitudeOrGrowthPage() {
        Log.i("unique1111111111111111111111","0");
        if(sharedPreference.read("PROGRAM_PURCHASE_ONLY","").equals("TRUE"))
        {
            sharedPreference.write("learn_nav_pos","",0+"");
            ((MainActivity)getActivity()).loadFragment(new CourseFragment(),"Course",null);


        }else
        {
            if(Util.checkConnection(getActivity()))
            {
                if(sharedPreference.read("ONETIME_TODAY_API_CALL","").equals(""))
                {
                    Log.i("unique1111111111111111111111","1");
                    getGrowthHomapageApiCallNew();
                }else
                {
                    mDisposable.add(gratitudeViewModel.getAllGratitudeByDate(SELECTED_DATE)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(gratitudeEntities -> {
                                if (gratitudeEntities != null && gratitudeEntities.size() > 0) {
                                    Log.i("unique1111111111111111111111","2");
                                    sharedPreference.write("appreciate_nav", "", "gratitude");
                                    sharedPreference.write("appreciate_nav_pos", "", 0 + "");
                                    if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                                    }else{
                                        // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                        if("3".equalsIgnoreCase(accesstype)){
                                            if("true".equalsIgnoreCase(habit_access)){
                                                ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);

                                            }else{
                                                if("true".equalsIgnoreCase(eq_access)){
                                                    ((MainActivity) getActivity()).refershGamePoint(getActivity());
                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                                    Util.isReloadTodayMainPage = true;
                                                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                    ((MainActivity) getActivity()).fungratitudeicon();
                                                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                                                }else if("true".equalsIgnoreCase(medi_access)){
                                                    Util.refresh_meditation="yes";
                                                    Util.refresh_gratitude="yes";
                                                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                    ((MainActivity) getActivity()).funMeditation();
                                                    Util.refresh_gratitude="yes";
                                                }else if("true".equalsIgnoreCase(Course_access)){

                                                    ((MainActivity) getActivity()).funDrawer1();
                                                    Util.refresh_gratitude="yes";
                                                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                    ((MainActivity) getActivity()).funCourse();
                                                }else if("true".equalsIgnoreCase(Live_access)){
                                                    ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                                                }else if("true".equalsIgnoreCase(Test_acess)){
                                                    ((MainActivity) getActivity()).funDrawer1();

                                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                                                    ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);
                                                }else if("true".equalsIgnoreCase(forum_access)){
                                                    Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                    startActivity(intent);
                                                }

                                            }
                                        }else{

                                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);


                                        }
                                    }
                                } else {
                                    Log.i("unique1111111111111111111111","3");
                                    mDisposable.add(growthViewModel.getAllGrowthByDate(SELECTED_DATE)
                                            .subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(gratitudeEntities1 -> {
                                                if (gratitudeEntities1 != null && gratitudeEntities1.size() > 0) {
                                                    Log.i("unique1111111111111111111111","4");
                                                    sharedPreference.write("appreciate_nav", "", "journal");
                                                    sharedPreference.write("appreciate_nav_pos", "", 1 + "");
                                                    if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                                                    }else{
                                                        // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                                        if("3".equalsIgnoreCase(accesstype)){
                                                            if("true".equalsIgnoreCase(habit_access)){
                                                                ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);

                                                            }else{
                                                                if("true".equalsIgnoreCase(eq_access)){
                                                                    ((MainActivity) getActivity()).refershGamePoint(getActivity());
                                                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                                                    Util.isReloadTodayMainPage = true;
                                                                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                                    ((MainActivity) getActivity()).fungratitudeicon();
                                                                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                                                                }else if("true".equalsIgnoreCase(medi_access)){
                                                                    Util.refresh_meditation="yes";
                                                                    Util.refresh_gratitude="yes";
                                                                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                                    ((MainActivity) getActivity()).funMeditation();
                                                                    Util.refresh_gratitude="yes";
                                                                }else if("true".equalsIgnoreCase(Course_access)){

                                                                    ((MainActivity) getActivity()).funDrawer1();
                                                                    Util.refresh_gratitude="yes";
                                                                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                                    ((MainActivity) getActivity()).funCourse();
                                                                }else if("true".equalsIgnoreCase(Live_access)){
                                                                    ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                                                                }else if("true".equalsIgnoreCase(Test_acess)){
                                                                    ((MainActivity) getActivity()).funDrawer1();

                                                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                                                                    ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);
                                                                }else if("true".equalsIgnoreCase(forum_access)){
                                                                    Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                                                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                                    startActivity(intent);
                                                                }

                                                            }
                                                        }else{

                                                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);


                                                        }
                                                    }
                                                } else {
                                                    Log.i("unique1111111111111111111111","5");

                                                    if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                                                    }else{
                                                        Log.i("unique","6");
                                                        Log.e("SIZEEEE", ">>>>>>>>>>>5");
                                                        sharedPreference.write("I_M_GRATEFUL", "", "");
                                                        sharedPreference.write("I_M_GRATEFUL_ACHIEVE", "", "");
                                                        setupViewPager(viewPagerToday);
                                                        rlWhiteView.setVisibility(View.GONE);
                                                    }


                                                }
                                            }, throwable -> {

                                            }));
                                }
                            }, throwable -> {

                            }));
                }
            }else
            {
                Log.i("unique1111111111111111111111","6");

                if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                }else{

                    setupViewPager(viewPagerToday);
                    rlWhiteView.setVisibility(View.GONE);
                }

            }
        }
    }



    private void setupViewPager(ViewPager viewPager) {

        if("3".equalsIgnoreCase(accesstype)){
            if("true".equalsIgnoreCase(habit_access)){
                try {

                    Log.e("SELECTED_DATEEEEEEEEE",SELECTED_DATE + ">>>>>>>>>>");


                    Log.e("GRATITUDE_VALUE",sharedPreference.read("I_M_GRATEFUL","")+">>>>>>");
                    Log.e("GROWTH_VALUE",sharedPreference.read("I_M_GRATEFUL_ACHIEVE","")+">>>>>>");

                    viewPager.setAdapter(null);


                    TodayViewPagerAdapter adapter = new TodayViewPagerAdapter(getChildFragmentManager());

                    adapter.addFragment(new MbhqTodayTwoFragment(), "TWO");
                    viewPager.setAdapter(adapter);

                    viewPager.setOffscreenPageLimit(0);

                    indicator.setViewPager(viewPager);

                    final float density = getResources().getDisplayMetrics().density;

                    //Set circle indicator radius
                    indicator.setRadius(5 * density);

                    NUM_PAGES = 2;

                    viewPager.setCurrentItem(currentPage++, true);


                    // Pager listener over indicator
                    indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                        @Override
                        public void onPageSelected(int position) {
                            currentPage = position;


                        }

                        @Override
                        public void onPageScrolled(int pos, float arg6E9971, int arg2) {

                        }


                        @Override
                        public void onPageScrollStateChanged(int pos) {


                        }
                    });
                    if(getArguments()!=null && getArguments().containsKey("LOAD_SECOND_PAGE"))
                    {
                        viewPagerToday.setCurrentItem(1);
                    }else {
                        viewPagerToday.setCurrentItem(0);
                    }

                    insideSetUpViewPager++;

                    if(insideSetUpViewPager > 6)
                    {
                        mDisposable.clear();
                        insideSetUpViewPager = 0;
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else{

                if("true".equalsIgnoreCase(eq_access)){
                    ((MainActivity) getActivity()).refershGamePoint(getActivity());
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                    Util.isReloadTodayMainPage = true;
                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).fungratitudeicon();
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                }else if("true".equalsIgnoreCase(medi_access)){
                    Util.refresh_meditation="yes";
                    Util.refresh_gratitude="yes";
                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).funMeditation();
                    Util.refresh_gratitude="yes";
                }else if("true".equalsIgnoreCase(Course_access)){

                    ((MainActivity) getActivity()).funDrawer1();
                    Util.refresh_gratitude="yes";
                    ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                    ((MainActivity) getActivity()).funCourse_();
                }else if("true".equalsIgnoreCase(Live_access)){
                    ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                }else if("true".equalsIgnoreCase(Test_acess)){
                    ((MainActivity) getActivity()).funDrawer1();

                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                    ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);
                }else if("true".equalsIgnoreCase(forum_access)){
                    Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            }
        }else{
            try {

                Log.e("SELECTED_DATEEEEEEEEE",SELECTED_DATE + ">>>>>>>>>>");


                Log.e("GRATITUDE_VALUE",sharedPreference.read("I_M_GRATEFUL","")+">>>>>>");
                Log.e("GROWTH_VALUE",sharedPreference.read("I_M_GRATEFUL_ACHIEVE","")+">>>>>>");

                viewPager.setAdapter(null);


                TodayViewPagerAdapter adapter = new TodayViewPagerAdapter(getChildFragmentManager());

                adapter.addFragment(new MbhqTodayTwoFragment(), "TWO");
                viewPager.setAdapter(adapter);

                viewPager.setOffscreenPageLimit(0);

                indicator.setViewPager(viewPager);

                final float density = getResources().getDisplayMetrics().density;

                //Set circle indicator radius
                indicator.setRadius(5 * density);

                NUM_PAGES = 2;

                viewPager.setCurrentItem(currentPage++, true);


                // Pager listener over indicator
                indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        currentPage = position;


                    }

                    @Override
                    public void onPageScrolled(int pos, float arg6E9971, int arg2) {

                    }


                    @Override
                    public void onPageScrollStateChanged(int pos) {


                    }
                });
                if(getArguments()!=null && getArguments().containsKey("LOAD_SECOND_PAGE"))
                {
                    viewPagerToday.setCurrentItem(1);
                }else {
                    viewPagerToday.setCurrentItem(0);
                }

                insideSetUpViewPager++;

                if(insideSetUpViewPager > 6)
                {
                    mDisposable.clear();
                    insideSetUpViewPager = 0;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }


/*
    private void funToolBar() {
     ((MainActivity) getActivity()).funDrawer();
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imgLogo = (ImageView) toolbar.findViewById(R.id.imgLogo);
        ImageView imgLeftBack = (ImageView) toolbar.findViewById(R.id.imgLeftBack);
        ImageView imgRightBack = (ImageView) toolbar.findViewById(R.id.imgRightBack);

        ImageView imgFilter = (ImageView) toolbar.findViewById(R.id.imgFilter);
        ImageView imgCircleBack = (ImageView) toolbar.findViewById(R.id.imgCircleBack);
        ImageView imgHelp = (ImageView) toolbar.findViewById(R.id.imgHelp);
        ImageView imgCalender = (ImageView) toolbar.findViewById(R.id.imgCalender);
        FrameLayout frameNotification = (FrameLayout) toolbar.findViewById(R.id.frameNotification);
        TextView txtPageHeader = (TextView) toolbar.findViewById(R.id.txtPageHeader);
        FrameLayout messageNotification = (FrameLayout) toolbar.findViewById(R.id.messageNotification);
        frameNotification.setVisibility(View.VISIBLE);

        imgRightBack.setClickable(true);
        imgRightBack.setEnabled(true);
        frameNotification.setVisibility(View.GONE);
        imgFilter.setVisibility(View.GONE);
        //imgFilter.setBackgroundResource(R.drawable.filter);
        imgRightBack.setVisibility(View.GONE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) getActivity()).getDrawer().isDrawerOpen(Gravity.END)) {
                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                    intent.putExtra("VIDEO_URL", "https://player.vimeo.com/external/290408257.m3u8?s=eb4b9b3ed9aff17c0a6b8297ad209d7c8d6ed8d6");
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                    intent.putExtra("VIDEO_URL", "https://player.vimeo.com/external/290408368.m3u8?s=b6c812928713281f6cab2e4528d5890e97814c62");
                    getActivity().startActivity(intent);
                }
            }
        });


        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreference.read("compChk", "").equals("false")) {
                    ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                } else {
                    ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);
                }
            }
        });

    }
*/



    private void getGrowthHomapageApiCallNew() {
        Log.i("unique1111111111111111111111","7");
        if (Connection.checkConnection(getActivity())) {

            ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

            sharedPreference.write("ONETIME_TODAY_API_CALL","","TRUE");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            if (SELECTED_DATE.equals("")) {
                String strTodayDate = "";
                Calendar calendar = Calendar.getInstance();
                strTodayDate = strDtaeFormat.format(calendar.getTime());
                hashReq.put("ForDate", strTodayDate);
                SELECTED_DATE = strTodayDate;
                Util.strTodayPageSelectedDate = SELECTED_DATE;
            } else {
                hashReq.put("ForDate", SELECTED_DATE);
            }
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<GetAppHomePageValuesResponseModel> appHomePageValuesResponseModelCall = finisherService.getGrowthHomapage(hashReq);
            appHomePageValuesResponseModelCall.enqueue(new Callback<GetAppHomePageValuesResponseModel>() {
                @Override
                public void onResponse(Call<GetAppHomePageValuesResponseModel> call, Response<GetAppHomePageValuesResponseModel> response) {
                    progressDialog.dismiss();

                    boolean boolGratitudeHasValue = false;
                    boolean boolGrowthHasValue = false;

                    if (response.body() != null && response.body().getSuccessFlag()) {

                        globaValuesResponseModel = response.body();

                        Gson gson = new Gson();
                        if (response.body().getGratitude() != null) {
                            try {
                                sharedPreference.write("I_M_GRATEFUL", "", response.body().getGratitude().getName());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar = Calendar.getInstance();
                                String date = simpleDateFormat.format(calendar.getTime());
                                sharedPreference.write("I_M_GRATEFUL_DATE","",date);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            boolGratitudeHasValue = true;
                            GetGratitudeListModelInner getGratitudeListModelInner = response.body().getGratitude();
                            Calendar calendar = Calendar.getInstance();
                            Gson gson1 = new Gson();
                            String gratitudeModel = gson1.toJson(getGratitudeListModelInner);
                            String timeStamp = String.valueOf(calendar.getTimeInMillis());
                            GratitudeEntity gratitudeEntity = new GratitudeEntity(getGratitudeListModelInner.getId(),SELECTED_DATE,gratitudeModel,timeStamp,true);
                            mDisposable.add(gratitudeViewModel.insertGratitude(gratitudeEntity)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.e("GRATITUDE_SAVE","SUCCESSFULL");
                                    },throwable -> {
                                        Log.e("GRATITUDE_SAVE","NOT_SUCCESSFULL");
                                    }));

                        }
                        if (response.body().getGrowth() != null) {
                            try {
                                sharedPreference.write("I_M_GRATEFUL_ACHIEVE", "", response.body().getGrowth().getAchievement());
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                                Calendar calendar = Calendar.getInstance();
                                String date = simpleDateFormat.format(calendar.getTime());
                                sharedPreference.write("I_M_GRATEFUL_ACHIEVE_DATE","",date);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            boolGrowthHasValue = true;
                            MyAchievementsListInnerModel getGratitudeListModelInner = response.body().getGrowth();
                            Calendar calendar = Calendar.getInstance();
                            Gson gson2 = new Gson();
                            String gratitudeModel = gson2.toJson(getGratitudeListModelInner);
                            String timeStamp = String.valueOf(calendar.getTimeInMillis());
                            GrowthEntity gratitudeEntity = new GrowthEntity(getGratitudeListModelInner.getId(),SELECTED_DATE,gratitudeModel,timeStamp,true);
                            mDisposable.add(growthViewModel.insertGrowth(gratitudeEntity)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.e("GROWTH_SAVE","SUCCESSFULL");
                                    },throwable -> {
                                        Log.e("GROWTH_SAVE","NOT_SUCCESSFULL");
                                    }));

                        }
                        if(boolGratitudeHasValue)
                        {
                            Log.i("unique1111111111111111111111","8");

                            sharedPreference.write("appreciate_nav", "", "gratitude");
                            sharedPreference.write("appreciate_nav_pos", "", 0 + "");

                            if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                            }else{

                                // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                if("3".equalsIgnoreCase(accesstype)){
                                    if("true".equalsIgnoreCase(habit_access)){
                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);

                                    }else{
                                        if("true".equalsIgnoreCase(eq_access)){
                                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
                                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                            Util.isReloadTodayMainPage = true;
                                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                            ((MainActivity) getActivity()).fungratitudeicon();
                                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                                        }else if("true".equalsIgnoreCase(medi_access)){
                                            Util.refresh_meditation="yes";
                                            Util.refresh_gratitude="yes";
                                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                            ((MainActivity) getActivity()).funMeditation();
                                            Util.refresh_gratitude="yes";
                                        }else if("true".equalsIgnoreCase(Course_access)){

                                            ((MainActivity) getActivity()).funDrawer1();
                                            Util.refresh_gratitude="yes";
                                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                            ((MainActivity) getActivity()).funCourse();
                                        }else if("true".equalsIgnoreCase(Live_access)){
                                            ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                                        }else if("true".equalsIgnoreCase(Test_acess)){
                                            ((MainActivity) getActivity()).funDrawer1();

                                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                                            ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);
                                        }else if("true".equalsIgnoreCase(forum_access)){
                                            Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            startActivity(intent);
                                        }

                                    }
                                }else{

                                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);


                                }
                            }
                        }else
                        {
                            Log.i("unique1111111111111111111111","9");

                            if(boolGrowthHasValue)
                            {
                                Log.i("unique1111111111111111111111","10");

                                sharedPreference.write("appreciate_nav", "", "journal");
                                sharedPreference.write("appreciate_nav_pos", "", 1 + "");

                                if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                                }else{

                                    if("3".equalsIgnoreCase(accesstype)){
                                        if("true".equalsIgnoreCase(habit_access)){
                                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);

                                        }else{
                                            if("true".equalsIgnoreCase(eq_access)){
                                                ((MainActivity) getActivity()).refershGamePoint(getActivity());
                                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                                Util.isReloadTodayMainPage = true;
                                                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                ((MainActivity) getActivity()).fungratitudeicon();
                                                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                                            }else if("true".equalsIgnoreCase(medi_access)){
                                                Util.refresh_meditation="yes";
                                                Util.refresh_gratitude="yes";
                                                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                ((MainActivity) getActivity()).funMeditation();
                                                Util.refresh_gratitude="yes";
                                            }else if("true".equalsIgnoreCase(Course_access)){

                                                ((MainActivity) getActivity()).funDrawer1();
                                                Util.refresh_gratitude="yes";
                                                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                                ((MainActivity) getActivity()).funCourse();
                                            }else if("true".equalsIgnoreCase(Live_access)){
                                                ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                                            }else if("true".equalsIgnoreCase(Test_acess)){
                                                ((MainActivity) getActivity()).funDrawer1();
                                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                                                ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);
                                            }else if("true".equalsIgnoreCase(forum_access)){
                                                Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                                startActivity(intent);
                                            }

                                        }
                                    }else{

                                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);


                                    }

                                }


                            }else
                            {
                                Log.i("unique1111111111111111111111","11");

                                if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                                }else{
                                    Util.opengratitudeforfirstuser="";
                                    sharedPreference.write("I_M_GRATEFUL", "", "");
                                    sharedPreference.write("I_M_GRATEFUL_ACHIEVE", "", "");
                                    rlWhiteView.setVisibility(View.GONE);
                                    setupViewPager(viewPagerToday);

                                }





                            }
                        }
                    }

                }

                @Override
                public void onFailure(Call<GetAppHomePageValuesResponseModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Log.i("unique1111111111111111111111","12");

            if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
                Util.opengratitudeforfirstuser="";
                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
            }else{
                Util.opengratitudeforfirstuser="";
                setupViewPager(viewPagerToday);

            }


        }

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

    @Override
    public void onStop() {
        super.onStop();

        // clear all the subscriptions
        mDisposable.clear();
    }

    void clearCacheTodayPageFragments() {
        try {
            for (int p = 0; p < ((MainActivity) getActivity()).arrFragment.size(); p++) {
                if (((MainActivity) getActivity()).arrFragment.get(p).getClass().getSimpleName().contains(MbhqTodayMainFragment.class.getSimpleName()) || ((MainActivity) getActivity()).arrFragment.get(p).getClass().getSimpleName().contains(MbhqTodayTwoFragment.class.getSimpleName()) /*|| ((MainActivity) getActivity()).arrFragment.get(p).getClass().getSimpleName().contains(MbhqTodayTwoFragment.class.getSimpleName())*/) {
                    ((MainActivity) getActivity()).arrFragment.remove(p);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}