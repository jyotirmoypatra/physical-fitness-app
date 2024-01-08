package com.ashysystem.mbhq.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import static com.ashysystem.mbhq.activity.MainActivity.decodeSampledBitmapFromFile;
import static com.ashysystem.mbhq.activity.MainActivity.encodeImage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.provider.Settings;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.multidex.BuildConfig;
import androidx.viewpager.widget.ViewPager;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;

import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.adapter.SlidingImage_Adapter;

import com.ashysystem.mbhq.model.response.AddUpdateMyAchievementModel;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.MbhqDatabse;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.edmodo.cropper.CropImageView;
import com.viewpagerindicator.CirclePageIndicator;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Executors;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DemoSliderActivity extends Activity {
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private LinearLayout llRoot;
    private static final Integer[] IMAGES= {R.drawable.welcome_overview1,R.drawable.img11,R.drawable.img21,R.drawable.img31,
            R.drawable.img41,R.drawable.img51,R.drawable.overview_slide61,R.drawable.img71/*,R.drawable.overview_slide7*/};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();

    ArrayList<String> arrAchieveOptions = new ArrayList<>(); ///////// added ///////
    SharedPreference sharedPreference; ///////// added ///////
    FinisherServiceImpl finisherService; ///// added /////////
    private File mFile;//// added /////
    public boolean clickGratitudeEntry = false; /// added /////
    private static int CAMERA_PIC_REQUEST = 1437;/// added /////
    File out;//// added ////
    private String imgPath = "";/// added ////
    private static int RESULT_LOAD_IMG = 1434;/// added //
    ProgressDialog progressDialog;// added //
    ImageView imgBackgroundPicTOP; /// added ///
    private String imgDecodableString = ""; /// added /////
    private CropImageView cropImageView; //// added ///
    private Bitmap onLineBitMap = null; /// added ///
    private Bitmap bitimage; /// added ///
    String stringImg = ""; //// added ////
    CardView cardViewBackgroundPicTOP; //// added////
    LinearLayout llAddPicTOP; /// added ///
    private String strDialogSelectionType = ""; /// added ////
    private long mLastClickTime = 0; // added ///

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_imageslider_demo);
        init();
    }
    private void init() {
        llRoot=(LinearLayout)findViewById(R.id.llRoot);
        for(int i=0;i<IMAGES.length;i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);


        mPager.setAdapter(new SlidingImage_Adapter(DemoSliderActivity.this,ImagesArray,mPager));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.length;

        // Auto start of viewpager
        /*final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);*/

        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        sharedPreference = new SharedPreference(this);
        finisherService = new FinisherServiceImpl(this);

        SharedPreferences prefPrompt = getSharedPreferences("getpromptoftheday", MODE_PRIVATE);
        String getprompt = prefPrompt.getString("promptname", "");

        arrAchieveOptions.add("I'm Grateful For:");
        arrAchieveOptions.add("Journal Entry:");

        arrAchieveOptions.add(getprompt);

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
        arrAchieveOptions.add("I've observed:");
        arrAchieveOptions.add("I've learned:");
        arrAchieveOptions.add("I acknowledge:");
        arrAchieveOptions.add("I laughed:");
        arrAchieveOptions.add("I've praised:");
        arrAchieveOptions.add("I've let go of:");
        arrAchieveOptions.add("I dreamt of:");
        arrAchieveOptions.add("One thing I appreciate about you today is:");
        arrAchieveOptions.add("I Choose to Know:");
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        // Pager listener over indicator
        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;


            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {
                if(pos == 7)
                {
                    // Intent intent=new Intent(DemoSliderActivity.this,ActivityAfterIMageSlider.class);
                    // startActivity(intent);
                    // finish();
                    // dialog open code
                    openDialogForAchieveAddWithOption(0);
                    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

                }
            }


            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });
        /*mPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("click me","1");
                mPager.setCurrentItem(mPager.getCurrentItem() + 1, true);
            }
        });*/

    }

    //////////////////////////////////////////////// new function added /////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void openDialogForAchieveAddWithOption(Integer selectedOption) {

        final Dialog dialog = new Dialog(this, R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_achievement_add2);

        TextView txtBack = dialog.findViewById(R.id.txtBack);
        TextView addPicButton = dialog.findViewById(R.id.addPicButton);
        TextView tstSelectedOption = dialog.findViewById(R.id.tstSelectedOption);
        EditText edtAchieve = dialog.findViewById(R.id.edtAchieve);
        RelativeLayout rlSave = dialog.findViewById(R.id.rlSave);
        RelativeLayout rlShare = dialog.findViewById(R.id.rlShare);
        cardViewBackgroundPicTOP = dialog.findViewById(R.id.cardViewBackgroundPic);
        imgBackgroundPicTOP = dialog.findViewById(R.id.imgBackgroundPic);

        dialog.setCancelable(false);

        //  rlShare.setVisibility(View.GONE);

        /*txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptOfTheDayClick = false;
                dialog.dismiss();
                Util.isReloadTodayMainPage = false;
                if (getArguments() != null && getArguments().containsKey("ADD_GROWTH_FROM_TODAY")) {
                    ((MainActivity) getActivity()).loadFragment(new MbhqTodayMainFragment(), "MbhqTodayMain", null);
                }
            }
        });*/

        edtAchieve.setFocusable(true);
        edtAchieve.setClickable(true);

        tstSelectedOption.setText(arrAchieveOptions.get(selectedOption) + "");


        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtAchieve.getText().toString().trim().equals("")) {

                    AlertDialogCustom alertDialogCustom = new AlertDialogCustom(DemoSliderActivity.this);
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
                    if (mFile != null) {
                        MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);

                        HashMap<String, Object> requestHash = new HashMap<>();
                        requestHash.put("model", getGratitudeListModelInner);
                        requestHash.put("Key", Util.KEY);
                        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                        saveAddGratitudeData(requestHash, dialog, selectedOption, arrAchieveOptions.get(selectedOption) + "" + edtAchieve.getText().toString().trim(), false);

                    }else{

                        showpopup_forimagevalidation(dialog, selectedOption, arrAchieveOptions.get(selectedOption) + "" + edtAchieve.getText().toString().trim(), false,edtAchieve);
                    }


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

                        AlertDialogCustom alertDialogCustom = new AlertDialogCustom(DemoSliderActivity.this);
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
                        if (mFile != null) {
                            MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);

                            HashMap<String, Object> requestHash = new HashMap<>();
                            requestHash.put("model", getGratitudeListModelInner);
                            requestHash.put("Key", Util.KEY);
                            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            saveAddGratitudeData(requestHash, dialog, selectedOption, arrAchieveOptions.get(selectedOption) + "" + edtAchieve.getText().toString().trim(), false);

                        }else{

                            showpopup_forimagevalidation(dialog, selectedOption, arrAchieveOptions.get(selectedOption) + "" + edtAchieve.getText().toString().trim(), false,edtAchieve);
                        }
                      /*  MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);

                        HashMap<String, Object> requestHash = new HashMap<>();
                        requestHash.put("model", getGratitudeListModelInner);
                        requestHash.put("Key", Util.KEY);
                        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                        saveAddGratitudeData(requestHash, dialog, selectedOption, edtAchieve.getText().toString().trim(), true);*/
                    }
                } else {
                    /////other journal wntry with add pic
                    // jornalImgAdd=true;
                    pickImageFromGallery();
                   /* if (imgBackgroundPicTOP.getResources() != null) {
                        addPicButton.setText("CHANGE PIC");
                    }*/
                }
            }
        });


        dialog.show();
    }

    private MyAchievementsListInnerModel makeAchieveModelForAdd(EditText edtAchieve, Integer selectedOption) {


        MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
        getGratitudeListModelInner.setId(0);
        getGratitudeListModelInner.setPrompt(arrAchieveOptions.get(selectedOption));
        getGratitudeListModelInner.setAchievement(edtAchieve.getText().toString().trim());
        getGratitudeListModelInner.setNotes("");
        getGratitudeListModelInner.setFrequencyId(1);
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


        if (Util.checkConnection(this)) {

            //progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            final ProgressDialog progressDialog = Util.getDeterminantProgress(this, getString(R.string.txt_upload_message));
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

            Call<AddUpdateMyAchievementModel> jsonObjectCall =
                    finisherService.addUpdateAchievementWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
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

                                    //new MyAsyncTask().execute();
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

           // saveGrowthLocaly(selectedOption, previousDialog, false, null);

        }

    }

    private class MyAsyncTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // This method runs in the background thread
            // You can perform any background tasks here
           // Log.e(TAG, "calleddelete:" + "yes");
           // getAchievementsListAndInsertToDB(); ??

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
           // Log.e(TAG, "calleddelete:" + "yes");
            Executors.newSingleThreadExecutor().execute(() -> MbhqDatabse.getInstance(DemoSliderActivity.this).gratitudeDao().deleteAllGratitudeNew());



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
    private String getRealPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
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
    private void pickImageFromGallery() {
        final Dialog dlg = new Dialog(this);
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

//                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        out = createFolder();
//                        imgPath = out.getAbsolutePath();
//                        Uri photoURI = FileProvider.getUriForFile(DemoSliderActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", out);
//                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                        ContentResolver contentResolver = getContentResolver();
                        ContentValues values = new ContentValues();
                        //values.put(MediaStore.Images.Media.TITLE, "EFC");
                        values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC" + System.currentTimeMillis());
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                      //  values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                        String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
                        values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);

                        Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        imgPath = getRealPathFromUri(uri);
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                    } else {
                        if (!Settings.System.canWrite(DemoSliderActivity.this)) {
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
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    out = createFolder();
//                    imgPath = out.getAbsolutePath();
//                    Uri photoURI = FileProvider.getUriForFile(DemoSliderActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", out);
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                    ContentResolver contentResolver = getContentResolver();
                    ContentValues values = new ContentValues();
                   // values.put(MediaStore.Images.Media.TITLE, "EFC");
                    values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC" + System.currentTimeMillis());
                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                   // values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                    String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
                    values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
                    Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    imgPath = getRealPathFromUri(uri);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
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
                       startActivityForResult(galleryIntent, MainActivity.PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);//need to check

                    } else {


                        if (!Settings.System.canWrite(DemoSliderActivity.this)) {

                            //add jyotirmoy
                            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                                requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 202);
                            }else{
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE}, 202);
                            }

                           // requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,}, 202);
                        }
                        // openAppSettings();
                    }

                } else {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                      MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                  startActivityForResult(galleryIntent, MainActivity.PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);//need check
                }

            }
        });
        dlg.show();

    }

    private File createFolder() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Squad");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(DemoSliderActivity.this, "Failed to create directory SQUAD  Directory.",
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

    private File createFolder1() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Squad");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                Toast.makeText(DemoSliderActivity.this, "Failed to create directory SQUAD  Directory.",
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

    private boolean hasCameraPermission() {
//        int hasPermissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int hasPermissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
//        if (hasPermissionRead == PackageManager.PERMISSION_GRANTED && hasPermissionCamera == PackageManager.PERMISSION_GRANTED && hasPermissionWrite == PackageManager.PERMISSION_GRANTED) {
//            Log.e("camera","10");
//
//            return true;
//        } else
//
//        return false;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // For Android versions below API level 30
            return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;

        } else {
            // For Android versions R (API level 30) and above
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        }
    }

    private boolean hasGalleryPermission() {

//       int hasPermissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//
//        if ( hasPermissionWrite==PackageManager.PERMISSION_GRANTED && hasPermissionRead==PackageManager.PERMISSION_GRANTED ) {
//            return true;
//        } else
//            return false;
        //need to check

        //add jyotirmoy
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
            // For Android versions below API level 30
            return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        } else {
            // For Android versions R (API level 30) and above
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_VIDEO) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO) == PackageManager.PERMISSION_GRANTED;
        }

    }

    private void openAppSettings() {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==203){
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                // You can now use the camera in your app
                Log.e("camera","20");
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                out = createFolder();
//                imgPath = out.getAbsolutePath();
//                Uri photoURI = FileProvider.getUriForFile(DemoSliderActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", out);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                ContentResolver contentResolver = getContentResolver();
                ContentValues values = new ContentValues();
                //values.put(MediaStore.Images.Media.TITLE, "EFC");
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC" + System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                //values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
                values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
                Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                imgPath = getRealPathFromUri(uri);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            } else {
                Log.e("camera","21");
               openAppSettings();
                // Permission was denied
                // You can disable the feature that requires the camera permission
            }
        }else if(requestCode==202){
            Log.e("gallery permision req-", String.valueOf(requestCode));
            Log.e("gallery permision req-", String.valueOf(requestCode));
            for (int i = 0; i < grantResults.length; i++) {
                Log.d("gallery permision req array list", "Permission result[" + i + "]: " + grantResults[i]);
            } //jyoti
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                // You can now use the camera in your app
                Log.e("camera","22");
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, MainActivity.PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

            } else {
                Log.e("camera","23");
                openAppSettings();
                // Permission was denied
                // You can disable the feature that requires the camera permission
            }

        }else if(requestCode==200){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission Gal", "Granted");
                try {

                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, RESULT_LOAD_IMG);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("Permission Gal", "Denied");
            }
        }else if(requestCode==201){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("Permission cam", "Granted");
//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                out = createFolder1();
//                imgPath = out.getAbsolutePath();
//                Uri photoURI = FileProvider.getUriForFile(DemoSliderActivity.this, BuildConfig.APPLICATION_ID + ".fileprovider", out);
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
                ContentResolver contentResolver = getContentResolver();
                ContentValues values = new ContentValues();
               // values.put(MediaStore.Images.Media.TITLE, "EFC");
                values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC" + System.currentTimeMillis());
                values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
               // values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
                values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
                Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                imgPath = getRealPathFromUri(uri);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

            } else {
                Log.e("Permission cam", "Denied");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {

                if (resultCode != 0) {

                    Log.e("camera","01");
                    if (data == null) {
                        progressDialog = ProgressDialog.show(DemoSliderActivity.this, "", "Please wait...");

                        Log.e("camera","02");
                        if (!imgPath.equals("")) {

                            Log.e("camera","03");
                            File selectedImagePath = new File(imgPath);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                imgBackgroundPicTOP.setBackground(null);
                            }
                            progressDialog.dismiss();
                            imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()));
                            imgDecodableString = selectedImagePath.getAbsolutePath();
                            cropPhoto(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()), imgDecodableString);

                        } else {
                            progressDialog.dismiss();
                            if (DemoSliderActivity.this != null) {
                                Toast.makeText(this, "An error occured", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else if (requestCode == MainActivity.PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST && resultCode == RESULT_OK
                            && null != data) {
                        Log.e("camera","04");
                        // Get the Image from data
                        Log.e("PICTURE Gal---->", "123");

                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        // Get the cursor
                        Cursor cursor = getContentResolver().query(selectedImage,
                                filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imgDecodableString = cursor.getString(columnIndex);
                        File comImg = null;
                        try {
                            comImg = new Compressor(this).compressToFile(new File(imgDecodableString));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        long bitmapLengthSize = comImg.length();
                        Log.e("gal compress img", "gallery compress img size>>>>>>>>>" + (bitmapLengthSize * 0.001) + " KB");
                        cursor.close();
                        cropPhoto(BitmapFactory.decodeFile(imgDecodableString), comImg.getAbsolutePath());

                    } else if (requestCode == MainActivity.CAMERA_PIC_REQUEST_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST && resultCode == RESULT_OK && null != data) {
                        Log.e("camera","05");
                        imgDecodableString = out.getPath();
                        Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                        if (bit != null) {
                            Log.e("camera","06");
                            cropPhoto(bit, imgDecodableString);
                        }
                    } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK && null != data) {
                        Log.e("camera","07");
                        imgDecodableString = out.getPath();
                        Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                        if (bit != null) {
                            Log.e("camera","08");
                            cropPhoto(bit, imgDecodableString);
                        }
                    }else {
                        Toast.makeText(this, "You haven't picked Image",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }

        }
    }//need to check

    private void cropPhoto(final Bitmap bitmap, final String imgPath) {
        final Dialog dialogcrop = new Dialog(this, android.R.style.Theme);
        dialogcrop.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialogcrop.setContentView(R.layout.cropdialogimage);
        dialogcrop.getWindow().setLayout(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT);
        TextView txtDone = (TextView) dialogcrop.findViewById(R.id.txtDone);
        TextView txtCancel = (TextView) dialogcrop.findViewById(R.id.txtCancel);
        cropImageView = (CropImageView) dialogcrop.findViewById(R.id.CropImageView);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        final int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        Bitmap myBitmap = null;
        File file = new File(imgPath);
        long bitmapLength = file.length();
        Log.e("bitmapsize", "img size>>>>>>>>>" + bitmapLength);
        if (bitmapLength > 30000000) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
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
            } //need to check
        } else {
            myBitmap = onLineBitMap;
            try {
                cropImageView.setImageBitmap(Util.getImage(onLineBitMap, imgPath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            onLineBitMap = null;
            //need to check
        }

        Log.e(" height before crop--->", myBitmap.getHeight() + "");
        Log.e(" width before crop--->", myBitmap.getWidth() + "");
         cropImageView.setFixedAspectRatio(false);
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("done click",  "");
                try {
                    // TODO Auto-generated method stub
                   bitimage = cropImageView.getCroppedImage(); //need check
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                   // imgBackgroundPicTOP.setImageBitmap(null);
                    //imgBackgroundPicTOP.setAdjustViewBounds(true);
                   // imgBackgroundPicTOP.setImageBitmap(bitimage);
                    dialogcrop.dismiss();
                    //Uri tempUri = getImageUri(getActivity(), bitimage);
                    //String cropPath = getRealPathFromURI(tempUri);
                   String cropPath = storeImage(bitimage);
                    preaprePictureForUpload(cropPath);
                } catch (Exception e) {
                    Log.e(" done click catch", e+ "");
                    e.printStackTrace();
                }
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Bitmap myBitmapCancel = decodeSampledBitmapFromFile(imgPath, ViewGroup.LayoutParams.FILL_PARENT, height);
                    imgBackgroundPicTOP.setImageBitmap(myBitmapCancel);
                    String cropPath = storeImage(myBitmapCancel);
                    preaprePictureForUpload(cropPath);
                    dialogcrop.dismiss(); //need to check
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        dialogcrop.show();
    }

//old code
//    private void preaprePictureForUpload(String cropPath) {
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
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
//            imgBackgroundPicTOP.setBackgroundResource(0);
//        }
//        imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(cropPath));
//        cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
//        imgBackgroundPicTOP.setVisibility(View.VISIBLE);
//        //llAddPicTOP.setVisibility(View.GONE);
//        //buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
//        if (strDialogSelectionType.equals("textOverPic")) {
//           // buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
//        }
//
//    }

    //add jp
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
            ContentResolver contentResolver = getContentResolver();
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
        imgBackgroundPicTOP.setImageURI(Uri.parse(cropPath));
        cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
        imgBackgroundPicTOP.setVisibility(View.VISIBLE);
    }
//    private String storeImage(Bitmap image) {
//        File pictureFile = getOutputMediaFile();
//
//        if (pictureFile == null) {
//            Log.d("TAG",
//                    "Error creating media file, check storage permissions: ");// e.getMessage());
//            return "";
//        }
//        try {
//            FileOutputStream fos = new FileOutputStream(pictureFile);
//            image.compress(Bitmap.CompressFormat.PNG, 40, fos);
//            fos.close();
//        } catch (FileNotFoundException e) {
//            Log.d("TAG", "File not found: " + e.getMessage());
//        } catch (IOException e) {
//            Log.d("TAG", "Error accessing file: " + e.getMessage());
//        }
//        return pictureFile.getAbsolutePath();
//    }
private String storeImage(Bitmap image) {
    ContentValues values = new ContentValues();
    values.put(MediaStore.Images.Media.DISPLAY_NAME, "EFC_CROPPED" + System.currentTimeMillis());
    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
   // values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
    String relativePath = Environment.DIRECTORY_PICTURES + File.separator + "Emotional FC";
    values.put(MediaStore.Images.Media.RELATIVE_PATH, relativePath);
    Uri imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

    if (imageUri == null) {
        Log.e("TAG", "Error creating media file");
        return "";
    }

    try {
        OutputStream outputStream = getContentResolver().openOutputStream(imageUri);
        image.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        if (outputStream != null) {
            outputStream.close();
        }
    } catch (IOException e) {
        Log.e("TAG", "Error accessing file: " + e.getMessage());
    }

    return imageUri.toString();
}


    private File getOutputMediaFile() {


        File mediaStorageDir = Util.getFile(this);  //added by jyotirmoy

        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }

    private void showpopup( Dialog previousDialog,boolean isShare){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Data successfully saved") .setTitle("MBHQ");

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
                       Util.opengratitudeforfirstuser="yes";
                        Intent intent = new Intent(DemoSliderActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();



                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();

    }
    private void showpopup_forimagevalidation(Dialog previousDialog, Integer selectedOption, String growthName, boolean isShare,EditText edtAchieve ){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you don’t want to add a pic?") .setTitle("MBHQ");

        //Setting message manually and performing action on button click
        builder.setCancelable(false)
.setNegativeButton("yes", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        MyAchievementsListInnerModel getGratitudeListModelInner = makeAchieveModelForAdd(edtAchieve, selectedOption);

        HashMap<String, Object> requestHash = new HashMap<>();
        requestHash.put("model", getGratitudeListModelInner);
        requestHash.put("Key", Util.KEY);
        requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

        saveAddGratitudeData(requestHash, previousDialog, selectedOption, arrAchieveOptions.get(selectedOption) + "" + edtAchieve.getText().toString().trim(), false);

    }
})
                .setPositiveButton("no", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        //Setting the title manually
        alert.show();

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}