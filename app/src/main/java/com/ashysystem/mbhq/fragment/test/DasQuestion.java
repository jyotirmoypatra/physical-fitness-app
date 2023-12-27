package com.ashysystem.mbhq.fragment.test;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.CreateAttemptModel;
import com.ashysystem.mbhq.model.GetCohenModel;

import com.ashysystem.mbhq.model.GetUserQuestionnaireAttemptListModel;
import com.ashysystem.mbhq.model.TempModelCohenSave_;
import com.ashysystem.mbhq.model.TmpModelQouestionForRequest;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DasQuestion extends Fragment {

    private NestedScrollView nestedScroll;
    private RelativeLayout rlNever1,rlNever2,rlNever3,rlNever4,rlNever5,rlNever6,rlNever7,rlNever8,rlNever9,rlNever10,
            rlNever11,rlNever12,rlNever13,rlNever14,rlNever15,rlNever16,rlNever17,rlNever18,rlNever19,rlNever20,rlNever21,

            rlANever1,rlANever2,rlANever3,rlANever4,rlANever5,rlANever6,rlANever7,rlANever8,rlANever9,rlANever10,

            rlSomeTimes1,rlSomeTimes2,rlSomeTimes3,rlSomeTimes4,rlSomeTimes5,rlSomeTimes6,rlSomeTimes7,rlSomeTimes8,rlSomeTimes9,rlSomeTimes10,
            rlSomeTimes11,rlSomeTimes12,rlSomeTimes13,rlSomeTimes14,rlSomeTimes15,rlSomeTimes16,rlSomeTimes17,rlSomeTimes18,rlSomeTimes19,rlSomeTimes20,rlSomeTimes21,
            rlOften1,rlOften2,rlOften3,rlOften4,rlOften5,rlOften6,rlOften7,rlOften8,rlOften9,rlOften10,
            rlOften11,rlOften12,rlOften13,rlOften14,rlOften15,rlOften16,rlOften17,rlOften18,rlOften19,rlOften20,rlOften21,
            rlVoften1,rlVoften2,rlVoften3,rlVoften4,rlVoften5,rlVoften6,rlVoften7,rlVoften8,rlVoften9,rlVoften10,
                    rlVoften11,rlVoften12,rlVoften13,rlVoften14,rlVoften15,rlVoften16,rlVoften17,rlVoften18,rlVoften19,rlVoften20,rlVoften21;


    private TextView txtScore,txtNever1,txtNever2,txtNever3,txtNever4,txtNever5,txtNever6,txtNever7,txtNever8,txtNever9, txtNever10,
            txtNever11,txtNever12,txtNever13,txtNever14,txtNever15,txtNever16,txtNever17,txtNever18,txtNever19, txtNever20,txtNever21,

    txtANever1,txtANever2,txtANever3,txtANever4,txtANever5,txtANever6,txtANever7,txtANever8,txtANever9,txtANever10,

            txtSomeTimes1,txtSomeTimes2,txtSomeTimes3,txtSomeTimes4,txtSomeTimes5,txtSomeTimes6,txtSomeTimes7,txtSomeTimes8,txtSomeTimes9,txtSomeTimes10,
            txtSomeTimes11,txtSomeTimes12,txtSomeTimes13,txtSomeTimes14,txtSomeTimes15,txtSomeTimes16,txtSomeTimes17,txtSomeTimes18,txtSomeTimes19,txtSomeTimes20,txtSomeTimes21,
    txtOften1,txtOften2,txtOften3,txtOften4,txtOften5,txtOften6,txtOften7,txtOften8,txtOften9,txtOften10,
                    txtOften11,txtOften12,txtOften13,txtOften14,txtOften15,txtOften16,txtOften17,txtOften18,txtOften19,txtOften20,txtOften21,
    txtVoften1,txtVoften2,txtVoften3,txtVoften4,txtVoften5,txtVoften6,txtVoften7,txtVoften8,txtVoften9,txtVoften10,
                    txtVoften11,txtVoften12,txtVoften13,txtVoften14,txtVoften15,txtVoften16,txtVoften17,txtVoften18,txtVoften19,txtVoften20,txtVoften21;
    int sum=0;
    int val1=0,val2=0,val3=0,val4=0,val5=0,val6=0,val7=0,val8=0,val9=0,val10=0,
            val11=0,val12=0,val13=0,val14=0,val15=0,val16=0,val17=0,val18=0,val19=0,val20=0,val21=0;
    private boolean bool1=false,bool2=false,bool3=false,bool4=false,bool5=false,bool6=false,bool7=false,bool8=false,bool9=false,bool10=false,
            bool11=false,bool12=false,bool13=false,bool14=false,bool15=false,bool16=false,bool17=false,bool18=false,bool19=false,bool20=false,bool21=false;
    private Integer attemptId=-1;
    private int questionnaireStatusID=0;
    private int questionnaireTypeId=0;
    private RelativeLayout rlSave;
    private int getAttemptId=-1;
    private RelativeLayout rlBack;
    private List<GetUserQuestionnaireAttemptListModel.Detail> lstData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.layout_das_strees,container,false);
       funToolBar();
       initView(view);
       getDataFromPrevious();

       if(getAttemptId!=-1)
       {
           Log.i("attemptId","1");

           attemptId=getAttemptId;
           Log.i("attemptId",String.valueOf(attemptId));
           getResultFromApi();
       }
       else
       {
           if(attemptId==-1)
               Log.i("attemptId","2");
               Log.i("attemptId",String.valueOf(attemptId));
           attemptId=0;
              // addUpdateAttempt();
       }
       return view;
    }

    private void getDataFromPrevious() {
        if(getArguments()!=null)
        {
            if(getArguments().containsKey("QuestionnaireStatusID"))
            {

                questionnaireStatusID=getArguments().getInt("QuestionnaireStatusID");
            }
            if(getArguments().containsKey("QuestionnaireTypeId"))
            {
                questionnaireTypeId=getArguments().getInt("QuestionnaireTypeId");
            }
            if(getArguments().containsKey("AttemptId"))
            {
                attemptId=getArguments().getInt("AttemptId");
            }
            if(getArguments().containsKey("GetAttemptId"))
            {
                getAttemptId=getArguments().getInt("GetAttemptId");
            }
        }
    }

    private void initView(View view) {
        rlBack=view.findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).funDrawer1();

                ((MainActivity) getActivity()).loadFragment(new QuestionariesFragment(), "Questionaries", getArguments());
            }
        });
        rlSave=(RelativeLayout)view.findViewById(R.id.rlSave);
        nestedScroll=(NestedScrollView)view.findViewById(R.id.nestedScroll);
        nestedScroll.setNestedScrollingEnabled(false);
        rlNever1=(RelativeLayout)view.findViewById(R.id.rlNever1);
        rlNever2=(RelativeLayout)view.findViewById(R.id.rlNever2);
        rlNever3=(RelativeLayout)view.findViewById(R.id.rlNever3);
        rlNever4=(RelativeLayout)view.findViewById(R.id.rlNever4);
        rlNever5=(RelativeLayout)view.findViewById(R.id.rlNever5);
        rlNever6=(RelativeLayout)view.findViewById(R.id.rlNever6);
        rlNever7=(RelativeLayout)view.findViewById(R.id.rlNever7);
        rlNever8=(RelativeLayout)view.findViewById(R.id.rlNever8);
        rlNever9=(RelativeLayout)view.findViewById(R.id.rlNever9);
        rlNever10=(RelativeLayout)view.findViewById(R.id.rlNever10);

        rlNever11=(RelativeLayout)view.findViewById(R.id.rlNever11);
        rlNever12=(RelativeLayout)view.findViewById(R.id.rlNever12);
        rlNever13=(RelativeLayout)view.findViewById(R.id.rlNever13);
        rlNever14=(RelativeLayout)view.findViewById(R.id.rlNever14);
        rlNever15=(RelativeLayout)view.findViewById(R.id.rlNever15);
        rlNever16=(RelativeLayout)view.findViewById(R.id.rlNever16);
        rlNever17=(RelativeLayout)view.findViewById(R.id.rlNever17);
        rlNever18=(RelativeLayout)view.findViewById(R.id.rlNever18);
        rlNever19=(RelativeLayout)view.findViewById(R.id.rlNever19);
        rlNever20=(RelativeLayout)view.findViewById(R.id.rlNever20);
        rlNever21=(RelativeLayout)view.findViewById(R.id.rlNever21);
        ////////////////////////////
        rlANever1=(RelativeLayout)view.findViewById(R.id.rlANever1);
        rlANever2=(RelativeLayout)view.findViewById(R.id.rlANever2);
        rlANever3=(RelativeLayout)view.findViewById(R.id.rlANever3);
        rlANever4=(RelativeLayout)view.findViewById(R.id.rlANever4);
        rlANever5=(RelativeLayout)view.findViewById(R.id.rlANever5);
        rlANever6=(RelativeLayout)view.findViewById(R.id.rlANever6);
        rlANever7=(RelativeLayout)view.findViewById(R.id.rlANever7);
        rlANever8=(RelativeLayout)view.findViewById(R.id.rlANever8);
        rlANever9=(RelativeLayout)view.findViewById(R.id.rlANever9);
        rlANever10=(RelativeLayout)view.findViewById(R.id.rlANever10);
        /////////////////
        rlSomeTimes1=(RelativeLayout)view.findViewById(R.id.rlSomeTimes1);
        rlSomeTimes2=(RelativeLayout)view.findViewById(R.id.rlSomeTimes2);
        rlSomeTimes3=(RelativeLayout)view.findViewById(R.id.rlSomeTimes3);
        rlSomeTimes4=(RelativeLayout)view.findViewById(R.id.rlSomeTimes4);
        rlSomeTimes5=(RelativeLayout)view.findViewById(R.id.rlSomeTimes5);
        rlSomeTimes6=(RelativeLayout)view.findViewById(R.id.rlSomeTimes6);
        rlSomeTimes7=(RelativeLayout)view.findViewById(R.id.rlSomeTimes7);
        rlSomeTimes8=(RelativeLayout)view.findViewById(R.id.rlSomeTimes8);
        rlSomeTimes9=(RelativeLayout)view.findViewById(R.id.rlSomeTimes9);
        rlSomeTimes10=(RelativeLayout)view.findViewById(R.id.rlSomeTimes10);

        rlSomeTimes11=(RelativeLayout)view.findViewById(R.id.rlSomeTimes11);
        rlSomeTimes12=(RelativeLayout)view.findViewById(R.id.rlSomeTimes12);
        rlSomeTimes13=(RelativeLayout)view.findViewById(R.id.rlSomeTimes13);
        rlSomeTimes14=(RelativeLayout)view.findViewById(R.id.rlSomeTimes14);
        rlSomeTimes15=(RelativeLayout)view.findViewById(R.id.rlSomeTimes15);
        rlSomeTimes16=(RelativeLayout)view.findViewById(R.id.rlSomeTimes16);
        rlSomeTimes17=(RelativeLayout)view.findViewById(R.id.rlSomeTimes17);
        rlSomeTimes18=(RelativeLayout)view.findViewById(R.id.rlSomeTimes18);
        rlSomeTimes19=(RelativeLayout)view.findViewById(R.id.rlSomeTimes19);
        rlSomeTimes20=(RelativeLayout)view.findViewById(R.id.rlSomeTimes20);
        rlSomeTimes21=(RelativeLayout)view.findViewById(R.id.rlSomeTimes21);

        /////////////////
        rlOften1=(RelativeLayout)view.findViewById(R.id.rlOften1);
        rlOften2=(RelativeLayout)view.findViewById(R.id.rlOften2);
        rlOften3=(RelativeLayout)view.findViewById(R.id.rlOften3);
        rlOften4=(RelativeLayout)view.findViewById(R.id.rlOften4);
        rlOften5=(RelativeLayout)view.findViewById(R.id.rlOften5);
        rlOften6=(RelativeLayout)view.findViewById(R.id.rlOften6);
        rlOften7=(RelativeLayout)view.findViewById(R.id.rlOften7);
        rlOften8=(RelativeLayout)view.findViewById(R.id.rlOften8);
        rlOften9=(RelativeLayout)view.findViewById(R.id.rlOften9);
        rlOften10=(RelativeLayout)view.findViewById(R.id.rlOften10);

        rlOften11=(RelativeLayout)view.findViewById(R.id.rlOften11);
        rlOften12=(RelativeLayout)view.findViewById(R.id.rlOften12);
        rlOften13=(RelativeLayout)view.findViewById(R.id.rlOften13);
        rlOften14=(RelativeLayout)view.findViewById(R.id.rlOften14);
        rlOften15=(RelativeLayout)view.findViewById(R.id.rlOften15);
        rlOften16=(RelativeLayout)view.findViewById(R.id.rlOften16);
        rlOften17=(RelativeLayout)view.findViewById(R.id.rlOften17);
        rlOften18=(RelativeLayout)view.findViewById(R.id.rlOften18);
        rlOften19=(RelativeLayout)view.findViewById(R.id.rlOften19);
        rlOften20=(RelativeLayout)view.findViewById(R.id.rlOften20);
        rlOften21=(RelativeLayout)view.findViewById(R.id.rlOften21);

        ////////////////////////////////////////////////
        rlVoften1=(RelativeLayout)view.findViewById(R.id.rlVoften1);
        rlVoften2=(RelativeLayout)view.findViewById(R.id.rlVoften2);
        rlVoften3=(RelativeLayout)view.findViewById(R.id.rlVoften3);
        rlVoften4=(RelativeLayout)view.findViewById(R.id.rlVoften4);
        rlVoften5=(RelativeLayout)view.findViewById(R.id.rlVoften5);
        rlVoften6=(RelativeLayout)view.findViewById(R.id.rlVoften6);
        rlVoften7=(RelativeLayout)view.findViewById(R.id.rlVoften7);
        rlVoften8=(RelativeLayout)view.findViewById(R.id.rlVoften8);
        rlVoften9=(RelativeLayout)view.findViewById(R.id.rlVoften9);
        rlVoften10=(RelativeLayout)view.findViewById(R.id.rlVoften10);

        rlVoften11=(RelativeLayout)view.findViewById(R.id.rlVoften11);
        rlVoften12=(RelativeLayout)view.findViewById(R.id.rlVoften12);
        rlVoften13=(RelativeLayout)view.findViewById(R.id.rlVoften13);
        rlVoften14=(RelativeLayout)view.findViewById(R.id.rlVoften14);
        rlVoften15=(RelativeLayout)view.findViewById(R.id.rlVoften15);
        rlVoften16=(RelativeLayout)view.findViewById(R.id.rlVoften16);
        rlVoften17=(RelativeLayout)view.findViewById(R.id.rlVoften17);
        rlVoften18=(RelativeLayout)view.findViewById(R.id.rlVoften18);
        rlVoften19=(RelativeLayout)view.findViewById(R.id.rlVoften19);
        rlVoften20=(RelativeLayout)view.findViewById(R.id.rlVoften20);
        rlVoften21=(RelativeLayout)view.findViewById(R.id.rlVoften21);

        ////////////////////////////
        txtScore=(TextView)view.findViewById(R.id.txtScore);
        txtNever1=(TextView)view.findViewById(R.id.txtNever1);
        txtNever2=(TextView)view.findViewById(R.id.txtNever2);
        txtNever3=(TextView)view.findViewById(R.id.txtNever3);
        txtNever4=(TextView)view.findViewById(R.id.txtNever4);
        txtNever5=(TextView)view.findViewById(R.id.txtNever5);
        txtNever6=(TextView)view.findViewById(R.id.txtNever6);
        txtNever7=(TextView)view.findViewById(R.id.txtNever7);
        txtNever8=(TextView)view.findViewById(R.id.txtNever8);
        txtNever9=(TextView)view.findViewById(R.id.txtNever9);
        txtNever10=(TextView)view.findViewById(R.id.txtNever10);

        txtNever11=(TextView)view.findViewById(R.id.txtNever11);
        txtNever12=(TextView)view.findViewById(R.id.txtNever12);
        txtNever13=(TextView)view.findViewById(R.id.txtNever13);
        txtNever14=(TextView)view.findViewById(R.id.txtNever14);
        txtNever15=(TextView)view.findViewById(R.id.txtNever15);
        txtNever16=(TextView)view.findViewById(R.id.txtNever16);
        txtNever17=(TextView)view.findViewById(R.id.txtNever17);
        txtNever18=(TextView)view.findViewById(R.id.txtNever18);
        txtNever19=(TextView)view.findViewById(R.id.txtNever19);
        txtNever20=(TextView)view.findViewById(R.id.txtNever20);
        txtNever21=(TextView)view.findViewById(R.id.txtNever21);

        //////////////
        txtANever1=(TextView)view.findViewById(R.id.txtANever1);
        txtANever2=(TextView)view.findViewById(R.id.txtANever2);
        txtANever3=(TextView)view.findViewById(R.id.txtANever3);
        txtANever4=(TextView)view.findViewById(R.id.txtANever4);
        txtANever5=(TextView)view.findViewById(R.id.txtANever5);
        txtANever6=(TextView)view.findViewById(R.id.txtANever6);
        txtANever7=(TextView)view.findViewById(R.id.txtANever7);
        txtANever8=(TextView)view.findViewById(R.id.txtANever8);
        txtANever9=(TextView)view.findViewById(R.id.txtANever9);
        txtANever10=(TextView)view.findViewById(R.id.txtANever10);
        ////////////////////
        txtSomeTimes1=(TextView)view.findViewById(R.id.txtSomeTimes1);
        txtSomeTimes2=(TextView)view.findViewById(R.id.txtSomeTimes2);
        txtSomeTimes3=(TextView)view.findViewById(R.id.txtSomeTimes3);
        txtSomeTimes4=(TextView)view.findViewById(R.id.txtSomeTimes4);
        txtSomeTimes5=(TextView)view.findViewById(R.id.txtSomeTimes5);
        txtSomeTimes6=(TextView)view.findViewById(R.id.txtSomeTimes6);
        txtSomeTimes7=(TextView)view.findViewById(R.id.txtSomeTimes7);
        txtSomeTimes8=(TextView)view.findViewById(R.id.txtSomeTimes8);
        txtSomeTimes9=(TextView)view.findViewById(R.id.txtSomeTimes9);
        txtSomeTimes10=(TextView)view.findViewById(R.id.txtSomeTimes10);

        txtSomeTimes11=(TextView)view.findViewById(R.id.txtSomeTimes11);
        txtSomeTimes12=(TextView)view.findViewById(R.id.txtSomeTimes12);
        txtSomeTimes13=(TextView)view.findViewById(R.id.txtSomeTimes13);
        txtSomeTimes14=(TextView)view.findViewById(R.id.txtSomeTimes14);
        txtSomeTimes15=(TextView)view.findViewById(R.id.txtSomeTimes15);
        txtSomeTimes16=(TextView)view.findViewById(R.id.txtSomeTimes16);
        txtSomeTimes17=(TextView)view.findViewById(R.id.txtSomeTimes17);
        txtSomeTimes18=(TextView)view.findViewById(R.id.txtSomeTimes18);
        txtSomeTimes19=(TextView)view.findViewById(R.id.txtSomeTimes19);
        txtSomeTimes20=(TextView)view.findViewById(R.id.txtSomeTimes20);
        txtSomeTimes21=(TextView)view.findViewById(R.id.txtSomeTimes21);

        ////////////////
        txtOften1=(TextView)view.findViewById(R.id.txtOften1);
        txtOften2=(TextView)view.findViewById(R.id.txtOften2);
        txtOften3=(TextView)view.findViewById(R.id.txtOften3);
        txtOften4=(TextView)view.findViewById(R.id.txtOften4);
        txtOften5=(TextView)view.findViewById(R.id.txtOften5);
        txtOften6=(TextView)view.findViewById(R.id.txtOften6);
        txtOften7=(TextView)view.findViewById(R.id.txtOften7);
        txtOften8=(TextView)view.findViewById(R.id.txtOften8);
        txtOften9=(TextView)view.findViewById(R.id.txtOften9);
        txtOften10=(TextView)view.findViewById(R.id.txtOften10);

        txtOften11=(TextView)view.findViewById(R.id.txtOften11);
        txtOften12=(TextView)view.findViewById(R.id.txtOften12);
        txtOften13=(TextView)view.findViewById(R.id.txtOften13);
        txtOften14=(TextView)view.findViewById(R.id.txtOften14);
        txtOften15=(TextView)view.findViewById(R.id.txtOften15);
        txtOften16=(TextView)view.findViewById(R.id.txtOften16);
        txtOften17=(TextView)view.findViewById(R.id.txtOften17);
        txtOften18=(TextView)view.findViewById(R.id.txtOften18);
        txtOften19=(TextView)view.findViewById(R.id.txtOften19);
        txtOften20=(TextView)view.findViewById(R.id.txtOften20);
        txtOften21=(TextView)view.findViewById(R.id.txtOften21);

        /////////////////////
        txtVoften1=(TextView)view.findViewById(R.id.txtVoften1);
        txtVoften2=(TextView)view.findViewById(R.id.txtVoften2);
        txtVoften3=(TextView)view.findViewById(R.id.txtVoften3);
        txtVoften4=(TextView)view.findViewById(R.id.txtVoften4);
        txtVoften5=(TextView)view.findViewById(R.id.txtVoften5);
        txtVoften6=(TextView)view.findViewById(R.id.txtVoften6);
        txtVoften7=(TextView)view.findViewById(R.id.txtVoften7);
        txtVoften8=(TextView)view.findViewById(R.id.txtVoften8);
        txtVoften9=(TextView)view.findViewById(R.id.txtVoften9);
        txtVoften10=(TextView)view.findViewById(R.id.txtVoften10);

        txtVoften11=(TextView)view.findViewById(R.id.txtVoften11);
        txtVoften12=(TextView)view.findViewById(R.id.txtVoften12);
        txtVoften13=(TextView)view.findViewById(R.id.txtVoften13);
        txtVoften14=(TextView)view.findViewById(R.id.txtVoften14);
        txtVoften15=(TextView)view.findViewById(R.id.txtVoften15);
        txtVoften16=(TextView)view.findViewById(R.id.txtVoften16);
        txtVoften17=(TextView)view.findViewById(R.id.txtVoften17);
        txtVoften18=(TextView)view.findViewById(R.id.txtVoften18);
        txtVoften19=(TextView)view.findViewById(R.id.txtVoften19);
        txtVoften20=(TextView)view.findViewById(R.id.txtVoften20);
        txtVoften21=(TextView)view.findViewById(R.id.txtVoften21);

        ///////////////////
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bool1&&bool2&&bool3&&bool4&&bool5&&bool6&bool7&bool8&bool9&bool10&
                        bool11&&bool12&&bool13&&bool14&&bool15&&bool16&bool17&bool18&bool19&bool20&bool21)

                   funMakeData();
                else
                {
                    Util.showDialog(getActivity(),"Alert","Please Answer All Question",false);
                }
            }
        });
        ///////////////////
        rlNever1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val1=0;
                bool1=true;
                rlNever1.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever1.setTextColor(Color.parseColor("#FFFFFF"));


                rlANever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever1.setTextColor(Color.parseColor("#32cdb8"));
                rlANever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever1.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes1.setTextColor(Color.parseColor("#32cdb8"));
                rlOften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften1.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften1.setTextColor(Color.parseColor("#32cdb8"));

            }
        });
        ///////////////////////
/*
        rlANever1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val1=1;
                bool1=true;

                rlANever1.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever1.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever1.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes1.setTextColor(Color.parseColor("#32cdb8"));
                rlOften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften1.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften1.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool1=true;
                val1=1;

                rlSomeTimes1.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes1.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever1.setTextColor(Color.parseColor("#32cdb8"));
                rlANever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever1.setTextColor(Color.parseColor("#32cdb8"));
                rlOften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften1.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften1.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool1=true;
                val1=2;

                rlOften1.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften1.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever1.setTextColor(Color.parseColor("#32cdb8"));
                rlANever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever1.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes1.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften1.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool1=true;
                val1=3;

                rlVoften1.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften1.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever1.setTextColor(Color.parseColor("#32cdb8"));
                rlANever1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever1.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes1.setTextColor(Color.parseColor("#32cdb8"));
                rlOften1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften1.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        ////////////////
        rlNever2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=0;
                rlNever2.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever2.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever2.setTextColor(Color.parseColor("#32cdb8"));
                rlANever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever2.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes2.setTextColor(Color.parseColor("#32cdb8"));
                rlOften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften2.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften2.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=1;

                rlANever2.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever2.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever2.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes2.setTextColor(Color.parseColor("#32cdb8"));
                rlOften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften2.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften2.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=1;

                rlSomeTimes2.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes2.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever2.setTextColor(Color.parseColor("#32cdb8"));
                rlANever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever2.setTextColor(Color.parseColor("#32cdb8"));
                rlOften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften2.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften2.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=2;

                rlOften2.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften2.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever2.setTextColor(Color.parseColor("#32cdb8"));
                rlANever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever2.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes2.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften2.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=3;

                rlVoften2.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften2.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever2.setTextColor(Color.parseColor("#32cdb8"));
                rlANever2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever2.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes2.setTextColor(Color.parseColor("#32cdb8"));
                rlOften2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften2.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////////

        rlNever3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=0;

                rlNever3.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever3.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever3.setTextColor(Color.parseColor("#32cdb8"));
                rlANever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever3.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes3.setTextColor(Color.parseColor("#32cdb8"));
                rlOften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften3.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften3.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=1;

                rlANever3.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever3.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever3.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes3.setTextColor(Color.parseColor("#32cdb8"));
                rlOften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften3.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften3.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=1;

                rlSomeTimes3.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes3.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever3.setTextColor(Color.parseColor("#32cdb8"));
                rlANever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever3.setTextColor(Color.parseColor("#32cdb8"));
                rlOften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften3.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften3.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=2;

                rlOften3.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften3.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever3.setTextColor(Color.parseColor("#32cdb8"));
                rlANever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever3.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes3.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften3.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=3;

                rlVoften3.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften3.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever3.setTextColor(Color.parseColor("#32cdb8"));
                rlANever3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever3.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes3.setTextColor(Color.parseColor("#32cdb8"));
                rlOften3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften3.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////////////

        rlNever4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=0;

                rlNever4.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever4.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever4.setTextColor(Color.parseColor("#32cdb8"));
                rlANever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever4.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes4.setTextColor(Color.parseColor("#32cdb8"));
                rlOften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften4.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften4.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=1;

                rlANever4.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever4.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever4.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes4.setTextColor(Color.parseColor("#32cdb8"));
                rlOften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften4.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften4.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=1;

                rlSomeTimes4.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes4.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever4.setTextColor(Color.parseColor("#32cdb8"));
                rlANever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever4.setTextColor(Color.parseColor("#32cdb8"));
                rlOften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften4.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften4.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=2;

                rlOften4.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften4.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever4.setTextColor(Color.parseColor("#32cdb8"));
                rlANever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever4.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes4.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften4.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=3;

                rlVoften4.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften4.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever4.setTextColor(Color.parseColor("#32cdb8"));
                rlANever4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever4.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes4.setTextColor(Color.parseColor("#32cdb8"));
                rlOften4.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften4.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////////////

        rlNever5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=0;

                rlNever5.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever5.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever5.setTextColor(Color.parseColor("#32cdb8"));
                rlANever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever5.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes5.setTextColor(Color.parseColor("#32cdb8"));
                rlOften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften5.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften5.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=1;

                rlANever5.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever5.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever5.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes5.setTextColor(Color.parseColor("#32cdb8"));
                rlOften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften5.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften5.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=1;

                rlSomeTimes5.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes5.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever5.setTextColor(Color.parseColor("#32cdb8"));
                rlANever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever5.setTextColor(Color.parseColor("#32cdb8"));
                rlOften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften5.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften5.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=2;

                rlOften5.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften5.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever5.setTextColor(Color.parseColor("#32cdb8"));
                rlANever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever5.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes5.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften5.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=3;

                rlVoften5.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften5.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever5.setTextColor(Color.parseColor("#32cdb8"));
                rlANever5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever5.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes5.setTextColor(Color.parseColor("#32cdb8"));
                rlOften5.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften5.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /////////////////////

        rlNever6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=0;

                rlNever6.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever6.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever6.setTextColor(Color.parseColor("#32cdb8"));
                rlANever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever6.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes6.setTextColor(Color.parseColor("#32cdb8"));
                rlOften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften6.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften6.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=1;

                rlANever6.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever6.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever6.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes6.setTextColor(Color.parseColor("#32cdb8"));
                rlOften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften6.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften6.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=1;

                rlSomeTimes6.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes6.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever6.setTextColor(Color.parseColor("#32cdb8"));
                rlANever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever6.setTextColor(Color.parseColor("#32cdb8"));
                rlOften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften6.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften6.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=2;

                rlOften6.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften6.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever6.setTextColor(Color.parseColor("#32cdb8"));
                rlANever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever6.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes6.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften6.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=3;

                rlVoften6.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften6.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever6.setTextColor(Color.parseColor("#32cdb8"));
                rlANever6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever6.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes6.setTextColor(Color.parseColor("#32cdb8"));
                rlOften6.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften6.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////////////

        rlNever7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=0;

                rlNever7.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever7.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever7.setTextColor(Color.parseColor("#32cdb8"));
                rlANever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever7.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes7.setTextColor(Color.parseColor("#32cdb8"));
                rlOften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften7.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften7.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=1;

                rlANever7.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever7.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever7.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes7.setTextColor(Color.parseColor("#32cdb8"));
                rlOften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften7.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften7.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=1;

                rlSomeTimes7.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes7.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever7.setTextColor(Color.parseColor("#32cdb8"));
                rlANever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever7.setTextColor(Color.parseColor("#32cdb8"));
                rlOften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften7.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften7.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=2;

                rlOften7.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften7.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever7.setTextColor(Color.parseColor("#32cdb8"));
                rlANever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever7.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes7.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften7.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=3;

                rlVoften7.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften7.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever7.setTextColor(Color.parseColor("#32cdb8"));
                rlANever7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever7.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes7.setTextColor(Color.parseColor("#32cdb8"));
                rlOften7.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften7.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////////////

        rlNever8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=0;

                rlNever8.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever8.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever8.setTextColor(Color.parseColor("#32cdb8"));
                rlANever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever8.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes8.setTextColor(Color.parseColor("#32cdb8"));
                rlOften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften8.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften8.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=1;

                rlANever8.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever8.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever8.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes8.setTextColor(Color.parseColor("#32cdb8"));
                rlOften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften8.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften8.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=1;

                rlSomeTimes8.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes8.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever8.setTextColor(Color.parseColor("#32cdb8"));
                rlANever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever8.setTextColor(Color.parseColor("#32cdb8"));
                rlOften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften8.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften8.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=2;

                rlOften8.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften8.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever8.setTextColor(Color.parseColor("#32cdb8"));
                rlANever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever8.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes8.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften8.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=3;

                rlVoften8.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften8.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever8.setTextColor(Color.parseColor("#32cdb8"));
                rlANever8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever8.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes8.setTextColor(Color.parseColor("#32cdb8"));
                rlOften8.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften8.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////////////

        rlNever9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=0;

                rlNever9.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever9.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever9.setTextColor(Color.parseColor("#32cdb8"));
                rlANever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever9.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes9.setTextColor(Color.parseColor("#32cdb8"));
                rlOften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften9.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften9.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=1;

                rlANever9.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever9.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever9.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes9.setTextColor(Color.parseColor("#32cdb8"));
                rlOften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften9.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften9.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=1;

                rlSomeTimes9.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes9.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever9.setTextColor(Color.parseColor("#32cdb8"));
                rlANever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever9.setTextColor(Color.parseColor("#32cdb8"));
                rlOften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften9.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften9.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=2;

                rlOften9.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften9.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever9.setTextColor(Color.parseColor("#32cdb8"));
                rlANever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever9.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes9.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften9.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=3;

                rlVoften9.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften9.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever9.setTextColor(Color.parseColor("#32cdb8"));
                rlANever9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever9.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes9.setTextColor(Color.parseColor("#32cdb8"));
                rlOften9.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften9.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////////////

        rlNever10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=0;

                rlNever10.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever10.setTextColor(Color.parseColor("#FFFFFF"));

                rlANever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever10.setTextColor(Color.parseColor("#32cdb8"));
                rlANever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever10.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes10.setTextColor(Color.parseColor("#32cdb8"));
                rlOften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften10.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften10.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
/*
        rlANever10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=1;

                rlANever10.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtANever10.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever10.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes10.setTextColor(Color.parseColor("#32cdb8"));
                rlOften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften10.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften10.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
*/
        //////////////////
        rlSomeTimes10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=1;

                rlSomeTimes10.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes10.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever10.setTextColor(Color.parseColor("#32cdb8"));
                rlANever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever10.setTextColor(Color.parseColor("#32cdb8"));
                rlOften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften10.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften10.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=2;

                rlOften10.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften10.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever10.setTextColor(Color.parseColor("#32cdb8"));
                rlANever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever10.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes10.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften10.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=3;

                rlVoften10.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften10.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever10.setTextColor(Color.parseColor("#32cdb8"));
                rlANever10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtANever10.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes10.setTextColor(Color.parseColor("#32cdb8"));
                rlOften10.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften10.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
/*****************added by sahenita*****************/


        rlNever11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool11=true;
                val11=0;

                rlNever11.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever11.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes11.setTextColor(Color.parseColor("#32cdb8"));
                rlOften11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften11.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften11.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool11=true;
                val11=1;

                rlSomeTimes11.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes11.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever11.setTextColor(Color.parseColor("#32cdb8"));

                rlOften11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften11.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften11.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool11=true;
                val11=2;

                rlOften11.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften11.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever11.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes11.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften11.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool11=true;
                val11=3;

                rlVoften11.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften11.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever11.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes11.setTextColor(Color.parseColor("#32cdb8"));
                rlOften11.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften11.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*--------------------------12---------------------------------*/
        rlNever12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool12=true;
                val12=0;

                rlNever12.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever12.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes12.setTextColor(Color.parseColor("#32cdb8"));
                rlOften12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften12.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften12.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool12=true;
                val12=1;

                rlSomeTimes12.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes12.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever12.setTextColor(Color.parseColor("#32cdb8"));

                rlOften12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften12.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften12.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool12=true;
                val12=2;

                rlOften12.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften12.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever12.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes12.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften12.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool12=true;
                val12=3;

                rlVoften12.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften12.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever12.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes12.setTextColor(Color.parseColor("#32cdb8"));
                rlOften12.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften12.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*---------------------13-------------------------*/

        rlNever13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool13=true;
                val13=0;

                rlNever13.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever13.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes13.setTextColor(Color.parseColor("#32cdb8"));
                rlOften13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften13.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften13.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool13=true;
                val13=1;

                rlSomeTimes13.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes13.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever13.setTextColor(Color.parseColor("#32cdb8"));

                rlOften13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften13.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften13.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool13=true;
                val13=2;

                rlOften13.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften13.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever13.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes13.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften13.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool13=true;
                val13=3;

                rlVoften13.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften13.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever13.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes13.setTextColor(Color.parseColor("#32cdb8"));
                rlOften13.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften13.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*-----------------14----------------*/

        rlNever14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool14=true;
                val14=0;

                rlNever14.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever14.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes14.setTextColor(Color.parseColor("#32cdb8"));
                rlOften14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften14.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften14.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool14=true;
                val14=1;

                rlSomeTimes14.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes14.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever14.setTextColor(Color.parseColor("#32cdb8"));

                rlOften14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften14.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften14.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool14=true;
                val14=2;

                rlOften14.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften14.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever14.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes14.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften14.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool14=true;
                val14=3;

                rlVoften14.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften14.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever14.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes14.setTextColor(Color.parseColor("#32cdb8"));
                rlOften14.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften14.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*------------------15---------------------*/

        rlNever15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool15=true;
                val15=0;

                rlNever15.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever15.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes15.setTextColor(Color.parseColor("#32cdb8"));
                rlOften15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften15.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften15.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool15=true;
                val15=1;

                rlSomeTimes15.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes15.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever15.setTextColor(Color.parseColor("#32cdb8"));

                rlOften15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften15.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften15.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool15=true;
                val15=2;

                rlOften15.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften15.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever15.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes15.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften15.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool15=true;
                val15=3;

                rlVoften15.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften15.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever15.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes15.setTextColor(Color.parseColor("#32cdb8"));
                rlOften15.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften15.setTextColor(Color.parseColor("#32cdb8"));
            }
        });

        /*-----------------16----------------------*/

        rlNever16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool16=true;
                val16=0;

                rlNever16.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever16.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes16.setTextColor(Color.parseColor("#32cdb8"));
                rlOften16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften16.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften16.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool16=true;
                val16=1;

                rlSomeTimes16.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes16.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever16.setTextColor(Color.parseColor("#32cdb8"));

                rlOften16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften16.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften16.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool16=true;
                val16=2;

                rlOften16.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften16.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever16.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes16.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften16.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool16=true;
                val16=3;

                rlVoften16.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften16.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever16.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes16.setTextColor(Color.parseColor("#32cdb8"));
                rlOften16.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften16.setTextColor(Color.parseColor("#32cdb8"));
            }
        });

        /*----------------17--------------------*/

        rlNever17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool17=true;
                val17=0;

                rlNever17.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever17.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes17.setTextColor(Color.parseColor("#32cdb8"));
                rlOften17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften17.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften17.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool17=true;
                val17=1;

                rlSomeTimes17.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes17.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever17.setTextColor(Color.parseColor("#32cdb8"));

                rlOften17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften17.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften17.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool17=true;
                val17=2;

                rlOften17.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften17.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever17.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes17.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften17.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool17=true;
                val17=3;

                rlVoften17.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften17.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever17.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes17.setTextColor(Color.parseColor("#32cdb8"));
                rlOften17.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften17.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*----------------18-----------------------*/

        rlNever18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool18=true;
                val18=0;

                rlNever18.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever18.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes18.setTextColor(Color.parseColor("#32cdb8"));
                rlOften18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften18.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften18.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool18=true;
                val18=1;

                rlSomeTimes18.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes18.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever18.setTextColor(Color.parseColor("#32cdb8"));

                rlOften18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften18.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften18.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool18=true;
                val18=2;

                rlOften18.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften18.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever18.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes18.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften18.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool18=true;
                val18=3;

                rlVoften18.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften18.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever18.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes18.setTextColor(Color.parseColor("#32cdb8"));
                rlOften18.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften18.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*----------------------19------------------------*/

        rlNever19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool19=true;
                val19=0;

                rlNever19.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever19.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes19.setTextColor(Color.parseColor("#32cdb8"));
                rlOften19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften19.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften19.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool19=true;
                val19=1;

                rlSomeTimes19.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes19.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever19.setTextColor(Color.parseColor("#32cdb8"));

                rlOften19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften19.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften19.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool19=true;
                val19=2;

                rlOften19.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften19.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever19.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes19.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften19.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool19=true;
                val19=3;

                rlVoften19.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften19.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever19.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes19.setTextColor(Color.parseColor("#32cdb8"));
                rlOften19.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften19.setTextColor(Color.parseColor("#32cdb8"));
            }
        });

        /*----------------20----------------------*/
        rlNever20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool20=true;
                val20=0;

                rlNever20.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever20.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes20.setTextColor(Color.parseColor("#32cdb8"));
                rlOften20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften20.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften20.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool20=true;
                val20=1;

                rlSomeTimes20.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes20.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever20.setTextColor(Color.parseColor("#32cdb8"));

                rlOften20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften20.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften20.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool20=true;
                val20=2;

                rlOften20.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften20.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever20.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes20.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften20.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool20=true;
                val20=3;

                rlVoften20.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften20.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever20.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes20.setTextColor(Color.parseColor("#32cdb8"));
                rlOften20.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften20.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        /*---------------21----------------------*/

        rlNever21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool21=true;
                val21=0;

                rlNever21.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtNever21.setTextColor(Color.parseColor("#FFFFFF"));


                rlSomeTimes21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes21.setTextColor(Color.parseColor("#32cdb8"));
                rlOften21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften21.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften21.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////////////
        //////////////////
        rlSomeTimes21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool21=true;
                val21=1;

                rlSomeTimes21.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtSomeTimes21.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever21.setTextColor(Color.parseColor("#32cdb8"));

                rlOften21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften21.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften21.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ////////////////
        rlOften21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool21=true;
                val21=2;

                rlOften21.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtOften21.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever21.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes21.setTextColor(Color.parseColor("#32cdb8"));
                rlVoften21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtVoften21.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
        ///////////////
        rlVoften21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool21=true;
                val21=3;

                rlVoften21.setBackground(getResources().getDrawable(R.drawable.bg_green_question));
                txtVoften21.setTextColor(Color.parseColor("#FFFFFF"));

                rlNever21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtNever21.setTextColor(Color.parseColor("#32cdb8"));
                rlSomeTimes21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtSomeTimes21.setTextColor(Color.parseColor("#32cdb8"));
                rlOften21.setBackgroundColor(Color.parseColor("#FFFFFF"));
                txtOften21.setTextColor(Color.parseColor("#32cdb8"));
            }
        });
    }

    private void funMakeData() {
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        ArrayList<TempModelCohenSave_> arrTmpCohen=new ArrayList<>();

        ArrayList<TempModelCohenSave_.Option1> optionArrayList=new ArrayList<>();
        TempModelCohenSave_.Option1 option1=new TempModelCohenSave_.Option1();
        option1.setDASOptionLabel("Never");
        option1.setDASOptionValue(0);
        option1.setDASOptionLabelHtml("Never");
        optionArrayList.add(option1);

        TempModelCohenSave_.Option1 option2=new TempModelCohenSave_.Option1();
        option1.setDASOptionLabel("Sometimes");
        option1.setDASOptionValue(1);
        option1.setDASOptionLabelHtml("Sometimes");
        optionArrayList.add(option2);

        TempModelCohenSave_.Option1 option3=new TempModelCohenSave_.Option1();
        option1.setDASOptionLabel("Often");
        option1.setDASOptionValue(2);
        option1.setDASOptionLabelHtml("Often");
        optionArrayList.add(option3);

        TempModelCohenSave_.Option1 option4=new TempModelCohenSave_.Option1();
        option1.setDASOptionLabel("Almost Always");
        option1.setDASOptionValue(3);
        option1.setDASOptionLabelHtml("Almost Always");
        optionArrayList.add(option4);

        TempModelCohenSave_ tempModelCohenSave1=new TempModelCohenSave_();
        tempModelCohenSave1.setQuestionMasterId(1);
        tempModelCohenSave1.setQuestion( "I found it hard to wind down");
        tempModelCohenSave1.setQuestionType(2);
        tempModelCohenSave1.setAnswer(val1);
        tempModelCohenSave1.setActive(false);
        tempModelCohenSave1.setDeleted(false);
        tempModelCohenSave1.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave1);
        /////////////
        TempModelCohenSave_ tempModelCohenSave2=new TempModelCohenSave_();
        tempModelCohenSave2.setQuestionMasterId(2);
        tempModelCohenSave2.setQuestion( "I was aware of dryness of my mouth");
        tempModelCohenSave2.setQuestionType(3);
        tempModelCohenSave2.setAnswer(val2);
        tempModelCohenSave2.setActive(false);
        tempModelCohenSave2.setDeleted(false);
        tempModelCohenSave2.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave2);
        //////////////////////
        TempModelCohenSave_ tempModelCohenSave3=new TempModelCohenSave_();
        tempModelCohenSave3.setQuestionMasterId(3);
        tempModelCohenSave3.setQuestion(  "I couldn’t seem to experience any positive feeling at all");
        tempModelCohenSave3.setQuestionType(1);
        tempModelCohenSave3.setAnswer(val3);
        tempModelCohenSave3.setActive(false);
        tempModelCohenSave3.setDeleted(false);
        tempModelCohenSave3.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave3);
        /////////////
        TempModelCohenSave_ tempModelCohenSave4=new TempModelCohenSave_();
        tempModelCohenSave4.setQuestionMasterId(4);
        tempModelCohenSave4.setQuestion( "I experienced breathing difficulty (eg, excessively rapid breathing, breathlessness in the absence of physical exertion)");
        tempModelCohenSave4.setQuestionType(3);
        tempModelCohenSave4.setAnswer(val4);
        tempModelCohenSave4.setActive(false);
        tempModelCohenSave4.setDeleted(false);
        tempModelCohenSave4.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave4);
        //////////////////
        TempModelCohenSave_ tempModelCohenSave5=new TempModelCohenSave_();
        tempModelCohenSave5.setQuestionMasterId(5);
        tempModelCohenSave5.setQuestion( "I found it difficult to work up the initiative to do things");
        tempModelCohenSave5.setQuestionType(1);
        tempModelCohenSave5.setAnswer(val5);
        tempModelCohenSave5.setActive(false);
        tempModelCohenSave5.setDeleted(false);
        tempModelCohenSave5.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave5);
        ///////////////////////
        TempModelCohenSave_ tempModelCohenSave6=new TempModelCohenSave_();
        tempModelCohenSave6.setQuestionMasterId(6);
        tempModelCohenSave6.setQuestion( "I tended to over-react to situations");
        tempModelCohenSave6.setQuestionType(2);
        tempModelCohenSave6.setAnswer(val6);
        tempModelCohenSave6.setActive(false);
        tempModelCohenSave6.setDeleted(false);
        tempModelCohenSave6.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave6);
        //////////////
        TempModelCohenSave_ tempModelCohenSave7=new TempModelCohenSave_();
        tempModelCohenSave7.setQuestionMasterId(7);
        tempModelCohenSave7.setQuestion( "I experienced trembling (eg, in the hands)");
        tempModelCohenSave7.setQuestionType(3);
        tempModelCohenSave7.setAnswer(val7);
        tempModelCohenSave7.setActive(false);
        tempModelCohenSave7.setDeleted(false);
        tempModelCohenSave7.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave7);
        ///////////////
        TempModelCohenSave_ tempModelCohenSave8=new TempModelCohenSave_();
        tempModelCohenSave8.setQuestionMasterId(8);
        tempModelCohenSave8.setQuestion( "I felt that I was using a lot of nervous energy");
        tempModelCohenSave8.setQuestionType(2);
        tempModelCohenSave8.setAnswer(val8);
        tempModelCohenSave8.setActive(false);
        tempModelCohenSave8.setDeleted(false);
        tempModelCohenSave8.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave8);
        ///////////////
        TempModelCohenSave_ tempModelCohenSave9=new TempModelCohenSave_();
        tempModelCohenSave9.setQuestionMasterId(9);
        tempModelCohenSave9.setQuestion( "I was worried about situationsin which I might panic and make a fool of myself");
        tempModelCohenSave9.setQuestionType(3);
        tempModelCohenSave9.setAnswer(val9);
        tempModelCohenSave9.setActive(false);
        tempModelCohenSave9.setDeleted(false);
        tempModelCohenSave9.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave9);
        ///////////////
        TempModelCohenSave_ tempModelCohenSave10=new TempModelCohenSave_();
        tempModelCohenSave10.setQuestionMasterId(10);
        tempModelCohenSave10.setQuestion("I felt that I had nothing to look forward to");
        tempModelCohenSave10.setQuestionType(1);
        tempModelCohenSave10.setAnswer(val10);
        tempModelCohenSave10.setActive(false);
        tempModelCohenSave10.setDeleted(false);
        tempModelCohenSave10.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave10);
        ///////////////


        TempModelCohenSave_ tempModelCohenSave11=new TempModelCohenSave_();
        tempModelCohenSave11.setQuestionMasterId(11);
        tempModelCohenSave11.setQuestion("I found myself getting agitated");
        tempModelCohenSave11.setQuestionType(2);
        tempModelCohenSave11.setAnswer(val11);
        tempModelCohenSave11.setActive(false);
        tempModelCohenSave11.setDeleted(false);
        tempModelCohenSave11.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave11);
/*
----------------------------------------------
*/
        TempModelCohenSave_ tempModelCohenSave12=new TempModelCohenSave_();
        tempModelCohenSave12.setQuestionMasterId(12);
        tempModelCohenSave12.setQuestion( "I found it difficult to relax");
        tempModelCohenSave12.setQuestionType(2);
        tempModelCohenSave12.setAnswer(val12);
        tempModelCohenSave12.setActive(false);
        tempModelCohenSave12.setDeleted(false);
        tempModelCohenSave12.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave12);
/*-------------------------------------*/
        TempModelCohenSave_ tempModelCohenSave13=new TempModelCohenSave_();
        tempModelCohenSave13.setQuestionMasterId(13);
        tempModelCohenSave13.setQuestion( "I felt down-hearted and blue");
        tempModelCohenSave13.setQuestionType(1);
        tempModelCohenSave13.setAnswer(val13);
        tempModelCohenSave13.setActive(false);
        tempModelCohenSave13.setDeleted(false);
        tempModelCohenSave13.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave13);
/*----------------------------*/
        TempModelCohenSave_ tempModelCohenSave14=new TempModelCohenSave_();
        tempModelCohenSave14.setQuestionMasterId(14);
        tempModelCohenSave14.setQuestion(  "I was intolerant of anything that kept me from getting on with what I was doing");
        tempModelCohenSave14.setQuestionType(2);
        tempModelCohenSave14.setAnswer(val14);
        tempModelCohenSave14.setActive(false);
        tempModelCohenSave14.setDeleted(false);
        tempModelCohenSave14.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave14);
/*--------------------------------------*/
        TempModelCohenSave_ tempModelCohenSave15=new TempModelCohenSave_();
        tempModelCohenSave15.setQuestionMasterId(15);
        tempModelCohenSave15.setQuestion("I felt I was close to panic");
        tempModelCohenSave15.setQuestionType(3);
        tempModelCohenSave15.setAnswer(val15);
        tempModelCohenSave15.setActive(false);
        tempModelCohenSave15.setDeleted(false);
        tempModelCohenSave15.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave15);
/*-----------------------------------------*/
        TempModelCohenSave_ tempModelCohenSave16=new TempModelCohenSave_();
        tempModelCohenSave16.setQuestionMasterId(16);
        tempModelCohenSave16.setQuestion("I was unable to become enthusiastic about anything");
        tempModelCohenSave16.setQuestionType(1);
        tempModelCohenSave16.setAnswer(val16);
        tempModelCohenSave16.setActive(false);
        tempModelCohenSave16.setDeleted(false);
        tempModelCohenSave16.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave16);
/*-----------------------------------*/
        TempModelCohenSave_ tempModelCohenSave17=new TempModelCohenSave_();
        tempModelCohenSave17.setQuestionMasterId(17);
        tempModelCohenSave17.setQuestion("I felt I wasn’t worth much as a person");
        tempModelCohenSave17.setQuestionType(1);
        tempModelCohenSave17.setAnswer(val17);
        tempModelCohenSave17.setActive(false);
        tempModelCohenSave17.setDeleted(false);
        tempModelCohenSave17.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave17);
/*----------------------------------------*/
        TempModelCohenSave_ tempModelCohenSave18=new TempModelCohenSave_();
        tempModelCohenSave18.setQuestionMasterId(18);
        tempModelCohenSave18.setQuestion("I felt that I was rather touchy");
        tempModelCohenSave18.setQuestionType(2);
        tempModelCohenSave18.setAnswer(val18);
        tempModelCohenSave18.setActive(false);
        tempModelCohenSave18.setDeleted(false);
        tempModelCohenSave18.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave18);
/*-------------------------------------*/
        TempModelCohenSave_ tempModelCohenSave19=new TempModelCohenSave_();
        tempModelCohenSave19.setQuestionMasterId(19);
        tempModelCohenSave19.setQuestion( "I was aware of the action of my heart in the absence of physicalexertion (eg, sense of heart rate increase, heart missing a beat)");
        tempModelCohenSave19.setQuestionType(3);
        tempModelCohenSave19.setAnswer(val19);
        tempModelCohenSave19.setActive(false);
        tempModelCohenSave19.setDeleted(false);
        tempModelCohenSave19.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave19);
/*-------------------------------*/
        TempModelCohenSave_ tempModelCohenSave20=new TempModelCohenSave_();
        tempModelCohenSave20.setQuestionMasterId(20);
        tempModelCohenSave20.setQuestion( "I felt scared without any good reason");
        tempModelCohenSave20.setQuestionType(3);
        tempModelCohenSave20.setAnswer(val20);
        tempModelCohenSave20.setActive(false);
        tempModelCohenSave20.setDeleted(false);
        tempModelCohenSave20.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave20);
/*------------------------------------*/
        TempModelCohenSave_ tempModelCohenSave21=new TempModelCohenSave_();
        tempModelCohenSave21.setQuestionMasterId(21);
        tempModelCohenSave21.setQuestion("I felt that life was meaningless");
        tempModelCohenSave21.setQuestionType(1);
        tempModelCohenSave21.setAnswer(val21);
        tempModelCohenSave21.setActive(false);
        tempModelCohenSave21.setDeleted(false);
        tempModelCohenSave21.setOptionList(optionArrayList);
        arrTmpCohen.add(tempModelCohenSave21);

        saveDataApi(arrTmpCohen);

    }

    private void saveDataApi(ArrayList<TempModelCohenSave_> arrTmpCohen) {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());
            HashMap<String, Object> hashReq = new HashMap<>();
            if(0==attemptId){

    hashReq.put("UserId", sharedPreference.read("UserID", ""));
    hashReq.put("Key", Util.KEY);
  //  hashReq.put("QuestionnaireAttemptId", attemptId);
    hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
    hashReq.put("Answers", arrTmpCohen);

}else{
    hashReq.put("UserId", sharedPreference.read("UserID", ""));
    hashReq.put("Key", Util.KEY);
    hashReq.put("QuestionnaireAttemptId", attemptId);
    hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
    hashReq.put("Answers", arrTmpCohen);

}



            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.saveDas(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    Log.e("success", "su");
                    if (response.body() != null)
                    {
                        if(response.body().get("SuccessFlag").getAsBoolean())
                        {
                            AlertDialogCustom alertDialogCustom=new AlertDialogCustom(getActivity());
                            alertDialogCustom.ShowDialog("Alert","Save Successfully",false);
                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                                @Override
                                public void onDone(String title) {
                                    ((MainActivity) getActivity()).funDrawer1();

                                    ((MainActivity)getActivity()).clearCacheForParticularFragment(new QuestionariesFragment());
                                    ((MainActivity)getActivity()).loadFragment(new QuestionariesFragment(),"Question",null);
                                }

                                @Override
                                public void onCancel(String title) {

                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Log.e("error", "er");
                    progressDialog.dismiss();


                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void addUpdateAttempt() {

        Log.i("attemptId","13");
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            TmpModelQouestionForRequest tmpModelQouestionForRequest=new TmpModelQouestionForRequest();
            //tmpModelQouestionForRequest.setQuestionnaireStatusID(questionnaireStatusID);
            tmpModelQouestionForRequest.setQuestionnaireStatusID(1);
            tmpModelQouestionForRequest.setQuestionnaireTypeId(questionnaireTypeId);
            tmpModelQouestionForRequest.setUserID(Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("model", tmpModelQouestionForRequest);



            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<CreateAttemptModel> serverCall = finisherService.addUpdateAttempt(hashReq);
            serverCall.enqueue(new Callback<CreateAttemptModel>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<CreateAttemptModel> call, Response<CreateAttemptModel> response) {
                    progressDialog.dismiss();
                    Log.i("attemptId","14");
                    Log.e("success", "su");
                    if (response.body() != null)
                    {

                        Log.i("attemptId","15");
                        attemptId=response.body().getDetails().getQuestionnaireAttemptId();
                        Log.i("attemptId",String.valueOf(attemptId));
                        getQuestionAnswer(attemptId);
                    }

                }

                @Override
                public void onFailure(Call<CreateAttemptModel> call, Throwable t) {

                    Log.e("error", "er");
                    progressDialog.dismiss();


                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void getQuestionAnswer(Integer attemptId) {

        /*Get all questions:
/api/Questionnaire/GetQuestionnaireDAS21MasterList*/

        Log.i("attemptId","10");
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("questionnaireAttemptId", attemptId);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            TmpModelQouestionForRequest tmpModelQouestionForRequest=new TmpModelQouestionForRequest();
            tmpModelQouestionForRequest.setQuestionnaireStatusID(questionnaireStatusID);
            tmpModelQouestionForRequest.setQuestionnaireTypeId(questionnaireTypeId);
            tmpModelQouestionForRequest.setUserID(Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("model", tmpModelQouestionForRequest);



            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetCohenModel> serverCall = finisherService.getCohenModel(hashReq);
            serverCall.enqueue(new Callback<GetCohenModel>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<GetCohenModel> call, Response<GetCohenModel> response) {
                    progressDialog.dismiss();
                    Log.i("attemptId","11");
                    Log.e("success", "su");
                    if (response.body() != null)
                    {
                        Log.i("attemptId","12");
                       updateEditView(response.body().getDetails());
                    }

                }

                @Override
                public void onFailure(Call<GetCohenModel> call, Throwable t) {

                    Log.e("error", "er");
                    progressDialog.dismiss();


                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void updateEditView(GetCohenModel.Details details) {
        if(null!=details.getDasStressScaleAnswerList()){
            for(int i=0;i<details.getDasStressScaleAnswerList().size();i++)
            {
                if(i==0)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever1.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes1.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften1.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften1.performClick();
                    }
                }
                else if(i==1)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever2.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes2.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften2.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften2.performClick();
                    }
                }
                else if(i==2)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever3.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes3.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften3.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften3.performClick();
                    }
                }
                else if(i==3)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever4.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes4.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften4.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften4.performClick();
                    }
                }
                else if(i==4)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever5.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes5.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften5.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften5.performClick();
                    }
                }
                else if(i==5)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever6.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes6.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften6.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften6.performClick();
                    }
                }
                else if(i==6)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever7.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes7.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften7.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften7.performClick();
                    }
                }
                else if(i==7)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever8.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes8.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften8.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften8.performClick();
                    }
                }
                else if(i==8)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever9.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes9.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften9.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften9.performClick();
                    }
                }
                else if(i==9)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever10.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes10.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften10.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften10.performClick();
                    }
                }  else if(i==10)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever11.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes11.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften11.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften11.performClick();
                    }
                }else if(i==11)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever12.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes12.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften12.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften12.performClick();
                    }
                }else if(i==12)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever13.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes13.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften13.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften13.performClick();
                    }
                }else if(i==13)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever14.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes14.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften14.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften14.performClick();
                    }
                }else if(i==14)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever15.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes15.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften15.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften15.performClick();
                    }
                }else if(i==15)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever16.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes16.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften16.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften16.performClick();
                    }
                }else if(i==16)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever17.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes17.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften17.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften17.performClick();
                    }
                }else if(i==17)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever18.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes18.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften18.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften18.performClick();
                    }
                }else if(i==18)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever19.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes19.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften19.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften19.performClick();
                    }
                }else if(i==19)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever20.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes20.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften20.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften20.performClick();
                    }
                }else if(i==20)
                {
                    if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                    {
                        rlNever21.performClick();
                    }

                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                    {
                        rlSomeTimes21.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                    {
                        rlOften21.performClick();
                    }
                    else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                    {
                        rlVoften21.performClick();
                    }
                }
            }

        }


    }

    private void funToolBar() {
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

      /*  imgLogo.setOnClickListener(new View.OnClickListener() {             @Override             public void onClick(View v) { 		((MainActivity)getActivity()).showPromotionalDialogs();                 if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {                     ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);                 } else { 		                         ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);                 }             }         });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).funDrawer1();

                ((MainActivity) getActivity()).loadFragment(new QuestionariesFragment(), "TrackFragment", getArguments());
            }
        });


    }
    /*added by sahenita*/
    private void getResultFromApi() {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("QuestionnaireTypeId", 5);



            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetUserQuestionnaireAttemptListModel> serverCall = finisherService.getUserQuestionnaireAttemptList(hashReq);
            serverCall.enqueue(new Callback<GetUserQuestionnaireAttemptListModel>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<GetUserQuestionnaireAttemptListModel> call, Response<GetUserQuestionnaireAttemptListModel> response) {
                    progressDialog.dismiss();

                    Log.e("success", "su");
                    if (response.body() != null)
                    {
                       /* if(null!=lstData)
                        lstData.clear();

                        rvList.setAdapter(null);*/
                        lstData=response.body().getDetails();
                       // Log.i("attemptId","20");
                        updateEditView_new(lstData);
                    }


                }

                @Override
                public void onFailure(Call<GetUserQuestionnaireAttemptListModel> call, Throwable t) {

                    Log.e("error", "er");
                    progressDialog.dismiss();


                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }


    private void updateEditView_new(List<GetUserQuestionnaireAttemptListModel.Detail> details) {
        List<GetUserQuestionnaireAttemptListModel.DasStressScaleAnswerList> details_ = null;
       // Log.i("attemptId",String.valueOf(attemptId));
       // Log.i("attemptId",String.valueOf(details.get(0).getQuestionnaireAttemptId()));
        for(int i=0;i<details.size();i++){
            Log.i("attemptId",String.valueOf(attemptId));
           // Log.i("attemptId",String.valueOf(details.get(0).getQuestionnaireAttemptId()));
            if(String.valueOf(attemptId).equalsIgnoreCase(String.valueOf(details.get(i).getQuestionnaireAttemptId()))){
//                details_=details.get(i).getDasStressScaleAnswerLists();

                Log.i("attemptId","21");
                if(null!=details.get(i).getDasStressScaleAnswerLists()){
                    Log.i("attemptId","22");
                    for(int j=0;j<details.get(i).getDasStressScaleAnswerLists().size();j++)
                    {
                        Log.i("attemptId","23");
                        if(j==0)
                        {
                            Log.i("attemptId","22");
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                Log.i("attemptId","23");
                                rlNever1.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                Log.i("attemptId","24");
                                rlSomeTimes1.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                             Log.i("attemptId","25");

                                rlOften1.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                Log.i("attemptId","26");

                                rlVoften1.performClick();
                            }
                        }
                        else if(j==1)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever2.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes2.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften2.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften2.performClick();
                            }
                        }
                        else if(j==2)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever3.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes3.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften3.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften3.performClick();
                            }
                        }
                        else if(j==3)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever4.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes4.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften4.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften4.performClick();
                            }
                        }
                        else if(j==4)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever5.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes5.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften5.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften5.performClick();
                            }
                        }
                        else if(j==5)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever6.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes6.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften6.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften6.performClick();
                            }
                        }
                        else if(j==6)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever7.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes7.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften7.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften7.performClick();
                            }
                        }
                        else if(j==7)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever8.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes8.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften8.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften8.performClick();
                            }
                        }
                        else if(j==8)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever9.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes9.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften9.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften9.performClick();
                            }
                        }
                        else if(j==9)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever10.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes10.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften10.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften10.performClick();
                            }
                        }  else if(j==10)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever11.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes11.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften11.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften11.performClick();
                            }
                        }else if(j==11)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever12.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes12.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften12.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften12.performClick();
                            }
                        }else if(j==12)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever13.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes13.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften13.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften13.performClick();
                            }
                        }else if(j==13)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever14.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes14.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften14.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften14.performClick();
                            }
                        }else if(j==14)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever15.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes15.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften15.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften15.performClick();
                            }
                        }else if(j==15)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever16.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes16.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften16.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften16.performClick();
                            }
                        }else if(j==16)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever17.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes17.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften17.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften17.performClick();
                            }
                        }else if(j==17)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever18.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes18.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften18.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften18.performClick();
                            }
                        }else if(j==18)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever19.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes19.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften19.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften19.performClick();
                            }
                        }else if(j==19)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever20.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes20.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften20.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften20.performClick();
                            }
                        }else if(j==20)
                        {
                            if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==0)
                            {
                                rlNever21.performClick();
                            }

                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==1)
                            {
                                rlSomeTimes21.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==2)
                            {
                                rlOften21.performClick();
                            }
                            else if(details.get(i).getDasStressScaleAnswerLists().get(j).getValue()==3)
                            {
                                rlVoften21.performClick();
                            }
                        }
                    }

                }

            }

        }


/*
        if(null!=details_){
            for(int i=0;i<details_.size();i++)
            {
                if(i==0)
                {
                    Log.i("attemptId","22");
                    if(details_.get(i).getValue()==0)
                    {
                        Log.i("attemptId","23");
                        rlNever1.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        Log.i("attemptId","24");
                        rlSomeTimes1.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        Log.i("attemptId","25");

                        rlOften1.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        Log.i("attemptId","26");

                        rlVoften1.performClick();
                    }
                }
                else if(i==1)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever2.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes2.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften2.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften2.performClick();
                    }
                }
                else if(i==2)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever3.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes3.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften3.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften3.performClick();
                    }
                }
                else if(i==3)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever4.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes4.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften4.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften4.performClick();
                    }
                }
                else if(i==4)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever5.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes5.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften5.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften5.performClick();
                    }
                }
                else if(i==5)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever6.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes6.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften6.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften6.performClick();
                    }
                }
                else if(i==6)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever7.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes7.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften7.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften7.performClick();
                    }
                }
                else if(i==7)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever8.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes8.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften8.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften8.performClick();
                    }
                }
                else if(i==8)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever9.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes9.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften9.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften9.performClick();
                    }
                }
                else if(i==9)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever10.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes10.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften10.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften10.performClick();
                    }
                }  else if(i==10)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever11.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes11.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften11.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften11.performClick();
                    }
                }else if(i==11)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever12.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes12.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften12.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften12.performClick();
                    }
                }else if(i==12)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever13.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes13.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften13.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften13.performClick();
                    }
                }else if(i==13)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever14.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes14.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften14.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften14.performClick();
                    }
                }else if(i==14)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever15.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes15.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften15.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften15.performClick();
                    }
                }else if(i==15)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever16.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes16.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften16.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften16.performClick();
                    }
                }else if(i==16)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever17.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes17.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften17.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften17.performClick();
                    }
                }else if(i==17)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever18.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes18.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften18.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften18.performClick();
                    }
                }else if(i==18)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever19.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes19.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften19.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften19.performClick();
                    }
                }else if(i==19)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever20.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes20.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften20.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften20.performClick();
                    }
                }else if(i==20)
                {
                    if(details_.get(i).getValue()==0)
                    {
                        rlNever21.performClick();
                    }

                    else if(details_.get(i).getValue()==1)
                    {
                        rlSomeTimes21.performClick();
                    }
                    else if(details_.get(i).getValue()==2)
                    {
                        rlOften21.performClick();
                    }
                    else if(details_.get(i).getValue()==3)
                    {
                        rlVoften21.performClick();
                    }
                }
            }

        }
*/


    }













}
