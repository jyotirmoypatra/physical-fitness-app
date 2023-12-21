package com.ashysystem.mbhq.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.activity.PlaySoundActivity;
import com.ashysystem.mbhq.fragment.CourseDetailsFragment;

import com.ashysystem.mbhq.fragment.meditation.MeditationDetailsNew;

import com.ashysystem.mbhq.model.AvailableCourseModel;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.response.AddCourseResponseModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android-arindam on 22/2/17.
 */

public class MeditationCourseAdapter extends RecyclerView.Adapter<MeditationCourseAdapter.ViewHolder> {
    ArrayList<MeditationCourseModel.Webinar> lstData = new ArrayList<>();
    ArrayList<MeditationCourseModel.Webinar> fullData = new ArrayList<MeditationCourseModel.Webinar>();
    Context context;
    private String origin = "";
    String PLAY_EPISODE_ONE = "";
    SharedPreference sharedPreference;
    String FROMSETPROGRAM = "";
    String FROMTODAYPAGE = "";
    int dynamicSize = 0;
    boolean isDownloadedMeditations;
    ProgressDialog progressDialog;
    FinisherServiceImpl finisherService;
    MeditationCourseListener meditationCourseListener;
    MeditationCourseLikeListener meditationCourseLikeListener;

    String TAG = "MeditationCourseAdapter";

    public interface MeditationCourseListener {
        void deleteMeditation(MeditationCourseModel.Webinar course);
    }

    public interface MeditationCourseLikeListener {
        void toggleLike(MeditationCourseModel.Webinar course);
    }


    public MeditationCourseAdapter(Context context, List<MeditationCourseModel.Webinar> lstData, int dynamicSize, boolean isDownloadedMeditations) {
        this.isDownloadedMeditations = isDownloadedMeditations;
        this.lstData.addAll(lstData);
        this.fullData.addAll(lstData);
        this.context = context;
        this.dynamicSize = dynamicSize;
        sharedPreference = new SharedPreference(context);
        finisherService = new FinisherServiceImpl(this.context);
    }

    public void setMeditationListener(MeditationCourseListener meditationListener) {
        this.meditationCourseListener = meditationListener;
    }

    public void setMeditationLikeListener(MeditationCourseLikeListener meditationCourseLikeListener) {
        this.meditationCourseLikeListener = meditationCourseLikeListener;
    }

    public void search(String searchString) {
        lstData.clear();
        dynamicSize = 0;
        String searchLower = searchString.toLowerCase();
        String searchUpper = searchString.toUpperCase();
        if (searchString.equals("")) {
            lstData.addAll(fullData);
        } else {
            for (int x = 0; x < fullData.size(); x++) {
                Log.i("med_tag","1");
                Log.i("med_tag",searchLower);
                Log.i("med_tag",searchUpper);
                if(fullData.get(x).getTags().size()>1){
                    Log.i("med_tag","2");
                    if (fullData.get(x).getEventName().toLowerCase().contains(searchString.toLowerCase()) ||fullData.get(x).getTags().contains(searchLower)||fullData.get(x).getTags().contains(searchUpper)||
                            fullData.get(x).getPresenterName().toLowerCase().contains(searchString.toLowerCase())||
                            fullData.get(x).getTags().get(0).toLowerCase().contains(searchLower)||
                            fullData.get(x).getTags().get(1).toLowerCase().contains(searchLower)
                            ) {

                        lstData.add(fullData.get(x));
                    }
                }else{
                    Log.i("med_tag","3");
                    if (fullData.get(x).getEventName().toLowerCase().contains(searchString.toLowerCase()) ||fullData.get(x).getTags().contains(searchLower)||fullData.get(x).getTags().contains(searchUpper)||
                            fullData.get(x).getPresenterName().toLowerCase().contains(searchString.toLowerCase())||
                            fullData.get(x).getTags().get(0).toLowerCase().contains(searchLower)
                    ) {

                        lstData.add(fullData.get(x));
                    }
                }

              /*  if (fullData.get(x).getEventName().toLowerCase().contains(searchString.toLowerCase()) ||fullData.get(x).getTags().contains(searchLower)||fullData.get(x).getTags().contains(searchUpper)||
                        fullData.get(x).getPresenterName().toLowerCase().contains(searchString.toLowerCase())) {

                    lstData.add(fullData.get(x));
                }*/
            }
        }
        dynamicSize = lstData.size();
        notifyDataSetChanged();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_meditation_vourse, null);
        return new ViewHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // Log.e("index no--",position+"???");
        if (lstData.size() > 0 && position < lstData.size()) {


            holder.txtCourseNmae.setText(lstData.get(position).getEventName());
            if(lstData.get(position).getTags().size()>1){
                holder.txtTag.setText(lstData.get(position).getTags().get(0)+","+lstData.get(position).getTags().get(1));
            }else{
                holder.txtTag.setText(lstData.get(position).getTags().get(0));
            }


            Glide.with(context).load(lstData.get(position).getImageUrl())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.loading)
                    .dontTransform()
                    .dontAnimate()
                    .into(holder.imgCircular);


            holder.txtState.setText("START");
            String tag = "";


            if (lstData.get(position).getTags().size() > 0) {
                for (int i = 0; i <= lstData.get(position).getTags().size() - 1; i = i + 2) {
                    View view = ((MainActivity) context).getLayoutInflater().inflate(R.layout.dynamic_tags, null);
                    TextView txtTags1 = view.findViewById(R.id.txtTags1);
                    TextView txtTags2 = view.findViewById(R.id.txtTags2);
                    ImageView imgTag1 = view.findViewById(R.id.imgTag1);
                    ImageView imgTag2 = view.findViewById(R.id.imgTag2);
                    txtTags1.setText(lstData.get(position).getTags().get(i));
                    if (i <= lstData.get(position).getTags().size() - 2) {
                        txtTags2.setText(lstData.get(position).getTags().get(i + 1));
                    } else {
                        imgTag2.setVisibility(View.GONE);
                    }

                    holder.llTag.addView(view);
                }
            }
            holder.imgFab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            holder.txtDuration.setText(lstData.get(position).getDuration() + " min");
            if(null==lstData.get(position).getLikes()){

            }else{
                if (lstData.get(position).getLikes())
                    holder.imgFab.setBackgroundResource(R.drawable.mbhq_heart_active_m);
                else
                    holder.imgFab.setBackgroundResource(R.drawable.mbhq_heart_inactive_m);
            }

        }
        holder.imgFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleEventLike(lstData.get(position).getEventItemID(), holder.imgFab, lstData.get(position));
            }
        });

        holder.rlState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* MeditationDetails meditationDetails=new MeditationDetails();
                Bundle bundle=new Bundle();
                String totalData=new Gson().toJson(lstData.get(position));
                bundle.putString("data",totalData);
                meditationDetails.setArguments(bundle);
                ((MainActivity)context).loadFragment(meditationDetails,"MeditationDetails",null);*/
                //////////////////////////
                Log.i("clicked__","1");
                SharedPreference sharedPreference = new SharedPreference(context);
                if (lstData.get(position).getEventTypename().equals("AshyLive") || lstData.get(position).getEventTypename().equals("WeeklyWellness") || lstData.get(position).getEventTypename().equals("16") || lstData.get(position).getEventTypename().equals("FridayFoodAndNutrition") || lstData.get(position).getEventTypename().equals("17")) {
                    Log.i("clicked__","2");
                    if (lstData.get(position).getFbUrl() != null && !lstData.get(position).getFbUrl().equals("")) {
                        Log.i("clicked__","3");
                        if (sharedPreference.read("SHOWLEARNFBDIALOG", "").equals("TRUE")) {
                            Log.i("clicked__","4");
                            try {
                                boolean installedOrNot = appInstalledOrNot("com.facebook.katana");
                                if (installedOrNot) {
                                    Uri uri = Uri.parse(lstData.get(position).getFbUrl());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    context.startActivity(intent);
                                } else {
                                    Uri uri = Uri.parse(lstData.get(position).getFbUrl());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    context.startActivity(intent);
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.i("clicked__","5");
                            sharedPreference.write("SHOWLEARNFBDIALOG", "", "TRUE");

                            final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(context);
                            alertDialogCustom.ShowDialog("Please Note", "If you haven’t already joined your forum, please know this chat is in the World Forum, your access will be granted soon. Please request to join from Connect section.", true);
                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                                @Override
                                public void onDone(String title) {
                                    try {
                                        boolean installedOrNot = appInstalledOrNot("com.facebook.katana");
                                        if (installedOrNot) {
                                            Uri uri = Uri.parse(lstData.get(position).getFbUrl());
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            context.startActivity(intent);
                                        } else {
                                            Uri uri = Uri.parse(lstData.get(position).getFbUrl());
                                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                            context.startActivity(intent);
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }

                                }

                                @Override
                                public void onCancel(String title) {

                                }
                            });
                        }
                    }
                } else if (lstData.get(position).getEventTypename().equals("2") || lstData.get(position).getEventTypename().equals("Podcast")) {
                    Log.i("clicked__","6");
                    if (lstData.get(position).getPodcast() != null && lstData.get(position).getPodcast().size() > 0) {
                        if (!lstData.get(position).getPodcast().get(0).equals("")) {
                            Intent intent = new Intent(context, PlaySoundActivity.class);
                            intent.putExtra("SOUNDURL", lstData.get(position).getPodcast().get(0));
                            context.startActivity(intent);
                        }
                    }
                } else {
                   /* WebinarPlayFragment webinarPlayFragment = new WebinarPlayFragment();
                    Bundle bundle=new Bundle();

                    bundle.putString("WEBINARSTATUS",webinarstatus);

                    bundle.putString("EVENTNAME",lstData.get(position).getEventName());
                    bundle.putString("PRESENTERNAME",lstData.get(position).getPresenterName());
                    bundle.putString("EVENTDESCRIPTION",lstData.get(position).getContent());
                    bundle.putString("IMAGEURL",lstData.get(position).getImageUrl());
                    bundle.putStringArrayList("VIDEOURL",lstData.get(position).getArrVideoUrl());
                    bundle.putStringArrayList("TAGLIST",lstData.get(position).getArrTags());
                    bundle.putBoolean("WATCHLIST",lstData.get(position).getArrWatchBool().size()>0?lstData.get(position).getArrWatchBool().get(0):false);
                    bundle.putBoolean("LIKES",lstData.get(position).isLikes());
                    bundle.putString("LIKECOUNT",lstData.get(position).getLikeCount());
                    bundle.putString("EVENTID",lstData.get(position).getEventID());
                    bundle.putString("VIDEOID",lstData.get(position).getEventItemVideoID());
                    bundle.putString("DOWNLOADURL",lstData.get(position).getDownloadURL());
                    bundle.putString("PODCASTURL",lstData.get(position).getArrPodcastURL().size()>0?lstData.get(position).getArrPodcastURL().get(0):"");
                    bundle.putString("EVENTTYPE",lstData.get(position).getEventType());
                    bundle.putString("EVENTTYPENAME",lstData.get(position).getEventTypeDescription());
                    bundle.putBoolean("UPCOMINGWEBINARS",lstData.get(position).isUpcomingEvent());
                    bundle.putString("EVENTTIME",lstData.get(position).getEventDate());
                    bundle.putInt("FILESIZE",lstData.get(position).getDownloadedFileSize());
                    if(((MainActivity)context).getTargetFragment().equals("Mywatclist"))
                    {
                        bundle.putBoolean("ISVIWEDVIDEO",lstData.get(position).isViewedVideo());
                    }
                    webinarPlayFragment.setArguments(bundle);
                    ((MainActivity)context).setTargetFragment(fromFragment);
                    FragmentManager fragmentManager = ((MainActivity)context).getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
                    fragmentTransaction.replace(R.id.container_body, webinarPl                                                                                                                                                                                                                                                  ayFragment, "Webinarplay");
                    fragmentTransaction.commit();*/

                    Util.clearMeditation_onpause="no";
if(lstData.get(position).getEventItemVideoDetails().get(0).getMediaType().equalsIgnoreCase("VIDEO")){
//     ((MainActivity) context).clearCacheForParticularFragment(MeditationDetailsNew_video.newInstance(lstData.get(position)));
//                    ((MainActivity) context).loadFragment(MeditationDetailsNew_video.newInstance(lstData.get(position)), "LiveChatPlayer", null);//temporary bt jyoti
}else{
    Util.backto="";
    ((MainActivity) context).clearCacheForParticularFragment(new MeditationDetailsNew());
    MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
    Bundle bundle = new Bundle();
    String totalData = new Gson().toJson(lstData.get(position));
    bundle.putString("data", totalData);
    meditationDetails.setArguments(bundle);
    ((MainActivity) context).loadFragment(meditationDetails, "MeditationDetailsNew", null);
    Log.i("clicked__","7");

}



                }
                ///////////////////////////


            }
        });

        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMeditationIntroModal(lstData.get(position),position);
                /*((MainActivity) context).clearCacheForParticularFragment(new MeditationIntroFragment());
                ((MainActivity) context).loadFragment(MeditationIntroFragment.newInstance(lstData.get(position)), "MeditationIntroFragment", null);*/
            }
        });


        holder.llRoot.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isDownloadedMeditations) {
                    openDialogAfterLongPress(lstData.get(position));
                    return true;
                }
                return false;
            }
        });


    }

    private void openMeditationIntroModal(MeditationCourseModel.Webinar webinar,Integer position) {

        Log.i(TAG, "open meditation intro modal");

        Dialog dialog = new Dialog(context, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_meditation_intro);

        View closeModal = dialog.findViewById(R.id.closeModal);

        ImageView imgCircular = dialog.findViewById(R.id.imgCircular);

        TextView txtCourseName = dialog.findViewById(R.id.txtCourseName);

        TextView txtDuration = dialog.findViewById(R.id.txtDuration);

        TextView txtTag = dialog.findViewById(R.id.txtTag);

        TextView txtLevel = dialog.findViewById(R.id.txtLevel);

        TextView txtMeditationDescription = dialog.findViewById(R.id.txtMeditationDescription);

        Button btnStartMeditation = dialog.findViewById(R.id.btnStartMeditation);

        txtCourseName.setText(webinar.getEventName());

        txtDuration.setText(webinar.getDuration() + " min");

        txtTag.setText(webinar.getTags().get(0));

        txtLevel.setText("Level " + webinar.getLevel().toString());

        Glide.with(context).load(webinar.getImageUrl())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.loading)
                .error(R.drawable.loading)
                .dontTransform()
                .dontAnimate()
                .into(imgCircular);


        Spanned meditationInfo = Html.fromHtml(webinar.getContent());

        txtMeditationDescription.setText(meditationInfo);

        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnStartMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
               /* ((MainActivity) context).clearCacheForParticularFragment(new MeditationDetailsNew());
                MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
                Bundle bundle = new Bundle();
                String totalData = new Gson().toJson(webinar);
                bundle.putString("data", totalData);
                meditationDetails.setArguments(bundle);
                ((MainActivity) context).loadFragment(meditationDetails, "MeditationDetailsNew", null);*/



                Util.clearMeditation_onpause="no";
                if(lstData.get(position).getEventItemVideoDetails().get(0).getMediaType().equalsIgnoreCase("VIDEO")){
//                    ((MainActivity) context).clearCacheForParticularFragment(MeditationDetailsNew_video.newInstance(lstData.get(position)));
//                    ((MainActivity) context).loadFragment(MeditationDetailsNew_video.newInstance(lstData.get(position)), "LiveChatPlayer", null);//temporary bt jyoti
                }else{
                    Util.backto="";
                    ((MainActivity) context).clearCacheForParticularFragment(new MeditationDetailsNew());
                    MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
                    Bundle bundle = new Bundle();
                    String totalData = new Gson().toJson(lstData.get(position));
                    bundle.putString("data", totalData);
                    meditationDetails.setArguments(bundle);
                    ((MainActivity) context).loadFragment(meditationDetails, "MeditationDetailsNew", null);
                    Log.i("clicked__","7");
                }

            }
        });

        dialog.show();

    }

    private void openDialogAfterLongPress(MeditationCourseModel.Webinar listModelInner) {
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_longpess_delete);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        RelativeLayout rlEdit = dialog.findViewById(R.id.rlEdit);
        RelativeLayout rlDelete = dialog.findViewById(R.id.rlDelete);
        RelativeLayout rlTopTransparent = dialog.findViewById(R.id.rlTopTransparent);
        RelativeLayout rlBottomTransparent = dialog.findViewById(R.id.rlBottomTransparent);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlBottomTransparent.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlTopTransparent.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlDelete.setOnClickListener(view -> {
            dialog.dismiss();

            final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(context);
            alertDialogCustom.ShowDialog("Alert!", "Are you sure want to delete?", true);
            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                @Override
                public void onDone(String title) {
                    if (meditationCourseListener != null) {
                        meditationCourseListener.deleteMeditation(listModelInner);
                    } else {
                        Log.i(TAG, "meditationCourseListener is null");
                    }

                }

                @Override
                public void onCancel(String title) {

                }
            });

        });

        dialog.show();
    }

    private void makejson(AvailableCourseModel.Course course, String weekType) {
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
            courseObj.put("CourseStartDate", getTodayDate());
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
            addCourseApi(queryHm, course.getCourseName(), weekType, course.getCourseId());
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
        //try {
        return lstData.size();
        //   Log.e("print dynamic size--",lstData.size()+"??");
        //return dynamicSize;
        /*} catch (Exception e) {
            e.printStackTrace();
            return 0;
        }*/
    }
    public ArrayList<MeditationCourseModel.Webinar>getArrayList() {

        return lstData;

    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtCourseNmae, txtCourseType, txtState, txtDuration, txtTag;
        //txtAction;
        // public ImageView imgAction,imgInfo;
        public ImageView imgCircular;
        public LinearLayout llRoot, llTag;
        public ImageView imgBanner, imgFab;
        public RelativeLayout rlState;


        //llAction;

        public ViewHolder(View itemView) {
            super(itemView);
            this.txtCourseNmae = itemView.findViewById(R.id.txtCourseNmae);
            this.imgBanner = itemView.findViewById(R.id.imgProgrambanner);
            this.llTag = itemView.findViewById(R.id.llTag);
            //this.txtAction = (TextView) itemView.findViewById(R.id.txtAction);
            // this.imgAction = (ImageView) itemView.findViewById(R.id.imgAction);
            llRoot = itemView.findViewById(R.id.llRoot);
            // this.imgInfo=(ImageView)itemView.findViewById(R.id.imgInfo);
            //llAction = (LinearLayout) itemView.findViewById(R.id.llAction);
            txtState = itemView.findViewById(R.id.txtState);
            // txtCompleted = (TextView) itemView.findViewById(R.id.txtCompleted);
            rlState = itemView.findViewById(R.id.rlState);
            txtDuration = itemView.findViewById(R.id.txtDuration);
            txtTag = itemView.findViewById(R.id.txtTag);
            imgFab = itemView.findViewById(R.id.imgFab);
            imgCircular = itemView.findViewById(R.id.imgCircular);
        }

    }

    private void addCourseApi(HashMap<String, Object> queryHm, final String name, final String weekType, int courseId) {

        if (Connection.checkConnection(context)) {
            final ProgressDialog progressDialog = ProgressDialog.show(context, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(context);
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
                                int newCourseId = resData.getNewData();
                                CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
                                Bundle bundle = new Bundle();
                                bundle.putInt("EnrollCourseId", newCourseId);
                                bundle.putString("name", name);
                                bundle.putString("type", weekType);
                                bundle.putInt("plainCourseid", courseId);
                                bundle.putBoolean("isfirstTime", true);
                                courseDetailsFragment.setArguments(bundle);
                                ((MainActivity) context).loadFragment(courseDetailsFragment, "detail", null);

                                /*AlertDialogCustom alertDialogCustom=new AlertDialogCustom(context);
                                alertDialogCustom.ShowDialog("Success","You have successfully Enrolled",false);
                                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                                    @Override
                                    public void onDone(String title) {

                                        int newCourseId=resData.getNewData();
                                        CourseDetailsFragment courseDetailsFragment=new CourseDetailsFragment();
                                        Bundle bundle=new Bundle();
                                        bundle.putInt("EnrollCourseId",newCourseId);
                                        bundle.putString("name",name);
                                        bundle.putString("type",weekType);
                                        bundle.putBoolean("isfirstTime",true);
                                        courseDetailsFragment.setArguments(bundle);
                                        ((MainActivity)context).loadFragment(courseDetailsFragment,"detail",null);

                                    }

                                    @Override
                                    public void onCancel(String title) {


                                    }
                                });*/

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

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = context.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    ////////////////////
    private void toggleEventLike(Integer eventID, ImageView imgFab, MeditationCourseModel.Webinar webinar) {

        if (Connection.checkConnection(context)) {

            progressDialog = ProgressDialog.show(context, "", "Please wait...");


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("EventID", eventID);


            Call<JsonObject> userHabitSwapsModelCall = finisherService.ToggleEventLike(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().has("Likes")) {
                            if(null==webinar.getLikes()){

                            }else{
                                if (webinar.getLikes()) {
                                    imgFab.setBackgroundResource(R.drawable.mbhq_heart_inactive_m);
                                    webinar.setLikes(false);
                                } else {
                                    imgFab.setBackgroundResource(R.drawable.mbhq_heart_active_m);
                                    webinar.setLikes(true);
                                }
                            }

                            if (meditationCourseLikeListener != null) {
                                meditationCourseLikeListener.toggleLike(webinar);
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
}
