package com.ashysystem.mbhq.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.Notification;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;

import com.ashysystem.mbhq.Service.impl.SessionServiceImpl;
import com.ashysystem.mbhq.model.AddRewardModel;


import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.ashysystem.mbhq.model.eqfolder.UserEqFolder;
import com.ashysystem.mbhq.model.livechat.Chat;
import com.ashysystem.mbhq.model.livechat.Meditations;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.video.DemoApplication;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by android-krishnendu on 11/3/16.
 */

public class Util {

    public static long downloadFile(Context context, String fileUrl, String fileName, String wibiner) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(fileUrl));
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName);

        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        long downloadId = downloadManager.enqueue(request);
        //Toast.makeText(context,String.valueOf(downloadId),Toast.LENGTH_LONG).show();

        // Save the download ID if you want to track the download progress or fetch the downloaded file later
        // You can store it in SharedPreferences or a local database

        // For example, you can save it to SharedPreferences:
    /*SharedPreferences preferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = preferences.edit();
    editor.putLong("downloadId", downloadId);
    editor.putString("my_downloaded_medicine", wibiner);
    editor.apply();*/

        return downloadId;
    }
    public static String getFilePathFromUri(Context context, Uri uri) {
        String filePath = null;
        if (uri.getScheme().equals("content")) {
            ContentResolver contentResolver = context.getContentResolver();
            Cursor cursor = contentResolver.query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                if (columnIndex != -1) {
                    filePath = cursor.getString(columnIndex);
                }
                cursor.close();
            }
        } else if (uri.getScheme().equals("file")) {
            filePath = uri.getPath();
        }
        return filePath;
    }
    public static Date getInstallTime() {
        PackageManager packageManager = DemoApplication.getInstance().getApplicationContext().getPackageManager();
        return firstNonNull(installTimeFromPackageManager(packageManager, BuildConfig.APPLICATION_ID),
                apkUpdateTime(packageManager, BuildConfig.APPLICATION_ID));
    }
    public static Uri getDownloadedFileUri(Context context, long downloadId) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(downloadId);

        Cursor cursor = downloadManager.query(query);
        if (cursor != null && cursor.moveToFirst()) {
            int statusIndex = cursor.getColumnIndex(DownloadManager.COLUMN_STATUS);
            int status = cursor.getInt(statusIndex);

            if (status == DownloadManager.STATUS_SUCCESSFUL) {
                int uriIndex = cursor.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI);
                String localUriString = cursor.getString(uriIndex);

                if (localUriString != null) {
                    return Uri.parse(localUriString);
                }
            }
        }

        return null;
    }

    public static String prompt = "";
    public static String openliveplayer = "";
    public static String edittext = "";
    public static String opengratitudeforfirstuser = "";
    public static String backto = "";
    public static String clearMeditation_onpause = "yes";
    public static Chat chat = new Chat();
//
    public static ArrayList<Meditations> meditationsArrayList = new ArrayList<>();
    public static ArrayList<String> week = new ArrayList<String>();
    public static String prompt1 = "";
    public static String edittext1 = "";
    public static boolean pushnotification=false;
    public static boolean callhabitdatabase=false;
    public static boolean callhabitonly=true;
    public static String edittext2 = "";
    public static String edittext3 = "";
    public static String edittext4 = "";
    public static String edittext5 = "";
    public static String edittext6 = "";
    public static String edittext7 = "";
    public static String edittext8 = "";
    public static String edittext9 = "";
    public static String frompopularhabitlist = "";
    public static String backtotodayhabit = "";


    public static String calldstatic = "no";
    public static String session = "";
    public static String userid = "";
    public static String jwt = "";
    ///////////////VARIABLE FOR KNOWING ADD HABIT ENTRY POINT//////////////////

    public static final int FREE_USER = 1;

    //////////////////////////////////////////////////////////

    ///////////////VARIABLES FOR SHOW PARTICULAR MEDITATION/////////////
    public static final int TRIAL_USER = 2;
    public static final int SQUAD_LITE_USER = 3;

    ///////////////////////////////////////////////////////////////////

    ///////////////VARIABLES FOR SHOW PARTICULAR PROGRAM/////////////
    public static final int PAID_USER = 4;
    public static final int UNKNOWN_USER = 0;
    ///////////////////////////////////////////////////////////////////
    public static boolean HABIT_ADDED_FROM_TODAY_PAGE = false;
    public static boolean boolBackGroundServiceRunningMeditation = false;
    public static String strMeditationDetailsForBackground = "";
    public static boolean boolBackGroundServiceRunningProgram = false;
    public static Bundle bundleProgramDetailsForBackground = null;
    public static ArrayList<View> arrViewNu = new ArrayList<>();
    public static List<MeditationCourseModel.Webinar> withfilterlist_afterbackfrommeditationdetails = new ArrayList<>();
    public static String str_withfilterlist_afterbackfrommeditationdetails = "";
    public static List<MeditationCourseModel.Webinar> withfilterlist = new ArrayList<>();
    public static ArrayList<UserEqFolder> myModelArrayList = new ArrayList<>();
    public static ArrayList<Integer> checkfolder = new ArrayList<>();

    public static ArrayList<Fragment> arrViewNuFragment = new ArrayList<>();
    public static int arrPos = 0;
    public static boolean matchFound = false;
    public static String networkMsg = "Please check your network connection";
    public static Date globalDate = null;
    public static String mMealIngredientList = "";
    public static String globalUserFlagResponse = "";
    public static String achivementfilter = "";
    public static String sourcepage = "";

    /////MbhqTodayTwoFragment all checked
    public static final String TAG_UNCHECKED = "tag_uncheked";
    public static final String TAG_CROSS= "tag_cross";
    public static final String TAG_ALLCHECKED="tag_allchecked";

    public static ArrayList<MyAchievementsListInnerModel> lstShowAll_db_nointernate = new ArrayList<MyAchievementsListInnerModel>();

    ///////BOOLEAN FOR MAIN PAGE RELOADING//////////////
    public static boolean isNetworkAvailable = false;
    public static boolean isLocalUploadGrowthGratitideCalled = false;
    public static String COMMUNITY_SERVERURL = "https://forum.mindbodyhq.com/";

    public static SimpleExoPlayer globalExoplayer;
    ////////////////////////////////////////////////////

    // public static String BASE_URL = "http://devthelife.com/";
    public static String COMMUNITY_SERVERKEY = "bc7ffc2314225976c01dbf88ab88287e";
    //public static String ABBBC_BASE_URL = "http://dev1abbbcapi.thesquadtours.com/";
    //public static String ABBBC_BASE_URL = "http://192.168.2.140/ABBBC.API/";
    //public static String ABBBC_BASE_URL = "http://192.168.2.15/ABBBC.API/";
    //public static String ABBBC_BASE_URL = "http://192.168.18.5/ABBBC.API/";
    //public static String ABBBC_BASE_URL = "http://192.168.10.139/ABBBC.API/";
    /////////////////////////////////////////////
    public static String COMMUNITY_ACCESS_TOKEN = "";
    public static boolean isReloadTodayMainPage = true;
    public static boolean calledgratitudeafterinternatecomming = true;
    //public static String SERVERURL = "http://devthelife.com/";
    public static String strTodayPageSelectedDate = "";
    //    public static String SERVERURL = "https://www.thesquadtours.com/";
    //public static String SERVERURL = "http://192.168.1.3:52959/";
    //public static String SERVERURL = "http://192.168.10.139:52959/";
    //public static String SERVERURL = "http://192.168.2.139:52958/";
    //public static String SERVERURL = "http://192.168.168.18:52959/";
    //public static String SERVERURL = "http://192.168.10.140:52959/";
    //public static String SERVERURL = "http://192.168.10.15:52959/";
    /////////////////////////////////////////////////////////////
    public static int GRATITUDE_SAVE_OFFLINE_COUNTER = 0;

    ////////////////URL//////////////////////
    //public static String ABBBC_BASE_URL = "https://api.abbbconline.com/";
    public static String ABBBC_BASE_URL = "https://abbbcapi.thesquadtours.com/";  //main
    //public static String ABBBC_BASE_URL = "http://dev1api.abbbconline.com/";    //dev

//        public static String SERVERURL = "http://dev1.thesquadtours.com/";    //dev

//   public static String SERVERURL = "https://www.thesquadtours.com/";      //main
    public static String SERVERURL = "https://admin.emotionalfitnessclub.com/";      //main

//    public static String SERVERURL = "http://admin.mindbodyhq.com/";      //main

    //public static String SERVERURL="http://ashybines.life/";
    //public static String SERVERURL="http://192.168.2.15:52959/";
    //public static String SERVERURL="http://192.168.2.140:52959/";
    ////////////////END URL//////////////////////

    public static String KEY = "5CDAAEC0BCA74C70BF2FFA3E4B4E963E";
    public static String KEY_ABBBC = "1234";
    public static Boolean HabitAccess = true;
    public static Boolean EqJournalAccess =true;
    public static Boolean MeditationAccess = true;
    public static Boolean ForumAccess = true;
    public static Boolean LiveChatAccess = true;
    public static Boolean TestsAccess = true;
    public static Boolean CourseAccess = true;
    public static String accesstype = "";

    public static String MYRESOURCESURL = SERVERURL + "api/Resources";
    public static String USERLOGON = SERVERURL + "api/MbhqMember/MbHQLogon";
    public static String UPCOMINGWEBINARS = SERVERURL + "api/UpcomingWebinars";
    public static String UPCOMINGWEBINARSNEW = SERVERURL + "api/UpcomingWebinarsAbsolute";
    public static String ARCHIVEDWEBINARS = SERVERURL + "api/ArchivedWebinars";
    public static String ARCHIVEDWEBINARSNEW = SERVERURL + "api/GetArchivedWebinarsAbsolute";
    public static String PRESENTERLIST = SERVERURL + "api/GetPresenterList";
    public static String MOREPRESENTERLISTDETAILS = SERVERURL + "api/GetEventDetailsByType";
    public static String MYPROGRAMS = SERVERURL + "api/MyPrograms";
    public static String GETEVENTTYPELIST = SERVERURL + "api/GetEventTypeList";
    public static String GETARCHIEVEYEARS = SERVERURL + "api/GetArchiveYears";
    public static String GETEVENTTAGSLIST = SERVERURL + "api/GetEventTagList";
    public static String GETEVENTDETAILSBYID = SERVERURL + "api/GetEventDetailsByID";
    public static String TOGGLELIKEEVENT = SERVERURL + "api/ToggleEventLike";
    //public static String GETPROFILE = SERVERURL + "api/GetProfile";
    public static String GETPROFILE = SERVERURL + "api/MbhqMember/GetMbhqUserProfile";
    public static String EDITPROFILE = SERVERURL + "api/ManageProfile";
    public static String GETUSERNOTIFICATION = SERVERURL + "api/GetUserNotification";
    public static String INITIALIZEUSERNOTIFICATION = SERVERURL + "api/InitializeUserNotification";
    public static String SAVEUSERNOTIFICATION = SERVERURL + "api/SaveUserNotification";
    public static String TOGGLEWATCHLIST = SERVERURL + "api/ToggleWatchlist";
    public static String GETWATCHLIST = SERVERURL + "api/GetWatchlist";
    public static String SAVEVIEWVIDEOSTATUS = SERVERURL + "api/SaveViewVideoStatus";
    public static String DISCOVERABLEAPI = SERVERURL + "api/UserSettings";
    public static String YOUTUBEPLAYLISTURL = "https://www.googleapis.com/youtube/v3/playlists?part=snippet&channelId=UCsJg7i0b0U9xQ66gmUTXDiQ&key=AIzaSyARHKD8wyBpEg5NIIhBN8vdLaP6f5rC8Lw";
    public static String YOUTUBEPLAYLISTVIDEODETAILSURL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&key=AIzaSyARHKD8wyBpEg5NIIhBN8vdLaP6f5rC8Lw&maxResults=50&playlistId=";
    //public static String GETEXERCISES="http://dev1api.abbbconline.com/api/Exercise/GetExercises";
    public static String GETEXERCISES = ABBBC_BASE_URL + "api/Exercise/GetExercises";
    //public static String SearchExercises="http://dev1api.abbbconline.com/api/Exercise/SearchExercises";
    public static String SearchExercises = ABBBC_BASE_URL + "api/Exercise/SearchExercises";
    public static String GetSports = ABBBC_BASE_URL + "api/Exercise/GetSports";
    public static String GetGymClases = ABBBC_BASE_URL + "api/Exercise/GetGymClasses";
    /////////////////ARINDAM API////////////////
    public static String MESSAGELISTAPI = SERVERURL + "api/GetMessageList";
    public static String MESSAGEDETAILSAPI = SERVERURL + "api/GetMessageDetail";
    public static String MESSAGESENDAPI = SERVERURL + "api/SendMessageToFriend";

    //public static String WORKOUTLISTAPI=SERVERURL+"api/values/GetWorkoutDetails?userId=16&ExerciseSessionId=3167&personalisedOne=False";
    public static String SESSIONLISTAPI = SERVERURL + "api/values/GetExerciseSessions?userId=";
    //public static String SESSIONLISTAPI=SERVERURL+"api/values/GetExerciseSessions?userId=16&courseId=1&weekNum=1&category=1&sessionTypeId=1&sessionFlowId=2";
    public static String WORKOUTLISTAPI = SERVERURL + "api/values/GetStationDetails?userId=";
    public static String LOCATIONSAVEAPI = SERVERURL + "api/UpdateUserLocation";
    public static String GETUSERELOCATIONAPI = SERVERURL + "api/NearByUser";
    //public static String VIDEODOWNLOADBASEURL=ABBBC_BASE_URL+"exercise_videos/Mini/";
    public static String VIDEOOFFTIME = "www.dropbox.com/s/pjsgkrxxa50a0h1";
    /*public static String VIDEODOWNLOADBASEURL=ABBBC_BASE_URL+"exercise_videos/Mini/";
    public static String AUDIOWNLOADBASEURL=SERVERURL+"content/audio/";
    public static String AUDIOWNLOADMOTIVEBASEURL=SERVERURL+"content/audio/";*/
    //////////////NEW S3//////////////
    public static String VIDEODOWNLOADBASEURL = "https://s3-ap-southeast-2.amazonaws.com/squad-live/exercise-videos/mini/";
    public static String AUDIOWNLOADBASEURL = "https://s3-ap-southeast-2.amazonaws.com/squad-live/exercise-audio";
    public static String AUDIOWNLOADMOTIVEBASEURL = "https://s3-ap-southeast-2.amazonaws.com/squad-live/exercise-audio";
    ////////////NEW S3////////////
    ////////////////ARINDAM API///////////////
    public static String VISIONHOMEURL1 = "https://player.vimeo.com/external/125750379.m3u8?s=c85a3928071e47c744698cf66662e8543adcf3f8";
    public static String VISIONHOMEURL2 = "https://player.vimeo.com/external/125750377.m3u8?s=2bce1b1ce005b50c1098506cc28934b892d87145";
    public static String CHALLENGE_ID_1 = "4TuGo";
    public static String CHALLENGE_ID_2 = "TuN9a";
    //public static String GETRESPONSEAPI="https://api3.getresponse360.pl/v3/contacts";
    public static String GETRESPONSEAPI = "https://a.klaviyo.com/api/v2/list/MU78Rt/members?api_key=pk_0868fc3492c992a6014ba2c67227636404";
    public static int currentinDel = 0;
    public static int currentOutDel = 1;
    public static int REQUESTCODE = 500;
    public static int PLAY_PAUSE_REQUEST = 888;
    /*public static boolean cameraPermission = false;
    public static boolean galleryPermission = false;
    public static boolean writePermission = false;
    public static boolean readPermission = false;
    public static boolean locatePermission = false;*/
    public static boolean rangeChange = false;
    public static long distance = 0L;
    public static Double speed = 0.0;
    public static Long time = 0L;
    public static int HELP_TYPE_HOME = 0;
    public static int HELP_TYPE_SETTINGS = 1;
    public static int HELP_TYPE_HELP = 2;
    public static int HELP_TYPE_CONNECT = 3;
    public static int HELP_TYPE_TRAIN = 4;
    public static int HELP_TYPE_NOURISH = 5;
    public static int HELP_TYPE_LEARN = 6;
    public static int HELP_TYPE_ACHIEVE = 7;
    public static int HELP_TYPE_APPRECIATE = 8;
    public static int HELP_TYPE_TRACK = 9;
    public static int HELP_TYPE_8_WEEK = 10;
//    public static SimpleExoPlayer globalExoplayer; //need to check
    public static int[] HELP_ARRAY = new int[]{
            HELP_TYPE_HOME,
            HELP_TYPE_SETTINGS,
            HELP_TYPE_HELP,
            HELP_TYPE_CONNECT,
            HELP_TYPE_TRAIN,
            HELP_TYPE_NOURISH,
            HELP_TYPE_LEARN,
            HELP_TYPE_ACHIEVE,
            HELP_TYPE_APPRECIATE,
            HELP_TYPE_TRACK,
            HELP_TYPE_8_WEEK
    };
//    public static Facebook facebook;
    public static String FACEBOOK_INFO = "user_facebook_info";
//    public static TrackImageProfile trackImageProfile = null;
    public static Notification.Builder mNotifyBuilder;
    public static int AUDIO_NOTIFICATION_ID = 10005;
    public static SimpleDateFormat globalFormatWithOutT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String INTENT_ACTION_TOKEN_REFRESHED = "intent_action_token_refreshed";
    public static String INTENT_ACTION_BACKGROUND = "intent_action_token_BACKGROUND";
    public static String KEY_FCM_TOKEN = "key_token";
    ////////////////////////////////FITBIT///////////////////////
    public static String FITBIT_CLIENT_ID = "228G58";
    public static String FITBIT_CLIENT_SECRET = "f1b2521a60ee562a4b30bcbff2f4a48e";

    //////////////////circuit android////////////
    public static String FITBIT_CALLBACK_URL = "abbbcsquad://com.abbbc.squad";
    public static String FITBIT_URL = "https://www.fitbit.com/oauth2/authorize?response_type=token&expires_in=31536000&client_id=228G58&redirect_uri=abbbcsquad://com.abbbc.squad&scope=activity%20nutrition%20heartrate%20location%20nutrition%20profile%20settings%20sleep%20social%20weight";
    //////////////////////////////JAWBONE///////////////////////////
    public static int OAUTH_REQUEST_CODE = 25;
    // These are obtained after registering on Jawbone Developer Portal
    // Credentials used here are created for "Test-App1"
    public static String CLIENT_ID = "QNvNSu2bvO8";
    public static String CLIENT_SECRET = "2a9817d746a7fbbcd7ae45cac063d71f2a60a12a";

    /*private void cropPhoto(final Bitmap bitmap, final String imgPath)
    {
        final Dialog dialogcrop=new Dialog(DealDescriptionDialog.this);
        dialogcrop.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogcrop.setContentView(R.layout.cropdialogimage);
        dialogcrop.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        RelativeLayout crop = (RelativeLayout)dialogcrop.findViewById(R.id.cropbutton);
        cropImageView = (CropImageView)dialogcrop.findViewById(R.id.CropImageView);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Bitmap myBitmap=decodeSampledBitmapFromFile(imgPath,height-50,width);
        cropImageView.setImageBitmap(myBitmap);
        // cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES, DEFAULT_ASPECT_RATIO_VALUES);
        Log.e("Print Original height--->",bitmap.getHeight()+"");
        Log.e("Print original height--->",bitmap.getWidth()+"");
        cropImageView.setAspectRatio(80, 29);
        cropImageView.setFixedAspectRatio(true);


        crop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                bitimage = cropImageView.getCroppedImage();
                Log.e("Print height After crop--->",bitimage.getHeight()+"");
                Log.e("Print height After crop--->",bitimage.getHeight()+"");
                new updateDealPhoto(bitimage,imgPath).execute("");

                dialogcrop.dismiss();
                // new ImageloadAsyn(bitimage).execute();
            }
        });
        //dialogcrop.addContentView(vi, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogcrop.show();
    }

     if (getPicSelect()!=null) {

        cropPhoto(BitmapFactory.decodeFile(imgDecodableString),imgDecodableString);
    }*/
    // This has to be identical to the OAuth redirect url setup in Jawbone Developer Portal
    public static String OAUTH_CALLBACK_URL = "abbbcsquad://com.abbbc.squad";
    public static String Con_U1_QHW = "QHW";
    public static String Con_U1_HW = "HW";
    public static String Con_U1_W = "W";
    public static String Con_O1_QHW = "QHW";
    public static String Con_O1_HW = "HW";
    public static String Con_O1_W = "W";
    public static String Con_O1_W25 = "W2.5";
    public static String Con_O1_W5 = "W5";
    ////////////////////////////////FITBIT///////////////////////
    public static String Unit_U1_QHW = "QHW";
    public static String Unit_U1_HW = "HW";
    public static String Unit_U1_W = "W";
    public static String Unit_O1_W = "W";
    //////////////////////////////JAWBONE///////////////////////////
    public static String Unit_O1_HW = "HW";
    public static String Unit_O1_W25 = "W2.5";
    public static String Unit_O1_W5 = "W5";
    //for RoundTrip Conversion use.
    public static String CN_QHW = "QHW";
    public static String CN_HW = "HW";
    public static String CN_W = "W";
    public static String CN_W25 = "W2.5";
    public static String CN_W5 = "W5";
    public static String refresh_gratitude = "yes";
    public static String refresh_meditation = "yes";
    public static void showToast(Context context, String msg) {
        try {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////FOR INGREDIENT CALCULATION/////////////////////////////////////

    //file path //jyotirmoy created
    public static  File getFile(Context context){

        File mediaStorageDir = new File(context.getFilesDir(),File.separator);

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("directory","dir not create");
                return null;

            }
            Log.e("directory not exist","dir not exist");
        }
        return mediaStorageDir;
    }

    public static void disableTextField(EditText editText){
        editText.setFocusable(false);
        editText.setEnabled(false);
        Log.e("disableText","disableTExt");
    }
    public static void enableTextField(EditText editText){
        editText.setFocusable(true);
        editText.setEnabled(true);
        editText.setFocusableInTouchMode(true);
        Log.e("enableText","enableText");
    }

    public static boolean grateFul_popup=false;
    public static boolean grateFul_popup_from_habit=true;
    //ended jyotirmoy created

    public static void openKeyBoard(Activity activity) {

        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public static void downloadphotowithGlide(Context context, ImageView imageView, String imgUrl) {
//        Glide.with(context).load(imgUrl)
//                .thumbnail(0.5f)
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .placeholder(R.drawable.empty_image_old)
//                .error(R.drawable.empty_image_old)
//                .dontTransform()
//                .dontAnimate()
//                .into(imageView);//need to check
    }

    public static boolean checkConnection(Context ctx) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo i = conMgr.getActiveNetworkInfo();
            if (i == null)
                return false;
            if (!i.isConnected())
                return false;
            if (!i.isAvailable())
                return false;

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void showDialog(final Context context, String title, final String msg, boolean cancelFlag) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setTitle(title);
        builder1.setMessage(msg);
        builder1.setCancelable(true);
        builder1.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    /*if (msg.equals("Do you want to Log Out?")) {
                    new SharedPreference(context).clear("auth");
                    context.startActivity(new Intent(context, LoginActivity.class));
                    } else if (msg.equalsIgnoreCase("File Uploaded Successfully") || msg.equalsIgnoreCase("Weight data uploaded successfully")) {
                    ((Activity) context).finish();
                    } else {

                    }*/


                        dialog.cancel();
                    }
                });

        if (cancelFlag) {
            builder1.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
        }
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
        Map<String, Object> retMap = new HashMap<String, Object>();

        if (json != JSONObject.NULL) {
            retMap = toMap(json);
        }
        return retMap;
    }

    public static Map<String, Object> toMap(JSONObject object) throws JSONException {
        Map<String, Object> map = new HashMap<String, Object>();

        Iterator<String> keysItr = object.keys();
        while (keysItr.hasNext()) {
            String key = keysItr.next();
            Object value = object.get(key);

            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            map.put(key, value);
        }
        return map;
    }

    public static List<Object> toList(JSONArray array) throws JSONException {
        List<Object> list = new ArrayList<Object>();
        for (int i = 0; i < array.length(); i++) {
            Object value = array.get(i);
            if (value instanceof JSONArray) {
                value = toList((JSONArray) value);
            } else if (value instanceof JSONObject) {
                value = toMap((JSONObject) value);
            }
            list.add(value);
        }
        return list;
    }

    public static boolean chkIsUrl(String url) {
        final String URL_REGEX = "^((https?|ftp)://|(www|ftp)\\.)?[a-z0-9-]+(\\.[a-z0-9-]+)+([/?].*)?$";

        Pattern p = Pattern.compile(URL_REGEX);
        Matcher m = p.matcher(url);//replace with string to compare
        if (m.find()) {
            System.out.println("String contains URL");
            return true;
        } else
            return false;
    }

    public static Bitmap getImage(Bitmap bitmap, String path) throws IOException {
        Matrix m = new Matrix();
        ExifInterface exif = new ExifInterface(path);
        int orientation = exif
                .getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
        if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
            m.postRotate(180);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            m.postRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            m.postRotate(270);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }

    public static ProgressDialog getDeterminantProgress(Context context, String message) {

        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.setMax(100);

        return progressDialog;
    }

    public static String getGsonFromObject(Object reqhash) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
        return gson.toJson(reqhash);
    }

    public static void clearSharedPref(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        sharedPreference.clear("ABBBCOnlineUserId");
        sharedPreference.clear("caltokg");
        sharedPreference.clear("UserID");
        sharedPreference.clear("UserSessionID");
        sharedPreference.clear("ABBBCOnlineUserSessionId");
        sharedPreference.clear("USEREMAIL");
        sharedPreference.clear("USERPASSWORD");
        sharedPreference.clear("gcm");
        sharedPreference.clear("unitp");
        sharedPreference.clear("discover");
        sharedPreference.clear("SESSIONJOININGDATE");
        sharedPreference.clear("loggedInFacebook");
        sharedPreference.clear("SHOWFITBITINSTRUCTION");
        sharedPreference.clear("LEFT_PIC_PHOTO_COMPARE_ID");
        sharedPreference.clear("RIGHT_PIC_PHOTO_COMPARE_ID");
        sharedPreference.clear("HOMEVIDEO");
        sharedPreference.clear("homeTool");
        sharedPreference.clear("PRESENTERLIST");
        sharedPreference.clear("IsNonSubscribeUser");
        sharedPreference.clear("email");
        sharedPreference.clear("firstName");
        sharedPreference.clear("lastName");
        sharedPreference.clear("isFacebookUser");
        //sharedPreference.clear("squadling");
        sharedPreference.clear("compChk");
        sharedPreference.clear("WELCOMECHECKONE");
        sharedPreference.clear("WELCOMECHECKTWO");
        sharedPreference.clear("WELCOMECHECKTHREE");
        sharedPreference.clear("WELCOMECHECKFOUR");
        sharedPreference.clear("WELCOMECHECKFIVE");
        //sharedPreference.clear("FIRST_INSTALL_DATE");
        sharedPreference.clear("FirstSignUpDate");
        sharedPreference.clear("GETPROFILECALL");
        sharedPreference.clear("FIRST_OPEN_CALL");
        sharedPreference.clear("Profile_Pic");
        sharedPreference.clear("AFTERLOGINFIRSTTIMEVIDEO");
        sharedPreference.clear("SQUADLITE");
        sharedPreference.clear("SIGNUPMETHOD");
        sharedPreference.clear("LIFETOKEN");
        sharedPreference.clear("ShowedRating");
        sharedPreference.clear("ACCOUNTABILITY_HOWTOUSE");
        sharedPreference.clear("ACTIVEUNTIL_DATE");
        sharedPreference.clear("FIRST_LOGIN_TIME");
        sharedPreference.clear("signupdate");
        sharedPreference.clear("dbquote");
        sharedPreference.clear("SEVENDAY_TRIAL_START");
        sharedPreference.clear("NUTRITION_SETTINGS_FINISH");
        sharedPreference.clear("EXERCISE_SETTINGS_FINISH");
        sharedPreference.clear("deviceType");
        sharedPreference.clear("skipN");
        sharedPreference.clear("skipE");
        sharedPreference.clear("TOKEN_EXPIRY_DATE");
        sharedPreference.clear("COMMUNITY_URL");
        sharedPreference.clear("CURRENT_STREAK");
        sharedPreference.clear("TOP_STREAK");
        sharedPreference.clear("LAST_STREAK");
        sharedPreference.clear("TOTAL_STREAK");
        sharedPreference.clear("HasChatHelpShownToUser");
        sharedPreference.clear("I_M_GRATEFUL_DATE");
        sharedPreference.clear("I_M_GRATEFUL_ACHIEVE_DATE");
        sharedPreference.clear("ONETIME_TODAY_API_CALL");
        sharedPreference.clear("HAS_USER_A_FAV_MEDITATION");
        sharedPreference.clear("HAS_MEDITATION_DIALOG_SHOWN");
        sharedPreference.clear("HAS_PROGRAMS_DIALOG_SHOWN");
        sharedPreference.clear("HAS_LIVE_COURSE_DIALOG_SHOWN");
        sharedPreference.clear("HAS_AEROPLANE_MODE_DIALOG_SHOWN");
        sharedPreference.clear("AVAILABLE_COURSE_MODEL");
        sharedPreference.clear("PROGRAM_PURCHASE_ONLY");
        sharedPreference.clear("habbitFirstTime");
        sharedPreference.clear("bucketFirstTime");
        sharedPreference.clear("GRATITUDE_INFO_ONCE");
        sharedPreference.clear("GROWTH_INFO_ONCE");
        sharedPreference.clear("GRATITUDE_FILTER_VALUE");
        sharedPreference.clear("GRATITUDE_FILTER_SEARCH");
        sharedPreference.clear("GROWTH_FILTER_SAVE_KEY");
        sharedPreference.clear("BUCKET_LIST_STATUS_ACTIVE");
        sharedPreference.clear("BUCKET_LIST_STATUS_HIDDEN");
        sharedPreference.clear("BUCKET_LIST_STATUS_COMPLETED");
        sharedPreference.clear("BUCKET_LIST_SORT_DUE_DATE");
        sharedPreference.clear("BUCKET_LIST_SORT_MANUAL");
        sharedPreference.clear("BUCKET_LIST_TIMELINE");
        sharedPreference.clear("med");
        sharedPreference.clear("medT");
        sharedPreference.clear("MEDITATION_TEST_NOW_TOKEN");
        sharedPreference.clear("MEDITATION_EXPIRATION_DATE_TIME");
        sharedPreference.clear("MEDITATION_INSERTION_DATE_TIME");

        sharedPreference.clear("LAST_ARTICLE_DETAILS");
        sharedPreference.clear("LAST_ARTICLE_ID");
        sharedPreference.clear("LAST_COURSE_DETAILS");
        sharedPreference.clear("LAST_COURSE_ID");

        sharedPreference.clear("COURSE_LIST_SHOULD_RENEW");
        sharedPreference.clear("GRATITUDE_REWARD_DATE");
        sharedPreference.clear("MEDITATION_IDS_FOR_FAV_DIALOG_SHOWN");
        sharedPreference.clear("MEDITATION_IDS_FOR_KLAVIYO_DURATION");
        sharedPreference.clear("COMMUNITY_CACHE_TIME");
        sharedPreference.clear("LAST_LAUNCH_DATE_DIALOG");

    }

    public static boolean isSevenDayOver(Context context) {
        SharedPreference sharedPreference = new SharedPreference(context);
        if (!sharedPreference.read("FIRST_INSTALL_DATE", "").equals("")) {
            Calendar calendar = Calendar.getInstance();
            Date currentdate = calendar.getTime();
            Date installDate = null;
            try {
                //installDate = DemoApplication.getInstance().getInstalledTime();
                installDate = globalFormatWithOutT.parse(sharedPreference.read("FIRST_INSTALL_DATE", ""));
            } catch (Exception e) {
                e.printStackTrace();
            }
            long diff = currentdate.getTime() - installDate.getTime();
            long diffDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

            diffDays = Math.abs(diffDays);

            if ((diffDays >= 7 && sharedPreference.read("IsNonSubscribeUser", "").equals("true")) || (diffDays >= 7 && sharedPreference.read("IsNonSubscribeUser", "").equals(""))) {
                return true;
            } else {
                return false;
            }

        } else {
            return false;
        }
    }

    /**
     * return install time from package manager, or apk file modification time,
     * or null if not found
     *
     * @return
     */
//    public static Date getInstallTime() {
//        PackageManager packageManager = DemoApplication.getInstance().getApplicationContext().getPackageManager();
//        return firstNonNull(installTimeFromPackageManager(packageManager, BuildConfig.APPLICATION_ID),
//                apkUpdateTime(packageManager, BuildConfig.APPLICATION_ID));
//    }//need to check

    private static Date apkUpdateTime(PackageManager packageManager, String packageName) {
        try {
            ApplicationInfo info = packageManager.getApplicationInfo(packageName, 0);
            File apkFile = new File(info.sourceDir);
            return apkFile.exists() ? new Date(apkFile.lastModified()) : null;
        } catch (Exception e) {
            return null; // package not found
        }
    }

    private static Date installTimeFromPackageManager(PackageManager packageManager, String packageName) {
        try {
            PackageInfo info = packageManager.getPackageInfo(packageName, 0);
            Field field = PackageInfo.class.getField("firstInstallTime");
            long timestamp = field.getLong(info);
            return new Date(timestamp);
        } catch (Exception e) {
            return null; // package not found
        }
    }

    /**
     * @param dates
     * @return
     */
    private static Date firstNonNull(Date... dates) {
        for (Date date : dates)
            if (date != null)
                return date;
        return null;
    }

    public static float ConversionNumberType(String conversionNumbeType, float quantity) {
        float conversionQuantity = 0;

        if (quantity <= 1) {
            if (conversionNumbeType.equals(Con_U1_QHW)) {
                conversionQuantity = RoundTripConversionValue(Con_U1_QHW, quantity);
            }

            if (conversionNumbeType.equals(Con_U1_HW)) {

                conversionQuantity = RoundTripConversionValue(Con_U1_HW, quantity);
            }

            if (conversionNumbeType.equals(Con_U1_W)) {
                conversionQuantity = RoundTripConversionValue(Con_U1_W, quantity);
            }
        } else {
            if (conversionNumbeType.equals(Con_O1_QHW)) {
                conversionQuantity = RoundTripConversionValue(Con_O1_QHW, quantity);
            }

            if (conversionNumbeType.equals(Con_O1_HW)) {
                conversionQuantity = RoundTripConversionValue(Con_O1_HW, quantity);
            }

            if (conversionNumbeType.equals(Con_O1_W)) {
                conversionQuantity = RoundTripConversionValue(Con_O1_W, quantity);
            }

            if (conversionNumbeType.equals(Con_O1_W25)) {
                conversionQuantity = RoundTripConversionValue(Con_O1_W25, quantity);
            }

            if (conversionNumbeType.equals(Con_O1_W5)) {
                conversionQuantity = RoundTripConversionValue(Con_O1_W5, quantity);
            }
        }

        return conversionQuantity;
    }

    public static float RoundTripConversionValue(String conversionNumbeType, float quantity) {
        float floorValue = 0;

        // for QWH round trip calculation
        if (conversionNumbeType.equals(CN_QHW)) {
            if (quantity == 0) {
                quantity = (float) 0.0;
            } else if (quantity <= 1) {
                if (quantity <= 0.37) {
                    quantity = (float) 0.25;
                } else if (quantity > 0.37 && quantity <= 0.62) {
                    quantity = (float) 0.50;
                } else if (quantity > 0.62 && quantity <= 0.87) {
                    quantity = (float) 0.75;
                } else {
                    quantity = (float) 1.0;
                }
                if (quantity <= 0) {
                    quantity = (float) 0.25;
                }
            } else {
                //2.61
                floorValue = quantity - (float) Math.floor(quantity); //.61
                quantity = quantity - floorValue; //2
                if (floorValue > 0) {
                    if (floorValue <= 0.37) {
                        quantity = quantity + (float) 0.25;
                    } else if (floorValue > 0.37 && floorValue <= 0.62) {
                        quantity = quantity + (float) 0.50;
                    } else if (floorValue > 0.62 && floorValue <= 0.87) {
                        quantity = quantity + (float) 0.75;
                    } else //if (floorValue > Convert.ToDecimal(0.87) && floorValue < Convert.ToDecimal(quantity))
                    {
                        quantity = quantity + (float) 1.0;
                    }
                }
            }
        }
        // for HW round trip calculation
        else if (conversionNumbeType.equals(CN_HW)) {
            if (quantity == 0) {
                quantity = 0;
            } else if (quantity <= 1) {
                if (quantity < 0.75) {
                    quantity = (float) 0.50;
                } else //if (quantity >= Convert.ToDecimal(0.75))
                {
                    quantity = (float) 1.0;
                }
            } else {
                floorValue = quantity - (float) Math.floor(quantity);
                quantity = quantity - floorValue;
                if (floorValue > 0) {
                    if (floorValue < 0.75) {
                        quantity = quantity + (float) 0.50;
                    } else //if (floorValue >= Convert.ToDecimal(0.75) && floorValue < Convert.ToDecimal(quantity))
                    {
                        quantity = quantity + 1;
                    }
                }
            }
        }
        // for W2.5 round trip calculation
        else if (conversionNumbeType.equals(CN_W25)) {
            floorValue = quantity % (float) 2.5;
            quantity = quantity - floorValue;
            // quantity = quantity + Convert.ToDecimal(2.50);

            if (floorValue < 1.25) {
                quantity = quantity + 0;
            } else {
                quantity = quantity + (float) 2.50;
            }

            if (quantity <= 0) {
                quantity = (float) 2.50;
            }

        }
        // for W5 round trip calculation
        else if (conversionNumbeType.equals(CN_W5)) {
            floorValue = quantity % (float) 5;
            quantity = quantity - floorValue;
            // quantity = quantity + Convert.ToDecimal(5);
            if (floorValue < 2.50) {
                quantity = quantity + 0;
            } else {
                quantity = quantity + 5;
            }
            if (quantity <= 0) {
                quantity = (float) 5.0;
            }
        }

        // for W round trip calculation
        else if (conversionNumbeType.equals(CN_W)) {
            quantity = (float) Math.round(quantity);
        }

        return quantity;
    }

    public static float UnitNumberType(String conversionNumbeType, float quantity) {
        float conversionQuantity = 0;

        if (quantity <= 1) {
            if (conversionNumbeType.equals(Unit_U1_QHW)) {
                conversionQuantity = RoundTripConversionValue(Unit_U1_QHW, quantity);
            }

            if (conversionNumbeType.equals(Unit_U1_HW)) {
                conversionQuantity = RoundTripConversionValue(Unit_U1_HW, quantity);
            }

            if (conversionNumbeType.equals(Unit_U1_W)) {
                conversionQuantity = RoundTripConversionValue(Unit_U1_W, quantity);
            }
        } else {
            if (conversionNumbeType.equals(Unit_U1_QHW)) {
                conversionQuantity = RoundTripConversionValue(Unit_U1_QHW, quantity);
            }


            if (conversionNumbeType.equals(Unit_O1_W)) {
                conversionQuantity = RoundTripConversionValue(Unit_O1_W, quantity);
            }

            if (conversionNumbeType.equals(Unit_O1_HW)) {
                conversionQuantity = RoundTripConversionValue(Unit_O1_HW, quantity);
            }

            if (conversionNumbeType.equals(Unit_O1_W25)) {
                conversionQuantity = RoundTripConversionValue(Unit_O1_W25, quantity);
            }

            if (conversionNumbeType.equals(Unit_O1_W5)) {
                conversionQuantity = RoundTripConversionValue(Unit_O1_W5, quantity);
            }
        }

        return conversionQuantity;
    }

    public static float ImperialRoundTripConversionValue(float quantity) {
        float floorValue = 0;

        floorValue = quantity % (float) .1;
        quantity = quantity - floorValue;

        if (floorValue < (float) (0.05)) {
            quantity = quantity + (float) 0;
        } else {
            quantity = quantity + (float) 0.1;
        }

        if (quantity <= 0) {
            quantity = (float) 0.1;
        }

        return quantity;
    }

    public static void setTextAppearance(TextView textView, Context context, int resId) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            textView.setTextAppearance(context, resId);
        } else {
            textView.setTextAppearance(resId);
        }
    }

    /**
     * @param context
     * @return
     */
    public static int getUserType(Context context) {
        if (context == null)
            return UNKNOWN_USER;
        SharedPreference sharedPreference = new SharedPreference(context);
        if (sharedPreference.read("IsNonSubscribeUser", "").equals("true")) {
            if (Util.isSevenDayOver(context)) {
                return FREE_USER;
            } else {
                return TRIAL_USER;
            }
        } else {
            if (sharedPreference.read("SQUADLITE", "").equals("TRUE")) {
                return SQUAD_LITE_USER;
            } else {
                return PAID_USER;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////

//    public static void showAlertDialogForDeniedAccess(AppCompatActivity activity) {
//
//        final Dialog dialog = new Dialog(activity, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_accessdenied);
//        ImageView imgCross = dialog.findViewById(R.id.imgCross);
//        RelativeLayout rlCancel = dialog.findViewById(R.id.rlCancel);
//        RelativeLayout rlOk = dialog.findViewById(R.id.rlOk);
//
//        imgCross.setOnClickListener(view -> dialog.dismiss());
//        rlCancel.setOnClickListener(view -> dialog.dismiss());
//        rlOk.setOnClickListener(view -> {
//            dialog.dismiss();
//            Intent intent = new Intent(activity, RegisterActivity.class);
//            intent.putExtra("UPGRADECLICK", "TRUE");
//            activity.startActivity(intent);
//            //MainActivity.this.finish();
//
//            //finish();
//        });
//
//        dialog.show();
//
//    } //need to check

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
//Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
//If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

//    public static void showAlertGergious(AppCompatActivity context) {
//        final Dialog dialog = new Dialog(context, R.style.DialogThemeAnother);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.setContentView(R.layout.dialog_show_gorgeious_alert);
//
//        ImageView imgCross = dialog.findViewById(R.id.imgCross);
//        RelativeLayout rlAddEmail = dialog.findViewById(R.id.rlAddEmail);
//
//        imgCross.setOnClickListener(view -> dialog.dismiss());
//
//        rlAddEmail.setOnClickListener(view -> {
//            dialog.dismiss();
//            Intent registerIntent = new Intent(context, RegisterActivity.class);
//            registerIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(registerIntent);
//
//        });
//
//        dialog.show();
//    }//need to check

    static boolean isNumber(String value) {
        try {
            Double.parseDouble(value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isShowDialogToday(Context context, String KEY) {
        SharedPreference sharedPreference = new SharedPreference(context);
        if (sharedPreference.read("LAST_LAUNCH_DATE_DIALOG" + KEY, "").equals(new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(new Date()))) {
            // Date matches. Dialog already displayed for today.
            return false;
        } else {
            // Show dialog
            sharedPreference.write("LAST_LAUNCH_DATE_DIALOG" + KEY, "", new SimpleDateFormat("yyyy/MM/dd", Locale.US).format(new Date()));
            return true;
        }
    }

//    public void startPressAnimation(View view, Context context) {
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.press_animation);
//        view.startAnimation(hyperspaceJumpAnimation);
//    }

    ///////////////circuit android//////////////
//    public boolean checkPermission(final Context context, List<String> _permissionRequest) {
//        if (!_permissionRequest.isEmpty()) {
//            for (int index = 0; index < _permissionRequest.size(); index++) {
//                if (ContextCompat.checkSelfPermission(context, _permissionRequest.get(index)) != PackageManager.PERMISSION_GRANTED) {
//                    return false;
//                }
//            }
//        }
//        return true;
//    }//need to check

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    public void requestPermission(final Context context, final List<String> _permissionRequest) {
//        boolean _displayCustomPrompt = false;
//        if (!_permissionRequest.isEmpty()) {
//            List<String> _requestedPermission = new ArrayList<>();
//            for (int index = 0; index < _permissionRequest.size(); index++) {
//                if (ContextCompat.checkSelfPermission(context, _permissionRequest.get(index)) != PackageManager.PERMISSION_GRANTED) {
//                    _requestedPermission.add(_permissionRequest.get(index));
//                    if (!((Activity) context).shouldShowRequestPermissionRationale(_permissionRequest.get(index))) {
//                        _displayCustomPrompt = true;
//                    }
//                }
//            }
//
//            if (!_requestedPermission.isEmpty()) {
//                if (_displayCustomPrompt) {
//                    new AlertDialog.Builder(context)
//                            .setMessage("This app needs all required permission to fully functional.")
//                            .setCancelable(false)
//                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    try {
//                                        if (!checkPermission(context, _permissionRequest)) {
//                                            Intent intent = new Intent();
//                                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                                            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
//                                            intent.setData(uri);
//                                            context.startActivity(intent);
//                                        }
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            })
//                            .show();
//                } else {
//                    ((Activity) context).requestPermissions(_requestedPermission.toArray(new String[_requestedPermission.size()]), 101211);
//                }
//            }
//        }
//    }//need to check

    public String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public int getHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Log.e("height", height + ">>");
        return height;
    }

    public void setDynamicHeight(GridView gridView) {
        ListAdapter gridViewAdapter = gridView.getAdapter();
        if (gridViewAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int items = gridViewAdapter.getCount();
        int rows = 0;

        View listItem = gridViewAdapter.getView(0, null, gridView);
        listItem.measure(0, 0);
        totalHeight = listItem.getMeasuredHeight();

        int x = 1;
        if (items > 3) {
            //  x = items/3;
            if (items % 3 != 0) {
                x = items / 3;
                rows = (int) (x + 1);
            } else
                rows = items / 3;
            totalHeight *= rows;
            // Log.e("print th",totalHeight+"?"+rows);
        }

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + 10;
        gridView.setLayoutParams(params);
    }
/*new uses*/

  //  public static List<MeditationCourseModel.Webinar> withfilterlist_afterbackfrommeditationdetails = new ArrayList<>();

    public void addRewardsApi(Context context, int UserActionId, String ReferenceEntityType, String ReferenceEntityId, boolean origin) {
        if (Connection.checkConnection(context)) {

            final SharedPreference sharedPreference = new SharedPreference(context);

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("ABBBCOnlineUserId", ""));
            hashReq.put("Key", Util.KEY_ABBBC);
            hashReq.put("UserSessionID", sharedPreference.read("ABBBCOnlineUserSessionId", ""));
            hashReq.put("UserActionId", UserActionId);
            hashReq.put("ReferenceEntityType", ReferenceEntityType);
            if (ReferenceEntityId.equals("")) {
                Date today = new Date();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                ReferenceEntityId = simpleDateFormat.format(today);
                Log.e("print ref id-", ReferenceEntityId + "?");
            }

            hashReq.put("ReferenceEntityId", ReferenceEntityId);


            SessionServiceImpl sessionService = new SessionServiceImpl(context);
            Call<AddRewardModel> serverCall = sessionService.addRewardApi(hashReq);
            serverCall.enqueue(new Callback<AddRewardModel>() {
                @Override
                public void onResponse(Call<AddRewardModel> call, Response<AddRewardModel> response) {

                    Log.e("success1111", "reward");
                    if (response.body() != null) {
                        AddRewardModel lstData = response.body();
                        if (origin) {
                       /*     ((MainActivity) context).refershGamePoint(context);
                            ((MainActivity) context).setOnapiResponse(new MainActivity.OnApiResponseListener() {
                                @Override
                                public void apiFinish() {

                                }
                            });
                            ((MainActivity) context).setOnDialogCloseListener(new MainActivity.OnDialogCloseListener() {
                                @Override
                                public void onClose() {

                                }
                            });*/
                        }




                    }
                }

                @Override
                public void onFailure(Call<AddRewardModel> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("error1111", "err");


                }
            });
        } else {
            Util.showToast(context, Util.networkMsg);
        }
    }
}
