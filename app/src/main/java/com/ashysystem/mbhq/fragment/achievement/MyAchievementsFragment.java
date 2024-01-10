package com.ashysystem.mbhq.fragment.achievement;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;

import static com.ashysystem.mbhq.activity.MainActivity.decodeSampledBitmapFromFile;
import static com.ashysystem.mbhq.activity.MainActivity.imgGratitude;
import static com.ashysystem.mbhq.activity.MainActivity.imgHabits;
import static com.ashysystem.mbhq.fragment.allSettingsFragment.KEY_DAILY_POPUP_JOURNAL;



import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ashysystem.mbhq.BuildConfig;
import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.adapter.MyAchievementsListAdapter;
//import com.ashysystem.mbhq.adapter.MyAchievementsListAdapter_new;
import com.ashysystem.mbhq.adapter.MyAdapter;
import com.ashysystem.mbhq.adapter.MyAdapter1;
import com.ashysystem.mbhq.dialog.CustomReminderDialogForEditAchievement;
import com.ashysystem.mbhq.fragment.CustomReminderDialog;
import com.ashysystem.mbhq.fragment.CustomReminderDialogForEdit;
import com.ashysystem.mbhq.fragment.MyAchievementsListAddEditFragment;
import com.ashysystem.mbhq.fragment.habit_hacker.MbhqTodayMainFragment;
import com.ashysystem.mbhq.fragment.meditation.MeditationFragment;
import com.ashysystem.mbhq.model.GetGratitudeCacheExpiryTimeResponse;
import com.ashysystem.mbhq.model.GetPrompt;
import com.ashysystem.mbhq.model.GetUserPaidStatusModel;
import com.ashysystem.mbhq.model.GrowthSaveFilterModel;
import com.ashysystem.mbhq.model.IndividualAchievementModel;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.MyAchievementsListModel;
import com.ashysystem.mbhq.model.MyValueListResponse;
import com.ashysystem.mbhq.model.eqfolder.Folderdefaultresponse;
import com.ashysystem.mbhq.model.eqfolder.UserEqFolder;
import com.ashysystem.mbhq.model.response.AddUpdateMyAchievementModel;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.MbhqDatabse;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;
import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGratitude;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGrowth;
import com.ashysystem.mbhq.roomDatabase.viewModel.GratitudeViewModel;
import com.ashysystem.mbhq.roomDatabase.viewModel.GrowthViewModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.CustomScrollView;
import com.ashysystem.mbhq.util.DatePickerControllerFromTextView;
import com.ashysystem.mbhq.util.DatePickerForGratitude;
import com.ashysystem.mbhq.util.EndlessRecyclerViewScrollListener;
import com.ashysystem.mbhq.util.OnDragTouchListener;
import com.ashysystem.mbhq.util.SetLocalNotificationForAchievement;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.edmodo.cropper.CropImageView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;

import id.zelory.compressor.Compressor;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.MODE_PRIVATE;
import static com.ashysystem.mbhq.activity.MainActivity.encodeImage;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by android-krishnendu on 3/16/17.
 */

public class MyAchievementsFragment extends Fragment {
    private static final long CLICK_DELAY = 2000; // Time in milliseconds
    private long lastClickTime;
    ArrayList<Integer>checkfolder=new ArrayList<>();
    boolean boolOnlyPic = false;
    String mCurrentPhotoPath = "";
    File photoFile = null;
    boolean boolIChooseToKnow = false;
    private final CompositeDisposable mDisposable = new CompositeDisposable();
    List<MyAchievementsListInnerModel> lstShowAllRmv = new ArrayList<MyAchievementsListInnerModel>();
    private static final String TAG = "MyAchievementsFragment";
    TextView txtGoalValue1, txtGoalValue2, txtGoalValue3;
    RecyclerView recyclerGratitudes;
    ImageView imgQuestionMark;
    VideoView video_view;
    LinearLayout llAddNew;
    CardView imgGratitudeMainCard;
    FrameLayout fram_;
    FinisherServiceImpl finisherService;
    SharedPreference sharedPreference;
    ProgressDialog progressDialog;
    boolean videoBarOpenStatus = false;
    ImageView imgAchieveAdd ;
    public static  ImageView imgFilterAchieve;
    ArrayList<MyAchievementsListInnerModel> lstAll,lstfilterfolder,lstShowAll, lstShowAll_db, lstShowAll_db_nointernate,lstToday, lstThisMonth, lstThreeMonthsAgo, lstOneYearAgo, lstProudOf, lstAccomplish, lstObserved, lstLearned, lstPraised,
            lstTodayletGoOf, lstDreamtOf, lstJournalEntry, lstTheStory, lstChallengedBy, lstCelebrating, lstLaughed, lstImFeeling, lstPicture;
    SimpleDateFormat simpleDateFormatReq = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat strDtaeFormat = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<String> arrAchieveOptions = new ArrayList<>();
    int filterSelectedvalue = 0;
    ImageView imgShowAllCheck;
    ImageView imgTodayCheck;
    ImageView imgThisMonthCheck;
    ImageView imgSixMonthCheck;
    ImageView imgOneYearCheck;
    ImageView imgAmProudOf;
    ImageView imgIHvAccomplish,imgIneedtowork;
    ImageView imgIHvObserve;
    ImageView imgIHvLearned;
    ImageView imgChooseTo;
    ImageView imgIHvPraised;
    ImageView imgTodayLetGoOf;
    ImageView imgDreamtOf;
    ImageView imgJournalEntry;
    ImageView imgTheStory;
    ImageView imgSelectDateRange;
    ImageView imgChallenge;
    ImageView imgCelebrating;
    ImageView imgLaughed;
    ImageView imgTodayImFeeling;
    ImageView imgContainPicture;
    ImageView imgShowPicture; //added by jyotirmoy->j4
    ImageView imgGratefulEntry; //added by jytoirmoy->j14
    ImageView imgWorkingTowards; //added by jytoirmoy->j14
    ImageView imgFeelingGrowth; //added by jytoirmoy->j14
    ImageView imgCommittingTo; //added by jytoirmoy->j14
    ImageView imgPromptOfTheday; //added by jytoirmoy->j14
    ImageView imgFoundGift; //added by jytoirmoy->j15
    ImageView imgHappyToday; //added by jytoirmoy->j15
    ImageView imgIAcknowledge; //added by jytoirmoy->j15
    ImageView imgOneThingIappreciate;
    ImageView imgOnlyPicture;
    boolean boolShowImg = false;
    private String strDialogSelectionType = "";
    ImageView imgBackgroundPicTOP;
    CardView cardViewBackgroundPicTOP;
    LinearLayout llAddPicTOP;
    Button buttonChangeBackgroundImageTOP;
    Button buttonMoveTextBoxTOP;
    RelativeLayout rlTextOverPicInnerTOP;
    File out;
    String stringImg = "";
    private String imgPath = "";
    private static int RESULT_LOAD_IMG = 1434;
    private static int CAMERA_PIC_REQUEST = 1437;
    private static int REQUEST_CODE_SONG = 500;
    private String imgDecodableString = "";
    private CropImageView cropImageView;
    private Bitmap onLineBitMap = null;
    private Bitmap bitimage;

    RelativeLayout rlSearch, rlLoadingNoDataFound;
    ImageView imgSearchAchieve, imgPrintAchieve, imgEQ;
    TextView txtLoading, txtLoadFailed;
    EditText edtSearch;
    EditText edtGratitudeName;
    View globalView;
    public LinearLayout llBottomMenu;
    LinearLayout llSecetDateWhole;
    public ProgressBar loadImageJournal;


    boolean boolDateSelected = false, boolToday = false, boolThisMonth = false, boolSixMonth = false, boolOneYear = false, boolPicture = false;
    boolean boolProudOf = false, boolAccomplished = false,boolNeedToWork = false, boolObserved = false, boolLearned = false, boolPraised = false, boolLetGoOf = false, boolPromptOfTheDay = false,
            boolShowAll = true, boolDreamtOf = false, boolJournalEntry = false, boolTheStory = false, boolChallenged = false, boolCelebrating = false, boolLaughed = false,
            boolTodayImFeeling = false, boolShowPicture = false, boolGratefulFor = false, boolWorkingTowards = false, boolFeelingGrowth = false, boolCommittingTo = false;
    boolean boolFoundGift = false, boolIAcknowledge = false;
    boolean boolIAppreciate = false;


    EditText edtAchieve;
    ImageView imgBigPlus;
    SwipeRefreshLayout swipeLayout;
    String globalSearchStr = "";
    Boolean haveGrowthMinimumOne = false;
    int AUTH_PHONE_USER_REQUEST = 5123;
    boolean isUserVerified = false;
    boolean pagenation_from_api = true;

    private File mFile;
    private ViewModelFactoryForGrowth factoryForGratitude;
    private GrowthViewModel gratitudeViewModel;
    ArrayList<UserEqFolder> mymodelArrayList = new ArrayList<>();

    private GratitudeViewModel gratitudeViewModel1; // added by mugdho
    private ViewModelFactoryForGratitude factoryForGratitude1; // added by mugdho

    private boolean refresh;
    private TextView txtFromDate, txtToDate;

    //added by jyotirmoy patra 28.09.22
    private static int CODE_AUTHENTICATION_VERIFICATION = 241;
    public static boolean FIRST_KEYGUARD_CHECK = false;
    ImageView checkneedtowork,checkGrateful_image, checkPromptofTheDay_image, checkTodayImFelling_image,checkOnething, checkGrateful, checkJournal, checkPromptofTheDay, checkTodayImFelling, checkProudOf, checkAccomplish, checkToward, checkFeelGrowth, checkCommitting, checkCelebrate, checkFoundGift, checkHappy;
    ImageView checkTheStoryAdd, checkChallenged, checkObserved, checkLearned, checkAcknowledge, checkLaughed, checkPraised, checkTodayLetOf, checkIDreamtOf,checkIchoose;
    public boolean promptOfTheDayClick = false, boolFeltHappyToday = false;
    public boolean clickGratitudeEntry = false, jornalImgAdd = false;

    public EndlessRecyclerViewScrollListener scrollListener = null;
    public int Page = 1;
    public int Page_db = 0;
    public int CurrentPage = 0;
    public int CurrentPage_db = 0;
    public int total_page = 0;
    public int total_page_db = 0;
    public int total_result = 0;
    public int total_result_db = 0;
    public int remaining = 0;
    public int remaining_db = 0;
    public int position = 0;
    public int count = 300;
    public MyAchievementsListAdapter myAchievementsListAdapter = null;
  //  public MyAchievementsListAdapter_new myAchievementsListAdapter_new = null;

    private NestedScrollView nestedSV;
    private long mLastClickTime = 0;
    ImageView img_notaccess;
    FrameLayout frm_notaccess;
    Button txt_notaccess;
    LinearLayout ll_total;


    /**/
    MyAchievementsListInnerModel globalmMyAchievementsListInnerModel;
    //    String imgPath = "";
//    String imgDecodableString = "";
//    String stringImg = "";
//    int RESULT_LOAD_IMG = 1434;
//    int CAMERA_PIC_REQUEST = 1437;
//    int REQUEST_CODE_SONG = 500;
    String gratitudeStatus = "";
    String songPathSelect = "";
    MediaPlayer mpObject = null;
    Bundle globalBundel = null;
//    Bitmap onLineBitMap = null;
//    Bitmap bitimage;

    //added by jyotirmoy patra

    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd yyyy");
    boolean globalPickImageFromGratitudeShareDialog = false;
    //    String strDialogSelectionType = "";
    boolean editEnable = false;

    JSONObject rootJson;
    JSONObject rootJsonInner;
    boolean dialogOpenOnceForEdit = false, dialogOpenOnceForAdd = false;
    String[] arrCategory = new String[]{"Fitness", "Nutrition", "Mindset", "Relationship", "Spiritual", "Business", "Personal Development", "Body/Physical Appearance", "Finance", "Health", "Lifestyle/holidays"};
    //    File out;
//    File mFile;
    String PREVIOUS_SET_NOTIFICATION = "";
    String ANNUAL_CROP_PATH = "";
    boolean somethingChanges = false;
    String path = Environment.getExternalStorageDirectory()
            + "/Android/data/"
            + "com.ashysystem.mbhq"
            + "/Files/";

//    ViewModelFactoryForGrowth factoryForGratitude;
//    GrowthViewModel gratitudeViewModel;

    //    final CompositeDisposable mDisposable = new CompositeDisposable();
    String  PREVIOUS_SET_NOTIFICATION1="";
    String OPEN_NOTIFICATION1="";
    CheckBox chkSetReminder;
    MyAchievementsListInnerModel getGratitudeListModelInner_nointernate=null;
    ImageView rlSaveAchievement,imgGratitudeMain,imgSaveGratitude;
    ProgressBar imgJournalLoadingBar;
    String openeditdialog="";
    MyAdapter adapter=null;
    MyAdapter1 adapter1=null;
    private String firstLoginTime = ""; /////////
    private String currentTime = ""; /////////
    private String achievementFirstTime = "";///////
    private String prompt="";

    /* String accesstype="3";
     String habit_access="true";
     String eq_access="true";
     String medi_access="true";
     String forum_access="true";
     String Live_access="true";
     String Test_acess="true";
     String Course_access="true";*/
    String accesstype="";
    String habit_access="";
    String eq_access="";
    String medi_access="";
    String forum_access="";
    String Live_access="";
    String Test_acess="";
    String Course_access="";
    private String filename1="";
    ArrayList<GrowthSaveFilterModel>growthSaveFilterModelArrayList=new ArrayList<>();
    private boolean modelupdated=false;
    private String foldernametoshow="";
    private Integer userfolderid=0;

    private void calldatabase_wheninternatecome(int position_, int count_) {
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();
        mDisposable.add(
                gratitudeViewModel1.getAllAchive(position_,count_)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                achievements -> {
                                    Log.e(TAG, "getAchievementsFromDB0:" + achievements.size());

                                    if (achievements.size() == 0) {
                                        if(false==Util.calledgratitudeafterinternatecomming){
                                            Util.calledgratitudeafterinternatecomming=true;
                                            // Util.showToast(getActivity(), "no data in database");
                                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                            Util.isReloadTodayMainPage = true;
                                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                                        }


                                    } else {
                                        Util.calledgratitudeafterinternatecomming = true;
                                        // Util.showToast(getActivity(), "data in database");

                                    /*    Util.showToast(getActivity(), "data in database");
                                        position = position_;
                                        count = count_;
                                        pagenation_from_api = false;

                                        Log.e(TAG, "getAchievementsFromDB2:" + achievements.size());

                                        lstShowAll.addAll(achievements);
                                        populateList(lstShowAll);*/
                                    }
                                },
                                Throwable::getMessage
                        )
        );
    }



    private BroadcastReceiver networkChangeReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                if("3".equalsIgnoreCase(accesstype)&&"false".equalsIgnoreCase(medi_access)){

                    Util.calledgratitudeafterinternatecomming = false;
                }else{
                    Util.calledgratitudeafterinternatecomming = false;
                    ((MainActivity) getActivity()).rlDownloadedMeditations.setVisibility(View.VISIBLE);

                }

                Util.calledgratitudeafterinternatecomming = false;
                ((MainActivity) getActivity()).rlDownloadedMeditations.setVisibility(View.VISIBLE);

            }else{


                String json= sharedPreference.read("my_list_nointernate", "");
                ArrayList<MyAchievementsListInnerModel> shared_getGratitudeListModelInner_nointernate = new Gson().fromJson(json, new TypeToken<ArrayList<MyAchievementsListInnerModel>>(){}.getType());
                if(null!=shared_getGratitudeListModelInner_nointernate ){
                    Util.calledgratitudeafterinternatecomming = true;
                    Log.e("called_offline","called_offline1");
                    if(shared_getGratitudeListModelInner_nointernate.size()>0){
                        Util.calledgratitudeafterinternatecomming = true;
                        for (int i = 0; i < shared_getGratitudeListModelInner_nointernate.size(); i++) {
                            HashMap<String, Object> requestHash1 = new HashMap<>();
                            requestHash1.put("model", shared_getGratitudeListModelInner_nointernate.get(i));
                            requestHash1.put("Key", Util.KEY);
                            requestHash1.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                            if(null==shared_getGratitudeListModelInner_nointernate.get(i).getPicture()){
                                Log.i("ppp","oo");
                            }else{
                                Log.i("ppp","ooo");
                            }

                            byte[] decodedString = Base64.decode(shared_getGratitudeListModelInner_nointernate.get(i).getPicture(), Base64.DEFAULT);
                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                            String cropPath = storeImage(bitmap);
                            //preaprePictureForUpload(cropPath);
                            File file=null;
                            if(null==cropPath){
                                shared_getGratitudeListModelInner_nointernate.remove(i);
                                Log.e("called_offline","called_offline2");
                                saveAddGratitudeData_internet(requestHash1,shared_getGratitudeListModelInner_nointernate,file);
                            }else{
                                file = new File(cropPath);
                                shared_getGratitudeListModelInner_nointernate.remove(i);
                                Log.e("called_offline","called_offline2");
                                saveAddGratitudeData_internet(requestHash1,shared_getGratitudeListModelInner_nointernate,file);
                            }

                        }


                    }else{
                        calldatabase_wheninternatecome(position, count);
                    }
                }else{
                    calldatabase_wheninternatecome(position, count);
                }
                ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).rlDownloadedMeditations.setVisibility(View.GONE);

            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){

            }else{

                getActivity().unregisterReceiver(networkChangeReceiver1);

            }
        }else{
            getActivity().unregisterReceiver(networkChangeReceiver1);
        }

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         Log.e("state1111111111111111111111111111","onCreate0");
        sharedPreference = new SharedPreference(getActivity());
        Util.checkfolder.clear();
        String json= sharedPreference.read("my_list_eqfolder", "");
        ArrayList<UserEqFolder> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<UserEqFolder>>(){}.getType());
        Log.e("state1111111111111111111111111111",String.valueOf(habitSwapList_.size()));

        if(null!=habitSwapList_ ) {
            if(habitSwapList_.size()>0){
                for(int i=0;i<habitSwapList_.size();i++){
                    if(habitSwapList_.get(i).getIsDefaultView()){
                        Util.checkfolder.add(habitSwapList_.get(i).getUserEqFolderId());

                        // Log.e("state1111111111111111111111111111","onCreate0");
                        Log.e("state1111111111111111111111111111",String.valueOf(checkfolder.size()));
                        Log.e("state1111111111111111111111111111",String.valueOf( Util.checkfolder.size()));

                    }
                }
            }
        }


        eq_access=sharedPreference.read("EqJournalAccess","");
//        eq_access="false";

        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
//        accesstype="3";
        habit_access=sharedPreference.read("HabitAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
        Log.i("printtttttttttttttttttttttttttt",Course_access);
        Log.i("printtttttttttttttttttttttttttt",medi_access);
        Log.i("printtttttttttttttttttttttttttt",accesstype);
        Log.i("printtttttttttttttttttttttttttt",habit_access);
        Log.i("printtttttttttttttttttttttttttt",forum_access);
        Log.i("printtttttttttttttttttttttttttt",Live_access);
        Log.i("printtttttttttttttttttttttttttt",Test_acess);
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){

            }else{
                init();
                Util.sourcepage="Eq";
                IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
                getActivity().registerReceiver(networkChangeReceiver1, filter);


                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    // Internet connection is lost
                    pagenation_from_api=false;
                }else{
                    // GetJounalPromptofDay();
                }
            }
        }else{
            init();
            Util.sourcepage="Eq";
            IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
            getActivity().registerReceiver(networkChangeReceiver1, filter);


            ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                // Internet connection is lost
                pagenation_from_api=false;
            }else{
                // GetJounalPromptofDay();
            }
        }

    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        sharedPreference = new SharedPreference(getActivity());
        eq_access=sharedPreference.read("EqJournalAccess","");
//        eq_access="false";

        Course_access=sharedPreference.read("CourseAccess","");
        medi_access=sharedPreference.read("MeditationAccess","");
        accesstype=sharedPreference.read("accesstype","");
//        accesstype="3";
        habit_access=sharedPreference.read("HabitAccess","");
        forum_access=sharedPreference.read("ForumAccess","");
        Live_access=sharedPreference.read("LiveChatAccess","");
        Test_acess=sharedPreference.read("TestsAccess","");
        if(null==sharedPreference.read("my_list_eq_filter", "")){
            Log.i("growthSaveFilterModellist1","null");

        }else{
            String json1= sharedPreference.read("my_list_eq_filter", "");
            ArrayList<GrowthSaveFilterModel> growthSaveFilterModellist1 = new Gson().fromJson(json1, new TypeToken<ArrayList<GrowthSaveFilterModel>>(){}.getType());
            Log.i("growthSaveFilterModellist1",new Gson().toJson(growthSaveFilterModellist1));
        }

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){
                if (globalView == null) {
                    Log.e("state","oncreateview");
                    globalView = inflater.inflate(R.layout.fragment_myachievements, null);
                    img_notaccess= globalView.findViewById(R.id.img_notaccess);
                    frm_notaccess= globalView.findViewById(R.id.frm_notaccess);
                    txt_notaccess= globalView.findViewById(R.id.txt_notaccess);
                    ll_total= globalView.findViewById(R.id.ll_total);
                    ll_total.setVisibility(View.GONE);
                    img_notaccess.setVisibility(View.VISIBLE);
                    frm_notaccess.setVisibility(View.VISIBLE);
                    txt_notaccess.setVisibility(View.VISIBLE);
                    img_notaccess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse("https://mindbodyhq.com/products/emotional-fitness-club-pro"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                    txt_notaccess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getUserPaidStatusApiCall();
                        }
                    });

                    return globalView;
                }else{
                    ll_total= globalView.findViewById(R.id.ll_total);
                    ll_total.setVisibility(View.GONE);
                    img_notaccess= globalView.findViewById(R.id.img_notaccess);
                    frm_notaccess= globalView.findViewById(R.id.frm_notaccess);
                    txt_notaccess= globalView.findViewById(R.id.txt_notaccess);
                    img_notaccess.setVisibility(View.VISIBLE);
                    frm_notaccess.setVisibility(View.VISIBLE);
                    txt_notaccess.setVisibility(View.VISIBLE);
                    img_notaccess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Uri uri = Uri.parse("https://mindbodyhq.com/products/emotional-fitness-club-pro"); // missing 'http://' will cause crashed
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
                   txt_notaccess.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getUserPaidStatusApiCall();
                        }
                    });

                    return globalView;
                }


                // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
            } else{

                if (globalView == null) {
                    Log.e("state","oncreateview");
                    globalView = inflater.inflate(R.layout.fragment_myachievements, null);
                    ll_total= globalView.findViewById(R.id.ll_total);
                    ll_total.setVisibility(View.VISIBLE);
                    img_notaccess= globalView.findViewById(R.id.img_notaccess);
                    frm_notaccess= globalView.findViewById(R.id.frm_notaccess);
                    txt_notaccess= globalView.findViewById(R.id.txt_notaccess);
                    img_notaccess.setVisibility(View.GONE);
                    frm_notaccess.setVisibility(View.GONE);
                    txt_notaccess.setVisibility(View.GONE);
                    funoncreateview_globalviewnull(globalView);
                    return globalView;
                } else {
                    Log.e("state","oncreateview1");
                    funoncreateview_globalview_notnull(globalView);
                    return globalView;
                }
            }
        } else{
            if (globalView == null) {
                Log.e("state","oncreateview");
                globalView = inflater.inflate(R.layout.fragment_myachievements, null);
                funoncreateview_globalviewnull(globalView);
                return globalView;
            } else {
                Log.e("state","oncreateview1");
                funoncreateview_globalview_notnull(globalView);
                return globalView;
            }
        }


    }

    void funoncreateview_globalview_notnull(View globalView){
        img_notaccess= globalView.findViewById(R.id.img_notaccess);
        frm_notaccess= globalView.findViewById(R.id.frm_notaccess);
        txt_notaccess= globalView.findViewById(R.id.txt_notaccess);
        img_notaccess.setVisibility(View.GONE);
        frm_notaccess.setVisibility(View.GONE);
        txt_notaccess.setVisibility(View.GONE);
        ll_total= globalView.findViewById(R.id.ll_total);
        ll_total.setVisibility(View.VISIBLE);
        txtLoading.setVisibility(View.GONE);
        Log.e("print","text2");
        txtLoading = globalView.findViewById(R.id.txtLoading);
        txtLoading.setVisibility(View.GONE);
        funToolBar();
    }

    void funoncreateview_globalviewnull(View globalView){
            Log.e("state","oncreateview");
            swipeLayout = globalView.findViewById(R.id.swipeLayout);
            img_notaccess= globalView.findViewById(R.id.img_notaccess);
           frm_notaccess= globalView.findViewById(R.id.frm_notaccess);
           txt_notaccess= globalView.findViewById(R.id.txt_notaccess);
            img_notaccess.setVisibility(View.GONE);
        frm_notaccess.setVisibility(View.GONE);
        txt_notaccess.setVisibility(View.GONE);
            ll_total= globalView.findViewById(R.id.ll_total);
            ll_total.setVisibility(View.VISIBLE);
            Log.i("called meditation","called meditation");
            funToolBar();
            sharedPreference = new SharedPreference(getActivity());
            finisherService = new FinisherServiceImpl(getActivity(),sharedPreference.read("jwt", ""));


            txtFromDate = globalView.findViewById(R.id.txtFromDate);
            txtToDate = globalView.findViewById(R.id.txtToDate);
            loadImageJournal = globalView.findViewById(R.id.loadImageJournal1);

            txtGoalValue1 = (TextView) globalView.findViewById(R.id.txtGoalValue1);
            txtGoalValue2 = (TextView) globalView.findViewById(R.id.txtGoalValue2);
            txtGoalValue3 = (TextView) globalView.findViewById(R.id.txtGoalValue3);
            recyclerGratitudes = (RecyclerView) globalView.findViewById(R.id.recyclerGratitudes);

            imgQuestionMark = (ImageView) globalView.findViewById(R.id.imgQuestionMark);
            video_view = (VideoView) globalView.findViewById(R.id.video_view);
            llAddNew = (LinearLayout) globalView.findViewById(R.id.llAddNew);

            imgAchieveAdd = globalView.findViewById(R.id.imgAchieveAdd);
            imgEQ = globalView.findViewById(R.id.imgEQ);
            imgFilterAchieve = globalView.findViewById(R.id.imgFilterAchieve);

          /*  if(null==sharedPreference.read("achivementfilter", "") || "".equalsIgnoreCase(sharedPreference.read("achivementfilter", ""))){
               imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);

               }else{
                 imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);

            }*/

            rlSearch = globalView.findViewById(R.id.rlSearch);
            rlLoadingNoDataFound = globalView.findViewById(R.id.rlLoadingNoDataFound);
            imgSearchAchieve = globalView.findViewById(R.id.imgSearchAchieve);
            imgPrintAchieve = globalView.findViewById(R.id.imgPrintAchieve);
            txtLoading = globalView.findViewById(R.id.txtLoading);
            txtLoadFailed = globalView.findViewById(R.id.txtLoadFailed);
            edtSearch = globalView.findViewById(R.id.edtSearch);
            imgBigPlus = globalView.findViewById(R.id.imgBigPlus);
            lstAll = new ArrayList<>();
            lstShowAll = new ArrayList<>();
            lstShowAll_db = new ArrayList<>();
            lstPicture = new ArrayList<>();
            lstToday = new ArrayList<>();
            lstThisMonth = new ArrayList<>();
            lstThreeMonthsAgo = new ArrayList<>();
            lstOneYearAgo = new ArrayList<>();

            lstProudOf = new ArrayList<>();
            lstAccomplish = new ArrayList<>();
            lstObserved = new ArrayList<>();
            lstLearned = new ArrayList<>();
            lstPraised = new ArrayList<>();
            lstTodayletGoOf = new ArrayList<>();
            lstDreamtOf = new ArrayList<>();
            lstJournalEntry = new ArrayList<>();
            lstTheStory = new ArrayList<>();
            lstChallengedBy = new ArrayList<>();
            lstCelebrating = new ArrayList<>();
            lstLaughed = new ArrayList<>();
            lstImFeeling = new ArrayList<>();


            setimage();


            String isInserted = sharedPreference.read("isInserted", "");
            Log.e(TAG, "onCreateView: " + isInserted);
            if ("no".equals(isInserted)) {

            } else {
                sharedPreference.write("isInserted", "", "yes");
            }


            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if (!isConnected) {
                        Log.e("print","nointernate2");
                        // Internet connection is lost
                        pagenation_from_api=false;
                        //  calldatabase_nointernate(position, count);
                        ((MainActivity) getActivity()).refershGamePoint(getActivity());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                    }else{
                        try {
                            swipeLayout.setRefreshing(false);
                            new MyAsyncTask3().execute();
                        } catch (Exception e) {
                            Log.e("block", "5");
                            e.printStackTrace();
                        }

                    }



                }
            });


            // passing array list to our adapter class.
            myAchievementsListAdapter = new MyAchievementsListAdapter(getActivity(), this);

            // setting layout manager to our recycler view.
//            recyclerGratitudes.setLayoutManager(new LinearLayoutManager(getActivity()));

            // setting adapter to our recycler view.
            recyclerGratitudes.setAdapter(myAchievementsListAdapter);



            // setting layout manager to our recycler view.
            //  recyclerGratitudes.setLayoutManager(new LinearLayoutManager(this));
            // setting adapter to our recycler view.

            LinearLayoutManager layoutManager_all = new LinearLayoutManager(getActivity()) {

                @Override
                public boolean canScrollVertically() {
                    return true;
                }
            };
            recyclerGratitudes.setLayoutManager(layoutManager_all);
            /*added by sahenita*/
            if (!"".equalsIgnoreCase(sharedPreference.read("total_result_db", ""))) {
                total_result_db = Integer.parseInt(sharedPreference.read("total_result_db", ""));
                remaining_db = Integer.parseInt(sharedPreference.read("remaining_db", ""));
                total_page_db = Integer.parseInt(sharedPreference.read("total_page_db", ""));
                CurrentPage_db = Integer.parseInt(sharedPreference.read("CurrentPage_db", ""));
                Log.e(TAG, "total_result_db0: " + total_result_db);

            }
         /*   ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
            if (!isConnected) {
                // Internet connection is lost
                pagenation_from_api=false;
            }else{
             //   GetJounalPromptofDay();
            }*/

            Log.e(TAG, "pagenation:" + pagenation_from_api);
            scrollListener = new EndlessRecyclerViewScrollListener(layoutManager_all) {
                @Override
                public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                    Log.e(TAG, "total_result_db2: " + total_result_db);
                    Log.e(TAG, "current_page_db: " + CurrentPage_db); ///
                    Log.e(TAG, "pagenation_from_api: " + pagenation_from_api);
                    Log.e(TAG, "total_page_db: " + total_page_db);

                    if (pagenation_from_api) {
                        Page = CurrentPage + 1;
                        Log.e(TAG, "onScrollChange: " + Page);
                        // loadingPB.setVisibility(View.VISIBLE);
                        getAchievementsList_loadmore(Page);

                    } else {
                        Log.e(TAG, "total_result_db1: " + total_result_db);

                        CurrentPage_db = CurrentPage_db + 1;//1
                        if (CurrentPage_db < total_page_db) {
                            position = position + count;//0>50>100
                            Log.e(TAG, "total_result_db2: " + total_result_db);
                            Log.e(TAG, "position_db2: " + position);
                            Log.e(TAG, "current_page_db: " + CurrentPage_db); ///
                            int remaining = total_result_db - position;
                            Log.e(TAG, "remaining_db2:: " + remaining);
                            if (remaining > 0) {
                                getAchievementsList_loadmore_for_db(position, count);
                            }
                        }
                    }

                }
            };

            recyclerGratitudes.addOnScrollListener(scrollListener);
            recyclerGratitudes.setAdapter(myAchievementsListAdapter);


               /* if (SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class) == null) {
                    Log.e("SAVE_FILTER", "NULLLLLLLLLLLL");
                    imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                    GrowthSaveFilterModel growthSaveFilterModel = new GrowthSaveFilterModel();
                    SharedPreference.saveObjectToSharedPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", growthSaveFilterModel);
                } else {
                    Log.e("SAVE_FILTER", "NOTTTTTTTT_NULLLLLLLLLLL");
                    GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);
                    if (!growthSaveFilterModel.getBool_proud_of() && !growthSaveFilterModel.getBool_accomplished() && !growthSaveFilterModel.getBool_observed() && !growthSaveFilterModel.getBool_learned() && !growthSaveFilterModel.getBool_praised() && !growthSaveFilterModel.getBool_let_go_of() && !growthSaveFilterModel.getBool_dreamt_of() && !growthSaveFilterModel.getBool_journal_entry() && !growthSaveFilterModel.getBool_the_story() && !growthSaveFilterModel.getBool_challenged() && !growthSaveFilterModel.getBool_celebrating() && !growthSaveFilterModel.getBool_laughed() && !growthSaveFilterModel.getBool_Today_I_M_Feeling() && growthSaveFilterModel.getSelected_date_range_filter_value() == 0 && growthSaveFilterModel.getStrGrowthSerach().equals("")
                            && !growthSaveFilterModel.getBool_working_towards() && !growthSaveFilterModel.getBool_found_gift() && !growthSaveFilterModel.getBool_I_acknowledge() && !growthSaveFilterModel.getBool_felt_happy_today()
                            && !growthSaveFilterModel.getBool_prompt_of_The_day()&& !growthSaveFilterModel.getBool_feeling_growth()&& !growthSaveFilterModel.getBool_committing_to()
                            && !growthSaveFilterModel.getBool_grateful_for()&& !growthSaveFilterModel.getBool_I_Appreciate()&& !growthSaveFilterModel.getBool_I_choose_To_Know()&& !growthSaveFilterModel.getBool_only_pic()&& growthSaveFilterModel.getBool_show_img()

                    ) {
                        Util.achivementfilter="";

                        sharedPreference.write("achivementfilter", "", "");

                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
                    } else {

                        sharedPreference.write("achivementfilter", "", "yes");

                        Util.achivementfilter="yes";
                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                    }
                }*/

            Log.e("SAVE_FILTER", "0");
            String userid=sharedPreference.read("UserID", "");
            Log.e("SAVE_FILTER", userid);

            if(null!=sharedPreference.read("my_list_eq_filter", "")){
                String json= sharedPreference.read("my_list_eq_filter", "");
                Log.e("SAVE_FILTER", json);
                if(null==new Gson().fromJson(json, new TypeToken<ArrayList<GrowthSaveFilterModel>>(){}.getType())){
                    ArrayList<GrowthSaveFilterModel> growthSaveFilterModellist=new ArrayList<>();
                    Log.e("SAVE_FILTER", "NULLLLLLLLLLLL");
                    imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                    GrowthSaveFilterModel growthSaveFilterModel = new GrowthSaveFilterModel();
                    growthSaveFilterModellist.add(growthSaveFilterModel);
                    sharedPreference.write("my_list_eq_filter", "", new Gson().toJson(growthSaveFilterModellist));
                    // GrowthSaveFilterModel growthSaveFilterModel = new GrowthSaveFilterModel();
                    SharedPreference.saveObjectToSharedPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", growthSaveFilterModel);
                }else{
                    ArrayList<GrowthSaveFilterModel> growthSaveFilterModellist=new ArrayList<>();
                    growthSaveFilterModellist = new Gson().fromJson(json, new TypeToken<ArrayList<GrowthSaveFilterModel>>(){}.getType());
                    Log.e("SAVE_FILTER", "NOT_NULLLLLLLLLLLL");
                    // Log.e("SAVE_FILTER", String.valueOf(growthSaveFilterModellist.size()));
                    // Log.e("SAVE_FILTER", String.valueOf(growthSaveFilterModellist.get(0).getUserid()));

                    GrowthSaveFilterModel growthSaveFilterModel=new GrowthSaveFilterModel();
                    if(growthSaveFilterModellist.size()>0){
                        for(int i =0;i<growthSaveFilterModellist.size();i++){
                            Log.e("SAVE_FILTER", "1");
                            if(userid.equalsIgnoreCase(growthSaveFilterModellist.get(i).getUserid())){
                                Log.e("SAVE_FILTER", "2");
                                growthSaveFilterModel=growthSaveFilterModellist.get(i);
                                modelupdated=true;
                                break;
                            }
                        }
                        if(modelupdated){

                        }else{

                            growthSaveFilterModel=new GrowthSaveFilterModel();

                        }
                    }else{
                        Log.e("SAVE_FILTER", "5");
                        growthSaveFilterModel=new GrowthSaveFilterModel();
                    }
                    SharedPreference.saveObjectToSharedPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", growthSaveFilterModel);
                    Log.d("SAVE_FILTER", growthSaveFilterModel.toString());

                    if (!growthSaveFilterModel.getBool_proud_of() && !growthSaveFilterModel.getBool_accomplished() &&!growthSaveFilterModel.getBool_needTowork()&& !growthSaveFilterModel.getBool_observed() && !growthSaveFilterModel.getBool_learned() && !growthSaveFilterModel.getBool_praised() && !growthSaveFilterModel.getBool_let_go_of() && !growthSaveFilterModel.getBool_dreamt_of() && !growthSaveFilterModel.getBool_journal_entry() && !growthSaveFilterModel.getBool_the_story() && !growthSaveFilterModel.getBool_challenged() && !growthSaveFilterModel.getBool_celebrating() && !growthSaveFilterModel.getBool_laughed() && !growthSaveFilterModel.getBool_Today_I_M_Feeling() && growthSaveFilterModel.getSelected_date_range_filter_value() == 0 && growthSaveFilterModel.getStrGrowthSerach().equals("")
                            && !growthSaveFilterModel.getBool_working_towards() && !growthSaveFilterModel.getBool_found_gift() && !growthSaveFilterModel.getBool_I_acknowledge() && !growthSaveFilterModel.getBool_felt_happy_today()
                            && !growthSaveFilterModel.getBool_prompt_of_The_day()&& !growthSaveFilterModel.getBool_feeling_growth()&& !growthSaveFilterModel.getBool_committing_to()
                            && !growthSaveFilterModel.getBool_grateful_for()&& !growthSaveFilterModel.getBool_I_Appreciate()&& !growthSaveFilterModel.getBool_I_choose_To_Know()&& !growthSaveFilterModel.getBool_only_pic()&& growthSaveFilterModel.getBool_show_img()

                    ) {
                        // Log.d("SAVE_FILTER", growthSaveFilterModel.toString());
                        Log.e("SAVE_FILTER", "3");
                        Util.achivementfilter="";

                        sharedPreference.write("achivementfilter", "", "");

                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
                    } else {
                        Log.e("SAVE_FILTER", "4");
                        Log.d("SAVE_FILTER", growthSaveFilterModel.toString());
                        sharedPreference.write("achivementfilter", "", "yes");

                        Util.achivementfilter="yes";
                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                    }
                }
                   /* growthSaveFilterModellist = new Gson().fromJson(json, new TypeToken<ArrayList<GrowthSaveFilterModel>>(){}.getType());
                    if(null==growthSaveFilterModellist|| growthSaveFilterModellist.size()==0){
                        Log.e("SAVE_FILTER", "NULLLLLLLLLLLL");
                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
//                        GrowthSaveFilterModel growthSaveFilterModel = new GrowthSaveFilterModel();
//                        growthSaveFilterModellist.add(growthSaveFilterModel);
                        //sharedPreference.write("my_list_eq_filter", "", new Gson().toJson(growthSaveFilterModellist));

                    }else{
                        Log.e("SAVE_FILTER", "NOT_NULLLLLLLLLLLL");
                        GrowthSaveFilterModel growthSaveFilterModel=null;
                        for(int i =0;i<growthSaveFilterModellist.size();i++){
                            Log.e("SAVE_FILTER", "1");
                            if(userid.equalsIgnoreCase(growthSaveFilterModellist.get(i).getUserid())){
                                Log.e("SAVE_FILTER", "2");
                                growthSaveFilterModel=growthSaveFilterModellist.get(i);
                                break;
                            }
                        }
                        Log.d("SAVE_FILTER", growthSaveFilterModel.toString());

                        if (!growthSaveFilterModel.getBool_proud_of() && !growthSaveFilterModel.getBool_accomplished() && !growthSaveFilterModel.getBool_observed() && !growthSaveFilterModel.getBool_learned() && !growthSaveFilterModel.getBool_praised() && !growthSaveFilterModel.getBool_let_go_of() && !growthSaveFilterModel.getBool_dreamt_of() && !growthSaveFilterModel.getBool_journal_entry() && !growthSaveFilterModel.getBool_the_story() && !growthSaveFilterModel.getBool_challenged() && !growthSaveFilterModel.getBool_celebrating() && !growthSaveFilterModel.getBool_laughed() && !growthSaveFilterModel.getBool_Today_I_M_Feeling() && growthSaveFilterModel.getSelected_date_range_filter_value() == 0 && growthSaveFilterModel.getStrGrowthSerach().equals("")
                                && !growthSaveFilterModel.getBool_working_towards() && !growthSaveFilterModel.getBool_found_gift() && !growthSaveFilterModel.getBool_I_acknowledge() && !growthSaveFilterModel.getBool_felt_happy_today()
                                && !growthSaveFilterModel.getBool_prompt_of_The_day()&& !growthSaveFilterModel.getBool_feeling_growth()&& !growthSaveFilterModel.getBool_committing_to()
                                && !growthSaveFilterModel.getBool_grateful_for()&& !growthSaveFilterModel.getBool_I_Appreciate()&& !growthSaveFilterModel.getBool_I_choose_To_Know()&& !growthSaveFilterModel.getBool_only_pic()&& growthSaveFilterModel.getBool_show_img()

                        ) {
                            Log.d("SAVE_FILTER", growthSaveFilterModel.toString());
                            Log.e("SAVE_FILTER", "3");
                            Util.achivementfilter="";

                            sharedPreference.write("achivementfilter", "", "");

                            imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
                        } else {
                            Log.e("SAVE_FILTER", "4");
                            Log.d("SAVE_FILTER", growthSaveFilterModel.toString());
                            sharedPreference.write("achivementfilter", "", "yes");

                            Util.achivementfilter="yes";
                            imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                        }

                    }*/
            }

               /* if (SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class) == null) {
                    Log.e("SAVE_FILTER", "NULLLLLLLLLLLL");
                    imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                    GrowthSaveFilterModel growthSaveFilterModel = new GrowthSaveFilterModel();
                    SharedPreference.saveObjectToSharedPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", growthSaveFilterModel);
                } else {
                    Log.e("SAVE_FILTER", "NOTTTTTTTT_NULLLLLLLLLLL");
                    GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);
                    if (!growthSaveFilterModel.getBool_proud_of() && !growthSaveFilterModel.getBool_accomplished() && !growthSaveFilterModel.getBool_observed() && !growthSaveFilterModel.getBool_learned() && !growthSaveFilterModel.getBool_praised() && !growthSaveFilterModel.getBool_let_go_of() && !growthSaveFilterModel.getBool_dreamt_of() && !growthSaveFilterModel.getBool_journal_entry() && !growthSaveFilterModel.getBool_the_story() && !growthSaveFilterModel.getBool_challenged() && !growthSaveFilterModel.getBool_celebrating() && !growthSaveFilterModel.getBool_laughed() && !growthSaveFilterModel.getBool_Today_I_M_Feeling() && growthSaveFilterModel.getSelected_date_range_filter_value() == 0 && growthSaveFilterModel.getStrGrowthSerach().equals("")
                            && !growthSaveFilterModel.getBool_working_towards() && !growthSaveFilterModel.getBool_found_gift() && !growthSaveFilterModel.getBool_I_acknowledge() && !growthSaveFilterModel.getBool_felt_happy_today()
                            && !growthSaveFilterModel.getBool_prompt_of_The_day()&& !growthSaveFilterModel.getBool_feeling_growth()&& !growthSaveFilterModel.getBool_committing_to()
                            && !growthSaveFilterModel.getBool_grateful_for()&& !growthSaveFilterModel.getBool_I_Appreciate()&& !growthSaveFilterModel.getBool_I_choose_To_Know()&& !growthSaveFilterModel.getBool_only_pic()&& growthSaveFilterModel.getBool_show_img()

                    ) {
                        Util.achivementfilter="";

                        sharedPreference.write("achivementfilter", "", "");

                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
                    } else {

                        sharedPreference.write("achivementfilter", "", "yes");

                        Util.achivementfilter="yes";
                        imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                    }
                }*/

            imgSearchAchieve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (rlSearch.getVisibility() == View.VISIBLE) {
                        rlSearch.setVisibility(View.GONE);
                    } else {
                        rlSearch.setVisibility(View.VISIBLE);
                    }
                }
            });

            imgAchieveAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    long currentTime = SystemClock.elapsedRealtime();
                    if (currentTime - lastClickTime < CLICK_DELAY) {
                        return; // Ignore the click event
                    }
                    lastClickTime = currentTime;
                    GetJounalPromptofDay();



                }
            });

            imgPrintAchieve.setOnClickListener(view -> {
                printGrowthApiCall();
            });

            imgBigPlus.setOnClickListener(view -> {
                if (!Util.isShowDialogToday(requireContext(), "GROWTH")) {
                    openDialogForAchieveAdd();
                } else showEQDialog(true);
            });


            imgFilterAchieve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    openDialogForFilter();
                }
            });

            imgQuestionMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (videoBarOpenStatus) {
                        videoBarOpenStatus = false;
                        video_view.setVisibility(View.GONE);
                    } else {
                        videoBarOpenStatus = true;
                        video_view.setVisibility(View.VISIBLE);
                    }
                }
            });


            llAddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Bundle bundle = new Bundle();
                    bundle.putString("GRATITUDESTATUS", "ADD");
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsListAddEditFragment(), "MyAchievementsListAddEdit", bundle);
                }
            });

            imgEQ.setOnClickListener(view -> {
                showEQDialog(false);
            });

            if (getArguments() != null && getArguments().containsKey("ADD_GROWTH_FROM_TODAY")) {
                imgAchieveAdd.performClick();
            }

            if (sharedPreference.read("GROWTH_INFO_ONCE", "").equals("")) {
                if (!Util.grateFul_popup &&  Util.grateFul_popup_from_habit) {       //add by jyotirmoy-j15
                    // openGrowthAndGratitudeDialog();
                }
            } else {
                //  JournalDailyPopupCall();   //added by jyotirmoy patra 18.10.22


            }
            try{
                achievementFirstTime = sharedPreference.read("achievementFirstTime","");

                firstLoginTime = sharedPreference.read("FIRST_LOGIN_TIME", "");
                Log.e("FFLLLIIII", "onCreateView: " + firstLoginTime );

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                Calendar calendar = Calendar.getInstance();
                currentTime = simpleDateFormat.format(calendar.getTime());
                Log.e("CURRRTIIII", "onCreateView: " + currentTime );

                Date dateFirstLoginDt = simpleDateFormat.parse(firstLoginTime);
                Date dateCurrentTime = simpleDateFormat.parse(currentTime);

                if (dateCurrentTime != null) {
                    if(dateCurrentTime.after(dateFirstLoginDt) && "true".equals(achievementFirstTime)){
                        Log.e("CHHHHTIMMMMMM", "onCreateView: " + "TRUE" );
                        sharedPreference.write("achievementFirstTime", "", "false");

                        // show popup here
                        // openGratitudeDialog();

                    }else {
                        Log.e("CHHHHTIMMMMMM", "onCreateView: " + "FALSE" );
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

            //add by jyotirmoy-j15
            if (Util.grateFul_popup) {
                openDialogForAchieveAdd();
            }

            txtLoading.setVisibility(View.GONE);
            Util.grateFul_popup_from_habit=true;

    }
    private void loadData1(ArrayList<MyAchievementsListInnerModel> details,String s) {

        Log.i("textchange","4");

        Log.i("textchange",s);

        // new setNotificationAsynctask().execute(details);
        loadImageJournal.setVisibility(View.GONE);
        //txtLoading.setVisibility(View.GONE);
        txtLoading.setVisibility(View.GONE);
        if (details.size() > 0) {
            // Log.e(TAG, "loadData: " + details.size());
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.GONE);
            txtLoading.setText("loading...");
            Log.i("textchange","5");
            myAchievementsListAdapter.loaddata(details);
            Log.i("textchange","6");
            if (recyclerGratitudes != null && recyclerGratitudes.getAdapter() != null) {
                Log.i("textchange","7");
                ((MyAchievementsListAdapter) recyclerGratitudes.getAdapter()).search(s.toString());

            }
            // myAchievementsListAdapter.notifyDataSetChanged();
        } else {
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.GONE);
            txtLoading.setText("no data found");
        }



    }


    private void loadData(ArrayList<MyAchievementsListInnerModel> details) {

        // new setNotificationAsynctask().execute(details);
        loadImageJournal.setVisibility(View.GONE);
        //txtLoading.setVisibility(View.GONE);
        txtLoading.setVisibility(View.GONE);
        if (details.size() > 0) {
            // Log.e(TAG, "loadData: " + details.size());
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.GONE);
            txtLoading.setText("loading...");
            myAchievementsListAdapter.loaddata(details);
            // myAchievementsListAdapter.notifyDataSetChanged();
        } else {
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.GONE);
            txtLoading.setText("no data found");
        }



    }

    private void loadAdapter1(ArrayList<MyAchievementsListInnerModel> details,String s) {
        Log.i("textchange","3");
        Log.i("textchange",s);
        Log.e(TAG, "loadAdapter: " + details.size());

        for (int i = 0; i < details.size(); i++) {
            SetLocalNotificationForAchievement.setNotificationForAchievement(details.get(i), getActivity());
        }

        if (haveGrowthMinimumOne) {
            if (details.size() > 0) {
                txtLoading.setVisibility(View.GONE);
                imgBigPlus.setVisibility(View.GONE);
                txtLoading.setText("loading...");
            } else {
                txtLoading.setVisibility(View.GONE);
                imgBigPlus.setVisibility(View.GONE);
                txtLoading.setText("no data found");
            }
        } else {
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.VISIBLE);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.e("state_","callednointernate4");

        if(!isConnected){
            String json= sharedPreference.read("my_list_nointernate", "");
            ArrayList<MyAchievementsListInnerModel> shared_getGratitudeListModelInner_nointernate = new Gson().fromJson(json, new TypeToken<ArrayList<MyAchievementsListInnerModel>>(){}.getType());
            if(null!=shared_getGratitudeListModelInner_nointernate ){
                Log.e("state_","callednointernate5");
                if(shared_getGratitudeListModelInner_nointernate.size()>0){
                    Log.e("state_",String.valueOf(details.size()));
                    shared_getGratitudeListModelInner_nointernate.addAll(details);
                    Log.e("state_","callednointernate6");
                    Log.e("state_",String.valueOf(shared_getGratitudeListModelInner_nointernate.size()));

                    Log.e("state_",shared_getGratitudeListModelInner_nointernate.get(0).getPicture());

                    loadData1(shared_getGratitudeListModelInner_nointernate,s);

                }else{
                    Log.e("state_","callednointernate7");
                    loadData1(details,s);
                }
            }else{
                Log.e("state_","callednointernate8");
                loadData1(details,s);
            }

        }else{
            Log.e("state_","callednointernate9");
            loadData1(details,s);
        }


    }

    private void loadAdapter(ArrayList<MyAchievementsListInnerModel> details) {
        Log.e(TAG, "loadAdapter: " + details.size());

        for (int i = 0; i < details.size(); i++) {
            SetLocalNotificationForAchievement.setNotificationForAchievement(details.get(i), getActivity());
        }

        if (haveGrowthMinimumOne) {
            if (details.size() > 0) {
                txtLoading.setVisibility(View.GONE);
                imgBigPlus.setVisibility(View.GONE);
                txtLoading.setText("loading...");
            } else {
                txtLoading.setVisibility(View.GONE);
                imgBigPlus.setVisibility(View.GONE);
                txtLoading.setText("no data found");
            }
        } else {
            txtLoading.setVisibility(View.GONE);
            imgBigPlus.setVisibility(View.VISIBLE);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        Log.e("state_","callednointernate4");

        if(!isConnected){
            String json= sharedPreference.read("my_list_nointernate", "");
            ArrayList<MyAchievementsListInnerModel> shared_getGratitudeListModelInner_nointernate = new Gson().fromJson(json, new TypeToken<ArrayList<MyAchievementsListInnerModel>>(){}.getType());
            if(null!=shared_getGratitudeListModelInner_nointernate ){
                Log.e("state_","callednointernate5");
                if(shared_getGratitudeListModelInner_nointernate.size()>0){
                    Log.e("state_",String.valueOf(details.size()));
                    shared_getGratitudeListModelInner_nointernate.addAll(details);
                    Log.e("state_","callednointernate6");
                    Log.e("state_",String.valueOf(shared_getGratitudeListModelInner_nointernate.size()));

                    Log.e("state_",shared_getGratitudeListModelInner_nointernate.get(0).getPicture());

                    loadData(shared_getGratitudeListModelInner_nointernate);

                }else{
                    Log.e("state_","callednointernate7");
                    loadData(details);
                }
            }else{
                Log.e("state_","callednointernate8");
                loadData(details);
            }

        }else{
            Log.e("state_","callednointernate9");
            loadData(details);
        }


    }

    private MyAchievementsListInnerModel makeAchieveModelForAdd_nonetwork(EditText edtAchieve, Integer selectedOption) {

        MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
        getGratitudeListModelInner.setPrompt(arrAchieveOptions.get(selectedOption));
        Log.e("GRATITUDEIMAGE0", arrAchieveOptions.get(selectedOption));
        Log.e("GRATITUDEIMAGE00", getGratitudeListModelInner.getPrompt());
        getGratitudeListModelInner.setAchievement(edtAchieve.getText().toString().trim());
        getGratitudeListModelInner.setPicture(sharedPreference.read("GRATITUDEIMAGE", ""));
        Log.e("GRATITUDEIMAGE4",  getGratitudeListModelInner.getPicture());
        getGratitudeListModelInner.setId(0);
        // getGratitudeListModelInner.setDateValue("Today");
        // Log.e("GRATITUDEIMAGE5",  getGratitudeListModelInner.getDateValue());

        getGratitudeListModelInner.setHasImage(true);

        getGratitudeListModelInner.setNotes("");
        getGratitudeListModelInner.setUserEqFolderId(filename1);

        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
        getGratitudeListModelInner.setStatus(false);
        getGratitudeListModelInner.setIsDeleted(false);

        getGratitudeListModelInner.setCategoryId(1);

        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1(43200);
        getGratitudeListModelInner.setReminderAt2(43200);
        getGratitudeListModelInner.setUploadPictureImgBase64("");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        getGratitudeListModelInner.setCreatedAt(simpleDateFormat.format(calendar.getTime()));

        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1(43200);
        getGratitudeListModelInner.setReminderAt2(43200);
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setSunday(false);
        getGratitudeListModelInner.setMonday(false);
        getGratitudeListModelInner.setTuesday(false);
        getGratitudeListModelInner.setWednesday(false);
        getGratitudeListModelInner.setThursday(false);
        getGratitudeListModelInner.setFriday(false);
        getGratitudeListModelInner.setSaturday(false);
        getGratitudeListModelInner.setJanuary(false);
        getGratitudeListModelInner.setFebruary(false);
        getGratitudeListModelInner.setMarch(false);
        getGratitudeListModelInner.setApril(false);
        getGratitudeListModelInner.setMay(false);
        getGratitudeListModelInner.setJune(false);
        getGratitudeListModelInner.setJuly(false);
        getGratitudeListModelInner.setAugust(false);
        getGratitudeListModelInner.setSeptember(false);
        getGratitudeListModelInner.setOctober(false);
        getGratitudeListModelInner.setNovember(false);
        getGratitudeListModelInner.setDecember(false);
        getGratitudeListModelInner.setUploadPictureImgBase64("");

        return getGratitudeListModelInner;
    }

    private void showEQDialog(Boolean isOpenJournal) {
        final Dialog dialog = new Dialog(requireContext(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_growth_eq);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlGotIt = dialog.findViewById(R.id.rlGotIt);
        imgCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (isOpenJournal)
                    //openDialogForAchieveAdd();
                    openDialogForAchieveAddWithOption(3);

            }
        });
        rlGotIt.setOnClickListener(view1 -> {
            dialog.dismiss();
            if (isOpenJournal)
                //openDialogForAchieveAdd();
                openDialogForAchieveAddWithOption(3);

        });
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MyAchievementsFragment", "request code => " + requestCode + "result code => " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if ("".equalsIgnoreCase(openeditdialog)) {
            if (requestCode == AUTH_PHONE_USER_REQUEST) {
                if (resultCode == RESULT_OK) {
                    isUserVerified = true;
                } else {
                    ((MainActivity) requireActivity()).rlGratitude.performClick();
                }
            } else if(requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
//                if (imgPath != null) {
//                    Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                    cropPhoto(imageBitmap,photoFile.getAbsolutePath());
//                }
                if (!imgPath.equals("")) {

                    Log.e("camera", "03");
                    File selectedImagePath = new File(imgPath);
                    imgDecodableString = selectedImagePath.getAbsolutePath();
                    cropPhoto(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()), imgDecodableString);
                }
            }
            else {
                if (resultCode != 0) {

                    Log.e("camera", "01");
                    if (data == null) {


                    }
                    else if (requestCode == ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST && resultCode == getActivity().RESULT_OK
                            && null != data) {
                        Log.e("camera", "04");
                        // Get the Image from data
                        Log.e("PICTURE Gal---->", "123");

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        // Get the cursor
                        if (getActivity() != null) {
                            Cursor cursor = ((MainActivity) getActivity()).getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);

                        }
                        Cursor cursor = ((MainActivity) getActivity()).getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgDecodableString = cursor.getString(columnIndex);
                        File comImg = null;
                        try {
                            if (getContext() != null) {
                                comImg = new Compressor(getContext()).compressToFile(new File(imgDecodableString));

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        long bitmapLengthSize = comImg.length();
                        Log.e("gal compress img", "gallery compress img size>>>>>>>>>" + (bitmapLengthSize * 0.001) + " KB");
                        cursor.close();
                        cropPhoto(BitmapFactory.decodeFile(imgDecodableString), comImg.getAbsolutePath());

                    } else if (requestCode == ((MainActivity) getActivity()).CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST && resultCode == getActivity().RESULT_OK && null != data) {
                        Log.e("camera", "05");
                        imgDecodableString = out.getPath();
                        Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                        if (bit != null) {
                            Log.e("camera", "06");
                            cropPhoto(bit, imgDecodableString);
                        }
                    } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == getActivity().RESULT_OK && null != data) {
                        // Log.e("camera", "07");
                        Log.e("CMMMRRRR", "onActivityResult: " + "CPRQ1" );
                        imgDecodableString = out.getPath();
                        Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                        if (bit != null) {
                            Log.e("camera", "08");
                            cropPhoto(bit, imgDecodableString);

                        }
                    } else {
                        Toast.makeText(getContext(), "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getContext(), "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            }
        } else if ("yes".equalsIgnoreCase(openeditdialog)) {
            if (requestCode != 0) {
                if (requestCode == REQUEST_CODE_SONG) {
                    String songPath = data.getStringExtra("song");
                    Log.e("song path-->", songPath + "?");
                    //  if (!songPath.equals(""))
                    // PlayMusic(songPath);
                } else if(requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
//                    if (photoFile != null) {
//                        Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
//                        cropPhoto1(imageBitmap,photoFile.getAbsolutePath());
//                    }
                    if (!imgPath.equals("")) {

                        Log.e("camera", "03");
                        File selectedImagePath = new File(imgPath);
                        imgDecodableString = selectedImagePath.getAbsolutePath();
                        cropPhoto1(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()), imgDecodableString);
                    }

                }
                else {
                    if (data == null) {

                    } else if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
                            && null != data) {

                        // Get the Image from data
                        Log.e("PICTURE Gal---->", "123");

                        Uri selectedImage = data.getData();
                        // if (Build.VERSION.SDK_INT < 19)
                        {
                            String[] filePathColumn = {MediaStore.Images.Media.DATA};

                            // Get the cursor
                            Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            // Move to first row
                            cursor.moveToFirst();
                            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                            imgDecodableString = cursor.getString(columnIndex);

                            File compressImg = null;
                            try {
                                compressImg = new Compressor(getContext()).compressToFile(new File(imgDecodableString));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            long bitmapLengthSize = compressImg.length();
                            Log.e("galcompress img", "Gal com img size>>>>>>>>>" + (bitmapLengthSize * 0.001) + " KB");

                            cursor.close();
                            cropPhoto1(BitmapFactory.decodeFile(imgDecodableString), compressImg.getAbsolutePath());


                        }

                    } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == getActivity().RESULT_OK && null != data) {
                        Log.e("CMMMRRRR", "onActivityResult: " + "CPRQ2" );
                        imgDecodableString = out.getPath();
                        Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                        if (bit != null) {
                            cropPhoto1(bit, imgDecodableString);
                        }
                    }
                    else {
                        Toast.makeText(getActivity(), "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }
                }
            } else {
                Toast.makeText(getActivity(), "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
    private boolean isDeviceSecure() {
        KeyguardManager keyguardManager = (KeyguardManager) requireActivity().getSystemService(KEYGUARD_SERVICE);
        //this method only work whose api level is greater than or equal to Jelly_Bean (16)
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && keyguardManager.isKeyguardSecure();
        //You can also use keyguardManager.isDeviceSecure(); but it requires API Level 23
    }

    private void authenticatePhoneUser() {
        if (sharedPreference.readBoolean("DOES_GROWTH_REQUIRES_PASSWORD", "")) {
            try {
                if (isDeviceSecure()) {
                    KeyguardManager keyguardManager = (KeyguardManager) requireActivity().getSystemService(KEYGUARD_SERVICE);
                    Intent userConfirmation = keyguardManager.createConfirmDeviceCredentialIntent("Enter phone screen lock pattern, PIN, password or fingerprint for \"Mbhq\"", "Enter password to gain access");
                    startActivityForResult(userConfirmation, AUTH_PHONE_USER_REQUEST);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }


    private void GetJounalPromptofDay() {
        if (Connection.checkConnection(getActivity())) {
            Log.i("promptttttttt","1");
            SharedPreference sharedPreference = new SharedPreference(getActivity());
            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));
            Log.e("userSession", "sessionid>>>" + sharedPreference.read("UserSessionID", ""));
            Log.e("key", "key>>>" + Util.KEY);
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity(),sharedPreference.read("jwt", ""));
            Call<GetPrompt> promptCall = finisherService.getJounalPromptofDay(hashReq);
            promptCall.enqueue(new Callback<GetPrompt>() {
                @Override
                public void onResponse(Call<GetPrompt> call, Response<GetPrompt> response) {

                    if (response.isSuccessful()) {


                        prompt = response.body().getPromptOftheDay().toString();
                        arrAchieveOptions.add("I'm Grateful For:");
                        arrAchieveOptions.add("Journal Entry:");

                        arrAchieveOptions.add(prompt);

                        arrAchieveOptions.add("Today I'm Feeling:");
                        arrAchieveOptions.add("I am proud of:");
                        arrAchieveOptions.add("I've accomplished:");
                        arrAchieveOptions.add("I'm working towards:");
                        arrAchieveOptions.add("I'm feeling growth in:");
                        arrAchieveOptions.add("I’m committing to:");
                        arrAchieveOptions.add("A small win I'm celebrating is:");
                        arrAchieveOptions.add("I've found the gift in:");
                        arrAchieveOptions.add("I felt happy today because:");

                        arrAchieveOptions.add("The story I've told myself is:");
                        arrAchieveOptions.add("I've been challenged by:");
                        arrAchieveOptions.add("I need to work on/clear:");
                        arrAchieveOptions.add("I've observed:");
                        arrAchieveOptions.add("I've learned:");
                        arrAchieveOptions.add("I acknowledge:");
                        arrAchieveOptions.add("I laughed:");
                        arrAchieveOptions.add("I've praised:");
                        arrAchieveOptions.add("I've let go of:");
                        arrAchieveOptions.add("I dreamt of:");
                        arrAchieveOptions.add("One thing I appreciate about you today is:");
                        arrAchieveOptions.add("I Choose to Know:");
                        openDialogForAchieveAdd();

                    } else {
                        Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                    }

                }

                @Override
                public void onFailure(Call<GetPrompt> call, Throwable t) {
                    Log.e("error prompt", "errrrrrrrrrrrrrrrrrrrrrr");
                }
            });
        }else{
            Log.i("promptttttttt","2");
            arrAchieveOptions.add("I'm Grateful For:");
            arrAchieveOptions.add("Journal Entry:");


            if("".equalsIgnoreCase(Util.prompt)){
                Log.i("promptttttttt","3");
                arrAchieveOptions.add("prompt of the day");
            }else{
                Log.i("promptttttttt","4");
                arrAchieveOptions.add(Util.prompt);
            }


            arrAchieveOptions.add("Today I'm Feeling:");
            arrAchieveOptions.add("I am proud of:");
            arrAchieveOptions.add("I've accomplished:");
            arrAchieveOptions.add("I'm working towards:");
            arrAchieveOptions.add("I'm feeling growth in:");
            arrAchieveOptions.add("I’m committing to:");
            arrAchieveOptions.add("A small win I'm celebrating is:");
            arrAchieveOptions.add("I've found the gift in:");
            arrAchieveOptions.add("I felt happy today because:");


            arrAchieveOptions.add("The story I've told myself is:");
            arrAchieveOptions.add("I've been challenged by:");
            arrAchieveOptions.add("I need to work on/clear:");
            arrAchieveOptions.add("I've observed:");
            arrAchieveOptions.add("I've learned:");
            arrAchieveOptions.add("I acknowledge:");
            arrAchieveOptions.add("I laughed:");
            arrAchieveOptions.add("I've praised:");
            arrAchieveOptions.add("I've let go of:");
            arrAchieveOptions.add("I dreamt of:");
            arrAchieveOptions.add("One thing I appreciate about you today is:");
            arrAchieveOptions.add("I Choose to Know:");
            openDialogForAchieveAdd();
        }

    }


    private void printGrowthApiCall() {
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserID", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.emailReverseBucketList(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().has("SuccessFlag")) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.showToast(getActivity(), "Email Sent Successfully");
                        }
                    }

                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-2");
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }
    private void getAchievementsList_filter(int page) {
        Log.e("call api", "yes");
        txtLoading.setVisibility(View.GONE);
        txtLoading.setText("loading...");
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("PageNo", page);
            hashReq.put("Count", 1000);

            Call<MyAchievementsListModel> getGratitudeListModelCall = finisherService.getMyAchievevmentsList(hashReq);
            getGratitudeListModelCall.enqueue(new Callback<MyAchievementsListModel>() {
                @Override
                public void onResponse(Call<MyAchievementsListModel> call, Response<MyAchievementsListModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {

                            total_result = response.body().getTotalCount();
                            Log.i("total_result", String.valueOf(total_result));
                            total_page = response.body().getTotalCount() / 20;
                            Log.i("total_page", String.valueOf(total_page));
                            remaining = response.body().getTotalCount() % 20;
                            Log.i("remaining", String.valueOf(remaining));
                            if (remaining > 0) {
                                total_page = total_page + 1;
                            } else {
                            }
                            CurrentPage = page;
                            haveGrowthMinimumOne = true;

                            lstShowAll = response.body().getDetails();




                            Log.e(TAG, "onResponse: " + lstShowAll.toString());
                            // loadData(lstShowAll);
                            Date dtToday = null, dtThisMonth = null, dtThreeMonth = null, dtOneYear = null, dtYesterDay = null;

                            Calendar calendar = Calendar.getInstance();
                            dtToday = calendar.getTime();
                            dtToday.setHours(0);
                            dtToday.setMinutes(0);
                            dtToday.setSeconds(0);

                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.DAY_OF_MONTH, 1);
                            dtThisMonth = calendar1.getTime();
                            dtThisMonth.setHours(0);
                            dtThisMonth.setMinutes(0);
                            dtThisMonth.setSeconds(0);

                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.add(Calendar.DATE, -90);
                            dtThreeMonth = calendar2.getTime();
                            dtThreeMonth.setHours(0);
                            dtThreeMonth.setMinutes(0);
                            dtThreeMonth.setSeconds(0);

                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.add(Calendar.DATE, -365);
                            dtOneYear = calendar3.getTime();
                            dtOneYear.setHours(0);
                            dtOneYear.setMinutes(0);
                            dtOneYear.setSeconds(0);

                            Calendar calendar4 = Calendar.getInstance();
                            calendar4.add(Calendar.DATE, -1);
                            dtYesterDay = calendar4.getTime();
                            dtYesterDay.setHours(0);
                            dtYesterDay.setMinutes(0);
                            dtYesterDay.setSeconds(0);


                            for (int i = 0; i < lstShowAll.size(); i++) {
                                if (lstShowAll.get(i).getCreatedAt() != null && !lstShowAll.get(i).getCreatedAt().equals("")) {

                                    Date dtCreatedAt = null;
                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";
                                        String strYesterDayDate = "";

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);
                                        strYesterDayDate = strDtaeFormat.format(dtYesterDay);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstShowAll.get(i).setDateValue("Today");
                                        } else if (strCreatedAt.equals(strYesterDayDate)) {
                                            lstShowAll.get(i).setDateValue("Yesterday");
                                        } else {
                                            lstShowAll.get(i).setDateValue(simpleDateFormat.format(format.parse(lstShowAll.get(i).getCreatedAt())));
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstToday.add(lstShowAll.get(i));
                                        }


                                        if ((dtCreatedAt.after(dtThisMonth) || dtCreatedAt.equals(dtThisMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThisMonth.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtThreeMonth) || dtCreatedAt.equals(dtThreeMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThreeMonthsAgo.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtOneYear) || dtCreatedAt.equals(dtOneYear)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstOneYearAgo.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getAchievement().contains("I am proud of")) {
                                            lstProudOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've accomplished")) {
                                            lstAccomplish.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've observed")) {
                                            lstObserved.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've learned")) {
                                            lstLearned.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've praised")) {
                                            lstPraised.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I've let go of")) {
                                            lstTodayletGoOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I dreamt of")) {
                                            lstDreamtOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Journal Entry")) {
                                            lstJournalEntry.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("The story I've told myself is")) {
                                            lstTheStory.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've been challenged by")) {
                                            lstChallengedBy.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("A small win I'm celebrating is")) {
                                            lstCelebrating.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I laughed")) {
                                            lstLaughed.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I'm Feeling")) {
                                            lstImFeeling.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getPicture() != null && !lstShowAll.get(i).getPicture().isEmpty()) {
                                            lstPicture.add(lstShowAll.get(i));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }



                            try {
                                GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);
                                //boolPicture = growthSaveFilterModel.getBool_showimage();
                                boolProudOf = growthSaveFilterModel.getBool_proud_of();
                                boolAccomplished = growthSaveFilterModel.getBool_accomplished();
                                boolObserved = growthSaveFilterModel.getBool_observed();
                                boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
                                boolLearned = growthSaveFilterModel.getBool_learned();
                                boolPraised = growthSaveFilterModel.getBool_praised();
                                boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
                                boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
                                boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

                                boolGratefulFor = growthSaveFilterModel.getBool_grateful_for(); //added by jyotirmoy->j14
                                boolWorkingTowards = growthSaveFilterModel.getBool_working_towards(); //added by jyotirmoy->j14
                                boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth(); //added by jyotirmoy->j14
                                boolCommittingTo = growthSaveFilterModel.getBool_committing_to(); //added by jyotirmoy->j14
                                boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day(); //added by jyotirmoy->j14
                                boolFoundGift = growthSaveFilterModel.getBool_found_gift(); //added by jyotirmoy->j15
                                boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today(); //added by jyotirmoy->j15
                                boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge(); //added by jyotirmoy->j15
                                boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();
                                boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
                                boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
                                boolShowImg = growthSaveFilterModel.getBool_show_img();






                                boolTheStory = growthSaveFilterModel.getBool_the_story();
                                boolChallenged = growthSaveFilterModel.getBool_challenged();
                                boolCelebrating = growthSaveFilterModel.getBool_celebrating();
                                boolLaughed = growthSaveFilterModel.getBool_laughed();
                                boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
                                globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
                                filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            funRefresh();

                        } else {

                            haveGrowthMinimumOne = false;

                            txtLoading.setVisibility(View.GONE);
                            imgBigPlus.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<MyAchievementsListModel> call, Throwable t) {
                    progressDialog.dismiss();

                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-1");
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void getAchievementsList(int page) {
        Log.i("check_eq","20");
        Log.e("call api", "yes");
        txtLoading.setVisibility(View.GONE);
        txtLoading.setText("loading...");
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("PageNo", page);
            hashReq.put("Count", 20);

            Call<MyAchievementsListModel> getGratitudeListModelCall = finisherService.getMyAchievevmentsList(hashReq);
            getGratitudeListModelCall.enqueue(new Callback<MyAchievementsListModel>() {
                @Override
                public void onResponse(Call<MyAchievementsListModel> call, Response<MyAchievementsListModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        // Util.showToast(getActivity(), "Util.networkMsg11111");
                        if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {

                            Log.i("check_eq","21");
                            total_result = response.body().getTotalCount();
                            Log.i("total_result", String.valueOf(total_result));
                            total_page = response.body().getTotalCount() / 20;
                            Log.i("total_page", String.valueOf(total_page));
                            remaining = response.body().getTotalCount() % 20;
                            Log.i("remaining", String.valueOf(remaining));
                            if (remaining > 0) {
                                total_page = total_page + 1;
                            } else {

                            }
                            CurrentPage = page;
                            haveGrowthMinimumOne = true;

                            lstShowAll = response.body().getDetails();
                            Log.e(TAG, "onResponse: " + lstShowAll.toString());
                            // loadData(lstShowAll);
                            Date dtToday = null, dtThisMonth = null, dtThreeMonth = null, dtOneYear = null, dtYesterDay = null;

                            Calendar calendar = Calendar.getInstance();
                            dtToday = calendar.getTime();
                            dtToday.setHours(0);
                            dtToday.setMinutes(0);
                            dtToday.setSeconds(0);

                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.DAY_OF_MONTH, 1);
                            dtThisMonth = calendar1.getTime();
                            dtThisMonth.setHours(0);
                            dtThisMonth.setMinutes(0);
                            dtThisMonth.setSeconds(0);

                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.add(Calendar.DATE, -90);
                            dtThreeMonth = calendar2.getTime();
                            dtThreeMonth.setHours(0);
                            dtThreeMonth.setMinutes(0);
                            dtThreeMonth.setSeconds(0);

                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.add(Calendar.DATE, -365);
                            dtOneYear = calendar3.getTime();
                            dtOneYear.setHours(0);
                            dtOneYear.setMinutes(0);
                            dtOneYear.setSeconds(0);

                            Calendar calendar4 = Calendar.getInstance();
                            calendar4.add(Calendar.DATE, -1);
                            dtYesterDay = calendar4.getTime();
                            dtYesterDay.setHours(0);
                            dtYesterDay.setMinutes(0);
                            dtYesterDay.setSeconds(0);


                            for (int i = 0; i < lstShowAll.size(); i++) {
                                if (lstShowAll.get(i).getCreatedAt() != null && !lstShowAll.get(i).getCreatedAt().equals("")) {

                                    Date dtCreatedAt = null;
                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";
                                        String strYesterDayDate = "";

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);
                                        strYesterDayDate = strDtaeFormat.format(dtYesterDay);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstShowAll.get(i).setDateValue("Today");
                                        } else if (strCreatedAt.equals(strYesterDayDate)) {
                                            lstShowAll.get(i).setDateValue("Yesterday");
                                        } else {
                                            lstShowAll.get(i).setDateValue(simpleDateFormat.format(format.parse(lstShowAll.get(i).getCreatedAt())));
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstToday.add(lstShowAll.get(i));
                                        }


                                        if ((dtCreatedAt.after(dtThisMonth) || dtCreatedAt.equals(dtThisMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThisMonth.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtThreeMonth) || dtCreatedAt.equals(dtThreeMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThreeMonthsAgo.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtOneYear) || dtCreatedAt.equals(dtOneYear)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstOneYearAgo.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getAchievement().contains("I am proud of")) {
                                            lstProudOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've accomplished")) {
                                            lstAccomplish.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've observed")) {
                                            lstObserved.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've learned")) {
                                            lstLearned.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've praised")) {
                                            lstPraised.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I've let go of")) {
                                            lstTodayletGoOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I dreamt of")) {
                                            lstDreamtOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Journal Entry")) {
                                            lstJournalEntry.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("The story I've told myself is")) {
                                            lstTheStory.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've been challenged by")) {
                                            lstChallengedBy.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("A small win I'm celebrating is")) {
                                            lstCelebrating.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I laughed")) {
                                            lstLaughed.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I'm Feeling")) {
                                            lstImFeeling.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getPicture() != null && !lstShowAll.get(i).getPicture().isEmpty()) {
                                            lstPicture.add(lstShowAll.get(i));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            String json= sharedPreference.read("my_list_eq_filter", "");
                            ArrayList<GrowthSaveFilterModel> growthSaveFilterModellist = new Gson().fromJson(json, new TypeToken<ArrayList<GrowthSaveFilterModel>>(){}.getType());

                            try {
                                GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);

                                boolProudOf = growthSaveFilterModel.getBool_proud_of();
                                boolAccomplished = growthSaveFilterModel.getBool_accomplished();
                                boolObserved = growthSaveFilterModel.getBool_observed();
                                boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
                                boolLearned = growthSaveFilterModel.getBool_learned();
                                boolPraised = growthSaveFilterModel.getBool_praised();
                                boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
                                boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
                                boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

                                boolGratefulFor = growthSaveFilterModel.getBool_grateful_for(); //added by jyotirmoy->j14
                                boolWorkingTowards = growthSaveFilterModel.getBool_working_towards(); //added by jyotirmoy->j14
                                boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth(); //added by jyotirmoy->j14
                                boolCommittingTo = growthSaveFilterModel.getBool_committing_to(); //added by jyotirmoy->j14
                                boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day(); //added by jyotirmoy->j14
                                boolFoundGift = growthSaveFilterModel.getBool_found_gift(); //added by jyotirmoy->j15
                                boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today(); //added by jyotirmoy->j15
                                boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge(); //added by jyotirmoy->j15
                                boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();
                                boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
                                boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
                                boolShowImg = growthSaveFilterModel.getBool_show_img();






                                boolTheStory = growthSaveFilterModel.getBool_the_story();
                                boolChallenged = growthSaveFilterModel.getBool_challenged();
                                boolCelebrating = growthSaveFilterModel.getBool_celebrating();
                                boolLaughed = growthSaveFilterModel.getBool_laughed();
                                boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
                                globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
                                filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();


                            } catch (Exception e) {
                                Log.i("check_eq","29");
                                e.printStackTrace();
                                // Util.showToast(getActivity(), Util.networkMsg);
                            }
                            Log.i("check_eq","23");
                            funRefresh();

                        } else {

                            haveGrowthMinimumOne = false;

                            txtLoading.setVisibility(View.GONE);
                            imgBigPlus.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<MyAchievementsListModel> call, Throwable t) {
                    progressDialog.dismiss();
                    Util.showToast(getActivity(), Util.networkMsg);
                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-1");
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void getAchievementsList_loadmore(int page) {
        Log.i("size_response1", String.valueOf(lstShowAll.size()));


        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();

        loadImageJournal.setVisibility(View.GONE);
        if (Page > total_page) {

        } else {
            if (Connection.checkConnection(getActivity())) {

                //  progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

                HashMap<String, Object> hashReq = new HashMap<>();
                hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
                hashReq.put("Key", Util.KEY);
                hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                hashReq.put("PageNo", page);
                hashReq.put("Count", 20);

                Call<MyAchievementsListModel> getGratitudeListModelCall = finisherService.getMyAchievevmentsList(hashReq);
                getGratitudeListModelCall.enqueue(new Callback<MyAchievementsListModel>() {
                    @Override
                    public void onResponse(Call<MyAchievementsListModel> call, Response<MyAchievementsListModel> response) {
                        //   progressDialog.dismiss();

                        if (response.body() != null) {
                            if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {
                                total_result = response.body().getTotalCount();
                                Log.i("total_result", String.valueOf(total_result));
                                total_page = response.body().getTotalCount() / 20;
                                Log.i("total_page", String.valueOf(total_page));
                                remaining = response.body().getTotalCount() % 20;
                                Log.i("remaining", String.valueOf(remaining));
                                if (remaining > 0) {
                                    total_page = total_page + 1;
                                } else {
                                    // total_page=total_page;
                                }
                                CurrentPage = page;
                                haveGrowthMinimumOne = true;


                                // lstShowAll = response.body().getDetails();
                                lstShowAll.addAll(response.body().getDetails());
                                Log.i("size_response2", String.valueOf(lstShowAll.size()));

                                // loadData(lstShowAll);

                                Date dtToday = null, dtThisMonth = null, dtThreeMonth = null, dtOneYear = null, dtYesterDay = null;

                                Calendar calendar = Calendar.getInstance();
                                dtToday = calendar.getTime();
                                dtToday.setHours(0);
                                dtToday.setMinutes(0);
                                dtToday.setSeconds(0);

                                Calendar calendar1 = Calendar.getInstance();
                                calendar1.set(Calendar.DAY_OF_MONTH, 1);
                                dtThisMonth = calendar1.getTime();
                                dtThisMonth.setHours(0);
                                dtThisMonth.setMinutes(0);
                                dtThisMonth.setSeconds(0);

                                Calendar calendar2 = Calendar.getInstance();
                                calendar2.add(Calendar.DATE, -90);
                                dtThreeMonth = calendar2.getTime();
                                dtThreeMonth.setHours(0);
                                dtThreeMonth.setMinutes(0);
                                dtThreeMonth.setSeconds(0);

                                Calendar calendar3 = Calendar.getInstance();
                                calendar3.add(Calendar.DATE, -365);
                                dtOneYear = calendar3.getTime();
                                dtOneYear.setHours(0);
                                dtOneYear.setMinutes(0);
                                dtOneYear.setSeconds(0);

                                Calendar calendar4 = Calendar.getInstance();
                                calendar4.add(Calendar.DATE, -1);
                                dtYesterDay = calendar4.getTime();
                                dtYesterDay.setHours(0);
                                dtYesterDay.setMinutes(0);
                                dtYesterDay.setSeconds(0);

                                for (int i = 0; i < lstShowAll.size(); i++) {
                                    if (lstShowAll.get(i).getCreatedAt() != null && !lstShowAll.get(i).getCreatedAt().equals("")) {

                                        Date dtCreatedAt = null;
                                        try {
                                            dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                            String strCreatedAt = "";
                                            String strTodayDate = "";
                                            String strYesterDayDate = "";

                                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                                            strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                            strTodayDate = strDtaeFormat.format(dtToday);
                                            strYesterDayDate = strDtaeFormat.format(dtYesterDay);

                                            if (strCreatedAt.equals(strTodayDate)) {
                                                lstShowAll.get(i).setDateValue("Today");
                                            } else if (strCreatedAt.equals(strYesterDayDate)) {
                                                lstShowAll.get(i).setDateValue("Yesterday");
                                            } else {
                                                lstShowAll.get(i).setDateValue(simpleDateFormat.format(format.parse(lstShowAll.get(i).getCreatedAt())));
                                            }

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        try {
                                            dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                            String strCreatedAt = "";
                                            String strTodayDate = "";

                                            strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                            strTodayDate = strDtaeFormat.format(dtToday);

                                            if (strCreatedAt.equals(strTodayDate)) {
                                                lstToday.add(lstShowAll.get(i));
                                            }


                                            if ((dtCreatedAt.after(dtThisMonth) || dtCreatedAt.equals(dtThisMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                                lstThisMonth.add(lstShowAll.get(i));
                                            }

                                            if ((dtCreatedAt.after(dtThreeMonth) || dtCreatedAt.equals(dtThreeMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                                lstThreeMonthsAgo.add(lstShowAll.get(i));
                                            }

                                            if ((dtCreatedAt.after(dtOneYear) || dtCreatedAt.equals(dtOneYear)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                                lstOneYearAgo.add(lstShowAll.get(i));
                                            }

                                            if (lstShowAll.get(i).getAchievement().contains("I am proud of")) {
                                                lstProudOf.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I've accomplished")) {
                                                lstAccomplish.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I've observed")) {
                                                lstObserved.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I've learned")) {
                                                lstLearned.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I've praised")) {
                                                lstPraised.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("Today I've let go of")) {
                                                lstTodayletGoOf.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I dreamt of")) {
                                                lstDreamtOf.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("Journal Entry")) {
                                                lstJournalEntry.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("The story I've told myself is")) {
                                                lstTheStory.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I've been challenged by")) {
                                                lstChallengedBy.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("A small win I'm celebrating is")) {
                                                lstCelebrating.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("I laughed")) {
                                                lstLaughed.add(lstShowAll.get(i));
                                            }
                                            if (lstShowAll.get(i).getAchievement().contains("Today I'm Feeling")) {
                                                lstImFeeling.add(lstShowAll.get(i));
                                            }

                                            if (lstShowAll.get(i).getPicture() != null && !lstShowAll.get(i).getPicture().isEmpty()) {
                                                lstPicture.add(lstShowAll.get(i));
                                            }

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }


                                try {
                                    GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);

                                    boolProudOf = growthSaveFilterModel.getBool_proud_of();
                                    boolAccomplished = growthSaveFilterModel.getBool_accomplished();
                                    boolObserved = growthSaveFilterModel.getBool_observed();
                                    boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
                                    boolLearned = growthSaveFilterModel.getBool_learned();
                                    boolPraised = growthSaveFilterModel.getBool_praised();
                                    boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
                                    boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
                                    boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

                                    boolGratefulFor = growthSaveFilterModel.getBool_grateful_for(); //added by jyotirmoy->j14
                                    boolWorkingTowards = growthSaveFilterModel.getBool_working_towards(); //added by jyotirmoy->j14
                                    boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth(); //added by jyotirmoy->j14
                                    boolCommittingTo = growthSaveFilterModel.getBool_committing_to(); //added by jyotirmoy->j14
                                    boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day(); //added by jyotirmoy->j14
                                    boolFoundGift = growthSaveFilterModel.getBool_found_gift(); //added by jyotirmoy->j15
                                    boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today(); //added by jyotirmoy->j15
                                    boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge(); //added by jyotirmoy->j15
                                    boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();
                                    boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
                                    boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
                                    boolShowImg = growthSaveFilterModel.getBool_show_img();






                                    boolTheStory = growthSaveFilterModel.getBool_the_story();
                                    boolChallenged = growthSaveFilterModel.getBool_challenged();
                                    boolCelebrating = growthSaveFilterModel.getBool_celebrating();
                                    boolLaughed = growthSaveFilterModel.getBool_laughed();
                                    boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
                                    globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
                                    filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                funRefresh();


                            } else {

                                haveGrowthMinimumOne = false;

                                txtLoading.setVisibility(View.GONE);
                                imgBigPlus.setVisibility(View.VISIBLE);
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<MyAchievementsListModel> call, Throwable t) {
                        //  progressDialog.dismiss();

                    }
                });

            } else {
                Log.e("callnonetwork", "callnonetwork-14");
                Util.showToast(getActivity(), Util.networkMsg);
            }
        }


    }

    //added by Jyotirmoy Patra 16.09.22
    private void showDailyJournalPrompt(String name) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.daily_prompt_journal);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        ImageView closePrompt = (ImageView) dialog.findViewById(R.id.promptClose);
        closePrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        TextView selectPrompt = (TextView) dialog.findViewById(R.id.choosePrompt);
        selectPrompt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogForAchieveAdd();
                dialog.dismiss();
            }
        });

        TextView promptOfTheDay = (TextView) dialog.findViewById(R.id.promptOfTheDay);
        promptOfTheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //j11
                promptOfTheDayClick = true;
                openDialogForAchieveAdd();
                dialog.dismiss();


            }
        });

        TextView txtUser = (TextView) dialog.findViewById(R.id.txtUser);
        String user = "Hi " + name;

        //for bold user name
        SpannableString ss = new SpannableString(user);
        StyleSpan boldSpan = new StyleSpan(Typeface.BOLD);
        ss.setSpan(boldSpan, 3, user.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtUser.setText(ss);


        dialog.show();


    }
    //ended by Jyotirmoy Patra 16.09.22


    private void openGrowthAndGratitudeDialog() {
        try {
            Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.dialog_info_growth);
            ImageView imgCross = dialog.findViewById(R.id.imgCross);
            Button btnGotIt = dialog.findViewById(R.id.btnGotIt);

            imgCross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnGotIt.setOnClickListener(view -> {
                dialog.dismiss();
            });

            sharedPreference.write("GROWTH_INFO_ONCE", "", "TRUE");

            dialog.show();

            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    JournalDailyPopupCall();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //added by Jyotirmoy Patra 18.09.22
    private void JournalDailyPopupCall() {


//                Calendar calendar = Calendar.getInstance();
//                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
//                SharedPreferences preferences = getContext().getSharedPreferences("pref", 0);
//                int lastDay = preferences.getInt("day", 0);

        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        SharedPreferences mPreJournal = PreferenceManager.getDefaultSharedPreferences(getContext());
        int lastDay = mPreJournal.getInt("dayJournal", 0);


        //checking daily popup setting
        SharedPreferences mCheckPref = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean checkPop = mCheckPref.getBoolean(KEY_DAILY_POPUP_JOURNAL, false);

        //get current user name
        SharedPreferences sp = getContext().getSharedPreferences("firstName", 0);
        String userName = sp.getString("firstName", "");
        userName = userName.toUpperCase();

        if (checkPop || !mCheckPref.contains(KEY_DAILY_POPUP_JOURNAL)) {


            if (lastDay != currentDay) {
                SharedPreferences.Editor editor = mPreJournal.edit();
                editor.putInt("dayJournal", currentDay);
                editor.commit();

                //  showDailyJournalPrompt(userName);

            }
        }

    }

    private void funRefresh1(String s) {

        Log.i("textchange","2");
        Log.i("textchange",s);
        Log.e(TAG, "funRefresh1: " + lstShowAll.size());

        List<MyAchievementsListInnerModel> lstShowResults = new ArrayList<>();

        if (filterSelectedvalue == -1) {
            List<MyAchievementsListInnerModel> lstSearch = null;
            if (!txtFromDate.getText().toString().equalsIgnoreCase("from date") && !txtToDate.getText().toString().equalsIgnoreCase("to date")) {
                lstSearch = performDateRangeSearch(txtFromDate.getText().toString(), txtToDate.getText().toString());
            } else {
                Util.showToast(getActivity(), "Please select date");
            }
            if (lstSearch != null) {
                for (int i = 0; i < lstSearch.size(); i++) {
                    lstShowResults.add(lstSearch.get(i));
                }
            }
        } else if (filterSelectedvalue == 0) {
            for (int i = 0; i < lstShowAll.size(); i++) {
                lstShowResults.add(lstShowAll.get(i));
            }
        } else if (filterSelectedvalue == 1) {
            for (int i = 0; i < lstToday.size(); i++) {
                lstShowResults.add(lstToday.get(i));
            }
        } else if (filterSelectedvalue == 2) {
            for (int i = 0; i < lstThisMonth.size(); i++) {
                lstShowResults.add(lstThisMonth.get(i));
            }
        } else if (filterSelectedvalue == 3) {
            for (int i = 0; i < lstThreeMonthsAgo.size(); i++) {
                lstShowResults.add(lstThreeMonthsAgo.get(i));
            }
        } else if (filterSelectedvalue == 4) {

            for (int i = 0; i < lstOneYearAgo.size(); i++) {
                lstShowResults.add(lstOneYearAgo.get(i));
            }
        } else if (filterSelectedvalue == 5) {

            for (int i = 0; i < lstPicture.size(); i++) {
                lstShowResults.add(lstPicture.get(i));
            }
        }


        Log.e(TAG, "funRefresh: " + lstShowResults.size());
        //////////////////////////////////j1
        List<MyAchievementsListInnerModel> lstShowResultsFinal = new ArrayList<>();
        if (boolProudOf) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I am proud of")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolAccomplished) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've accomplished")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolObserved) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've observed")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolNeedToWork) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I need to work on/clear")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if(boolIChooseToKnow){
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I Choose to Know")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }


        }

        if(boolOnlyPic){
            for(int i =0; i< lstShowResults.size(); i++){
                if(lstShowResults.get(i).getHasImage()){
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }

            }

        }

        if(boolShowImg){
            for(int i =0; i< lstShowResults.size(); i++){
                if(!lstShowResults.get(i).getHasImage()){
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolLearned) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've learned")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolPraised) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've praised")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolLetGoOf) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Today I've let go of")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolDreamtOf) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I dreamt of")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        //added by jyotirmoy->j14
        if (boolGratefulFor) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Grateful For")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolWorkingTowards) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("working towards")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolFeelingGrowth) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("feeling growth")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolCommittingTo) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("committing to")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolPromptOfTheDay) {
            for (int i = 0; i < lstShowResults.size(); i++) {

                if (lstShowResults.get(i).getPromptOfDay().equals(true)) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }


            }
        }

        if (boolFoundGift) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("found the gift in")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolFeltHappyToday) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("felt happy today because")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolIAcknowledge) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I acknowledge")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if(boolIAppreciate){
            for(int i = 0;i < lstShowResults.size(); i++){
                if(lstShowResults.get(i).getPrompt().contains("One thing I appreciate about you")){
                    lstShowResultsFinal.add(lstShowResults.get(i));

                }

            }

        }
        //end by jyotirmoy->j14

        if (boolJournalEntry) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Journal Entry")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolTheStory) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("The story I've told myself is")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolChallenged) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've been challenged by")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolCelebrating) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("A small win I'm celebrating is")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolLaughed) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I laughed")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolTodayImFeeling) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Today I'm Feeling")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }





        if (boolDateSelected && txtFromDate.getText().toString().equalsIgnoreCase("from date") && txtToDate.getText().toString().equalsIgnoreCase("to date")) {
            Util.showToast(getActivity(), "Please select date");
        } else {
            if (boolProudOf || boolAccomplished ||boolNeedToWork|| boolObserved || boolLearned || boolPraised ||
                    boolLetGoOf || boolFeelingGrowth || boolIAcknowledge || boolFeltHappyToday ||
                    boolFoundGift || boolPromptOfTheDay || boolCommittingTo || boolDreamtOf ||
                    boolJournalEntry || boolGratefulFor || boolWorkingTowards || boolTheStory ||
                    boolChallenged || boolCelebrating || boolLaughed || boolTodayImFeeling||boolIAppreciate||boolIChooseToKnow||boolOnlyPic||boolShowImg) {

                ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResultsFinal);
                loadAdapter1(lstUnique,s); /////////////////////////////////////////
            } else {

                ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResults);
                loadAdapter1(lstUnique,s);//////////////////////////////////////////////////////
            }
        }

    }

    private void funRefresh() {

        Log.e(TAG, "funRefresh1: " + lstShowAll.size());

        List<MyAchievementsListInnerModel> lstShowResults = new ArrayList<>();

        if (filterSelectedvalue == -1) {
            List<MyAchievementsListInnerModel> lstSearch = null;
            if (!txtFromDate.getText().toString().equalsIgnoreCase("from date") && !txtToDate.getText().toString().equalsIgnoreCase("to date")) {
                lstSearch = performDateRangeSearch(txtFromDate.getText().toString(), txtToDate.getText().toString());
            } else {
                Util.showToast(getActivity(), "Please select date");
            }
            if (lstSearch != null) {
                for (int i = 0; i < lstSearch.size(); i++) {
                    lstShowResults.add(lstSearch.get(i));
                }
            }
        } else if (filterSelectedvalue == 0) {
            for (int i = 0; i < lstShowAll.size(); i++) {
                lstShowResults.add(lstShowAll.get(i));
            }
        } else if (filterSelectedvalue == 1) {
            for (int i = 0; i < lstToday.size(); i++) {
                lstShowResults.add(lstToday.get(i));
            }
        } else if (filterSelectedvalue == 2) {
            for (int i = 0; i < lstThisMonth.size(); i++) {
                lstShowResults.add(lstThisMonth.get(i));
            }
        } else if (filterSelectedvalue == 3) {
            for (int i = 0; i < lstThreeMonthsAgo.size(); i++) {
                lstShowResults.add(lstThreeMonthsAgo.get(i));
            }
        } else if (filterSelectedvalue == 4) {

            for (int i = 0; i < lstOneYearAgo.size(); i++) {
                lstShowResults.add(lstOneYearAgo.get(i));
            }
        } else if (filterSelectedvalue == 5) {

            for (int i = 0; i < lstPicture.size(); i++) {
                lstShowResults.add(lstPicture.get(i));
            }
        }


        Log.e(TAG, "funRefresh: " + lstShowResults.size());
        //////////////////////////////////j1
        List<MyAchievementsListInnerModel> lstShowResultsFinal = new ArrayList<>();
        List<MyAchievementsListInnerModel> lstShowResultsFinal_ = new ArrayList<>();

        if (boolProudOf) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I am proud of")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolAccomplished) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've accomplished")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolObserved) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've observed")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolNeedToWork) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I need to work on/clear")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if(boolIChooseToKnow){
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I Choose to Know")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }


        }

        if(boolOnlyPic){
            for(int i =0; i< lstShowResults.size(); i++){
                if(lstShowResults.get(i).getHasImage()){
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }

            }

        }
        if(boolShowImg){
            for(int i =0; i< lstShowResults.size(); i++){
                if(!lstShowResults.get(i).getHasImage()){
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolLearned) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've learned")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolPraised) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've praised")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolLetGoOf) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Today I've let go of")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolDreamtOf) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I dreamt of")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        //added by jyotirmoy->j14
        if (boolGratefulFor) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Grateful For")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolWorkingTowards) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("working towards")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolFeelingGrowth) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("feeling growth")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolCommittingTo) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("committing to")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolPromptOfTheDay) {
            for (int i = 0; i < lstShowResults.size(); i++) {

                if (lstShowResults.get(i).getPromptOfDay().equals(true)) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }


            }
        }

        if (boolFoundGift) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("found the gift in")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolFeltHappyToday) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("felt happy today because")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }

        if (boolIAcknowledge) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I acknowledge")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if(boolIAppreciate){
            for(int i = 0;i < lstShowResults.size(); i++){
                if(lstShowResults.get(i).getPrompt().contains("One thing I appreciate about you")){
                    lstShowResultsFinal.add(lstShowResults.get(i));

                }

            }

        }
        //end by jyotirmoy->j14

        if (boolJournalEntry) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Journal Entry")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolTheStory) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("The story I've told myself is")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolChallenged) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I've been challenged by")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolCelebrating) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("A small win I'm celebrating is")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolLaughed) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("I laughed")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }
        if (boolTodayImFeeling) {
            for (int i = 0; i < lstShowResults.size(); i++) {
                if (lstShowResults.get(i).getPrompt().contains("Today I'm Feeling")) {
                    lstShowResultsFinal.add(lstShowResults.get(i));
                }
            }
        }


        if (boolDateSelected && txtFromDate.getText().toString().equalsIgnoreCase("from date") && txtToDate.getText().toString().equalsIgnoreCase("to date")) {
            Util.showToast(getActivity(), "Please select date");
        } else {
            if (boolProudOf || boolAccomplished ||boolNeedToWork|| boolObserved || boolLearned || boolPraised ||
                    boolLetGoOf || boolFeelingGrowth || boolIAcknowledge || boolFeltHappyToday ||
                    boolFoundGift || boolPromptOfTheDay || boolCommittingTo || boolDreamtOf ||
                    boolJournalEntry || boolGratefulFor || boolWorkingTowards || boolTheStory ||
                    boolChallenged || boolCelebrating || boolLaughed || boolTodayImFeeling||boolIAppreciate||boolIChooseToKnow||boolOnlyPic||boolShowImg) {

                lstShowResultsFinal_.clear();
                Log.e("Folder_block","11");
                Log.e("Folder_block",String.valueOf(Util.checkfolder));
                Log.e("Folder_block",String.valueOf(lstShowResultsFinal.size()));

                if (Util.checkfolder.size() > 0) {
                    for (int i = 0; i < Util.checkfolder.size(); i++) {
                        Log.e("checkfolder__",String.valueOf(Util.checkfolder.size()));

                        for (int j = 0; j < lstShowResultsFinal.size(); j++) {
                            if(null!=lstShowResultsFinal.get(j).getUserFolderId() ){
                                Log.e("lstShowResults__item",String.valueOf(lstShowResultsFinal.get(j).getUserFolderId()));
                                Log.e("checkfolder_item",String.valueOf(Util.checkfolder.get(i)));
                                Log.e("match_or_not",String.valueOf(Util.checkfolder.get(i)== lstShowResultsFinal.get(j).getUserFolderId()));
                                int b=Util.checkfolder.get(i);
                                if ( b == lstShowResultsFinal.get(j).getUserFolderId()) {
                                    lstShowResultsFinal_.add(lstShowResultsFinal.get(j));
                                    Log.i("Match","yes");
                                }else{
                                    Log.i("Match","no");
                                }
                            }

                        }

                    }
                    Log.e("lstShowResultsFinal_",String.valueOf(lstShowResultsFinal_.size()));
                    ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResultsFinal_);
                    loadAdapter(lstUnique); /////////////////////////////////////////


                }else{
                    Log.e("lstShowResultsFinal_",String.valueOf(lstShowResultsFinal.size()));
                    ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResultsFinal);
                    loadAdapter(lstUnique); /////////////////////////////////////////
                }

                //  ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResultsFinal)
                // loadAdapter(lstShowResultsFinal_folder);
            } else {
                lstShowResultsFinal_.clear();
                Log.e("Folder_block","12");
                Log.e("Folder_block",String.valueOf(Util.checkfolder));
                Log.e("Folder_block",String.valueOf(lstShowResults.size()));
                if (Util.checkfolder.size() > 0) {
                    for (int i = 0; i < Util.checkfolder.size(); i++) {
                        Log.e("checkfolder__",String.valueOf(Util.checkfolder.size()));
//                        Log.e("lstShowResults__",String.valueOf(lstShowResults.size()));

                        for (int j = 0; j < lstShowResults.size(); j++) {
                            if(null!=lstShowResults.get(j).getUserFolderId() ){
                                Log.e("lstShowResults__item",String.valueOf(lstShowResults.get(j).getUserFolderId()));
                                Log.e("checkfolder_item",String.valueOf(Util.checkfolder.get(i)));
                                Log.e("match_or_not",String.valueOf(Util.checkfolder.get(i)== lstShowResults.get(j).getUserFolderId()));
                                int a=Util.checkfolder.get(i);
                                if ( a == lstShowResults.get(j).getUserFolderId()) {
                                    lstShowResultsFinal_.add(lstShowResults.get(j));

                                    Log.i("Match","yes");

                                }else{
                                    Log.i("Match","no");

                                }
                            }

                        }

                    }
                    Log.e("lstShowResultsFinal_",String.valueOf(lstShowResultsFinal_.size()));
                    ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResultsFinal_);
                    loadAdapter(lstUnique);//////////////////////////////////////////////////////
                }else{
                    Log.e("lstShowResultsFinal_",String.valueOf(lstShowResults.size()));
                    ArrayList<MyAchievementsListInnerModel> lstUnique = new ArrayList<>(lstShowResults);
                    loadAdapter(lstUnique);//////////////////////////////////////////////////////
                }



            }
        }

    }

    public void openDialogForAchieveAdd() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_add_options);

        dialog.setCancelable(false);

        TextView txtProudOf = dialog.findViewById(R.id.txtProudOf);
        TextView txtTodayImFelling = dialog.findViewById(R.id.txtTodayImFelling);
        TextView txtAccomplish = dialog.findViewById(R.id.txtAccomplish);
        TextView txtObserved = dialog.findViewById(R.id.txtObserved);
        TextView txtLearned = dialog.findViewById(R.id.txtLearned);
        TextView txtLaughed = dialog.findViewById(R.id.txtLaughed);
        TextView txtPraised = dialog.findViewById(R.id.txtPraised);
        TextView txtChallenged = dialog.findViewById(R.id.txtChallenged);
        TextView txtCelebrate = dialog.findViewById(R.id.txtCelebrate);
        TextView txtTodayLetOf = dialog.findViewById(R.id.txtTodayLetOf);
        TextView txtIDreamtOf = dialog.findViewById(R.id.txtIDreamtOf);
        TextView txtJournalEntry = dialog.findViewById(R.id.txtJournalEntry);
        TextView txtTheStoryAdd = dialog.findViewById(R.id.txtTheStoryAdd);
        RelativeLayout rlCancel = dialog.findViewById(R.id.rlCancel);

        TextView txtAcknowledge = dialog.findViewById(R.id.txtAcknowledge);
        TextView txtToward = dialog.findViewById(R.id.txtToward);
        TextView txtFoundGift = dialog.findViewById(R.id.txtFoundGift);
        TextView txtHappy = dialog.findViewById(R.id.txtHappy);
        TextView txtIchoose=dialog.findViewById(R.id.txtIchoose);
        TextView txtOnething = dialog.findViewById(R.id.txtOnething);
        TextView txtFeelGrowth = dialog.findViewById(R.id.txtFeelGrowth);
        TextView txtCommitting = dialog.findViewById(R.id.txtCommitting);

        TextView txtIamGratefulFor = dialog.findViewById(R.id.txtIamGratefulFor);
        TextView txtPromptofTheDay = dialog.findViewById(R.id.txtPromptofTheDay);
        TextView txtneedtowork = dialog.findViewById(R.id.txtneedtowork);
        /*added by sahenita*/
        checkneedtowork = dialog.findViewById(R.id.checkneedtowork);
        checkGrateful_image = dialog.findViewById(R.id.checkGrateful_image);
        checkPromptofTheDay_image = dialog.findViewById(R.id.checkPromptofTheDay_image);
        checkTodayImFelling_image = dialog.findViewById(R.id.checkTodayImFelling_image);
        /*added by sahenita*/
        checkGrateful = dialog.findViewById(R.id.checkGrateful);
        checkOnething=dialog.findViewById(R.id.checkOnething);
        checkJournal = dialog.findViewById(R.id.checkJournal);
        checkPromptofTheDay = dialog.findViewById(R.id.checkPromptofTheDay);
        checkTodayImFelling = dialog.findViewById(R.id.checkTodayImFelling);
        checkProudOf = dialog.findViewById(R.id.checkProudOf);
        checkAccomplish = dialog.findViewById(R.id.checkAccomplish);

        checkToward = dialog.findViewById(R.id.checkToward);
        checkFeelGrowth = dialog.findViewById(R.id.checkFeelGrowth);
        checkCommitting = dialog.findViewById(R.id.checkCommitting);
        checkCelebrate = dialog.findViewById(R.id.checkCelebrate);
        checkFoundGift = dialog.findViewById(R.id.checkFoundGift);
        checkHappy = dialog.findViewById(R.id.checkHappy);
        checkTheStoryAdd = dialog.findViewById(R.id.checkTheStoryAdd);
        checkChallenged = dialog.findViewById(R.id.checkChallenged);

        checkObserved = dialog.findViewById(R.id.checkObserved);
        checkLearned = dialog.findViewById(R.id.checkLearned);
        checkAcknowledge = dialog.findViewById(R.id.checkAcknowledge);
        checkLaughed = dialog.findViewById(R.id.checkLaughed);
        checkPraised = dialog.findViewById(R.id.checkPraised);
        checkTodayLetOf = dialog.findViewById(R.id.checkTodayLetOf);
        checkIDreamtOf = dialog.findViewById(R.id.checkIDreamtOf);
        checkIchoose= dialog.findViewById(R.id.checkIchoose);
        if (promptOfTheDayClick) {
            checkPromptofTheDay.setImageResource(R.drawable.mbhq_checked_active);
            checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
        } else {
            checkGrateful.setImageResource(R.drawable.mbhq_checked_active);
        }

        if(null==sharedPreference.read("click_option","") || "".equalsIgnoreCase(sharedPreference.read("click_option",""))){
            checkGrateful.setImageResource(R.drawable.mbhq_checked_active);
            checkJournal.setImageResource(R.drawable.mbhq_circle_green);
            checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
            checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
            checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
            checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
            checkToward.setImageResource(R.drawable.mbhq_circle_green);
            checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
            checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
            checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
            checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
            checkHappy.setImageResource(R.drawable.mbhq_circle_green);
            checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
            checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
            checkObserved.setImageResource(R.drawable.mbhq_circle_green);
            checkLearned.setImageResource(R.drawable.mbhq_circle_green);
            checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
            checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
            checkPraised.setImageResource(R.drawable.mbhq_circle_green);
            checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
            checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
            checkOnething.setImageResource(R.drawable.mbhq_circle_green);
            checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
            checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
        }else{
            if("click_grateful".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_checked_active);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_journalentry".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_checked_active);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_needtowork".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);

                checkneedtowork.setImageResource(R.drawable.mbhq_checked_active);

                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_promptoftheday".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_checked_active);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_todayiamfelling".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_checked_active);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_proudof".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_checked_active);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_accomplise".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_checked_active);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_toward".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_checked_active);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_feelgrowth".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_checked_active);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_committing".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_checked_active);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_celebrate".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_checked_active);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_foundgift".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_checked_active);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_happy".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_checked_active);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_onething".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_checked_active);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_thestoryadd".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_checked_active);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_challenged".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_checked_active);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_observed".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_checked_active);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_learned".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_checked_active);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_acknowledge".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_checked_active);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_laughed".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_checked_active);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_praised".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_checked_active);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_todayletof".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_checked_active);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_dreamtof".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_checked_active);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }else if("click_ichoose".equalsIgnoreCase(sharedPreference.read("click_option",""))){
                checkGrateful.setImageResource(R.drawable.mbhq_circle_green);
                checkJournal.setImageResource(R.drawable.mbhq_circle_green);
                checkPromptofTheDay.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayImFelling.setImageResource(R.drawable.mbhq_circle_green);
                checkProudOf.setImageResource(R.drawable.mbhq_circle_green);
                checkAccomplish.setImageResource(R.drawable.mbhq_circle_green);
                checkToward.setImageResource(R.drawable.mbhq_circle_green);
                checkFeelGrowth.setImageResource(R.drawable.mbhq_circle_green);
                checkCommitting.setImageResource(R.drawable.mbhq_circle_green);
                checkCelebrate.setImageResource(R.drawable.mbhq_circle_green);
                checkFoundGift.setImageResource(R.drawable.mbhq_circle_green);
                checkHappy.setImageResource(R.drawable.mbhq_circle_green);
                checkTheStoryAdd.setImageResource(R.drawable.mbhq_circle_green);
                checkChallenged.setImageResource(R.drawable.mbhq_circle_green);
                checkObserved.setImageResource(R.drawable.mbhq_circle_green);
                checkLearned.setImageResource(R.drawable.mbhq_circle_green);
                checkAcknowledge.setImageResource(R.drawable.mbhq_circle_green);
                checkLaughed.setImageResource(R.drawable.mbhq_circle_green);
                checkPraised.setImageResource(R.drawable.mbhq_circle_green);
                checkTodayLetOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIDreamtOf.setImageResource(R.drawable.mbhq_circle_green);
                checkIchoose.setImageResource(R.drawable.mbhq_checked_active);
                checkOnething.setImageResource(R.drawable.mbhq_circle_green);
                checkneedtowork.setImageResource(R.drawable.mbhq_circle_green);
            }
        }

        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //add by jyotirmoy-j15
                if (Util.grateFul_popup) {
                    Util.grateFul_popup = false;
                    // openGrowthAndGratitudeDialog();
                }
                promptOfTheDayClick = false;
                dialog.dismiss();
                Util.isReloadTodayMainPage = false;
                if (getArguments() != null && getArguments().containsKey("ADD_GROWTH_FROM_TODAY")) {
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                }
            }
        });


        txtIamGratefulFor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //j16
                sharedPreference.write("click_option", "", "click_grateful");

                clickGratitudeEntry = false;

                dialog.dismiss();

                openDialogForAchieveAddWithOption(0);
            }
        });
        txtneedtowork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //j16
                sharedPreference.write("click_option", "", "click_needtowork");

                dialog.dismiss();

                openDialogForAchieveAddWithOption(14);
            }
        });


        txtJournalEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_journalentry");
                openDialogForAchieveAddWithOption(1);

            }
        });

        //added by jyotirmoy
        txtPromptofTheDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

                ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if (!isConnected) {
                    // Internet connection is lost
                    showpopup4();
                }else{
                    sharedPreference.write("click_option", "", "click_promptoftheday");

                    openDialogForAchieveAddWithOption(2);

                }


            }
        });

        txtTodayImFelling.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (!Util.isShowDialogToday(requireContext(), "GROWTH")) {
                    sharedPreference.write("click_option", "", "click_todayiamfelling");

                    openDialogForAchieveAddWithOption(3);
                } else showEQDialog(true);

                //  showEQDialog(true);
                //


            }
        });
        txtProudOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_proudof");

                openDialogForAchieveAddWithOption(4);


            }
        });

        txtAccomplish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_accomplise");

                openDialogForAchieveAddWithOption(5);


            }
        });
        txtToward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_toward");

                openDialogForAchieveAddWithOption(6);


            }
        });

        txtFeelGrowth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_feelgrowth");

                openDialogForAchieveAddWithOption(7);


            }
        });

        txtCommitting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_committing");

                openDialogForAchieveAddWithOption(8);


            }
        });

        txtCelebrate.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_celebrate");

            openDialogForAchieveAddWithOption(9);


        });

        txtFoundGift.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_foundgift");

            openDialogForAchieveAddWithOption(10);


        });

        txtHappy.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_happy");

            openDialogForAchieveAddWithOption(11);


        });
        txtOnething.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_onething");

            openDialogForAchieveAddWithOption(21);


        });
        txtTheStoryAdd.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_thestoryadd");

            openDialogForAchieveAddWithOption(12);


        });

        txtChallenged.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_challenged");


            openDialogForAchieveAddWithOption(13);


        });

        txtObserved.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_observed");

                openDialogForAchieveAddWithOption(15);


            }
        });
        txtLearned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_learned");


                openDialogForAchieveAddWithOption(16);


            }
        });

        txtAcknowledge.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_acknowledge");

            openDialogForAchieveAddWithOption(17);


        });

        txtLaughed.setOnClickListener(view -> {
            dialog.dismiss();
            sharedPreference.write("click_option", "", "click_laughed");

            openDialogForAchieveAddWithOption(18);


        });

        txtPraised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_praised");

                openDialogForAchieveAddWithOption(19);


            }
        });
        txtTodayLetOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_todayletof");

                openDialogForAchieveAddWithOption(20);


            }
        });

        txtIDreamtOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_dreamtof");

                openDialogForAchieveAddWithOption(21);


            }
        });
        txtIchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                sharedPreference.write("click_option", "", "click_ichoose");

                openDialogForAchieveAddWithOption(22);


            }
        });

        dialog.show();
    }

    public void premission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            Log.e("camera","12");

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.CAMERA)) {
                Log.e("camera","13");

            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.CAMERA},
                        10);
                Log.e("camera","14");

            }
        } else {
            Log.e("camera","14");
            // Permission has already been granted
        }
    }

    private void openDialogForAchieveAddWithOption(Integer selectedOption) {
        sharedPreference.clear("GRATITUDEIMAGE");
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_add);

        TextView txtBack = dialog.findViewById(R.id.txtBack);
        TextView addPicButton = dialog.findViewById(R.id.addPicButton);
        TextView tstSelectedOption = dialog.findViewById(R.id.tstSelectedOption);
        edtAchieve = dialog.findViewById(R.id.edtAchieve);
        RelativeLayout rlSave = dialog.findViewById(R.id.rlSave);
        RelativeLayout rlShare = dialog.findViewById(R.id.rlShare);
        cardViewBackgroundPicTOP = dialog.findViewById(R.id.cardViewBackgroundPic);
        imgBackgroundPicTOP = dialog.findViewById(R.id.imgBackgroundPic);

        dialog.setCancelable(false);


       /* ArrayList<UserEqFolder> myData = Util.myModelArrayList;
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String json= sharedPreference.read("my_list_eqfolder", "");
        ArrayList<UserEqFolder> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<UserEqFolder>>(){}.getType());

        RecyclerView rl_folder=dialog.findViewById(R.id.rl_F1);
        if(null!=habitSwapList_ ) {

            rl_folder.setVisibility(View.VISIBLE);

            if ( habitSwapList_.size() > 0 ) {
                rl_folder.setVisibility(View.VISIBLE);
                rl_folder.setLayoutManager(new LinearLayoutManager(getActivity())); // You can use GridLayoutManager or others for different layouts
                adapter1 = new MyAdapter1(getActivity(),new OnItemClickListener(){
                    @Override
                    public void onItemClick_add(int p,int folderid, String filename, Boolean check) {

                         filename1=String.valueOf(folderid);
                      *//*  habitSwapList_.get(p).setIsDefaultView(true);
                        if(!Util.checkfolder.contains(folderid)){
                            // Log.i("clicked_________folderadd",String.valueOf(check) +" ->>  "+ String.valueOf(eqfolderid));

                            Util.checkfolder.add(folderid);
                            setDefault(folderid,true);
                        }*//*

                       // adapter1.notifyDataSetChanged();
                        Toast.makeText(getActivity(),filename,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onItemClick_remove(int p,int folderidrmv, String filename, Boolean check) {
                        //  Log.i("clicked_________folderremove",String.valueOf(check) +" ->>  "+ String.valueOf(folderidrmv));
                      *//*  Integer a=folderidrmv;
                        habitSwapList_.get(p).setIsDefaultView(false);
                        if(Util.checkfolder.contains(a)){


                            Util.checkfolder.remove(a);
                            setDefault(a,false);
                        }
                        Log.i("clicked_________folderadd",String.valueOf(Util.checkfolder.size()));

                        adapter1.notifyDataSetChanged();*//*

                    }

                },habitSwapList_); // Provide your data list
                rl_folder.setAdapter(adapter1);
            }else{
                rl_folder.setVisibility(View.GONE);
            }
        }else{
            rl_folder.setVisibility(View.GONE);
        }*/




        //  rlShare.setVisibility(View.GONE);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptOfTheDayClick = false;
                dialog.dismiss();
                Util.isReloadTodayMainPage = false;
                if (getArguments() != null && getArguments().containsKey("ADD_GROWTH_FROM_TODAY")) {
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                }
            }
        });

        edtAchieve.setFocusable(true);
        edtAchieve.setClickable(true);

        tstSelectedOption.setText(arrAchieveOptions.get(selectedOption) + "");


        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtAchieve.getText().toString().trim().equals("")) {

                    AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("Alert", "Please enter some text", false);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {

                        }

                        @Override
                        public void onCancel(String title) {

                        }
                    });

                } else {
                    getGratitudeListModelInner_nointernate=null;
                    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                    if (!isConnected) {
                        // Log.e("state_1","1");


                        // getGratitudeListModelInner_nointernate=new MyAchievementsListInnerModel();
                        String json= sharedPreference.read("my_list_nointernate", "");
                        ArrayList<MyAchievementsListInnerModel> shared_getGratitudeListModelInner_nointernate = new Gson().fromJson(json, new TypeToken<ArrayList<MyAchievementsListInnerModel>>(){}.getType());
                        getGratitudeListModelInner_nointernate = makeAchieveModelForAdd_nonetwork(edtAchieve, selectedOption);

                        //Util.lstShowAll_db_nointernate.clear();

                        if(Util.lstShowAll_db_nointernate.size()>0){
                            Util.lstShowAll_db_nointernate.add(0,getGratitudeListModelInner_nointernate);
                        }else{
                            Util.lstShowAll_db_nointernate.add(getGratitudeListModelInner_nointernate);
                        }

                        if(null!=shared_getGratitudeListModelInner_nointernate ){

                            if(shared_getGratitudeListModelInner_nointernate.size()>0){
                                // shared_getGratitudeListModelInner_nointernate.addAll(Util.lstShowAll_db_nointernate);
                                sharedPreference.write("my_list_nointernate", "", new Gson().toJson(Util.lstShowAll_db_nointernate));

                            }else{
                                Log.e("state_1","5");
                                sharedPreference.write("my_list_nointernate", "", new Gson().toJson(Util.lstShowAll_db_nointernate));

                            }
                        }else{
                            Log.e("state_1","6");
                            sharedPreference.write("my_list_nointernate", "", new Gson().toJson(Util.lstShowAll_db_nointernate));

                        }

                        dialog.dismiss();
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                    } else{

                        MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);
                        HashMap<String, Object> requestHash = new HashMap<>();
                        requestHash.put("model", getGratitudeListModelInner);
                        requestHash.put("Key", Util.KEY);
                        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                        saveAddGratitudeData(requestHash, dialog, selectedOption, arrAchieveOptions.get(selectedOption) + "" + edtAchieve.getText().toString().trim(), false);

                    }

                    // Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(getActivity()).gratitudeDao().insertAchievements(lstShowAll_db));




                }

            }
        });


        //add by jyotirmoy-j151

        rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (clickGratitudeEntry) {
                    clickGratitudeEntry = false;
                    //gratitudeentry with add pic share option
                    if (edtAchieve.getText().toString().trim().equals("")) {

                        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                        alertDialogCustom.ShowDialog("Alert", "Please enter some text", false);
                        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                            @Override
                            public void onDone(String title) {

                            }

                            @Override
                            public void onCancel(String title) {

                            }
                        });

                    } else {
                        MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);
                        HashMap<String, Object> requestHash = new HashMap<>();
                        requestHash.put("model", getGratitudeListModelInner);
                        requestHash.put("Key", Util.KEY);
                        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                        saveAddGratitudeData(requestHash, dialog, selectedOption, edtAchieve.getText().toString().trim(), true);
                    }
                } else {
                    /////other journal wntry with add pic
                    // jornalImgAdd=true;

//                    pickImageFromGalleryNew(addPicButton,imgBackgroundPicTOP);
                    pickImageFromCamrea_(addPicButton);


                    if (imgBackgroundPicTOP.getResources() != null) {
                        addPicButton.setText("CHANGE PIC");
                    }

                }
            }
        });


        dialog.show();
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Objects.requireNonNull(getActivity()).getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, // prefix
                ".jpg", // suffix
                storageDir // directory
        );

        // Save a file path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void openCam(){
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
//            // Create the File where the photo should go
//            try {
//                photoFile = createImageFile();
//                // Continue only if the File was successfully created
//                Uri photoURI = FileProvider.getUriForFile(
//                        getActivity(),
//                        "com.ashysystem.mbhq.fileprovider",
//                        photoFile
//                );
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
//            } catch (Exception ex) {
//                // Error occurred while creating the File
//                ex.printStackTrace();
//            }
//        }
        ContentResolver contentResolver = getActivity().getContentResolver();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC" + System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
        values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        imgPath = getRealPathFromUri(uri);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) {
            // Handle the error gracefully
            return null;
        }

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String filePath = cursor.getString(column_index);
        cursor.close();
        return filePath;
    }
    private void pickImageFromCamrea_(TextView addPicButton) {

        final Dialog dlg = new Dialog(getActivity());
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dlg.setContentView(R.layout.dialog_browse_bottom);

        Window window = dlg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        RelativeLayout rlGal = (RelativeLayout) dlg.findViewById(R.id.rlGal);
        RelativeLayout rlCancel = (RelativeLayout) dlg.findViewById(R.id.rlCancel);
        RelativeLayout rlCam = (RelativeLayout) dlg.findViewById(R.id.rlCam);
        rlCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission() && hasGalleryPermission()) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
                            // Create the File where the photo should go
                            openCam();
                        }

                    } else {
                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 203);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.CAMERA}, 203);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 203);
                            }
                        }
                    }

                } else {
                    openCam();
                }

            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPicButton.setText("ADD PIC");
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
                        getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                    } else {

                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 202);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            }
                        }
                        // openAppSettings();
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);
                }

            }
        });
        dlg.show();

    }


    private MyAchievementsListInnerModel makeAchieveModelForAddPic(String edtAchieve, Integer id) {
        MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
        getGratitudeListModelInner.setId(id);
        getGratitudeListModelInner.setAchievement(edtAchieve);
        getGratitudeListModelInner.setNotes("");
        getGratitudeListModelInner.setUserEqFolderId(filename1);
        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
        getGratitudeListModelInner.setStatus(false);
        getGratitudeListModelInner.setIsDeleted(false);
        getGratitudeListModelInner.setCategoryId(1);

        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1(43200);
        getGratitudeListModelInner.setReminderAt2(43200);
        getGratitudeListModelInner.setUploadPictureImgBase64("");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        getGratitudeListModelInner.setCreatedAt(simpleDateFormat.format(calendar.getTime()));

        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1(43200);
        getGratitudeListModelInner.setReminderAt2(43200);
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setSunday(false);
        getGratitudeListModelInner.setMonday(false);
        getGratitudeListModelInner.setTuesday(false);
        getGratitudeListModelInner.setWednesday(false);
        getGratitudeListModelInner.setThursday(false);
        getGratitudeListModelInner.setFriday(false);
        getGratitudeListModelInner.setSaturday(false);
        getGratitudeListModelInner.setJanuary(false);
        getGratitudeListModelInner.setFebruary(false);
        getGratitudeListModelInner.setMarch(false);
        getGratitudeListModelInner.setApril(false);
        getGratitudeListModelInner.setMay(false);
        getGratitudeListModelInner.setJune(false);
        getGratitudeListModelInner.setJuly(false);
        getGratitudeListModelInner.setAugust(false);
        getGratitudeListModelInner.setSeptember(false);
        getGratitudeListModelInner.setOctober(false);
        getGratitudeListModelInner.setNovember(false);
        getGratitudeListModelInner.setDecember(false);
        getGratitudeListModelInner.setUploadPictureImgBase64("");
        getGratitudeListModelInner.setCreatedAt(simpleDateFormat.format(calendar.getTime()));
        return getGratitudeListModelInner;
    }


    //j16


    private MyAchievementsListInnerModel makeAchieveModelForAdd(EditText edtAchieve, Integer selectedOption) {


        MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
        getGratitudeListModelInner.setId(0);
        getGratitudeListModelInner.setPrompt(arrAchieveOptions.get(selectedOption));
        getGratitudeListModelInner.setAchievement(edtAchieve.getText().toString().trim());
        getGratitudeListModelInner.setNotes("");
        getGratitudeListModelInner.setUserEqFolderId(filename1);
        getGratitudeListModelInner.setFrequencyId(1);
//        getGratitudeListModelInner.setUserEqFolderId("" +
//                "");

        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
        getGratitudeListModelInner.setStatus(false);
        getGratitudeListModelInner.setIsDeleted(false);

        if (selectedOption == 2) {
            getGratitudeListModelInner.setPromptOfDay(true);
        }

        getGratitudeListModelInner.setCategoryId(1);

        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1(43200);
        getGratitudeListModelInner.setReminderAt2(43200);
        getGratitudeListModelInner.setUploadPictureImgBase64("");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        getGratitudeListModelInner.setCreatedAt(simpleDateFormat.format(calendar.getTime()));

        getGratitudeListModelInner.setFrequencyId(1);
        getGratitudeListModelInner.setMonthReminder(1);
        getGratitudeListModelInner.setReminderOption(1);
        getGratitudeListModelInner.setReminderAt1(43200);
        getGratitudeListModelInner.setReminderAt2(43200);
        getGratitudeListModelInner.setEmail(false);
        getGratitudeListModelInner.setPushNotification(false);
        getGratitudeListModelInner.setSunday(false);
        getGratitudeListModelInner.setMonday(false);
        getGratitudeListModelInner.setTuesday(false);
        getGratitudeListModelInner.setWednesday(false);
        getGratitudeListModelInner.setThursday(false);
        getGratitudeListModelInner.setFriday(false);
        getGratitudeListModelInner.setSaturday(false);
        getGratitudeListModelInner.setJanuary(false);
        getGratitudeListModelInner.setFebruary(false);
        getGratitudeListModelInner.setMarch(false);
        getGratitudeListModelInner.setApril(false);
        getGratitudeListModelInner.setMay(false);
        getGratitudeListModelInner.setJune(false);
        getGratitudeListModelInner.setJuly(false);
        getGratitudeListModelInner.setAugust(false);
        getGratitudeListModelInner.setSeptember(false);
        getGratitudeListModelInner.setOctober(false);
        getGratitudeListModelInner.setNovember(false);
        getGratitudeListModelInner.setDecember(false);
        getGratitudeListModelInner.setUploadPictureImgBase64("");
        getGratitudeListModelInner.setCreatedAt(simpleDateFormat.format(calendar.getTime()));

        return getGratitudeListModelInner;
    }

    private void saveAddGratitudeData(HashMap<String, Object> reqhash, Dialog previousDialog, Integer selectedOption, String growthName, boolean isShare) {
        new MyAsyncTask1().execute();
        SimpleDateFormat simpleDateFormat100 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar100 = Calendar.getInstance();
        calendar100.set(Calendar.HOUR, 0);
        calendar100.set(Calendar.MINUTE, 0);
        calendar100.set(Calendar.SECOND, 0);
        if (sharedPreference.read("I_M_GRATEFUL_ACHIEVE_DATE", "").equals("")) {
            sharedPreference.write("I_M_GRATEFUL_ACHIEVE", "", growthName);
            String date = simpleDateFormat100.format(calendar100.getTime());
            sharedPreference.write("I_M_GRATEFUL_ACHIEVE_DATE", "", date);
        } else {
            try {
                String date = simpleDateFormat100.format(calendar100.getTime());
                if (!sharedPreference.read("I_M_GRATEFUL_ACHIEVE_DATE", "").equals(date)) {
                    sharedPreference.write("I_M_GRATEFUL_ACHIEVE", "", growthName);
                    sharedPreference.write("I_M_GRATEFUL_ACHIEVE_DATE", "", date);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        if (Util.checkConnection(getActivity())) {

            //progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        progressDialog.setProgress(percentage);
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
            progressDialog.show();

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    progressDialog.setProgress(100);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {

                                    new MyAsyncTask().execute();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            showpopup(previousDialog,isShare);
                                        }

                                    }, 700);


                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 200);

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            //Util.showToast(getActivity(), Util.networkMsg);

            saveGrowthLocaly(selectedOption, previousDialog, false, null);

        }

    }
    private void showpopup( Dialog previousDialog,boolean isShare){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Data successfully saved") .setTitle("Efc");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //                                            new MyAsyncTask_().execute();

                        // Preventing multiple clicks, using threshold of 1 second
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();


                        pagenation_from_api=true;
                        previousDialog.dismiss();
                        ((MainActivity) getActivity()).refershGamePoint(getActivity());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                        Util.isReloadTodayMainPage = true;

                        if (!isShare) {
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
                        } else {
                            // openDialogForGratitudeShare(previousDialog, growthName, response.body().getDetails().getId());
                        }

                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();

    }
    //added by jyotirmoy-j151

    //add by jyotirmoy-j151
    private void openDialogForTextOverPicOption(String gratitudeName, Integer gratitudeID, String TYPE) {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_text_over_pic_shareability);

        RelativeLayout rlSharableSectionTOP = dialog.findViewById(R.id.rlSharableSection);
        RelativeLayout rlPicSectionTOP = dialog.findViewById(R.id.rlPicSection);
        cardViewBackgroundPicTOP = dialog.findViewById(R.id.cardViewBackgroundPic);
        imgBackgroundPicTOP = dialog.findViewById(R.id.imgBackgroundPic);
        llAddPicTOP = dialog.findViewById(R.id.llAddPic);
        buttonChangeBackgroundImageTOP = dialog.findViewById(R.id.buttonChangeBackgroundImage);
        buttonMoveTextBoxTOP = dialog.findViewById(R.id.buttonMoveTextBox);
        RelativeLayout rlBorderTOP = dialog.findViewById(R.id.rlBorder);
        CheckBox chkBorderNoBorderTOP = dialog.findViewById(R.id.chkBorderNoBorder);
        Spinner spTextSizeTOP = dialog.findViewById(R.id.spTextSize);
        Button btnLeftAlignmentTOP = dialog.findViewById(R.id.btnLeftAlignment);
        Button btnCenterAlignmentTOP = dialog.findViewById(R.id.btnCenterAlignment);
        Button btnRightAlignmentTOP = dialog.findViewById(R.id.btnRightAlignment);
        RelativeLayout rlWhiteBackgroundBlackTextTOP = dialog.findViewById(R.id.rlWhiteBackgroundBlackText);
        RelativeLayout rlBlackBackgroundWhiteTextTOP = dialog.findViewById(R.id.rlBlackBackgroundWhiteText);
        RelativeLayout rlTransparentBackgroundBlackTextTOP = dialog.findViewById(R.id.rlTransparentBackgroundBlackText);
        RelativeLayout rlTransparentBackgroundWhiteTextTOP = dialog.findViewById(R.id.rlTransparentBackgroundWhiteText);
        ImageView imgWhiteBackgroundBlackTextTOP = dialog.findViewById(R.id.imgWhiteBackgroundBlackText);
        ImageView imgBlackBackgroundWhiteTextTOP = dialog.findViewById(R.id.imgBlackBackgroundWhiteText);
        ImageView imgTransparentBackgroundBlackTextTOP = dialog.findViewById(R.id.imgTransparentBackgroundBlackText);
        ImageView imgTransparentBackgroundWhiteTextTOP = dialog.findViewById(R.id.imgTransparentBackgroundWhiteText);
        rlTextOverPicInnerTOP = dialog.findViewById(R.id.rlTextOverPicInner);
        EditText edtTextOverPicInnerTOP = dialog.findViewById(R.id.edtTextOverPicInner);
        TextView txtGratefulFor = dialog.findViewById(R.id.txtGratefulFor);
        CustomScrollView scroll_view = dialog.findViewById(R.id.scroll_view);
        FrameLayout frameLayout = dialog.findViewById(R.id.frameLayout);
        RelativeLayout rootlayout = dialog.findViewById(R.id.rootlayout);
        TextView txtTextOverPicOwner = dialog.findViewById(R.id.txtTextOverPicOwner);
        ImageView imgMindBodyHq = dialog.findViewById(R.id.imgMindBodyHq);
        RelativeLayout rlShareGratitude = dialog.findViewById(R.id.rlShareGratitude);
        RelativeLayout rlCancelGratitude = dialog.findViewById(R.id.rlCancelGratitude);
        RelativeLayout rlTextAndPic = dialog.findViewById(R.id.rlTextAndPic);
        EditText edtTextANDPic = dialog.findViewById(R.id.edtTextANDPic);

        final boolean[] boolWhiteBackgroundBlackText = {true};
        final boolean[] boolBlackBackgroundWhiteText = {false};
        final boolean[] boolTransparentBackgroundBlackText = {false};
        final boolean[] boolTransparentBackgroundWhiteText = {false};

        if (TYPE.equals("textAndPic")) {
            rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextAndPic.setVisibility(View.VISIBLE);
            rlWhiteBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlBlackBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundWhiteTextTOP.setVisibility(View.GONE);
        } else if (TYPE.equals("textOnly")) {
            rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextAndPic.setVisibility(View.VISIBLE);
            rlWhiteBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlBlackBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlPicSectionTOP.setVisibility(View.GONE);
        }



        edtTextOverPicInnerTOP.setText(gratitudeName);
        txtTextOverPicOwner.setText(gratitudeName);
        edtTextANDPic.setText(gratitudeName);


        rlBorderTOP.setOnClickListener(view -> {
            if (chkBorderNoBorderTOP.isChecked()) {
                chkBorderNoBorderTOP.setChecked(false);
            } else {
                chkBorderNoBorderTOP.setChecked(true);
            }
        });
        chkBorderNoBorderTOP.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                if (strDialogSelectionType.equals("textAndPic") || strDialogSelectionType.equals("textOnly")) {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.round_corner_black);
                    rlTextAndPic.setBackgroundResource(R.drawable.edittext_background_black_border);
                } else {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.round_corner_black);
                    if (boolWhiteBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black);
                    } else if (boolBlackBackgroundWhiteText[0]) {

                    } else if (boolTransparentBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
                    } else {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
                    }
                }
            } else {
                if (strDialogSelectionType.equals("textAndPic") || strDialogSelectionType.equals("textOnly")) {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    rlTextAndPic.setBackgroundResource(R.drawable.edittext_background_white_border);
                } else {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    if (boolWhiteBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    } else if (boolBlackBackgroundWhiteText[0]) {

                    } else if (boolTransparentBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
                    } else {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
                    }
                }
            }
        });
        rlBorderTOP.performClick();
        llAddPicTOP.setOnClickListener(view -> {
            Log.e("direct add", "direct add image-line-1206");
            pickImageFromGallery();
        });
        buttonChangeBackgroundImageTOP.setOnClickListener(view -> {
            pickImageFromGallery();
        });
        ArrayList<Integer> lstTextSize = new ArrayList<>();
        for (int i = 12; i <= 48; i = i + 2) {
            lstTextSize.add(i);
        }
        ArrayAdapter<Integer> adapterFlag = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, lstTextSize);
        adapterFlag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTextSizeTOP.setAdapter(adapterFlag);
        spTextSizeTOP.setSelection(4);

        spTextSizeTOP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                } else if (i == 1) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                } else if (i == 2) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                } else if (i == 3) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                } else if (i == 4) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                } else if (i == 5) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                } else if (i == 6) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                } else if (i == 7) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                } else if (i == 8) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                } else if (i == 9) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                } else if (i == 10) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                } else if (i == 11) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                } else if (i == 12) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                } else if (i == 13) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                } else if (i == 14) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                } else if (i == 15) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                } else if (i == 16) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                } else if (i == 17) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                } else if (i == 18) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLeftAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.LEFT);
            edtTextANDPic.setGravity(Gravity.LEFT);
            txtTextOverPicOwner.setGravity(Gravity.LEFT);
        });

        btnCenterAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.CENTER);
            edtTextANDPic.setGravity(Gravity.CENTER);
            txtTextOverPicOwner.setGravity(Gravity.CENTER);
        });

        btnRightAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.RIGHT);
            edtTextANDPic.setGravity(Gravity.RIGHT);
            txtTextOverPicOwner.setGravity(Gravity.RIGHT);
        });

        rlWhiteBackgroundBlackTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.white));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.black));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.black));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.black));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounded_corner_white);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.logo_mbhq);
            boolWhiteBackgroundBlackText[0] = true;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlBlackBackgroundWhiteTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.black));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.white));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.white));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.white));
            rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_filled_black);
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.mndbody_logo_white);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = true;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlTransparentBackgroundBlackTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.white));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.black));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.black));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.black));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.logo_mbhq);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = true;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlTransparentBackgroundWhiteTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_green_check);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.black));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.white));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.white));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.white));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.mndbody_logo_white);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = true;
        });

        final boolean[] boolMoveText = {false};
        buttonMoveTextBoxTOP.setOnClickListener(view -> {
            if (!boolMoveText[0]) {
                boolMoveText[0] = true;
                scroll_view.setEnableScrolling(false);
                scroll_view.setFocusableInTouchMode(false);
                buttonMoveTextBoxTOP.setText("lock text box");
                buttonMoveTextBoxTOP.setBackgroundResource(R.drawable.rounded_corner_green);
                buttonMoveTextBoxTOP.setTextColor(getResources().getColor(R.color.white));
                txtTextOverPicOwner.setText(edtTextOverPicInnerTOP.getText().toString());
                edtTextOverPicInnerTOP.setVisibility(View.GONE);
                txtTextOverPicOwner.setVisibility(View.VISIBLE);
                rlTextOverPicInnerTOP.setOnTouchListener(new OnDragTouchListener(rlTextOverPicInnerTOP, frameLayout));
            } else {
                boolMoveText[0] = false;
                scroll_view.setEnableScrolling(true);
                scroll_view.setFocusableInTouchMode(true);
                buttonMoveTextBoxTOP.setText("move text box");
                buttonMoveTextBoxTOP.setBackgroundResource(R.drawable.rounded_corner_green_border_white_inside);
                buttonMoveTextBoxTOP.setTextColor(getResources().getColor(R.color.colorPrimary));
                edtTextOverPicInnerTOP.setVisibility(View.VISIBLE);
                txtTextOverPicOwner.setVisibility(View.GONE);
                rlTextOverPicInnerTOP.setOnTouchListener(null);
            }
        });

        rlCancelGratitude.setOnClickListener(view -> {
            dialog.dismiss();
            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
        });

        rlShareGratitude.setOnClickListener(view -> {
            if (mFile == null) {
                final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
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
                dialog.dismiss();
                if (strDialogSelectionType.equals("textOverPic")) {
                    // funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextOverPicInnerTOP);
                    funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextOverPicInnerTOP.getText().toString());
                } else {
                    //funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextANDPic);
                    funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextANDPic.getText().toString());
                }
            }
        });

        dialog.show();
    }

    //add by jyotirmoy-j151
    private void pickImageFromGallery() {
        final Dialog dlg = new Dialog(getActivity());
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dlg.setContentView(R.layout.dialog_browse_bottom);

        Window window = dlg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
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
                        Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                    } else {
                        if (!Settings.System.canWrite(getActivity())) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 203);
                        }
                    }

                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    out = createFolder();
                    imgPath = out.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
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
                        getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                    } else {

                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 202);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            }
                        }
                        // openAppSettings();
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);
                }

            }
        });
        dlg.show();

    }

    private void pickImageFromGalleryNew(TextView addPicButton, ImageView imgBackgroundPicTOP) {
        final Dialog dlg = new Dialog(getActivity());
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dlg.setContentView(R.layout.dialog_browse_bottom);

        Window window = dlg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
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
                        Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                    } else {
                        if (!Settings.System.canWrite(getActivity())) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 203);
                        }
                    }

                } else {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    out = createFolder();
                    imgPath = out.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                }

          /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            } else {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                out = createFolder();
                Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
            }*/

            }
        });
        rlCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPicButton.setText("ADD PIC");
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
                        getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                    } else {

                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 202);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            }
                        }
                        // openAppSettings();
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);
                }

            }
        });
        dlg.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {


        if (requestCode == 203) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                // You can now use the camera in your app
                Log.e("camera", "20");

                openCam();


            } else {
                Log.e("camera", "21");
                openAppSettings();
                // Permission was denied
                // You can disable the feature that requires the camera permission
            }
        } else if (requestCode == 202) {

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                // You can now use the camera in your app
                Log.e("camera", "22");
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

            } else {
                Log.e("camera", "23");
                openAppSettings();
                // Permission was denied
                // You can disable the feature that requires the camera permission
            }

        } else if (requestCode == 200) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission Gal", "Granted");
                try {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("Permission Gal", "Denied");
            }
        }
    }


    private void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    private boolean hasCameraPermission() {
//        int hasPermissionWrite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        int hasPermissionCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
//        if (hasPermissionRead == PackageManager.PERMISSION_GRANTED && hasPermissionCamera == PackageManager.PERMISSION_GRANTED && hasPermissionWrite == PackageManager.PERMISSION_GRANTED) {
//            Log.e("camera","10");
//
//            return true;
//        } else
//
//            Log.e("camera","11");
//
//        return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // For Android versions below API level 30
            return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        } else {
            // For Android versions R (API level 30) and above
            return ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private boolean hasGalleryPermission() {
//        int hasPermissionWrite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED) {
//            return true;
//        } else
//            return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // For Android versions below API level 30
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            // For Android versions R (API level 30) and above
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private File createFolder() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Squad");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(getActivity(), "Failed to create directory SQUAD  Directory.",
                        Toast.LENGTH_LONG).show();

                Log.d("Laylam Path Create", "Prep UP");
                return null;

            }
        }
        // Create a media file name
        // For unique file name appending current timeStamp with file name
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());
        File mediaFile;
        // For unique video file name appending current timeStamp with file name
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
        return mediaFile;
    }




    private void cropPhoto(final Bitmap bitmap, final String imgPath) {
        final Dialog dialogcrop = new Dialog(getActivity(), android.R.style.Theme);
        dialogcrop.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogcrop.setContentView(R.layout.cropdialogimage);
        dialogcrop.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        TextView txtDone = (TextView) dialogcrop.findViewById(R.id.txtDone);
        TextView txtCancel = (TextView) dialogcrop.findViewById(R.id.txtCancel);
        cropImageView = (CropImageView) dialogcrop.findViewById(R.id.CropImageView);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Bitmap myBitmap = null;
        File file = new File(imgPath);
        long bitmapLength = file.length();
        Log.e("bitmapsize", "img size>>>>>>>>>" + bitmapLength);
        if (bitmapLength > 30000000) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Image Is Too Large");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            return;
        }
        if (onLineBitMap == null) {
            myBitmap = decodeSampledBitmapFromFile(imgPath, ViewGroup.LayoutParams.MATCH_PARENT, height);
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
                try {

                    bitimage = cropImageView.getCroppedImage();
                    dialogcrop.dismiss();
//                    Uri uri = getImageUri(Objects.requireNonNull(getActivity()),bitimage);
//                    String cropPath = Util.getFilePathFromUri(Objects.requireNonNull(getActivity()),uri);
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

                    if (bitimage != null) {
                        // Set the bitmap to null
                        bitimage.recycle();
                        bitimage = null;
                    }
                    dialogcrop.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialogcrop.show();
    }
    private Uri getImageUri(Context inContext, Bitmap inImage) {
        String fileName = System.currentTimeMillis() + "_IMG";
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        //inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, fileName, null);
        return Uri.parse(path);
    }

//    private void preaprePictureForUpload(String cropPath) {
//        try {
//            File file = new File(cropPath);
//            FileInputStream imageInFile = new FileInputStream(file);
//            byte[] imageData = new byte[(int) file.length()];
//            imageInFile.read(imageData);
//            stringImg = encodeImage(imageData);
//            Log.e("BASE64STRING", stringImg);//////
//            sharedPreference.write("GRATITUDEIMAGE", "", stringImg);
//            mFile = file;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
//            imgBackgroundPicTOP.setVisibility(View.VISIBLE);
//            if (null != cropPath) {
//                Log.e("CRPPPPPP", "preaprePictureForUpload: " + cropPath );
//                imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(cropPath));
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.e("CROPPPP", "preaprePictureForUpload: " + e.getMessage());
//        }
//    }

    public  File getFileFromContentUri(ContentResolver contentResolver, Uri contentUri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = contentResolver.query(contentUri, projection, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            String filePath = cursor.getString(columnIndex);

            // Now you have the actual file path
            cursor.close();
            return new File(filePath);
        } else {
            // Handle the case where the cursor is null or empty
            if (cursor != null) {
                cursor.close();
            }
            return null;
        }
    }
    private void preaprePictureForUpload(String cropPath) {
    try {
        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri contentUri = Uri.parse(cropPath);
        File file =getFileFromContentUri(contentResolver, contentUri);

        InputStream imageInFile =  contentResolver.openInputStream(Uri.parse(cropPath));
        byte[] imageData = new byte[(int) file.length()];
        imageInFile.read(imageData);
        stringImg = encodeImage(imageData);
        Log.e("BASE64STRING", stringImg);
        sharedPreference.write("GRATITUDEIMAGE", "", stringImg);
        mFile = file;
        Log.e("mfile",""+mFile);

    } catch (Exception e) {
        e.printStackTrace();
    }
        cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
    imgBackgroundPicTOP.setImageURI(Uri.parse(cropPath));
    cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
    imgBackgroundPicTOP.setVisibility(View.VISIBLE);

}
    private String storeImage(Bitmap image) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC_EQ_CROPPED" + System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        // values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
        String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
        values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
        Uri imageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (imageUri == null) {
            Log.e("TAG", "Error creating media file");
            return "";
        }

        try {
            OutputStream outputStream = getActivity().getContentResolver().openOutputStream(imageUri);
            image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (IOException e) {
            Log.e("TAG", "Error accessing file: " + e.getMessage());
        }

        return imageUri.toString();
    }
//    private String storeImage(Bitmap image) {
//
//        if(null==image){
//
//        }else{
//            File pictureFile = getOutputMediaFile();
//
//            if (pictureFile == null) {
//                Log.d("TAG",
//                        "Error creating media file, check storage permissions: ");// e.getMessage());
//                return "";
//            }
//            try {
//                FileOutputStream fos = new FileOutputStream(pictureFile);
//                image.compress(Bitmap.CompressFormat.PNG, 40, fos);
//                fos.close();
//            } catch (FileNotFoundException e) {
//                Log.d("TAG", "File not found: " + e.getMessage());
//            } catch (IOException e) {
//                Log.d("TAG", "Error accessing file: " + e.getMessage());
//            }
//            return pictureFile.getAbsolutePath();
//        }
//        return null;
//    }

    private File getOutputMediaFile() {


        File mediaStorageDir = Util.getFile(getContext());  //added by jyotirmoy

        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    //add by jyotirmoy-j15f
    private void funcForShareImageGratitudeSharability(RelativeLayout rlPicSection, Integer gratitudeID, String edtAchieveDialog) {
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


        MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAddPic(edtAchieveDialog, gratitudeID);

        HashMap<String, Object> requestHash = new HashMap<>();
        requestHash.put("model", getGratitudeListModelInner);
        requestHash.put("Key", Util.KEY);
        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

        saveAddGratitudeDataForShare(requestHash, shareFile);


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

    private void openDialogForShareExamples(String TYPE) {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gratitude_share_examples);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView imgExample = dialog.findViewById(R.id.imgExample);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        if (TYPE.equals("textAndPic")) {
            imgExample.setImageResource(R.drawable.text_and_pic);
        } else if (TYPE.equals("textOverPic")) {
            imgExample.setImageResource(R.drawable.text_over_pic);
        } else {
            imgExample.setImageResource(R.drawable.text_only);
        }

        dialog.show();

    }

    public void saveAddGratitudeData(HashMap<String, Object> reqhash) {

        if (Util.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        progressDialog.setProgress(percentage);
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
            progressDialog.show();

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    progressDialog.setProgress(100);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 200);

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Log.e("callnonetwork", "callnonetwork-13");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    public void saveAddGratitudeDataForShare(HashMap<String, Object> reqhash, File shareFile) {

        if (Util.checkConnection(getActivity())) {

            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        progressDialog.setProgress(percentage);
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
            progressDialog.show();

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    progressDialog.setProgress(100);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {
                                    Intent share = new Intent(Intent.ACTION_SEND);
                                    share.setType("image/jpeg");
                                    Log.e("FILE_PATH", shareFile.getPath() + ">>>>");
                                    Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" + ".fileprovider", shareFile);
                                    share.putExtra(Intent.EXTRA_STREAM, photoURI);
                                    startActivity(Intent.createChooser(share, "Share Image"));

                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
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
            Log.e("callnonetwork", "callnonetwork-12");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    private void getAllGrowth(){
        mDisposable.add(gratitudeViewModel.getAllGrowth()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(growthEntities -> {
                    if(growthEntities!=null && growthEntities.size()>0)
                    {

                    }else {

                    }
                },throwable -> {

                }));

    }
    private void saveGrowthLocaly(Integer selectedOption, Dialog previousDialog, boolean isNetAvailbale, MyAchievementsListInnerModel innerModel) {

        if (isNetAvailbale) {
            MyAchievementsListInnerModel getGratitudeListModelInner = innerModel;
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String todatDate = simpleDateFormat.format(calendar.getTime());
            Gson gson = new Gson();
            String gratitudeModel = gson.toJson(getGratitudeListModelInner);
            String timeStamp = String.valueOf(calendar.getTimeInMillis());
            GrowthEntity gratitudeEntity = new GrowthEntity(getGratitudeListModelInner.getId(), todatDate, gratitudeModel, timeStamp, true);
            mDisposable.add(gratitudeViewModel.insertGrowth(gratitudeEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.e("GROWTH_SAVE", "SUCCESSFULL");
                        previousDialog.dismiss();

                    }, throwable -> {
                        previousDialog.dismiss();
                        Log.e("GROWTH_SAVE", "NOT_SUCCESSFULL");
                    }));
        } else {
            MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();
            String todatDate = simpleDateFormat.format(calendar.getTime());
            Gson gson = new Gson();
            String gratitudeModel = gson.toJson(getGratitudeListModelInner);
            String timeStamp = String.valueOf(calendar.getTimeInMillis());
            GrowthEntity gratitudeEntity = new GrowthEntity(0, todatDate, gratitudeModel, timeStamp, false);
            mDisposable.add(gratitudeViewModel.insertGrowth(gratitudeEntity)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(() -> {
                        Log.e("GROWTH_SAVE", "SUCCESSFULL");
                        previousDialog.dismiss();
                        Util.isReloadTodayMainPage = false;
                        openDialogForGROWTHSaveLocally();

                    }, throwable -> {
                        previousDialog.dismiss();
                        Log.e("GROWTH_SAVE", "NOT_SUCCESSFULL");
                    }));
        }
    }

    private void openDialogForGROWTHSaveLocally() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_save_growth_local_succes);

        RelativeLayout rlOk = dialog.findViewById(R.id.rlOk);

        rlOk.setOnClickListener(view -> {
            dialog.dismiss();
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MbhqTodayMainFragment());
            ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
        });

        dialog.show();
    }

    private void setDefault(Integer Id,Boolean defaultvalue) {

        if (Connection.checkConnection(getActivity())) {
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserEqFolderId", Id);
            hashReq.put("IsDefaultView", defaultvalue);


            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity(),sharedPreference.read("jwt", ""));

            Call<Folderdefaultresponse> jsonObjectCall = finisherService.setDefault(hashReq);
            jsonObjectCall.enqueue(new Callback<Folderdefaultresponse>() {
                @Override
                public void onResponse(Call<Folderdefaultresponse> call, Response<Folderdefaultresponse> response) {
                    Gson gson = new Gson();
                    if(response.isSuccessful()){
                        mymodelArrayList.clear();
                        // ArrayList<Mymodel>mymodelArrayList=new ArrayList<>();
                        Type type = new TypeToken<ArrayList<UserEqFolder>>() {
                        }.getType();
                        mymodelArrayList = gson.fromJson(gson.toJson(response.body().getUserEqFolders()), type);

                    }
                    sharedPreference.write("my_list_eqfolder", "", new Gson().toJson(mymodelArrayList));

                    // sharedPreference.write("my_list_eqfolder", "", new Gson().toJson(mymodelArrayList));

                }

                @Override
                public void onFailure(Call<Folderdefaultresponse> call, Throwable t) {

                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void openDialogForFilter() {
        Log.i("clickedfolder",String.valueOf(Util.checkfolder.size()));
        Util.checkfolder.clear();
        ArrayList<UserEqFolder> myData = Util.myModelArrayList;
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String json= sharedPreference.read("my_list_eqfolder", "");
        ArrayList<UserEqFolder> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<UserEqFolder>>(){}.getType());

        if(null!=habitSwapList_ ) {
            if (habitSwapList_.size() > 0) {
                for (int i = 0; i < habitSwapList_.size(); i++) {
                    if (habitSwapList_.get(i).getIsDefaultView()) {
                        Util.checkfolder.add(habitSwapList_.get(i).getUserEqFolderId());

                        //Log.e("state1111111111111111111111111111", "onCreate0");
                        // Log.e("state1111111111111111111111111111", String.valueOf(checkfolder.size()));

                    }
                }
            }
        }
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_filter);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        RecyclerView rl_folder=dialog.findViewById(R.id.rl_F);
        if(null!=habitSwapList_ ) {
            /*for(int i=0;i<habitSwapList_.size();i++){
                if(habitSwapList_.get(i).getIsDefaultView()){
                    checkfolder.add(habitSwapList_.get(i).getUserEqFolderId());
                }
            }*/
            rl_folder.setVisibility(View.VISIBLE);

            if ( habitSwapList_.size() > 0 ) {
                rl_folder.setVisibility(View.VISIBLE);
                rl_folder.setLayoutManager(new LinearLayoutManager(getActivity())); // You can use GridLayoutManager or others for different layouts
                adapter = new MyAdapter(getActivity(),new OnItemClickListener(){
                    @Override
                    public void onItemClick_add(int p,int folderid, String filename, Boolean check) {
                        habitSwapList_.get(p).setIsDefaultView(true);
                        if(!Util.checkfolder.contains(folderid)){
                            // Log.i("clicked_________folderadd",String.valueOf(check) +" ->>  "+ String.valueOf(eqfolderid));

                            Util.checkfolder.add(folderid);
                            setDefault(folderid,true);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onItemClick_remove(int p,int folderidrmv, String filename, Boolean check) {
                        //  Log.i("clicked_________folderremove",String.valueOf(check) +" ->>  "+ String.valueOf(folderidrmv));
                        Integer a=folderidrmv;
                        habitSwapList_.get(p).setIsDefaultView(false);
                        if(Util.checkfolder.contains(a)){


                            Util.checkfolder.remove(a);
                            setDefault(a,false);
                        }
                        Log.i("clicked_________folderadd",String.valueOf(Util.checkfolder.size()));

                        adapter.notifyDataSetChanged();

                    }

                },habitSwapList_); // Provide your data list
                rl_folder.setAdapter(adapter);
            }else{
                rl_folder.setVisibility(View.GONE);
            }
        }else{
            rl_folder.setVisibility(View.GONE);
        }


        ImageView imgExpandClose = dialog.findViewById(R.id.imgExpandClose);
        ImageView imgSearchGrowth = dialog.findViewById(R.id.imgSearchGrowth);
        RelativeLayout rlShowAll = dialog.findViewById(R.id.rlShowAll);
        RelativeLayout rlToday = dialog.findViewById(R.id.rlToday);
        RelativeLayout rlThisMonth = dialog.findViewById(R.id.rlThisMonth);
        RelativeLayout rlSixMonth = dialog.findViewById(R.id.rlSixMonth);
        RelativeLayout rlOneYear = dialog.findViewById(R.id.rlOneYear);
        RelativeLayout rlAmProudOf = dialog.findViewById(R.id.rlAmProudOf);
        RelativeLayout rlIHvAccomplish = dialog.findViewById(R.id.rlIHvAccomplish);
        RelativeLayout rlChooseTo = dialog.findViewById(R.id.rlChooseTo);
        RelativeLayout rlIHvObserve = dialog.findViewById(R.id.rlIHvObserve);
        RelativeLayout rlIneedtowork=dialog.findViewById(R.id.rlIneedtowork);
        RelativeLayout rlIHvLearned = dialog.findViewById(R.id.rlIHvLearned);
        RelativeLayout rlIHvPraised = dialog.findViewById(R.id.rlIHvPraised);
        RelativeLayout rlTodayLetGoOf = dialog.findViewById(R.id.rlTodayLetGoOf);
        RelativeLayout rlDreamtOf = dialog.findViewById(R.id.rlDreamtOf);
        RelativeLayout rlJournalEntry = dialog.findViewById(R.id.rlJournalEntry);
        RelativeLayout rlPrint = dialog.findViewById(R.id.rlPrint);
        RelativeLayout rlRequirePassword = dialog.findViewById(R.id.rlRequirePassword);
        RelativeLayout rlTheStory = dialog.findViewById(R.id.rlTheStory);
        RelativeLayout rlChallenged = dialog.findViewById(R.id.rlChallenged);
        RelativeLayout rlCelebrating = dialog.findViewById(R.id.rlCelebrating);
        RelativeLayout rlLaughed = dialog.findViewById(R.id.rlLaughed);
        RelativeLayout rlTodayImFeeling = dialog.findViewById(R.id.rlTodayImFeeling);
        EditText edtSearch1 = dialog.findViewById(R.id.edtSearch1);

        //added by jyotirmoy->j4
        RelativeLayout rlShowPicture = dialog.findViewById(R.id.rlShowPicture);
        RelativeLayout rlGratefulEntry = dialog.findViewById(R.id.rlGratefulEntry);
        RelativeLayout rlWorkingTowards = dialog.findViewById(R.id.rlWorkingTowards);
        RelativeLayout rlFeelingGrowth = dialog.findViewById(R.id.rlFeelingGrowth);
        RelativeLayout rlCommittingTo = dialog.findViewById(R.id.rlCommittingTo);
        RelativeLayout rlPromptOfTheDAy = dialog.findViewById(R.id.rlPromptOfTheDAy);
        RelativeLayout rlFoundGift = dialog.findViewById(R.id.rlFoundGift);
        RelativeLayout rlHappyToday = dialog.findViewById(R.id.rlHappyToday);
        RelativeLayout rlIAcknowledge = dialog.findViewById(R.id.rlIAcknowledge);
        RelativeLayout rlOneThingIappreciate = dialog.findViewById(R.id.rlOneThingIappreciate);
        RelativeLayout rlOnlyPicture = dialog.findViewById(R.id.rlOnlyPicture);



        //ended by jyotirmoy->j4


        imgShowAllCheck = dialog.findViewById(R.id.imgShowAllCheck);
        imgTodayCheck = dialog.findViewById(R.id.imgTodayCheck);
        imgThisMonthCheck = dialog.findViewById(R.id.imgThisMonthCheck);
        imgSixMonthCheck = dialog.findViewById(R.id.imgSixMonthCheck);
        imgOneYearCheck = dialog.findViewById(R.id.imgOneYearCheck);
        imgAmProudOf = dialog.findViewById(R.id.imgAmProudOf);
        imgIHvAccomplish = dialog.findViewById(R.id.imgIHvAccomplish);
        imgIneedtowork = dialog.findViewById(R.id.imgIneedtowork);
        imgIHvObserve = dialog.findViewById(R.id.imgIHvObserve);
        imgIHvLearned = dialog.findViewById(R.id.imgIHvLearned);
        imgIHvPraised = dialog.findViewById(R.id.imgIHvPraised);
        imgTodayLetGoOf = dialog.findViewById(R.id.imgTodayLetGoOf);
        imgDreamtOf = dialog.findViewById(R.id.imgDreamtOf);
        imgJournalEntry = dialog.findViewById(R.id.imgJournalEntry);
        imgTheStory = dialog.findViewById(R.id.imgTheStory);
        imgChallenge = dialog.findViewById(R.id.imgChallenge);
        imgCelebrating = dialog.findViewById(R.id.imgCelebrating);
        imgLaughed = dialog.findViewById(R.id.imgLaughed);
        imgTodayImFeeling = dialog.findViewById(R.id.imgTodayImFeeling);
        imgChooseTo = dialog.findViewById(R.id.imgChooseTo);



        ImageView imgRequirePasswordCheck = dialog.findViewById(R.id.imgRequirePasswordCheck);


        LinearLayout llCheckBoxOptions = dialog.findViewById(R.id.llCheckBoxOptions);
        RelativeLayout rlSelectDate = dialog.findViewById(R.id.rlSelectDate);
        TextView txtSelectDate = dialog.findViewById(R.id.txtSelectDate);
        RelativeLayout rlShowResults = dialog.findViewById(R.id.rlShowResults);
        RelativeLayout rlClearAll = dialog.findViewById(R.id.rlClearAll);
        RelativeLayout rlTransparent = dialog.findViewById(R.id.rlTransparent);
        imgSelectDateRange = dialog.findViewById(R.id.imgSelectDateRange);
        llSecetDateWhole = dialog.findViewById(R.id.llSecetDateWhole);
        txtFromDate = dialog.findViewById(R.id.txtFromDate);
        txtToDate = dialog.findViewById(R.id.txtToDate);

        RelativeLayout rlContainPicture = dialog.findViewById(R.id.rlContainPicture);
        imgContainPicture = dialog.findViewById(R.id.imgContainPicture);
        imgShowPicture = dialog.findViewById(R.id.imgShowPicture); //add by jyotirmoy->j4
        imgGratefulEntry = dialog.findViewById(R.id.imgGratefulEntry); //add by jyotirmoy->j14
        imgWorkingTowards = dialog.findViewById(R.id.imgWorkingTowards); //add by jyotirmoy->j14
        imgFeelingGrowth = dialog.findViewById(R.id.imgFeelingGrowth); //add by jyotirmoy->j14
        imgCommittingTo = dialog.findViewById(R.id.imgCommittingTo); //add by jyotirmoy->j14
        imgPromptOfTheday = dialog.findViewById(R.id.imgPromptOfTheday); //add by jyotirmoy->j14
        imgFoundGift = dialog.findViewById(R.id.imgFoundGift); //add by jyotirmoy->j15
        imgHappyToday = dialog.findViewById(R.id.imgHappyToday); //add by jyotirmoy->j15
        imgIAcknowledge = dialog.findViewById(R.id.imgIAcknowledge); //add by jyotirmoy->j15
        imgOneThingIappreciate = dialog.findViewById(R.id.imgOneThingIappreciate);
        imgOnlyPicture = dialog.findViewById(R.id.imgOnlyPicture);

        GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);

        boolProudOf = growthSaveFilterModel.getBool_proud_of();
        boolAccomplished = growthSaveFilterModel.getBool_accomplished();
        boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
        boolObserved = growthSaveFilterModel.getBool_observed();
        boolLearned = growthSaveFilterModel.getBool_learned();
        boolPraised = growthSaveFilterModel.getBool_praised();
        boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
        boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
        boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

        boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
        boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
        boolShowImg = growthSaveFilterModel.getBool_show_img();



        boolGratefulFor = growthSaveFilterModel.getBool_grateful_for();//added by jyotirmoy->j14
        boolWorkingTowards = growthSaveFilterModel.getBool_working_towards();//added by jyotirmoy->j14
        boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth();//added by jyotirmoy->j14
        boolCommittingTo = growthSaveFilterModel.getBool_committing_to();//added by jyotirmoy->j14
        boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day();//added by jyotirmoy->j14
        boolFoundGift = growthSaveFilterModel.getBool_found_gift();//added by jyotirmoy->j15
        boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today();//added by jyotirmoy->j15
        boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge();//added by jyotirmoy->j15
        boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();


        boolTheStory = growthSaveFilterModel.getBool_the_story();
        boolChallenged = growthSaveFilterModel.getBool_challenged();
        boolCelebrating = growthSaveFilterModel.getBool_celebrating();
        boolLaughed = growthSaveFilterModel.getBool_laughed();
        boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
        globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
        filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();
        rlShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.write("my_list_eqfolder", "", new Gson().toJson(habitSwapList_));
                showFilterResults(growthSaveFilterModel);
                dialog.dismiss();

            }
        });

        SharedPreferences prefPicture = getActivity().getSharedPreferences("showpicture", MODE_PRIVATE);
        Boolean valuepic = prefPicture.getBoolean("picshow", false);

        if (valuepic) {
            sharedPreference.write("achivementfilter", "", "yes");
            imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            sharedPreference.write("achivementfilter", "", "");
            imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }


        if (boolProudOf) {
            imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolAccomplished) {
            imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolObserved) {
            imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolNeedToWork) {
            imgIneedtowork.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgIneedtowork.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolLearned) {
            imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolPraised) {
            imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolLetGoOf) {
            imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolDreamtOf) {
            imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolJournalEntry) {
            imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        //added jyotirmoy->j14
        if (boolGratefulFor) {
            imgGratefulEntry.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgGratefulEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolWorkingTowards) {
            imgWorkingTowards.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgWorkingTowards.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolFeelingGrowth) {
            imgFeelingGrowth.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgFeelingGrowth.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolCommittingTo) {
            imgCommittingTo.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgCommittingTo.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolPromptOfTheDay) {
            imgPromptOfTheday.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgPromptOfTheday.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolFoundGift) {
            imgFoundGift.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgFoundGift.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolFeltHappyToday) {
            imgHappyToday.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgHappyToday.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (boolIAcknowledge) {
            imgIAcknowledge.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgIAcknowledge.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if(boolIAppreciate){
            imgOneThingIappreciate.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }else {
            imgOneThingIappreciate.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        //ended jyotirmoy->j14
        if(boolIChooseToKnow){
            imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_tick);

        }else{
            imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if(boolOnlyPic){
            imgOnlyPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);

        }else {
            imgOnlyPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if(!boolShowImg){
            imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);

        }else {
            imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolTheStory) {
            imgTheStory.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgTheStory.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolChallenged) {
            imgChallenge.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgChallenge.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolCelebrating) {
            imgCelebrating.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgCelebrating.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolLaughed) {
            imgLaughed.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgLaughed.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (boolTodayImFeeling) {
            imgTodayImFeeling.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgTodayImFeeling.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (sharedPreference.readBoolean("DOES_GROWTH_REQUIRES_PASSWORD", "")) {
            imgRequirePasswordCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgRequirePasswordCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }

        if (filterSelectedvalue == 0) {
            setAllImageViewUnchecked();
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
        } else {
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        }
        if (filterSelectedvalue == 1) {
            setAllImageViewUnchecked();
            imgTodayCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeAllBoolFalse();
            boolToday = true;
        }
        if (filterSelectedvalue == 2) {
            setAllImageViewUnchecked();
            imgThisMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeAllBoolFalse();
            boolThisMonth = true;
        }
        if (filterSelectedvalue == 3) {
            setAllImageViewUnchecked();
            imgSixMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeAllBoolFalse();
            boolSixMonth = true;
        }
        if (filterSelectedvalue == 4) {
            setAllImageViewUnchecked();
            imgOneYearCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeAllBoolFalse();
            boolOneYear = true;
        }
        if (filterSelectedvalue == 5) {
            setAllImageViewUnchecked();
            imgContainPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeAllBoolFalse();
            boolPicture = true;
        }
        if (filterSelectedvalue == -1) {
            setAllImageViewUnchecked();
            imgSelectDateRange.setBackgroundResource(R.drawable.mbhq_ic_tick);
            makeAllBoolFalse();
            boolDateSelected = true;
        }

        rlPrint.setOnClickListener(view -> {
            dialog.dismiss();
            printGrowthApiCall();
        });



        edtSearch1.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {



            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {

               /* withfilterlist.clear();
                withfilterlist= ((MeditationCourseAdapter) rvCourseM.getAdapter()).getArrayList();
*/
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                Log.i("textchange","0");

                if(s.toString().length()>0){
                    List<MeditationCourseModel.Webinar> avaiavlelist1 = new ArrayList<>();

                    Log.i("textchange","1");
                    funRefresh1(s.toString());


                    //  loadAvailableAdapterM(avaiavlelist, avaiavlelist.size(),0);



                }else if(s.toString().length()==0){

                    funRefresh1(s.toString());


                }

            }
        });

        imgSearchGrowth.setOnClickListener(view -> {

            String searchPrmpt = edtSearch1.getText().toString().trim();
            if ("".equals(searchPrmpt)) {
                Toast.makeText(getActivity(), "Type Something to search", Toast.LENGTH_SHORT).show();


            } else {


                if ("I'm Grateful For".equalsIgnoreCase(searchPrmpt)) {

                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolGratefulFor) {
                        boolGratefulFor = true;
                        imgGratefulEntry.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolGratefulFor = false;
                        imgGratefulEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("Journal entry".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolJournalEntry) {
                        boolJournalEntry = true;
                        imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolJournalEntry = false;
                        imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("Prompt of the Day".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolPromptOfTheDay) {
                        boolPromptOfTheDay = true;
                        imgPromptOfTheday.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolPromptOfTheDay = false;
                        imgPromptOfTheday.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("Today I'm Feeling".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolTodayImFeeling) {
                        boolTodayImFeeling = true;
                        imgTodayImFeeling.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolTodayImFeeling = false;
                        imgTodayImFeeling.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I am proud of".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolProudOf) {
                        boolProudOf = true;
                        imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolProudOf = false;
                        imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("I Choose To Know".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolIChooseToKnow) {
                        boolIChooseToKnow = true;
                        imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_tick);

                    } else {
                        boolIChooseToKnow = false;
                        imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I've accomplished".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolAccomplished) {
                        boolAccomplished = true;
                        imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolAccomplished = false;
                        imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("I'm working towards".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolWorkingTowards) {
                        boolWorkingTowards = true;
                        imgWorkingTowards.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolWorkingTowards = false;
                        imgWorkingTowards.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("I'm feeling growth in".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolFeelingGrowth) {
                        boolFeelingGrowth = true;
                        imgFeelingGrowth.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolFeelingGrowth = false;
                        imgFeelingGrowth.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I'm committing to".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolCommittingTo) {
                        boolCommittingTo = true;
                        imgCommittingTo.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolCommittingTo = false;
                        imgCommittingTo.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("A small win I'm celebrating is".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolCelebrating) {
                        boolCelebrating = true;
                        imgCelebrating.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolCelebrating = false;
                        imgCelebrating.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }
                } else if ("I've found the gift in".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolFoundGift) {
                        boolFoundGift = true;
                        imgFoundGift.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolFoundGift = false;
                        imgFoundGift.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I felt happy today because".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolFeltHappyToday) {
                        boolFeltHappyToday = true;
                        imgHappyToday.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolFeltHappyToday = false;
                        imgHappyToday.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("One thing I appreciate about you today is".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolIAppreciate) {
                        boolIAppreciate = true;
                        imgOneThingIappreciate.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolIAppreciate = false;
                        imgOneThingIappreciate.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("The story I've told myself is".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolTheStory) {
                        boolTheStory = true;
                        imgTheStory.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolTheStory = false;
                        imgTheStory.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I've been challenged by".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolChallenged) {
                        boolChallenged = true;
                        imgChallenge.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolChallenged = false;
                        imgChallenge.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I need to work on/clear".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolNeedToWork) {
                        boolNeedToWork = true;
                        imgIneedtowork.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolNeedToWork = false;
                        imgIneedtowork.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                }else if ("I've observed".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolObserved) {
                        boolObserved = true;
                        imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolObserved = false;
                        imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I've learned".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolLearned) {
                        boolLearned = true;
                        imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolLearned = false;
                        imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I Acknowledge".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolIAcknowledge) {
                        boolIAcknowledge = true;
                        imgIAcknowledge.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolIAcknowledge = false;
                        imgIAcknowledge.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I laughed".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolLaughed) {
                        boolLaughed = true;
                        imgLaughed.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolLaughed = false;
                        imgLaughed.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I've praised".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolPraised) {
                        boolPraised = true;
                        imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolPraised = false;
                        imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else if ("I've let go of".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolLetGoOf) {
                        boolLetGoOf = true;
                        imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolLetGoOf = false;
                        imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }


                } else if ("I dreamt of".equalsIgnoreCase(searchPrmpt)) {
                    clearFilterResults(growthSaveFilterModel, txtSelectDate);
                    globalSearchStr = "";
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    if (!boolDreamtOf) {
                        boolDreamtOf = true;
                        imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    } else {
                        boolDreamtOf = false;
                        imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
                    }

                } else {
                    globalSearchStr = "";
                    filterSelectedvalue = 0;
                    growthSaveFilterModel.setSelected_date_range_filter_value(0);
                    setAllImageViewUnchecked();
                    imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    llSecetDateWhole.setVisibility(View.GONE);
                    makeAllTYPEBoolFalse();
                    setAllTYPEImageViewUnchecked();

                }

                showFilterResults(growthSaveFilterModel);
                dialog.dismiss();
            }
        });

        imgExpandClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (llCheckBoxOptions.getVisibility() == View.VISIBLE) {
                    imgExpandClose.setBackgroundResource(R.drawable.mbhq_expand_more);
                    llCheckBoxOptions.setVisibility(View.GONE);
                } else {
                    imgExpandClose.setBackgroundResource(R.drawable.mbhq_expand_less);
                    llCheckBoxOptions.setVisibility(View.VISIBLE);
                }
            }
        });

        rlTransparent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rlShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                filterSelectedvalue = 0;
                growthSaveFilterModel.setSelected_date_range_filter_value(0);
                setAllImageViewUnchecked();
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                llSecetDateWhole.setVisibility(View.GONE);
                makeAllTYPEBoolFalse();
                setAllTYPEImageViewUnchecked();
            }
        });
        rlContainPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                if (!boolPicture) {
                    makeAllBoolFalse();
                    boolPicture = true;
                    filterSelectedvalue = 5;
                    growthSaveFilterModel.setSelected_date_range_filter_value(5);
                    setAllImageViewUnchecked();
                    imgContainPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    llSecetDateWhole.setVisibility(View.GONE);
                    boolDateSelected = false;
                } else {
                    boolPicture = false;
                    setAllImageViewUnchecked();
                    makeAllBoolFalse();
                }
            }
        });

        rlToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                if (!boolToday) {
                    makeAllBoolFalse();
                    boolToday = true;
                    filterSelectedvalue = 1;
                    growthSaveFilterModel.setSelected_date_range_filter_value(1);
                    setAllImageViewUnchecked();
                    imgTodayCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    llSecetDateWhole.setVisibility(View.GONE);
                    boolDateSelected = false;
                } else {
                    boolToday = false;
                    setAllImageViewUnchecked();
                    makeAllBoolFalse();
                }
            }
        });

        rlThisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                if (!boolThisMonth) {
                    makeAllBoolFalse();
                    boolThisMonth = true;
                    filterSelectedvalue = 2;
                    growthSaveFilterModel.setSelected_date_range_filter_value(2);
                    setAllImageViewUnchecked();
                    imgThisMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    llSecetDateWhole.setVisibility(View.GONE);
                } else {
                    boolThisMonth = false;
                    setAllImageViewUnchecked();
                    makeAllBoolFalse();
                }
            }
        });

        rlSixMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                if (!boolSixMonth) {
                    makeAllBoolFalse();
                    boolSixMonth = true;
                    filterSelectedvalue = 3;
                    growthSaveFilterModel.setSelected_date_range_filter_value(3);
                    setAllImageViewUnchecked();
                    imgSixMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    llSecetDateWhole.setVisibility(View.GONE);
                } else {
                    boolSixMonth = false;
                    setAllImageViewUnchecked();
                    makeAllBoolFalse();
                }
            }
        });

        rlOneYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                if (!boolOneYear) {
                    makeAllBoolFalse();
                    boolOneYear = true;
                    filterSelectedvalue = 4;
                    growthSaveFilterModel.setSelected_date_range_filter_value(4);
                    setAllImageViewUnchecked();
                    imgOneYearCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    llSecetDateWhole.setVisibility(View.GONE);
                } else {
                    boolOneYear = false;
                    setAllImageViewUnchecked();
                    makeAllBoolFalse();
                }
            }
        });

        rlSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                if (!boolDateSelected) {
                    makeAllBoolFalse();
                    filterSelectedvalue = -1;
                    growthSaveFilterModel.setSelected_date_range_filter_value(-1);
                    setAllImageViewUnchecked();
                    imgSelectDateRange.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    boolDateSelected = true;
                    llSecetDateWhole.setVisibility(View.VISIBLE);
                } else {
                    boolDateSelected = false;
                    setAllImageViewUnchecked();
                    makeAllBoolFalse();
                    llSecetDateWhole.setVisibility(View.GONE);
                }

            }
        });

        txtFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar calendar = Calendar.getInstance();
                Date dtToday = calendar.getTime();
                DatePickerForGratitude datePickerControllerFromTextView = new DatePickerForGratitude(getActivity(), txtFromDate, myDateFormat.format(dtToday), false, "", null);
            }
        });

        txtToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat myDateFormat = new SimpleDateFormat("MM/dd/yyyy");
                Calendar calendar = Calendar.getInstance();
                Date dtToday = calendar.getTime();
                DatePickerForGratitude datePickerControllerFromTextView = new DatePickerForGratitude(getActivity(), txtToDate, myDateFormat.format(dtToday), false, "", null);
            }
        });

        rlAmProudOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolProudOf) {
                    boolProudOf = true;
                    imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolProudOf = false;
                    imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });
        rlChooseTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if(!boolIChooseToKnow){
                    boolIChooseToKnow = true;
                    imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_tick);

                }else {
                    boolIChooseToKnow = false;
                    imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }

            }
        });
        rlIHvAccomplish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolAccomplished) {
                    boolAccomplished = true;
                    imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolAccomplished = false;
                    imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlIHvObserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolObserved) {
                    boolObserved = true;
                    imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolObserved = false;
                    imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });
        rlIneedtowork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolNeedToWork) {
                    boolNeedToWork = true;
                    imgIneedtowork.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolNeedToWork = false;
                    imgIneedtowork.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });
        rlIHvLearned.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolLearned) {
                    boolLearned = true;
                    imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolLearned = false;
                    imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlIHvPraised.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolPraised) {
                    boolPraised = true;
                    imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolPraised = false;
                    imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlTodayLetGoOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolLetGoOf) {
                    boolLetGoOf = true;
                    imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolLetGoOf = false;
                    imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlDreamtOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolDreamtOf) {
                    boolDreamtOf = true;
                    imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolDreamtOf = false;
                    imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlJournalEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolJournalEntry) {
                    boolJournalEntry = true;
                    imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolJournalEntry = false;
                    imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });
        //added by jyotirmoy patra->j14
        rlGratefulEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolGratefulFor) {
                    boolGratefulFor = true;
                    imgGratefulEntry.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolGratefulFor = false;
                    imgGratefulEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });


        rlWorkingTowards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolWorkingTowards) {
                    boolWorkingTowards = true;
                    imgWorkingTowards.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolWorkingTowards = false;
                    imgWorkingTowards.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });


        rlFeelingGrowth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolFeelingGrowth) {
                    boolFeelingGrowth = true;
                    imgFeelingGrowth.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolFeelingGrowth = false;
                    imgFeelingGrowth.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlCommittingTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolCommittingTo) {
                    boolCommittingTo = true;
                    imgCommittingTo.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolCommittingTo = false;
                    imgCommittingTo.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlPromptOfTheDAy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolPromptOfTheDay) {
                    boolPromptOfTheDay = true;
                    imgPromptOfTheday.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolPromptOfTheDay = false;
                    imgPromptOfTheday.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlFoundGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolFoundGift) {
                    boolFoundGift = true;
                    imgFoundGift.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolFoundGift = false;
                    imgFoundGift.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlHappyToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolFeltHappyToday) {
                    boolFeltHappyToday = true;
                    imgHappyToday.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolFeltHappyToday = false;
                    imgHappyToday.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });

        rlIAcknowledge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolIAcknowledge) {
                    boolIAcknowledge = true;
                    imgIAcknowledge.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolIAcknowledge = false;
                    imgIAcknowledge.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }
            }
        });
        rlOneThingIappreciate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(getActivity().getBaseContext(),"Checked",Toast.LENGTH_SHORT).show();
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolIAppreciate) {
                    boolIAppreciate = true;
                    imgOneThingIappreciate.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolIAppreciate = false;
                    imgOneThingIappreciate.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }

            }
        });
        //ended by jyotirmoy patra->j14


        rlTheStory.setOnClickListener(view -> {
            globalSearchStr = "";
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
            if (!boolTheStory) {
                boolTheStory = true;
                imgTheStory.setBackgroundResource(R.drawable.mbhq_ic_tick);
            } else {
                boolTheStory = false;
                imgTheStory.setBackgroundResource(R.drawable.mbhq_ic_untick);
            }
        });

        rlChallenged.setOnClickListener(view -> {
            globalSearchStr = "";
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
            if (!boolChallenged) {
                boolChallenged = true;
                imgChallenge.setBackgroundResource(R.drawable.mbhq_ic_tick);
            } else {
                boolChallenged = false;
                imgChallenge.setBackgroundResource(R.drawable.mbhq_ic_untick);
            }
        });

        rlCelebrating.setOnClickListener(view -> {
            globalSearchStr = "";
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
            if (!boolCelebrating) {
                boolCelebrating = true;
                imgCelebrating.setBackgroundResource(R.drawable.mbhq_ic_tick);
            } else {
                boolCelebrating = false;
                imgCelebrating.setBackgroundResource(R.drawable.mbhq_ic_untick);
            }
        });

        rlLaughed.setOnClickListener(view -> {
            globalSearchStr = "";
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
            if (!boolLaughed) {
                boolLaughed = true;
                imgLaughed.setBackgroundResource(R.drawable.mbhq_ic_tick);
            } else {
                boolLaughed = false;
                imgLaughed.setBackgroundResource(R.drawable.mbhq_ic_untick);
            }
        });

        rlTodayImFeeling.setOnClickListener(view -> {
            globalSearchStr = "";
            imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
            if (!boolTodayImFeeling) {
                boolTodayImFeeling = true;
                imgTodayImFeeling.setBackgroundResource(R.drawable.mbhq_ic_tick);
            } else {
                boolTodayImFeeling = false;
                imgTodayImFeeling.setBackgroundResource(R.drawable.mbhq_ic_untick);
            }
        });

        rlRequirePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isPasswordRequired = sharedPreference.readBoolean("DOES_GROWTH_REQUIRES_PASSWORD", "");
                if (isPasswordRequired) {
                    sharedPreference.writeBoolean("DOES_GROWTH_REQUIRES_PASSWORD", "", false);
                    imgRequirePasswordCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                } else {
                    sharedPreference.writeBoolean("DOES_GROWTH_REQUIRES_PASSWORD", "", true);
                    imgRequirePasswordCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                }
            }
        });


        //added by jyotirmoy->j4

        SharedPreferences prefPic = getActivity().getSharedPreferences("showpicture", MODE_PRIVATE);
        SharedPreferences.Editor myEditPic = prefPic.edit();

        rlShowPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);

                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolShowPicture) {
                    //growthSaveFilterModel.setBool_showimage(true);
                    boolShowPicture = true;
                    myEditPic.putBoolean("picshow", true);
                    myEditPic.commit();
                    sharedPreference.write("achivementfilter", "", "yes");
                    imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                   // growthSaveFilterModel.setBool_showimage(false);
                    boolShowPicture = false;
                    myEditPic.putBoolean("picshow", false);
                    myEditPic.commit();
                    sharedPreference.write("achivementfilter", "", "");
                    imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);

                }*/
                GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolShowImg) {
                    boolShowImg = true;
                    myEditPic.putBoolean("picshow", true);
                    myEditPic.commit();

                    imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);
                } else {
                    boolShowImg = false;
                    myEditPic.putBoolean("picshow", false);
                    myEditPic.commit();

                    imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
                }

            }
        });
        //ended by jyotirmoy->j4
        rlOnlyPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalSearchStr = "";
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                if (!boolOnlyPic) {
                    boolOnlyPic = true;
                    imgOnlyPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
                } else {
                    boolOnlyPic = false;
                    imgOnlyPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }




            }
        });

        rlClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearFilterResults(growthSaveFilterModel,txtSelectDate);
                rlShowResults.performClick();
                imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                // imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
                if(boolShowImg){
                    imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
                }else {
                    imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
                }

                sharedPreference.write("achivementfilter", "", "");

                Util.achivementfilter="";
            }
        });


        dialog.show();
    }

    private void setAllTYPEImageViewUnchecked() {

        imgAmProudOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgIHvAccomplish.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgIHvObserve.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgIHvLearned.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgIHvPraised.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgTodayLetGoOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgDreamtOf.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgJournalEntry.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgChooseTo.setBackgroundResource(R.drawable.mbhq_ic_untick);



    }

    private void makeAllTYPEBoolFalse() {
        boolShowImg=false;
        boolOnlyPic=false;
        boolIChooseToKnow = false;
        boolProudOf = false;
        boolAccomplished = false;
        boolNeedToWork = false;
        boolObserved = false;
        boolLearned = false;
        boolPraised = false;
        boolLetGoOf = false;
        boolDreamtOf = false;
        boolJournalEntry = false;
        boolFeelingGrowth = false;
        boolPromptOfTheDay = false;
        boolCommittingTo = false;
        boolFoundGift = false;
        boolFeltHappyToday = false;
        boolIAcknowledge = false;
        boolGratefulFor = false;
        boolWorkingTowards = false;
        boolTheStory = false;
        boolChallenged = false;
        boolCelebrating = false;
        boolLaughed = false;
        boolTodayImFeeling = false;
        boolIAppreciate = false;


    }

    private List<MyAchievementsListInnerModel> performDateRangeSearch(String fromDate, String toDate) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date dtFrom = null;
        Date dtTo = null;

        try {
            dtFrom = simpleDateFormat.parse(fromDate);
            dtFrom.setHours(0);
            dtFrom.setMinutes(0);
            dtFrom.setSeconds(0);

            dtTo = simpleDateFormat.parse(toDate);
            dtTo.setHours(0);
            dtTo.setMinutes(0);
            dtTo.setSeconds(0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        List<MyAchievementsListInnerModel> lstSearchResult = new ArrayList<>();
        for (int i = 0; i < lstShowAll.size(); i++) {
            try {
                Date dtGratitude = format.parse(lstShowAll.get(i).getCreatedAt());
                dtGratitude.setHours(0);
                dtGratitude.setMinutes(0);
                dtGratitude.setSeconds(0);

                if (dtGratitude.equals(dtFrom) || dtGratitude.equals(dtTo) || (dtGratitude.after(dtFrom) && dtGratitude.before(dtTo))) {
                    lstSearchResult.add(lstShowAll.get(i));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        //loadAdapter(lstSearchResult);
        return lstSearchResult;
    }


    private void makeAllBoolFalse() {
        boolDateSelected = false;
        boolToday = false;
        boolThisMonth = false;
        boolSixMonth = false;
        boolOneYear = false;
        boolPicture = false;
    }

    private void setAllImageViewUnchecked() {

        imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgTodayCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgThisMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_untick); /////
        imgSixMonthCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgOneYearCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgSelectDateRange.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgContainPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);

    }


    @Override
    public void onPause() {
        super.onPause();

        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){

            }else{
                LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                llTabView.setVisibility(View.GONE);
                Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
                toolbar.setNavigationIcon(null);
            }
        }else{
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.GONE);
            Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
            toolbar.setNavigationIcon(null);
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if("3".equalsIgnoreCase(accesstype)){
            if("false".equalsIgnoreCase(eq_access)){
                // return null;
                // ((MainActivity) getActivity()).loadFragment(new MbhqTodayTwoFragment(), "MbhqTodayTwo", null);
            }else{
                ((MainActivity) getActivity()).funDrawer1();
                Log.e("print","text3");

                Log.i("MyAchievement", "onresume");

                String isInserted = sharedPreference.read("isInserted", "");
                Log.e(TAG, "onResume: " + isInserted);

                if (isInserted.equals("yes")) {
                    sharedPreference.write("isInserted", "", "no");
                } else {

                }

                Log.i("MyAchievement", "onresume 2270");
                LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
                llTabView.setVisibility(View.GONE);
                //((MainActivity) getActivity()).funDrawer();//commented by jyoti


                getView().setFocusableInTouchMode(true);
                getView().requestFocus();
                getView().setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {

                        if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                            // handle back button
                            ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);

                            return true;

                        }

                        return false;
                    }
                });
            }
        }else{
            ((MainActivity) getActivity()).funDrawer1();
            Log.e("print","text3");

            Log.i("MyAchievement", "onresume");

            String isInserted = sharedPreference.read("isInserted", "");
            Log.e(TAG, "onResume: " + isInserted);

            if (isInserted.equals("yes")) {
                sharedPreference.write("isInserted", "", "no");
            } else {

            }

            Log.i("MyAchievement", "onresume 2270");
            LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
            llTabView.setVisibility(View.GONE);
           // ((MainActivity) getActivity()).funDrawer();//commented by jyoti


            getView().setFocusableInTouchMode(true);
            getView().requestFocus();
            getView().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {

                    if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                        // handle back button
                        ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);

                        return true;

                    }

                    return false;
                }
            });
        }



    }

//    private void startGratitudeService() {
//        Intent intent = new Intent(getActivity(), MyGratitudeService.class);
//        // intent.putExtra("UserID", userID);
//        // intent.putExtra("userSessionID", userSessionID);
//        Objects.requireNonNull(getActivity()).startService(intent);
//
//    }//commented by jyoti


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
        imgRightBack.setVisibility(View.GONE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        if (new SharedPreference(getActivity()).read("IsNonSubscribeUser", "").equals("true")) {
            if (Util.isSevenDayOver(getActivity())) {
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

        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
            }
        });
    }

    public void getSelectedAchievement(Integer ID, String openNotification, Boolean prevSetNotification) {

        if (Util.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ReverseBuckeId", ID);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<IndividualAchievementModel> achievementModelCall = finisherService.selectAchievement(hashMap);
            achievementModelCall.enqueue(new Callback<IndividualAchievementModel>() {
                @Override
                public void onResponse(Call<IndividualAchievementModel> call, Response<IndividualAchievementModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().getSuccessFlag()) {
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            bundle.putString("GRATITUDESTATUS", "EDIT");
                            bundle.putString("GRATITUDEDATA", gson.toJson(response.body().getDetails()));
                            bundle.putString("GRATITUDE_PIC", response.body().getDetails().getPicture()); /////////////////////////////////////////
                            Log.e("PRINT_GROWTH", gson.toJson(response.body().getDetails()) + ">>>>");
                            String OPEN_NOTIFICATION="FALSE";
                            String PREVIOUS_SET_NOTIFICATION="FALSE";
                            if (openNotification.equals("TRUE")) {
                                OPEN_NOTIFICATION="TRUE";
                                bundle.putString("OPEN_NOTIFICATION", "TRUE");
                            }
                            if (prevSetNotification) {
                                PREVIOUS_SET_NOTIFICATION="TRUE";
                                bundle.putString("PREVIOUS_SET_NOTIFICATION", "TRUE");
                            } else {
                                PREVIOUS_SET_NOTIFICATION="FALSE";
                                bundle.putString("PREVIOUS_SET_NOTIFICATION", "FALSE");
                            }
                            // ((MainActivity) getActivity()).loadFragment(new MyAchievementsListAddEditFragment(), "MyAchievementsListAddEdit", bundle);
                            openeditdialog="yes";
                            openDialogForAchieveAddEdit("EDIT",response.body().getDetails(),response.body().getDetails().getPicture(),OPEN_NOTIFICATION,PREVIOUS_SET_NOTIFICATION);
                        }
                    }


                }

                @Override
                public void onFailure(Call<IndividualAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-11");
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    public void deleteGratitude(Integer growth_Id) {
        new MyAsyncTask1().execute();
        if (Util.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ReverseBucketID", growth_Id);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.deleteAchievement(hashMap);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.isReloadTodayMainPage = true;

                            Completable.fromAction(() -> {
                                        gratitudeViewModel.deleteGrowthById(growth_Id);
                                    }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.e("GROWTH_DELETE", "TRUE" + ">>>>>");
                                        Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                                    }, throwable -> {
                                        Log.e("GROWTH_DELETE", "FALSE" + ">>>>>");
                                        Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                                    });
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();


                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-10");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }


    public void setimage() {

        SharedPreferences prefPic = getActivity().getSharedPreferences("showpicture", MODE_PRIVATE);
        SharedPreferences.Editor myEditPic = prefPic.edit();

       /* globalSearchStr = "";
        // imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        if (!boolShowPicture) {
            boolShowPicture = true;
            myEditPic.putBoolean("picshow", true);
            myEditPic.commit();

        } else {
            boolShowPicture = false;
            myEditPic.putBoolean("picshow", false);
            myEditPic.commit();

        }
*/

        globalSearchStr = "";
        // imgShowAllCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        if (!boolShowImg) {
            boolShowImg = true;
            myEditPic.putBoolean("picshow", true);
            myEditPic.commit();

            // imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_untick);
        } else {
            boolShowImg = false;
            myEditPic.putBoolean("picshow", false);
            myEditPic.commit();

            // imgShowPicture.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }

    }



    public void populateList(ArrayList<MyAchievementsListInnerModel> lstShowAll) {
        Log.e(TAG, "populateList: " + lstShowAll.size());
        Date dtToday = null, dtThisMonth = null, dtThreeMonth = null, dtOneYear = null, dtYesterDay = null;

        Calendar calendar = Calendar.getInstance();
        dtToday = calendar.getTime();
        dtToday.setHours(0);
        dtToday.setMinutes(0);
        dtToday.setSeconds(0);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(Calendar.DAY_OF_MONTH, 1);
        dtThisMonth = calendar1.getTime();
        dtThisMonth.setHours(0);
        dtThisMonth.setMinutes(0);
        dtThisMonth.setSeconds(0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.add(Calendar.DATE, -90);
        dtThreeMonth = calendar2.getTime();
        dtThreeMonth.setHours(0);
        dtThreeMonth.setMinutes(0);
        dtThreeMonth.setSeconds(0);

        Calendar calendar3 = Calendar.getInstance();
        calendar3.add(Calendar.DATE, -365);
        dtOneYear = calendar3.getTime();
        dtOneYear.setHours(0);
        dtOneYear.setMinutes(0);
        dtOneYear.setSeconds(0);

        Calendar calendar4 = Calendar.getInstance();
        calendar4.add(Calendar.DATE, -1);
        dtYesterDay = calendar4.getTime();
        dtYesterDay.setHours(0);
        dtYesterDay.setMinutes(0);
        dtYesterDay.setSeconds(0);


        for (int i = 0; i < lstShowAll.size(); i++) {
            if (lstShowAll.get(i).getCreatedAt() != null && !lstShowAll.get(i).getCreatedAt().equals("")) {

                Date dtCreatedAt = null;
                try {
                    dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                    String strCreatedAt = "";
                    String strTodayDate = "";
                    String strYesterDayDate = "";

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                    strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                    strTodayDate = strDtaeFormat.format(dtToday);
                    strYesterDayDate = strDtaeFormat.format(dtYesterDay);

                    if (strCreatedAt.equals(strTodayDate)) {
                        lstShowAll.get(i).setDateValue("Today");
                    } else if (strCreatedAt.equals(strYesterDayDate)) {
                        lstShowAll.get(i).setDateValue("Yesterday");
                    } else {
                        lstShowAll.get(i).setDateValue(simpleDateFormat.format(format.parse(lstShowAll.get(i).getCreatedAt())));
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                try {
                    dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                    String strCreatedAt = "";
                    String strTodayDate = "";

                    strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                    strTodayDate = strDtaeFormat.format(dtToday);

                    if (strCreatedAt.equals(strTodayDate)) {
                        lstToday.add(lstShowAll.get(i));
                    }


                    if ((dtCreatedAt.after(dtThisMonth) || dtCreatedAt.equals(dtThisMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                        lstThisMonth.add(lstShowAll.get(i));
                    }

                    if ((dtCreatedAt.after(dtThreeMonth) || dtCreatedAt.equals(dtThreeMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                        lstThreeMonthsAgo.add(lstShowAll.get(i));
                    }

                    if ((dtCreatedAt.after(dtOneYear) || dtCreatedAt.equals(dtOneYear)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                        lstOneYearAgo.add(lstShowAll.get(i));
                    }

                    if (lstShowAll.get(i).getAchievement().contains("I am proud of")) {
                        lstProudOf.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I've accomplished")) {
                        lstAccomplish.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I've observed")) {
                        lstObserved.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I've learned")) {
                        lstLearned.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I've praised")) {
                        lstPraised.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("Today I've let go of")) {
                        lstTodayletGoOf.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I dreamt of")) {
                        lstDreamtOf.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("Journal Entry")) {
                        lstJournalEntry.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("The story I've told myself is")) {
                        lstTheStory.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I've been challenged by")) {
                        lstChallengedBy.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("A small win I'm celebrating is")) {
                        lstCelebrating.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("I laughed")) {
                        lstLaughed.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getAchievement().contains("Today I'm Feeling")) {
                        lstImFeeling.add(lstShowAll.get(i));
                    }
                    if (lstShowAll.get(i).getPicture() != null && !lstShowAll.get(i).getPicture().isEmpty()) {
                        lstPicture.add(lstShowAll.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }



        try {
            GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);

            boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
            boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
            boolShowImg = growthSaveFilterModel.getBool_show_img();


            boolProudOf = growthSaveFilterModel.getBool_proud_of();
            boolAccomplished = growthSaveFilterModel.getBool_accomplished();
            boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
            boolObserved = growthSaveFilterModel.getBool_observed();
            boolLearned = growthSaveFilterModel.getBool_learned();
            boolPraised = growthSaveFilterModel.getBool_praised();
            boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
            boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
            boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

            boolGratefulFor = growthSaveFilterModel.getBool_grateful_for(); //added by jyotirmoy->j14
            boolWorkingTowards = growthSaveFilterModel.getBool_working_towards(); //added by jyotirmoy->j14
            boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth(); //added by jyotirmoy->j14
            boolCommittingTo = growthSaveFilterModel.getBool_committing_to(); //added by jyotirmoy->j14
            boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day(); //added by jyotirmoy->j14
            boolFoundGift = growthSaveFilterModel.getBool_found_gift(); //added by jyotirmoy->j15
            boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today(); //added by jyotirmoy->j15
            boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge(); //added by jyotirmoy->j15
            boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();



            boolTheStory = growthSaveFilterModel.getBool_the_story();
            boolChallenged = growthSaveFilterModel.getBool_challenged();
            boolCelebrating = growthSaveFilterModel.getBool_celebrating();
            boolLaughed = growthSaveFilterModel.getBool_laughed();
            boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
            globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
            filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();


        } catch (Exception e) {
            e.printStackTrace();
        }


        haveGrowthMinimumOne = true;
        txtLoading.setVisibility(View.GONE);
        imgBigPlus.setVisibility(View.GONE);

        funRefresh();
    }

    @Override
    public void onStop() {
        super.onStop();
        mDisposable.dispose();
    }

    private void checkGratitudeCacheExpiration() {
        Log.i("check_eq","1");
        if (Connection.checkConnection(getContext())) {

            HashMap<String, Object> hashReq = new HashMap<>();
            //hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));

            FinisherServiceImpl finisherService = new FinisherServiceImpl(getContext(),sharedPreference.read("jwt", ""));
            Call<GetGratitudeCacheExpiryTimeResponse> serverCall = finisherService.GetGratitudeCacheExpiryTime(hashReq);
            serverCall.enqueue(new Callback<GetGratitudeCacheExpiryTimeResponse>() {
                @Override
                public void onResponse(Call<GetGratitudeCacheExpiryTimeResponse> call, Response<GetGratitudeCacheExpiryTimeResponse> response) {
                    //progressDialog.dismiss();
                    GetGratitudeCacheExpiryTimeResponse responseBody = response.body();
                    if (responseBody != null && responseBody.isSuccessFlag()) {
                        try {

                            if("2020-1-1 00:00:00".equalsIgnoreCase(sharedPreference.read("GRATITUDE_EXPIRATION_DATE_TIME", ""))){
                                sharedPreference.write("GRATITUDE_EXPIRATION_DATE_TIME", "", responseBody.getEqJournalExpiryDateTime());
                            }
                            Log.i("check_eq","2");
                            ///////////////////
                            getAchievementsFromDB(position, count);

                        } catch (Exception e) {
                            Log.i("check_eq","0");
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(Call<GetGratitudeCacheExpiryTimeResponse> call, Throwable t) {
                    //progressDialog.dismiss();
                    Log.i("check_eq","3");
                    t.printStackTrace();

                }
            });

        }else{
            Log.i("check_eq","4");
            pagenation_from_api=false;
            getAchievementsFromDB(position, count);
        }

    }

    private void calldatabase_nointernate(int position_, int count_) {
        Log.i("check_eq","33");
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();
        mDisposable.add(
                gratitudeViewModel1.getAllAchive(position_,count_)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                achievements -> {
                                    Log.e(TAG, "getAchievementsFromDB0:" + achievements.size());

                                    if (achievements.size() == 0) {
                                        Log.i("check_eq","34");
                                        // Util.showToast(getActivity(), "no data in database");
                                        Log.e(TAG, "getAchievementsFromDB1:" + achievements.size());

                                        txtLoading.setVisibility(View.GONE);
                                        imgBigPlus.setVisibility(View.GONE);
                                        txtLoading.setText("no data found");

                                    } else {
                                        Log.i("check_eq","35");
                                        // Util.showToast(getActivity(), "data in database");
                                        position = position_;
                                        count = count_;
                                        pagenation_from_api = false;

                                        Log.e(TAG, "getAchievementsFromDB2:" + achievements.size());

                                        lstShowAll.addAll(achievements);
                                        populateList(lstShowAll);
                                    }
                                },
                                Throwable::getMessage
                        )
        );
    }


    private void getAchievementsFromDB(int position_, int count_) {
        Log.i("check_eq","14");

        txtLoading.setVisibility(View.GONE);
        txtLoading.setText("loading...");

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        String gratitudeExpirationTime = sharedPreference.read("GRATITUDE_EXPIRATION_DATE_TIME", "");
        String gratitudeInsertionTime = sharedPreference.read("GRATITUDE_INSERTION_DATE_TIME", "");

        Log.i(TAG, "GRATITUDE_EXPIRATION_DATE_TIME => " + gratitudeExpirationTime);
        Log.i(TAG, "GRATITUDE_INSERTION_DATE_TIME => " + gratitudeInsertionTime);
        Log.i(TAG, "shouldGratitudeRenew" + "false");
        boolean shouldGratitudeRenew = false;//false;

        try {
            if ("2020-1-1 00:00:00".equalsIgnoreCase(gratitudeExpirationTime)
                // !dateFormatter.parse(gratitudeExpirationTime).after(dateFormatter.parse(gratitudeInsertionTime))
            ) {
                shouldGratitudeRenew = false;//true;
                Log.i(TAG, "shouldGratitudeRenew" + "false");
            }else{
                shouldGratitudeRenew = true;
                Log.i(TAG, "shouldGratitudeRenew" + "true");
            }

        } catch (Exception ex) {
            Log.i(TAG, "shouldGratitudeRenew" + "nill");
            ex.printStackTrace();
        }
        if (Connection.checkConnection(requireContext())){
            if (!shouldGratitudeRenew) {
                Log.i("check_eq","15");
                Log.i(TAG, "shouldGratitudeRenew0" + shouldGratitudeRenew);
                lstShowAll.clear();
                lstToday.clear();
                lstThisMonth.clear();
                lstThreeMonthsAgo.clear();
                lstOneYearAgo.clear();
                lstProudOf.clear();
                lstAccomplish.clear();
                lstObserved.clear();
                lstLearned.clear();
                lstPraised.clear();
                lstTodayletGoOf.clear();
                lstDreamtOf.clear();
                lstJournalEntry.clear();
                lstTheStory.clear();
                lstChallengedBy.clear();
                lstCelebrating.clear();
                lstLaughed.clear();
                lstImFeeling.clear();
                lstPicture.clear();
                Log.i("check_eq","150");
        mDisposable.add(
                gratitudeViewModel1.getAllAchive(position_,count_)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                achievements -> {
                                    Log.e(TAG, "getAchievementsFromDB0:" + achievements.size());

                                    if (achievements.size() == 0) {
                                        Log.i("check_eq","16");
                                        pagenation_from_api = true;
                                        Log.e(TAG, "getAchievementsFromDB1:" + achievements.size());
                                            new MyAsyncTask().execute();
                                            getAchievementsList(Page);


                                    } else {
                                        Log.i("check_eq","17");
                                        position = position_;
                                        count = count_;
                                        pagenation_from_api = false;

                                        Log.e(TAG, "getAchievementsFromDB2:" + achievements.size());

                                        lstShowAll.addAll(achievements);
                                          populateList(lstShowAll);
                                    }
                                },
                                Throwable::getMessage
                        )
        );
               /* pagenation_from_api = true;
                new MyAsyncTask().execute();
                getAchievementsList(Page);*/


            } else {
                Log.i("check_eq","18");
                Log.e(TAG, "getAchievementsFromDB3:" + 0);
                sharedPreference.write("GRATITUDE_EXPIRATION_DATE_TIME", "", "2020-1-1 00:00:00");
                pagenation_from_api = true;
                new MyAsyncTask1().execute();
                getAchievementsList_(Page);

            }
        }else{
            Log.i("check_eq","180");
        }



    }


    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // This method runs in the background thread
            // You can perform any background tasks here
            Log.e(TAG, "calleddelete:" + "yes");
            getAchievementsListAndInsertToDB();

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //  getAchievementsList(1);
            Log.e("saved in database", "yes");

            // This method is called after the background task is completed
            // You can update the UI or do any post-processing here
        }
    }

    private class MyAsyncTask1 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // This method runs in the background thread
            // You can perform any background tasks here
            Log.e(TAG, "calleddelete:" + "yes");
            Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(getActivity()).gratitudeDao().deleteAllGratitudeNew());



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //  getAchievementsList(1);
            Log.e("saved in database", "yes");

            // This method is called after the background task is completed
            // You can update the UI or do any post-processing here
        }
    }
    private class MyAsyncTask3 extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // This method runs in the background thread
            // You can perform any background tasks here
            Log.e(TAG, "calleddelete:" + "yes");
            Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(getActivity()).gratitudeDao().deleteAllGratitudeNew());



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //  getAchievementsList(1);
            Log.e("saved in database", "yes");

            pagenation_from_api=true;
            openeditdialog="";
            ((MainActivity) getActivity()).refershGamePoint(getActivity());
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
            Util.isReloadTodayMainPage = true;
            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

        }
    }
    private void getAchievementsListAndInsertToDB() {

        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();


        HashMap<String, Object> hashReq = new HashMap<>();
        hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
        hashReq.put("Key", Util.KEY);
        hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
        // hashReq.put("PageNo", 1);
        //hashReq.put("Count", 50);

        Call<MyAchievementsListModel> getGratitudeListModelCall = finisherService.getMyAchievevmentsList(hashReq);
        getGratitudeListModelCall.enqueue(new Callback<MyAchievementsListModel>() {
            @SuppressLint("CheckResult")
            @Override
            public void onResponse(Call<MyAchievementsListModel> call, Response<MyAchievementsListModel> response) {
                // progressDialog.dismiss();

                if (response.body() != null) {
                    if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {

//                            haveGrowthMinimumOne = true;
                        lstShowAll_db = response.body().getDetails();
                        Log.e(TAG, "onResponse_for db:" + lstShowAll_db);
                        //  sharedPreference.write("onResponse_for db:", "", lstShowAll_db.toString());

                        total_result_db = response.body().getTotalCount();
                        Log.i("total_result_db", String.valueOf(total_result_db));
                        sharedPreference.write("total_result_db", "", String.valueOf(total_result_db));

                        total_page_db = response.body().getTotalCount() / 20;

                        remaining_db = response.body().getTotalCount() % 20;
                        Log.i("remaining_db", String.valueOf(remaining_db));
                        sharedPreference.write("remaining_db", "", String.valueOf(remaining_db));

                        if (remaining_db > 0) {
                            total_page_db = total_page_db + 1;
                        } else {
                        }
                        Log.i("total_page_db", String.valueOf(total_page_db));
                        sharedPreference.write("total_page_db", "", String.valueOf(total_page_db));

                        CurrentPage_db = Page_db;
                        sharedPreference.write("CurrentPage_db", "", String.valueOf(CurrentPage_db));
                        txtLoading.setVisibility(View.GONE);

                        ///////////////////////////////////////////////////////////////////////////////////////
                        List<GratitudeEntity> gratitudeEntities = new ArrayList<>();

                        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
                        Calendar calendar = Calendar.getInstance();
                        String todatDate = simpleDateFormat1.format(calendar.getTime());
                        Gson gson = new Gson();
                        //  String gratitudeModel = gson.toJson(getGratitudeListModelInner);
                        String timeStamp = String.valueOf(calendar.getTimeInMillis());
                        // GratitudeEntityNew gratitudeEntityNew;

                        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
                        String formattedDate = dateFormatter.format(new Date());
                        Log.i(TAG, "Gratitude insert time => " + formattedDate);
                        sharedPreference.write("GRATITUDE_INSERTION_DATE_TIME", "", formattedDate);
                        Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(getActivity()).gratitudeDao().insertAchievements(lstShowAll_db));

                        ///////////////////////////////////////////////////////////////////////////////////////
                    } else {

                    }

                }
            }

            @Override
            public void onFailure(Call<MyAchievementsListModel> call, Throwable t) {
                // progressDialog.dismiss();
            }
        });


    }


    private void getAchievementsList_loadmore_for_db(int position, int count) {


        loadImageJournal.setVisibility(View.GONE);
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();

        if (Connection.checkConnection(getActivity())) {
            mDisposable.add(
                    gratitudeViewModel1.getAllAchive(position, count)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    achievements -> {
                                        Log.e(TAG, "getAchievementsFromDB0:" + achievements.size());

                                        //  Log.e(TAG, "getAchievementsFromDB1: " + achievements.size());
                                        if (achievements.size() == 0) {
                                            Log.e(TAG, "getAchievementsFromDB1:" + achievements.size());

                                        } else {
                                            Log.e(TAG, "getAchievementsFromD2db2:" + lstShowAll.size());
                                            lstShowAll.addAll(achievements);
                                            populateList(lstShowAll);
                                        }
                                    },
                                    Throwable::getMessage
                            )
            );


        } else {
            Log.e("callnonetwork", "callnonetwork-9");
            mDisposable.add(
                    gratitudeViewModel1.getAllAchive(position, count)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    achievements -> {
                                        Log.e(TAG, "getAchievementsFromDB110:" + achievements.size());

                                        if (achievements.size() == 0) {

                                            Log.e(TAG, "getAchievementsFromDB111:" + achievements.size());
                                        } else {
                                            Log.e(TAG, "getAchievementsFromD2db112:" + lstShowAll.size());
                                            lstShowAll.addAll(achievements);
                                            populateList(lstShowAll);
                                        }
                                    },
                                    Throwable::getMessage
                            )
            );


        }

    }
    /*edit activities*/
    private boolean isBlankOrSpecialCharacter(String input) {
        // Define your validation pattern here
        return input.matches("^[\\s!@#$%^&*(),.?\":{}|<>]*$"); // Matches blank or only special characters
    }
    public void openDialogForAchieveAddEdit(String gratitudeStatus,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel,String GRATITUDE_PIC,String OPEN_NOTIFICATION,  String PREVIOUS_SET_NOTIFICATION) {
        int userfolderid_=globalmMyAchievementsListInnerModel.getUserFolderId();
        Log.i("userfolderid",String.valueOf(userfolderid_));
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_add1);

        dialog.setCancelable(false);
        TextView txtGoalValue1 = dialog.findViewById(R.id.txtGoalValue1);
        TextView txtGoalValue2 = dialog.findViewById(R.id.txtGoalValue2);
        TextView txtGoalValue3 = dialog.findViewById(R.id.txtGoalValue3);
        TextView txtSetReminder = dialog.findViewById(R.id.txtSetReminder);
        TextView txtUploadChangeImage = dialog.findViewById(R.id.txtUploadChangeImage);
        edtGratitudeName = dialog.findViewById(R.id.edtGratitudeName);
        TextView editGratitudeOne = dialog.findViewById(R.id.editGratitudeOne);
        EditText edtNotes = dialog.findViewById(R.id.edtNotes);
        ImageView imgEdit =dialog.findViewById(R.id.imgEdit);
        RelativeLayout rlSong = dialog.findViewById(R.id.rlSong);
        imgGratitudeMain = dialog.findViewById(R.id.imgGratitudeMain);
        ImageView imgDeleteIcon = dialog.findViewById(R.id.imgDeleteIcon);
        ImageView imgCopy = dialog.findViewById(R.id.imgCopy);
        imgSaveGratitude =dialog.findViewById(R.id.imgSaveGratitude);
        ImageView imgDeleteGratitude = dialog.findViewById(R.id.imgDeleteGratitude);
        RelativeLayout rlChangeImage =dialog.findViewById(R.id.rlChangeImage);
        RelativeLayout rlEditCopy = dialog.findViewById(R.id.rlEditCopy);
        RelativeLayout rlSetReminder = dialog.findViewById(R.id.rlSetReminder);
        LinearLayout llSaveDelete = dialog.findViewById(R.id.llSaveDelete);
        LinearLayout llChangeImageDelete = dialog.findViewById(R.id.llChangeImageDelete);
        chkSetReminder = dialog.findViewById(R.id.chkSetReminder);
        Spinner spCategory = dialog.findViewById(R.id.spCategory);
        //////////////song///////
        TextView txtSongBtn = dialog.findViewById(R.id.txtSongBtn);
        ImageView txtBack = dialog.findViewById(R.id.txtBack);
        EditText edtMyAchievement = dialog.findViewById(R.id.edtMyAchievement);
        RelativeLayout rlUpload = dialog.findViewById(R.id.rlUpload);
        rlSaveAchievement = dialog.findViewById(R.id.rlSaveAchievement);
        RelativeLayout rlDeleteAchievement = dialog.findViewById(R.id.rlDeleteAchievement);

        imgJournalLoadingBar = dialog.findViewById(R.id.imgJournalLoadingBar);

        imgGratitudeMainCard = dialog.findViewById(R.id.imgGratitudeMainCard);
        fram_= dialog.findViewById(R.id.fram_);
        //added by jyotirmoy patra
        ImageView editPopupBtn = dialog.findViewById(R.id.editPopupBtn);
        RelativeLayout editLayout = dialog.findViewById(R.id.editLayout);
        LinearLayout buttonPanelEditmode= dialog.findViewById(R.id.buttonPanelEditmode);
        ImageView clickPhoto = dialog.findViewById(R.id.clickPhoto);
        ImageView galleryPhoto = dialog.findViewById(R.id.galleryPhoto);
        ImageView chooseDate = dialog.findViewById(R.id.chooseDate);
        TextView dateShow = dialog.findViewById(R.id.dateShow);
        Util.disableTextField(edtGratitudeName);

        ArrayList<UserEqFolder> myData = Util.myModelArrayList;
        SharedPreferences prefs = getActivity().getPreferences(Context.MODE_PRIVATE);
        String json= sharedPreference.read("my_list_eqfolder", "");
        ArrayList<UserEqFolder> habitSwapList_ = new Gson().fromJson(json, new TypeToken<ArrayList<UserEqFolder>>(){}.getType());

        for(int i=0;i<habitSwapList_.size();i++){
            Integer folder=habitSwapList_.get(i).getUserEqFolderId();
            if(userfolderid_==folder){
                foldernametoshow=habitSwapList_.get(i).getFolderName();
                userfolderid=userfolderid_;
                habitSwapList_.get(i).setIsDefaultView(true);
                // break;
            }else{
                habitSwapList_.get(i).setIsDefaultView(false);
            }
        }

        RecyclerView rl_folder_list=dialog.findViewById(R.id.rl_F1);
        TextView tv_selectfolder=dialog.findViewById(R.id.tv_selectfolder);
        if(null!=habitSwapList_ ) {

            //  rl_folder.setVisibility(View.VISIBLE);

            if ( habitSwapList_.size() > 0 ) {
                //  rl_folder.setVisibility(View.VISIBLE);
                rl_folder_list.setLayoutManager(new LinearLayoutManager(getActivity())); // You can use GridLayoutManager or others for different layouts
                adapter1 = new MyAdapter1(getActivity(),new OnItemClickListener(){
                    @Override
                    public void onItemClick_add(int p,int folderid, String filename, Boolean check) {

                        //filename1=String.valueOf(folderid);
                        for(int i=0;i<habitSwapList_.size();i++){
                            if(p==i){
                                habitSwapList_.get(i).setIsDefaultView(true);
                            }else{
                                habitSwapList_.get(i).setIsDefaultView(false);
                            }

                        }
                        userfolderid=folderid;
                        moveEQFolder(folderid,globalmMyAchievementsListInnerModel.getId());
                        adapter1.notifyDataSetChanged();

                        Toast.makeText(getActivity(),filename,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onItemClick_remove(int p,int folderidrmv, String filename, Boolean check) {
                        //  Log.i("clicked_________folderremove",String.valueOf(check) +" ->>  "+ String.valueOf(folderidrmv));


                    }

                },habitSwapList_); // Provide your data list
                rl_folder_list.setAdapter(adapter1);
            }else{
                rl_folder_list.setVisibility(View.GONE);
            }
        }else{
            rl_folder_list.setVisibility(View.GONE);
        }




        //  globalmMyAchievementsListInnerModel = globalmMyAchievementsListInnerModel;
        if (globalmMyAchievementsListInnerModel.getSong() != null)
            songPathSelect = globalmMyAchievementsListInnerModel.getSong();

        SetLocalNotificationForAchievement.setNotificationForAchievement(globalmMyAchievementsListInnerModel, getActivity());

        // gratitudeStatus = gratitudeStatus

        PREVIOUS_SET_NOTIFICATION1 = PREVIOUS_SET_NOTIFICATION;
        OPEN_NOTIFICATION1=OPEN_NOTIFICATION;

        if(GRATITUDE_PIC.equalsIgnoreCase("") ||   null== GRATITUDE_PIC){
            // edtGratitudeName.setMinLines(30);
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            int screenHeight = display.getHeight();

            edtGratitudeName.setMaxHeight(screenHeight - 400);
            imgGratitudeMainCard.setVisibility(View.GONE);
            fram_.setVisibility(View.GONE);
        }else {


            edtGratitudeName.setMaxHeight(800);//////////////////////////////
            imgGratitudeMainCard.setVisibility(View.VISIBLE);
            fram_.setVisibility(View.VISIBLE);

        }



        edtGratitudeName.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                if (edtGratitudeName.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }

                }

                if(v.getId() == R.id.edtGratitudeName){
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK){
                        case MotionEvent.ACTION_UP:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            break;
                    }

                }
                return false;
            }
        });

        clickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission() && hasGalleryPermission()) {

                        openCam();

                    } else {
//                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 203);
//                        }
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.CAMERA}, 203);
                        }else{
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 203);
                        }
                    }

                } else {
                    openCam();
                }
                rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            }
        });


        galleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasGalleryPermission()) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    } else {
                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 200);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                            }
                        }
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                }
                rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            }
        });

        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerControllerFromTextView(getActivity(), dateShow, "", false);
                rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                somethingChanges = true;
            }
        });

        editPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogEdit = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialogEdit.setContentView(R.layout.myachievement_edit_dialog);
                dialogEdit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                RelativeLayout editAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.editAchievement);
                RelativeLayout rl_folder=(RelativeLayout) dialogEdit.findViewById(R.id.rl_folder);
                TextView tv_foldername=(TextView) dialogEdit.findViewById(R.id.tv_foldername);
                rl_folder.setVisibility(View.VISIBLE);
                tv_foldername.setText(foldernametoshow);
                rl_folder.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editLayout.setVisibility(View.VISIBLE);
                        buttonPanelEditmode.setVisibility(View.VISIBLE);
                        rl_folder_list.setVisibility(View.VISIBLE);
                        tv_selectfolder.setVisibility(View.VISIBLE);
                        Util.enableTextField(edtGratitudeName);
                        editEnable = true;
                        edtGratitudeName.setBackground(getResources().getDrawable(R.drawable.edittextjournal_shape));
                        edtGratitudeName.setMinLines(10);
                        edtGratitudeName.setFocusable(true);
                        dialogEdit.dismiss();
                    }
                });
                editAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editLayout.setVisibility(View.VISIBLE);
                        buttonPanelEditmode.setVisibility(View.VISIBLE);
                        Util.enableTextField(edtGratitudeName);
                        rl_folder_list.setVisibility(View.GONE);
                        tv_selectfolder.setVisibility(View.GONE);
                        editEnable = true;
                        edtGratitudeName.setBackground(getResources().getDrawable(R.drawable.edittextjournal_shape));
                        edtGratitudeName.setMinLines(10);
                        edtGratitudeName.setFocusable(true);
                        dialogEdit.dismiss();


                    }
                });


                RelativeLayout shareAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.shareAchievement);
                if (editGratitudeOne.getText().toString().contains("Grateful")) {
                    shareAchievement.setVisibility(View.VISIBLE);
                } else {
                    shareAchievement.setVisibility(View.GONE);
                }
                shareAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rl_folder_list.setVisibility(View.GONE);
                        tv_selectfolder.setVisibility(View.GONE);
                        if (globalmMyAchievementsListInnerModel != null) {

                            openDialogForGratitudeShare1(edtGratitudeName.getText().toString(), globalmMyAchievementsListInnerModel.getId(),dialog,globalmMyAchievementsListInnerModel);
                            Log.e("journalId", "id>>>>" + globalmMyAchievementsListInnerModel.getId());
                        }
                        dialogEdit.dismiss();
                    }
                });

                RelativeLayout deleteAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.deleteAchievement);
                deleteAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        rl_folder_list.setVisibility(View.GONE);
                        tv_selectfolder.setVisibility(View.GONE);
                        rlDeleteAchievement.performClick();
                        dialogEdit.dismiss();
                    }
                });

                ImageView editDialogClose = (ImageView) dialogEdit.findViewById(R.id.editDialogClose);
                editDialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEdit.dismiss();
                    }
                });

                LinearLayout dialogLayout = (LinearLayout) dialogEdit.findViewById(R.id.dialogLayout);
                dialogLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEdit.dismiss();
                    }
                });


                dialogEdit.show();
            }
        });
        edtNotes.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                makeSaveButtonGreen();
                return false;
            }
        });

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_folder_list.setVisibility(View.GONE);
                tv_selectfolder.setVisibility(View.GONE);
                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                if (somethingChanges) {
                    final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {
                            rlSaveAchievement.performClick();

                        }

                        @Override
                        public void onCancel(String title) {
                            openeditdialog="";
                            dialog.dismiss();
                        }
                    });
                } else {
                    openeditdialog="";
                    dialog.dismiss();
                }
            }
        });
        rlSaveAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_folder_list.setVisibility(View.GONE);
                tv_selectfolder.setVisibility(View.GONE);
                if (gratitudeStatus.equals("EDIT")) {

                    if (dialogOpenOnceForEdit) {
                        try {
                            if (!isBlankOrSpecialCharacter(edtGratitudeName.getText().toString().trim())) {
                                rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
                                rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
                                rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
                                rootJsonInner.put("UserFolderId",userfolderid);

                                rootJsonInner.put("Notes", edtNotes.getText().toString());
                                //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                rootJsonInner.put("CategoryId", 1);
                                rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
                                rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
                                rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
                                rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
                                rootJsonInner.put("Song", songPathSelect);
                                rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
                                rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
                                rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
                                rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
                                //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
                                //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
                                rootJsonInner.put("PushNotification", true);

                                if (!ANNUAL_CROP_PATH.equals("")) {
                                    rootJsonInner.put("PictureDevicePath", ANNUAL_CROP_PATH);
                                } else {
                                    try {
                                        rootJsonInner.put("PictureDevicePath", globalmMyAchievementsListInnerModel.getPictureDevicePath());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                rootJson.put("model", rootJsonInner);
                                rootJson.put("Key", Util.KEY);
                                rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));


                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                saveAddGratitudeData1(hashMap,dialog,globalmMyAchievementsListInnerModel);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (!isBlankOrSpecialCharacter(edtGratitudeName.getText().toString().trim()) ){
                            HashMap<String, Object> requestHash = new HashMap<>();
                            if (chkSetReminder.isChecked()) {
                                globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
                                globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
                                globalmMyAchievementsListInnerModel.setUserFolderId(userfolderid);
                                globalmMyAchievementsListInnerModel.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                globalmMyAchievementsListInnerModel.setCategoryId(1);
                                globalmMyAchievementsListInnerModel.setSong(songPathSelect);
                                globalmMyAchievementsListInnerModel.setPushNotification(true);

                                if (!ANNUAL_CROP_PATH.equals("")) {
                                    globalmMyAchievementsListInnerModel.setPictureDevicePath(ANNUAL_CROP_PATH);
                                } else {
                                    try {
                                        globalmMyAchievementsListInnerModel.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                requestHash.put("model", globalmMyAchievementsListInnerModel);
                                requestHash.put("Key", Util.KEY);
                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            } else {
                                MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                                getGratitudeListModelInner.setId(globalmMyAchievementsListInnerModel.getId());
                                getGratitudeListModelInner.setUserFolderId(userfolderid);

                                getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                                getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                                //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                getGratitudeListModelInner.setCategoryId(1);
                                getGratitudeListModelInner.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                getGratitudeListModelInner.setStatus(false);
                                getGratitudeListModelInner.setIsDeleted(false);
                                getGratitudeListModelInner.setSong(songPathSelect);
                                getGratitudeListModelInner.setPushNotification(false);

                                if (!ANNUAL_CROP_PATH.equals("")) {
                                    getGratitudeListModelInner.setPictureDevicePath(ANNUAL_CROP_PATH);
                                } else {
                                    try {
                                        getGratitudeListModelInner.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                String updateDate = dateShow.getText().toString();
                                Date visionDate = null;
                                try {
                                    visionDate = simpleFormat.parse(updateDate);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                getGratitudeListModelInner.setCreatedAt(format.format(visionDate));

                                requestHash.put("model", getGratitudeListModelInner);
                                requestHash.put("Key", Util.KEY);
                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                            }


                            saveAddGratitudeData1(requestHash,dialog,globalmMyAchievementsListInnerModel);

                        } else {
                            Toast.makeText(getActivity(), "Please enter properachievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("ADD")) {
                    Log.e("add enable", "add enable");
                    if (dialogOpenOnceForAdd) {

                        try {
                            if (!isBlankOrSpecialCharacter(edtGratitudeName.getText().toString().trim())) {
                                try {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", null);
                                    rootJsonInner.put("UserFolderId",userfolderid);
                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", null);
                                    rootJsonInner.put("last_reminder_inserted_date", "");
                                    rootJsonInner.put("Picture", "");
                                    rootJsonInner.put("UploadPictureImgFileName", null);
                                    rootJsonInner.put("UploadPictureImg", "");
                                    rootJsonInner.put("Song", songPathSelect);
                                    rootJsonInner.put("CreatedAt", "");
                                    rootJsonInner.put("CreatedBy", Integer.parseInt(sharedPreference.read("UserID", "")));
                                    rootJsonInner.put("IsDeleted", false);
                                    rootJsonInner.put("Status", false);
                                    rootJsonInner.put("IsCreatedUpdated", null);
                                    rootJsonInner.put("PushNotification", true);

                                    rootJson.put("model", rootJsonInner);
                                    rootJson.put("Key", Util.KEY);
                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                //saveAddGratitudeData(hashMap);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        if (!isBlankOrSpecialCharacter(edtGratitudeName.getText().toString().trim())) {
                            MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                            getGratitudeListModelInner.setId(0);
                            getGratitudeListModelInner.setUserFolderId(userfolderid);
                            getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                            getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                            //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);

                            getGratitudeListModelInner.setCategoryId(1);
                            getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                            getGratitudeListModelInner.setStatus(false);
                            getGratitudeListModelInner.setIsDeleted(false);
                            getGratitudeListModelInner.setSong(songPathSelect);
                            if (chkSetReminder.isChecked()) {
                                getGratitudeListModelInner.setPushNotification(true);
                                getGratitudeListModelInner.setFrequencyId(1);
                                getGratitudeListModelInner.setReminderOption(1);
                                getGratitudeListModelInner.setReminderAt1(43200);
                                getGratitudeListModelInner.setReminderAt1Int(43200);
                            }
                            HashMap<String, Object> requestHash = new HashMap<>();
                            requestHash.put("model", getGratitudeListModelInner);
                            requestHash.put("Key", Util.KEY);
                            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            //saveAddGratitudeData(requestHash);

                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("EDITCOPY")) {
                    Log.e("edit copy", "edit copy");
                    if (!isBlankOrSpecialCharacter(edtGratitudeName.getText().toString().trim())) {

                        if (dialogOpenOnceForEdit) {
                            try {
                                if (!edtGratitudeName.getText().toString().trim().equals("")) {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("UserFolderId",userfolderid);
                                    rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
                                    rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
                                    rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
                                    rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                    rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
                                    rootJsonInner.put("Song", songPathSelect);
                                    rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
                                    rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
                                    rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
                                    rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
                                    // rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());

                                    rootJson.put("model", rootJsonInner);
                                    rootJson.put("Key", Util.KEY);
                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));


                                    HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                    //saveAddGratitudeData(hashMap);

                                } else {
                                    Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (!isBlankOrSpecialCharacter(edtGratitudeName.getText().toString().trim())) {
                                HashMap<String, Object> requestHash = new HashMap<>();
                                if (chkSetReminder.isChecked()) {
                                    globalmMyAchievementsListInnerModel.setId(0);
                                    globalmMyAchievementsListInnerModel.setUserFolderId(userfolderid);

                                    globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
                                    globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
                                    //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    globalmMyAchievementsListInnerModel.setCategoryId(1);
                                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);

                                    requestHash.put("model", globalmMyAchievementsListInnerModel);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } else {
                                    MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                                    getGratitudeListModelInner.setId(0);
                                    getGratitudeListModelInner.setUserFolderId(userfolderid);

                                    getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                                    getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                                    //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    getGratitudeListModelInner.setCategoryId(1);
                                    getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                    getGratitudeListModelInner.setStatus(false);
                                    getGratitudeListModelInner.setIsDeleted(false);
                                    getGratitudeListModelInner.setSong(songPathSelect);

                                    requestHash.put("model", getGratitudeListModelInner);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                                }


                                //saveAddGratitudeData(requestHash);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        rlDeleteAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogWithCustomButton alertDialogCustom = new AlertDialogWithCustomButton(getActivity());
                alertDialogCustom.ShowDialog("Delete confirmation", "Are you sure you want to delete this achievement? ", true, "CONFIRM", "NO");
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {
                        deleteGratitude(dialog,globalmMyAchievementsListInnerModel);
                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });
            }
        });

        rlUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeSaveButtonGreen();
                pickImageFromGallery1(globalmMyAchievementsListInnerModel);
            }
        });

        txtSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(arrCategory));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(spinnerArrayAdapter);
        rootJson = new JSONObject();
        sharedPreference.clear("GRATITUDEIMAGE");
        getValueListFromApi(globalmMyAchievementsListInnerModel);


        if (gratitudeStatus.equals("EDIT")) {
            edtGratitudeName.setEnabled(true);
            edtNotes.setEnabled(true);
            llChangeImageDelete.setVisibility(View.GONE);
            llSaveDelete.setVisibility(View.GONE);
            rlSetReminder.setVisibility(View.VISIBLE);
            rlSong.setVisibility(View.GONE);
            spCategory.setEnabled(true);
            imgSaveGratitude.setVisibility(View.GONE);
            imgEdit.setVisibility(View.VISIBLE);
        } else if (gratitudeStatus.equals("ADD")) {
            edtGratitudeName.setEnabled(true);
            edtGratitudeName.setHint("ADD ACHIEVEMENTS");
            edtNotes.setEnabled(true);
            txtUploadChangeImage.setText("Upload Image");
            llChangeImageDelete.setVisibility(View.VISIBLE);
            llSaveDelete.setVisibility(View.VISIBLE);
            imgDeleteGratitude.setVisibility(View.GONE);
            imgDeleteIcon.setVisibility(View.GONE);
            //rlEditCopy.setVisibility(View.GONE);
            spCategory.setEnabled(true);
            imgSaveGratitude.setVisibility(View.VISIBLE);
            blink();
            imgEdit.setVisibility(View.GONE);
        } else if (gratitudeStatus.equals("EDITCOPY")) {
            edtGratitudeName.setEnabled(true);
            edtNotes.setEnabled(true);
            txtUploadChangeImage.setText("Upload Image");
            llChangeImageDelete.setVisibility(View.VISIBLE);
            llSaveDelete.setVisibility(View.VISIBLE);
            imgDeleteGratitude.setVisibility(View.GONE);
            imgDeleteIcon.setVisibility(View.GONE);
            rlEditCopy.setVisibility(View.GONE);
            spCategory.setEnabled(true);
        }



        if (globalmMyAchievementsListInnerModel != null) {

            try {
                dateShow.setText(simpleFormat.format(format.parse(globalmMyAchievementsListInnerModel.getCreatedAt())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (gratitudeStatus.equals("EDIT")) {

                if(globalmMyAchievementsListInnerModel.getAchievement()!=null && !globalmMyAchievementsListInnerModel.getAchievement().equals("") && globalmMyAchievementsListInnerModel.getPrompt()!=null && !globalmMyAchievementsListInnerModel.getPrompt().equals(""))
                {
                    editGratitudeOne.setText(globalmMyAchievementsListInnerModel.getPrompt()+"");
                    edtGratitudeName.setText(globalmMyAchievementsListInnerModel.getAchievement());
                }


                edtGratitudeName.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {


                        Log.e("after text", "text change-------->" + editable.toString());
                        rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                        somethingChanges = true;


                    }
                });


                Log.e("journal Name", "journal---->" + globalmMyAchievementsListInnerModel.getAchievement());
            } else if (gratitudeStatus.equals("EDITCOPY")) {
                //added by jyotirmoy

                editGratitudeOne.setText(globalmMyAchievementsListInnerModel.getPrompt());
                edtGratitudeName.setText(globalmMyAchievementsListInnerModel.getAchievement());

            }

            edtNotes.setText(globalmMyAchievementsListInnerModel.getNotes());
            spCategory.setSelection(globalmMyAchievementsListInnerModel.getCategoryId() - 1);

            try {

                if (globalmMyAchievementsListInnerModel.getHasImage()) {
                    Log.e("block","0");
                    if (globalmMyAchievementsListInnerModel.getPictureDevicePath() != null && !globalmMyAchievementsListInnerModel.getPictureDevicePath().equals("") && !globalmMyAchievementsListInnerModel.getPicture().equals("")) {
                        Log.e("block","00");

                        imgJournalLoadingBar.setVisibility(View.VISIBLE);

                        Glide.with(getActivity())
                                .load(globalmMyAchievementsListInnerModel.getPicture())
                                .placeholder(R.drawable.empty_image_old)
                                .error(R.drawable.notfound)
                                .fitCenter()
                                // .override(200, 200) // resize the image to 200x200 pixels
                                .thumbnail(0.5f)
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .dontAnimate()
                                .listener(new RequestListener<Drawable>() {
                                    @Override
                                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                        imgJournalLoadingBar.setVisibility(View.GONE);
                                        imgGratitudeMainCard.setVisibility(View.GONE);

                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                        imgJournalLoadingBar.setVisibility(View.GONE);
                                        imgGratitudeMainCard.setVisibility(View.VISIBLE);

                                        return false;
                                    }

                                })

                                .into(imgGratitudeMain);


                    } else {


                        if (!globalmMyAchievementsListInnerModel.getPicture().equals("")) {
                            Log.e("block","01");
                            imgJournalLoadingBar.setVisibility(View.VISIBLE);

                            Glide.with(getActivity())
                                    .load(globalmMyAchievementsListInnerModel.getPicture())
                                    .placeholder(R.drawable.empty_image_old)
                                    .error(R.drawable.notfound)
                                    .fitCenter()
                                    // .override(200, 200) // resize the image to 200x200 pixels
                                    .thumbnail(0.5f)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .dontAnimate()
                                    .listener(new RequestListener<Drawable>() {
                                        @Override
                                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                            imgJournalLoadingBar.setVisibility(View.GONE);
                                            imgGratitudeMainCard.setVisibility(View.GONE);

                                            return false;

                                        }

                                        @Override
                                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                            imgJournalLoadingBar.setVisibility(View.GONE);
                                            imgGratitudeMainCard.setVisibility(View.VISIBLE);

                                            return false;
                                        }

                                    })
                                    .into(imgGratitudeMain);
                        }else{
                            Log.e("block","02");
                            String filePath = path + globalmMyAchievementsListInnerModel.getPictureDevicePath();
                            File file = new File(filePath);
                            Log.e("Path", ">>>>>>>>>>>>>> " + file.getAbsolutePath());
                            imgJournalLoadingBar.setVisibility(View.VISIBLE);
                            if (file.exists()) {

                                BitmapFactory.Options options = new BitmapFactory.Options();
                                options.inSampleSize = 4; // This will reduce the image resolution by a factor of 4
                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);

                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                                String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
                                Uri imageUri= Uri.parse(path);

                                Log.e("imgurl", "imgurl------->" + imageUri);
                                Glide.with(getActivity())
                                        .load(imageUri)
                                        .placeholder(R.drawable.empty_image_old)
                                        .error(R.drawable.notfound)
                                        .dontAnimate()
                                        .diskCacheStrategy(DiskCacheStrategy.ALL)

                                        .listener(new RequestListener<Drawable>() {
                                            @Override
                                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                                imgJournalLoadingBar.setVisibility(View.GONE);
                                                imgGratitudeMainCard.setVisibility(View.GONE);

                                                return false;

                                            }

                                            @Override
                                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                                imgJournalLoadingBar.setVisibility(View.GONE);
                                                imgGratitudeMainCard.setVisibility(View.VISIBLE);

                                                return false;
                                            }

                                        })
                                        .into(imgGratitudeMain);


                            }
                        }

                    }
                }else {
                    imgGratitudeMainCard.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }



            if (globalmMyAchievementsListInnerModel.getPushNotification() != null && globalmMyAchievementsListInnerModel.getPushNotification()) {
                chkSetReminder.setChecked(true);
                txtSetReminder.setText("View Reminder");
            }
        }

        rlSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gratitudeStatus.equals("EDIT")) {
                    if (chkSetReminder.isChecked() && txtSetReminder.getText().toString().equals("View Reminder")) {
                        CustomReminderDialogForEdit customReminderDialog = new CustomReminderDialogForEdit();
                        Gson gson = new Gson();
                        Bundle bundle = new Bundle();
                        bundle.putString("MODEL", gson.toJson(globalmMyAchievementsListInnerModel));
                        bundle.putString("MODELNAME", "GRATITUDE");
                        customReminderDialog.setArguments(bundle);
                        customReminderDialog.mListener = new CustomReminderDialogForEdit.onSubmitListener() {
                            @Override
                            public void setOnSubmitListener(JSONObject arg) {
                                Log.e("print jj", arg.toString() + "?");
                                dialogOpenOnceForEdit = true;

                                rootJsonInner = arg;
                                try {
                                    globalmMyAchievementsListInnerModel.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                                    globalmMyAchievementsListInnerModel.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                                    globalmMyAchievementsListInnerModel.setReminderOption(rootJsonInner.getInt("ReminderOption"));
                                    globalmMyAchievementsListInnerModel.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                                    globalmMyAchievementsListInnerModel.setReminderAt1Int(rootJsonInner.getInt("ReminderAt1"));
                                    globalmMyAchievementsListInnerModel.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                                    globalmMyAchievementsListInnerModel.setReminderAt2Int(rootJsonInner.getInt("ReminderAt2"));//...
                                    globalmMyAchievementsListInnerModel.setEmail(rootJsonInner.getBoolean("Email"));
                                    globalmMyAchievementsListInnerModel.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                                    globalmMyAchievementsListInnerModel.setSunday(rootJsonInner.getBoolean("Sunday"));
                                    globalmMyAchievementsListInnerModel.setMonday(rootJsonInner.getBoolean("Monday"));
                                    globalmMyAchievementsListInnerModel.setTuesday(rootJsonInner.getBoolean("Tuesday"));
                                    globalmMyAchievementsListInnerModel.setWednesday(rootJsonInner.getBoolean("Wednesday"));
                                    globalmMyAchievementsListInnerModel.setThursday(rootJsonInner.getBoolean("Thursday"));
                                    globalmMyAchievementsListInnerModel.setFriday(rootJsonInner.getBoolean("Friday"));
                                    globalmMyAchievementsListInnerModel.setSaturday(rootJsonInner.getBoolean("Saturday"));
                                    globalmMyAchievementsListInnerModel.setJanuary(rootJsonInner.getBoolean("January"));
                                    globalmMyAchievementsListInnerModel.setFebruary(rootJsonInner.getBoolean("February"));
                                    globalmMyAchievementsListInnerModel.setMarch(rootJsonInner.getBoolean("March"));
                                    globalmMyAchievementsListInnerModel.setApril(rootJsonInner.getBoolean("April"));
                                    globalmMyAchievementsListInnerModel.setMay(rootJsonInner.getBoolean("May"));
                                    globalmMyAchievementsListInnerModel.setJune(rootJsonInner.getBoolean("June"));
                                    globalmMyAchievementsListInnerModel.setAugust(rootJsonInner.getBoolean("August"));
                                    globalmMyAchievementsListInnerModel.setSeptember(rootJsonInner.getBoolean("September"));
                                    globalmMyAchievementsListInnerModel.setOctober(rootJsonInner.getBoolean("October"));
                                    globalmMyAchievementsListInnerModel.setNovember(rootJsonInner.getBoolean("November"));
                                    globalmMyAchievementsListInnerModel.setDecember(rootJsonInner.getBoolean("December"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                /////////////////////


                            }
                        };
                        customReminderDialog.show(getFragmentManager(), "");
                    }

                }

            }
        });

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imgEdit.setVisibility(View.GONE);
                imgSaveGratitude.setVisibility(View.VISIBLE);
                blink();
                imgCopy.setVisibility(View.GONE);
                edtGratitudeName.setEnabled(true);
                edtNotes.setEnabled(true);
                llChangeImageDelete.setVisibility(View.VISIBLE);
                llSaveDelete.setVisibility(View.VISIBLE);
                rlSetReminder.setVisibility(View.VISIBLE);
                rlSong.setVisibility(View.VISIBLE);
                spCategory.setEnabled(true);
            }
        });
        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (globalmMyAchievementsListInnerModel != null) {
                    //globalmMyAchievementsListInnerModel.setCategoryId(i + 1);
                    globalmMyAchievementsListInnerModel.setCategoryId(1);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rlChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery1(globalmMyAchievementsListInnerModel);//
            }
        });

        imgDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgGratitudeMain.setBackground(null);
                // imgGratitudeMain.setBackgroundResource(R.drawable.empty_image);
                stringImg = "";
                globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(stringImg);
                deleteImage(globalmMyAchievementsListInnerModel);

            }
        });
        chkSetReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makeSaveButtonGreen();
                if (isChecked) {
                    if (gratitudeStatus.equals("EDIT")) {
                        if (!edtGratitudeName.getText().toString().trim().equals("")) {

                            txtSetReminder.setText("View Reminder");

                            CustomReminderDialogForEditAchievement customReminderDialogForEditAchievement = new CustomReminderDialogForEditAchievement();
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            bundle.putString("MODELNAME", "ACHIEVEMENT");
                            bundle.putString("MODEL", gson.toJson(globalmMyAchievementsListInnerModel));
                            customReminderDialogForEditAchievement.setArguments(bundle);
                            customReminderDialogForEditAchievement.mListener = new CustomReminderDialogForEditAchievement.onSubmitListener() {
                                @Override
                                public void setOnSubmitListener(JSONObject arg) {
                                    Log.e("print jj", arg.toString() + "?");
                                    dialogOpenOnceForEdit = true;

                                    rootJsonInner = arg;
                                    try {
                                        globalmMyAchievementsListInnerModel.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                                        globalmMyAchievementsListInnerModel.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                                        globalmMyAchievementsListInnerModel.setReminderOption(rootJsonInner.getInt("ReminderOption"));
                                        globalmMyAchievementsListInnerModel.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                                        globalmMyAchievementsListInnerModel.setReminderAt1Int(rootJsonInner.getInt("ReminderAt1"));
                                        globalmMyAchievementsListInnerModel.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                                        globalmMyAchievementsListInnerModel.setReminderAt2Int(rootJsonInner.getInt("ReminderAt2"));//...
                                        globalmMyAchievementsListInnerModel.setEmail(rootJsonInner.getBoolean("Email"));
                                        globalmMyAchievementsListInnerModel.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                                        globalmMyAchievementsListInnerModel.setSunday(rootJsonInner.getBoolean("Sunday"));
                                        globalmMyAchievementsListInnerModel.setMonday(rootJsonInner.getBoolean("Monday"));
                                        globalmMyAchievementsListInnerModel.setTuesday(rootJsonInner.getBoolean("Tuesday"));
                                        globalmMyAchievementsListInnerModel.setWednesday(rootJsonInner.getBoolean("Wednesday"));
                                        globalmMyAchievementsListInnerModel.setThursday(rootJsonInner.getBoolean("Thursday"));
                                        globalmMyAchievementsListInnerModel.setFriday(rootJsonInner.getBoolean("Friday"));
                                        globalmMyAchievementsListInnerModel.setSaturday(rootJsonInner.getBoolean("Saturday"));
                                        globalmMyAchievementsListInnerModel.setJanuary(rootJsonInner.getBoolean("January"));
                                        globalmMyAchievementsListInnerModel.setFebruary(rootJsonInner.getBoolean("February"));
                                        globalmMyAchievementsListInnerModel.setMarch(rootJsonInner.getBoolean("March"));
                                        globalmMyAchievementsListInnerModel.setApril(rootJsonInner.getBoolean("April"));
                                        globalmMyAchievementsListInnerModel.setMay(rootJsonInner.getBoolean("May"));
                                        globalmMyAchievementsListInnerModel.setJune(rootJsonInner.getBoolean("June"));
                                        globalmMyAchievementsListInnerModel.setAugust(rootJsonInner.getBoolean("August"));
                                        globalmMyAchievementsListInnerModel.setSeptember(rootJsonInner.getBoolean("September"));
                                        globalmMyAchievementsListInnerModel.setOctober(rootJsonInner.getBoolean("October"));
                                        globalmMyAchievementsListInnerModel.setNovember(rootJsonInner.getBoolean("November"));
                                        globalmMyAchievementsListInnerModel.setDecember(rootJsonInner.getBoolean("December"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    /////////////////////////////


                                }
                            };
                            customReminderDialogForEditAchievement.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }


                    } else if (gratitudeStatus.equals("ADD")) {
                        if (!edtGratitudeName.getText().toString().trim().equals("")) {
                            CustomReminderDialog customReminderDialog = new CustomReminderDialog();
                            customReminderDialog.mListener = new CustomReminderDialog.onSubmitListener() {
                                @Override
                                public void setOnSubmitListener(JSONObject arg) {
                                    Log.e("print jj", arg.toString() + "?");
                                    dialogOpenOnceForAdd = true;
                                    rootJsonInner = arg;
                                }
                            };
                            customReminderDialog.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    if (PREVIOUS_SET_NOTIFICATION1.equals("TRUE")) {
                        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
                        dialog.setContentView(R.layout.dialog_turnoff_edit_reminder);
                        dialog.setCancelable(false);

                        RelativeLayout rlEditReminder = dialog.findViewById(R.id.rlEditReminder);
                        RelativeLayout rlTurnOffReminder = dialog.findViewById(R.id.rlTurnOffReminder);
                        RelativeLayout rlTop = dialog.findViewById(R.id.rlTop);
                        RelativeLayout rlBottom = dialog.findViewById(R.id.rlBottom);

                        rlTop.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION1 = "FALSE";
                            txtSetReminder.setText("Set a Reminder");
                        });

                        rlBottom.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION1 = "FALSE";
                            txtSetReminder.setText("Set a Reminder");
                        });


                        rlEditReminder.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(true);
                        });

                        rlTurnOffReminder.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION1 = "FALSE";
                            txtSetReminder.setText("Set a Reminder");
                        });

                        dialog.show();
                    } else {
                        txtSetReminder.setText("Set a Reminder");
                    }
                }
            }
        });

        imgSaveGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String journalName = editGratitudeOne.getText().toString().trim() + edtGratitudeName.getText().toString().trim();

                if (gratitudeStatus.equals("EDIT")) {
                    if (dialogOpenOnceForEdit) {
                        try {
                            if (!edtGratitudeName.getText().toString().trim().equals("")) {
                                rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
                                rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
                                rootJsonInner.put("Achievement", journalName);
                                rootJsonInner.put("Notes", edtNotes.getText().toString());
                                //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                rootJsonInner.put("CategoryId", 1);
                                rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
                                rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
                                rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
                                rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
                                rootJsonInner.put("Song", songPathSelect);
                                rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
                                rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
                                rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
                                rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
                                // rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
                                rootJsonInner.put("PushNotification", true);

                                if (!ANNUAL_CROP_PATH.equals("")) {
                                    rootJsonInner.put("PictureDevicePath", ANNUAL_CROP_PATH);
                                } else {
                                    try {
                                        rootJsonInner.put("PictureDevicePath", globalmMyAchievementsListInnerModel.getPictureDevicePath());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                rootJson.put("model", rootJsonInner);
                                rootJson.put("Key", Util.KEY);
                                rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));


                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                saveAddGratitudeData1(hashMap,dialog,globalmMyAchievementsListInnerModel);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (!edtGratitudeName.getText().toString().trim().equals("")) {
                            HashMap<String, Object> requestHash = new HashMap<>();
                            if (chkSetReminder.isChecked()) {
                                globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
                                globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString().trim());
                                globalmMyAchievementsListInnerModel.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                globalmMyAchievementsListInnerModel.setCategoryId(1);
                                globalmMyAchievementsListInnerModel.setSong(songPathSelect);
                                globalmMyAchievementsListInnerModel.setPushNotification(true);

                                if (!ANNUAL_CROP_PATH.equals("")) {
                                    globalmMyAchievementsListInnerModel.setPictureDevicePath(ANNUAL_CROP_PATH);
                                } else {
                                    try {
                                        globalmMyAchievementsListInnerModel.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                requestHash.put("model", globalmMyAchievementsListInnerModel);
                                requestHash.put("Key", Util.KEY);
                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            } else {
                                MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                                getGratitudeListModelInner.setId(globalmMyAchievementsListInnerModel.getId());
                                getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                                getGratitudeListModelInner.setNotes(edtNotes.getText().toString().trim());
                                //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                getGratitudeListModelInner.setCategoryId(1);
                                getGratitudeListModelInner.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                getGratitudeListModelInner.setStatus(false);
                                getGratitudeListModelInner.setIsDeleted(false);
                                getGratitudeListModelInner.setSong(songPathSelect);
                                getGratitudeListModelInner.setPushNotification(false);

                                if (!ANNUAL_CROP_PATH.equals("")) {
                                    getGratitudeListModelInner.setPictureDevicePath(ANNUAL_CROP_PATH);
                                } else {
                                    try {
                                        getGratitudeListModelInner.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                requestHash.put("model", getGratitudeListModelInner);
                                requestHash.put("Key", Util.KEY);
                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                            }


                            saveAddGratitudeData1(requestHash,dialog,globalmMyAchievementsListInnerModel);

                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("ADD")) {
                    if (dialogOpenOnceForAdd) {

                        try {
                            if (!edtGratitudeName.getText().toString().trim().equals("")) {
                                try {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", null);
                                    rootJsonInner.put("Achievement", journalName);
                                    rootJsonInner.put("Notes", edtNotes.getText().toString().trim());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", null);
                                    rootJsonInner.put("last_reminder_inserted_date", "");
                                    rootJsonInner.put("Picture", "");
                                    rootJsonInner.put("UploadPictureImgFileName", null);
                                    rootJsonInner.put("UploadPictureImg", "");
                                    rootJsonInner.put("Song", songPathSelect);
                                    rootJsonInner.put("CreatedAt", "");
                                    rootJsonInner.put("CreatedBy", Integer.parseInt(sharedPreference.read("UserID", "")));
                                    rootJsonInner.put("IsDeleted", false);
                                    rootJsonInner.put("Status", false);
                                    rootJsonInner.put("IsCreatedUpdated", null);
                                    rootJsonInner.put("PushNotification", true);

                                    rootJson.put("model", rootJsonInner);
                                    rootJson.put("Key", Util.KEY);
                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                saveAddGratitudeData1(hashMap,dialog,globalmMyAchievementsListInnerModel);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        if (!edtGratitudeName.getText().toString().trim().equals("")) {
                            MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                            getGratitudeListModelInner.setId(0);
                            getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                            getGratitudeListModelInner.setNotes(edtNotes.getText().toString().trim());
                            //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                            getGratitudeListModelInner.setCategoryId(1);
                            getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                            getGratitudeListModelInner.setStatus(false);
                            getGratitudeListModelInner.setIsDeleted(false);
                            getGratitudeListModelInner.setSong(songPathSelect);
                            if (chkSetReminder.isChecked()) {
                                getGratitudeListModelInner.setPushNotification(true);
                                getGratitudeListModelInner.setFrequencyId(1);
                                getGratitudeListModelInner.setReminderOption(1);
                                getGratitudeListModelInner.setReminderAt1(43200);
                                getGratitudeListModelInner.setReminderAt1Int(43200);
                            }
                            HashMap<String, Object> requestHash = new HashMap<>();
                            requestHash.put("model", getGratitudeListModelInner);
                            requestHash.put("Key", Util.KEY);
                            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            saveAddGratitudeData1(requestHash,dialog,globalmMyAchievementsListInnerModel);

                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("EDITCOPY")) {
                    if (!edtGratitudeName.getText().toString().trim().equals("")) {

                        if (dialogOpenOnceForEdit) {
                            try {
                                if (!edtGratitudeName.getText().toString().trim().equals("")) {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
                                    rootJsonInner.put("Notes", edtNotes.getText().toString().trim());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
                                    rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
                                    rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
                                    rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                    rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
                                    rootJsonInner.put("Song", songPathSelect);
                                    rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
                                    rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
                                    rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
                                    rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
                                    //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());

                                    rootJson.put("model", rootJsonInner);
                                    rootJson.put("Key", Util.KEY);
                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));


                                    HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                    saveAddGratitudeData1(hashMap,dialog,globalmMyAchievementsListInnerModel);

                                } else {
                                    Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (!edtGratitudeName.getText().toString().trim().equals("")) {
                                HashMap<String, Object> requestHash = new HashMap<>();
                                if (chkSetReminder.isChecked()) {
                                    globalmMyAchievementsListInnerModel.setId(0);
                                    globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
                                    globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString().trim());
                                    //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    globalmMyAchievementsListInnerModel.setCategoryId(1);
                                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);

                                    requestHash.put("model", globalmMyAchievementsListInnerModel);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } else {
                                    MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                                    getGratitudeListModelInner.setId(0);
                                    getGratitudeListModelInner.setUserEqFolderId(filename1);
                                    getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                                    getGratitudeListModelInner.setNotes(edtNotes.getText().toString().trim());
                                    //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    getGratitudeListModelInner.setCategoryId(1);
                                    getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                    getGratitudeListModelInner.setStatus(false);
                                    getGratitudeListModelInner.setIsDeleted(false);
                                    getGratitudeListModelInner.setSong(songPathSelect);

                                    requestHash.put("model", getGratitudeListModelInner);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                                }


                                saveAddGratitudeData1(requestHash,dialog,globalmMyAchievementsListInnerModel);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        imgDeleteGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteGratitude(dialog,globalmMyAchievementsListInnerModel);

            }
        });

        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
                openeditdialog="yes";
                openDialogForAchieveAddEdit("EDITCOPY",globalmMyAchievementsListInnerModel,globalmMyAchievementsListInnerModel.getPicture(),OPEN_NOTIFICATION1,PREVIOUS_SET_NOTIFICATION1);

            }
        });
        if (!new SharedPreference(getActivity()).read("FTIME_VIDEO_INFO", "").equals("") && OPEN_NOTIFICATION1.equals("TRUE")) {
            rlSetReminder.performClick();
        }


        dialog.show();
    }





    private void openDialogForGratitudeShare1(String gratitudeName, Integer gratitudeID,Dialog dialog1,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gratitude_share_options);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        RelativeLayout rlTestAndPic = dialog.findViewById(R.id.rlTestAndPic);
        RelativeLayout rlTextAndPicEx = dialog.findViewById(R.id.rlTextAndPicEx);
        RelativeLayout rlTextOverPic = dialog.findViewById(R.id.rlTextOverPic);
        RelativeLayout rlTextOverPicEx = dialog.findViewById(R.id.rlTextOverPicEx);
        RelativeLayout rlTextOnly = dialog.findViewById(R.id.rlTextOnly);
        RelativeLayout rlTextOnlyEx = dialog.findViewById(R.id.rlTextOnlyEx);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        rlTestAndPic.setOnClickListener(view -> {
            strDialogSelectionType = "textAndPic";
            dialog.dismiss();
            openDialogForTextOverPicOption1(gratitudeName, gratitudeID, "textAndPic",dialog1,globalmMyAchievementsListInnerModel);
        });

        rlTextAndPicEx.setOnClickListener(view -> {
            openDialogForShareExamples1("textAndPic",globalmMyAchievementsListInnerModel);
        });

        rlTextOverPic.setOnClickListener(view -> {
            strDialogSelectionType = "textOverPic";
            dialog.dismiss();
            openDialogForTextOverPicOption1(gratitudeName, gratitudeID, "textOverPic",dialog1,globalmMyAchievementsListInnerModel);
        });

        rlTextOverPicEx.setOnClickListener(view -> {
            openDialogForShareExamples1("textOverPic",globalmMyAchievementsListInnerModel);
        });

        rlTextOnly.setOnClickListener(view -> {
            strDialogSelectionType = "textOnly";
            dialog.dismiss();
            openDialogForTextOverPicOption1(gratitudeName, gratitudeID, "textOnly",dialog1,globalmMyAchievementsListInnerModel);
        });

        rlTextOnlyEx.setOnClickListener(view -> {
            openDialogForShareExamples1("textOnly",globalmMyAchievementsListInnerModel);
        });

        dialog.show();
    }
    private void openDialogForShareExamples1(String TYPE,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_gratitude_share_examples);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView imgExample = dialog.findViewById(R.id.imgExample);

        imgClose.setOnClickListener(view -> {
            dialog.dismiss();
        });

        if (TYPE.equals("textAndPic")) {
            imgExample.setImageResource(R.drawable.text_and_pic);
        } else if (TYPE.equals("textOverPic")) {
            imgExample.setImageResource(R.drawable.text_over_pic);
        } else {
            imgExample.setImageResource(R.drawable.text_only);
        }

        dialog.show();

    }




    private void openDialogForTextOverPicOption1(String gratitudeName, Integer gratitudeID, String TYPE,Dialog dialog1,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        final Dialog dialog = new Dialog(getActivity(), R.style.DialogTheme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_text_over_pic_shareability);


        RelativeLayout rlSharableSectionTOP = dialog.findViewById(R.id.rlSharableSection);
        RelativeLayout rlPicSectionTOP = dialog.findViewById(R.id.rlPicSection);
        cardViewBackgroundPicTOP = dialog.findViewById(R.id.cardViewBackgroundPic);
        imgBackgroundPicTOP = dialog.findViewById(R.id.imgBackgroundPic);
        llAddPicTOP = dialog.findViewById(R.id.llAddPic);
        buttonChangeBackgroundImageTOP = dialog.findViewById(R.id.buttonChangeBackgroundImage);
        buttonMoveTextBoxTOP = dialog.findViewById(R.id.buttonMoveTextBox);
        RelativeLayout rlBorderTOP = dialog.findViewById(R.id.rlBorder);
        CheckBox chkBorderNoBorderTOP = dialog.findViewById(R.id.chkBorderNoBorder);
        Spinner spTextSizeTOP = dialog.findViewById(R.id.spTextSize);
        Button btnLeftAlignmentTOP = dialog.findViewById(R.id.btnLeftAlignment);
        Button btnCenterAlignmentTOP = dialog.findViewById(R.id.btnCenterAlignment);
        Button btnRightAlignmentTOP = dialog.findViewById(R.id.btnRightAlignment);
        RelativeLayout rlWhiteBackgroundBlackTextTOP = dialog.findViewById(R.id.rlWhiteBackgroundBlackText);
        RelativeLayout rlBlackBackgroundWhiteTextTOP = dialog.findViewById(R.id.rlBlackBackgroundWhiteText);
        RelativeLayout rlTransparentBackgroundBlackTextTOP = dialog.findViewById(R.id.rlTransparentBackgroundBlackText);
        RelativeLayout rlTransparentBackgroundWhiteTextTOP = dialog.findViewById(R.id.rlTransparentBackgroundWhiteText);
        ImageView imgWhiteBackgroundBlackTextTOP = dialog.findViewById(R.id.imgWhiteBackgroundBlackText);
        ImageView imgBlackBackgroundWhiteTextTOP = dialog.findViewById(R.id.imgBlackBackgroundWhiteText);
        ImageView imgTransparentBackgroundBlackTextTOP = dialog.findViewById(R.id.imgTransparentBackgroundBlackText);
        ImageView imgTransparentBackgroundWhiteTextTOP = dialog.findViewById(R.id.imgTransparentBackgroundWhiteText);
        rlTextOverPicInnerTOP = dialog.findViewById(R.id.rlTextOverPicInner);
        EditText edtTextOverPicInnerTOP = dialog.findViewById(R.id.edtTextOverPicInner);
        TextView txtGratefulFor = dialog.findViewById(R.id.txtGratefulFor);
        CustomScrollView scroll_view = dialog.findViewById(R.id.scroll_view);
        FrameLayout frameLayout = dialog.findViewById(R.id.frameLayout);
        RelativeLayout rootlayout = dialog.findViewById(R.id.rootlayout);
        TextView txtTextOverPicOwner = dialog.findViewById(R.id.txtTextOverPicOwner);
        ImageView imgMindBodyHq = dialog.findViewById(R.id.imgMindBodyHq);
        RelativeLayout rlShareGratitude = dialog.findViewById(R.id.rlShareGratitude);
        RelativeLayout rlCancelGratitude = dialog.findViewById(R.id.rlCancelGratitude);
        RelativeLayout rlTextAndPic = dialog.findViewById(R.id.rlTextAndPic);
        EditText edtTextANDPic = dialog.findViewById(R.id.edtTextANDPic);

        final boolean[] boolWhiteBackgroundBlackText = {true};
        final boolean[] boolBlackBackgroundWhiteText = {false};
        final boolean[] boolTransparentBackgroundBlackText = {false};
        final boolean[] boolTransparentBackgroundWhiteText = {false};

        if (TYPE.equals("textAndPic")) {
            rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextAndPic.setVisibility(View.VISIBLE);
            rlWhiteBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlBlackBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundWhiteTextTOP.setVisibility(View.GONE);
        } else if (TYPE.equals("textOnly")) {
            rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextAndPic.setVisibility(View.VISIBLE);
            rlWhiteBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlBlackBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundBlackTextTOP.setVisibility(View.GONE);
            rlTransparentBackgroundWhiteTextTOP.setVisibility(View.GONE);
            rlPicSectionTOP.setVisibility(View.GONE);
        }

        edtTextOverPicInnerTOP.setText(gratitudeName);
        txtTextOverPicOwner.setText(gratitudeName);
        edtTextANDPic.setText(gratitudeName);

        if (mFile != null) {
            cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
            imgBackgroundPicTOP.setVisibility(View.VISIBLE);
            llAddPicTOP.setVisibility(View.GONE);
            buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
            if (strDialogSelectionType.equals("textOverPic")) {
                buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
            }
            /*rlTextOverPicInnerTOP.setVisibility(View.GONE);
            rlTextOverPicInnerTOP.setVisibility(View.VISIBLE);*/
            Uri imageUri = Uri.fromFile(mFile);
            Glide.with(getActivity())
                    .load(imageUri)
                    .into(imgBackgroundPicTOP);
        } else {
            try {
                if (globalmMyAchievementsListInnerModel != null && globalmMyAchievementsListInnerModel.getPictureDevicePath() != null && !globalmMyAchievementsListInnerModel.getPictureDevicePath().equals("")) {

                    // String filePath = path + globalGetGratitudeListModelInner.getPictureDevicePath();
                    String filePath = Util.getFile(getContext()).getPath() + globalmMyAchievementsListInnerModel.getPictureDevicePath();
                    Log.e("filepath", "filepath-----" + filePath);
                    File file = new File(filePath);
                    Log.e("Path", ">>>>>>>>>>>>>> " + file.getAbsolutePath());
                    cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
                    imgBackgroundPicTOP.setVisibility(View.VISIBLE);
                    llAddPicTOP.setVisibility(View.GONE);
                    buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
                    if (strDialogSelectionType.equals("textOverPic")) {
                        buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
                    }
                    /*rlTextOverPicInnerTOP.setVisibility(View.GONE);
                    rlTextOverPicInnerTOP.setVisibility(View.VISIBLE);*/
                    if (file.exists()) {
                        Uri imageUri = Uri.fromFile(file);
                        Glide.with(getActivity())
                                .load(imageUri)
                                .into(imgBackgroundPicTOP);
                    } else {
                        Glide.with(getActivity())
                                .load(globalmMyAchievementsListInnerModel.getPicture())
                                .into(imgBackgroundPicTOP);
                    }
                } else if (globalmMyAchievementsListInnerModel.getPicture() != null && !globalmMyAchievementsListInnerModel.getPicture().equals("")) {
                    cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
                    imgBackgroundPicTOP.setVisibility(View.VISIBLE);
                    llAddPicTOP.setVisibility(View.GONE);
                    buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
                    if (strDialogSelectionType.equals("textOverPic")) {
                        buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
                    }
                    /*rlTextOverPicInnerTOP.setVisibility(View.GONE);
                    rlTextOverPicInnerTOP.setVisibility(View.VISIBLE);*/
                    Glide.with(getActivity())
                            .load(globalmMyAchievementsListInnerModel.getPicture())
                            .into(imgBackgroundPicTOP);
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        rlBorderTOP.setOnClickListener(view -> {
            if (chkBorderNoBorderTOP.isChecked()) {
                chkBorderNoBorderTOP.setChecked(false);
            } else {
                chkBorderNoBorderTOP.setChecked(true);
            }
        });
        chkBorderNoBorderTOP.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                if (strDialogSelectionType.equals("textAndPic") || strDialogSelectionType.equals("textOnly")) {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.round_corner_black);
                    rlTextAndPic.setBackgroundResource(R.drawable.edittext_background_black_border);
                } else {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.round_corner_black);
                    if (boolWhiteBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black);
                    } else if (boolBlackBackgroundWhiteText[0]) {

                    } else if (boolTransparentBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
                    } else {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
                    }
                }
            } else {
                if (strDialogSelectionType.equals("textAndPic") || strDialogSelectionType.equals("textOnly")) {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    rlTextAndPic.setBackgroundResource(R.drawable.edittext_background_white_border);
                } else {
                    rlPicSectionTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    if (boolWhiteBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounded_corner_white);
                    } else if (boolBlackBackgroundWhiteText[0]) {

                    } else if (boolTransparentBackgroundBlackText[0]) {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
                    } else {
                        rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
                    }
                }
            }
        });
        rlBorderTOP.performClick();
        llAddPicTOP.setOnClickListener(view -> {
            Log.e("add pic", "add pic-line-2341");
            globalPickImageFromGratitudeShareDialog = true;
            pickImageFromGallery1(globalmMyAchievementsListInnerModel);

        });
        buttonChangeBackgroundImageTOP.setOnClickListener(view -> {
            globalPickImageFromGratitudeShareDialog = true;
            pickImageFromGallery1(globalmMyAchievementsListInnerModel);
            Log.e("change pic", "change background pic-line-2341");
        });
        ArrayList<Integer> lstTextSize = new ArrayList<>();
        for (int i = 12; i <= 48; i = i + 2) {
            lstTextSize.add(i);
        }
        ArrayAdapter<Integer> adapterFlag = new ArrayAdapter<Integer>(getActivity(), android.R.layout.simple_spinner_item, lstTextSize);
        adapterFlag.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTextSizeTOP.setAdapter(adapterFlag);
        spTextSizeTOP.setSelection(4);

        spTextSizeTOP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twelve));
                } else if (i == 1) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fourteen));
                } else if (i == 2) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_sixteen));
                } else if (i == 3) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_eighteen));
                } else if (i == 4) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty));
                } else if (i == 5) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_two));
                } else if (i == 6) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_four));
                } else if (i == 7) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_six));
                } else if (i == 8) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_twenty_eight));
                } else if (i == 9) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirty));
                } else if (i == 10) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyTwo));
                } else if (i == 11) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyFour));
                } else if (i == 12) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtySix));
                } else if (i == 13) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_thirtyEight));
                } else if (i == 14) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_forty));
                } else if (i == 15) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyTwo));
                } else if (i == 16) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyFour));
                } else if (i == 17) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortySix));
                } else if (i == 18) {
                    edtTextOverPicInnerTOP.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                    edtTextANDPic.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                    txtTextOverPicOwner.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.fab_fortyEight));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnLeftAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.LEFT);
            edtTextANDPic.setGravity(Gravity.LEFT);
            txtTextOverPicOwner.setGravity(Gravity.LEFT);
        });

        btnCenterAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.CENTER);
            edtTextANDPic.setGravity(Gravity.CENTER);
            txtTextOverPicOwner.setGravity(Gravity.CENTER);
        });

        btnRightAlignmentTOP.setOnClickListener(view -> {
            edtTextOverPicInnerTOP.setGravity(Gravity.RIGHT);
            edtTextANDPic.setGravity(Gravity.RIGHT);
            txtTextOverPicOwner.setGravity(Gravity.RIGHT);
        });

        rlWhiteBackgroundBlackTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.white));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.black));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.black));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.black));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounded_corner_white);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.logo_mbhq);
            boolWhiteBackgroundBlackText[0] = true;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlBlackBackgroundWhiteTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.black));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.white));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.white));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.white));
            rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_filled_black);
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.mndbody_logo_white);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = true;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlTransparentBackgroundBlackTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_green_check);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.white));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.black));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.black));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.black));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.logo_mbhq);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = true;
            boolTransparentBackgroundWhiteText[0] = false;
        });
        rlTransparentBackgroundWhiteTextTOP.setOnClickListener(view -> {
            imgWhiteBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgBlackBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundBlackTextTOP.setImageResource(R.drawable.mbhq_circle_green);
            imgTransparentBackgroundWhiteTextTOP.setImageResource(R.drawable.mbhq_green_check);
            rlSharableSectionTOP.setBackgroundColor(getResources().getColor(R.color.black));
            txtGratefulFor.setTextColor(getResources().getColor(R.color.white));
            edtTextOverPicInnerTOP.setTextColor(getResources().getColor(R.color.white));
            txtTextOverPicOwner.setTextColor(getResources().getColor(R.color.white));
            if (chkBorderNoBorderTOP.isChecked()) {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.round_corner_black_transparent_inside);
            } else {
                rlTextOverPicInnerTOP.setBackgroundResource(R.drawable.rounde_corner_filled_transparent);
            }
            //MIND BODY HQ IMAGE RELATED WORK
            imgMindBodyHq.setImageResource(R.drawable.mndbody_logo_white);
            boolWhiteBackgroundBlackText[0] = false;
            boolBlackBackgroundWhiteText[0] = false;
            boolTransparentBackgroundBlackText[0] = false;
            boolTransparentBackgroundWhiteText[0] = true;
        });

        final boolean[] boolMoveText = {false};
        buttonMoveTextBoxTOP.setOnClickListener(view -> {
            if (!boolMoveText[0]) {
                boolMoveText[0] = true;
                scroll_view.setEnableScrolling(false);
                scroll_view.setFocusableInTouchMode(false);
                buttonMoveTextBoxTOP.setText("lock text box");
                buttonMoveTextBoxTOP.setBackgroundResource(R.drawable.rounded_corner_green);
                buttonMoveTextBoxTOP.setTextColor(getResources().getColor(R.color.white));
                txtTextOverPicOwner.setText(edtTextOverPicInnerTOP.getText().toString());
                edtTextOverPicInnerTOP.setVisibility(View.GONE);
                txtTextOverPicOwner.setVisibility(View.VISIBLE);
                rlTextOverPicInnerTOP.setOnTouchListener(new OnDragTouchListener(rlTextOverPicInnerTOP, frameLayout));
            } else {
                boolMoveText[0] = false;
                scroll_view.setEnableScrolling(true);
                scroll_view.setFocusableInTouchMode(true);
                buttonMoveTextBoxTOP.setText("move text box");
                buttonMoveTextBoxTOP.setBackgroundResource(R.drawable.rounded_corner_green_border_white_inside);
                buttonMoveTextBoxTOP.setTextColor(getResources().getColor(R.color.colorPrimary));
                edtTextOverPicInnerTOP.setVisibility(View.VISIBLE);
                txtTextOverPicOwner.setVisibility(View.GONE);
                rlTextOverPicInnerTOP.setOnTouchListener(null);
            }
        });

        rlCancelGratitude.setOnClickListener(view -> {
            openeditdialog="";
            dialog.dismiss();
            dialog1.dismiss();
            //((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievementList", null);
        });

        rlShareGratitude.setOnClickListener(view -> {
            if (mFile == null && (globalmMyAchievementsListInnerModel.getPictureDevicePath() == null || globalmMyAchievementsListInnerModel.getPictureDevicePath().equals(""))
                    && (globalmMyAchievementsListInnerModel.getPicture() == null || globalmMyAchievementsListInnerModel.getPicture().equals(""))) {
                final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
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
                dialog.dismiss();
                if (strDialogSelectionType.equals("textOverPic")) {
                    funcForShareImageGratitudeSharability1(rlSharableSectionTOP, gratitudeID, edtTextOverPicInnerTOP,dialog1,globalmMyAchievementsListInnerModel);
                } else {
                    funcForShareImageGratitudeSharability1(rlSharableSectionTOP, gratitudeID, edtTextANDPic,dialog1,globalmMyAchievementsListInnerModel);
                }
            }
        });

        dialog.show();

    }
    private void funcForShareImageGratitudeSharability1(RelativeLayout rlPicSection, Integer gratitudeID, EditText edtGratitudeName,Dialog dialog1,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        rlPicSection.setDrawingCacheEnabled(true);
        Bitmap bitmap = getScreenShot1(rlPicSection);
        File shareFile = createFolder1();
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

        String journalName = edtGratitudeName.getText().toString().trim();
        dialogOpenOnceForEdit = true;

        rootJsonInner = new JSONObject();
        int userfolderid=globalmMyAchievementsListInnerModel.getUserFolderId();

        if (dialogOpenOnceForEdit) {

            try {
                if (!edtGratitudeName.getText().toString().equals("")) {
                    rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
                    rootJsonInner.put("UserFolderId", userfolderid);

                    Log.e("journalId", "journal update id>>>>>" + globalmMyAchievementsListInnerModel.getId());
                    rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
                    rootJsonInner.put("Achievement", journalName);
                    // rootJsonInner.put("Description", edtGrateful.getText().toString());
                    rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
                    rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
                    rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                    rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
                    rootJsonInner.put("Song", songPathSelect);
                    rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
                    rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
                    rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
                    //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
                    //rootJsonInner.put("PushNotification", true);

                    if (!ANNUAL_CROP_PATH.equals("")) {
                        rootJsonInner.put("PictureDevicePath", ANNUAL_CROP_PATH);
                    } else {
                        try {
                            rootJsonInner.put("PictureDevicePath", globalmMyAchievementsListInnerModel.getPictureDevicePath());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    rootJson.put("model", rootJsonInner);
                    rootJson.put("Key", Util.KEY);
                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                    HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                    //saveAddGratitudeData(hashMap);
                    Log.e("share req", "share req data>>>>>" + hashMap);
                    saveAddGratitudeDataForShare1(hashMap, shareFile,dialog1,globalmMyAchievementsListInnerModel);

                } else {
                    Toast.makeText(getActivity(), "Please enter gratitude name", Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            if (!edtGratitudeName.getText().toString().equals("")) {
                HashMap<String, Object> requestHash = new HashMap<>();
                if (chkSetReminder.isChecked()) {
                    globalmMyAchievementsListInnerModel.setAchievement(journalName);
                    // globalmMyAchievementsListInnerModel.setDescription(edtGrateful.getText().toString());
                    globalmMyAchievementsListInnerModel.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);
                    globalmMyAchievementsListInnerModel.setPushNotification(true);

                    if (!ANNUAL_CROP_PATH.equals("")) {
                        globalmMyAchievementsListInnerModel.setPictureDevicePath(ANNUAL_CROP_PATH);
                    } else {
                        try {
                            globalmMyAchievementsListInnerModel.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    requestHash.put("model", globalmMyAchievementsListInnerModel);
                    requestHash.put("Key", Util.KEY);
                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                } else {
                    // MyAchievementsListInnerModel globalmMyAchievementsListInnerModel = new MyAchievementsListInnerModel();
                    globalmMyAchievementsListInnerModel.setId(globalmMyAchievementsListInnerModel.getId());
                    globalmMyAchievementsListInnerModel.setUserFolderId(userfolderid);

                    globalmMyAchievementsListInnerModel.setAchievement(journalName);
                    //globalmMyAchievementsListInnerModel.setDescription(edtGrateful.getText().toString());
                    globalmMyAchievementsListInnerModel.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                    globalmMyAchievementsListInnerModel.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                    globalmMyAchievementsListInnerModel.setStatus(false);
                    globalmMyAchievementsListInnerModel.setIsDeleted(false);
                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);
                    globalmMyAchievementsListInnerModel.setPushNotification(false);

                    if (!ANNUAL_CROP_PATH.equals("")) {
                        globalmMyAchievementsListInnerModel.setPictureDevicePath(ANNUAL_CROP_PATH);
                    } else {
                        try {
                            globalmMyAchievementsListInnerModel.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    requestHash.put("model", globalmMyAchievementsListInnerModel);
                    requestHash.put("Key", Util.KEY);
                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                }

                //saveAddGratitudeData(requestHash);
                Log.e("share req", "share req data 2>>>>>" + requestHash);
                saveAddGratitudeDataForShare1(requestHash, shareFile,dialog1,globalmMyAchievementsListInnerModel);

            } else {
                Toast.makeText(getActivity(), "Please enter gratitude name", Toast.LENGTH_SHORT).show();
            }

        }


    }
    public void saveAddGratitudeDataForShare1(HashMap<String, Object> reqhash, File shareFile,Dialog dialog1,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        new MyAsyncTask1().execute();
        if (Util.checkConnection(getActivity())) {

            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        progressDialog.setProgress(percentage);
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
            progressDialog.show();

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    progressDialog.setProgress(100);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {


                                    new MyAsyncTask().execute();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            showpopup(dialog1,shareFile);
                                        }

                                    }, 700);
                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 400);

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Log.e("callnonetwork", "callnonetwork-8");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    public Bitmap getScreenShot1(RelativeLayout view) {
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
    private void makeSaveButtonGreen() {
        somethingChanges = true;
        rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
    }

    private void deleteGratitude(Dialog dialog1,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        new MyAsyncTask2().execute(globalmMyAchievementsListInnerModel.getId());
        if (Util.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("ReverseBucketID", globalmMyAchievementsListInnerModel.getId());
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.deleteAchievement(hashMap);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            showpopup(dialog1);

                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    progressDialog.dismiss();


                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-7");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    private void saveAddGratitudeData1(HashMap<String, Object> reqhash,Dialog dialog1,MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        new MyAsyncTask1().execute();

        if (Util.checkConnection(getActivity())) {

            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            if (mFile != null) {
                fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {

                        progressDialog.setProgress(percentage);
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
            progressDialog.show();

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    progressDialog.setProgress(100);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {


                                    new MyAsyncTask1().execute();

                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            showpopup1(dialog1);
                                        }

                                    }, 700);


                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 400);

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {
            Log.e("callnonetwork", "callnonetwork-6");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    private void deleteImage(MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {

        if (Util.checkConnection(getActivity())) {
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> requestHash = new HashMap<>();
            requestHash.put("model", globalmMyAchievementsListInnerModel);
            requestHash.put("Key", Util.KEY);
            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievement(requestHash);
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, Response<AddUpdateMyAchievementModel> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().getSuccessFlag()) {
                            Toast.makeText(getActivity(), "Image successfully deleted", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-5");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    private void getValueListFromApi(MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));


            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity(),sharedPreference.read("jwt", ""));
            Call<MyValueListResponse> serverCall = finisherService.getValueListApi(hashReq);
            serverCall.enqueue(new Callback<MyValueListResponse>() {
                @Override
                public void onResponse(Call<MyValueListResponse> call, Response<MyValueListResponse> response) {
                    progressDialog.dismiss();

                    Log.e("success", "su");
                    if (response.body() != null) {
                        MyValueListResponse lstData = response.body();
                        if (lstData.getDetails() != null && lstData.getDetails().size() > 0) {
                            for (int i = 0; i < 3; i++) {
                                if (i == 0) {
                                    txtGoalValue1.setText(lstData.getDetails().get(i).getValue().toUpperCase());
                                } else if (i == 1) {
                                    txtGoalValue2.setText(lstData.getDetails().get(i).getValue().toUpperCase());
                                } else if (i == 2) {
                                    txtGoalValue3.setText(lstData.getDetails().get(i).getValue().toUpperCase());
                                }
                            }
                        }

                    }
                }

                @Override
                public void onFailure(Call<MyValueListResponse> call, Throwable t) {
                    Log.e("error", "er");
                    progressDialog.dismiss();
                }
            });
        } else {
            Log.e("callnonetwork", "callnonetwork-4");
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }
    private void pickImageFromGallery1(MyAchievementsListInnerModel globalmMyAchievementsListInnerModel) {
        final Dialog dlg = new Dialog(getActivity());
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dlg.getWindow().getDecorView().setBackgroundResource(android.R.color.transparent);
        dlg.setContentView(R.layout.dialog_browse_bottom);

        Window window = dlg.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        WindowManager.LayoutParams wlp = window.getAttributes();

        wlp.gravity = Gravity.BOTTOM;
        //wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        RelativeLayout rlGal = (RelativeLayout) dlg.findViewById(R.id.rlGal);
        RelativeLayout rlCancel = (RelativeLayout) dlg.findViewById(R.id.rlCancel);
        RelativeLayout rlCam = (RelativeLayout) dlg.findViewById(R.id.rlCam);
        rlCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                somethingChanges = true;
                dlg.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission()) {

//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        out = createFolder1();
//                        imgPath = out.getAbsolutePath();
//                        Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                        openCam();

                    } else {
                        if (!Settings.System.canWrite(getActivity())) {
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.CAMERA}, 203);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 203);
                            }
                        }
                    }

                } else {
                    openCam();
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
                somethingChanges = true;
                dlg.dismiss();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasGalleryPermission()) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                    } else {
                        if (!Settings.System.canWrite(getActivity())) {
//                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 200);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
                            }
                        }
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
                }

            }
        });
        dlg.show();

    }
    private File createFolder1() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Squad");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(getActivity(), "Failed to create directory SQUAD  Directory.",
                        Toast.LENGTH_LONG).show();

                Log.d("Laylam Path Create", "Prep UP");
                return null;

            }
        }


        // Create a media file name

        // For unique file name appending current timeStamp with file name
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());

        File mediaFile;

        // For unique video file name appending current timeStamp with file name
        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_" + timeStamp + ".jpg");
    /*mediaFile = new File(mediaStorageDir.getPath() + File.separator +
            "test" +".mp3");*/

        return mediaFile;


    }
    private void cropPhoto1(final Bitmap bitmap, final String imgPath) {
        final Dialog dialogcrop = new Dialog(getActivity(), android.R.style.Theme);
        dialogcrop.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogcrop.setContentView(R.layout.cropdialogimage);
        dialogcrop.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        TextView txtDone = (TextView) dialogcrop.findViewById(R.id.txtDone);
        TextView txtCancel = (TextView) dialogcrop.findViewById(R.id.txtCancel);
        cropImageView = (CropImageView) dialogcrop.findViewById(R.id.CropImageView);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Bitmap myBitmap = null;

        File file = new File(imgPath);
        long bitmapLength = file.length();
        Log.e("bitmapsize", "img size>>>>>>>>>" + bitmapLength);
        if (bitmapLength > 30000000) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
            builder1.setMessage("Image Is Too Large");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
            //  Toast.makeText(getContext(), "Image Is Too Large", Toast.LENGTH_SHORT).show();
            return;
        }

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
                imgJournalLoadingBar.setVisibility(View.VISIBLE);

                try {
                    // TODO Auto-generated method stub
                    edtGratitudeName.setMaxHeight(800);//////////////////////////////
                    imgGratitudeMain.setVisibility(View.VISIBLE);
                    imgGratitudeMainCard.setVisibility(View.VISIBLE);
                    fram_.setVisibility(View.VISIBLE);
                    imgJournalLoadingBar.setVisibility(View.GONE);
                    bitimage = cropImageView.getCroppedImage();
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    dialogcrop.dismiss();
                    String cropPath = storeImage(bitimage);
                    preaprePictureForUpload1(cropPath);

                } catch (Exception e) {
                    e.printStackTrace();
                    imgJournalLoadingBar.setVisibility(View.GONE);
                }
            }
        });

        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    if (bitimage != null) {
                        // Set the bitmap to null
                        bitimage.recycle();
                        bitimage = null;
                    }
                    dialogcrop.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        dialogcrop.show();
    }
    private String storeImage1(Bitmap image) {
        File pictureFile = getOutputMediaFile1();
        if (pictureFile == null) {
            Log.d("TAG",
                    "Error creating media file, check storage permissions: ");// e.getMessage());
            return "";
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            image.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.getAbsolutePath();
    }
    private File getOutputMediaFile1() {
        // To be safe, you should check that the SDCard is mounted
        File mediaStorageDir = Util.getFile(getContext()); //added by jyotirmoy patra
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }
    private void preaprePictureForUpload1(String cropPath) {
//        try {
//            File file = new File(cropPath);
//            FileInputStream imageInFile = new FileInputStream(file);
//            byte[] imageData = new byte[(int) file.length()];
//            imageInFile.read(imageData);
//            stringImg = encodeImage(imageData);
//            Log.e("BASE64STRING", stringImg);
//            sharedPreference.write("GRATITUDEIMAGE", "", stringImg);
//            mFile = file;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        try {
            ContentResolver contentResolver = getActivity().getContentResolver();
            Uri contentUri = Uri.parse(cropPath);
            File file =getFileFromContentUri(contentResolver, contentUri);

            InputStream imageInFile =  contentResolver.openInputStream(Uri.parse(cropPath));
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            stringImg = encodeImage(imageData);
            Log.e("BASE64STRING", stringImg);
            sharedPreference.write("GRATITUDEIMAGE", "", stringImg);
            mFile = file;
            Log.e("mfile",""+mFile);

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            imgGratitudeMain.setBackgroundResource(0);
        }
       // imgGratitudeMain.setImageBitmap(BitmapFactory.decodeFile(cropPath));
        imgGratitudeMain.setImageURI(Uri.parse(cropPath));

        if (gratitudeStatus.equals("EDIT")) {
            //sendPicInfoToserver();
        }
        Log.e("ANNUAL_CROP_PATH", cropPath + ">>>>>>");

        ANNUAL_CROP_PATH = cropPath;

        /** Edited by Amit dated on 5/03/2020 **/

        String[] split_path = cropPath.split("/");
        ANNUAL_CROP_PATH = split_path[split_path.length - 1];

        //added by jyotirmoy->j7
        try {
            if (globalPickImageFromGratitudeShareDialog) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    imgBackgroundPicTOP.setBackgroundResource(0);
                }
                Log.e("add img direct", "add img direct---->" + cropPath);
//                imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(cropPath));
//                cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
//                imgBackgroundPicTOP.setVisibility(View.VISIBLE);
//                llAddPicTOP.setVisibility(View.GONE);
//                buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
//                if (strDialogSelectionType.equals("textOverPic")) {
//                    buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
//                }

                imgBackgroundPicTOP.setImageURI(Uri.parse(cropPath));
                cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
                imgBackgroundPicTOP.setVisibility(View.VISIBLE);
                llAddPicTOP.setVisibility(View.GONE);
                buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
                if (strDialogSelectionType.equals("textOverPic")) {
                    buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
                }

            }
        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    private void blink() {
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try {
                    Thread.sleep(timeToBlink);
                } catch (Exception e) {
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        if (imgSaveGratitude.getVisibility() == View.VISIBLE) {
                            imgSaveGratitude.setVisibility(View.INVISIBLE);
                        } else {
                            imgSaveGratitude.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }
    private class MyAsyncTask2 extends AsyncTask<Integer, Void, Void> {
        @Override
        protected Void doInBackground(Integer... params) {
            // This method runs in the background thread
            // You can perform any background tasks here
            Log.e(TAG, "calleddelete:" + "yes");
            Integer value_id = params[0];
            Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(getActivity()).gratitudeDao().deleteAllGratitudeNew_byid(value_id));



            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            //  getAchievementsList(1);
            Log.e("saved in database", "yes");

            // This method is called after the background task is completed
            // You can update the UI or do any post-processing here
        }
    }


    private void showpopup( Dialog previousDialog){
        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
        alertDialogCustom.ShowDialog("Efc", "Data successfully deleted", false);
        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
            @Override
            public void onDone(String title) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();

                pagenation_from_api=false;

                Util.isReloadTodayMainPage = true;
                openeditdialog="";
                previousDialog.dismiss();
                ((MainActivity) getActivity()).refershGamePoint(getActivity());
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);


            }

            @Override
            public void onCancel(String title) {

            }
        });

    }
    private void showpopup( Dialog previousDialog,File shareFile){
        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
        alertDialogCustom.ShowDialog("Efc", "Data successfully saved", false);
        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
            @Override
            public void onDone(String title) {

                // Preventing multiple clicks, using threshold of 1 second
                if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                pagenation_from_api=true;
                previousDialog.dismiss();
                openeditdialog="";

                ((MainActivity) getActivity()).refershGamePoint(getActivity());
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                Util.isReloadTodayMainPage = true;
                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);


                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                Log.e("FILE_PATH", shareFile.getPath() + ">>>>");
                Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" + ".fileprovider", shareFile);
                share.putExtra(Intent.EXTRA_STREAM, photoURI);
                startActivity(Intent.createChooser(share, "Share Image"));

            }

            @Override
            public void onCancel(String title) {

            }
        });

    }

    private void showpopup1( Dialog previousDialog){
        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
        alertDialogCustom.ShowDialog("Efc", "Data successfully saved", false);
        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
            @Override
            public void onDone(String title) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


                pagenation_from_api=true;
                previousDialog.dismiss();
                openeditdialog="";

                ((MainActivity) getActivity()).refershGamePoint(getActivity());
                ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                Util.isReloadTodayMainPage = true;
                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);


            }

            @Override
            public void onCancel(String title) {

            }
        });

    }

    private void getAchievementsList_(int page) {
        Log.i("check_eq","19");
        Log.e("call api", "yes");

        txtLoading.setVisibility(View.GONE);
        txtLoading.setText("loading...");
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("PageNo", page);
            hashReq.put("Count", 20);

            Call<MyAchievementsListModel> getGratitudeListModelCall = finisherService.getMyAchievevmentsList(hashReq);
            getGratitudeListModelCall.enqueue(new Callback<MyAchievementsListModel>() {
                @Override
                public void onResponse(Call<MyAchievementsListModel> call, Response<MyAchievementsListModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        Log.i("check_eq","22");
                        if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {
                            new MyAsyncTask().execute();
                            total_result = response.body().getTotalCount();
                            Log.i("total_result", String.valueOf(total_result));
                            total_page = response.body().getTotalCount() / 20;
                            Log.i("total_page", String.valueOf(total_page));
                            remaining = response.body().getTotalCount() % 20;
                            Log.i("remaining", String.valueOf(remaining));
                            if (remaining > 0) {
                                total_page = total_page + 1;
                            } else {
                            }
                            CurrentPage = page;
                            haveGrowthMinimumOne = true;

                            lstShowAll = response.body().getDetails();
                            // Log.e(TAG, "onResponse: " + lstShowAll.toString());
                            // loadData(lstShowAll);
                            Date dtToday = null, dtThisMonth = null, dtThreeMonth = null, dtOneYear = null, dtYesterDay = null;

                            Calendar calendar = Calendar.getInstance();
                            dtToday = calendar.getTime();
                            dtToday.setHours(0);
                            dtToday.setMinutes(0);
                            dtToday.setSeconds(0);

                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.DAY_OF_MONTH, 1);
                            dtThisMonth = calendar1.getTime();
                            dtThisMonth.setHours(0);
                            dtThisMonth.setMinutes(0);
                            dtThisMonth.setSeconds(0);

                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.add(Calendar.DATE, -90);
                            dtThreeMonth = calendar2.getTime();
                            dtThreeMonth.setHours(0);
                            dtThreeMonth.setMinutes(0);
                            dtThreeMonth.setSeconds(0);

                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.add(Calendar.DATE, -365);
                            dtOneYear = calendar3.getTime();
                            dtOneYear.setHours(0);
                            dtOneYear.setMinutes(0);
                            dtOneYear.setSeconds(0);

                            Calendar calendar4 = Calendar.getInstance();
                            calendar4.add(Calendar.DATE, -1);
                            dtYesterDay = calendar4.getTime();
                            dtYesterDay.setHours(0);
                            dtYesterDay.setMinutes(0);
                            dtYesterDay.setSeconds(0);


                            for (int i = 0; i < lstShowAll.size(); i++) {
                                if (lstShowAll.get(i).getCreatedAt() != null && !lstShowAll.get(i).getCreatedAt().equals("")) {

                                    Date dtCreatedAt = null;
                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";
                                        String strYesterDayDate = "";

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);
                                        strYesterDayDate = strDtaeFormat.format(dtYesterDay);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstShowAll.get(i).setDateValue("Today");
                                        } else if (strCreatedAt.equals(strYesterDayDate)) {
                                            lstShowAll.get(i).setDateValue("Yesterday");
                                        } else {
                                            lstShowAll.get(i).setDateValue(simpleDateFormat.format(format.parse(lstShowAll.get(i).getCreatedAt())));
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstToday.add(lstShowAll.get(i));
                                        }


                                        if ((dtCreatedAt.after(dtThisMonth) || dtCreatedAt.equals(dtThisMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThisMonth.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtThreeMonth) || dtCreatedAt.equals(dtThreeMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThreeMonthsAgo.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtOneYear) || dtCreatedAt.equals(dtOneYear)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstOneYearAgo.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getAchievement().contains("I am proud of")) {
                                            lstProudOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've accomplished")) {
                                            lstAccomplish.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've observed")) {
                                            lstObserved.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've learned")) {
                                            lstLearned.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've praised")) {
                                            lstPraised.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I've let go of")) {
                                            lstTodayletGoOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I dreamt of")) {
                                            lstDreamtOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Journal Entry")) {
                                            lstJournalEntry.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("The story I've told myself is")) {
                                            lstTheStory.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've been challenged by")) {
                                            lstChallengedBy.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("A small win I'm celebrating is")) {
                                            lstCelebrating.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I laughed")) {
                                            lstLaughed.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I'm Feeling")) {
                                            lstImFeeling.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getPicture() != null && !lstShowAll.get(i).getPicture().isEmpty()) {
                                            lstPicture.add(lstShowAll.get(i));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }



                            try {
                                GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);
                                boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
                                boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
                                boolShowImg = growthSaveFilterModel.getBool_show_img();



                                boolProudOf = growthSaveFilterModel.getBool_proud_of();
                                boolAccomplished = growthSaveFilterModel.getBool_accomplished();
                                boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
                                boolObserved = growthSaveFilterModel.getBool_observed();
                                boolLearned = growthSaveFilterModel.getBool_learned();
                                boolPraised = growthSaveFilterModel.getBool_praised();
                                boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
                                boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
                                boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

                                boolGratefulFor = growthSaveFilterModel.getBool_grateful_for(); //added by jyotirmoy->j14
                                boolWorkingTowards = growthSaveFilterModel.getBool_working_towards(); //added by jyotirmoy->j14
                                boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth(); //added by jyotirmoy->j14
                                boolCommittingTo = growthSaveFilterModel.getBool_committing_to(); //added by jyotirmoy->j14
                                boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day(); //added by jyotirmoy->j14
                                boolFoundGift = growthSaveFilterModel.getBool_found_gift(); //added by jyotirmoy->j15
                                boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today(); //added by jyotirmoy->j15
                                boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge(); //added by jyotirmoy->j15
                                boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();



                                boolTheStory = growthSaveFilterModel.getBool_the_story();
                                boolChallenged = growthSaveFilterModel.getBool_challenged();
                                boolCelebrating = growthSaveFilterModel.getBool_celebrating();
                                boolLaughed = growthSaveFilterModel.getBool_laughed();
                                boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
                                globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
                                filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            funRefresh();

                        } else {

                            haveGrowthMinimumOne = false;

                            txtLoading.setVisibility(View.GONE);
                            imgBigPlus.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<MyAchievementsListModel> call, Throwable t) {
                    progressDialog.dismiss();

                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-3");
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
    private void showpopup4( ){
        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
        alertDialogCustom.ShowDialog("Efc", "Prompt of the day option can't be selected in offline mode", false);
        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
            @Override
            public void onDone(String title) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                    return;
                }
                mLastClickTime = SystemClock.elapsedRealtime();


            }

            @Override
            public void onCancel(String title) {

            }
        });

    }



    private void saveAddGratitudeData_internet(HashMap<String, Object> reqhash,ArrayList<MyAchievementsListInnerModel> shared_getGratitudeListModelInner_nointernate ,File file) {
        Log.e("called_offline","called_offline3");
        new MyAsyncTask1().execute();
        SimpleDateFormat simpleDateFormat100 = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar100 = Calendar.getInstance();
        calendar100.set(Calendar.HOUR, 0);
        calendar100.set(Calendar.MINUTE, 0);
        calendar100.set(Calendar.SECOND, 0);

        if (Util.checkConnection(getActivity())) {

            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            if (null!=file) {
                fileBody = new ProgressRequestBody(file, new ProgressRequestBody.UploadCallbacks() {
                    @Override
                    public void onProgressUpdate(int percentage) {
                        progressDialog.setProgress(percentage);
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
            Log.e("called_offline","called_offline4");
            Call<AddUpdateMyAchievementModel> jsonObjectCall = finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<AddUpdateMyAchievementModel>() {
                @Override
                public void onResponse(Call<AddUpdateMyAchievementModel> call, final Response<AddUpdateMyAchievementModel> response) {

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            // progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {
                                    if(shared_getGratitudeListModelInner_nointernate.size()>0){
                                        for (int i = 0; i < shared_getGratitudeListModelInner_nointernate.size(); i++) {
                                            HashMap<String, Object> requestHash1 = new HashMap<>();
                                            requestHash1.put("model", shared_getGratitudeListModelInner_nointernate.get(i));
                                            requestHash1.put("Key", Util.KEY);
                                            requestHash1.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                                            byte[] decodedString = Base64.decode(shared_getGratitudeListModelInner_nointernate.get(i).getPicture(), Base64.DEFAULT);
                                            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                                            String cropPath = storeImage(bitmap);
                                            File file=null;
                                            if(null==cropPath){
                                                shared_getGratitudeListModelInner_nointernate.remove(i);
                                                Log.e("called_offline","called_offline2");
                                                saveAddGratitudeData_internet(requestHash1,shared_getGratitudeListModelInner_nointernate,file);
                                            }else{
                                                file = new File(cropPath);
                                                shared_getGratitudeListModelInner_nointernate.remove(i);
                                                Log.e("called_offline","called_offline2");
                                                saveAddGratitudeData_internet(requestHash1,shared_getGratitudeListModelInner_nointernate,file);
                                            }

                                        }


                                    }else{
                                        new MyAsyncTask().execute();
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                Util.lstShowAll_db_nointernate.clear();
                                                sharedPreference.write("my_list_nointernate", "", new Gson().toJson( Util.lstShowAll_db_nointernate));
                                                Util.lstShowAll_db_nointernate.clear();
                                                showpopup();

                                            }

                                        }, 700);

                                    }




                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 200);

                }

                @Override
                public void onFailure(Call<AddUpdateMyAchievementModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });
        } else {

        }

    }

    private void showpopup( ){

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("you'r online please refresh this page..") .setTitle("EFC");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //                                            new MyAsyncTask_().execute();

                        // Preventing multiple clicks, using threshold of 1 second
                        if (SystemClock.elapsedRealtime() - mLastClickTime < 6000) {
                            return;
                        }
                        mLastClickTime = SystemClock.elapsedRealtime();

                        pagenation_from_api=true;
                        ((MainActivity) getActivity()).refershGamePoint(getActivity());
                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                        Util.isReloadTodayMainPage = true;
                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);



                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();

    }

    private void getAchievementsList_firstcall(int page) {
        Log.i("check_eq","24");
        Log.e("call api", "yes");
        txtLoading.setVisibility(View.GONE);
        txtLoading.setText("loading...");
        lstShowAll.clear();
        lstToday.clear();
        lstThisMonth.clear();
        lstThreeMonthsAgo.clear();
        lstOneYearAgo.clear();
        lstProudOf.clear();
        lstAccomplish.clear();
        lstObserved.clear();
        lstLearned.clear();
        lstPraised.clear();
        lstTodayletGoOf.clear();
        lstDreamtOf.clear();
        lstJournalEntry.clear();
        lstTheStory.clear();
        lstChallengedBy.clear();
        lstCelebrating.clear();
        lstLaughed.clear();
        lstImFeeling.clear();
        lstPicture.clear();
        if (Connection.checkConnection(getActivity())) {

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("PageNo", page);
            hashReq.put("Count", 20);

            Call<MyAchievementsListModel> getGratitudeListModelCall = finisherService.getMyAchievevmentsList(hashReq);
            getGratitudeListModelCall.enqueue(new Callback<MyAchievementsListModel>() {
                @Override
                public void onResponse(Call<MyAchievementsListModel> call, Response<MyAchievementsListModel> response) {

                    if (response.body() != null) {
                        Log.i("check_eq","25");
                        if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {

                            total_result = response.body().getTotalCount();
                            Log.i("total_result", String.valueOf(total_result));
                            total_page = response.body().getTotalCount() / 20;
                            Log.i("total_page", String.valueOf(total_page));
                            remaining = response.body().getTotalCount() % 20;
                            Log.i("remaining", String.valueOf(remaining));
                            if (remaining > 0) {
                                total_page = total_page + 1;
                            } else {
                            }
                            CurrentPage = page;
                            haveGrowthMinimumOne = true;

                            lstShowAll = response.body().getDetails();
                            Log.e(TAG, "onResponse: " + lstShowAll.toString());
                            Date dtToday = null, dtThisMonth = null, dtThreeMonth = null, dtOneYear = null, dtYesterDay = null;

                            Calendar calendar = Calendar.getInstance();
                            dtToday = calendar.getTime();
                            dtToday.setHours(0);
                            dtToday.setMinutes(0);
                            dtToday.setSeconds(0);

                            Calendar calendar1 = Calendar.getInstance();
                            calendar1.set(Calendar.DAY_OF_MONTH, 1);
                            dtThisMonth = calendar1.getTime();
                            dtThisMonth.setHours(0);
                            dtThisMonth.setMinutes(0);
                            dtThisMonth.setSeconds(0);

                            Calendar calendar2 = Calendar.getInstance();
                            calendar2.add(Calendar.DATE, -90);
                            dtThreeMonth = calendar2.getTime();
                            dtThreeMonth.setHours(0);
                            dtThreeMonth.setMinutes(0);
                            dtThreeMonth.setSeconds(0);

                            Calendar calendar3 = Calendar.getInstance();
                            calendar3.add(Calendar.DATE, -365);
                            dtOneYear = calendar3.getTime();
                            dtOneYear.setHours(0);
                            dtOneYear.setMinutes(0);
                            dtOneYear.setSeconds(0);

                            Calendar calendar4 = Calendar.getInstance();
                            calendar4.add(Calendar.DATE, -1);
                            dtYesterDay = calendar4.getTime();
                            dtYesterDay.setHours(0);
                            dtYesterDay.setMinutes(0);
                            dtYesterDay.setSeconds(0);


                            for (int i = 0; i < lstShowAll.size(); i++) {
                                if (lstShowAll.get(i).getCreatedAt() != null && !lstShowAll.get(i).getCreatedAt().equals("")) {

                                    Date dtCreatedAt = null;
                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";
                                        String strYesterDayDate = "";

                                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE, dd MMMM yyyy");

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);
                                        strYesterDayDate = strDtaeFormat.format(dtYesterDay);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstShowAll.get(i).setDateValue("Today");
                                        } else if (strCreatedAt.equals(strYesterDayDate)) {
                                            lstShowAll.get(i).setDateValue("Yesterday");
                                        } else {
                                            lstShowAll.get(i).setDateValue(simpleDateFormat.format(format.parse(lstShowAll.get(i).getCreatedAt())));
                                        }

                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }


                                    try {
                                        dtCreatedAt = simpleDateFormatReq.parse(lstShowAll.get(i).getCreatedAt());

                                        String strCreatedAt = "";
                                        String strTodayDate = "";

                                        strCreatedAt = strDtaeFormat.format(dtCreatedAt);
                                        strTodayDate = strDtaeFormat.format(dtToday);

                                        if (strCreatedAt.equals(strTodayDate)) {
                                            lstToday.add(lstShowAll.get(i));
                                        }


                                        if ((dtCreatedAt.after(dtThisMonth) || dtCreatedAt.equals(dtThisMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThisMonth.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtThreeMonth) || dtCreatedAt.equals(dtThreeMonth)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstThreeMonthsAgo.add(lstShowAll.get(i));
                                        }

                                        if ((dtCreatedAt.after(dtOneYear) || dtCreatedAt.equals(dtOneYear)) && (dtCreatedAt.before(dtToday) || dtCreatedAt.equals(dtToday))) {
                                            lstOneYearAgo.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getAchievement().contains("I am proud of")) {
                                            lstProudOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've accomplished")) {
                                            lstAccomplish.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've observed")) {
                                            lstObserved.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've learned")) {
                                            lstLearned.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've praised")) {
                                            lstPraised.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I've let go of")) {
                                            lstTodayletGoOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I dreamt of")) {
                                            lstDreamtOf.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Journal Entry")) {
                                            lstJournalEntry.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("The story I've told myself is")) {
                                            lstTheStory.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I've been challenged by")) {
                                            lstChallengedBy.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("A small win I'm celebrating is")) {
                                            lstCelebrating.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("I laughed")) {
                                            lstLaughed.add(lstShowAll.get(i));
                                        }
                                        if (lstShowAll.get(i).getAchievement().contains("Today I'm Feeling")) {
                                            lstImFeeling.add(lstShowAll.get(i));
                                        }

                                        if (lstShowAll.get(i).getPicture() != null && !lstShowAll.get(i).getPicture().isEmpty()) {
                                            lstPicture.add(lstShowAll.get(i));
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }



                            try {
                                GrowthSaveFilterModel growthSaveFilterModel = SharedPreference.getSavedObjectFromPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", GrowthSaveFilterModel.class);
                                boolIChooseToKnow = growthSaveFilterModel.getBool_I_choose_To_Know();
                                boolOnlyPic = growthSaveFilterModel.getBool_only_pic();
                                boolShowImg = growthSaveFilterModel.getBool_show_img();



                                boolProudOf = growthSaveFilterModel.getBool_proud_of();
                                boolAccomplished = growthSaveFilterModel.getBool_accomplished();
                                boolNeedToWork = growthSaveFilterModel.getBool_needTowork();
                                boolObserved = growthSaveFilterModel.getBool_observed();
                                boolLearned = growthSaveFilterModel.getBool_learned();
                                boolPraised = growthSaveFilterModel.getBool_praised();
                                boolLetGoOf = growthSaveFilterModel.getBool_let_go_of();
                                boolDreamtOf = growthSaveFilterModel.getBool_dreamt_of();
                                boolJournalEntry = growthSaveFilterModel.getBool_journal_entry();

                                boolGratefulFor = growthSaveFilterModel.getBool_grateful_for(); //added by jyotirmoy->j14
                                boolWorkingTowards = growthSaveFilterModel.getBool_working_towards(); //added by jyotirmoy->j14
                                boolFeelingGrowth = growthSaveFilterModel.getBool_feeling_growth(); //added by jyotirmoy->j14
                                boolCommittingTo = growthSaveFilterModel.getBool_committing_to(); //added by jyotirmoy->j14
                                boolPromptOfTheDay = growthSaveFilterModel.getBool_prompt_of_The_day(); //added by jyotirmoy->j14
                                boolFoundGift = growthSaveFilterModel.getBool_found_gift(); //added by jyotirmoy->j15
                                boolFeltHappyToday = growthSaveFilterModel.getBool_felt_happy_today(); //added by jyotirmoy->j15
                                boolIAcknowledge = growthSaveFilterModel.getBool_I_acknowledge(); //added by jyotirmoy->j15
                                boolIAppreciate = growthSaveFilterModel.getBool_I_Appreciate();



                                boolTheStory = growthSaveFilterModel.getBool_the_story();
                                boolChallenged = growthSaveFilterModel.getBool_challenged();
                                boolCelebrating = growthSaveFilterModel.getBool_celebrating();
                                boolLaughed = growthSaveFilterModel.getBool_laughed();
                                boolTodayImFeeling = growthSaveFilterModel.getBool_Today_I_M_Feeling();
                                globalSearchStr = growthSaveFilterModel.getStrGrowthSerach();
                                filterSelectedvalue = growthSaveFilterModel.getSelected_date_range_filter_value().intValue();


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            funRefresh();

                        } else {

                            haveGrowthMinimumOne = false;

                            txtLoading.setVisibility(View.GONE);
                            imgBigPlus.setVisibility(View.VISIBLE);
                        }

                    }
                }

                @Override
                public void onFailure(Call<MyAchievementsListModel> call, Throwable t) {
                    //  progressDialog.dismiss();

                }
            });

        } else {
            Log.e("callnonetwork", "callnonetwork-1");
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }






    private void showFilterResults(GrowthSaveFilterModel growthSaveFilterModel){
        Log.i("Log_Filter1","0");
        myAchievementsListAdapter = new MyAchievementsListAdapter(getActivity(), MyAchievementsFragment.this);
        String json= sharedPreference.read("my_list_eq_filter", "");
        ArrayList<GrowthSaveFilterModel> growthSaveFilterModellist = new Gson().fromJson(json, new TypeToken<ArrayList<GrowthSaveFilterModel>>(){}.getType());
        Log.i("Log_Filter","1");
        String userid=sharedPreference.read("UserID", "");
        Log.i("Log_Filter",userid);
        growthSaveFilterModelArrayList.clear();
        Log.i("Log_Filter","2");
        recyclerGratitudes.setAdapter(myAchievementsListAdapter);

        LinearLayoutManager layoutManager_all = new LinearLayoutManager(getActivity()) {

            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        recyclerGratitudes.setLayoutManager(layoutManager_all);
        /*added by sahenita*/
        if (!"".equalsIgnoreCase(sharedPreference.read("total_result_db", ""))) {
            total_result_db = Integer.parseInt(sharedPreference.read("total_result_db", ""));
            remaining_db = Integer.parseInt(sharedPreference.read("remaining_db", ""));
            total_page_db = Integer.parseInt(sharedPreference.read("total_page_db", ""));
            CurrentPage_db = Integer.parseInt(sharedPreference.read("CurrentPage_db", ""));
            Log.e(TAG, "total_result_db0: " + total_result_db);

        }

        scrollListener = new EndlessRecyclerViewScrollListener(layoutManager_all) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Log.e(TAG, "total_result_db2: " + total_result_db);
                Log.e(TAG, "current_page_db: " + CurrentPage_db); ///
                Log.e(TAG, "pagenation_from_api: " + pagenation_from_api);
                Log.e(TAG, "total_page_db: " + total_page_db);

                if (pagenation_from_api) {
                    Page = CurrentPage + 1;
                    Log.e(TAG, "onScrollChange: " + Page);

                    getAchievementsList_loadmore(Page);

                } else {

                    Log.e(TAG, "total_result_db1: " + total_result_db);

                    CurrentPage_db = CurrentPage_db + 1;//1
                    if (CurrentPage_db < total_page_db) {
                        position = position + count;
                        Log.e(TAG, "total_result_db2: " + total_result_db);
                        Log.e(TAG, "position_db2: " + position);
                        Log.e(TAG, "current_page_db: " + CurrentPage_db); ///
//                                Log.e(TAG, "count_db2: " + count);
                        int remaining = total_result_db - position;
                        Log.e(TAG, "remaining_db2:: " + remaining);
                        if (remaining > 0) {
                            // Log.e(TAG, "remaining_db2:: " + remaining);
                            getAchievementsList_loadmore_for_db(position, count);
                        }
                    }
                }

            }
        };


        recyclerGratitudes.addOnScrollListener(scrollListener);
        recyclerGratitudes.setAdapter(myAchievementsListAdapter);



        // imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);

        // sharedPreference.write("achivementfilter", "", "yes");

        Util.achivementfilter="yes";
        List<MyAchievementsListInnerModel> lstShowResults = new ArrayList<>();

        // growthSaveFilterModel.setBool_showimage(boolShowPicture);
        growthSaveFilterModel.setUserid(userid);
        growthSaveFilterModel.setBool_proud_of(boolProudOf);
        growthSaveFilterModel.setBool_accomplished(boolAccomplished);
        growthSaveFilterModel.setBool_I_choose_To_Know(boolIChooseToKnow);
        growthSaveFilterModel.setBool_only_pic(boolOnlyPic);
        growthSaveFilterModel.setBool_show_img(boolShowImg);



        growthSaveFilterModel.setBool_needTowork(boolNeedToWork);
        growthSaveFilterModel.setBool_observed(boolObserved);
        growthSaveFilterModel.setBool_learned(boolLearned);
        growthSaveFilterModel.setBool_praised(boolPraised);
        growthSaveFilterModel.setBool_let_go_of(boolLetGoOf);
        growthSaveFilterModel.setBool_dreamt_of(boolDreamtOf);
        growthSaveFilterModel.setBool_journal_entry(boolJournalEntry);

        growthSaveFilterModel.setBool_grateful_for(boolGratefulFor);//add jyotirmoy->j14
        growthSaveFilterModel.setBool_working_towards(boolWorkingTowards);//add jyotirmoy->j14
        growthSaveFilterModel.setBool_feeling_growth(boolFeelingGrowth);//add jyotirmoy->j14
        growthSaveFilterModel.setBool_committing_to(boolCommittingTo);//add jyotirmoy->j14
        growthSaveFilterModel.setBool_prompt_of_The_day(boolPromptOfTheDay);//add jyotirmoy->j14
        growthSaveFilterModel.setBool_found_gift(boolFoundGift);//add jyotirmoy->j15
        growthSaveFilterModel.setBool_felt_happy_today(boolFeltHappyToday);//add jyotirmoy->j15
        growthSaveFilterModel.setBool_I_acknowledge(boolIAcknowledge);//add jyotirmoy->j15
        growthSaveFilterModel.setBool_I_Appreciate(boolIAppreciate);


        growthSaveFilterModel.setBool_the_story(boolTheStory);
        growthSaveFilterModel.setBool_challenged(boolChallenged);
        growthSaveFilterModel.setBool_celebrating(boolCelebrating);
        growthSaveFilterModel.setBool_laughed(boolLaughed);
        growthSaveFilterModel.setBool_Today_I_M_Feeling(boolTodayImFeeling);

        if (!growthSaveFilterModel.getBool_proud_of() && !growthSaveFilterModel.getBool_accomplished() &&!growthSaveFilterModel.getBool_needTowork()&& !growthSaveFilterModel.getBool_observed() && !growthSaveFilterModel.getBool_learned() && !growthSaveFilterModel.getBool_praised() && !growthSaveFilterModel.getBool_let_go_of() && !growthSaveFilterModel.getBool_dreamt_of() && !growthSaveFilterModel.getBool_journal_entry() && !growthSaveFilterModel.getBool_the_story() && !growthSaveFilterModel.getBool_challenged() && !growthSaveFilterModel.getBool_celebrating() && !growthSaveFilterModel.getBool_laughed() && !growthSaveFilterModel.getBool_Today_I_M_Feeling() && growthSaveFilterModel.getSelected_date_range_filter_value() == 0 && growthSaveFilterModel.getStrGrowthSerach().equals("")
                && !growthSaveFilterModel.getBool_working_towards() && !growthSaveFilterModel.getBool_found_gift() && !growthSaveFilterModel.getBool_I_acknowledge() && !growthSaveFilterModel.getBool_felt_happy_today()
                && !growthSaveFilterModel.getBool_prompt_of_The_day()&& !growthSaveFilterModel.getBool_feeling_growth()&& !growthSaveFilterModel.getBool_committing_to()
                && !growthSaveFilterModel.getBool_grateful_for()&& !growthSaveFilterModel.getBool_I_Appreciate()&& !growthSaveFilterModel.getBool_I_choose_To_Know()&& !growthSaveFilterModel.getBool_only_pic()&&growthSaveFilterModel.getBool_show_img()) {
            Util.achivementfilter="";
            Log.i("boolvalue","1");
            sharedPreference.write("achivementfilter", "", "");

            imgFilterAchieve.setImageResource(R.drawable.mbhq_filter);
        } else {
            Log.i("boolvalue","2");
            sharedPreference.write("achivementfilter", "", "yes");

            Util.achivementfilter="yes";
            imgFilterAchieve.setImageResource(R.drawable.mbhq_filter_green);
        }

        Log.i("Log_Filter","3");
        if(null!=growthSaveFilterModellist && growthSaveFilterModellist.size()>0){
            Log.i("Log_Filter","4");
            Log.i("Log_Filter","size of "+String.valueOf(growthSaveFilterModellist.size()));
            for(int i =0;i<growthSaveFilterModellist.size();i++){
                Log.i("Log_Filter","5");
                if(!userid.equalsIgnoreCase(growthSaveFilterModellist.get(i).getUserid())){
                    Log.i("Log_Filter","6");
                    growthSaveFilterModelArrayList.add(growthSaveFilterModellist.get(i));
                }
            }
            Log.i("Log_Filter","7");
            growthSaveFilterModelArrayList.add(growthSaveFilterModel);
            Log.i("Log_Filter","size of 2 "+String.valueOf(growthSaveFilterModelArrayList.size()));
            Log.i("Log_Filter","8");
            sharedPreference.write("my_list_eq_filter", "", new Gson().toJson(growthSaveFilterModelArrayList));

        }else{
            Log.i("Log_Filter","9");
            growthSaveFilterModelArrayList.add(growthSaveFilterModel);
            Log.i("Log_Filter","10");
            Log.i("Log_Filter","size of 3 "+String.valueOf(growthSaveFilterModelArrayList.size()));

            sharedPreference.write("my_list_eq_filter", "", new Gson().toJson(growthSaveFilterModelArrayList));

        }



        SharedPreference.saveObjectToSharedPreference(getActivity(), "GROWTH_FILTER_SAVE_FILE", "GROWTH_FILTER_SAVE_KEY", growthSaveFilterModel);

        pagenation_from_api=true;
        Page=1;
        total_page=0;
        total_result=0;
        CurrentPage=0;
        getAchievementsList_filter(Page);
    }

    private void clearFilterResults(GrowthSaveFilterModel growthSaveFilterModel, TextView txtSelectDate){

        filterSelectedvalue = 0;
        setAllImageViewUnchecked();
        makeAllBoolFalse();
        makeAllTYPEBoolFalse();
        boolDateSelected = false;
        txtSelectDate.setText("SELECT DATE");
        growthSaveFilterModel.setBool_I_Appreciate(false);
        growthSaveFilterModel.setBool_I_choose_To_Know(false);
        growthSaveFilterModel.setBool_only_pic(false);

        growthSaveFilterModel.setBool_proud_of(false);
        growthSaveFilterModel.setBool_accomplished(false);
        growthSaveFilterModel.setBool_needTowork(false);
        growthSaveFilterModel.setBool_observed(false);
        growthSaveFilterModel.setBool_learned(false);
        growthSaveFilterModel.setBool_praised(false);
        growthSaveFilterModel.setBool_let_go_of(false);
        growthSaveFilterModel.setBool_dreamt_of(false);
        growthSaveFilterModel.setBool_journal_entry(false);
        growthSaveFilterModel.setBool_Today_I_M_Feeling(false);
        growthSaveFilterModel.setBool_the_story(false);
        growthSaveFilterModel.setBool_challenged(false);
        growthSaveFilterModel.setBool_celebrating(false);
        growthSaveFilterModel.setBool_laughed(false);
        growthSaveFilterModel.setSelected_date_range_filter_value(0);
        growthSaveFilterModel.setStrGrowthSerach("");

    }


    public interface OnItemClickListener {
        void onItemClick_add(int p,int position,String filename,Boolean check);
        void onItemClick_remove(int p,int position,String filename,Boolean check);

    }



    private void moveEQFolder(Integer Id,int ReverseBucketListId) {

        if (Connection.checkConnection(getActivity())) {
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserEqFolderId", Id);
            hashReq.put("ReverseBucketListId", ReverseBucketListId);


            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity(),sharedPreference.read("jwt", ""));

            Call<JsonObject> jsonObjectCall = finisherService.moveEqfolder(hashReq);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
void init(){
    imgHabits.setImageResource(0);
    imgHabits.setImageResource(R.drawable.mbhq_habits_inactive);
    imgGratitude.setImageResource(0);
    imgGratitude.setImageResource(R.drawable.mbhq_gratitude_active);

    Log.e("state11111111111","onActivityCreated");
    ////////////////////////////////////////////// Added by mugdho///////////////////////////////////////////
    factoryForGratitude1 = Injection.provideViewModelFactoryGratitude(getActivity());
    gratitudeViewModel1 = new ViewModelProvider(this, factoryForGratitude1).get(GratitudeViewModel.class);
    //////////////////////////////////////////////////////////////////////////////////////////////////////////

//    if("yes".equalsIgnoreCase(Util.opengratitudeforfirstuser)){
//        Log.e("state11111111111","onActivityCreated");
//        Log.i("check_eq","23");
//        Util.opengratitudeforfirstuser="";
//        Log.e("state","onActivityCreated1");
//        pagenation_from_api=true;
//        getAchievementsList_firstcall(Page);
//    }
    Util.opengratitudeforfirstuser="";
    factoryForGratitude = Injection.provideViewModelFactoryGrowth(getActivity());
    gratitudeViewModel = new ViewModelProvider(this, factoryForGratitude).get(GrowthViewModel.class);

    ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    if (!isConnected) {
        Log.i("check_eq","32");
        // Internet connection is lost
        Log.e("state11111111111","onActivityCreated");
        pagenation_from_api=false;
        calldatabase_nointernate(position, count);
    }else{
        Log.e("state11111111111","onActivityCreated");
        Log.i("check_eq","26");
        if (getArguments() != null && getArguments().containsKey("NOTIFICATIONID")) {
            Log.i("check_eq","27");
            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
            getSelectedAchievement(getArguments().getInt("NOTIFICATIONID"), "", false);
        } else {
            Log.i("check_eq","28");
            if (getArguments() != null && getArguments().containsKey("ADD_GROWTH_FROM_TODAY")) {
                Log.i("check_eq","29");
            } else if (getArguments() != null && getArguments().containsKey("openaddpopup")) {
                Log.i("check_eq","30");
                Log.e("state11111111111","onActivityCreated");
                checkGratitudeCacheExpiration();
                arrAchieveOptions.add("I'm Grateful For:");
                arrAchieveOptions.add("Journal Entry:");

                arrAchieveOptions.add(Util.prompt1 );

                arrAchieveOptions.add("Today I'm Feeling:");
                arrAchieveOptions.add("I am proud of:");
                arrAchieveOptions.add("I've accomplished:");
                arrAchieveOptions.add("I'm working towards:");
                arrAchieveOptions.add("I'm feeling growth in:");
                arrAchieveOptions.add("I’m committing to:");
                arrAchieveOptions.add("A small win I'm celebrating is:");
                arrAchieveOptions.add("I've found the gift in:");
                arrAchieveOptions.add("I felt happy today because:");

                arrAchieveOptions.add("The story I've told myself is:");
                arrAchieveOptions.add("I've been challenged by:");
                arrAchieveOptions.add("I need to work on/clear:");
                arrAchieveOptions.add("I've observed:");
                arrAchieveOptions.add("I've learned:");
                arrAchieveOptions.add("I acknowledge:");
                arrAchieveOptions.add("I laughed:");
                arrAchieveOptions.add("I've praised:");
                arrAchieveOptions.add("I've let go of:");
                arrAchieveOptions.add("I dreamt of:");
                arrAchieveOptions.add("One thing I appreciate about you today is:");
                arrAchieveOptions.add("I Choose to Know:");
                openDialogForAchieveAdd();

            } else if (getArguments() != null && getArguments().containsKey("openprompt")) {
                Log.e("state11111111111","--------5");
                Log.i("check_eq","30");
                checkGratitudeCacheExpiration();

                // GetJounalPromptofDay();

                ConnectivityManager connectivityManager1 = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork1 = connectivityManager1.getActiveNetworkInfo();
                boolean isConnected1 = activeNetwork1!= null && activeNetwork1.isConnectedOrConnecting();
                if (!isConnected1) {
                    // Internet connection is lost
                    showpopup4();
                }else{
                    sharedPreference.write("click_option", "", "click_promptoftheday");

                    arrAchieveOptions.add("I'm Grateful For:");
                    arrAchieveOptions.add("Journal Entry:");

                    arrAchieveOptions.add(Util.prompt1 );

                    arrAchieveOptions.add("Today I'm Feeling:");
                    arrAchieveOptions.add("I am proud of:");
                    arrAchieveOptions.add("I've accomplished:");
                    arrAchieveOptions.add("I'm working towards:");
                    arrAchieveOptions.add("I'm feeling growth in:");
                    arrAchieveOptions.add("I’m committing to:");
                    arrAchieveOptions.add("A small win I'm celebrating is:");
                    arrAchieveOptions.add("I've found the gift in:");
                    arrAchieveOptions.add("I felt happy today because:");

                    arrAchieveOptions.add("The story I've told myself is:");
                    arrAchieveOptions.add("I've been challenged by:");
                    arrAchieveOptions.add("I need to work on/clear:");
                    arrAchieveOptions.add("I've observed:");
                    arrAchieveOptions.add("I've learned:");
                    arrAchieveOptions.add("I acknowledge:");
                    arrAchieveOptions.add("I laughed:");
                    arrAchieveOptions.add("I've praised:");
                    arrAchieveOptions.add("I've let go of:");
                    arrAchieveOptions.add("I dreamt of:");
                    arrAchieveOptions.add("One thing I appreciate about you today is:");
                    arrAchieveOptions.add("I Choose to Know:");
                    openDialogForAchieveAddWithOption(2);

                }

            }else if (getArguments() != null && getArguments().containsKey("calledapi")) {
                Log.i("check_eq","31");
                Log.e("state11111111111","onActivityCreated");
                checkGratitudeCacheExpiration();

            } else {
                Log.i("check_eq","122");
                Log.e("state11111111111","onActivityCreated");
                checkGratitudeCacheExpiration();

            }
        }
    }
}



    private void getUserPaidStatusApiCall() {

        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("Email", sharedPreference.read("USEREMAIL", ""));
            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
            Call<GetUserPaidStatusModel> paidStatusModelCall = finisherService.getUserPaidStatusApi(hashReq);
            paidStatusModelCall.enqueue(new Callback<GetUserPaidStatusModel>() {
                @Override
                public void onResponse(Call<GetUserPaidStatusModel> call, Response<GetUserPaidStatusModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null && response.body().getSuccessFlag()) {

                        Integer accesstype=response.body().getMbhqAccessType();
                        Log.i("printttttttttttttttttttttttttttttt",String.valueOf(accesstype));
                        Log.i("111111",String.valueOf(accesstype));
                        Boolean HabitAccess=response.body().getHabitAccess();
                        Log.i("111111",String.valueOf(HabitAccess));
                        Boolean EqJournalAccess=response.body().getEqJournalAccess();
                        Log.i("111111",String.valueOf(EqJournalAccess));
                        Boolean MeditationAccess=response.body().getMeditationAccess();
                        Log.i("111111",String.valueOf(MeditationAccess));
                        Boolean ForumAccess=response.body().getForumAccess();
                        Log.i("111111",String.valueOf(ForumAccess));
                        Boolean LiveChatAccess=response.body().getLiveChatAccess();
                        Log.i("111111",String.valueOf(LiveChatAccess));
                        Boolean TestsAccess=response.body().getTestsAccess();
                        Log.i("111111",String.valueOf(TestsAccess));
                        Boolean CourseAccess=response.body().getCourseAccess();
                        Log.i("111111",String.valueOf(CourseAccess));
                        sharedPreference.write("accesstype", "", String.valueOf(accesstype));
                        sharedPreference.write("HabitAccess", "", String.valueOf(HabitAccess));
                        sharedPreference.write("EqJournalAccess", "", String.valueOf(EqJournalAccess));
                        sharedPreference.write("MeditationAccess", "", String.valueOf(MeditationAccess));
                        sharedPreference.write("ForumAccess", "", String.valueOf(ForumAccess));
                        sharedPreference.write("LiveChatAccess", "", String.valueOf(LiveChatAccess));
                        sharedPreference.write("TestsAccess", "", String.valueOf(TestsAccess));
                        sharedPreference.write("CourseAccess", "", String.valueOf(CourseAccess));
                        String accesstype1=sharedPreference.read("accesstype","");
                        String habit_access=sharedPreference.read("HabitAccess","");
                        String eq_access=sharedPreference.read("EqJournalAccess","");
                        String medi_access=sharedPreference.read("MeditationAccess","");
                        String forum_access=sharedPreference.read("ForumAccess","");
                        String Live_access=sharedPreference.read("LiveChatAccess","");
                        String Test_acess=sharedPreference.read("TestsAccess","");
                        String Course_access=sharedPreference.read("CourseAccess","");

                        Log.i("1111111100",eq_access);
                        Log.i("2222222200",medi_access);
                        Log.i("3333333300",accesstype1);
                        Log.i("4444444400",habit_access);
                        Log.i("5555555500",forum_access);
                        Log.i("6666666600",Live_access);
                        Log.i("7777777700",Test_acess);
                        Log.i("8888888800",Course_access);

                        if(null!=getActivity()){
                            pagenation_from_api=true;
                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                            Util.isReloadTodayMainPage = true;
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                        }


                    }
                }

                @Override
                public void onFailure(Call<GetUserPaidStatusModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }



}
