package com.ashysystem.mbhq.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ashysystem.mbhq.R;


import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
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

import com.ashysystem.mbhq.BuildConfig;
import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.dialog.CustomReminderDialogForEditAchievement;
import com.ashysystem.mbhq.fragment.achievement.MyAchievementsFragment;
import com.ashysystem.mbhq.listener.UploadCallback;
import com.ashysystem.mbhq.model.MyValueListResponse;
import com.ashysystem.mbhq.model.response.AddUpdateMyAchievementModel;
import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.Injection;
import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;
import com.ashysystem.mbhq.roomDatabase.modelFactory.ViewModelFactoryForGrowth;
import com.ashysystem.mbhq.roomDatabase.viewModel.GrowthViewModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.CustomScrollView;
import com.ashysystem.mbhq.util.DatePickerControllerFromTextView;
import com.ashysystem.mbhq.util.OnDragTouchListener;
import com.ashysystem.mbhq.util.SetLocalNotificationForAchievement;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.edmodo.cropper.CropImageView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by android-krishnendu on 3/16/17.
 */

public class MyAchievementsListAddEditFragment extends Fragment {

    TextView txtGoalValue1, txtGoalValue2, txtGoalValue3, txtUploadChangeImage, txtSetReminder;
    EditText edtNotes;
    TextView editGratitudeOne;
    EditText edtGratitudeName;
    ImageView imgEdit, imgGratitudeMain, imgDeleteIcon, imgCopy, imgSaveGratitude, imgDeleteGratitude;
    RelativeLayout rlChangeImage, rlEditCopy, rlSetReminder;
    LinearLayout llSaveDelete, llChangeImageDelete;
    CheckBox chkSetReminder;
    CardView imgGratitudeMainCard;
    MyAchievementsListInnerModel globalmMyAchievementsListInnerModel;
    private String imgPath = "";
    private String imgDecodableString = "";
    String stringImg = "";
    private static int RESULT_LOAD_IMG = 1434;
    private static int CAMERA_PIC_REQUEST = 1437;
    private static int REQUEST_CODE_SONG = 500;
    String gratitudeStatus = "";
    FinisherServiceImpl finisherService;
    SharedPreference sharedPreference;
    ProgressDialog progressDialog;
    Spinner spCategory;
    TextView txtSongBtn;
    private String songPathSelect = "";
    MediaPlayer mpObject = null;
    private Bundle globalBundel = null;
    private RelativeLayout rlSong;

    private CropImageView cropImageView;
    private Bitmap onLineBitMap = null;
    private ImageView imgContainer;
    private Bitmap bitimage;
    FrameLayout fram_;
    ProgressBar imgJournalLoadingBar;

    //added by jyotirmoy patra
    ImageView editPopupBtn;
    ImageView txtBack;
    RelativeLayout editLayout;
    LinearLayout buttonPanelEditmode;
    ImageView clickPhoto, galleryPhoto, chooseDate;
    TextView dateShow;
    CardView cardViewBackgroundPicTOP;
    ImageView imgBackgroundPicTOP;
    LinearLayout llAddPicTOP;
    Button buttonChangeBackgroundImageTOP;
    Button buttonMoveTextBoxTOP;
    RelativeLayout rlTextOverPicInnerTOP;
    boolean globalPickImageFromGratitudeShareDialog = false;
    private String strDialogSelectionType = "";
    private boolean editEnable = false;

    JSONObject rootJson;
    JSONObject rootJsonInner;
    boolean dialogOpenOnceForEdit = false, dialogOpenOnceForAdd = false;
    String[] arrCategory = new String[]{"Fitness", "Nutrition", "Mindset", "Relationship", "Spiritual", "Business", "Personal Development", "Body/Physical Appearance", "Finance", "Health", "Lifestyle/holidays"};
    File out;
    private File mFile;
    // TextView txtBack;

    EditText edtMyAchievement;
    RelativeLayout rlUpload;
    RelativeLayout rlDeleteAchievement;
    ImageView rlSaveAchievement;

    String PREVIOUS_SET_NOTIFICATION = "";
    private String ANNUAL_CROP_PATH = "";

    boolean somethingChanges = false;
    //String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";
    String path = Environment.getExternalStorageDirectory()
            + "/Android/data/"
            + "com.ashysystem.mbhq"
            + "/Files/";

    private ViewModelFactoryForGrowth factoryForGratitude;
    private GrowthViewModel gratitudeViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_achievements_list_add_edit, null);

        funToolBar();

//        txtGoalValue1 = (TextView) view.findViewById(R.id.txtGoalValue1);
//        txtGoalValue2 = (TextView) view.findViewById(R.id.txtGoalValue2);
//        txtGoalValue3 = (TextView) view.findViewById(R.id.txtGoalValue3);
//        txtSetReminder = (TextView) view.findViewById(R.id.txtSetReminder);
//        txtUploadChangeImage = (TextView) view.findViewById(R.id.txtUploadChangeImage);
//        edtGratitudeName = (EditText) view.findViewById(R.id.edtGratitudeName);
//        editGratitudeOne = (TextView) view.findViewById(R.id.editGratitudeOne);
//        edtNotes = (EditText) view.findViewById(R.id.edtNotes);
//        imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
//        rlSong = (RelativeLayout) view.findViewById(R.id.rlSong);
//        imgGratitudeMain = (ImageView) view.findViewById(R.id.imgGratitudeMain);
//        imgDeleteIcon = (ImageView) view.findViewById(R.id.imgDeleteIcon);
//        imgCopy = (ImageView) view.findViewById(R.id.imgCopy);
//        imgSaveGratitude = (ImageView) view.findViewById(R.id.imgSaveGratitude);
//        imgDeleteGratitude = (ImageView) view.findViewById(R.id.imgDeleteGratitude);
//        rlChangeImage = (RelativeLayout) view.findViewById(R.id.rlChangeImage);
//        rlEditCopy = (RelativeLayout) view.findViewById(R.id.rlEditCopy);
//        rlSetReminder = (RelativeLayout) view.findViewById(R.id.rlSetReminder);
//        llSaveDelete = (LinearLayout) view.findViewById(R.id.llSaveDelete);
//        llChangeImageDelete = (LinearLayout) view.findViewById(R.id.llChangeImageDelete);
//        chkSetReminder = (CheckBox) view.findViewById(R.id.chkSetReminder);
//        spCategory = (Spinner) view.findViewById(R.id.spCategory);
//        //////////////song///////
//        txtSongBtn = (TextView) view.findViewById(R.id.txtSongBtn);
//        txtBack = (ImageView) view.findViewById(R.id.txtBack);
//        edtMyAchievement = view.findViewById(R.id.edtMyAchievement);
//        rlUpload = view.findViewById(R.id.rlUpload);
//        rlSaveAchievement = view.findViewById(R.id.rlSaveAchievement);
//        rlDeleteAchievement = view.findViewById(R.id.rlDeleteAchievement);
//
//        imgJournalLoadingBar = view.findViewById(R.id.imgJournalLoadingBar);
//
//        imgGratitudeMainCard = view.findViewById(R.id.imgGratitudeMainCard);
//        fram_= view.findViewById(R.id.fram_);
//        //added by jyotirmoy patra
//        editPopupBtn = view.findViewById(R.id.editPopupBtn);
//        editLayout = view.findViewById(R.id.editLayout);
//        buttonPanelEditmode= view.findViewById(R.id.buttonPanelEditmode);
//        clickPhoto = view.findViewById(R.id.clickPhoto);
//        galleryPhoto = view.findViewById(R.id.galleryPhoto);
//        chooseDate = view.findViewById(R.id.chooseDate);
//        dateShow = view.findViewById(R.id.dateShow);
//        Util.disableTextField(edtGratitudeName);
///*
//        edtGratitudeName.setOnTouchListener(new View.OnTouchListener() {
//
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if (edtGratitudeName.hasFocus()) {
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
//                        case MotionEvent.ACTION_SCROLL:
//                            v.getParent().requestDisallowInterceptTouchEvent(false);
//                            return true;
//                    }
//
//                }
//
//                if(v.getId() == R.id.edtGratitudeName){
//                    v.getParent().requestDisallowInterceptTouchEvent(true);
//                    switch (event.getAction() & MotionEvent.ACTION_MASK){
//                        case MotionEvent.ACTION_UP:
//                            v.getParent().requestDisallowInterceptTouchEvent(false);
//                            break;
//                    }
//
//                }
//                return false;
//            }
//        });
//*/
//
//
//
//
//
//
//        //ended by jyotirmoy patra
//
//
//        factoryForGratitude = Injection.provideViewModelFactoryGrowth(getActivity());
//        gratitudeViewModel = new ViewModelProvider(this, factoryForGratitude).get(GrowthViewModel.class);
//
//
//        //added by jyotirmoy patra
//        //endded by jyotirmoy patra
//
//
//
//
//        txtBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
//                if (somethingChanges) {
//                    final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
//                    alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
//                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
//                        @Override
//                        public void onDone(String title) {
//                            rlSaveAchievement.performClick();
//
//                        }
//
//                        @Override
//                        public void onCancel(String title) {
//
//                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
//                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
//                            Util.isReloadTodayMainPage = true;
//                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
//
//                            // ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
//                        }
//                    });
//                } else {
//                    ((MainActivity) getActivity()).refershGamePoint(getActivity());
//                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
//                    Util.isReloadTodayMainPage = true;
//                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);
//
////                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
//                }
//            }
//        });
//
//        rlSaveAchievement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (gratitudeStatus.equals("EDIT")) {
//
//                    if (dialogOpenOnceForEdit) {
//                        try {
//                            if (!edtGratitudeName.getText().toString().equals("")) {
//                                rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
//                                rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
//                                rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
//                                rootJsonInner.put("Notes", edtNotes.getText().toString());
//                                //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
//                                rootJsonInner.put("CategoryId", 1);
//                                rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
//                                rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
//                                rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
//                                rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
//                                //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
//                                rootJsonInner.put("Song", songPathSelect);
//                                rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
//                                rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
//                                rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
//                                rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
//                                //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
//                                //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
//                                rootJsonInner.put("PushNotification", true);
//
//                                if (!ANNUAL_CROP_PATH.equals("")) {
//                                    rootJsonInner.put("PictureDevicePath", ANNUAL_CROP_PATH);
//                                } else {
//                                    try {
//                                        rootJsonInner.put("PictureDevicePath", globalmMyAchievementsListInnerModel.getPictureDevicePath());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                rootJson.put("model", rootJsonInner);
//                                rootJson.put("Key", Util.KEY);
//                                rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//
//                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//
//                                saveAddGratitudeData(hashMap);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//
//                        if (!edtGratitudeName.getText().toString().equals("")) {
//                            HashMap<String, Object> requestHash = new HashMap<>();
//                            if (chkSetReminder.isChecked()) {
//                                globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
//                                globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
//                                //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                globalmMyAchievementsListInnerModel.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                globalmMyAchievementsListInnerModel.setCategoryId(1);
//                                globalmMyAchievementsListInnerModel.setSong(songPathSelect);
//                                globalmMyAchievementsListInnerModel.setPushNotification(true);
//
//                                if (!ANNUAL_CROP_PATH.equals("")) {
//                                    globalmMyAchievementsListInnerModel.setPictureDevicePath(ANNUAL_CROP_PATH);
//                                } else {
//                                    try {
//                                        globalmMyAchievementsListInnerModel.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                requestHash.put("model", globalmMyAchievementsListInnerModel);
//                                requestHash.put("Key", Util.KEY);
//                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                            } else {
//                                MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
//                                getGratitudeListModelInner.setId(globalmMyAchievementsListInnerModel.getId());
//                                getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
//                                getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
//                                //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                getGratitudeListModelInner.setCategoryId(1);
//                                //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                getGratitudeListModelInner.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
//                                getGratitudeListModelInner.setStatus(false);
//                                getGratitudeListModelInner.setIsDeleted(false);
//                                getGratitudeListModelInner.setSong(songPathSelect);
//                                getGratitudeListModelInner.setPushNotification(false);
//
//                                if (!ANNUAL_CROP_PATH.equals("")) {
//                                    getGratitudeListModelInner.setPictureDevicePath(ANNUAL_CROP_PATH);
//                                } else {
//                                    try {
//                                        getGratitudeListModelInner.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                String updateDate = dateShow.getText().toString();
//                                Date visionDate = null;
//                                try {
//                                    visionDate = simpleFormat.parse(updateDate);
//                                } catch (ParseException e) {
//                                    e.printStackTrace();
//                                }
//                                getGratitudeListModelInner.setCreatedAt(format.format(visionDate));
//
//                                requestHash.put("model", getGratitudeListModelInner);
//                                requestHash.put("Key", Util.KEY);
//                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//                            }
//
//
//                            saveAddGratitudeData(requestHash);
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                } else if (gratitudeStatus.equals("ADD")) {
//                    Log.e("add enable", "add enable");
//                    if (dialogOpenOnceForAdd) {
//
//                        try {
//                            if (!edtGratitudeName.getText().toString().equals("")) {
//                                try {
//                                    rootJsonInner.put("Id", 0);
//                                    rootJsonInner.put("DueDate", null);
//                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
//                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
//                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
//                                    rootJsonInner.put("CategoryId", 1);
//                                    rootJsonInner.put("reminder_till_date", null);
//                                    rootJsonInner.put("last_reminder_inserted_date", "");
//                                    rootJsonInner.put("Picture", "");
//                                    rootJsonInner.put("UploadPictureImgFileName", null);
//                                    rootJsonInner.put("UploadPictureImg", "");
//                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
//                                    rootJsonInner.put("Song", songPathSelect);
//                                    rootJsonInner.put("CreatedAt", "");
//                                    rootJsonInner.put("CreatedBy", Integer.parseInt(sharedPreference.read("UserID", "")));
//                                    rootJsonInner.put("IsDeleted", false);
//                                    rootJsonInner.put("Status", false);
//                                    rootJsonInner.put("IsCreatedUpdated", null);
//                                    rootJsonInner.put("PushNotification", true);
//
//                                    rootJson.put("model", rootJsonInner);
//                                    rootJson.put("Key", Util.KEY);
//                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//
//                                //saveAddGratitudeData(hashMap);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//
//                        if (!edtGratitudeName.getText().toString().equals("")) {
//                            MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
//                            getGratitudeListModelInner.setId(0);
//                            getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
//                            getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
//                            //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                            getGratitudeListModelInner.setCategoryId(1);
//                            //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                            getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
//                            getGratitudeListModelInner.setStatus(false);
//                            getGratitudeListModelInner.setIsDeleted(false);
//                            getGratitudeListModelInner.setSong(songPathSelect);
//                            if (chkSetReminder.isChecked()) {
//                                getGratitudeListModelInner.setPushNotification(true);
//                                getGratitudeListModelInner.setFrequencyId(1);
//                                getGratitudeListModelInner.setReminderOption(1);
//                                getGratitudeListModelInner.setReminderAt1(43200);
//                                getGratitudeListModelInner.setReminderAt1Int(43200);
//                            }
//                            HashMap<String, Object> requestHash = new HashMap<>();
//                            requestHash.put("model", getGratitudeListModelInner);
//                            requestHash.put("Key", Util.KEY);
//                            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                            //saveAddGratitudeData(requestHash);
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                } else if (gratitudeStatus.equals("EDITCOPY")) {
//                    Log.e("edit copy", "edit copy");
//                    if (!edtGratitudeName.getText().toString().equals("")) {
//
//                        if (dialogOpenOnceForEdit) {
//                            try {
//                                if (!edtGratitudeName.getText().toString().equals("")) {
//                                    rootJsonInner.put("Id", 0);
//                                    rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
//                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
//                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
//                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
//                                    rootJsonInner.put("CategoryId", 1);
//                                    rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
//                                    rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
//                                    rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
//                                    rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                    rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
//                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
//                                    rootJsonInner.put("Song", songPathSelect);
//                                    rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
//                                    rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
//                                    rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
//                                    rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
//                                    // rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
//
//                                    rootJson.put("model", rootJsonInner);
//                                    rootJson.put("Key", Util.KEY);
//                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//
//                                    HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//
//                                    //saveAddGratitudeData(hashMap);
//
//                                } else {
//                                    Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            if (!edtGratitudeName.getText().toString().equals("")) {
//                                HashMap<String, Object> requestHash = new HashMap<>();
//                                if (chkSetReminder.isChecked()) {
//                                    globalmMyAchievementsListInnerModel.setId(0);
//                                    globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
//                                    globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
//                                    //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                    globalmMyAchievementsListInnerModel.setCategoryId(1);
//                                    //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);
//
//                                    requestHash.put("model", globalmMyAchievementsListInnerModel);
//                                    requestHash.put("Key", Util.KEY);
//                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                                } else {
//                                    MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
//                                    getGratitudeListModelInner.setId(0);
//                                    getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
//                                    getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
//                                    //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                    getGratitudeListModelInner.setCategoryId(1);
//                                    //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                    getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
//                                    getGratitudeListModelInner.setStatus(false);
//                                    getGratitudeListModelInner.setIsDeleted(false);
//                                    getGratitudeListModelInner.setSong(songPathSelect);
//
//                                    requestHash.put("model", getGratitudeListModelInner);
//                                    requestHash.put("Key", Util.KEY);
//                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//                                }
//
//
//                                //saveAddGratitudeData(requestHash);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//
//                    } else {
//                        Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//        rlDeleteAchievement.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialogWithCustomButton alertDialogCustom = new AlertDialogWithCustomButton(getActivity());
//                alertDialogCustom.ShowDialog("Delete confirmation", "Are you sure you want to delete this achievement? ", true, "CONFIRM", "NO");
//                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
//                    @Override
//                    public void onDone(String title) {
//                        deleteGratitude();
//                    }
//
//                    @Override
//                    public void onCancel(String title) {
//
//                    }
//                });
//            }
//        });
//
//        rlUpload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                makeSaveButtonGreen();
//                pickImageFromGallery();
//            }
//        });
//
//        txtSongBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                stopMusic();
//                FragmentManager manager = getFragmentManager();
////                final SongSelectionFragment mydialog = new SongSelectionFragment();
////                mydialog.setTargetFragment(MyAchievementsListAddEditFragment.this, REQUEST_CODE_SONG);
////                mydialog.show(manager, "AllSongListFragment"); //commented by jyoti
//
//            }
//        });
//
//        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(arrCategory));
//        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spCategory.setAdapter(spinnerArrayAdapter);
//
//        finisherService = new FinisherServiceImpl(getActivity());/*stop*/
//        sharedPreference = new SharedPreference(getActivity());/*stop*/
//        rootJson = new JSONObject();
//
//        sharedPreference.clear("GRATITUDEIMAGE");
//
//        getValueListFromApi();
//
//        if (getArguments() != null) {
//            globalBundel = getArguments();
//            if (getArguments().containsKey("GRATITUDEDATA")) {
//                Gson gson = new Gson();
//                globalmMyAchievementsListInnerModel = gson.fromJson(getArguments().getString("GRATITUDEDATA"), MyAchievementsListInnerModel.class);
//                if (globalmMyAchievementsListInnerModel.getSong() != null)
//                    songPathSelect = globalmMyAchievementsListInnerModel.getSong();
//
//                SetLocalNotificationForAchievement.setNotificationForAchievement(globalmMyAchievementsListInnerModel, getActivity());
//            }
//            if (getArguments().containsKey("GRATITUDESTATUS")) {
//                gratitudeStatus = getArguments().getString("GRATITUDESTATUS");
//            }
//            if (getArguments().containsKey("song")) {/*stop*/
//
//                songPathSelect = getArguments().getString("song");
//                if (!songPathSelect.equals("")) {
//                    String[] spPath = songPathSelect.split(Pattern.quote("*##*"));
//                    txtSongBtn.setText(spPath[1]);
//                    if (!songPathSelect.equals(""))
//                        PlayMusic(songPathSelect);
//                    Log.e("song path choose", songPathSelect);
//                }
//
//            }/*stop*/
//
//            if (getArguments().containsKey("PREVIOUS_SET_NOTIFICATION")) {
//                PREVIOUS_SET_NOTIFICATION = getArguments().getString("PREVIOUS_SET_NOTIFICATION");
//            }
//
//            if(getArguments().containsKey("GRATITUDE_PIC")){
//                if(getArguments().getString("GRATITUDE_PIC").equals("") || getArguments().getString("GRATITUDE_PIC") == null){
//                    // edtGratitudeName.setMinLines(30);
//                    Display display = getActivity().getWindowManager().getDefaultDisplay();
//                    int screenHeight = display.getHeight();
//
//                    edtGratitudeName.setMaxHeight(screenHeight - 400);
//                    imgGratitudeMainCard.setVisibility(View.GONE);
//                    fram_.setVisibility(View.GONE);
//                }else {
//
//
//                    edtGratitudeName.setMaxHeight(800);//////////////////////////////
//                    imgGratitudeMainCard.setVisibility(View.VISIBLE);
//                    fram_.setVisibility(View.VISIBLE);
//
//                }
//
//            }
//
//        }
//
//        if (gratitudeStatus.equals("EDIT")) {
//            edtGratitudeName.setEnabled(true);
//            edtNotes.setEnabled(true);
//            llChangeImageDelete.setVisibility(View.GONE);
//            llSaveDelete.setVisibility(View.GONE);
//            rlSetReminder.setVisibility(View.VISIBLE);
//            rlSong.setVisibility(View.GONE);
//            spCategory.setEnabled(true);
//            imgSaveGratitude.setVisibility(View.GONE);
//            imgEdit.setVisibility(View.VISIBLE);
//        } else if (gratitudeStatus.equals("ADD")) {
//            edtGratitudeName.setEnabled(true);
//            edtGratitudeName.setHint("ADD ACHIEVEMENTS");
//            edtNotes.setEnabled(true);
//            txtUploadChangeImage.setText("Upload Image");
//            llChangeImageDelete.setVisibility(View.VISIBLE);
//            llSaveDelete.setVisibility(View.VISIBLE);
//            imgDeleteGratitude.setVisibility(View.GONE);
//            imgDeleteIcon.setVisibility(View.GONE);
//            //rlEditCopy.setVisibility(View.GONE);
//            spCategory.setEnabled(true);
//            imgSaveGratitude.setVisibility(View.VISIBLE);
//            blink();
//            imgEdit.setVisibility(View.GONE);
//        } else if (gratitudeStatus.equals("EDITCOPY")) {
//            edtGratitudeName.setEnabled(true);
//            edtNotes.setEnabled(true);
//            txtUploadChangeImage.setText("Upload Image");
//            llChangeImageDelete.setVisibility(View.VISIBLE);
//            llSaveDelete.setVisibility(View.VISIBLE);
//            imgDeleteGratitude.setVisibility(View.GONE);
//            imgDeleteIcon.setVisibility(View.GONE);
//            rlEditCopy.setVisibility(View.GONE);
//            spCategory.setEnabled(true);
//        }
//
//        if (globalmMyAchievementsListInnerModel != null) {
//            if (getArguments() != null) {
//                if (!getArguments().containsKey("song")) {
//                    if (globalmMyAchievementsListInnerModel.getSong() != null)
//                        PlayMusic(globalmMyAchievementsListInnerModel.getSong());
//                }
//            }
//
//            //added by jyotirmoy
//            try {
//                dateShow.setText(simpleFormat.format(format.parse(globalmMyAchievementsListInnerModel.getCreatedAt())));
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            //  dateShow.setText(globalmMyAchievementsListInnerModel.getCreatedAt());
//
//
//            if (gratitudeStatus.equals("EDIT")) {
//                //added by jyotirmoy
//
//                if(globalmMyAchievementsListInnerModel.getAchievement()!=null && !globalmMyAchievementsListInnerModel.getAchievement().equals("") && globalmMyAchievementsListInnerModel.getPrompt()!=null && !globalmMyAchievementsListInnerModel.getPrompt().equals(""))
//                {
//                    editGratitudeOne.setText(globalmMyAchievementsListInnerModel.getPrompt()+":");
//                    edtGratitudeName.setText(globalmMyAchievementsListInnerModel.getAchievement());
//                }
//
//
//                edtGratitudeName.addTextChangedListener(new TextWatcher() {
//                    @Override
//                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                    }
//
//                    @Override
//                    public void afterTextChanged(Editable editable) {
//
//
//                        Log.e("after text", "text change-------->" + editable.toString());
//                        rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
//                        somethingChanges = true;
//
//
//                    }
//                });
//
//
//                Log.e("journal Name", "journal---->" + globalmMyAchievementsListInnerModel.getAchievement());
//            } else if (gratitudeStatus.equals("EDITCOPY")) {
//                //added by jyotirmoy
//
//                editGratitudeOne.setText(globalmMyAchievementsListInnerModel.getPrompt());
//                edtGratitudeName.setText(globalmMyAchievementsListInnerModel.getAchievement());
//
//            }
//
//            edtNotes.setText(globalmMyAchievementsListInnerModel.getNotes());
//            spCategory.setSelection(globalmMyAchievementsListInnerModel.getCategoryId() - 1);
//
//            try {
//
//                if (globalmMyAchievementsListInnerModel.getHasImage()) {
//                    Log.e("block","0");
//                    if (globalmMyAchievementsListInnerModel.getPictureDevicePath() != null && !globalmMyAchievementsListInnerModel.getPictureDevicePath().equals("") && !globalmMyAchievementsListInnerModel.getPicture().equals("")) {
//                        Log.e("block","00");
//
//                        imgJournalLoadingBar.setVisibility(View.VISIBLE);
//
///*
//                        Glide.with(getActivity())
//                                .load(globalmMyAchievementsListInnerModel.getPicture())
//                                .placeholder(R.drawable.empty_image_old)
//                                .error(R.drawable.notfound)
//                                .fitCenter()
//                                // .override(200, 200) // resize the image to 200x200 pixels
//                                .thumbnail(0.5f)
//                                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                .dontAnimate()
//                                .listener(new RequestListener<String, GlideDrawable>() {
//                                    @Override
//                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//
//                                        imgJournalLoadingBar.setVisibility(View.GONE);
//                                        imgGratitudeMainCard.setVisibility(View.GONE);
//
//                                        return false;
//
//                                    }
//
//                                    @Override
//                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                        imgJournalLoadingBar.setVisibility(View.GONE);
//                                        imgGratitudeMainCard.setVisibility(View.VISIBLE);
//
//                                        return false;
//                                    }
//                                })
//                                .into(imgGratitudeMain);
//*/
//
//
//                    } else {
//
//
//                        if (!globalmMyAchievementsListInnerModel.getPicture().equals("")) {
//                            Log.e("block","01");
//                            imgJournalLoadingBar.setVisibility(View.VISIBLE);
//
///*
//                            Glide.with(getActivity())
//                                    .load(globalmMyAchievementsListInnerModel.getPicture())
//                                    .placeholder(R.drawable.empty_image_old)
//                                    .error(R.drawable.notfound)
//                                    .fitCenter()
//                                    // .override(200, 200) // resize the image to 200x200 pixels
//                                    .thumbnail(0.5f)
//                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                    .dontAnimate()
//                                    .listener(new RequestListener<String, GlideDrawable>() {
//                                        @Override
//                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//
//                                            imgJournalLoadingBar.setVisibility(View.GONE);
//                                            imgGratitudeMainCard.setVisibility(View.GONE);
//
//                                            return false;
//
//                                        }
//
//                                        @Override
//                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                            imgJournalLoadingBar.setVisibility(View.GONE);
//                                            imgGratitudeMainCard.setVisibility(View.VISIBLE);
//
//                                            return false;
//                                        }
//                                    })
//                                    .into(imgGratitudeMain);
//*/
//                        }else{
//                            Log.e("block","02");
//                            String filePath = path + globalmMyAchievementsListInnerModel.getPictureDevicePath();
//                            File file = new File(filePath);
//                            Log.e("Path", ">>>>>>>>>>>>>> " + file.getAbsolutePath());
//                            imgJournalLoadingBar.setVisibility(View.VISIBLE);
//                            if (file.exists()) {
//
//                                BitmapFactory.Options options = new BitmapFactory.Options();
//                                options.inSampleSize = 4; // This will reduce the image resolution by a factor of 4
//                                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
//
//                                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//                                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//                                String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), bitmap, "Title", null);
//                                Uri imageUri= Uri.parse(path);
//
//                                Log.e("imgurl", "imgurl------->" + imageUri);
//                                Glide.with(getActivity())
//                                        .load(imageUri)
//                                        .placeholder(R.drawable.empty_image_old)
//                                        .error(R.drawable.notfound)
//                                        .dontAnimate()
//                                        .diskCacheStrategy(DiskCacheStrategy.ALL)
//                                        .listener(new RequestListener<Uri, GlideDrawable>() {
//                                            @Override
//                                            public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
//                                                imgJournalLoadingBar.setVisibility(View.GONE);
//                                                imgGratitudeMainCard.setVisibility(View.GONE);
//
//                                                return false;
//                                            }
//
//                                            @Override
//                                            public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                                                imgJournalLoadingBar.setVisibility(View.GONE);
//                                                imgGratitudeMainCard.setVisibility(View.VISIBLE);
//
//                                                return false;
//                                            }
//                                        })
//                                        .into(imgGratitudeMain);
//
//
//                            }
//                        }
//
//                    }
//                }else {
//                    imgGratitudeMainCard.setVisibility(View.GONE);
//                }
//
//
//
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//
//            if (globalmMyAchievementsListInnerModel.getPushNotification() != null && globalmMyAchievementsListInnerModel.getPushNotification()) {
//                chkSetReminder.setChecked(true);
//                txtSetReminder.setText("View Reminder");
//            }
//        }
//
//        rlSetReminder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (gratitudeStatus.equals("EDIT")) {
//                    if (chkSetReminder.isChecked() && txtSetReminder.getText().toString().equals("View Reminder")) {
//                        CustomReminderDialogForEdit customReminderDialog = new CustomReminderDialogForEdit();
//                        Gson gson = new Gson();
//                        Bundle bundle = new Bundle();
//                        bundle.putString("MODEL", gson.toJson(globalmMyAchievementsListInnerModel));
//                        bundle.putString("MODELNAME", "GRATITUDE");
//                        customReminderDialog.setArguments(bundle);
//                        customReminderDialog.mListener = new CustomReminderDialogForEdit.onSubmitListener() {
//                            @Override
//                            public void setOnSubmitListener(JSONObject arg) {
//                                Log.e("print jj", arg.toString() + "?");
//                                dialogOpenOnceForEdit = true;
//
//                                rootJsonInner = arg;
//                                try {
//                                    globalmMyAchievementsListInnerModel.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
//                                    globalmMyAchievementsListInnerModel.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
//                                    globalmMyAchievementsListInnerModel.setReminderOption(rootJsonInner.getInt("ReminderOption"));
//                                    globalmMyAchievementsListInnerModel.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
//                                    globalmMyAchievementsListInnerModel.setReminderAt1Int(rootJsonInner.getInt("ReminderAt1"));
//                                    globalmMyAchievementsListInnerModel.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
//                                    globalmMyAchievementsListInnerModel.setReminderAt2Int(rootJsonInner.getInt("ReminderAt2"));//...
//                                    globalmMyAchievementsListInnerModel.setEmail(rootJsonInner.getBoolean("Email"));
//                                    globalmMyAchievementsListInnerModel.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
//                                    globalmMyAchievementsListInnerModel.setSunday(rootJsonInner.getBoolean("Sunday"));
//                                    globalmMyAchievementsListInnerModel.setMonday(rootJsonInner.getBoolean("Monday"));
//                                    globalmMyAchievementsListInnerModel.setTuesday(rootJsonInner.getBoolean("Tuesday"));
//                                    globalmMyAchievementsListInnerModel.setWednesday(rootJsonInner.getBoolean("Wednesday"));
//                                    globalmMyAchievementsListInnerModel.setThursday(rootJsonInner.getBoolean("Thursday"));
//                                    globalmMyAchievementsListInnerModel.setFriday(rootJsonInner.getBoolean("Friday"));
//                                    globalmMyAchievementsListInnerModel.setSaturday(rootJsonInner.getBoolean("Saturday"));
//                                    globalmMyAchievementsListInnerModel.setJanuary(rootJsonInner.getBoolean("January"));
//                                    globalmMyAchievementsListInnerModel.setFebruary(rootJsonInner.getBoolean("February"));
//                                    globalmMyAchievementsListInnerModel.setMarch(rootJsonInner.getBoolean("March"));
//                                    globalmMyAchievementsListInnerModel.setApril(rootJsonInner.getBoolean("April"));
//                                    globalmMyAchievementsListInnerModel.setMay(rootJsonInner.getBoolean("May"));
//                                    globalmMyAchievementsListInnerModel.setJune(rootJsonInner.getBoolean("June"));
//                                    globalmMyAchievementsListInnerModel.setAugust(rootJsonInner.getBoolean("August"));
//                                    globalmMyAchievementsListInnerModel.setSeptember(rootJsonInner.getBoolean("September"));
//                                    globalmMyAchievementsListInnerModel.setOctober(rootJsonInner.getBoolean("October"));
//                                    globalmMyAchievementsListInnerModel.setNovember(rootJsonInner.getBoolean("November"));
//                                    globalmMyAchievementsListInnerModel.setDecember(rootJsonInner.getBoolean("December"));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                                /////////////////////
//
//
//                            }
//                        };
//                        customReminderDialog.show(getFragmentManager(), "");
//                    }
//
//                }
//
//            }
//        });
//
//        imgEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                imgEdit.setVisibility(View.GONE);
//                imgSaveGratitude.setVisibility(View.VISIBLE);
//                blink();
//                imgCopy.setVisibility(View.GONE);
//                edtGratitudeName.setEnabled(true);
//                edtNotes.setEnabled(true);
//                llChangeImageDelete.setVisibility(View.VISIBLE);
//                llSaveDelete.setVisibility(View.VISIBLE);
//                rlSetReminder.setVisibility(View.VISIBLE);
//                rlSong.setVisibility(View.VISIBLE);
//                spCategory.setEnabled(true);
//            }
//        });
//
//        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                if (globalmMyAchievementsListInnerModel != null) {
//                    //globalmMyAchievementsListInnerModel.setCategoryId(i + 1);
//                    globalmMyAchievementsListInnerModel.setCategoryId(1);
//                }
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//
//        rlChangeImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                pickImageFromGallery();
//            }
//        });
//
//        imgDeleteIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                imgGratitudeMain.setBackground(null);
//                // imgGratitudeMain.setBackgroundResource(R.drawable.empty_image);
//                stringImg = "";
//                globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(stringImg);
//                deleteImage();
//
//            }
//        });
//
//
//        chkSetReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                makeSaveButtonGreen();
//                if (isChecked) {
//                    if (gratitudeStatus.equals("EDIT")) {
//                        if (!edtGratitudeName.getText().toString().equals("")) {
//
//                            txtSetReminder.setText("View Reminder");
//
//                            CustomReminderDialogForEditAchievement customReminderDialogForEditAchievement = new CustomReminderDialogForEditAchievement();
//                            Gson gson = new Gson();
//                            Bundle bundle = new Bundle();
//                            bundle.putString("MODELNAME", "ACHIEVEMENT");
//                            bundle.putString("MODEL", gson.toJson(globalmMyAchievementsListInnerModel));
//                            customReminderDialogForEditAchievement.setArguments(bundle);
//                            customReminderDialogForEditAchievement.mListener = new CustomReminderDialogForEditAchievement.onSubmitListener() {
//                                @Override
//                                public void setOnSubmitListener(JSONObject arg) {
//                                    Log.e("print jj", arg.toString() + "?");
//                                    dialogOpenOnceForEdit = true;
//
//                                    rootJsonInner = arg;
//                                    try {
//                                        globalmMyAchievementsListInnerModel.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
//                                        globalmMyAchievementsListInnerModel.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
//                                        globalmMyAchievementsListInnerModel.setReminderOption(rootJsonInner.getInt("ReminderOption"));
//                                        globalmMyAchievementsListInnerModel.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
//                                        globalmMyAchievementsListInnerModel.setReminderAt1Int(rootJsonInner.getInt("ReminderAt1"));
//                                        globalmMyAchievementsListInnerModel.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
//                                        globalmMyAchievementsListInnerModel.setReminderAt2Int(rootJsonInner.getInt("ReminderAt2"));//...
//                                        globalmMyAchievementsListInnerModel.setEmail(rootJsonInner.getBoolean("Email"));
//                                        globalmMyAchievementsListInnerModel.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
//                                        globalmMyAchievementsListInnerModel.setSunday(rootJsonInner.getBoolean("Sunday"));
//                                        globalmMyAchievementsListInnerModel.setMonday(rootJsonInner.getBoolean("Monday"));
//                                        globalmMyAchievementsListInnerModel.setTuesday(rootJsonInner.getBoolean("Tuesday"));
//                                        globalmMyAchievementsListInnerModel.setWednesday(rootJsonInner.getBoolean("Wednesday"));
//                                        globalmMyAchievementsListInnerModel.setThursday(rootJsonInner.getBoolean("Thursday"));
//                                        globalmMyAchievementsListInnerModel.setFriday(rootJsonInner.getBoolean("Friday"));
//                                        globalmMyAchievementsListInnerModel.setSaturday(rootJsonInner.getBoolean("Saturday"));
//                                        globalmMyAchievementsListInnerModel.setJanuary(rootJsonInner.getBoolean("January"));
//                                        globalmMyAchievementsListInnerModel.setFebruary(rootJsonInner.getBoolean("February"));
//                                        globalmMyAchievementsListInnerModel.setMarch(rootJsonInner.getBoolean("March"));
//                                        globalmMyAchievementsListInnerModel.setApril(rootJsonInner.getBoolean("April"));
//                                        globalmMyAchievementsListInnerModel.setMay(rootJsonInner.getBoolean("May"));
//                                        globalmMyAchievementsListInnerModel.setJune(rootJsonInner.getBoolean("June"));
//                                        globalmMyAchievementsListInnerModel.setAugust(rootJsonInner.getBoolean("August"));
//                                        globalmMyAchievementsListInnerModel.setSeptember(rootJsonInner.getBoolean("September"));
//                                        globalmMyAchievementsListInnerModel.setOctober(rootJsonInner.getBoolean("October"));
//                                        globalmMyAchievementsListInnerModel.setNovember(rootJsonInner.getBoolean("November"));
//                                        globalmMyAchievementsListInnerModel.setDecember(rootJsonInner.getBoolean("December"));
//
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                    /////////////////////////////
//
//
//                                }
//                            };
//                            customReminderDialogForEditAchievement.show(getFragmentManager(), "");
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                        }
//
//
//                    } else if (gratitudeStatus.equals("ADD")) {
//                        if (!edtGratitudeName.getText().toString().equals("")) {
//                            CustomReminderDialog customReminderDialog = new CustomReminderDialog();
//                            customReminderDialog.mListener = new CustomReminderDialog.onSubmitListener() {
//                                @Override
//                                public void setOnSubmitListener(JSONObject arg) {
//                                    Log.e("print jj", arg.toString() + "?");
//                                    dialogOpenOnceForAdd = true;
//                                    rootJsonInner = arg;
//                                }
//                            };
//                            customReminderDialog.show(getFragmentManager(), "");
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                } else {
//                    if (PREVIOUS_SET_NOTIFICATION.equals("TRUE")) {
//                        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
//                        dialog.setContentView(R.layout.dialog_turnoff_edit_reminder);
//                        dialog.setCancelable(false);
//
//                        RelativeLayout rlEditReminder = dialog.findViewById(R.id.rlEditReminder);
//                        RelativeLayout rlTurnOffReminder = dialog.findViewById(R.id.rlTurnOffReminder);
//                        RelativeLayout rlTop = dialog.findViewById(R.id.rlTop);
//                        RelativeLayout rlBottom = dialog.findViewById(R.id.rlBottom);
//
//                        rlTop.setOnClickListener(view1 -> {
//                            dialog.dismiss();
//                            chkSetReminder.setChecked(false);
//                            PREVIOUS_SET_NOTIFICATION = "FALSE";
//                            txtSetReminder.setText("Set a Reminder");
//                        });
//
//                        rlBottom.setOnClickListener(view1 -> {
//                            dialog.dismiss();
//                            chkSetReminder.setChecked(false);
//                            PREVIOUS_SET_NOTIFICATION = "FALSE";
//                            txtSetReminder.setText("Set a Reminder");
//                        });
//
//
//                        rlEditReminder.setOnClickListener(view1 -> {
//                            dialog.dismiss();
//                            chkSetReminder.setChecked(true);
//                        });
//
//                        rlTurnOffReminder.setOnClickListener(view1 -> {
//                            dialog.dismiss();
//                            chkSetReminder.setChecked(false);
//                            PREVIOUS_SET_NOTIFICATION = "FALSE";
//                            txtSetReminder.setText("Set a Reminder");
//                        });
//
//                        dialog.show();
//                    } else {
//                        txtSetReminder.setText("Set a Reminder");
//                    }
//                }
//            }
//        });
//
//        imgSaveGratitude.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String journalName = editGratitudeOne.getText().toString().trim() + edtGratitudeName.getText().toString().trim();
//
//                if (gratitudeStatus.equals("EDIT")) {
//                    if (dialogOpenOnceForEdit) {
//                        try {
//                            if (!edtGratitudeName.getText().toString().equals("")) {
//                                rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
//                                rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
//                                rootJsonInner.put("Achievement", journalName);
//                                rootJsonInner.put("Notes", edtNotes.getText().toString());
//                                //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
//                                rootJsonInner.put("CategoryId", 1);
//                                rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
//                                rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
//                                rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
//                                rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
//                                //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
//                                rootJsonInner.put("Song", songPathSelect);
//                                rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
//                                rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
//                                rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
//                                rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
//                                // rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
//                                rootJsonInner.put("PushNotification", true);
//
//                                if (!ANNUAL_CROP_PATH.equals("")) {
//                                    rootJsonInner.put("PictureDevicePath", ANNUAL_CROP_PATH);
//                                } else {
//                                    try {
//                                        rootJsonInner.put("PictureDevicePath", globalmMyAchievementsListInnerModel.getPictureDevicePath());
//                                    } catch (JSONException e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                rootJson.put("model", rootJsonInner);
//                                rootJson.put("Key", Util.KEY);
//                                rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//
//                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//
//                                saveAddGratitudeData(hashMap);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    } else {
//                        if (!edtGratitudeName.getText().toString().equals("")) {
//                            HashMap<String, Object> requestHash = new HashMap<>();
//                            if (chkSetReminder.isChecked()) {
//                                globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString());
//                                globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
//                                //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                globalmMyAchievementsListInnerModel.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                globalmMyAchievementsListInnerModel.setCategoryId(1);
//                                globalmMyAchievementsListInnerModel.setSong(songPathSelect);
//                                globalmMyAchievementsListInnerModel.setPushNotification(true);
//
//                                if (!ANNUAL_CROP_PATH.equals("")) {
//                                    globalmMyAchievementsListInnerModel.setPictureDevicePath(ANNUAL_CROP_PATH);
//                                } else {
//                                    try {
//                                        globalmMyAchievementsListInnerModel.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                requestHash.put("model", globalmMyAchievementsListInnerModel);
//                                requestHash.put("Key", Util.KEY);
//                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                            } else {
//                                MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
//                                getGratitudeListModelInner.setId(globalmMyAchievementsListInnerModel.getId());
//                                getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString());
//                                getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
//                                //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                getGratitudeListModelInner.setCategoryId(1);
//                                //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                getGratitudeListModelInner.setUploadPictureImgFileName(globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
//                                getGratitudeListModelInner.setStatus(false);
//                                getGratitudeListModelInner.setIsDeleted(false);
//                                getGratitudeListModelInner.setSong(songPathSelect);
//                                getGratitudeListModelInner.setPushNotification(false);
//
//                                if (!ANNUAL_CROP_PATH.equals("")) {
//                                    getGratitudeListModelInner.setPictureDevicePath(ANNUAL_CROP_PATH);
//                                } else {
//                                    try {
//                                        getGratitudeListModelInner.setPictureDevicePath(globalmMyAchievementsListInnerModel.getPictureDevicePath());
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//
//                                requestHash.put("model", getGratitudeListModelInner);
//                                requestHash.put("Key", Util.KEY);
//                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//                            }
//
//
//                            saveAddGratitudeData(requestHash);
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                } else if (gratitudeStatus.equals("ADD")) {
//                    if (dialogOpenOnceForAdd) {
//
//                        try {
//                            if (!edtGratitudeName.getText().toString().equals("")) {
//                                try {
//                                    rootJsonInner.put("Id", 0);
//                                    rootJsonInner.put("DueDate", null);
//                                    rootJsonInner.put("Achievement", journalName);
//                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
//                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
//                                    rootJsonInner.put("CategoryId", 1);
//                                    rootJsonInner.put("reminder_till_date", null);
//                                    rootJsonInner.put("last_reminder_inserted_date", "");
//                                    rootJsonInner.put("Picture", "");
//                                    rootJsonInner.put("UploadPictureImgFileName", null);
//                                    rootJsonInner.put("UploadPictureImg", "");
//                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
//                                    rootJsonInner.put("Song", songPathSelect);
//                                    rootJsonInner.put("CreatedAt", "");
//                                    rootJsonInner.put("CreatedBy", Integer.parseInt(sharedPreference.read("UserID", "")));
//                                    rootJsonInner.put("IsDeleted", false);
//                                    rootJsonInner.put("Status", false);
//                                    rootJsonInner.put("IsCreatedUpdated", null);
//                                    rootJsonInner.put("PushNotification", true);
//
//                                    rootJson.put("model", rootJsonInner);
//                                    rootJson.put("Key", Util.KEY);
//                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//
//                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//
//                                saveAddGratitudeData(hashMap);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    } else {
//
//                        if (!edtGratitudeName.getText().toString().equals("")) {
//                            MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
//                            getGratitudeListModelInner.setId(0);
//                            getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString());
//                            getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
//                            //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                            getGratitudeListModelInner.setCategoryId(1);
//                            //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                            getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
//                            getGratitudeListModelInner.setStatus(false);
//                            getGratitudeListModelInner.setIsDeleted(false);
//                            getGratitudeListModelInner.setSong(songPathSelect);
//                            if (chkSetReminder.isChecked()) {
//                                getGratitudeListModelInner.setPushNotification(true);
//                                getGratitudeListModelInner.setFrequencyId(1);
//                                getGratitudeListModelInner.setReminderOption(1);
//                                getGratitudeListModelInner.setReminderAt1(43200);
//                                getGratitudeListModelInner.setReminderAt1Int(43200);
//                            }
//                            HashMap<String, Object> requestHash = new HashMap<>();
//                            requestHash.put("model", getGratitudeListModelInner);
//                            requestHash.put("Key", Util.KEY);
//                            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                            saveAddGratitudeData(requestHash);
//
//                        } else {
//                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                        }
//
//                    }
//                } else if (gratitudeStatus.equals("EDITCOPY")) {
//                    if (!edtGratitudeName.getText().toString().equals("")) {
//
//                        if (dialogOpenOnceForEdit) {
//                            try {
//                                if (!edtGratitudeName.getText().toString().equals("")) {
//                                    rootJsonInner.put("Id", 0);
//                                    rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
//                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString());
//                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
//                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
//                                    rootJsonInner.put("CategoryId", 1);
//                                    rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
//                                    rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
//                                    rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
//                                    rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
//                                    rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
//                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
//                                    rootJsonInner.put("Song", songPathSelect);
//                                    rootJsonInner.put("CreatedAt", globalmMyAchievementsListInnerModel.getCreatedAt());
//                                    rootJsonInner.put("CreatedBy", globalmMyAchievementsListInnerModel.getCreatedBy());
//                                    rootJsonInner.put("IsDeleted", globalmMyAchievementsListInnerModel.getIsDeleted());
//                                    rootJsonInner.put("Status", globalmMyAchievementsListInnerModel.getStatus());
//                                    //  rootJsonInner.put("IsCreatedUpdated", globalmMyAchievementsListInnerModel.getIsCreatedUpdated());
//
//                                    rootJson.put("model", rootJsonInner);
//                                    rootJson.put("Key", Util.KEY);
//                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//
//                                    HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
//
//                                    saveAddGratitudeData(hashMap);
//
//                                } else {
//                                    Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                                }
//
//
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            if (!edtGratitudeName.getText().toString().equals("")) {
//                                HashMap<String, Object> requestHash = new HashMap<>();
//                                if (chkSetReminder.isChecked()) {
//                                    globalmMyAchievementsListInnerModel.setId(0);
//                                    globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString());
//                                    globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
//                                    //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                    globalmMyAchievementsListInnerModel.setCategoryId(1);
//                                    //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);
//
//                                    requestHash.put("model", globalmMyAchievementsListInnerModel);
//                                    requestHash.put("Key", Util.KEY);
//                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//
//                                } else {
//                                    MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
//                                    getGratitudeListModelInner.setId(0);
//                                    getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString());
//                                    getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
//                                    //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
//                                    getGratitudeListModelInner.setCategoryId(1);
//                                    //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
//                                    getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
//                                    getGratitudeListModelInner.setStatus(false);
//                                    getGratitudeListModelInner.setIsDeleted(false);
//                                    getGratitudeListModelInner.setSong(songPathSelect);
//
//                                    requestHash.put("model", getGratitudeListModelInner);
//                                    requestHash.put("Key", Util.KEY);
//                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
//                                }
//
//
//                                saveAddGratitudeData(requestHash);
//
//                            } else {
//                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//
//                    } else {
//                        Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        });
//
//
//        imgDeleteGratitude.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                deleteGratitude();
//
//            }
//        });
//
//        imgCopy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                Gson gson = new Gson();
//                Bundle bundle = new Bundle();
//                bundle.putString("GRATITUDESTATUS", "EDITCOPY");
//                bundle.putString("GRATITUDEDATA", gson.toJson(globalmMyAchievementsListInnerModel));
//                ((MainActivity) getActivity()).loadFragment(new MyAchievementsListAddEditFragment(), "MyAchievementsListAddEdit", bundle);
//
//            }
//        });
//
//        if (getArguments() != null && getArguments().containsKey("OPEN_NOTIFICATION")) {
//            if (!new SharedPreference(getActivity()).read("FTIME_VIDEO_INFO", "").equals("") && getArguments().getString("OPEN_NOTIFICATION").equals("TRUE")) {
//                rlSetReminder.performClick();
//            }
//        }
//

        return view;
    }

    private void openDialogForGratitudeShare(String gratitudeName, Integer gratitudeID) {
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
            //((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievementFragment", null);
        });

        rlTestAndPic.setOnClickListener(view -> {
            strDialogSelectionType = "textAndPic";
            dialog.dismiss();
        });

        rlTextAndPicEx.setOnClickListener(view -> {
            openDialogForShareExamples("textAndPic");
        });

        rlTextOverPic.setOnClickListener(view -> {
            strDialogSelectionType = "textOverPic";
            dialog.dismiss();
        });

        rlTextOverPicEx.setOnClickListener(view -> {
            openDialogForShareExamples("textOverPic");
        });

        rlTextOnly.setOnClickListener(view -> {
            strDialogSelectionType = "textOnly";
            dialog.dismiss();
        });

        rlTextOnlyEx.setOnClickListener(view -> {
            openDialogForShareExamples("textOnly");
        });

        dialog.show();
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





    /*stop*/
    /*stop*/










    /**
     * Create a File for saving an image or video
     */


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }









    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeToString(imageByteArray, Base64.DEFAULT);
    }

    @Override
    public void onResume() {
        super.onResume();
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);

                    return true;

                }

                return false;
            }
        });
    }


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

                if (gratitudeStatus.equals("ADD")) {
                    if (!edtGratitudeName.getText().toString().equals("") || !edtNotes.getText().toString().equals("") || chkSetReminder.isChecked() || !stringImg.equals("")) {
                        final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                        alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
                        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                            @Override
                            public void onDone(String title) {
                                rlSaveAchievement.performClick();
                            }

                            @Override
                            public void onCancel(String title) {
                                ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                            }
                        });

                    } else {
                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                    }
                } else if (gratitudeStatus.equals("EDIT") && imgEdit.getVisibility() == View.GONE) {
                    final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {
                            imgSaveGratitude.performClick();
                        }

                        @Override
                        public void onCancel(String title) {
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                        }
                    });
                } else {
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                }

            }
        });
    }

    private boolean hasCameraPermission() {
        int hasPermissionWrite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        int hasPermissionCamera = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED && hasPermissionCamera == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }

    private boolean hasGalleryPermission() {
        int hasPermissionWrite = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int hasPermissionRead = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
        if (hasPermissionWrite == PackageManager.PERMISSION_GRANTED && hasPermissionRead == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else
            return false;
    }


    @Override
    public void onPause() {
        try {
            if (mpObject != null) {
                Log.e("stop", "123");
                if (mpObject.isPlaying()) {
                    mpObject.stop();
                    mpObject.release();
                }

            }

        } catch (Exception e) {

        }

        super.onPause();
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

    @Override
    public void onAttach(Context context) {
        if (context instanceof UploadCallback) {
            uploadCallback = (UploadCallback) context;
        } else {
            Log.e(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Classcast exception");
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if (uploadCallback != null) {
            uploadCallback = null;
        }
        super.onDetach();
    }

    public static final String TAG = MyAchievementsListAddEditFragment.class.getSimpleName();
    private UploadCallback uploadCallback;



}

