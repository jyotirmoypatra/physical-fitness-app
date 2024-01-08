package com.ashysystem.mbhq.fragment.test;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.CreateAttemptModel;
import com.ashysystem.mbhq.model.GetCohenModel;
import com.ashysystem.mbhq.model.TempModelCohenSave;
import com.ashysystem.mbhq.model.TmpModelQouestionForRequest;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CohenQuestion extends Fragment {

    private NestedScrollView nestedScroll;
    private RelativeLayout rlNever1,rlNever2,rlNever3,rlNever4,rlNever5,rlNever6,rlNever7,rlNever8,rlNever9,rlNever10,rlANever1,rlANever2,rlANever3,rlANever4,rlANever5,rlANever6,rlANever7,rlANever8,rlANever9,rlANever10,rlSomeTimes1,rlSomeTimes2,rlSomeTimes3,rlSomeTimes4,rlSomeTimes5,rlSomeTimes6,rlSomeTimes7,rlSomeTimes8,rlSomeTimes9,rlSomeTimes10,rlOften1,rlOften2,rlOften3,rlOften4,rlOften5,rlOften6,rlOften7,rlOften8,rlOften9,rlOften10,rlVoften1,rlVoften2,rlVoften3,rlVoften4,rlVoften5,rlVoften6,rlVoften7,rlVoften8,rlVoften9,rlVoften10;
    private TextView txtScore,txtNever1,txtNever2,txtNever3,txtNever4,txtNever5,txtNever6,txtNever7,txtNever8,txtNever9,txtNever10,txtANever1,txtANever2,txtANever3,txtANever4,txtANever5,txtANever6,txtANever7,txtANever8,txtANever9,txtANever10,txtSomeTimes1,txtSomeTimes2,txtSomeTimes3,txtSomeTimes4,txtSomeTimes5,txtSomeTimes6,txtSomeTimes7,txtSomeTimes8,txtSomeTimes9,txtSomeTimes10,txtOften1,txtOften2,txtOften3,txtOften4,txtOften5,txtOften6,txtOften7,txtOften8,txtOften9,txtOften10,txtVoften1,txtVoften2,txtVoften3,txtVoften4,txtVoften5,txtVoften6,txtVoften7,txtVoften8,txtVoften9,txtVoften10;
    int sum=0;
    int val1=0,val2=0,val3=0,val4=0,val5=0,val6=0,val7=0,val8=0,val9=0,val10=0;
    private boolean bool1=false,bool2=false,bool3=false,bool4=false,bool5=false,bool6=false,bool7=false,bool8=false,bool9=false,bool10=false;
    private Integer attemptId=-1;
    private int questionnaireStatusID=0;
    private int questionnaireTypeId=0;
    private RelativeLayout rlSave;
    private int getAttemptId=-1;
    private RelativeLayout rlBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_cohen_strees,container,false);
        funToolBar();
        initView(view);
        getDataFromPrevious();
     /*  if(attemptId==-1)
        addUpdateAttempt();*/
        if(getAttemptId!=-1)
        {

            attemptId=getAttemptId;
            Log.i("attemptId",String.valueOf(attemptId));
            Log.i("attemptId","1");

            getQuestionAnswer(getAttemptId);
        }
        else
        {
            if(attemptId==-1)
                Log.i("attemptId",String.valueOf(attemptId));
            Log.i("attemptId","2");
            addUpdateAttempt();
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
        ///////////////////
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bool1&&bool2&&bool3&&bool4&&bool5&&bool6&bool7&bool8&bool9&bool10)
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
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                val1=1;
                bool1=true;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool1=true;
                val1=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val1=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val1=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool2=true;
                val2=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val2=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val2=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        ////////////////////////
        //////////////////////
        /////////////////////
        rlNever3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool3=true;
                val3=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val3=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val3=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        /////////////////////////////
        //////////////////////////////
        ////////////////////////////////
        rlNever4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool4=true;
                val4=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val4=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val4=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum*=4;
                txtScore.setText(sum+"");
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
        ////////////////////
        /////////////////////
        /////////////////////
        /////////////////////
        rlNever5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum*=4;
                txtScore.setText(sum+"");
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
        rlANever5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum*=4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool5=true;
                val5=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val5=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val5=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        ///////////////////////
        ///////////////////////////
        ///////////////////////////
        /////////////////////////
        ///////////////////
        rlNever6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool6=true;
                val6=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val6=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val6=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        ///////////////////////
        ////////////////////////
        ////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////////////
        rlNever7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool7=true;
                val7=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val7=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val7=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        ///////////////////////
        ////////////////////////
        ////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////////////
        rlNever8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool8=true;
                val8=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val8=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val8=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        ///////////////////////
        ////////////////////////
        ////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////
        rlNever9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool9=true;
                val9=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val9=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val9=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        ///////////////////////
        ////////////////////////
        ////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////////////
        /////////////////////
        ////////////////////////
        rlNever10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=0;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        rlANever10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=1;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
        //////////////////
        rlSomeTimes10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bool10=true;
                val10=2;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val10=3;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
                val10=4;
                sum=val1+val2+val3+val4+val5+val6+val7+val8+val9+val10;
                sum = sum * 4;
                txtScore.setText(sum+"");
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
    }

    private void funMakeData() {
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        ArrayList<TempModelCohenSave> arrTmpCohen=new ArrayList<>();
        TempModelCohenSave tempModelCohenSave1=new TempModelCohenSave();
        tempModelCohenSave1.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave1.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave1.setQuestionnaireCohenStressScaleMasterId(1);
        tempModelCohenSave1.setValue(val1);
        arrTmpCohen.add(tempModelCohenSave1);
        /////////////
        TempModelCohenSave tempModelCohenSave2=new TempModelCohenSave();
        tempModelCohenSave2.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave2.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave2.setQuestionnaireCohenStressScaleMasterId(2);
        tempModelCohenSave2.setValue(val2);
        arrTmpCohen.add(tempModelCohenSave2);
        //////////////////////
        TempModelCohenSave tempModelCohenSave3=new TempModelCohenSave();
        tempModelCohenSave3.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave3.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave3.setQuestionnaireCohenStressScaleMasterId(3);
        tempModelCohenSave3.setValue(val3);
        arrTmpCohen.add(tempModelCohenSave3);
        /////////////
        TempModelCohenSave tempModelCohenSave4=new TempModelCohenSave();
        tempModelCohenSave4.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave4.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave4.setQuestionnaireCohenStressScaleMasterId(4);
        tempModelCohenSave4.setValue(val4);
        arrTmpCohen.add(tempModelCohenSave4);
        //////////////////
        TempModelCohenSave tempModelCohenSave5=new TempModelCohenSave();
        tempModelCohenSave5.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave5.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave5.setQuestionnaireCohenStressScaleMasterId(5);
        tempModelCohenSave5.setValue(val5);
        arrTmpCohen.add(tempModelCohenSave5);
        ///////////////////////
        TempModelCohenSave tempModelCohenSave6=new TempModelCohenSave();
        tempModelCohenSave6.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave6.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave6.setQuestionnaireCohenStressScaleMasterId(6);
        tempModelCohenSave6.setValue(val6);
        arrTmpCohen.add(tempModelCohenSave6);
        //////////////
        TempModelCohenSave tempModelCohenSave7=new TempModelCohenSave();
        tempModelCohenSave7.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave7.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave7.setQuestionnaireCohenStressScaleMasterId(7);
        tempModelCohenSave7.setValue(val7);
        arrTmpCohen.add(tempModelCohenSave7);
        ///////////////
        TempModelCohenSave tempModelCohenSave8=new TempModelCohenSave();
        tempModelCohenSave8.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave8.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave8.setQuestionnaireCohenStressScaleMasterId(8);
        tempModelCohenSave8.setValue(val8);
        arrTmpCohen.add(tempModelCohenSave8);
        ///////////////
        TempModelCohenSave tempModelCohenSave9=new TempModelCohenSave();
        tempModelCohenSave9.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave9.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave9.setQuestionnaireCohenStressScaleMasterId(9);
        tempModelCohenSave9.setValue(val9);
        arrTmpCohen.add(tempModelCohenSave9);
        ///////////////
        TempModelCohenSave tempModelCohenSave10=new TempModelCohenSave();
        tempModelCohenSave10.setUserId(Integer.parseInt(sharedPreference.read("UserID", "")));
        tempModelCohenSave10.setQuestionnaireAttemptId(attemptId);
        tempModelCohenSave10.setQuestionnaireCohenStressScaleMasterId(10);
        tempModelCohenSave10.setValue(val10);
        arrTmpCohen.add(tempModelCohenSave10);
        ///////////////
        saveDataApi(arrTmpCohen);


    }

    private void saveDataApi(ArrayList<TempModelCohenSave> arrTmpCohen) {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            /*JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("QuestionnaireStatusID",questionnaireStatusID);
                jsonObject.put("QuestionnaireTypeId",questionnaireTypeId);
                jsonObject.put("UserId",sharedPreference.read("UserID", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            hashReq.put("model", arrTmpCohen);



            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.saveCohen(hashReq);
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
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            /*JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("QuestionnaireStatusID",questionnaireStatusID);
                jsonObject.put("QuestionnaireTypeId",questionnaireTypeId);
                jsonObject.put("UserId",sharedPreference.read("UserID", ""));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
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

                    Log.e("success", "su");
                    if (response.body() != null)
                    {
                        attemptId=response.body().getDetails().getQuestionnaireAttemptId();
                        Log.i("attemptId",String.valueOf(attemptId));
                        Log.i("attemptId","3");

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

                    Log.e("success", "su");
                    if (response.body() != null)
                    {

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
        for(int i=0;i<details.getCohenStressScaleAnswerList().size();i++)
        {
            if(i==0)
            {
                if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                {
                    rlNever1.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                {
                    rlANever1.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes1.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlOften1.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
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
                    rlANever2.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes2.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlOften2.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
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
                    rlANever3.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes3.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlOften3.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlVoften3.performClick();
                }
            }
            else if(i==3)
            {
                if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlNever4.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlANever4.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes4.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                {
                    rlOften4.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                {
                    rlVoften4.performClick();
                }
            }
            else if(i==4)
            {
                if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlNever5.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlANever5.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes5.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                {
                    rlOften5.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
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
                    rlANever6.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes6.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlOften6.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlVoften6.performClick();
                }
            }
            else if(i==6)
            {
                if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlNever7.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlANever7.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes7.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                {
                    rlOften7.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
                {
                    rlVoften7.performClick();
                }
            }
            else if(i==7)
            {
                if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlNever8.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlANever8.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes8.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==1)
                {
                    rlOften8.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==0)
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
                    rlANever9.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes9.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlOften9.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
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
                    rlANever10.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==2)
                {
                    rlSomeTimes10.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==3)
                {
                    rlOften10.performClick();
                }
                else if(details.getCohenStressScaleAnswerList().get(i).getValue()==4)
                {
                    rlVoften10.performClick();
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

    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.logo1);

    }
}
