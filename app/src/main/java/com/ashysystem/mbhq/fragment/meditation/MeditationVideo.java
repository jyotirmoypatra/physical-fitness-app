package com.ashysystem.mbhq.fragment.meditation;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.AsyncTask;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.BaseExpandableListAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.AudioService;
import com.ashysystem.mbhq.Service.BackgroundSoundServiceNew;
import com.ashysystem.mbhq.Service.OnClearFromRecentService;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.activity.WebViewActivity;
import com.ashysystem.mbhq.fragment.course.CourseDetailsFragment;
import com.ashysystem.mbhq.fragment.course.CourseFragment;
import com.ashysystem.mbhq.model.GetArticleDetail;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.ReadUnreadResponse;
import com.ashysystem.mbhq.model.response.suggestedmedicin.Suggestedmedicin;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.Downloader;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.util.VisualizerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android-krishnendu on 2/23/17.
 */

public class MeditationVideo extends Fragment implements AudioService.TrackChangeLIstener, View.OnTouchListener {
    Handler seekHandler = new Handler();
    LinearLayout llSeek, llAction;
    FrameLayout frameVideo;
    RelativeLayout rlAttachment, llTickLastBottom, rlControl, rlFlashScreen, rlTitle, rlHeader, rlBottom;
    TextView txtBack;
    TextView txtHeader, txtAttachment, txtTask, txtTickStatus, txtAuthor, txtTickStatusBottom;
    WebView txtTodoHeaderAns, txtTodoHeader, txtTaskAns;
    ImageView imgTick, imgTickLast;
    VideoView videoView;
    LinearLayout llAttachment, llDynamicAttachment, llTask, llDynamicTask;
    FinisherServiceImpl finisherService;
    SharedPreference sharedPreference;
    LayoutInflater layoutInflater;
    Bundle courseDetailBundle;
    Integer articleId, courseId, taskId;
    String author, imageUrl,type;
    Boolean isThisProgramRunningInBackground = false;
    ImageView imgVideoState;
    String videoFilePath = "";
    NotificationManager mNotifyManager;
    WebView webInstruction;
    RelativeLayout rlFullscreen, llTickLast;
    ViewGroup videoControlsOnVideoView;
    boolean playOnBackButtonPress = false;
    //private boolean musicBound;
    long globalSeekProgress = 0;
    TextView txtChallengeNameBelow;
    RelativeLayout rlDownload;
    String globalVideoLinkForBackgroundPlay = "";
    BackgroundSoundServiceNew.MediaType globalLinkMediaType = BackgroundSoundServiceNew.MediaType.UNKNOWN;
    Integer globalCurrentVideoPostion = 0;
    boolean isBroadcastFromServicesCalled = false;
    ////////////
    VisualizerView mVisualizerView;
    BroadcastReceiver broadcastGettingPos = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.hasExtra("CURRENT_POS_VIDEO")) {
                int video_pos = intent.getIntExtra("CURRENT_POS_VIDEO", 0);
                globalCurrentVideoPostion = video_pos;
                isBroadcastFromServicesCalled = true;
                try {
                    videoView.seekTo(globalCurrentVideoPostion);
                    videoView.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private WebView webview;
    private RelativeLayout rl_suggestedmedicines;
    private TextView txtTime, txtTotalTime, txtElapsedDurationOnVideo, txtTotalDurationOnVideo;
    private int seekForwardTime = 30000;
    private int seekBackwardTime = 30000;
    private Handler mHandler = new Handler();
    private Handler mediaPlayerHandler = new Handler();
    private ViewGroup programWithImageContainer;
    private Dialog progressDialog;
    private boolean isRead = false, STAT = false;

    /* public AudioService getMusicSrv() {
         return musicSrv;
     }

     public void setMusicSrv(AudioService musicSrv) {
         this.musicSrv = musicSrv;
     }*/
    private int notificationId = 1000;
    private ImageView imgFullScreen;
    private ImageView imgPlayPause, imgBack, imgForward, imgPlayPauseOnVideo, imgBackwardOnVideo, imgForwardOnVideo;
    private SeekBar SeekBarTestPlay, seekBarOnVideo;
    // private AudioService musicSrv = null;
    private String origin = "";
    private boolean fromNotification;
    private String globalVideoURl = "";
    private String globalArticleName = "";
    private Integer globalArticleId = null;
    private MediaPlayer globalMediaPlayer = null;
    private Response<GetArticleDetail> globalResponse;
    private Response<Suggestedmedicin> globalResponse1;

    private String globalVideoId = "";
    private String seekTimeApi = "";
    private Double seekTimeApiLong = 0.0;
    private BackgroundSoundServiceNew musicSrv = null;
    private AudioManager audioManager;
    private BroadcastReceiver audioFocusReceiver;
    private boolean musicBound = false;
    private Visualizer mVisualizer;
    private ImageView imgProgram;
    private ViewGroup videoViewContainer;
    private View vwTodo, vwTaskAns;
    private ScrollView sv_main;
    private String TAG = "CourseArticleDetailsNewFragment";
    GetArticleDetail lastArticleDetails_new = null;
    private AudioManager.OnAudioFocusChangeListener afChangeListener;
    private Handler videoControlHandler = new Handler();
    private Runnable controlsVisibilityRunner = new Runnable() {
        @Override
        public void run() {
            videoControlsOnVideoView.setVisibility(View.GONE);
        }
    };
    private FRAGMENT_STATE fragmentState = FRAGMENT_STATE.RESUMED;
    private long VIDEO_CONTROLS_VISIBILITY_TIMEOUT = 3000L;
    private Runnable mediaUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = musicSrv.totalMediaDuration();
            long currentDuration = musicSrv.currentMediaPosition();

            txtTime.setText("" + milliSecondsToTimer(currentDuration));
            txtTotalTime.setText("" + milliSecondsToTimer(totalDuration));

            txtElapsedDurationOnVideo.setText("" + milliSecondsToTimer(currentDuration));
            txtTotalDurationOnVideo.setText("" + milliSecondsToTimer(totalDuration));

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
                seekBarOnVideo.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mediaPlayerHandler.postDelayed(this, 1000);
        }
    };

    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = videoView.getDuration();
            long currentDuration = videoView.getCurrentPosition();

            // Displaying Total Duration time
            //songTotalDurationLabel.setText(""+milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            txtTime.setText("" + milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            //Log.e("Progress", ""+progress);
            globalSeekProgress = currentDuration;
            try {
                SeekBarTestPlay.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                seekBarOnVideo.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };
    private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            BackgroundSoundServiceNew.ServiceBinder binder = (BackgroundSoundServiceNew.ServiceBinder) service;
            //get service
            musicSrv = binder.getService();

            BackgroundSoundServiceNew.OnMediaStateListener stateListener = new BackgroundSoundServiceNew.OnMediaStateListener() {
                @Override
                public void onMediaStateChange(MediaPlayer mediaPlayer, BackgroundSoundServiceNew.MediaState newState) {

                    progressDialog.setContentView(R.layout.buffering_progress_bar);
                    progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

                    switch (newState) {

                        case MEDIA_NOT_PREPARED:
                            Log.i(TAG, "Media Not Prepared...");
                            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }
                            try {
                                //  progressDialog = ProgressDialog.show(getContext(), "", "Buffering Program. Please wait...", true);
                                progressDialog.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            break;

                        case MEDIA_ERROR:
                            Log.i(TAG, "Media Error...");
                            //mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                            break;

                        case MEDIA_PREPARED:

                            Log.i(TAG, "Media Prepared...");

                            if (progressDialog != null) {
                                progressDialog.dismiss();
                            }

                            try {

                                if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                                    //musicSrv.getMediaPlayer().setSurface(videoView.getHolder().getSurface());
                                    musicSrv.getMediaPlayer().setDisplay(videoView.getHolder());
                                    scaleVideo();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            Log.i(TAG, "video height: " + musicSrv.getMediaPlayer().getVideoHeight() + " width: " + musicSrv.getMediaPlayer().getVideoWidth());


                            musicSrv.seekMedia(seekTimeApiLong.intValue());

                            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);

                            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);

                            enableMediaControls();

                            break;

                        case MEDIA_SIZE_CHANGED:
                            Log.i(TAG, "video size changed... video height: " + musicSrv.getMediaPlayer().getVideoHeight() + " width: " + musicSrv.getMediaPlayer().getVideoWidth());
                            scaleVideo();
                            break;

                        case MEDIA_PLAYING:
                            Log.i(TAG, "Media playing...");
                            try {

                                if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                                    //musicSrv.getMediaPlayer().setSurface(videoView.getHolder().getSurface());
                                    musicSrv.getMediaPlayer().setDisplay(videoView.getHolder());
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
                                // progressDialog = ProgressDialog.show(getContext(), "", "Buffering Program. Please wait...", true);
                                progressDialog.show();
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
                                    // progressDialog = ProgressDialog.show(getContext(), "", "Buffering Program. Please wait...", true);
                                    progressDialog.show();
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

            if (musicSrv.getVideoName().equals(txtChallengeNameBelow.getText().toString()) && musicSrv.getFromPage().equals(BackgroundSoundServiceNew.FromPage.PROGRAM)) {

                musicSrv.createMediaPlayer(txtChallengeNameBelow.getText().toString(), BackgroundSoundServiceNew.FromPage.PROGRAM, globalVideoLinkForBackgroundPlay, globalLinkMediaType, seekTimeApiLong.intValue(), courseDetailBundle, stateListener);

                if (musicSrv.isMediaPlaying()) {
                    imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
                    imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause_black);
                } else {
                    imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                    imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play_black);
                }

                if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                    musicSrv.getMediaPlayer().setDisplay(videoView.getHolder());
                    scaleVideo();
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
                progressDialog.setCancelable(true);

                musicSrv.createMediaPlayer(txtChallengeNameBelow.getText().toString(), BackgroundSoundServiceNew.FromPage.PROGRAM, globalVideoLinkForBackgroundPlay, globalLinkMediaType, seekTimeApiLong.intValue(), courseDetailBundle, stateListener);
                imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play_black);

            }

            musicBound = true;

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




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate Start");



       /* AudioManager audioManager = (AudioManager) Objects.requireNonNull(getActivity()).getSystemService(Context.AUDIO_SERVICE);
        afChangeListener = new AudioManager.OnAudioFocusChangeListener() {
            public void onAudioFocusChange(int focusChange) {
                if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                    // Pause playback
                    Log.i("autofocus","AUDIOFOCUS_LOSS_TRANSIENT");
                    musicSrv.pauseMedia();
                } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                    // Resume playback
                    //  musicSrv.startMedia();
                    Log.i("autofocus","AUDIOFOCUS_GAIN");
                } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                    // Stop playback
                    Log.i("autofocus","AUDIOFOCUS_LOSS");
                    musicSrv.pauseMedia();
                }else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                    // Stop playback
                    Log.i("autofocus","AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK");
                    musicSrv.pauseMedia();
                }
            }
        };

        int result = audioManager.requestAudioFocus(afChangeListener,
                // Use the music stream.
                AudioManager.STREAM_MUSIC,
                // Request permanent focus.
                AudioManager.AUDIOFOCUS_GAIN);*/

       /* if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Start playback
           // musicSrv.startMedia();
        }*/


        if (getArguments() != null) {
            courseDetailBundle = getArguments();
            Log.i(TAG, courseDetailBundle.toString());
            if (getArguments().containsKey("COURSEID")) {
                courseId = getArguments().getInt("COURSEID");//
            }
            if (getArguments().containsKey("ARTICLEID")) {
                articleId = getArguments().getInt("ARTICLEID");//
                ////Log.e("rcvd art id", articleId + "?");
            }
            if (getArguments().containsKey("TASKID")) {
                taskId = getArguments().getInt("TASKID");
            }
            if (getArguments().containsKey("STAT")) {
                STAT = getArguments().getBoolean("STAT");
            }
            if (getArguments().containsKey("origin")) {
                origin = getArguments().getString("origin");
            }

            if (getArguments().containsKey("notification")) {
                fromNotification = true;
            }
            if (getArguments().containsKey("AUTHOR")) {
                author = getArguments().getString("AUTHOR");//
            }
            if (getArguments().containsKey("IMAGE_URL")) {
                imageUrl = getArguments().getString("IMAGE_URL");//
            }
            if (getArguments().containsKey("type")){
                type = getArguments().getString("type");//
            }
        }

        try {
            Bundle cachedBundleForBackgroundRunningProgram = Util.bundleProgramDetailsForBackground;
            int cachedCourseId = cachedBundleForBackgroundRunningProgram.getInt("COURSEID");
            int cachedArticleId = cachedBundleForBackgroundRunningProgram.getInt("ARTICLEID");
            if (cachedArticleId == articleId && cachedCourseId == courseId) {
                isThisProgramRunningInBackground = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        progressDialog = new ProgressDialog(getContext()); //////////////////////////
        View vi = inflater.inflate(R.layout.fragment_coursearticledetails, container,false);
        // initView(vi);



        return vi;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.i(TAG, "onViewCreated");

        initView(view);

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("data", courseDetailBundle);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Log.i(TAG, "onActivityCreated");

        if (savedInstanceState != null) {
            Bundle arguments = savedInstanceState.getBundle("data");
            if (arguments != null) {
                courseDetailBundle = arguments;
                if (courseDetailBundle.containsKey("COURSEID")) {
                    courseId = courseDetailBundle.getInt("COURSEID");
                }
                if (courseDetailBundle.containsKey("ARTICLEID")) {
                    articleId = courseDetailBundle.getInt("ARTICLEID");
                    ////Log.e("rcvd art id", articleId + "?");
                }
                if (courseDetailBundle.containsKey("TASKID")) {
                    taskId = courseDetailBundle.getInt("TASKID");
                }
                if (courseDetailBundle.containsKey("STAT")) {
                    STAT = courseDetailBundle.getBoolean("STAT");
                }
                if (courseDetailBundle.containsKey("origin")) {
                    origin = courseDetailBundle.getString("origin");
                }

                if (courseDetailBundle.containsKey("notification")) {
                    fromNotification = true;
                }
                if (courseDetailBundle.containsKey("AUTHOR")) {
                    author = courseDetailBundle.getString("AUTHOR");
                }
            }

        }

        disableMediaControls();

        if (Util.boolBackGroundServiceRunningProgram) {

            int lastArticleId = -1;
            GetArticleDetail lastArticleDetails = null;

            try {

                lastArticleId = Integer.parseInt(sharedPreference.read("LAST_ARTICLE_ID", ""));

                lastArticleDetails = new Gson().fromJson(sharedPreference.read("LAST_ARTICLE_DETAILS", ""), GetArticleDetail.class);

                Log.i(TAG, "lastArticle id :" + lastArticleId + " last article details:" + new Gson().toJson(lastArticleDetails));

            } catch (Exception e) {
                e.printStackTrace();

            } finally {

                if (lastArticleId == articleId && lastArticleDetails != null) {

                    processArticleResponse(lastArticleDetails);

                } else {

                    progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
                    getArticleDetailsFromApi();

                }

            }
        } else {
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            getArticleDetailsFromApi();
        }

    }

    @Override
    public void onResume() {
        super.onResume();





        requireActivity().registerReceiver(broadcastReceiver, new IntentFilter("MediaNotification"));
        getActivity().startService(new Intent(getContext(), OnClearFromRecentService.class));
//        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
//        getActivity().registerReceiver(broadcastReceiverForScreen,filter);
/*

        IntentFilter filter1 = new IntentFilter();
        filter1.addAction("android.media.AUDIO_BECOMING_NOISY");
        getActivity().registerReceiver(VideoPlaybackReceiver,filter1);
*/
/*TEMP*/
       /* IntentFilter intentFilter = new IntentFilter(VideoPlaybackService.ACTION_VIDEO_PLAYING);
        getActivity().registerReceiver(VideoPlaybackReceiver, intentFilter);
*/

        Log.i(TAG, "onResume");

        fragmentState = FRAGMENT_STATE.RESUMED;

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);

        if (musicSrv != null) {
            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 100);
        }

    }

    /*@Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.i(TAG, "onConfigurationChanged-> newConfig: " + newConfig.orientation);

        if (newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            portraitMode();
            *//*try {

                musicSrv.startMedia();

                if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                    musicSrv.getMediaPlayer().setSurface(videoView.getHolder().getSurface());
                }

                Log.i(TAG, "total duration: " + musicSrv.totalMediaDuration());

            } catch (Exception e) {
                e.printStackTrace();
            }*//*
        }else if(newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
            landscapeMode();
        }

    }*/
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

        rlHeader.setVisibility(View.GONE);
        rlTitle.setVisibility(View.GONE);
        llSeek.setVisibility(View.GONE);
        rlControl.setVisibility(View.GONE);
        llAction.setVisibility(View.GONE);
        txtTodoHeader.setVisibility(View.GONE);
        txtTodoHeaderAns.setVisibility(View.GONE);
        txtTaskAns.setVisibility(View.GONE);
        rlBottom.setVisibility(View.GONE);

        vwTodo.setVisibility(View.GONE);
        vwTaskAns.setVisibility(View.GONE);

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

    private void portraitMode() {

        rlTitle.setVisibility(View.VISIBLE);
        rlHeader.setVisibility(View.VISIBLE);
        llSeek.setVisibility(View.VISIBLE);
        rlControl.setVisibility(View.VISIBLE);
        llAction.setVisibility(View.VISIBLE);
        txtTodoHeader.setVisibility(View.VISIBLE);
        txtTodoHeaderAns.setVisibility(View.VISIBLE);
        txtTaskAns.setVisibility(View.VISIBLE);
        rlBottom.setVisibility(View.VISIBLE);

        vwTodo.setVisibility(View.VISIBLE);
        vwTaskAns.setVisibility(View.VISIBLE);

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

        //((FrameLayout.LayoutParams) lp).setMargins(10, 10, 10, 10);

        frameVideo.requestLayout();

        videoViewContainer.requestLayout();

        scaleVideo();


    }

    private void landscapeMode() {

        rlHeader.setVisibility(View.GONE);
        rlTitle.setVisibility(View.GONE);
        llSeek.setVisibility(View.GONE);
        rlControl.setVisibility(View.GONE);
        llAction.setVisibility(View.GONE);
        txtTodoHeader.setVisibility(View.GONE);
        txtTodoHeaderAns.setVisibility(View.GONE);
        txtTaskAns.setVisibility(View.GONE);
        rlBottom.setVisibility(View.GONE);

        vwTodo.setVisibility(View.GONE);
        vwTaskAns.setVisibility(View.GONE);

        frameVideo.setEnabled(true);

        //sv_main.setPadding(0,0,0,0);

        imgFullScreen.setImageResource(R.drawable.ic_exit_full_screen);
        imgFullScreen.setTag("FULL_SCREEN");

        ((MainActivity) getActivity()).toolbar.setVisibility(View.GONE);

        ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.GONE);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        ViewGroup.LayoutParams lp = videoViewContainer.getLayoutParams();

        ((FrameLayout.LayoutParams) lp).setMargins(0, 0, 0, 0);

        lp.height = displayMetrics.widthPixels;//(int)(1920 / ((float) getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT));

        videoViewContainer.requestLayout();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause_test");
        // Abandon audio focus when the fragment is paused or stopped
        fragmentState = FRAGMENT_STATE.PAUSED;

        ((MainActivity) getActivity()).rlCourses.setEnabled(true);

        if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {

            portraitMode();

        }

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
        Log.i(TAG, "onStop");
       /* AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.abandonAudioFocus(afChangeListener);*/
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
        //getActivity().unregisterReceiver(broadcastReceiverForScreen);

        // getActivity().unregisterReceiver(VideoPlaybackReceiver);
        // getActivity().unregisterReceiver(audioFocusReceiver);

        ////////////////////////////////////////////////////////////
       /* if (musicSrv.isMediaPlaying()) {
            musicSrv.pauseMedia();
        }
        musicSrv.stopForeground(true);*/
        ////////////////////////////////////////////////////////////
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {


        if (v.getId() == frameVideo.getId()) {

            switch (event.getAction()) {

                case MotionEvent.ACTION_DOWN:

                    if (videoControlsOnVideoView.getVisibility() == View.VISIBLE) {

                        videoControlsOnVideoView.setVisibility(View.GONE);
                        videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    } else {

                        videoControlsOnVideoView.setVisibility(View.VISIBLE);
                        videoControlHandler.postDelayed(
                                controlsVisibilityRunner, VIDEO_CONTROLS_VISIBILITY_TIMEOUT
                        );
                    }

                    return true;
                //break;

                case MotionEvent.ACTION_UP:
                    videoControlHandler.removeCallbacks(controlsVisibilityRunner);
                    videoControlHandler.postDelayed(
                            controlsVisibilityRunner, VIDEO_CONTROLS_VISIBILITY_TIMEOUT
                    );
                    return true;

                case MotionEvent.ACTION_BUTTON_PRESS:
                    videoView.performClick();
                    return true;
            }
        }


        return false;
    }

    private void initView(View view) {

        mVisualizerView = (VisualizerView) view.findViewById(R.id.myvisualizerview);
        rl_suggestedmedicines= view.findViewById(R.id.rl_suggestedmedicines);
        webview = view.findViewById(R.id.webview);
        txtTaskAns = view.findViewById(R.id.txtTaskAns);
        llSeek = view.findViewById(R.id.llSeek);
        rlControl = view.findViewById(R.id.rlControl);
        frameVideo = view.findViewById(R.id.frameVideo);
        txtTime = view.findViewById(R.id.txtTime);
        txtTotalTime = view.findViewById(R.id.txtTotalTime);
        txtElapsedDurationOnVideo = view.findViewById(R.id.txtElapsedDurationOnVideo);
        txtTotalDurationOnVideo = view.findViewById(R.id.txtTotalDurationOnVideo);
        SeekBarTestPlay = view.findViewById(R.id.SeekBarTestPlay);
        seekBarOnVideo = view.findViewById(R.id.seekBarOnVideo);
        videoControlsOnVideoView = view.findViewById(R.id.videoControlsOnVideoView);
        imgPlayPause = view.findViewById(R.id.imgPlayPause);
        imgPlayPauseOnVideo = view.findViewById(R.id.imgPlayPauseOnVideo);
        imgForward = view.findViewById(R.id.imgForward);
        imgForwardOnVideo = view.findViewById(R.id.imgForwardOnVideo);
        imgBack = view.findViewById(R.id.imgBack);
        imgBackwardOnVideo = view.findViewById(R.id.imgBackwardOnVideo);
        txtBack = (TextView) view.findViewById(R.id.txtBack);
        llTickLastBottom = view.findViewById(R.id.llTickLastBottom);
        txtTickStatusBottom = view.findViewById(R.id.txtTickStatusBottom);
        rlAttachment = view.findViewById(R.id.rlAttachment);
        txtTodoHeader = view.findViewById(R.id.txtTodoHeader);
        txtTodoHeaderAns = view.findViewById(R.id.txtTodoHeaderAns);
        txtAuthor = view.findViewById(R.id.txtAuthor);
        imgFullScreen = view.findViewById(R.id.imgFullScreen);
        rlFullscreen = (RelativeLayout) view.findViewById(R.id.rlFullscreen);
        txtHeader = view.findViewById(R.id.txtHeader);
        txtTickStatus = view.findViewById(R.id.txtTickStatus);
        imgTick = view.findViewById(R.id.imgTick);
        imgVideoState = view.findViewById(R.id.imgVideoState);
        videoView = view.findViewById(R.id.videoView);
        llDynamicAttachment = view.findViewById(R.id.llDynamicAttachment);
        llTickLast = view.findViewById(R.id.llTickLast);

        txtChallengeNameBelow = view.findViewById(R.id.txtChallengeNameBelow);
        rlDownload = view.findViewById(R.id.rlDownload);

        imgProgram = view.findViewById(R.id.imgProgram);

        programWithImageContainer = view.findViewById(R.id.programWithImageContainer);

        rlFlashScreen = view.findViewById(R.id.rlFlashScreen);
        rlTitle = view.findViewById(R.id.rlTitle);

        rlHeader = view.findViewById(R.id.rlHeader);

        rlBottom = view.findViewById(R.id.rlBottom);

        llAction = view.findViewById(R.id.llAction);

        videoViewContainer = view.findViewById(R.id.videoViewContainer);

        vwTaskAns = view.findViewById(R.id.vwTaskAns);
        vwTodo = view.findViewById(R.id.vwTodo);
        sv_main = view.findViewById(R.id.sv_main);

        rlFlashScreen.setVisibility(View.VISIBLE);

        finisherService = new FinisherServiceImpl(getActivity());
        sharedPreference = new SharedPreference(getActivity());
        layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mNotifyManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);

        if (Util.globalExoplayer != null) {
            Util.globalExoplayer.release();
        }

        if (isRead) {
            Log.e("ISREAD", "TRUE???????");
        } else {
            Log.e("ISREAD", "FALSE???????");
        }

        Log.i(TAG, "image url : " + imageUrl);

        Glide.with(getContext()).load(imageUrl)
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.empty_image_old)
                .error(R.drawable.empty_image_old)
                .dontTransform()
                .dontAnimate()
                .into(imgProgram);

        funToolBar();

        frameVideo.setOnTouchListener(this);

        frameVideo.setEnabled(false);

        videoView.getHolder().addCallback(new SurfaceHolder.Callback() {
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

        //////////////////Click listener///////////
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
                            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
                            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play_black);
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

        SeekBarTestPlay.setOnSeekBarChangeListener(seekListener);

        seekBarOnVideo.setOnSeekBarChangeListener(seekListener);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            seekBarOnVideo.getProgressDrawable().setColorFilter(
                    requireContext().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        } else {
            seekBarOnVideo.getProgressDrawable().setColorFilter(
                    getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        }

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //clearCacheFfragment();

                    playOnBackButtonPress = false;
                    /*if(webview.getVisibility()==View.GONE)
                        setDurationTime();*/
                    //setDurationTime();
                    ////////////////////////////////////////////////////////////////////
                   /* musicSrv.pauseMedia();
                    NotificationManager nm = ((NotificationManager) Objects.requireNonNull(getActivity())
                            .getSystemService(Context.NOTIFICATION_SERVICE));
                    nm.cancelAll();*/
                    /*if (musicSrv.isMediaPlaying()) {
                        musicSrv.pauseMedia();
                    }
                    musicSrv.stopForeground(true);*/
                    ///////////////////////////////////////////////////////////////////

                    handleback();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        rlDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // if (globalArticleId != null && globalArticleName != null && globalVideoURl != null)
                {
                    String fileName = globalArticleName.replaceAll(" ", "") + "_" + globalArticleId + ".mp4";
                    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getActivity());
                    mBuilder.setContentTitle(globalArticleName)
                            .setContentText("Download in progress")
                            .setSmallIcon(R.drawable.ic_launcher);
                    mBuilder.setOngoing(true);
                    Downloader downloader = new Downloader(getActivity(), mNotifyManager, mBuilder, notificationId, videoFilePath, fileName, null, null, null, null);
                    downloader.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, videoFilePath);
                    notificationId++;
                }
            }
        });

        rlFullscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (imgFullScreen.getTag() != null && imgFullScreen.getTag().equals("FULL_SCREEN")) {
                    Log.i("calledcourse:","1");
                    portraitMode();

                    rlTitle.requestFocus();

                } else {
                    Log.i("calledcourse:","2");
                    portraitFullscreenMode();

                }

            }
        });

        llTickLastBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llTickLast.performClick();
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
                                imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play_black);
                            } else {
                                Log.i(TAG, "media player started");
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
        imgPlayPauseOnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgPlayPause.performClick();
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
                        musicSrv.seekMedia(currentPosition + seekForwardTime);
                    } else {
                        // forward to end position
                        musicSrv.seekMedia(musicSrv.totalMediaDuration());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        imgForwardOnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgForward.performClick();
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
                        musicSrv.seekMedia(currentPosition - seekBackwardTime);
                    } else {
                        // backward to starting position
                        musicSrv.seekMedia(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        imgBackwardOnVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgBack.performClick();
            }
        });
        /////////////////////click listener//////////

/*TEMP*/
/*
        imgVideoState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               */
/* SongInfoModel songInfoModel = new SongInfoModel("", "", "", "", videoFilePath, true);
                ArrayList<SongInfoModel> arrayList = new ArrayList<SongInfoModel>();
                arrayList.add(songInfoModel);
                try {
                    musicSrv.setPlayList(arrayList);
                } catch (Exception e) {
                    e.printStackTrace();
                }*//*

                playVideoForTab(videoFilePath);

            }
        });
*/

        if (isMyServiceRunning(AudioService.class) && ((MainActivity) getActivity()).getMusicSrv() != null) {
            ((MainActivity) getActivity()).getMusicSrv().onDestroy();
        }

    }

    private void handleback() {

        if (isAdded()) {
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseFragment());
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new CourseDetailsFragment());
            courseDetailBundle.putString("BACKBUTTONCLICKED", "TRUE");
            getArguments().putString("BACKBUTTONCLICKED", "TRUE");

            Log.i(TAG, "handle back");
            if (getArguments().containsKey("listback")) {
                ////Log.e("yes from menu", "123");
                Log.i(TAG, "handle back1");
                ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
            } else {
                if (getArguments().containsKey("origin") && getArguments().getString("origin").equals("CourseFragment")) {
                    Log.i(TAG, "handle back new");
                    ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "Course", null);
                } else if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {
                    Log.i(TAG, "handle back 2");
                    ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetailsFragment", getArguments());
                } else {
                    if (getArguments() != null) {
                        Log.i(TAG, "handle back 3");

                        if (getArguments().containsKey("DEEPLINKTYPE")) {
                            Log.i(TAG, "handle back 4");

                            ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "home", getArguments());
                        } else {
                            Log.i(TAG, "handle back 5");

                            if (getArguments().containsKey("origin")) {
                                Log.i(TAG, "handle back 6");

                                Log.i(TAG, getArguments().getString("origin"));

                                if (getArguments().getString("origin").equals("CourseFragment")) {
                                    Log.i(TAG, "handle back 7");
                                    ((MainActivity) getActivity()).loadFragment(new CourseFragment(), "Course", null);

                                } else if (getArguments().getString("origin").equals("chk")) {
                                    Log.i(TAG, "handle back 8");

                                    ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetailsFragment", getArguments());
                                } else {
                                    Log.i(TAG, "handle back 9");

                                    ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                                }
                            } else {
                                Log.i(TAG, "handle back 10");

                                ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                            }

                        }
                    } else {
                        Log.i(TAG, "handle back 11");

                        ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                    }
                }
            }
        }


    }
    private void openAttachmentDialog1() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_attachment_course);
        LinearLayout llDynamicAttachment = dialog.findViewById(R.id.llDynamicAttachment);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Response<GetArticleDetail> response = globalResponse;
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ///////////////
        if (lastArticleDetails_new.getArticleDetail().getMeditations() != null && lastArticleDetails_new.getArticleDetail().getMeditations().size() > 0) {
            //llAttachment.setVisibility(View.VISIBLE);
            //txtAttachment.setText("Attachments");
            llDynamicAttachment.removeAllViews();
            for (int i = 0; i < lastArticleDetails_new.getArticleDetail().getMeditations().size(); i++) {
                View dynamicView = layoutInflater.inflate(R.layout.dynamic_course_attachement1, null);
                TextView txtTipsInstructions = dynamicView.findViewById(R.id.txtTipsInstructions);
                LinearLayout llTotal = dynamicView.findViewById(R.id.llTotal);
                //txtTipsInstructions.setTextColor(Color.parseColor("#98B6F7"));
                txtTipsInstructions.setText(lastArticleDetails_new.getArticleDetail().getMeditations().get(i).getEventName());
                //txtTipsInstructions.setText("Click Here");
                final int finalI = i;

                dynamicView.setId(i); // Set a unique ID for each view

                dynamicView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Handle click event here
                        int clickedViewId = v.getId(); // Get the ID of the clicked view

                        Log.i("clicked position",String.valueOf(clickedViewId));
                        Log.i("clicked position",String.valueOf(lastArticleDetails_new.getArticleDetail().getMeditations().get(clickedViewId).getEventItemId()));
                        getSuggestedmeditationdetails(lastArticleDetails_new.getArticleDetail().getMeditations().get(clickedViewId).getEventItemId(),dialog);
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

    private void openAttachmentDialog() {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_attachment_course);
        LinearLayout llDynamicAttachment = dialog.findViewById(R.id.llDynamicAttachment);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Response<GetArticleDetail> response = globalResponse;
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        ///////////////
        if (response.body().getArticleDetail().getAttachments() != null && response.body().getArticleDetail().getAttachments().size() > 0) {
            //llAttachment.setVisibility(View.VISIBLE);
            //txtAttachment.setText("Attachments");
            llDynamicAttachment.removeAllViews();
            for (int i = 0; i < response.body().getArticleDetail().getAttachments().size(); i++) {
                View dynamicView = layoutInflater.inflate(R.layout.dynamic_course_attachement, null);
                TextView txtTipsInstructions = dynamicView.findViewById(R.id.txtTipsInstructions);
                LinearLayout llTotal = dynamicView.findViewById(R.id.llTotal);
                //txtTipsInstructions.setTextColor(Color.parseColor("#98B6F7"));
                txtTipsInstructions.setText("ATTACHMENT " + (i + 1));
                //txtTipsInstructions.setText("Click Here");
                final int finalI = i;
                llTotal.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //if (response.body().getArticleDetail().getAttachments().get(finalI).contains("dropbox"))
                        {
                            isRead = true;
                           /* Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            intent.putExtra("WEBVIEWURL", response.body().getArticleDetail().getAttachments().get(finalI));*/


                            Intent intent = new Intent(getActivity(), WebViewActivity.class);
                            String pdf = response.body().getArticleDetail().getAttachments().get(finalI);
                            intent.putExtra("WEBVIEWURL", "http://drive.google.com/viewerng/viewer?embedded=true&url=" + pdf);
                            startActivity(intent);


                        } /*else {
                            Uri uri = Uri.parse(response.body().getArticleDetail().getAttachments().get(finalI)); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            intent.setPackage("com.android.chrome");
                            startActivity(intent);
                        }*/
                    }
                });
                llDynamicAttachment.addView(dynamicView);
            }
        } else {
            //llAttachment.setVisibility(View.GONE);
        }


        dialog.show();
    }
/*TEMP*/
/*
    private void playVideoForTab(String videoFilePath) {

        JSONArray arrJson = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("name", "HLS");
            JSONArray innerJsonArray = new JSONArray();
            JSONObject innerJsonObj = new JSONObject();
            innerJsonObj.put("name", "VIDEO");
            innerJsonObj.put("uri", videoFilePath);
            innerJsonArray.put(innerJsonObj);
            jsonObject.put("samples", innerJsonArray);
            arrJson.put(jsonObject);


            File cacheFile = getActivity().getCacheDir();
            File jsonFile = new File(cacheFile, "/VIDEO/uri.json");
            if (jsonFile.exists()) {
                jsonFile.delete();
            }
            jsonFile.getParentFile().mkdirs();

            try {
                OutputStream outputStream = new FileOutputStream(new File(jsonFile.getAbsolutePath().toString()), true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                outputStreamWriter.write(arrJson.toString());
                outputStreamWriter.close();

                String[] uris = new String[]{
                        jsonFile.getAbsolutePath()
                };
                SampleListLoader sampleListLoader = new SampleListLoader();
                sampleListLoader.execute(uris);

            } catch (IOException e) {
                ////Log.e("Exception", "File write failed: " + e.toString());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

                        */
/*Intent intent=new Intent(getActivity(),VideoPlayDownloadActivity.class);
                        intent.putExtra("VIDEOLINK",VIDEOurl);
                        startActivity(intent);*//*


        //getActivity().finish();

    }
*/

    private void processArticleResponse(GetArticleDetail articleDetail) {
        lastArticleDetails_new=articleDetail;
        // txtHeader.setText(response.body().getArticleDetail().getArticleTitle());
        txtChallengeNameBelow.setText(articleDetail.getArticleDetail().getArticleTitle());
/*
        if (articleDetail.getArticleDetail().getTime() != null && !articleDetail.getArticleDetail().getTime().equals("")) {
            seekTimeApi = articleDetail.getArticleDetail().getTime();
            seekTimeApiLong = Double.parseDouble(seekTimeApi);
            seekTimeApiLong = seekTimeApiLong * 1000;

        }
*/


//        callFireBaseEvent(articleDetail.getArticleDetail().getArticleTitle());



        //Do Tick Status Work
      /*  if (articleDetail.getArticleDetail().getIsRead()) {
            // imgTick.setVisibility(View.VISIBLE);
            //imgTickLast.setVisibility(View.GONE);
            txtTickStatus.setText("DONE");
            txtTickStatusBottom.setText("DONE");


            llTickLast.setBackgroundResource(R.drawable.capsule_green);
            llTickLastBottom.setBackgroundResource(R.drawable.capsule_green);
            txtTickStatus.setTextColor(getActivity().getResources().getColor(R.color.white));
            txtTickStatusBottom.setTextColor(getActivity().getResources().getColor(R.color.white));

        } else {
            //imgTick.setVisibility(View.GONE);
            //imgTickLast.setVisibility(View.GONE);
            txtTickStatus.setText("TICK IT OFF");
            txtTickStatusBottom.setText("TICK IT OFF");

            llTickLast.setBackgroundResource(R.drawable.capsule_border_green_white);
            llTickLastBottom.setBackgroundResource(R.drawable.capsule_border_green_white);
            txtTickStatus.setTextColor(getActivity().getResources().getColor(R.color.light_green));
            txtTickStatusBottom.setTextColor(getActivity().getResources().getColor(R.color.light_green));

        }*/
        isRead = articleDetail.getArticleDetail().getIsRead();
        if (articleDetail.getArticleDetail().getRelatedTask() != null) {
            if (articleDetail.getArticleDetail().getRelatedTask() != null && articleDetail.getArticleDetail().getRelatedTask().getTaskId() != null)
                taskId = articleDetail.getArticleDetail().getRelatedTask().getTaskId();
            if (articleDetail.getArticleDetail().getAttachments() != null && articleDetail.getArticleDetail().getAttachments().size() == 0) {
                rlAttachment.setVisibility(View.GONE);
            }
            if (articleDetail.getArticleDetail().getMeditations() != null && articleDetail.getArticleDetail().getMeditations().size() == 0) {
                rl_suggestedmedicines.setVisibility(View.GONE);
            }
            //  txtTodoHeader.setText(response.body().getArticleDetail().getRelatedTask().getTaskTitle());

            if (articleDetail.getArticleDetail().getRelatedTask().getTaskTitle() != null)
                txtTodoHeader.loadData(articleDetail.getArticleDetail().getRelatedTask().getTaskTitle(), "text/html", "UTF-8");

            String dynamicText = "";
            for (int x = 0; x < articleDetail.getArticleDetail().getRelatedTask().getInstructions().size(); x++) {
                dynamicText += articleDetail.getArticleDetail().getRelatedTask().getInstructions().get(x);
                // dynamicText += "\u2022 ";
            }
            if (!dynamicText.isEmpty()) {
                // txtTodoHeaderAns.setText(dynamicText);
                txtTodoHeaderAns.loadData(dynamicText, "text/html", "UTF-8");
                txtTodoHeaderAns.setVisibility(View.VISIBLE);
            } else txtTodoHeaderAns.setVisibility(View.GONE);

            if (!articleDetail.getArticleDetail().getRelatedTask().getTaskText().isEmpty()) {
                // txtTaskAns.setText(response.body().getArticleDetail().getRelatedTask().getTaskText());
                txtTaskAns.loadData(articleDetail.getArticleDetail().getRelatedTask().getTaskText(), "text/html", "UTF-8");
                txtTaskAns.setVisibility(View.VISIBLE);
            } else txtTaskAns.setVisibility(View.GONE);
        }
        //  if (response.body().getArticleDetail().getPublicVideoUrls() != null && response.body().getArticleDetail().getPublicVideoUrls().size() > 0&&!response.body().getArticleDetail().getPublicVideoUrls().get(0).equals(""))
        {

            if (articleDetail.getArticleDetail().getPublicVideoUrls().get(0) != null) {

                videoFilePath = articleDetail.getArticleDetail().getPublicVideoUrls().get(0);
                if (Util.checkConnection(getActivity())) {
                    String fileName = articleDetail.getArticleDetail().getArticleTitle().replaceAll(" ", "") + "_" + articleDetail.getArticleDetail().getArticleId() + ".mp4";
                    File file = new File(getActivity().getCacheDir(), "SQUAD/" + fileName);

                    globalArticleId = articleDetail.getArticleDetail().getArticleId();
                    globalArticleName = articleDetail.getArticleDetail().getArticleTitle();
                    /* videoView.setVideoURI(Uri.parse("https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/226037127&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true"));*/


                    if (!file.exists()) {
                        //Toast.makeText(getActivity(), "Check download progress in the notification window", Toast.LENGTH_LONG).show();

                        globalVideoURl = articleDetail.getArticleDetail().getPublicVideoUrls().get(0);
                        if (articleDetail.getArticleDetail().getVideos() != null && articleDetail.getArticleDetail().getVideos().size() > 0) {

                            String vId = "";
                            String vTime = "";
                            if (articleDetail.getArticleDetail().getVideos().size() > 0)
                                vId = articleDetail.getArticleDetail().getVideos().get(0);
                            for (int k = 0; k < articleDetail.getArticleDetail().getVideoWithTime().size(); k++) {
                                if (articleDetail.getArticleDetail().getVideoWithTime().get(k).getVideoUrl().equals(vId)) {
                                    vTime = articleDetail.getArticleDetail().getVideoWithTime().get(k).getTime();
                                    seekTimeApiLong = Double.parseDouble(vTime);
                                    seekTimeApiLong = seekTimeApiLong * 1000;
                                }
                            }
                            mVisualizerView.setVisibility(View.GONE);
                            globalVideoId = articleDetail.getArticleDetail().getVideos().get(0);
                            globalVideoLinkForBackgroundPlay = articleDetail.getArticleDetail().getPublicVideoUrls().get(0);
                            globalLinkMediaType = BackgroundSoundServiceNew.MediaType.VIDEO;
                            //videoView.setVideoURI(Uri.parse(articleDetail.getArticleDetail().getPublicVideoUrls().get(0)));

                        } else if (articleDetail.getArticleDetail().getAudios() != null && articleDetail.getArticleDetail().getAudios().size() > 0) {
                            globalVideoId = "";
                            globalVideoLinkForBackgroundPlay = articleDetail.getArticleDetail().getAudios().get(0);
                            globalLinkMediaType = BackgroundSoundServiceNew.MediaType.AUDIO;
                            rlFullscreen.setEnabled(false);
                            //videoView.setVideoURI(Uri.parse(articleDetail.getArticleDetail().getAudios().get(0)));

                            //  frameVideo.setVisibility(View.GONE);
                            //  funAudio(globalVideoId);
                        }

                        Log.e("print global 1--", globalVideoURl +
                                "???" + globalVideoId + "--");

                    } else {
                        //  videoView.setVideoPath(file.getAbsolutePath());
                        globalVideoURl = articleDetail.getArticleDetail().getPublicVideoUrls().get(0);
                        if (articleDetail.getArticleDetail().getVideos() != null && articleDetail.getArticleDetail().getVideos().size() > 0) {
                            String vId = "";
                            String vTime = "";
                            if (articleDetail.getArticleDetail().getVideos().size() > 0)
                                vId = articleDetail.getArticleDetail().getVideos().get(0);
                            for (int k = 0; k < articleDetail.getArticleDetail().getVideoWithTime().size(); k++) {
                                if (articleDetail.getArticleDetail().getVideoWithTime().get(k).getVideoUrl().equals(vId)) {
                                    vTime = articleDetail.getArticleDetail().getVideoWithTime().get(k).getTime();
                                    seekTimeApiLong = Double.parseDouble(vTime);
                                    seekTimeApiLong = seekTimeApiLong * 1000;
                                }
                            }
                            mVisualizerView.setVisibility(View.GONE);
                            globalVideoId = articleDetail.getArticleDetail().getVideos().get(0);
                            globalVideoLinkForBackgroundPlay = articleDetail.getArticleDetail().getPublicVideoUrls().get(0);
                            globalLinkMediaType = BackgroundSoundServiceNew.MediaType.VIDEO;
                            //videoView.setVideoURI(Uri.parse(articleDetail.getArticleDetail().getPublicVideoUrls().get(0)));
                        } else if (articleDetail.getArticleDetail().getAudios() != null && articleDetail.getArticleDetail().getAudios().size() > 0) {
                            globalVideoId = "";
                            globalVideoLinkForBackgroundPlay = articleDetail.getArticleDetail().getAudios().get(0);
                            globalLinkMediaType = BackgroundSoundServiceNew.MediaType.AUDIO;
                            rlFullscreen.setOnClickListener(null);
                            //videoView.setVideoURI(Uri.parse(articleDetail.getArticleDetail().getAudios().get(0)));
                            //  frameVideo.setVisibility(View.GONE);
                            // funAudio(globalVideoId);
                        }

                        Log.e("print global 2--", globalVideoURl +
                                "???" + globalVideoId + "--");
                    }

                }
            } else if (articleDetail.getArticleDetail().getAudios() != null && articleDetail.getArticleDetail().getAudios().size() > 0) {
                globalVideoId = articleDetail.getArticleDetail().getVideos().get(0);
                globalVideoLinkForBackgroundPlay = articleDetail.getArticleDetail().getAudios().get(0);
                globalLinkMediaType = BackgroundSoundServiceNew.MediaType.AUDIO;
                rlFullscreen.setOnClickListener(null);
                //videoView.setVideoURI(Uri.parse(articleDetail.getArticleDetail().getAudios().get(0)));
                // videoView.setVisibility(View.GONE);
                //  frameVideo.setVisibility(View.GONE);
                //  funAudio(globalVideoId);
            }

            //videoView.setVideoURI(Uri.parse(globalVideoLinkForBackgroundPlay));
            courseDetailBundle.putString("globalVideoId", globalVideoId);

            if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                frameVideo.setVisibility(View.VISIBLE);
                videoView.setVisibility(View.VISIBLE);

                mVisualizerView.setVisibility(View.GONE);
                programWithImageContainer.setVisibility(View.GONE);
                //musicSrv.getMediaPlayer().setSurface(videoView.getHolder().getSurface());
            } else {
                frameVideo.setVisibility(View.GONE);
                programWithImageContainer.setVisibility(View.VISIBLE);
            }


            Intent intent = new Intent(getActivity(), BackgroundSoundServiceNew.class);
            requireActivity().startService(intent);
            ((MainActivity) requireActivity()).setMusicConnection(musicConnection);
            requireActivity().bindService(intent,
                    musicConnection, Context.BIND_AUTO_CREATE);

        }

        rlFlashScreen.setVisibility(View.GONE);

    }

    private void getArticleDetailsFromApi() {
     /*   //Log.e("print art id-", articleId +
                "?" + courseId + "?");*/

        if (Connection.checkConnection(getActivity())) {

            // progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", sharedPreference.read("UserID", ""));
            hashMap.put("CourseId", courseId);
            hashMap.put("ArticleId", articleId);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            Call<GetArticleDetail> getArticleDetailCall = finisherService.getArticleDetail(hashMap);
            getArticleDetailCall.enqueue(new Callback<GetArticleDetail>() {
                @Override
                public void onResponse(Call<GetArticleDetail> call, final Response<GetArticleDetail> response) {
                    if (progressDialog != null)
                        progressDialog.dismiss();
                    try {
                        if (response.body() != null && response.body().getArticleDetail() != null) {
                            globalResponse = response;

                            sharedPreference.write("LAST_ARTICLE_DETAILS", "", new Gson().toJson(response.body()));
                            sharedPreference.write("LAST_ARTICLE_ID", "", articleId.toString());

                            processArticleResponse(response.body());

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(Call<GetArticleDetail> call, Throwable t) {
                    rlFlashScreen.setVisibility(View.GONE);
                    progressDialog.dismiss();
                }
            });


        } else {
            rlFlashScreen.setVisibility(View.GONE);
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void funAudio(String globalVideoIdN) {
        llSeek.setVisibility(View.GONE);
        webview.setVisibility(View.VISIBLE);
        videoView.setVisibility(View.GONE);
        rlControl.setVisibility(View.GONE);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);
        webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        ProgressDialog progressBar = ProgressDialog.show(getActivity(), null, "Loading...");

        webview.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i("Tag", "Processing webview url click...");
                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i("Tag", "Finished loading URL: " + url);
                if (progressBar.isShowing()) {
                    progressBar.dismiss();
                }
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                //Log.e("Tag", "Error: " + description);
                Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
   /*     String testUrl="https://w.soundcloud.com/player/?url=https%3A//api.soundcloud.com/tracks/226037127&amp;auto_play=false&amp;hide_related=false&amp;show_comments=true&amp;show_user=true&amp;show_reposts=false&amp;visual=true";
        testUrl=testUrl.replaceAll("hide_related=false","hide_related=true");*/
        globalVideoIdN = globalVideoIdN.replaceAll("hide_related=false", "hide_related=true");
        webview.loadUrl(globalVideoIdN);
        //webview.loadUrl(globalVideoId);
    }

    private void funToolBar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        ImageView imgLogo = toolbar.findViewById(R.id.imgLogo);
        ImageView imgLeftBack = toolbar.findViewById(R.id.imgLeftBack);
        ImageView imgRightBack = toolbar.findViewById(R.id.imgRightBack);
        ImageView imgFilter = toolbar.findViewById(R.id.imgFilter);
        ImageView imgCircleBack = toolbar.findViewById(R.id.imgCircleBack);
        ImageView imgHelp = toolbar.findViewById(R.id.imgHelp);
        ImageView imgCalender = toolbar.findViewById(R.id.imgCalender);
        FrameLayout frameNotification = toolbar.findViewById(R.id.frameNotification);
        TextView txtPageHeader = toolbar.findViewById(R.id.txtPageHeader);

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
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        imgRightBack.setVisibility(View.GONE);
        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCacheFfragment();
                playOnBackButtonPress = false;
                if (getArguments().containsKey("listback")) {
                    ////Log.e("yes from menu", "123");
                    ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                } else {
                    if (new SharedPreference(getActivity()).read("compChk", "").equals("false")) {
                        ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetailsFragment", null);
                    } else {
                        if (getArguments() != null) {

                            if (getArguments().containsKey("DEEPLINKTYPE")) {
                                ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "home", null);
                            } else {
                                if (getArguments().containsKey("origin")) {
                                    if (getArguments().getString("origin").equals("chk")) {
                                        ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetailsFragment", null);
                                    } else {
                                        ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                                    }
                                } else

                                    ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                            }
                        } else {
                            ((MainActivity) getActivity()).loadFragment(new CourseDetailsFragment(), "CourseDetails", courseDetailBundle);
                        }
                    }
                }


            }
        });
    }

    public void updateTask(boolean isDone) {
        if (Connection.checkConnection(getActivity())) {

            //progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", sharedPreference.read("UserID", ""));
            hashMap.put("TaskId", taskId);
            hashMap.put("IsDone", isDone);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            Call<ReadUnreadResponse> unreadResponseCall = finisherService.updateTask(hashMap);
            unreadResponseCall.enqueue(new Callback<ReadUnreadResponse>() {
                @Override
                public void onResponse(Call<ReadUnreadResponse> call, Response<ReadUnreadResponse> response) {
                    //progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().getSuccessFlag()) {
                            videoView.pause();
                            clearCacheFfragment();
                            getArticleDetailsFromApi();
                            /*((MainActivity)getActivity()).loadFragment(new CourseArticleDetailsFragment(),"CourseArticleDetails",courseDetailBundle);*/
                        }
                    }

                }

                @Override
                public void onFailure(Call<ReadUnreadResponse> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    ///////////////////////////////////////////////////EXOPLAYER////////////////////////////////////////////////////

    private void clearCacheFfragment() {
        try {
            for (int p = 0; p < ((MainActivity) getActivity()).arrFragment.size(); p++) {
                if (((MainActivity) getActivity()).arrFragment.get(p).getClass().getSimpleName().contains("CourseFragment")) {
                    ((MainActivity) getActivity()).arrFragment.remove(p);
                    ////Log.e("Removed--", "90");
                    break;
                }
            }
            for (int p = 0; p < ((MainActivity) getActivity()).arrFragment.size(); p++) {
                if (((MainActivity) getActivity()).arrFragment.get(p).getClass().getSimpleName().contains("CourseDetailsFragment")) {
                    ((MainActivity) getActivity()).arrFragment.remove(p);
                    ////Log.e("Removed--", "60");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    @Override
    public void onTrackChange(int position) {

    }

    private void onSampleGroups(final List<SampleGroup> groups, boolean sawError) {
        if (sawError) {
            Toast.makeText(getActivity(), R.string.sample_list_load_error, Toast.LENGTH_LONG)
                    .show();
        }
        onSampleSelected(groups.get(0).samples.get(0));
    }

    private void onSampleSelected(Sample sample) {
        /*TEMP*/
       /* startActivity(sample.buildIntent(getActivity()));*/
        //getActivity().startActivityForResult(sample.buildIntent(getActivity()),Util.PLAY_PAUSE_REQUEST);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
/*commented by sahenita unused in squad*/
/*
    private void openFullscreenVideoDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_show_video);
        RelativeLayout rlBack = (RelativeLayout) dialog.findViewById(R.id.rlBack);
        ProgressBar progress = (ProgressBar) dialog.findViewById(R.id.progress);
        VideoView video_viewDialog = (VideoView) dialog.findViewById(R.id.video_view);

        ImageView imgExitFullScreen = (ImageView) dialog.findViewById(R.id.imgExitFullScreen);

        imgExitFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    musicSrv.getMediaPlayer().setDisplay(null);
                    musicSrv.getMediaPlayer().setSurface(null);
                    video_viewDialog.getHolder().getSurface().release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                dialog.dismiss();

            }
        });

        video_viewDialog.requestFocus();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {

                try {
                    musicSrv.getMediaPlayer().setDisplay(null);
                    musicSrv.getMediaPlayer().setSurface(null);
                    //videoView.getHolder().getSurface().release();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {

                    if (globalLinkMediaType == BackgroundSoundServiceNew.MediaType.VIDEO) {
                        musicSrv.getMediaPlayer().setSurface(video_viewDialog.getHolder().getSurface());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

            }
        });

        dialog.show();

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);


    }
*/

    private void callFireBaseEvent(String articleTitle) {
        Bundle bundle = new Bundle();
        bundle.putString("week_course_8", articleTitle);
        /*commented by sahenita*/
       // ((MainActivity) getActivity()).postFireBaseEvent(FirebaseAnalytics.Event.JOIN_GROUP, bundle);
    }


   /* private ServiceConnection musicConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.ServiceBinder binder = (AudioService.ServiceBinder) service;
            //get service
            musicSrv = binder.getService();
            ((MainActivity) getActivity()).setMusicSrv(musicSrv);
            musicSrv.setCurrenentTrack(CourseArticleDetailsFragment.this);

            ////Log.e("connection ", "open");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //musicBound = false;
            musicSrv = null;
        }
    };*/


   /* public void doBindService() {
        getActivity().bindService(new Intent(getActivity(), AudioService.class), musicConnection, Context.BIND_AUTO_CREATE);
        //musicBound = true;
        ////Log.e("BIND", "BIND>>>>>>>>>>>>");
    }*/

   /* public void doUnbindService() {
        //if(musicBound)
        getActivity().unbindService(musicConnection);
        ////Log.e("UNBIND", "UNBIND>>>>>>>>>>>>");
    }*/

    /////////SeekBAR/////////////
    public void updateProgressBar() {
        mHandler.postDelayed(mUpdateTimeTask, 100);
    }

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

    //////////////SeekBar////////

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

    private void setDurationTime() {
        if (Connection.checkConnection(getActivity())) {

            //if (!sharedPreference.read("USEREMAIL", "").equals(""))
            {
                // progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("UserId", sharedPreference.read("UserID", ""));
                //hashMap.put("CourseId", courseId);
                hashMap.put("ArticleId", articleId);
                hashMap.put("VideoId", globalVideoId);
                hashMap.put("Time", ((videoView.getCurrentPosition()) / 1000) + "");
                //hashMap.put("Time", ((globalCurrentVideoPostion)/1000)+"");
                hashMap.put("Key", Util.KEY);


                {
                    Call<JsonObject> readUnreadResponseCall = finisherService.setDuration(hashMap);
                    readUnreadResponseCall.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            // progressDialog.dismiss();
                            if (response.body() != null) {
                                {
                                    //handleback();
                                }
                            } else {
                                //handleback();
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            // progressDialog.dismiss();
                            //handleback();
                        }
                    });
                }
            }

         /*   else {
                ((MainActivity) getActivity()).openDialogForRegisterUser(null, null);
            }*/


        } else {
            // handleback();
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }
/*commented by sahenita unused in squad*/
/*
    private void playBackgroundMusic(Integer videoCurrentPosition) {
        //globalCurrentVideoPostion = videoCurrentPosition;
        Intent intent = new Intent(getActivity(), BackgroundSoundService.class);
        intent.putExtra("VIDEOURI", globalVideoLinkForBackgroundPlay);
        intent.putExtra("VIDEO_POS", videoCurrentPosition);
        intent.putExtra("VIDEO_NAME", txtChallengeNameBelow.getText().toString());
        intent.putExtra("VIDEO_PLAYING_ONLINE", "TRUE");
        intent.putExtra("FROM_PAGE", "COURSE");

        // getActivity().startService(intent);
        doBindService(intent);
    }
*/

    ///////////////////////////////////////////////////////////////////////////
    public void doBindService(Intent intent) {

        ((MainActivity) getActivity()).setMusicConnection(musicConnection);
        getActivity().bindService(intent,
                musicConnection, Context.BIND_AUTO_CREATE);
        musicBound = true;
        Log.e("bind ", "zzz");

    }

    private void enableMediaControls() {

        imgPlayPause.setEnabled(true);
        imgPlayPauseOnVideo.setEnabled(true);
        seekBarOnVideo.setEnabled(true);
        SeekBarTestPlay.setEnabled(true);
        imgForward.setEnabled(true);
        imgBack.setEnabled(true);
        imgForwardOnVideo.setEnabled(true);
        imgBackwardOnVideo.setEnabled(true);
        Log.i(TAG, "controls enabled");
    }

    private void disableMediaControls() {

        imgPlayPause.setEnabled(false);
        imgPlayPauseOnVideo.setEnabled(false);
        seekBarOnVideo.setEnabled(false);
        SeekBarTestPlay.setEnabled(false);
        imgForward.setEnabled(false);
        imgBack.setEnabled(false);
        imgForwardOnVideo.setEnabled(false);
        imgBackwardOnVideo.setEnabled(false);
        Log.i(TAG, "controls disabled");

    }
    /*commented by sahenita unused in squad*/

/*
    private void doUnbindService() {
        new Thread(new Runnable() {
            public void run() {
                if (isMyServiceRunning(BackgroundSoundService.class) && ((MainActivity) getActivity()).getMusicConnection() != null) {
                    getActivity().unbindService(((MainActivity) getActivity()).getMusicConnection());
                    musicBound = false;
                    Log.e("case--", 78 + "??");
                    ((MainActivity) getActivity()).setMusicConnection(null);
                }
            }
        }).start();
    }
*/

    /////////////////////
    private void setupVisualizerFxAndUI() {
//http://android-er.blogspot.com/2015/02/create-audio-visualizer-for-mediaplayer.html
        // Create the Visualizer object and attach it to our media player.
        Log.e("plot graph--", "123" + "??");

        try {
            mVisualizerView.setVisibility(View.VISIBLE);
            // videoView.setVisibility(View.GONE);
            mVisualizer = new Visualizer(videoView.getAudioSessionId());
            mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);
            mVisualizer.setDataCaptureListener(
                    new Visualizer.OnDataCaptureListener() {
                        public void onWaveFormDataCapture(Visualizer visualizer,
                                                          byte[] bytes, int samplingRate) {
                            // Log.e("print bytes--",bytes+"??");
                            mVisualizerView.updateVisualizer(bytes);
                        }

                        public void onFftDataCapture(Visualizer visualizer,
                                                     byte[] bytes, int samplingRate) {
                        }
                    }, Visualizer.getMaxCaptureRate() / 2, true, false);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    ////////////////BROADCAST FOR GETTING VIDEO POSITION IN SERVICE////////////

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


    enum FRAGMENT_STATE {
        RESUMED,
        PAUSED
    }

    private static final class SampleAdapter extends BaseExpandableListAdapter {

        private final Context context;
        private final List<SampleGroup> sampleGroups;

        public SampleAdapter(Context context, List<SampleGroup> sampleGroups) {
            this.context = context;
            this.sampleGroups = sampleGroups;
        }

        @Override
        public Sample getChild(int groupPosition, int childPosition) {
            return getGroup(groupPosition).samples.get(childPosition);
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                                 View convertView, ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent,
                        false);
            }
            ((TextView) view).setText(getChild(groupPosition, childPosition).name);
            return view;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return getGroup(groupPosition).samples.size();
        }

        @Override
        public SampleGroup getGroup(int groupPosition) {
            return sampleGroups.get(groupPosition);
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                                 ViewGroup parent) {
            View view = convertView;
            if (view == null) {
                view = LayoutInflater.from(context).inflate(android.R.layout.simple_expandable_list_item_1,
                        parent, false);
            }
            ((TextView) view).setText(getGroup(groupPosition).title);
            return view;
        }

        @Override
        public int getGroupCount() {
            return sampleGroups.size();
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }

    private static final class SampleGroup {

        public final String title;
        public final List<Sample> samples;

        public SampleGroup(String title) {
            this.title = title;
            this.samples = new ArrayList<>();
        }

    }

    private abstract static class Sample {

        public final String name;
        public final boolean preferExtensionDecoders;
        public final UUID drmSchemeUuid;
        public final String drmLicenseUrl;
        public final String[] drmKeyRequestProperties;

        public Sample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                      String[] drmKeyRequestProperties, boolean preferExtensionDecoders) {
            this.name = name;
            this.drmSchemeUuid = drmSchemeUuid;
            this.drmLicenseUrl = drmLicenseUrl;
            this.drmKeyRequestProperties = drmKeyRequestProperties;
            this.preferExtensionDecoders = preferExtensionDecoders;
        }
/*TEMP*/
/*
        public Intent buildIntent(Context context) {
            Intent intent = new Intent(context, ExoPlayerActivity.class);
            intent.putExtra(ExoPlayerActivity.PREFER_EXTENSION_DECODERS, preferExtensionDecoders);
            if (drmSchemeUuid != null) {
                intent.putExtra(ExoPlayerActivity.DRM_SCHEME_UUID_EXTRA, drmSchemeUuid.toString());
                intent.putExtra(ExoPlayerActivity.DRM_LICENSE_URL, drmLicenseUrl);
                intent.putExtra(ExoPlayerActivity.DRM_KEY_REQUEST_PROPERTIES, drmKeyRequestProperties);
            }
            return intent;
        }
*/

    }
/*TEMP*/
/*
    private static final class UriSample extends Sample {

        public final String uri;
        public final String extension;

        public UriSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                         String[] drmKeyRequestProperties, boolean preferExtensionDecoders, String uri,
                         String extension) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            this.uri = uri;
            this.extension = extension;
        }

        @Override
        public Intent buildIntent(Context context) {
            return super.buildIntent(context)
                    .setData(Uri.parse(uri))
                    .putExtra(ExoPlayerActivity.EXTENSION_EXTRA, extension)
                    .setAction(ExoPlayerActivity.ACTION_VIEW);
        }

    }
*/
/*TEMP*/
/*
    private static final class PlaylistSample extends Sample {

        public final UriSample[] children;

        public PlaylistSample(String name, UUID drmSchemeUuid, String drmLicenseUrl,
                              String[] drmKeyRequestProperties, boolean preferExtensionDecoders,
                              UriSample... children) {
            super(name, drmSchemeUuid, drmLicenseUrl, drmKeyRequestProperties, preferExtensionDecoders);
            this.children = children;
        }

        @Override
        public Intent buildIntent(Context context) {
            String[] uris = new String[children.length];
            String[] extensions = new String[children.length];
            for (int i = 0; i < children.length; i++) {
                uris[i] = children[i].uri;
                extensions[i] = children[i].extension;

            }
            return super.buildIntent(context)
                    .putExtra(ExoPlayerActivity.URI_LIST_EXTRA, uris)
                    .putExtra(ExoPlayerActivity.EXTENSION_LIST_EXTRA, extensions)
                    .setAction(ExoPlayerActivity.ACTION_VIEW_LIST);
        }

    }
*/
/*TEMP*/
/*
    private final class SampleListLoader extends AsyncTask<String, Void, List<SampleGroup>> {

        private boolean sawError;

        @Override
        protected List<SampleGroup> doInBackground(String... uris) {
            List<SampleGroup> result = new ArrayList<>();
            Context context = getActivity();
            String userAgent = com.google.android.exoplayer2.util.Util.getUserAgent(context, "ExoPlayerDemo");
            DataSource dataSource = new DefaultDataSource(context, null, userAgent, false);
            for (String uri : uris) {
                DataSpec dataSpec = new DataSpec(Uri.parse(uri));
                InputStream inputStream = new DataSourceInputStream(dataSource, dataSpec);
                try {
                    readSampleGroups(new JsonReader(new InputStreamReader(inputStream, "UTF-8")), result);
                } catch (Exception e) {
                    //////Log.e(TAG, "Error loading sample list: " + uri, e);
                    sawError = true;
                } finally {
                    com.google.android.exoplayer2.util.Util.closeQuietly(dataSource);
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(List<SampleGroup> result) {
            onSampleGroups(result, sawError);
        }

        private void readSampleGroups(JsonReader reader, List<SampleGroup> groups) throws IOException {
            reader.beginArray();
            while (reader.hasNext()) {
                readSampleGroup(reader, groups);
            }
            reader.endArray();
        }

        private void readSampleGroup(JsonReader reader, List<SampleGroup> groups) throws IOException {
            String groupName = "";
            ArrayList<Sample> samples = new ArrayList<>();

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "name":
                        groupName = reader.nextString();
                        break;
                    case "samples":
                        reader.beginArray();
                        while (reader.hasNext()) {
                            samples.add(readEntry(reader, false));
                        }
                        reader.endArray();
                        break;
                    case "_comment":
                        reader.nextString(); // Ignore.
                        break;
                    default:
                        throw new ParserException("Unsupported name: " + name);
                }
            }
            reader.endObject();

            SampleGroup group = getGroup(groupName, groups);
            group.samples.addAll(samples);
        }

        private Sample readEntry(JsonReader reader, boolean insidePlaylist) throws IOException {
            String sampleName = null;
            String uri = null;
            String extension = null;
            UUID drmUuid = null;
            String drmLicenseUrl = null;
            String[] drmKeyRequestProperties = null;
            boolean preferExtensionDecoders = false;
            ArrayList<UriSample> playlistSamples = null;

            reader.beginObject();
            while (reader.hasNext()) {
                String name = reader.nextName();
                switch (name) {
                    case "name":
                        sampleName = reader.nextString();
                        break;
                    case "uri":
                        uri = reader.nextString();
                        break;
                    case "extension":
                        extension = reader.nextString();
                        break;
                    case "drm_scheme":
                        Assertions.checkState(!insidePlaylist, "Invalid attribute on nested item: drm_scheme");
                        drmUuid = getDrmUuid(reader.nextString());
                        break;
                    case "drm_license_url":
                        Assertions.checkState(!insidePlaylist,
                                "Invalid attribute on nested item: drm_license_url");
                        drmLicenseUrl = reader.nextString();
                        break;
                    case "drm_key_request_properties":
                        Assertions.checkState(!insidePlaylist, "Invalid attribute on nested item: drm_key_request_properties");
                        ArrayList<String> drmKeyRequestPropertiesList = new ArrayList<>();
                        reader.beginObject();
                        while (reader.hasNext()) {
                            drmKeyRequestPropertiesList.add(reader.nextName());
                            drmKeyRequestPropertiesList.add(reader.nextString());
                        }
                        reader.endObject();
                        drmKeyRequestProperties = drmKeyRequestPropertiesList.toArray(new String[0]);
                        break;
                    case "prefer_extension_decoders":
                        Assertions.checkState(!insidePlaylist,
                                "Invalid attribute on nested item: prefer_extension_decoders");
                        preferExtensionDecoders = reader.nextBoolean();
                        break;
                    case "playlist":
                        Assertions.checkState(!insidePlaylist, "Invalid nesting of playlists");
                        playlistSamples = new ArrayList<>();
                        reader.beginArray();
                        while (reader.hasNext()) {
                            playlistSamples.add((UriSample) readEntry(reader, true));
                        }
                        reader.endArray();
                        break;
                    default:
                        throw new ParserException("Unsupported attribute name: " + name);
                }
            }
            reader.endObject();

            if (playlistSamples != null) {
                UriSample[] playlistSamplesArray = playlistSamples.toArray(
                        new UriSample[playlistSamples.size()]);
                return new PlaylistSample(sampleName, drmUuid, drmLicenseUrl, drmKeyRequestProperties,
                        preferExtensionDecoders, playlistSamplesArray);
            } else {
                return new UriSample(sampleName, drmUuid, drmLicenseUrl, drmKeyRequestProperties,
                        preferExtensionDecoders, uri, extension);
            }
        }

        private SampleGroup getGroup(String groupName, List<SampleGroup> groups) {
            for (int i = 0; i < groups.size(); i++) {
                if (com.google.android.exoplayer2.util.Util.areEqual(groupName, groups.get(i).title)) {
                    return groups.get(i);
                }
            }
            SampleGroup group = new SampleGroup(groupName);
            groups.add(group);
            return group;
        }

        private UUID getDrmUuid(String typeString) throws ParserException {
            switch (typeString.toLowerCase()) {
                case "widevine":
                    return C.WIDEVINE_UUID;
                case "playready":
                    return C.PLAYREADY_UUID;
                default:
                    try {
                        return UUID.fromString(typeString);
                    } catch (RuntimeException e) {
                        throw new ParserException("Unsupported drm type: " + typeString);
                    }
            }
        }

    }
*/

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

                // onPlayPause1();

            }
        }
    };

    BroadcastReceiver VideoPlaybackReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("othervideo",action);
/*
            if (action != null && action.equals("android.media.AUDIO_BECOMING_NOISY")) {
                AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if (audioManager != null && audioManager.isMusicActive()) {
                    Log.i("othervideo","playing");
                    // Another video is playing
                    // Perform your desired actions here
                }
            }
*/
/*TEMP*/
/*
            if (intent.getAction() != null && intent.getAction().equals(VideoPlaybackService.ACTION_VIDEO_PLAYING)) {
                Log.i("othervideo","playing");
                // Video is playing, perform necessary actions
                // You can update UI, log the event, or trigger other operations here
            }
*/

        }
    };

    private void onPlayPause() {
        //  Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
        Log.e("MMPPLL", "onPlayPause: " + musicSrv.isMediaPlaying());
        if (musicSrv.isMediaPlaying()) {

            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause_black);
        } else {
            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play_black);
            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play_black);
        }

    }
    private void onPlayPause1() {
        //  Toast.makeText(this,"Test",Toast.LENGTH_SHORT).show();
        Log.e("MMPPLL", "onPlayPause: " + musicSrv.isMediaPlaying());
        if (musicSrv.isMediaPlaying()) {

            imgPlayPause.setBackgroundResource(R.drawable.mbhq_pause_black);
            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_pause_black);
        } else {
//            imgPlayPause.setBackgroundResource(R.drawable.mbhq_play);
//            imgPlayPauseOnVideo.setBackgroundResource(R.drawable.mbhq_play);
        }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void getSuggestedmeditationdetails(int EventItemId,Dialog dialog) {
     /*   //Log.e("print art id-", articleId +
                "?" + courseId + "?");*/
        Log.i("courdrid",String.valueOf(EventItemId));
//        if (Connection.checkConnection(getActivity())) {


        // progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("UserId", sharedPreference.read("UserID", ""));
        hashMap.put("EventItemId", String.valueOf(EventItemId));
        hashMap.put("Key", Util.KEY);
        hashMap.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

        Call<Suggestedmedicin> getArticleDetailCall = finisherService.getsuggestedmeditation(hashMap);
        getArticleDetailCall.enqueue(new Callback<Suggestedmedicin>() {
            @Override
            public void onResponse(Call<Suggestedmedicin> call, final Response<Suggestedmedicin> response) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                try {
                    if (response.body() != null && response.body()!= null) {
                        dialog.dismiss();
                        globalResponse1=response;
                        MeditationCourseModel.Webinar lstData=new MeditationCourseModel.Webinar();
                        lstData.setCommentsCount(response.body().getMeditationDetails().getCommentsCount());
                        lstData.setContent(response.body().getMeditationDetails().getContent());
                        lstData.setDuration(response.body().getMeditationDetails().getDuration());
                        lstData.setDonationAmount(response.body().getMeditationDetails().getDonationAmount());
                        lstData.setEventItemID(response.body().getMeditationDetails().getEventItemID());
                        lstData.setEventName(response.body().getMeditationDetails().getEventName());
                        int v=response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getEventItemVideoID();
                        MeditationCourseModel.EventItemVideoDetail eventItemVideoDetail=new MeditationCourseModel.EventItemVideoDetail();

                        eventItemVideoDetail.setEventItemVideoID(v);
                        eventItemVideoDetail.setSequenceNo(response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getSequenceNo());
                        eventItemVideoDetail.setVideoURL(response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getVideoURL());
                        eventItemVideoDetail.setAppURL(response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getAppURL());
                        eventItemVideoDetail.setDownloadURL(response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getDownloadURL2());
                        eventItemVideoDetail.setIsWatchListVideo(response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getIsWatchListVideo());
                        eventItemVideoDetail.setIsViewedVideo(response.body().getMeditationDetails().getEventItemVideoDetails().get(0).getIsViewedVideo());
                        ArrayList<MeditationCourseModel.EventItemVideoDetail>eventItemVideoDetailArrayList=new ArrayList<>();
                        eventItemVideoDetailArrayList.add(eventItemVideoDetail);

                        lstData.setEventItemVideoDetails(eventItemVideoDetailArrayList);
                        lstData.setTags(response.body().getMeditationDetails().getTags());
                        lstData.setPresenterName(response.body().getMeditationDetails().getPresenterName());
                        lstData.setEventType(response.body().getMeditationDetails().getEventType());
                        lstData.setImageUrl(response.body().getMeditationDetails().getImageUrl());
                        lstData.setEventTypename(response.body().getMeditationDetails().getEventTypename());
                        ((MainActivity) requireActivity()).clearCacheForParticularFragment(new MeditationDetailsNew());
                        Util.backto="course";
                        MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
                        Bundle bundle = new Bundle();
                        String totalData = new Gson().toJson(lstData);
                        bundle.putString("data", totalData);

                        bundle.putInt("COURSEID", courseId);
                        bundle.putInt("ARTICLEID", articleId);
                        bundle.putString("AUTHOR", author);
                        bundle.putString("type", type);
                        bundle.putString("IMAGE_URL", imageUrl);

                        meditationDetails.setArguments(bundle);
                        ((MainActivity) requireActivity()).loadFragment(meditationDetails, "MeditationDetailsNew", null);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<Suggestedmedicin> call, Throwable t) {
                rlFlashScreen.setVisibility(View.GONE);
                progressDialog.dismiss();
            }
        });



    }




}