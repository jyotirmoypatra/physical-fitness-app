package com.ashysystem.mbhq.fragment.course;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.ExpandableListAdapter;
import com.ashysystem.mbhq.fragment.SetProgramsFragmentNew;
import com.ashysystem.mbhq.model.CourseDetailModel;
import com.ashysystem.mbhq.model.ProgressCourseResponse;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.ExpandableCourseListNew;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseDetailsFragment extends Fragment {
    private int enrollCourseId = 0, weekNo = 0;
    private String name = "";
    private TextView txtHeaderUp, txtQuestion;
    private RecyclerView rvCourseDetail;
    HashMap<Integer, List<CourseDetailModel.ArticleFeedDetail>> hmData;
    HashMap<Integer, List<CourseDetailModel.ArticleFeedDetail>> hmDataAnother;
    List<CourseDetailModel.ArticleFeedDetail> articleFeedDetail;
    private ProgressBar progressBarRest;
    private int userArticleReadCount = 0, articleCount = 0, plainCourseid = 0;
    private boolean isfirstTime = false;
    Bundle fromCourseBundle;
    private int previousWekkNumber = 0;
    String runBy = "";
    private RelativeLayout rlBack;
    SharedPreference sharedPreference;
    private boolean seminarNotification, messageNotification;
    private HashMap<Integer, String> hmThemeKey = new HashMap<>();

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type = "";
    private ImageView imgRefresh;
    ArrayList<Integer> arrWeekNumbers;
    String PLAY_EPISODE_ONE = "";
    String FROMSETPROGRAM = "";
    String FROMTODAYPAGE = "";
    ImageView imgNotification, imgLink;
    int status;
    boolean IS_LIVE_COURSE = false;
    String LIVE_WEBINAR_DAY = "";
    String LIVE_WEBINAR_TIME = "";
    String LIVE_WEBINAR_URL = "";
    String LIVE_WEBINAR_PASSWORD = "";
    String OFFICIAL_START_DATE = "";
    String ENROLLMENT_END_DATE = "";
    String FORUM_URL = "";
    String OTHER_FORUM_URL = "";
    String FB_FORUM_URL = "";
    String MBHQ_FORUM_URL = "";

    String IMAGE_URL = "";

    ProgressDialog progressDialog = null;

    private String TAG = "CourseDetailsFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Log.e("fragment load check","course deatils fragment");
        View view = inflater.inflate(R.layout.fragment_course_detail, container, false);
        sharedPreference = new SharedPreference(getActivity());
        //((MainActivity) getActivity()).funDrawer();
        if (getArguments() != null) {
            // fromCourseBundle=getArguments();
            Bundle bunNew = new Bundle();
            if (getArguments().containsKey("EnrollCourseId"))
                enrollCourseId = getArguments().getInt("EnrollCourseId");
            if (getArguments().containsKey("name"))
                name = getArguments().getString("name");
            if (getArguments().containsKey("isfirstTime"))
                isfirstTime = getArguments().getBoolean("isfirstTime");
            if (getArguments().containsKey("plainCourseid")) {
                plainCourseid = getArguments().getInt("plainCourseid");
            }
            if (getArguments().containsKey("type"))
                type = getArguments().getString("type");

            if (getArguments().containsKey("PLAY_EPISODE_ONE")) {
                PLAY_EPISODE_ONE = getArguments().getString("PLAY_EPISODE_ONE");
            }

            if (getArguments().containsKey("FROMSETPROGRAM")) {
                FROMSETPROGRAM = getArguments().getString("FROMSETPROGRAM");
            }

            if (getArguments().containsKey("FROMTODAYPAGE")) {
                FROMTODAYPAGE = getArguments().getString("FROMTODAYPAGE");
            }
            if (getArguments().containsKey("status")) {
                status = getArguments().getInt("status");
            }

            if (getArguments().containsKey("IsLiveCourse")) {
                IS_LIVE_COURSE = getArguments().getBoolean("IsLiveCourse");
            }


            if (getArguments().containsKey("LiveWebinarDay")) {
                LIVE_WEBINAR_DAY = getArguments().getString("LiveWebinarDay");
            }

            if (getArguments().containsKey("LiveWebinarTime")) {
                LIVE_WEBINAR_TIME = getArguments().getString("LiveWebinarTime");
            }

            if (getArguments().containsKey("LiveWebinarUrl")) {
                LIVE_WEBINAR_URL = getArguments().getString("LiveWebinarUrl");
            }

            if (getArguments().containsKey("LiveWebinarPassword")) {
                LIVE_WEBINAR_PASSWORD = getArguments().getString("LiveWebinarPassword");
            }

            if (getArguments().containsKey("OfficialStartDate")) {
                OFFICIAL_START_DATE = getArguments().getString("OfficialStartDate");
            }

            if (getArguments().containsKey("EnrollmentEndDate")) {
                ENROLLMENT_END_DATE = getArguments().getString("EnrollmentEndDate");
            }

            if (getArguments().containsKey("ForumUrl")) {
                FORUM_URL = getArguments().getString("ForumUrl");
            }

            if (getArguments().containsKey("OtherForumUrl")) {
                OTHER_FORUM_URL = getArguments().getString("OtherForumUrl");
            }

            if (getArguments().containsKey("FBForumUrl")) {
                FB_FORUM_URL = getArguments().getString("FBForumUrl");
            }

            if (getArguments().containsKey("MbhqForumUrl")) {
                MBHQ_FORUM_URL = getArguments().getString("MbhqForumUrl");
            }

            if (getArguments().containsKey("IMAGE_URL")) {
                IMAGE_URL = getArguments().getString("IMAGE_URL");
            }


            bunNew.putInt("EnrollCourseId", enrollCourseId);
            bunNew.putString("name", name);
            bunNew.putInt("plainCourseid", plainCourseid);
            if (getArguments().containsKey("listback"))
                bunNew.putString("listback", "yes");
            fromCourseBundle = bunNew;


        }

        arrWeekNumbers = new ArrayList<>();

        initView(view);
       // ((MainActivity) getActivity()).funDrawer();
        funToolBar();
        if (isfirstTime) {
            String msg = "Welcome to " + name + "\n" +
                    "\n" +
                    "Let's get started immediately\n" +
                    "\n" +
                    "Please watch and 'tick off' each seminar to work your way through the course.\n" +
                    "\n" +
                    "New seminars are released as you go";
            AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
            alertDialogCustom.ShowDialog("Alert", msg, false);
            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                @Override
                public void onDone(String title) {

                    getCourseDetailsFromApi();
                }

                @Override
                public void onCancel(String title) {

                }
            });

        } else {

            int lastCourseId = -1;
            CourseDetailModel lastCourseDetails = null;
            try {

                lastCourseId = Integer.parseInt(sharedPreference.read("LAST_COURSE_ID", ""));

                lastCourseDetails = new Gson().fromJson(sharedPreference.read("LAST_COURSE_DETAILS", ""), CourseDetailModel.class);

                Log.i(TAG, "last Course id :" + lastCourseId + " last course details:" + new Gson().toJson(lastCourseDetails));

            } catch (Exception e) {
                e.printStackTrace();

            } finally {

                if (lastCourseId == enrollCourseId && lastCourseDetails != null) {

                    processCourseDetails(lastCourseDetails);

                } else {

                    getCourseDetailsFromApi();

                }

            }

        }

        if (!sharedPreference.readBoolean("HAS_LIVE_COURSE_DIALOG_SHOWN", "") && IS_LIVE_COURSE) {
            openLiveSemianrDetailDialog();
            sharedPreference.writeBoolean("HAS_LIVE_COURSE_DIALOG_SHOWN", "", true);
        }

        return view;
    }

    private void getProgressApi() {
        if (getActivity() != null)
            if (Connection.checkConnection(getActivity())) {
                /*final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");*/
                SharedPreference sharedPreference = new SharedPreference(getActivity());

                HashMap<String, Object> hashReq = new HashMap<>();
                hashReq.put("UserId", sharedPreference.read("UserID", ""));
                hashReq.put("Key", Util.KEY);
                hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
                hashReq.put("CourseId", plainCourseid);
                hashReq.put("WeekNumber", weekNo);

                FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
                Call<ProgressCourseResponse> serverCall = finisherService.getProgressResponse(hashReq);
                serverCall.enqueue(new Callback<ProgressCourseResponse>() {
                    @Override
                    public void onResponse(Call<ProgressCourseResponse> call, Response<ProgressCourseResponse> response) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                        //Log.e("success", "su");
                        if (response.body() != null) {
                            ProgressCourseResponse resdata = response.body();

                            if (resdata != null)
                                progressBarrUpdate(resdata);
                            else
                                Util.showToast(getActivity(), "An error occurred");
                            //loadEnrollAdapter(lstData.getCourses());
                        }
                    }

                    @Override
                    public void onFailure(Call<ProgressCourseResponse> call, Throwable t) {
                        //Log.e("error", "er");
                        if (progressDialog != null)
                            progressDialog.dismiss();

                    }
                });
            } else {
                Util.showToast(getActivity(), Util.networkMsg);
            }
    }

    private void progressBarrUpdate(ProgressCourseResponse resdata) {
        userArticleReadCount = resdata.getUserArticleReadCount();
        articleCount = resdata.getArticleCount();
        if (userArticleReadCount >= articleCount) {
            /*commented bt sahenita*/
           // Leanplum.track("Learn_Android_Finished a course");
        }
        if (articleCount != 0) {

            //txtQuestion.setText(userArticleReadCount + "/" + articleCount);
            int progress = (userArticleReadCount * 100) / articleCount;
            txtQuestion.setText(progress + " %");
       /* progressBarRest.setBackground(null);
        progressBarRest.setBackgroundResource(R.drawable.btn_pink);*/
            //Log.e("print prog", progress + "?" + (progressBarRest.getMax() - progress));
            //  progressBarRest.setMax(6);
            // progressBarRest.setProgress(progressBarRest.getMax()-progress);
            //progressBarRest.incrementProgressBy(progress);
            progressBarRest.setProgress(progress);
            // progressBarRest.setProgressDrawable(getActivity().getResources().getDrawable(R.drawable.status_bar));
        } else {
            progressBarRest.setProgress(0);
        }

    }

    private void processCourseDetails(CourseDetailModel courseDetailModel) {

        weekNo = courseDetailModel.getSelectedWeekNumber();
        runBy = courseDetailModel.getRunBy();
        if (runBy.equalsIgnoreCase("WEEK_FLOW")) {
            runBy = "WEEK";
        }
        getProgressApi();
        articleFeedDetail = courseDetailModel.getArticleFeedDetail();
        seminarNotification = courseDetailModel.getSeminarNotification();
        messageNotification = courseDetailModel.getMessageNotification();
        status = courseDetailModel.getStatus();
        if (articleFeedDetail != null) {
            List<CourseDetailModel.ArticleFeedDetail> tmp = filterFutureDate(courseDetailModel.getArticleFeedDetail());
            sortdataWeekwise(tmp);
            getProgressApi();

        } else
            Util.showToast(getActivity(), "An error occurred");

    }

    private void getCourseDetailsFromApi() {

        if (Connection.checkConnection(getActivity())) {
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("EnrollCourseId", enrollCourseId);
            hashReq.put("isfirstTime", isfirstTime);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<CourseDetailModel> serverCall = finisherService.getCourseDetail(hashReq);
            serverCall.enqueue(new Callback<CourseDetailModel>() {
                @Override
                public void onResponse(Call<CourseDetailModel> call, Response<CourseDetailModel> response) {
                    //progressDialog.dismiss();
                    //Log.e("success", "su");
                    if (response.body() != null) {
                        CourseDetailModel lstData = response.body();

                        sharedPreference.write("LAST_COURSE_DETAILS", "", new Gson().toJson(response.body()));
                        sharedPreference.write("LAST_COURSE_ID", "", enrollCourseId + "");

                        processCourseDetails(response.body());


                    }
                }

                @Override
                public void onFailure(Call<CourseDetailModel> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private List<CourseDetailModel.ArticleFeedDetail> filterFutureDate(List<CourseDetailModel.ArticleFeedDetail> articleFeedDetail) {
        return articleFeedDetail;

     /*   List<CourseDetailModel.ArticleFeedDetail> sortedList=new ArrayList<>();
        //Log.e("before sort size--",articleFeedDetail.size()+"???");
        for(int x=0;x<articleFeedDetail.size();x++)
        {
            String msgDate=articleFeedDetail.get(x).getReleaseDate();
            //  String []spDate=msgDate.split(" ");
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date getDate=simpleDateFormat.parse(msgDate);
                if(getDate.after(new Date()))
                    continue;
                else
                {
                    sortedList.add(articleFeedDetail.get(x));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        //Log.e("after sort size--",sortedList.size()+"???");
        return sortedList;*/


    }

    private void sortdataWeekwise(List<CourseDetailModel.ArticleFeedDetail> articleFeedDetail) {
        if (type.equals("Week Challenge")) {
            for (int i = 0; i < articleFeedDetail.size(); i++) {
                if (arrWeekNumbers.size() > 0) {
                    if (articleFeedDetail.get(i).getWeekNumber() == previousWekkNumber) {
                        continue;
                    } else {
                        arrWeekNumbers.add(articleFeedDetail.get(i).getWeekNumber());
                        previousWekkNumber = articleFeedDetail.get(i).getWeekNumber();
                    }
                } else {
                    arrWeekNumbers.add(articleFeedDetail.get(i).getWeekNumber());
                    previousWekkNumber = articleFeedDetail.get(i).getWeekNumber();
                }
            }

            hmDataAnother = new HashMap<>();

            for (int i = 0; i < arrWeekNumbers.size(); i++) {
                List<CourseDetailModel.ArticleFeedDetail> inList = new ArrayList<>();
                for (int k = 0; k < articleFeedDetail.size(); k++) {
                    if (articleFeedDetail.get(k).getWeekNumber() == arrWeekNumbers.get(i)) {
                        inList.add(articleFeedDetail.get(k));
                    }
                    if (k == articleFeedDetail.size() - 1) {
                        hmDataAnother.put(arrWeekNumbers.get(i), inList);
                    }
                }
            }
            loadAdapterAnother(hmDataAnother);
            //Log.e("KEYSET", hmDataAnother.keySet().toString() + "??????");
        } else {

            hmData = new HashMap<>();
            for (int k = 0; k < articleFeedDetail.size(); k++) {
                if (hmData.containsKey(k + "")) {
                    continue;
                } else {
                    int key = articleFeedDetail.get(k).getWeekNumber();
                    List<CourseDetailModel.ArticleFeedDetail> inList = new ArrayList<>();
                    for (int p = 0; p < articleFeedDetail.size(); p++) {
                        Integer eachWk = articleFeedDetail.get(p).getWeekNumber();
                        if (eachWk == key)
                            inList.add(articleFeedDetail.get(p));

                    }
                    if (inList.size() > 0) {
                        //Log.e("print array size--",inList.size()+"???");
                        hmData.put(key, inList);
                        hmThemeKey.put(key, inList.get(0).getWeeklyTheme());
                    }
                }
            }
            //Log.e("map sz", hmData.size() + "?");
            for (Integer keyy : hmData.keySet()) {
                //Log.e("print", hmData.get(keyy) + "?" + keyy);
            }
            loadAdapter(hmData);

        }
    }

    private void loadAdapter(HashMap<Integer, List<CourseDetailModel.ArticleFeedDetail>> hmDataN) {
        rvCourseDetail.setAdapter(null);
        List<ExpandableCourseListNew.Item> data = new ArrayList<>();
        Map<Integer, List<CourseDetailModel.ArticleFeedDetail>> hmData = new TreeMap<Integer, List<CourseDetailModel.ArticleFeedDetail>>(hmDataN);
        for (Integer key : hmData.keySet()) {
            ////////////////////Accordian Open for having circuit//////////


            /////////////////////////Accordian Open for having circuit End//////////
            //Log.e("adapter size", data.size() + "?");
            Log.e("KEY", key + ">>>>>>>" + hmThemeKey.get(key) + "??");


            data.add(new ExpandableCourseListNew.Item(ExpandableCourseListNew.HEADER, key + "", type, null, key, runBy, 0, false, "", hmThemeKey.get(key)));
            for (int p = 0; p < hmData.get(key).size(); p++) {
                /*commented by sahenita temporary*/
               data.add(new ExpandableCourseListNew.Item(ExpandableListAdapter.CHILD, hmData.get(key).get(p).getArticleTitle(), type, hmData.get(key).get(p), key, runBy, (p + 1), hmData.get(key).get(p).getAvailable(), hmData.get(key).get(p).getReleaseDate(), ""));
            }


        }
        if (data != null)
            rvCourseDetail.setAdapter(new ExpandableCourseListNew(data, getActivity(), plainCourseid, fromCourseBundle, isfirstTime, getArguments(), PLAY_EPISODE_ONE, IMAGE_URL));
    }

    private void loadAdapterAnother(HashMap<Integer, List<CourseDetailModel.ArticleFeedDetail>> hmData) {
        rvCourseDetail.setAdapter(null);
        List<ExpandableCourseListNew.Item> data = new ArrayList<>();
        Log.e("course list adapter call","adapter course load");
        for (int i = 0; i < hmData.size(); i++) {
            data.add(new ExpandableCourseListNew.Item(ExpandableCourseListNew.HEADER, arrWeekNumbers.get(i) + "", type, null, i, runBy, 0, false, "", ""));
            for (int p = 0; p < hmData.get(arrWeekNumbers.get(i)).size(); p++) {
                /*commented by sahenita temporary*/
                data.add(new ExpandableCourseListNew.Item(ExpandableListAdapter.CHILD, hmData.get(arrWeekNumbers.get(i)).get(p).getArticleTitle(), type, hmData.get(arrWeekNumbers.get(i)).get(p), i, runBy, (p + 1), hmData.get(arrWeekNumbers.get(i)).get(p).getAvailable(), hmData.get(arrWeekNumbers.get(i)).get(p).getReleaseDate(), ""));
            }


        }
        if (data != null)
            rvCourseDetail.setAdapter(new ExpandableCourseListNew(data, getActivity(), plainCourseid, fromCourseBundle, isfirstTime, getArguments(), PLAY_EPISODE_ONE, IMAGE_URL));
    }


    private void initView(View view) {
        imgNotification = view.findViewById(R.id.imgNotification);
        imgLink = view.findViewById(R.id.imgLink);
        rlBack = view.findViewById(R.id.rlBack);
        txtHeaderUp = (TextView) view.findViewById(R.id.txtHeaderUp);
        txtHeaderUp.setText(name);
        rvCourseDetail = (RecyclerView) view.findViewById(R.id.rvCourseDetail);
        progressBarRest = (ProgressBar) view.findViewById(R.id.progressBarRest);
        txtQuestion = (TextView) view.findViewById(R.id.txtQuestion);
        imgRefresh = (ImageView) view.findViewById(R.id.imgRefresh);
        rvCourseDetail.setLayoutManager(new LinearLayoutManager(getActivity()));
        /*rvCourseDetail.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(getActivity())
                        .color(Color.BLACK)
                        .sizeResId(R.dimen.divider)
                        .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                        .build());*/
        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_alert_reset_8_week);
                Button btnYes = (Button) dialog.findViewById(R.id.btnYes);
                Button btnNo = (Button) dialog.findViewById(R.id.btnNo);
                btnYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        refreshApi();
                        dialog.dismiss();
                    }
                });
                btnNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                dialog.show();


            }
        });
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getArguments() != null && getArguments().containsKey("TODAY")) {
                    /*commented by sahenita*/
                   // ((MainActivity) getActivity()).loadFragment(new TodayFragment(), "today", null);
                } else if (getArguments() != null && getArguments().containsKey("NEWHOME")) {
                    /*commented by sahenita*/
                   // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", getArguments());
                } else {
                    if (!FROMSETPROGRAM.equals("")) {
                        //((MainActivity) getActivity()).loadFragment(new SetProgramsFragment(), "SetPrograms", null);
                        if (!FROMTODAYPAGE.equals("")) {
                            /*commented by sahenita*/
                           // ((MainActivity) getActivity()).loadFragment(new TodayFragment(), "Today", null);
                        } else {
                            /*commented by sahenita*/
                           // ((MainActivity) getActivity()).loadFragment(new SetProgramsFragmentNew(), "SetPrograms", null);
                        }
                    } else {
                        Bundle courseDetailBundle = new Bundle();
                        courseDetailBundle.putString("BACKBUTTONCLICKED", "TRUE");
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());
                        ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "Course", courseDetailBundle);
                    }
                }
            }
        });
        imgNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funNotificationDialog();
            }
        });
        imgLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLiveSemianrDetailDialog();
            }
        });

        if (IS_LIVE_COURSE) {
            imgNotification.setVisibility(View.GONE);
            imgLink.setVisibility(View.VISIBLE);
        } else {
            imgNotification.setVisibility(View.VISIBLE);
            imgLink.setVisibility(View.GONE);
        }


    }

    private void openLiveSemianrDetailDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_live_seminar_detail);

        ImageView closeDialogImg = dialog.findViewById(R.id.closeDialogImg);
        ImageView liveWebinarLinkCopyImg = dialog.findViewById(R.id.liveWebinarLinkCopyImg);
        ImageView passwordCopyImg = dialog.findViewById(R.id.passwordCopyImg);
        ImageView forumLinkCopyImg = dialog.findViewById(R.id.forumLinkCopyImg);
        /*ImageView mbhqForumLinkCopyImg = dialog.findViewById(R.id.mbhqForumLinkCopyImg);
        ImageView fbForumLinkCopyImg = dialog.findViewById(R.id.fbForumLinkCopyImg);*/
        TextView officialStartDateTimeTxt = dialog.findViewById(R.id.officialStartDateTimeTxt);
        TextView weeklyWebinarTimingTxt = dialog.findViewById(R.id.weeklyWebinarTimingTxt);
        TextView forumLinkStatic = dialog.findViewById(R.id.forumLinkStatic);
        /*TextView mbhqForumLinkStatic = dialog.findViewById(R.id.mbhqForumLinkStatic);
        TextView fbForumLinkStatic = dialog.findViewById(R.id.fbForumLinkStatic);*/
        TextView passwordTxt = dialog.findViewById(R.id.passwordTxt);
        TextView liveWebinarLinkTxt = dialog.findViewById(R.id.liveWebinarLinkTxt);
        TextView forumLinkTxt = dialog.findViewById(R.id.forumLinkTxt);
        /*Button mbhqForumLinkBtn = dialog.findViewById(R.id.mbhqForumLinkBtn);
        Button fbForumLinkBtn = dialog.findViewById(R.id.fbForumLinkBtn);*/

        SpannableString clickHereString = new SpannableString(getString(R.string.click_here));
        clickHereString.setSpan(new UnderlineSpan(), 0, clickHereString.length(), 0);

        forumLinkTxt.setText(clickHereString);
        liveWebinarLinkTxt.setText(clickHereString);

        try {

            weeklyWebinarTimingTxt.setText("Every " + LIVE_WEBINAR_DAY + " at " + LIVE_WEBINAR_TIME + " AEST");

            SimpleDateFormat desiredDateFormat = new SimpleDateFormat("EEEE, MMMM dd, yyyy", Locale.ENGLISH);
            SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
            forumLinkStatic.setText(name + " Forum :");
            /*fbForumLinkStatic.setText(name+" FB Forum :");
            mbhqForumLinkStatic.setText(name+" MBHQ Forum :");*/

            officialStartDateTimeTxt.setText(desiredDateFormat.format(apiDateFormat.parse(OFFICIAL_START_DATE)));

            passwordTxt.setText(LIVE_WEBINAR_PASSWORD);


        } catch (Exception ex) {
            ex.printStackTrace();
        }

        closeDialogImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        liveWebinarLinkCopyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Efc live webinar link", LIVE_WEBINAR_URL);
                clipboard.setPrimaryClip(clip);

                Util.showToast(getContext(), "Link copied");
            }
        });

        passwordCopyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("Efc live webinar password", LIVE_WEBINAR_PASSWORD);
                clipboard.setPrimaryClip(clip);
                Util.showToast(getContext(), "Password copied");
            }
        });

        forumLinkCopyImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = null;

                if (!OTHER_FORUM_URL.isEmpty()) {
                    clip = ClipData.newPlainText("forum link", OTHER_FORUM_URL);
                } else if (!FB_FORUM_URL.isEmpty()) {
                    clip = ClipData.newPlainText("forum link", FB_FORUM_URL);
                } else if (!MBHQ_FORUM_URL.isEmpty()) {
                    clip = ClipData.newPlainText("forum link", MBHQ_FORUM_URL);
                } else {
                    clip = ClipData.newPlainText("forum link", FORUM_URL);
                }

                clipboard.setPrimaryClip(clip);
                Util.showToast(getContext(), "Link copied");
            }
        });


        liveWebinarLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(LIVE_WEBINAR_URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                getActivity().startActivity(intent);
            }
        });

        forumLinkTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!OTHER_FORUM_URL.isEmpty()) {
                    Uri uri = Uri.parse(OTHER_FORUM_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.android.chrome");
                    requireActivity().startActivity(intent);

                } else if (!FB_FORUM_URL.isEmpty()) {

                    Uri uri = Uri.parse(FB_FORUM_URL);
                    try {
                        ApplicationInfo applicationInfo = getActivity().getPackageManager().getApplicationInfo("com.facebook.katana", 0);
                        if (applicationInfo.enabled) {
                            uri = Uri.parse("fb://facewebmodal/f?href=" + uri);
                        }
                    } catch (PackageManager.NameNotFoundException ignored) {
                        ignored.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);

                    requireActivity().startActivity(intent);

                } else if (!MBHQ_FORUM_URL.isEmpty()) {

                    if (!sharedPreference.read("COMMUNITY_ACCESS_TOKEN", "").equals("")) {

                        Uri uri = Uri.parse(MBHQ_FORUM_URL)
                                .buildUpon()
                                .appendQueryParameter("token", sharedPreference.read("COMMUNITY_ACCESS_TOKEN", ""))
                                .build();

                        //((MainActivity) requireActivity()).clearCacheForParticularFragment(new CommunityFragment());
                        Bundle bun = new Bundle();
                        bun.putString("COMMUNITY_EXTRA_URL", uri.toString());
                        /*commented by sahenita*/
                       // ((MainActivity) requireActivity()).loadFragment(new CommunityFragment(), "CommunityFragment", bun);

                    } else {
                        Log.i("ProgramDetailsFragment", "img click");
                        ((MainActivity) requireActivity()).imgForum.performClick();
                    }
                    dialog.dismiss();

                } else {

                    Uri uri = Uri.parse(FORUM_URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.android.chrome");
                    getActivity().startActivity(intent);
                }
            }
        });


        dialog.show();

    }
/*commented by sahenita*/
    private void funNotificationDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_course_notification);
 dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.dialog_course_notification);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        ImageView imgBack = dialog.findViewById(R.id.imgBack);
        CheckBox chkActive = dialog.findViewById(R.id.chkActive);
        CheckBox chkPause = dialog.findViewById(R.id.chkPause);
        CheckBox chkCompleted = dialog.findViewById(R.id.chkCompleted);
        CheckBox chkMsg = dialog.findViewById(R.id.chkMsg);
        CheckBox chkSeminar = dialog.findViewById(R.id.chkSeminar);
        RelativeLayout btnApply = dialog.findViewById(R.id.btnApply);
        RelativeLayout rlReset = dialog.findViewById(R.id.rlReset);
        rlReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialogN = new Dialog(getActivity(), R.style.DialogThemeAnother);
                dialog.setContentView(R.layout.dialog_alert_course_start);
                TextView txtYes = dialog.findViewById(R.id.txtYes);
                TextView txtNo = dialog.findViewById(R.id.txtNo);
                dialog.show();
                txtYes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        resetApiApi(4, chkMsg.isChecked(), chkActive.isChecked(), dialogN);
                    }
                });
                txtNo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogN.dismiss();
                        dialog.dismiss();
                    }
                });

            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = 0;
                if (chkActive.isChecked())
                    status = 1;
                else if (chkPause.isChecked())
                    status = 2;
                else if (chkCompleted.isChecked())
                    status = 3;
                if (status > 0)
                    statusApi(status, chkMsg.isChecked(), chkSeminar.isChecked(), dialog);


            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (status == 1)
            chkActive.setChecked(true);
        else if (status == 2)
            chkPause.setChecked(true);
        else if (status == 3)
            chkCompleted.setChecked(true);
        if (messageNotification)
            chkMsg.setChecked(true);
        if (seminarNotification)
            chkSeminar.setChecked(true);
        chkActive.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkPause.setChecked(false);
                    chkCompleted.setChecked(false);
                }
            }
        });
        chkPause.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkActive.setChecked(false);
                    chkCompleted.setChecked(false);
                }
            }
        });
        chkCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chkActive.setChecked(false);
                    chkPause.setChecked(false);
                }
            }
        });
        dialog.show();
    }
    /*commented by sahenita*/
    private void resetApiApi(int status, boolean chkMsgChecked, boolean chkSeminarChecked, Dialog dialog) {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("CourseId", plainCourseid);
            hashReq.put("Status", status);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.updateCourseStats(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    //Log.e("success", "su");
                    if (response.body() != null) {
                        if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                            dialog.dismiss();
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());
                            ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "CourseFragment", getArguments());

                        } else {

                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    /*commented by sahenita*/
    private void statusApi(int status, boolean chkMsgChecked, boolean chkSeminarChecked, Dialog dialog) {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("CourseId", plainCourseid);
            hashReq.put("Status", status);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.updateCourseStats(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    //Log.e("success", "su");
                    if (response.body() != null) {
                        if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                            msgApi(chkMsgChecked, chkSeminarChecked, dialog);

                        } else {

                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
/*commented by sahenita*/
    private void msgApi(boolean statusMsg, boolean statusSeminar, Dialog dialog) {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("CourseId", plainCourseid);
            hashReq.put("On", statusMsg);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.toggleMessageNotificationFlag(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    //Log.e("success", "su");
                    if (response.body() != null) {
                        if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                            seminarApi(statusMsg, statusSeminar, dialog);

                        } else {

                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
/*commented by sahenita*/
    private void seminarApi(boolean statusMsg, boolean statusSeminar, Dialog dialog) {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("CourseId", plainCourseid);
            hashReq.put("Status", statusSeminar);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.toggleSeminarNotificationFlag(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    //Log.e("success", "su");
                    if (response.body() != null) {
                        if (response.body() != null && response.body().get("SuccessFlag").getAsBoolean()) {
                            dialog.dismiss();
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());
                            ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "CourseFragment", getArguments());


                        } else {

                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
/*commented by sahenita*/
    private void refreshApi() {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("CourseId", plainCourseid);

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<JsonObject> serverCall = finisherService.refreshCourse(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    //Log.e("success", "su");
                    if (response.body() != null) {
                        if (response.body() != null && response.body().get("Success").getAsBoolean()) {
                            getCourseDetailsFromApi();

                        } else {

                        }

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
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
        imgRightBack.setVisibility(View.VISIBLE);
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
        if (type.equals("Week Challenge")) {
            imgHelp.setVisibility(View.GONE);
            imgRefresh.setVisibility(View.VISIBLE);

        } else {
            imgHelp.setVisibility(View.GONE);
            imgRefresh.setVisibility(View.GONE);
        }

        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        /*imgLogo.setOnClickListener(new View.OnClickListener() {             @Override             public void onClick(View v) { 		((MainActivity)getActivity()).showPromotionalDialogs();                 if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {                     ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);                 } else { 		                         ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "home", null);                 }             }         });*/
        imgRightBack.setVisibility(View.GONE);
        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getArguments() != null && getArguments().containsKey("TODAY")) {
                    /*commented by sahenita*/
                   // ((MainActivity) getActivity()).loadFragment(new TodayFragment(), "today", null);
                } else if (getArguments().containsKey("NEWHOME")) {
                    /*commented by sahenita*/
                   // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", getArguments());
                } else {
                    if (!FROMSETPROGRAM.equals("")) {
                        //((MainActivity) getActivity()).loadFragment(new SetProgramsFragment(), "SetPrograms", null);
                        if (!FROMTODAYPAGE.equals("")) {
                            /*commented by sahenita*/
                            //((MainActivity) getActivity()).loadFragment(new TodayFragment(), "Today", null);
                        } else {
                            ((MainActivity) getActivity()).loadFragment(new SetProgramsFragmentNew(), "SetPrograms", null);
                        }
                    } else {
                        ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "Course", null);
                    }
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    if (!FROMSETPROGRAM.equals("")) {
                        //((MainActivity) getActivity()).loadFragment(new SetProgramsFragment(), "SetPrograms", null);
                        if (!FROMTODAYPAGE.equals("")) {
                            /*commented by sahenita*/
                            //((MainActivity) getActivity()).loadFragment(new TodayFragment(), "Today", null);
                        } else {
                            ((MainActivity) getActivity()).loadFragment(new SetProgramsFragmentNew(), "SetPrograms", null);
                        }
                    } else {
                        ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "Course", null);
                    }

                    return true;

                }

                return false;
            }
        });
    }

}
