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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.HappinessQuestionAdapter;
import com.ashysystem.mbhq.model.Category;
import com.ashysystem.mbhq.model.ContentItemHappinessQuestion;
import com.ashysystem.mbhq.model.CreateAttemptModel;
import com.ashysystem.mbhq.model.GetCohenModel;
import com.ashysystem.mbhq.model.HappinessModel;
import com.ashysystem.mbhq.model.HappinessQuestionModel;
import com.ashysystem.mbhq.model.HeaderHappinessQuestionModel;
import com.ashysystem.mbhq.model.TempModelHappinessSave;
import com.ashysystem.mbhq.model.TmpModelQouestionForRequest;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HappinessQuestionFragment extends Fragment {
    private RecyclerView rvlList;
    //private String[] questionSet={"A. I feel like a failure.","B. I do not feel like a winner.","C. I feel like I have succeeded more than most people.","D. As I look back on my life, all I see are victories.","E. I feel I am extraordinarily successful.","A. I am usually in a bad mood.","B. I am usually in a neutral mood.","C. I am usually in a good mood.","D. I am usually in a great mood.","E. I am usually in an unbelievably great mood.","A. When I am working, I pay more attention to what is going on around me than to what I am doing.","B. When I am working, I pay as much attention to what is going on around me as to what I am doing.","C. When I am working, I pay more attention to what I am doing than to what is going on around me.","D. When I am working, I rarely notice what is going on around me.","E. When I am working, I pay so much attention to what I am doing that the outside world practically ceases to exist.","A. My life does not have any purpose or meaning.","B. I do not know the purpose or meaning of my life.","C. I have a hint about my purpose in life.","D. I have a pretty good idea about the purpose or meaning of my life.","E. I have a very clear idea about the purpose or meaning of my life.","A. I rarely get what I want.","B. Sometimes, I get what I want, and sometimes not.","C. Somewhat more often than not, I get what I want.","D. I usually get what I want.","E. I always get what I want.","A. I have sorrow in my life.","B. I have neither sorrow nor joy in my life.","C. I have more joy than sorrow in my life.","D. I have much more joy than sorrow in my life.","E. My life is filled with joy.","A. Most of the time I feel bored.","B. Most of the time I feel neither bored nor interested in what I am doing.","C. Most of the time I feel interested in what I am doing.","D. Most of the time I feel quite interested in what I am doing.","E. Most of the time I feel fascinated by what I am doing.","A. I feel cut off from other people.","B. I feel neither close to nor cut off from other people.","C. I feel close to friends and family members.","D. I feel close to most people, even if I do not know them well.","E. I feel close to everyone in the world.","A. By objective standards, I do poorly.","B. By objective standards, I do neither well nor poorly.","C. By objective standards, I do rather well.","D. By objective standards, I do quite well.","E. By objective standards, I do amazingly well.","A. I am ashamed of myself.","B. I am not ashamed of myself.","C. I am proud of myself.","D. I am very proud of myself.","E. I am extraordinarily proud of myself.","A. Time passes slowly during most of the things that I do.","B. Time passes quickly during some of the things that I do and slowly for other things.","C. Time passes quickly during most of the things that I do.","D. Time passes quickly during all of the things that I do. ","E. Time passes so quickly during all of the things that I do that I do not even notice it","A. In the grand scheme of things, my existence may hurt the world.","B. My existence neither helps nor hurts the world.","C. My existence has a small but positive effect on the world.","D. My existence makes the world a better place.","E. My existence has a lasting, large, and positive impact on the world.","A. I do not do most things very well.","B. I do okay at most things I am doing.","C. I do well at some things I am doing.","D. I do well at most things I am doing.","E. I do really well at whatever I am doing.","A. I have little or no enthusiasm.","B. My enthusiasm level is neither high nor low.","C. I have a good amount of enthusiasm.","D. I feel enthusiastic doing almost everything.","E. I have so much enthusiasm that I feel I can do most anything.","A. I do not like my work (paid or unpaid).","B. I feel neutral about my work.","C. For the most part, I like my work.","D. I really like my work.","E. I truly love my work.","A. I am pessimistic about the future.","B. I am neither optimistic nor pessimistic about the future.","C. I feel somewhat optimistic about the future.","D. I feel quite optimistic about the future.","E. I feel extraordinarily optimistic about the future.","A. I have accomplished little in life.","B. I have accomplished no more in life than most people.","C. I have accomplished somewhat more in life than most people.","D. I have accomplished more in life than most people.","E. I have accomplished a great deal more in my life than most people.","A. I am unhappy with myself.","B. I am neither happy nor unhappy with myself--I am neutral.","C. I am happy with myself.","D. I am very happy with myself.","E. I could not be any happier with myself.","A. My skills are never challenged by the situations I encounter.","B. My skills are occasionally challenged by the situations I encounter.","C. My skills are sometimes challenged by the situations I encounter.","D. My skills are often challenged by the situations I encounter.","E. My skills are always challenged by the situations I encounter.","A. I spend all of my time doing things that are unimportant.","B. I spend a lot of time doing things that are neither important nor unimportant.","C. I spend some of my time every day doing things that are important.","D. I spend most of my time every day doing things that are important.","E. I spend practically every moment every day doing things that are important.","A. If I were keeping score in life, I would be behind.","B. If I were keeping score in life, I would be about even.","C. If I were keeping score in life, I would be somewhat ahead.","D. If I were keeping score in life, I would be ahead.","E. If I were keeping score in life, I would be far ahead.","A. I experience more pain than pleasure.","B. I experience pain and pleasure in equal measure.","C. I experience more pleasure than pain.","D. I experience much more pleasure than pain.","E. My life is filled with pleasure.","A. I do not enjoy my daily routine.","B. I feel neutral about my daily routine.","C. I like my daily routine, but I am happy to get away from it.","D. I like my daily routine so much that I rarely take breaks from it.","E. I like my daily routine so much that I almost never take breaks from it.","A. My life is a bad one.","B. My life is an OK one.","C. My life is a good one.","D. My life is a very good one.","E. My life is a wonderful one."};
    private String[] questionSet={"A. I feel like a failure.","B. I do not feel like a winner.","C. I’ve had slightly more success than the average person","D. I succeed most of the time.","E. I am successful in everything I do","A. I am usually in a bad mood.","B. I am usually in a neutral mood.","C. I am usually in a good mood.","D. I am usually in an excellent mood.","E. I am usually in an unbelievably great mood.","A. When I am working, I pay more attention to what is going on around me than to what I am doing.","B. When I am working, I pay as much attention to what is going on around me as to what I am doing.","C. When I am working, I pay more attention to what I am doing than to what is going on around me.","D. When I am working I have great attention and rarely notice what is going on around me.","E. When I am working, I virtually never notice what is going on around me.","A. My life does not have any purpose or meaning.","B. I do not know the purpose or meaning of my life.","C. I think I know my purpose in life.","D. I have a pretty good idea about the purpose or meaning of my life.","E. I have a very clear idea about the purpose or meaning of my life.","A. I rarely get what I want.","B. Sometimes, I get what I want, and sometimes not.","C. I get what I want slightly more than I don’t get what I want","D. I usually get what I want.","E. I always get what I want.","A. My life is filled with sorrow.","B. I have more sorrow than joy in my life.","C. I have more joy than sorrow in my life.","D. I have much more joy than sorrow in my life.","E. My life is filled with joy.","A. Most of the time I feel bored.","B. Most of the time I feel neither bored nor interested in what I am doing.","C. Some of the time I feel interested in what I am doing.","D. Most of the time I feel quite interested in what I am doing.","E. Most of the time I feel fascinated by what I am doing.","A. I feel cut off from other people.","B. I feel cut off from most people","C. I feel close to all my friends and family.","D. I feel close to most people in general","E. I feel close to everyone, even if I don’t know them.","A. By objective standards, I do poorly.","B. By objective standards, I am average","C. By objective standards, I do better than average.","D. By objective standards, I do well.","E. By objective standards, I do amazingly well.","A. I am ashamed of myself.","B. I am not ashamed of myself.","C. I am proud of myself.","D. I am very proud of myself.","E. I am extraordinarily proud of myself.","A. Time passes slowly during most of the things that I do.","B. Time passes quickly during some of the things that I do and slowly for other things.","C. Time passes quickly during most of the things that I do.","D. Time passes quickly during all of the things that I do. ","E. Time passes so quickly during all of the things that I do that I do not even notice it","A. In the grand scheme of things, my existence may hurt the world.","B. My existence has no positive impact on the world.","C. My existence has a small but positive impact on the world.","D. My existence makes the world a better place.","E. My existence has a lasting, large, and positive impact on the world.","A. I do poorly at most things I am doing.","B. I do okay at most things I am doing.","C. I do well at some things I am doing.","D. I do well at most things I am doing.","E. I do really well at whatever I am doing.","A. I have little or no enthusiasm.","B. My enthusiasm level is about average","C. I have a good amount of enthusiasm.","D. I feel enthusiastic doing most things","E. I have enthusiasm in everything I do","A. I hate my work.","B. I dislike my work most of the time.","C. I like my work most days.","D. I really like my work.","E. I truly love my work.","A. I am pessimistic about the future.","B. I am neither optimistic nor pessimistic about the future.","C. I feel somewhat optimistic about the future.","D. I feel quite optimistic about the future.","E. I feel extraordinarily optimistic about the future.","A. I have accomplished little in life.","B. I have accomplished no more in life than most people.","C. I have accomplished a little more in life than most people.","D. I have accomplished more in life than most people.","E. I have accomplished a great deal more in my life than most people.","A. I am unhappy with myself.","B. I am neither happy nor unhappy with myself--I am neutral.","C. I am happy with myself.","D. I am very happy with myself.","E. I could not be any happier with myself.","A. My skills are never challenged by the situations I encounter.","B. My skills are rarely challenged by the situations I encounter.","C. My skills are occasionally challenged by the situations I encounter.","D. My skills are often challenged by the situations I encounter.","E. My skills are always challenged by the situations I encounter.","A. I spend all of my time doing things that are unimportant.","B. I spend most of my time doing things that are not overly important","C. I spend some of my time every day doing things that are important.","D. I spend most of my time every day doing things that are important.","E. I spend all of my time every day doing things that are important.","A. If I were keeping score in life, I would be behind.","B. If I were keeping score in life, I would be about even.","C. If I were keeping score in life, I would be slightly ahead.","D. If I were keeping score in life, I would definitely be ahead.","E. If I were keeping score in life, I would be far ahead.","A. I experience more pain than pleasure.","B. I experience pain and pleasure in equal measure.","C. I experience more pleasure than pain.","D. I experience much more pleasure than pain.","E. My life is filled with pleasure.","A. I do not enjoy my daily routine.","B. I feel neutral about my daily routine.","C. I like my daily routine, but I am happy to get away from it.","D. I like my daily routine so much that I rarely take breaks from it.","E. I like my daily routine so much that I almost never take breaks from it.","A. My life is a bad one.","B. My life is an OK one.","C. My life is a good one.","D. My life is a very good one.","E. My life is a wonderful one."};
    private TextView txtScore;
    public Integer attemptId=-1;
    public int questionnaireStatusID=0;
    public int questionnaireTypeId=0;
    private RelativeLayout rlSave;
    private int getAttemptId=-1;
    private List<GetCohenModel.QuestionnaireHappinessAnswerList> lstResponseData;
    private Integer getHappinessTotalFromApi=0;
    private RelativeLayout rlBack;

    public ArrayList<TempModelHappinessSave> getArrRaheModel() {
        return arrHappy;
    }

    public void setArrRaheModel(TempModelHappinessSave tempModelRaheSave) {
        this.arrHappy.add(tempModelRaheSave);
    }

    public ArrayList<TempModelHappinessSave> arrHappy=new ArrayList<>();
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_happiness,container,false);
        initView(view);
        funToolBar();
        Log.i("called test","1");
        getDataFromPrevious();
      /*  if(attemptId==-1)
            addUpdateAttempt();*/
        if(getAttemptId!=-1)
        {
            attemptId=getAttemptId;
            getQuestionAnswer();
        }
        else
        {
            loadAdapter();
            if(attemptId==-1)
                addUpdateAttempt();
        }
        return view;
    }

    private void initView(View view) {
        rlSave=(RelativeLayout)view.findViewById(R.id.rlSave);
        txtScore=(TextView)view.findViewById(R.id.txtScore);
        rvlList=(RecyclerView)view.findViewById(R.id.rvlList);
        rlBack=view.findViewById(R.id.rlBack);
        rvlList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvlList.setNestedScrollingEnabled(false);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).funDrawer1();

                ((MainActivity) getActivity()).loadFragment(new QuestionariesFragment(), "Questionaries", getArguments());
            }
        });

        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrHappy.size()>=24)
                {
                    Collections.sort(getArrRaheModel(), new Comparator<TempModelHappinessSave>() {
                        @Override
                        public int compare(TempModelHappinessSave o1, TempModelHappinessSave o2) {
                            return o1.getQuestionnaireHappinessMasterId().compareTo(o2.getQuestionnaireHappinessMasterId());
                        }
                    });
                    saveDataApi();
                }

                else
                    Util.showDialog(getActivity(),"Alert","Please Answer All Question",false);
            }
        });
    }
    private void addUpdateAttempt() {
        if (Connection.checkConnection(getActivity())) {
            Log.e("print stat--",questionnaireStatusID+"--"+questionnaireTypeId);
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
            //tmpModelQouestionForRequest.setQuestionnaireTypeId(questionnaireTypeId);
            tmpModelQouestionForRequest.setQuestionnaireTypeId(3);
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
                        // getQuestionAnswer();
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
    private ArrayList<HappinessModel> funFilterData() {


        ArrayList<HappinessModel> arrModelAll=new ArrayList<HappinessModel>();
        ArrayList<HappinessModel> arrModel=new ArrayList<HappinessModel>();
        int index=0;
        for(int i=0;i<24;i++)
        {

            for(int x=0;x<5;x++)
            {

                Category category = new Category(i, (i+1)+"");
                ContentItemHappinessQuestion contentItem = new ContentItemHappinessQuestion();
                contentItem.setCategory(category);
                HappinessQuestionModel happinessQuestionModel=new HappinessQuestionModel();
                happinessQuestionModel.setQuestion(questionSet[index]);
                happinessQuestionModel.setValue(x+1);
                happinessQuestionModel.setId(i+1);
                if(lstResponseData!=null && lstResponseData.size()>0)
                {
                    if(happinessQuestionModel.getValue()==lstResponseData.get(i).getValue())
                        happinessQuestionModel.setSelected(true);
                }

                contentItem.setHappinessQuestionModel(happinessQuestionModel);
                //arrModelTodo.add(contentItem);
                arrModel.add(contentItem);
                index++;
                Log.e("answer--",happinessQuestionModel.getId()+"???"+happinessQuestionModel.getValue()+"??");
            }

        }
        int indexA=0;
        for(int k=0;k<24;k++)
        {
            HeaderHappinessQuestionModel header=new HeaderHappinessQuestionModel();
            header.setHeader((k+1)+"");
            arrModelAll.add(header);
            ArrayList<HappinessModel> arrEach=new ArrayList<>();
            for(int r=0;r<5;r++)
            {
                arrEach.add(arrModel.get(indexA));
                indexA++;
            }
            arrModelAll.addAll(arrEach);
        }


        return arrModelAll;

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
        // //imgFilter.setBackgroundResource(R.drawable.filter);
        imgRightBack.setVisibility(View.GONE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        if(new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true"))        {            if(Util.isSevenDayOver(getActivity()))            {                imgCircleBack.setVisibility(View.VISIBLE);            }else {                imgCircleBack.setVisibility(View.VISIBLE);            }        }else {            if(new SharedPreference(getActivity()).read("SQUADLITE", "").equals("TRUE"))            {                imgCircleBack.setVisibility(View.VISIBLE);            }else {                imgCircleBack.setVisibility(View.VISIBLE);            }        }
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        /*imgLogo.setOnClickListener(new View.OnClickListener() {             @Override             public void onClick(View v) { 		((MainActivity)getActivity()).showPromotionalDialogs();                 if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {                     ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);                 } else { 		                         ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);                 }             }         });*/

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).funDrawer1();

                ((MainActivity) getActivity()).loadFragment(new QuestionariesFragment(), "Questionaries", getArguments());
            }
        });


    }

    public void funCalculteScore(double sum) {
        String formato = String.format("%.2f",sum);
        long var = Math.round(sum);
        txtScore.setText(var+"");
        ///////////////

    }
    private void saveDataApi() {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", sharedPreference.read("UserID", ""));
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

            Log.e("print model size",getArrRaheModel().size()+"?");
            for(int x=0;x<getArrRaheModel().size();x++)
            {
                Log.e("Table",getArrRaheModel().get(x).getQuestionnaireHappinessMasterId()+"----"+getArrRaheModel().get(x).getValue());
            }
            hashReq.put("model", getArrRaheModel());



            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.saveHappyQuestion(hashReq);
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
    private void loadAdapter()
    {
        HappinessQuestionAdapter adapterObj = new HappinessQuestionAdapter(funFilterData(), getActivity(), lstResponseData, HappinessQuestionFragment.this);
        rvlList.setAdapter(adapterObj);
        //adapterObj.setHasStableIds(true);

        if(lstResponseData!=null)
        {
            int total=getHappinessTotalFromApi;
            double totalD=total;
            double weight=totalD/120.00;
            double percentD=weight*100;
            String formato = String.format("%.2f",percentD);
            //holder.txtAns.setText("%.2f",percentD);
            long var = Math.round(percentD);
            txtScore.setText(var+"");
        }

    }
    private void getQuestionAnswer() {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("questionnaireAttemptId", attemptId);
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

                        // updateEditView(response.body().getDetails());
                        lstResponseData=response.body().getDetails().getQuestionnaireHappinessAnswerList();
                        getHappinessTotalFromApi=response.body().getDetails().getTotal();
                        loadAdapter();
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
    @Override
    public void onResume() {
        super.onResume();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.logo1);

    }
}
