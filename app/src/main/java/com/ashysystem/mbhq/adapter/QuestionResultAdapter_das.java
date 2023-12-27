package com.ashysystem.mbhq.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.fragment.test.DasQuestion;
import com.ashysystem.mbhq.fragment.test.QuestionariesFragment;
import com.ashysystem.mbhq.model.GetUserQuestionnaireAttemptListModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QuestionResultAdapter_das extends RecyclerView.Adapter<QuestionResultAdapter_das.MyViewHolder>{
    private Context context;
    private int typeId=0;
    private int attemptId = 0;
    private List<GetUserQuestionnaireAttemptListModel.Detail> lstData;
    private OnDeleteListner deleteListner;


    public QuestionResultAdapter_das(Context context, int typeId, List<GetUserQuestionnaireAttemptListModel.Detail> lstData,
                                     int attemptId) {
        this.context=context;
        this.typeId=typeId;
        this.lstData=lstData;
        this.attemptId = attemptId;
    }

    public QuestionResultAdapter_das setDeleteListner(OnDeleteListner deleteListner) {
        this.deleteListner = deleteListner;
        return this;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_result_question1, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String createdAt = lstData.get(position).getCreatedAt();
        String[] spData=createdAt.split("T");
        SimpleDateFormat simpleDateFormatGet=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatRcvd=new SimpleDateFormat("dd/MM/yyyy");
        Date getDate=null,showDate=null;
        try {
            getDate=simpleDateFormatGet.parse(spData[0]);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.txtDate.setText(simpleDateFormatRcvd.format(getDate));
        holder.txt_d.setText(String.valueOf( lstData.get(position).getDAS21Depression()));
        holder.txt_a.setText(String.valueOf( lstData.get(position).getDAS21Anxiety()));
        holder.txt_s.setText(String.valueOf( lstData.get(position).getDAS21Stress()));

        holder.llBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putInt("QuestionnaireStatusID",1);
                bundle.putInt("QuestionnaireTypeId",typeId);
                bundle.putInt("GetAttemptId",lstData.get(position).getQuestionnaireAttemptId());
                ((MainActivity)context).clearCacheForParticularFragment(new DasQuestion());
                DasQuestion happinessQuestionFragment=new DasQuestion();
                happinessQuestionFragment.setArguments(bundle);
                ((MainActivity)context).loadFragment(happinessQuestionFragment,"Happy",null);
            }
        });

        holder.rlDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteQuestionApi(lstData.get(position).getQuestionnaireAttemptId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return lstData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtDate,txt_d,txt_a,txt_s;
        LinearLayout llBlock;
        RelativeLayout rlDelete;

        public MyViewHolder(View itemView) {
            super(itemView);
            txtDate=(TextView)itemView.findViewById(R.id.txtDate);
            txt_d=(TextView)itemView.findViewById(R.id.txt_d);
            txt_a=(TextView)itemView.findViewById(R.id.txt_a);
            txt_s=(TextView)itemView.findViewById(R.id.txt_s);
            llBlock=(LinearLayout)itemView.findViewById(R.id.llBlock);
            rlDelete=(RelativeLayout)itemView.findViewById(R.id.rlDelete);

        }
    }


    private void deleteQuestionApi(Integer questionnaireAttemptId) {
        if (Connection.checkConnection(context)) {
            final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(context);

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("questionnaireAttemptId", questionnaireAttemptId);


            FinisherServiceImpl finisherService = new FinisherServiceImpl(context);
            Call<JsonObject> serverCall = finisherService.deleteQuestion(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()){
                        progressDialog.dismiss();
                        if (response.body().has("SuccessFlag")){
                            if (response.body().get("SuccessFlag").getAsBoolean()){
                                ((MainActivity)context).clearCacheForParticularFragment(new QuestionariesFragment());
                                Log.e("Success", "complete");
                                if (deleteListner!=null){
                                    deleteListner.onDelete();
                                }
                            }else {
                                Log.e("Failed", response.body().
                                        get("ErrorMessage").getAsString());
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Util.showToast(context, Util.networkMsg);
        }
    }

    public interface OnDeleteListner{
        void onDelete();
    }
}
