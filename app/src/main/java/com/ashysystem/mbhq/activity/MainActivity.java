package com.ashysystem.mbhq.activity;

import static com.ashysystem.mbhq.util.Util.HELP_TYPE_8_WEEK;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_ACHIEVE;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_APPRECIATE;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_CONNECT;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_HELP;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_HOME;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_LEARN;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_NOURISH;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_SETTINGS;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_TRACK;
import static com.ashysystem.mbhq.util.Util.HELP_TYPE_TRAIN;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.UnderlineSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.BackgroundSoundServiceNew;
import com.ashysystem.mbhq.Service.OnClearFromRecentService;
import com.ashysystem.mbhq.Service.SongNotification;
import com.ashysystem.mbhq.Service.UploadService;
import com.ashysystem.mbhq.Service.impl.CommunityServiceImpl;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.Service.impl.Service;
import com.ashysystem.mbhq.Service.impl.ServiceSessionMain;
import com.ashysystem.mbhq.fragment.*;
import com.ashysystem.mbhq.fragment.bucket.BucketAddEditFragment;
import com.ashysystem.mbhq.fragment.bucket.BucketListFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitCalendarTickUntickFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabbitDetailsCalendarFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddEight;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddFirstPage;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddFive;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddFour;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddMainFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddNine;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddOne;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddSecondPage;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddSeven;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddSix;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddTen;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddThree;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerAddTwo;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerEditFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.HabitHackerListFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.PopularHabitsListFragment;
import com.ashysystem.mbhq.fragment.live_chat.LiveChatFragment;
import com.ashysystem.mbhq.fragment.meditation.MeditationDetails;
import com.ashysystem.mbhq.fragment.meditation.MeditationDetailsNew;
import com.ashysystem.mbhq.fragment.meditation.MeditationFragment;
import com.ashysystem.mbhq.listener.OnLoadFragmentRequestListener;
import com.ashysystem.mbhq.listener.UploadCallback;
import com.ashysystem.mbhq.listener.UploadDatabaseCallback;

import com.ashysystem.mbhq.model.AddUpdateGratitudeModel;
import com.ashysystem.mbhq.model.GetGratitudeListModelInner;
import com.ashysystem.mbhq.model.GetMeditationCacheExpiryTimeResponse;
import com.ashysystem.mbhq.model.GetPrompt;
import com.ashysystem.mbhq.model.GetStreakDataResponse;
import com.ashysystem.mbhq.model.GetTaskStatusForDateResponse;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.StreakData;
import com.ashysystem.mbhq.model.UpdateBadgeShownResponse;
import com.ashysystem.mbhq.model.eqfolder.Eqfolder;
import com.ashysystem.mbhq.model.eqfolder.UserEqFolder;
import com.ashysystem.mbhq.model.response.AddCourseResponseModel;
import com.ashysystem.mbhq.model.response.AddUpdateMyAchievementModel;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.MbhqDatabse;
import com.ashysystem.mbhq.roomDatabase.UploadDatabaseManager;
import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;
import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGratitude;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGrowth;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForMeditation;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.GrowthViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.MeditationViewModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.NetworkSchedulerService;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.ShowDiffPageBroadcast;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.video.DemoApplication;
import com.ashysystem.mbhq.view.EditTextOswaldRegular;
import com.edmodo.cropper.CropImageView;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import com.ashysystem.mbhq.Service.AudioService;

public class MainActivity extends AppCompatActivity implements OnLoadFragmentRequestListener, UploadCallback {
    public static final SparseArray<String> STRING_SPARSE_ARRAY = new SparseArray<>(Util.HELP_ARRAY.length);
    public static int currentVolume = -1;
    public static String APPSFLYER_ID = "";
    public static String APPSFLYER_DEV_KEY = "fvcWEuttagSYwiZqfz9mSP";
    public static int PICK_IMAGE_FROM_GALLERY_CODE = 778;
    public static int PICK_IMAGE_FROM_GALLERY_CODE_FROM_GRATITUDE_LIST = 782;
    public static int PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT = 780;
    public static int PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST = 783;
    public static int CAMERA_PIC_REQUEST_CODE = 779;
    public static int CAMERA_PIC_REQUEST_CODE_FROM_GRATITUDE_LIST = 784;
    public static int CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT = 781;
    public static int CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST = 785;
    private static MainActivity mainActivity = null;

    boolean f1=false;
    boolean f2=false;
    boolean f3=false;


    static {
        STRING_SPARSE_ARRAY.put(HELP_TYPE_HOME, "https://player.vimeo.com/external/220933773.m3u8?s=04d41e4e04fa8ce700db0f52f247acce968b72e5");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_SETTINGS, "https://player.vimeo.com/external/220933773.m3u8?s=04d41e4e04fa8ce700db0f52f247acce968b72e5");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_HELP, "https://player.vimeo.com/external/220933773.m3u8?s=04d41e4e04fa8ce700db0f52f247acce968b72e5");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_CONNECT, "https://player.vimeo.com/external/220932517.m3u8?s=dd3882bb52f88673c33ff600b24bfc55684011de");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_TRAIN, "https://player.vimeo.com/external/220932728.m3u8?s=5df94fedbc4655abb941380001975b0f4665586c");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_NOURISH, "https://player.vimeo.com/external/220933018.m3u8?s=ec878c58bd2e8675af4994335feb49789319b5b6");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_LEARN, "https://player.vimeo.com/external/220933065.m3u8?s=387a479d1133aee78893c610a7ef9654b82ac4b4");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_ACHIEVE, "https://player.vimeo.com/external/220933321.m3u8?s=dbe73990b8d048743040db27d4e8e29e78144aa3");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_APPRECIATE, "https://player.vimeo.com/external/220933321.m3u8?s=dbe73990b8d048743040db27d4e8e29e78144aa3");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_TRACK, "https://player.vimeo.com/external/220933540.m3u8?s=ea345fe4a29d1c3409a4b3b8f381ced8f99f2870");
        STRING_SPARSE_ARRAY.put(HELP_TYPE_8_WEEK, "https://player.vimeo.com/external/234300535.m3u8?s=ce8a737e8d0fbae45fc51161df702079be8186e1");
    }

    //////////DAYABASE//////////////
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    public RelativeLayout rlGratitude, rlMeditation, rlToday, rlHabits, rlCourses, rlCoursesBackToMain, rlPersonalSetting, rlCustomProgramSetting, rlMusicSetting, rlCustomNutritionSetting;
    public LinearLayout llBottomMenu;
    public RelativeLayout rlDownloadedMeditations;

    public ImageButton imgForum;
    public Toolbar toolbar;
    public ImageView imgCircleBack;
    public Fragment currentFragmentTab;
    public ArrayList<Fragment> arrFragment = new ArrayList<>();
    public RelativeLayout rlLeftTab = null, rlRightTab = null;
    public ShowDiffPageBroadcast showDiffPageBroadcast;
    public AudioService musicSrv = null;
    /*commentout by sahenita*/
   // public SpotifySelectSongGoalFragment spotifySelectSongGoalFragment;
    public boolean restrictModeTrain = false;
    HashMap<Integer, String> hashMapHabbit = new HashMap<>();
    String TAG = "MainActivity";
    int SlowDownThreshold = 300;
    boolean currentlyTouching = false;
    boolean currentlyScrolling = false;
    ImageView imgGratitude, imgMeditation, imgToday, imgHabits, imgCourses;
    ImageView imgLogo;
    ImageView imgLeftBack;
    ImageView imgRightBack;
    ImageView imgFilter;
    FrameLayout frameNotification;
    TextView txtPageHeader, txtExistingUserLogin;

  //  LinearLayout llGame;
    /*commentout by sahenita*/
    // FinisherSingle finisherSingle;
    String selectedEventTypeId;
    String selectedPresenterId;
    String selectedDate;
    String selectedTags;
    String Year;
    int Count;
    SharedPreference sharedPreference;
    Fragment currentFragment;
    String strGlobalPromotionArray = "";
    SimpleDateFormat globalFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat globalFormatWithT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat globalFormatWithOutT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    RelativeLayout rlCommunityCircle, rlNotificationCircle;
    TextView txtCommunityCounter, txtNotificationCounter;
    IntentFilter intentFilterAnother;
    DrawerLayout drawer;
    LinearLayout llTool;
    MediaSessionCompat mediaSession;
    SongNotification songNotification = null;
    NotificationUpdate notificationUpdate;
    NavigationView navigationView;
    ArrayList<PromotionalChallengeInfo> globalLstPromotionalInfo;
    boolean boolExerciseNutritionSettings = false;
    RelativeLayout rlChatCount;
    JobScheduler jobScheduler;
    int MYJOBID = 1;
    ViewModelFactoryForGratitude factoryForGratitude;
    GratitudeViewModel gratitudeViewModel;
    ViewModelFactoryForGrowth factoryForGrowth;
    GrowthViewModel growthViewModel;
    ViewModelFactoryForMeditation factoryForMeditation;
    MeditationViewModel meditationViewModel;
    ServiceConnection musicConnection;
    ////////////FOR GRATITUDE CREW AND SHARABILITY////////
    Integer SHARE_ONLY_READ_WRITE_CAMERA_PERMISSION_REQUEST_CODE = 777;
    RelativeLayout globalRlonlyShareUsinTemplate;
    File out;
    ImageView imgBackgroundPic;
    CardView cardViewBackgroundPic;
    ImageView imgBackgroundMannyPic;
    RelativeLayout rlPicSection, rlRewardCircle;
    LinearLayout llAddPic;
    String stringImg = "";
    Boolean gratitudeCrewDialogShownOnceBool = false;
    Boolean gratitudeCrewSharableBool = false;
    Button btnRewardCount;
    ArrayList<UserEqFolder> mymodelArrayList = new ArrayList<>();


    LinearLayout dialogLayout;

    ///////////////////////////  CHAT ////////////////////////////////
    Handler mCountHandler = new Handler();
    Runnable mCountRunnable = new Runnable() {
        @Override
        public void run() {

            mCountHandler.postDelayed(this, 500);
        }
    };
    Handler mChatLoginDetect = new Handler();
    Runnable mChatLogin = new Runnable() {
        @Override
        public void run() {

        }
    };
    BroadcastReceiver meditationDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = "";
            if (intent != null) {
                if (intent.hasExtra("DOWNLOAD")) {
                    msg = intent.getStringExtra("DOWNLOAD");

                    Gson gson = new Gson();
                    MeditationCourseModel.Webinar webinarMeditationModel = gson.fromJson(msg, MeditationCourseModel.Webinar.class);
                    if (webinarMeditationModel != null) {
                        factoryForMeditation = Injection.provideViewModelFactoryMeditation(MainActivity.this);
                        /*commented by sahenita*/
                        //meditationViewModel = ViewModelProviders.of(MainActivity.this, factoryForMeditation).get(MeditationViewModel.class);
                         meditationViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(MeditationViewModel.class);

                        Log.e("EVENT_ITEM_ID", webinarMeditationModel.getEventItemID() + ">>>>>>>>>");

                        MeditationEntity meditationEntity = new MeditationEntity(webinarMeditationModel.getEventItemID(), msg, true, false);
                        if (mDisposable != null) {
                            Log.e("DISPOSABLE", "NOT_NULL");
                        } else {
                            Log.e("DISPOSABLE", "NULL");
                        }
                        if (meditationViewModel != null) {
                            Log.e("meditationViewModel", "NOT_NULL");
                        } else {
                            Log.e("meditationViewModelaqb", "NULL");
                        }
                        mDisposable.add(meditationViewModel.updateMeditation(meditationEntity)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(() -> {
                                    Log.i(this.getClass().getSimpleName(), "MEDITATION_INSERT TRUE");
                                }, throwable -> {
                                    Log.e(this.getClass().getSimpleName(), "MEDITATION_INSERT FALSE");
                                }));

                    }
                }
            }
        }
    };
    private Dialog progressDialogGlobal;
    private RelativeLayout leftDot, rightDot;
    private TextView txtGamificationCount;
    private String gamificationString = "0";
    private String targetFragment = "";
    private String selectedTag = "";
    private String categoryTag = "";
    private String selectedLeaderboardType = "";
    private List<String> permissionsNeeded;
    private boolean popupGameShown = false;
    private int previousVal = -1;
    private FrameLayout messageNotification;
    private TextView txtChatCount;
    /*commented by sahenita*/
   // private MusicUtils.ServiceToken globalMusicServiceToken = null;
    private float x1;
    private float x2;
    private String lastFragmentTag = "";
    private String FLOWTYPE = "";
    private int currentX = 0;
    private int currentY = 0;
    private int totalScroll = 0;
    private IntentFilter intentFilter, intentFilter1;
    private ProgressDialog progressDialog, progressDialogChat;
    private Integer programStepNumber;
    private FinisherServiceImpl finisherService;
    private TextView txtGamificationCountPopUp,txt_prompt;
    /*commented by sahenita*/
//    BillingClient billingClient;
    TouchTypeDetector.TouchTypListener touchTypListener = new TouchTypeDetector.TouchTypListener() {
        @Override
        public void onTwoFingerSingleTap() {
            // Two fingers single tap
        }

        @Override
        public void onThreeFingerSingleTap() {
            // Three fingers single tap
        }

        @Override
        public void onDoubleTap() {
            // Double tap
        }

        @Override
        public void onScroll(int scrollDirection) {
            switch (scrollDirection) {
                case TouchTypeDetector.SCROLL_DIR_UP:
                    Log.e("Up scroll", "up");
                    // Scrolling Up
                    break;
                case TouchTypeDetector.SCROLL_DIR_DOWN:
                    Log.e("Down scroll", "down");
                    // Scrolling Down
                    break;
                case TouchTypeDetector.SCROLL_DIR_LEFT:
                    // Scrolling Left
                    Log.e("Left scroll", "left");
                    break;
                case TouchTypeDetector.SCROLL_DIR_RIGHT:
                    // Scrolling Right
                    Log.e("Right scroll", "right");
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        @Override
        public void onSingleTap() {
            // Single tap
            Log.e("press single", "tap");



        }

        @Override
        public void onSwipe(int swipeDirection) {
            switch (swipeDirection) {
                case TouchTypeDetector.SWIPE_DIR_UP:
                    // Swipe Up
                    Log.e("UP", "up");
                    break;
                case TouchTypeDetector.SWIPE_DIR_DOWN:
                    // Swipe Down
                    Log.e("DOWN", "down");
                    break;
                case TouchTypeDetector.SWIPE_DIR_LEFT:
                    // Swipe Left
                    Log.e("print state--,", currentX + "???");

                    if (Util.checkConnection(MainActivity.this) && !new SharedPreference(MainActivity.this).read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                        if (currentFragmentTab instanceof MbhqTodayOneFragment || currentFragmentTab instanceof GratitudeMyListFragment || currentFragmentTab instanceof MbhqGratitudeAddEditFragment) {
                            fungratitudeicon();
                            new SharedPreference(MainActivity.this).write("appreciate_nav_pos", "", 1 + "");
                            loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                        } else if (currentFragmentTab instanceof MyAchievementsFragment || currentFragmentTab instanceof MyAchievementsListAddEditFragment) {
                            if (Util.boolBackGroundServiceRunningMeditation && !Util.strMeditationDetailsForBackground.equals("")) {
                                Log.e("UTILVALUEEEEEE", Util.strMeditationDetailsForBackground + ">>>>>");
                                clearCacheForParticularFragment(new MeditationDetails());
                                MeditationDetails meditationDetails = new MeditationDetails();
                                Bundle bundle = new Bundle();
                                bundle.putString("data", Util.strMeditationDetailsForBackground);
                                meditationDetails.setArguments(bundle);
                                loadFragment(meditationDetails, "MeditationDetails", null);
                            } else {
                                rlMeditation.performClick();
                            }
                        } else if (currentFragmentTab instanceof MeditationFragment || currentFragmentTab instanceof MeditationDetails) {
                            new SharedPreference(MainActivity.this).write("achieve_nav_pos", "", 0 + "");
                            Log.i("unique","20");

                            Log.i("Landing1111111111111","0");
                            loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
                            imgHabits.setImageResource(0);
                            imgHabits.setImageResource(R.drawable.mbhq_habits_active);
                            imgMeditation.setImageResource(0);
                            imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
                        } else if (currentFragmentTab instanceof MbhqTodayTwoFragment || currentFragmentTab instanceof HabitHackerListFragment || currentFragmentTab instanceof HabitHackerEditFragment || currentFragmentTab instanceof HabbitDetailsCalendarFragment || currentFragmentTab instanceof HabbitCalendarTickUntickFragment) {
                            new SharedPreference(MainActivity.this).write("achieve_nav_pos", "", 1 + "");
                            loadFragment(new BucketListFragment(), "BucketList", null);
                        } else if (currentFragmentTab instanceof BucketListFragment || currentFragmentTab instanceof BucketAddEditFragment) {
                            new SharedPreference(MainActivity.this).write("learn_nav_pos", "", 0 + "");
                            loadFragment(new CourseFragment(), "Course", null);
                        } else if (currentFragmentTab instanceof QuestionariesFragment || currentFragmentTab instanceof QuestionResultFragment || currentFragmentTab instanceof CohenQuestion) {

                            Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else if (currentFragmentTab instanceof CourseFragment || currentFragmentTab instanceof ProgramDetailsFragment || currentFragmentTab instanceof CourseDetailsFragment) {
                            new SharedPreference(MainActivity.this).write("learn_nav_pos", "", 1 + "");
                            funDrawer1();
                            loadFragment(new QuestionariesFragment(), "Questionaries", null);
                        }
                    }

                    Log.e("Left", "left");
                    break;

                case TouchTypeDetector.SWIPE_DIR_RIGHT:
                    // Swipe Right

                    if (Util.checkConnection(MainActivity.this) && !new SharedPreference(MainActivity.this).read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {

                        if (currentFragmentTab instanceof MyAchievementsFragment || currentFragmentTab instanceof MyAchievementsListAddEditFragment) {
                            new SharedPreference(MainActivity.this).write("appreciate_nav_pos", "", 0 + "");
                            Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        } else if (currentFragmentTab instanceof MeditationFragment || currentFragmentTab instanceof MeditationDetails) {
                            fungratitudeicon();
                            new SharedPreference(MainActivity.this).write("appreciate_nav_pos", "", 0 + "");
                            loadFragment(new MyAchievementsFragment(), "MyAchievements", null);

                        } else if (currentFragmentTab instanceof MbhqTodayTwoFragment || currentFragmentTab instanceof HabitHackerListFragment || currentFragmentTab instanceof HabitHackerEditFragment || currentFragmentTab instanceof HabbitDetailsCalendarFragment || currentFragmentTab instanceof HabbitCalendarTickUntickFragment) {
                            rlMeditation.performClick();

                        } else if (currentFragmentTab instanceof BucketListFragment || currentFragmentTab instanceof BucketAddEditFragment) {
                            new SharedPreference(MainActivity.this).write("achieve_nav_pos", "", 0 + "");
                            Log.i("unique","20");
                            Log.i("Landing1111111111111","1");
                            loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);


                        } else if (currentFragmentTab instanceof QuestionariesFragment || currentFragmentTab instanceof QuestionResultFragment || currentFragmentTab instanceof CohenQuestion) {
                            new SharedPreference(MainActivity.this).write("learn_nav_pos", "", 0 + "");
                            loadFragment(new CourseFragment(), "Course", null);
                        } else if (currentFragmentTab instanceof CourseFragment || currentFragmentTab instanceof ProgramDetailsFragment || currentFragmentTab instanceof CourseDetailsFragment) {
                            new SharedPreference(MainActivity.this).write("achieve_nav_pos", "", 1 + "");
                            loadFragment(new BucketListFragment(), "BucketList", null);
                        }
                    }
                    Log.e("Right", "right");

                    break;
                default:
                    //do nothing
                    break;
            }
        }

        @Override
        public void onLongPress() {
            // Long press
        }


    };
    BroadcastReceiver broadCastNewMessage = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String msg = "";
            if (intent != null) {
                msg = intent.getStringExtra("CONNECT");

                Log.e("HELLLOOOOOOOOOO", ">>>>>>>>>>>");

                if (msg.equals("TRUE") && !Util.isNetworkAvailable) {
                    Log.e("INSIDE_BROADCAST", "TRUE");
                    Util.isNetworkAvailable = true;
                    llBottomMenu.setVisibility(View.VISIBLE);
                    rlDownloadedMeditations.setVisibility(View.GONE);

                    ///CLEAR ALL FRAGMENTS IN CACHE
                    if (arrFragment != null && arrFragment.size() > 0) {
                        arrFragment.clear();
                    }

                    ////////UPLOAD LOCAL GRATITUDE AND GROWTH//////////
                    if (!Util.isLocalUploadGrowthGratitideCalled && Util.checkConnection(MainActivity.this)) {
                       // functionToUploadLocallySavedGratitudeAndGrowth();
                    }

                } else {
                    Log.e("INSIDE_BROADCAST", "FALSE");
                    Util.isNetworkAvailable = false;
                    Util.isLocalUploadGrowthGratitideCalled = false;
                    llBottomMenu.setVisibility(View.GONE);
                    rlDownloadedMeditations.setVisibility(View.VISIBLE);
                    if (Util.checkConnection(MainActivity.this)) {
                        llBottomMenu.setVisibility(View.VISIBLE);
                        rlDownloadedMeditations.setVisibility(View.GONE);

                    } else {
                        llBottomMenu.setVisibility(View.GONE);
                        rlDownloadedMeditations.setVisibility(View.VISIBLE);
                        clearCacheForParticularFragment(new MbhqTodayMainFragment());
                        Log.i("called landind","2");
                        loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                    }
                }
            }
        }
    };
    private LinearLayout llSettingsBottomMenu;
    private NotificationManager mNotificationManager;
    /*commentout by sahenita*/
//    private Player spotifyPlayer = null;
//    private Player.OperationCallback mOperationCallback = null;
//    private Player spotifyGOALPlayer = null;
//
//    //////////////////////////////////////////////
//    private Player.OperationCallback mOperationCallbackGOAL = null;
    /*commentout by sahenita*/
  //  private ConnectivityReceiver mConnectivityReceiver;
    /*commentout by sahenita*/
   // private CustomHandler handler;
    private UploadDatabaseManager uploadDatabaseManager;
    private String imgDecodableString = "";

    private BackgroundSoundServiceNew musicSrvce = null;
    private AudioManager audioManager;
    private BroadcastReceiver audioFocusReceiver;
    /////////////////////////////////////////////////
    private String imgPath = "";
    private File mFile;
    /////////crop img/////
    private CropImageView cropImageView;
    private Bitmap onLineBitMap = null;
    private ImageView imgContainer;
    private Bitmap bitimage;
    ActionBarDrawerToggle toggle;
    private BroadcastReceiver refreshFCMTokenReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent == null)
                return;
            String action = intent.getAction();
            if (Util.INTENT_ACTION_TOKEN_REFRESHED.equals(action) && intent.hasExtra(Util.KEY_FCM_TOKEN)) {
                String token = intent.getStringExtra(Util.KEY_FCM_TOKEN);
                if (!new SharedPreference(MainActivity.this).read("gcm", "strgcm").equals("1") && !new SharedPreference(MainActivity.this).read("USEREMAIL", "").equals("")) {
                   // sendRegistrationIdToServer(token);//commented by jyotirmoy
                }
            }
        }
    };
    /*commentout by sahenita*/
/*
    private retrofit.Callback accessTokenRequestListener = new retrofit.Callback<OauthAccessTokenResponse>() {
        @Override
        public void success(OauthAccessTokenResponse result, retrofit.client.Response response) {

            if (result.access_token != null) {

                */
/*commentout by sahenita*//*

               // addFitbitDevice(result.access_token, 3);

            } else {
            }
        }

        @Override
        public void failure(RetrofitError retrofitError) {
            //Log.e("JAWBONE", "failed to get accessToken:" + retrofitError.getMessage());
        }
    };
*/


    ///////////////////////////////////FOR RESTRICTING USER TO EXIT SETTINGS PAGE//////////////////
    private OnDialogCloseListener onDialogCloseListener;
    private OnApiResponseListener onApiResponseListener;
    private String day="";
    private String day1="";
    public static MainActivity getInstance() {
        if (mainActivity == null)
            mainActivity = new MainActivity();

        return mainActivity;
    }

    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) {
        // BEST QUALITY MATCH

        //First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize, Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float) height / (float) reqHeight);
        }
        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float) width / (float) reqWidth);
        }

        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public void dismissProgressDialogGlobal() {
        if (this.progressDialogGlobal != null) {
            this.progressDialogGlobal.dismiss();
        }
    }

    public void setProgressDialogGlobal() {
        this.progressDialogGlobal = ProgressDialog.show(MainActivity.this, "", "Please wait...");
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    public HashMap<Integer, String> getHashMapHabbit() {
        return hashMapHabbit;
    }

    public void setHashMapHabbit(int id, String data) {
        this.hashMapHabbit.put(id, data);
    }

    public void clearHashMapHabbit(int id) {
        this.hashMapHabbit.remove(id);
    }
/*commentout by sahenita*/
  /*  public FirebaseAnalytics getmFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }*/

    public ShowDiffPageBroadcast getShowDiffPageBroadcast() {
        return showDiffPageBroadcast;
    }

    public MainActivity setShowDiffPageBroadcast(ShowDiffPageBroadcast showDiffPageBroadcast) {
        this.showDiffPageBroadcast = showDiffPageBroadcast;
        return this;
    }

    public DrawerLayout getDrawer() {
        return drawer;
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }

    public AudioService getMusicSrv() {
        return musicSrv;
    }

    public void setMusicSrv(AudioService musicSrv) {
        this.musicSrv = musicSrv;
    }

    public LinearLayout getToolTipView() {
        return llTool;
    }

    //////////////////////////////////

    public void setToolTipView(LinearLayout llTool) {
        this.llTool = llTool;
    }

    public Fragment getCurrentFragment() {
        return currentFragment;
    }

    public String getSelectedEventTypeId() {
        return selectedEventTypeId;
    }

    public void setSelectedEventTypeId(String selectedEventTypeId) {
        this.selectedEventTypeId = selectedEventTypeId;
    }

    public String getSelectedPresenterId() {
        return selectedPresenterId;
    }

    public void setSelectedPresenterId(String selectedPresenterId) {
        this.selectedPresenterId = selectedPresenterId;
    }

    public String getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
    }

    public String getSelectedTags() {
        return selectedTags;
    }

    public void setSelectedTags(String selectedTags) {
        this.selectedTags = selectedTags;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String year) {
        Year = year;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public String getTargetFragment() {
        return targetFragment;
    }

    public void setTargetFragment(String targetFragment) {
        this.targetFragment = targetFragment;
    }

    public String getSelectedTag() {
        return selectedTag;
    }

    public void setSelectedTag(String selectedTag) {
        this.selectedTag = selectedTag;
    }

    public String getCategoryTag() {
        return categoryTag;
    }

    public void setCategoryTag(String categoryTag) {
        this.categoryTag = categoryTag;
    }
    /*commentout by sahenita*/
  /*  public FinisherSingle getFinisherSingle() {
        return finisherSingle;
    }

    public void setFinisherSingle(FinisherSingle finisherSingle) {
        this.finisherSingle = finisherSingle;
    }*/

    public String getSelectedLeaderboardType() {
        return selectedLeaderboardType;
    }

    public void setSelectedLeaderboardType(String selectedLeaderboardType) {
        this.selectedLeaderboardType = selectedLeaderboardType;
    }

    public LinearLayout getLlBottomMenu() {
        return llBottomMenu;
    }

    public void setLlBottomMenu(LinearLayout llBottomMenu) {
        this.llBottomMenu = llBottomMenu;
    }

    public LinearLayout getLlSettingsBottomMenu() {
        return llSettingsBottomMenu;
    }

    public void setLlSettingsBottomMenu(LinearLayout llSettingsBottomMenu) {
        this.llSettingsBottomMenu = llSettingsBottomMenu;
    }

    public SongNotification getSongNotification() {
        return songNotification;
    }

    public void setSongNotification(SongNotification songNotification) {
        this.songNotification = songNotification;
    }

    public void setNotificationUpdate(NotificationUpdate notificationUpdate) {
        this.notificationUpdate = notificationUpdate;
    }

    public boolean isBoolExerciseNutritionSettings() {
        return boolExerciseNutritionSettings;
    }

    public MainActivity setBoolExerciseNutritionSettings(boolean boolExerciseNutritionSettings) {
        this.boolExerciseNutritionSettings = boolExerciseNutritionSettings;
        return this;
    }

    public boolean isRestrictModeTrain() {
        return restrictModeTrain;
    }

    public void setRestrictModeTrain(boolean restrictModeTrain) {
        this.restrictModeTrain = restrictModeTrain;
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        if (restrictModeTrain)
            sharedPreference.write("restricttrain", "", "true");
        else
            sharedPreference.write("restricttrain", "", "false");

    }

    public ServiceConnection getMusicConnection() {
        return musicConnection;
    }

    public void setMusicConnection(ServiceConnection musicConnection) {
        this.musicConnection = musicConnection;
    }

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        registerReceiver(broadcastReceiver, new IntentFilter("MediaNotification"));
        startService(new Intent(getBaseContext(), OnClearFromRecentService.class));

        ////////////////////////////////////////////////////////////////////

        Intent intent = new Intent(this, BackgroundSoundServiceNew.class);
        this.startService(intent);
        this.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                BackgroundSoundServiceNew.ServiceBinder binder = (BackgroundSoundServiceNew.ServiceBinder) service;
                musicSrvce = binder.getService();



            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("MainActivity", "service disconnected");
            }
        }, Context.BIND_AUTO_CREATE);



        savedInstanceState = null;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        finisherService = new FinisherServiceImpl(MainActivity.this);
        sharedPreference = new SharedPreference(MainActivity.this);
        sharedPreference.write("WELCOMECHECKONE", "", "true");
        sharedPreference.write("WELCOMECHECKTWO", "", "true");
        sharedPreference.write("WELCOMECHECKTHREE", "", "true");
        sharedPreference.write("WELCOMECHECKFOUR", "", "true");
        sharedPreference.write("compChk", "", "true");
        sharedPreference.write("restricttrain", "", "false");

        /*commentout by sahenita*/
        /*if (!sharedPreference.read("dbquote", "").equals("true")) {
            new DbAsynTask().execute();
        }*/


        initView();
        /*commentout by sahenita*/
       // funGame();
        funDrawer1();
        /*commentout by sahenita*/
       // funForcancel7dayTrial();
        funBottomMenu();
        /*commentout by sahenita*/
      //  funNotification();
        funChat();
        funRatingBar();
        funPomotion();
        GetUnreadCount();

        funSensey();
        scheduleJob();
        scheduleUploadJob();

        funMenu();
        uploadDatabaseManager = UploadDatabaseManager.getInstance(getApplicationContext());

        /*commentout by sahenita*/
       /* if (getIntent() != null && getIntent().hasExtra("FROM_LOGIN") && !new SharedPreference(MainActivity.this).read("FIREBASE_TOKEN", "").equals("")) {
            performCommunityLogIn(null);
        }*/
/*commentout by sahenita*/
       /* Handler handler = new Handler();
        int delay = 30000; //milliseconds

        handler.postDelayed(new Runnable() {
            public void run() {
                //do something
                performCommunityCounterApiCall();
                handler.postDelayed(this, delay);
            }
        }, delay);*/

    }

    @Override
    protected void onNewIntent(Intent intent) {
        Log.i(TAG, "onNewIntent");
        super.onNewIntent(intent);

        Log.i(TAG, "onNewIntent");
        funMenu();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Intent intent = new Intent(this, BackgroundSoundServiceNew.class);
        this.startService(intent);
        this.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                BackgroundSoundServiceNew.ServiceBinder binder = (BackgroundSoundServiceNew.ServiceBinder) service;
                if (binder.getService() != null && binder.getService().isMediaPlaying()) {
                    Log.i("MainActivity", "service connected");
                    rlMeditation.performClick();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.i("MainActivity", "service disconnected");
            }
        }, Context.BIND_AUTO_CREATE);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Log.i("MainActivity", "onRestoreInstanceState, persistentState");
    }

    private void performCommunityCounterApiCall() {

        if (!new SharedPreference(MainActivity.this).read("COMMUNITY_ACCESS_TOKEN", "").equals("")) {
            CommunityServiceImpl communityService = new CommunityServiceImpl(MainActivity.this);
            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

            Util.COMMUNITY_ACCESS_TOKEN = sharedPreference.read("COMMUNITY_ACCESS_TOKEN", "");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("fetch", "notifications,friend_requests,count_new_messages");
            hashReq.put("server_key", Util.COMMUNITY_SERVERKEY);

            Call<JsonObject> jsonObjectCall = communityService.communityNotificationCount(hashReq, sharedPreference.read("COMMUNITY_ACCESS_TOKEN", ""));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override

                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null) {
                        Integer notificationCount = 0;
                        if (response.body().has("new_notifications_count") && response.body().get("new_notifications_count").getAsInt() > 0) {
                            notificationCount = notificationCount + response.body().get("new_notifications_count").getAsInt();
                        }
                        if (response.body().has("count_new_messages") && response.body().get("count_new_messages").getAsInt() > 0) {
                            notificationCount = notificationCount + response.body().get("count_new_messages").getAsInt();
                        }
                        if (response.body().has("new_friend_requests_count") && response.body().get("new_friend_requests_count").getAsInt() > 0) {
                            notificationCount = notificationCount + response.body().get("new_friend_requests_count").getAsInt();
                        }
                        if (notificationCount > 0) {

                        } else {
                            rlNotificationCircle.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }

    }
/*commentout by sahenita*/
/*
    private void performCommunityLogIn(CommunityLoginCallBack callback) {

        CommunityServiceImpl communityService = new CommunityServiceImpl(MainActivity.this);
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

        HashMap<String, Object> hashReq = new HashMap<>();
        hashReq.put("username", sharedPreference.read("USEREMAIL", ""));
        hashReq.put("password", "forumuser@" + Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("squad_user_id", Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("server_key", Util.COMMUNITY_SERVERKEY);
        hashReq.put("squad_access_token", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
        hashReq.put("android_m_device_id", sharedPreference.read("FIREBASE_TOKEN", ""));

        Call<JsonObject> jsonObjectCall = communityService.communityLoginApi(hashReq);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("SUCCESS", response.toString() + ">>>>>>>>>>");
                if (response.body() != null) {
                    if (response.body().has("errors")) {
                        performCommunityCreateUser(callback);
                    } else {
                        if (response.body().has("location") && !response.body().get("location").getAsString().equals("")) {
                            if (response.body().has("access_token") && !response.body().get("access_token").getAsString().equals("")) {
                                sharedPreference.write("COMMUNITY_ACCESS_TOKEN", "", response.body().get("access_token").getAsString());
                            }
                            sharedPreference.write("COMMUNITY_URL", "", response.body().get("location").getAsString());

                            if (callback != null) {
                                callback.onCommunityLoginSuccess();
                            }

                            performUpdateUserDataApiCall();

                        } else {
                            performCommunityCreateUser(callback);
                        }
                    }
                } else {
                    if (callback != null) {
                        callback.onCommunityLoginFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("FALIURE", t.toString() + ">>>>>>>>>>");
                if (callback != null) {
                    callback.onCommunityLoginFailed();
                }
            }
        });

    }
*/
/*commentout by sahenita*/
/*
    private void performUpdateUserDataApiCall() {

        CommunityServiceImpl communityService = new CommunityServiceImpl(MainActivity.this);
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

        HashMap<String, Object> hashReq = new HashMap<>();
        hashReq.put("first_name", sharedPreference.read("firstName", ""));
        hashReq.put("last_name", sharedPreference.read("lastName", ""));
        hashReq.put("server_key", Util.COMMUNITY_SERVERKEY);

        Call<JsonObject> jsonObjectCall = communityService.communityUpdateUserApi(hashReq, sharedPreference.read("COMMUNITY_ACCESS_TOKEN", ""));
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    Log.e("SUCCESS_UPDATE_USER", response.body().toString() + ">>>>>>>>>>");
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }
*/
/*commentout by sahenita*/
/*
    private void performCommunityCreateUser(CommunityLoginCallBack callback) {

        CommunityServiceImpl communityService = new CommunityServiceImpl(MainActivity.this);
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

        HashMap<String, Object> hashReq = new HashMap<>();
        hashReq.put("username", sharedPreference.read("firstName", "") + Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("password", "forumuser@" + Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("squad_user_id", Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("server_key", Util.COMMUNITY_SERVERKEY);
        hashReq.put("squad_access_token", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
        hashReq.put("confirm_password", "forumuser@" + Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("email", sharedPreference.read("USEREMAIL", ""));

        Call<JsonObject> jsonObjectCall = communityService.createAccountCommunityApi(hashReq);
        jsonObjectCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("SUCCESS_CREATE", response.toString() + ">>>>>>>>>>");
                if (response.body() != null) {
                    if (response.body().has("errors")) {
                        Log.e("CREATE_COMMUNITY_USER", "FAILED>>>>>>");
                        if (callback != null) {
                            callback.onCommunityLoginFailed();
                        }
                    } else {
                        */
/*commentout by sahenita*//*

                       // performCommunityLogIn(callback);
                    }
                } else {
                    if (callback != null) {
                        callback.onCommunityLoginFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("FALIURE_CREATE", t.toString() + ">>>>>>>>>>");
                if (callback != null) {
                    callback.onCommunityLoginFailed();
                }
            }
        });

    }
*/

    private void funSensey() {
        Sensey.getInstance().init(this);
        Sensey.getInstance().startTouchTypeDetection(this, touchTypListener);
    }


    public void  funDrawer1() {
        Log.i("gamificationString","10");
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                toolbar.setNavigationIcon(R.drawable.logo1);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // Do whatever you want here
                toolbar.setNavigationIcon(R.drawable.logo1);
            }
        };
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        drawer.addDrawerListener(toggle);
        toolbar.setNavigationIcon(R.drawable.logo1);

        LinearLayout menu_button_stack = findViewById(R.id.menu_button1);
        txtGamificationCountPopUp= findViewById(R.id.txtGamificationCountPopUp1);
        LinearLayout menu_test = findViewById(R.id.menu_test);
        LinearLayout menu_itemtharapiest=findViewById(R.id.menu_itemtharapiest);
        LinearLayout menu_item5_livechats = findViewById(R.id.menu_item5);
        LinearLayout menu_forum = findViewById(R.id.menu_forum);
        LinearLayout menu_bucketlist = findViewById(R.id.menu_bucketlist);

        LinearLayout menu_item2_help = findViewById(R.id.menu_item2);
        LinearLayout menu_item4_coach = findViewById(R.id.menu_item4);
        LinearLayout menu_item2_folders=findViewById(R.id.menu_item2_folders);
        LinearLayout menu_item6_contact = findViewById(R.id.menu_item6);
        TextView txtContactLink1 = findViewById(R.id.txtContactLink);
        txtContactLink1.setVisibility(View.GONE);
        LinearLayout ll_prompt= findViewById(R.id.ll_prompt);
        txt_prompt= findViewById(R.id.txt_prompt);
        LinearLayout menu_button2= findViewById(R.id.menu_button2);
        ll_prompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                Util.grateFul_popup_from_habit = false;
                Util.isReloadTodayMainPage = true;
                b.putString("openprompt", "yes");
                Util.prompt1=txt_prompt.getText().toString();
                fungratitudeicon();
                clearCacheForParticularFragment(new MyAchievementsFragment());
                loadFragment(new MyAchievementsFragment(), "MyAchievements", b);

            }
        });
        menu_button_stack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                    Bundle bun = new Bundle();
                    bun.putString("FOR_PAGE", "FORUM");
                    clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
                    loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", bun);
                } else {
                    if (Util.checkConnection(MainActivity.this)) {
                        if (restrictionBool()) {
                            funTrainRestriction();

                        } else {
                            if (isBoolExerciseNutritionSettings()) {
                                showDialogForNutritionExerciseSetting();
                            } else {
                                /*commentout by sahenita (temporary)*/
                               // funTabBarforReward(true);
                            }
                        }
                    } else {
                        Util.showToast(MainActivity.this, Util.networkMsg);
                    }
                }
            }
        });

        menu_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clearCacheForParticularFragment(new QuestionariesFragment());
                loadFragment(new QuestionariesFragment(),"Question",null);
            }
        });
        menu_item5_livechats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

            }
        });
        menu_itemtharapiest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.emotionalfitnessclub.com/therapy"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
        menu_forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

                String forum_access=sharedPreference.read("ForumAccess","");
                if("true".equalsIgnoreCase(forum_access)){
                    Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("User don't have access to use Forum") .setTitle("Efc");

                    //Setting message manually and performing action on button click
                    builder.setCancelable(false)

                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {



                                }
                            });

                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    alert.setCanceledOnTouchOutside(false);
                    //Setting the title manually
                    alert.show();
                }

            }
        });

        menu_bucketlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new BucketListFragment(), "BucketList", null);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                }, 200);
            }
        });
        menu_item2_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHelp();
            }
        });
        menu_item4_coach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callCoachApi();
            }
        });
        menu_item6_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtContactLink1.getVisibility() == View.VISIBLE) {
                    txtContactLink1.setVisibility(View.GONE);
                } else {
                    txtContactLink1.setVisibility(View.VISIBLE);
                }
            }
        });
        menu_item2_folders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.closeDrawer(GravityCompat.START);
                openDialogEqFolder();

            }
        });
        menu_button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.userid="";
                Util.session="";
                Util.withfilterlist_afterbackfrommeditationdetails.clear();
                Util.refresh_gratitude="yes";
                sharedPreference.clear("SAVED_HABIT_FILTER_VALUES");
                sharedPreference.clear("my_list");
                sharedPreference.clear("selectall_habit");
                sharedPreference.clear("click_option");
                sharedPreference.clear("habbitFirstTime_1");
                sharedPreference.clear("habbitFirstTime");

                sharedPreference.clear("accesstype");
                sharedPreference.clear("HabitAccess");
                sharedPreference.clear("EqJournalAccess");
                sharedPreference.clear("MeditationAccess");
                sharedPreference.clear("ForumAccess");
                sharedPreference.clear("LiveChatAccess");
                sharedPreference.clear("TestsAccess");
                sharedPreference.clear("CourseAccess");
                sharedPreference.write("HABIT_VIEW_HIGH", "", "");
                new MyAsyncTask1().execute();
                chatLogout();
                Util.clearSharedPref(MainActivity.this);
                MainActivity.this.mDisposable.add(
                        Completable.fromAction(() -> {
                                    Injection.provideMeditationDataSource(MainActivity.this).deleteAllMeditations();
                                }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> {
                                            Log.i(MainActivity.this.TAG, "meditations deleted");
                                        },
                                        throwable -> {
                                            Log.i(MainActivity.this.TAG, "error occured while deleting meditations");
                                            throwable.printStackTrace();
                                        }
                                )
                );
                Intent signUpIntent = new Intent(MainActivity.this, UserPaidStatusActivity.class);
                signUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


                ///clear daily popup
                SharedPreferences mPrefGratitude = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor mEditor = mPrefGratitude.edit();
                mEditor.clear();
                mEditor.apply();
                finish();

                //clear image show journal list
                SharedPreferences prefPic = getSharedPreferences("showpicture", MODE_PRIVATE);
                SharedPreferences.Editor EditPic = prefPic.edit();
                EditPic.clear();
                EditPic.apply();
                finish();


                startActivity(signUpIntent);
                finish();
            }
        });

    }

/*commentout by sahenita*/
/*
    private void funNotification() {
        if (getIntent() != null && getIntent().hasExtra(DemoApplication.REGISTER_NOTIFICATION_KEY)) {
            handlePostRegisterNotification(getIntent().getBundleExtra(DemoApplication.REGISTER_NOTIFICATION_KEY));
        } else if (getIntent() != null && getIntent().hasExtra(DemoApplication.TRIAL_NOTIFICATION_KEY)) {
            handelTrialNotificationNEW();
        } else if (getIntent() != null && getIntent().hasExtra(DemoApplication.FREE_WORKOUT_NOTIFICATION_KEY)) {
            handelFreeWorkoutNotification();
        }
    }
*/
/*commentout*/
/*
    private void funForcancel7dayTrial() {
        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
                txtExistingUserLogin.setVisibility(View.GONE);

            } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {
                txtExistingUserLogin.setVisibility(View.GONE);
            } else {
                txtExistingUserLogin.setVisibility(View.GONE);
            }
        } else {
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                txtExistingUserLogin.setVisibility(View.GONE);
            } else {
                txtExistingUserLogin.setVisibility(View.GONE);
            }

            cancelFree7dayTrialNotification(1);
            cancelFree7dayTrialNotification(11);
            cancelFree7dayTrialNotification(2);
            cancelFree7dayTrialNotification(22);
            cancelFree7dayTrialNotification(222);
            cancelFree7dayTrialNotification(3);
            cancelFree7dayTrialNotification(33);
            cancelFree7dayTrialNotification(333);
            cancelFree7dayTrialNotification(4);
            cancelFree7dayTrialNotification(5);
            cancelFree7dayTrialNotification(55);
            cancelFree7dayTrialNotification(555);
            cancelFree7dayTrialNotification(6);
            cancelFree7dayTrialNotification(66);
            cancelFree7dayTrialNotification(666);
            cancelFree7dayTrialNotification(7);
            cancelFree7dayTrialNotification(77);
            cancelFree7dayTrialNotification(777);
            cancelFree7dayTrialNotification(7777);
            cancelFree7dayTrialNotification(8);
            cancelFree7dayTrialNotification(88);
            cancelFree7dayTrialNotification(9);
            cancelFree7dayTrialNotification(14);
            cancelFree7dayTrialNotification(21);
        }
    }
*/
    /*commentout by sahenita*/
  /*  private void funGame() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            imgCircleBack.setImageResource(R.drawable.mbhq_circle);
            DrawableCompat.setTint(imgCircleBack.getDrawable(), ContextCompat.getColor(this, R.color.black));
        } else {
            int color = ContextCompat.getColor(MainActivity.this, R.color.black);
            Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(MainActivity.this, R.drawable.mbhq_circle));
            imgCircleBack.setImageDrawable(drawable);
            drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        }
        if (!getIntent().hasExtra("FITBITACCESSTOKEN") && !getIntent().hasExtra("GARMIN_OAUTHVERIFIER"))



            llGame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Util.checkConnection(MainActivity.this)) {
                        if (restrictionBool()) {
                            funTrainRestriction();

                        } else {
                            if (isBoolExerciseNutritionSettings()) {
                                showDialogForNutritionExerciseSetting();
                            } else {

                                *//*commentout by sahenita (temporary)*//*
                              //  funTabBarforReward(true);


                            }
                        }
                    } else {
                        Util.showToast(MainActivity.this, Util.networkMsg);
                    }
                }
            });
    }*/

    /*commentout by sahenita (temporary)*/
/*
    public void funTabBarforReward(boolean isGraditutePage) {
        if (isGraditutePage) {
            sharedPreference.write("learn_nav_pos", "", 0 + "");
            loadFragment(new RewardNewFragment(), "RewardNew", null);
        } else {
            sharedPreference.write("learn_nav_pos", "", 1 + "");
            loadFragment(new RewardMeditationFragment(), "RewardNew", null);
        }

        String[] arrTab = {"GRATITUDE", "MEDITATION"};
        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
        horTab.setVisibility(View.VISIBLE);
        LinearLayout llHorTab = findViewById(R.id.llHorTab);
        llHorTab.removeAllViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            horTab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    View view = (View) horTab.getChildAt(horTab.getChildCount() - 1);
                    int diff = (view.getRight() - (horTab.getWidth() + horTab.getScrollX()));
                    Log.e("print diff scrolld--", diff + "???");
                    if (scrollX == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if ((scrollX - oldScrollX) > 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if (diff == 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.GONE);
                    } else {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    }

                    if (Math.abs(scrollX - oldScrollX) > SlowDownThreshold) {
                        Log.e("SCROLL", "123");
                        Sensey.getInstance().stopTouchTypeDetection();
                    } else {
                        funSensey();
                        Log.e("SCROLL", "456");
                    }
                }
            });
        }
        ArrayList<TextView> arrTextView = new ArrayList<>();
        ArrayList<RelativeLayout> arrRelative = new ArrayList<>();
        for (int i = 0; i < arrTab.length; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_dynamic_tab, null);
            TextView textView = view.findViewById(R.id.txtTab);
            RelativeLayout rlMain = (RelativeLayout) view.findViewById(R.id.rlMain);
            textView.setId(i);
            textView.setText(arrTab[i]);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            rlMain.setLayoutParams(param);

            llHorTab.addView(view);
            int finalI = i;
            arrTextView.add(textView);
            arrRelative.add(rlMain);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTextSelection(arrTextView, arrRelative);

                    try {
                        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                        int x = arrRelative.get(finalI).getLeft() - 110;
                        horTab.post(new Runnable() {
                            @Override
                            public void run() {
                                horTab.smoothScrollTo(x, 0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (finalI == 0) {
                        arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                        arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);

                        loadFragment(new RewardNewFragment(), "RewardNew", null);
                        ///////////////////
                        sharedPreference.write("learn_nav", "", "program");
                        sharedPreference.write("learn_nav_pos", "", finalI + "");
                        rlLeftTab = null;
                        rlRightTab = arrRelative.get(1);

                    } else if (finalI == 1) {
                        arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                        arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);

                        loadFragment(new RewardMeditationFragment(), "RewardMeditation", null);

                        sharedPreference.write("learn_nav", "", "personal");
                        sharedPreference.write("learn_nav_pos", "", finalI + "");
                        rlLeftTab = arrRelative.get(0);
                        rlRightTab = null;

                    }
                }
            });
        }

        funTabselection("learn_nav_pos", arrRelative, arrTextView, llHorTab, horTab);

    }
*/

    private void funBottomMenu() {

        rlGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funDrawer1();
                Log.e("print_block","1");
                toolbar.setVisibility(View.VISIBLE);
                Util.isReloadTodayMainPage = true;
                sharedPreference.write("appreciate_nav", "", "gratitude");
                sharedPreference.write("appreciate_nav_pos", "", 0 + "");
                if (restrictionBool()) {
                    Log.e("print_block","2");
                    funTrainRestriction();
                } else {
                    Log.e("print_block","3");
                    sharedPreference = new SharedPreference(MainActivity.this);
                    if (sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                        Log.e("print_block","4");
                        Bundle bun = new Bundle();
                        bun.putString("FOR_PAGE", "GRATITUDE");
                        clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
                        loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", bun);
                    } else {
                        Log.e("print_block","5");
                        if (isTrialAndPaidUser()) {
                            Log.e("print_block","6");

                            if("yes".equalsIgnoreCase(Util.refresh_gratitude)){
                                refershGamePoint(MainActivity.this);
                                clearCacheForParticularFragment(new MyAchievementsFragment());
                                Util.isReloadTodayMainPage = true;
                                fungratitudeicon();
                                loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                            }

                        } else {

                            Log.e("print_block","7");

                            Bundle bundle = new Bundle();
                            bundle.putString("GRATITUDE_CLICK", "TRUE");
                            fungratitudeicon();
                            loadFragment(new MyAchievementsFragment(), "GratitudeMyList", bundle);
                        }
                    }
                }
            }
        });

        rlMeditation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                funDrawer1();
                if (Util.boolBackGroundServiceRunningMeditation && !Util.strMeditationDetailsForBackground.equals("")) {
                    Log.e("UTILVALUEEEEEE", Util.strMeditationDetailsForBackground + ">>>>>");
                    clearCacheForParticularFragment(new MeditationDetailsNew());
                    MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
                    Bundle bundle = new Bundle();
                    bundle.putString("data", Util.strMeditationDetailsForBackground);
                    meditationDetails.setArguments(bundle);
                    loadFragment(meditationDetails, "MeditationDetailsNew", null);
                } else {


                    Util.refresh_meditation="yes";
                    Util.refresh_gratitude="yes";
                    toolbar.setVisibility(View.VISIBLE);
                    funMeditation();
                    Util.refresh_gratitude="yes";
                    toolbar.setVisibility(View.VISIBLE);

                }


                funMeditation();

            }
        });

        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funDrawer1();
                Util.refresh_gratitude="yes";
                if (restrictionBool()) {
                    funTrainRestriction();
                } else {
                    imgToday.setImageResource(0);
                    imgToday.setImageResource(R.drawable.mbhq_today_active);
                    imgGratitude.setImageResource(0);
                    imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
                    imgMeditation.setImageResource(0);
                    imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
                    imgHabits.setImageResource(0);
                    imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
                    imgCourses.setImageResource(0);
                    imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
                    HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                    horTab.setVisibility(View.GONE);
                    Log.i("called landind","1");
                    loadFragment(new MbhqTodayMainFragment(), "Train", null);
                }
            }
        });

        rlHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toolbar.setVisibility(View.VISIBLE);
                funDrawer1();
                Log.i("habit111111111","habit");
                Util.refresh_gratitude="yes";
                toolbar.setVisibility(View.VISIBLE);
                if (restrictionBool()) {
                    Log.e("calledhabit","-1");
                    funTrainRestriction();
                } else {
                    sharedPreference = new SharedPreference(MainActivity.this);
                    if (sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                        Log.e("calledhabit","00");
                        Bundle bun = new Bundle();
                        bun.putString("FOR_PAGE", "HABIT");
                        clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
                        loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", bun);
                    } else {
                        Log.e("calledhabit","0");
                        if (isTrialAndPaidUser()) {

                            sharedPreference.write("achieve_nav", "", "habit_hacker");
                            sharedPreference.write("achieve_nav_pos", "", 0 + "");
                            Log.e("calledhabit","1");


                            Log.e("Hiiiiiiiiiiiiiiii","10000");
                            funHabits();
                        } else {
                            Log.e("Hiiiiiiiiiiiiiiii","10001");
                            Log.e("calledhabit","2");
                            sharedPreference.write("achieve_nav", "", "habit_hacker");
                            sharedPreference.write("achieve_nav_pos", "", 0 + "");
                            funHabits();
                        }
                    }
                }


            }
        });

        rlCourses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funDrawer1();
                Util.refresh_gratitude="yes";
                toolbar.setVisibility(View.VISIBLE);
                Log.e("COURSE_CLICKED", ">>>>>>>>>>>>>>");
                if (restrictionBool()) {
                    funTrainRestriction();
                } else {
                    funCourse();

                }


            }
        });

        rlMusicSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.refresh_gratitude="yes";


            }
        });

        rlPersonalSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.refresh_gratitude="yes";
                /*commentout by sahenita*/
//                loadFragment(new ProfileFragment(), "Profile", null);
//                setTargetFragment("Profile");
            }
        });
        /*commentout by sahenita*/
       /* rlCustomNutritionSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.refresh_gratitude="yes";
                AlertDialogCustom alertDialogCustom = new AlertDialogCustom(MainActivity.this);
                alertDialogCustom.ShowDialog("Alert!", "Are you certain, you want to update this setting? This will change your meals from today onward and effect any shopping list you have created", true);
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {
                        loadFragment(new NutritionSettingsMainFragmnet(), "NutritionSettingsMain", null);
                        setTargetFragment("NutritionSettingsMain");
                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });
            }
        });*/
        /*commentout by sahenita*/
      /*  rlCustomProgramSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.refresh_gratitude="yes";
                loadFragment(new ExerciseSettingFragmentMain(), "Exercise", null);
            }
        });*/


    }
    /*commentout by sahenita*/
    /*private void openCommunityScreen() {

        loadFragment(new CommunityFragment(), "Community", null);

    }*/
    public void fungratitudeicon(){
        String eq_access=sharedPreference.read("EqJournalAccess","");
        String accesstype=sharedPreference.read("accesstype","");

        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgHabits.setImageResource(0);
        imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
                imgGratitude.setAlpha(0.5f);

            }else{
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);
            }
        }else{
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);
        }



    }
    // IT IS USED FOR HABITS NOW
    public void funCourse_() {

        String Course_access=sharedPreference.read("CourseAccess","");
        String accesstype=sharedPreference.read("accesstype","");



        imgGratitude.setImageResource(0);
        imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgHabits.setImageResource(0);
        imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(Course_access)){
                imgCourses.setImageResource(0);
                imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
                imgCourses.setAlpha(0.5f);

            }else{
                imgCourses.setImageResource(0);
                imgCourses.setImageResource(R.drawable.mbhq_learn_active);
            }
        }else{
            imgCourses.setImageResource(0);
            imgCourses.setImageResource(R.drawable.mbhq_learn_active);
        }


        funTabBarforProgram();

        Bundle bundle = new Bundle();
        bundle.putString("NEWHOME", "true");
        loadFragment(new CourseFragment(), "Course", bundle);

    }

    public void funCourse() {
        String  Course_access=sharedPreference.read("CourseAccess","");
        if("true".equalsIgnoreCase(Course_access)){

            Log.i("101010","00");
            imgCourses.setImageResource(0);
            imgCourses.setImageResource(R.drawable.mbhq_learn_active);
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
            imgMeditation.setImageResource(0);
            imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
            imgToday.setImageResource(0);
            imgToday.setImageResource(R.drawable.mbhq_today_inactive);
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);

        }else{

            Log.i("101010","01");
            imgCourses.setImageResource(0);
            imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
            imgCourses.setAlpha(0.3f);
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
            imgMeditation.setImageResource(0);
            imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
            imgToday.setImageResource(0);
            imgToday.setImageResource(R.drawable.mbhq_today_inactive);
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        }

        funTabBarforProgram();
        Bundle bundle = new Bundle();
        String lastKey = sharedPreference.read("learn_nav", "");
        int lastPosition = 0;
        try {
            lastPosition = Integer.parseInt(sharedPreference.read("learn_nav_pos", ""));
        } catch (Exception e) {
            e.printStackTrace();
            lastPosition = 0;
        }
        funProgramClick(lastPosition);
    }

    public void funHabits_() {
        String habit_access=sharedPreference.read("HabitAccess","");
        String accesstype=sharedPreference.read("accesstype","");

        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgGratitude.setImageResource(0);
        imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);

        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);

        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);

        if("3".equalsIgnoreCase(accesstype)){

            if("false".equalsIgnoreCase(habit_access)){
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
                imgHabits.setAlpha(0.5f);

            }else{
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_active);
            }
        }else{
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_active);
        }



    }


    public void funHabits() {
        String habit_access=sharedPreference.read("HabitAccess","");
        String accesstype=sharedPreference.read("accesstype","");

        sharedPreference = new SharedPreference(MainActivity.this);
        sharedPreference.write("ftimetrain", "", "true");
        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
                imgHabits.setAlpha(0.5f);

            }else{
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_active);
            }
        }else{
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_active);
        }

        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgGratitude.setImageResource(0);
        imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
        Bundle bundle = new Bundle();
        String lastKey = sharedPreference.read("achieve_nav", "");
        Log.e("calledhabit","3");
        int lastPosition = 0;
        try {
            Log.e("calledhabit","4");
            lastPosition = Integer.parseInt(sharedPreference.read("achieve_nav_pos", ""));
        } catch (NumberFormatException e) {
            Log.e("calledhabit","5");
            e.printStackTrace();
        }
        if (lastKey.equals(""))
            bundle.putString("where", "custom");
        else
            bundle.putString("where", lastKey);
        funTabBarforHabits();
        funTrainClick(lastPosition);
    }
    //IT IS USED FOR GRATITUDE NOW
    public void funGratitude() {
        String eq_access=sharedPreference.read("EqJournalAccess","");
        String accesstype=sharedPreference.read("accesstype","");

        sharedPreference = new SharedPreference(MainActivity.this);
        sharedPreference.write("ftimenourish", "", "true");

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
                imgGratitude.setAlpha(0.5f);

            }else{
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);
            }
        }else{
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);
        }

        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgHabits.setImageResource(0);
        imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
        funTabBarforGratitude();
        Bundle bundle = new Bundle();
        String lastKey = sharedPreference.read("appreciate_nav", "");
        int lastPosition = 0;
        try {
            lastPosition = Integer.parseInt(sharedPreference.read("appreciate_nav_pos", ""));
        } catch (Exception e) {
            e.printStackTrace();
            lastPosition = 0;
        }
        funTrainNourishClick(lastPosition);

    }

    public void funMeditation() {
        sharedPreference = new SharedPreference(MainActivity.this);
        if (sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
            Bundle bun = new Bundle();
            bun.putString("FOR_PAGE", "MEDITATION");
            clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
            loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", bun);
        } else {
            String medi_access=sharedPreference.read("MeditationAccess","");
            String accesstype=sharedPreference.read("accesstype","");

            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);

            imgToday.setImageResource(0);
            imgToday.setImageResource(R.drawable.mbhq_today_inactive);
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
            imgCourses.setImageResource(0);
            imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
            if("3".equalsIgnoreCase(accesstype)){
                if("false".equalsIgnoreCase(medi_access)){
                    imgMeditation.setImageResource(0);
                    imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
                    imgMeditation.setAlpha(0.5f);

                }else{
                    imgMeditation.setImageResource(0);
                    imgMeditation.setImageResource(R.drawable.mbhq_meditation_active);
                }
            }else{
                imgMeditation.setImageResource(0);
                imgMeditation.setImageResource(R.drawable.mbhq_meditation_active);
            }

            loadFragment(new MeditationFragment(), "MeditationFragment", null);
        }
    }

    private void funChat() {
        messageNotification.setVisibility(View.GONE);
        try {
            PackageInfo info = getPackageManager().getPackageInfo("com.ashysystem.mbhq",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        mChatLoginDetect.post(mChatLogin);

        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
                messageNotification.setVisibility(View.GONE);

            } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {

            } else {
            }

        } else {
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                messageNotification.setVisibility(View.GONE);
            } else {
                messageNotification.setVisibility(View.GONE);
            }
        }

        messageNotification.setOnClickListener(view -> {
            if (Util.checkConnection(MainActivity.this)) {
                if (restrictionBool()) {
                    funTrainRestriction();
                } else {
                    loadFragment(new CourseFragment(), "Course", null);
                }
            } else {
                Util.showToast(MainActivity.this, Util.networkMsg);
            }
        });
    }

    private void funRatingBar() {
        if (!sharedPreference.readBoolean("ShowedRating", "Rating") && Util.getInstallTime() != null) {
            try {
                if (!sharedPreference.read("logintime", "").equals("")) {
                    String logTime = sharedPreference.read("logintime", "");
                    Long savedMillis = Long.parseLong(logTime);
                    if (System.currentTimeMillis() >= savedMillis + 24 * 60 * 60 * 1000) {
                        // time has elapsed
                        if (isPaidUser()) {
                            openRatingBar();
                            sharedPreference.writeBoolean("ShowedRating", "Rating", true);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void funPomotion() {
        globalLstPromotionalInfo = new ArrayList<>();


        PromotionalChallengeInfo promotion1 = new PromotionalChallengeInfo();
        promotion1.setTitle("You Can't Out Train a Bad Diet");
        promotion1.setBody("Get my FREE ‘Go To Guide’ to get you back on track TODAY");
        promotion1.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=gotoguide");
        promotion1.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion1);

        PromotionalChallengeInfo promotion5 = new PromotionalChallengeInfo();
        promotion5.setTitle("Abs are Made in the Kitchen");
        promotion5.setBody("Get my FREE ‘Go To Guide’ to get you back on track TODAY");
        promotion5.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=gotoguide");
        promotion5.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion5);

        PromotionalChallengeInfo promotion8 = new PromotionalChallengeInfo();
        promotion8.setTitle("One Size Never Fits ALL");
        promotion8.setBody("Learn how to listen to what your body needs \n Use code: Ashy72 for a special SQUAD discount off my new program");
        promotion8.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=signup");
        promotion8.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion8);

        PromotionalChallengeInfo promotion11 = new PromotionalChallengeInfo();
        promotion11.setTitle("Overwhelmed about what to eat and when?");
        promotion11.setBody("Get my FREE ‘Go To Guide’ to get you back on track TODAY");
        promotion11.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=gotoguide");
        promotion11.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion11);

        PromotionalChallengeInfo promotion2 = new PromotionalChallengeInfo();
        promotion2.setTitle("Ditch the Diet");
        promotion2.setBody("Stop Dieting, Start Listening To Your Body \n Use code: Ashy72 for 60% off");
        promotion2.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=signup");
        promotion2.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion2);

        PromotionalChallengeInfo promotion6 = new PromotionalChallengeInfo();
        promotion6.setTitle("Feeling Bloated all the time?");
        promotion6.setBody("Beat the Bloat so you can Look Your Best\nUse code: Ashy72 to try my new program");
        promotion6.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=signup");
        promotion6.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion6);

        PromotionalChallengeInfo promotion9 = new PromotionalChallengeInfo();
        promotion9.setTitle("Hormones Stopping You Burning Fat?");
        promotion9.setBody("Turn Your Fat Burning Switch on Today\nUse code: Ashy72 to try my new program");
        promotion9.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=signup");
        promotion9.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion9);

        PromotionalChallengeInfo promotion12 = new PromotionalChallengeInfo();
        promotion12.setTitle("STOP Yo-Yo Dieting");
        promotion12.setBody("I’m sharing my secrets for results that last\nUse code: Ashy72 to try my new program");
        promotion12.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=signup");
        promotion12.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion12);

        PromotionalChallengeInfo promotion3 = new PromotionalChallengeInfo();
        promotion3.setTitle("Stop Sabotaging Yourself");
        promotion3.setBody("It’s all about food freedom – let me show you.\nUse code: Ashy72 to try my new program");
        promotion3.setFromController("https://www.thesquadtours.com/Home/Redirect?mode=signup");
        promotion3.setActionTitle("FIND OUT MORE");

        globalLstPromotionalInfo.add(promotion3);


    }

    private void initView() {

        toolbar = findViewById(R.id.toolbar);
        leftDot = findViewById(R.id.leftDot);
        rightDot = findViewById(R.id.rightDot);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        sharedPreference.write("height", "", height + "");
        sharedPreference.write("width", "", width + "");
        imgGratitude = (ImageView) findViewById(R.id.imgGratitude);
        imgMeditation = (ImageView) findViewById(R.id.imgMeditation);
        imgToday = (ImageView) findViewById(R.id.imgToday);
        imgHabits = (ImageView) findViewById(R.id.imgHabits);
        imgCourses = (ImageView) findViewById(R.id.imgCourses);
        rlGratitude = (RelativeLayout) findViewById(R.id.rlGratitude);
        rlMeditation = (RelativeLayout) findViewById(R.id.rlMeditation);
        rlToday = (RelativeLayout) findViewById(R.id.rlToday);
        rlHabits = (RelativeLayout) findViewById(R.id.rlHabits);
        rlCourses = (RelativeLayout) findViewById(R.id.rlCourses);
        rlPersonalSetting = (RelativeLayout) findViewById(R.id.rlPersonalSetting);
        rlCustomProgramSetting = (RelativeLayout) findViewById(R.id.rlCustomProgramSetting);
        rlMusicSetting = (RelativeLayout) findViewById(R.id.rlMusicSetting);
        rlCustomNutritionSetting = (RelativeLayout) findViewById(R.id.rlCustomNutritionSetting);
        llBottomMenu = (LinearLayout) findViewById(R.id.llBottomMenu);
        rlDownloadedMeditations = findViewById(R.id.rlDownloadedMeditations);
        llSettingsBottomMenu = (LinearLayout) findViewById(R.id.llSettingsBottomMenu);
        txtGamificationCount = (TextView) toolbar.findViewById(R.id.txtGamificationCount);

        //  llGame = (LinearLayout) toolbar.findViewById(R.id.llGame);
        imgLogo = (ImageView) toolbar.findViewById(R.id.imgLogo);
        imgLeftBack = (ImageView) toolbar.findViewById(R.id.imgLeftBack);
        imgRightBack = (ImageView) toolbar.findViewById(R.id.imgRightBack);
        imgFilter = (ImageView) toolbar.findViewById(R.id.imgFilter);
        imgCircleBack = toolbar.findViewById(R.id.imgCircleBack);
        TextView txtGamificationCount = (TextView) toolbar.findViewById(R.id.txtGamificationCount);
        frameNotification = (FrameLayout) toolbar.findViewById(R.id.frameNotification);
        txtPageHeader = (TextView) toolbar.findViewById(R.id.txtPageHeader);
        txtExistingUserLogin = (TextView) toolbar.findViewById(R.id.txtExistingUserLogin);
        messageNotification = toolbar.findViewById(R.id.messageNotification);
        txtChatCount = toolbar.findViewById(R.id.txtChatCount);
        rlChatCount = toolbar.findViewById(R.id.rlChatCount);

        rlCommunityCircle = findViewById(R.id.rlCommunityCircle);
        txtCommunityCounter = findViewById(R.id.txtCommunityCounter);
        rlNotificationCircle = findViewById(R.id.rlNotificationCircle);
        txtNotificationCounter = findViewById(R.id.txtNotificationCounter);
        dialogLayout = findViewById(R.id.dialogLayout);
        imgForum =findViewById(R.id.imgForum);

        imgLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        rlDownloadedMeditations.setOnClickListener(view -> {
            if (Util.boolBackGroundServiceRunningMeditation && !Util.strMeditationDetailsForBackground.equals("")) {
                Log.e("UTILVALUEEEEEE", Util.strMeditationDetailsForBackground + ">>>>>");
                clearCacheForParticularFragment(new MeditationDetailsNew());
                MeditationDetailsNew meditationDetails = new MeditationDetailsNew();
                Bundle bundle = new Bundle();
                bundle.putString("data", Util.strMeditationDetailsForBackground);
                meditationDetails.setArguments(bundle);
                loadFragment(meditationDetails, "MeditationDetailsNew", null);
            } else {

                Util.refresh_meditation="no";
                Util.refresh_gratitude="yes";
                funMeditation();

            }
        });


        imgForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Util.checkConnection(MainActivity.this)) {
                    if (new SharedPreference(MainActivity.this).read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                        Bundle bun = new Bundle();
                        bun.putString("FOR_PAGE", "FORUM");
                        clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
                        loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", bun);
                    } else {

                        showDialog(MainActivity.this, "dialog");
                        //ended by Jyotirmoy Patra 15.09.22

                    }
                } else {
                    Util.showDialog(MainActivity.this, "Alert!", "The MindbodyHQ forum is not available in aeroplane mode. Please turn your internet back on to access the forum", false);
                }
            }
        });

    }
//added by Jyotirmoy Patra 15.09.22


    public void showDialog(Activity activity, String msg) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        //  final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);

        dialog.setContentView(R.layout.forum_dropdown);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        LinearLayout dialogLayout = (LinearLayout) dialog.findViewById(R.id.dialogLayout);
        dialogLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        ImageView dialogBtn_cancel = (ImageView) dialog.findViewById(R.id.imgCrossDropdown);
        dialogBtn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView forumlive = (TextView) dialog.findViewById(R.id.txtLiveChatBtn);
        forumlive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // openForum();
                dialog.dismiss();
                loadFragment(LiveChatFragment.newInstance(), "LiveChat", null);

            }
        });
        TextView fbForum = (TextView) dialog.findViewById(R.id.txtCommunityForumBtn);
        fbForum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    //endedby Jyotirmoy Patra 15.09.22


    private void callCoachApi() {
        if (Connection.checkConnection(MainActivity.this)) {

            ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

            FinisherServiceImpl finisherService = new FinisherServiceImpl(MainActivity.this);
            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("Email", sharedPreference.read("USEREMAIL", ""));
            hashReq.put("Password", sharedPreference.read("USERPASSWORD", ""));

            String url = "https://www.thesquadtours.com/api/coach/GetCoachToken";
            Call<JsonObject> jsonObjectCall = finisherService.GetCoachToken(url, hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    try {
                        JsonObject jsonResponse = response.body();
                        if (jsonResponse != null) {
                            if (jsonResponse.has("OnceOffToken")) {
                                String onceOffToken = jsonResponse.get("OnceOffToken").getAsString();
                                if (onceOffToken != null && !onceOffToken.isEmpty()) {
                                    openBrowser(onceOffToken);
                                } else openBrowser("");
                            } else openBrowser("");
                        } else openBrowser("");
                    } catch (Exception e) {
                        e.printStackTrace();
                        openBrowser("");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        }

    }

    private void openBrowser(String token) {
        if (token.isEmpty()) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://coach.emotionalfitnessclub.com/members/member/logon"));

            startActivity(browserIntent);
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://coach.emotionalfitnessclub.com/members/member/LogonViaToken?token=" + token));

            startActivity(browserIntent);
        }
    }

    private void funMenu() {

       //handleTotalAccess(); //temporary by jyoti
       loadFragment(new MbhqTodayMainFragment(),"Today",null);
    }
/*commentout by sahenita*/
/*
    private void funDb() {
        DatabaseHelper db = new DatabaseHelper(this);
        try {
            db.importFromCsv();
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.close();

    }
*/

    public boolean controlCheck() {

        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
                return false;
            } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {
                return true;

            } else {
                return false;
            }

        } else {
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                return false;
            } else {

                return true;

            }
        }
    }

    /**
     * Handle trial notifications.
     * On notification click we take user to the desired location on the app
     */

    public boolean isTrialAndPaidUser() {
        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
                return false;
            } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {
                return true;

            } else {
                return false;
            }

        } else {
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                return false;
            } else {

                return true;

            }
        }
    }

    public boolean isPaidUser() {

        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {

                return false;
            } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {
                return false;

            } else {
                return false;
            }

        } else {
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                showAlertDialogForDeniedAccess();
                return false;
            } else {

                return true;

            }
        }
    }

//    @NeedsPermission(Manifest.permission.CAMERA)
//    public void openChatScreen() {
//        Bundle bundle = new Bundle();
//        loadFragment(new ChatNewActivity(), "CHATNEW", bundle);
//    }//commented by jyotirmoy

    private void handleTotalAccess() {

        llBottomMenu.setVisibility(View.VISIBLE);
        if (getIntent() != null && getIntent().hasExtra(DemoApplication.QUOTE_NOTIFICATION_KEY)) {
            //handelTrialNotification();
          //  handelQuoteNotification();
            Log.e("loadfragment","1");
        } else if (getIntent() != null && getIntent().hasExtra("SETPROGRAM_LOGIN")) {
         //   getSquadMiniProgramDetailsApiCall(12);
        } else if (getIntent().hasExtra("msgsend")) {
            Log.e("loadfragment","2");
            String senderId = getIntent().getStringExtra("msgsend");
            String name = getIntent().getStringExtra("name");
            if (senderId != null && name != null && !name.equals("")) {
                Fragment fragment = new ConversationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("frnd", Integer.parseInt(senderId));
                bundle.putString("name", name);
                fragment.setArguments(bundle);
                Log.e("loadfragment","3");
                loadFragment(fragment, "conver", null);
            } else {
                Log.e("loadfragment","4");
                Log.i("called landind","0");
                loadFragment(new MbhqTodayMainFragment(), "home", null);
            }

        } else if (getIntent().hasExtra("NOTIFICATIONTYPE")) {
            Log.e("loadfragment","5");
            Bundle bundle = getIntent().getExtras();
            if (bundle.getString("NOTIFICATIONTYPE").equals("BUCKETLIST")) {
                Log.e("loadfragment","6");
                loadFragment(new BucketListFragment(), "BucketList", bundle);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("GRATITUDELIST")) {
                Log.e("loadfragment","7");
                loadFragment(new GratitudeMyListFragment(), "GratitudeMyList", bundle);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("HABITLIST")) {
                Log.e("loadfragment","8");
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", bundle.getInt("NOTIFICATIONID"));
                bundle1.putBoolean("from_notification", true);


                loadFragment(new HabbitDetailsCalendarFragment(), "HabbitDetailsCalendar", bundle1);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("ACHIEVEMENTLIST")) {
                Log.e("loadfragment","9");
                fungratitudeicon();
                loadFragment(new MyAchievementsFragment(), "MyAchievements", bundle);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("VISIONBOARD")) {
                Log.e("loadfragment","9");
                //loadFragment(new VisionGoalActionFragment(), "VisionGoalAction", bundle);
                loadFragment(new VisionBoardHome(), "VisionGoalAction", bundle);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("ACTIONLIST")) {
                Log.e("loadfragment","10");
                Bundle bundle1 = new Bundle();
                bundle1.putInt("action_edit", bundle.getInt("NOTIFICATIONID"));
                bundle1.putString("mode", "action");
                loadFragment(new AddEditGoalFragment(), "AddEditGoal", bundle1);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("GOALLIST")) {
                Log.e("loadfragment","11");
                Bundle bundle1 = new Bundle();
                bundle1.putInt("goal_edit", bundle.getInt("NOTIFICATIONID"));
                bundle1.putString("mode", "goal");
                loadFragment(new AddEditGoalFragment(), "AddEditGoal", bundle1);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("PERSONALCHALLENGELIST")) {
                Log.e("loadfragment","12");
                Bundle bundle1 = new Bundle();
                bundle1.putString("CHALLENGENAME", bundle.getString("NOTIFICATIONHEADING"));
                bundle1.putString("CHALLENGESTARTDATE", bundle.getString("NOTIFICATIONDATE"));
                bundle1.putString("CHALLENGEENDDATE", bundle.getString("NOTIFICATIONENDDATE"));
                bundle1.putInt("CHALLENGEID", bundle.getInt("NOTIFICATIONID"));
                loadFragment(new PersonalChallengeCalenderViewFragment(), "PersonalChallengeCalenderView", bundle1);
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("MEDITATION_PLAYING")) {
                Log.e("loadfragment","13");
                Log.i(TAG, "meditation playing");
                Util.strMeditationDetailsForBackground = bundle.getString("data");
                Util.boolBackGroundServiceRunningMeditation = true;
                rlMeditation.performClick();
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("PROGRAM_PLAYING")) {
                Log.e("loadfragment","14");
                Log.i(TAG, "program playing");
                Util.bundleProgramDetailsForBackground = bundle.getBundle("data");
                Util.boolBackGroundServiceRunningProgram = true;
                rlCourses.performClick();
            } else if (bundle.getString("NOTIFICATIONTYPE").equals("LIVE_CHAT_PLAYING")) {
                Log.e("loadfragment","15");
                Log.i(TAG, "live chat playing");
              //  loadFragment(LiveChatPlayerFragment.newInstance((Chat) bundle.getSerializable("data")), "LiveChatPlayer", null);

            }
        } else if (getIntent().hasExtra("FITBITACCESSTOKEN") && !getIntent().getStringExtra("FITBITACCESSTOKEN").equals("")) {
            /*commentout by sahenita*/
           // addFitbitDevice(getIntent().getStringExtra("FITBITACCESSTOKEN"), 1);
            //////////FITBIT//////////////////
        } else if (getIntent().hasExtra("GARMIN_OAUTHVERIFIER") && !getIntent().getStringExtra("GARMIN_OAUTHVERIFIER").equals("")) {
           // saveGarminAuthToken(getIntent().getStringExtra("GARMIN_OAUTHVERIFIER"));
            /////////////GARMIN//////////////////
        } else if (getIntent().hasExtra("notification"))//daily notification for seven days
        {
            Log.e("loadfragment","16");
            try {
                if (getIntent().hasExtra("origin")) {
                    Log.e("loadfragment","17");
                    Bundle tmpIntent = getIntent().getExtras();
                    String timeStr = tmpIntent.getString("origin");
                    int time = Integer.parseInt(timeStr);
                    if (time == 0) {
                        Log.e("loadfragment","18");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        loadFragment(new ConnectFragment(), "Connect", bundle);

                    } else if (time == 1) {
                        Log.e("loadfragment","19");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        loadFragment(new TrainFragment(), "Train", bundle);

                    } else if (time == 2) {
                        Log.e("loadfragment","20");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        loadFragment(new NourishFragmentHome(), "Connect", bundle);

                    } else if (time == 3) {
                        Log.e("loadfragment","21");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        loadFragment(new CourseFragment(), "Course", bundle);

                    } else if (time == 4) {
                        Log.e("loadfragment","22");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        bundle.putString("WEBINARSTATUS", "MEDITATION");
                        loadFragment(new WebinarFragment(), "WebinarFragment", bundle);

                    } else if (time == 5) {
                        Log.e("loadfragment","23");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        loadFragment(new FindAFriendFragment(), "FindAfragment", bundle);

                    } else if (time == 6) {
                        Log.e("loadfragment","24");
                        loadFragment(new VisionGoalActionFragment(), "VisionGoalAction", null);

                    } else if (time == 7) {
                        Log.e("loadfragment","25");
                        Bundle bundle = new Bundle();
                        bundle.putString("notification", "true");
                        bundle.putString("notificatonDaily", "true");
                        loadFragment(new TrainFragment(), "Train", bundle);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (getIntent().hasExtra("DEEPLINKTYPE")) {
            Log.e("DEEPLINKTYPE", ">>>>>>>>>>>");

           // handleDeepLinkType();

        } else if (getIntent().hasExtra("Song") && getIntent().getStringExtra("Song").equals("playpause")) {
            Log.e("loadfragment","26");
            notificationUpdate.onClicked();
        } else {

            if (new SharedPreference(MainActivity.this).read("IsNonSubscribeUser", "").equals("true")) {

                if (new SharedPreference(MainActivity.this).read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {

                    llBottomMenu.setVisibility(View.VISIBLE);
                    Bundle bundle = null;

                    if (getIntent() != null && getIntent().hasExtra("OPENCOMMUNITY") && getIntent().getStringExtra("OPENCOMMUNITY").equals("TRUE")) {
                        bundle = new Bundle();
                        bundle.putString("OPENCOMMUNITY", "TRUE");
                    }

                    if (bundle != null) {
                        Log.e("loadfragment","27");
                        Log.i("called landind","4");
                        loadFragment(new MbhqTodayMainFragment(), "home", bundle);
                    } else {
                        Log.e("loadfragment","28");
                        Log.i("called landind","5");
                        loadFragment(new MbhqTodayMainFragment(), "home", null);
                    }

                } else if (new SharedPreference(MainActivity.this).read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {

                    if (!new SharedPreference(MainActivity.this).read("AFTERLOGINFIRSTTIMEVIDEO", "").equals("TRUE")) {
                        Log.e("loadfragment","29");
                        Bundle bundle = new Bundle();
                        bundle.putString("FREETRIAL", "TRUE");
                        loadFragment(new AfterLoginFirstTimeVideoFragment(), "AfterLoginFirstTimeVideo", bundle);
                    } else {
                        if (new SharedPreference(MainActivity.this).read("compChk", "").equals("false")) {
                            Log.e("loadfragment","30");
                            llSettingsBottomMenu.setVisibility(View.GONE);
                            Bundle bundle = new Bundle();
                            if (getIntent() != null) {
                                if (getIntent().hasExtra("FLOWTYPE")) {
                                    FLOWTYPE = getIntent().getStringExtra("FLOWTYPE");
                                    bundle.putString("FLOWTYPE", getIntent().getStringExtra("FLOWTYPE"));
                                }
                            }
                            Log.e("loadfragment","31");
                            bundle.putString("FREETRIAL", "TRUE");
                            loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", bundle);
                        } else {
                            Log.e("loadfragment","32");
                            llBottomMenu.setVisibility(View.VISIBLE);
                            Log.i("called landind","6");
                            loadFragment(new MbhqTodayMainFragment(), "home", null);
                        }
                    }


                } else {
                    Log.e("loadfragment","33");
                    llBottomMenu.setVisibility(View.VISIBLE);
                    Bundle bundle = null;

                    if (getIntent() != null && getIntent().hasExtra("OPENCOMMUNITY") && getIntent().getStringExtra("OPENCOMMUNITY").equals("TRUE")) {
                        bundle = new Bundle();
                        bundle.putString("OPENCOMMUNITY", "TRUE");
                    }

                    if (bundle != null) {
                        Log.i("called landind","7");

                        Log.e("loadfragment","34");
                        loadFragment(new MbhqTodayMainFragment(), "home", bundle);
                    } else {
                        Log.e("loadfragment","35");
                        Log.i("called landind","8");
                        loadFragment(new MbhqTodayMainFragment(), "home", null);
                    }

                   // setFreeWorkoutNotification();

                }

            } else {
                Log.e("loadfragment","36");
                if (!new SharedPreference(MainActivity.this).read("AFTERLOGINFIRSTTIMEVIDEO", "").equals("TRUE")) {
                    Log.e("loadfragment","37");
                  //  loadFragment(new AfterLoginFirstTimeVideoFragment(), "AfterLoginFirstTimeVideo", null);
                   loadFragment(new MbhqTodayMainFragment(), "Today", null);
                } else {
                    if (new SharedPreference(MainActivity.this).read("compChk", "").equals("false")) {
                        Log.e("loadfragment","38");
                        llSettingsBottomMenu.setVisibility(View.GONE);
                        Bundle bundle = new Bundle();
                        if (getIntent() != null) {
                            if (getIntent().hasExtra("FLOWTYPE")) {
                                FLOWTYPE = getIntent().getStringExtra("FLOWTYPE");
                                bundle.putString("FLOWTYPE", getIntent().getStringExtra("FLOWTYPE"));
                            }
                        }
                        loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIstFragment", bundle);
                    } else {
                        Log.e("loadfragment","39");
                        llBottomMenu.setVisibility(View.VISIBLE);
                        Log.i("called landind","9");
                        loadFragment(new MbhqTodayMainFragment(), "home", null);
                    }
                }
            }

        }

        if (!new SharedPreference(MainActivity.this).read("gcm", "strgcm").equals("1") && !new SharedPreference(MainActivity.this).read("USEREMAIL", "").equals("")) {
            /*commentout by sahenita*/
          /*  FirebaseApp.initializeApp(getApplicationContext());
            String tkn = FirebaseInstanceId.getInstance().getToken();
            sendRegistrationIdToServer(tkn);
            new SharedPreference(MainActivity.this).write("FIREBASE_TOKEN", "", tkn);*/
/*commentout by sahenita*/
           // performCommunityLogIn(null);

           // Log.e("print tkn", tkn + "?");
        }

    }//temporary
//    private void handleDeepLinkType() {
//        if (getIntent().getStringExtra("DEEPLINKTYPE").equals("today_page")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            clearCacheForParticularFragment(new MbhqTodayMainFragment());
//            Log.i("called landind","10");
//            loadFragment(new MbhqTodayMainFragment(), "TodayFragment", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("vision_board")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            clearCacheForParticularFragment(new VisionBoardHome());
//            loadFragment(new VisionBoardHome(), "VisionBoardHome", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("questionnaire")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            clearCacheForParticularFragment(new QuestionariesFragment());
//            funDrawer1();
//
//            loadFragment(new QuestionariesFragment(), "QuestionariesFragment", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("help")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            loadFragment(new HelpFragment(), "HelpFragment", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("personal_challenge")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            loadFragment(new AchieveHomeFragment(), "AchieveHomeFragment", bundle);
//        }
//
//        else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("courses")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            clearCacheForParticularFragment(new CourseFragment());
//            loadFragment(new CourseFragment(), "Course", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("cusotmnutrition")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINK", "TRUE");
//            clearCacheForParticularFragment(new MbhqTodayMainFragment());
//            Log.i("called landind","11");
//            loadFragment(new MbhqTodayMainFragment(), "Home", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("squaddaily")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("notificatonDaily", "true");
//            clearCacheForParticularFragment(new TrainFragment());
//            loadFragment(new TrainFragment(), "Train", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_CUSTOMWORKOUT)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            loadFragment(new TrainFragment(), "Train", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("dailygoodness")) {
//            loadFragment(new SquadDailyMealFragment(), "SquadDailyMeal", null);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("fatloss")) {
//            Bundle bundle = new Bundle();
//            bundle.putInt("COURSEID", 1);
//            bundle.putInt("ARTICLEID", 1);
//            bundle.putString("DEEPLINKTYPE", "fatloss");
//            loadFragment(new CourseArticleDetailsNewFragment(), "CRC", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("mealplan")) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", "mealplan");
//            loadFragment(new NourishFragmentHome(), "Nourish", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_CALORIE_COUNTER)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_CALORIE_COUNTER);
//            loadFragment(new NourishFragmentHome(), "Nourish", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_FOOD_PREP_HELPER)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_FOOD_PREP_HELPER);
//            loadFragment(new NourishFragmentHome(), "Nourish", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_RECIPES_LIST)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_RECIPES_LIST);
//            loadFragment(new NourishFragmentHome(), "Nourish", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_FITBITIWATCH)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_FITBITIWATCH);
//            loadFragment(new TrainFragment(), "Train", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_FBWTRACKER)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_FBWTRACKER);
//            loadFragment(new TrainFragment(), "Train", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_SESSIONLIST)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_SESSIONLIST);
//            loadFragment(new TrainFragment(), "Train", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_8_WEEK_CHALLENGE)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_8_WEEK_CHALLENGE);
//            loadFragment(new LearnFragment(), "Learn", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_SET_PROGRAMS)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_SET_PROGRAMS);
//            loadFragment(new LearnFragment(), "Learn", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_WEBINAR)) {
//
//            loadFragment(new WebinarFragment(), "Webinar", null);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_FIND_A_FRIEND)) {
//
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_FIND_A_FRIEND);
//            loadFragment(new ConnectFragment(), "Connect", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_ACCOUNTABILITY_BUDDIES)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_ACCOUNTABILITY_BUDDIES);
//            loadFragment(new AchieveHomeFragment(), "AchieveHome", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_GOALS_VISION)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_GOALS_VISION);
//            loadFragment(new AchieveHomeFragment(), "AchieveHome", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_BUCKET_LIST)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_BUCKET_LIST);
//            loadFragment(new AchieveHomeFragment(), "AchieveHome", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_MEDITATION)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_MEDITATION);
//            loadFragment(new AppreciateFragment(), "Appreciate", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_GRATITUDE_LIST)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_GRATITUDE_LIST);
//            loadFragment(new AppreciateFragment(), "Appreciate", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_PHOTOS)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_PHOTOS);
//            loadFragment(new TrackFragment(), "Track", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_WEIGH_INS)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_WEIGH_INS);
//            loadFragment(new TrackFragment(), "Track", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_GAMIFICATION_CENTRE)) {
//            imgCircleBack.performClick();
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_BOOTY_ABS)) {
//            loadFragment(new ProgramsFragment(), "Programs", null);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_CALENDAR)) {
//            loadFragment(new UpcomingWebinarsFragment(), "UpcomingWebinars", null);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_SETTINGS)) {
//            loadFragment(new SettingsMainFragment(), "SettingsMain", null);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_CHALLENGE)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", DemoApplication.DEEPLINK_CHALLENGE);
//            loadFragment(new TrainFragment(), "Train", bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").contains(DemoApplication.DEEPLINK_FRIEND_REQUEST)) {
//            Bundle bundle = new Bundle();
//            bundle.putString("notification", "true");
//            bundle.putString("DEEPLINKTYPE", getIntent().getStringExtra("DEEPLINKTYPE"));
//            loadFragment(new AccountabilityBuddyListFragment(), AccountabilityBuddyListFragment.class.getSimpleName(), bundle);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").contains(DemoApplication.DEEPLINK_MESSAGE_CENTER)) {
//            clearCacheForParticularFragment(new MbhqTodayMainFragment());
//            Log.i("called landind","12");
//            loadFragment(new MbhqTodayMainFragment(), "Home", null);
//            mChatLoginDetect.post(mChatLogin);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals("web")) {
//            clearCacheForParticularFragment(new MbhqTodayMainFragment());
//            Log.i("called landind","13");
//            loadFragment(new MbhqTodayMainFragment(), "Home", null);
//            try {
//                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
//                intent.putExtra("WEBVIEWURL", getIntent().getStringExtra("DEEPLINKWEBURL"));
//                startActivity(intent);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_WORLD_FORUM)) {
//            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
//            try {
//                if (!sharedPreference.read("FbWorldForumUrl", "").equals("")) {
//                    Uri uri = Uri.parse(sharedPreference.read("FbWorldForumUrl", "")); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.setPackage("com.android.chrome");
//                    MainActivity.this.startActivity(intent);
//                } else {
//                    Uri uri = Uri.parse("https://www.facebook.com/groups/250625228700325/"); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.setPackage("com.android.chrome");
//                    MainActivity.this.startActivity(intent);
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_CLEAN_UP_START)) {
//            getSquadMiniProgramDetailsApiCall(12);
//        } else if (getIntent().getStringExtra("DEEPLINKTYPE").equals(DemoApplication.DEEPLINK_CLEAN_UP_SEMINAR)) {
//            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
//            clearCacheForParticularFragment(new CourseFragment());
//            clearCacheForParticularFragment(new CourseDetailsFragment());
//
//            sharedPreference.write("learn_nav", "", "course");
//            sharedPreference.write("learn_nav_pos", "", 1 + "");
//            funTabBarforProgram();
//
//            Bundle bundle = new Bundle();
//            bundle.putString("FROMSETPROGRAM", "Ashy Bines Clean Up");
//            bundle.putString("FROMTODAYPAGE", "TRUE");
//            loadFragment(new CourseFragment(), "Course", bundle);
//        }
//
//    }//temporary

    /**
     * @param requestCode
     */

    /*commentout by sahenita*/
/*
    private void cancelFree7dayTrialNotification(int requestCode) {

        try {
            Intent intent = new Intent(this, AlarmReceiverFreeTrial.class);
            PendingIntent sender = PendingIntent.getBroadcast(this, requestCode, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

            alarmManager.cancel(sender);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
*/

    /**
     *
     */
//    private void setFreeWorkoutNotification() {
//        if (!sharedPreference.read("FIRST_INSTALL_DATE", "").equals("") && !sharedPreference.read("FREEWORKOUTSET", "").equals("TRUE")) {
//
//            sharedPreference.write("FREEWORKOUTSET", "", "TRUE");
//        } else {
//
//        }
//
//    }//commented by jyotirmoy

    /**
     *
     */

    /*commentout by sahenita (temporary)*/
/*
    private void setFree7dayTrialNotification() {
        if (!sharedPreference.read("FIRST_INSTALL_DATE", "").equals("") && !sharedPreference.read("7DAYFREETRIALSET", "").equals("TRUE")) {

            cancelFree7dayTrialNotification(1);
            cancelFree7dayTrialNotification(11);
            cancelFree7dayTrialNotification(2);
            cancelFree7dayTrialNotification(3);
            cancelFree7dayTrialNotification(4);
            cancelFree7dayTrialNotification(5);
            cancelFree7dayTrialNotification(6);
            cancelFree7dayTrialNotification(7);
            cancelFree7dayTrialNotification(10);
            cancelFree7dayTrialNotification(14);


            cancelFree7dayTrialNotification(8);
            cancelFree7dayTrialNotification(9);
            cancelFree7dayTrialNotification(15);
            cancelFree7dayTrialNotification(220);
            cancelFree7dayTrialNotification(29);
            cancelFree7dayTrialNotification(36);
            cancelFree7dayTrialNotification(43);
            cancelFree7dayTrialNotification(50);
            cancelFree7dayTrialNotification(57);
            cancelFree7dayTrialNotification(64);
            cancelFree7dayTrialNotification(71);
            cancelFree7dayTrialNotification(78);




            sharedPreference.write("7DAYFREETRIALSET", "", "TRUE");
        } else {

        }

    }
*/

//    private void setNotificationFreeWorkout(String notificationMsg, int notificationId, Calendar calendar, String title) {
//
//        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(MainActivity.this, AlarmReceiverFreeTrial.class);
//        Bundle bundle = new Bundle();
//        String notifyId = String.valueOf(notificationId);
//        if (notificationId != 10 && notifyId.contains("1")) {
//            bundle.putString("NOTIFICATIONTYPE", "FIRSTDAY_TRIAL");
//        } else if (notifyId.contains("2")) {
//            bundle.putString("NOTIFICATIONTYPE", "SECONDDAY_TRIAL");
//        } else if (notifyId.contains("3")) {
//            bundle.putString("NOTIFICATIONTYPE", "THIRDDAY_TRIAL");
//        } else if (notifyId.contains("4")) {
//            bundle.putString("NOTIFICATIONTYPE", "FOURTHDAY_TRIAL");
//        } else if (notifyId.contains("5")) {
//            bundle.putString("NOTIFICATIONTYPE", "FIFTHDAY_TRIAL");
//        } else if (notifyId.contains("6")) {
//            bundle.putString("NOTIFICATIONTYPE", "SIXTHDAY_TRIAL");
//        } else if (notifyId.contains("7")) {
//            bundle.putString("NOTIFICATIONTYPE", "SEVENTHDAY_TRIAL");
//        } else if (notifyId.contains("8")) {
//            bundle.putString("NOTIFICATIONTYPE", "EIGHTHDAY_TRIAL");
//        } else if (notifyId.contains("9")) {
//            bundle.putString("NOTIFICATIONTYPE", "NINTHDAY_TRIAL");
//        } else if (notifyId.contains("10")) {
//            bundle.putString("NOTIFICATIONTYPE", "TENTHDAY_TRIAL");
//        }
//        bundle.putInt("NOTIFICATIONID", notificationId);
//        bundle.putString("NOTIFICATIONHEADING", title);
//        bundle.putString("NOTIFICATIONMSG", notificationMsg);
//        intent.putExtra(DemoApplication.FREE_WORKOUT_NOTIFICATION_KEY, bundle);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0);
//        if (am != null)
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//    }
//commented by jyotirmoy
//    private void funQuote() {
//        sharedPreference = new SharedPreference(MainActivity.this);
//        {
//            DatabaseHelper db = new DatabaseHelper(MainActivity.this);
//            int notificationId = 1987;
//            int dayNo = 0;
//            for (int i = 1; i <= 365; i++) {
//                Quote quoteObj = db.getQuote(i);
//                // Log.e("Print quotes--", quoteObj.getQuote() + "???");
//                Date installDate = null;
//                try {
//                    installDate = globalFormatWithOutT.parse(sharedPreference.read("FIRST_INSTALL_DATE", ""));
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//
//                Calendar calendar = Calendar.getInstance();
//                Calendar tmpCalendar = Calendar.getInstance();
//
//                if (dayNo > 0) ///Second day onwards
//                {
//                    calendar.add(Calendar.DATE, dayNo);
//                    calendar.set(Calendar.HOUR_OF_DAY, 7);
//                    calendar.set(Calendar.MINUTE, 30);
//                } else {  ////for only first day
//                    calendar.set(Calendar.HOUR_OF_DAY, tmpCalendar.get(Calendar.HOUR_OF_DAY) + 1);
//                    calendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE));
//                }
//
//                calendar.set(Calendar.HOUR_OF_DAY, tmpCalendar.get(Calendar.HOUR_OF_DAY) + 1);
//                calendar.set(Calendar.MINUTE, tmpCalendar.get(Calendar.MINUTE));
//                calendar.set(Calendar.SECOND, tmpCalendar.get(Calendar.SECOND));
//                setNotificationForQuotes(quoteObj.getQuote(), notificationId, calendar, quoteObj.getAuthor());
//                notificationId++;
//                dayNo++;
//            }
//            db.close();
//        }
//
//    }//commented by jyotirmoy

//    private void setNotificationForQuotes(String notificationMsg, int notificationId, Calendar calendar, String title) {
//        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(MainActivity.this, AlarmReceiverFreeTrial.class);
//        Bundle bundle = new Bundle();
//        String notifyId = String.valueOf(notificationId);
//
//        bundle.putInt("NOTIFICATIONID", notificationId);
//        bundle.putString("NOTIFICATIONHEADING", title);
//        bundle.putString("NOTIFICATIONMSG", notificationMsg);
//        intent.putExtra(DemoApplication.QUOTE_NOTIFICATION_KEY, bundle);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0);
//        if (am != null)
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//    }//commented by jyotirmoy

//    private void handelQuoteNotification() {
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra(DemoApplication.QUOTE_NOTIFICATION_KEY)) {
//            Bundle bundle = intent.getBundleExtra(DemoApplication.QUOTE_NOTIFICATION_KEY);
//            Bundle bundleInner = new Bundle();
//            Log.e("print msg author", bundle.getString("NOTIFICATIONMSG") + "?" + bundle.getString("NOTIFICATIONHEADING"));
//            bundleInner.putString("Quote", bundle.getString("NOTIFICATIONMSG"));
//            bundleInner.putString("Author", bundle.getString("NOTIFICATIONHEADING"));
//            int notificationId = bundle.getInt("NOTIFICATIONID", 0);
//
//            loadFragment(new QuoteViewFragment(), "QuoteViewFragment", bundleInner);
//
//        }
//    }//commented by jyotirmoy

//    private void setNotification7dayTrial(String notificationMsg, int notificationId, Calendar calendar, String title) {
//
//        AlarmManager am = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(MainActivity.this, AlarmReceiverFreeTrial.class);
//        Bundle bundle = new Bundle();
//        String notifyId = String.valueOf(notificationId);
//        if (notificationId != 10 && notifyId.contains("1")) {
//            bundle.putString("NOTIFICATIONTYPE", "FIRSTDAY_TRIAL");
//        } else if (notifyId.contains("2")) {
//            bundle.putString("NOTIFICATIONTYPE", "SECONDDAY_TRIAL");
//        } else if (notifyId.contains("3")) {
//            bundle.putString("NOTIFICATIONTYPE", "THIRDDAY_TRIAL");
//        } else if (notifyId.contains("4")) {
//            bundle.putString("NOTIFICATIONTYPE", "FOURTHDAY_TRIAL");
//        } else if (notifyId.contains("5")) {
//            bundle.putString("NOTIFICATIONTYPE", "FIFTHDAY_TRIAL");
//        } else if (notifyId.contains("6")) {
//            bundle.putString("NOTIFICATIONTYPE", "SIXTHDAY_TRIAL");
//        } else if (notifyId.contains("7")) {
//            bundle.putString("NOTIFICATIONTYPE", "SEVENTHDAY_TRIAL");
//        } else if (notifyId.contains("8")) {
//            bundle.putString("NOTIFICATIONTYPE", "EIGHTHDAY_TRIAL");
//        } else if (notifyId.contains("9")) {
//            bundle.putString("NOTIFICATIONTYPE", "NINTHDAY_TRIAL");
//        } else if (notifyId.contains("10")) {
//            bundle.putString("NOTIFICATIONTYPE", "TENTHDAY_TRIAL");
//        }
//        bundle.putInt("NOTIFICATIONID", notificationId);
//        bundle.putString("NOTIFICATIONHEADING", title);
//        bundle.putString("NOTIFICATIONMSG", notificationMsg);
//        intent.putExtra(DemoApplication.TRIAL_NOTIFICATION_KEY, bundle);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, notificationId, intent, 0);
//        if (am != null)
//            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
//    }//commented by jyotirmoy

    /**
     * Handle free workout notifications.
     * On notification click we take user to the desired location on the app
     */
//    private void handelFreeWorkoutNotification() {
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra(DemoApplication.FREE_WORKOUT_NOTIFICATION_KEY)) {
//
//
//
//        }
//    }//commented by jyotirmoy

//    private void getSquadMiniProgramDetailsApiCall(Integer programId) {
//
//        ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
//
//        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
//
//        HashMap<String, Object> hashReq = new HashMap<>();
//        hashReq.put("UserId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
//        if (programId != null) {
//            hashReq.put("ProgramId", 12);
//        } else {
//            hashReq.put("ProgramId", 12);
//        }
//        hashReq.put("Key", Util.KEY_ABBBC);
//        hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));
//        SessionServiceImpl sessionService = new SessionServiceImpl(MainActivity.this);
//        Call<GetUserSquadMiniProgramDetailModel> jsonObjectCall = sessionService.getSquadMiniProgramDetails(hashReq);
//        jsonObjectCall.enqueue(new Callback<GetUserSquadMiniProgramDetailModel>() {
//            @Override
//            public void onResponse(Call<GetUserSquadMiniProgramDetailModel> call, Response<GetUserSquadMiniProgramDetailModel> response) {
//                progressDialog.dismiss();
//                if (response.body() != null && response.body().getSuccess()) {
//                    clearCacheForParticularFragment(new SetProgramsDetailsFragment());
//
//                    if (getIntent() != null && getIntent().hasExtra("SETPROGRAM_LOGIN")) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("FROM_TODAY_PAGE", "TRUE");
//                        bundle.putString("SETPROGRAM_LOGIN", "TRUE");
//                        Gson gson = new Gson();
//                        bundle.putString("PROGRAM_MODEL", gson.toJson(response.body().getSquadMiniProgramModel()));
//                        loadFragment(new SetProgramsDetailsFragment(), "SetProgramsDetails", bundle);
//                    } else {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("FROM_TODAY_PAGE", "TRUE");
//                        Gson gson = new Gson();
//                        bundle.putString("PROGRAM_MODEL", gson.toJson(response.body().getSquadMiniProgramModel()));
//                        loadFragment(new SetProgramsDetailsFragment(), "SetProgramsDetails", bundle);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GetUserSquadMiniProgramDetailModel> call, Throwable t) {
//                progressDialog.dismiss();
//            }
//        });
//
//    }//commented by jyotirmoy

    /**
     * Handle trial notifications.
     * On notification click we take user to the desired location on the app
     */
//    private void handelTrialNotificationNEW() {
//        Intent intent = getIntent();
//        if (intent != null && intent.hasExtra(DemoApplication.TRIAL_NOTIFICATION_KEY)) {
//            Bundle bundle = intent.getBundleExtra(DemoApplication.TRIAL_NOTIFICATION_KEY);
//            Bundle bundleInner = new Bundle();
//            int notificationId = bundle.getInt("NOTIFICATIONID", 0);
//            if (notificationId == 1123) {
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 11123) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_CUSTOM_WORKOUT);
//                Log.i("called landind","14");
//                loadFragment(new MbhqTodayMainFragment(), "Home", bundleInner);
//            }
//            if (notificationId == 2123) {
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 22123) {
//                loadFragment(new TrainFragment(), "Train", null);
//            } else if (notificationId == 222123) {
//                bundleInner.putString("notification", "true");
//                loadFragment(new ConnectFragment(), "Connect", bundleInner);
//            }
//            if (notificationId == 3123) {
//
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 33123) {
//
//                getAvailableCourse();
//            } else if (notificationId == 333123) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_UPCOMING_WEBINAR);
//                loadFragment(new LearnFragment(), "Learn", bundleInner);
//            }
//            if (notificationId == 4123) {
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 44123) {
//
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            }
//            if (notificationId == 5123) {
//
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 55123) {
//                // TODO Menu Settings for offline
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            }
//            if (notificationId == 6123) {
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 66123) {
//
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            }
//            if (notificationId == 7123) {
//
//                getSquadMiniProgramDetailsApiCall(12);
//
//            } else if (notificationId == 77123) {
//                linkSIgnUp();
//            } else if (notificationId == 8123) {
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            } else if (notificationId == 9123) {
//                openSurvey();
//            } else if (notificationId == 10123) {
//                linkSIgnUp();
//            } else if (notificationId == 14123) {
//                linkSIgnUp();
//            } else if (notificationId == 21123) {
//                linkSIgnUp();
//            } else if (notificationId == 15123) {
//                loadFragment(new SquadDailyListFragment(), "squaddaily", null);
//            } else if (notificationId == 220123) {
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                intent.setPackage("com.android.chrome");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                startActivity(buyIntent);
//            } else if (notificationId == 29123) {
//                Bundle sessionBundle = new Bundle();
//                sessionBundle.putString("where", "session");
//                sessionBundle.putString("NEWHOME", "true");
//                loadFragment(new TrainFragment(), "Train", sessionBundle);
//            } else if (notificationId == 36123) {
//                Bundle setProgrambundle = new Bundle();
//                setProgrambundle.putString("NEWHOME", "true");
//                loadFragment(new SetProgramsFragmentNew(), "SetPrograms", setProgrambundle);
//            } else if (notificationId == 43123) {
//                Bundle timerBundle = new Bundle();
//                timerBundle.putString("FROMHOME", "TRUE");
//                loadFragment(new TimerModeFragment(), "Timer", timerBundle);
//            } else if (notificationId == 50123) {
//                Uri uri = Uri.parse("https://www.ashybines.com/home/keto");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            } else if (notificationId == 57123) {
//                Bundle dailyBundle = new Bundle();
//                dailyBundle.putString("where", "daily");
//                dailyBundle.putString("NEWHOME", "true");
//                loadFragment(new TrainFragment(), "Train", dailyBundle);
//            } else if (notificationId == 64123) {
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            } else if (notificationId == 71123) {
//                Bundle trackBundle = new Bundle();
//                trackBundle.putString("NEWHOME", "true");
//                loadFragment(new TrackFragment(), "Track", trackBundle);
//            } else if (notificationId == 78123) {
//                Uri uri = Uri.parse("https://www.thesquadtours.com/Home/Redirect?mode=signup");
//                Intent buyIntent = new Intent(Intent.ACTION_VIEW, uri);
//                intent.setPackage("com.android.chrome");
//                startActivity(buyIntent);
//            }
//        }
//    }//commented by jyotirmoy


//    private void handlePostRegisterNotification(Bundle bundle) {
//        try {
//            int notificationId = bundle.getInt("NOTIFICATIONID", 0);
//            Bundle bundleInner = new Bundle();
//            if (notificationId == 10) {
//                loadFragment(new ConnectFragment(), "Connect", null);
//            } else if (notificationId == 20) {
//                getAvailableCourse();
//            } else if (notificationId == 30) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_FIT_BIT);
//                loadFragment(new TrainFragment(), "Train", bundleInner);
//            } else if (notificationId == 40) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_CUSTOM_SHOP_LIST);
//                loadFragment(new NourishFragmentHome(), "Nourish", bundleInner);
//            } else if (notificationId == 50) {
//                bundleInner.putString("notification", "true");
//                loadFragment(new CourseFragment(), "Course", bundleInner);
//            } else if (notificationId == 80) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_ALL_WEBINARS);
//                loadFragment(new LearnFragment(), "Learn", bundleInner);
//            } else if (notificationId == 120) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_MY_PHOTO);
//                loadFragment(new TrackFragment(), "Track", bundleInner);
//            } else if (notificationId == 150) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_FBW_TRACK);
//                loadFragment(new TrackFragment(), "Track", bundleInner);
//            } else if (notificationId == 190) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_UPCOMING_WEBINAR);
//                loadFragment(new LearnFragment(), "Learn", null);
//            } else if (notificationId == 220) {
//                loadFragment(new SettingsMainFragment(), "SettingMain", null);
//            } else if (notificationId == 260) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_CALORIE_COUNT);
//                loadFragment(new NourishFragmentHome(), "Nourish", bundleInner);
//            } else if (notificationId == 290) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_NUTRITION);
//                loadFragment(new SettingsMainFragment(), "SettingMain", bundleInner);
//            } else if (notificationId == 330) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_EXERCISE);
//                loadFragment(new SettingsMainFragment(), "SettingMain", bundleInner);
//            } else if (notificationId == 101) {
//                loadFragment(new ConnectFragment(), "Connect", null);
//            } else if (notificationId == 201) {
//                loadFragment(new SquadDailyListFragment(), "SquadDailyList", null);
//            } else if (notificationId == 301) {
//                bundleInner.putString("notification", "true");
//                bundleInner.putString(DemoApplication.BUNDLE_KEY_FOR, DemoApplication.FOR_FIT_BIT);
//                loadFragment(new TrainFragment(), "Train", bundleInner);
//            } else if (notificationId == 401) {
//                loadFragment(new SquadDailyListFragment(), "SquadDailyList", null);
//            } else if (notificationId == 501) {
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                intent.putExtra("UPGRADECLICK", "TRUE");
//                startActivity(intent);
//                MainActivity.this.finish();
//            } else if (notificationId == 801) {
//                loadFragment(new SquadDailyListFragment(), "SquadDailyList", null);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }//commented by jyotirmoy

//    private void linkSIgnUp() {
//        Intent signUpIntent = new Intent(this, RegisterActivity.class);
//        signUpIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        startActivity(signUpIntent);
//        finish();
//    }//commented by jyotirmoy

//    private void setTextColorForMenuItem(MenuItem menuItem, @ColorRes int color) {
//        SpannableString spanString = new SpannableString(menuItem.getTitle().toString());
//        // spanString.setSpan(new ForegroundColorSpan(color, 0, spanString.length(), 0);
//        spanString.setSpan(new ForegroundColorSpan(color), 0, spanString.length(), 0);
//        menuItem.setTitle(spanString);
//    }//commented by jyotirmoy

//    private void sendToFragmentExcercise(int stepNumber, int nutritionStepNo) {
//
//        if (stepNumber == 0 && nutritionStepNo == 0) {
//            Intent intent = new Intent(MainActivity.this, TrialActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
//        } else if (stepNumber == 0) {
//            sendToFragmentNutrition(nutritionStepNo, stepNumber);
//        } else if (stepNumber == 1) {
//            loadFragment(new CustomProgramPageOneFragment(), "CustomProgramPageOne", null);
//        } else if (stepNumber == 2) {
//            loadFragment(new CustomProgramPageTwoFragment(), "CustomProgramPageTwo", null);
//        } else if (stepNumber == 3) {
//            loadFragment(new CustomProgramPageThreeFragment(), "CustomProgramPageThree", null);
//        } else if (stepNumber == 4) {
//            loadFragment(new CustomProgramPageFourFragment(), "CustomProgramPageFour", null);
//        } else if (stepNumber == 5) {
//            loadFragment(new CustomProgramPageFiveFragment(), "CustomProgramPageFive", null);
//        } else if (stepNumber == 6) {
//            loadFragment(new CustomProgramPageSixFragment(), "CustomProgramPageSix", null);
//        } else if (stepNumber == 7) {
//            loadFragment(new CustomProgramPageSevenFragment(), "CustomProgramPageSeven", null);
//        }
//    }//commented by jyotirmoy

//    private void sendToFragmentNutrition(int nutritionStepNo, int programStepNumber) {
//        if (nutritionStepNo == 0) {
//            /*sendToFragmentExcercise(programStepNumber);*/
//            Intent intent = new Intent(MainActivity.this, TrialActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//            finish();
//        } else if (nutritionStepNo == 1) {
//            loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
//        } else if (nutritionStepNo == -1) {
//            if (programStepNumber == 0) {
//                loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
//            } else {
//                loadFragment(new NutritionSettingsPageOneGoalFragment(), "NutritionSettingsPageOneGoal", null);
//            }
//        } else if (nutritionStepNo == 2) {
//            loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
//        } else if (nutritionStepNo == 3) {
//            loadFragment(new NutritionChooseMealFrequencyFragment(), "NutritionChooseMealFrequency", null);
//        } else if (nutritionStepNo == 4) {
//            loadFragment(new NutritionMealVarietyFragment(), "NutritionMealVariety", null);
//        } else if (nutritionStepNo == 5) {
//            loadFragment(new NutritionSelectMealPlanFragment(), "NutritionSelectMealPlan", null);
//        }
//    }//commented by jyotirmoy
/*commentout by sahenita*/
/*
    private void addFitbitDevice(String fitbitAccessToken, Integer deviceTypeId) {

        if (Connection.checkConnection(MainActivity.this)) {
            progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("AccessToken", fitbitAccessToken);
            hashReq.put("Key", Util.KEY);
            hashReq.put("DeviceTypeId", deviceTypeId);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.addFitbitDevice(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null && response.body().get("Success").getAsBoolean()) {

                        Leanplum.track("Track_Android_Linked a wearable");
                        refershGamePoint(MainActivity.this);
                        Bundle bundle = new Bundle();
                        bundle.putString("FROMPAGE", "MAINACTIVITY");
                        loadFragment(new MyWearableFragment(), "MyWearable", bundle);

                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }

    }
*/

//    private void saveGarminAuthToken(String oauthVerifier) {
//
//        if (Connection.checkConnection(MainActivity.this)) {
//            progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
//
//            HashMap<String, Object> hashReq = new HashMap<>();
//            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
//            hashReq.put("OauthVerifier", oauthVerifier);
//            hashReq.put("Key", Util.KEY);
//            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//            hashReq.put("AbbbcUserId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
//            hashReq.put("AbbbcSessionId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));
//
//            Call<JsonObject> jsonObjectCall = finisherService.saveGarminAuthToken(hashReq);
//            jsonObjectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    progressDialog.dismiss();
//                    if (response.body() != null && response.body().get("Success").getAsBoolean()) {
//                        Leanplum.track("Track_Android_Linked a wearable");
//                        refershGamePoint(MainActivity.this);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("FROMPAGE", "MAINACTIVITY");
//                        loadFragment(new MyWearableFragment(), "MyWearable", bundle);
//
//                    } else {
//                        Log.i("called landind","15");
//                        loadFragment(new MbhqTodayMainFragment(), "Home", null);
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    progressDialog.dismiss();
//                }
//            });
//
//        } else {
//            Util.showToast(MainActivity.this, Util.networkMsg);
//        }
//
//    }//commented by jyotirmoy

//    private void sendRegistrationIdToServer(String registrationId) {
//
//        if (TextUtils.isEmpty(registrationId))
//            return;
//
//        JSONObject requestJson = new JSONObject();
//        final SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
//        try {
//            requestJson.put("User_id", Integer.parseInt(sharedPreference.read("UserID", "")));
//            requestJson.put("Key", Util.KEY);
//            requestJson.put("AppsFlyerUID", APPSFLYER_ID);
//            requestJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//            requestJson.put("Android_device_id", registrationId);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (Connection.checkConnection(MainActivity.this)) {
//            GenericAsynTask genericAsynTask = new GenericAsynTask("Please wait...", MainActivity.this, Util.INITIALIZEUSERNOTIFICATION, "POST", requestJson, "", "");
//            genericAsynTask.setOnTaskListener(new GenericAsynTask.TaskListener() {
//                @Override
//                public void onSuccess(String success) {
//
//                    sharedPreference.write("gcm", "strgcm", "1");
//
//                }
//
//                @Override
//                public void onFailure(String error) {
//                    //Log.e("FAILFAIL",">>>>>>>>>");
//                }
//            });
//        } else {
//            Toast.makeText(MainActivity.this, "Check your internet connection", Toast.LENGTH_LONG).show();
//        }
//
//    }//commented by jyotirmoy

    @Override
    public void onBackPressed() {


        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            ConnectFragment connectFragment = (ConnectFragment) getSupportFragmentManager().findFragmentByTag("Connect");
            FindAFriendFragment findAFriendFragment = (FindAFriendFragment) getSupportFragmentManager().findFragmentByTag("frnd");
            ConversationFragment conversationFragment = (ConversationFragment) getSupportFragmentManager().findFragmentByTag("conver");
            NourishFragmentHome nourishFragmentHome = (NourishFragmentHome) getSupportFragmentManager().findFragmentByTag("Nourish");
            LearnFragment learnFragment = (LearnFragment) getSupportFragmentManager().findFragmentByTag("Learn");
            AppreciateFragment appreciateFragment = (AppreciateFragment) getSupportFragmentManager().findFragmentByTag("appricate");
            SquadDailyListFragment squadDailyListFragment = (SquadDailyListFragment) getSupportFragmentManager().findFragmentByTag("squaddaily");
            TrainOptionFragment trainOptionFragment = (TrainOptionFragment) getSupportFragmentManager().findFragmentByTag("trainop");
            SessionOverviewFragment sessionOverviewFragment = (SessionOverviewFragment) getSupportFragmentManager().findFragmentByTag("sov");
            if (connectFragment != null) {
                if (connectFragment.isVisible())
                    Log.i("called landind","16");
                loadFragment(new MbhqTodayMainFragment(), "home", null);
            } else if (conversationFragment != null) {
                if (conversationFragment.isVisible()) {
                    loadFragment(new FindAFriendFragment(), "frnd", null);
                }
            } else if (findAFriendFragment != null) {
                if (findAFriendFragment.isVisible()) {
                    loadFragment(new ConnectFragment(), "connect", null);

                }
            } else if (nourishFragmentHome != null) {
                if (nourishFragmentHome.isVisible()) {
                    Log.i("called landind","17");
                    loadFragment(new MbhqTodayMainFragment(), "home", null);

                }
            } else if (learnFragment != null) {
                if (learnFragment.isVisible()) {
                    Log.i("called landind","18");
                    loadFragment(new MbhqTodayMainFragment(), "home", null);

                }
            } else if (appreciateFragment != null) {
                if (appreciateFragment.isVisible()) {
                    Log.i("called landind","19");
                    loadFragment(new MbhqTodayMainFragment(), "home", null);

                }
            } else if (squadDailyListFragment != null) {
                if (squadDailyListFragment.isVisible()) {
                    loadFragment(new TrainFragment(), "Train", null);

                }
            } else if (trainOptionFragment != null) {
                if (trainOptionFragment.isVisible()) {
                    loadFragment(new SquadDailyListFragment(), "squaddaily", null);

                }
            } else if (sessionOverviewFragment != null) {
                if (sessionOverviewFragment.isVisible()) {
                    loadFragment(new TrainOptionFragment(), "trainop", null);

                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.main, menu); //commented by jyotirmoy
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.e("REQUESTCODE", requestCode + ">>>>>>");
        Log.e("RESULTCODE", resultCode + ">>>>>>");

        if (requestCode == 2222) {
            Log.e("GRATITUDE_SHARE_IMAGE", ">>>>>>>>>");
        }

        if (requestCode == 8080) {
            if (data != null) {
                if (currentFragment != null) {
                    currentFragment.onActivityResult(requestCode, resultCode, data);
                }
            }
        }

        if (requestCode == 1337) {
            if (data != null) {
                try {
                    getSupportFragmentManager().findFragmentByTag("spotify").onActivityResult(requestCode, resultCode, data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (requestCode == 1887) {

            /*commentout by sahenita*/
           /* if (data != null && spotifySelectSongGoalFragment != null) {

                spotifySelectSongGoalFragment.onActivityResult(requestCode, resultCode, data);
            }*/
        } else if (requestCode == 1002 || requestCode == 1334) {
            if (currentFragment != null) {
                currentFragment.onActivityResult(requestCode, resultCode, data);
            }
        } else if (requestCode == 6969 && currentFragment != null) {

            currentFragment.onActivityResult(requestCode, resultCode, data);

        } else if (requestCode == 1434 || requestCode == 1437 || requestCode == 500 || requestCode == 9971 || requestCode == 9921 || requestCode == 1356) {
            if (currentFragment != null) {
                currentFragment.onActivityResult(requestCode, resultCode, data);
            }
        }
//        else if (requestCode == Util.OAUTH_REQUEST_CODE) {
//            if (data != null) {
//                String code = data.getStringExtra(UpPlatformSdkConstants.ACCESS_CODE);
//                if (code != null) {
//                    ApiManager.getRequestInterceptor().clearAccessToken();
//
//                    ApiManager.getRestApiInterface().getAccessToken(
//                            Util.CLIENT_ID,
//                            Util.CLIENT_SECRET,
//                            code,
//                            accessTokenRequestListener);
//                }
//            }
//        } //commented by jyotirmoy
        else if (requestCode == 600) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == Util.PLAY_PAUSE_REQUEST) {
            currentFragment.onActivityResult(requestCode, resultCode, data);
        } else if (requestCode == DemoApplication.CHAT_CLOSE_REQUEST && resultCode != Activity.RESULT_CANCELED) {
            super.onActivityResult(requestCode, resultCode, data);
            if (data != null && data.hasExtra(DemoApplication.KEY_START_FRAGMENT)) {
                if (FindAFriendFragment.class.getSimpleName().equalsIgnoreCase(data.getStringExtra(DemoApplication.KEY_START_FRAGMENT))) {
                    loadFragment(new FindAFriendFragment(), FindAFriendFragment.class.getSimpleName(), null);
                }  else if (AccountabilityBuddyMainFragment.class.getSimpleName().equalsIgnoreCase(data.getStringExtra(DemoApplication.KEY_START_FRAGMENT))) {
                    loadFragment(new AccountabilityBuddyMainFragment(), AccountabilityBuddyMainFragment.class.getSimpleName(), null);
                }
            }
        } else if (requestCode == CAMERA_PIC_REQUEST_CODE || requestCode == PICK_IMAGE_FROM_GALLERY_CODE) {
            try {
                if (resultCode != 0) {
                    if (data == null) {
                        try {
                            if (!imgPath.equals("")) {
                                File selectedImagePath = new File(imgPath);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                    imgBackgroundPic.setBackground(null);
                                }
                                imgBackgroundPic.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()));
                                imgDecodableString = selectedImagePath.getAbsolutePath();

                                try {
                                    FileInputStream imageInFile = new FileInputStream(selectedImagePath);
                                    byte imageData[] = new byte[(int) selectedImagePath.length()];
                                    imageInFile.read(imageData);
                                    stringImg = encodeImage(imageData);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                cropPhoto(BitmapFactory.decodeFile(imgDecodableString), imgDecodableString);
                            } else
                                Toast.makeText(MainActivity.this, "An error occured", Toast.LENGTH_SHORT);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else if (requestCode == PICK_IMAGE_FROM_GALLERY_CODE && resultCode == MainActivity.this.RESULT_OK
                            && null != data) {

                        try {
                            Uri selectedImage = data.getData();
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            // Get the Image from data
                            Log.e("PICTURE Gal---->", "123" + selectedImage);

                            Cursor cursor = MainActivity.this.getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imgDecodableString = cursor.getString(columnIndex);
                            cursor.close();
                            cropPhoto(BitmapFactory.decodeFile(imgDecodableString), imgDecodableString);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else if (requestCode == CAMERA_PIC_REQUEST_CODE && resultCode == MainActivity.this.RESULT_OK && null != data) {
                        try {
                            imgDecodableString = out.getPath();
                            Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                            if (bit != null) {
                                cropPhoto(bit, imgDecodableString);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (requestCode == 782 || requestCode == 783 || requestCode == 784 || requestCode == 785) {
            if (currentFragment != null) {
                currentFragment.onActivityResult(requestCode, resultCode, data);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

//        if (id == R.id.action_settings) {
//            return true;
//        }//temporary

        return super.onOptionsItemSelected(item);
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return false;
    }

    public void loadFragment(Fragment fragment, String title, Bundle bun) {
        Log.i("unique","20");
        Log.i("fragment load ",title+"");
        funDrawer1();

        setBoolExerciseNutritionSettings(false);
        if (fragment instanceof MyAchievementsListAddEditFragment) {
            toolbar.setVisibility(View.GONE);
        } else {
            toolbar.setVisibility(View.VISIBLE);
        }


        if (Util.checkConnection(MainActivity.this)) {
            llBottomMenu.setVisibility(View.VISIBLE);
            rlDownloadedMeditations.setVisibility(View.GONE);
        } else {
            llBottomMenu.setVisibility(View.GONE);
            rlDownloadedMeditations.setVisibility(View.VISIBLE);
            if (!sharedPreference.readBoolean("HAS_AEROPLANE_MODE_DIALOG_SHOWN", "")) {
                openAeroplaneModeDialog();
                sharedPreference.writeBoolean("HAS_AEROPLANE_MODE_DIALOG_SHOWN", "", true);
            }
        }

        if (bun != null)
            fragment.setArguments(bun);
        fragment.setRetainInstance(true);

        currentFragment = fragment;

        try {
            drawer.closeDrawers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        currentFragmentTab = fragment;

        if (!Util.isLocalUploadGrowthGratitideCalled && Util.checkConnection(MainActivity.this)) {
           // functionToUploadLocallySavedGratitudeAndGrowth();
        }


       checkLocal7DayTrial(fragment, title);
        funDrawer1();
        getStreakData();
        getMeditationStreakData();
        checkMeditationCacheExpiration();
    }

    private void checkLocal7DayTrial(Fragment fragment, String title) {

        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            Log.e("calledhabit","10");

            if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
                Log.e("calledhabit","11");
                Log.e("h-", "1");
                fragmentTransacrionMethodWithCondition(fragment, title);

            } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {
                Log.e("calledhabit","12");
                Log.e("h-", "7");
                try {
                    try {
                        ServiceSessionMain.client.dispatcher().cancelAll();
                        Service.client.dispatcher().cancelAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Util.hideKeyboard(MainActivity.this);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    Log.e("Print class name trial", fragment.getClass().getSimpleName() + "???" + fragment.getClass().getSimpleName() + "????");
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                    funFragmentReUse(fragmentTransaction, title, fragmentManager, fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                Log.e("calledhabit","13");
                Log.e("h-", "2");
                fragmentTransacrionMethodWithCondition(fragment, title);
            }


        } else {
            Log.e("calledhabit","14");
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                Log.e("calledhabit","15");
                Log.e("h-", "3");
                fragmentTransacrionMethodWithCondition(fragment, title);
            } else {
                Log.e("calledhabit","16");
                try {
                    try {
                        ServiceSessionMain.client.dispatcher().cancelAll();
                        Service.client.dispatcher().cancelAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Util.hideKeyboard(MainActivity.this);
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Log.e("Print class name1", fragment.getClass().getSimpleName());


                    funFragmentReUse(fragmentTransaction, title, fragmentManager, fragment);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void funFragmentReUse(FragmentTransaction fragmentTransaction, String title, FragmentManager fragmentManager, Fragment fragment) {
        Log.e("calledhabit1","20");
        if (restrictionBool()) {
            Log.e("calledhabit1","21");
            funTrainRestriction();

        } else {
            Log.e("calledhabit1","22");
            Log.e("sssize____",String.valueOf(arrFragment.size()));
            for (int p = 0; p < arrFragment.size(); p++) {
                Log.e("sssize____",String.valueOf(arrFragment.size()));
                if (fragment.getClass().getSimpleName().equals(arrFragment.get(p).getClass().getSimpleName())) {
                    Log.e("calledhabit1","23");
                    Log.e("getSimpleName",fragment.getClass().getSimpleName());
                    Log.e("getSimpleName",arrFragment.get(p).getClass().getSimpleName());
                    Util.matchFound = true;
                    fragment = arrFragment.get(p);
                    Util.arrPos = p;
                    break;

                }

            }
            if (!Util.matchFound) {
                Log.e("calledhabit1","24");
                Log.e("absent-", fragment.getClass().getSimpleName() + "?");
                if (allowCache(fragment)) {
                    Log.e("calledhabit1","25");
                    Log.e("Added--", fragment.getClass().getSimpleName() + "?");
                    arrFragment.add(fragment);
                } else {
                    arrFragment.add(fragment);
                    Log.e("calledhabit1","26");
                    Log.e("Added--", fragment.getClass().getSimpleName() + "?" + "-NOT");
                }
                Log.e("calledhabit1","27");
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                Util.matchFound = false;
            } else {
                Log.e("calledhabit1","28");
                Log.e("present-", fragment.getClass().getSimpleName() + "?");
                fragmentTransaction.replace(R.id.container_body, fragment);
                fragmentTransaction.commit();
                Util.matchFound = false;
            }


        }
    }

    private void funTrainRestriction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("");
        builder.setMessage("Don't go,try a workout NOW!\nYou won't regret it!!");
        builder.setPositiveButton("Explore squad", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                clearCacheForParticularFragment(new TrainOptionFragment());
                imgToday.setImageResource(0);
                imgToday.setImageResource(R.drawable.mbhq_today_active);
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
                imgMeditation.setImageResource(0);
                imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
                imgCourses.setImageResource(0);
                imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
                setRestrictModeTrain(false);
                Log.i("called landind","20");
                loadFragment(new MbhqTodayMainFragment(), "Today Fargment", null);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("Choose workout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                setRestrictModeTrain(true);
                SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
                sharedPreference.write("restricttrain", "", "true");
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void fragmentTransacrionMethodWithCondition(Fragment fragment, String title) {

        if (fragment instanceof AchieveHomeFragment) {
            showDifferentDialog();
        } else if (fragment instanceof AppreciateFragment) {
            showDifferentDialog();
        } else if (fragment instanceof TrackFragment) {
            showDifferentDialog();
        } else if (fragment instanceof AllLeaderBoardFragment) {
            showDifferentDialog();
        }

       else if (fragment instanceof UpcomingWebinarsFragment) {
            showDifferentDialog();
        } else if (fragment instanceof GamificationFragment) {
            showDifferentDialog();
        } else if (fragment instanceof NourishFragmentHome) {
            showDifferentDialog();
        } else if (fragment instanceof LearnFragment) {
            showDifferentDialog();
        } else if (fragment instanceof BucketListFragment) {
            showDifferentDialog();
        } else if (fragment instanceof GratitudeMyListFragment) {
            showDifferentDialog();
        } else if (fragment instanceof MyAchievementsFragment) {
            showDifferentDialog();
        } else if (fragment instanceof VisionGoalActionFragment) {
            showDifferentDialog();
        } else if (fragment instanceof VisionBoardHome) {
            showDifferentDialog();
        } else if (fragment instanceof AddEditGoalFragment) {
            showDifferentDialog();
        } else if (fragment instanceof CourseFragment) {
            showDifferentDialog();
        } else if (fragment instanceof WebinarFragment) {
            showDifferentDialog();
        }else if (fragment instanceof SquadDailyMealFragment) {
            showDifferentDialog();
        } else if (fragment instanceof CourseArticleDetailsFragment) {
            showDifferentDialog();
        } else if (fragment instanceof AccountabilityBuddyListFragment) {
            showDifferentDialog();
        }else if (fragment instanceof NutritionSettingsMainFragmnet) {
            showDifferentDialog();
        } else if (fragment instanceof ExerciseSettingFragmentMain) {
            showDifferentDialog();
        } else {
            try {
                if (getIntent() != null && getIntent().hasExtra("srckey") && getIntent().getStringExtra("srckey").equals("train")) {
                    Log.e("find-", "1");
                    getIntent().putExtra("srckey", "false");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("srckey", "true");
                    currentFragmentTab = new TrainOptionFragment();
                    currentFragmentTab.setArguments(bundle);
                    Log.e("Print class name src", fragment.getClass().getSimpleName() + "???" + currentFragmentTab.getClass().getSimpleName() + "????");
                    sharedPreference.write("achieve_nav", "", "daily");
                    sharedPreference.write("achieve_nav_pos", "", 1 + "");
                    funTabBarforHabits();
                    funFragmentReUse(fragmentTransaction, title, fragmentManager, currentFragmentTab);
                } else if (getIntent() != null && getIntent().hasExtra("massUser") && getIntent().getStringExtra("massUser").equals("true")) {
                    Log.e("find-", "12");
                    getIntent().putExtra("massUser", "false");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Bundle bundle = new Bundle();
                    bundle.putString("massUser", "true");
                    Log.i("called landind","21");
                    currentFragmentTab = new MbhqTodayMainFragment();
                    currentFragmentTab.setArguments(bundle);
                    Log.e("Print class name src", fragment.getClass().getSimpleName() + "???" + currentFragmentTab.getClass().getSimpleName() + "????");
                    sharedPreference.write("achieve_nav", "", "daily");
                    sharedPreference.write("achieve_nav_pos", "", 1 + "");
                    funTabBarforHabits();
                    funFragmentReUse(fragmentTransaction, title, fragmentManager, currentFragmentTab);
                } else {
                    Log.e("find-", "2");
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Log.e("Print class name2", fragment.getClass().getSimpleName());
                    funFragmentReUse(fragmentTransaction, title, fragmentManager, currentFragmentTab);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showDifferentDialog() {

        showAlertGergious();


    }

    public void showAlertDialogForDeniedAccess() {

        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_accessdenied);
        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
        RelativeLayout rlCancel = (RelativeLayout) dialog.findViewById(R.id.rlCancel);
        RelativeLayout rlOk = (RelativeLayout) dialog.findViewById(R.id.rlOk);

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        rlOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
//                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
//                intent.putExtra("UPGRADECLICK", "TRUE");
//                startActivity(intent);

            }
        });

        dialog.show();

    }

//    public void openDialogForRegisterUser(Fragment fragment, String title) {
//
//        if (sharedPreference.read("USEREMAIL", "").equals("")) {
//
//
//            final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setContentView(R.layout.dialog_register_email_alert);
//            ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
//            RelativeLayout rlAddEmail = (RelativeLayout) dialog.findViewById(R.id.rlAddEmail);
//
//            imgCross.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                }
//            });
//            rlAddEmail.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    dialog.dismiss();
//                    Intent registerIntent = new Intent(MainActivity.this, RegisterFirstActivity.class);
//                    if (!title.equals("")) {
//                        Bundle bundle = new Bundle();
//                        bundle.putString("FLOWTYPE", title);
//                        registerIntent.putExtras(bundle);
//                    }
//                    registerIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(registerIntent);
//                    finish();
//                }
//            });
//
//            dialog.show();
//
//        } else {
//            try {
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                Log.e("Print class name3", fragment.getClass().getSimpleName());
//
//                funFragmentReUse(fragmentTransaction, title, fragmentManager, fragment);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }//temporary


    public void openDialogEqFolder() {


        SharedPreferences prefs = getPreferences(Context.MODE_PRIVATE);
        String json= sharedPreference.read("my_list_eqfolder", "");
        ArrayList<UserEqFolder> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<UserEqFolder>>(){}.getType());


        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.eq_folder);
        ImageView imgCross1 = (ImageView) dialog.findViewById(R.id.imgCross1);
        EditTextOswaldRegular textF1= (EditTextOswaldRegular)  dialog.findViewById(R.id.textF1);
        EditTextOswaldRegular textF2= (EditTextOswaldRegular)  dialog.findViewById(R.id.textF2);
        EditTextOswaldRegular textF3= (EditTextOswaldRegular)  dialog.findViewById(R.id.textF3);

        textF1.setSelection(textF1.getText().length());
        textF2.setSelection(textF2.getText().length());
        textF3.setSelection(textF3.getText().length());

        InputFilter[] filters = new InputFilter[1];
        filters[0] = new InputFilter.LengthFilter(10); // Sets the maximum length to 10 characters

        textF1.setFilters(filters);
        textF2.setFilters(filters);
        textF3.setFilters(filters);

        textF1.setFocusable(false);
        textF1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        textF1.setClickable(false);

        textF2.setFocusable(false);
        textF2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        textF2.setClickable(false);

        textF3.setFocusable(false);
        textF3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
        textF3.setClickable(false);


        ImageView imgF1= (ImageView) dialog.findViewById(R.id.imgF1);
        ImageView imgF2= (ImageView) dialog.findViewById(R.id.imgF2);
        ImageView imgF3= (ImageView) dialog.findViewById(R.id.imgF3);

        imgF1.setImageResource(R.drawable.eqedit);
        imgF2.setImageResource(R.drawable.eqedit);
        imgF3.setImageResource(R.drawable.eqedit);

        if(null!=habitSwapList_ ) {

            if ( habitSwapList_.size() > 0 ) {
                textF1.setHint(habitSwapList_.get(0).getFolderName());
                textF2.setHint(habitSwapList_.get(1).getFolderName());
                textF3.setHint(habitSwapList_.get(2).getFolderName());
            }

        }

        imgCross1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textF1.addTextChangedListener(new TextWatcher() {


            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        imgF1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f1){

                    f1=false;


                    textF1.setFocusable(false);
                    textF1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF1.setClickable(false);

                    if(textF1.getText().toString().equalsIgnoreCase("")){
                        showpopup();
                    }else{
                        imgF1.setImageResource(R.drawable.eqedit);
                        updateEqName(textF1.getText().toString(),habitSwapList_.get(1).getUserEqFolderId(),dialog);
                    }

                }else{
                    f1=true;

                    f2=false;
                    f3=false;
                    imgF2.setImageResource(R.drawable.eqedit);

                    textF2.setText("");
                    textF2.setFocusable(false);
                    textF2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF2.setClickable(false);

                    imgF3.setImageResource(R.drawable.eqedit);

                    textF3.setText("");
                    textF3.setFocusable(false);
                    textF3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF3.setClickable(false);


                    imgF1.setImageResource(R.drawable.eqsave);
                    textF1.setFocusable(true);
                    textF1.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    textF1.setClickable(true);

                    textF2.setFocusable(false);
                    textF2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF2.setClickable(false);

                    textF3.setFocusable(false);
                    textF3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF3.setClickable(false);

                }
            }
        });

        imgF2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f2){
                    f2=false;


                    textF2.setFocusable(false);
                    textF2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF2.setClickable(false);

                    if(textF2.getText().toString().equalsIgnoreCase("")){
                        showpopup();
                    }else{
                        imgF2.setImageResource(R.drawable.eqedit);
                        updateEqName(textF2.getText().toString(),habitSwapList_.get(1).getUserEqFolderId(),dialog);
                    }

                }else{
                    f2=true;

                    f1=false;
                    f3=false;
                    imgF1.setImageResource(R.drawable.eqedit);
                    textF1.setText("");
                    textF1.setFocusable(false);
                    textF1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF1.setClickable(false);
                    imgF3.setImageResource(R.drawable.eqedit);
                    textF3.setText("");
                    textF3.setFocusable(false);
                    textF3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF3.setClickable(false);


                    imgF2.setImageResource(R.drawable.eqsave);
                    textF2.setText("");
                    textF2.setFocusable(true);
                    textF2.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    textF2.setClickable(true);

                    textF1.setFocusable(false);
                    textF1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF1.setClickable(false);

                    textF3.setFocusable(false);
                    textF3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF3.setClickable(false);
                }
            }
        });

        imgF3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(f3){
                    f3=false;


                    textF3.setFocusable(false);
                    textF3.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF3.setClickable(false);
                    if(textF3.getText().toString().equalsIgnoreCase("")){
                        showpopup();
                    }else{
                        imgF3.setImageResource(R.drawable.eqedit);
                        updateEqName(textF3.getText().toString(),habitSwapList_.get(2).getUserEqFolderId(),dialog);
                    }
                }else{
                    f3=true;

                    f1=false;
                    f2=false;
                    imgF2.setImageResource(R.drawable.eqedit);
                    textF2.setText("");
                    textF2.setFocusable(false);
                    textF2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF2.setClickable(false);
                    imgF1.setImageResource(R.drawable.eqedit);
                    textF1.setText("");
                    textF1.setFocusable(false);
                    textF1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF1.setClickable(false);

                    imgF3.setImageResource(R.drawable.eqsave);
                    textF3.setText("");
                    textF3.setFocusable(true);
                    textF3.setFocusableInTouchMode(true); // user touches widget on phone with touch screen
                    textF3.setClickable(true);

                    textF1.setFocusable(false);
                    textF1.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF1.setClickable(false);

                    textF2.setFocusable(false);
                    textF2.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                    textF2.setClickable(false);
                }
            }
        });


        dialog.show();

    }



    public void showAlertGergious() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_show_gorgeious_alert);

        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
        RelativeLayout rlAddEmail = (RelativeLayout) dialog.findViewById(R.id.rlAddEmail);
        RelativeLayout rlLogin = (RelativeLayout) dialog.findViewById(R.id.rlLogin);
        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
        TextView txtAddEmail = dialog.findViewById(R.id.txtAddEmail);
        TextView txtNoThanks = dialog.findViewById(R.id.txtNoThanks);

        String mystring = new String("NO THANKS");
        SpannableString content = new SpannableString(mystring);
        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
        txtNoThanks.setText(content);

        if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
            txtDesc.setText("This amazing feature is for full members only");
            txtAddEmail.setText("Get Access Now!");
        } else {
            txtDesc.setText("This amazing feature is for full members only");
            txtAddEmail.setText("Trial it now for 7 days");
        }

        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        txtNoThanks.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();


                if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {

                    String URL = "https://www.thesquadtours.com/Home/Redirect?mode=signup" + "&userId=" + new SharedPreference(MainActivity.this).read("UserID", "");

                    Uri uri = Uri.parse(URL);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setPackage("com.android.chrome");
                    startActivity(intent);

                } else {

                    sharedPreference.write("SEVENDAY_TRIAL_START", "", "TRUE");
                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat globalFormatWithOutT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    SimpleDateFormat globalFormat = new SimpleDateFormat("yyyy-MM-dd");
                    sharedPreference.write("FIRST_INSTALL_DATE", "", globalFormatWithOutT.format(calendar.getTime()));

                  //  setFree7dayTrialNotification();

                    String updateDate = globalFormat.format(calendar.getTime());
                    updateTrialDateApiCall(updateDate);
                    showTrialDialog();

                }
            }
        });

        rlLogin.setOnClickListener(view -> {
            dialog.dismiss();

            Intent loginIntent = new Intent(MainActivity.this, UserPaidStatusActivity.class);
            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(loginIntent);
        });

        dialog.show();
    }

    public void showTrialDialog() {
        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_traial_new_design);
        RelativeLayout rlDoBtn = (RelativeLayout) dialog.findViewById(R.id.rlDoBtn);
        rlDoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(getIntent());
            }
        });


        dialog.show();
    }

//    public void showAchieveDialogFirstTime() {
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_traial_new_design);
//        RelativeLayout rlDoBtn = dialog.findViewById(R.id.rlDoBtn);
//        TextView txtWelcome = dialog.findViewById(R.id.txtWelcome);
//        TextView txtPersonal = dialog.findViewById(R.id.txtPersonal);
//        txtWelcome.setText("Welcome To mbHQ");
//        txtPersonal.setText("Let's set your goals and create a personalised program to get maximum results!");
//
//        new SharedPreference(MainActivity.this).write("ftimeachieve", "", "true");
//
//        rlDoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                sharedPreference.write("achieve_nav", "", "habit_hacker");
//                sharedPreference.write("achieve_nav_pos", "", 0 + "");
//                Log.e("Hiiiiiiiiiiiiiiii","100002");
//                funHabits();
//            }
//        });
//
//        dialog.show();
//    }//commented by jyotirmoy

//    public void showAppreciateDialogFirstTime() {
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_traial_new_design);
//        RelativeLayout rlDoBtn = dialog.findViewById(R.id.rlDoBtn);
//        TextView txtWelcome = dialog.findViewById(R.id.txtWelcome);
//        TextView txtPersonal = dialog.findViewById(R.id.txtPersonal);
//        txtWelcome.setText("Welcome To mbHQ");
//        txtPersonal.setText("Let's set your goals and create a personalised program to get maximum results!");
//
//        new SharedPreference(MainActivity.this).write("ftimeAppreciate", "", "true");
//
//        rlDoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//                sharedPreference.write("appreciate_nav", "", "gratitude");
//                sharedPreference.write("appreciate_nav_pos", "", 0 + "");
//                funGratitude();
//            }
//        });
//
//        dialog.show();
//    }//commented by jyotirmoy

//    public void showNouishTrainlDialogFirstTime(String type) {
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.layout_traial_new_design);
//        RelativeLayout rlDoBtn = (RelativeLayout) dialog.findViewById(R.id.rlDoBtn);
//        TextView txtWelcome = (TextView) dialog.findViewById(R.id.txtWelcome);
//        txtWelcome.setText("Welcome To Squad");
//        rlDoBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                if (type.equals("Nourish")) {
//                    funGratitude();
//                } else
//                    Log.e("Hiiiiiiiiiiiiiiii","100003");
//                funHabits();
//            }
//        });
//
//
//        dialog.show();
//    }//commented by jyotirmoy

//    private void openAlertAchieveYourGoal() {
//
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_show_gorgeious_alert);
//
//        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
//        RelativeLayout rlAddEmail = (RelativeLayout) dialog.findViewById(R.id.rlAddEmail);
//        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
//        TextView txtAddEmail = dialog.findViewById(R.id.txtAddEmail);
//        TextView txtNoThanks = dialog.findViewById(R.id.txtNoThanks);
//
//
//        txtDesc.setText("Welcome to Your 7 Day Trial. It's time to help you ");
//        txtAddEmail.setText("ACHIEVE YOUR GOALS");
//
//        imgCross.setVisibility(View.INVISIBLE);
//        txtNoThanks.setVisibility(View.INVISIBLE);
//
//        imgCross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        txtNoThanks.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//
//        rlAddEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//                sharedPreference.write("SEVENDAY_TRIAL_START", "", "TRUE");
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat globalFormatWithOutT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                SimpleDateFormat globalFormat = new SimpleDateFormat("yyyy-MM-dd");
//                sharedPreference.write("FIRST_INSTALL_DATE", "", globalFormatWithOutT.format(calendar.getTime()));
//
//             //   setFree7dayTrialNotification();
//
//                String updateDate = globalFormat.format(calendar.getTime());
//
//                finish();
//                startActivity(getIntent());
//
//            }
//        });
//
//        dialog.show();
//
//    }//temporary


    ///////////////////////////// New 8 Week Challange ///////////////////////////

    private void updateTrialDateApiCall(String updateDate) {

        if (Connection.checkConnection(MainActivity.this)) {
            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserID", sharedPreference.read("UserID", ""));
            hashReq.put("TrialDate", updateDate);


            FinisherServiceImpl finisherService = new FinisherServiceImpl(MainActivity.this);

            Call<JsonObject> jsonObjectCall = finisherService.updateTrialDate(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.body() != null && response.body().has("Success") && response.body().get("Success").getAsBoolean()) {
                        Log.e("TRIAL DATE UPDATE", "SUCCESSFUL");
                        sharedPreference.write("SEVENDAY_TRIAL_START", "", "TRUE");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }

    }

//    public void showAlertForPaidService(final String findOutUrl) {
//
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_show_gorgeious_alert);
//
//        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
//        RelativeLayout rlAddEmail = (RelativeLayout) dialog.findViewById(R.id.rlAddEmail);
//        RelativeLayout rlLogin = (RelativeLayout) dialog.findViewById(R.id.rlLogin);
//        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
//        TextView txtDescAnother = dialog.findViewById(R.id.txtDescAnother);
//        TextView txtAddEmail = dialog.findViewById(R.id.txtAddEmail);
//        TextView txtNoThanks = dialog.findViewById(R.id.txtNoThanks);
//
//        String mystring = new String("NO THANKS");
//        SpannableString content = new SpannableString(mystring);
//        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
//        txtNoThanks.setText(content);
//
//        txtDesc.setText("This program is not available to trial but can be purchased here.");
//        txtDescAnother.setText("Use code: ASHY72 for a 60% discount for the next 72 hours");
//
//        if (!findOutUrl.equals("")) {
//            txtAddEmail.setText("GET STARTED NOW");
//        } else {
//            txtAddEmail.setText("find out more");
//        }
//
//        imgCross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        txtNoThanks.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//
//        rlAddEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//
//                if (findOutUrl.equals("")) {
//                    String URL = "https://www.thesquadtours.com/Home/Redirect?mode=signup" + "&userId=" + new SharedPreference(MainActivity.this).read("UserID", "");
//                    Uri uri = Uri.parse(URL);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.setPackage("com.android.chrome");
//                    startActivity(intent);
//                } else {
//                    String URL = "";
//                    URL = findOutUrl + "&userId=" + new SharedPreference(MainActivity.this).read("UserID", "");
//                    Uri uri = Uri.parse(URL);
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.setPackage("com.android.chrome");
//                    startActivity(intent);
//                }
//            }
//        });
//
//        rlLogin.setOnClickListener(view -> {
//            dialog.dismiss();
//
//            Intent loginIntent = new Intent(MainActivity.this, UserPaidStatusActivity.class);
//            loginIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(loginIntent);
//        });
//
//        dialog.show();
//    }//commented by jyotirmoy




    @Override
    protected void onResume() {
        super.onResume();
        Log.i("fund","fund");
        //AppEventsLogger.activateApp(this);
        funDrawer1();
        try {
            if (intentFilter == null) {
                intentFilter = new IntentFilter();
                intentFilter.addAction(Util.INTENT_ACTION_TOKEN_REFRESHED);
            }
            registerReceiver(refreshFCMTokenReceiver, intentFilter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction("com.ashysystem.mbhq.networkconnectivity");
        registerReceiver(broadCastNewMessage, intentFilter2);

        IntentFilter intentFilter3 = new IntentFilter();
        intentFilter3.addAction("com.ashysystem.mbhq.meditationDownloadComplete");
        registerReceiver(meditationDownloadReceiver, intentFilter3);
    }

    ///////////////////////////// New 8 Week Challange ///////////////////////////

    @Override
    protected void onPause() {
        super.onPause();
        //AppEventsLogger.deactivateApp(this);
        try {
            unregisterReceiver(refreshFCMTokenReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            unregisterReceiver(broadCastNewMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            unregisterReceiver(meditationDownloadReceiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ////////////Gamification////////////


//    private void squadNutritionSettingStep() {
//
//        if (Connection.checkConnection(MainActivity.this)) {
//
//            progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
//
//            HashMap<String, Object> hashReq = new HashMap<>();
//            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
//            hashReq.put("Key", Util.KEY);
//            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//            Call<JsonObject> jsonObjectCall = finisherService.squadNutritionSettingStep(hashReq);
//            jsonObjectCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    progressDialog.dismiss();
//                    if (response.body() != null) {
//                        if (response.body().get("SuccessFlag").getAsBoolean()) {
//                            final int nutritionStepNo = response.body().get("StepNumber").getAsInt();
//                            sharedPreference.write("NUTRITIONSTEPNUMBER", "", String.valueOf(nutritionStepNo));
//                            if (nutritionStepNo == 0) {
//                                //((MainActivity)this).loadFragment(new CustomNutritionPlanFragment(),"CustomNutritionPlan",null);
//                                loadFragment(new NourishFragmentHome(), "Nourish", null);
//                            } else if (nutritionStepNo == 1) {
//                                loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
//                            } else if (nutritionStepNo == -1) {
//                                if (programStepNumber != null && (programStepNumber == 0 || programStepNumber > 2)) {
//                                    loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
//                                } else {
//                                    loadFragment(new NutritionSettingsPageOneGoalFragment(), "NutritionSettingsPageOneGoal", null);
//                                }
//                            } else if (nutritionStepNo == 2) {
//                                loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
//                            } else if (nutritionStepNo == 3) {
//                                loadFragment(new NutritionChooseMealFrequencyFragment(), "NutritionChooseMealFrequency", null);
//                            } else if (nutritionStepNo == 4) {
//                                loadFragment(new NutritionMealVarietyFragment(), "NutritionMealVariety", null);
//                            } else if (nutritionStepNo == 5) {
//                                loadFragment(new NutritionSelectMealPlanFragment(), "NutritionSelectMealPlan", null);
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    progressDialog.dismiss();
//                }
//            });
//
//        } else {
//            Util.showToast(MainActivity.this, Util.networkMsg);
//        }
//
//    }//commented by jyotirmoy

//    private void applyFontToMenuItem(MenuItem mi) {
//        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Oswald-Light.ttf");
//        SpannableString mNewTitle = new SpannableString(mi.getTitle());
//        mNewTitle.setSpan(new CustomTypefaceSpan("", font), 0, mNewTitle.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        mi.setTitle(mNewTitle);
//    } //commented by jyotirmoy

    /**
     * Open a web page of a specified URL
     */
    public void openSurvey() {
        Uri webpage = Uri.parse("https://www.surveymonkey.com/r/VKHHVMV");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

//    public void getAvailableCourse() {
//
//        if (Connection.checkConnection(MainActivity.this)) {
//            final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
//            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
//
//            HashMap<String, Object> hashReq = new HashMap<>();
//            hashReq.put("UserId", sharedPreference.read("UserID", ""));
//            hashReq.put("Key", Util.KEY);
//            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
//
//            FinisherServiceImpl finisherService = new FinisherServiceImpl(MainActivity.this);
//            Call<AvailableCourseModel> serverCall = finisherService.getAvailableCourse(hashReq);
//            serverCall.enqueue(new Callback<AvailableCourseModel>() {
//                @Override
//                public void onResponse(Call<AvailableCourseModel> call, Response<AvailableCourseModel> response) {
//                    progressDialog.dismiss();
//                    //Log.e("success", "su");
//                    if (response.body() != null) {
//                        AvailableCourseModel lstData = response.body();
//                        funEightWeek(lstData.getCourses());
//
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<AvailableCourseModel> call, Throwable t) {
//                    //Log.e("error", "er");
//                    progressDialog.dismiss();
//
//                }
//            });
//        } else {
//            Util.showToast(MainActivity.this, Util.networkMsg);
//        }
//    }//temporary

//    private void funEightWeek(List<AvailableCourseModel.Course> lstData) {
//        if (sharedPreference.read("USEREMAIL", "").equals("")) {
//            (MainActivity.this).openDialogForRegisterUser(null, "");
//        } else {
//            int position = -1;
//            for (int x = 0; x < lstData.size(); x++) {
//                if (lstData.get(x).getCourseType().equals("Week Challenge")) {
//                    position = x;
//                    break;
//                }
//            }
//            if (position != -1) {
//                if (lstData.get(position).getIsEnroll()) {
//                    String PLAY_EPISODE_ONE = "";
//                    CourseDetailsFragment courseDetailsFragment = new CourseDetailsFragment();
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("EnrollCourseId", lstData.get(position).getUserSquadCourseId());
//                    bundle.putInt("plainCourseid", lstData.get(position).getCourseId());
//                    bundle.putString("name", lstData.get(position).getCourseName());
//                    bundle.putString("type", lstData.get(position).getCourseType());
//                    bundle.putString("origin", "chk");
//                    bundle.putString("listback", "yes");
//                    if (!PLAY_EPISODE_ONE.equals("")) {
//                        bundle.putString("PLAY_EPISODE_ONE", "TRUE");
//                    }
//                    courseDetailsFragment.setArguments(bundle);
//                    loadFragment(courseDetailsFragment, "detail", null);
//                } else {
//                    makejson(lstData.get(position), lstData.get(position).getCourseType());
//
//                }
//            }
//
//        }
//    }//temporary

//    private void makejson(AvailableCourseModel.Course course, String weekType) {
//        JSONObject rootJson = new JSONObject();
//        JSONObject courseObj = new JSONObject();
//        try {
//            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
//            rootJson.put("UserID", sharedPreference.read("UserID", ""));
//            rootJson.put("Key", Util.KEY);
//            rootJson.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
//            courseObj.put("CourseType", course.getCourseType());
//            courseObj.put("isAllArticleRead", course.getIsAllArticleRead());
//            courseObj.put("CourseName", course.getCourseName());
//            courseObj.put("UserSquadCourseId", course.getUserSquadCourseId());
//            courseObj.put("isPeriodFinished", course.getIsPeriodFinished());
//            courseObj.put("CourseStartDate", getTodayDate());
//            courseObj.put("IsEnroll", course.getIsEnroll());
//            courseObj.put("CourseId", course.getCourseId());
//            courseObj.put("WeekNumber", course.getWeekNumber());
//            rootJson.put("CourseList", courseObj);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        HashMap<String, Object> queryHm = new HashMap<>();
//        try {
//            queryHm = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//            addCourseApi(queryHm, course.getCourseName(), weekType);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }//temporary

    private String getTodayDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        Date today = new Date();
        return format.format(today);
    }

    private void addCourseApi(HashMap<String, Object> queryHm, final String name, final String weekType) {

        if (Connection.checkConnection(MainActivity.this)) {
            final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(MainActivity.this);
            Call<AddCourseResponseModel> serverCall = finisherService.addCourse(queryHm);
            serverCall.enqueue(new Callback<AddCourseResponseModel>() {
                @Override
                public void onResponse(Call<AddCourseResponseModel> call, Response<AddCourseResponseModel> response) {
                    progressDialog.dismiss();
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
                                bundle.putBoolean("isfirstTime", true);
                                courseDetailsFragment.setArguments(bundle);
                                ((MainActivity) MainActivity.this).loadFragment(courseDetailsFragment, "detail", null);



                            } else
                                Util.showToast(MainActivity.this, "An error occurred");

                        } else
                            Util.showToast(MainActivity.this, "An error occurred");
                    }
                }

                @Override
                public void onFailure(Call<AddCourseResponseModel> call, Throwable t) {
                    //Log.e("error", "er");
                    progressDialog.dismiss();

                }
            });
        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }
    }

   /*commentout by sahenita*/
   /* public void postFireBaseEvent(String eventName, Bundle eventBundle) {
        if (TextUtils.isEmpty(eventName) || eventBundle == null)
            return;
        getmFirebaseAnalytics().logEvent(eventName, eventBundle);
    }*/
//////////////////Gamification//////////////

//    public void openGamificationPopUpNew(int colorCode, String subTitle, String colorName, Context context) {
//        if (getAccessCheck()) {
//            boolean apiCallRunning = false;
//            try {
//                if (ServiceSessionMain.client.dispatcher().runningCalls().size() > 0)
//                    apiCallRunning = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            try {
//                if (Service.client.dispatcher().runningCalls().size() > 0)
//                    apiCallRunning = true;
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            if (!apiCallRunning) {
//                if (popupGameShown) {
//                    Dialog dialogNew = new Dialog(context);
//                    dialogNew.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                    dialogNew.setContentView(R.layout.dialog_popup_game);
//                    RelativeLayout rlRange = (RelativeLayout) dialogNew.findViewById(R.id.rlRange);
//                    RelativeLayout rlSquare = (RelativeLayout) dialogNew.findViewById(R.id.rlSquare);
//                    ImageView imgTriangle = (ImageView) dialogNew.findViewById(R.id.imgTriangle);
//                    TextView txtRange = (TextView) dialogNew.findViewById(R.id.txtRange);
//                    TextView txtColorName = (TextView) dialogNew.findViewById(R.id.txtColorName);
//                    TextView txtLeveltitle = (TextView) dialogNew.findViewById(R.id.txtLeveltitle);
//                    dialogNew.show();
//                    txtRange.setText(subTitle + "");
//                    rlRange.setBackgroundColor(Color.parseColor("#01D0B9"));
//                    rlSquare.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(ContextCompat.getColor(this, colorCode))));
//                    txtColorName.setText(colorName.toUpperCase());
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//
//                        Drawable d = ContextCompat.getDrawable(MainActivity.this, R.drawable.triangle_vector_popup);
//                        d = DrawableCompat.wrap(d);
//                        DrawableCompat.setTint(d.mutate(), ContextCompat.getColor(MainActivity.this, colorCode));
//                        imgTriangle.setImageDrawable(d);
//                    } else {
//                        int color = ContextCompat.getColor(MainActivity.this, colorCode);
//                        Drawable drawable = DrawableCompat.wrap(ContextCompat.getDrawable(MainActivity.this, R.drawable.triangle_vector_popup));
//                        imgTriangle.setImageDrawable(drawable);
//                        drawable.mutate().setColorFilter(color, PorterDuff.Mode.SRC_IN);
//                    }
//
//                    String tmpText = txtLeveltitle.getText().toString();
//                    tmpText += "\n";
//                    tmpText += subTitle;
//                    tmpText += "\n";
//                    tmpText += colorName;
//                    txtLeveltitle.setText(tmpText);
//                    dialogNew.setOnDismissListener(dialogInterface -> {
//                        if (onDialogCloseListener != null)
//                            onDialogCloseListener.onClose();
//                    });
//                    final Handler handler = new Handler();
//                    handler.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (dialogNew.isShowing()) {
//                                dialogNew.dismiss();
//                            }
//                        }
//                    }, 5000);
//                }
//            }
//
//
//        }
//
//    }//commented by jyotirmoy

    public void setOnDialogCloseListener(OnDialogCloseListener onDialogCloseListener) {
        this.onDialogCloseListener = onDialogCloseListener;
    }

    public void setOnapiResponse(OnApiResponseListener onapiResponse) {
        this.onApiResponseListener = onapiResponse;
    }

    public void refershGamePoint(Context context) {
        popupGameShown = true;

        checkMeditationCacheExpiration();
    }

//    private void getGameProfileApi(TextView txtGamificationCount, Context context) {
//        if (Connection.checkConnection(this)) {
//            final SharedPreference sharedPreference = new SharedPreference(this);
//
//            HashMap<String, Object> hashReq = new HashMap<>();
//            hashReq.put("UserId", sharedPreference.read("ABBBCOnlineUserId", ""));
//            hashReq.put("Key", Util.KEY_ABBBC);
//            hashReq.put("UserSessionID", sharedPreference.read("ABBBCOnlineUserSessionId", ""));
//
//
//            SessionServiceImpl sessionService = new SessionServiceImpl(this);
//            Call<JsonObject> serverCall = sessionService.GetUserRewardPoints(hashReq);
//            serverCall.enqueue(new Callback<JsonObject>() {
//                @Override
//                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
//                    if (progressDialog != null)
//                        progressDialog.dismiss();
//                    if (response.body() != null) {
//                        if (response.body().get("Success").getAsBoolean()) {
//                            int totalCount = 0;
//                            totalCount = response.body().get("RewardPoints").getAsInt();
//                            countPoint(totalCount, txtGamificationCount, context);
//
//                        }
//                    } else
//                        txtGamificationCount.setText(0 + "");
//                }
//
//                @Override
//                public void onFailure(Call<JsonObject> call, Throwable t) {
//                    t.printStackTrace();
//                    if (progressDialog != null)
//                        progressDialog.dismiss();
//
//                }
//            });
//        } else {
//            Util.showToast(this, Util.networkMsg);
//        }
//    }//commented by jyotirmoy

    private void countPoint(int totalWeekCount, TextView txtGamificationCount, Context context) {

        try {
            txtGamificationCount.setText(totalWeekCount + "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openRatingBar() {

        Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_rating);
        TextView txtNot = dialog.findViewById(R.id.txtNot);
        TextView txtRate = dialog.findViewById(R.id.txtRate);
        RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);

        txtRate.setOnClickListener(view -> {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            new SharedPreference(MainActivity.this).write("rating", "", "");
            dialog.dismiss();
        });

        txtNot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @SuppressLint("CheckResult")
    protected void chatLogout() {

    }

    @Override
    public void onLoadFragmentRequest(Fragment fragment, String title, Bundle bundle) {
        if (fragment != null) {
            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    loadFragment(fragment, title, bundle);
                }
            }, 1000);

        }
    }

    private class MyAsyncTask1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {

            Log.e(TAG, "calleddelete:" + "yes");
            Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(MainActivity.this).gratitudeDao().deleteAllGratitudeNew());




            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            Log.e("saved in database", "yes");

        }
    }
    ///////////////////////////  CHAT ////////////////////////////////

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(broadcastReceiver);



        if (mCountHandler != null) {
            mCountHandler.removeCallbacks(mCountRunnable);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == SHARE_ONLY_READ_WRITE_CAMERA_PERMISSION_REQUEST_CODE) {
            if (globalRlonlyShareUsinTemplate != null) {
                funcForShareImageUsingTemplateOption(globalRlonlyShareUsinTemplate);
            }
        } else if (requestCode == CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission cam", "Granted");
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                out = createFolder();
                imgPath = out.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(MainActivity.this,
                        "com.ashysystem.mbhq" +
                                ".fileprovider", out);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST_CODE);

            }
        } else if (requestCode == PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_IMAGE_FROM_GALLERY_CODE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean getAccessCheck() {
        if (new SharedPreference(MainActivity.this).read("WELCOMECHECKONE", "").equals("TRUE") && new SharedPreference(MainActivity.this).read("WELCOMECHECKTWO", "").equals("TRUE") && new SharedPreference(MainActivity.this).read("WELCOMECHECKTHREE", "").equals("TRUE") && new SharedPreference(MainActivity.this).read("WELCOMECHECKFOUR", "").equals("TRUE")) {
            return true;
        } else {
            return false;
        }
    }

//    public void checkMiniProgramForExerciseApiCall() {
//
//        if (Connection.checkConnection(this)) {
//            final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Please wait...");
//            SharedPreference sharedPreference = new SharedPreference(this);
//            HashMap<String, Object> hashReq = new HashMap<>();
//            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
//            hashReq.put("SquadUserId", Integer.parseInt(sharedPreference.read("UserID", "")));
//            hashReq.put("Key", Util.KEY_ABBBC);
//            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));
//
//            SessionServiceImpl sessionService = new SessionServiceImpl(this);
//            Call<SetProgramsSettingsModel> setProgramsSettingsModelCall = sessionService.checkMiniProgramForExercise(hashReq);
//
//            setProgramsSettingsModelCall.enqueue(new Callback<SetProgramsSettingsModel>() {
//                @Override
//                public void onResponse(Call<SetProgramsSettingsModel> call, Response<SetProgramsSettingsModel> response) {
//                    progressDialog.dismiss();
//
//                    if (response.body() != null && response.body().getSuccess()) {
//                        if (response.body().getSquadMiniProgramModel().getProgramName() != null && response.body().getSquadMiniProgramModel().getProgramId() != null && response.body().getSquadMiniProgramModel().getProgramId() > 0) {
//
//                            AlertDialogCustom alertDialogCustom = new AlertDialogCustom(MainActivity.this);
//                            alertDialogCustom.ShowDialog("Alert!", "Are you certain you want to change your workout settings? This will change your custom workout plan from next week onward.", true);
//                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
//                                @Override
//                                public void onDone(String title) {
//                                    loadFragment(new ExerciseSettingFragmentMain(), "ExerciseSetting", null);
//                                }
//
//                                @Override
//                                public void onCancel(String title) {
//
//                                }
//                            });
//
//
//                        } else {
//                            AlertDialogCustom alertDialogCustom = new AlertDialogCustom(MainActivity.this);
//                            alertDialogCustom.ShowDialog("Alert!", "Are you certain you want to change your workout settings? This will change your custom workout plan from next week onward.", true);
//                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
//                                @Override
//                                public void onDone(String title) {
//                                    loadFragment(new ExerciseSettingFragmentMain(), "ExerciseSetting", null);
//                                }
//
//                                @Override
//                                public void onCancel(String title) {
//
//                                }
//                            });
//                        }
//                    }
//
//                }
//
//                @Override
//                public void onFailure(Call<SetProgramsSettingsModel> call, Throwable t) {
//                    progressDialog.dismiss();
//                }
//            });
//
//        }
//
//    }//commented by jyotirmoy

    private void showAlertDialog(String alertMessage, String TYPE) {

        AlertDialogWithCustomButton alertDialogCustom = new AlertDialogWithCustomButton(this);

        if (!TYPE.equals("")) {
            alertDialogCustom.ShowDialog("Alert!", alertMessage, true, "REVERT", "CLOSE");

        } else {
            alertDialogCustom.ShowDialog("Alert!", alertMessage, true, "OK", "CANCEL");
        }

        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
            @Override
            public void onDone(String title) {

                if (title.equals("REVERT")) {
                    Bundle bundle = new Bundle();
                    bundle.putString("FROMPAGE", "SETTINGS");
                    loadFragment(new SetProgramsFragmentNew(), "SetPrograms", bundle);
                } else {

                }
            }

            @Override
            public void onCancel(String title) {

            }
        });

    }



//    private void openDialogForPromotion(PromotionalChallengeInfo promoJsonObject) {
//
//        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_showpromotionalinfo);
//
//        ImageView imgCross = (ImageView) dialog.findViewById(R.id.imgCross);
//        RelativeLayout rlAddEmail = (RelativeLayout) dialog.findViewById(R.id.rlAddEmail);
//        TextView txtDesc = dialog.findViewById(R.id.txtDesc);
//        TextView txtTitle = dialog.findViewById(R.id.txtTitle);
//        TextView txtAddEmail = dialog.findViewById(R.id.txtAddEmail);
//        TextView txtNoThanks = dialog.findViewById(R.id.txtNoThanks);
//
//        String mystring = new String("NO THANKS");
//        SpannableString content = new SpannableString(mystring);
//        content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
//        txtNoThanks.setText(content);
//
//
//        if (promoJsonObject != null) {
//            txtTitle.setText(promoJsonObject.getTitle());
//            txtDesc.setText(promoJsonObject.getBody());
//            txtAddEmail.setText(promoJsonObject.getActionTitle());
//        }
//
//        imgCross.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        txtNoThanks.setOnClickListener(view -> {
//            dialog.dismiss();
//        });
//
//        rlAddEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//
//                try {
//                    String promotionUrl = promoJsonObject.getFromController() + "&userId=" + new SharedPreference(MainActivity.this).read("UserID", "");
//
//                    Uri uri = Uri.parse(promotionUrl); // missing 'http://' will cause crashed
//                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//                    intent.setPackage("com.android.chrome");
//                    startActivity(intent);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        dialog.show();
//
//    }//commented out by jyotirmoy

    public void showDialogForNutritionExerciseSetting() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
        builder1.setTitle("ALERT!");
        builder1.setMessage("Are you sure you want to exit?\n" +
                "\n" +
                "We need your goals and preferences to create your custom nutrition and workout program and set up your challenges.\n" +
                "\n" +
                "Please continue!");
        builder1.setCancelable(true);
        builder1.setPositiveButton("CONTINUE",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        builder1.setNegativeButton("EXIT",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        if (getAccessCheck()) {
                            Log.i("called landind","22");
                            loadFragment(new MbhqTodayMainFragment(), "Today", null);
                        } else {
                            loadFragment(new WelcomeScrenCheckLIstFragment(), "WelcomeScrenCheckLIst", null);
                        }

                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
/*commentout by sahenita*/
/*
    public void checkMiniProgramForNutritionApiCall(String NAVIGATION) {

        if (Connection.checkConnection(this)) {
            final ProgressDialog progressDialog = ProgressDialog.show(this, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(this);
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserId", "")));
            hashReq.put("SquadUserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY_ABBBC);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("ABBBCOnlineUserSessionId", "")));

            SessionServiceImpl sessionService = new SessionServiceImpl(this);
            Call<SetProgramsSettingsModel> setProgramsSettingsModelCall = sessionService.checkMiniProgramForNutrition(hashReq);
            setProgramsSettingsModelCall.enqueue(new Callback<SetProgramsSettingsModel>() {
                @Override
                public void onResponse(Call<SetProgramsSettingsModel> call, Response<SetProgramsSettingsModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().getSuccess()) {

                        if (NAVIGATION.equals("GOTO_SELECT_MEAL_PLAN")) {
                            Bundle bundle = new Bundle();
                            bundle.putString("GOTO_SELECT_MEAL_PLAN", "TRUE");
                            loadFragment(new NutritionSelectMealPlanFragment(), "NutritionSelectMealPlan", bundle);
                            setTargetFragment("NutritionSettingsMain");
                        } else if (response.body().getSquadMiniProgramModel().getProgramName() != null && response.body().getSquadMiniProgramModel().getProgramId() != null && response.body().getSquadMiniProgramModel().getProgramId() > 0) {


                            Bundle bundle = new Bundle();
                            bundle.putString("SET_PROGRAM_RUNNING", "TRUE");
                            loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", bundle);

                        } else {
                            AlertDialogCustom alertDialogCustom = new AlertDialogCustom(MainActivity.this);
                            alertDialogCustom.ShowDialog("Alert!", "Are you certain you want to change your nutrition settings? This will change your nutrition plan from today onward.", true);
                            alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                                @Override
                                public void onDone(String title) {
                                    if (NAVIGATION.equals("GOTO_SELECT_MEAL_PLAN")) {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("GOTO_SELECT_MEAL_PLAN", "TRUE");
                                        loadFragment(new NutritionSelectMealPlanFragment(), "NutritionSelectMealPlan", bundle);
                                        setTargetFragment("NutritionSettingsMain");
                                    } else {
                                        loadFragment(new NutritionSettingsMainFragmnet(), "NutritionSettingsMain", null);
                                        setTargetFragment("NutritionSettingsMain");
                                    }
                                }

                                @Override
                                public void onCancel(String title) {

                                }
                            });
                        }
                    }

                }

                @Override
                public void onFailure(Call<SetProgramsSettingsModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });


        } else {
            Util.showToast(this, Util.networkMsg);
        }
    }
*/

    public void funTabBarforHabits() {
        /////////////////
        String habit_access=sharedPreference.read("HabitAccess","");
        String accesstype=sharedPreference.read("accesstype","");

        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgGratitude.setImageResource(0);
        imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);


        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(habit_access)){
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
                imgHabits.setAlpha(0.5f);

            }else{
                imgHabits.setImageResource(0);
                imgHabits.setImageResource(R.drawable.mbhq_habits_active);
            }
        }else{
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_active);
        }

        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
        //////////////
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        String[] arrTab = {"HABIT LIST"/*, "FUTURE YOU"*/, "BUCKET LIST"};
        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
        horTab.setVisibility(View.VISIBLE);
        LinearLayout llHorTab = findViewById(R.id.llHorTab);
        llHorTab.removeAllViews();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            horTab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    Log.e("SCROLL", "scrollX----" + scrollX + "----oldScrollX----" + oldScrollX);
                    View view = (View) horTab.getChildAt(horTab.getChildCount() - 1);
                    int diff = (view.getRight() - (horTab.getWidth() + horTab.getScrollX()));
                    Log.e("print diff scrolla--", diff + "???");
                    if (arrTab.length > 3 && scrollX == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.GONE);
                    } else if (arrTab.length > 3 && (scrollX - oldScrollX) > 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.GONE);
                    } else if (arrTab.length > 3 && diff == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.GONE);
                    } else {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.GONE);
                    }
                    if (Math.abs(scrollX - oldScrollX) > SlowDownThreshold) {
                        Log.e("SCROLL", "123");
                        Sensey.getInstance().stopTouchTypeDetection();
                    } else {
                        funSensey();
                        Log.e("SCROLL", "456");
                    }
                }
            });
        }

        ArrayList<TextView> arrTextView = new ArrayList<>();
        ArrayList<RelativeLayout> arrRelative = new ArrayList<>();
        for (int i = 0; i < arrTab.length; i++) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_dynamic_tab, null);
            TextView textView = view.findViewById(R.id.txtTab);
            RelativeLayout rlMain = (RelativeLayout) view.findViewById(R.id.rlMain);
            textView.setId(i);
            textView.setText(arrTab[i]);
            Log.e("print tab name--", arrTab[i] + "???");

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            rlMain.setLayoutParams(param);

            llHorTab.addView(view);
            int finalI = i;
            arrTextView.add(textView);
            arrRelative.add(rlMain);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("Print  name src", "???" + currentFragmentTab.getClass().getSimpleName() + "????");
                    clearTextSelection(arrTextView, arrRelative);
                    String lastKey = sharedPreference.read("achieve_nav", "");

                    try {
                        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                        int x = arrRelative.get(finalI).getLeft() - 110;
                        horTab.post(new Runnable() {
                            @Override
                            public void run() {
                                horTab.smoothScrollTo(x, 0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (finalI == 0) {
                        if (restrictionBool()) {
                            funTrainRestriction();
                        } else {
                            if (allowAccess()) {

                                arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                                arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                                arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                                Bundle bundle = new Bundle();
                                bundle.putString("where", "custom");
                                sharedPreference.write("achieve_nav", "", "habit_hacker");
                                sharedPreference.write("achieve_nav_pos", "", finalI + "");
                                rlLeftTab = null;
                                rlRightTab = arrRelative.get(1);
                                loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
                            }
                        }

                    } else if (finalI == 1) {

                        if (restrictionBool()) {
                            funTrainRestriction();

                        } else {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            loadFragment(new BucketListFragment(), "BucketList", null);
                            sharedPreference.write("achieve_nav", "", "bucket_list");
                            sharedPreference.write("achieve_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(0);
                            rlRightTab = null;
                        }

                    } else if (finalI == 2) {
                        if (restrictionBool()) {
                            funTrainRestriction();

                        } else {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            loadFragment(new BucketListFragment(), "BucketList", null);
                            sharedPreference.write("achieve_nav", "", "bucket_list");
                            sharedPreference.write("achieve_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(1);
                            rlRightTab = null;
                        }
                    }
                }
            });
        }

        funTabselection("achieve_nav_pos", arrRelative, arrTextView, llHorTab, horTab);

        funDrawer1();
    }

    public void funTabBarforGratitude() {
        String eq_access=sharedPreference.read("EqJournalAccess","");
        String accesstype=sharedPreference.read("accesstype","");

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
                imgGratitude.setAlpha(0.5f);

            }else{
                imgGratitude.setImageResource(0);
                imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);
            }
        }else{
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);
        }

        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgHabits.setImageResource(0);
        imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
        /////////////////////////////
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        String[] arrTab = {"GRATITUDE", "EQ JOURNAL"/*, "MEDITATIONS"*/};
        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
        horTab.setVisibility(View.VISIBLE);
        LinearLayout llHorTab = findViewById(R.id.llHorTab);
        llHorTab.removeAllViews();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            horTab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    View view = (View) horTab.getChildAt(horTab.getChildCount() - 1);
                    int diff = (view.getRight() - (horTab.getWidth() + horTab.getScrollX()));
                    Log.e("print diff scrollb--", diff + "???");
                    if (scrollX == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if ((scrollX - oldScrollX) > 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if (diff == 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.GONE);
                    } else {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    }


                    Log.e("SCROLL", "scrollX>>>" + scrollX + "oldScrollX>>>" + oldScrollX);

                    ///////////////////////////////////
                    if (Math.abs(scrollX - oldScrollX) > SlowDownThreshold) {
                        Log.e("SCROLL", "123");
                        Sensey.getInstance().stopTouchTypeDetection();
                    } else {
                        funSensey();
                        Log.e("SCROLL", "456");
                    }
                }
            });
        }

        ArrayList<TextView> arrTextView = new ArrayList<>();
        ArrayList<RelativeLayout> arrRelative = new ArrayList<>();
        for (int i = 0; i < arrTab.length; i++) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_dynamic_tab, null);
            TextView textView = view.findViewById(R.id.txtTab);
            RelativeLayout rlMain = (RelativeLayout) view.findViewById(R.id.rlMain);

            textView.setId(i);
            textView.setText(arrTab[i]);

            textView.setSingleLine(true);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18); //added by jyotirmoy->j8

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            rlMain.setLayoutParams(param);

            llHorTab.addView(view);
            int finalI = i;
            arrTextView.add(textView);
            arrRelative.add(rlMain);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTextSelection(arrTextView, arrRelative);

                    try {
                        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                        int x = arrRelative.get(finalI).getLeft() - 110;
                        horTab.post(new Runnable() {
                            @Override
                            public void run() {
                                horTab.smoothScrollTo(x, 0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (finalI == 0) {
                        if (allowAccess()) {
                            if (Util.checkConnection(MainActivity.this)) {
                                arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                                arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                                arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                                Bundle bundle = new Bundle();
                                bundle.putString("where", "customnut");
                                sharedPreference.write("appreciate_nav", "", "gratitude");
                                sharedPreference.write("appreciate_nav_pos", "", finalI + "");
                                rlLeftTab = null;
                                rlRightTab = arrRelative.get(1);

                                loadFragment(new GratitudeMyListFragment(), "GratitudeMyList", null);
                            } else {
                                Log.i("called landind","23");
                                loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                            }

                        }
                    } else if (finalI == 1) {
                        if (allowAccess()) {
                            if (Util.checkConnection(MainActivity.this)) {
                                arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                                arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                                arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                                sharedPreference.write("appreciate_nav", "", "growth");
                                sharedPreference.write("appreciate_nav_pos", "", finalI + "");
                                rlLeftTab = arrRelative.get(0);
                                rlRightTab = null;
                                fungratitudeicon();
                                loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                            } else {
                                Log.i("called landind","24");
                                loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                            }
                        }
                    } else if (finalI == 2) {
                        arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                        arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));

                        sharedPreference.clear("med");
                        sharedPreference.clear("medT");


                        Bundle bundle = new Bundle();
                        bundle.putString("WEBINARSTATUS", "MEDITATION");
                        loadFragment(new MeditationFragment(), "MeditationFragment", null);

                        sharedPreference.write("appreciate_nav", "", "meditation");
                        sharedPreference.write("appreciate_nav_pos", "", finalI + "");
                        rlLeftTab = arrRelative.get(1);
                        rlRightTab = null;
                    } else if (finalI == 3) {
                        if (allowAccess()) {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            sharedPreference.write("appreciate_nav", "", "quote_gallery");
                            sharedPreference.write("appreciate_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(2);
                            rlRightTab = null;

                            loadFragment(new QuoteGalleryFragment(), "QuoteGallery", null);
                        }
                    }
                }


            });
        }

        funTabselection("appreciate_nav_pos", arrRelative, arrTextView, llHorTab, horTab);
        funDrawer1();
    }

    public void funTabBarforTrack() {
        //////////////////
        imgHabits.setImageResource(0);
        imgHabits.setImageResource(R.drawable.mbhq_habits_active);
        imgGratitude.setImageResource(0);
        imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
        //////////////////
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        String[] arrTab = {"ALL", "PHOTOS", "MEASURE", "VITAMINS", "QUESTIONNAIRE", "FBWTRACKER"};
        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
        horTab.setVisibility(View.VISIBLE);
        LinearLayout llHorTab = findViewById(R.id.llHorTab);
        llHorTab.removeAllViews();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            horTab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    View view = (View) horTab.getChildAt(horTab.getChildCount() - 1);
                    int diff = (view.getRight() - (horTab.getWidth() + horTab.getScrollX()));
                    Log.e("print diff scrollc--", diff + "???");
                    if (scrollX == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if ((scrollX - oldScrollX) > 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if (diff == 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.GONE);
                    } else {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    }

                    if (Math.abs(scrollX - oldScrollX) > SlowDownThreshold) {
                        Log.e("SCROLL", "123");
                        Sensey.getInstance().stopTouchTypeDetection();
                    } else {
                        funSensey();
                        Log.e("SCROLL", "456");
                    }
                }
            });
        }
        ArrayList<TextView> arrTextView = new ArrayList<>();
        ArrayList<RelativeLayout> arrRelative = new ArrayList<>();

        for (int i = 0; i < arrTab.length; i++) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_dynamic_tab, null);
            TextView textView = view.findViewById(R.id.txtTab);
            textView.setId(i);
            textView.setText(arrTab[i]);
            llHorTab.addView(view);
            int finalI = i;
            arrTextView.add(textView);
            RelativeLayout rlMain = (RelativeLayout) view.findViewById(R.id.rlMain);
            arrRelative.add(rlMain);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTextSelection(arrTextView, arrRelative);

                    try {
                        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                        int x = arrRelative.get(finalI).getLeft() - 110;
                        horTab.post(new Runnable() {
                            @Override
                            public void run() {
                                horTab.smoothScrollTo(x, 0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (finalI == 0) {
                        {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));

                            loadFragment(new TrackAllFragment(), "TrackAllFragment", null);
                            sharedPreference.write("track_nav", "", "TrackAllFragment");
                            sharedPreference.write("track_nav_pos", "", finalI + "");
                            rlLeftTab = null;
                            rlRightTab = arrRelative.get(1);
                        }


                    } else if (finalI == 1 && allowAccess()) {
                        {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            Bundle bundle = new Bundle();
                            bundle.putString("where", "photo");
                            loadFragment(new TrackMyPhotoFragment(), "TrackMyPhoto", bundle);
                            sharedPreference.write("track_nav", "", "photo");
                            sharedPreference.write("track_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(0);
                            rlRightTab = arrRelative.get(2);
                        }

                    } else if (finalI == 2 && allowAccess()) {
                        {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            Bundle bundle = new Bundle();
                            bundle.putString("where", "weigh");
                            loadFragment(new BodyCompositionFragment(), "LatestResultTrack", bundle);
                            sharedPreference.write("track_nav", "", "weigh");
                            sharedPreference.write("track_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(1);
                            rlRightTab = arrRelative.get(3);
                        }
                    } else if (finalI == 3 && allowAccess()) {
                        {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            Bundle bundle = new Bundle();
                            bundle.putString("key", "today");
                            loadFragment(new VitaminsFragment(), "Track", bundle);
                            sharedPreference.write("track_nav", "", "vitamin");
                            sharedPreference.write("track_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(2);
                            rlRightTab = arrRelative.get(4);
                        }
                    } else if (finalI == 4) {
                        {
                            funDrawer1();
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            Bundle bundle = new Bundle();
                            bundle.putString("where", "question");
                            loadFragment(new QuestionariesFragment(), "QuestionariesFragment", null);
                            sharedPreference.write("track_nav", "", "question");
                            sharedPreference.write("track_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(3);
                            rlRightTab = null;
                        }
                    } else if (finalI == 5) {
                        {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            Bundle bundle = new Bundle();
                            bundle.putString("where", "question");
                            loadFragment(new FbwTrackHome(), "FbwTrackHome", null);
                            sharedPreference.write("track_nav", "", "question");
                            sharedPreference.write("track_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(3);
                            rlRightTab = null;
                        }
                    }

                }
            });
        }
        funTabselection("track_nav_pos", arrRelative, arrTextView, llHorTab, horTab);



    }

    public void funTabBarforProgram() {
        ///////////////////
        String  Course_access=sharedPreference.read("CourseAccess","");
        if("true".equalsIgnoreCase(Course_access)) {
            imgCourses.setImageResource(0);
            imgCourses.setImageResource(R.drawable.mbhq_learn_active);
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
            imgMeditation.setImageResource(0);
            imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
            imgToday.setImageResource(0);
            imgToday.setImageResource(R.drawable.mbhq_today_inactive);
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        }else{
            imgCourses.setImageResource(0);
            imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
            imgCourses.setAlpha(0.5f);
            imgGratitude.setImageResource(0);
            imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
            imgMeditation.setImageResource(0);
            imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
            imgToday.setImageResource(0);
            imgToday.setImageResource(R.drawable.mbhq_today_inactive);
            imgHabits.setImageResource(0);
            imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
        }



        ///////////
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        String[] arrTab = {"COURSES", "TESTS"};
        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
        horTab.setVisibility(View.VISIBLE);
        LinearLayout llHorTab = findViewById(R.id.llHorTab);
        llHorTab.removeAllViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            horTab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    View view = (View) horTab.getChildAt(horTab.getChildCount() - 1);
                    int diff = (view.getRight() - (horTab.getWidth() + horTab.getScrollX()));
                    Log.e("print diff scrolld--", diff + "???");
                    if (scrollX == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if ((scrollX - oldScrollX) > 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if (diff == 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.GONE);
                    } else {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    }

                    if (Math.abs(scrollX - oldScrollX) > SlowDownThreshold) {
                        Log.e("SCROLL", "123");
                        Sensey.getInstance().stopTouchTypeDetection();
                    } else {
                        funSensey();
                        Log.e("SCROLL", "456");
                    }
                }
            });
        }
        ArrayList<TextView> arrTextView = new ArrayList<>();
        ArrayList<RelativeLayout> arrRelative = new ArrayList<>();
        for (int i = 0; i < arrTab.length; i++) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_dynamic_tab, null);
            TextView textView = view.findViewById(R.id.txtTab);
            RelativeLayout rlMain = (RelativeLayout) view.findViewById(R.id.rlMain);
            textView.setId(i);
            textView.setText(arrTab[i]);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            rlMain.setLayoutParams(param);

            llHorTab.addView(view);
            int finalI = i;
            arrTextView.add(textView);
            arrRelative.add(rlMain);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTextSelection(arrTextView, arrRelative);

                    try {
                        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                        int x = arrRelative.get(finalI).getLeft() - 110;
                        horTab.post(new Runnable() {
                            @Override
                            public void run() {
                                horTab.smoothScrollTo(x, 0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (finalI == 0) {
                        {
                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            Bundle bundle = new Bundle();
                            bundle.putString("NEWHOME", "true");
                            loadFragment(new CourseFragment(), "Course", bundle);
                            ///////////////////
                            sharedPreference.write("learn_nav", "", "program");
                            sharedPreference.write("learn_nav_pos", "", finalI + "");
                            rlLeftTab = null;
                            rlRightTab = arrRelative.get(1);
                        }


                    } else if (finalI == 1 && allowAccess()) {

                        // rlDailyWorkout.performClick();
                        if (sharedPreference.read("PROGRAM_PURCHASE_ONLY", "").equals("TRUE")) {
                            Bundle bun = new Bundle();
                            bun.putString("FOR_PAGE", "FORUM");
                            clearCacheForParticularFragment(new OnlyProgramPurchasedFragment());
                            loadFragment(new OnlyProgramPurchasedFragment(), "OnlyProgramPurchased", bun);
                        } else {
                            funDrawer1();

                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            Bundle bundle = new Bundle();
                            bundle.putString("where", "question");
                            //loadFragment(new TrackFragment(), "TrackFragment", bundle);
                            loadFragment(new QuestionariesFragment(), "QuestionariesFragment", null);
                            sharedPreference.write("learn_nav", "", "question");
                            sharedPreference.write("learn_nav_pos", "", finalI + "");
                            rlLeftTab = arrRelative.get(0);
                            rlRightTab = null;
                        }
                    } else if (finalI == 2 && allowAccess()) {
                        arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                        arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                        Bundle bundle = new Bundle();
                        bundle.putString("where", "webin");
                        //loadFragment(new LearnFragment(), "LearnFragment", bundle);
                        setTargetFragment("Learn");
                        loadFragment(new WebinarFragment(), "Webinar", null);
                        sharedPreference.write("learn_nav", "", "webin");
                        sharedPreference.write("learn_nav_pos", "", finalI + "");
                        rlLeftTab = arrRelative.get(1);
                        rlRightTab = arrRelative.get(3);

                    } else if (finalI == 3) {
                        arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                        arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                        loadFragment(new PersonalChallengeFragment(), "PersonalChallenge", null);
                        sharedPreference.write("learn_nav", "", "personal");
                        sharedPreference.write("learn_nav_pos", "", finalI + "");
                        rlLeftTab = arrRelative.get(2);
                        rlRightTab = null;

                    }

                }
            });
        }
        funTabselection("learn_nav_pos", arrRelative, arrTextView, llHorTab, horTab);



    }

    public void funTabselection(String key, ArrayList<RelativeLayout> arrRelative, ArrayList<TextView> arrTextView, LinearLayout llHorTab, HorizontalScrollView horTab) {
        String tabPos = sharedPreference.read(key, "");
        int globalTabNo = 0;
        if (!tabPos.equals("")) {
            int tabNo = Integer.parseInt(tabPos);
            globalTabNo = tabNo;
            arrTextView.get(tabNo).setTextColor(Color.parseColor("#01D0B9"));
            arrTextView.get(tabNo).setTypeface(null, Typeface.BOLD);
            arrRelative.get(tabNo).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
            if (tabNo > 0)
                rlLeftTab = arrRelative.get(tabNo - 1);
            else
                rlLeftTab = null;
            if (tabNo < arrRelative.size() - 1)
                rlRightTab = arrRelative.get(tabNo + 1);
            else
                rlRightTab = null;
        } else {
            if (arrTextView.size() > 0) {
                arrTextView.get(0).setTextColor(Color.parseColor("#01D0B9"));
                arrTextView.get(0).setTypeface(null, Typeface.BOLD);
            }
            if (arrRelative.size() > 0)
                arrRelative.get(0).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));

            rlLeftTab = null;
            rlRightTab = arrRelative.get(0);
        }

        Log.e("global no--", globalTabNo + "???");

        //////////////

    }

    public void clearTextSelection(ArrayList<TextView> arrTextView, ArrayList<RelativeLayout> arrRelative) {
        for (int x = 0; x < arrTextView.size(); x++) {
            arrTextView.get(x).setTextColor(Color.parseColor("#333333"));
            arrTextView.get(x).setTypeface(null, Typeface.NORMAL);
        }
        if (arrRelative != null) {
            for (int x = 0; x < arrRelative.size(); x++) {
                arrRelative.get(x).setBackgroundColor(Color.parseColor("#F6F6F6"));
            }
        }

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        try {
            Sensey.getInstance().setupDispatchTouchEvent(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.dispatchTouchEvent(event);
    }
    /////////////////////
/*commentout by sahenita*/
/*    private boolean tabInstanceCheck() {
        if (currentFragmentTab instanceof HabitHackerListFragment || currentFragmentTab instanceof VisionBoardHome || currentFragmentTab instanceof BucketListFragment || currentFragmentTab instanceof GratitudeMyListFragment || currentFragmentTab instanceof MyAchievementsFragment || currentFragmentTab instanceof MeditationFragment || currentFragmentTab instanceof CourseFragment || currentFragmentTab instanceof QuestionariesFragment *//*|| currentFragmentTab instanceof CourseFragment || currentFragmentTab instanceof SetProgramsFragment || currentFragmentTab instanceof PersonalChallengeFragment || currentFragmentTab instanceof NourishFragmentHome || currentFragmentTab instanceof CustomNutritionPlanFragmentNew || currentFragmentTab instanceof NourishShoppingListFragment || currentFragmentTab instanceof MealMatchListFragment || currentFragmentTab instanceof NutritionRecipeFragment || currentFragmentTab instanceof SquadDailyMealFragment || currentFragmentTab instanceof SquadDailyMealDetailFragment || currentFragmentTab instanceof SavedNutritionPlanFragment || currentFragmentTab instanceof MealPrepHelperFragment || currentFragmentTab instanceof RecipeAddFragment || currentFragmentTab instanceof TrainFragment || currentFragmentTab instanceof CustomProgramFinishFragment || currentFragmentTab instanceof TimerModeFragment || currentFragmentTab instanceof SessionListFragment || currentFragmentTab instanceof CategoryFragment || currentFragmentTab instanceof TrainOptionFragment || currentFragmentTab instanceof MealPrepNewAdvanceSearchFragment || currentFragmentTab instanceof SessionSearchFragment || currentFragmentTab instanceof MealOfTheDayFragment || currentFragmentTab instanceof FBWWalkTrack || currentFragmentTab instanceof SetProgramsFragmentNew*//*) {
            return true;
        } else
            return false;
    }*/

    private boolean allowCache(Fragment fragment) {
        if (fragment instanceof MbhqTodayMainFragment ||
                fragment instanceof MealMatchAddMealFoodDrinkConsumeFragment ||
                fragment instanceof VideoPlayerFragment ||
                fragment instanceof NutritionSelectMealPlanFragment ||
                fragment instanceof NutritionSettingsPageOneGoalFragment ||
                fragment instanceof NutritionDieteryPrefFragment ||
                fragment instanceof NutritionChooseMealFrequencyFragment ||
                fragment instanceof NutritionMealVarietyFragment ||
                fragment instanceof CustomProgramPageOneFragment ||
                fragment instanceof CustomProgramPageTwoFragment ||
                fragment instanceof CustomProgramPageThreeFragment ||
                fragment instanceof CustomProgramPageFourFragment ||
                fragment instanceof CustomProgramPageFiveFragment ||
                fragment instanceof CustomProgramPageSixFragment ||
                fragment instanceof CustomProgramPageSevenFragment ||
                fragment instanceof MealPrepNewAdvanceSearchFragment ||
                fragment instanceof NourishShoppingListFragment ||
                currentFragmentTab instanceof SessionSearchFragment ||
                fragment instanceof AddVitaminFragment ||
                fragment instanceof VitaminReminderGroupFragment ||
                currentFragmentTab instanceof SquadDailyListFragment ||
                currentFragmentTab instanceof AddEditCustomNutritionMeal ||
                currentFragmentTab instanceof SquadDailyMealDetailFragment ||
                currentFragmentTab instanceof RecipeAddFragment ||
                currentFragmentTab instanceof SettingsMainFragment ||
                currentFragmentTab instanceof MusicFragment ||
                currentFragmentTab instanceof SpotifyFragment ||
                currentFragmentTab instanceof SpotifyAlbumListFragment ||
                currentFragmentTab instanceof SessionOverviewFragment ||
                currentFragmentTab instanceof MealMatchAnalyseFragment ||
                currentFragmentTab instanceof AddUpdateCustomProgramFragment ||

                currentFragmentTab instanceof RaheQuestionFragment ||
                currentFragmentTab instanceof HappinessQuestionFragment ||
                currentFragmentTab instanceof CohenQuestion ||
                currentFragmentTab instanceof QuestionResultFragment ||
                currentFragmentTab instanceof FBWWalkTrack ||
                currentFragmentTab instanceof ActivityHistoryFrgament ||
                currentFragmentTab instanceof CourseArticleDetailsFragment ||
                currentFragmentTab instanceof CourseDetailsFragment ||
                currentFragmentTab instanceof WeightHistoryMyPLanFragment ||
                currentFragmentTab instanceof ChatNewActivity ||
                currentFragmentTab instanceof CourseMsgDetailsFragment ||
                currentFragmentTab instanceof TrackMyPhotoFragment ||
                currentFragmentTab instanceof MbhqGratitudeAddEditFragment ||
                currentFragmentTab instanceof MyAchievementsListAddEditFragment ||
                currentFragmentTab instanceof HabitHackerAddFirstPage ||
                currentFragmentTab instanceof HabitHackerAddSecondPage ||
                currentFragmentTab instanceof HabitHackerAddOne ||
                currentFragmentTab instanceof HabitHackerAddTwo ||
                currentFragmentTab instanceof HabitHackerAddThree ||
                currentFragmentTab instanceof HabitHackerAddFour ||
                currentFragmentTab instanceof HabitHackerAddFive ||
                currentFragmentTab instanceof HabitHackerAddSix ||
                currentFragmentTab instanceof HabitHackerAddSeven ||
                currentFragmentTab instanceof HabitHackerAddEight ||
                currentFragmentTab instanceof HabitHackerAddNine ||
                currentFragmentTab instanceof HabitHackerAddTen ||
                currentFragmentTab instanceof HabitHackerAddMainFragment ||
                currentFragmentTab instanceof HabitHackerEditFragment ||
                currentFragmentTab instanceof PopularHabitsListFragment
           ) {
            return false;
        } else {
            return true;
        }
    }


/*commentout by sahenita*/
/*
    private boolean allowCacheForMbhq(Fragment fragment) {
        if (fragment instanceof GratitudeMyListFragment) {
            return true;
        } else {
            return false;
        }
    }
*/
/*commentout by sahenita*/
/*
    public void checkAppUsage() {
        Log.e("CheckAppUsage", "Called");
        SharedPreference sharedPreference = new SharedPreference(this);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date today = null;
        Calendar calendar = Calendar.getInstance();
        today = calendar.getTime();
        if (sharedPreference.read("UsageDate", "") != null) {
            String savedDateString = sharedPreference.read("UsageDate", "");
            if (!savedDateString.equalsIgnoreCase(df.format(today))) {
                sharedPreference.write("UsageDate", "", df.format(today));
                setAppUsage(df.format(today));
            } else {
                Log.e("DayExist", "true");


            }
        } else {
            sharedPreference.write("UsageDate", "", df.format(today));
            setAppUsage(df.format(today));
        }
    }
*/
/*commentout by sahenita*/
/*
    public void setAppUsage(String dt) {
        Log.e("AppUsageApi", "Called");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        if (Connection.checkConnection(this)) {


            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("CurrentDate", dt);
            hashReq.put("Key", Util.KEY);


            Log.e("RequestSent", hashReq.toString());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(this);
            Call<JsonObject> serverCall = finisherService.setAppUsage(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if (response.isSuccessful()) {

                        StreakDialog streakDialog = new StreakDialog(MainActivity.this);
                        streakDialog.showStreakDialog(MainActivity.this);
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("ErrorAppUsageApi", "error");
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.DATE, -1);
                    Date prev = calendar.getTime();
                    sharedPreference.write("UsageDate", "", df.format(prev));
                }
            });


        }
    }
*/


    /////////////////////////////MEDITATION REWARD CREW SECTION////////////////////////////////
    private void getMeditationStreakData() {
        if (Connection.checkConnection(this)) {

            SharedPreference sharedPreference = new SharedPreference(this);
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("UserCurrentDate", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            hashReq.put("Key", Util.KEY);


            Log.e("RequestSent", hashReq.toString());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(this);
            Call<GetStreakDataResponse> serverCall = finisherService.getMeditationStreakData(hashReq);
            serverCall.enqueue(new Callback<GetStreakDataResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<GetStreakDataResponse> call, Response<GetStreakDataResponse> response) {
                    //progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        GetStreakDataResponse dataResponse = response.body();
                        if (dataResponse != null && dataResponse.getSuccessFlag()) {
                            StreakData streakData = dataResponse.getStreakData();
                            String currentStreak = String.valueOf(streakData.getCurrentStreak()) + System.lineSeparator();
                            sharedPreference.write("CURRENT_STREAK_MEDITATION", "", String.valueOf(streakData.getCurrentStreak()));
                            sharedPreference.write("TOP_STREAK_MEDITATION", "", String.valueOf(streakData.getTopStreak()));
                            sharedPreference.write("LAST_STREAK_MEDITATION", "", String.valueOf(streakData.getLastStreak()));
                            sharedPreference.write("TOTAL_STREAK_MEDITATION", "", String.valueOf(streakData.getTotal()));

                            if (response.body().getStreakData().getTotal() != null) {

                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetStreakDataResponse> call, Throwable t) {
                    Log.e("ErrorGetStreakData", t.getMessage());
                }
            });


        }
    }


    /////////////////////////////GRATITUDE REWARD CREW SECTION////////////////////////////////
    private void getStreakData() {
        Log.i("gamificationString","1");
        if (Connection.checkConnection(this)) {

            SharedPreference sharedPreference = new SharedPreference(this);
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            hashReq.put("UserCurrentDate", new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
            hashReq.put("Key", Util.KEY);


            Log.e("RequestSent", hashReq.toString());
            FinisherServiceImpl finisherService = new FinisherServiceImpl(this);
            Call<GetStreakDataResponse> serverCall = finisherService.getStreakData(hashReq);
            serverCall.enqueue(new Callback<GetStreakDataResponse>() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<GetStreakDataResponse> call, Response<GetStreakDataResponse> response) {
                    if (response.isSuccessful()) {
                        GetJounalPromptofDay();
                        Log.i("gamificationString","2");
                        GetStreakDataResponse dataResponse = response.body();
                        if (dataResponse != null && dataResponse.getSuccessFlag()) {
                            Log.i("gamificationString","4");
                            StreakData streakData = dataResponse.getStreakData();
                            String currentStreak = String.valueOf(streakData.getCurrentStreak()) + System.lineSeparator();
                            sharedPreference.write("CURRENT_STREAK", "", String.valueOf(streakData.getCurrentStreak()));
                            sharedPreference.write("TOP_STREAK", "", String.valueOf(streakData.getTopStreak()));
                            sharedPreference.write("LAST_STREAK", "", String.valueOf(streakData.getLastStreak()));
                            sharedPreference.write("TOTAL_STREAK", "", String.valueOf(streakData.getTotal()));
                            txtGamificationCount.setText(response.body().getStreakData().getTotal() + "");
                            Log.i("gamificationString","5");
                            gamificationString = response.body().getStreakData().getTotal() + "";

                            txtGamificationCountPopUp.setText(gamificationString);

                            Log.i("gamificationString",gamificationString);
                            if (response.body().getStreakData().getTotal() != null) {
                                showPopUpForGratitudeCrew(response.body().getStreakData()
                                        .getTotal());
                            }
                            GetUnreadCount();
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetStreakDataResponse> call, Throwable t) {
                    Log.i("gamificationString","3");
                    Log.e("ErrorGetStreakData", t.getMessage());
                    GetUnreadCount();
                    GetJounalPromptofDay();
                }
            });


        }
    }

    private void showPopUpForGratitudeCrew(Integer rewardPoint) {
        if (rewardPoint == 3 || rewardPoint == 7 || rewardPoint == 14 || rewardPoint == 21 || rewardPoint == 28 || rewardPoint == 35 || rewardPoint == 42 || rewardPoint == 50 ||
                rewardPoint == 75 || rewardPoint == 100 || rewardPoint == 125 || rewardPoint == 150 || rewardPoint == 175 || rewardPoint == 200 || rewardPoint == 225 || rewardPoint == 250 ||
                rewardPoint == 275 || rewardPoint == 300 || rewardPoint == 325 || rewardPoint == 350 || rewardPoint == 375 || rewardPoint == 400 || rewardPoint == 425 || rewardPoint == 450 ||
                rewardPoint == 475 || rewardPoint == 500 || rewardPoint == 550 || rewardPoint == 600 || rewardPoint == 650 || rewardPoint == 700 || rewardPoint == 750 || rewardPoint == 800 ||
                rewardPoint == 850 || rewardPoint == 900 || rewardPoint == 950 || rewardPoint == 1000 || rewardPoint == 1100 || rewardPoint == 1200 || rewardPoint == 1300 || rewardPoint == 1400 ||
                rewardPoint == 1500 || rewardPoint == 1600 || rewardPoint == 1700 || rewardPoint == 1800 || rewardPoint == 1900 || rewardPoint == 2000 || rewardPoint == 2250 || rewardPoint == 2500 ||
                rewardPoint == 2750 || rewardPoint == 3000 || rewardPoint == 5000 || rewardPoint == 10000) {
            Log.e("GRATITUDE REWARD DATE", new SharedPreference(MainActivity.this).read("GRATITUDE_REWARD_DATE", "") + ">>>>>>>>>>>>>>>>>>");
            SimpleDateFormat simpleDateFormatGet = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String strCurrentDate = simpleDateFormatGet.format(calendar.getTime());
            if (new SharedPreference(MainActivity.this).read("GRATITUDE_REWARD_DATE", "").equals("")
                    || !new SharedPreference(MainActivity.this).read("GRATITUDE_REWARD_DATE", "").equals(strCurrentDate)) {
            }
        }
    }
/*commentout by sahenita*/
/*
    public void openDialogForGratitudeReward(Integer rewardPoint) {

        gratitudeCrewSharableBool = false;

        SimpleDateFormat simpleDateFormatGet = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        String strCurrentDate = simpleDateFormatGet.format(calendar.getTime());
        new SharedPreference(MainActivity.this).write("GRATITUDE_REWARD_DATE", "", strCurrentDate);

        Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_sharemysuccess_keeptomyself);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        TextView txtCongratulation = dialog.findViewById(R.id.txtCongratulation);
        TextView txtRewardCount = dialog.findViewById(R.id.txtRewardCount);
        TextView txtLevelRising = dialog.findViewById(R.id.txtLevelRising);
        Button btnShareMySuccess = dialog.findViewById(R.id.btnShareMySuccess);
        Button btnKeepItToMyself = dialog.findViewById(R.id.btnKeepItToMyself);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        txtCongratulation.setText("Congratulations " + sharedPreference.read("firstName", "") +
                "!");
        txtRewardCount.setText(rewardPoint.toString());

        btnShareMySuccess.setOnClickListener(view -> {
            dialog.dismiss();
            openDialogForGratitudeRewardSecond(rewardPoint, false);
        });

        btnKeepItToMyself.setOnClickListener(view -> {
            Log.e("CLICKEDDDDDD", ">>>>>>");
            dialog.dismiss();
            */
/*commentout by sahenita (temporary)*//*

          //  funTabBarforReward(true);
        });

        dialog.show();
    }
*/

    public void openDialogForGratitudeRewardSecond(Integer rewardPoint, boolean boolUseTemplate) {

        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_gratitude_crew_add_pic);

        llAddPic = dialog.findViewById(R.id.llAddPic);
        imgBackgroundPic = dialog.findViewById(R.id.imgBackgroundPic);
        imgBackgroundMannyPic = dialog.findViewById(R.id.imgBackgroundMannyPic);
        TextView txtRewardCount = dialog.findViewById(R.id.txtRewardCount);
        Button btnShare = dialog.findViewById(R.id.btnShare);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        rlPicSection = dialog.findViewById(R.id.rlPicSection);
        cardViewBackgroundPic = dialog.findViewById(R.id.cardViewBackgroundPic);
        rlRewardCircle = dialog.findViewById(R.id.rlRewardCircle);
        btnRewardCount = dialog.findViewById(R.id.btnRewardCount);
        RelativeLayout rlSharableSection = dialog.findViewById(R.id.rlSharableSection);

        txtRewardCount.setText(rewardPoint.toString());
        btnRewardCount.setText(rewardPoint.toString());

        btnCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        btnShare.setOnClickListener(view -> {
            if (!gratitudeCrewSharableBool) {
                final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(MainActivity.this);
                alertDialogCustom.ShowDialog("Alert!", "Please add your pic to share", true);
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {

                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (hasGalleryPermission()) {
                        funcForShareImageUsingTemplateOption(rlSharableSection);
                    } else {
                        if (!Settings.System.canWrite(MainActivity.this)) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA}, SHARE_ONLY_READ_WRITE_CAMERA_PERMISSION_REQUEST_CODE);
                        }
                    }
                } else {
                    funcForShareImageUsingTemplateOption(rlSharableSection);
                }
            }
        });

        llAddPic.setOnClickListener(view -> {
            openDialogForAddPicUseTemplate(dialog, rewardPoint);
        });

        dialog.show();

        if (boolUseTemplate) {
            gratitudeCrewSharableBool = true;
            globalRlonlyShareUsinTemplate = rlSharableSection;
            llAddPic.setVisibility(View.GONE);
            rlPicSection.setBackgroundResource(R.drawable.round_corner_bg);
            imgBackgroundMannyPic.setVisibility(View.VISIBLE);
            imgBackgroundMannyPic.setImageResource(R.drawable.manny_trophy);
        } else {
            openDialogForAddPicUseTemplate(dialog, rewardPoint);
        }
    }

    public void funcForShareImageUsingTemplateOption(RelativeLayout rlPicSection) {
        rlPicSection.setDrawingCacheEnabled(true);
        Bitmap bitmap = getScreenShot(rlPicSection);
        File shareFile = createFolder();
        try {
            if (!shareFile.exists()) {
                shareFile.createNewFile();
            }
            FileOutputStream ostream = new FileOutputStream(shareFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 10, ostream);
            ostream.close();
            rlPicSection.invalidate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            rlPicSection.setDrawingCacheEnabled(false);
        }
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        Log.e("FILE_PATH", shareFile.getPath() + ">>>>");
        Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.ashysystem.mbhq" + ".fileprovider", shareFile);
        share.putExtra(Intent.EXTRA_STREAM, photoURI);
        startActivity(Intent.createChooser(share, "Share Image"));
    }

    public Bitmap getScreenShot(RelativeLayout view) {
        if (null != view) {
            int height = 0;
            //Get the ScrollView correctly
            for (int i = 0; i < view.getChildCount(); i++) {
                height += view.getChildAt(i).getHeight();
            }
            if (height < view.getHeight()) {
                height = view.getHeight();

            }
            if (height > 0) {
                //Create a cache to save the cache
                Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), height, Bitmap.Config.RGB_565);
                //Can easily understand Canvas as a drawing board and bitmap is a block canvas
                Canvas canvas = new Canvas(bitmap);
                //Draw the contents of the view to the specified canvas Canvas
                view.draw(canvas);
                return bitmap;
            }
        }
        return null;
    }

    private void openDialogForAddPicUseTemplate(Dialog previousDialog, Integer rewardPoint) {

        final Dialog dialog = new Dialog(MainActivity.this, R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_addpic_usetemplate);

        RelativeLayout rlAddPic = dialog.findViewById(R.id.rlAddPic);
        RelativeLayout rlUseTemplate = dialog.findViewById(R.id.rlUseTemplate);
        RelativeLayout rlCancel = dialog.findViewById(R.id.rlCancel);
        RelativeLayout rlTransparentPart = dialog.findViewById(R.id.rlTransparentPart);

        rlTransparentPart.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlCancel.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlAddPic.setOnClickListener(view -> {
            dialog.dismiss();
            pickImageFromGallery();
        });

        rlUseTemplate.setOnClickListener(view -> {
            dialog.dismiss();
            previousDialog.dismiss();
            openDialogForGratitudeRewardSecond(rewardPoint, true);
        });

        dialog.show();
    }

    private File createFolder() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "SQUAD");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(MainActivity.this, "Failed to create directory CLG  Direcory.",
                        Toast.LENGTH_LONG).show();
                return null;

            }
        }

        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());
        File mediaFile;
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }

    private void pickImageFromGallery() {
        final Dialog dlg = new Dialog(MainActivity.this);
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dlg.setContentView(R.layout.dialog_browse_bottom);

        Window window = dlg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        RelativeLayout rlGal = (RelativeLayout) dlg.findViewById(R.id.rlGal);
        RelativeLayout rlCancel = (RelativeLayout) dlg.findViewById(R.id.rlCancel);
        RelativeLayout rlCam = (RelativeLayout) dlg.findViewById(R.id.rlCam);
        rlCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission()) {

                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        out = createFolder();
                        imgPath = out.getAbsolutePath();
                        Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.ashysystem.mbhq" + ".fileprovider", out);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST_CODE);

                    } else {
                        if (!Settings.System.canWrite(MainActivity.this)) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA}, CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT);
                        }
                    }

                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    out = createFolder();
                    imgPath = out.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(MainActivity.this, "com.ashysystem.mbhq" + ".fileprovider", out);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST_CODE);
                }
            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
            }
        });

        rlGal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasGalleryPermission()) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(galleryIntent, PICK_IMAGE_FROM_GALLERY_CODE);


                    } else {
                        if (!Settings.System.canWrite(MainActivity.this)) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT);
                        }
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_IMAGE_FROM_GALLERY_CODE);
                }
            }
        });
        dlg.show();

    }

    private void cropPhoto(final Bitmap bitmap, final String imgPath) {
        try {
            final Dialog dialogcrop = new Dialog(MainActivity.this, android.R.style.Theme);
            dialogcrop.requestWindowFeature(Window.FEATURE_NO_TITLE);

            dialogcrop.setContentView(R.layout.cropdialogimage);
            dialogcrop.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
            TextView txtDone = (TextView) dialogcrop.findViewById(R.id.txtDone);
            TextView txtCancel = (TextView) dialogcrop.findViewById(R.id.txtCancel);
            cropImageView = (CropImageView) dialogcrop.findViewById(R.id.CropImageView);
            DisplayMetrics displaymetrics = new DisplayMetrics();
            MainActivity.this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            final int height = displaymetrics.heightPixels;
            int width = displaymetrics.widthPixels;
            Bitmap myBitmap = null;
            if (onLineBitMap == null) {
                myBitmap = decodeSampledBitmapFromFile(imgPath, ViewGroup.LayoutParams.FILL_PARENT, height);
                try {
                    cropImageView.setImageBitmap(Util.getImage(myBitmap, imgPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                myBitmap = onLineBitMap;
                try {
                    cropImageView.setImageBitmap(Util.getImage(onLineBitMap, imgPath));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                onLineBitMap = null;
            }

            Log.e(" height before crop--->", myBitmap.getHeight() + "");
            Log.e(" width before crop--->", myBitmap.getWidth() + "");
            cropImageView.setFixedAspectRatio(false);

            txtDone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    try {
                        bitimage = cropImageView.getCroppedImage();
                        Log.e(" height After crop--->", bitimage.getHeight() + "");
                        Log.e(" height After crop--->", bitimage.getHeight() + "");
                        imgBackgroundPic.setImageBitmap(null);
                        imgBackgroundPic.setImageBitmap(bitimage);
                        dialogcrop.dismiss();
                        String cropPath = storeImage(bitimage);
                        preaprePictureForUpload(cropPath);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            txtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Bitmap myBitmapCancel = decodeSampledBitmapFromFile(imgPath, ViewGroup.LayoutParams.FILL_PARENT, height);
                        imgBackgroundPic.setImageBitmap(myBitmapCancel);
                        preaprePictureForUpload(imgPath);
                        dialogcrop.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dialogcrop.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
        if (pictureFile == null) {
            Log.d(TAG,
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return "";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return pictureFile.getAbsolutePath();
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getApplicationContext().getPackageName()
                + "/Files");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = MainActivity.this.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void preaprePictureForUpload(String cropPath) {
        try {
            File file = new File(cropPath);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            stringImg = encodeImage(imageData);
            Log.e("BASE64STRING", stringImg);
            mFile = file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imgBackgroundPic.setBackgroundResource(0);
        }
        llAddPic.setVisibility(View.GONE);
        rlPicSection.setBackgroundResource(R.drawable.round_corner_bg);
        imgBackgroundPic.setVisibility(View.VISIBLE);
        cardViewBackgroundPic.setVisibility(View.VISIBLE);
        rlRewardCircle.setVisibility(View.VISIBLE);
        btnRewardCount.setVisibility(View.GONE);
        imgBackgroundPic.setImageBitmap(BitmapFactory.decodeFile(cropPath));
        gratitudeCrewSharableBool = true;
    }

    private boolean hasCameraPermission() {
        int hasPermissionWrite = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasPermissionCamera = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED && hasPermissionCamera == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    private boolean hasGalleryPermission() {
        int hasPermissionWrite = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }


/*commentout by sahenita*/
/*
    private void checkUserProgramStep(String TYPE, Bundle bundle) {

        progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

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
                        programStepNumber = response.body().get("StepNumber").getAsInt();
                    }
                    if (programStepNumber != null && programStepNumber.intValue() == 0) {
                        sharedPreference.write("EXERCISE_SETTINGS_FINISH", "", "TRUE");
                    }
                    squadNutritionSettingStepNourish(TYPE, bundle);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
                squadNutritionSettingStepNourish(TYPE, bundle);
            }
        });

    }
*/

   /*commentout by sahenita*/
/*
    private void squadNutritionSettingStepNourish(String TYPE, Bundle bundle) {

        if (Connection.checkConnection(MainActivity.this)) {

            progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.squadNutritionSettingStep(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    try {
                        if (response.body() != null) {
                            if (response.body().get("SuccessFlag").getAsBoolean()) {
                                final int nutritionStepNo = response.body().get("StepNumber").getAsInt();
                                sharedPreference.write("NUTRITIONSTEPNUMBER", "", String.valueOf(nutritionStepNo));
                                if (nutritionStepNo == 0) {
                                    sharedPreference.write("NUTRITION_SETTINGS_FINISH", "", "TRUE");
                                    if (TYPE.equals("CUSTOM")) {
                                        //CustomNutritionPlanFragment customNutritionPlanFragment = new CustomNutritionPlanFragment();
                                        CustomNutritionPlanFragmentNew customNutritionPlanFragment = new CustomNutritionPlanFragmentNew();
                                        Bundle bundle = new Bundle();
                                        bundle.putBoolean("Timer", true);
                                        if (bundle != null) {
                                            if (bundle.containsKey("origin")) {
                                                if (bundle.getString("origin").equals("chk")) {
                                                    bundle.putString("origin", "chk");
                                                }
                                            }

                                        }

                                        if (bundle != null && bundle.containsKey("NEWHOME")) {
                                            bundle.putString("NEWHOME", "true");
                                        }
                                        customNutritionPlanFragment.setArguments(bundle);
                                        loadFragment(customNutritionPlanFragment, "CustomNutritionPlan", bundle);
                                    } else {
                                        Bundle bundle = new Bundle();
                                        bundle.putString("FROMPAGE", "NOURISH");
                                        if (bundle != null && bundle.containsKey("NEWHOME")) {
                                            bundle.putString("NEWHOME", "true");
                                        }
                                        loadFragment(new MealMatchListFragment(), "MealMatchListFragment", bundle);
                                    }

                                } else if (nutritionStepNo == 1) {
                                    loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
                                } else if (nutritionStepNo == -1) {
                                    if (programStepNumber != null && (programStepNumber == 0 || programStepNumber > 2)) {
                                        loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
                                    } else {
                                        loadFragment(new NutritionSettingsPageOneGoalFragment(), "NutritionSettingsPageOneGoal", null);
                                    }
                                } else if (nutritionStepNo == 2) {
                                    loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
                                } else if (nutritionStepNo == 3) {
                                    loadFragment(new NutritionChooseMealFrequencyFragment(), "NutritionChooseMealFrequency", null);
                                } else if (nutritionStepNo == 4) {
                                    loadFragment(new NutritionMealVarietyFragment(), "NutritionMealVariety", null);
                                } else if (nutritionStepNo == 5) {
                                    loadFragment(new NutritionSelectMealPlanFragment(), "NutritionSelectMealPlan", null);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }

    }
*/
/*commentout by sahenita*/
/*
    public void funCustomWorkout(Bundle bundle) {
        if (Util.checkConnection(MainActivity.this)) {

            if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
                if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && Util.isSevenDayOver(MainActivity.this)) {
                    showAlertGergious();
                } else if (sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE") && !Util.isSevenDayOver(MainActivity.this)) {
                    checkUserProgramStepTr(bundle);
                } else {
                    showAlertGergious();
                }


            } else {
                if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                    showAlertDialogForDeniedAccess();
                } else {
                    checkUserProgramStepTr(bundle);
                }
            }

        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }
    }
*/

    ///////////////////////////////////////////////////////////////////////////////////////////////

/////////////////////////////////////////////////////////////////////////////////////////////////////////

////////////////////////////////GRATITUDE SHAREABILITY SECTION///////////////////////////////////////////
/*commentout by sahenita*/
/*
    private void checkUserProgramStepTr(Bundle bundle) {

        progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

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
                        sendToFragmentTr(response.body().get("StepNumber").getAsInt(), bundle);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }
*/

////////////////////////////////////////////////////////////////////////////////////////////////////////
/*commentout by sahenita*/
/*
    private void sendToFragmentTr(int stepNumber, Bundle bundleA) {

        if (stepNumber == 0) {
            //Toast.makeText(MainActivity.this,"GO TO CHANGE WEEk PAGE",Toast.LENGTH_LONG).show();
            new SharedPreference(MainActivity.this).write("EXERCISE_SETTINGS_FINISH", "", "TRUE");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendarGlobal = Calendar.getInstance();
            calendarGlobal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Bundle bundle = new Bundle();
            bundle.putString("CALENDERDATE", format.format(calendarGlobal.getTime()));
            if (bundleA != null) {
                if (bundle.containsKey("origin")) {
                    if (bundleA.getString("origin").equals("chk")) {
                        bundle.putString("origin", "chk");
                    }
                }
                if (bundleA != null && bundleA.containsKey("NEWHOME")) {
                    bundle.putString("NEWHOME", "true");
                }
            }

            loadFragment(new CustomProgramFinishFragment(), "CustomProgramFinish", bundle);
        } else if (stepNumber == 1) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundleA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageOneFragment(), "CustomProgramPageOne", bundle);
        } else if (stepNumber == 2) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundleA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageTwoFragment(), "CustomProgramPageTwo", bundle);
        } else if (stepNumber == 3) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundleA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageThreeFragment(), "CustomProgramPageThree", bundle);
        } else if (stepNumber == 4) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundleA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageFourFragment(), "CustomProgramPageFour", bundle);
        } else if (stepNumber == 5) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundleA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageFiveFragment(), "CustomProgramPageFive", bundle);
        } else if (stepNumber == 6) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundleA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageSixFragment(), "CustomProgramPageSix", bundle);
        } else if (stepNumber == 7) {
            Bundle bundle = new Bundle();
            if (bundleA != null) {
                if (bundle.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageSevenFragment(), "CustomProgramPageSeven", bundle);
        }

    }
*/

    //////////////////////////////////

    /*commentout by sahenita*/
/*
    private void checkUserProgramStepLearn(Bundle bundle) {

        ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

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
                        sendToFragmentLearn(response.body().get("StepNumber").getAsInt(), bundle);
                        programStepNumber = response.body().get("StepNumber").getAsInt();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }
*/
/*commentout by sahenita*/
/*
    private void sendToFragmentLearn(int stepNumber, Bundle bunA) {

        if (stepNumber == 0) {
*/
/*commentout by sahenita*//*

          //  squadNutritionSettingStepLearn(bunA);

        } else if (stepNumber == 1) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageOneFragment(), "CustomProgramPageOne", bundle);
        } else if (stepNumber == 2) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageTwoFragment(), "CustomProgramPageTwo", bundle);
        } else if (stepNumber == 3) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageThreeFragment(), "CustomProgramPageThree", bundle);
        } else if (stepNumber == 4) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageFourFragment(), "CustomProgramPageFour", bundle);
        } else if (stepNumber == 5) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageFiveFragment(), "CustomProgramPageFive", bundle);
        } else if (stepNumber == 6) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageSixFragment(), "CustomProgramPageSix", bundle);
        } else if (stepNumber == 7) {
            Bundle bundle = new Bundle();
            if (bunA != null) {
                if (bunA.containsKey("notification")) {
                    bundle.putString("notification", "TRUE");
                    bundle.putString("TYPE", "TRAIN");
                }
            }
            loadFragment(new CustomProgramPageSevenFragment(), "CustomProgramPageSeven", bundle);
        }

    }
*/
/*commentout by sahenita*/
/*
    private void squadNutritionSettingStepLearn(Bundle bunA) {

        if (Connection.checkConnection(MainActivity.this)) {

            ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.squadNutritionSettingStep(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            final int nutritionStepNo = response.body().get("StepNumber").getAsInt();
                            sharedPreference.write("NUTRITIONSTEPNUMBER", "", String.valueOf(nutritionStepNo));
                            */
/*if(getArguments()!=null && getArguments().containsKey("notification"))
                            {
                                sendToFragment(1);
                            }
                            else*//*

                            if (nutritionStepNo == 0) {
                                Bundle bundle = new Bundle();
                                if (bunA != null && bunA.containsKey("NEWHOME")) {
                                    bundle.putString("NEWHOME", "true");
                                }
                                //loadFragment(new SetProgramsFragment(), "SetPrograms", bundle);
                                loadFragment(new SetProgramsFragmentNew(), "SetPrograms", bundle);

                            } else if (nutritionStepNo == 1) {
                                loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
                            } else if (nutritionStepNo == -1) {
                                if (programStepNumber != null && (programStepNumber == 0 || programStepNumber > 2)) {
                                    loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
                                } else {
                                    loadFragment(new NutritionSettingsPageOneGoalFragment(), "NutritionSettingsPageOneGoal", null);
                                }
                            } else if (nutritionStepNo == 2) {
                                loadFragment(new NutritionDieteryPrefFragment(), "NutritionDieteryPref", null);
                            } else if (nutritionStepNo == 3) {
                                loadFragment(new NutritionChooseMealFrequencyFragment(), "NutritionChooseMealFrequency", null);
                            } else if (nutritionStepNo == 4) {
                                loadFragment(new NutritionMealVarietyFragment(), "NutritionMealVariety", null);
                            } else if (nutritionStepNo == 5) {
                                loadFragment(new NutritionSelectMealPlanFragment(), "NutritionSelectMealPlan", null);
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
            Util.showToast(MainActivity.this, Util.networkMsg);
        }

    }
*/

    ///////////////////////////////////
    public void selectTodayTabColor() {
        imgToday.setImageResource(0);
        imgToday.setImageResource(R.drawable.mbhq_today_inactive);
        imgGratitude.setImageResource(0);
        imgGratitude.setImageResource(R.drawable.mbhq_gratitude_inactive);
        imgMeditation.setImageResource(0);
        imgMeditation.setImageResource(R.drawable.mbhq_meditation_inactive);
        imgHabits.setImageResource(0);
        imgHabits.setImageResource(R.drawable.mbhq_habits_active);
        imgCourses.setImageResource(0);
        imgCourses.setImageResource(R.drawable.mbhq_learn_inactive);
    }

    public void funTrainClick(int finalI) {
        if (finalI == 0) {
            Log.e("calledhabit","6");
            sharedPreference.write("achieve_nav", "", "habit_hacker");
            sharedPreference.write("achieve_nav_pos", "", finalI + "");
            //loadFragment(new HabitHackerListFragment(), "HabitHackerList", null);
            Log.i("Landing1111111111111","3");
            loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
        } else if (finalI == 1) {
            Log.e("calledhabit","7");
            /*loadFragment(new VisionBoardHome(),"VisionBoardHome",null);
            sharedPreference.write("achieve_nav", "", "future_you");
            sharedPreference.write("achieve_nav_pos", "", finalI + "");*/
            loadFragment(new BucketListFragment(), "BucketList", null);
            sharedPreference.write("achieve_nav", "", "bucket_list");
            sharedPreference.write("achieve_nav_pos", "", finalI + "");
        } else if (finalI == 2) {
            Log.e("calledhabit","8");
            loadFragment(new BucketListFragment(), "BucketList", null);
            sharedPreference.write("achieve_nav", "", "bucket_list");
            sharedPreference.write("achieve_nav_pos", "", finalI + "");
        }
    }

    public void funTrainNourishClick(int finalI) {
        if (finalI == 0) {
            if (Util.checkConnection(MainActivity.this)) {
                sharedPreference.write("appreciate_nav", "", "gratitude");
                sharedPreference.write("appreciate_nav_pos", "", finalI + "");
                loadFragment(new GratitudeMyListFragment(), "GratitudeMyList", null);
            } else {
                Log.i("called landind","25");
                clearCacheForParticularFragment(new MbhqTodayMainFragment());
                loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            }
        } else if (finalI == 1) {

            if (Util.checkConnection(MainActivity.this)) {
                sharedPreference.write("appreciate_nav", "", "growth");
                sharedPreference.write("appreciate_nav_pos", "", finalI + "");
                fungratitudeicon();
                loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
            } else {
                Log.i("called landind","26");
                clearCacheForParticularFragment(new MbhqTodayMainFragment());
                loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            }

        } else if (finalI == 2) {
            Bundle bundle = new Bundle();
            bundle.putString("WEBINARSTATUS", "MEDITATION");
            loadFragment(new WebinarFragment(), "Webinar", null);

            sharedPreference.write("appreciate_nav", "", "meditation");
            sharedPreference.write("appreciate_nav_pos", "", finalI + "");

        } else if (finalI == 3) {
            sharedPreference.write("appreciate_nav", "", "quote_gallery");
            sharedPreference.write("appreciate_nav_pos", "", finalI + "");

            loadFragment(new QuoteGalleryFragment(), "QuoteGallery", null);
        }
    }
/*commentout by sahenita*/
/*
    public void funTrackClick(int finalI) {
        if (finalI == 0) {
            {


                loadFragment(new TrackAllFragment(), "TrackAllFragment", null);
                sharedPreference.write("track_nav", "", "TrackAllFragment");
                sharedPreference.write("track_nav_pos", "", finalI + "");

            }


        } else if (finalI == 1) {
            {

                Bundle bundle = new Bundle();
                bundle.putString("where", "photo");
                //  loadFragment(new TrackFragment(), "TrackFragment", bundle);
                loadFragment(new TrackMyPhotoFragment(), "TrackMyPhoto", bundle);
                sharedPreference.write("track_nav", "", "photo");
                sharedPreference.write("track_nav_pos", "", finalI + "");

            }

        } else if (finalI == 2) {
            {

                Bundle bundle = new Bundle();
                bundle.putString("where", "weigh");
                //loadFragment(new TrackFragment(), "TrackFragment", bundle);
                loadFragment(new BodyCompositionFragment(), "LatestResultTrack", bundle);
                sharedPreference.write("track_nav", "", "weigh");
                sharedPreference.write("track_nav_pos", "", finalI + "");

            }
        } else if (finalI == 3) {
            {
                Bundle bundle = new Bundle();
                bundle.putString("key", "today");
                loadFragment(new VitaminsFragment(), "Track", bundle);
                sharedPreference.write("track_nav", "", "vitamin");
                sharedPreference.write("track_nav_pos", "", finalI + "");

            }
        } else if (finalI == 4) {
            {
                funDrawer1();
                Bundle bundle = new Bundle();
                bundle.putString("where", "question");
                loadFragment(new QuestionariesFragment(), "QuestionariesFragment", null);
                sharedPreference.write("track_nav", "", "question");
                sharedPreference.write("track_nav_pos", "", finalI + "");

            }
        } else if (finalI == 5) {
            {

                Bundle bundle = new Bundle();
                bundle.putString("where", "question");
                //loadFragment(new TrackFragment(), "TrackFragment", bundle);
                loadFragment(new FbwTrackHome(), "FbwTrackHome", null);
                sharedPreference.write("track_nav", "", "question");
                sharedPreference.write("track_nav_pos", "", finalI + "");

            }
        }
    }
*/

    public void funProgramClick(int finalI) {
        funDrawer1();
        Bundle bundle = new Bundle();
        bundle.putString("NEWHOME", "true");
        loadFragment(new CourseFragment(), "Course", bundle);
    }

    public boolean allowAccess() {
        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (!sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE")) {
                showAlertGergious();
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }
/*commentout by sahenita*/
/*
    public boolean isFreeUserNotAvailTrial() {
        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (!sharedPreference.read("SEVENDAY_TRIAL_START", "").equals("TRUE")) {

                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
*/

    public boolean restrictionBool() {
        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        if (sharedPreference.read("restricttrain", "").equals("true")) {
            return true;
        } else
            return false;

    }

    public void clearCacheForParticularFragment(Fragment fragment) {
        try {
            for (int p = 0; p < arrFragment.size(); p++) {
                if (arrFragment.get(p).getClass().getSimpleName().contains(fragment.getClass().getSimpleName())) {

                    if("MbhqTodayTwoFragment".equalsIgnoreCase(fragment.getClass().getSimpleName())){
                        Util.callhabitdatabase=false;
                    }
                    arrFragment.remove(p);
                    Log.e("Removed--", "90");
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////////////////////
    private void GetUnreadCount() {
        if (Connection.checkConnection(MainActivity.this)) {
            //final ProgressDialog progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));


            FinisherServiceImpl finisherService = new FinisherServiceImpl(MainActivity.this);
            Call<JsonObject> serverCall = finisherService.GetUnreadCount(hashReq);
            serverCall.enqueue(new Callback<JsonObject>() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    //progressDialog.dismiss();

                    Log.e("UNREAD", "RCVD");
                    if (response.body() != null) {
                        String unreadCount = response.body().get("UnreadArticleCount").getAsString();
                        if (Integer.parseInt(unreadCount) > 0) {
                            rlChatCount.setVisibility(View.VISIBLE);
                            txtChatCount.setVisibility(View.VISIBLE);
                            //messageNotification.setVisibility(View.VISIBLE);
                            messageNotification.setVisibility(View.GONE);
                            if (Integer.parseInt(unreadCount) > 1) {
                                unreadCount = "1+";
                            } else {
                                unreadCount = "1";
                            }
                            txtChatCount.setText(unreadCount);
                        } else {
                            messageNotification.setVisibility(View.GONE);
                            rlChatCount.setVisibility(View.GONE);
                            txtChatCount.setVisibility(View.GONE);
                            txtChatCount.setText("");
                        }

                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                    Log.e("error", "er");
                    //progressDialog.dismiss();


                }
            });
        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }
    }
/*commentout by sahenita*/
/*
    public void getIndividualGratitude(Integer ID, String openNotification) {

        if (Util.checkConnection(MainActivity.this)) {

            progressDialog = ProgressDialog.show(MainActivity.this, "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("GratitudeId", ID);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<IndividualGratitudeModel> achievementModelCall = finisherService.selectGratitude(hashMap);
            achievementModelCall.enqueue(new Callback<IndividualGratitudeModel>() {
                @Override
                public void onResponse(Call<IndividualGratitudeModel> call, Response<IndividualGratitudeModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().getSuccessFlag()) {
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            bundle.putString("GRATITUDESTATUS", "EDIT");
                            bundle.putString("GRATITUDEDATA", gson.toJson(response.body().getDetails()));
                            if (openNotification.equals("TRUE")) {
                                bundle.putString("OPEN_NOTIFICATION", "TRUE");
                            }
                            ((MainActivity) MainActivity.this).loadFragment(new MbhqGratitudeAddEditFragment(), "MbhqGratitudeAddEdit", bundle);
                        }
                    }

                }

                @Override
                public void onFailure(Call<IndividualGratitudeModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }

    }
*/

    private void scheduleJob() {
        JobInfo myJob = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                    .setRequiresCharging(true)
                    .setMinimumLatency(1000)
                    .setOverrideDeadline(2000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .setPersisted(true)
                    .build();
            JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(myJob);
        }
    }

    ////////////////////CLEAR CACHE FOR PARTICULAR FRAGMENT/////////////////////

//    private void functionToUploadLocallySavedGratitudeAndGrowth() {
//
//        Util.isLocalUploadGrowthGratitideCalled = true;
//
//        factoryForGratitude = Injection.provideViewModelFactoryGratitude(MainActivity.this);
////        gratitudeViewModel = ViewModelProviders.of(MainActivity.this, factoryForGratitude).get(GratitudeViewModel.class);
//        gratitudeViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(GratitudeViewModel.class);
//
//        mDisposable.add(gratitudeViewModel.getAllGratitudeNotSynced()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//                .subscribe(gratitudeEntities -> {
//
//                    if (gratitudeEntities != null && gratitudeEntities.size() > 0) {
//                        String strGratitude = gratitudeEntities.get(0).getGratitudeDetails();
//                        String strTimeStamp = gratitudeEntities.get(0).getTimeStamp();
//                        String strDate = gratitudeEntities.get(0).getGratitudeDate();
//                        Gson gson = new Gson();
//                        GetGratitudeListModelInner getGratitudeListModelInner = gson.fromJson(strGratitude, GetGratitudeListModelInner.class);
//                        HashMap<String, Object> requestHash = new HashMap<>();
//                        requestHash.put("model", getGratitudeListModelInner);
//                        requestHash.put("Key", Util.KEY);
//                        requestHash.put("UserSessionID", Integer.parseInt(new SharedPreference(MainActivity.this).read("UserSessionID", "")));
//
//                        saveAddGratitudeData(requestHash, strTimeStamp, strDate);
//                    }
//                }, throwable -> {
//
//                }));
//
//
//        factoryForGrowth = Injection.provideViewModelFactoryGrowth(MainActivity.this);
////        growthViewModel = ViewModelProviders.of(MainActivity.this, factoryForGrowth).get(GrowthViewModel.class);
//        growthViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(GrowthViewModel.class);
//        mDisposable.add(growthViewModel.getAllGrowthNotSynced()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//
//                .subscribe(gratitudeEntities -> {
//                    if (gratitudeEntities != null && gratitudeEntities.size() > 0) {
//                        String strGratitude = gratitudeEntities.get(0).getGrowthDetails();
//                        String strTimeStamp = gratitudeEntities.get(0).getTimeStamp();
//                        String strDate = gratitudeEntities.get(0).getGrowthDate();
//                        Gson gson = new Gson();
//                        MyAchievementsListInnerModel getGratitudeListModelInner = gson.fromJson(strGratitude, MyAchievementsListInnerModel.class);
//                        HashMap<String, Object> requestHash = new HashMap<>();
//                        requestHash.put("model", getGratitudeListModelInner);
//                        requestHash.put("Key", Util.KEY);
//                        requestHash.put("UserSessionID", Integer.parseInt(new SharedPreference(MainActivity.this).read("UserSessionID", "")));
//
//                        saveAddAchievementData(requestHash, strTimeStamp, strDate);
//                    }
//
//                }, throwable -> {
//
//                }));
//
//        /////////////////////////////////////////////////
//
//    }//commented by jyotirmoy

    private void saveAddGratitudeData(HashMap<String, Object> reqhash, String strTimeStamp, String date) {

        if (Util.checkConnection(MainActivity.this)) {

            File mFile = null;
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onFinish() {


                    }
                }, 1);

            }

            Call<AddUpdateGratitudeModel> jsonObjectCall = finisherService.addUpdateGratitudeWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateGratitudeModel>() {
                @Override
                public void onResponse(Call<AddUpdateGratitudeModel> call, final Response<AddUpdateGratitudeModel> response) {

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {
                                    factoryForGratitude = Injection.provideViewModelFactoryGratitude(MainActivity.this);
                                    //gratitudeViewModel = ViewModelProviders.of(MainActivity.this, factoryForGratitude).get(GratitudeViewModel.class);
                                    gratitudeViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(GratitudeViewModel.class);

                                    Completable.fromAction(() -> {
                                                //  gratitudeViewModel.deleteGratitudeByTimeStamp(strTimeStamp);
                                            }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {

                                                factoryForGratitude = Injection.provideViewModelFactoryGratitude(MainActivity.this);
                                               // gratitudeViewModel = ViewModelProviders.of(MainActivity.this, factoryForGratitude).get(GratitudeViewModel.class);
                                                gratitudeViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(GratitudeViewModel.class);

                                                mDisposable.add(gratitudeViewModel.getAllGratitudeNotSynced()
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())

                                                        .subscribe(gratitudeEntities -> {

                                                            if (gratitudeEntities != null && gratitudeEntities.size() > 0) {
                                                                String strGratitude = gratitudeEntities.get(0).getGratitudeDetails();
                                                                String strTimeStamp = gratitudeEntities.get(0).getTimeStamp();
                                                                String strDate = gratitudeEntities.get(0).getGratitudeDate();
                                                                Gson gson1 = new Gson();
                                                                GetGratitudeListModelInner getGratitudeListModelInner1 = gson1.fromJson(strGratitude, GetGratitudeListModelInner.class);
                                                                HashMap<String, Object> requestHash = new HashMap<>();
                                                                requestHash.put("model", getGratitudeListModelInner1);
                                                                requestHash.put("Key", Util.KEY);
                                                                requestHash.put("UserSessionID", Integer.parseInt(new SharedPreference(MainActivity.this).read("UserSessionID", "")));

                                                                saveAddGratitudeData(requestHash, strTimeStamp, strDate);
                                                            }
                                                        }, throwable -> {

                                                        }));


                                            }, throwable -> {

                                            });


                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 500);

                }

                @Override
                public void onFailure(Call<AddUpdateGratitudeModel> call, Throwable t) {
                }
            });
        } else {
        }

    }

    private void saveAddAchievementData(HashMap<String, Object> reqhash, String strTimeStamp, String date) {

        if (Util.checkConnection(MainActivity.this)) {

            File mFile = null;
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onFinish() {
                        progressDialog.setProgress(100);
                    }
                }, 1);

            }

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {

                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {

                                    factoryForGrowth = Injection.provideViewModelFactoryGrowth(MainActivity.this);
//                                    growthViewModel = ViewModelProviders.of(MainActivity.this, factoryForGrowth).get(GrowthViewModel.class);
                                    growthViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(GrowthViewModel.class);

                                    Completable.fromAction(() -> {
                                                growthViewModel.deleteGrowthByTimeStamp(strTimeStamp);
                                            }).subscribeOn(Schedulers.io())
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(() -> {



                                                factoryForGrowth = Injection.provideViewModelFactoryGrowth(MainActivity.this);
//                                                growthViewModel = ViewModelProviders.of(MainActivity.this, factoryForGrowth).get(GrowthViewModel.class);
                                                growthViewModel = new ViewModelProvider(MainActivity.this, factoryForMeditation).get(GrowthViewModel.class);

                                                mDisposable.add(growthViewModel.getAllGrowthNotSynced()
                                                        .subscribeOn(Schedulers.io())
                                                        .observeOn(AndroidSchedulers.mainThread())

                                                        .subscribe(gratitudeEntities -> {
                                                            if (gratitudeEntities != null && gratitudeEntities.size() > 0) {
                                                                String strGratitude = gratitudeEntities.get(0).getGrowthDetails();
                                                                String strTimeStamp = gratitudeEntities.get(0).getTimeStamp();
                                                                String strDate = gratitudeEntities.get(0).getGrowthDate();
                                                                Gson gson1 = new Gson();
                                                                MyAchievementsListInnerModel getGratitudeListModelInner1 = gson1.fromJson(strGratitude, MyAchievementsListInnerModel.class);
                                                                HashMap<String, Object> requestHash = new HashMap<>();
                                                                requestHash.put("model", getGratitudeListModelInner1);
                                                                requestHash.put("Key", Util.KEY);
                                                                requestHash.put("UserSessionID", Integer.parseInt(new SharedPreference(MainActivity.this).read("UserSessionID", "")));

                                                                saveAddAchievementData(requestHash, strTimeStamp, strDate);
                                                            }

                                                        }, throwable -> {

                                                        }));


                                            }, throwable -> {

                                            });

                                }
                            }

                        }
                    };
                    new Handler().postDelayed(runnable, 500);

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {

        }

    }

    @SuppressLint("NewApi")
    private void scheduleUploadJob() {
        final JobScheduler jobScheduler = (JobScheduler) getSystemService(
                Context.JOB_SCHEDULER_SERVICE);

        final ComponentName name = new ComponentName(this, UploadService.class);

        final int result = jobScheduler.schedule(getJobInfo(1010, 1, name));

        if (result == JobScheduler.RESULT_SUCCESS) {
            Log.e(MainActivity.class.getSimpleName(), "Scheduled job successfully!");
        }

    }

    /////////////BROADCAST FOR ONLINE OFFLINE/////////////

    @SuppressLint("NewApi")
    private JobInfo getJobInfo(final int id, final long hour, final ComponentName name) {
        final long interval = TimeUnit.HOURS.toMillis(hour); // run every hour
        final boolean isPersistent = true; // persist through boot
        final int networkType = JobInfo.NETWORK_TYPE_ANY; // Requires some sort of connectivity

        final JobInfo jobInfo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(id, name)
                    //.setMinimumLatency(interval)
                    .setMinimumLatency(5000)
                    .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(id, name)
                    //.setPeriodic(interval)
                    .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .setPeriodic(5000)
                    .build();
        }

        return jobInfo;
    }

    @Override
    public void startUpload() {

    }

    /////////////////////////////////////////////////////

    @Override
    public void addInQueue(UploadEntity uploadEntity) {
        if (uploadDatabaseManager != null) {
            uploadDatabaseManager.addUploadQueueData(new UploadDatabaseCallback() {
                @Override
                public void loadInCompletedList(List<UploadEntity> uploadEntities) {

                }

                @Override
                public void onInsertCompleted() {
                    Log.e("AddedInQueue", "Completed....");
                }
            }, uploadEntity);
        }
    }

    private void openHelp() {
        clearCacheForParticularFragment(new NewHelpFragment());
        LinearLayout llTabView = (LinearLayout) findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        loadFragment(NewHelpFragment.newInstance(2), "NewHelpFragment", null);


    }

    /*commentout by sahenita*/
/*
    private void openEq() {
        clearCacheForParticularFragment(new NewHelpFragment());
        LinearLayout llTabView = (LinearLayout) findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        loadFragment(EqFragment.newInstance(2), "NewHelpFragment", null);


    }
*/


    //////////////////////////BROADCAST FOR DOWNLOAD MEDITATION COMPLETE//////////////
/*commentout by sahenita*/
/*
    public void openForum() {
        SharedPreference sharedPref = new SharedPreference(this);
        if (!sharedPref.read(
                "COMMUNITY_URL",
                ""
        ).equals("")

        ) {
            if (!sharedPref.readBoolean("HasChatHelpShownToUser", "")) {
                sharedPref.writeBoolean("HasChatHelpShownToUser", "", true);
                clearCacheForParticularFragment(new CommunityHelpFragment());
                loadFragment(new CommunityHelpFragment(), "CommunityHelpFragment", null);

            } else {
                loadFragment(new CommunityFragment(), "CommunityFragment", null);
            }
        } else {
            Log.e("COMMUNITY_URL", "NULLLLLLLLLL");
            */
/*commentout by sahenita*//*

*/
/*
            performCommunityLogIn(new CommunityLoginCallBack() {
                @Override
                public void onCommunityLoginSuccess() {
                    openForum();
                }

                @Override
                public void onCommunityLoginFailed() {
                    Util.showToast(MainActivity.this, "Forum not created.");
                }
            });
*//*


        }
    }
*/

    @Override
    protected void onStop() {
        stopService(new Intent(this, NetworkSchedulerService.class));
        mDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Start service and provide it a way to communicate with this class.
        try {
            Intent startServiceIntent = new Intent(MainActivity.this, NetworkSchedulerService.class);
            this.startService(startServiceIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
/*commentout by sahenita*/
/*
    public void createTabBarforHelp() {

        SharedPreference sharedPreference = new SharedPreference(MainActivity.this);
        String[] arrTab = {"MBHQ Manual", "Walkthrough"};
        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
        horTab.setVisibility(View.VISIBLE);
        LinearLayout llHorTab = findViewById(R.id.llHorTab);
        llHorTab.removeAllViews();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            horTab.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {

                    View view = (View) horTab.getChildAt(horTab.getChildCount() - 1);
                    int diff = (view.getRight() - (horTab.getWidth() + horTab.getScrollX()));
                    Log.e("print diff scrolld--", diff + "???");
                    if (scrollX == 0) {
                        leftDot.setVisibility(View.GONE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if ((scrollX - oldScrollX) > 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    } else if (diff == 0) {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.GONE);
                    } else {
                        leftDot.setVisibility(View.VISIBLE);
                        rightDot.setVisibility(View.VISIBLE);
                    }

                    if (Math.abs(scrollX - oldScrollX) > SlowDownThreshold) {
                        Log.e("SCROLL", "123");
                        Sensey.getInstance().stopTouchTypeDetection();
                    } else {
                        funSensey();
                        Log.e("SCROLL", "456");
                    }
                }
            });
        }
        ArrayList<TextView> arrTextView = new ArrayList<>();
        ArrayList<RelativeLayout> arrRelative = new ArrayList<>();

        for (int i = 0; i < arrTab.length; i++) {

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.layout_dynamic_tab, null);
            TextView textView = view.findViewById(R.id.txtTab);
            RelativeLayout rlMain = (RelativeLayout) view.findViewById(R.id.rlMain);
            textView.setId(i);
            textView.setText(arrTab[i]);

            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            rlMain.setLayoutParams(param);

            llHorTab.addView(view);
            int finalI = i;
            arrTextView.add(textView);
            arrRelative.add(rlMain);
            rlMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clearTextSelection(arrTextView, arrRelative);

                    try {
                        HorizontalScrollView horTab = (HorizontalScrollView) findViewById(R.id.horTab);
                        int x = arrRelative.get(finalI).getLeft() - 110;
                        horTab.post(new Runnable() {
                            @Override
                            public void run() {
                                horTab.smoothScrollTo(x, 0);
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (finalI == 0) {
                        {

                            arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                            arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));
                            arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);

                            clearCacheForParticularFragment(new MBHQManualFragment());
                            loadFragment(new MBHQManualFragment(), "MBHQ Manual Fragment", null);
                            ///////////////////
                            sharedPreference.write("help_nav", "", "MBHQ");
                            sharedPreference.write("help_nav_pos", "", finalI + "");
                            rlLeftTab = null;
                            rlRightTab = arrRelative.get(1);
                        }


                    } else if (finalI == 1) {

                        arrTextView.get(finalI).setTextColor(getResources().getColor(R.color.colorPrimary));
                        arrTextView.get(finalI).setTypeface(null, Typeface.BOLD);
                        arrRelative.get(finalI).setBackground(getResources().getDrawable(R.drawable.rectangle_white_tab));

                        clearCacheForParticularFragment(new WalkthroughFragment());
                        loadFragment(new WalkthroughFragment(), "Walkthrough Fragment", null);
                        sharedPreference.write("help_nav", "", "Walkthrough");
                        sharedPreference.write("help_nav_pos", "", finalI + "");
                        rlLeftTab = arrRelative.get(0);
                        rlRightTab = null;

                    }

                }
            });
        }
        sharedPreference.write("help_nav", "", "MBHQ");
        sharedPreference.write("help_nav_pos", "", 0 + "");
        funTabselection("help_nav_pos", arrRelative, arrTextView, llHorTab, horTab);

    }
*/
/*commentout by sahenita*/
/*
    public void onHelpClick(int finalI) {

        if (finalI == 0) {
            {
                loadFragment(new MBHQManualFragment(), "MBHQ Manual Fragment", null);

            }

        } else if (finalI == 1) {

            loadFragment(new WalkthroughFragment(), "Walkthrough Fragment", null);

        }

    }
*/

    private void openAeroplaneModeDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_aeroplane_mode);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

        btnGotIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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

    public Bitmap getScreenshotOf(View v) {

        Bitmap screenshot;
        v.setDrawingCacheEnabled(true);
        screenshot = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        FileOutputStream fileOutputStream = null;

        File path = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        String uniqueID = "habit_win";//UUID.randomUUID().toString();

        File file = new File(path, uniqueID + ".jpg");
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(TAG, "file not found exception");
        }

        if (screenshot.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)) {
            Log.i(TAG, "file created successfully");
        } else {
            Log.i(TAG, "file not created");
        }

        Log.i(TAG, "file path =" + file.getAbsolutePath());

        try {
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "file not flushed");
        }

        Uri uri = FileProvider.getUriForFile(MainActivity.this, "com.ashysystem.mbhq" + ".fileprovider", file);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));

        Log.i(TAG, uri.toString());

        return screenshot;
    }

    public void openWinTheHabitModal(Boolean wonTheWeek, GetTaskStatusForDateResponse getTaskStatusForDateResponse, String currentDate) {

        Log.i(TAG, "open win the habit modal");

        Integer winStreak = getTaskStatusForDateResponse.getDaysDoneForTheWeek();
        String weekStartDate = getTaskStatusForDateResponse.getWeekStartDate();

        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthNameDateFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat dayNameDateFormat = new SimpleDateFormat("EEE");
        SimpleDateFormat numericalDateFormat = new SimpleDateFormat("dd");

        Date currentDate1 = new Date();

        Dialog dialog = new Dialog(this, R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_win_the_habit_congratulation);

        View closeModal = dialog.findViewById(R.id.closeModal);
        TextView userGreetText = dialog.findViewById(R.id.userGreetText);
        TextView winTypeText = dialog.findViewById(R.id.winTypeText);
        TextView winDateDayText = dialog.findViewById(R.id.winDateDayText);
        TextView winDateText = dialog.findViewById(R.id.winDateText);
        TextView extraText = dialog.findViewById(R.id.extraText);
        ImageView congratsImage = dialog.findViewById(R.id.congratsImage);
        ViewGroup mainModal = dialog.findViewById(R.id.mainModal);
        ViewGroup actionButtonsContainer = dialog.findViewById(R.id.actionButtonsContainer);
        Button saveButton = dialog.findViewById(R.id.saveButton);
        Button seeResultButton = dialog.findViewById(R.id.seeResultButton);

        String firstName = sharedPreference.read("firstName", "");
        if (firstName.length() > 0) {
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        }

        userGreetText.setText("Congratulations " + firstName);

        if (wonTheWeek) {
            Log.i("status_allcheck_data","3");
            winTypeText.setText("WON THE WEEK");

            try {
                winDateDayText.setText(
                        monthNameDateFormat.format(
                                apiDateFormat.parse(weekStartDate)
                        )
                );

                winDateText.setText(
                        numericalDateFormat.format(
                                apiDateFormat.parse(weekStartDate)
                        )
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            extraText.setText(winStreak + " Days Won");
            switch (winStreak) {
                case 7:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.seven_manny_3x));
                    break;
                case 6:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.six_manny_3x));
                    break;
                case 5:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.five_manny_3x));
                    break;
                default:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.four_manny_3x));
            }

        } else {
            Log.i("status_allcheck_data","4");
            winTypeText.setText("WON THE DAY");
            extraText.setText("100% Habits Completed");
            Log.i("currentDate",currentDate);

            SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

            try {
                Date date = inputDateFormat.parse(currentDate);

                SimpleDateFormat outputDateFormat = new SimpleDateFormat("EEE", Locale.US);
                SimpleDateFormat outputDateFormat1 = new SimpleDateFormat("dd", Locale.US);

                day = outputDateFormat.format(date);
                day1 = outputDateFormat1.format(date);
                Log.d("Day11111111111111111", day);
                Log.d("Day11111111111111111", day1);
            } catch (Exception e) {
                Log.d("Day11111111111111111",  e.getMessage());
                e.printStackTrace();
            }
            winDateDayText.setText(day);
            winDateText.setText(day1);

        congratsImage.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.one_manny_thumbsup_silver_3x));


        }

        closeModal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (PermissionChecker.checkCallingOrSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
                    getScreenshotOf(mainModal);
                } else {
                    MainActivity.this.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 289);
                }
            }
        });

        seeResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                loadFragment(new WinTheWeekStatsFragment(), "WinTheWeekStatsFragment", null);
            }
        });

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                callUpdateBadgeShownAPI(wonTheWeek,currentDate);
            }
        });

        dialog.show();

    }


    //////////////////////////////////////////////////////////////////////////////////
/*commentout by sahenita*/
/*
    public void callWinTheWeekStatsAPI() {

        if (Connection.checkConnection(MainActivity.this)) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<GetWinTheWeekStatsResponse> jsonObjectCall = finisherService.getWinTheWeekStats(hashReq);
            jsonObjectCall.enqueue(new Callback<GetWinTheWeekStatsResponse>() {

                @Override
                public void onResponse(Call<GetWinTheWeekStatsResponse> call, Response<GetWinTheWeekStatsResponse> response) {
                    if (response.body() != null) {

                        Log.i(TAG, response.body().getDayWins().toString());
                    }
                }

                @Override
                public void onFailure(Call<GetWinTheWeekStatsResponse> call, Throwable t) {
                    t.printStackTrace();
                }

            });

        } else {
        }
    }
*/

    public void callTaskStatusForDateAPI(String forDate_optionclick) {
        String currentDate="";
        if("".equalsIgnoreCase(forDate_optionclick)){
            currentDate = (new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date()));

        }else{
            currentDate=forDate_optionclick;
        }
        if (Connection.checkConnection(MainActivity.this)) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("ForDate", currentDate);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<GetTaskStatusForDateResponse> jsonObjectCall = finisherService.getTaskStatusForDate(hashReq);
            String finalCurrentDate = currentDate;
            jsonObjectCall.enqueue(new Callback<GetTaskStatusForDateResponse>() {

                @Override
                public void onResponse(Call<GetTaskStatusForDateResponse> call, Response<GetTaskStatusForDateResponse> response) {
                    if (response.body() != null) {

                        GetTaskStatusForDateResponse responseBody = response.body();

                        if (responseBody.getDaysDoneForTheWeek() >= 4 && !responseBody.getWeeklyBadgeShown()) {

                            Log.i("status_allcheck_data","1");
                            openWinTheHabitModal(true, responseBody, finalCurrentDate);
                        } else if (responseBody.getTotalTaskForTheDay()>0 && responseBody.getTotalTaskDoneForTheDay() > 0 && responseBody.getTotalTaskForTheDay().equals(responseBody.getTotalTaskDoneForTheDay()) && !responseBody.getDailyBadgeShown()) {
                            Log.i("status_allcheck_data","2");
                            openWinTheHabitModal(false, responseBody, finalCurrentDate);
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetTaskStatusForDateResponse> call, Throwable t) {
                    Log.i(TAG, "on Failure");
                    t.printStackTrace();
                }

            });

        } else {
        }
    }

    public void callUpdateBadgeShownAPI(boolean isWeekBadge,String currentDate) {

        if (Connection.checkConnection(MainActivity.this)) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("ForDate", currentDate);
            hashReq.put("IsWeekBadge", isWeekBadge);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<UpdateBadgeShownResponse> jsonObjectCall = finisherService.updateBadgeShown(hashReq);
            jsonObjectCall.enqueue(new Callback<UpdateBadgeShownResponse>() {

                @Override
                public void onResponse(Call<UpdateBadgeShownResponse> call, Response<UpdateBadgeShownResponse> response) {
                    if (response.body() != null) {
                        //response.body().getDayWins();
                        Log.i(TAG, response.body().getSuccessFlag().toString());
                    }
                }

                @Override
                public void onFailure(Call<UpdateBadgeShownResponse> call, Throwable t) {
                    t.printStackTrace();
                }

            });

        } else {
        }
    }

    private void checkMeditationCacheExpiration() {

        if (Connection.checkConnection(this)) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(this);
            Call<GetMeditationCacheExpiryTimeResponse> serverCall = finisherService.GetMeditationCacheExpiryTime(hashReq);
            serverCall.enqueue(new Callback<GetMeditationCacheExpiryTimeResponse>() {
                @Override
                public void onResponse(Call<GetMeditationCacheExpiryTimeResponse> call, Response<GetMeditationCacheExpiryTimeResponse> response) {
                    //progressDialog.dismiss();
                    GetMeditationCacheExpiryTimeResponse responseBody = response.body();
                    if (responseBody != null && responseBody.getSuccessFlag()) {
                        try {
                            sharedPreference.write("MEDITATION_EXPIRATION_DATE_TIME", "", responseBody.getExpiryDateTime());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetMeditationCacheExpiryTimeResponse> call, Throwable t) {

                }
            });

        }

    }


    public interface NotificationUpdate {
        public void onClicked();
    }

    public interface OnDialogCloseListener {
        void onClose();
    }

    public interface OnApiResponseListener {
        void apiFinish();
    }
/*commentout by sahenita*/
   /* interface CommunityLoginCallBack {
        void onCommunityLoginSuccess();

        void onCommunityLoginFailed();
    }*/

    private class PromotionalChallengeInfo {

        private String title;
        private String body;
        private String fromController;
        private String actionTitle;

        public String getTitle() {
            return title;
        }

        public PromotionalChallengeInfo setTitle(String title) {
            this.title = title;
            return this;
        }

        public String getBody() {
            return body;
        }

        public PromotionalChallengeInfo setBody(String body) {
            this.body = body;
            return this;
        }

        public String getFromController() {
            return fromController;
        }

        public PromotionalChallengeInfo setFromController(String fromController) {
            this.fromController = fromController;
            return this;
        }

        public String getActionTitle() {
            return actionTitle;
        }

        public PromotionalChallengeInfo setActionTitle(String actionTitle) {
            this.actionTitle = actionTitle;
            return this;
        }
    }

    ////////////////////////////

    /*commentout by sahenita*/
/*
    private class DbAsynTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {

            funDb();
            return null;

        }

        @Override
        protected void onPostExecute(Void result1) {
            new DbQuoteAsynTask().execute();
        }
    }
*/
/*commentout by sahenita*/
/*
    private class DbQuoteAsynTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {

            return null;
        }

        @Override
        protected void onPostExecute(Void result1) {
            sharedPreference.write("dbquote", "", "true");
            sharedPreference.write("quoteon", "", "true");
        }
    }
*/

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getExtras().getString("actionName");

            switch (action) {
                case BackgroundSoundServiceNew.ACTION_PLAY:
                    onPlayPause();
                    break;


            }
        }
    };

    /*commentout by sahenita*/
/*
    BroadcastReceiver broadcastReceiverForScreen = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {

                // onPlayPause1();

            }
        }
    };
*/


    /*commentout by sahenita*/
/*
    BroadcastReceiver MediaButtonReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_SCREEN_OFF)) {


            }
        }
    };
*/




    private void onPlayPause() {
        Log.e("MAINPLAY", "onPlayPause: " + musicSrvce.isMediaPlaying());

        if (musicSrvce.isMediaPlaying()) {

            musicSrvce.pauseMedia();

        } else {
            musicSrvce.startMedia();
        }
    }
/*commentout by sahenita*/
/*
    private void onPlayPause1() {
        Log.e("MAINPLAY", "onPlayPause: " + musicSrvce.isMediaPlaying());

        if (musicSrvce.isMediaPlaying()) {

            musicSrvce.pauseMedia();

        } else {

        }
    }
*/

    /*commentout by sahenita*/
/*
    BroadcastReceiver VideoPlaybackReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.i("othervideo",action);


            if (intent.getAction() != null && intent.getAction().equals(VideoPlaybackService.ACTION_VIDEO_PLAYING)) {
                Log.i("othervideo","playing");
            }
        }
    };
*/

/*commentout by sahenita*/
  /*  private void startVideoPlayback() {
        // Start the video playback service
        Intent serviceIntent = new Intent(this, VideoPlaybackService.class);
        startService(serviceIntent);
    }*/


    private void GetJounalPromptofDay() {
        if (Connection.checkConnection(this)) {
            SharedPreference sharedPreference = new SharedPreference(this);
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            Log.e("userSession", "sessionid>>>" + sharedPreference.read("UserSessionID", ""));
            Log.e("key", "key>>>" + Util.KEY);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(this);
            Call<GetPrompt> promptCall = finisherService.getJounalPromptofDay(hashReq);
            promptCall.enqueue(new Callback<GetPrompt>() {
                @Override
                public void onResponse(Call<GetPrompt> call, Response<GetPrompt> response) {

                    if (response.isSuccessful()) {
                        String prompt = response.body().getPromptOftheDay().toString();
                        Log.e("Promptoftheday", "Prompt of the day>>>>>>>" + prompt);
                        String getprompt = prompt;
                        txt_prompt.setText(getprompt);
                    } else {
                        Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                    }

                }

                @Override
                public void onFailure(Call<GetPrompt> call, Throwable t) {
                    Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                }
            });
        }

    }

    private void updateEqName(String name,int Id,Dialog dialog) {

        if (Connection.checkConnection(MainActivity.this)) {
            SharedPreference sharedPreference = new SharedPreference(MainActivity.this);

            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserFolderId", Id);
            hashReq.put("NewFolderName", name);


            FinisherServiceImpl finisherService = new FinisherServiceImpl(MainActivity.this,sharedPreference.read("jwt", ""));

            Call<Eqfolder> jsonObjectCall = finisherService.updateEqname(hashReq);
            jsonObjectCall.enqueue(new Callback<Eqfolder>() {
                @Override
                public void onResponse(Call<Eqfolder> call, Response<Eqfolder> response) {


                    Gson gson = new Gson();
                    if(response.isSuccessful()){
                        f1=false;
                        f2=false;
                        f3=false;
                        Type type = new TypeToken<ArrayList<UserEqFolder>>() {
                        }.getType();
                        mymodelArrayList = gson.fromJson(gson.toJson(response.body().getUserEqFolders()), type);
                        showpopup1(dialog);
                    }

                }

                @Override
                public void onFailure(Call<Eqfolder> call, Throwable t) {

                }
            });

        } else {
            Util.showToast(MainActivity.this, Util.networkMsg);
        }



    }
    private void showpopup( ){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Folder name is empty.") .setTitle("EFC");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();

    }
    private void showpopup1(Dialog dialog1 ){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("FolderName Updated Successfully.") .setTitle("EFC");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        sharedPreference.write("my_list_eqfolder", "", new Gson().toJson(mymodelArrayList));

                        dialog1.dismiss();
                        dialog.dismiss();

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();

    }

}