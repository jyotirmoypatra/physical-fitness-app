package com.ashysystem.mbhq.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.fragment.course.CourseArticleDetailsNewFragment;
import com.ashysystem.mbhq.fragment.course.CourseDetailsFragment;
import com.ashysystem.mbhq.fragment.course.ProgramDetailsFragment;
import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.model.response.AddCourseResponseModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android-arindam on 22/2/17.
 */

public class AvailableCourseAdapter extends RecyclerView.Adapter<AvailableCourseAdapter.ViewHolder> {
    List<AvailableCourseModel.Course> lstData;
    Context context;
    private String origin = "";
    String PLAY_EPISODE_ONE = "";
    SharedPreference sharedPreference;
    String FROMSETPROGRAM = "";
    String FROMTODAYPAGE = "";
    int dynamicSize = 0;
    ProgramType programType = ProgramType.MY_PROGRAMS;

    public enum ProgramType {
        LIVE_PROGRAM,
        PAID_PROGRAMS,
        MY_PROGRAMS,
        MEMBER_ONLY_PROGRAMS,
        MASTERCLASS_PROGRAMS,
        PAID_MASTERCLASS_PROGRAMS,
        PODCAST_PROGRAMS
    }

    public AvailableCourseAdapter(Context context, List<AvailableCourseModel.Course> lstData, ProgramType programType, String origin, String PLAY_EPISODE_ONE, String FROMSETPROGRAM, String FROMTODAYPAGE, int dynamicSize) {
        this.lstData = lstData;
        this.context = context;
        this.origin = origin;
        this.dynamicSize = dynamicSize;
        this.PLAY_EPISODE_ONE = PLAY_EPISODE_ONE;
        this.FROMSETPROGRAM = FROMSETPROGRAM;
        this.FROMTODAYPAGE = FROMTODAYPAGE;
        this.programType = programType;
        sharedPreference = new SharedPreference(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_course_list, null);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Log.e("index no--", position + "???");
        if (lstData.size() > 0 && position < lstData.size()) {

            holder.txtCourseNmae.setText(lstData.get(position).getCourseName());
            holder.txtModule.setText(lstData.get(position).getAuthorName());
            holder.imgBanner.setClipToOutline(true);

            Glide.with(context).load(lstData.get(position).getImageURL2())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.empty_image_old)
                    .error(R.drawable.notfound)
                    .dontTransform()
                    .dontAnimate()
                    .into(holder.imgBanner);


        }
        Log.e("print status--", lstData.get(position).getStatus() + "---" + position + "???");

        holder.viewRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (programType == ProgramType.PODCAST_PROGRAMS) {

                    openPodcast(lstData.get(position).getPurchaseUrl());

                } else {

                    Bundle bundle = new Bundle();
                    bundle.putInt("EnrollCourseId", lstData.get(position).getUserSquadCourseId());
                    bundle.putInt("plainCourseid", lstData.get(position).getCourseId());
                    bundle.putString("IMAGE_URL", lstData.get(position).getImageURL2());
                    bundle.putString("name", lstData.get(position).getCourseName());
                    bundle.putString("type", lstData.get(position).getCourseType());
                    bundle.putInt("status", lstData.get(position).getStatus());

                    if (lstData.get(position).getIsLiveCourse()) {
                        bundle.putString("LiveWebinarDay", lstData.get(position).getLiveWebinarDay());
                        bundle.putString("LiveWebinarTime", lstData.get(position).getLiveWebinarTime());
                        bundle.putString("LiveWebinarUrl", lstData.get(position).getLiveWebinarUrl());
                        bundle.putString("LiveWebinarPassword", lstData.get(position).getLiveWebinarPassword());

                        bundle.putString("OfficialStartDate", lstData.get(position).getOfficialStartDate());
                        bundle.putString("EnrollmentEndDate", lstData.get(position).getEnrollmentEndDate());

                        bundle.putString("ForumUrl", lstData.get(position).getForumUrl());


                        bundle.putString("OtherForumUrl", lstData.get(position).getOtherForumUrl());
                        bundle.putString("FBForumUrl", lstData.get(position).getFBForumUrl());
                        bundle.putString("MbhqForumUrl", lstData.get(position).getMbhqForumUrl());

                        bundle.putBoolean("IsLiveCourse", lstData.get(position).getIsLiveCourse());

                    }

                    if (!PLAY_EPISODE_ONE.equals("")) {
                        bundle.putString("PLAY_EPISODE_ONE", "TRUE");
                    }

                    if (!FROMSETPROGRAM.equals("")) {
                        bundle.putString("FROMSETPROGRAM", FROMSETPROGRAM);
                    }

                    if (!FROMTODAYPAGE.equals("")) {
                        bundle.putString("FROMTODAYPAGE", FROMTODAYPAGE);
                    }

                    openProgramDetailsPage(lstData.get(position), bundle);

                }
            }
        });

        if (programType == ProgramType.MEMBER_ONLY_PROGRAMS) {
            holder.txtState.setText("JOIN");
        } else if (programType == ProgramType.PODCAST_PROGRAMS) {
            holder.txtState.setText("LISTEN NOW");
        } else {

            switch (lstData.get(position).getStatus()) {
                case 0: {
                    if (programType == ProgramType.LIVE_PROGRAM || programType == ProgramType.PAID_PROGRAMS || programType == ProgramType.MASTERCLASS_PROGRAMS || programType == ProgramType.PAID_MASTERCLASS_PROGRAMS) {
                        holder.txtState.setText("LEARN MORE");
                    } else {
                        holder.txtState.setText("START");
                    }
                    holder.rlState.setBackground(context.getResources().getDrawable(R.drawable.capsule_light_green));
                    holder.txtState.setTextColor(ContextCompat.getColor(holder.viewRoot.getContext(), R.color.black));
                    break;
                }
                case 1: {
                    holder.txtState.setText("IN PROGRESS");
                    holder.rlState.setBackground(context.getResources().getDrawable(R.drawable.drawble_green_capsule_empty));
                    holder.txtState.setTextColor(ContextCompat.getColor(holder.viewRoot.getContext(), R.color.black));
                    /*holder.txtState.setTextColor(Color.parseColor("#6fcdcd"));*/
                    break;
                }
                case 2: {
                    holder.txtState.setText("PAUSED");
                    break;
                }
                case 3: {
                    holder.txtState.setText("COMPLETED");
                    holder.rlState.setBackground(context.getResources().getDrawable(R.drawable.capsule_grey));
                    holder.txtState.setTextColor(Color.parseColor("#6fcdcd"));
                    holder.txtState.setText("COMPLETED");
                    break;
                }
                default: {

                }
            }

        }

        holder.rlState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ((MainActivity) context).refershGamePoint(context);

                if (sharedPreference.read("USEREMAIL", "").equals("")) {

                    /*commented by sahenita temporary*/
                   // ((MainActivity) context).openDialogForRegisterUser(null, "");
                } else if (programType == ProgramType.PODCAST_PROGRAMS) {
                    openPodcast(lstData.get(position).getPurchaseUrl());
                } else if (programType == ProgramType.MASTERCLASS_PROGRAMS && lstData.get(position).getStatus() == 1 || lstData.get(position).getStatus() == 2) {

                    Bundle fromCourseBundle = new Bundle();
                    fromCourseBundle.putInt("COURSEID", lstData.get(position).getUserSquadCourseId());
                    fromCourseBundle.putInt("ARTICLEID", lstData.get(position).getFirstArticleId());
                    fromCourseBundle.putString("AUTHOR", lstData.get(position).getAuthorName());
                    fromCourseBundle.putString("type", lstData.get(position).getCourseType());
                    fromCourseBundle.putString("origin", "CourseFragment");
                    fromCourseBundle.putString("IMAGE_URL", lstData.get(position).getImageURL2());
                    Log.i("AvailableCourseAdapter", fromCourseBundle.toString());
                    Log.i("AvailableCourseAdapter", fromCourseBundle.getString("origin"));
                    ((MainActivity) context).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                    ((MainActivity) context).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", fromCourseBundle);

                } else {

                    if (lstData.get(position).getStatus() == 0) {
                        //"Start
                        //openAlertDialogForStart(lstData.get(position), lstData.get(position).getCourseType());

                        Bundle bundle = new Bundle();
                        bundle.putInt("EnrollCourseId", lstData.get(position).getUserSquadCourseId());
                        bundle.putInt("plainCourseid", lstData.get(position).getCourseId());
                        bundle.putString("IMAGE_URL", lstData.get(position).getImageURL2());
                        bundle.putString("name", lstData.get(position).getCourseName());
                        bundle.putString("type", lstData.get(position).getCourseType());
                        bundle.putInt("status", lstData.get(position).getStatus());

                        if (lstData.get(position).getIsLiveCourse()) {
                            bundle.putString("LiveWebinarDay", lstData.get(position).getLiveWebinarDay());
                            bundle.putString("LiveWebinarTime", lstData.get(position).getLiveWebinarTime());
                            bundle.putString("LiveWebinarUrl", lstData.get(position).getLiveWebinarUrl());
                            bundle.putString("LiveWebinarPassword", lstData.get(position).getLiveWebinarPassword());

                            bundle.putString("OfficialStartDate", lstData.get(position).getOfficialStartDate());
                            bundle.putString("EnrollmentEndDate", lstData.get(position).getEnrollmentEndDate());

                            bundle.putString("ForumUrl", lstData.get(position).getForumUrl());


                            bundle.putString("OtherForumUrl", lstData.get(position).getOtherForumUrl());
                            bundle.putString("FBForumUrl", lstData.get(position).getFBForumUrl());
                            bundle.putString("MbhqForumUrl", lstData.get(position).getMbhqForumUrl());

                            bundle.putBoolean("IsLiveCourse", lstData.get(position).getIsLiveCourse());

                        }

                        if (!PLAY_EPISODE_ONE.equals("")) {
                            bundle.putString("PLAY_EPISODE_ONE", "TRUE");
                        }

                        if (!FROMSETPROGRAM.equals("")) {
                            bundle.putString("FROMSETPROGRAM", FROMSETPROGRAM);
                        }

                        if (!FROMTODAYPAGE.equals("")) {
                            bundle.putString("FROMTODAYPAGE", FROMTODAYPAGE);
                        }
                        openProgramDetailsPage(lstData.get(position), bundle);

                    } else if (lstData.get(position).getStatus() == 1) {
                        //"InProgress"

                        //((MainActivity) context).clearCacheForParticularFragment(new CourseDetailsFragment());

                        CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("EnrollCourseId", lstData.get(position).getUserSquadCourseId());
                        bundle.putInt("plainCourseid", lstData.get(position).getCourseId());
                        bundle.putString("name", lstData.get(position).getCourseName());
                        bundle.putString("type", lstData.get(position).getCourseType());
                        bundle.putInt("status", lstData.get(position).getStatus());
                        bundle.putString("IMAGE_URL", lstData.get(position).getImageURL2());
                        if (!PLAY_EPISODE_ONE.equals("")) {
                            bundle.putString("PLAY_EPISODE_ONE", "TRUE");
                        }

                        if (!FROMSETPROGRAM.equals("")) {
                            bundle.putString("FROMSETPROGRAM", FROMSETPROGRAM);
                        }

                        if (!FROMTODAYPAGE.equals("")) {
                            bundle.putString("FROMTODAYPAGE", FROMTODAYPAGE);
                        }

                        if (lstData.get(position).getIsLiveCourse()) {
                            bundle.putString("LiveWebinarDay", lstData.get(position).getLiveWebinarDay());
                            bundle.putString("LiveWebinarTime", lstData.get(position).getLiveWebinarTime());
                            bundle.putString("LiveWebinarUrl", lstData.get(position).getLiveWebinarUrl());
                            bundle.putString("LiveWebinarPassword", lstData.get(position).getLiveWebinarPassword());

                            bundle.putString("OfficialStartDate", lstData.get(position).getOfficialStartDate());
                            bundle.putString("EnrollmentEndDate", lstData.get(position).getEnrollmentEndDate());

                            bundle.putString("ForumUrl", lstData.get(position).getForumUrl());

                            bundle.putString("OtherForumUrl", lstData.get(position).getOtherForumUrl());
                            bundle.putString("FBForumUrl", lstData.get(position).getFBForumUrl());
                            bundle.putString("MbhqForumUrl", lstData.get(position).getMbhqForumUrl());


                            bundle.putBoolean("IsLiveCourse", lstData.get(position).getIsLiveCourse());

                        }

                        courseDetailsFragment.setArguments(bundle);
                        ((MainActivity) context).loadFragment(courseDetailsFragment, "detail", null);

                    } else if (lstData.get(position).getStatus() == 2) {
                        // "Paused"

                        //((MainActivity) context).clearCacheForParticularFragment(new CourseDetailsFragment());

                        CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("EnrollCourseId", lstData.get(position).getUserSquadCourseId());
                        bundle.putInt("plainCourseid", lstData.get(position).getCourseId());
                        bundle.putString("name", lstData.get(position).getCourseName());
                        bundle.putString("type", lstData.get(position).getCourseType());
                        bundle.putInt("status", lstData.get(position).getStatus());
                        bundle.putString("IMAGE_URL", lstData.get(position).getImageURL2());
                        if (!PLAY_EPISODE_ONE.equals("")) {
                            bundle.putString("PLAY_EPISODE_ONE", "TRUE");
                        }

                        if (!FROMSETPROGRAM.equals("")) {
                            bundle.putString("FROMSETPROGRAM", FROMSETPROGRAM);
                        }

                        if (!FROMTODAYPAGE.equals("")) {
                            bundle.putString("FROMTODAYPAGE", FROMTODAYPAGE);
                        }

                        if (lstData.get(position).getIsLiveCourse()) {
                            bundle.putString("LiveWebinarDay", lstData.get(position).getLiveWebinarDay());
                            bundle.putString("LiveWebinarTime", lstData.get(position).getLiveWebinarTime());
                            bundle.putString("LiveWebinarUrl", lstData.get(position).getLiveWebinarUrl());
                            bundle.putString("LiveWebinarPassword", lstData.get(position).getLiveWebinarPassword());

                            bundle.putString("OfficialStartDate", lstData.get(position).getOfficialStartDate());
                            bundle.putString("EnrollmentEndDate", lstData.get(position).getEnrollmentEndDate());

                            bundle.putString("ForumUrl", lstData.get(position).getForumUrl());

                            bundle.putString("OtherForumUrl", lstData.get(position).getOtherForumUrl());
                            bundle.putString("FBForumUrl", lstData.get(position).getFBForumUrl());
                            bundle.putString("MbhqForumUrl", lstData.get(position).getMbhqForumUrl());

                            bundle.putBoolean("IsLiveCourse", lstData.get(position).getIsLiveCourse());

                        }

                        courseDetailsFragment.setArguments(bundle);
                        ((MainActivity) context).loadFragment(courseDetailsFragment, "detail", null);

                    } else if (lstData.get(position).getStatus() == 3) {
                        //"Completed";
                        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(context);
                        alertDialogCustom.ShowDialog("Alert!", "Do you want to enroll in this course again?", true);
                        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                            @Override
                            public void onDone(String title) {
                                openAlertDialogForStart(lstData.get(position), lstData.get(position).getCourseType());
                            }

                            @Override
                            public void onCancel(String title) {

                            }
                        });

                    }

                }


            }
        });


        if (lstData.size() > 0 && position < lstData.size()) {
            if (lstData.get(position).getCourseType().equals("Week Challenge")) {
                if (origin.equals("chk") || origin.equals("learn"))
                    holder.rlState.performClick();
            }

            if (lstData.get(position).getCourseName().equalsIgnoreCase(FROMSETPROGRAM)) {
                holder.rlState.performClick();
            }
        }


    }

    private void openProgramDetailsPage(AvailableCourseModel.Course course, Bundle bundle) {

        ProgramDetailsFragment programDetailsFragment = ProgramDetailsFragment.newInstance(course);
        ((MainActivity) context).clearCacheForParticularFragment(programDetailsFragment);
        Bundle bundle1 = new Bundle();

        if (bundle != null) {
            Log.i(this.getClass().getSimpleName(), "bundle is not null");
            Log.i(this.getClass().getSimpleName(), bundle.toString());

            Log.i(this.getClass().getSimpleName(), bundle.getInt("EnrollCourseId") + "");

            Log.i(this.getClass().getSimpleName(), bundle.getInt("plainCourseid") + "");

            bundle1.putBundle("BUNDLE_FOR_COURSE_DETAIL_FRAGMENT", bundle);
            programDetailsFragment.setArguments(bundle1);
        } else {
            Log.i(this.getClass().getSimpleName(), "bundle is null");
        }

        ((MainActivity) context).loadFragment(programDetailsFragment, "Program Details Fragment", bundle1);

    }

    private void openAlertDialogForStart(AvailableCourseModel.Course course, String courseType) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        c.add(Calendar.DATE, 7);
        System.out.println("Date " + c.getTime());
        Log.e("date print--", c.getTime() + "??");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Dialog dialog = new Dialog(context, R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_reset_course);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlNextWeek = dialog.findViewById(R.id.rlNextWeek);
        RelativeLayout rlToday = dialog.findViewById(R.id.rlToday);
        TextView txtNextWeekDate = dialog.findViewById(R.id.txtNextWeekDate);
        txtNextWeekDate.setText("Next Monday \n(" + simpleDateFormat.format(c.getTime()) + ")");
        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                makejson(course, getTodayDate(), false);
            }
        });
        rlNextWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                c.add(Calendar.DATE, 7);
                System.out.println("Date " + c.getTime());
                Log.e("date print--", c.getTime() + "??");
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
                String cDate = simpleDateFormat.format(c.getTime());
                dialog.dismiss();
                makejson(course, cDate, false);
            }
        });
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void makejson(AvailableCourseModel.Course course, String cDate, Boolean isMasterclassProgram) {
        JSONObject rootJson = new JSONObject();
        JSONObject courseObj = new JSONObject();
        try {
            SharedPreference sharedPreference = new SharedPreference(context);
            rootJson.put("UserID", sharedPreference.read("UserID", ""));
            rootJson.put("Key", Util.KEY);
            rootJson.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            courseObj.put("CourseType", course.getCourseType());
            courseObj.put("isAllArticleRead", course.getIsAllArticleRead());
            courseObj.put("CourseName", course.getCourseName());
            courseObj.put("UserSquadCourseId", course.getUserSquadCourseId());
            courseObj.put("isPeriodFinished", course.getIsPeriodFinished());
            courseObj.put("CourseStartDate", cDate);
            courseObj.put("IsEnroll", course.getIsEnroll());
            courseObj.put("CourseId", course.getCourseId());
            courseObj.put("WeekNumber", course.getWeekNumber());
            rootJson.put("CourseList", courseObj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        HashMap<String, Object> queryHm = new HashMap<>();
        //queryHm=(HashMap<String, Object>)receiveBundle.getSerializable("data");
        try {
            queryHm = (HashMap<String, Object>) Util.jsonToMap(rootJson);
            addCourseApi(queryHm, course, isMasterclassProgram);
            Log.e("print json", queryHm + "?" + rootJson.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getTodayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        Date today = new Date();
        return format.format(today);
    }

    @Override
    public int getItemCount() {
        try {
            //return lstData.size();
            Log.e("print dynamic size--", lstData.size() + "??");
            return dynamicSize;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private void openPodcast(String purchaseurl) {
//        Uri uri = Uri.parse("https://anchor.fm/mindbodyhq");
        Uri uri = Uri.parse(purchaseurl);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.android.chrome");
        context.startActivity(intent);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCourseNmae, txtCourseType, txtModule, txtState, txtCompleted;
        //txtAction;
        // public ImageView imgAction,imgInfo;
        public ViewGroup viewRoot;
        public ImageView imgBanner, lockIcon, coverImage;
        public RelativeLayout rlState;


        //llAction;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtCourseNmae = (TextView) itemView.findViewById(R.id.txtCourseNmae);
            this.txtCourseType = (TextView) itemView.findViewById(R.id.txtCourseType);
            this.imgBanner = (ImageView) itemView.findViewById(R.id.imgProgrambanner);
            this.txtModule = (TextView) itemView.findViewById(R.id.txtModule);
            //this.lockIcon = (ImageView) itemView.findViewById(R.id.lockIcon);

            //this.txtAction = (TextView) itemView.findViewById(R.id.txtAction);
            // this.imgAction = (ImageView) itemView.findViewById(R.id.imgAction);
            viewRoot = (ViewGroup) itemView.findViewById(R.id.viewRoot);
            // this.imgInfo=(ImageView)itemView.findViewById(R.id.imgInfo);
            //llAction = (LinearLayout) itemView.findViewById(R.id.llAction);
            txtState = (TextView) itemView.findViewById(R.id.txtState);
            txtCompleted = (TextView) itemView.findViewById(R.id.txtCompleted);
            rlState = (RelativeLayout) itemView.findViewById(R.id.rlState);
        }
    }

    private void addCourseApi(HashMap<String, Object> queryHm, final AvailableCourseModel.Course course, Boolean isMasterClass) {

        if (Connection.checkConnection(context)) {
            final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(context);
            sharedPreference.writeBoolean("COURSE_LIST_SHOULD_RENEW", "", true);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(context);
            Call<AddCourseResponseModel> serverCall = finisherService.addCourse(queryHm);
            serverCall.enqueue(new Callback<AddCourseResponseModel>() {
                @Override
                public void onResponse(Call<AddCourseResponseModel> call, Response<AddCourseResponseModel> response) {
                    progressDialog.dismiss();
                    Log.e("success", "su");
                    if (response.body() != null) {
                        final AddCourseResponseModel resData = response.body();

                        if (resData != null) {
                            if (resData.getNewData() != null) {

                                if (isMasterClass) {

                                    Bundle fromCourseBundle = new Bundle();
                                    fromCourseBundle.putInt("COURSEID", course.getUserSquadCourseId());
                                    fromCourseBundle.putInt("ARTICLEID", course.getFirstArticleId());
                                    fromCourseBundle.putString("AUTHOR", course.getAuthorName());
                                    fromCourseBundle.putString("type", course.getCourseType());
                                    fromCourseBundle.putString("origin", "CourseFragment");
                                    fromCourseBundle.putString("IMAGE_URL", course.getImageURL2());
                                    //((MainActivity) context).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                                    ((MainActivity) context).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", fromCourseBundle);

                                } else {

                                    int newCourseId = resData.getNewData();
                                    CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
                                    Bundle bundle = new Bundle();
                                    bundle.putInt("EnrollCourseId", newCourseId);
                                    bundle.putString("name", course.getCourseName());
                                    bundle.putString("type", course.getCourseType());
                                    bundle.putInt("plainCourseid", course.getCourseId());
                                    bundle.putBoolean("isfirstTime", true);
                                    bundle.putInt("status", course.getStatus());
                                    bundle.putString("IMAGE_URL", course.getImageURL2());
                                    courseDetailsFragment.setArguments(bundle);
                                    ((MainActivity) context).loadFragment(courseDetailsFragment, "detail", null);
                                }

                            } else
                                Util.showToast(context, "An error occurred");

                        } else
                            Util.showToast(context, "An error occurred");
                        //loadEnrollAdapter(lstData.getCourses());
                    }
                }

                @Override
                public void onFailure(Call<AddCourseResponseModel> call, Throwable t) {
                    Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(context, Util.networkMsg);
        }
    }
}
