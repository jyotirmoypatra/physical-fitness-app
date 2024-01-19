package com.ashysystem.mbhq.fragment.meditation;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.BackgroundSoundServiceNew;
import com.ashysystem.mbhq.Service.OnClearFromRecentService;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class LiveChatPlayerFragment_video extends Fragment implements View.OnClickListener, View.OnTouchListener {


    private static final String ARG_LIVE_CHAT_DATA = "live_chat_data";

    private String mParam1;


    public LiveChatPlayerFragment_video() {
        // Required empty public constructor
    }
    //ArrayList<MeditationCourseModel.Webinar> lstData = new ArrayList<>();
    public static LiveChatPlayerFragment_video newInstance(MeditationCourseModel.Webinar liveChatData) {

        LiveChatPlayerFragment_video fragment = new LiveChatPlayerFragment_video();
        Bundle args = new Bundle();
        args.putSerializable(ARG_LIVE_CHAT_DATA, liveChatData);
        fragment.setArguments(args);
        return fragment;
    }

    private TextView txtBack, txtLiveChatTitle, txtLiveChatPresenter, txtElapsedDuration, txtTotalDuration;
    private ImageView imgPlayPause, imgRewind, imgFastForward;
    private ViewGroup videoViewControlsContainer;
    private VideoView liveChatVideoView;
    private SeekBar seekBarForVideo;
    LayoutInflater layoutInflater;
    FinisherServiceImpl finisherService;

    ///////////// added //////////////////////////////
    FrameLayout frameVideo;
    RelativeLayout rlFullscreen;
    ImageView imgFullScreen;
    LinearLayout llSeek;
    private ViewGroup videoViewContainer;
    RelativeLayout rlHead;
    ViewGroup videoControlsOnVideoView;
    ImageView imgBackwardOnVideo,imgForwardOnVideo,imgPlayPauseOnVideo;
    TextView txtElapsedDurationOnVideo,txtTotalDurationOnVideo;
    SeekBar seekBarOnVideo;
    /////////////////////////////////////////////////
    private Uri downloadedFileUri = null;

    private MeditationCourseModel.Webinar liveChatData;
    private RelativeLayout rl_suggestedmedicines;

    private Handler videoControlHandler = new Handler();
    SharedPreference sharedPreference;

    private Handler mediaPlayerHandler = new Handler();

    private Runnable controlsVisibilityRunner = new Runnable() {
        @Override
        public void run() {
            videoViewControlsContainer.setVisibility(View.VISIBLE); //////////////////////////////////////////////
        }
    };

    private Runnable mediaUpdateTimeTask = new Runnable() {

        public void run() {

            long totalDuration = musicSrv.totalMediaDuration();
            long currentDuration = musicSrv.currentMediaPosition();

            txtElapsedDuration.setText("" + milliSecondsToTimer(currentDuration));
            txtTotalDuration.setText("" + milliSecondsToTimer(totalDuration));

            txtElapsedDurationOnVideo.setText("" + milliSecondsToTimer(currentDuration));
            txtTotalDurationOnVideo.setText("" + milliSecondsToTimer(totalDuration));

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            try {
                seekBarForVideo.setProgress(progress);

            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                seekBarOnVideo.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }


            mediaPlayerHandler.postDelayed(this, 1000);
        }
    };

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private Handler videoControlHandler1 = new Handler();
    private Runnable controlsVisibilityRunner1 = new Runnable() {
        @Override
        public void run() {
            videoControlsOnVideoView.setVisibility(View.GONE);
        }
    };
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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


    public int getProgressPercentage(long currentDuration, long totalDuration) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }


    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double) progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }

    private BackgroundSoundServiceNew musicSrv = null;
    private boolean musicBound = false;
    private Dialog progressDialog;

    private long VIDEO_CONTROLS_VISIBILITY_TIMEOUT = 3000L;


    private int FAST_FORWARD_TIME = 30000;
    private int REWIND_TIME = 30000;


    private FRAGMENT_STATE fragmentState = FRAGMENT_STATE.RESUMED;

    BackgroundSoundServiceNew.MediaType globalLinkMediaType = BackgroundSoundServiceNew.MediaType.VIDEO;

    private String TAG = this.getClass().getSimpleName();

    enum FRAGMENT_STATE {
        RESUMED,
        PAUSED
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        if (getArguments() != null) {
            liveChatData = (MeditationCourseModel.Webinar) getArguments().getSerializable(ARG_LIVE_CHAT_DATA);
        }
        downloadedFileUri = Util.getDownloadedFileUri(getContext(), liveChatData.getDownloadid());
        Log.e("D_URI", "onReceive: " + downloadedFileUri);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_live_chat_player, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);
        disableMediaControls();
        doBindService();
        liveChatVideoView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                Log.i(TAG, "surfaceChanged");
                if (musicSrv != null && musicSrv.isMediaPlaying()) {
                    musicSrv.getMediaPlayer().setDisplay(holder);
                    scaleVideo();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (musicSrv != null && musicSrv.isMediaPlaying()) {
                    musicSrv.getMediaPlayer().setDisplay(null);
                }
            }
        });

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        SeekBar.OnSeekBarChangeListener seekListener = new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    //Log.i(TAG, "seek no-->" + (progress * 1000) + "?" + progress);
                    if (progress == 100) {
                        PowerManager powerManager = (PowerManager) requireContext().getSystemService(Context.POWER_SERVICE);
                        boolean isScreenAwake = (Build.VERSION.SDK_INT < 20 ? powerManager.isScreenOn() : powerManager.isInteractive());
                        KeyguardManager myKM = (KeyguardManager) requireContext().getSystemService(Context.KEYGUARD_SERVICE);
                        boolean isPhoneLocked = myKM.inKeyguardRestrictedInputMode();
                        boolean isKeyGuardLocked = myKM.isKeyguardLocked();
                        Log.i(TAG, "isScreen On :" + isScreenAwake + "isPhoneLocked :" + isPhoneLocked + "isKeyGuardLocked :" + isKeyGuardLocked + "fragmentState :" + fragmentState);
                        if (isScreenAwake && !isPhoneLocked && !isKeyGuardLocked && fragmentState == FRAGMENT_STATE.RESUMED) {
//                            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
//                            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play);
                            imgPlayPause.setImageResource(R.drawable.mbhq_play_black);
                            imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_play_black);
                            musicSrv.seekMedia(0);
                            musicSrv.pauseMedia();
                        }
                    } else {
                        if (fromUser) {
                            int currentPosition = progressToTimer(progress, musicSrv.totalMediaDuration());
                            Log.i(TAG, "current pos" + currentPosition);
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

        seekBarForVideo.setOnSeekBarChangeListener(seekListener);
        seekBarOnVideo.setOnSeekBarChangeListener(seekListener);
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            seekBarOnVideo.getProgressDrawable().setColorFilter(
                    requireContext().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        } else {
            seekBarOnVideo.getProgressDrawable().setColorFilter(
                    getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        }


        txtLiveChatTitle.setText(liveChatData.getEventName());
        txtLiveChatPresenter.setText(liveChatData.getPresenterName());
        txtBack.setOnClickListener(this);
        // liveChatVideoView.setOnTouchListener(this);
        // videoViewControlsContainer.setOnTouchListener(this);
        //  imgPlayPause.setOnTouchListener(this);
        imgFastForward.setOnTouchListener(this);
        imgRewind.setOnTouchListener(this);
        rlFullscreen.setOnClickListener(this); // added

        imgBackwardOnVideo.setOnTouchListener(this);
        imgForwardOnVideo.setOnTouchListener(this);

        // videoControlsOnVideoView.setOnTouchListener(this); // added
        frameVideo.setOnTouchListener(this); // added


    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        outState.putSerializable("LIVE_CHAT_DATA", liveChatData);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter("MediaNotification"));
        getActivity().startService(new Intent(getContext(), OnClearFromRecentService.class));
//        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//        getActivity().registerReceiver(broadcastReceiverForScreen,filter);
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
        if (musicSrv != null) {
            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        //((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
       /* LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);*/
        try {
            musicSrv.getMediaPlayer().setDisplay(null);
            musicSrv.getMediaPlayer().setSurface(null);
            //videoView.getHolder().getSurface().release();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onStop() {
        super.onStop();

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

    @Override
    public void onDestroy() {
        super.onDestroy();

        requireActivity().unregisterReceiver(broadcastReceiver);
        // getActivity().unregisterReceiver(broadcastReceiverForScreen);


    }
    private void handleback(boolean fromBack) {
        Util.openliveplayer1="";
        Util.chat1=null;
        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MeditationFragment());
        MeditationFragment meditationFragment = new MeditationFragment();
        if (fromBack) {
            getArguments().putString("BACKBUTTONCLICKED", "TRUE");
        }
        ((MainActivity) getActivity()).loadFragment(meditationFragment, "MeditationFragment", getArguments());
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == txtBack.getId()) {
            handleback(true);
        }
        if (v.getId() == rlFullscreen.getId()) {

            if (imgFullScreen.getTag() != null && imgFullScreen.getTag().equals("FULL_SCREEN")) {
                Log.e("FLSCREEEN","IF");

                portraitMode();

            } else {
                Log.e("FLSCREEEN","ELSE");

                portraitFullscreenMode();
            }
        }

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (v.getId() == liveChatVideoView.getId()) {


        } else if (v.getId() == videoViewControlsContainer.getId()) {


        } else if (v.getId() == imgPlayPause.getId()) {


        } else if (v.getId() == imgFastForward.getId()) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    return true;

                case MotionEvent.ACTION_UP:

                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    try {
                        int currentPosition = musicSrv.currentMediaPosition();
                        Log.i(TAG, "current pos: $currentPosition ");
                        // check if seekForward time is lesser than song duration
                        if (currentPosition + FAST_FORWARD_TIME <= musicSrv.totalMediaDuration()) {
                            // forward song
                            musicSrv.seekMedia(currentPosition + FAST_FORWARD_TIME);
                            Log.i(TAG, "seek pos: ${currentPosition + FAST_FORWARD_TIME}");
                        } else {
                            // forward to end position
                            int seekPos = musicSrv.totalMediaDuration();
                            musicSrv.seekMedia(musicSrv.totalMediaDuration());
                            Log.i(TAG, "seek pos: $seekPos");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "forward button error");
                    }
                    videoControlHandler.postDelayed(
                            controlsVisibilityRunner, 3000
                    );
                    mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
                    return true;

                case MotionEvent.ACTION_BUTTON_PRESS:
                    liveChatVideoView.performClick();
                    return true;
            }
        } else if (v.getId() == imgRewind.getId()) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    Log.i(TAG, "imgRewind down");
                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    return true;
                case MotionEvent.ACTION_UP:

                    Log.i(TAG, "imgRewind up");
                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    try {
                        int currentPosition = musicSrv.currentMediaPosition();
                        // check if seekBackward time is greater than 0 sec
                        if (currentPosition - REWIND_TIME >= 0) {
                            // forward song
                            musicSrv.seekMedia(currentPosition - REWIND_TIME);
                        } else {
                            // backward to starting position
                            musicSrv.seekMedia(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    videoControlHandler.postDelayed(
                            controlsVisibilityRunner, 3000
                    );
                    mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
                    return true;

                case MotionEvent.ACTION_BUTTON_PRESS:
                    liveChatVideoView.performClick();
                    return true;
            }
        }
        else if(v.getId() == frameVideo.getId()){
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    if (videoControlsOnVideoView.getVisibility() == View.VISIBLE) {

                        videoControlsOnVideoView.setVisibility(View.GONE);
                        videoControlHandler1.removeCallbacks(controlsVisibilityRunner1);
                    } else {

                        videoControlsOnVideoView.setVisibility(View.VISIBLE);
                        videoControlHandler1.postDelayed(
                                controlsVisibilityRunner1, VIDEO_CONTROLS_VISIBILITY_TIMEOUT
                        );
                    }

                    return true;
                //break;

                case MotionEvent.ACTION_UP:
                    videoControlHandler1.removeCallbacks(controlsVisibilityRunner1);
                    videoControlHandler1.postDelayed(
                            controlsVisibilityRunner1, VIDEO_CONTROLS_VISIBILITY_TIMEOUT
                    );
                    return true;

                case MotionEvent.ACTION_BUTTON_PRESS:
                    liveChatVideoView.performClick();
                    return true;
            }

        }
        else if(v.getId() == imgBackwardOnVideo.getId()){
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    Log.i(TAG, "imgRewind down");
                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    return true;
                case MotionEvent.ACTION_UP:

                    Log.i(TAG, "imgRewind up");
                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    try {
                        int currentPosition = musicSrv.currentMediaPosition();
                        // check if seekBackward time is greater than 0 sec
                        if (currentPosition - REWIND_TIME >= 0) {
                            // forward song
                            musicSrv.seekMedia(currentPosition - REWIND_TIME);
                        } else {
                            // backward to starting position
                            musicSrv.seekMedia(0);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    videoControlHandler.postDelayed(
                            controlsVisibilityRunner, 3000
                    );
                    mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
                    return true;

                case MotionEvent.ACTION_BUTTON_PRESS:
                    liveChatVideoView.performClick();
                    return true;
            }

        }

        else if(v.getId() == imgForwardOnVideo.getId()){
            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    return true;

                case MotionEvent.ACTION_UP:

                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    try {
                        int currentPosition = musicSrv.currentMediaPosition();
                        Log.i(TAG, "current pos: $currentPosition ");
                        // check if seekForward time is lesser than song duration
                        if (currentPosition + FAST_FORWARD_TIME <= musicSrv.totalMediaDuration()) {
                            // forward song
                            musicSrv.seekMedia(currentPosition + FAST_FORWARD_TIME);
                            Log.i(TAG, "seek pos: ${currentPosition + FAST_FORWARD_TIME}");
                        } else {
                            // forward to end position
                            int seekPos = musicSrv.totalMediaDuration();
                            musicSrv.seekMedia(musicSrv.totalMediaDuration());
                            Log.i(TAG, "seek pos: $seekPos");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.i(TAG, "forward button error");
                    }
                    videoControlHandler.postDelayed(
                            controlsVisibilityRunner, 3000
                    );
                    mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
                    return true;

                case MotionEvent.ACTION_BUTTON_PRESS:
                    liveChatVideoView.performClick();
                    return true;
            }

        }
        else if(v.getId() == imgPlayPauseOnVideo.getId()){

        }


        return false;
    }

    private void doBindService() {

        Intent intent = new Intent(getActivity(), BackgroundSoundServiceNew.class);
        requireActivity().startService(intent);
        ((MainActivity) requireActivity()).setMusicConnection(musicConnection);
        requireActivity().bindService(intent,
                musicConnection, Context.BIND_AUTO_CREATE);
        musicBound = true;
    }

    private void enableMediaControls() {
        seekBarForVideo.setEnabled(true);
        imgPlayPause.setEnabled(true);
        imgFastForward.setEnabled(true);
        imgRewind.setEnabled(true);

        imgPlayPause.setEnabled(true);
        imgPlayPauseOnVideo.setEnabled(true);
        seekBarOnVideo.setEnabled(true);

        imgForwardOnVideo.setEnabled(true);
        imgBackwardOnVideo.setEnabled(true);
        Log.i(TAG, "controls enabled");
    }

    private void disableMediaControls() {
        seekBarForVideo.setEnabled(false);
        imgPlayPause.setEnabled(false);
        imgFastForward.setEnabled(false);
        imgRewind.setEnabled(false);

        imgPlayPause.setEnabled(false);
        imgPlayPauseOnVideo.setEnabled(false);
        seekBarOnVideo.setEnabled(false);

        imgForwardOnVideo.setEnabled(false);
        imgBackwardOnVideo.setEnabled(false);
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
                                progressDialog = ProgressDialog.show(getContext(), "", "Buffering Video. Please wait...", true);
                                progressDialog.setCancelable(false);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case MEDIA_PREPARED:

                            Log.i(TAG, "Media Prepared...");

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            try {

                                if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                                    //musicSrv.getMediaPlayer().setSurface(videoView.getHolder().getSurface());
                                    musicSrv.getMediaPlayer().setDisplay(liveChatVideoView.getHolder());
                                    scaleVideo();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Log.i(TAG, "video height: " + musicSrv.getMediaPlayer().getVideoHeight() + " width: " + musicSrv.getMediaPlayer().getVideoWidth());
                            downloadFile();
                            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);
                            musicSrv.startMedia();
                            //  imgPlayPause.setImageDrawable(LiveChatPlayerFragment.this.requireContext().getDrawable(R.drawable.mbhq_pause));
//                            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause); //
                            imgPlayPause.setImageResource(R.drawable.mbhq_pause_black);
                            // mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);
                            enableMediaControls();

                            break;

                        case MEDIA_PLAYING:
                            try {

                                if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                                    //musicSrv.getMediaPlayer().setSurface(videoView.getHolder().getSurface());
                                    musicSrv.getMediaPlayer().setDisplay(liveChatVideoView.getHolder());
                                    scaleVideo();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            scaleVideo();
                            break;

                        case MEDIA_PAUSED:
                            break;

                        case MEDIA_BUFFERING_START:
                            Log.i(TAG, "Buffering Start...");

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            try {
                                progressDialog = ProgressDialog.show(getContext(), "", "Buffering Video. Please wait...", true);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
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
                                try {
                                    progressDialog = ProgressDialog.show(getContext(), "", "Buffering Video. Please wait...", true);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                            break;


                        case MEDIA_SEEK_COMPLETE:

                            Log.i(TAG, "Seek END...");
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            break;

                        case MEDIA_SIZE_CHANGED:
                            Log.i(TAG, "video size changed... video height: " + musicSrv.getMediaPlayer().getVideoHeight() + " width: " + musicSrv.getMediaPlayer().getVideoWidth());
                            scaleVideo();
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



            musicSrv.stopMedia();
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
            progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...", true);
            progressDialog.setCancelable(false);

           /* if(!isConnected){
                musicSrv.createMediaPlayer1_(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.MEDITATION_VIDEO, downloadedFileUri, globalLinkMediaType, 0, liveChatData, stateListener);
            }else {
                musicSrv.createMediaPlayer1(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.MEDITATION_VIDEO, liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(), globalLinkMediaType, 0, liveChatData, stateListener);

            }*/


            if(!isConnected){
                musicSrv.createMediaPlayer_meditationvideo_(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.MEDITATION_VIDEO, downloadedFileUri, globalLinkMediaType, 0, liveChatData, stateListener);
            }else {
                musicSrv.createMediaPlayer_meditationvideo(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.MEDITATION_VIDEO, liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(), globalLinkMediaType, 0, liveChatData, stateListener);

            }

            if (musicSrv.isMediaPlaying()) {

                imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
                imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause_black);
            } else {
                imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play_black);
            }

            musicBound = true;

           /* if (musicSrv.getVideoName().equals(liveChatData.getEventName()) && musicSrv.getFromPage().equals(BackgroundSoundServiceNew.FromPage.LIVE_CHAT)) {



                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                @SuppressLint("MissingPermission") NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();



               // musicSrv.stopMedia();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...", true);
                progressDialog.setCancelable(false);

                if(!isConnected){
                    musicSrv.createMediaPlayer_meditationvideo_(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.MEDITATION_VIDEO, downloadedFileUri, globalLinkMediaType, 0, liveChatData, stateListener);
                }else {
                    musicSrv.createMediaPlayer_meditationvideo(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.MEDITATION_VIDEO, liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(), globalLinkMediaType, 0, liveChatData, stateListener);

                }





              //  musicSrv.createMediaPlayer_meditationvideo(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.LIVE_CHAT, liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(), globalLinkMediaType, 0, liveChatData, stateListener);





                if (musicSrv.isMediaPlaying()) {

                    imgPlayPause.setImageResource(R.drawable.mbhq_pause_black);
                    imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_pause_black);
                } else {

                    imgPlayPause.setImageResource(R.drawable.mbhq_play_black);
                    imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_play_black);
                }

                try {
                    if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                        musicSrv.getMediaPlayer().setDisplay(liveChatVideoView.getHolder());
                        scaleVideo();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


                if (progressDialog != null) {
                    progressDialog.dismiss();
                }


                mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);
                enableMediaControls();



            } else {
                musicSrv.stopMedia();
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                progressDialog = ProgressDialog.show(requireContext(), "", "Please wait...", true);
                progressDialog.setCancelable(false);

                musicSrv.createMediaPlayer_meditationvideo(liveChatData.getEventName(), BackgroundSoundServiceNew.FromPage.LIVE_CHAT, liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(), globalLinkMediaType, 0, liveChatData, stateListener);
                imgPlayPause.setImageResource(R.drawable.mbhq_play_black);
                imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_pause_black);
            }*/

            //musicBound = true;

            Log.i(TAG, "connection open");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "connection close");
            musicBound = false;
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        }
    };
    private void openAttachmentDialog1() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_attachment_course);
        LinearLayout llDynamicAttachment = dialog.findViewById(R.id.llDynamicAttachment);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ///////////////
        if (Util.meditationsArrayList != null && Util.meditationsArrayList.size() > 0) {
            //llAttachment.setVisibility(View.VISIBLE);
            //txtAttachment.setText("Attachments");
            llDynamicAttachment.removeAllViews();
            for (int i = 0; i < Util.meditationsArrayList.size(); i++) {
                View dynamicView = layoutInflater.inflate(R.layout.dynamic_course_attachement1, null);
                TextView txtTipsInstructions = dynamicView.findViewById(R.id.txtTipsInstructions);
                LinearLayout llTotal = dynamicView.findViewById(R.id.llTotal);
                //txtTipsInstructions.setTextColor(Color.parseColor("#98B6F7"));
                txtTipsInstructions.setText(Util.meditationsArrayList.get(i).getEventName());
                //txtTipsInstructions.setText("Click Here");
                final int finalI = i;

                dynamicView.setId(i); // Set a unique ID for each view

                dynamicView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle click event here
                        int clickedViewId = v.getId(); // Get the ID of the clicked view

                        Log.i("clicked position",String.valueOf(clickedViewId));
                        Log.i("clicked position",String.valueOf(Util.meditationsArrayList.get(clickedViewId).getEventItemId()));
                        // Do something based on which view was clicked
                    }
                });

                llDynamicAttachment.addView(dynamicView);
            }
        } else {
        }


        ////////////
        dialog.show();
    }

    private void initView(View vi) {
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        finisherService = new FinisherServiceImpl(getActivity());
        rl_suggestedmedicines= vi.findViewById(R.id.rl_suggestedmedicines);
        rl_suggestedmedicines.setVisibility(View.GONE);
        sharedPreference = new SharedPreference(getActivity());
        txtBack = vi.findViewById(R.id.txtBack);
        txtElapsedDuration = vi.findViewById(R.id.txtElapsedDuration);
        txtTotalDuration = vi.findViewById(R.id.txtTotalDuration);
        txtLiveChatTitle = vi.findViewById(R.id.txtLiveChatTitle);
        txtLiveChatPresenter = vi.findViewById(R.id.txtLiveChatPresenter);

        imgPlayPause = vi.findViewById(R.id.imgPlayPause);
        imgFastForward = vi.findViewById(R.id.imgFastForward);
        imgRewind = vi.findViewById(R.id.imgRewind);

        videoViewControlsContainer = vi.findViewById(R.id.videoViewControlsContainer);
        liveChatVideoView = vi.findViewById(R.id.liveChatVideoView);
        seekBarForVideo = vi.findViewById(R.id.seekBarForVideo);


        /////////////// added /////////////////////////////////////////////////////////////////////////////////////////////////
        frameVideo = vi.findViewById(R.id.frameVideo);
        rlFullscreen = vi.findViewById(R.id.rlFullscreen);
        imgFullScreen = vi.findViewById(R.id.imgFullScreen);
        llSeek = vi.findViewById(R.id.llSeek);
        videoViewContainer = vi.findViewById(R.id.videoViewContainer);
        rlHead = vi.findViewById(R.id.rlHead);
        videoControlsOnVideoView = vi.findViewById(R.id.videoControlsOnVideoView);
        imgBackwardOnVideo = vi.findViewById(R.id.imgBackwardOnVideo);
        imgForwardOnVideo = vi.findViewById(R.id.imgForwardOnVideo);
        imgPlayPauseOnVideo = vi.findViewById(R.id.imgPlayPauseOnVideo);
        txtElapsedDurationOnVideo = vi.findViewById(R.id.txtElapsedDurationOnVideo);
        txtTotalDurationOnVideo = vi.findViewById(R.id.txtTotalDurationOnVideo);
        seekBarOnVideo = vi.findViewById(R.id.seekBarOnVideo);

        frameVideo.setOnTouchListener(this);
        imgBackwardOnVideo.setOnTouchListener(this);
        imgForwardOnVideo.setOnTouchListener(this);
        frameVideo.setEnabled(false);
        rlHead.setVisibility(View.VISIBLE);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        imgPlayPauseOnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPlayPause.performClick();
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
                                Util.openliveplayer1="";
                                Util.chat1=null;
                                Log.i(TAG, "media player stopped");

                                musicSrv.pauseMedia();
//                                imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
//                                imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play);
                                imgPlayPause.setImageResource(R.drawable.mbhq_play_black);
                                imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_play_black);
                            } else {
                                Log.i(TAG, "media player started");
//                                musicSrv.startMedia();
//                                imgPlayPause.setImageResource(R.drawable.mbhq_pause_black);
//                                imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_pause_black);
//                                requireActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

                                Log.e("DWNLOAD_DATA1", "onClick: " + liveChatData.getEventItemVideoDetails().get(0).getDownloadURL());
                                Log.i(TAG, "media player started");

                                String[] segments = liveChatData.getEventItemVideoDetails().get(0).getDownloadURL().split("/");
                                String lastSegment = segments[segments.length - 1];

                                long dwnldId =  Util.downloadFile(requireActivity().getBaseContext(),liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(),lastSegment,""); /////////
                                List<MeditationCourseModel.Webinar> lstTotalDataM = new ArrayList<>();

                                List<MeditationCourseModel.Webinar> lstTotalDataM_ = new ArrayList<>();

                                SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
                                String jsonData = preferences.getString("my_downloaded_medicine", "");

                                if (jsonData.isEmpty()) {
                                    liveChatData.setDownloadid(dwnldId);
                                    lstTotalDataM.add(liveChatData);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putString("my_downloaded_medicine", new Gson().toJson(lstTotalDataM));
                                    editor.apply();

                                }else{
                                    lstTotalDataM_ = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());

                                    if(0==liveChatData.getDownloadid()){
                                        liveChatData.setDownloadid(dwnldId);
                                        lstTotalDataM.add(liveChatData);
                                        List<MeditationCourseModel.Webinar> concatinatelist = new ArrayList<>();
                                        concatinatelist.addAll(lstTotalDataM_);
                                        concatinatelist.addAll(lstTotalDataM);
                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("my_downloaded_medicine", new Gson().toJson(concatinatelist));
                                        editor.apply();
                                    }

                                }

                                musicSrv.startMedia();
                                imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
                                imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause_black);
                                requireActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

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

    }


    public void downloadFile(){
        Log.e("DWNLOAD_DATA1", "onClick: " + liveChatData.getEventItemVideoDetails().get(0).getDownloadURL());
        Log.i(TAG, "media player started");

        String[] segments = liveChatData.getEventItemVideoDetails().get(0).getDownloadURL().split("/");
        String lastSegment = segments[segments.length - 1];

        long dwnldId =  Util.downloadFile(requireActivity().getBaseContext(),liveChatData.getEventItemVideoDetails().get(0).getDownloadURL(),lastSegment,""); /////////
        List<MeditationCourseModel.Webinar> lstTotalDataM = new ArrayList<>();

        List<MeditationCourseModel.Webinar> lstTotalDataM_ = new ArrayList<>();

        SharedPreferences preferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        String jsonData = preferences.getString("my_downloaded_medicine", "");

        if (jsonData.isEmpty()) {
            liveChatData.setDownloadid(dwnldId);
            lstTotalDataM.add(liveChatData);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("my_downloaded_medicine", new Gson().toJson(lstTotalDataM));
            editor.apply();

        }else{
            lstTotalDataM_ = new Gson().fromJson(jsonData, new TypeToken<List<MeditationCourseModel.Webinar>>() {}.getType());

            if(0==liveChatData.getDownloadid()){
                liveChatData.setDownloadid(dwnldId);
                lstTotalDataM.add(liveChatData);
                List<MeditationCourseModel.Webinar> concatinatelist = new ArrayList<>();
                concatinatelist.addAll(lstTotalDataM_);
                concatinatelist.addAll(lstTotalDataM);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("my_downloaded_medicine", new Gson().toJson(concatinatelist));
                editor.apply();
            }

        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionName");

            if (BackgroundSoundServiceNew.ACTION_PLAY.equals(action)) {
                onPlayPause();
            }
        }
    };
    BroadcastReceiver broadcastReceiverForScreen = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {

                //  onPlayPause1();

            }
        }
    };
    private void onPlayPause() {
        //  Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
        Log.e("MMPPLL", "onPlayPause: " + musicSrv.isMediaPlaying());
        if (musicSrv.isMediaPlaying()) {
            //imgPlayPause.setImageDrawable(LiveChatPlayerFragment.this.requireContext().getDrawable(R.drawable.mbhq_pause));
//            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause);
//            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause);
            imgPlayPause.setImageResource(R.drawable.mbhq_pause_black);
            imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_pause_black);
        } else {
            // imgPlayPause.setImageDrawable(LiveChatPlayerFragment.this.requireContext().getDrawable(R.drawable.mbhq_play));
            //imgPlayPauseOnVideo.setImageDrawable(LiveChatPlayerFragment.this.requireContext().getDrawable(R.drawable.mbhq_play));
//            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
//            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play);
            imgPlayPause.setImageResource(R.drawable.mbhq_play_black);
            imgPlayPauseOnVideo.setImageResource(R.drawable.mbhq_play_black);
        }


    }
    private void onPlayPause1() {
        //  Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
        Log.e("MMPPLL", "onPlayPause: " + musicSrv.isMediaPlaying());
        if (musicSrv.isMediaPlaying()) {
            //imgPlayPause.setImageDrawable(LiveChatPlayerFragment.this.requireContext().getDrawable(R.drawable.mbhq_pause));
            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause_black);
        } else {
            // imgPlayPause.setImageDrawable(LiveChatPlayerFragment.this.requireContext().getDrawable(R.drawable.mbhq_play));
            /*   imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
             */
        }


    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////// newly added methods //////////////////////////////////////////////////////////////////
    private void portraitMode() {

        llSeek.setVisibility(View.VISIBLE);
        videoViewControlsContainer.setVisibility(View.VISIBLE);
        rlHead.setVisibility(View.VISIBLE);
        //sv_main.setPadding(10,10,10,10);
        imgFullScreen.setImageResource(R.drawable.ic_switch_to_full_screen);
        imgFullScreen.setTag("NON_FULL_SCREEN");

        frameVideo.setEnabled(false);
        frameVideo.setBackgroundColor(getResources().getColor(R.color.subtle_gray));

        ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.VISIBLE);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ViewGroup.LayoutParams fvlp = frameVideo.getLayoutParams();

        fvlp.height = RelativeLayout.LayoutParams.WRAP_CONTENT;
        ViewGroup.LayoutParams lp = videoViewContainer.getLayoutParams();
        lp.height = (int) (200 * ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        frameVideo.requestLayout();
        videoViewContainer.requestLayout();
        scaleVideo();
    }

    private void scaleVideo() {

        boolean isFullScreen = false;

        if (imgFullScreen.getTag() != null && imgFullScreen.getTag().equals("FULL_SCREEN")) {
            isFullScreen = true;
        } else {
            isFullScreen = false;
        }

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);


        int screenHeight = displayMetrics.heightPixels;
        int screenWidth = displayMetrics.widthPixels;

        int videoHeight = 0;
        int videoWidth = 0;

        try {
            videoHeight = musicSrv.getMediaPlayer().getVideoHeight();
            videoWidth = musicSrv.getMediaPlayer().getVideoWidth();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        double videoRes = (float) videoWidth / (float) videoHeight;

        ViewGroup.LayoutParams lp = videoViewContainer.getLayoutParams();

        int newHeight = 0;
        int newWidth = 0;
        int maxAllowedHeight = 0;
        int maxAllowedWidth = 0;


        if (isFullScreen) {
            //video is in full screen mode.
            //portrait videos will be scaled upon screen height.
            //landscape videos will be scaled upon full width and ratio-wise height with a max of screen height.
            maxAllowedHeight = screenHeight;
            maxAllowedWidth = screenWidth;

        } else {
            //video is in non full screen mode.
            //portrait videos will be scaled upon screen height's half.
            //landscape videos will be scaled upon full width and ratio-wise height with a max of screen height's half
            maxAllowedHeight = screenHeight / 2;
            maxAllowedWidth = screenWidth;

        }

        if (videoRes <= 1) {
            //video is in portrait mode
            newHeight = maxAllowedHeight;
            newWidth = (int) (newHeight * videoRes);

            if (newWidth > maxAllowedWidth) {

                newWidth = maxAllowedWidth;

                newHeight = (int) (newWidth / videoRes);

            }

            if (newHeight > maxAllowedHeight || newWidth > maxAllowedWidth) {

                for (int i = 0; ; i++) {

                    newHeight = (int) (newHeight - (newHeight * 0.1));

                    newWidth = (int) (newWidth - (newWidth * 0.1));

                    if (newHeight <= maxAllowedHeight && newWidth <= maxAllowedWidth) {
                        break;
                    }
                }

            }


        } else {

            newWidth = maxAllowedWidth;
            newHeight = (int) (newWidth / videoRes);

            if (newHeight > maxAllowedHeight) {

                newHeight = maxAllowedHeight;

                newWidth = (int) (newHeight * videoRes);

            }

            if (newHeight > maxAllowedHeight || newWidth > maxAllowedWidth) {

                for (int i = 0; ; i++) {

                    newHeight = (int) (newHeight - (newHeight * 0.1));

                    newWidth = (int) (newWidth - (newWidth * 0.1));

                    if (newHeight <= maxAllowedHeight && newWidth <= maxAllowedWidth) {
                        break;
                    }
                }

            }

        }

        if (newHeight > 100 && newWidth > 100) {

            lp.height = newHeight;

            lp.width = newWidth;

            Log.i(TAG, "new Height width set");

        }


        Log.i(TAG, "screenHeight: " + screenHeight + " screenWidth: " + screenWidth);
        Log.i(TAG, "videoHeight: " + videoHeight + " videoWidth: " + videoWidth);
        Log.i(TAG, "new height of videoview: " + newHeight);
        Log.i(TAG, "new width of videoview: " + newWidth);

        videoViewContainer.requestLayout();

    }


    private void portraitFullscreenMode() {

        llSeek.setVisibility(View.GONE);
        rlHead.setVisibility(View.GONE);
        videoViewControlsContainer.setVisibility(View.GONE);


        frameVideo.setEnabled(true);

        frameVideo.setBackgroundColor(getResources().getColor(R.color.black));

        //sv_main.setPadding(0,0,0,0);

        imgFullScreen.setImageResource(R.drawable.ic_exit_full_screen);
        imgFullScreen.setTag("FULL_SCREEN");

        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);

        ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.GONE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams fvlp = frameVideo.getLayoutParams();

        fvlp.height = displayMetrics.heightPixels;

        ViewGroup.LayoutParams lp = videoViewContainer.getLayoutParams();

        ((FrameLayout.LayoutParams) lp).setMargins(0, 0, 0, 0);

        lp.height = displayMetrics.heightPixels;

        frameVideo.requestLayout();

        videoViewContainer.requestLayout();

        scaleVideo();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}