package com.ashysystem.mbhq.fragment.test;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.ashysystem.mbhq.adapter.QuestionResultAdapter;
import com.ashysystem.mbhq.adapter.QuestionResultAdapter_das;
import com.ashysystem.mbhq.model.GetUserQuestionnaireAttemptListModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionResultFragment extends Fragment implements QuestionResultAdapter.OnDeleteListner{
    private RecyclerView rvList;
    private int questionnaireTypeId=0;
    private int questionnaireAttemptId = 0;
    private List<GetUserQuestionnaireAttemptListModel.Detail> lstData;
    private TextView txtHeader;
    private QuestionResultAdapter resultAdapter;
    private QuestionResultAdapter_das resultAdapter_das;
    private RelativeLayout rlBack;
    private LinearLayout llBlock;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_result_question,container,false);
        initview(view);
        funToolBar();
        getDataFromPrevious();

        Log.i("questionnaireTypeId",String.valueOf(questionnaireTypeId));

        if(questionnaireTypeId==1)
        {
            Log.i("questionnaireTypeId","1");
            llBlock.setVisibility(View.GONE);
            txtHeader.setText("RESULTS : STRESS TEST");
        }

        if(questionnaireTypeId==2)
        {
            Log.i("questionnaireTypeId","2");
            llBlock.setVisibility(View.GONE);
            txtHeader.setText("RESULTS : RAHE PERCEIVED STRESS");
        }
        if(questionnaireTypeId==3)
        {
            Log.i("questionnaireTypeId","3");
            llBlock.setVisibility(View.GONE);
            txtHeader.setText("RESULTS : HAPPINESS TEST");
        }
      /*  if(questionnaireTypeId==5)
        {
            llBlock.setVisibility(View.VISIBLE);
            txtHeader.setText("RESULTS : DAS TEST");
        }*/
        if(questionnaireTypeId!=0)
            getResultFromApi();


        return view;
    }

    private void getDataFromPrevious() {
        if(getArguments()!=null)
        {
            if(getArguments().containsKey("QuestionnaireTypeId"))
                questionnaireTypeId=getArguments().getInt("QuestionnaireTypeId");
        }

    }

    private void initview(View view) {
        llBlock=(LinearLayout) view.findViewById(R.id.llBlock);
        rvList=(RecyclerView)view.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        txtHeader=(TextView)view.findViewById(R.id.txtHeader);
        rlBack=(RelativeLayout)view.findViewById(R.id.rlBack);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).funDrawer1();

                ((MainActivity) getActivity()).loadFragment(new QuestionariesFragment(), "QuestionariesFragment", getArguments());
            }
        });

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

        /*   imgLogo.setOnClickListener(new View.OnClickListener() {             @Override             public void onClick(View v) { 		((MainActivity)getActivity()).showPromotionalDialogs();                 if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {                     ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);                 } else { 		                         ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);                 }             }         });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).funDrawer1();

                ((MainActivity) getActivity()).loadFragment(new QuestionariesFragment(), "TrackFragment", getArguments());
            }
        });


    }
    ////////////
    private void getResultFromApi() {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("QuestionnaireTypeId", questionnaireTypeId);
            Log.i("questionnaireTypeId",String.valueOf(questionnaireTypeId));


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

                    }
                    if(lstData!=null)
                    {

                       /* if(questionnaireTypeId==5)
                        {
                            loadAdapter_das();
                        }else{*/
                        loadAdapter();
                        // }


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

    private void loadAdapter() {
        resultAdapter = new QuestionResultAdapter(getActivity(),questionnaireTypeId,lstData, questionnaireAttemptId);
        resultAdapter.setDeleteListner(this);
        rvList.setAdapter(resultAdapter);
    }
    /* private void loadAdapter_das() {
         resultAdapter_das = new QuestionResultAdapter_das(getActivity(),questionnaireTypeId,lstData, questionnaireAttemptId);
         resultAdapter_das.setDeleteListner(this);
         rvList.setAdapter(resultAdapter);
     }*/
    @Override
    public void onDelete() {
        lstData.clear();
        rvList.setAdapter(null);
        getResultFromApi();
    }
}
