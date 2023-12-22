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
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
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
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat simpleFormat = new SimpleDateFormat("MMM dd yyyy");
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

        txtGoalValue1 = (TextView) view.findViewById(R.id.txtGoalValue1);
        txtGoalValue2 = (TextView) view.findViewById(R.id.txtGoalValue2);
        txtGoalValue3 = (TextView) view.findViewById(R.id.txtGoalValue3);
        txtSetReminder = (TextView) view.findViewById(R.id.txtSetReminder);
        txtUploadChangeImage = (TextView) view.findViewById(R.id.txtUploadChangeImage);
        edtGratitudeName = (EditText) view.findViewById(R.id.edtGratitudeName);
        editGratitudeOne = (TextView) view.findViewById(R.id.editGratitudeOne);
        edtNotes = (EditText) view.findViewById(R.id.edtNotes);
        imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
        rlSong = (RelativeLayout) view.findViewById(R.id.rlSong);
        imgGratitudeMain = (ImageView) view.findViewById(R.id.imgGratitudeMain);
        imgDeleteIcon = (ImageView) view.findViewById(R.id.imgDeleteIcon);
        imgCopy = (ImageView) view.findViewById(R.id.imgCopy);
        imgSaveGratitude = (ImageView) view.findViewById(R.id.imgSaveGratitude);
        imgDeleteGratitude = (ImageView) view.findViewById(R.id.imgDeleteGratitude);
        rlChangeImage = (RelativeLayout) view.findViewById(R.id.rlChangeImage);
        rlEditCopy = (RelativeLayout) view.findViewById(R.id.rlEditCopy);
        rlSetReminder = (RelativeLayout) view.findViewById(R.id.rlSetReminder);
        llSaveDelete = (LinearLayout) view.findViewById(R.id.llSaveDelete);
        llChangeImageDelete = (LinearLayout) view.findViewById(R.id.llChangeImageDelete);
        chkSetReminder = (CheckBox) view.findViewById(R.id.chkSetReminder);
        spCategory = (Spinner) view.findViewById(R.id.spCategory);
        //////////////song///////
        txtSongBtn = (TextView) view.findViewById(R.id.txtSongBtn);
        txtBack = (ImageView) view.findViewById(R.id.txtBack);
        edtMyAchievement = view.findViewById(R.id.edtMyAchievement);
        rlUpload = view.findViewById(R.id.rlUpload);
        rlSaveAchievement = view.findViewById(R.id.rlSaveAchievement);
        rlDeleteAchievement = view.findViewById(R.id.rlDeleteAchievement);

        imgJournalLoadingBar = view.findViewById(R.id.imgJournalLoadingBar);

        imgGratitudeMainCard = view.findViewById(R.id.imgGratitudeMainCard);
        fram_= view.findViewById(R.id.fram_);
        //added by jyotirmoy patra
        editPopupBtn = view.findViewById(R.id.editPopupBtn);
        editLayout = view.findViewById(R.id.editLayout);
        buttonPanelEditmode= view.findViewById(R.id.buttonPanelEditmode);
        clickPhoto = view.findViewById(R.id.clickPhoto);
        galleryPhoto = view.findViewById(R.id.galleryPhoto);
        chooseDate = view.findViewById(R.id.chooseDate);
        dateShow = view.findViewById(R.id.dateShow);
        Util.disableTextField(edtGratitudeName);
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
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 201);
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
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
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


        //ended by jyotirmoy patra


        factoryForGratitude = Injection.provideViewModelFactoryGrowth(getActivity());
        gratitudeViewModel = new ViewModelProvider(this, factoryForGratitude).get(GrowthViewModel.class);


        //added by jyotirmoy patra
        editPopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialogEdit = new Dialog(getContext(), android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialogEdit.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialogEdit.setContentView(R.layout.myachievement_edit_dialog);
                dialogEdit.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                RelativeLayout editAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.editAchievement);
                editAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editLayout.setVisibility(View.VISIBLE);
                        buttonPanelEditmode.setVisibility(View.VISIBLE);
                        Util.enableTextField(edtGratitudeName);
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
                        if (globalmMyAchievementsListInnerModel != null) {

                            openDialogForGratitudeShare(edtGratitudeName.getText().toString(), globalmMyAchievementsListInnerModel.getId());
                            Log.e("journalId", "id>>>>" + globalmMyAchievementsListInnerModel.getId());
                        }
                        dialogEdit.dismiss();
                    }
                });

                RelativeLayout deleteAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.deleteAchievement);
                deleteAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
        //endded by jyotirmoy patra



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

                            ((MainActivity) getActivity()).refershGamePoint(getActivity());
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                            Util.isReloadTodayMainPage = true;
                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

                            // ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                        }
                    });
                } else {
                    ((MainActivity) getActivity()).refershGamePoint(getActivity());
                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                    Util.isReloadTodayMainPage = true;
                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "GratitudeMyList", null);

//                    ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                }
            }
        });

        rlSaveAchievement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gratitudeStatus.equals("EDIT")) {

                    if (dialogOpenOnceForEdit) {
                        try {
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
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
                                //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
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

                                saveAddGratitudeData(hashMap);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {

                        if (!edtGratitudeName.getText().toString().equals("")) {
                            HashMap<String, Object> requestHash = new HashMap<>();
                            if (chkSetReminder.isChecked()) {
                                globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
                                globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
                                //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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
                                getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                                //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                getGratitudeListModelInner.setCategoryId(1);
                                //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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


                            saveAddGratitudeData(requestHash);

                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("ADD")) {
                    Log.e("add enable", "add enable");
                    if (dialogOpenOnceForAdd) {

                        try {
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                try {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", null);
                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString().trim());
                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", null);
                                    rootJsonInner.put("last_reminder_inserted_date", "");
                                    rootJsonInner.put("Picture", "");
                                    rootJsonInner.put("UploadPictureImgFileName", null);
                                    rootJsonInner.put("UploadPictureImg", "");
                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
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

                        if (!edtGratitudeName.getText().toString().equals("")) {
                            MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                            getGratitudeListModelInner.setId(0);
                            getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                            getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                            //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                            getGratitudeListModelInner.setCategoryId(1);
                            //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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
                    if (!edtGratitudeName.getText().toString().equals("")) {

                        if (dialogOpenOnceForEdit) {
                            try {
                                if (!edtGratitudeName.getText().toString().equals("")) {
                                    rootJsonInner.put("Id", 0);
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
                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
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
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                HashMap<String, Object> requestHash = new HashMap<>();
                                if (chkSetReminder.isChecked()) {
                                    globalmMyAchievementsListInnerModel.setId(0);
                                    globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString().trim());
                                    globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
                                    //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    globalmMyAchievementsListInnerModel.setCategoryId(1);
                                    //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
                                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);

                                    requestHash.put("model", globalmMyAchievementsListInnerModel);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } else {
                                    MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                                    getGratitudeListModelInner.setId(0);
                                    getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString().trim());
                                    getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                                    //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    getGratitudeListModelInner.setCategoryId(1);
                                    //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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
                        deleteGratitude();
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
                pickImageFromGallery();
            }
        });

        txtSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stopMusic();
                FragmentManager manager = getFragmentManager();
//                final SongSelectionFragment mydialog = new SongSelectionFragment();
//                mydialog.setTargetFragment(MyAchievementsListAddEditFragment.this, REQUEST_CODE_SONG);
//                mydialog.show(manager, "AllSongListFragment"); //commented by jyoti

            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(arrCategory));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(spinnerArrayAdapter);

        finisherService = new FinisherServiceImpl(getActivity());/*stop*/
        sharedPreference = new SharedPreference(getActivity());/*stop*/
        rootJson = new JSONObject();

        sharedPreference.clear("GRATITUDEIMAGE");

        getValueListFromApi();

        if (getArguments() != null) {
            globalBundel = getArguments();
            if (getArguments().containsKey("GRATITUDEDATA")) {
                Gson gson = new Gson();
                globalmMyAchievementsListInnerModel = gson.fromJson(getArguments().getString("GRATITUDEDATA"), MyAchievementsListInnerModel.class);
                if (globalmMyAchievementsListInnerModel.getSong() != null)
                    songPathSelect = globalmMyAchievementsListInnerModel.getSong();

                SetLocalNotificationForAchievement.setNotificationForAchievement(globalmMyAchievementsListInnerModel, getActivity());
            }
            if (getArguments().containsKey("GRATITUDESTATUS")) {
                gratitudeStatus = getArguments().getString("GRATITUDESTATUS");
            }
            if (getArguments().containsKey("song")) {/*stop*/

                songPathSelect = getArguments().getString("song");
                if (!songPathSelect.equals("")) {
                    String[] spPath = songPathSelect.split(Pattern.quote("*##*"));
                    txtSongBtn.setText(spPath[1]);
                    if (!songPathSelect.equals(""))
                        PlayMusic(songPathSelect);
                    Log.e("song path choose", songPathSelect);
                }

            }/*stop*/

            if (getArguments().containsKey("PREVIOUS_SET_NOTIFICATION")) {
                PREVIOUS_SET_NOTIFICATION = getArguments().getString("PREVIOUS_SET_NOTIFICATION");
            }

            if(getArguments().containsKey("GRATITUDE_PIC")){
                if(getArguments().getString("GRATITUDE_PIC").equals("") || getArguments().getString("GRATITUDE_PIC") == null){
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

            }

        }

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
            if (getArguments() != null) {
                if (!getArguments().containsKey("song")) {
                    if (globalmMyAchievementsListInnerModel.getSong() != null)
                        PlayMusic(globalmMyAchievementsListInnerModel.getSong());
                }
            }

            //added by jyotirmoy
            try {
                dateShow.setText(simpleFormat.format(format.parse(globalmMyAchievementsListInnerModel.getCreatedAt())));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            //  dateShow.setText(globalmMyAchievementsListInnerModel.getCreatedAt());


            if (gratitudeStatus.equals("EDIT")) {
                //added by jyotirmoy

                if(globalmMyAchievementsListInnerModel.getAchievement()!=null && !globalmMyAchievementsListInnerModel.getAchievement().equals("") && globalmMyAchievementsListInnerModel.getPrompt()!=null && !globalmMyAchievementsListInnerModel.getPrompt().equals(""))
                {
                    editGratitudeOne.setText(globalmMyAchievementsListInnerModel.getPrompt()+":");
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
                                .listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                                        imgJournalLoadingBar.setVisibility(View.GONE);
                                        imgGratitudeMainCard.setVisibility(View.GONE);

                                        return false;

                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
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
                                    .listener(new RequestListener<String, GlideDrawable>() {
                                        @Override
                                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {

                                            imgJournalLoadingBar.setVisibility(View.GONE);
                                            imgGratitudeMainCard.setVisibility(View.GONE);

                                            return false;

                                        }

                                        @Override
                                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
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
                                        .listener(new RequestListener<Uri, GlideDrawable>() {
                                            @Override
                                            public boolean onException(Exception e, Uri model, Target<GlideDrawable> target, boolean isFirstResource) {
                                                imgJournalLoadingBar.setVisibility(View.GONE);
                                                imgGratitudeMainCard.setVisibility(View.GONE);

                                                return false;
                                            }

                                            @Override
                                            public boolean onResourceReady(GlideDrawable resource, Uri model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
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
                pickImageFromGallery();
            }
        });

        imgDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgGratitudeMain.setBackground(null);
                // imgGratitudeMain.setBackgroundResource(R.drawable.empty_image);
                stringImg = "";
                globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(stringImg);
                deleteImage();

            }
        });


        chkSetReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                makeSaveButtonGreen();
                if (isChecked) {
                    if (gratitudeStatus.equals("EDIT")) {
                        if (!edtGratitudeName.getText().toString().equals("")) {

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
                        if (!edtGratitudeName.getText().toString().equals("")) {
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
                    if (PREVIOUS_SET_NOTIFICATION.equals("TRUE")) {
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
                            PREVIOUS_SET_NOTIFICATION = "FALSE";
                            txtSetReminder.setText("Set a Reminder");
                        });

                        rlBottom.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION = "FALSE";
                            txtSetReminder.setText("Set a Reminder");
                        });


                        rlEditReminder.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(true);
                        });

                        rlTurnOffReminder.setOnClickListener(view1 -> {
                            dialog.dismiss();
                            chkSetReminder.setChecked(false);
                            PREVIOUS_SET_NOTIFICATION = "FALSE";
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
                            if (!edtGratitudeName.getText().toString().equals("")) {
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
                                //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
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

                                saveAddGratitudeData(hashMap);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (!edtGratitudeName.getText().toString().equals("")) {
                            HashMap<String, Object> requestHash = new HashMap<>();
                            if (chkSetReminder.isChecked()) {
                                globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString());
                                globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
                                //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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
                                getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString());
                                getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                                //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                getGratitudeListModelInner.setCategoryId(1);
                                //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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


                            saveAddGratitudeData(requestHash);

                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("ADD")) {
                    if (dialogOpenOnceForAdd) {

                        try {
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                try {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", null);
                                    rootJsonInner.put("Achievement", journalName);
                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", null);
                                    rootJsonInner.put("last_reminder_inserted_date", "");
                                    rootJsonInner.put("Picture", "");
                                    rootJsonInner.put("UploadPictureImgFileName", null);
                                    rootJsonInner.put("UploadPictureImg", "");
                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
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

                                saveAddGratitudeData(hashMap);

                            } else {
                                Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        if (!edtGratitudeName.getText().toString().equals("")) {
                            MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                            getGratitudeListModelInner.setId(0);
                            getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString());
                            getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                            //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                            getGratitudeListModelInner.setCategoryId(1);
                            //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
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

                            saveAddGratitudeData(requestHash);

                        } else {
                            Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("EDITCOPY")) {
                    if (!edtGratitudeName.getText().toString().equals("")) {

                        if (dialogOpenOnceForEdit) {
                            try {
                                if (!edtGratitudeName.getText().toString().equals("")) {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", globalmMyAchievementsListInnerModel.getDueDate());
                                    rootJsonInner.put("Achievement", edtGratitudeName.getText().toString());
                                    rootJsonInner.put("Notes", edtNotes.getText().toString());
                                    //rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("CategoryId", 1);
                                    rootJsonInner.put("reminder_till_date", globalmMyAchievementsListInnerModel.getReminderTillDate());
                                    rootJsonInner.put("last_reminder_inserted_date", globalmMyAchievementsListInnerModel.getLastReminderInsertedDate());
                                    rootJsonInner.put("Picture", globalmMyAchievementsListInnerModel.getPicture());
                                    rootJsonInner.put("UploadPictureImgFileName", globalmMyAchievementsListInnerModel.getUploadPictureImgFileName());
                                    rootJsonInner.put("UploadPictureImg", globalmMyAchievementsListInnerModel.getUploadPictureImg());
                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("GRATITUDEIMAGE",""));
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

                                    saveAddGratitudeData(hashMap);

                                } else {
                                    Toast.makeText(getActivity(), "Please enter achievement name", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                HashMap<String, Object> requestHash = new HashMap<>();
                                if (chkSetReminder.isChecked()) {
                                    globalmMyAchievementsListInnerModel.setId(0);
                                    globalmMyAchievementsListInnerModel.setAchievement(edtGratitudeName.getText().toString());
                                    globalmMyAchievementsListInnerModel.setNotes(edtNotes.getText().toString());
                                    //globalmMyAchievementsListInnerModel.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    globalmMyAchievementsListInnerModel.setCategoryId(1);
                                    //globalmMyAchievementsListInnerModel.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
                                    globalmMyAchievementsListInnerModel.setSong(songPathSelect);

                                    requestHash.put("model", globalmMyAchievementsListInnerModel);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } else {
                                    MyAchievementsListInnerModel getGratitudeListModelInner = new MyAchievementsListInnerModel();
                                    getGratitudeListModelInner.setId(0);
                                    getGratitudeListModelInner.setAchievement(edtGratitudeName.getText().toString());
                                    getGratitudeListModelInner.setNotes(edtNotes.getText().toString());
                                    //getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    getGratitudeListModelInner.setCategoryId(1);
                                    //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("GRATITUDEIMAGE",""));
                                    getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                    getGratitudeListModelInner.setStatus(false);
                                    getGratitudeListModelInner.setIsDeleted(false);
                                    getGratitudeListModelInner.setSong(songPathSelect);

                                    requestHash.put("model", getGratitudeListModelInner);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                                }


                                saveAddGratitudeData(requestHash);

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

                deleteGratitude();

            }
        });

        imgCopy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Gson gson = new Gson();
                Bundle bundle = new Bundle();
                bundle.putString("GRATITUDESTATUS", "EDITCOPY");
                bundle.putString("GRATITUDEDATA", gson.toJson(globalmMyAchievementsListInnerModel));
                ((MainActivity) getActivity()).loadFragment(new MyAchievementsListAddEditFragment(), "MyAchievementsListAddEdit", bundle);

            }
        });

        if (getArguments() != null && getArguments().containsKey("OPEN_NOTIFICATION")) {
            if (!new SharedPreference(getActivity()).read("FTIME_VIDEO_INFO", "").equals("") && getArguments().getString("OPEN_NOTIFICATION").equals("TRUE")) {
                rlSetReminder.performClick();
            }
        }


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
            openDialogForTextOverPicOption(gratitudeName, gratitudeID, "textAndPic");
        });

        rlTextAndPicEx.setOnClickListener(view -> {
            openDialogForShareExamples("textAndPic");
        });

        rlTextOverPic.setOnClickListener(view -> {
            strDialogSelectionType = "textOverPic";
            dialog.dismiss();
            openDialogForTextOverPicOption(gratitudeName, gratitudeID, "textOverPic");
        });

        rlTextOverPicEx.setOnClickListener(view -> {
            openDialogForShareExamples("textOverPic");
        });

        rlTextOnly.setOnClickListener(view -> {
            strDialogSelectionType = "textOnly";
            dialog.dismiss();
            openDialogForTextOverPicOption(gratitudeName, gratitudeID, "textOnly");
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
            pickImageFromGallery();

        });
        buttonChangeBackgroundImageTOP.setOnClickListener(view -> {
            globalPickImageFromGratitudeShareDialog = true;
            pickImageFromGallery();
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
            dialog.dismiss();
            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievementList", null);
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
                    funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextOverPicInnerTOP);
                } else {
                    funcForShareImageGratitudeSharability(rlSharableSectionTOP, gratitudeID, edtTextANDPic);
                }
            }
        });

        dialog.show();
    }

    private void funcForShareImageGratitudeSharability(RelativeLayout rlPicSection, Integer gratitudeID, EditText edtGratitudeName) {
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

        String journalName = edtGratitudeName.getText().toString().trim();
        dialogOpenOnceForEdit = true;

        rootJsonInner = new JSONObject();

        if (dialogOpenOnceForEdit) {

            try {
                if (!edtGratitudeName.getText().toString().equals("")) {
                    rootJsonInner.put("Id", globalmMyAchievementsListInnerModel.getId());
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
                    saveAddGratitudeDataForShare(hashMap, shareFile);

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
                    MyAchievementsListInnerModel globalmMyAchievementsListInnerModel = new MyAchievementsListInnerModel();
                    globalmMyAchievementsListInnerModel.setId(globalmMyAchievementsListInnerModel.getId());
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
                saveAddGratitudeDataForShare(requestHash, shareFile);

            } else {
                Toast.makeText(getActivity(), "Please enter gratitude name", Toast.LENGTH_SHORT).show();
            }

        }

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            PendingIntent pi = PendingIntent.getBroadcast(getActivity(), 2222,
                    new Intent(getActivity(), GratitudeShareBroadCast.class),
                    PendingIntent.FLAG_UPDATE_CURRENT);
            share = Intent.createChooser(share, "Share Image", pi.getIntentSender());
            Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" +
                            ".fileprovider"
                    , shareFile);
            share.putExtra(Intent.EXTRA_STREAM, photoURI);
            startActivity(share);
        }*/

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
            Util.showToast(getActivity(), Util.networkMsg);
        }

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

    private void makeSaveButtonGreen() {
        somethingChanges = true;
        rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
    }
    /*stop*/
    private void stopMusic() {
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
    }
    /*stop*/
    private void deleteGratitude() {

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
                            Util.isReloadTodayMainPage = true;

                            Completable.fromAction(() -> {
                                        gratitudeViewModel.deleteGrowthById(globalmMyAchievementsListInnerModel.getId());
                                    }).subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(() -> {
                                        Log.e("GROWTH_DELETE", "TRUE" + ">>>>>");
                                        Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                                        ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                    }, throwable -> {
                                        Log.e("GROWTH_DELETE", "FALSE" + ">>>>>");
                                        Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                                        ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                        ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                                        ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
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
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void saveAddGratitudeData(HashMap<String, Object> reqhash) {


        if (Util.checkConnection(getActivity())) {

            //progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            final ProgressDialog progressDialog = Util.getDeterminantProgress(getActivity(), getString(R.string.txt_upload_message));
            ProgressRequestBody fileBody = null;
            // mFile = null;
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
                            //progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().getSuccessFlag()) {

                                    if (uploadCallback != null) {
                                        int image_type = 3;

                                        UploadEntity entity = new UploadEntity(image_type, response.body().getDetails().getId(), ANNUAL_CROP_PATH, false);
                                        uploadCallback.addInQueue(entity);
                                    }

                                    Util.isReloadTodayMainPage = true;

                                    Toast.makeText(getActivity(), "Data successfully saved", Toast.LENGTH_SHORT).show();
                                    ((MainActivity) getActivity()).refershGamePoint(getActivity());

                                    SetLocalNotificationForAchievement.setNotificationForAchievement(response.body().getDetails(), getActivity());
                                    Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new MyAchievementsFragment());
                                            ((MainActivity) getActivity()).loadFragment(new MyAchievementsFragment(), "MyAchievements", null);
                                            ((MainActivity) getActivity()).toolbar.setVisibility(View.VISIBLE);
                                        }
                                    }, 2000);

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
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void deleteImage() {

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
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void getValueListFromApi() {
        if (Connection.checkConnection(getActivity())) {
            final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");
            SharedPreference sharedPreference = new SharedPreference(getActivity());

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", sharedPreference.read("UserID", ""));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", sharedPreference.read("UserSessionID", ""));


            FinisherServiceImpl finisherService = new FinisherServiceImpl(getActivity());
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
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }


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
                somethingChanges = true;
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
                                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, 201);
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
                //   startActivity(new Intent(getActivity(), CameraActivity.class));
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
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
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
        java.util.Date date = new java.util.Date();
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

    private void cropPhoto(final Bitmap bitmap, final String imgPath) {
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
        // cropImageView.setAspectRatio(DEFAULT_ASPECT_RATIO_VALUES, DEFAULT_ASPECT_RATIO_VALUES);

        //cropImageView.setAspectRatio(1, 1);
        // cropImageView.setMinimumWidth(320);
        // cropImageView.setMinimumHeight(116);
        cropImageView.setFixedAspectRatio(false);
        //cropImageView.setAspectRatio(1, 1);
        //cropImageView.setFixedAspectRatio(true);
        // Log.e("print src------>",src+""+">>>>>");

        txtDone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                imgJournalLoadingBar.setVisibility(View.VISIBLE);

                try {
                    // TODO Auto-generated method stub
                    bitimage = cropImageView.getCroppedImage();
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    imgGratitudeMain.setImageBitmap(null);
                    imgGratitudeMain.setAdjustViewBounds(true);
                    imgGratitudeMain.setImageBitmap(bitimage);
                    dialogcrop.dismiss();
                    // new ImageloadAsyn(bitimage).execute();
                    //Uri tempUri = getImageUri(getActivity(), bitimage);
                    //String cropPath = getRealPathFromURI(tempUri);
                    String cropPath = storeImage(bitimage);
                    preaprePictureForUpload(cropPath);
                    imgJournalLoadingBar.setVisibility(View.GONE);
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
                    Bitmap myBitmapCancel = decodeSampledBitmapFromFile(imgPath, ViewGroup.LayoutParams.FILL_PARENT, height);
                    imgGratitudeMain.setImageBitmap(myBitmapCancel);
                    preaprePictureForUpload(imgPath);
                    dialogcrop.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //dialogcrop.addContentView(vi, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        dialogcrop.show();
    }

    private String storeImage(Bitmap image) {
        File pictureFile = getOutputMediaFile();
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

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        // To be safe, you should check that the SDCard is mounted
        File mediaStorageDir = Util.getFile(getContext()); //added by jyotirmoy patra
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName = "MI_" + timeStamp + ".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
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
            sharedPreference.write("GRATITUDEIMAGE", "", stringImg);
            mFile = file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            //imgGratitudeMain.setBackground(null);
            imgGratitudeMain.setBackgroundResource(0);
        }
        imgGratitudeMain.setImageBitmap(BitmapFactory.decodeFile(cropPath));

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
                imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(cropPath));
                cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
                imgBackgroundPicTOP.setVisibility(View.VISIBLE);
                llAddPicTOP.setVisibility(View.GONE);
                buttonChangeBackgroundImageTOP.setVisibility(View.VISIBLE);
                if (strDialogSelectionType.equals("textOverPic")) {
                    buttonMoveTextBoxTOP.setVisibility(View.VISIBLE);
                }
                /*rlTextOverPicInnerTOP.setVisibility(View.GONE);
                rlTextOverPicInnerTOP.setVisibility(View.VISIBLE);*/
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

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


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("PICTURE FRAGMENT", "trueeeeeeeee");
        if (requestCode != 0) {
            if (requestCode == REQUEST_CODE_SONG) {
                String songPath = data.getStringExtra("song");
                Log.e("song path-->", songPath + "?");
                if (!songPath.equals(""))
                    PlayMusic(songPath);
            } else {
                if (data == null) {
                    if (!imgPath.equals("")) {
                        File selectedImagePath = new File(imgPath);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            imgGratitudeMain.setBackground(null);
                        }
                        imgGratitudeMain.setImageBitmap(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()));
                        imgDecodableString = selectedImagePath.getAbsolutePath();
                        cropPhoto(BitmapFactory.decodeFile(selectedImagePath.getAbsolutePath()), imgDecodableString);



                    } else
                        Toast.makeText(getActivity(), "An error occured", Toast.LENGTH_SHORT);
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

                        //stringImg=Util.convertImageToString(new Util().compressImage(imgDecodableString));-----------------
                        //  Log.e("Print img path--->",imgDecodableString+"????");

                        //j17
                        File compressImg=null;
                        try {
                            compressImg=new Compressor(getContext()).compressToFile(new File(imgDecodableString));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        long bitmapLengthSize = compressImg.length();
                        Log.e("galcompress img", "Gal com img size>>>>>>>>>" + (bitmapLengthSize*0.001)+" KB");

                        cursor.close();
                        cropPhoto(BitmapFactory.decodeFile(imgDecodableString), compressImg.getAbsolutePath());



                    }

                } else if (requestCode == CAMERA_PIC_REQUEST && resultCode == getActivity().RESULT_OK && null != data) {
                    imgDecodableString = out.getPath();
                    Bitmap bit = BitmapFactory.decodeFile(imgDecodableString);
                    if (bit != null) {
                        cropPhoto(bit, imgDecodableString);
                    }
                } else {
                    Toast.makeText(getActivity(), "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }
            }


        } else {
            Toast.makeText(getActivity(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
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
                return;
            }
            case 201: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission cam", "Granted");
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    out = createFolder();
                    imgPath = out.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".fileprovider", out);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                } else {
                    Log.e("Permission cam", "Denied");
                }
                return;
            }
            // startActivity(new Intent(getActivity(), CameraActivity.class));
        }
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

    private void PlayMusic(String DataStream) {
        if (!DataStream.equals("")) {
            stopMusic();
            Log.e("print path", DataStream + "?");
            songPathSelect = DataStream;
            if (globalmMyAchievementsListInnerModel != null) {
                globalmMyAchievementsListInnerModel.setSong(songPathSelect);
            }
            String[] spPath = songPathSelect.split(Pattern.quote("*##*"));
            txtSongBtn.setText(spPath[1]);
            Log.e("song path choose", spPath[0]);
            DataStream = spPath[0];
            mpObject = new MediaPlayer();
            if (DataStream == null)
                return;
            try {
                mpObject.setDataSource(DataStream);
                mpObject.prepare();
                mpObject.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

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

