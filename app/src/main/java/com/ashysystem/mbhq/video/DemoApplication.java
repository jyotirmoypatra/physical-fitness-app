/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ashysystem.mbhq.video;

import static com.ashysystem.mbhq.media.crop.RotateBitmap.TAG;

import android.content.Context;
import android.content.IntentFilter;

import android.text.TextUtils;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import com.ashysystem.mbhq.activity.CustomExceptionHandler;

import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.ShowDiffPageBroadcast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.util.Util;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import androidx.multidex.MultiDexApplication;

/**
 * Placeholder application to facilitate overriding Application methods for debugging and testing.
 */
public class DemoApplication extends MultiDexApplication {
    public static final String DEEPLINK_FRIEND_REQUEST = "friendShipId";
    //public static SkuDetails REQUESTED_SKU = null;


    protected String userAgent;
    private RequestQueue mRequestQueue;

    private static DemoApplication mInstance;
    public boolean isForeGround = true;
    private SharedPreference sharedPreference;
    private Date installedTime;
    public static SimpleDateFormat signUpDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat installDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    public static final String REGISTER_NOTIFICATION_KEY = "post_register_notification";
    public static final String TRIAL_NOTIFICATION_KEY = "trail_notification";
    public static final String FREE_WORKOUT_NOTIFICATION_KEY = "free_workout__notification";

    public static final String FOR_EIGHT_WEEK = "eight_week";
    public static final String FOR_UPCOMING_WEBINAR = "upcoming_webinar";
    public static final String FOR_CUSTOM_SHOP_LIST = "custom_shop_list";
    public static final String FOR_FIT_BIT = "fit_bit";
    public static final String FOR_CONNECT = "connect";
    public static final String FOR_ALL_WEBINARS = "all_webinars";
    public static final String FOR_MY_PHOTO = "my_photo";
    public static final String FOR_FBW_TRACK = "fbw_track";
    public static final String FOR_CALORIE_COUNT = "calorie_count";
    public static final String FOR_NUTRITION = "nutrition";
    public static final String FOR_EXERCISE = "exercise";
    public static final String FOR_CUSTOM_WORKOUT = "custom_workout";
    public static final String BUNDLE_KEY_FOR = "for";

    /////////////////DEEPLINK///////////////////////

    public static final String DEEPLINK_CUSTOMWORKOUT = "customexercise";
    public static final String DEEPLINK_CHALLENGE = "challenge";
    public static final String DEEPLINK_FITBITIWATCH = "fitbit_iwatch";
    public static final String DEEPLINK_FBWTRACKER = "fbw_tracker";
    public static final String DEEPLINK_SESSIONLIST = "session_list";
    public static final String DEEPLINK_CALORIE_COUNTER = "calorie_counter";
    public static final String DEEPLINK_FOOD_PREP_HELPER = "food_prep_helper";
    public static final String DEEPLINK_RECIPES_LIST = "recipes_list";
    public static final String DEEPLINK_8_WEEK_CHALLENGE = "eight_week_challenge";
    public static final String DEEPLINK_SET_PROGRAMS = "set_programs";
    public static final String DEEPLINK_WEBINAR = "webinar_all";
    public static final String DEEPLINK_FIND_A_FRIEND = "find_a_friend";
    public static final String DEEPLINK_MESSAGE_CENTER = "message_center";
    public static final String DEEPLINK_ACCOUNTABILITY_BUDDIES = "accountability_buddies";
    public static final String DEEPLINK_GOALS_VISION = "goals_vision";
    public static final String DEEPLINK_BUCKET_LIST = "bucket_list";
    public static final String DEEPLINK_MEDITATION = "meditation";
    public static final String DEEPLINK_GRATITUDE_LIST = "gratitude_list";
    public static final String DEEPLINK_PHOTOS = "photos";
    public static final String DEEPLINK_WEIGH_INS = "weigh_ins";
    public static final String DEEPLINK_GAMIFICATION_CENTRE = "gamification_centre";
    public static final String DEEPLINK_BOOTY_ABS = "booty_abs";
    public static final String DEEPLINK_CALENDAR = "calendar";
    public static final String DEEPLINK_SETTINGS = "settings";
    public static final String DEEPLINK_WORLD_FORUM = "WorldForum";
    public static final String DEEPLINK_CLEAN_UP_START = "clean_up_start";
    public static final String DEEPLINK_CLEAN_UP_SEMINAR = "clean_up_seminar";


    public static final String BUNDLE_KEY_MEAL_PREP = "meal_prep_object";
    public static final String BUNDLE_KEY_MEAL_PREP_UPDATE_FOR = "key_meal_prep_update_for";
    public static final String BUNDLE_KEY_MEAL_PREP_NAME = "key_food_prep_name";
    public static final String BUNDLE_KEY_WEEKLY_MEAL_PREP = "key_weekly_meal_prep_obj";

    public static final int BUNDLE_MEAL_PREP_ADD = 1;
    public static final int BUNDLE_MEAL_PREP_EDIT = 2;
    public static final int MEAL_PREP_UPDATE_BACK_REQUEST_CODE = 9090;

    /*Chat*/
    public static final String KEY_CHAT_LOGIN = "chat_initialize";
    public static final int CHAT_CLOSE_REQUEST = 9874;
    public static final String KEY_START_FRAGMENT = "start_fragment";
    public static final String FIREBASE_PASSWORD_APPEND = "#@%$&^*#";
    /*Chat*/

    public static String NUMBER_PICKER_MIN="min";
    public static String NUMBER_PICKER_MAX="max";
    public static final String KEY_MEAL_SEARCH_ANALYSIS = "analysis";
    public static final String KEY_MEAL_SEARCH_ANALYSIS_REQUEST = "analysis_req";

    public static final String KEY_TRACK_PHOTO_LIST = "track_photo_list";

    public static final String KEY_OPEN_SEARCH_WITH_VALUE = "open_search_with_value";

    public static final String QUOTE_NOTIFICATION_KEY = "quote__notification";

    public static synchronized Context getContext() {
        return getInstance().getApplicationContext();
    }

    public ShowDiffPageBroadcast getShowDiffPageBroadcast() {
        return showDiffPageBroadcast;
    }

    public DemoApplication setShowDiffPageBroadcast(ShowDiffPageBroadcast showDiffPageBroadcast) {
        this.showDiffPageBroadcast = showDiffPageBroadcast;
        return this;
    }

    public ShowDiffPageBroadcast showDiffPageBroadcast;
    IntentFilter intentFilter;

    private AppStateListener appStateListener;
    public interface AppStateListener{
        void onForeground();
        void onBackground();
    }

    public void setAppStateListener(AppStateListener appStateListener) {
        this.appStateListener = appStateListener;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        sharedPreference = new SharedPreference(getApplicationContext());

        Thread.setDefaultUncaughtExceptionHandler(new CustomExceptionHandler(getApplicationContext()));

        /*if (sharedPreference.read("FIRST_INSTALL_DATE", "").equals("")) {
            // get application installation time
            installedTime = getInstallTime();
            if (installedTime == null) {
                // if we could not get the app installed time, we take current date as the
                // installed date
                Calendar calendar = Calendar.getInstance();
                installedTime = calendar.getTime();
            }
            sharedPreference.write("FIRST_INSTALL_DATE", "", installDateFormat.format(installedTime));
        } else {
            // get application installation time
            try {
                installedTime = installDateFormat.parse(sharedPreference.read("FIRST_INSTALL_DATE", ""));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        System.out.println("INSTALLED_TIME==>" + installedTime);*/
       // AppEventsLogger.activateApp(this, BuildConfig.APPLICATION_ID);


        ///////////////////////////APPSFLYER INITIALIZATION//////////////////////////////////////////////

//        AppsFlyerLib.getInstance().startTracking(this, getApplicationContext().getString(R.string.app_fly_dev_key));
//        AppsFlyerLib.getInstance().trackAppLaunch(this, getApplicationContext().getString(R.string.app_fly_dev_key));

        ///////////////////////////////////////////////////////////////////////////////////////////////

        Fresco.initialize(getApplicationContext());
       // FacebookSdk.sdkInitialize(getApplicationContext());
        userAgent = Util.getUserAgent(this, "ExoPlayerDemo");
        mInstance = this;
//        AppVisibilityDetector.init(this, new AppVisibilityDetector.AppVisibilityCallback() {
//            @Override
//            public void onAppGotoForeground() {
//
//                Log.e("FOREGROUND", "FOREGROUND>>>>>>>>");
//                isForeGround = true;
//
//                if (appStateListener!=null)
//                    appStateListener.onForeground();
//
//
//            }
//
//            @Override
//            public void onAppGotoBackground() {
//                Log.e("BACKGROUND", "BACKGROUND>>>>>>>>");
//                isForeGround = false;
//
//                if (appStateListener!=null)
//                    appStateListener.onBackground();
//            }
//        });

//        Leanplum.setApplicationContext(this);
//        Parser.parseVariables(this);
//        //  For session lifecyle tracking.
//        LeanplumActivityHelper.enableLifecycleCallbacks(this);
//
//        // Insert your API keys here.
//        if (BuildConfig.DEBUG) {
//            Leanplum.setAppIdForDevelopmentMode("app_khulFjwzJrZU5djgHiIutLkj7tbx7NZYuTjrCnqOWsQ", "dev_Uvw7wCQ7rVAVBiSi8OrO2TDrw3VUpBFrNJ2QoJXWmmY");
//        } else {
//            Leanplum.setAppIdForProductionMode("app_khulFjwzJrZU5djgHiIutLkj7tbx7NZYuTjrCnqOWsQ", "prod_YeLxhRR710aRIkjJ9VItV0tEngzYgx7uDbddx6utDj4");
//        }

        // Enable push notifications.

        // Option 1: Google Cloud Messaging
        // For YOUR_SENDER_ID, there are 2 options:
        // 1. Use LeanplumPushService.LEANPLUM_SENDER_ID if you are not already
        //    using push notifications in your app.
        // 2. Use your GCM sender ID if you already have one. You'll also need to
        //    upload your API key to our dashboard.
        // It's important that the sender IDs you provide to us are consistent with the ones
        // you provide to other third party SDKs or to registration code you already use.
//        LeanplumPushService.setGcmSenderId("16508100785");
//
//        // Option 2: Firebase Cloud Messaging
//        // Be sure to upload your Server API key to our dashboard.
//        LeanplumPushService.enableFirebase();
//
//        // This will only run once per session, even if the activity is restarted.
//        Leanplum.start(this);

        //chat sdk
        /*Configuration.Builder builder = new Configuration.Builder(getApplicationContext());
        builder.firebase(new String("squad"), new String("AAAAA9f1oLE:APA91bEkDRas1DnbAp_nuawweogK23JunjDTJhE9qBD85isNXAZpzRLMbakW_EwQAetwzJWBhV2oPW_uKXD5YIu4X5oYLisTuB4vfVRzSBWY3wKZlGN7PEuQFq9zGtem8e2rk9MMOnvx"))
                //.googleMaps("AIzaSyAIkkfBDHILrb9IjqPf7PejQzeN4nXHuME")
                .googleMaps("AIzaSyC-fcP3xy_bTS8wWpQpbDcaEGme8ZAeW2g")
                .pushNotificationSound(new String("Notification.caf"))
                .groupsEnabled(true)
                .publicRoomCreationEnabled(false)
                .setShowLocalNotifications(true)
                .facebookLoginEnabled(true)
                .twitterLoginEnabled(false)
                .googleLoginEnabled(false)
                .setMessageTimeFormat(new String("dd MMM HH:mm"));*/

        //ChatSDK.initialize(builder.build());

        //UserInterfaceModule.activate(getApplicationContext());

        //FirebaseModule.activate();
        //FirebaseFileStorageModule.activate();
        /*FirebasePushModule.activateForFirebase();
        FirebaseSocialLoginModule.activate(getApplicationContext());
        search = new UserFirebaseSearchHandler();*/
    }

    /*public static UserSearchHandler search() {
        return search;
    }*/

    DataSource.Factory buildDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
        return new DefaultDataSourceFactory(this, bandwidthMeter, buildHttpDataSourceFactory(bandwidthMeter));
    }

    HttpDataSource.Factory buildHttpDataSourceFactory(DefaultBandwidthMeter bandwidthMeter) {
      //  return new DefaultHttpDataSourceFactory(userAgent, bandwidthMeter);
        return null;
    }

    public static synchronized DemoApplication getInstance() {
        return mInstance;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public Date getInstalledTime() {
        return installedTime;
    }

}
