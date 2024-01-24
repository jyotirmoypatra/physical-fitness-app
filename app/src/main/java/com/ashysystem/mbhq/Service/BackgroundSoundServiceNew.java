package com.ashysystem.mbhq.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSession;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.ashysystem.mbhq.BuildConfig;
import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.GuidedMeditationModel;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.livechat.Chat;
import com.ashysystem.mbhq.roomDatabase.MbhqDatabse;
import com.ashysystem.mbhq.roomDatabase.datasource.localDataSource.LocalMeditationDataSource;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BackgroundSoundServiceNew extends Service{
    private MediaSessionManager mediaSessionManager;
    private boolean isYouTubePlaying;
    private Thread checkMusicActiveThread;
    private Handler handler;
    private Runnable checkMusicActiveRunnable;
   // private AudioManager audioManager;
    private AudioManager.OnAudioFocusChangeListener afChangeListener;
    private Integer changetag=0;
    private MediaSession mediaSession;
   private AudioFocusRequest focusRequest;
    private Runnable mediaUpdateTimeTask = new Runnable() {
        public void run() {

            long currentDuration = currentMediaPosition();

            try {
                Log.i(TAG, "current duration in secs:" + (currentDuration / 1000));
                if (currentDuration / 1000 >= 30 && !isPlayingNonCued() && !isThisMeditationTimeRecorded) {
                    sendMeditationDataToApi();
                    isThisMeditationTimeRecorded = true;
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    Log.i(TAG, "meditation data sent to api:");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();

                Log.i(TAG, "an error occured! error msg:"+e.getMessage()+" error cause: "+ e.getCause());

            }


            mediaPlayerHandler.postDelayed(this, 1000);
        }
    };

    private Handler mediaPlayerHandler = new Handler();

    public enum MediaState {
        MEDIA_NOT_PREPARED,
        MEDIA_PREPARED,
        MEDIA_PLAYING,
        MEDIA_PAUSED,
        MEDIA_BUFFERING_START,
        MEDIA_BUFFERING_END,
        MEDIA_SEEK_START,
        MEDIA_SEEK_COMPLETE,
        MEDIA_SIZE_CHANGED,
        MEDIA_ERROR,
        MEDIA_CAN_NOT_BE_PLAYED
    }

    public interface OnMediaStateListener {
        void onMediaStateChange(MediaPlayer mp, MediaState newState);
    }


    public static   MediaPlayer mediaPlayer = null; ////////
    public  static  String videoName = "";
    String videoUrl = "";
    MediaType mediaType = MediaType.UNKNOWN;
    FromPage fromPage = FromPage.NONE;
    MeditationCourseModel.Webinar meditationData;
    Bundle programData;
    Chat liveChatData;
    MeditationCourseModel.Webinar m_liveChatData;
    MeditationCourseModel.Webinar liveChatData1=null;
    String TAG = "BackgroundSoundServiceNew";
    public static final String ACTION_PLAY = "actionplay"; ///
    public static final String CHANNEL_ID = "channel1";///

    String SPACE_ODYSSEY_NO_CUES = "https://squad-live.s3-ap-southeast-2.amazonaws.com/mbHQ+Meditate/Space/Space+Odyssey_BLANK_20mins.mp3";
    String THE_FOREST_NO_CUES = "https://squad-live.s3-ap-southeast-2.amazonaws.com/mbHQ+Meditate/forest/The+Forest_BLANK_+20mins.mp3";
    String AIR_FLOW_NO_CUES = "https://squad-live.s3-ap-southeast-2.amazonaws.com/mbHQ+Meditate/wind/Air+Flow_BLANK+20mins.mp3";
    String DEEP_CALM_NO_CUES = "https://squad-live.s3-ap-southeast-2.amazonaws.com/mbHQ+Meditate/water/Deep+Calm_+BLANK+20mins.mp3";

    boolean playingNonCued = false;
    boolean shouldPlayNonCuedAfterWards = false;

    private boolean isThisMeditationTimeRecorded = false;

    String nextPlayableMeditationUrl = "";

    NextPlayableMediaType nextPlayableMeditationType = NextPlayableMediaType.NONE;

    private OnMediaStateListener mediaStateListener = null;

    private ArrayList<GuidedMeditationModel> otherNextMeditations = new ArrayList<>();

    public static Notification notification; ///


    private final IBinder serviceBinder = new ServiceBinder();

    public enum FromPage {
        MEDITATION,
        PROGRAM,
        MEDITATION_VIDEO,
        LIVE_CHAT,
        NONE
    }

    public enum MediaType {
        VIDEO,
        AUDIO,
        UNKNOWN
    }

    public enum NextPlayableMediaType {

        NONE,
        NO_CUE,
        GUIDED1,
        GUIDED2,
        GUIDED3,
        GUIDED4,
        GUIDED5,
        OTHER

    }


    public class ServiceBinder extends Binder {
        public  BackgroundSoundServiceNew getService() {
            return BackgroundSoundServiceNew.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind");
        return serviceBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind");
        super.onUnbind(intent);
        return true;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG, "onRebind");
    }


    @Override
    public void onCreate() {
        super.onCreate();



        Log.i(TAG, "onCreate");
    }




    public int onStartCommand(Intent intent, int flags, int startId) {
// Create a new instance of AudioFocusRequest

        Log.i(TAG, "onStartCommand");
        return START_STICKY;
    }

    public void onStart(Intent intent, int startId) {
        Log.i(TAG, "onStart");
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy");
        saveMediaCursor();
        if (mediaPlayer != null) {
            try {
                if (fromPage.equals(FromPage.MEDITATION)) {
                    int video_pos = mediaPlayer.getCurrentPosition();
                    Intent intent = new Intent("com.ashysystem.mbhq.getPositionOfVideo");
                    intent.putExtra("CURRENT_POS_VIDEO", video_pos);
                    intent.putExtra("VIDEO_NAME", videoName);
                    sendBroadcast(intent);
                } else {
                    int video_pos = mediaPlayer.getCurrentPosition();
                    Intent intent = new Intent("com.ashysystem.mbhq.getPositionOfVideoFromCourse");
                    intent.putExtra("CURRENT_POS_VIDEO", video_pos);
                    intent.putExtra("VIDEO_NAME", videoName);
                    sendBroadcast(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mediaPlayer.stop();
                mediaPlayer.release();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Log.i(TAG, "onDestroy end");


    }



    @Override
    public void onLowMemory() {
        Log.i(TAG, "onLowMemory");
    }



    public void startMedia() {
        Log.e("SRTTTTTTT", "startMedia: "  + "STARTM");
        try {
            mediaPlayer.start();
            try{
                if (mediaStateListener != null) {
                    mediaStateListener.onMediaStateChange(mediaPlayer, MediaState.MEDIA_PLAYING);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                startForeground(9876, createNotification("Play"));
                switch (this.fromPage) {
                    case MEDITATION: {
                        Util.boolBackGroundServiceRunningMeditation = true;
                        Util.strMeditationDetailsForBackground = new Gson().toJson(this.meditationData);

                        mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                        if(!isThisMeditationTimeRecorded){
                            mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);
                        }

                        break;
                    }

                    case PROGRAM: {
                        Util.boolBackGroundServiceRunningProgram = true;
                        Util.bundleProgramDetailsForBackground = this.programData;
                        mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                        break;
                    }   case LIVE_CHAT: {
                        Util.boolBackGroundServiceRunningProgram_video = true;
                        Util.bundleProgramDetailsForBackground_vedio = this.m_liveChatData;
                        mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                        break;
                    }
                    default: {
                        mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                        break;
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
            Log.e("EXXCCCCPPPP", "startMedia: " + e.getMessage() );
        }




    }
    public void pauseMedia() {
        Log.e("SRTTTTTTT", "startMedia: "  + "PAUSEM");
        saveMediaCursor();
        try {
            Util.boolBackGroundServiceRunningMeditation = false;
            Util.strMeditationDetailsForBackground = "";
            Util.boolBackGroundServiceRunningProgram = false;
            Util.bundleProgramDetailsForBackground = null;
            Util.boolBackGroundServiceRunningProgram_video = false;
            Util.bundleProgramDetailsForBackground_vedio = null;
            //  stopForeground(true);
            startForeground(9876, createNotification("Pause"));
            mediaPlayer.pause();
            if (mediaStateListener != null) {
                mediaStateListener.onMediaStateChange(mediaPlayer, MediaState.MEDIA_PAUSED);
            }
            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopMedia() {
        saveMediaCursor();
        try {
            if (this.fromPage == FromPage.MEDITATION) {
                Util.boolBackGroundServiceRunningMeditation = false;
                Util.strMeditationDetailsForBackground = "";
            } else if (this.fromPage == FromPage.PROGRAM) {
                Util.boolBackGroundServiceRunningProgram = false;
                Util.bundleProgramDetailsForBackground = null;
            }else if (this.fromPage == FromPage.MEDITATION_VIDEO) {
                Util.boolBackGroundServiceRunningProgram_video = false;
                Util.bundleProgramDetailsForBackground_vedio = null;
            }
            stopForeground(true);
            mediaPlayer.stop();
            if (mediaStateListener != null) {
                mediaStateListener.onMediaStateChange(mediaPlayer, MediaState.MEDIA_PAUSED);
            }
            mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seekMedia(int msec) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(msec);
                if (mediaStateListener != null) {
                    mediaStateListener.onMediaStateChange(mediaPlayer, MediaState.MEDIA_SEEK_START);
                }
                Log.i(TAG, "media seeked at "+ msec);
                if(msec < 30000 && !isPlayingNonCued() && fromPage == FromPage.MEDITATION){
                    mediaPlayerHandler.removeCallbacks(mediaUpdateTimeTask);
                    mediaPlayerHandler.postDelayed(mediaUpdateTimeTask, 1000);
                    isThisMeditationTimeRecorded = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "an error occured. seek media error. error msg : "+e.getMessage() + " error cause:" + e.getCause());
        }
    }

    public int currentMediaPosition() {
        try {
            if (mediaPlayer != null) {
                return mediaPlayer.getCurrentPosition();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int totalMediaDuration() {
        try {
            if (mediaPlayer != null) {
                return mediaPlayer.getDuration();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static boolean isMediaPlaying() {

        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }

        try {
            if (mediaPlayer != null) {
                Log.e("player is playing",""+mediaPlayer.isPlaying());
                return mediaPlayer.isPlaying();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public static String getVideoName() {
        return videoName;
    }

    public FromPage getFromPage() {
        return fromPage;
    }

    public boolean isPlayingNonCued() {
        return playingNonCued;
    }

    public boolean shouldPlayNonCuedAfterWards() {
        return shouldPlayNonCuedAfterWards;
    }

    public void setShouldPlayNonCuedAfterWards(boolean shouldPlayNonCuedAfterWards) {
        this.shouldPlayNonCuedAfterWards = shouldPlayNonCuedAfterWards;
    }

    public String getNextPlayableMeditationUrl() {

        if (getNextPlayableMeditationType() == NextPlayableMediaType.OTHER) {
            for (int index = 0; index < getOtherNextMeditations().size(); index++) {
                if (getOtherNextMeditations().get(index).isSelected()) {
                    return getOtherNextMeditations().get(index).getWebinar().getEventItemVideoDetails().get(0).getVideoURL();
                }
            }
        }

        return nextPlayableMeditationUrl;
    }

    public void setNextPlayableMeditationUrl(String nextPlayableMeditationUrl) {
        this.nextPlayableMeditationUrl = nextPlayableMeditationUrl;
        Log.i(TAG, "next playable media : " + nextPlayableMeditationUrl);
    }

    public NextPlayableMediaType getNextPlayableMeditationType() {
        return nextPlayableMeditationType;
    }

    public void setNextPlayableMeditationType(NextPlayableMediaType nextPlayableMeditationType) {
        if (nextPlayableMeditationType != NextPlayableMediaType.OTHER) {
            disableAllOtherMeditation();
        }
        this.nextPlayableMeditationType = nextPlayableMeditationType;
    }

    public void setNextMeditation(String nextMeditationUrl, NextPlayableMediaType nextMeditationType) {

        setNextPlayableMeditationUrl(nextMeditationUrl);

        setNextPlayableMeditationType(nextMeditationType);

        if (nextMeditationType == NextPlayableMediaType.NONE) {
            setShouldPlayNonCuedAfterWards(false);
            if (this.mediaPlayer != null) {
                this.mediaPlayer.setLooping(false);
                this.mediaPlayer.setOnCompletionListener(null);
            }
        } else {
            setShouldPlayNonCuedAfterWards(true);
            if (this.mediaPlayer != null) {
                this.mediaPlayer.setLooping(false);
                this.mediaPlayer.setOnCompletionListener(null);
                this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        seekMedia(0);
                        pauseMedia();
                        Log.i(TAG, "Media finished");
                        playNonCue();
                    }
                });
            }
        }

    }

    public void enableOtherMeditation(GuidedMeditationModel data) {

        for (int index = 0; index < otherNextMeditations.size(); index++) {
            otherNextMeditations.get(index).setSelected(false);
        }

        for (int index = 0; index < otherNextMeditations.size(); index++) {
            if (data.getWebinar().getEventItemID().equals(otherNextMeditations.get(index).getWebinar().getEventItemID())) {
                otherNextMeditations.get(index).setSelected(true);
                setNextMeditation("", NextPlayableMediaType.OTHER);
            }
        }
    }

    public void disableOtherMeditation(GuidedMeditationModel data) {
        for (int index = 0; index < otherNextMeditations.size(); index++) {
            if (data.getWebinar().getEventItemID().equals(otherNextMeditations.get(index).getWebinar().getEventItemID())) {
                otherNextMeditations.get(index).setSelected(false);
            }
        }
    }

    public void disableAllOtherMeditation() {
        for (int index = 0; index < otherNextMeditations.size(); index++) {
            otherNextMeditations.get(index).setSelected(false);
        }
    }

    public void addOtherMeditation(GuidedMeditationModel data) {
        otherNextMeditations.add(data);
    }

    public void resetOtherMeditation() {
        otherNextMeditations.clear();
    }

    public ArrayList<GuidedMeditationModel> getOtherNextMeditations() {
        return otherNextMeditations;
    }

    public void removeOtherMeditation(GuidedMeditationModel data) {

        for (int index = 0; index < otherNextMeditations.size(); index++) {
            if (data.getWebinar().getEventItemID().equals(otherNextMeditations.get(index).getWebinar().getEventItemID())) {
                Log.i(TAG, "removed other med: " + otherNextMeditations.get(index).getWebinar().getEventName());
                otherNextMeditations.remove(index);
            }
        }

    }


    public MediaType getMediaType() {
        return this.mediaType;
    }

    public void playNonCue() {

        try {
            if (getNextPlayableMeditationUrl().equals("")) {

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer = null;
                }
                Log.i(TAG, "same url: " + videoUrl);
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), Uri.parse(videoUrl));
                mediaPlayer.setLooping(false);
                removeMediaStateListener();
                attachMediaStateListener();
                if (mediaStateListener != null) {
                    mediaStateListener.onMediaStateChange(getMediaPlayer(), MediaState.MEDIA_NOT_PREPARED);
                }
                mediaPlayer.prepareAsync();
                playingNonCued = false;

            } else {

                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer = null;
                }
                Log.i(TAG, "non cue/guided url: " + getNextPlayableMeditationUrl());
                mediaPlayer = MediaPlayer.create(getApplicationContext(), Uri.parse(getNextPlayableMeditationUrl()));
                mediaPlayer.setLooping(false);
                removeMediaStateListener();
                attachMediaStateListener();
                if (mediaStateListener != null) {
                    mediaStateListener.onMediaStateChange(getMediaPlayer(), MediaState.MEDIA_NOT_PREPARED);
                }
                startMedia();
                playingNonCued = true;

            }


        } catch (Exception e) {
            e.printStackTrace();
        }





    }

    private Uri getNonCueUrlByMeditation(String meditationName) {
        if (meditationName.toLowerCase().contains("space")) {
            return Uri.parse(SPACE_ODYSSEY_NO_CUES);
        } else if (meditationName.toLowerCase().contains("air")) {
            return Uri.parse(AIR_FLOW_NO_CUES);
        } else if (meditationName.toLowerCase().contains("forest")) {
            return Uri.parse(THE_FOREST_NO_CUES);
        } else {
            return Uri.parse(DEEP_CALM_NO_CUES);
        }
    }

    private void removeMediaStateListener() {
        try {
            mediaPlayer.setOnPreparedListener(null);
            mediaPlayer.setOnBufferingUpdateListener(null);
            mediaPlayer.setOnSeekCompleteListener(null);
            mediaPlayer.setOnInfoListener(null);
            mediaPlayer.setOnVideoSizeChangedListener(null);
            mediaPlayer.setOnErrorListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void attachMediaStateListener() {

        try {
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {

                    Log.i(TAG, "Media prepared...");
                    if (mediaStateListener != null) {
                        mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_PREPARED);
                    }


                }
            });

            mediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
                @Override
                public void onBufferingUpdate(MediaPlayer mp, int percent) {
                    /*Log.i(TAG, "Buffering... "+ percent+ "%");*/
                }
            });

            mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                @Override
                public void onSeekComplete(MediaPlayer mp) {
                    Log.i(TAG, "Seek Complete");
                    if (mediaStateListener != null)
                        mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_SEEK_COMPLETE);
                }
            });

            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {

                    switch (what) {
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            Log.i(TAG, "Media Buffer start...");
                            if (mediaStateListener != null)
                                mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_BUFFERING_START);
                            return true;

                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            Log.i(TAG, "Media Buffer end...");
                            if (mediaStateListener != null)
                                mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_BUFFERING_END);
                            return true;

                        default:
                            Log.i(TAG, "Info Code: " + what + " extra :" + extra);
                            return true;
                    }

                }
            });

            mediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
                @Override
                public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                    Log.i(TAG, "Media Size changed... width: " + width + " height: " + height);
                    if (mediaStateListener != null) {
                        mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_SIZE_CHANGED);
                    }
                }
            });

            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.i(TAG, "Error Code: " + what + " extra :" + extra);
                    if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN) {
                        if (mediaStateListener != null) {
                            mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_CAN_NOT_BE_PLAYED);
                        }
                    } else {
                        if (mediaStateListener != null) {
                            mediaStateListener.onMediaStateChange(mp, MediaState.MEDIA_ERROR);
                        }
                    }

                    return true;
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void createMediaPlayer_(String videoName, FromPage pageName, Uri videoUrl, int videoCurrentPosition, MeditationCourseModel.Webinar data, boolean isVideoOnline, String noCueUrl, OnMediaStateListener listener) {

        Log.i(TAG, "prev video =" + this.videoName);
        Log.i(TAG, "curr video =" + videoName);
        Log.i(TAG, "curr video url=" + videoUrl);
        Log.i("videourl",videoUrl.toString());
        Log.i("mediatype",data.getEventItemVideoDetails().get(0).getMediaType());

        removeMediaStateListener();

        this.mediaStateListener = listener;

        try {
            this.mediaPlayer.setOnCompletionListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (
                (this.videoName.equals(videoName) && !isPlayingNonCued())
                        || (this.videoName.equals(videoName) && isPlayingNonCued() && isMediaPlaying())
        ) {

            attachMediaStateListener();

        } else {

            this.videoName = videoName;
            this.fromPage = pageName;
            this.meditationData = data;

            this.playingNonCued = false;

            this.isThisMeditationTimeRecorded = false;

            resetOtherMeditation();

            try {
                this.mediaPlayer.setOnCompletionListener(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isVideoOnline) {

                try {

                    Uri VIDEOURI = videoUrl;
                    Log.i("videourl",VIDEOURI.toString());


                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    } else {
                        mediaPlayer.reset();
                    }

                    mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);

                    this.videoUrl = VIDEOURI.toString();

                    mediaPlayer.setLooping(false);

                    attachMediaStateListener();

                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                Uri VIDEOURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", new File(String.valueOf(videoUrl)));
                Log.i("videourl",VIDEOURI.toString());
                try {

                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    } else {
                        mediaPlayer.reset();
                    }
                    //mediaPlayer = new MediaPlayer();

                    mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);

                    this.videoUrl = VIDEOURI.toString();


                    mediaPlayer.setLooping(false);

                    attachMediaStateListener();

                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (noCueUrl.isEmpty()) {
                setNextMeditation("", NextPlayableMediaType.NONE);
            } else {
                setNextMeditation(noCueUrl, NextPlayableMediaType.NO_CUE);
            }

        }

    }
    public void createMediaPlayer(String videoName, FromPage pageName, String videoUrl, int videoCurrentPosition, MeditationCourseModel.Webinar data, boolean isVideoOnline, String noCueUrl, OnMediaStateListener listener) {


        removeMediaStateListener();

        this.mediaStateListener = listener;

        try {
            this.mediaPlayer.setOnCompletionListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (
                (this.videoName.equals(videoName) && !isPlayingNonCued())
                        || (this.videoName.equals(videoName) && isPlayingNonCued() && isMediaPlaying())
        ) {

            attachMediaStateListener();

        } else {

            this.videoName = videoName;
            this.fromPage = pageName;
            this.meditationData = data;

            this.playingNonCued = false;

            this.isThisMeditationTimeRecorded = false;

            resetOtherMeditation();

            try {
                this.mediaPlayer.setOnCompletionListener(null);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (isVideoOnline) {

                try {

                    Uri VIDEOURI = Uri.parse(videoUrl);
                    Log.i("videourl",VIDEOURI.toString());

                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    } else {
                        mediaPlayer.reset();
                    }

                    mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);

                    this.videoUrl = VIDEOURI.toString();


                    mediaPlayer.setLooping(false);

                    attachMediaStateListener();

                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

                Uri VIDEOURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".fileprovider", new File(videoUrl));
                Log.i("videourl",VIDEOURI.toString());
                try {

                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    } else {
                        mediaPlayer.reset();
                    }

                    mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);

                    this.videoUrl = VIDEOURI.toString();



                    mediaPlayer.setLooping(false);

                    attachMediaStateListener();

                    mediaPlayer.prepareAsync();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (noCueUrl.isEmpty()) {
                setNextMeditation("", NextPlayableMediaType.NONE);
            } else {
                setNextMeditation(noCueUrl, NextPlayableMediaType.NO_CUE);
            }

        }

    }

    public void createMediaPlayer(String videoName, FromPage pageName, String videoUrl, MediaType mediaType, int videoCurrentPosition, Bundle arguments, OnMediaStateListener listener) {

        Log.i(TAG, "prev video =" + this.videoName);
        Log.i(TAG, "curr video =" + videoName);
        Log.i(TAG, "curr video url=" + videoUrl);

        removeMediaStateListener();
        this.mediaStateListener = listener;

        try {
            this.mediaPlayer.setOnCompletionListener(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (this.videoName.equals(videoName)) {

            attachMediaStateListener();

        } else {
            this.videoName = videoName;
            this.mediaType = mediaType;
            this.fromPage = pageName;
            this.programData = arguments;
            this.shouldPlayNonCuedAfterWards = false;
            this.playingNonCued = false;
            this.isThisMeditationTimeRecorded = false;
            Log.i(TAG, "here");
            try {
                if (mediaStateListener != null) {
                    mediaStateListener.onMediaStateChange(mediaPlayer, MediaState.MEDIA_NOT_PREPARED);
                }
                Uri VIDEOURI = Uri.parse(videoUrl);


                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.reset();
                }

                mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);
                mediaPlayer.setLooping(false);
                attachMediaStateListener();
                mediaPlayer.prepareAsync();
                setNextMeditation("", NextPlayableMediaType.NONE);
                resetOtherMeditation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void createMediaPlayer_meditationvideo_(String videoName, FromPage pageName, Uri videoUrl, MediaType mediaType, int videoCurrentPosition, MeditationCourseModel.Webinar liveChatData, OnMediaStateListener listener) {

        Log.i(TAG, "prev video =" + this.videoName);
        Log.i(TAG, "curr video =" + videoName);
        Log.i(TAG, "curr video url=" + videoUrl);


        this.mediaStateListener = listener;

        if (this.videoName.equals(videoName)) {

            attachMediaStateListener();

        } else {

            this.videoName = videoName;
            this.mediaType = mediaType;
            this.fromPage = pageName;
            this.m_liveChatData = liveChatData;
            this.playingNonCued = false;
            this.isThisMeditationTimeRecorded = false;

            try {
                Uri VIDEOURI = videoUrl;
                //Uri VIDEOURI = Uri.parse(videoUrl);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.reset();
                }
                /*if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer = null;
                }*/
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);
                mediaPlayer.setLooping(false);
                attachMediaStateListener();
                mediaPlayer.prepareAsync();
                setNextMeditation("", NextPlayableMediaType.NONE);
                resetOtherMeditation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void createMediaPlayer_meditationvideo(String videoName, FromPage pageName, String videoUrl, MediaType mediaType, int videoCurrentPosition, MeditationCourseModel.Webinar liveChatData, OnMediaStateListener listener) {

        Log.i(TAG, "prev video =" + this.videoName);
        Log.i(TAG, "curr video =" + videoName);
        Log.i(TAG, "curr video url=" + videoUrl);


        this.mediaStateListener = listener;

        if (this.videoName.equals(videoName)) {

            attachMediaStateListener();

        } else {

            this.videoName = videoName;
            this.mediaType = mediaType;
            this.fromPage = pageName;
            this.m_liveChatData = liveChatData;
            this.playingNonCued = false;
            this.isThisMeditationTimeRecorded = false;

            try {

                Uri VIDEOURI = Uri.parse(videoUrl);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.reset();
                }
                /*if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer = null;
                }*/
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);
                mediaPlayer.setLooping(false);
                attachMediaStateListener();
                mediaPlayer.prepareAsync();
                setNextMeditation("", NextPlayableMediaType.NONE);
                resetOtherMeditation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void createMediaPlayer1_(String videoName, FromPage pageName, Uri videoUrl, MediaType mediaType, int videoCurrentPosition, MeditationCourseModel.Webinar liveChatData, OnMediaStateListener listener) {
        Log.i("called_block_media","2");

        this.mediaStateListener = listener;

        if (this.videoName.equals(videoName)) {

            attachMediaStateListener();

        } else {
            this.videoName = videoName;
            this.mediaType = mediaType;
            this.fromPage = pageName;
            this.liveChatData1 = liveChatData;
            this.playingNonCued = false;
            this.isThisMeditationTimeRecorded = false;

            try {
                Uri VIDEOURI = videoUrl;
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.reset();
                }

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);
                mediaPlayer.setLooping(false);
                attachMediaStateListener();
                mediaPlayer.prepareAsync();
                setNextMeditation("", NextPlayableMediaType.NONE);
                resetOtherMeditation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void createMediaPlayer1(String videoName, FromPage pageName, String videoUrl, MediaType mediaType, int videoCurrentPosition, MeditationCourseModel.Webinar liveChatData, OnMediaStateListener listener) {
        Log.i("called_block_media","1");
        Log.i(TAG, "prev video =" + this.videoName);
        Log.i(TAG, "curr video =" + videoName);
        Log.i(TAG, "curr video url=" + videoUrl);


        this.mediaStateListener = listener;

        if (this.videoName.equals(videoName)) {

            attachMediaStateListener();

        } else {
            this.videoName = videoName;
            this.mediaType = mediaType;
            this.fromPage = pageName;
            this.liveChatData1 = liveChatData;
            this.playingNonCued = false;
            this.isThisMeditationTimeRecorded = false;

            try {
                Uri VIDEOURI = Uri.parse(videoUrl);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.reset();
                }

                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);
                mediaPlayer.setLooping(false);
                attachMediaStateListener();
                mediaPlayer.prepareAsync();
                setNextMeditation("", NextPlayableMediaType.NONE);
                resetOtherMeditation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void createMediaPlayer(String videoName, FromPage pageName, String videoUrl, MediaType mediaType, int videoCurrentPosition, Chat liveChatData, OnMediaStateListener listener) {

        Log.i(TAG, "prev video =" + this.videoName);
        Log.i(TAG, "curr video =" + videoName);
        Log.i(TAG, "curr video url=" + videoUrl);


        this.mediaStateListener = listener;

        if (this.videoName.equals(videoName)) {

            attachMediaStateListener();

        } else {

            this.videoName = videoName;
            this.mediaType = mediaType;
            this.fromPage = pageName;
            this.liveChatData = liveChatData;
            this.playingNonCued = false;
            this.isThisMeditationTimeRecorded = false;

            try {
                Uri VIDEOURI = Uri.parse(videoUrl);
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                } else {
                    mediaPlayer.reset();
                }
                /*if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    mediaPlayer = null;
                }*/
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(getApplicationContext(), VIDEOURI);
                mediaPlayer.setLooping(false);
                attachMediaStateListener();
                mediaPlayer.prepareAsync();
                setNextMeditation("", NextPlayableMediaType.NONE);
                resetOtherMeditation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void saveMediaCursor() {

        if (this.fromPage == FromPage.MEDITATION && !this.playingNonCued) {

            try {
                MbhqDatabse database = MbhqDatabse.getInstance(getApplicationContext());
                LocalMeditationDataSource dataSource = new LocalMeditationDataSource(database.meditationListDao());
                CompositeDisposable mDisposable = new CompositeDisposable();

                MeditationCourseModel.Webinar webinar = this.meditationData;
                String insertedTime = String.valueOf(currentMediaPosition() / 1000);
                Log.i(TAG, "inserted time =>" + insertedTime);
                Log.i(TAG, "event item id =>" + webinar.getEventItemID());
                mDisposable.add(dataSource.getMeditationById(webinar.getEventItemID())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meditation -> {

                            Log.i(TAG, "meditataion id =>" + meditation.getMeditationId());
                            Log.i(TAG, "event item id =>" + webinar.getEventItemID());
                            webinar.getEventItemVideoDetails().get(0).setTime(insertedTime);

                            Log.i(TAG, "Meditation insert time => " + webinar.getEventItemVideoDetails().get(0).getTime());

                            meditation.setMeditationDetails(new Gson().toJson(webinar));
                            mDisposable.add(dataSource.updateMeditation(meditation)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.i(TAG, "Meditation insert => TRUE");
                                    }, throwable -> {
                                        Log.i(TAG, "Meditation insert => FALSE");
                                    }));

                        }, throwable -> {

                        }));



            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (this.fromPage == FromPage.PROGRAM) {
            try {
                FinisherServiceImpl finisherService = new FinisherServiceImpl(getApplicationContext());
                SharedPreference sharedPreference = new SharedPreference(getApplicationContext());

                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("UserId", sharedPreference.read("UserID", ""));
                if (this.programData.containsKey("globalVideoId")) {
                    hashMap.put("VideoId", this.programData.getString("globalVideoId"));
                }
                if (this.programData.containsKey("ARTICLEID")) {
                    hashMap.put("ArticleId", this.programData.getInt("ARTICLEID"));
                }

                hashMap.put("Time", ((mediaPlayer.getCurrentPosition()) / 1000) + "");
                hashMap.put("Key", Util.KEY);
                Call<JsonObject> readUnreadResponseCall = finisherService.setDuration(hashMap);
                readUnreadResponseCall.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        Log.i(TAG, "onResponse");
                        Log.i(TAG, response.message());
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Log.i(TAG, "onfailure");
                        t.printStackTrace();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


    private Notification createNotification(String tag) {
        Log.e("ISPLAYINGGGGG", "createNotification: " + tag );
        Log.e("MDDDPLAYINGGGGG", "createNotification: " + isMediaPlaying() );

        switch (fromPage) {
            case PROGRAM:
                if(tag.equals("Play")){
                    return createNotificationForProgram(R.drawable.ic_baseline_pause);///
                }else{
                    return createNotificationForProgram(R.drawable.ic_baseline_play);///
                }


            case MEDITATION:

                if(tag.equals("Play")){
                    return createNotificationForMeditation(R.drawable.ic_baseline_pause);///
                }else{
                    return createNotificationForMeditation(R.drawable.ic_baseline_play);///
                }

            case LIVE_CHAT:
                // return createNotificationForLiveChat();
                if(tag.equals("Play")){
                    return createNotificationForLiveChat(R.drawable.ic_baseline_pause);///
                }else{
                    return createNotificationForLiveChat(R.drawable.ic_baseline_play);///
                }
            case MEDITATION_VIDEO:
                // return createNotificationForLiveChat();
                if(tag.equals("Play")){
                    return createNotificationForLiveChat(R.drawable.ic_baseline_pause);///
                }else{
                    return createNotificationForLiveChat(R.drawable.ic_baseline_play);///
                }

            default:
                return null;
        }
    }


    ///////////////////////////// Modified /////////////////////////////////////////////////////////////////////////////////////////
    private Notification createNotificationForMeditation(int resId) {

        String notificationChannelId = "Meditation play service";
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            NotificationChannel channel = new NotificationChannel(
                    notificationChannelId,
                    "Meditation background playing",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Meditation Playing Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null, null);

            nm.createNotificationChannel(channel);
       // }


        Intent meditationPlayIntent = new Intent(this, MainActivity.class);
        meditationPlayIntent.putExtra("NOTIFICATIONTYPE", "MEDITATION_PLAYING");
        meditationPlayIntent.putExtra("FROM_LOGIN", "FALSE");
        Gson gson = new Gson();
        meditationPlayIntent.putExtra("data", gson.toJson(this.meditationData));

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(meditationPlayIntent);


//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {


            pendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_IMMUTABLE);

        } else {

            pendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }

//        Intent intentPlay=new Intent(getApplicationContext(), NotificationActionService.class)
//                .setAction(ACTION_PLAY);
//        PendingIntent pendingIntentPlay=PendingIntent.getBroadcast(getApplicationContext(),0,
//                intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingIntentPlay=null;
        try {
            Intent intentPlay = new Intent(getApplicationContext(), NotificationActionService.class)
                    .setAction(ACTION_PLAY);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                pendingIntentPlay = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        intentPlay, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntentPlay = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        @SuppressLint({"NewApi", "LocalSuppress"}) Notification.Builder builder = new Notification.Builder(getApplicationContext(), notificationChannelId);


        String notificationTitle = "";
        String notificationContent = "";
        if (this.meditationData != null) {
            if (this.meditationData.getTags() != null && this.meditationData.getTags().get(0) != null) {
                notificationTitle = this.meditationData.getTags().get(0);
            }
            notificationContent = this.meditationData.getEventName();
        }



        return builder
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setContentIntent(pendingIntent)
                // .setContentIntent(pendingIntentPlay) ////////
                .setSmallIcon(R.drawable.app_icon)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .addAction(resId,"Play",pendingIntentPlay)
                .setStyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(0))
                .setOnlyAlertOnce(true) // show notification for only first time
                .setShowWhen(false)
                .setOngoing(true)
                .setSound(null)
                .build();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    ////////////////////////////// Modified ////////////////////////////////////////////////////////////////////////////
    public  Notification createNotificationForProgram(int resId) {

        String notificationChannelId = "Program play service";
      //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            NotificationChannel channel = new NotificationChannel(
                    notificationChannelId,
                    "Program background playing",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Program Playing Channel");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null, null);

            nm.createNotificationChannel(channel);
       // }


        Intent meditationPlayIntent = new Intent(this, MainActivity.class);
        meditationPlayIntent.putExtra("NOTIFICATIONTYPE", "PROGRAM_PLAYING");
        meditationPlayIntent.putExtra("data", this.programData);
        meditationPlayIntent.putExtra("FROM_LOGIN", "FALSE");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(meditationPlayIntent);


//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);


        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

            pendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_IMMUTABLE);

        } else {

            pendingIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }


       /* Intent intentPlay=new Intent(getApplicationContext(), NotificationActionService.class)
                .setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay=PendingIntent.getBroadcast(getApplicationContext(),0,
                intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);*/

        PendingIntent pendingIntentPlay=null;
        try {
            Intent intentPlay = new Intent(getApplicationContext(), NotificationActionService.class)
                    .setAction(ACTION_PLAY);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
                pendingIntentPlay = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        intentPlay, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntentPlay = PendingIntent.getBroadcast(getApplicationContext(), 0,
                        intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
            }
        }catch (Exception e){
            e.printStackTrace();
        }


        @SuppressLint({"NewApi", "LocalSuppress"}) Notification.Builder builder = new Notification.Builder(getApplicationContext(), notificationChannelId);


        String notificationTitle = "Program";
        String notificationContent = "Program is playing";
        notificationContent = getVideoName();



        return builder
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.app_icon)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .addAction(resId,"Play",pendingIntentPlay)
                .setStyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(0))
                .setOnlyAlertOnce(true) // show notification for only first time
                .setShowWhen(false)
                .setOngoing(true)
                .setSound(null)
                .build();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    /////////////////////////////////////// Modified ///////////////////////////////////////////////////////////////////////////
    private  Notification createNotificationForLiveChat(int resId) {

        String notificationChannelId = "Program play service livechat";
       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager nm = ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE));
            NotificationChannel channel = new NotificationChannel(
                    notificationChannelId,
                    "Program background playing livechat",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription("Program Playing Channel livechat");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(false);
            channel.setImportance(NotificationManager.IMPORTANCE_DEFAULT);
            channel.setSound(null, null);

            nm.createNotificationChannel(channel);
       // }


        Intent meditationPlayIntent = new Intent(this, MainActivity.class);
        meditationPlayIntent.putExtra("NOTIFICATIONTYPE", "LIVE_CHAT_PLAYING");
        meditationPlayIntent.putExtra("data", Util.chat);
        Log.e("Event name","background"+Util.chat.getEventName());
        meditationPlayIntent.putExtra("FROM_LOGIN", "FALSE");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(meditationPlayIntent);


//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,
//                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {

             pendingIntent = stackBuilder.getPendingIntent(1,
                 PendingIntent.FLAG_IMMUTABLE);

        } else {

            pendingIntent = stackBuilder.getPendingIntent(1,
                    PendingIntent.FLAG_CANCEL_CURRENT);
        }
        PendingIntent pendingIntentPlay=null;
try {
    Intent intentPlay = new Intent(getApplicationContext(), NotificationActionService.class)
            .setAction(ACTION_PLAY);


    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        pendingIntentPlay = PendingIntent.getBroadcast(getApplicationContext(), 1,
                intentPlay, PendingIntent.FLAG_IMMUTABLE);
    } else {
        pendingIntentPlay = PendingIntent.getBroadcast(getApplicationContext(), 1,
                intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}catch (Exception e){
    e.printStackTrace();
}
        @SuppressLint({"NewApi", "LocalSuppress"}) Notification.Builder builder = new Notification.Builder(getApplicationContext(), notificationChannelId);


        String notificationTitle = "Live Chat";
        String notificationContent = "Live chat is playing";
        notificationContent = getVideoName();


        return builder
                .setContentTitle(notificationTitle)
                .setContentText(notificationContent)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.app_icon)
                .setPriority(Notification.PRIORITY_DEFAULT)
                .addAction(resId,"Play",pendingIntentPlay)
                .setStyle(new Notification.MediaStyle()
                        .setShowActionsInCompactView(0))
                .setOnlyAlertOnce(true) // show notification for only first time
                .setShowWhen(false)
                .setOngoing(true)
                .setSound(null)
                .build();
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void sendMeditationDataToApi() {

        if (Connection.checkConnection(getApplicationContext())) {

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getApplicationContext());

            SharedPreference sharedPreference = new SharedPreference(getApplicationContext());

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("UserId", sharedPreference.read("UserID", ""));
            hashMap.put("EventItemId", this.meditationData.getEventItemID());
            if (!meditationData.getEventItemVideoDetails().isEmpty()) {
                hashMap.put("VideoUrl", meditationData.getEventItemVideoDetails().get(0).getAppURL());
            }
            hashMap.put("Time", ((currentMediaPosition()) / 1000) + "");
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



        }

    }


}