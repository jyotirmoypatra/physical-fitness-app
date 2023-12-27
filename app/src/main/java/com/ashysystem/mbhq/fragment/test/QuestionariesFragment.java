package com.ashysystem.mbhq.fragment.test;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.fragment.TrackFragment;
import com.ashysystem.mbhq.fragment.achievement.MyAchievementsFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.MbhqTodayTwoFragment;
import com.ashysystem.mbhq.fragment.live_chat.LiveChatFragment;
import com.ashysystem.mbhq.model.QuestionMain;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionariesFragment extends Fragment {
  /*  String accesstype="3";
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
    private LinearLayout llCohen,llRahe,llHappy,llDas,llDas_below,llDas1;
    View view_das;
    private List<QuestionMain.Detail> lstData;
    private List<QuestionMain.Detail> lstCohen=new ArrayList<QuestionMain.Detail>();
    private List<QuestionMain.Detail> lstRahe=new ArrayList<QuestionMain.Detail>();
    private List<QuestionMain.Detail> lstHappiness=new ArrayList<QuestionMain.Detail>();
    private List<QuestionMain.Detail> lstDas=new ArrayList<QuestionMain.Detail>();

    private ImageView imgMIconD,imgEIconD,imgMIconT,imgEIconT,imgMIconM,imgEIconM,imgMIconE,imgEIconE;
    private TextView txtEIconD,txtMIconD,txtMIconT,txtEIconT,txtMIconM,txtEIconM,txtMIconE,txtEIconE;
    private RelativeLayout rlResultCohen,rlResultRahe,rlResultHappy,rlTypeNowHappy,rlTypeNowStress,rlResultDas;
    private LinearLayout llMainBlock;
    private TextView txtHappyBefore,txtHappyNow,txtCohenBefore,txtCohenNow;
    View view=null;
    private Integer cohenNowAttemptId=0;
    private Integer cohenNowTypeId=0;
    private Integer happinessNowAttemptId=0;
    private Integer happinessNowTypeId=0;
    private LinearLayout llHappyBefore,llStressBefore,llStressAfter,llHappyAfter,ll_happy_details,ll_happy_details1,ll_stress_details,ll_stress_details1;
    private RelativeLayout rlHappyAction,rlStressAction,rlDasAction;
    ImageView imgHappyAdd,imgStressAdd;
    TextView txt_b_d,txt_b_a,txt_b_s,txt_a_d,txt_a_a,txt_a_s;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("state1111111111111111111111111111","onCreate5");
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        Test_acess=sharedPreference.read("TestsAccess","");

        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(Test_acess)) {
                return null;
            }else{
                Util.sourcepage="test";
                if(view==null)
                {
                    view=inflater.inflate(R.layout.layout_questionnaries,container,false);
                    LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                    llTabView.setVisibility(View.VISIBLE);
                    initview(view);
                    funToolBar();
                    getQuestionFromApi();
                    return view;
                }
                else
                {
                    funToolBar();
                    return view;
                }
            }
        }else{
            Util.sourcepage="test";
            if(view==null)
            {
                view=inflater.inflate(R.layout.layout_questionnaries,container,false);
                LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                llTabView.setVisibility(View.VISIBLE);
                initview(view);
                funToolBar();
                getQuestionFromApi();
                return view;
            }
            else
            {
                funToolBar();
                return view;
            }
        }




    }
    @Override
    public void onResume() {
        super.onResume();
        SharedPreference  sharedPreference = new SharedPreference(getActivity());
        Test_acess=sharedPreference.read("TestsAccess","");

        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
        habit_access=sharedPreference.read("HabitAccess","");
        eq_access=sharedPreference.read("EqJournalAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");




        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(Test_acess)){

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("User don't have access to use Test") .setTitle("Efc");

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
                                }else if("habit".equalsIgnoreCase(Util.sourcepage)){
                                    ((MainActivity) getActivity()).funHabits_();
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayTwoFragment());
                                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                                }else if("Live".equalsIgnoreCase(Util.sourcepage)){
                                    ((MainActivity) getActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                                }

                            }
                        });

                //Creating dialog box
                AlertDialog alert = builder.create();
                alert.setCanceledOnTouchOutside(false);
                //Setting the title manually
                alert.show();
                // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
            }else{
                LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                llTabView.setVisibility(View.VISIBLE);
                //((MainActivity)getActivity()).funDrawer();
                ((MainActivity)getActivity()).funTabBarforProgram();
            }
        }else{
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.VISIBLE);
           // ((MainActivity)getActivity()).funDrawer();
            ((MainActivity)getActivity()).funTabBarforProgram();
        }


    }
    @Override
    public void onPause() {
        super.onPause();

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(Test_acess)) {
            }else{
                LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                llTabView.setVisibility(View.GONE);
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setNavigationIcon(null);
            }
        }else{
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.GONE);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(null);
        }


    }

    private void initview(View view) {

        ll_happy_details=view.findViewById(R.id.ll_happy_details);
        ll_happy_details1=view.findViewById(R.id.ll_happy_details1);
        ll_stress_details=view.findViewById(R.id.ll_stress_details);
        ll_stress_details1=view.findViewById(R.id.ll_stress_details1);

        llDas1=view.findViewById(R.id.llDas1);


        llDas_below=view.findViewById(R.id.llDas_below);
        llDas=view.findViewById(R.id.llDas);
        view_das=view.findViewById(R.id.view_das);
        txt_b_d=view.findViewById(R.id.txt_b_d);
        txt_b_a=view.findViewById(R.id.txt_b_a);
        txt_b_s=view.findViewById(R.id.txt_b_s);
        txt_a_d=view.findViewById(R.id.txt_a_d);
        txt_a_a=view.findViewById(R.id.txt_a_a);
        txt_a_s=view.findViewById(R.id.txt_a_s);
        txtEIconD=view.findViewById(R.id.txtEIconD);
        txtMIconD=view.findViewById(R.id.txtMIconD);
        rlResultDas=view.findViewById(R.id.rlResultDas);
        llHappyAfter=view.findViewById(R.id.llHappyAfter);
        llStressAfter=view.findViewById(R.id.llStressAfter);
        imgHappyAdd=view.findViewById(R.id.imgHappyAdd);
        imgStressAdd=view.findViewById(R.id.imgStressAdd);
        rlStressAction=view.findViewById(R.id.rlStressAction);
        rlHappyAction=view.findViewById(R.id.rlHappyAction);
        rlDasAction=view.findViewById(R.id.rlDasAction);
        llStressBefore=view.findViewById(R.id.llStressBefore);
        llHappyBefore=view.findViewById(R.id.llHappyBefore);
        rlTypeNowHappy=view.findViewById(R.id.rlTypeNowHappy);
        rlTypeNowStress=view.findViewById(R.id.rlTypeNowStress);
        llMainBlock=(LinearLayout)view.findViewById(R.id.llMainBlock);
        llCohen=(LinearLayout)view.findViewById(R.id.llCohen);
        llRahe=(LinearLayout)view.findViewById(R.id.llRahe);
        llHappy=(LinearLayout)view.findViewById(R.id.llHappy);
        rlResultCohen=(RelativeLayout)view.findViewById(R.id.rlResultCohen);
        rlResultRahe=(RelativeLayout)view.findViewById(R.id.rlResultRahe);
        rlResultHappy=(RelativeLayout)view.findViewById(R.id.rlResultHappy);
        txtHappyBefore=view.findViewById(R.id.txtHappyBefore);
        txtHappyNow=view.findViewById(R.id.txtHappyNow);
        txtCohenBefore=view.findViewById(R.id.txtCohenBefore);
        txtCohenNow=view.findViewById(R.id.txtCohenNow);
        imgMIconD=(ImageView)view.findViewById(R.id.imgMIconD);
        imgEIconD=(ImageView)view.findViewById(R.id.imgEIconD);
        imgMIconT=(ImageView)view.findViewById(R.id.imgMIconT);
        imgEIconT=(ImageView)view.findViewById(R.id.imgEIconT);

        imgMIconM=(ImageView)view.findViewById(R.id.imgMIconM);
        imgEIconM=(ImageView)view.findViewById(R.id.imgEIconM);

        imgMIconE=(ImageView)view.findViewById(R.id.imgMIconE);
        imgEIconE=(ImageView)view.findViewById(R.id.imgEIconE);
        /////////////////////
        txtMIconT=(TextView) view.findViewById(R.id.txtMIconT);
        txtEIconT=(TextView) view.findViewById(R.id.txtEIconT);

        txtMIconM=(TextView) view.findViewById(R.id.txtMIconM);
        txtEIconM=(TextView) view.findViewById(R.id.txtEIconM);

        txtMIconE=(TextView) view.findViewById(R.id.txtMIconE);
        txtEIconE=(TextView) view.findViewById(R.id.txtEIconE);

        ll_happy_details1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlResultHappy.performClick();
            }
        });
        ll_happy_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlResultHappy.performClick();
            }
        });
        ll_stress_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlResultCohen.performClick();
            }
        });
        ll_stress_details1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlResultCohen.performClick();
            }
        });
        imgHappyAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlHappyAction.performClick();
            }
        });
        imgStressAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlStressAction.performClick();
            }
        });
        llHappyBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlResultHappy.performClick();
            }
        });
        llStressBefore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlResultCohen.performClick();
            }
        });
        rlTypeNowStress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            /*  Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireStatusID",1);
                bundle.putInt("QuestionnaireTypeId",cohenNowTypeId);
                bundle.putInt("GetAttemptId",cohenNowAttemptId);

                    CohenQuestion cohenQuestion=new CohenQuestion();
                    cohenQuestion.setArguments(bundle);
                    ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Cohen",null);*/

                rlResultCohen.performClick();



                //getActivity().startActivity(new Intent(getActivity(), DummyKotlin.class));
                // ((MainActivity)getActivity()).loadFragment(new DummyFragment(),"DummyFragment",null);




            }
        });
        rlTypeNowHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireStatusID",1);
                bundle.putInt("QuestionnaireTypeId",happinessNowTypeId);
                bundle.putInt("GetAttemptId",happinessNowAttemptId);



                    HappinessQuestionFragment happinessQuestionFragment=new HappinessQuestionFragment();
                    happinessQuestionFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).loadFragment(happinessQuestionFragment,"Happy",null);*/

                rlResultHappy.performClick();


            }
        });
        rlResultCohen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                    if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                    {
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionResultFragment());
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",1);
                        QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }else {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    } else {
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionResultFragment());
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",1);
                        QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }
                }



               /* Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireTypeId",1);
                QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                questionResultFragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);*/
            }
        });
        rlResultRahe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                    if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                    {
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionResultFragment());
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",2);
                        QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }else {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    } else {
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionResultFragment());
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",2);
                        QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }
                }


               /* Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireTypeId",2);
                QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                questionResultFragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);*/
            }
        });

        rlResultDas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                    if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                    {
                        Log.i("hiii","1");
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",5);
                        QuestionResultFragment_Das questionResultFragment=new QuestionResultFragment_Das();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }else {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    } else {
                        Log.i("hiii","2");
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",5);
                        QuestionResultFragment_Das questionResultFragment=new QuestionResultFragment_Das();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }
                }*/
                Log.i("hiii","3");
                Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireTypeId",5);
                QuestionResultFragment_Das questionResultFragment=new QuestionResultFragment_Das();
                questionResultFragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
            }
        });


        rlResultHappy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                    if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                    {
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionResultFragment());
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",3);
                        QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }else {
                        ((MainActivity)getActivity()).showAlertGergious();
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                        ((MainActivity)getActivity()).showAlertGergious();
                    } else {
                        ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionResultFragment());
                        Bundle bundle=new Bundle();
                        bundle.putInt("QuestionnaireTypeId",3);
                        QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                        questionResultFragment.setArguments(bundle);
                        ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);
                    }
                }
                /*Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireTypeId",3);
                QuestionResultFragment questionResultFragment=new QuestionResultFragment();
                questionResultFragment.setArguments(bundle);
                ((MainActivity)getActivity()).loadFragment(questionResultFragment,"QuestionResultFragment",null);*/
            }
        });
        ////////////////////

        rlStressAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearCacheForParticularFragment(new CohenQuestion());
                if(lstCohen.size()>0) {
                    if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                        if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                            ((MainActivity)getActivity()).showAlertGergious();
                        }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                        {
                            Bundle bundle=new Bundle();
                            bundle.putInt("QuestionnaireStatusID",1);
                            bundle.putInt("QuestionnaireTypeId",1);
                            if(lstCohen.size()>0) {
                                if (lstCohen.get(lstCohen.size() - 1).getQuestionnaireStatusID() == 1) {
                                    bundle.putInt("AttemptId",lstCohen.get(lstCohen.size() - 1).getQuestionnaireAttemptId());
                                }

                                bundle.putInt("QuestionnaireStatusID",lstCohen.get(lstCohen.size() - 1).getQuestionnaireStatusID());
                            }
                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new CohenQuestion());
                            CohenQuestion cohenQuestion=new CohenQuestion();
                            cohenQuestion.setArguments(bundle);
                            ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Cohen",null);
                        }else {
                            ((MainActivity)getActivity()).showAlertGergious();
                        }
                    } else {
                        if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                            ((MainActivity)getActivity()).showAlertGergious();
                        } else {
                            Bundle bundle=new Bundle();
                            bundle.putInt("QuestionnaireStatusID",1);
                            bundle.putInt("QuestionnaireTypeId",1);
                            if(lstCohen.size()>0) {
                                if (lstCohen.get(lstCohen.size() - 1).getQuestionnaireStatusID() == 1) {
                                    bundle.putInt("AttemptId",lstCohen.get(lstCohen.size() - 1).getQuestionnaireAttemptId());
                                }

                                bundle.putInt("QuestionnaireStatusID",lstCohen.get(lstCohen.size() - 1).getQuestionnaireStatusID());
                            }
                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new CohenQuestion());
                            CohenQuestion cohenQuestion=new CohenQuestion();
                            cohenQuestion.setArguments(bundle);
                            ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Cohen",null);
                        }
                    }
                }else
                {
                    Bundle bundle=new Bundle();
                    bundle.putInt("QuestionnaireStatusID",1);
                    bundle.putInt("QuestionnaireTypeId",1);
                    if(lstCohen.size()>0) {
                        if (lstCohen.get(lstCohen.size() - 1).getQuestionnaireStatusID() == 1) {
                            bundle.putInt("AttemptId",lstCohen.get(lstCohen.size() - 1).getQuestionnaireAttemptId());
                        }

                        bundle.putInt("QuestionnaireStatusID",lstCohen.get(lstCohen.size() - 1).getQuestionnaireStatusID());
                    }
                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new CohenQuestion());
                    CohenQuestion cohenQuestion=new CohenQuestion();
                    cohenQuestion.setArguments(bundle);
                    ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Cohen",null);
                }

            }
        });

        rlHappyAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearCacheForParticularFragment(new HappinessQuestionFragment());
                if(lstHappiness.size()>0) {
                    if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                        if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                            ((MainActivity)getActivity()).showAlertGergious();
                        }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                        {
                            Bundle bundle=new Bundle();
                            bundle.putInt("QuestionnaireTypeId",3);
                            if(lstHappiness.size()>0) {
                                if (lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID() == 1) {
                                    Log.e("Send haapy attempt--",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireAttemptId()+"?");
                                    bundle.putInt("AttemptId",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireAttemptId());
                                    bundle.putInt("QuestionnaireStatusID",1);
                                }
                                else if(lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID() == 2)
                                {
                                    bundle.putInt("QuestionnaireStatusID",2);
                                }
                                bundle.putInt("QuestionnaireStatusID",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID());
                            }
                            else
                            {
                                bundle.putInt("QuestionnaireStatusID",1);
                            }
                            HappinessQuestionFragment happinessQuestionFragment=new HappinessQuestionFragment();
                            happinessQuestionFragment.setArguments(bundle);
                            ((MainActivity)getActivity()).loadFragment(happinessQuestionFragment,"Happy",null);
                        }else {
                            ((MainActivity)getActivity()).showAlertGergious();
                        }
                    } else {
                        if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                            ((MainActivity)getActivity()).showAlertGergious();
                        } else {
                            Bundle bundle=new Bundle();
                            bundle.putInt("QuestionnaireTypeId",3);
                            if(lstHappiness.size()>0) {
                                if (lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID() == 1) {
                                    Log.e("Send haapy attempt--",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireAttemptId()+"?");
                                    bundle.putInt("AttemptId",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireAttemptId());
                                    bundle.putInt("QuestionnaireStatusID",1);
                                }
                                else if(lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID() == 2)
                                {
                                    bundle.putInt("QuestionnaireStatusID",2);
                                }
                                bundle.putInt("QuestionnaireStatusID",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID());
                            }
                            else
                            {
                                bundle.putInt("QuestionnaireStatusID",1);
                            }
                            HappinessQuestionFragment happinessQuestionFragment=new HappinessQuestionFragment();
                            happinessQuestionFragment.setArguments(bundle);
                            ((MainActivity)getActivity()).loadFragment(happinessQuestionFragment,"Happy",null);
                        }
                    }
                }
                else
                {
                    Bundle bundle=new Bundle();
                    bundle.putInt("QuestionnaireTypeId",3);
                    if(lstHappiness.size()>0) {
                        if (lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID() == 1) {
                            Log.e("Send haapy attempt--",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireAttemptId()+"?");
                            bundle.putInt("AttemptId",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireAttemptId());
                            bundle.putInt("QuestionnaireStatusID",1);
                        }
                        else if(lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID() == 2)
                        {
                            bundle.putInt("QuestionnaireStatusID",2);
                        }
                        bundle.putInt("QuestionnaireStatusID",lstHappiness.get(lstHappiness.size() - 1).getQuestionnaireStatusID());
                    }
                    else
                    {
                        bundle.putInt("QuestionnaireStatusID",1);
                    }
                    HappinessQuestionFragment happinessQuestionFragment=new HappinessQuestionFragment();
                    happinessQuestionFragment.setArguments(bundle);
                    ((MainActivity)getActivity()).loadFragment(happinessQuestionFragment,"Happy",null);
                }


            }
        });




        rlDasAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).clearCacheForParticularFragment(new DasQuestion());
                if(lstDas.size()>0) {
                    if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
                        if (new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && Util.isSevenDayOver(getActivity())) {
                            ((MainActivity)getActivity()).showAlertGergious();
                        }else if(new SharedPreference(getActivity()).read("SEVENDAY_TRIAL_START","").equals("TRUE") && !Util.isSevenDayOver(getActivity()))
                        {
                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new DasQuestion());

                            Bundle bundle=new Bundle();
                            bundle.putInt("QuestionnaireStatusID",1);
                            bundle.putInt("QuestionnaireTypeId",5);
                            if(lstDas.size()>0) {
                                if (lstDas.get(lstDas.size() - 1).getQuestionnaireStatusID() == 1) {
                                    bundle.putInt("AttemptId",lstDas.get(lstDas.size() - 1).getQuestionnaireAttemptId());
                                }
                                bundle.putInt("QuestionnaireStatusID",lstDas.get(lstDas.size() - 1).getQuestionnaireStatusID());
                            }
                            DasQuestion cohenQuestion=new DasQuestion();
                            cohenQuestion.setArguments(bundle);
                            ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Das",null);
                        }else {
                            ((MainActivity)getActivity()).showAlertGergious();
                        }
                    } else {
                        if (new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE")) {
                            ((MainActivity)getActivity()).showAlertGergious();
                        } else {
                            ((MainActivity)getActivity()).clearCacheForParticularFragment(new DasQuestion());
                            Bundle bundle=new Bundle();
                            bundle.putInt("QuestionnaireStatusID",1);
                            bundle.putInt("QuestionnaireTypeId",5);
                            if(lstDas.size()>0) {
                                if (lstDas.get(lstDas.size() - 1).getQuestionnaireStatusID() == 1) {
                                    bundle.putInt("AttemptId",lstDas.get(lstDas.size() - 1).getQuestionnaireAttemptId());
                                }

                                bundle.putInt("QuestionnaireStatusID",lstDas.get(lstDas.size() - 1).getQuestionnaireStatusID());
                            }
                            DasQuestion cohenQuestion=new DasQuestion();
                            cohenQuestion.setArguments(bundle);
                            ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Das",null);
                        }
                    }
                }else
                {
                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new DasQuestion());
                    Bundle bundle=new Bundle();
                    bundle.putInt("QuestionnaireStatusID",1);
                    bundle.putInt("QuestionnaireTypeId",5);
                    if(lstDas.size()>0) {
                        if (lstDas.get(lstDas.size() - 1).getQuestionnaireStatusID() == 1) {
                            bundle.putInt("AttemptId",lstDas.get(lstDas.size() - 1).getQuestionnaireAttemptId());
                        }

                        bundle.putInt("QuestionnaireStatusID",lstDas.get(lstDas.size() - 1).getQuestionnaireStatusID());
                    }
                    DasQuestion cohenQuestion=new DasQuestion();
                    cohenQuestion.setArguments(bundle);
                    ((MainActivity)getActivity()).loadFragment(cohenQuestion,"Das",null);
                }
            }
        });

        ////////////////////////////
    }
    private void funToolBar() {
        //((MainActivity)getActivity()).funDrawer();
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
        if(new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true"))        {            if(Util.isSevenDayOver(getActivity()))            {                imgCircleBack.setVisibility(View.VISIBLE);            }else {                imgCircleBack.setVisibility(View.VISIBLE);            }        }else {            if(new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE"))            {                imgCircleBack.setVisibility(View.VISIBLE);            }else {                imgCircleBack.setVisibility(View.VISIBLE);            }        }
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        /* imgLogo.setOnClickListener(new View.OnClickListener() {             @Override             public void onClick(View v) { 		((MainActivity)getActivity()).showPromotionalDialogs();                 if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {                     ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);                 } else { 		                         ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);                 }             }         });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getArguments()!=null&&getArguments().containsKey("NEWHOME"))
                {
                   // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "HomeFragment", getArguments());
                }
                else {
                    ((MainActivity) getActivity()).loadFragment(new TrackFragment(), "TrackFragment", getArguments());
                }
            }
        });


    }
    private void getQuestionFromApi() {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));


            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<QuestionMain> serverCall = finisherService.getQuestionMain(hashReq);
            serverCall.enqueue(new Callback<QuestionMain>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<QuestionMain> call, Response<QuestionMain> response) {
                    progressDialog.dismiss();

                    Log.e("success", "su");
                    if (response.body() != null)
                    {
                        if(response.body().getSuccessFlag())
                        {
                            llMainBlock.setVisibility(View.VISIBLE);
                            lstData=response.body().getDetails();
                            filterData(lstData);
                        }
                        else
                        {
                            Util.showDialog(getActivity(),"Alert","An error occurred",false);
                        }

                    }

                }

                @Override
                public void onFailure(Call<QuestionMain> call, Throwable t) {

                    Log.e("error", "er");
                    progressDialog.dismiss();


                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void filterData(List<QuestionMain.Detail> lstData) {
        for(int x=0;x<lstData.size();x++)
        {
            if(lstData.get(x).getQuestionnaireTypeId()==1)
            {

                lstCohen.add(lstData.get(x));
                Log.e("Cohen insert--",x+"---"+lstData.get(x).getTotal()+"???"+lstData.get(x).getQuestionnaireAttemptId());
            }
            if(lstData.get(x).getQuestionnaireTypeId()==2)
            {
                lstRahe.add(lstData.get(x));
            }
            if(lstData.get(x).getQuestionnaireTypeId()==3)
            {
                //Log.e("Happiness inset","1");
                lstHappiness.add(lstData.get(x));
            }
            if(lstData.get(x).getQuestionnaireTypeId()==5)
            {
                //Log.e("Happiness inset","1");
                lstDas.add(lstData.get(x));
            }
        }

        Collections.sort(lstDas, new Comparator<QuestionMain.Detail>() {
            @Override
            public int compare(QuestionMain.Detail o1, QuestionMain.Detail o2) {
                return o1.getQuestionnaireAttemptId().compareTo(o2.getQuestionnaireAttemptId());
            }
        });

        Collections.sort(lstCohen, new Comparator<QuestionMain.Detail>() {
            @Override
            public int compare(QuestionMain.Detail o1, QuestionMain.Detail o2) {
                return o1.getQuestionnaireAttemptId().compareTo(o2.getQuestionnaireAttemptId());
            }
        });
        Collections.sort(lstRahe, new Comparator<QuestionMain.Detail>() {
            @Override
            public int compare(QuestionMain.Detail o1, QuestionMain.Detail o2) {
                return o1.getQuestionnaireAttemptId().compareTo(o2.getQuestionnaireAttemptId());
            }
        });
        Collections.sort(lstHappiness, new Comparator<QuestionMain.Detail>() {
            @Override
            public int compare(QuestionMain.Detail o1, QuestionMain.Detail o2) {
                return o1.getQuestionnaireAttemptId().compareTo(o2.getQuestionnaireAttemptId());
            }
        });

        ////////////////1/////////

        Log.e("Cohen size--",lstCohen.size()+"???");

        if(lstCohen.size()>0)

        {
            llStressBefore.setVisibility(View.VISIBLE);
            llStressAfter.setVisibility(View.VISIBLE);
            imgMIconT.setImageResource(R.drawable.mbhq_eye);
            txtMIconT.setTextColor(Color.parseColor("#00AE29"));
            txtMIconT.setText("SEE RESULT");
            if(lstCohen.get(lstCohen.size()-1).getQuestionnaireStatusID()==1)
            {

                imgEIconT.setImageResource(R.drawable.progresshdpi);
                txtEIconT.setText("IN PROGRESS");

            }
            else if(lstCohen.get(lstCohen.size()-1).getQuestionnaireStatusID()==2)
            {

                imgEIconT.setImageResource(0);
                imgEIconT.setImageResource(R.drawable.ic_add_green);

            }

            int chohenBeforeScore= lstCohen.get(0).getTotal();
            int chohenNowScore= lstCohen.get(lstCohen.size()-1).getTotal();
            cohenNowAttemptId=lstCohen.get(lstCohen.size()-1).getQuestionnaireAttemptId();
            cohenNowTypeId=lstCohen.get(lstCohen.size()-1).getQuestionnaireTypeId();
            chohenBeforeScore*=4;
            chohenNowScore*=4;
            txtCohenBefore.setText(chohenBeforeScore+"");
            txtCohenNow.setText(chohenNowScore+"");

            if(chohenNowScore<chohenBeforeScore)
            {
                rlTypeNowStress.setBackground(null);
                // rlTypeNowStress.setBackground(getActivity().getResources().getDrawable(R.drawable.mbhq_smiley));
            }
            if(lstCohen.size()==1)
            {
                llStressBefore.setVisibility(View.GONE);
            }
        }
        else
        {
            imgMIconT.setImageResource(0);
            txtMIconT.setText("");
            imgEIconT.setImageResource(0);
            imgEIconT.setImageResource(R.drawable.ic_add_green);
            llStressBefore.setVisibility(View.GONE);
            llStressAfter.setVisibility(View.GONE);
            // imgStressAdd.setVisibility(View.VISIBLE);

        }
        //////////2/////////////
        if(lstRahe.size()>0)
        {
            imgMIconM.setImageResource(R.drawable.mbhq_eye);
            txtMIconM.setTextColor(Color.parseColor("#00AE29"));
            txtMIconM.setText("SEE RESULT");
            if(lstRahe.get(lstRahe.size()-1).getQuestionnaireStatusID()==1)
            {

                imgEIconM.setImageResource(R.drawable.progresshdpi);
                txtEIconM.setTextColor(Color.parseColor("#A3D4E1"));
                txtEIconM.setText("IN PROGRESS");

            }
            else if(lstRahe.get(lstRahe.size()-1).getQuestionnaireStatusID()==2)
            {

                imgEIconM.setImageResource(0);
                imgEIconM.setImageResource(R.drawable.ic_add_green);
                txtEIconM.setTextColor(Color.parseColor("#FF00A5"));
            }
        }
        else
        {
            imgMIconM.setImageResource(0);
            txtMIconM.setText("");
            imgEIconM.setImageResource(0);
            imgEIconM.setImageResource(R.drawable.ic_add_green);
            txtEIconM.setTextColor(Color.parseColor("#FF00A5"));
        }
        ///////////////3/////////////
        Log.e("happiness size--",lstHappiness.size()+"??");

        if(lstHappiness.size()>0)
        {
            llHappyBefore.setVisibility(View.VISIBLE);
            llHappyAfter.setVisibility(View.VISIBLE);
            imgMIconE.setImageResource(R.drawable.mbhq_eye);
            txtMIconE.setTextColor(Color.parseColor("#00AE29"));
            txtMIconE.setText("SEE RESULT");
            if(lstHappiness.get(lstHappiness.size()-1).getQuestionnaireStatusID()==1)
            {

                imgEIconE.setImageResource(R.drawable.progresshdpi);
                txtEIconE.setText("IN PROGRESS");
            }
            else if(lstHappiness.get(lstHappiness.size()-1).getQuestionnaireStatusID()==2)
            {

                imgEIconE.setImageResource(0);
                imgEIconE.setImageResource(R.drawable.ic_add_green);

            }
            int happinessBeforeScore= lstHappiness.get(0).getTotal();
            int happinessNowScore= lstHappiness.get(lstHappiness.size()-1).getTotal();
            happinessNowAttemptId=lstHappiness.get(lstHappiness.size()-1).getQuestionnaireAttemptId();
            happinessNowTypeId=lstHappiness.get(lstHappiness.size()-1).getQuestionnaireTypeId();

            double weight=happinessBeforeScore/120.00;
            double percentD=weight*100;
            txtHappyBefore.setText(Math.round(percentD)+"");
            ////////////////
            double weightNow=happinessNowScore/120.00;
            double percentDNow=weightNow*100;
            txtHappyNow.setText(Math.round(percentDNow)+"");
            if(happinessNowScore>happinessBeforeScore)
            {
                rlTypeNowHappy.setBackground(null);
                // rlTypeNowHappy.setBackground(getActivity().getResources().getDrawable(R.drawable.mbhq_smiley));
            }
            if(lstHappiness.size()==1)
            {
                llHappyBefore.setVisibility(View.GONE);
            }
        }
        else
        {
            imgMIconE.setImageResource(0);
            txtMIconE.setText("");
            imgEIconE.setImageResource(0);
            imgEIconE.setImageResource(R.drawable.ic_add_green);

            llHappyAfter.setVisibility(View.GONE);
            llHappyBefore.setVisibility(View.GONE);
            // imgHappyAdd.setVisibility(View.VISIBLE);
        }



        //----------------------------------------------------------//


        if(lstDas.size()>0)
        {
            rlResultDas.setVisibility(View.VISIBLE);
            llDas_below.setVisibility(View.VISIBLE);
            llDas.setVisibility(View.VISIBLE);
            view_das.setVisibility(View.GONE);

//            llHappyBefore.setVisibility(View.VISIBLE);
//            llHappyAfter.setVisibility(View.VISIBLE);
            imgMIconD.setImageResource(R.drawable.mbhq_eye);
            txtMIconD.setTextColor(Color.parseColor("#00AE29"));
            txtMIconD.setText("SEE RESULT");
            if(lstDas.get(lstDas.size()-1).getQuestionnaireStatusID()==1)
            {

                imgEIconD.setImageResource(R.drawable.progresshdpi);
                txtEIconD.setText("IN PROGRESS");

            }
            else if(lstDas.get(lstDas.size()-1).getQuestionnaireStatusID()==2)
            {

                imgEIconD.setImageResource(0);
                imgEIconD.setImageResource(R.drawable.ic_add_green);

            }

            int happinessBeforeScore_D= lstDas.get(0).getDAS21Depression();
            int happinessBeforeScore_A= lstDas.get(0).getDAS21Anxiety();
            int happinessBeforeScore_S= lstDas.get(0).getDAS21Stress();
            int happinessNowScore_D= lstDas.get(lstDas.size()-1).getDAS21Depression();
            int happinessNowScore_A= lstDas.get(lstDas.size()-1).getDAS21Anxiety();
            int happinessNowScore_S= lstDas.get(lstDas.size()-1).getDAS21Stress();

            txt_b_d.setText(String.valueOf(happinessBeforeScore_D));
            txt_b_a.setText(String.valueOf(happinessBeforeScore_A));
            txt_b_s.setText(String.valueOf(happinessBeforeScore_S));

            txt_a_d.setText(String.valueOf(happinessNowScore_D));
            txt_a_a.setText(String.valueOf(happinessNowScore_A));
            txt_a_s.setText(String.valueOf(happinessNowScore_S));

            //            happinessNowAttemptId=lstHappiness.get(lstHappiness.size()-1).getQuestionnaireAttemptId();

            //          happinessNowTypeId=lstHappiness.get(lstHappiness.size()-1).getQuestionnaireTypeId();

           /* double weight=happinessBeforeScore/120.00;
            double percentD=weight*100;
            txtHappyBefore.setText(Math.round(percentD)+"");
            ////////////////
            double weightNow=happinessNowScore/120.00;
            double percentDNow=weightNow*100;
            txtHappyNow.setText(Math.round(percentDNow)+"");
            if(happinessNowScore>happinessBeforeScore);*/
           /* {
                rlTypeNowHappy.setBackground(null);
                rlTypeNowHappy.setBackground(getActivity().getResources().getDrawable(R.drawable.mbhq_smiley));
            }*/
           /* if(lstHappiness.size()==1)
            {
                llHappyBefore.setVisibility(View.GONE);
            }*/
        } else
        {
            rlResultDas.setVisibility(View.GONE);
            llDas_below.setVisibility(View.GONE);
            llDas.setVisibility(View.GONE);
            view_das.setVisibility(View.GONE);
            imgMIconD.setImageResource(0);
            txtMIconD.setText("");
            imgEIconD.setImageResource(0);
            imgEIconD.setImageResource(R.drawable.ic_add_green);

            /*llHappyAfter.setVisibility(View.GONE);
            llHappyBefore.setVisibility(View.GONE);
            imgHappyAdd.setVisibility(View.VISIBLE);*/
        }


    }
}
