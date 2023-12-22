package com.ashysystem.mbhq.fragment.course;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.adapter.AvailableCourseAdapter;
import com.ashysystem.mbhq.fragment.LearnFragment;
import com.ashysystem.mbhq.fragment.MyAchievementsFragment;
import com.ashysystem.mbhq.fragment.TrainFragment;
import com.ashysystem.mbhq.fragment.WelcomeScrenCheckLIstFragment;
import com.ashysystem.mbhq.fragment.meditation.MeditationFragment;
import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.model.CourseDataViewModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android-arindam on 21/2/17.
 */

public class CourseFragment extends Fragment {

/*    String accesstype="3";
    String habit_access="true";
    String eq_access="true";
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

    private RecyclerView rvEnroll, rvMyPrograms, rvPaidPrograms, rvLivePrograms, rvMemberPrograms, rvMasterclassPrograms, rvPaidMasterclassPrograms, rvPodcastPrograms;
    private SharedPreference sharedPreference;
    private String origin = "";
    private String PLAY_EPISODE_ONE = "";
    String FROMSETPROGRAM = "";
    String FROMTODAYPAGE = "";
    View vi = null;
    int filterSelectedvalue = 0;
    ImageView imgFilter;

    NestedScrollView nsv;

    private String firstLoginTime = ""; /////////
    private String currentTime = ""; /////////
    private String courseFirstTime = "";


    /*List<AvailableCourseModel.Course> courseViewModel.lstTotalDataM = new ArrayList<>();

    /////////////////////////////////////////
    List<AvailableCourseModel.Course> courseViewModel.myInProgressPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.myPausedPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.myCompletedPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.myAllPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.myVirginPrograms = new ArrayList<>();

    /////////////////////////////////////////
    List<AvailableCourseModel.Course> courseViewModel.inProgressMemberPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.pausedMemberPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.completedMemberPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.allMemberPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.virginMemberPrograms = new ArrayList<>();

    ///////////////////////////////////////////////
    List<AvailableCourseModel.Course> courseViewModel.allLivePrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.pausedLivePrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.inProgressLivePrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.completedLivePrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.virginLivePrograms = new ArrayList<>();

    ///////////////////////////////////////////////////
    List<AvailableCourseModel.Course> courseViewModel.allPaidPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.pausedPaidPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.inProgressPaidPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.completedPaidPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.virginPaidPrograms = new ArrayList<>();

    ///////////////////////////////////////////////////
    List<AvailableCourseModel.Course> courseViewModel.allMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.pausedMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.inProgressMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.completedMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.virginMaterclassPrograms = new ArrayList<>();

    ///////////////////////////////////////////////////
    List<AvailableCourseModel.Course> courseViewModel.allPaidMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.pausedPaidMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.inProgressPaidMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.completedPaidMaterclassPrograms = new ArrayList<>();
    List<AvailableCourseModel.Course> courseViewModel.virginPaidMaterclassPrograms = new ArrayList<>();

    //////////////////////////////////////////

    List<AvailableCourseModel.Course> courseViewModel.allPodcastPrograms = new ArrayList<AvailableCourseModel.Course>(){{
        add(new AvailableCourseModel.Course(){{
            setCourseId(-1);
            setCourseName("MindBodyHQ");
            setCourseType("");
            setStatus(0);
            setSubscriptionType(0);
            setAuthorName("Levi Walz");
            setImageURL2("https://squad-live.s3-ap-southeast-2.amazonaws.com/mbHQ+images/MindbodyPODCAST+for+App.png");
            setWeekNumber(-1);
        }});
    }};

    ArrayList<String> courseViewModel.allTags = new ArrayList<String>();*/

    private final String TAG = this.getClass().getSimpleName();

    private static final String FILTER_TYPE_MY_PROGRAMS = "FILTER_TYPE_MY_PROGRAMS";
    private static final String FILTER_TYPE_PAID_PROGRAMS = "FILTER_TYPE_PAID_PROGRAMS";
    private static final String FILTER_TYPE_LIVE_PROGRAMS = "FILTER_TYPE_LIVE_PROGRAMS";
    private static final String FILTER_TYPE_MASTERCLASS = "FILTER_TYPE_MASTERCLASS";
    private static final String FILTER_TYPE_PAID_MASTERCLASS = "FILTER_TYPE_PAID_MASTERCLASS";

    private static final String FILTER_STATUS_ALL_STATUSES = "FILTER_STATUS_ALL_STATUSES";
    private static final String FILTER_STATUS_IN_PROGRESS = "FILTER_STATUS_IN_PROGRESS";
    private static final String FILTER_STATUS_PAUSED = "FILTER_STATUS_PAUSED";
    private static final String FILTER_STATUS_COMPLETED = "FILTER_STATUS_COMPLETED";

    private static final String FILTER_KEY_TYPE = "TYPE";
    private static final String FILTER_KEY_STATUS = "STATUS";
    private static final String FILTER_KEY_TAGS = "TAGS";


    HashMap<String, ArrayList<String>> filters = new HashMap<String, ArrayList<String>>() {{
        put(FILTER_KEY_TYPE, new ArrayList<String>() {{
        }});
        put(FILTER_KEY_STATUS, new ArrayList<String>() {{
        }});
        put(FILTER_KEY_TAGS, new ArrayList<>());
    }};



    String searchFilterText = "";


    private EditText edtSearch;
    TextView seeAllLivePrograms, seeAllPaidPrograms, txtMediHead;
    LinearLayout llBreath, llMorning, llPowerNap;
    SwipeRefreshLayout swipeLayout;

    private CourseDataViewModel courseViewModel;

    ViewGroup myProgramsContainer, liveProgramsContainer, paidProgramsContainer, memberOnlyProgramsContainer, masterclassProgramsContainer, paidMasterclassProgramsContainer, podcastProgramsContainer;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {




        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");

    /*   if("3".equalsIgnoreCase(accesstype)){
           if("false".equalsIgnoreCase(Course_access)) {
               return null;
           }else{
               if (vi == null) {
                   vi = inflater.inflate(R.layout.fragment_courses, container, false);
                   sharedPreference = new SharedPreference(getActivity());
                   sharedPreference.clear("med");
                   sharedPreference.clear("medT");
                   LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                   llTabView.setVisibility(View.VISIBLE);
                   initView(vi);
                   if (getArguments() != null) {
                       if (getArguments().containsKey("origin")) {
                           origin = getArguments().getString("origin");
                       }
                       if (getArguments().containsKey("PLAY_EPISODE_ONE")) {
                           PLAY_EPISODE_ONE = getArguments().getString("PLAY_EPISODE_ONE");
                       }
                       if (getArguments().containsKey("FROMSETPROGRAM")) {
                           FROMSETPROGRAM = getArguments().getString("FROMSETPROGRAM");
                       }

                       if (getArguments().containsKey("FROMTODAYPAGE")) {
                           FROMTODAYPAGE = getArguments().getString("FROMTODAYPAGE");
                       }

                   }
                   swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                       @Override
                       public void onRefresh() {
                           //webCommunity.loadUrl(new SharedPreference(getActivity()).read("COMMUNITY_URL",""));

                           ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());


                           getAvailableCourse();


                           swipeLayout.setRefreshing(false);
                       }
                   });
                   funToolBar();

          *//*  if (!sharedPreference.readBoolean("HAS_PROGRAMS_DIALOG_SHOWN", "")) {
                openProgramsDialog();
                sharedPreference.writeBoolean("HAS_PROGRAMS_DIALOG_SHOWN", "", true);
            }*//*
                   try{
                       courseFirstTime = sharedPreference.read("courseFirstTime","");
                       firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
                       Log.e("FFLLLIIII", "onCreateView: " + firstLoginTime );

                       SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                       Calendar calendar = Calendar.getInstance();
                       currentTime = simpleDateFormat.format(calendar.getTime());
                       Log.e("CURRRTIIII", "onCreateView: " + currentTime );

                       Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
                       Date dateCurrentTime = simpleDateFormat.parse(currentTime);

                       if (dateCurrentTime != null) {
                           if(dateCurrentTime.after(dateFirstLoginDt) && "true".equals(courseFirstTime) ){
                               Log.e("CHHHHTIMMMMMM", "onCreateView: " + "TRUE" );
                               sharedPreference.write("courseFirstTime", "", "false");
                               // show popup here
                               openProgramsDialog();

                           }else {
                               Log.e("CHHHHTIMMMMMM", "onCreateView: " + "FALSE" );
                           }
                       }

                   }catch (Exception e){
                       e.printStackTrace();
                   }
                   return vi;
               } else {
                   funToolBar();
                   if (Util.boolBackGroundServiceRunningProgram && Util.bundleProgramDetailsForBackground != null) {
                       Log.i(TAG, "from create view");
                       //((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                       ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", Util.bundleProgramDetailsForBackground);
                   }
                   return vi;
               }
           }
       }else{*/
           if (vi == null) {
               vi = inflater.inflate(R.layout.fragment_courses, container, false);
               sharedPreference = new SharedPreference(getActivity());
               sharedPreference.clear("med");
               sharedPreference.clear("medT");
               LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
               llTabView.setVisibility(View.VISIBLE);
               initView(vi);
               if (getArguments() != null) {
                   if (getArguments().containsKey("origin")) {
                       origin = getArguments().getString("origin");
                   }
                   if (getArguments().containsKey("PLAY_EPISODE_ONE")) {
                       PLAY_EPISODE_ONE = getArguments().getString("PLAY_EPISODE_ONE");
                   }
                   if (getArguments().containsKey("FROMSETPROGRAM")) {
                       FROMSETPROGRAM = getArguments().getString("FROMSETPROGRAM");
                   }

                   if (getArguments().containsKey("FROMTODAYPAGE")) {
                       FROMTODAYPAGE = getArguments().getString("FROMTODAYPAGE");
                   }

               }
               swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                   @Override
                   public void onRefresh() {
                       //webCommunity.loadUrl(new SharedPreference(getActivity()).read("COMMUNITY_URL",""));

                       ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());


                       getAvailableCourse();


                       swipeLayout.setRefreshing(false);
                   }
               });
               funToolBar();

          /*  if (!sharedPreference.readBoolean("HAS_PROGRAMS_DIALOG_SHOWN", "")) {
                openProgramsDialog();
                sharedPreference.writeBoolean("HAS_PROGRAMS_DIALOG_SHOWN", "", true);
            }*/
               try{
                   courseFirstTime = sharedPreference.read("courseFirstTime","");
                   firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
                   Log.e("FFLLLIIII", "onCreateView: " + firstLoginTime );

                   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                   Calendar calendar = Calendar.getInstance();
                   currentTime = simpleDateFormat.format(calendar.getTime());
                   Log.e("CURRRTIIII", "onCreateView: " + currentTime );

                   Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
                   Date dateCurrentTime = simpleDateFormat.parse(currentTime);

                   if (dateCurrentTime != null) {
                       if(dateCurrentTime.after(dateFirstLoginDt) && "true".equals(courseFirstTime) ){
                           Log.e("CHHHHTIMMMMMM", "onCreateView: " + "TRUE" );
                           sharedPreference.write("courseFirstTime", "", "false");
                           // show popup here
                           openProgramsDialog();

                       }else {
                           Log.e("CHHHHTIMMMMMM", "onCreateView: " + "FALSE" );
                       }
                   }

               }catch (Exception e){
                   e.printStackTrace();
               }
               return vi;
           } else {
               funToolBar();
               if (Util.boolBackGroundServiceRunningProgram && Util.bundleProgramDetailsForBackground != null) {
                   Log.i(TAG, "from create view");
                   //((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                   ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", Util.bundleProgramDetailsForBackground);
               }
               return vi;
           }
      // }




    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(Course_access)) {

    }else{
        courseViewModel = ViewModelProviders.of(requireActivity()).get(CourseDataViewModel.class);

        boolean shouldCourseRenew = sharedPreference.readBoolean("COURSE_LIST_SHOULD_RENEW", "");

        if (courseViewModel.lstTotalDataM.isEmpty() || courseViewModel.allTags.isEmpty() || shouldCourseRenew) {

            sharedPreference.writeBoolean("COURSE_LIST_SHOULD_RENEW", "", false);

            getAvailableCourse();

        } else {

            loadAllAdapters();

        }
    }
}else{*/
    courseViewModel = new ViewModelProvider(requireActivity()).get(CourseDataViewModel.class);

    boolean shouldCourseRenew = sharedPreference.readBoolean("COURSE_LIST_SHOULD_RENEW", "");

    if (courseViewModel.lstTotalDataM.isEmpty() || courseViewModel.allTags.isEmpty() || shouldCourseRenew) {

        sharedPreference.writeBoolean("COURSE_LIST_SHOULD_RENEW", "", false);

        getAvailableCourse();

    } else {

        loadAllAdapters();

    }
//}




    }

    private void openProgramsDialog() {

        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_programs);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void initView(View vi) {
        swipeLayout = vi.findViewById(R.id.swipeLayout);
        llBreath = vi.findViewById(R.id.llBreath);
        llMorning = vi.findViewById(R.id.llMorning);
        llPowerNap = vi.findViewById(R.id.llPowerNap);

        txtMediHead = vi.findViewById(R.id.txtMediHead);
        imgFilter = (ImageView) vi.findViewById(R.id.imgFilter);
        edtSearch = (EditText) vi.findViewById(R.id.edtSearch);

        rvMyPrograms = (RecyclerView) vi.findViewById(R.id.rvMyPrograms);
        rvPaidPrograms = (RecyclerView) vi.findViewById(R.id.rvPaidPrograms);
        rvLivePrograms = (RecyclerView) vi.findViewById(R.id.rvLivePrograms);
        rvMemberPrograms = (RecyclerView) vi.findViewById(R.id.rvMemberPrograms);
        rvMasterclassPrograms = (RecyclerView) vi.findViewById(R.id.rvMasterclassPrograms);
        rvPaidMasterclassPrograms = (RecyclerView) vi.findViewById(R.id.rvPaidMasterclassPrograms);
        rvPodcastPrograms = (RecyclerView) vi.findViewById(R.id.rvPodcastPrograms);

        myProgramsContainer = vi.findViewById(R.id.myProgramsContainer);
        liveProgramsContainer = vi.findViewById(R.id.liveProgramsContainer);
        paidProgramsContainer = vi.findViewById(R.id.paidProgramsContainer);
        memberOnlyProgramsContainer = vi.findViewById(R.id.memberOnlyProgramsContainer);
        masterclassProgramsContainer = vi.findViewById(R.id.masterclassProgramsContainer);
        paidMasterclassProgramsContainer = vi.findViewById(R.id.paidMasterclassProgramsContainer);
        podcastProgramsContainer = vi.findViewById(R.id.podcastProgramsContainer);

        rvEnroll = (RecyclerView) vi.findViewById(R.id.rvEnroll);

        rvMyPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPaidPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLivePrograms.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvEnroll.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMemberPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvMasterclassPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPaidMasterclassPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvPodcastPrograms.setLayoutManager(new LinearLayoutManager(getActivity()));

        seeAllLivePrograms = vi.findViewById(R.id.seeAllLivePrograms);

        seeAllPaidPrograms = vi.findViewById(R.id.seeAllPaidPrograms);


        ViewCompat.setNestedScrollingEnabled(rvMyPrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvPaidPrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvLivePrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvMemberPrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvMasterclassPrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvPaidMasterclassPrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvPodcastPrograms, false);
        ViewCompat.setNestedScrollingEnabled(rvEnroll, false);

        rvEnroll.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.BLACK)
                        .sizeResId(R.dimen.divider)
                        .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                        .build());

        if (Util.boolBackGroundServiceRunningProgram && Util.bundleProgramDetailsForBackground != null && getArguments() != null && !getArguments().containsKey("BACKBUTTONCLICKED")) {
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
            Log.i(TAG, "from init view");
            ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", Util.bundleProgramDetailsForBackground);
        }/* else {
            getAvailableCourse();
        }*/
        //getEnrollCourse();
        llBreath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SharedPreference(getActivity()).write("appreciate_nav", "", "meditation");
                new SharedPreference(getActivity()).write("appreciate_nav_pos", "", 2 + "");

                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                Bundle bundle = new Bundle();
                bundle.putString("mode", "1");
                MeditationFragment meditationFragment = new MeditationFragment();
                meditationFragment.setArguments(bundle);
                ((MainActivity) getActivity()).clearCacheForParticularFragment(meditationFragment);
                ((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", bundle);
            }
        });
        llMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SharedPreference(getActivity()).write("appreciate_nav", "", "meditation");
                new SharedPreference(getActivity()).write("appreciate_nav_pos", "", 2 + "");

                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                Bundle bundle = new Bundle();
                bundle.putString("mode", "2");
                MeditationFragment meditationFragment = new MeditationFragment();
                meditationFragment.setArguments(bundle);
                ((MainActivity) getActivity()).clearCacheForParticularFragment(meditationFragment);
                ((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", bundle);
            }
        });
        llPowerNap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SharedPreference(getActivity()).write("appreciate_nav", "", "meditation");
                new SharedPreference(getActivity()).write("appreciate_nav_pos", "", 2 + "");

                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                Bundle bundle = new Bundle();
                bundle.putString("mode", "3");
                MeditationFragment meditationFragment = new MeditationFragment();
                meditationFragment.setArguments(bundle);
                ((MainActivity) getActivity()).clearCacheForParticularFragment(meditationFragment);
                ((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", bundle);
            }
        });
        imgFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForFilter();
            }
        });


        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!edtSearch.getText().toString().equals("")) {
                        performSearch(edtSearch.getText().toString());
                        Util.hideKeyboard(getActivity());
                    }
                    return true;
                }
                return false;
            }
        });

        //openRestrictionDialog();

    }

    private void performSearch(String s) {
        List<AvailableCourseModel.Course> lstSearchDataP = new ArrayList<>();
        List<AvailableCourseModel.Course> lstSearchDataM = new ArrayList<>();

        List<AvailableCourseModel.Course> lstSearchDataLive = new ArrayList<>();
        List<AvailableCourseModel.Course> lstSearchDataPaid = new ArrayList<>();
        List<AvailableCourseModel.Course> lstSearchDataMaster = new ArrayList<>();

        Log.e("total size---", courseViewModel.myAllPrograms.size() + "???" + s);
        for (int x = 0; x < courseViewModel.myAllPrograms.size(); x++) {
            Log.e("loop size---", x + "???" + courseViewModel.myAllPrograms.get(x).getCourseName());

            if (courseViewModel.myAllPrograms.get(x).getCourseName().toLowerCase().contains(s.toLowerCase())) {
                lstSearchDataP.add(courseViewModel.myAllPrograms.get(x));
            }
        }

        /*for (int x = 0; x < courseViewModel.lstTotalDataM.size(); x++) {
            Log.e("loop size---", x + "???" + courseViewModel.lstTotalDataM.get(x).getEventName());

            if (courseViewModel.lstTotalDataM.get(x).getEventName().toLowerCase().contains(s.toLowerCase())) {
                lstSearchDataM.add(courseViewModel.myAllPrograms.get(x));
            }
        }*/
        Log.e("search size---", lstSearchDataM.size() + "??");
        for (int x = 0; x < courseViewModel.allLivePrograms.size(); x++) {

            if (courseViewModel.allLivePrograms.get(x).getCourseName().toLowerCase().contains(s.toLowerCase())) {
                lstSearchDataLive.add(courseViewModel.allLivePrograms.get(x));
            }
        }

        for (int x = 0; x < courseViewModel.allPaidPrograms.size(); x++) {

            if (courseViewModel.allPaidPrograms.get(x).getCourseName().toLowerCase().contains(s.toLowerCase())) {
                lstSearchDataPaid.add(courseViewModel.allPaidPrograms.get(x));
            }
        }

        for (int x = 0; x < courseViewModel.allMaterclassPrograms.size(); x++) {

            if (courseViewModel.allMaterclassPrograms.get(x).getCourseName().toLowerCase().contains(s.toLowerCase())) {
                lstSearchDataMaster.add(courseViewModel.allMaterclassPrograms.get(x));
            }
        }

        if (lstSearchDataP.size() > 0 || lstSearchDataLive.size() > 0 || lstSearchDataPaid.size() > 0 || lstSearchDataMaster.size() > 0) {
            rvMyPrograms.setAdapter(null);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstSearchDataP, AvailableCourseAdapter.ProgramType.MY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, lstSearchDataP.size());
            rvMyPrograms.setAdapter(availableCourseAdapter);

            loadLiveProgramsAdapter(lstSearchDataLive, lstSearchDataLive.size());
            loadPaidProgramsAdapter(lstSearchDataPaid, lstSearchDataPaid.size());
            loadMasterclassProgramsAdapter(lstSearchDataMaster, lstSearchDataMaster.size());
        } else {
            Util.showToast(getActivity(), "No Program Data Found");
        }

    }

    private void getAvailableCourse() {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<AvailableCourseModel> serverCall = finisherService.getAvailableCourse(hashReq);
            serverCall.enqueue(new Callback<AvailableCourseModel>() {
                @Override
                public void onResponse(Call<AvailableCourseModel> call, Response<AvailableCourseModel> response) {
                    progressDialog.dismiss();

                    Log.e("success", "su");
                    if (response.body() != null) {

                        courseViewModel.lstTotalDataM.clear();
                        courseViewModel.allPodcastPrograms.clear();
                        courseViewModel.myAllPrograms.clear();
                        courseViewModel.myCompletedPrograms.clear();
                        courseViewModel.myInProgressPrograms.clear();
                        courseViewModel.myVirginPrograms.clear();
                        courseViewModel.myPausedPrograms.clear();

                        courseViewModel.allMemberPrograms.clear();
                        courseViewModel.completedMemberPrograms.clear();
                        courseViewModel.inProgressMemberPrograms.clear();
                        courseViewModel.pausedMemberPrograms.clear();
                        courseViewModel.virginMemberPrograms.clear();

                        courseViewModel.allLivePrograms.clear();
                        courseViewModel.inProgressLivePrograms.clear();
                        courseViewModel.pausedLivePrograms.clear();
                        courseViewModel.completedLivePrograms.clear();
                        courseViewModel.virginLivePrograms.clear();

                        courseViewModel.allPaidPrograms.clear();
                        courseViewModel.inProgressPaidPrograms.clear();
                        courseViewModel.pausedPaidPrograms.clear();
                        courseViewModel.completedPaidPrograms.clear();
                        courseViewModel.virginPaidPrograms.clear();

                        courseViewModel.allMaterclassPrograms.clear();
                        courseViewModel.inProgressMaterclassPrograms.clear();
                        courseViewModel.pausedMaterclassPrograms.clear();
                        courseViewModel.completedMaterclassPrograms.clear();
                        courseViewModel.virginMaterclassPrograms.clear();

                        courseViewModel.allPaidMaterclassPrograms.clear();
                        courseViewModel.inProgressPaidMaterclassPrograms.clear();
                        courseViewModel.pausedPaidMaterclassPrograms.clear();
                        courseViewModel.completedPaidMaterclassPrograms.clear();
                        courseViewModel.virginPaidMaterclassPrograms.clear();


                        try {
                            AvailableCourseModel lstData = response.body();

                            List<AvailableCourseModel.Course> courses = lstData.getCourses();

                            sharedPreference.write("AVAILABLE_COURSE_MODEL", "", new Gson().toJson(lstData));

                            courseViewModel.allTags.clear();

                            for (int index = 0; index < courses.size(); index++) {

                                AvailableCourseModel.Course course = courses.get(index);

                                courseViewModel.lstTotalDataM.add(course);

                                List<String> tags = course.getTags();
                                for (int tagIndex = 0; tagIndex < tags.size(); tagIndex++) {
                                    if (!courseViewModel.allTags.contains(tags.get(tagIndex))) {
                                        courseViewModel.allTags.add(tags.get(tagIndex));
                                    }
                                }

                                if (course.getCourseType().equalsIgnoreCase("Podcast")) {
                                    courseViewModel.allPodcastPrograms.add(course);
                                }else{
                                    if (course.getCourseType().equalsIgnoreCase("Masterclass")) {

                                        if (course.getSubscriptionType() == 2 && course.getHasSubscribed() == false) {

                                            courseViewModel.allPaidMaterclassPrograms.add(course);

                                            switch (course.getStatus()) {

                                                case 0: {
                                                    courseViewModel.virginPaidMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                case 1: {
                                                    courseViewModel.inProgressPaidMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                case 2: {
                                                    courseViewModel.pausedPaidMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                case 3: {
                                                    courseViewModel.completedPaidMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                default: {

                                                }
                                            }

                                        } else {

                                            courseViewModel.allMaterclassPrograms.add(course);

                                            switch (course.getStatus()) {

                                                case 0: {
                                                    courseViewModel.virginMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                case 1: {
                                                    courseViewModel.inProgressMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                case 2: {
                                                    courseViewModel.pausedMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                case 3: {
                                                    courseViewModel.completedMaterclassPrograms.add(course);
                                                    break;
                                                }
                                                default: {

                                                }
                                            }
                                        }

                                    } else if ((course.getSubscriptionType() == 0 || course.getSubscriptionType() == 1) && sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equalsIgnoreCase("TRUE")) {

                                        courseViewModel.allMemberPrograms.add(course);

                                        switch (course.getStatus()) {

                                            case 0: {
                                                courseViewModel.virginMemberPrograms.add(course);
                                                break;
                                            }
                                            case 1: {
                                                courseViewModel.inProgressMemberPrograms.add(course);
                                                break;
                                            }
                                            case 2: {
                                                courseViewModel.pausedMemberPrograms.add(course);
                                                break;
                                            }
                                            case 3: {
                                                courseViewModel.completedMemberPrograms.add(course);
                                                break;
                                            }
                                            default: {

                                            }
                                        }

                                    } else if (course.getHasSubscribed() || course.getSubscriptionType() == 0 || course.getSubscriptionType() == 1) {

                                        courseViewModel.myAllPrograms.add(course);

                                        switch (course.getStatus()) {

                                            case 0: {
                                                courseViewModel.myVirginPrograms.add(course);
                                                break;
                                            }
                                            case 1: {
                                                courseViewModel.myInProgressPrograms.add(course);
                                                break;
                                            }
                                            case 2: {
                                                courseViewModel.myPausedPrograms.add(course);
                                                break;
                                            }
                                            case 3: {
                                                courseViewModel.myCompletedPrograms.add(course);
                                                break;
                                            }
                                            default: {

                                            }
                                        }

                                    } else if (course.getIsLiveCourse()) {

                                        courseViewModel.allLivePrograms.add(course);

                                        switch (course.getStatus()) {

                                            case 0: {
                                                courseViewModel.virginLivePrograms.add(course);
                                                break;
                                            }
                                            case 1: {
                                                courseViewModel.inProgressLivePrograms.add(course);
                                                break;
                                            }
                                            case 2: {
                                                courseViewModel.pausedLivePrograms.add(course);
                                                break;
                                            }
                                            case 3: {
                                                courseViewModel.completedLivePrograms.add(course);
                                                break;
                                            }
                                            default: {

                                            }
                                        }

                                    } else if (course.getSubscriptionType() == 2) {

                                        courseViewModel.allPaidPrograms.add(course);

                                        switch (course.getStatus()) {

                                            case 0: {
                                                courseViewModel.virginPaidPrograms.add(course);
                                                break;
                                            }
                                            case 1: {
                                                courseViewModel.inProgressPaidPrograms.add(course);
                                                break;
                                            }
                                            case 2: {
                                                courseViewModel.pausedPaidPrograms.add(course);
                                                break;
                                            }
                                            case 3: {
                                                courseViewModel.completedPaidPrograms.add(course);
                                                break;
                                            }
                                            default: {

                                            }
                                        }

                                    }
                                }



                            }

                            Collections.sort(courseViewModel.allTags, new Comparator<String>() {
                                @Override
                                public int compare(String o1, String o2) {
                                    return o1.compareToIgnoreCase(o2);
                                }
                            });

                            Collections.sort(courseViewModel.myAllPrograms, new Comparator<AvailableCourseModel.Course>() {
                                @Override
                                public int compare(AvailableCourseModel.Course o1, AvailableCourseModel.Course o2) {

                                    if (o1.getIsLiveCourse() && o2.getIsLiveCourse()) {
                                        return 0;
                                    } else if (o1.getIsLiveCourse()) {
                                        return 1;
                                    } else if (o1.getSubscriptionType() != 2 && o2.getSubscriptionType() != 2) {
                                        return 0;
                                    } else {
                                        if ((o1.getSubscriptionType() == 0 || o1.getSubscriptionType() == 1) && (o2.getSubscriptionType() == 0 || o2.getSubscriptionType() == 1)) {
                                            return 0;
                                        } else if (o1.getSubscriptionType() == 0 || o1.getSubscriptionType() == 1) {
                                            return -1;
                                        }
                                    }

                                    return 0;

                                }
                            });


                            loadAllAdapters();


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        getUserPermission();
                    }
                }

                @Override
                public void onFailure(Call<AvailableCourseModel> call, Throwable t) {
                    t.printStackTrace();
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }
    /////////////

    private void loadAllAdapters() {
        Log.e("pod",String.valueOf(courseViewModel.allPodcastPrograms.size()));

        loadMyPrgramsAdapter(courseViewModel.myAllPrograms, courseViewModel.myAllPrograms.size());

        loadMemberProgramsAdapter(courseViewModel.allMemberPrograms, courseViewModel.allMemberPrograms.size());

        loadLiveProgramsAdapter(courseViewModel.allLivePrograms, courseViewModel.allLivePrograms.size());

        loadPaidProgramsAdapter(courseViewModel.allPaidPrograms, courseViewModel.allPaidPrograms.size());

        loadMasterclassProgramsAdapter(courseViewModel.allMaterclassPrograms, courseViewModel.allMaterclassPrograms.size());

        loadPaidMasterclassProgramsAdapter(courseViewModel.allPaidMaterclassPrograms, courseViewModel.allPaidMaterclassPrograms.size());

        loadPodcastProgramsAdapter(courseViewModel.allPodcastPrograms, courseViewModel.allPodcastPrograms.size());

    }


    private void loadMyPrgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
        if (Size > 0) {
            myProgramsContainer.setVisibility(View.VISIBLE);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.MY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvMyPrograms.setAdapter(availableCourseAdapter);
        } else {
            myProgramsContainer.setVisibility(View.GONE);
        }

    }

    private void loadPaidProgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
        if (Size > 0) {
            paidProgramsContainer.setVisibility(View.VISIBLE);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.PAID_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvPaidPrograms.setAdapter(availableCourseAdapter);
        } else {
            paidProgramsContainer.setVisibility(View.GONE);
        }
    }

    private void loadLiveProgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
        if (Size > 0) {
            liveProgramsContainer.setVisibility(View.VISIBLE);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.LIVE_PROGRAM, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvLivePrograms.setAdapter(availableCourseAdapter);
        } else {
            liveProgramsContainer.setVisibility(View.GONE);
        }
    }

    private void loadMasterclassProgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
        if (Size > 0) {
            masterclassProgramsContainer.setVisibility(View.VISIBLE);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.MASTERCLASS_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvMasterclassPrograms.setAdapter(availableCourseAdapter);
        } else {
            masterclassProgramsContainer.setVisibility(View.GONE);
        }
    }

    private void loadPaidMasterclassProgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
        if (Size > 0) {
            paidMasterclassProgramsContainer.setVisibility(View.VISIBLE);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.PAID_MASTERCLASS_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvPaidMasterclassPrograms.setAdapter(availableCourseAdapter);
        } else {
            paidMasterclassProgramsContainer.setVisibility(View.GONE);
        }
    }

    private void loadPodcastProgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
      Log.e("pod",String.valueOf(lstData.size()));
        if (Size > 0) {
            podcastProgramsContainer.setVisibility(View.VISIBLE);
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.PODCAST_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvPodcastPrograms.setAdapter(availableCourseAdapter);
        } else {
            podcastProgramsContainer.setVisibility(View.GONE);
        }
    }

    private void loadMemberProgramsAdapter(List<AvailableCourseModel.Course> lstData, int Size) {
        if (Size > 0) {
            AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), lstData, AvailableCourseAdapter.ProgramType.MEMBER_ONLY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, Size);
            rvMemberPrograms.setAdapter(availableCourseAdapter);
        } else {
            memberOnlyProgramsContainer.setVisibility(View.GONE);
        }
    }

/*commented by sahenita unused in squad*/
/*
    private void loadEnrollAdapter(List<AvailableCourseModel.Course> lstData) {
        EnrollCourseAdapter availableCourseAdapter = new EnrollCourseAdapter(getActivity(), lstData);
        rvEnroll.setAdapter(availableCourseAdapter);
    }
*/
    /*commented by sahenita unused in squad*/
/*
    private void getEnrollCourse() {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<AvailableCourseModel> serverCall = finisherService.getEnrolledCourse(hashReq);
            serverCall.enqueue(new Callback<AvailableCourseModel>() {
                @Override
                public void onResponse(Call<AvailableCourseModel> call, Response<AvailableCourseModel> response) {
                    progressDialog.dismiss();
                    Log.e("success", "su");
                    if (response.body() != null) {
                        AvailableCourseModel lstData = response.body();
                        loadEnrollAdapter(lstData.getCourses());
                    }
                }

                @Override
                public void onFailure(Call<AvailableCourseModel> call, Throwable t) {
                    Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }
*/

    private void funToolBar() {
       // ((MainActivity) getActivity()).funDrawer();
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

        imgRightBack.setClickable(true);
        imgRightBack.setEnabled(true);
        frameNotification.setVisibility(View.GONE);
        imgFilter.setVisibility(View.GONE);
        //imgFilter.setBackgroundResource(R.drawable.filter);
        imgRightBack.setVisibility(View.GONE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
            /*if (Util.isSevenDayOver(getActivity())) {
                imgCircleBack.setVisibility(View.VISIBLE);
            } else {
                imgCircleBack.setVisibility(View.VISIBLE);
            }*/
            if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(getActivity())) {

                imgCircleBack.setVisibility(View.VISIBLE);

            } else if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(getActivity())) {

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

        /*commented by sahenita unused in squad*/
/*
        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((MainActivity) getActivity()).getDrawer().isDrawerOpen(Gravity.END)) {
                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                    intent.putExtra("VIDEO_URL", "https://player.vimeo.com/external/290408257.m3u8?s=eb4b9b3ed9aff17c0a6b8297ad209d7c8d6ed8d6");
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
                    intent.putExtra("VIDEO_URL", "https://player.vimeo.com/external/290408217.m3u8?s=f0b76fab6bfec350339727655d99961f15132eca");
                    getActivity().startActivity(intent);
                }
            }
        });
*/

        /*imgLogo.setOnClickListener(new View.OnClickListener() {             @Override             public void onClick(View v) {       ((MainActivity)getActivity()).showPromotionalDialogs();

            if(new SharedPreference(getActivity()).read("compChk", "").equals("false"))
            {
                ((MainActivity)getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
            }else {

                ((MainActivity)getActivity()).loadFragment(new HomeFragment(), "home", null);
            }
        }
        });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(origin.equals(""))
                    ((MainActivity)getActivity()).loadFragment(new LearnFragment(),"Learn",null);
                else
                {
                    ((MainActivity)getActivity()).loadFragment(new FBWHome(),"FBWHome",null);

                }*/


                if (getArguments() != null) {
                    if (getArguments().containsKey("origin")) {
                        if (getArguments().getString("origin").equals("chk")) {
                            ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                        } else if (getArguments().containsKey("NEWHOME")) {
                            /*commented by sahenita unused in squad*/
                           // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", getArguments());
                        } else if (getArguments().getString("origin").equals("learn")) {
                            ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                        } else {

                            {
                                ((MainActivity) getActivity()).loadFragment(new TrainFragment(), "Train", null);

                            }
                        }
                    } else {
                        if (getArguments().containsKey("NEWHOME")) {
                            /*commented by sahenita unused in squad*/
                           // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", null);
                        }
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("compChk", "").equals("false"))

                        ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                    else
                        ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
/*if("3".equalsIgnoreCase(accesstype)){
    if("false".equalsIgnoreCase(Course_access)) {

    }else{
        ((MainActivity) getActivity()).funDrawer1();
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).funDrawer();
        ((MainActivity) getActivity()).funTabBarforProgram();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    if (getArguments() != null) {
                        if (getArguments().containsKey("origin")) {
                            if (getArguments().getString("origin").equals("chk")) {
                                ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                            } else if (getArguments().getString("origin").equals("learn")) {
                                ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                            } else {
                                //  ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", null);
                            *//*if(origin.equals(""))
                                ((MainActivity)getActivity()).loadFragment(new LearnFragment(),"Learn",null);
                            else*//*
                                ((MainActivity) getActivity()).loadFragment(new TrainFragment(), "Train", null);
                            }
                        }
                    } else {
                        if (new SharedPreference(getActivity()).read("compChk", "").equals("false"))
                            ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                        else
                            ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                    }

                    return true;

                }

                return false;
            }
        });
    }
}else{*/
    ((MainActivity) getActivity()).funDrawer1();
    LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
    llTabView.setVisibility(View.VISIBLE);
  //  ((MainActivity) getActivity()).funDrawer();
    ((MainActivity) getActivity()).funTabBarforProgram();

    getView().setFocusableInTouchMode(true);
    getView().requestFocus();
    getView().setOnKeyListener(new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {

            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                // handle back button
                if (getArguments() != null) {
                    if (getArguments().containsKey("origin")) {
                        if (getArguments().getString("origin").equals("chk")) {
                            ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                        } else if (getArguments().getString("origin").equals("learn")) {
                            ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                        } else {
                            //  ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", null);
                            /*if(origin.equals(""))
                                ((MainActivity)getActivity()).loadFragment(new LearnFragment(),"Learn",null);
                            else*/
                            ((MainActivity) getActivity()).loadFragment(new TrainFragment(), "Train", null);
                        }
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("compChk", "").equals("false"))
                        ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                    else
                        ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                }

                return true;

            }

            return false;
        }
    });
//}



    }

    @Override
    public void onPause() {
        super.onPause();

      /*  if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(Course_access)) {

            }else{
                LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                llTabView.setVisibility(View.GONE);
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setNavigationIcon(null);
            }
        }else{*/
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.GONE);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(null);
//        }


    }
    /*commented by sahenita unused in squad*/
    /*
    private void openRestrictionDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_restriction);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button updateNow = dialog.findViewById(R.id.updateNowBtn);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        updateNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });
        dialog.show();

    }
*/


    private void openDialogForFilter() {
        try{

            filters= sharedPreference.getLocalFilters(getActivity(), "Coursefilterfile");
            //filters= sharedPreference.getLocalFilters(getActivity(), "Coursefilterfile");


        }catch (Exception e){
            e.printStackTrace();
        }
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_setprograms_filter);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView imgAllCheck = dialog.findViewById(R.id.imgAllCheck);
        ImageView imgActiveCheck = dialog.findViewById(R.id.imgActiveCheck);
        ImageView imgAvailableCheck = dialog.findViewById(R.id.imgAvailableCheck);
        ImageView imgCompletedCheck = dialog.findViewById(R.id.imgCompletedCheck);
        RelativeLayout rlAll = dialog.findViewById(R.id.rlAll);
        RelativeLayout rlInprogress = dialog.findViewById(R.id.rlInprogress);
        RelativeLayout rlPaused = dialog.findViewById(R.id.rlPaused);
        RelativeLayout rlCompleted = dialog.findViewById(R.id.rlCompleted);
        RelativeLayout rlTransparent = dialog.findViewById(R.id.rlTransparent);
        LinearLayout llTagsContainer = dialog.findViewById(R.id.llTagsContainer);
        EditText edtSearch = dialog.findViewById(R.id.edtSearch);

        RelativeLayout rlClearAll = dialog.findViewById(R.id.rlClearAll);
        RelativeLayout rlShowResults = dialog.findViewById(R.id.rlShowResults);

        CheckBox chkBoxShowAllCheck = dialog.findViewById(R.id.chkBoxShowAllCheck);

        CheckBox chkBoxMyPrograms = dialog.findViewById(R.id.chkBoxMyPrograms);
        CheckBox chkBoxPaidPrograms = dialog.findViewById(R.id.chkBoxPaidPrograms);
        CheckBox chkBoxLivePrograms = dialog.findViewById(R.id.chkBoxLivePrograms);
        CheckBox chkBoxMasterclassPrograms = dialog.findViewById(R.id.chkBoxMasterclassPrograms);

        CheckBox chkBoxStatusAll = dialog.findViewById(R.id.chkBoxStatusAll);
        CheckBox chkBoxStatusInProgress = dialog.findViewById(R.id.chkBoxStatusInProgress);
        CheckBox chkBoxStatusPaused = dialog.findViewById(R.id.chkBoxStatusPaused);
        CheckBox chkBoxStatusCompleted = dialog.findViewById(R.id.chkBoxStatusCompleted);

        ArrayList<CheckBox> chkBoxTagsList = new ArrayList<CheckBox>();

        HashMap<String, ArrayList<String>> localFilters = new HashMap<String, ArrayList<String>>() {{
            put(FILTER_KEY_TYPE, new ArrayList<String>() {{
            }});
            put(FILTER_KEY_STATUS, new ArrayList<String>() {{
            }});
            put(FILTER_KEY_TAGS, new ArrayList<>());
        }};


        for (int index = 0; index < courseViewModel.allTags.size(); index++) {

            View v = getLayoutInflater().inflate(R.layout.layout_dynamic_filter_meditation, null);

            CheckBox imgChk = v.findViewById(R.id.imgChk);
            imgChk.setText(courseViewModel.allTags.get(index));
            imgChk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String val = imgChk.getText().toString();
                  //  uncheckTickBoxes(chkBoxShowAllCheck);
                    if (isChecked) {
                        if (!localFilters.get(FILTER_KEY_TAGS).contains(val))
                            localFilters.get(FILTER_KEY_TAGS).add(val);
                    } else {
                        localFilters.get(FILTER_KEY_TAGS).remove(val);
                    }
                }
            });

            if(null==filters){
                imgChk.setChecked(true);
            }else{
                uncheckTickBoxes(chkBoxShowAllCheck);
                if (filters.get(FILTER_KEY_TAGS).contains(courseViewModel.allTags.get(index))) {
                    imgChk.setChecked(true);
                }

            }


            chkBoxTagsList.add(imgChk);
            llTagsContainer.addView(v);
        }

        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int clickedCheckboxId = buttonView.getId();

                if (clickedCheckboxId == chkBoxShowAllCheck.getId()) {

                    if (isChecked) {

                        uncheckTickBoxes(chkBoxMyPrograms, chkBoxPaidPrograms, chkBoxLivePrograms, chkBoxStatusAll, chkBoxStatusInProgress, chkBoxStatusPaused, chkBoxStatusCompleted);

                        uncheckTickBoxes(chkBoxTagsList.toArray(new CheckBox[chkBoxTagsList.size()]));


                    } else {

                    }

                } else if (clickedCheckboxId == chkBoxMyPrograms.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);

                        if (!localFilters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MY_PROGRAMS)) {
                            localFilters.get(FILTER_KEY_TYPE).add(FILTER_TYPE_MY_PROGRAMS);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_TYPE).remove(FILTER_TYPE_MY_PROGRAMS);
                    }
                } else if (clickedCheckboxId == chkBoxMasterclassPrograms.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MASTERCLASS)) {
                            localFilters.get(FILTER_KEY_TYPE).add(FILTER_TYPE_MASTERCLASS);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_TYPE).remove(FILTER_TYPE_MASTERCLASS);
                    }
                } else if (clickedCheckboxId == chkBoxPaidPrograms.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_PAID_PROGRAMS)) {
                            localFilters.get(FILTER_KEY_TYPE).add(FILTER_TYPE_PAID_PROGRAMS);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_TYPE).remove(FILTER_TYPE_PAID_PROGRAMS);
                    }
                } else if (clickedCheckboxId == chkBoxLivePrograms.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_LIVE_PROGRAMS)) {
                            localFilters.get(FILTER_KEY_TYPE).add(FILTER_TYPE_LIVE_PROGRAMS);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_TYPE).remove(FILTER_TYPE_LIVE_PROGRAMS);
                    }
                } else if (clickedCheckboxId == chkBoxStatusAll.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES)) {
                            localFilters.get(FILTER_KEY_STATUS).add(FILTER_STATUS_ALL_STATUSES);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_STATUS).remove(FILTER_STATUS_ALL_STATUSES);
                    }
                } else if (clickedCheckboxId == chkBoxStatusInProgress.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                            localFilters.get(FILTER_KEY_STATUS).add(FILTER_STATUS_IN_PROGRESS);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_STATUS).remove(FILTER_STATUS_IN_PROGRESS);
                    }
                } else if (clickedCheckboxId == chkBoxStatusPaused.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                            localFilters.get(FILTER_KEY_STATUS).add(FILTER_STATUS_PAUSED);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_STATUS).remove(FILTER_STATUS_PAUSED);
                    }
                } else if (clickedCheckboxId == chkBoxStatusCompleted.getId()) {
                    if (isChecked) {
                        uncheckTickBoxes(chkBoxShowAllCheck);
                        if (!localFilters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                            localFilters.get(FILTER_KEY_STATUS).add(FILTER_STATUS_COMPLETED);
                        }
                    } else {
                        localFilters.get(FILTER_KEY_STATUS).remove(FILTER_STATUS_COMPLETED);
                    }
                }

                Log.i(TAG, "filter types => " + localFilters.get(FILTER_KEY_TYPE).toString());
                Log.i(TAG, "filter statuses => " + localFilters.get(FILTER_KEY_STATUS).toString());

            }
        };

        chkBoxShowAllCheck.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxMyPrograms.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxPaidPrograms.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxLivePrograms.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxMasterclassPrograms.setOnCheckedChangeListener(onCheckedChangeListener);

        chkBoxStatusAll.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxStatusInProgress.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxStatusPaused.setOnCheckedChangeListener(onCheckedChangeListener);
        chkBoxStatusCompleted.setOnCheckedChangeListener(onCheckedChangeListener);


        rlClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uncheckTickBoxes(chkBoxShowAllCheck, chkBoxMyPrograms, chkBoxPaidPrograms, chkBoxLivePrograms, chkBoxStatusAll, chkBoxStatusInProgress, chkBoxStatusPaused, chkBoxStatusCompleted);
                imgFilter.setImageResource(R.drawable.mbhq_filter);
                chkBoxShowAllCheck.setChecked(true);
                filters.get(FILTER_KEY_TYPE).clear();
                filters.get(FILTER_KEY_STATUS).clear();
                filters.get(FILTER_KEY_TAGS).clear();
                getAvailableCourse();
                dialog.dismiss();
            }
        });
        if(null==filters){

            chkBoxShowAllCheck.setChecked(true);
            Log.i("course_filter","1");
            imgFilter.setImageResource(R.drawable.mbhq_filter);

        }else{

            if (filters.get(FILTER_KEY_STATUS).isEmpty() && filters.get(FILTER_KEY_TYPE).isEmpty() && filters.get(FILTER_KEY_TAGS).isEmpty()) {
                imgFilter.setImageResource(R.drawable.mbhq_filter);
                chkBoxShowAllCheck.setChecked(true);
            } else {
                imgFilter.setImageResource(R.drawable.mbhq_filter_green);
                chkBoxShowAllCheck.setChecked(false);
                if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MY_PROGRAMS)) {
                    chkBoxMyPrograms.setChecked(true);
                }
                if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_LIVE_PROGRAMS)) {
                    chkBoxLivePrograms.setChecked(true);
                }
                if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_PAID_PROGRAMS)) {
                    chkBoxPaidPrograms.setChecked(true);
                }
                if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MASTERCLASS)) {
                    chkBoxMasterclassPrograms.setChecked(true);
                }

                if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES)) {
                    chkBoxStatusAll.setChecked(true);
                }
                if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                    chkBoxStatusInProgress.setChecked(true);
                }
                if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                    chkBoxStatusPaused.setChecked(true);
                }
                if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                    chkBoxStatusCompleted.setChecked(true);
                }

            }
        }

        /*show data*/
if(null==filters){
loadAllAdapters();
}else{
    ArrayList<AvailableCourseModel.Course> filteredMyPrograms = new ArrayList<>();
    ArrayList<AvailableCourseModel.Course> filteredPaidPrograms = new ArrayList<>();
    ArrayList<AvailableCourseModel.Course> filteredLivePrograms = new ArrayList<>();
    ArrayList<AvailableCourseModel.Course> filteredMasterclassPrograms = new ArrayList<>();


    if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MY_PROGRAMS)
            || filters.get(FILTER_KEY_TYPE).isEmpty()) {

        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
            filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myAllPrograms, localFilters.get(FILTER_KEY_TAGS)));
        } else {

            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myInProgressPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myCompletedPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myPausedPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }

        }

    }

    if (
            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_PAID_PROGRAMS)
                    ||
                    filters.get(FILTER_KEY_TYPE).isEmpty()
    ) {

        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
            filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.allPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
        } else {
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.inProgressPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.completedPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.pausedPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
        }

    }

    if (
            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_LIVE_PROGRAMS)
                    ||
                    filters.get(FILTER_KEY_TYPE).isEmpty()
    ) {

        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
            filteredLivePrograms.addAll(sortDataByTags(courseViewModel.allLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
        } else {
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.inProgressLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.completedLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.pausedLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
        }

    }

    if (
            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MASTERCLASS)
                    ||
                    filters.get(FILTER_KEY_TYPE).isEmpty()
    ) {

        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
            filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.allMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
        } else {
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.inProgressMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.completedMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.pausedMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
            }
        }

    }

    if (!filteredMyPrograms.isEmpty() || !filteredLivePrograms.isEmpty() || !filteredPaidPrograms.isEmpty() || !filteredMasterclassPrograms.isEmpty()) {

        loadMyPrgramsAdapter(filteredMyPrograms, filteredMyPrograms.size());

        loadLiveProgramsAdapter(filteredLivePrograms, filteredLivePrograms.size());

        loadPaidProgramsAdapter(filteredPaidPrograms, filteredPaidPrograms.size());

        loadMasterclassProgramsAdapter(filteredMasterclassPrograms, filteredMasterclassPrograms.size());


    } else {
        Util.showToast(getActivity(), "No Program Data Found");
    }
}


        /*show data*/






        edtSearch.setText(searchFilterText);

        rlShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null==filters){
                    HashMap<String, ArrayList<String>> filters = new HashMap<String, ArrayList<String>>() {{
                        put(FILTER_KEY_TYPE, new ArrayList<String>() {{
                        }});
                        put(FILTER_KEY_STATUS, new ArrayList<String>() {{
                        }});
                        put(FILTER_KEY_TAGS, new ArrayList<>());
                    }};
                    filters.get(FILTER_KEY_TYPE).clear();
                    filters.get(FILTER_KEY_STATUS).clear();
                    filters.get(FILTER_KEY_TAGS).clear();

                    filters.get(FILTER_KEY_TYPE).addAll(localFilters.get(FILTER_KEY_TYPE));
                    filters.get(FILTER_KEY_STATUS).addAll(localFilters.get(FILTER_KEY_STATUS));
                    filters.get(FILTER_KEY_TAGS).addAll(localFilters.get(FILTER_KEY_TAGS));

                    //  sharedPreference.saveLocalFilters(filters);

                    ArrayList<AvailableCourseModel.Course> filteredMyPrograms = new ArrayList<>();
                    ArrayList<AvailableCourseModel.Course> filteredPaidPrograms = new ArrayList<>();
                    ArrayList<AvailableCourseModel.Course> filteredLivePrograms = new ArrayList<>();
                    ArrayList<AvailableCourseModel.Course> filteredMasterclassPrograms = new ArrayList<>();


                    if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MY_PROGRAMS)
                            || filters.get(FILTER_KEY_TYPE).isEmpty()) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myAllPrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {

                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myInProgressPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myCompletedPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myPausedPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }

                        }

                    }

                    if (
                            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_PAID_PROGRAMS)
                                    ||
                                    filters.get(FILTER_KEY_TYPE).isEmpty()
                    ) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.allPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.inProgressPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.completedPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.pausedPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                        }

                    }

                    if (
                            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_LIVE_PROGRAMS)
                                    ||
                                    filters.get(FILTER_KEY_TYPE).isEmpty()
                    ) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredLivePrograms.addAll(sortDataByTags(courseViewModel.allLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.inProgressLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.completedLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.pausedLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                        }

                    }

                    if (
                            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MASTERCLASS)
                                    ||
                                    filters.get(FILTER_KEY_TYPE).isEmpty()
                    ) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.allMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.inProgressMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.completedMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.pausedMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                        }

                    }

                    if (!filteredMyPrograms.isEmpty() || !filteredLivePrograms.isEmpty() || !filteredPaidPrograms.isEmpty() || !filteredMasterclassPrograms.isEmpty()) {

                        loadMyPrgramsAdapter(filteredMyPrograms, filteredMyPrograms.size());

                        loadLiveProgramsAdapter(filteredLivePrograms, filteredLivePrograms.size());

                        loadPaidProgramsAdapter(filteredPaidPrograms, filteredPaidPrograms.size());

                        loadMasterclassProgramsAdapter(filteredMasterclassPrograms, filteredMasterclassPrograms.size());


                    } else {
                        Util.showToast(getActivity(), "No Program Data Found");
                    }

                    try{

                        sharedPreference.saveLocalFilters(getActivity(),"Coursefilterfile","aaa",filters);
                    }catch(Exception e){
                        e.printStackTrace();
                    }


                }else{
                    filters.get(FILTER_KEY_TYPE).clear();
                    filters.get(FILTER_KEY_STATUS).clear();
                    filters.get(FILTER_KEY_TAGS).clear();

                    filters.get(FILTER_KEY_TYPE).addAll(localFilters.get(FILTER_KEY_TYPE));
                    filters.get(FILTER_KEY_STATUS).addAll(localFilters.get(FILTER_KEY_STATUS));
                    filters.get(FILTER_KEY_TAGS).addAll(localFilters.get(FILTER_KEY_TAGS));

                    //  sharedPreference.saveLocalFilters(filters);

                    ArrayList<AvailableCourseModel.Course> filteredMyPrograms = new ArrayList<>();
                    ArrayList<AvailableCourseModel.Course> filteredPaidPrograms = new ArrayList<>();
                    ArrayList<AvailableCourseModel.Course> filteredLivePrograms = new ArrayList<>();
                    ArrayList<AvailableCourseModel.Course> filteredMasterclassPrograms = new ArrayList<>();


                    if (filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MY_PROGRAMS)
                            || filters.get(FILTER_KEY_TYPE).isEmpty()) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myAllPrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {

                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myInProgressPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myCompletedPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredMyPrograms.addAll(sortDataByTags(courseViewModel.myPausedPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }

                        }

                    }

                    if (
                            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_PAID_PROGRAMS)
                                    ||
                                    filters.get(FILTER_KEY_TYPE).isEmpty()
                    ) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.allPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.inProgressPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.completedPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredPaidPrograms.addAll(sortDataByTags(courseViewModel.pausedPaidPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                        }

                    }

                    if (
                            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_LIVE_PROGRAMS)
                                    ||
                                    filters.get(FILTER_KEY_TYPE).isEmpty()
                    ) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredLivePrograms.addAll(sortDataByTags(courseViewModel.allLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.inProgressLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.completedLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredLivePrograms.addAll(sortDataByTags(courseViewModel.pausedLivePrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                        }

                    }

                    if (
                            filters.get(FILTER_KEY_TYPE).contains(FILTER_TYPE_MASTERCLASS)
                                    ||
                                    filters.get(FILTER_KEY_TYPE).isEmpty()
                    ) {

                        if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_ALL_STATUSES) || filters.get(FILTER_KEY_STATUS).isEmpty()) {
                            filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.allMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                        } else {
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_IN_PROGRESS)) {
                                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.inProgressMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_COMPLETED)) {
                                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.completedMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                            if (filters.get(FILTER_KEY_STATUS).contains(FILTER_STATUS_PAUSED)) {
                                filteredMasterclassPrograms.addAll(sortDataByTags(courseViewModel.pausedMaterclassPrograms, localFilters.get(FILTER_KEY_TAGS)));
                            }
                        }

                    }

                    if (!filteredMyPrograms.isEmpty() || !filteredLivePrograms.isEmpty() || !filteredPaidPrograms.isEmpty() || !filteredMasterclassPrograms.isEmpty()) {

                        loadMyPrgramsAdapter(filteredMyPrograms, filteredMyPrograms.size());

                        loadLiveProgramsAdapter(filteredLivePrograms, filteredLivePrograms.size());

                        loadPaidProgramsAdapter(filteredPaidPrograms, filteredPaidPrograms.size());

                        loadMasterclassProgramsAdapter(filteredMasterclassPrograms, filteredMasterclassPrograms.size());


                    } else {
                        Util.showToast(getActivity(), "No Program Data Found");
                    }

                    try{

                        sharedPreference.saveLocalFilters(getActivity(),"Coursefilterfile","aaa",filters);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }

                dialog.dismiss();


            }
        });


        if (filterSelectedvalue == 0) {
            hideAllFilterImage(imgAllCheck, imgActiveCheck, imgAvailableCheck, imgCompletedCheck);
            imgAllCheck.setVisibility(View.VISIBLE);
        }
        if (filterSelectedvalue == 1) {
            hideAllFilterImage(imgAllCheck, imgActiveCheck, imgAvailableCheck, imgCompletedCheck);
            imgActiveCheck.setVisibility(View.VISIBLE);
        }
        if (filterSelectedvalue == 2) {
            hideAllFilterImage(imgAllCheck, imgActiveCheck, imgAvailableCheck, imgCompletedCheck);
            imgAvailableCheck.setVisibility(View.VISIBLE);
        }
        if (filterSelectedvalue == 3) {
            hideAllFilterImage(imgAllCheck, imgActiveCheck, imgAvailableCheck, imgCompletedCheck);
            imgCompletedCheck.setVisibility(View.VISIBLE);
        }

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!edtSearch.getText().toString().equals("")) {
                        searchFilterText = edtSearch.getText().toString();
                        performSearch(searchFilterText);
                        Util.hideKeyboard(getActivity());

                    }
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });

        rlAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (courseViewModel.myAllPrograms.size() > 0) {
                    filterSelectedvalue = 0;
                    rvMyPrograms.setAdapter(null);
                    AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), courseViewModel.myAllPrograms, AvailableCourseAdapter.ProgramType.MY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, courseViewModel.myAllPrograms.size());
                    rvMyPrograms.setAdapter(availableCourseAdapter);
                    loadLiveProgramsAdapter(courseViewModel.allLivePrograms, courseViewModel.allLivePrograms.size());
                    loadPaidProgramsAdapter(courseViewModel.allPaidPrograms, courseViewModel.allPaidPrograms.size());
                    loadMemberProgramsAdapter(courseViewModel.allMemberPrograms, courseViewModel.allMemberPrograms.size());

                } else
                    Util.showToast(getActivity(), "No matching data found");
                ////////////////


            }
        });

        rlInprogress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (courseViewModel.myInProgressPrograms.size() > 0) {
                    filterSelectedvalue = 1;
                    rvMyPrograms.setAdapter(null);
                    Log.e("print size inprogress--", courseViewModel.myInProgressPrograms.size() + "???");
                    AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), courseViewModel.myInProgressPrograms, AvailableCourseAdapter.ProgramType.MY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, courseViewModel.myInProgressPrograms.size());
                    rvMyPrograms.setAdapter(availableCourseAdapter);
                    loadLiveProgramsAdapter(courseViewModel.inProgressLivePrograms, courseViewModel.inProgressLivePrograms.size());
                    loadPaidProgramsAdapter(courseViewModel.inProgressPaidPrograms, courseViewModel.inProgressPaidPrograms.size());
                    loadMemberProgramsAdapter(courseViewModel.inProgressMemberPrograms, courseViewModel.inProgressMemberPrograms.size());
                } else
                    Util.showToast(getActivity(), "No matching data found");
                ///////////////


            }
        });

        rlPaused.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (courseViewModel.myPausedPrograms.size() > 0) {
                    filterSelectedvalue = 2;
                    Log.e("print size paused--", courseViewModel.myPausedPrograms.size() + "???");
                    AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), courseViewModel.myPausedPrograms, AvailableCourseAdapter.ProgramType.MY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, courseViewModel.myPausedPrograms.size());
                    rvMyPrograms.setAdapter(availableCourseAdapter);
                    loadLiveProgramsAdapter(courseViewModel.pausedLivePrograms, courseViewModel.pausedLivePrograms.size());
                    loadPaidProgramsAdapter(courseViewModel.pausedPaidPrograms, courseViewModel.pausedPaidPrograms.size());
                    loadMemberProgramsAdapter(courseViewModel.pausedMemberPrograms, courseViewModel.pausedMemberPrograms.size());

                } else
                    Util.showToast(getActivity(), "No matching data found");
                //////////////

            }
        });

        rlCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (courseViewModel.myCompletedPrograms.size() > 0) {
                    filterSelectedvalue = 3;
                    Log.e("print size completed--", courseViewModel.myCompletedPrograms.size() + "???");
                    AvailableCourseAdapter availableCourseAdapter = new AvailableCourseAdapter(getActivity(), courseViewModel.myCompletedPrograms, AvailableCourseAdapter.ProgramType.MY_PROGRAMS, origin, PLAY_EPISODE_ONE, FROMSETPROGRAM, FROMTODAYPAGE, courseViewModel.myCompletedPrograms.size());
                    rvMyPrograms.setAdapter(availableCourseAdapter);
                    loadLiveProgramsAdapter(courseViewModel.completedLivePrograms, courseViewModel.completedLivePrograms.size());
                    loadPaidProgramsAdapter(courseViewModel.completedPaidPrograms, courseViewModel.completedPaidPrograms.size());
                    loadMemberProgramsAdapter(courseViewModel.completedMemberPrograms, courseViewModel.completedMemberPrograms.size());

                } else
                    Util.showToast(getActivity(), "No matching data found");


            }
        });

        rlTransparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private ArrayList<AvailableCourseModel.Course> sortDataByTags(List<AvailableCourseModel.Course> dataToBeFiltered, ArrayList<String> filteringTags) {
        ArrayList<AvailableCourseModel.Course> sortedData = new ArrayList<AvailableCourseModel.Course>();

        if (!filteringTags.isEmpty()) {
            for (int index = 0; index < dataToBeFiltered.size(); index++) {
                for (int tagIndex = 0; tagIndex < filteringTags.size(); tagIndex++) {
                    if (dataToBeFiltered.get(index).getTags().contains(filteringTags.get(tagIndex))) {
                        sortedData.add(dataToBeFiltered.get(index));
                    }
                }
            }
        } else {
            sortedData.addAll(dataToBeFiltered);
        }

        Log.i(TAG, "data to be filtered : " + dataToBeFiltered);

        Log.i(TAG, "filtering tags : " + filteringTags);

        Log.i(TAG, "sorted data : " + sortedData.toString());

        return sortedData;
    }

    private void hideAllFilterImage(ImageView imageView1, ImageView imageView2, ImageView imageView3, ImageView imageView4) {
        imageView1.setVisibility(View.GONE);
        imageView2.setVisibility(View.GONE);
        imageView3.setVisibility(View.GONE);
        imageView4.setVisibility(View.GONE);
    }


    private void uncheckTickBoxes(CheckBox... checkBoxes) {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setChecked(false);
        }
    }


    private void getUserPermission() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (hasWritePermission() && hasRecordPermission()) {


                } else {

                    if (!Settings.System.canWrite((MainActivity) getActivity())) {
                        ((MainActivity) getActivity()).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, 289);
                    }
                }


            } else {
                // continue with your code
                //  funDownloadVideoFollowAlong(lstData.getEventItemVideoDetails().get(0).getDownloadURL(),fileName);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            switch (requestCode) {
                case 289: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Permission write", "Granted");

                        // getTodayDataFromApi(Util.globalDate);
                        // getDownloadFileName();
                        SharedPreference sharedPreference = new SharedPreference(((MainActivity) getActivity()));
                        sharedPreference.write("writePer", "", "true");


                    }
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // setupVisualizerFxAndUI();

                    } else {
                        Log.e("Permission write", "Denied");
                        Util.showToast(((MainActivity) getActivity()), "Please enable Write  permission from settings.");
                    }
                    return;
                }
                case 201: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Permission Gal", "Granted");

                    } else {
                        // Log.e("Permission MAP", "Denied");

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasWritePermission() {
        int hasPermissionWrite = ContextCompat.checkSelfPermission((MainActivity) getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = ContextCompat.checkSelfPermission((MainActivity) getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED;
    }

    private boolean hasRecordPermission() {
        int hasPermissionRecord = ContextCompat.checkSelfPermission(((MainActivity) getActivity()), Manifest.permission.RECORD_AUDIO);

        return hasPermissionRecord == PackageManager.PERMISSION_GRANTED && hasPermissionRecord == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("state1111111111111111111111111111","onCreate4");

        sharedPreference = new SharedPreference(getActivity());
        Course_access=sharedPreference.read("CourseAccess","");

        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");



    /*    if("false".equalsIgnoreCase(Course_access)){

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("User don't have access to use Course") .setTitle("Efc");

            //Setting message manually and performing action on button click
            builder.setCancelable(false)

                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            if("habit".equalsIgnoreCase(Util.sourcepage)){
                                ((MainActivity) getActivity()).funHabits_();
                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                            }else if("Eq".equalsIgnoreCase(Util.sourcepage)){
                                ((MainActivity) getActivity()).refershGamePoint(getActivity());
                                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                Util.isReloadTodayMainPage = true;
                                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                ((MainActivity) getActivity()).fungratitudeicon();
                                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                            }else if("Live".equalsIgnoreCase(Util.sourcepage)){
                                ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                            }else if("test".equalsIgnoreCase(Util.sourcepage)){
                                ((MainActivity) getActivity()).funDrawer1();

                                ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                                ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);

                            }else if("meditation".equalsIgnoreCase(Util.sourcepage)){
                                Util.refresh_meditation="yes";
                                Util.refresh_gratitude="yes";
                                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                ((MainActivity) getActivity()).funMeditation();
                                Util.refresh_gratitude="yes";
                            }

                        }
                    });

            //Creating dialog box
            AlertDialog alert = builder.create();
            alert.setCanceledOnTouchOutside(false);
            //Setting the title manually
            alert.show();
            // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
        }else{*/
            Util.sourcepage="course";
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(networkChangeReceiver2, filter);

//        }



    }

    @Override
    public void onDestroy() {
        super.onDestroy();
      /*  if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(Course_access)) {
            }else{
                getActivity().unregisterReceiver(networkChangeReceiver2);

            }
        }else{*/
            getActivity().unregisterReceiver(networkChangeReceiver2);

//        }

    }

    private BroadcastReceiver networkChangeReceiver2 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {

                if("3".equalsIgnoreCase(accesstype)&&"false".equalsIgnoreCase(eq_access)){

                }else{
                    Util.calledgratitudeafterinternatecomming = false;
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
}
