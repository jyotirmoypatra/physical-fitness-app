package com.ashysystem.mbhq.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

//import com.aigestudio.wheelpicker.WheelPicker;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.SessionServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.ExpandableListAdapter;
import com.ashysystem.mbhq.db.DatabaseHelper;
import com.ashysystem.mbhq.model.CircuitListModel;
import com.ashysystem.mbhq.model.DropDownSessionModel;
import com.ashysystem.mbhq.model.EditDailySessionModel;
import com.ashysystem.mbhq.model.EditedExerciseModel;
import com.ashysystem.mbhq.model.ExerciseRequestModel;
import com.ashysystem.mbhq.model.GetSquadWorkoutSessionModel;
import com.ashysystem.mbhq.model.QuickEditCircuitResponseModel;
import com.ashysystem.mbhq.model.QuickEditedCircuitList;
import com.ashysystem.mbhq.model.SessionOverViewModel;
import com.ashysystem.mbhq.model.SwapModelForSession;
import com.ashysystem.mbhq.model.ToggleModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.DownloadFileFromURL;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android-arindam on 26/12/16.
 */

public class SessionOverviewFragment extends Fragment {
    RecyclerView.SmoothScroller smoothScroller;
    private TextView txtOverView;
    private RecyclerView rvSessionList;
    private Dialog progressDialog;
    private RelativeLayout rlNext;
    private TextView txtDayOfWk;
    private ImageView imgGymState, imgHomeState;
    //private RelativeLayout   rlBack;
    private List<SessionOverViewModel.Exercise> lstExercise = null;

    private Paint p = new Paint();
    private View view;
    private AlertDialog.Builder alertDialog;
    private boolean add = false;
    private EditText et_country;
    private int edit_position;
    ExpandableListAdapter.ListHeaderViewHolder preHolder = null;
    private float bitmapXPosition = 0f, bitmapWidth = 0f, bitmapYPosition = 0f, bitmapHeight = 0f;
    private RectF backgroundBkp = null;
    private Canvas canvasBkp = null;
    private float width = 0;
    private String exerciseId = "", title = "", newExerciseSessionId = "", SessionDate = "";
    private int exerciseIdforWork=0;
    private String SessionTitle = "";
    private boolean isPersonalised;
    private TextView txtSessionType, txtBodyArea;
    // private TextView txtTips,txtTitle,txtEquipment;
    private ArrayList<EditedExerciseModel> arrEsitedExerciseModel = new ArrayList<>();
    Integer exerciseSessionId;
    SharedPreference sharedPreference;
    Bundle trainOptionBundle, addUpdateCustomProgramBundle, customProgramFinishBundle;
    String fromFragment = "";
    private int duration = 0;
    private String workoutType = "";
    private boolean alreadyHave = true;
    private int fileCount = 0;
    private int fileCountAudio = 0;
    int countTool = 0, countCue = 0;
    ObjectAnimator textColorAnim;
    //private ImageView imgPause;
    ArrayList<View> arrAllView = new ArrayList<>();
    private String exerciseIdCustom = "";
    private String CalDate = "";
    private static int REQUEST_CODE_WEIGHT = 786;
    ScrollView nsv;
    private String completeid = "";
    private String wtype = "equip";
    private TextView txtBarbell, txtKbell, klpress, dumbell, txtNeed, txtTitle;
    private RelativeLayout rlWeightBelow;
    private int scrollIndex;
    private LinearLayoutManager llmanager;
    private ExpandableListAdapter adapter;
    private LinearLayout llEquipment;
    RelativeLayout rlMusicTop, rlMusicBelow;
    private RelativeLayout rlReadyBelow;
    private int dayToday = 0;
    private String SelectedDate = "";
    public boolean customMode = false;
    private RelativeLayout rlEditSession;
    private boolean isWeightShown=false;
    TextView txtTime;
    private Integer flowId=0;
    private Integer time=0;
    private ImageView imgFav;
    private String videoLInk="",publicVideoUrl="",downloadVideoLink="";
    private TextView txtWorkout;
    private RelativeLayout rlDropdown;
    SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
    private RelativeLayout rlTodayText;
    private TextView txtTimer;
    private ImageView imgClock;
    private boolean isViewClicked=false;
    private RelativeLayout rlOverView;
    private String globalOverViewText="";

    public List<SessionOverViewModel.Exercise> getLstExercise() {
        return lstExercise;
    }

    private RelativeLayout rlWeight;

    public void setLstExercise(List<SessionOverViewModel.Exercise> lstExercise) {
        this.lstExercise = lstExercise;
    }





    private ArrayList<QuickEditedCircuitList> arrQuickEditedCircuitList = new ArrayList<>();

    public ArrayList<QuickEditedCircuitList> getArrQuickEditedCircuitList() {
        return arrQuickEditedCircuitList;
    }





    private String[] arrCue = {"Click here for tools and tips to get the most out of the TRAIN section"};
    public Integer getFlowId() {
        return flowId;
    }

    public void setFlowId(Integer flowId) {
        this.flowId = flowId;
    }

    //////////////////////////////////

    SessionOverViewModel globalSesiionOverViewModel = null;
    DatabaseHelper databaseHelper;

    String RECEIVESTRING = "";

    /////////////////////////////////

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vi = inflater.inflate(R.layout.fragment_session_overview_new, container, false);
        new SharedPreference(getActivity()).write("skip", "", "");
        Util.currentinDel = 0;
        Util.currentOutDel = 0;
        if (getArguments() != null) {
            trainOptionBundle = getArguments();
            addUpdateCustomProgramBundle = getArguments();
            customProgramFinishBundle = getArguments();
            if (getArguments().containsKey("exerciseId"))
                exerciseId = getArguments().getString("exerciseId");
            if (getArguments().containsKey("titleTop"))
                title = getArguments().getString("title");
            if (getArguments().containsKey("completeid")) {
                completeid = getArguments().getString("completeid");
            }
            if (getArguments().containsKey("FROMFRAGMENT")) {
                fromFragment = getArguments().getString("FROMFRAGMENT");
            }
            if (getArguments().containsKey("SessionDate")) {
                SessionDate = getArguments().getString("SessionDate");


                Log.e("print session date--", SessionDate + "??");
            }
            if (getArguments().containsKey("exerciseIdCustom")) {
                exerciseIdCustom = getArguments().getString("exerciseIdCustom");
                customMode = true;
            }
            if (getArguments().containsKey("CalDate")) {
                CalDate = getArguments().getString("CalDate");
            }
            if (getArguments().containsKey("SelectedDate")) {
                SelectedDate = getArguments().getString("SelectedDate");
            }
            if (getArguments().containsKey("wtype"))
                wtype = getArguments().getString("wtype");
            if (getArguments().containsKey("dayToday"))
                dayToday = getArguments().getInt("dayToday");

            if(getArguments().containsKey("RECEIVESTRING"))
            {
                RECEIVESTRING = getArguments().getString("RECEIVESTRING");
            }

            Log.e("print info--", exerciseIdCustom + "???" + CalDate);
        }


        sharedPreference = new SharedPreference(getActivity());
        databaseHelper = new DatabaseHelper(getActivity());

        initView(vi);
        funToolBar();

        if (isAdded()&&Connection.checkConnection(getActivity())) {
            getSessionListFromApi(false);
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

        if (Util.globalExoplayer != null) {
            Util.globalExoplayer.release();
        }

        //  String strJson=loadJSONFromAsset(getActivity(),"OverView.json","");
        //  parseJson(strJson);
        return vi;
    }

    private void setDayName(int dayNo) {
        if (dayNo == -1)
            txtDayOfWk.setText("");
        else if (dayNo == 2)
            txtDayOfWk.setText("Monday");
        else if (dayNo == 3)
            txtDayOfWk.setText("Tuesday");
        else if (dayNo == 4)
            txtDayOfWk.setText("Wednesday");
        else if (dayNo == 5)
            txtDayOfWk.setText("Thursday");
        else if (dayNo == 6)
            txtDayOfWk.setText("Friday");
        else if (dayNo == 7)
            txtDayOfWk.setText("Saturday");
        else
            txtDayOfWk.setText("Sunday");
    }

    public void getSessionListFromApi(final boolean refersh) {
        progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
        ////////////
        HashMap<String, Object> queryHm = new HashMap<>();
        //?userId=16&ExerciseSessionId=178&personalisedOne=false
        queryHm.put("userId", sharedPreference.read("ABBBCOnlineUserId", ""));
        if (!newExerciseSessionId.equals("")) {
            queryHm.put("ExerciseSessionId", newExerciseSessionId);
            exerciseIdforWork=Integer.parseInt(newExerciseSessionId);
            //queryHm.put("personalisedOne",true);
        } else {
            queryHm.put("ExerciseSessionId", exerciseId);
            if(!exerciseId.equals(""))
             exerciseIdforWork=Integer.parseInt(exerciseId);
            //queryHm.put("personalisedOne",false);
        }
        queryHm.put("personalisedOne", false);
        //queryHm.put("ExerciseSessionId","3269");

        ////////////
        SessionServiceImpl serviceSessionMain = new SessionServiceImpl(getActivity());
        Call<SessionOverViewModel> sessionCall = serviceSessionMain.getSessionOverView(queryHm);
        sessionCall.enqueue(new Callback<SessionOverViewModel>() {
            @Override
            public void onResponse(Call<SessionOverViewModel> call, Response<SessionOverViewModel> response) {
                // Log.e("print ex size-->",response.body().getObj()+"?");
                lstExercise = new ArrayList<SessionOverViewModel.Exercise>();
                progressDialog.dismiss();
                //toolTipSetUp();
                if (response.body() != null)
                    if (response.body().getObj() != null) {

                        globalSesiionOverViewModel = response.body();

                        getEquipmentList(response.body().getObj());
                        SessionTitle = response.body().getObj().getSessionTitle();
                        duration = response.body().getObj().getDuration();
                        workoutType = response.body().getObj().getWorkoutType();
                        Log.e("print workout type",workoutType+"???");
                        flowId=response.body().getObj().getSessionFlowId();
                        if(response.body().getObj().getSessionOverview()!=null)
                        {

                            if(response.body().getObj().getSessionOverview().size()>0)
                            {
                                rlOverView.setVisibility(View.VISIBLE);
                            }
                            else
                                rlOverView.setVisibility(View.GONE);
                            for(int x=0;x<response.body().getObj().getSessionOverview().size();x++)
                            {
                              globalOverViewText+= response.body().getObj().getSessionOverview().get(x);
                                globalOverViewText+="\n";
                            }

                        }else
                        {
                            rlOverView.setVisibility(View.GONE);
                        }
                        setFlowId(flowId);
                        if(flowId!=null)
                        {
                            if(flowId==1||flowId==5|flowId==6)
                            {
                                rlEditSession.setVisibility(View.GONE);
                            }
                        }
                        if(response.body().getObj().getTime()!=null)
                        {
                            time=response.body().getObj().getTime();
                            if(flowId==5||flowId==6)
                            {
                                txtTimer.setVisibility(View.VISIBLE);
                                imgClock.setVisibility(View.VISIBLE);
                                txtTimer.setText(time+" Min");
                            }
                        }

                        if(response.body().getObj().getVideoLink()!=null)
                            videoLInk=response.body().getObj().getVideoLink();
                        if(response.body().getObj().getDownloadVideoLink()!=null)
                            downloadVideoLink=response.body().getObj().getDownloadVideoLink();
                        if(response.body().getObj().getPublicVideoUrl()!=null)
                            publicVideoUrl=response.body().getObj().getPublicVideoUrl();

                        txtTime.setText("Workout Time"+"\n"+duration+" Minutes");
                        if(response.body().getObj().isFavourite())
                        {
                           // imgFav.setBackgroundResource(R.drawable.favourite_active);
                        }
                        else
                        {
                           // imgFav.setBackgroundResource(R.drawable.favourite_disable);
                        }

                        // txtTitle.setText(SessionTitle);
                   /* if(SessionDate.equals(""))
                    {
                        txtDayOfWk.setText(SessionTitle);
                    }
*/
                   txtWorkout.setText(SessionTitle.toUpperCase());
                        Log.e("duration print", duration + "?"+SessionTitle.toUpperCase());
                        if (response.body().getObj().getSessionOverview().size() > 0) {
                            // txtTips.setText((String)response.body().getObj().getSessionOverview().get(0));
                        }

                        //if (workoutType.equals("Weights")&&!SessionDate.equals("")) {
                        if (workoutType.equals("Weights")&&!fromFragment.equals("SESSIONLIST")) {
                            rlWeight.setVisibility(View.VISIBLE);
                            rlWeightBelow.setVisibility(View.VISIBLE);
                            if(!new SharedPreference(getActivity()).read("wmsg","").equals("true"))
                            {
                                openWeightMsgDialog();
                            }
                        } else {
                            rlWeight.setVisibility(View.GONE);
                            rlWeightBelow.setVisibility(View.GONE);
                        }



                        lstExercise = response.body().getObj().getExercises();
                        setLstExercise(lstExercise);
                        //getUserPermission();
                        //txtTitle.setText(response.body().getObj().getSessionTitle());
                        isPersonalised = response.body().getObj().getIsPersonalised();
                        exerciseSessionId = response.body().getObj().getExerciseSessionId();
                        Log.e("print ex size-->", lstExercise.size() + "?");
                        Log.e("print success-->", "45" + "?");
                        if (refersh) {
                            Log.e("all", 100 + "?");
                            if(isAdded())
                            playCircuitInVideoPage();

                        } else {
                            modiData(lstExercise);

                            loadAdapter(lstExercise);
                            //modifyData(lstExercise);

                        }

                       // callFireBaseEvent(SessionTitle); //commented by jyoti

                    } else
                        new Util().showDialog(getActivity(), "Alert", "An error occurred", false);

            }

            @Override
            public void onFailure(Call<SessionOverViewModel> call, Throwable t) {
                t.printStackTrace();
                Log.e("print fail-->", "12" + "?");
                progressDialog.dismiss();

            }
        });
    }

    private void modiData(List<SessionOverViewModel.Exercise> lstExercise) {
        ArrayList<SessionOverViewModel.Exercise> tempExe = new ArrayList<SessionOverViewModel.Exercise>();
        for (int mC = 0; mC < lstExercise.size(); mC++) {
            List<SessionOverViewModel.Circuit> cir = lstExercise.get(mC).getCircuitExercises();
            ArrayList<SessionOverViewModel.Circuit> tempCir = new ArrayList<SessionOverViewModel.Circuit>();
            if (cir != null)
                for (int cC = 0; cC < cir.size(); cC++) {
                    if (cir.get(cC).getIsSuperSet() >= 1)
                        if (cir.get(cC).getSuperSetPosition() >= -1 && cir.get(cC).getSuperSetPosition() <= 1) {

                            tempCir.add(cir.get(cC));
                            if (cir.get(cC).getSuperSetPosition() == 1) {
                                if (tempCir != null && tempCir.size() > 0) {
                                    int midPos = midOfPos(tempCir.size());
                                    tempCir.get(midPos).setMiddle("m");
                                }
                                tempCir.clear();
                            }
                        }
                }
            if (lstExercise.get(mC).getIsSuperSet() >= 1)
                if (lstExercise.get(mC).getSuperSetPosition() >= -1 && lstExercise.get(mC).getSuperSetPosition() <= 1) {
                    tempExe.add(lstExercise.get(mC));
                    if (lstExercise.get(mC).getSuperSetPosition() == 1) {
                        if (tempExe != null && tempExe.size() > 0) {
                            int midPos = midOfPos(tempExe.size());
                            tempExe.get(midPos).setMiddle("m");
                        }
                        tempExe.clear();
                    }
                }
        }
    }

    private int midOfPos(int size) {
        if (size == 2)
            return 1;
        else {
            if (size % 2 == 0) {
                return (size / 2) - 1;
            } else {
                return ( int ) ((size / 2));
            }
        }

    }

    private void playCircuitInVideoPage() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_setplaylist_song);

        RelativeLayout rlYes = ( RelativeLayout ) dialog.findViewById(R.id.rlYes);
        RelativeLayout rlSameAsLastSession = ( RelativeLayout ) dialog.findViewById(R.id.rlSameAsLastSession);
        RelativeLayout rlNoMusic = ( RelativeLayout ) dialog.findViewById(R.id.rlNoMusic);

        rlYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Gson gson = new Gson();
                String jsonString = gson.toJson(lstExercise);
                trainOptionBundle.putString("data", jsonString);
                trainOptionBundle.putString("exerciseId", exerciseId);
                trainOptionBundle.putString("title", SessionTitle);
                trainOptionBundle.putInt("duration", duration);
                trainOptionBundle.putString("workouttype", workoutType);
                trainOptionBundle.putString("uri", "song");
                trainOptionBundle.putString("completeid", completeid);
                trainOptionBundle.putString("spotifyactive", "false");
//                if ((( MainActivity ) getActivity()).getSpotifyObj() != null) {
//                    Spotify.destroyPlayer((( MainActivity ) getActivity()).getSpotifyObj());
//                }
//                (( MainActivity ) getActivity()).setSpotifyObj(null);
//                (( MainActivity ) getActivity()).setSpotifyPlayer(null);
//                (( MainActivity ) getActivity()).setmOperationCallback(null);
                new SharedPreference(getActivity()).write("playposStr", "playpos", "");
                new SharedPreference(getActivity()).write("muzik", "", "yes");
                new SharedPreference(getActivity()).write("spotifyM", "", "false");
               // (( MainActivity ) getActivity()).loadFragment(new SettingFragment(), "setting", trainOptionBundle);

            }
        });

        rlSameAsLastSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Gson gson = new Gson();
                String jsonString = gson.toJson(lstExercise);
                trainOptionBundle.putString("data", jsonString);
                trainOptionBundle.putString("exerciseId", exerciseId);
                trainOptionBundle.putString("title", SessionTitle);
                trainOptionBundle.putInt("duration", duration);
                trainOptionBundle.putString("workouttype", workoutType);
                ///////////For Weight///////////
                trainOptionBundle.putString("ExId", exerciseId);
                trainOptionBundle.putString("SessionDate", SessionDate);
                trainOptionBundle.putString("CalDate", CalDate);
                trainOptionBundle.putString("exerciseIdCustom", exerciseIdCustom);
                trainOptionBundle.putString("completeid", completeid);
                ///////////For Weight///////////
                trainOptionBundle.putInt("flowId",flowId);
                trainOptionBundle.putInt("timerepeat",time);
                trainOptionBundle.putString("videolink",videoLInk);
                trainOptionBundle.putString("downloadvideolink",downloadVideoLink);
                trainOptionBundle.putString("publicvideourl",publicVideoUrl);
                trainOptionBundle.putInt("out", -1);
                if (isSpotifyActive()) {
                    trainOptionBundle.putString("spotifyactive", "true");

                }
                VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
                videoPlayerFragment.setArguments(trainOptionBundle);
                (( MainActivity ) getActivity()).loadFragment(videoPlayerFragment, "videx", null);
            }
        });

        rlNoMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                Gson gson = new Gson();
                String jsonString = gson.toJson(lstExercise);
                trainOptionBundle.putString("data", jsonString);
                trainOptionBundle.putString("exerciseId", exerciseId);
                trainOptionBundle.putString("title", SessionTitle);
                trainOptionBundle.putInt("duration", duration);
                trainOptionBundle.putString("workouttype", workoutType);
                ///////////For Weight///////////
                trainOptionBundle.putString("ExId", exerciseId);
                trainOptionBundle.putString("SessionDate", SessionDate);
                trainOptionBundle.putString("CalDate", CalDate);
                trainOptionBundle.putString("exerciseIdCustom", exerciseIdCustom);
                trainOptionBundle.putString("completeid", completeid);
                ///////////For Weight///////////

                trainOptionBundle.putInt("out", -1);
                trainOptionBundle.putInt("flowId",flowId);
                trainOptionBundle.putInt("timerepeat",time);
                trainOptionBundle.putString("videolink",videoLInk);
                trainOptionBundle.putString("downloadvideolink",downloadVideoLink);
                trainOptionBundle.putString("publicvideourl",publicVideoUrl);
                trainOptionBundle.putBoolean("NOMUSIC", true);
//                if ((( MainActivity ) getActivity()).getSpotifyObj() != null) {
//                    Spotify.destroyPlayer((( MainActivity ) getActivity()).getSpotifyObj());
//                }
//                (( MainActivity ) getActivity()).setSpotifyObj(null);
//                (( MainActivity ) getActivity()).setSpotifyPlayer(null);
//                (( MainActivity ) getActivity()).setmOperationCallback(null);
                trainOptionBundle.putString("spotifyactive", "false");
                new SharedPreference(getActivity()).write("muzik", "", "no");
                new SharedPreference(getActivity()).write("playposStr", "playpos", "");
                new SharedPreference(getActivity()).write("spotifyM", "", "false");
                VideoPlayerFragment videoPlayerFragment = new VideoPlayerFragment();
                videoPlayerFragment.setArguments(trainOptionBundle);
                (( MainActivity ) getActivity()).loadFragment(videoPlayerFragment, "videx", null);
            }
        });

        dialog.show();


    }

    private void initView(View vi) {
        txtTimer=(TextView )vi.findViewById(R.id.txtTimer);
        rlOverView=(RelativeLayout)vi.findViewById(R.id.rlOverView);
        imgClock=(ImageView )vi.findViewById(R.id.imgClock);
        rlTodayText=(RelativeLayout )vi.findViewById(R.id.rlTodayText);
        txtWorkout=(TextView )vi.findViewById(R.id.txtWorkout);
        rlDropdown=(RelativeLayout )vi.findViewById(R.id.rlDropdown);
        rlEditSession = ( RelativeLayout ) vi.findViewById(R.id.rlEditSession);
        rlMusicTop = ( RelativeLayout ) vi.findViewById(R.id.rlMusicTop);
        rlMusicBelow = ( RelativeLayout ) vi.findViewById(R.id.rlMusicBelow);
        rlReadyBelow = ( RelativeLayout ) vi.findViewById(R.id.rlReadyBelow);
        rlWeightBelow = ( RelativeLayout ) vi.findViewById(R.id.rlWeightBelow);
        rvSessionList = ( RecyclerView ) vi.findViewById(R.id.rvSessionList);
        llmanager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvSessionList.setLayoutManager(llmanager);
        rlNext = ( RelativeLayout ) vi.findViewById(R.id.rlNext);
        txtSessionType = ( TextView ) vi.findViewById(R.id.txtSessionType);
        txtBodyArea = ( TextView ) vi.findViewById(R.id.txtBodyArea);
        txtDayOfWk = ( TextView ) vi.findViewById(R.id.txtDayOfWk);
        imgHomeState = ( ImageView ) vi.findViewById(R.id.imgHomeState);
        imgGymState = ( ImageView ) vi.findViewById(R.id.imgGymState);
        txtNeed = ( TextView ) vi.findViewById(R.id.txtNeed);
        txtTitle = ( TextView ) vi.findViewById(R.id.txtTitle);
        txtTime = ( TextView ) vi.findViewById(R.id.txtTime);
        imgFav=(ImageView )vi.findViewById(R.id.imgFav);
        txtOverView=(TextView)vi.findViewById(R.id.txtOverView);
        // rlBack=(RelativeLayout)vi.findViewById(R.id.rlBack);
        //txtTips=(TextView)vi.findViewById(R.id.txtTips);
        //txtEquipment=(TextView)vi.findViewById(R.id.txtEquipment);
        // txtTitle=(TextView)vi.findViewById(R.id.txtTitle);
        // imgPause=(ImageView) vi.findViewById(R.id.imgPause);
        rlWeight = ( RelativeLayout ) vi.findViewById(R.id.rlWeight);
        rlWeightBelow = ( RelativeLayout ) vi.findViewById(R.id.rlWeightBelow);
        if (customMode)
            rlEditSession.setVisibility(View.VISIBLE);
        else
            rlEditSession.setVisibility(View.GONE);
        rlEditSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSquadWorkoutSession();
            }
        });
        rlTodayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.checkConnection(getActivity()))
                {
                    isViewClicked=true;
                   checkUserProgramStep();
                }
                else
                    Util.showToast(getActivity(),"Please check your internet connection");
               /* Date currentDateObj=new Date();
                String todayDate = format.format(currentDateObj);
                getworkoutFromSessionApi(todayDate);*/
            }
        });
        rlOverView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.showDialog(getActivity(),"Session Overview",globalOverViewText,false);
            }
        });

        nsv = ( ScrollView ) vi.findViewById(R.id.nsv);
        llEquipment = ( LinearLayout ) vi.findViewById(R.id.llEquipment);
        rlWeightBelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlWeight.performClick();
            }
        });
        rlReadyBelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlNext.performClick();
            }
        });

        //nsv.setNestedScrollingEnabled(false);
        rlMusicBelow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rlMusicTop.performClick();
            }
        });

        rlMusicTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new Gson();
                String jsonString = gson.toJson(lstExercise);
                trainOptionBundle.putString("data", jsonString);
                trainOptionBundle.putString("exerciseId", exerciseId);
                trainOptionBundle.putString("title", SessionTitle);
                trainOptionBundle.putInt("duration", duration);
                trainOptionBundle.putString("workouttype", workoutType);
                trainOptionBundle.putString("uri", "song");
                trainOptionBundle.putString("completeid", completeid);
                trainOptionBundle.putString("spotifyactive", "false");
                trainOptionBundle.putString("src", "session");
//                if ((( MainActivity ) getActivity()).getSpotifyObj() != null) {
//                    Spotify.destroyPlayer((( MainActivity ) getActivity()).getSpotifyObj());
//                }
//                (( MainActivity ) getActivity()).setSpotifyObj(null);
//                (( MainActivity ) getActivity()).setSpotifyPlayer(null);
//                (( MainActivity ) getActivity()).setmOperationCallback(null);
                new SharedPreference(getActivity()).write("playposStr", "playpos", "");
                new SharedPreference(getActivity()).write("muzik", "", "yes");
                new SharedPreference(getActivity()).write("spotifyM", "", "false");
              //  (( MainActivity ) getActivity()).loadFragment(new SettingFragment(), "setting", trainOptionBundle);
            }
        });
        rlNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  SharedPreference sharedPreference=new SharedPreference(getActivity());
                if(!sharedPreference.read("writePer","").equals("true"))
                    getUserPermission();
                else
                {
                    if(isAdded())
                        playCircuitInVideoPage();
                }*/
                if(isAdded())
                    playCircuitInVideoPage();

            }
        });
        rlDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Util.checkConnection(getActivity()))
                    checkUserProgramStep();
                else
                    Util.showToast(getActivity(),"Please check your internet connection");
                //openDropDownDialog();
            }
        });
        imgFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleFavApi(exerciseSessionId,imgFav,0);
            }
        });

        rvSessionList.setOnTouchListener(touchFun);


        final Animation animation = new AlphaAnimation(1, 0);
        animation.setDuration(500);
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE);
        animation.setRepeatMode(Animation.REVERSE);
        //imgPause.startAnimation(animation);
        if (!SessionDate.equals(""))
            setDayName(getDayNo(SessionDate, false));
        else {
            if (dayToday == 0)
                txtDayOfWk.setText("Monday");
            else if (dayToday == 1)
                txtDayOfWk.setText("Tuesday");
            else if (dayToday == 2)
                txtDayOfWk.setText("Wednesday");
            else if (dayToday == 3)
                txtDayOfWk.setText("Thrusday");
            else if (dayToday == 4)
                txtDayOfWk.setText("Friday");
            else if (dayToday == 5)
                txtDayOfWk.setText("Saturdy");
            else if (dayToday == 6)
                txtDayOfWk.setText("Sunday");
            // setDayName( getDayNo(CalDate,true));

        }

        setWorkoutImg();
        Log.e("print s title--", SessionTitle + "??");

        scrollIndex = 0;





    }

    private void openDropDownDialog() {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.dropdown_custom_plan);
        LinearLayout llView=(LinearLayout )dialog.findViewById(R.id.llView);
        LinearLayout llMonday=(LinearLayout )dialog.findViewById(R.id.llMonday);
        LinearLayout llTuesday=(LinearLayout )dialog.findViewById(R.id.llTuesday);
        LinearLayout llWedDay=(LinearLayout )dialog.findViewById(R.id.llWedDay);
        LinearLayout llThrusday=(LinearLayout )dialog.findViewById(R.id.llThrusday);
        LinearLayout llFriday=(LinearLayout )dialog.findViewById(R.id.llFriday);
        LinearLayout llSatDay=(LinearLayout )dialog.findViewById(R.id.llSatDay);
        LinearLayout llSunDay=(LinearLayout )dialog.findViewById(R.id.llSunDay);
        llView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if(Util.checkConnection(getActivity()))
                {
                    //checkUserProgramStep();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    Calendar calendarGlobal = Calendar.getInstance();
                    calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                    //Bundle bundle = new Bundle();
                    trainOptionBundle.putString("CALENDERDATE", format.format(calendarGlobal.getTime()));

                    trainOptionBundle.putString("origin", "sov");

                    ((MainActivity) getActivity()).loadFragment(new CustomProgramFinishFragment(), "CustomProgramFinish", trainOptionBundle);
                }

                else
                    Util.showToast(getActivity(),"Please check your network connection");


            }
        });
        llMonday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);

            }
        });
        llTuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);
            }
        });
        llWedDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);
            }
        });
        llThrusday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);
            }
        });
        llFriday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);
            }
        });
        llSatDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);
            }
        });
        llSunDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarGlobal = Calendar.getInstance();
                calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendarGlobal.set(Calendar.HOUR,0);
                calendarGlobal.set(Calendar.MINUTE,0);
                calendarGlobal.set(Calendar.SECOND,0);
                String currentDate=format.format(calendarGlobal.getTime());
                dialog.dismiss();
                getworkoutFromSessionApi(currentDate);
            }
        });
        dialog.show();

    }






    private void openWeightMsgDialog() {
        //BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        Dialog dialog=new Dialog(getActivity(),R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_weight_session);
        RelativeLayout rlGotIt=(RelativeLayout )dialog.findViewById(R.id.rlGotIt);
        RelativeLayout rlCancel=(RelativeLayout )dialog.findViewById(R.id.rlCancel);
        rlGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               dialog.dismiss();
            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SharedPreference(getActivity()).write("wmsg","","true");
                dialog.dismiss();
            }
        });

        dialog.show();

    }



    private void getworkoutFromSessionApi(String currentDate) {
        if(Util.checkConnection(getActivity()))
        {
            Log.e("print curent date-",currentDate+"?");
            Date currentDateObj=new Date();
            String currentDateString=format.format(currentDateObj);
            String msg="";
            Log.e("print curent string-",currentDateString+"?");
            if(currentDateString.equals(currentDate))
            {
                msg="You already have this session in your today's custom plan.";
            }
            else
            {
                msg="You already have this session in your selected day custom plan.";
            }

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            ////////////
            HashMap<String, Object> modelHashMap = new HashMap<>();
            modelHashMap.put("UserId", sharedPreference.read("ABBBCOnlineUserId",""));
            modelHashMap.put("Key", Util.KEY_ABBBC);
            modelHashMap.put("UserSessionID", sharedPreference.read("ABBBCOnlineUserSessionId",""));
            modelHashMap.put("SessionDate", currentDate);

            ////////////
            SessionServiceImpl serviceSessionMain = new SessionServiceImpl(getActivity());
            Call<DropDownSessionModel> sessionCall = serviceSessionMain.getWorkoutSessionsFromDate(modelHashMap);
            String finalMsg = msg;
            sessionCall.enqueue(new Callback<DropDownSessionModel>() {
                @Override
                public void onResponse(Call<DropDownSessionModel> call, Response<DropDownSessionModel> response) {
                    // Log.e("print ex size-->",response.body().getObj()+"?");
                    //lstExercise = new ArrayList<SessionOverViewModel.Exercise>();
                    progressDialog.dismiss();
                    Log.e("print  id for work",exerciseIdforWork+"?");
                    //toolTipSetUp();
                    if (response.body() != null)
                    {
                        if(response.body().getWorkoutSessionList()!=null)
                        {
                            boolean matchFound=false;
                            if(response.body().getWorkoutSessionList().size()>0)
                            {
                                for(int p=0;p<response.body().getWorkoutSessionList().size();p++) {
                                    if (exerciseIdforWork == response.body().getWorkoutSessionList().get(p).getExerciseSessionId()) {
                                        matchFound = true;

                                        break;
                                    }
                                }
                                if(matchFound)
                                {
                                    Util.showDialog(getActivity(),"Alert", finalMsg,false);
                                }
                                else
                                {
                                    if(response.body().getWorkoutSessionList().size()<2)
                                    {

                                        funaddSessionSwapApi(currentDate,false,0);
                                    }
                                    else
                                    {
                                        openSwapDialog(response.body().getWorkoutSessionList(),currentDate);
                                    }


                                }
                            }
                            else
                            {
                                funaddSessionSwapApi(currentDate,false, 0);
                            }

                        }
                    }


                }

                @Override
                public void onFailure(Call<DropDownSessionModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("print fail-->", "12" + "?");
                    progressDialog.dismiss();

                }
            });
        }
        else
        {
            Util.showToast(getActivity(),"Please check your network connection");
        }

    }



    private void openSwapDialog(List<DropDownSessionModel.WorkoutSessionList> workoutSessionList, String currentDate) {
        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(R.layout.dialog_swap_add_session);
        LinearLayout llDynamic=dialog.findViewById(R.id.llDynamic);
        for(int p=0;p<workoutSessionList.size();p++)
        {
            View v = getLayoutInflater().inflate(R.layout.layout_swap_add_session, null);
            TextView txtSession=(TextView )v.findViewById(R.id.txtSession);
            txtSession.setText(workoutSessionList.get(p).getExerciseSessionDetails().getSessionTitle());
            int finalP = p;
            txtSession.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                    funaddSessionSwapApi(currentDate,true,workoutSessionList.get(finalP).getId());
                }
            });

            llDynamic.addView(v);
        }
        dialog.show();

    }

    private void funaddSessionSwapApi(String currentDate, boolean workIdSend, Integer id) {
        Log.e("swap","call");
       Log.e("print curent date-",currentDate+"?");
        Date currentDateObj=new Date();
        format.format(currentDateObj);
        String msg="";
        if(format.equals(currentDate))
        {
            msg="You already have this session in your today's custom plan.";
        }
        else
        {
            msg="You already have this session in your selected day custom plan.";
        }

        progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
        ////////////
        HashMap<String, Object> modelHashMap = new HashMap<>();
        modelHashMap.put("UserId", sharedPreference.read("ABBBCOnlineUserId",""));
        modelHashMap.put("Key", Util.KEY_ABBBC);
        modelHashMap.put("UserSessionID", sharedPreference.read("ABBBCOnlineUserSessionId",""));
        modelHashMap.put("SessionDate", currentDate);
        modelHashMap.put("ExerciseSessionId", exerciseIdforWork);
        if(id!=0)
        {
            modelHashMap.put("WorkoutId", id);
        }

        ////////////
        SessionServiceImpl serviceSessionMain = new SessionServiceImpl(getActivity());
        Call<SwapModelForSession> sessionCall = serviceSessionMain.addSwapCustomSession(modelHashMap);
        String finalMsg = msg;
        sessionCall.enqueue(new Callback<SwapModelForSession>() {
            @Override
            public void onResponse(Call<SwapModelForSession> call, Response<SwapModelForSession> response) {
                // Log.e("print ex size-->",response.body().getObj()+"?");
               // lstExercise = new ArrayList<SessionOverViewModel.Exercise>();
                progressDialog.dismiss();
                //toolTipSetUp();
                if (response.body() != null)
                {
                    //completeid=response.body().getNewId()+"";
                    exerciseIdCustom=response.body().getNewId()+"";
                    //exerciseIdCustom=completeid;
                    Log.e("print new id--",exerciseIdCustom+"?");
                    if(workIdSend)
                        Util.showDialog(getActivity(),"Alert","Succesfully Swaped",false);
                    else
                        Util.showDialog(getActivity(),"Alert","Succesfully added",false);
                }


            }

            @Override
            public void onFailure(Call<SwapModelForSession> call, Throwable t) {
                t.printStackTrace();
                Log.e("print fail-->", "12" + "?");
                progressDialog.dismiss();

            }
        });
    }

    private void changescroll(int scrollIndex, int child) {

        try {
            if(scrollIndex<=2)
                nsv.scrollTo(0,rvSessionList.getTop());
            else
            {
                View tmp=rvSessionList.getChildAt(scrollIndex);
                if(tmp!=null)
                {
                    float y = rvSessionList.getChildAt(scrollIndex).getY();
                    Log.e("scroll fun",scrollIndex+"?"+((int) y));
                    nsv.smoothScrollTo(0, (int) y);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setWorkoutImg() {
        if(wtype.equals("equip"))
        {
            imgGymState.setBackgroundResource(0);
            imgHomeState.setBackgroundResource(0);
            //imgGymState.setBackgroundResource(R.drawable.checked_1);
            imgHomeState.setBackgroundResource(R.drawable.unchecked);
        }
        else if(wtype.equals("body"))
        {
            imgGymState.setBackgroundResource(0);
            imgHomeState.setBackgroundResource(0);
          //  imgGymState.setBackgroundResource(R.drawable.unchecked);
           // imgHomeState.setBackgroundResource(R.drawable.checked_1);
        }
        else
        {
            imgGymState.setBackgroundResource(0);
            imgHomeState.setBackgroundResource(0);
            imgGymState.setBackgroundResource(R.drawable.unchecked);
          //  imgHomeState.setBackgroundResource(R.drawable.checked_1);
        }
    }


    private void loadAdapter(List<SessionOverViewModel.Exercise> lstExercise)
    {
        List<ExpandableListAdapter.Item> data=new ArrayList<>();
        Log.e("print szz",lstExercise.size()+"?");
        for(int p=0;p<lstExercise.size();p++)
        {
            ////////////////////Accordian Open for having circuit//////////
            if(p!=0&&lstExercise.get(p).getIsCircuit())
            {
                data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, p + "", lstExercise.get(p)));
                data.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, p + "", lstExercise.get(p)));
            }
            /////////////////////////Accordian Open for having circuit End//////////
            ///////////////////////Accordian close //////////////////////////////////
            else
            {
                ExpandableListAdapter.Item places = new ExpandableListAdapter.Item(ExpandableListAdapter.HEADER, p + "", lstExercise.get(p));
                places.invisibleChildren = new ArrayList<>();
                places.invisibleChildren.add(new ExpandableListAdapter.Item(ExpandableListAdapter.CHILD, p + "", lstExercise.get(p)));
                data.add(places);
                Log.e("print index", p + "?");
            }
            //////////////////////Accordian close  End//////////////////////////////


        }
        Log.e("adapter size",data.size()+"?");
        if(data!=null) {
            adapter=new ExpandableListAdapter(data, getActivity(), lstExercise, SessionOverviewFragment.this);
            rvSessionList.setAdapter(adapter);
        }
       initSwipe();
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                if(isAdded())
                getUserPermission();
            }
        }, 5000);



    }
    private void funToolBar() {
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ImageView imgLogo = (ImageView) toolbar.findViewById(R.id.imgLogo);
        ImageView imgLeftBack = (ImageView) toolbar.findViewById(R.id.imgLeftBack);
        ImageView imgRightBack = (ImageView) toolbar.findViewById(R.id.imgRightBack);
        ImageView imgFilter = (ImageView) toolbar.findViewById(R.id.imgFilter);
        FrameLayout frameNotification = (FrameLayout) toolbar.findViewById(R.id.frameNotification);
        TextView txtPageHeader = (TextView) toolbar.findViewById(R.id.txtPageHeader);
        toolbar.setNavigationIcon(null);
        frameNotification.setVisibility(View.GONE);
        imgFilter.setVisibility(View.GONE);
       // //imgFilter.setBackgroundResource(R.drawable.filter);
        imgRightBack.setVisibility(View.VISIBLE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));



        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity)getActivity()).onBackPressed();
                if(getArguments()!=null&&getArguments().containsKey("workout"))
                {
                    //((MainActivity)getActivity()).loadFragment(new WorkoutFragment(),"WorkoutFragment",getArguments());//commented by jyoti
                }
                else if(getArguments()!=null&&getArguments().containsKey("TODAY"))
                {
                    //((MainActivity)getActivity()).loadFragment(new TodayFragment(),"Today",null);//commented by jyoti
                }
                else
                {
                    if(fromFragment.equals("SESSIONLIST"))
                    {
                        if(!RECEIVESTRING.equals(""))
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString("RECEIVESTRING",RECEIVESTRING);
                           // ((MainActivity)getActivity()).loadFragment(new SessionListFragment(),"SessionList",bundle);//commented by jyoti
                        }else {
                           // ((MainActivity)getActivity()).loadFragment(new SessionListFragment(),"SessionList",null);//commented by jyoti
                        }
                    }else if(fromFragment.equals("ADDUPDATECUSTOMPROGRAM"))
                    {
                        ((MainActivity)getActivity()).loadFragment(new AddUpdateCustomProgramFragment(),"AddUpdateCustomProgram",addUpdateCustomProgramBundle);
                    }else if(fromFragment.equals("CUSTOMPROGRAMFINISH"))
                    {
                        ((MainActivity)getActivity()).loadFragment(new CustomProgramFinishFragment(),"CustomProgramFinish",customProgramFinishBundle);
                    }else
                    {
                        ((MainActivity)getActivity()).loadFragment(new TrainOptionFragment(),"TrainOption",trainOptionBundle);
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

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){


                    if(getArguments()!=null&&getArguments().containsKey("workout"))
                    {
                       // ((MainActivity)getActivity()).loadFragment(new WorkoutFragment(),"WorkoutFragment",getArguments());//commented by jyoti
                    }
                    else if(getArguments()!=null&&getArguments().containsKey("TODAY"))
                    {
                       // ((MainActivity)getActivity()).loadFragment(new TodayFragment(),"Today",null);//commented by jyoti
                    }
                    else
                    {
                        if(fromFragment.equals("SESSIONLIST"))
                        {
                            if(!RECEIVESTRING.equals(""))
                            {
                                Bundle bundle = new Bundle();
                                bundle.putString("RECEIVESTRING",RECEIVESTRING);
                              //  ((MainActivity)getActivity()).loadFragment(new SessionListFragment(),"SessionList",bundle);//commented by jyoti
                            }else {
                               // ((MainActivity)getActivity()).loadFragment(new SessionListFragment(),"SessionList",null);//commented by jyoti
                            }
                        }else if(fromFragment.equals("ADDUPDATECUSTOMPROGRAM"))
                        {
                            ((MainActivity)getActivity()).loadFragment(new AddUpdateCustomProgramFragment(),"AddUpdateCustomProgram",addUpdateCustomProgramBundle);
                        }else if(fromFragment.equals("CUSTOMPROGRAMFINISH"))
                        {
                            ((MainActivity)getActivity()).loadFragment(new CustomProgramFinishFragment(),"CustomProgramFinish",customProgramFinishBundle);
                        }else
                        {
                            ((MainActivity)getActivity()).loadFragment(new TrainOptionFragment(),"TrainOption",trainOptionBundle);
                        }
                    }

                    return true;

                }

                return false;
            }
        });
    }
    ///////////////////////////Swipe RecyclerView////////////
    private void initSwipe(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                if (direction == ItemTouchHelper.LEFT){
                    Log.e("left","12");
                } else {
                    Log.e("right","45");
                }

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                preHolder= (ExpandableListAdapter.ListHeaderViewHolder) viewHolder;
                if(!preHolder.refferalItem.isCircuit)
                {
                    Bitmap icon;
                    if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE)
                    {
                  /*  if(canvasBkp!=null&&preHolder!=null)

                    {
                        canvasBkp.drawColor(0, PorterDuff.Mode.CLEAR);
                        super.onChildDraw(canvasBkp, recyclerView, preHolder, 0, dY, actionState, isCurrentlyActive);
                        preHolder=null;
                        canvasBkp=null;
                    }*/

                        View itemView = viewHolder.itemView;

                        float height = (float) itemView.getBottom() - (float) itemView.getTop();
                        width = height / 3;

                        if(dX > 0)
                        {
                            Log.e("con 1","123");
                            p.setColor(Color.parseColor("#388E3C"));
                            RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX,(float) itemView.getBottom());
                            c.drawRect(background,p);
                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.active_check);
                            RectF icon_dest = new RectF((float) itemView.getLeft() + width ,(float) itemView.getTop() + width,(float) itemView.getLeft()+ 2*width,(float)itemView.getBottom() - width);
                            c.drawBitmap(icon,null,icon_dest,p);

                        }
                        else
                        {
                            Log.e("con 2","456");

                            p.setColor(Color.parseColor("#FF00A5"));
                            RectF background = new RectF((float) itemView.getRight() + dX/5, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                            backgroundBkp=background;
                            c.drawRect(background,p);
                            icon = BitmapFactory.decodeResource(getResources(), R.drawable.s_t_ce_edit);
                            RectF icon_dest = new RectF(background.left-5 ,background.top-5,background.right-5,background.bottom-5);
                            c.drawBitmap(icon,null,icon_dest,p);
                            canvasBkp=c;
                            preHolder= (ExpandableListAdapter.ListHeaderViewHolder) viewHolder;


                        /*bitmapXPosition=(float) itemView.getRight() + dX/5;
                        bitmapYPosition=(float) itemView.getTop();
                        bitmapWidth=width;
                        bitmapHeight=((float) itemView.getTop()-((float) itemView.getBottom()));*/



                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX/5, dY, actionState, isCurrentlyActive);
                }


            }
            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder instanceof ExpandableListAdapter.ListItemViewHolder) return 0;
                return super.getSwipeDirs(recyclerView, viewHolder);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(rvSessionList);
    }


    View.OnTouchListener touchFun=new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            float x = event.getX();
            float y = event.getY();
            switch(event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    //Check if the x and y position of the touch is inside the bitmap

                   if(backgroundBkp!=null)
                   if( backgroundBkp.contains(x,y))
                    {
                        //Bitmap touched
                        Log.e("print touch","123");
                        //getCircuitListFromApi();
                       getExerciseList();


                    }
                    else
                   {
                       Log.e("out touch","123");
                   }
                    return true;
            }
            return false;
        }
    };

    private void getExerciseList() {

        if(Connection.checkConnection(getActivity()))
        {
            final ProgressDialog  progressDialog= ProgressDialog.show(getActivity(), "", "Please wait...");
            /////////////////Edit Session Api//////////////
            HashMap<String,Object> hmModel=new HashMap<String, Object>();

            hmModel.put("Key", Util.KEY_ABBBC);
            //hmModel.put("UserSessionID",108);
            hmModel.put("UserSessionID",sharedPreference.read("ABBBCOnlineUserSessionId",""));

            SessionServiceImpl serviceSessionMain=new SessionServiceImpl(getActivity());
            Call<ExerciseRequestModel> sessionCall=serviceSessionMain.getExerciseList(hmModel);
            sessionCall.enqueue(new Callback<ExerciseRequestModel>()
            {
                @Override
                public void onResponse(Call<ExerciseRequestModel> call, Response<ExerciseRequestModel> response)
                {
                    Log.e("print response-->",response.toString()+"?");
                    List<ExerciseRequestModel.Exercise> lstExercisePicker = response.body().getExercises();
                    progressDialog.dismiss();
                  //  showPickerForExercise(lstExercisePicker);
                }

                @Override
                public void onFailure(Call<ExerciseRequestModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("print fail-->","14"+"?");
                    progressDialog.dismiss();

                }
            });
        }else
        {
            Util.showToast(getActivity(),Util.networkMsg);
        }
        ////////////////Edit Session Api//////////////
    }



    /////////////File Download///////////////////
    private void getUserPermission() {
        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(hasGalleryPermission()) {
                getDownloadFileName();

            }
            else {
                if(isAdded())
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 201);
            }
        } else {
            // continue with your code
            getDownloadFileName();

        }
    }

    private void getDownloadFileName() {
        ArrayList<String> arrFileName=new ArrayList<>();
        List<SessionOverViewModel.Exercise> lstExercise=getLstExercise();
        if(lstExercise!=null)
        for(int p=0;p<lstExercise.size();p++) {
            if(!lstExercise.get(p).getIsCircuit()) {
                String fileNameMain=lstExercise.get(p).getName();
                arrFileName.add(fileNameMain);
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad" + File.separator + fileNameMain + ".mp4");
                if(!file.exists())
                    alreadyHave=false;
                //new DownloadFileFromURL(getActivity(), fileNameMain);
            }
            if(lstExercise.get(p).getCircuitExercises()!=null) {
                for(int k=0;k<lstExercise.get(p).getCircuitExercises().size();k++) {
                    String fileName=lstExercise.get(p).getCircuitExercises().get(k).getExerciseName();
                    // Log.e("print trim",lstExercise.get(p).getCircuitExercises().get(k).getExerciseName()+"?");
                    arrFileName.add(fileName);
                    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad" + File.separator + fileName + ".mp4");
                    if(!file.exists())
                        alreadyHave=false;
                }
            }
            /*else {
                if(p==lstExercise.size()-1) {
                    if(lstExercise!=null) {
                        alreadyHave=false;
                        Log.e("MMM","000");
                        playCircuitInVideoPage();
                    }
                }
            }*/
        }
        downloadAudioFileNew(arrFileName);
        if(alreadyHave) {
            if(lstExercise!=null) {
                Log.e("all","1");

                downloadAudioFileNew(arrFileName);
                //playCircuitInVideoPage();

            }
        }else{
            if(arrFileName.size() > 0) {
                Log.e("all","2");

                //final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Downloading files...");
                downloadFromUrl(arrFileName,null);
               // playCircuitInVideoPage();
                //downloadAudioFile(arrFileName);
              //  playCircuitInVideoPage();
            }
        }



    }


    private void downloadFromUrl(final ArrayList<String> arrFileName, final ProgressDialog progressDialog){

        if(fileCount<arrFileName.size()) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad" + File.separator + arrFileName.get(fileCount) + ".mp4");
            //Log.e("full",file.getAbsoluteFile()+"?");
            if (!file.exists()) {
               // alreadyHave = false;
                new DownloadFileFromURL(getActivity(), arrFileName.get(fileCount),"Video", false, ".mp4",new DownloadFileFromURL.AsynResponse() {
                    @Override
                    public void processFinish(Boolean output) {
                        if (fileCount == arrFileName.size() - 1) {
                            // START NEW TASK HERE
                            //alreadyHave = false;
                            if(progressDialog != null && progressDialog.isShowing())
                                progressDialog.dismiss();
                          //  playCircuitInVideoPage();
                            Log.e("aud","1");
                          downloadAudioFileNew(arrFileName);
                        } else {
                            fileCount++;
                            downloadFromUrl(arrFileName,progressDialog);
                        }
                    }
                });
            } else {
                if (fileCount < arrFileName.size() - 1) {
                    fileCount++;
                    downloadFromUrl(arrFileName,progressDialog);
                }else{
                    if(progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("aud","2");
                    downloadAudioFileNew(arrFileName);
                    //playCircuitInVideoPage();
                }
            }
        }

    }

    private void downloadAudioFileNew(final ArrayList<String> arrFileName) {
        Log.e("audio dowload","123");

        if(fileCountAudio<arrFileName.size()) {
            File mp3file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad" + File.separator + arrFileName.get(fileCountAudio) + ".mp3");

            if (!mp3file.exists()) {
                File wavfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad" + File.separator + arrFileName.get(fileCountAudio) + ".wav");

                if (!wavfile.exists())
                {

                    //////////////////i
                    if(exists(Util.AUDIOWNLOADBASEURL+arrFileName.get(fileCountAudio)+".wav"))
                    {
                        Log.e("wav pre","123");
                        new DownloadFileFromURL(getActivity(), arrFileName.get(fileCountAudio),"Audio", false, ".wav",new DownloadFileFromURL.AsynResponse() {
                            @Override
                            public void processFinish(Boolean output) {
                                if (fileCountAudio == arrFileName.size() - 1) {
                                    // START NEW TASK HERE
                                    //alreadyHave = false;
                                    if(progressDialog != null && progressDialog.isShowing())
                                        progressDialog.dismiss();
                                    //playCircuitInVideoPage();
                                    //  downloadAudioFile(arrFileName);
                                } else {
                                    Log.e("next audio","2");
                                    fileCountAudio++;
                                    downloadAudioFileNew(arrFileName);
                                }
                            }
                        });
                    }
                    else
                    {
                        if(exists(Util.AUDIOWNLOADBASEURL+arrFileName.get(fileCountAudio)+".mp3"))
                        {
                            Log.e("mp3 pre","123");
                            new DownloadFileFromURL(getActivity(), arrFileName.get(fileCountAudio),"Audio", false, ".mp3",new DownloadFileFromURL.AsynResponse() {
                                @Override
                                public void processFinish(Boolean output) {
                                    if (fileCountAudio == arrFileName.size() - 1) {
                                        // START NEW TASK HERE
                                        //alreadyHave = false;
                                        if(progressDialog != null && progressDialog.isShowing())
                                            progressDialog.dismiss();
                                        //playCircuitInVideoPage();
                                        //downloadAudioFile(arrFileName);
                                    } else {
                                        fileCountAudio++;
                                        downloadAudioFileNew(arrFileName);
                                    }
                                }
                            });
                        }
                    }

                }
                else
                {
                    if (fileCountAudio < arrFileName.size() - 1) {
                        Log.e("next audio","1");
                        fileCountAudio++;
                        downloadAudioFileNew(arrFileName);
                    }else{
                        if(progressDialog != null && progressDialog.isShowing())
                            progressDialog.dismiss();
                        //playCircuitInVideoPage();
                    }
                }


            } else {
                if (fileCountAudio < arrFileName.size() - 1) {
                    Log.e("next audio","1");
                    fileCountAudio++;
                    downloadAudioFileNew(arrFileName);
                }else{
                    if(progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    //playCircuitInVideoPage();
                }
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 201:
            {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission write", "Granted");

                    // Util.writePermission=true;
                    new SharedPreference(getActivity()).write("writePer","","true");
                    getDownloadFileName();
                    if(isAdded())
                        playCircuitInVideoPage();

                } else {
                    Log.e("Permission write", "Denied");
                    Util.showToast(getActivity(),"Please enable Write  permission from settings.");
                }
                return;
            }
        }
    }
    /////////////////////////////////


    private boolean hasGalleryPermission()
    {
        int hasPermissionWrite = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED&&hasPermissionRead==PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        else
            return false;
    }
    private void getEquipmentList(SessionOverViewModel.Obj obj) {
        String sessionType="",duration="",bodyArea="",equipment="",total="";
        if(obj.getWorkoutType()!=null)
          sessionType="Session Type: "+obj.getWorkoutType();
        txtSessionType.setText(obj.getWorkoutType());
        total+=sessionType+"\n";
        if(obj.getDuration()!=null)
        {
            if(obj.getDuration()>0)
            {
                duration="Session Duration: "+obj.getDuration()+" mins";
                total+=duration+"\n";
            }

        }
        if(obj.getBodyArea()!=null)
          bodyArea="Body Area: "+obj.getBodyArea();
        total+=bodyArea+"\n";
        txtBodyArea.setText(obj.getBodyArea());
        equipment="Equipment Required: ";
        ArrayList<String> arrEquipment=new ArrayList<>();
        for(int p=0;p<obj.getExercises().size();p++)
        {
            if(obj.getExercises().get(p).getEquipments()!=null)
            {
                for(int k=0;k<obj.getExercises().get(p).getEquipments().size();k++)
                {
                    if(!arrEquipment.contains(obj.getExercises().get(p).getEquipments().get(k)))
                    {
                        arrEquipment.add(obj.getExercises().get(p).getEquipments().get(k));
                    }
                }
            }
            if(obj.getExercises().get(p).getCircuitExercises()!=null)
            {
                for(int j=0;j<obj.getExercises().get(p).getCircuitExercises().size();j++)
                {
                    SessionOverViewModel.Circuit eachCircuit = obj.getExercises().get(p).getCircuitExercises().get(j);
                    List<String> lstEquipmentCircuit = eachCircuit.getEquipments();
                    if(lstEquipmentCircuit!=null)
                        for(int f=0;f<lstEquipmentCircuit.size();f++)
                        {
                            if(!arrEquipment.contains(lstEquipmentCircuit.get(f)))
                            {
                                arrEquipment.add(lstEquipmentCircuit.get(f));
                            }
                        }

                }
            }

        }
        Log.e("print size--",arrEquipment.size()+"?");
        for(int b=0;b<arrEquipment.size();b+=2) {
            Log.e("print row no",b+"?");
            //equipment+="\n";
            equipment += "\u2022 ";

            equipment += arrEquipment.get(b);
            LinearLayout horLayout = new LinearLayout(getActivity());
            horLayout.setOrientation(LinearLayout.HORIZONTAL);

            for (int d = 0; d <= 1; d++) {
                Log.e("print item no",d+"?");
                LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        .05f
                );
                param.setMargins(5,5,5,5);

                LayoutInflater vi = ( LayoutInflater ) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = vi.inflate(R.layout.layout_dynamic_equipment, null);
                v.setLayoutParams(param);
                TextView txtEquip=(TextView )v.findViewById(R.id.txtEquip);
                RelativeLayout rlEdit=(RelativeLayout)v.findViewById(R.id.rlEdit);
                LinearLayout llRoot=(LinearLayout)v.findViewById(R.id.llRoot);

                if(d==0)
                    txtEquip.setText(arrEquipment.get(b));
                else
                    if(b+1<arrEquipment.size())
                      txtEquip.setText(arrEquipment.get(b+1));
                else
                    {
                        llRoot.setVisibility(View.INVISIBLE);
                    }
                rlEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String getEquipName = txtEquip.getText().toString();
                        int parent=0;
                        int child=0;
                        boolean boolLoop=false;
                        Log.e("click edit","123"+getEquipName);
                        for(int g=0;g<lstExercise.size();g++)
                        {

                           for(int v=0;v<lstExercise.get(g).getCircuitExercises().size();v++)
                           {
                              // if(getEquipName.equals(lstExercise.get(g).getCircuitExercises().get(v).getExerciseName()))
                               for(int u=0;u<lstExercise.get(g).getCircuitExercises().get(v).getEquipments().size();u++)
                               {
                                   String equipEach=lstExercise.get(g).getCircuitExercises().get(v).getEquipments().get(u);
                                   if(getEquipName.equals(equipEach))
                                   {
                                       parent=g;
                                       child=v;
                                      /* parent+=child;
                                       parent++;*/
                                       boolLoop=true;
                                      // Log.e("yes match",equipEach+"?"+getEquipName);
                                       Log.e("yes match",parent+"?"+child);

                                       break;
                                   }
                               }
                               if(boolLoop)
                                   break;


                           }
                           if(boolLoop)
                           {
                               Log.e("yes scroll","WORKING"+"?"+parent+"?"+child);

                               int finalParent = parent;

                               changescroll(finalParent,child);


                              break;
                           }
                        }

                    }
                });
                horLayout.addView(v);
            }
            llEquipment.addView(horLayout);
        }



        if(arrEquipment.size()<=0)
        {
            equipment="No Equipment Required";
            txtNeed.setText("No Equipment Required");
        }
        total+=equipment;
       // txtEquipment.setText(total);
    }




    private boolean isSpotifyActive() {
        if (getActivity() != null) {

            if (new SharedPreference(getActivity()).read("spotifyM", "").equals("true"))
                return true;
            else
                return false;
        } else {
            return false;
        }

    }


    @SuppressLint("WrongConstant")
    private int getDayNo(String getDateStr, boolean customMode) {
        Date getDate = null, reqDate = null;
        int dayOfWeek=-1;
        String reqDateStr = "";
        SimpleDateFormat simpleDateFormatGet = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat simpleDateFormatGetCustom = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat simpleDateFormatReq = new SimpleDateFormat("EEEE-dd");

        try {
            if(customMode)
            {
                getDate = simpleDateFormatGetCustom.parse(getDateStr);
            }
            else
              getDate = simpleDateFormatGet.parse(getDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        Calendar c = Calendar.getInstance();
        if(getDate!=null)
        {
            c.setTime(getDate);
            dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

        }
        else
            return -1;

        return dayOfWeek;
    }

    public void getSquadWorkoutSession() {
        //public void getSquadWorkoutSession(final Integer ID, final String selectedDate, final String currentDate)

        if(Util.checkConnection(getActivity())) {
            SessionServiceImpl sessionService=new SessionServiceImpl(getActivity());;
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String,Object> reqHash=new HashMap<>();
            reqHash.put("UserId",Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
            reqHash.put("UserSessionID",Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));
            reqHash.put("Key",Util.KEY_ABBBC);
            reqHash.put("Id",exerciseIdCustom);

            Call<GetSquadWorkoutSessionModel> sessionModelCall=sessionService.getSquadWorkoutSession(reqHash);
            sessionModelCall.enqueue(new Callback<GetSquadWorkoutSessionModel>() {
                @Override
                public void onResponse(Call<GetSquadWorkoutSessionModel> call, Response<GetSquadWorkoutSessionModel> response) {
                    progressDialog.dismiss();
                    if(response.body()!=null)
                    {
                        if(response.body().getSuccess())
                        {
                           // Bundle bundle=new Bundle();

                            trainOptionBundle.putString("SELECTEDDATE",SelectedDate);
                            trainOptionBundle.putString("CALENDERDATE",CalDate);
                            Gson gson=new Gson();
                            trainOptionBundle.putString("EDITMODEL",gson.toJson(response.body()));
                            trainOptionBundle.putString("ADDUPDATE","EDIT");
                            trainOptionBundle.putString("exerciseIdForCustom",exerciseIdCustom+"");
                            trainOptionBundle.putString("Origin","SessionOverview"+"");
                            ((MainActivity)getActivity()).loadFragment(new AddUpdateCustomProgramFragment(),"AddUpdateCustomProgram",trainOptionBundle);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetSquadWorkoutSessionModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else {
            Util.showToast(getActivity(),Util.networkMsg);
        }

    }

    private void toggleFavApi(Integer sessionId, final ImageView imgFav,final int position) {


        if(Connection.checkConnection(getActivity()))
        {
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String,Object> hashMap=new HashMap<>();

            hashMap.put("UserId",Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
            hashMap.put("Key", Util.KEY_ABBBC);
            hashMap.put("UserSessionID",Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));
            hashMap.put("SessionId",sessionId);

            SessionServiceImpl sessionService=new SessionServiceImpl(getActivity());
            Call<ToggleModel> getSessionListCall=sessionService.favoriteSessionToggle(hashMap);
            getSessionListCall.enqueue(new Callback<ToggleModel>() {
                @Override
                public void onResponse(Call<ToggleModel> call, Response<ToggleModel> response) {
                    progressDialog.dismiss();
                    if(response.body()!=null)
                    {
                        if(response.body().getSuccess())
                        {
                            // lstFavData=response.body()();
                            if(response.body().getStatus()) {
                                //imgFav.setImageResource(R.drawable.favourite_active);
                            }
                            else {
                               // imgFav.setImageResource(R.drawable.favourite_disable);
                            }

                          /*  try {
                                int index = lstFavId.indexOf(sessionLists.get(position).getExerciseSessionId());
                                lstFavData.get(index).setIsFavorite(response.body().getStatus());
                            }catch(Exception exp){
                                exp.printStackTrace();
                            }*/
                        }
                        else
                            Util.showToast(getActivity(),"An error occurred");


                    }
                }

                @Override
                public void onFailure(Call<ToggleModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        }else
        {
            Util.showToast(getActivity(),Util.networkMsg);
        }
    }

    private void checkUserProgramStep() {
        FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

        progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

        HashMap<String, Object> reqHash = new HashMap<>();
        reqHash.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
        reqHash.put("Key", Util.KEY);
        reqHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

        Call<JsonObject> jsonObjectCall = finisherService.checkUserProgramStep(reqHash);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                progressDialog.dismiss();
                if (response.body() != null) {
                    if (response.body().get("StepNumber") != null) {
                        sharedPreference.write("CUSTOMSETTINGSSTEPNUMBER", "", response.body().get("StepNumber").toString());
                        if(response.body().get("StepNumber").getAsInt()!=0)
                        {
                            AlertDialogCustom alertDialogCustom=new AlertDialogCustom(getActivity());
                            alertDialogCustom.ShowDialog("Alert!", "You have not completed the exercise settings,complete the exercise settings to use this feature.Do you want to complete it now?", true);
                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                                @Override
                                public void onDone(String title) {
                                    sendToFragment(response.body().get("StepNumber").getAsInt());
                                }

                                @Override
                                public void onCancel(String title) {

                                }
                            });
                        }
                        else if(response.body().get("StepNumber").getAsInt()==0)
                        {
                            if(isViewClicked)
                            {
                                isViewClicked=false;
                                Date currentDateObj=new Date();
                                String todayDate = format.format(currentDateObj);
                               getworkoutFromSessionApi(todayDate);
                            }
                            else{
                                 openDropDownDialog();
                            }



                        }
                           // sendToFragment(response.body().get("StepNumber").getAsInt());

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void sendToFragment(int stepNumber) {

        if (stepNumber == 0) {
            //Toast.makeText(getActivity(),"GO TO CHANGE WEEk PAGE",Toast.LENGTH_LONG).show();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendarGlobal = Calendar.getInstance();
            calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            //Bundle bundle = new Bundle();
            trainOptionBundle.putString("CALENDERDATE", format.format(calendarGlobal.getTime()));

                    trainOptionBundle.putString("origin", "sov");

            ((MainActivity) getActivity()).loadFragment(new CustomProgramFinishFragment(), "CustomProgramFinish", trainOptionBundle);
        } else if (stepNumber == 1) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageOneFragment(), "CustomProgramPageOne", bundle);
        } else if (stepNumber == 2) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageTwoFragment(), "CustomProgramPageTwo", bundle);
        } else if (stepNumber == 3) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageThreeFragment(), "CustomProgramPageThree", bundle);
        } else if (stepNumber == 4) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageFourFragment(), "CustomProgramPageFour", bundle);
        } else if (stepNumber == 5) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageFiveFragment(), "CustomProgramPageFive", bundle);
        } else if (stepNumber == 6) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageSixFragment(), "CustomProgramPageSix", bundle);
        } else if (stepNumber == 7) {
            Bundle bundle = new Bundle();
            if (getArguments() != null) {
                if (getArguments().containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            ((MainActivity) getActivity()).loadFragment(new CustomProgramPageSevenFragment(), "CustomProgramPageSeven", bundle);
        }

    }
    public static boolean exists(String URLName){
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(URLName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}