package com.ashysystem.mbhq.fragment.meditation;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;

import com.ashysystem.mbhq.fragment.live_chat.LiveChatFragment;
import com.ashysystem.mbhq.util.Downloader;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.VideoView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.BackgroundSoundServiceNew;
import com.ashysystem.mbhq.Service.OnClearFromRecentService;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.GuidedMeditationAdapter;
import com.ashysystem.mbhq.adapter.OtherGuidedMeditationAdapter;
import com.ashysystem.mbhq.fragment.CourseArticleDetailsNewFragment;
import com.ashysystem.mbhq.fragment.LearnFragment;

import com.ashysystem.mbhq.fragment.TrainFragment;
import com.ashysystem.mbhq.fragment.WelcomeScrenCheckLIstFragment;
import com.ashysystem.mbhq.model.GuidedMeditationModel;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForMeditation;
import com.ashysystem.mbhq.roomDatabase.viewModel.MeditationViewModel;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.TLSSocketFactory;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.util.VisualizerView;
import com.ashysystem.mbhq.view.visualizer.renderer.BarGraphRenderer;
import com.ashysystem.mbhq.view.visualizer.renderer.CircleBarRenderer;
import com.ashysystem.mbhq.view.visualizer.renderer.CircleRenderer;
import com.ashysystem.mbhq.view.visualizer.renderer.LineRenderer;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MeditationDetailsNew extends Fragment {
    TextView txtChallengeNameBelow, txtAuthor;
    private int seekForwardTime = 30000;
    private int seekBackwardTime = 30000;

    String dataString = "";
    Integer articleId, courseId;
    String author, imageUrl,type;
    MeditationCourseModel.Webinar lstData, lastPlayingCachedData;
    TextView txtBack, txtTime, txtTotalTime, txtMeditationLevel, dialogTxtTime, dialogTxtTotalTime;
    ImageView imgBack, imgPlayPause, imgForward, dialogImgPlayPause, dialogImgForward, dialogImgBack, imgSearchGuidedMeditation, imgNonCuedHelp;
    //com.ashysystem.mbhq.view.visualizer.VisualizerView dialogVisualizerView;
    //com.ashysystem.mbhq.view.audiovisualizer.visualizer.CircleLineVisualizer dialogVisualizerView;
    //com.zjy.audiovisualize.view.CircleVisualizeView dialogVisualizerView;
    com.gauravk.audiovisualizer.visualizer.BlobVisualizer dialogVisualizerView;
    //com.ashysystem.mbhq.util.VisualizerView dialogVisualizerView;
    VideoView videoView;
    Button btnShopForMeditationMask, btnTestMeditationLevel, btnNonCuedHelp;
    private ProgressDialog progressDialog;
    private Handler mediaPlayerHandler = new Handler();
    private SeekBar SeekBarTestPlay, dialogSeekBarTestPlay;
    long globalSeekProgress = 0;
    private MediaPlayer globalMediaPlayer = null;
    private String globalVideoURl = "";
    private RelativeLayout rlDownload;
    private ViewGroup rlNonCuedContainer, guidedRadioContainer, meditationSubActionContainer;
    private CheckBox chkBoxNonCued, chkGuided1, chkGuided2, chkGuided3, chkGuided4, chkGuided5;
    private int lenghtOfFile;
    private String fileName = "";

    private String videoLInk;
    NotificationManager mNotifyManager;
    TextView txtDownload;
    //int fileSize;
    RecyclerView recyclerOtherGuidedMeds;
    OtherGuidedMeditationAdapter otherGuidedMeditationAdapter = new OtherGuidedMeditationAdapter();
    OtherGuidedMeditationAdapter.OnItemClickListener otherGuidedListener = new OtherGuidedMeditationAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(GuidedMeditationModel data) {
            if (musicSrv != null) {
                boolean isSelected = data.isSelected();
                chkBoxNonCued.setChecked(false);
                chkGuided1.setChecked(false);
                chkGuided2.setChecked(false);
                chkGuided3.setChecked(false);
                chkGuided4.setChecked(false);
                chkGuided5.setChecked(false);
                if (isSelected) {
                    musicSrv.enableOtherMeditation(data);
                } else {
                    musicSrv.disableOtherMeditation(data);
                }
                otherGuidedMeditationAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onItemRemoved(GuidedMeditationModel data) {
            if (musicSrv != null) {
                Log.i(TAG, "removed med: " + data.getWebinar().getEventName());
                musicSrv.removeOtherMeditation(data);
                if (musicSrv.getOtherNextMeditations().isEmpty()) {
                    imgSearchGuidedMeditation.setVisibility(View.VISIBLE);
                } else {
                    imgSearchGuidedMeditation.setVisibility(View.GONE);
                }
            }
        }

        @Override
        public void onItemAdded() {
            if (musicSrv != null) {
                String guidedMedList = sharedPreference.read("GUIDED_MEDITATION_LIST", "");

                if (!guidedMedList.isEmpty()) {

                    MeditationCourseModel guidedMeditationModel = new Gson().fromJson(guidedMedList, MeditationCourseModel.class);

                    ArrayList<GuidedMeditationModel> guidedMeditationList = new ArrayList<>();

                    for (int index = 0; index < guidedMeditationModel.getWebinars().size(); index++) {

                        GuidedMeditationModel guidedMeditation = new GuidedMeditationModel();

                        guidedMeditation.setWebinar(guidedMeditationModel.getWebinars().get(index));

                        guidedMeditation.setSelected(false);

                        guidedMeditationList.add(guidedMeditation);

                    }

                    for (int index = 0; index < musicSrv.getOtherNextMeditations().size(); index++) {

                        for (int index2 = 0; index2 < guidedMeditationList.size(); index2++) {

                            if (musicSrv.getOtherNextMeditations().get(index).getWebinar().getEventItemID().equals(guidedMeditationList.get(index2).getWebinar().getEventItemID())) {
                                guidedMeditationList.get(index2).setSelected(true);
                            }

                        }
                    }

                    showGuidedMeditationChooser(guidedMeditationList);

                }
            }
        }
    };

    int notificationId = 1;
    LinearLayout llAction;
    private String vTime = "";
    private Double seekTimeApiLong = 0.0;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = null;

    ViewModelFactoryForMeditation factoryForMeditation;
    MeditationViewModel meditationViewModel;

    String globalVideoLinkForBackgroundPlay = "";
    String searchTagForOfflinePlay = "";
    boolean isPlayingOnlineVideo = false;
    Integer globalCurrentVideoPostion = 0;

    boolean isBroadcastFromServicesCalled = false;
    private BackgroundSoundServiceNew musicSrv = null;
    private boolean musicBound = false;
    VisualizerView mVisualizerView;
    private Visualizer mVisualizer, dialogVisualizer;

    View vi = null;

    boolean isThisLastPlayingData = false;
    boolean requestingPermission = false;
    private String TAG = "MeditationDetailsNew1";
    private FRAGMENT_STATE fragmentState = FRAGMENT_STATE.RESUMED;

    private boolean isVisualizerDialogOpen = false;

    private Dialog visualizerDialog = null;
    private Uri downloadedFileUri = null;

    enum FRAGMENT_STATE {
        RESUMED,
        PAUSED
    }

    SharedPreference sharedPreference;
    ImageView imgMeditation, imgMeditationSubActionShowHide, imgGuidedMeditationShowHide;
    LinearLayout llTestNowWithArrow, llTestNowContent, llShopNowWithArrow, llShopNowContent;
    ImageView imgTestNowArrow, imgShopNowArrow;
    RelativeLayout rlShopMeditationMask;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate Start");


        if (getArguments() != null && getArguments().containsKey("data")) {
            dataString = getArguments().getString("data");
        }
        if (getArguments() != null && getArguments().containsKey("data")) {
            courseId = getArguments().getInt("COURSEID");//
        }
        if (getArguments() != null && getArguments().containsKey("data")) {
            articleId = getArguments().getInt("ARTICLEID");//
        }
        if (getArguments() != null && getArguments().containsKey("data")) {
            author = getArguments().getString("AUTHOR");//
        }
        if (getArguments() != null && getArguments().containsKey("data")) {
            imageUrl = getArguments().getString("IMAGE_URL");//
        }
        if (getArguments() != null && getArguments().containsKey("data")) {
            type = getArguments().getString("type");//
        }

        Gson gson = new Gson();
        try {
            lstData = gson.fromJson(dataString, MeditationCourseModel.Webinar.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            Util.showToast(getActivity(), "Something went wrong");
        }

/*
        try {
            lastPlayingCachedData = gson.fromJson(Util.strMeditationDetailsForBackground, MeditationCourseModel.Webinar.class);
            if (lastPlayingCachedData.getEventName().equals(lstData.getEventName())) {
                isThisLastPlayingData = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        Log.i(TAG, "onCreate End");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView start");

        View vi = inflater.inflate(R.layout.layout_meditation_details, container, false);
        factoryForMeditation = Injection.provideViewModelFactoryMeditation(requireActivity());
        //  meditationViewModel = ViewModelProviders.of(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);
        meditationViewModel = new ViewModelProvider(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);

        initView(vi);
        disableMediaControls();

      //  funToolBar();
        populateData();

        String[] segments = videoLInk.split("/");
        String lastSegment = segments[segments.length - 1];
        searchTagForOfflinePlay = lastSegment;
        downloadedFileUri = Util.getDownloadedFileUri(getContext(), lstData.getDownloadid());
        Log.e("D_URI", "onReceive: " + downloadedFileUri);


        Log.i(TAG, "onCreateView End");
        return vi;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("dataString", dataString);
    }

/*
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated Start");

        if (savedInstanceState != null) {

            dataString = savedInstanceState.getString("dataString");

            Gson gson = new Gson();
            try {
                lstData = gson.fromJson(dataString, MeditationCourseModel.Webinar.class);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                Util.showToast(getActivity(), "Something went wrong");
            }

*/
/*
            try {
                lastPlayingCachedData = gson.fromJson(Util.strMeditationDetailsForBackground, MeditationCourseModel.Webinar.class);
                if (lastPlayingCachedData.getEventName().equals(lstData.getEventName())) {
                    isThisLastPlayingData = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
*//*

        }

        factoryForMeditation = Injection.provideViewModelFactoryMeditation(requireActivity());
      //  meditationViewModel = ViewModelProviders.of(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);
        meditationViewModel = new ViewModelProvider(requireActivity(), factoryForMeditation).get(MeditationViewModel.class);

        disableMediaControls();

        funToolBar();
        populateData();

        String[] segments = videoLInk.split("/");
        String lastSegment = segments[segments.length - 1];
        searchTagForOfflinePlay = lastSegment;





        downloadedFileUri = Util.getDownloadedFileUri(getContext(), lstData.getDownloadid());
        Log.e("D_URI", "onReceive: " + downloadedFileUri);

       */
/* if (downloadedFileUri != null) {
            // Use the downloaded file URI as needed
            // musicSrv.createMediaPlayer();
        } else {
            // The file may not be downloaded yet or encountered an error
        }*//*


        Log.i(TAG, "onActivityCreated End");
    }
*/

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(broadcastReceiver,new IntentFilter("MediaNotification"));
        getActivity().startService(new Intent(getContext(), OnClearFromRecentService.class));
        /*IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        getActivity().registerReceiver(broadcastReceiverForScreen,filter);*/


        fragmentState = FRAGMENT_STATE.RESUMED;
        Log.i("MeditationDetailsNew", "onResume start");
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
       // ((MainActivity) getActivity()).funDrawer();//commented by jyoti
        if (musicSrv != null) {
            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
        }

    }

    @Override
    public void onPause() {
        super.onPause();

        Log.i("MeditationDetailsNew", "onPause start");

        fragmentState = FRAGMENT_STATE.PAUSED;

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
//        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
//        toolbar.setNavigationIcon(null);//comment by jyoti

        if (!requestingPermission) {
            try {
                Log.i("MeditationDetailsNew", "onPause try");
                if (musicSrv != null) {
                    lstData.getEventItemVideoDetails().get(0).setTime(((musicSrv.currentMediaPosition()) / 1000) + "");
                }
                Gson gson = new Gson();
                String strData = gson.toJson(lstData);
                MeditationEntity meditationEntity = new MeditationEntity(lstData.getEventItemID(), strData, true, false);
                Log.i("MeditationDetailsNew", "onPause try2");
                mDisposable.add(meditationViewModel.updateMeditation(meditationEntity)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Log.i(TAG, "Meditation insert => TRUE");
                            //((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                            //MeditationFragment meditationFragment = new MeditationFragment();
                            //((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", getArguments());
                        }, throwable -> {
                            Log.i(TAG, "Meditation insert => FALSE");
                        }));
            } catch (Exception e) {
                Log.i("MeditationDetailsNew", "onPause catch");
                e.printStackTrace();
            }
        }

        Log.i("MeditationDetailsNew", "onPause end");

    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("MeditationDetailsNew", "onStop");

        mDisposable.clear();

        try {
            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (musicConnection != null) {
                requireActivity().unbindService(musicConnection);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void populateData() {

        if (lstData.getImageUrl() != null && !lstData.getImageUrl().equals("")) {
            Util.downloadphotowithGlide(getActivity(), imgMeditation, lstData.getImageUrl());
        } else {
            Log.e("MEDITATION", "IMAGE-NULL");
        }

        try {
            txtChallengeNameBelow.setText(lstData.getEventName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            txtAuthor.setText(lstData.getPresenterName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            fileName = lstData.getEventItemVideoDetails().get(0).getDownloadURL();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            videoLInk = lstData.getEventItemVideoDetails().get(0).getDownloadURL();
            videoLInk.replaceAll(" ", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            /*File file = new File(getActivity().getCacheDir(), "SQUAD/" + fileName);
            if (!file.exists()) {
                getUserPermission();
                videoLInk = lstData.getEventItemVideoDetails().get(0).getAppURL();
                videoLInk.replaceAll(" ", "");
                progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...", true);
                progressDialog.setCancelable(true);
                //PlayVideo(videoLInk);
                globalVideoLinkForBackgroundPlay = videoLInk;
                isPlayingOnlineVideo = true;
                playBackgroundMusic(globalCurrentVideoPostion);
            } else {
                ///////////////
                if (lstData.getEventItemVideoDetails().size() > 0 && lstData.getEventItemVideoDetails().get(0).getTime() != null && !lstData.getEventItemVideoDetails().get(0).getTime().equals("")) {
                    vTime = lstData.getEventItemVideoDetails().get(0).getTime();
                    seekTimeApiLong = Double.parseDouble(vTime);
                    seekTimeApiLong = seekTimeApiLong * 1000;
                    globalCurrentVideoPostion = seekTimeApiLong.intValue();
                }

                //////////////////

                globalVideoLinkForBackgroundPlay = file.getAbsolutePath();
                isPlayingOnlineVideo = false;

                txtDownload.setText("Downloaded Meditation");
                llAction.setVisibility(View.GONE);
                Uri videoUrl = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", new File(file.getAbsolutePath()));
                progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...", true);
                playBackgroundMusic(globalCurrentVideoPostion);

            }*/
            /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
           // getUserPermission();
            videoLInk = lstData.getEventItemVideoDetails().get(0).getAppURL();
            videoLInk.replaceAll(" ", "");
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...", true);
            progressDialog.setCancelable(true);
            //PlayVideo(videoLInk);
            globalVideoLinkForBackgroundPlay = videoLInk;
            isPlayingOnlineVideo = true;
            playBackgroundMusic(globalCurrentVideoPostion);
            ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        } catch (Exception e) {
            e.printStackTrace();
        }


        // PlayVideo(lstData.getEventItemVideoDetails().get(0).getAppURL());
    }

    private void initView(View vi) {
        mVisualizerView = (VisualizerView) vi.findViewById(R.id.myvisualizerview);
        llAction = vi.findViewById(R.id.llAction);
        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        txtDownload = vi.findViewById(R.id.txtDownload);
        rlDownload = vi.findViewById(R.id.rlDownload);
        SeekBarTestPlay = vi.findViewById(R.id.SeekBarTestPlay);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            SeekBarTestPlay.getProgressDrawable().setColorFilter(
                    requireContext().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        } else {
            SeekBarTestPlay.getProgressDrawable().setColorFilter(
                    getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        }
        txtChallengeNameBelow = vi.findViewById(R.id.txtChallengeNameBelow);
        txtAuthor = vi.findViewById(R.id.txtAuthor);
        txtBack = vi.findViewById(R.id.txtBack);
        imgForward = vi.findViewById(R.id.imgForward);
        imgBack = vi.findViewById(R.id.imgBack);
        imgPlayPause = vi.findViewById(R.id.imgPlayPause);
        videoView = vi.findViewById(R.id.videoView);
        txtTime = vi.findViewById(R.id.txtTime);
        txtTotalTime = vi.findViewById(R.id.txtTotalTime);
        txtMeditationLevel = vi.findViewById(R.id.txtMeditationLevel);
        btnShopForMeditationMask = vi.findViewById(R.id.btnShopForMeditationMask);
        btnTestMeditationLevel = vi.findViewById(R.id.btnTestMeditationLevel);
        rlNonCuedContainer = vi.findViewById(R.id.rlNonCuedContainer);
        guidedRadioContainer = vi.findViewById(R.id.guidedRadioContainer);
        chkBoxNonCued = vi.findViewById(R.id.chkBoxNonCued);
        chkGuided1 = vi.findViewById(R.id.chkGuided1);
        chkGuided2 = vi.findViewById(R.id.chkGuided2);
        chkGuided3 = vi.findViewById(R.id.chkGuided3);
        chkGuided4 = vi.findViewById(R.id.chkGuided4);
        chkGuided5 = vi.findViewById(R.id.chkGuided5);

        btnNonCuedHelp = vi.findViewById(R.id.btnNonCuedHelp);
        imgMeditation = vi.findViewById(R.id.imgMeditation);
        llTestNowWithArrow = vi.findViewById(R.id.llTestNowWithArrow);
        llTestNowContent = vi.findViewById(R.id.llTestNowContent);
        llShopNowWithArrow = vi.findViewById(R.id.llShopNowWithArrow);
        llShopNowContent = vi.findViewById(R.id.llShopNowContent);
        imgTestNowArrow = vi.findViewById(R.id.imgTestNowArrow);
        imgShopNowArrow = vi.findViewById(R.id.imgShopNowArrow);
        rlShopMeditationMask = vi.findViewById(R.id.rlShopMeditationMask);

        imgMeditationSubActionShowHide = vi.findViewById(R.id.imgMeditationSubActionShowHide);

        meditationSubActionContainer = vi.findViewById(R.id.meditationSubActionContainer);

        imgGuidedMeditationShowHide = vi.findViewById(R.id.imgGuidedMeditationShowHide);

        imgNonCuedHelp = vi.findViewById(R.id.imgNonCuedHelp);
        imgSearchGuidedMeditation = vi.findViewById(R.id.imgSearchGuidedMeditation);

        recyclerOtherGuidedMeds = vi.findViewById(R.id.recyclerOtherGuidedMeds);

        recyclerOtherGuidedMeds.setNestedScrollingEnabled(false);

        recyclerOtherGuidedMeds.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        recyclerOtherGuidedMeds.setAdapter(otherGuidedMeditationAdapter);

        otherGuidedMeditationAdapter.setListener(otherGuidedListener);

        meditationSubActionContainer.setVisibility(View.GONE);
        imgMeditationSubActionShowHide.setImageResource(R.drawable.ic_down_double_arrow_black);

        imgMeditationSubActionShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (meditationSubActionContainer.getVisibility() == View.VISIBLE) {
                    meditationSubActionContainer.setVisibility(View.GONE);
                    imgMeditationSubActionShowHide.setImageResource(R.drawable.ic_down_double_arrow_black);
                } else {
                    meditationSubActionContainer.setVisibility(View.VISIBLE);
                    imgMeditationSubActionShowHide.setImageResource(R.drawable.ic_up_double_arrow_black);
                    meditationSubActionContainer.requestFocus();
                }


            }
        });

        guidedRadioContainer.setVisibility(View.VISIBLE);
        imgGuidedMeditationShowHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (guidedRadioContainer.getVisibility() == View.VISIBLE) {
                    guidedRadioContainer.setVisibility(View.GONE);
                    imgGuidedMeditationShowHide.setImageResource(R.drawable.ic_down_arrow_black);
                } else {
                    guidedRadioContainer.setVisibility(View.VISIBLE);
                    imgGuidedMeditationShowHide.setImageResource(R.drawable.ic_up_arrow_black);
                    guidedRadioContainer.requestFocus();
                }

            }
        });


        btnNonCuedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(requireContext(), R.style.DialogThemeAnother);
                dialog.setContentView(R.layout.dialog_non_cued_meditation_help_new);
                ImageView imgCross = dialog.findViewById(R.id.imgCross);
                RelativeLayout rlGotIt = dialog.findViewById(R.id.rlGotIt);
                imgCross.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                rlGotIt.setOnClickListener(view -> {
                    dialog.dismiss();
                });
                dialog.show();
            }
        });

        imgNonCuedHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnNonCuedHelp.performClick();
            }
        });

        imgSearchGuidedMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String guidedMedList = sharedPreference.read("GUIDED_MEDITATION_LIST", "");

                if (!guidedMedList.isEmpty()) {

                    MeditationCourseModel guidedMeditationModel = new Gson().fromJson(guidedMedList, MeditationCourseModel.class);

                    ArrayList<GuidedMeditationModel> guidedMeditationList = new ArrayList<>();

                    for (int index = 0; index < guidedMeditationModel.getWebinars().size(); index++) {

                        GuidedMeditationModel guidedMeditation = new GuidedMeditationModel();

                        guidedMeditation.setWebinar(guidedMeditationModel.getWebinars().get(index));

                        guidedMeditation.setSelected(false);

                        guidedMeditationList.add(guidedMeditation);

                    }

                    showGuidedMeditationChooser(guidedMeditationList);

                } else {
                    getGuidedMeditationList();
                }


            }
        });

        llTestNowWithArrow.setOnClickListener(view -> {
            if (llTestNowContent.getVisibility() == View.GONE) {
                llTestNowContent.setVisibility(View.VISIBLE);
                imgTestNowArrow.setImageResource(R.drawable.ic_up_arrow_black);
            } else {
                llTestNowContent.setVisibility(View.GONE);
                imgTestNowArrow.setImageResource(R.drawable.ic_down_arrow_black);
            }
        });

        llShopNowWithArrow.setOnClickListener(view -> {
            if (llShopNowContent.getVisibility() == View.GONE) {
                llShopNowContent.setVisibility(View.VISIBLE);
                imgShopNowArrow.setImageResource(R.drawable.ic_up_arrow_black);
            } else {
                llShopNowContent.setVisibility(View.GONE);
                imgShopNowArrow.setImageResource(R.drawable.ic_down_arrow_black);
            }
        });

        sharedPreference = new SharedPreference(getActivity());

        if (sharedPreference.readBoolean("SHOW_SHOP_NOW_FOR_MEDITATIONS", "")) {
            rlShopMeditationMask.setVisibility(View.VISIBLE);
        } else {
            rlShopMeditationMask.setVisibility(View.GONE);
        }

/*
        CompoundButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int buttonId = buttonView.getId();

                Log.i(TAG, "button id: " + buttonId + " ischecked: " + isChecked);


                if (buttonId == chkBoxNonCued.getId()) {

                    //uncheckAllTickBoxes();

                    if (isChecked) {
                        //chkBoxNonCued.setChecked(false);
                        chkGuided1.setChecked(false);
                        chkGuided2.setChecked(false);
                        chkGuided3.setChecked(false);
                        chkGuided4.setChecked(false);
                        chkGuided5.setChecked(false);

                        musicSrv.setNextMeditation(lstData.getNoCueUrl(), BackgroundSoundServiceNew.NextPlayableMediaType.NO_CUE);

                    } else {
                        musicSrv.setNextMeditation("", BackgroundSoundServiceNew.NextPlayableMediaType.NONE);
                        //musicSrv.setShouldPlayNonCuedAfterWards(false);
                    }

                    Log.i(TAG, "non cued button id: " + buttonId + " ischecked: " + isChecked);


                } else if (buttonId == chkGuided1.getId()) {

                    //uncheckAllTickBoxes();

                    if (isChecked) {
                        chkBoxNonCued.setChecked(false);
                        //chkGuided1.setChecked(false);
                        chkGuided2.setChecked(false);
                        chkGuided3.setChecked(false);
                        chkGuided4.setChecked(false);
                        chkGuided5.setChecked(false);

                        musicSrv.setNextMeditation(lstData.getGuided1Url(), BackgroundSoundServiceNew.NextPlayableMediaType.GUIDED1);

                    } else {
                        musicSrv.setNextMeditation("", BackgroundSoundServiceNew.NextPlayableMediaType.NONE);
                        //musicSrv.setShouldPlayNonCuedAfterWards(false);
                    }

                    Log.i(TAG, "guided 1 button id: " + buttonId + " ischecked: " + isChecked);

                } else if (buttonId == chkGuided2.getId()) {

                    if (isChecked) {
                        chkBoxNonCued.setChecked(false);
                        chkGuided1.setChecked(false);
                        //chkGuided2.setChecked(false);
                        chkGuided3.setChecked(false);
                        chkGuided4.setChecked(false);
                        chkGuided5.setChecked(false);

                        musicSrv.setNextMeditation(lstData.getGuided2Url(), BackgroundSoundServiceNew.NextPlayableMediaType.GUIDED2);

                    } else {
                        musicSrv.setNextMeditation("", BackgroundSoundServiceNew.NextPlayableMediaType.NONE);
                        //musicSrv.setShouldPlayNonCuedAfterWards(false);
                    }

                    Log.i(TAG, "guided 2 button id: " + buttonId + " ischecked: " + isChecked);

                } else if (buttonId == chkGuided3.getId()) {

                    //uncheckAllTickBoxes();

                    if (isChecked) {
                        chkBoxNonCued.setChecked(false);
                        chkGuided1.setChecked(false);
                        chkGuided2.setChecked(false);
                        //chkGuided3.setChecked(false);
                        chkGuided4.setChecked(false);
                        chkGuided5.setChecked(false);

                        musicSrv.setNextMeditation(lstData.getGuided3Url(), BackgroundSoundServiceNew.NextPlayableMediaType.GUIDED3);

                    } else {
                        musicSrv.setNextMeditation("", BackgroundSoundServiceNew.NextPlayableMediaType.NONE);
                        //musicSrv.setShouldPlayNonCuedAfterWards(false);
                    }

                    Log.i(TAG, "guided 3 button id: " + buttonId + " ischecked: " + isChecked);

                } else if (buttonId == chkGuided4.getId()) {

                    //uncheckAllTickBoxes();

                    if (isChecked) {
                        chkBoxNonCued.setChecked(false);
                        chkGuided1.setChecked(false);
                        chkGuided2.setChecked(false);
                        chkGuided3.setChecked(false);
                        //chkGuided4.setChecked(false);
                        chkGuided5.setChecked(false);

                        musicSrv.setNextMeditation(lstData.getGuided4Url(), BackgroundSoundServiceNew.NextPlayableMediaType.GUIDED4);

                    } else {
                        musicSrv.setNextMeditation("", BackgroundSoundServiceNew.NextPlayableMediaType.NONE);
                        //musicSrv.setShouldPlayNonCuedAfterWards(false);

                    }

                    Log.i(TAG, "guided 4 button id: " + buttonId + " ischecked: " + isChecked);

                } else if (buttonId == chkGuided5.getId()) {

                    //uncheckAllTickBoxes();

                    if (isChecked) {
                        chkBoxNonCued.setChecked(false);
                        chkGuided1.setChecked(false);
                        chkGuided2.setChecked(false);
                        chkGuided3.setChecked(false);
                        chkGuided4.setChecked(false);
                        //chkGuided5.setChecked(false);
                        musicSrv.setNextMeditation(lstData.getGuided5Url(), BackgroundSoundServiceNew.NextPlayableMediaType.GUIDED5);

                    } else {
                        musicSrv.setNextMeditation("", BackgroundSoundServiceNew.NextPlayableMediaType.NONE);
                        //musicSrv.setShouldPlayNonCuedAfterWards(false);

                    }

                    Log.i(TAG, "guided 5 button id: " + buttonId + " ischecked: " + isChecked);

                } else {
                    Log.i(TAG, "no button found. button id: " + buttonId);
                }

                otherGuidedMeditationAdapter.setData(musicSrv.getOtherNextMeditations());
            }
        };
*/

      /*  chkBoxNonCued.setOnCheckedChangeListener(onCheckedChangeListener);
        chkGuided1.setOnCheckedChangeListener(onCheckedChangeListener);
        chkGuided2.setOnCheckedChangeListener(onCheckedChangeListener);
        chkGuided3.setOnCheckedChangeListener(onCheckedChangeListener);
        chkGuided4.setOnCheckedChangeListener(onCheckedChangeListener);
        chkGuided5.setOnCheckedChangeListener(onCheckedChangeListener);
*/


        /*chkBoxNonCued.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    musicSrv.setShouldPlayNonCuedAfterWards(isChecked);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });*/

        btnShopForMeditationMask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Uri uri = Uri.parse("https://www.mindbodyhq.com/meditationmask");
                    Uri uri = Uri.parse(sharedPreference.read("SHOP_URL_FOR_MEDITATIONS", ""));
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.android.chrome");
                    requireActivity().startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        btnTestMeditationLevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String URL = "https://meditate.emotionalfitnessclub.com/members?token=" + sharedPreference.read("MEDITATION_TEST_NOW_TOKEN", "");
                //Uri uri = Uri.parse("http://meditate.mindbodyhq.com");
                Uri uri = Uri.parse(URL);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.setPackage("com.android.chrome");
                requireActivity().startActivity(intent);
            }
        });

        Integer currentMeditationLevel = lstData.getLevel();
        txtMeditationLevel.setText(String.format(getString(R.string.meditation_details_short), currentMeditationLevel.toString()));

        if (lstData.getTags().contains("Healing Meditations")) {
            txtMeditationLevel.setVisibility(View.GONE);
        } else {
            txtMeditationLevel.setVisibility(View.VISIBLE);
        }

        if (lstData.getNoCueUrl().isEmpty()) {
            rlNonCuedContainer.setVisibility(View.GONE);
        } else {
            rlNonCuedContainer.setVisibility(View.VISIBLE);
            if (lstData.getTags().contains("Guided Meditation")) {
                imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
                imgSearchGuidedMeditation.setVisibility(View.GONE);
                btnNonCuedHelp.setVisibility(View.GONE);
            } else {
                imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
                imgSearchGuidedMeditation.setVisibility(View.VISIBLE);
                btnNonCuedHelp.setVisibility(View.GONE);
            }
        }

        Log.i(TAG, "data : " + new Gson().toJson(lstData));

        if (lstData.getGuided1Title().isEmpty() || lstData.getGuided1Url().isEmpty()) {
            chkGuided1.setVisibility(View.GONE);
        } else {
            chkGuided1.setVisibility(View.VISIBLE);
            chkGuided1.setText(lstData.getGuided1Title());
            imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
        }

        if (lstData.getGuided2Title().isEmpty() || lstData.getGuided2Url().isEmpty()) {
            chkGuided2.setVisibility(View.GONE);
        } else {
            chkGuided2.setVisibility(View.VISIBLE);
            chkGuided2.setText(lstData.getGuided2Title());
            imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
        }

        if (lstData.getGuided3Title().isEmpty() || lstData.getGuided3Url().isEmpty()) {
            chkGuided3.setVisibility(View.GONE);
        } else {
            chkGuided3.setVisibility(View.VISIBLE);
            chkGuided3.setText(lstData.getGuided3Title());
            imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
        }

        if (lstData.getGuided4Title().isEmpty() || lstData.getGuided4Url().isEmpty()) {
            chkGuided4.setVisibility(View.GONE);
        } else {
            chkGuided4.setVisibility(View.VISIBLE);
            chkGuided4.setText(lstData.getGuided4Title());
            imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
        }

        if (lstData.getGuided5Title().isEmpty() || lstData.getGuided5Url().isEmpty()) {
            chkGuided5.setVisibility(View.GONE);
        } else {
            chkGuided5.setVisibility(View.VISIBLE);
            chkGuided5.setText(lstData.getGuided5Title());
            imgGuidedMeditationShowHide.setVisibility(View.VISIBLE);
        }

        rlDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getUserPermission();
                //funDownloadVideoFollowAlong(lstData.getEventItemVideoDetails().get(0).getDownloadURL(),fileName);
            }
        });
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("livechat".equalsIgnoreCase(Util.backto)){
                    Util.backto="";
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new LiveChatFragment());
                    ((MainActivity) requireActivity()).loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

                }else if("course".equalsIgnoreCase(Util.backto)){
                    Util.backto="";
                    Bundle b=new Bundle();
                    b.putInt("COURSEID", courseId);
                    b.putInt("ARTICLEID", articleId);
                    b.putString("AUTHOR", author);
                    b.putString("type", type);
                    b.putString("IMAGE_URL", imageUrl);
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseArticleDetailsNewFragment());
                    ((MainActivity) getActivity()).loadFragment(new CourseArticleDetailsNewFragment(), "CourseArticleDetailsNew", b);

//    ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());
//    ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "CourseFragment", getArguments());

                }else{
                    handleback(true);
                }

            }
        });

        imgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (musicBound) {
                        Log.i(TAG, "music bound true");
                        if (musicSrv != null) {
                            Log.i(TAG, "media player not null");
                            if (musicSrv.isMediaPlaying()) {
                                Log.i(TAG, "media player stopped");
                                musicSrv.pauseMedia();
                                imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                            } else {
                                /// link dwnld + dwnld id sp store
                                Log.e("DWNLOAD_DATA1", "onClick: " + globalVideoLinkForBackgroundPlay);
                                Log.e("DWNLOAD_DATA2", "onClick: " + lstData.getUniqueName());
                                Log.i(TAG, "media player started");

                                String[] segments = globalVideoLinkForBackgroundPlay.split("/");
                                String lastSegment = segments[segments.length - 1];

                                long dwnldId =  Util.downloadFile(requireActivity().getBaseContext(),fileName,lastSegment,""); /////////
                                List<MeditationCourseModel.Webinar> lstTotalDataM = new ArrayList<>();

                                List<MeditationCourseModel.Webinar> lstTotalDataM_ = new ArrayList<>();

                                SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                                String jsonData = preferences.getString("my_downloaded_medicine", "");

                                if (jsonData.isEmpty()) {
                                    lstData.setDownloadid(dwnldId);
                                    lstTotalDataM.add(lstData);
                                    SharedPreferences.Editor editor = preferences.edit();
//                                sharedPreference.write("my_downloaded_medicine", "", new Gson().toJson(testViewModel.lstTotalDataM));
                                    editor.putString("my_downloaded_medicine", new Gson().toJson(lstTotalDataM));
                                    editor.apply();

                                }else{
                                    lstTotalDataM_ = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());

                                    if(0==lstData.getDownloadid()){
                                        lstData.setDownloadid(dwnldId);
                                        lstTotalDataM.add(lstData);
                                        List<MeditationCourseModel.Webinar> concatinatelist = new ArrayList<>();
                                        concatinatelist.addAll(lstTotalDataM_);
                                        concatinatelist.addAll(lstTotalDataM);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("my_downloaded_medicine", new Gson().toJson(concatinatelist));
                                        editor.apply();
                                    }

                                }
                                musicSrv.startMedia();
                                /*if (!musicSrv.isPlayingNonCued()) {
                                    setDurationTime();
                                }*/
                                imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
                                requireActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
                                openVisualizerDialog(musicSrv.getMediaPlayer());
                            }
                        } else {
                            Log.i(TAG, "media player null");
                        }
                    } else {
                        Log.i(TAG, "music bound false");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    int currentPosition = musicSrv.currentMediaPosition();
                    // check if seekForward time is lesser than song duration
                    if (currentPosition + seekForwardTime <= musicSrv.totalMediaDuration()) {
                        // forward song
                        Log.i("seek","seek_1");
                        musicSrv.seekMedia(currentPosition + seekForwardTime);
                    } else {
                        // forward to end position
                        Log.i("seek","seek_2");
                        musicSrv.seekMedia(musicSrv.totalMediaDuration());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int currentPosition = musicSrv.currentMediaPosition();
                    // check if seekBackward time is greater than 0 sec
                    if (currentPosition - seekBackwardTime >= 0) {
                        // forward song
                        Log.i("seek","seek_3");
                        musicSrv.seekMedia(currentPosition - seekBackwardTime);
                    } else {
                        // backward to starting position
                        Log.i("seek","seek_4");
                        musicSrv.seekMedia(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                try {
                    //Log.i(TAG, "seek no-->" + (progress * 1000) + "?" + progress);
                    if (progress == 100) {
                        PowerManager powerManager = (PowerManager) getContext().getSystemService(Context.POWER_SERVICE);
                        boolean isScreenAwake = (Build.VERSION.SDK_INT < 20 ? powerManager.isScreenOn() : powerManager.isInteractive());
                        KeyguardManager myKM = (KeyguardManager) getContext().getSystemService(Context.KEYGUARD_SERVICE);
                        boolean isPhoneLocked = myKM.inKeyguardRestrictedInputMode();
                        boolean isKeyGuardLocked = myKM.isKeyguardLocked();
                        Log.i(TAG, "isScreen On :" + isScreenAwake + "isPhoneLocked :" + isPhoneLocked + "isKeyGuardLocked :" + isKeyGuardLocked + "fragmentState :" + fragmentState);
                        if (musicSrv.isPlayingNonCued()) {
                            //do nothing
                        } else if (musicSrv.shouldPlayNonCuedAfterWards()) {
                            /*musicSrv.seekMedia(0);
                            musicSrv.pauseMedia();
                            musicSrv.playNonCue();
                            try {
                                if (musicSrv != null && musicSrv.getMediaPlayer() != null) {
                                    setUpDialogVisualizerFX(musicSrv.getMediaPlayer().getAudioSessionId());
                                    dialogVisualizer.setEnabled(true);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*/
                        } else if (isScreenAwake && !isPhoneLocked && !isKeyGuardLocked && fragmentState == FRAGMENT_STATE.RESUMED) {
                            Log.i(TAG, "stop the media");
                            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                            Log.i("seek","seek_5");
                            musicSrv.seekMedia(0);
                            musicSrv.pauseMedia();
                            Log.i(TAG, "stop the media2");
                            try {
                                dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
                                visualizerDialog.dismiss();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            Log.i(TAG, "stop the media3");
                        }
                        //Log.i(TAG, "chkBoxNonCued.isChecked() => " + musicSrv.shouldPlayNonCuedAfterWards());
                    } else {
                        if (fromUser) {
                            int currentPosition = progressToTimer(progress, musicSrv.totalMediaDuration());
                            Log.i(TAG, "current pos" + currentPosition);
                            Log.i("seek","seek_6");
                            musicSrv.seekMedia(currentPosition);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                try {
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    Log.i(TAG, "onStartTrackingTouch");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                try {
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);

                    mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);

                    Log.i(TAG, "onStopTrackingTouch");
                    Log.i(TAG, "onStopTrackingTouch: " + seekBar.getProgress());

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        SeekBarTestPlay.setOnSeekBarChangeListener(onSeekBarChangeListener);

        SeekBarTestPlay.setProgress(0);
        SeekBarTestPlay.setMax(100);

    }


    private void funToolBar() {
       // ((MainActivity) getActivity()).funDrawer(); //commented by jyoti
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
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));
//        imgHelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (((MainActivity) getActivity()).getDrawer().isDrawerOpen(Gravity.END)) {
//                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
//                    intent.putExtra("VIDEO_URL", "https://player.vimeo.com/external/290408257.m3u8?s=eb4b9b3ed9aff17c0a6b8297ad209d7c8d6ed8d6");
//                    getActivity().startActivity(intent);
//                } else {
//                    Intent intent = new Intent(getActivity(), ShowVideoActivity.class);
//                    intent.putExtra("VIDEO_URL", "https://player.vimeo.com/external/290408217.m3u8?s=f0b76fab6bfec350339727655d99961f15132eca");
//                    getActivity().startActivity(intent);
//                }
//            }
//        });  //commented by jyotirmoy

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  if(origin.equals(""))
                    ((MainActivity)getActivity()).loadFragment(new LearnFragment(),"Learn",null);
                else
                {
                    ((MainActivity)getActivity()).loadFragment(new FBWHome(),"FBWHome",null);

                }*/


                if (getArguments() != null) {
                    if (getArguments().containsKey("origin")) {
                        if (getArguments().getString("origin").equals("chk")) {
                            ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                        } else if (getArguments().containsKey("NEWHOME")) {
                           // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", getArguments()); //commented by jyoti
                        } else if (getArguments().getString("origin").equals("learn")) {
                            ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                        } else {
                            //  ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", null);
                            /*if(origin.equals(""))
                                ((MainActivity)getActivity()).loadFragment(new LearnFragment(),"Learn",null);
                            else*/
                            {
                                ((MainActivity) getActivity()).loadFragment(new TrainFragment(), "Train", null);

                            }
                        }
                    } else {
                        if (getArguments().containsKey("NEWHOME")) {
                           // ((MainActivity) getActivity()).loadFragment(new HomeFragment(), "Home", null); //commented by jyoti
                        }
                    }
                } else {
                    if (new SharedPreference(getActivity()).read("compChk", "").equals("false"))

                        ((MainActivity) getActivity()).loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", null);
                    else
                        ((MainActivity) getActivity()).loadFragment(new LearnFragment(), "Learn", null);
                }
            }
        });
    }


    private void doUnbindService() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (isMyServiceRunning(BackgroundSoundServiceNew.class) && ((MainActivity) getActivity()).getMusicConnection() != null) {
                        getActivity().unbindService(((MainActivity) getActivity()).getMusicConnection());
                        musicBound = false;
                        Log.i(TAG, "doUnbindService");
                        ((MainActivity) getActivity()).setMusicConnection(null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        requireActivity().unregisterReceiver(broadcastReceiver);
        //getActivity().unregisterReceiver(broadcastReceiverForScreen);
        // getActivity().unregisterReceiver(networkChangeReceiver);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private Runnable mediaUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = musicSrv.totalMediaDuration();
            long currentDuration = musicSrv.currentMediaPosition();



            txtTime.setText("" + milliSecondsToTimer(currentDuration));
            txtTotalTime.setText("" + milliSecondsToTimer(totalDuration));

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            globalSeekProgress = currentDuration;
            try {
                SeekBarTestPlay.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                dialogTxtTime.setText("" + milliSecondsToTimer(currentDuration));
                dialogTxtTotalTime.setText("" + milliSecondsToTimer(totalDuration));
                dialogSeekBarTestPlay.setProgress(progress);

            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaPlayerHandler.postDelayed(this, 1000);
        }
    };

    public String milliSecondsToTimer(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     *
     * @param currentDuration
     * @param totalDuration
     */
    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     *
     * @param progress      -
     * @param totalDuration returns current duration in milliseconds
     */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    class DownloadClass extends AsyncTask<String, String, String> {
        String type = "";
        //   String extension = "";
        ProgressDialog progressDialog;
        private Context context;
        private String fileUrl = "";


        public DownloadClass(Context context, String fileUrl, String type, String extension) {

            this.context = context;
            // this.fileUrl = fileUrl;
            this.fileUrl = "https://docs.google.com/uc?export=download&id=1NmdZuEQmxyYgptuPJtacoFC3d8Yj8bQY";
            this.type = type;
            ///  this.extension = extension;
            progressDialog = new ProgressDialog(context);
            // progressDialog.setMessage("Please wait..");

            execute();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progressDialog.show();
            Log.e("starting", "00");

        }

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            Log.e("progress---", progress[0] + "??");
            // progressBar.setProgress(Integer.parseInt(progress[0]));
            // txtProgress.setText(progress[0]);
            //Log.e("progress", progress[0]);
        }

        @Override
        protected void onPostExecute(String file_url) {
            super.onPostExecute(file_url);
            // progressDialog.dismiss();
            //imgDownload.setVisibility(View.GONE);
            Log.e("completed", "22");
        }

        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {
                /////////////////

                String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

                String urlToDownload = "";
                if (type.equals("Video"))
                    // urlToDownload += fileUrl + extension;
                    urlToDownload += fileUrl;

                Log.e("print url", urlToDownload + "?");
                String urlEncoded = Uri.encode(urlToDownload, ALLOWED_URI_CHARS);
                URL url = new URL(urlEncoded);
                InputStream input = null;
                ///////////////////////////

                if (type.equals("Video") || type.equals("AudioMotive")) {
                    URLConnection conection = url.openConnection();
                    conection.connect();
                    input = new BufferedInputStream(url.openStream());
                    lenghtOfFile = conection.getContentLength();
                    conection.setConnectTimeout(50000);
                    conection.setReadTimeout(50000);

                } else if (type.equals("Audio")) {
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                    url.openConnection();
                    httpsURLConnection.setSSLSocketFactory(new TLSSocketFactory());
                    input = httpsURLConnection.getInputStream();
                }
           /* URLConnection conection = url.openConnection();
            conection.connect();
            input = new BufferedInputStream(url.openStream());*/


                /////////////////////////////

                // input stream to read file - with 8k buffer
                // Output stream to write file
                // OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
                String filePath = getOutputMediaFile("https://docs.google.com/uc?export=download&id=1NmdZuEQmxyYgptuPJtacoFC3d8Yj8bQY");
                // Util.filePath=filePath;
                Log.e("m path", filePath + "?");

                OutputStream output = new FileOutputStream(filePath);

                byte data[] = new byte[5120];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    // Log.e("Print progresss---->",(int)((total*100)/lenghtOfFile)+"????");
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Error: ", e.getMessage());

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad" + File.separator + "https://docs.google.com/uc?export=download&id=1NmdZuEQmxyYgptuPJtacoFC3d8Yj8bQY" + ".mp4");
                file.delete();

             /*   imgDownload.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                txtProgress.setVisibility(View.GONE);*/
                return null;
            }

            return null;
        }
    }

    private String getOutputMediaFile(String fileUrl) {
        // File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), ".Squad");
        String directoryPath = Environment.getExternalStorageDirectory().getPath() + "/MyDir";////////
        File mediaStorageDir = new File(directoryPath, ".Squad");


        //String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"Squad";
        //File mediaStorageDir = new File(fullPath);


        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // String filePath=mediaStorageDir.getPath() + File.separator + "CT_" + timeStamp + ".mp4";
        //String filePath=mediaStorageDir.getPath() + File.separator + fileUrl + ".mp4";
        String filePath = mediaStorageDir.getPath() + File.separator + fileUrl + ".mp4";

        return filePath;
    }

    private void getUserPermission() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (hasWritePermission()) {
                    if (Util.checkConnection(getActivity())) {
                        videoLInk = lstData.getEventItemVideoDetails().get(0).getDownloadURL();
                        videoLInk = videoLInk.replaceAll(" ", "");
                        String fileName = videoLInk;
                        Log.e("print url download--", videoLInk + "???");
                        File file = new File(getActivity().getCacheDir(), "SQUAD/" + fileName);
                        if (!file.exists()) {
                            // imgDownload.setBackgroundResource(R.drawable.w_activedownload);
                            //Toast.makeText(getActivity(), "Check download progress in the notification window", Toast.LENGTH_LONG).show();
                            ///////////////////
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());

                            mBuilder = new NotificationCompat.Builder(getActivity());
                            mBuilder.setContentTitle(txtChallengeNameBelow.getText().toString())
                                    .setContentText("Download in progress")
                                    .setSmallIcon(R.drawable.ic_launcher);
                            mBuilder.setOngoing(true);
                            mBuilder.setSound(null);
                            Downloader downloader = null;
                            String NOTIFICATION_CHANNEL_ID = "10002";
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_MEDITATION", importance);
                                notificationChannel.enableLights(true);
                                notificationChannel.setLightColor(Color.RED);
                                notificationChannel.setSound(null, null);
                                //notificationChannel.enableVibration(true);
                                // notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                assert mNotifyManager != null;
                                mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                                mNotifyManager.createNotificationChannel(notificationChannel);
                                //////////

                            }

                            downloader = new Downloader(getActivity(), mNotifyManager, mBuilder, notificationId, videoLInk, fileName, null, null, txtDownload, lstData);
                            downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoLInk);
                            notificationId++;

                            downloader.setOnDownloadCompleteListener(new Downloader.OnDownloadCompleteListener() {
                                @Override
                                public void onDownloadComplete(int position) {
                                    if (position == 1)
                                        llAction.setVisibility(View.GONE);
                                    else
                                        llAction.setVisibility(View.VISIBLE);

                                }
                            });

                        } else {

                           /* Intent intent = new Intent(getActivity(), VideoPlayDownloadActivity.class);
                            intent.putExtra("VIDEOLINK", file.getAbsolutePath());
                            getActivity().startActivity(intent);*/
                            Toast.makeText(getActivity(), "Video already downloaded", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Check your net connection", Toast.LENGTH_LONG).show();
                    }

                }
                if (hasRecordPermission()) {
                    getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
                    /*try {
                        if (musicSrv != null && musicSrv.getMediaPlayer() != null) {
                            setupVisualizerFxAndUI(musicSrv.getMediaPlayer().getAudioSessionId());
                            mVisualizer.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/
                    try {
                        if (musicSrv != null && musicSrv.getMediaPlayer() != null) {
                            setUpDialogVisualizerFX(musicSrv.getMediaPlayer().getAudioSessionId());
                            dialogVisualizer.setEnabled(true);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {

                    if (!Settings.System.canWrite(getActivity())) {
                        requestingPermission = true;
                     //   requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.RECORD_AUDIO}, 289);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            requestPermissions(new String[]{ android.Manifest.permission.READ_MEDIA_AUDIO}, 289);
                        }else{
                            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,android.Manifest.permission.READ_MEDIA_AUDIO}, 289);
                        };
                    }
                }


            } else {
                // continue with your code
                //  funDownloadVideoFollowAlong(lstData.getEventItemVideoDetails().get(0).getDownloadURL(),fileName);
                if (Util.checkConnection(getActivity())) {
                    videoLInk = lstData.getEventItemVideoDetails().get(0).getDownloadURL();
                    videoLInk = videoLInk.replaceAll(" ", "");
                    String fileName = videoLInk;
                    Log.e("print url download--", videoLInk + "???");
                    File file = new File(getActivity().getCacheDir(), "SQUAD/" + fileName);
                    if (!file.exists()) {
                        // imgDownload.setBackgroundResource(R.drawable.w_activedownload);
                        Toast.makeText(getActivity(), "Check download progress in the notification window", Toast.LENGTH_LONG).show();
                        ///////////////////
                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());

                        mBuilder = new NotificationCompat.Builder(getActivity());
                        mBuilder.setContentTitle(txtChallengeNameBelow.getText().toString())
                                .setContentText("Download in progress")
                                .setSmallIcon(R.drawable.ic_launcher);
                        mBuilder.setOngoing(true);
                        mBuilder.setSound(null);

                        Downloader downloader = null;
                        String NOTIFICATION_CHANNEL_ID = "10002";
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            int importance = NotificationManager.IMPORTANCE_DEFAULT;
                            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_MEDITATION", importance);
                            notificationChannel.enableLights(true);
                            notificationChannel.setLightColor(Color.RED);
                            notificationChannel.setSound(null, null);
                            // notificationChannel.enableVibration(true);
                            // notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                            assert mNotifyManager != null;
                            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                            mNotifyManager.createNotificationChannel(notificationChannel);
                            //////////

                        }

                        downloader = new Downloader(getActivity(), mNotifyManager, mBuilder, notificationId, videoLInk, fileName, null, null, txtDownload, lstData);
                        downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoLInk);
                        downloader.setOnDownloadCompleteListener(new Downloader.OnDownloadCompleteListener() {
                            @Override
                            public void onDownloadComplete(int position) {
                                if (position == 1)
                                    llAction.setVisibility(View.GONE);
                                else
                                    llAction.setVisibility(View.VISIBLE);

                            }
                        });
                        notificationId++;

                    } else {

                       /* Intent intent = new Intent(getActivity(), VideoPlayDownloadActivity.class);
                        intent.putExtra("VIDEOLINK", file.getAbsolutePath());
                        getActivity().startActivity(intent);*/
                        Toast.makeText(getActivity(), "Video already downloaded", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Check your net connection", Toast.LENGTH_LONG).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        try {
            requestingPermission = false;
            switch (requestCode) {
                case 289: {
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        Log.e("Permission write", "Granted");

                        // getTodayDataFromApi(Util.globalDate);
                        // getDownloadFileName();
                        SharedPreference sharedPreference = new SharedPreference(getActivity());
                        sharedPreference.write("writePer", "", "true");
                        if (Util.checkConnection(getActivity())) {
                            videoLInk = lstData.getEventItemVideoDetails().get(0).getDownloadURL();
                            videoLInk = videoLInk.replaceAll(" ", "");
                            String fileName = videoLInk;
                            Log.e("print url download--", videoLInk + "???");
                            File file = new File(getActivity().getCacheDir(), "SQUAD/" + fileName);
                            if (!file.exists()) {
                                // imgDownload.setBackgroundResource(R.drawable.w_activedownload);
                                Toast.makeText(getActivity(), "Check download progress in the notification window", Toast.LENGTH_LONG).show();

                                ///////////////////
                                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());

                                mBuilder = new NotificationCompat.Builder(getActivity());
                                mBuilder.setContentTitle(txtChallengeNameBelow.getText().toString())
                                        .setContentText("Download in progress")
                                        .setSmallIcon(R.drawable.ic_launcher);
                                mBuilder.setOngoing(true);
                                mBuilder.setSound(null);
                                Downloader downloader = null;
                                String NOTIFICATION_CHANNEL_ID = "10002";
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME_MEDITATION", importance);
                                    notificationChannel.enableLights(true);
                                    notificationChannel.setLightColor(Color.RED);
                                    notificationChannel.setSound(null, null);
                                    //notificationChannel.enableVibration(true);
                                    // notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                                    assert mNotifyManager != null;
                                    mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
                                    mNotifyManager.createNotificationChannel(notificationChannel);
                                    //////////

                                }

                                downloader = new Downloader(getActivity(), mNotifyManager, mBuilder, notificationId, videoLInk, fileName, null, null, txtDownload, lstData);
                                downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoLInk);
                                downloader.setOnDownloadCompleteListener(new Downloader.OnDownloadCompleteListener() {
                                    @Override
                                    public void onDownloadComplete(int position) {
                                        if (position == 1)
                                            llAction.setVisibility(View.GONE);
                                        else
                                            llAction.setVisibility(View.VISIBLE);

                                    }
                                });
                                notificationId++;

                            } else {
                                Toast.makeText(getActivity(), "Video already downloaded", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Check your net connection", Toast.LENGTH_LONG).show();
                        }

                    }
                    if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                        // setupVisualizerFxAndUI();
                        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
                        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);
                        /*try {
                            if (musicSrv != null && musicSrv.getMediaPlayer() != null) {
                                setupVisualizerFxAndUI(musicSrv.getMediaPlayer().getAudioSessionId());
                                mVisualizer.setEnabled(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        try {
                            if (musicSrv != null && musicSrv.getMediaPlayer() != null) {
                                setUpDialogVisualizerFX(musicSrv.getMediaPlayer().getAudioSessionId());
                                dialogVisualizer.setEnabled(true);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Log.e("Permission write", "Denied");
                        Util.showToast(getActivity(), "Please enable Write  permission from settings.");
                    }
                    return;
                }
                case 201: {
//                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Log.e("Permission Gal", "Granted");
//                        if (!new SharedPreference(getActivity()).read("discover", "strdiscover").equals("false"))
//                            getActivity().startService(new Intent(getActivity(), LocationApiService.class));
//                    } else {
//                        // Log.e("Permission MAP", "Denied");
//                        new Util().showDialog(getActivity(), "Alert", "Please enable location service from settings", false);
//                    } //commented by jyotirmoy
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean hasWritePermission() {
//        int hasPermissionWrite = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        return hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED;


        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // For Android versions below API level 30
            return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            // For Android versions R (API level 30) and above
            return ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }

    }

    private boolean hasRecordPermission() {
//        int hasPermissionRecord = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO);
//
//
            // For Android versions R (API level 30) and above
            return  ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;


    }

    private void getGuidedMeditationList() {

        if (Connection.checkConnection(requireContext())) {

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", sharedPreference.read("UserID", ""));
            hashMap.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashMap.put("Key", Util.KEY);

            Call<MeditationCourseModel> readUnreadResponseCall = finisherService.getGuidedMeditationsBySearchTag(hashMap);
            progressDialog = ProgressDialog.show(getContext(), "", "Loading Options...", false);
            readUnreadResponseCall.enqueue(new Callback<MeditationCourseModel>() {
                @Override
                public void onResponse(Call<MeditationCourseModel> call, Response<MeditationCourseModel> response) {

                    if (progressDialog != null) {
                        progressDialog.hide();
                    }
                    if (response.body() != null) {

                        MeditationCourseModel guidedMeditationModel = response.body();

                        sharedPreference.write("GUIDED_MEDITATION_LIST", "", new Gson().toJson(response.body()));

                        ArrayList<GuidedMeditationModel> guidedMeditationList = new ArrayList<>();

                        for (int index = 0; index < guidedMeditationModel.getWebinars().size(); index++) {

                            GuidedMeditationModel guidedMeditation = new GuidedMeditationModel();

                            guidedMeditation.setWebinar(guidedMeditationModel.getWebinars().get(index));

                            guidedMeditation.setSelected(false);

                            guidedMeditationList.add(guidedMeditation);

                        }

                        showGuidedMeditationChooser(guidedMeditationList);

                    }

                }

                @Override
                public void onFailure(Call<MeditationCourseModel> call, Throwable t) {
                    if (progressDialog != null) {
                        progressDialog.hide();
                    }
                    t.printStackTrace();

                }
            });

        } else {
            Util.showToast(requireContext(), Util.networkMsg);
            progressDialog.hide();
        }

    }

    private void showGuidedMeditationChooser(ArrayList<GuidedMeditationModel> guidedMedList) {

        final Dialog dialog = new Dialog(requireContext(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_guided_meditation_chooser);

        RecyclerView recyclerGuidedMeditation = dialog.findViewById(R.id.recyclerGuidedMeditation);
        Button btnCancelModal = dialog.findViewById(R.id.btnCancelModal);

        recyclerGuidedMeditation.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        GuidedMeditationAdapter guidedMeditationAdapter = new GuidedMeditationAdapter();

        guidedMeditationAdapter.setData(guidedMedList);

        guidedMeditationAdapter.setListener(new GuidedMeditationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(GuidedMeditationModel data) {
                Log.i(TAG, "item clicked: name: " + data.getWebinar().getEventName() + " isSelected: " + data.isSelected());
                if (musicSrv != null) {
                    if (data.isSelected()) {
                        GuidedMeditationModel guidedMeditationModel = new GuidedMeditationModel();
                        guidedMeditationModel.setSelected(false);
                        guidedMeditationModel.setWebinar(data.getWebinar());
                        musicSrv.addOtherMeditation(guidedMeditationModel);
                    } else {
                        musicSrv.removeOtherMeditation(data);
                    }

                    otherGuidedMeditationAdapter.setData(musicSrv.getOtherNextMeditations());
                    if (musicSrv.getOtherNextMeditations().isEmpty()) {
                        imgSearchGuidedMeditation.setVisibility(View.VISIBLE);
                    } else {
                        imgSearchGuidedMeditation.setVisibility(View.GONE);
                    }
                }

                dialog.dismiss();


            }
        });

        recyclerGuidedMeditation.setAdapter(guidedMeditationAdapter);

        btnCancelModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*if (musicSrv != null) {
                    for (int index = 0; index < musicSrv.getOtherNextMeditations().size(); index++) {
                        Log.i(TAG, "added event name: " + musicSrv.getOtherNextMeditations().get(index).getWebinar().getEventName());
                        Log.i(TAG, "added event selected: " + musicSrv.getOtherNextMeditations().get(index).isSelected());
                    }
                    otherGuidedMeditationAdapter.setData(musicSrv.getOtherNextMeditations());
                    if(musicSrv.getOtherNextMeditations().isEmpty()){
                        imgSearchGuidedMeditation.setVisibility(View.VISIBLE);
                    }else {
                        imgSearchGuidedMeditation.setVisibility(View.GONE);
                    }
                }*/

                dialog.dismiss();
            }
        });
        dialog.show();

    }


    private void setDurationTime() {

        if (Connection.checkConnection(requireContext())) {

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());

            SharedPreference sharedPreference = new SharedPreference(getActivity());

            /*List<Integer> medIds = new ArrayList<>();

            String medIdString = sharedPreference.read("MEDITATION_IDS_FOR_KLAVIYO_DURATION", "");
            Log.i(TAG, "med ids: " + medIdString);

            if (!medIdString.isEmpty()) {
                medIds = new Gson().fromJson(medIdString, new TypeToken<List<Integer>>() {
                }.getType());
            }

            if (medIds != null && !medIds.contains(lstData.getEventItemID())) {*/

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", sharedPreference.read("UserID", ""));
            hashMap.put("EventItemId", lstData.getEventItemID());
            if (!lstData.getEventItemVideoDetails().isEmpty()) {
                hashMap.put("VideoUrl", lstData.getEventItemVideoDetails().get(0).getAppURL());
            }
            //hashMap.put("Time", "00");
            hashMap.put("Time", (musicSrv.currentMediaPosition() / 1000));
            hashMap.put("Key", Util.KEY);

            Call<JsonObject> readUnreadResponseCall = finisherService.setDurationForMeditation(hashMap);
            readUnreadResponseCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    t.printStackTrace();
                }
            });

            /*    medIds.add(lstData.getEventItemID());
                sharedPreference.write("MEDITATION_IDS_FOR_KLAVIYO_DURATION", "", new Gson().toJson(medIds));
                medIdString = sharedPreference.read("MEDITATION_IDS_FOR_KLAVIYO_DURATION", "");
                Log.i(TAG, "med ids: " + medIdString);

            }*/

        }

    }

    private void handleback(boolean fromBack) {

        SharedPreference sharedPreference = new SharedPreference(requireContext());

        List<Integer> medIds = new ArrayList<>();

        String medIdString = sharedPreference.read("MEDITATION_IDS_FOR_FAV_DIALOG_SHOWN", "");
        Log.i(TAG, "med ids: " + medIdString);

        if (!medIdString.isEmpty()) {
            medIds = new Gson().fromJson(medIdString, new TypeToken<List<Integer>>() {
            }.getType());
        }

        if (!sharedPreference.readBoolean("HAS_USER_A_FAV_MEDITATION", "") && medIds != null && !medIds.contains(lstData.getEventItemID())) {
            Log.i("clearMeditation_onpause","Uuuuuuuuu");

            medIds.add(lstData.getEventItemID());
            sharedPreference.write("MEDITATION_IDS_FOR_FAV_DIALOG_SHOWN", "", new Gson().toJson(medIds));
            medIdString = sharedPreference.read("MEDITATION_IDS_FOR_FAV_DIALOG_SHOWN", "");
            Log.i(TAG, "med ids: " + medIdString);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(requireContext());
            alertBuilder.setMessage("Did you like this meditation? Make it a favourite now so you can find it again quickly");
            alertBuilder.setPositiveButton("yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            try {
                                toggleEventLike(lstData.getEventItemID());
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }


                        }
                    });

            alertBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    try {
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
                        MeditationFragment meditationFragment = new MeditationFragment();
                        if (fromBack) {
                            getArguments().putString("BACKBUTTONCLICKED", "TRUE");
                        }
                        ((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", getArguments());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                }
            });

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();

        } else {
            Log.i("clearMeditation_onpause","Util.clearMeditation_onpause");

            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
            MeditationFragment meditationFragment = new MeditationFragment();
            if (fromBack) {
                getArguments().putString("BACKBUTTONCLICKED", "TRUE");
            }
            ((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", getArguments());

        }

    }

    private void toggleEventLike(Integer eventID) {

        SharedPreference sharedPreference = new SharedPreference(requireContext());
        FinisherServiceImpl finisherService = new FinisherServiceImpl(requireContext());

        if (Connection.checkConnection(requireContext())) {

            progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...", true);


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
                        sharedPreference.writeBoolean("HAS_USER_A_FAV_MEDITATION", "", true);

                        handleback(true);

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(requireContext(), Util.networkMsg);
            progressDialog.hide();
        }

    }

    private void playBackgroundMusic(Integer videoCurrentPosition) {
        Log.i(TAG, "playBackgroundMusic");
//musicSrv.seekMedia(0);
        doBindService();

    }

    ////////////////BROADCAST FOR GETTING VIDEO POSITION IN SERVICE////////////

    BroadcastReceiver broadcastGettingPos = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("CURRENT_POS_VIDEO")) {
                int video_pos = intent.getIntExtra("CURRENT_POS_VIDEO", 0);
                isBroadcastFromServicesCalled = true;
                try {
                    if (intent.getStringExtra("VIDEO_NAME").equals(txtChallengeNameBelow.getText().toString())) {
                        videoView.seekTo(video_pos);
                        videoView.start();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    ///////////////////////////////////////////////////////////////////////////
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void doBindService() {

        Log.i(TAG, "doBindService");
        Intent intent = new Intent(getActivity(), BackgroundSoundServiceNew.class);
        requireActivity().startService(intent);
        ((MainActivity) requireActivity()).setMusicConnection(musicConnection);
        requireActivity().bindService(intent,
                musicConnection, Context.BIND_AUTO_CREATE);
        //musicBound = true;

    }

    private void enableMediaControls() {

        imgPlayPause.setEnabled(true);
        SeekBarTestPlay.setEnabled(true);
        imgForward.setEnabled(true);
        imgBack.setEnabled(true);
        //dialogImgPlayPause.setEnabled(true);
        //dialogSeekBarTestPlay.setEnabled(true);
        //dialogImgForward.setEnabled(true);
        //dialogImgBack.setEnabled(true);
        Log.i(TAG, "controls enabled");
    }

    private void disableMediaControls() {

        imgPlayPause.setEnabled(false);
        SeekBarTestPlay.setEnabled(false);
        imgForward.setEnabled(false);
        imgBack.setEnabled(false);
        //dialogSeekBarTestPlay.setEnabled(false);
        //dialogImgPlayPause.setEnabled(false);
        //dialogImgForward.setEnabled(false);
        //dialogImgBack.setEnabled(false);
        Log.i(TAG, "controls disabled");

    }

    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            BackgroundSoundServiceNew.ServiceBinder binder = (BackgroundSoundServiceNew.ServiceBinder) service;
            //get service
            musicSrv = binder.getService();

            BackgroundSoundServiceNew.OnMediaStateListener stateListener = new BackgroundSoundServiceNew.OnMediaStateListener() {
                @Override
                public void onMediaStateChange(MediaPlayer mediaPlayer, BackgroundSoundServiceNew.MediaState newState) {

                    switch (newState) {

                        case MEDIA_NOT_PREPARED:

                            Log.i(TAG, "Media Not Prepared...");

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            try {
                                //  progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...", true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            break;

                        case MEDIA_PREPARED:

                            Log.i(TAG, "Media Prepared...");
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                            Calendar calendar = Calendar.getInstance();
                            String strDt = simpleDateFormat.format(calendar.getTime());
                            sharedPreference.write("start_exact_position", "", strDt);

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            try {
                                if (musicSrv != null && mediaPlayer != null) {
                                    setUpDialogVisualizerFX(mediaPlayer.getAudioSessionId());
                                    dialogVisualizer.setEnabled(true);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                         /*   if (!musicSrv.isPlayingNonCued()) {
                                Log.i(TAG, "global seek time: " + globalCurrentVideoPostion);
                                Log.i("seek","seek_7");
                                musicSrv.seekMedia(globalCurrentVideoPostion);
                            }*/

                            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);

                            enableMediaControls();

                            break;
                        case MEDIA_PLAYING:
                            break;
                        case MEDIA_PAUSED:
                            break;
                        case MEDIA_BUFFERING_START:
                            Log.i(TAG, "Buffering Start...");

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            /*try{
                                progressDialog = ProgressDialog.show(requireContext(), "", "Buffering Meditation. Please wait...", true);
                            }catch (Exception e){
                                e.printStackTrace();
                            }*/

                            break;

                        case MEDIA_BUFFERING_END:

                            Log.i(TAG, "Buffering END...");
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            break;

                        case MEDIA_SEEK_START:

                            Log.i(TAG, "Seek Start...");

                            if (mediaPlayer.isPlaying()) {

                                if (progressDialog != null) {
                                    progressDialog.dismiss();
                                }

                                /*try{
                                    progressDialog = ProgressDialog.show(requireContext(), "", "Buffering Meditation. Please wait...", true);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }*/
                            }

                            break;


                        case MEDIA_SEEK_COMPLETE:
                            Log.i(TAG, "Seek END...");
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            break;

                        case MEDIA_CAN_NOT_BE_PLAYED:

                            Log.i(TAG, "Media can not play media");
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            disableMediaControls();

                            Util.showDialog(getContext(), "Media error", "Media file not supported.", true);

                            break;
                    }

                }
            };

            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            @SuppressLint("MissingPermission") NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            isPlayingOnlineVideo = true;

            if (musicSrv.getVideoName().equals(lstData.getUniqueName()) && musicSrv.getFromPage().equals(BackgroundSoundServiceNew.FromPage.MEDITATION)) {

                if(!isConnected){
                    musicSrv.createMediaPlayer_(lstData.getUniqueName(), BackgroundSoundServiceNew.FromPage.MEDITATION, downloadedFileUri, globalCurrentVideoPostion, lstData, isPlayingOnlineVideo, lstData.getNoCueUrl(), stateListener);
                }else {
                    musicSrv.createMediaPlayer(lstData.getUniqueName(), BackgroundSoundServiceNew.FromPage.MEDITATION, globalVideoLinkForBackgroundPlay, globalCurrentVideoPostion, lstData, isPlayingOnlineVideo, lstData.getNoCueUrl(), stateListener);
                }

                if (musicSrv.isMediaPlaying()) {

                    imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
                    openVisualizerDialog(musicSrv.getMediaPlayer());


                } else {
                    imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                }

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);

                enableMediaControls();

            } else {
                musicSrv.stopMedia();

                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...", true);
                progressDialog.setCancelable(true);

                if(!isConnected){
                    musicSrv.createMediaPlayer_(lstData.getUniqueName(), BackgroundSoundServiceNew.FromPage.MEDITATION, downloadedFileUri, globalCurrentVideoPostion, lstData, isPlayingOnlineVideo, lstData.getNoCueUrl(), stateListener);
                }else {
                    musicSrv.createMediaPlayer(lstData.getUniqueName(), BackgroundSoundServiceNew.FromPage.MEDITATION, globalVideoLinkForBackgroundPlay, globalCurrentVideoPostion, lstData, isPlayingOnlineVideo, lstData.getNoCueUrl(), stateListener);
                }


                imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                //chkBoxNonCued.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
            }

            switch (musicSrv.getNextPlayableMeditationType()) {
                case NO_CUE:
                    chkBoxNonCued.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
                    break;
                case GUIDED1:
                    chkGuided1.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
                    break;

                case GUIDED2:
                    chkGuided2.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
                    break;
                case GUIDED3:
                    chkGuided3.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
                    break;
                case GUIDED4:
                    chkGuided4.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
                    break;
                case GUIDED5:
                    chkGuided5.setChecked(musicSrv.shouldPlayNonCuedAfterWards());
                    break;
                case OTHER:
                    break;
            }

            if (!musicSrv.getOtherNextMeditations().isEmpty()) {
                otherGuidedMeditationAdapter.setData(musicSrv.getOtherNextMeditations());
                imgSearchGuidedMeditation.setVisibility(View.GONE);
            } else {
                imgSearchGuidedMeditation.setVisibility(View.VISIBLE);
            }

            musicBound = true;
            if (!musicSrv.isPlayingNonCued()) {
                Log.i(TAG, "global seek time: " + globalCurrentVideoPostion);
                Log.i("seek","seek_7");


                String firstLoginTime = sharedPreference.read("start_exact_position", "");
                Log.i("CURRRTIIII", firstLoginTime);

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Calendar calendar = Calendar.getInstance();
                String  currentTime = simpleDateFormat.format(calendar.getTime());
//                String currentTime="2023/04/20";
                Log.i("CURRRTIIII_1",currentTime );

                try {
                    Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
                    Date dateCurrentTime = simpleDateFormat.parse(currentTime);
                    if (dateCurrentTime != null) {
                        if(dateCurrentTime.after(dateFirstLoginDt)){
                            musicSrv.seekMedia(0);
                        }else{
                            musicSrv.seekMedia(globalCurrentVideoPostion);
                        }
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }
            Log.i(TAG, "connection open");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "connection close");
            musicBound = false;
        }
    };


    private void setupVisualizerFxAndUI(int audioSessionId) {
        //http://android-er.blogspot.com/2015/02/create-audio-visualizer-for-mediaplayer.html
        // Create the Visualizer object and attach it to our media player.
        //Log.e("plot graph--", "123" + "??");
        mVisualizerView.setVisibility(View.VISIBLE);
        // videoView.setVisibility(View.GONE);
        Log.i(TAG, "audio session Id : " + audioSessionId);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mVisualizerView.releasePointerCapture();
            }
            mVisualizer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mVisualizer = new Visualizer(audioSessionId);
        mVisualizer.setEnabled(false);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        mVisualizerView.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);


    }


    private void openVisualizerDialog(MediaPlayer mediaPlayer) {

        Log.i(TAG, "open visualizer dialog ");

        visualizerDialog = null;

        visualizerDialog = new Dialog(requireContext(), R.style.DialogThemeAnother);

        visualizerDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.i(TAG, "open visualizer dialog dismissed");
                Log.i("DSSSSSSMMMMMMMMMMM", "open visualizer dialog dismissed");
                isVisualizerDialogOpen = false;
            }
        });

        visualizerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                Log.i(TAG, "open visualizer dialog shown");
                isVisualizerDialogOpen = true;
                try {
                    if (musicSrv != null && musicSrv.getMediaPlayer() != null) {
                        setUpDialogVisualizerFX(musicSrv.getMediaPlayer().getAudioSessionId());
                        dialogVisualizer.setEnabled(true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        visualizerDialog.setContentView(R.layout.layout_meditation_visualizer_dialog);

        dialogVisualizerView = null;

        dialogVisualizerView = visualizerDialog.findViewById(R.id.newVisualizerView);
        dialogImgPlayPause = visualizerDialog.findViewById(R.id.imgPlayPause);

        dialogImgForward = visualizerDialog.findViewById(R.id.imgForward);
        dialogImgBack = visualizerDialog.findViewById(R.id.imgBack);

        dialogTxtTime = visualizerDialog.findViewById(R.id.txtTime);
        dialogTxtTotalTime = visualizerDialog.findViewById(R.id.txtTotalTime);
        dialogSeekBarTestPlay = visualizerDialog.findViewById(R.id.SeekBarTestPlay);


        dialogSeekBarTestPlay.setOnSeekBarChangeListener(onSeekBarChangeListener);

        dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);

        dialogImgForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeditationDetailsNew.this.imgForward.performClick();
            }
        });

        dialogImgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MeditationDetailsNew.this.imgBack.performClick();
            }
        });

        ///////////////////////////////////////////////////////////////////////////////
        if(musicSrv.isMediaPlaying()){
            dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);

        }else {
            dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
        }
        ///////////////////////////////////////////////////////////////////////////////


        dialogImgPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
                MeditationDetailsNew.this.imgPlayPause.performClick();

                visualizerDialog.dismiss();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            dialogSeekBarTestPlay.getProgressDrawable().setColorFilter(
                    requireContext().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        } else {
            dialogSeekBarTestPlay.getProgressDrawable().setColorFilter(
                    getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }

        visualizerDialog.show();

        //dialogVisualizerView.link(mediaPlayer);

    }

   /* private void setUpDialogVisualizerFXNew(int audioSessionId) {

        dialogVisualizerView.setVisibility(View.VISIBLE);
        // videoView.setVisibility(View.GONE);
        Log.i(TAG, "audio session Id : " + audioSessionId);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dialogVisualizerView.releasePointerCapture();
            }
            mVisualizer.release();
        } catch (Exception e) {
            e.printStackTrace();
        }

        dialogVisualizer = new Visualizer(audioSessionId);
        dialogVisualizer.setEnabled(false);
        dialogVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
        dialogVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {
                        dialogVisualizerView.updateVisualizer(bytes);
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                    }
                }, Visualizer.getMaxCaptureRate() / 2, true, false);

        dialogVisualizer.setEnabled(true);


    }*/

/*
    private void setUpDialogVisualizerFX(int audioSessionId) {

        try {

            dialogVisualizerView.clearRenderers();
            Log.i(TAG, "dialogVisualizerView clear renderer");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dialogVisualizerView.releasePointerCapture();
            }

            dialogVisualizer.release();
            Log.i(TAG, "dialogVisualizerView release");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the Visualizer object and attach it to our media player.
        dialogVisualizer = new Visualizer(audioSessionId);
        dialogVisualizer.setEnabled(false);
        dialogVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        // Pass through Visualizer data to VisualizerView
        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
                dialogVisualizerView.updateVisualizer(bytes);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
                                         int samplingRate) {
                dialogVisualizerView.updateVisualizerFFT(bytes);
            }
        };

        dialogVisualizer.setDataCaptureListener(captureListener,
                Visualizer.getMaxCaptureRate() / 2, true, true);

        // Enabled Visualizer and disable when we're done with the stream

        addCircleRenderer(dialogVisualizerView);
        //addLineRenderer(dialogVisualizerView);

    }
*/

    private void setUpDialogVisualizerFX(int audioSessionId) {

        try {
            Log.i(TAG, "dialogVisualizerView clear renderer");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                dialogVisualizerView.releasePointerCapture();
            }

            dialogVisualizer.release();
            Log.i(TAG, "dialogVisualizerView release");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the Visualizer object and attach it to our media player.
        dialogVisualizer = new Visualizer(audioSessionId);
        dialogVisualizer.setEnabled(false);
        dialogVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        // Pass through Visualizer data to VisualizerView
        Visualizer.OnDataCaptureListener captureListener = new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
                try {
                    if (dialogVisualizerView != null) {
                        //dialogVisualizerView.updateVisualizer(bytes);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //dialogVisualizerView.setRawAudioBytes(bytes);
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] bytes,
                                         int samplingRate) {
                //dialogVisualizerView.updateVisualizerFFT(bytes);
                //dialogVisualizerView.setRawAudioBytes(bytes);
            }
        };

        dialogVisualizer.setDataCaptureListener(captureListener,
                Visualizer.getMaxCaptureRate() / 2, true, true);

        // Enabled Visualizer and disable when we're done with the stream

        //addCircleRenderer(dialogVisualizerView);
        //addLineRenderer(dialogVisualizerView);

        //dialogVisualizerView.setDrawLine(false);
        if (audioSessionId != -1)
            dialogVisualizerView.setAudioSessionId(audioSessionId);

    }


    private void addCircleBarRenderer(com.ashysystem.mbhq.view.visualizer.VisualizerView visualizerView) {
        Paint paint = new Paint();
        paint.setStrokeWidth(12f);
        paint.setAntiAlias(true);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN));
        //paint.setColor(Color.argb(255, 222, 92, 143));
        paint.setColor(Color.parseColor("#01D0B9"));
        CircleBarRenderer circleBarRenderer = new CircleBarRenderer(paint, 32, false);
        visualizerView.addRenderer(circleBarRenderer);
    }

    private void addCircleRenderer(com.ashysystem.mbhq.view.visualizer.VisualizerView visualizerView) {
        Paint paint = new Paint();
        paint.setStrokeWidth(3f);
        paint.setAntiAlias(true);
        //paint.setColor(Color.argb(255, 222, 92, 143));
        paint.setColor(Color.parseColor("#01D0B9"));
        CircleRenderer circleRenderer = new CircleRenderer(paint, false);
        visualizerView.addRenderer(circleRenderer);
    }

    private void addBarGraphRenderers(com.ashysystem.mbhq.view.visualizer.VisualizerView visualizerView) {
        Paint paint = new Paint();
        paint.setStrokeWidth(50f);
        paint.setAntiAlias(true);
        //paint.setColor(Color.argb(200, 56, 138, 252));
        paint.setColor(Color.parseColor("#01D0B9"));
        BarGraphRenderer barGraphRendererBottom = new BarGraphRenderer(16, paint, false);
        visualizerView.addRenderer(barGraphRendererBottom);

        Paint paint2 = new Paint();
        paint2.setStrokeWidth(12f);
        paint2.setAntiAlias(true);
        //paint2.setColor(Color.argb(200, 181, 111, 233));
        paint.setColor(Color.parseColor("#01D0B9"));
        BarGraphRenderer barGraphRendererTop = new BarGraphRenderer(4, paint2, false);
        visualizerView.addRenderer(barGraphRendererTop);
    }

    private void addLineRenderer(com.ashysystem.mbhq.view.visualizer.VisualizerView visualizerView) {
        Paint linePaint = new Paint();
        linePaint.setStrokeWidth(1f);
        linePaint.setAntiAlias(true);
        //linePaint.setColor(Color.argb(88, 0, 128, 255));
        linePaint.setColor(Color.parseColor("#01D0B9"));

        Paint lineFlashPaint = new Paint();
        lineFlashPaint.setStrokeWidth(5f);
        lineFlashPaint.setAntiAlias(true);
        //lineFlashPaint.setColor(Color.argb(188, 255, 255, 255));
        linePaint.setColor(Color.parseColor("#01D0B9"));
        LineRenderer lineRenderer = new LineRenderer(linePaint, lineFlashPaint, false);
        visualizerView.addRenderer(lineRenderer);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getExtras().getString("actionName");

            if (BackgroundSoundServiceNew.ACTION_PLAY.equals(action)) {
                onPlayPause();
            }
        }
    };
    /*BroadcastReceiver broadcastReceiverForScreen = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {

                //onPlayPause1();

            }
        }
    };*/
    private void onPlayPause() {
        //  Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
        Log.e("MMPPLL", "onPlayPause: " + musicSrv.isMediaPlaying());
        if(musicSrv.isMediaPlaying()){

            //imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);
            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
            dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);
        }else {
            //imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
            dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
            visualizerDialog.dismiss();
        }

    }

    /*private void onPlayPause1() {
        //  Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
        Log.e("MMPPLL", "onPlayPause: " + musicSrv.isMediaPlaying());
        if(musicSrv.isMediaPlaying()){

            //imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);
            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
            dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);
        }else {
            //imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
           *//* imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
            dialogImgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
            visualizerDialog.dismiss();*//*
        }

    }*/

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

   /* private final BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                SharedPreferences preferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                long downloadId = preferences.getLong("downloadId",0);
               // Toast.makeText(context,String.valueOf(downloadId) + "TESTINGG",Toast.LENGTH_LONG).show();
                Uri downloadedFileUri = Util.getDownloadedFileUri(getContext(), downloadId);
                Log.e("D_URI", "onReceive: " + downloadedFileUri);
                if (downloadedFileUri != null) {
                    // Use the downloaded file URI as needed
                   // musicSrv.createMediaPlayer();
                } else {
                    // The file may not be downloaded yet or encountered an error
                }

            }
        }
    };*/
}