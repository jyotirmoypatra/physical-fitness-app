package com.ashysystem.mbhq.fragment.bucket;

import static android.app.Activity.RESULT_OK;

import com.ashysystem.mbhq.activity.CameraNewActivity;
import com.ashysystem.mbhq.activity.ImageCropperActivity;
import com.ashysystem.mbhq.fragment.CustomReminderDialog;
import com.ashysystem.mbhq.fragment.CustomReminderDialogForEdit;
import com.ashysystem.mbhq.fragment.CustomReminderDialogForEditBucket;
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
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.listener.UploadCallback;
import com.ashysystem.mbhq.model.BucketListModelInner;
import com.ashysystem.mbhq.model.MyValueListResponse;
import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.AlertDialogWithCustomButton;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.DatePickerControllerFromTextView;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.edmodo.cropper.CropImageView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Pattern;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BucketAddEditFragment extends Fragment {
    ImageView editPopupBtn ;
    TextView txtGoalValue1, txtGoalValue2, txtGoalValue3, txtUploadChangeImage, txtSetReminder, txtCompletionDate;
    EditText edtGratitudeName;
    ImageView imgEdit, imgGratitudeMain, imgDeleteIcon, imgCopy,  clickPhoto,   galleryPhoto,chooseDate,      rlSaveAchievement;
    RelativeLayout editLayout,rlChangeImage, rlEditCopy, rlSetReminder, rlCompletionDate, rlCategory,     imgDeleteGratitude,    imgSaveGratitude;
    LinearLayout buttonPanelEditmode,llSaveDelete, llChangeImageDelete,        rl_status;
    CheckBox chkSetReminder;
    Spinner spCategory;
    View view12,view13;
    BucketListModelInner globalGetGratitudeListModelInner;
    private String imgPath = "";
    private final int REQUEST_IMAGE_CAPTURE = 304;

    private String imgDecodableString = "";
    String stringImg = "";
    private static int RESULT_LOAD_IMG = 1434;
    private static int CAMERA_PIC_REQUEST = 1437;
    private static int REQUEST_CODE_SONG = 500;
    String gratitudeStatus = "";
    FinisherServiceImpl finisherService;
    SharedPreference sharedPreference;
    ProgressDialog progressDialog;
    boolean editClicked = false;
    private RelativeLayout rlBack;

    private CropImageView cropImageView;
    private Bitmap onLineBitMap = null;
    private ImageView imgContainer;
    private Bitmap bitimage;

    JSONObject rootJson;
    JSONObject rootJsonInner;
    boolean dialogOpenOnceForEdit = false, dialogOpenOnceForAdd = false;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat simpleFormat = new SimpleDateFormat("dd/MM/yyyy");
    TextView txtSongBtn;
    private String songPathSelect = "";
    MediaPlayer mpObject = null;
    private RelativeLayout rlSong;
    private Bundle globalBundel;
    File out;
    String[] arrCategory = new String[]{"Fitness", "Nutrition", "Mindset", "Relationship", "Spiritual", "Business", "Personal Development", "Body/Physical Appearance", "Finance", "Health", "Lifestyle/holidays"};
    private File mFile;
    private CheckBox chkInPRog, chkCompleted, chkHideen;
    private CompoundButton.OnCheckedChangeListener completedChkListener, chkHiddenListen, chkProgListener;
    private TextWatcher globalTextChangeListener = null;
    private boolean changeByUser;
    private String cropPath = "";
    //String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";
    String path = Environment.getExternalStorageDirectory()
            + "/Android/data/"
            + "com.ashysystem.mbhq"
            + "/Files/";
    private boolean boolHiddenSaveSuccess = false;

    private String ANNUAL_CROP_PATH = "";
    File photoFile = null;
    String mCurrentPhotoPath = "";


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void openCustomCamera() {
        Intent intent = new Intent(getActivity(), CameraNewActivity.class);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_addeditbucket, null);
        funToolBar();
        view12= view.findViewById(R.id.view12);
        view13= view.findViewById(R.id.view13);
        rlSaveAchievement= view.findViewById(R.id.rlSaveAchievement);
        clickPhoto= view.findViewById(R.id.clickPhoto);
        galleryPhoto= view.findViewById(R.id.galleryPhoto);
        chooseDate= view.findViewById(R.id.chooseDate);
        view12.setVisibility(View.GONE);
        view13.setVisibility(View.GONE);
        buttonPanelEditmode= view.findViewById(R.id.buttonPanelEditmode);
        editLayout= view.findViewById(R.id.editLayout);
        rl_status= view.findViewById(R.id.rl_status);
        rl_status.setVisibility(View.GONE);
        editPopupBtn = view.findViewById(R.id.editPopupBtn);
        chkInPRog = view.findViewById(R.id.chkInPRog);
        chkCompleted = view.findViewById(R.id.chkCompleted);
        chkHideen = view.findViewById(R.id.chkHideen);
        rlBack = view.findViewById(R.id.rlBack);
        txtGoalValue1 = (TextView) view.findViewById(R.id.txtGoalValue1);
        txtGoalValue2 = (TextView) view.findViewById(R.id.txtGoalValue2);
        txtGoalValue3 = (TextView) view.findViewById(R.id.txtGoalValue3);
        txtSetReminder = (TextView) view.findViewById(R.id.txtSetReminder);
        txtUploadChangeImage = (TextView) view.findViewById(R.id.txtUploadChangeImage);
        txtCompletionDate = (TextView) view.findViewById(R.id.txtCompletionDate);
        edtGratitudeName = (EditText) view.findViewById(R.id.edtGratitudeName);

        imgEdit = (ImageView) view.findViewById(R.id.imgEdit);
        imgGratitudeMain = (ImageView) view.findViewById(R.id.imgGratitudeMain);
        imgDeleteIcon = (ImageView) view.findViewById(R.id.imgDeleteIcon);
        imgCopy = (ImageView) view.findViewById(R.id.imgCopy);
        //imgSaveGratitude = view.findViewById(R.id.imgSaveGratitude);
        // imgDeleteGratitude = view.findViewById(R.id.imgDeleteGratitude);
        rlChangeImage = (RelativeLayout) view.findViewById(R.id.rlChangeImage);
        rlEditCopy = (RelativeLayout) view.findViewById(R.id.rlEditCopy);
        rlSetReminder = (RelativeLayout) view.findViewById(R.id.rlSetReminder);
        rlSong = (RelativeLayout) view.findViewById(R.id.rlSong);
        rlCompletionDate = (RelativeLayout) view.findViewById(R.id.rlCompletionDate);
        rlCompletionDate.setVisibility(View.GONE);
        rlCategory = (RelativeLayout) view.findViewById(R.id.rlCategory);
        //  llSaveDelete=(LinearLayout)view.findViewById(R.id.llSaveDelete);
        llChangeImageDelete = (LinearLayout) view.findViewById(R.id.llChangeImageDelete);
        chkSetReminder = (CheckBox) view.findViewById(R.id.chkSetReminder);
        txtSongBtn = (TextView) view.findViewById(R.id.txtSongBtn);
        spCategory = (Spinner) view.findViewById(R.id.spCategory);

        rlCompletionDate.setVisibility(View.GONE);
        changeByUser_();
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                {
                    int newColor = ContextCompat.getColor(getActivity(), R.color.mbhq_base_color); // Change 'new_color' to the desired color resource or value
                    clickPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                    galleryPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                    chooseDate.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);


                    SimpleDateFormat myDateFormat=new SimpleDateFormat("MM/dd/yyyy");
                    DatePickerControllerFromTextView datePickerControllerFromTextView = new DatePickerControllerFromTextView(getActivity(), txtCompletionDate, myDateFormat.format(new Date()), false);
                    changeByUser();
                }
            }
        });
        /*commented by sahenita (temporary)*/
        clickPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int newColor = ContextCompat.getColor(getActivity(), R.color.mbhq_base_color); // Change 'new_color' to the desired color resource or value
                clickPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                galleryPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                chooseDate.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);


                changeByUser = true;
                changeByUser();
                // pickImageFromGallery();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission() && hasGalleryPermission()) {

                        openCustomCamera();

                    } else {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.CAMERA}, 203);
                        }else{
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 203);
                        }
                    }

                } else {
                    openCustomCamera();
                }
                rlSaveAchievement.setImageResource(R.drawable.ic_check);
                //  rlSaveAchievement.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));

            }
        });
        /*commented by sahenita (temporary)*/

        galleryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newColor = ContextCompat.getColor(getActivity(), R.color.mbhq_base_color); // Change 'new_color' to the desired color resource or value
                clickPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                galleryPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                chooseDate.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);

                changeByUser = true;
                changeByUser();


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasGalleryPermission()) {
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

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
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                }
                rlSaveAchievement.setImageResource(R.drawable.ic_check);
            }

        });


        rlChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newColor = ContextCompat.getColor(getActivity(), R.color.mbhq_base_color); // Change 'new_color' to the desired color resource or value
                clickPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                galleryPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                chooseDate.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);

                changeByUser = true;
                changeByUser();

            }
        });
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
                        view12.setVisibility(View.VISIBLE);
                        view13.setVisibility(View.VISIBLE);
                        rlCompletionDate.setVisibility(View.VISIBLE);
                        editLayout.setVisibility(View.VISIBLE);
                        rl_status.setVisibility(View.GONE);
                        buttonPanelEditmode.setVisibility(View.VISIBLE);
                        // Util.enableTextField(edtGratitudeName);
                        // editEnable = true;
                        rlCompletionDate.setVisibility(View.VISIBLE);
                        edtGratitudeName.setBackground(getResources().getDrawable(R.drawable.edittextjournal_shape));
                        edtGratitudeName.setMinLines(3);
                        edtGratitudeName.setFocusable(true);
                        dialogEdit.dismiss();


                    }
                });
                ImageView img_status=(ImageView) dialogEdit.findViewById(R.id.img_status);
                img_status.setVisibility(View.INVISIBLE);
                img_status.setEnabled(false);
                img_status.setClickable(false);
                RelativeLayout shareAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.shareAchievement);

                shareAchievement.setVisibility(View.VISIBLE);

                TextView tv_status= (TextView) dialogEdit.findViewById(R.id.tv_status);

                tv_status.setText("STATUS");

                shareAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEdit.dismiss();
                        rl_status.setVisibility(View.VISIBLE);

                        buttonPanelEditmode.setVisibility(View.GONE);
                        view12.setVisibility(View.GONE);
                        view13.setVisibility(View.GONE);
                        rlCompletionDate.setVisibility(View.GONE);
                        editLayout.setVisibility(View.GONE);

                    }
                });
                RelativeLayout deleteAchievement = (RelativeLayout) dialogEdit.findViewById(R.id.deleteAchievement);
                deleteAchievement.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogEdit.dismiss();



                        AlertDialogWithCustomButton alertDialogCustom = new AlertDialogWithCustomButton(getActivity());
                        alertDialogCustom.ShowDialog("Delete confirmation", "Are you sure you want to delete this bucket? ", true, "CONFIRM", "NO");
                        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                            @Override
                            public void onDone(String title) {
                                deleteGratitude();
                            }

                            @Override
                            public void onCancel(String title) {

                            }
                        });
                        // rlDeleteAchievement.performClick();
                        // dialogEdit.dismiss();
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




        txtSongBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*commented by sahenita*/
               /* stopMusic();
                FragmentManager manager = getFragmentManager();
                final SongSelectionFragment mydialog = new SongSelectionFragment();
                mydialog.setTargetFragment(BucketAddEditFragment.this, REQUEST_CODE_SONG);
                mydialog.show(manager, "AllSongListFragment");*/
            }
        });

        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Arrays.asList(arrCategory));
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCategory.setAdapter(spinnerArrayAdapter);

        finisherService = new FinisherServiceImpl(getActivity());
        sharedPreference = new SharedPreference(getActivity());
        rootJson = new JSONObject();

        sharedPreference.clear("BUCKETIMAGE");

        getValueListFromApi();

        if (getArguments() != null) {
            globalBundel = getArguments();
            if (getArguments().containsKey("GRATITUDEDATA")) {
                Gson gson = new Gson();
                globalGetGratitudeListModelInner = gson.fromJson(getArguments().getString("GRATITUDEDATA"), BucketListModelInner.class);
              /*commented by sahenita*/
               // SetLocalNotificationForBucket.SetLocalNotificationForBucket(globalGetGratitudeListModelInner, getActivity());
            }
            if (getArguments().containsKey("GRATITUDESTATUS")) {
                gratitudeStatus = getArguments().getString("GRATITUDESTATUS");
            }
            if (getArguments().containsKey("song")) {
                songPathSelect = getArguments().getString("song");

                if (!songPathSelect.equals(""))
                    PlayMusic(songPathSelect);
                Log.e("song path choose", songPathSelect);
            }

        }


        if (globalGetGratitudeListModelInner != null) {
            if (getArguments() != null) {
                if (!getArguments().containsKey("song")) {
                    if (globalGetGratitudeListModelInner.getSong() != null)
                        PlayMusic(globalGetGratitudeListModelInner.getSong());
                }
            }


            if (gratitudeStatus.equals("EDIT")) {
                //edtGratitudeName.setText(globalGetGratitudeListModelInner.getName().toUpperCase());
                edtGratitudeName.setText(globalGetGratitudeListModelInner.getName());
            } else if (gratitudeStatus.equals("EDITCOPY")) {
                //edtGratitudeName.setText("COPY OF : " + globalGetGratitudeListModelInner.getName().toUpperCase());
                edtGratitudeName.setText("COPY OF : " + globalGetGratitudeListModelInner.getName());
            }


            String imgPath = globalGetGratitudeListModelInner.getPictureDevicePath();
            Log.e("img path rcvd--", imgPath + "??");
            if (imgPath!=null&&!imgPath.equals("")) {

                String filePath = /*path+*/ globalGetGratitudeListModelInner.getPictureDevicePath();
                File file = new File(filePath);
                Log.e("Path", ">>>>>>>>>>>>>> "+file.getAbsolutePath());
                if (file.exists()) {
                    Uri imageUri = Uri.fromFile(file);
                    Glide.with(getActivity())
                            .load(imageUri)
                            .placeholder(R.drawable.empty_image_old)
                            .error(R.drawable.empty_image_old)
                            .dontAnimate()
                            .into(imgGratitudeMain);
                }else {
                    try {
                        Glide.with(getActivity())
                                .load(globalGetGratitudeListModelInner.getPicture())
                                .placeholder(R.drawable.empty_image_old)
                                .error(R.drawable.empty_image_old)
                                .dontAnimate()
                                .into(imgGratitudeMain);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                cropPath=imgPath;
            }else {
                try {
                    Glide.with(getActivity())
                            .load(globalGetGratitudeListModelInner.getPicture())
                            .placeholder(R.drawable.empty_image_old)
                            .error(R.drawable.empty_image_old)
                            .dontAnimate()
                            .into(imgGratitudeMain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            globalTextChangeListener = new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0)
                        changeByUser();
                }
            };
            edtGratitudeName.addTextChangedListener(globalTextChangeListener);
            //edtGrateful.setText(globalGetGratitudeListModelInner.getDescription());
            try {
                txtCompletionDate.setText(simpleFormat.format(format.parse(globalGetGratitudeListModelInner.getCompletionDate())));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (globalGetGratitudeListModelInner.getPicture() != null && !globalGetGratitudeListModelInner.getPicture().equals("")) {
                String largeImage = globalGetGratitudeListModelInner.getPicture().replace("thumbnails/", "");
                /*Glide.with(getActivity())
                        .load(largeImage)
                        .placeholder(R.drawable.empty_image)
                        .error(R.drawable.empty_image)
                        .dontAnimate()
                        .into(imgGratitudeMain);*/


                Glide.with(getActivity())
                        .asBitmap()
                        .load(largeImage)
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.empty_image_old)
                        .error(R.drawable.empty_image_old)
                        .into(new CustomTarget<Bitmap>() {


                            @Override
                            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                stringImg = encodeImage(byteArray);
                                Log.e("BASE64STRING", stringImg);
                                //sharedPreference.write("BUCKETIMAGE","",stringImg);
                                imgGratitudeMain.setImageBitmap(resource);
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {
                                // Implement if needed
                            }
                        });



/*
                Glide.with(getActivity())
                        .load(largeImage)
                        .asBitmap()
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.empty_image_old)
                        .error(R.drawable.empty_image_old)
                        .into(new SimpleTarget<Bitmap>() {
                            @Override
                            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                                //Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.visiondefault);
                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                resource.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                byte[] byteArray = byteArrayOutputStream.toByteArray();
                                stringImg = encodeImage(byteArray);
                                Log.e("BASE64STRING", stringImg);
                                //sharedPreference.write("BUCKETIMAGE","",stringImg);
                                imgGratitudeMain.setImageBitmap(resource);
                            }
                        });
*/

            }
            if (globalGetGratitudeListModelInner.getPushNotification() != null && globalGetGratitudeListModelInner.getPushNotification()) {
                chkSetReminder.setChecked(true);
                txtSetReminder.setText("View Reminder");
            }
            if (globalGetGratitudeListModelInner.getCategoryId() != null) {
                spCategory.setSelection(globalGetGratitudeListModelInner.getCategoryId() - 1);
            }
            if (globalGetGratitudeListModelInner.getBucketStatus() == 0) {
                chkHideen.setChecked(true);
                boolHiddenSaveSuccess = true;
            } else if (globalGetGratitudeListModelInner.getBucketStatus() == 1) {
                chkInPRog.setChecked(true);
                boolHiddenSaveSuccess = false;
            } else if (globalGetGratitudeListModelInner.getBucketStatus() == 2) {
                chkCompleted.setChecked(true);
                boolHiddenSaveSuccess = false;
            }

        }
        chkHiddenListen = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkCompleted.setOnCheckedChangeListener(null);
                chkCompleted.setChecked(false);
                chkCompleted.setOnCheckedChangeListener(completedChkListener);
                chkInPRog.setOnCheckedChangeListener(null);
                chkInPRog.setChecked(false);
                chkInPRog.setOnCheckedChangeListener(chkProgListener);
                changeBucketStatusApi(globalGetGratitudeListModelInner.getId(), isChecked, 0);

            }
        };
        chkProgListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkCompleted.setOnCheckedChangeListener(null);
                chkCompleted.setChecked(false);
                chkCompleted.setOnCheckedChangeListener(completedChkListener);
                chkHideen.setOnCheckedChangeListener(null);
                chkHideen.setChecked(false);
                chkHideen.setOnCheckedChangeListener(chkHiddenListen);
                changeBucketStatusApi(globalGetGratitudeListModelInner.getId(), isChecked, 1);

            }
        };

        completedChkListener = new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                chkHideen.setOnCheckedChangeListener(null);
                chkHideen.setChecked(false);
                chkHideen.setOnCheckedChangeListener(chkHiddenListen);
                chkInPRog.setOnCheckedChangeListener(null);
                chkInPRog.setChecked(false);
                chkInPRog.setOnCheckedChangeListener(chkProgListener);
                changeBucketStatusApi(globalGetGratitudeListModelInner.getId(), isChecked, 2);

            }
        };
        chkCompleted.setOnCheckedChangeListener(completedChkListener);
        chkInPRog.setOnCheckedChangeListener(chkProgListener);
        chkHideen.setOnCheckedChangeListener(chkHiddenListen);
        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (changeByUser) {
                    final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {
                            rlSaveAchievement.performClick();
                        }

                        @Override
                        public void onCancel(String title) {
                            ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
                        }
                    });

                } else {
                    ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
                }
            }
        });
        rlSetReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gratitudeStatus.equals("EDIT")) {
                    if (chkSetReminder.isChecked() && txtSetReminder.getText().toString().equals("View Reminder")) {
                        CustomReminderDialogForEditBucket customReminderDialog = new CustomReminderDialogForEditBucket();
                        Gson gson = new Gson();
                        Bundle bundle = new Bundle();
                        bundle.putString("MODEL", gson.toJson(globalGetGratitudeListModelInner));
                        bundle.putString("MODELNAME", "GRATITUDE");
                        customReminderDialog.setArguments(bundle);
                        customReminderDialog.mListener = new CustomReminderDialogForEditBucket.onSubmitListener() {
                            @Override
                            public void setOnSubmitListener(JSONObject arg) {
                                Log.e("print jj", arg.toString() + "?");
                                dialogOpenOnceForEdit = true;
                                rootJsonInner = arg;
                                try {
                                    globalGetGratitudeListModelInner.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                                    globalGetGratitudeListModelInner.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                                    globalGetGratitudeListModelInner.setReminderOption(rootJsonInner.getInt("ReminderOption"));
                                    globalGetGratitudeListModelInner.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                                    globalGetGratitudeListModelInner.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                                    globalGetGratitudeListModelInner.setEmail(rootJsonInner.getBoolean("Email"));
                                    globalGetGratitudeListModelInner.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                                    globalGetGratitudeListModelInner.setSunday(rootJsonInner.getBoolean("Sunday"));
                                    globalGetGratitudeListModelInner.setMonday(rootJsonInner.getBoolean("Monday"));
                                    globalGetGratitudeListModelInner.setTuesday(rootJsonInner.getBoolean("Tuesday"));
                                    globalGetGratitudeListModelInner.setWednesday(rootJsonInner.getBoolean("Wednesday"));
                                    globalGetGratitudeListModelInner.setThursday(rootJsonInner.getBoolean("Thursday"));
                                    globalGetGratitudeListModelInner.setFriday(rootJsonInner.getBoolean("Friday"));
                                    globalGetGratitudeListModelInner.setSaturday(rootJsonInner.getBoolean("Saturday"));
                                    globalGetGratitudeListModelInner.setJanuary(rootJsonInner.getBoolean("January"));
                                    globalGetGratitudeListModelInner.setFebruary(rootJsonInner.getBoolean("February"));
                                    globalGetGratitudeListModelInner.setMarch(rootJsonInner.getBoolean("March"));
                                    globalGetGratitudeListModelInner.setApril(rootJsonInner.getBoolean("April"));
                                    globalGetGratitudeListModelInner.setMay(rootJsonInner.getBoolean("May"));
                                    globalGetGratitudeListModelInner.setJune(rootJsonInner.getBoolean("June"));
                                    globalGetGratitudeListModelInner.setAugust(rootJsonInner.getBoolean("August"));
                                    globalGetGratitudeListModelInner.setSeptember(rootJsonInner.getBoolean("September"));
                                    globalGetGratitudeListModelInner.setOctober(rootJsonInner.getBoolean("October"));
                                    globalGetGratitudeListModelInner.setNovember(rootJsonInner.getBoolean("November"));
                                    globalGetGratitudeListModelInner.setDecember(rootJsonInner.getBoolean("December"));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                ///////////////////


                            }
                        };
                        customReminderDialog.show(getFragmentManager(), "");
                    }

                }

            }
        });




        imgDeleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                alertDialogCustom.ShowDialog("Alert", "Do you want to delete this image ?", true);
                alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                    @Override
                    public void onDone(String title) {
                        imgGratitudeMain.setBackground(null);
                        //  imgGratitudeMain.setBackgroundResource(R.drawable.empty_image);
                        stringImg = "";
                        globalGetGratitudeListModelInner.setUploadPictureImgBase64(stringImg);
                        deleteImage();
                    }

                    @Override
                    public void onCancel(String title) {

                    }
                });


            }
        });


        chkSetReminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                int newColor = ContextCompat.getColor(getActivity(), R.color.mbhq_base_color); // Change 'new_color' to the desired color resource or value
                clickPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                galleryPhoto.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);
                chooseDate.setColorFilter(newColor, PorterDuff.Mode.MULTIPLY);

                changeByUser();
                if (isChecked) {
                    if (gratitudeStatus.equals("EDIT")) {
                        if (!edtGratitudeName.getText().toString().equals("")) {
                            txtSetReminder.setText("View Reminder");

                            CustomReminderDialogForEdit customReminderDialog = new CustomReminderDialogForEdit();
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            bundle.putString("MODEL", gson.toJson(globalGetGratitudeListModelInner));
                            bundle.putString("MODELNAME", "GRATITUDE");
                            customReminderDialog.setArguments(bundle);
                            customReminderDialog.mListener = new CustomReminderDialogForEdit.onSubmitListener() {
                                @Override
                                public void setOnSubmitListener(JSONObject arg) {
                                    Log.e("print jj", arg.toString() + "?");
                                    dialogOpenOnceForEdit = true;
                                    rootJsonInner = arg;
                                    try {
                                        globalGetGratitudeListModelInner.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                                        globalGetGratitudeListModelInner.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                                        globalGetGratitudeListModelInner.setReminderOption(rootJsonInner.getInt("ReminderOption"));
                                        globalGetGratitudeListModelInner.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                                        globalGetGratitudeListModelInner.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                                        globalGetGratitudeListModelInner.setEmail(rootJsonInner.getBoolean("Email"));
                                        globalGetGratitudeListModelInner.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                                        globalGetGratitudeListModelInner.setSunday(rootJsonInner.getBoolean("Sunday"));
                                        globalGetGratitudeListModelInner.setMonday(rootJsonInner.getBoolean("Monday"));
                                        globalGetGratitudeListModelInner.setTuesday(rootJsonInner.getBoolean("Tuesday"));
                                        globalGetGratitudeListModelInner.setWednesday(rootJsonInner.getBoolean("Wednesday"));
                                        globalGetGratitudeListModelInner.setThursday(rootJsonInner.getBoolean("Thursday"));
                                        globalGetGratitudeListModelInner.setFriday(rootJsonInner.getBoolean("Friday"));
                                        globalGetGratitudeListModelInner.setSaturday(rootJsonInner.getBoolean("Saturday"));
                                        globalGetGratitudeListModelInner.setJanuary(rootJsonInner.getBoolean("January"));
                                        globalGetGratitudeListModelInner.setFebruary(rootJsonInner.getBoolean("February"));
                                        globalGetGratitudeListModelInner.setMarch(rootJsonInner.getBoolean("March"));
                                        globalGetGratitudeListModelInner.setApril(rootJsonInner.getBoolean("April"));
                                        globalGetGratitudeListModelInner.setMay(rootJsonInner.getBoolean("May"));
                                        globalGetGratitudeListModelInner.setJune(rootJsonInner.getBoolean("June"));
                                        globalGetGratitudeListModelInner.setAugust(rootJsonInner.getBoolean("August"));
                                        globalGetGratitudeListModelInner.setSeptember(rootJsonInner.getBoolean("September"));
                                        globalGetGratitudeListModelInner.setOctober(rootJsonInner.getBoolean("October"));
                                        globalGetGratitudeListModelInner.setNovember(rootJsonInner.getBoolean("November"));
                                        globalGetGratitudeListModelInner.setDecember(rootJsonInner.getBoolean("December"));

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ////////////////////


                                }
                            };
                            customReminderDialog.show(getFragmentManager(), "");
                        } else {
                            Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                        }
                    }

                } else {
                    // dialogOpenOnceForEdit=false;////CHANGE TODAY////
                    txtSetReminder.setText("Set a Reminder");
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
                                rootJsonInner.put("Id", globalGetGratitudeListModelInner.getId());
                                rootJsonInner.put("DueDate", globalGetGratitudeListModelInner.getDueDate());
                                rootJsonInner.put("CompletionDate", globalGetGratitudeListModelInner.getCompletionDate());
                                rootJsonInner.put("Name", edtGratitudeName.getText().toString());
                                rootJsonInner.put("Description", "");
                                rootJsonInner.put("reminder_till_date", globalGetGratitudeListModelInner.getReminderTillDate());
                                rootJsonInner.put("last_reminder_inserted_date", globalGetGratitudeListModelInner.getLastReminderInsertedDate());
                                rootJsonInner.put("Picture", globalGetGratitudeListModelInner.getPicture());
                                rootJsonInner.put("UploadPictureImgFileName", globalGetGratitudeListModelInner.getUploadPictureImgFileName());
                                rootJsonInner.put("UploadPictureImg", globalGetGratitudeListModelInner.getUploadPictureImg());
                                //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("BUCKETIMAGE",""));
                                rootJsonInner.put("Song", songPathSelect);
                                rootJsonInner.put("CreatedAt", globalGetGratitudeListModelInner.getCreatedAt());
                                rootJsonInner.put("CreatedBy", globalGetGratitudeListModelInner.getCreatedBy());
                                rootJsonInner.put("IsDeleted", globalGetGratitudeListModelInner.getIsDeleted());
                                rootJsonInner.put("Status", globalGetGratitudeListModelInner.getStatus());
                                rootJsonInner.put("IsCreatedUpdated", globalGetGratitudeListModelInner.getIsCreatedUpdated());
                                rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                if(boolHiddenSaveSuccess)
                                {
                                    rootJsonInner.put("PushNotification", false);
                                }else {
                                    rootJsonInner.put("PushNotification", true);
                                }

                                Log.e("print pic path--", cropPath + "???");
                                if (!cropPath.equals("")) {
                                    rootJsonInner.put("PictureDevicePath", cropPath);
                                }else {
                                    try {
                                        rootJsonInner.put("PictureDevicePath", globalGetGratitudeListModelInner.getPictureDevicePath());
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
                                Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (!edtGratitudeName.getText().toString().equals("")) {
                            HashMap<String, Object> requestHash = new HashMap<>();
                            if (chkSetReminder.isChecked()) {
                                globalGetGratitudeListModelInner.setName(edtGratitudeName.getText().toString());
                                globalGetGratitudeListModelInner.setDescription("");
                                globalGetGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                //globalGetGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("BUCKETIMAGE",""));
                                globalGetGratitudeListModelInner.setUploadPictureImgFileName(globalGetGratitudeListModelInner.getUploadPictureImgFileName());
                                globalGetGratitudeListModelInner.setSong(songPathSelect);
                                String visiondateStr = txtCompletionDate.getText().toString();
                                Log.i("visiondateStr",visiondateStr);
                                Util.showToast(getActivity(),"visiondateStr");
                                Date visionDate = null;
                                try {
                                    visionDate = simpleFormat.parse(visiondateStr);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                globalGetGratitudeListModelInner.setCompletionDate(format.format(visionDate));
                                if(boolHiddenSaveSuccess)
                                {
                                    globalGetGratitudeListModelInner.setPushNotification(false);
                                }else {
                                    globalGetGratitudeListModelInner.setPushNotification(true);
                                }

                                if (!cropPath.equals(""))
                                {
                                    globalGetGratitudeListModelInner.setPictureDevicePath(cropPath);
                                }else {
                                    try {
                                        globalGetGratitudeListModelInner.setPictureDevicePath(globalGetGratitudeListModelInner.getPictureDevicePath());
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                requestHash.put("model", globalGetGratitudeListModelInner);
                                requestHash.put("Key", Util.KEY);
                                requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            } else {
                                BucketListModelInner getGratitudeListModelInner = new BucketListModelInner();
                                getGratitudeListModelInner.setId(globalGetGratitudeListModelInner.getId());
                                getGratitudeListModelInner.setName(edtGratitudeName.getText().toString());
                                getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                getGratitudeListModelInner.setDescription("");
                                //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("BUCKETIMAGE",""));
                                getGratitudeListModelInner.setUploadPictureImgFileName(globalGetGratitudeListModelInner.getUploadPictureImgFileName());
                                getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                getGratitudeListModelInner.setStatus(false);
                                getGratitudeListModelInner.setIsDeleted(false);
                                getGratitudeListModelInner.setSong(songPathSelect);
                                getGratitudeListModelInner.setFrequencyId(1);
                                getGratitudeListModelInner.setPushNotification(false);
                                String visiondateStr = txtCompletionDate.getText().toString();
                                Log.i("visiondateStr1",visiondateStr);
                                //  Util.showToast(getActivity(),"visiondateStr");

                               /* Date visionDate = null;
                                SimpleDateFormat simpleFormat1 = new SimpleDateFormat("MMM dd yyyy");
                                try {
                                    visionDate = simpleFormat.parse(visiondateStr);
                                    getGratitudeListModelInner.setCompletionDate(simpleFormat.format(visionDate));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }*/

                                String dateString = visiondateStr;
                                String formatPattern = "dd/MM/yyyy";

                                SimpleDateFormat dateFormat = new SimpleDateFormat(formatPattern);

                                try {
                                    Date date = dateFormat.parse(dateString);
                                    // The date object now contains the parsed date

                                    // If you need a DateTime object (from a library like Joda-Time),
                                    // you can convert the java.util.Date to a DateTime object
                                    // using appropriate conversions provided by the library.

                                    // Example using Joda-Time:
                                    // DateTime dateTime = new DateTime(date);

                                    System.out.println(date);
                                    getGratitudeListModelInner.setCompletionDate(format.format(date));

                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }





                                if (!cropPath.equals(""))
                                {
                                    getGratitudeListModelInner.setPictureDevicePath(cropPath);
                                }else {
                                    try {
                                        getGratitudeListModelInner.setPictureDevicePath(globalGetGratitudeListModelInner.getPictureDevicePath());
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
                            Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("ADD")) {
                    if (dialogOpenOnceForAdd) {

                        try {
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                try {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", null);
                                    rootJsonInner.put("Name", edtGratitudeName.getText().toString());
                                    rootJsonInner.put("Description", "");
                                    rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("reminder_till_date", null);
                                    rootJsonInner.put("last_reminder_inserted_date", "");
                                    rootJsonInner.put("Picture", "");
                                    rootJsonInner.put("UploadPictureImgFileName", null);
                                    rootJsonInner.put("UploadPictureImg", "");
                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("BUCKETIMAGE",""));
                                    rootJsonInner.put("Song", songPathSelect);
                                    rootJsonInner.put("CreatedAt", "");
                                    rootJsonInner.put("CreatedBy", Integer.parseInt(sharedPreference.read("UserID", "")));
                                    rootJsonInner.put("IsDeleted", false);
                                    rootJsonInner.put("Status", false);
                                    rootJsonInner.put("IsCreatedUpdated", null);
                                    rootJsonInner.put("PushNotification", true);
                                    String visiondateStr = txtCompletionDate.getText().toString();
                                    Date visionDate = null;
                                    try {
                                        visionDate = simpleFormat.parse(visiondateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    rootJsonInner.put("CompletionDate", format.format(visionDate));
                                    Log.e("print pic path--", cropPath + "???");
                                    if (!cropPath.equals("")) {
                                        rootJsonInner.put("PictureDevicePath", cropPath);
                                    }else {
                                        try {
                                            rootJsonInner.put("PictureDevicePath", globalGetGratitudeListModelInner.getPictureDevicePath());
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    rootJson.put("model", rootJsonInner);
                                    rootJson.put("Key", Util.KEY);
                                    rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);

                                saveAddGratitudeData(hashMap);

                            } else {
                                Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {

                        if (!edtGratitudeName.getText().toString().equals("")) {
                            BucketListModelInner getGratitudeListModelInner = new BucketListModelInner();
                            getGratitudeListModelInner.setId(0);
                            getGratitudeListModelInner.setName(edtGratitudeName.getText().toString());
                            getGratitudeListModelInner.setDescription("");
                            getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                            //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("BUCKETIMAGE",""));
                            getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                            getGratitudeListModelInner.setStatus(false);
                            getGratitudeListModelInner.setIsDeleted(false);
                            getGratitudeListModelInner.setSong(songPathSelect);
                            getGratitudeListModelInner.setFrequencyId(1);
                            String visiondateStr = txtCompletionDate.getText().toString();
                            Date visionDate = null;
                            try {
                                visionDate = simpleFormat.parse(visiondateStr);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            getGratitudeListModelInner.setCompletionDate(format.format(visionDate));
                            if (chkSetReminder.isChecked()) {
                                getGratitudeListModelInner.setPushNotification(true);
                                getGratitudeListModelInner.setFrequencyId(1);
                                getGratitudeListModelInner.setReminderOption(1);
                                getGratitudeListModelInner.setReminderAt1(12);
                            }
                            Log.e("print pic path--", cropPath + "???");
                            if (!cropPath.equals("")) {
                                getGratitudeListModelInner.setPictureDevicePath( cropPath);
                            }else {
                                try {
                                    getGratitudeListModelInner.setPictureDevicePath( globalGetGratitudeListModelInner.getPictureDevicePath());
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            HashMap<String, Object> requestHash = new HashMap<>();
                            requestHash.put("model", getGratitudeListModelInner);
                            requestHash.put("Key", Util.KEY);
                            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                            saveAddGratitudeData(requestHash);

                        } else {
                            Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                        }

                    }
                } else if (gratitudeStatus.equals("EDITCOPY")) {
                    if (!edtGratitudeName.getText().toString().equals("")) {

                        if (dialogOpenOnceForEdit) {
                            try {
                                if (!edtGratitudeName.getText().toString().equals("")) {
                                    rootJsonInner.put("Id", 0);
                                    rootJsonInner.put("DueDate", globalGetGratitudeListModelInner.getDueDate());
                                    rootJsonInner.put("Name", edtGratitudeName.getText().toString());
                                    rootJsonInner.put("Description", "");
                                    rootJsonInner.put("CategoryId", spCategory.getSelectedItemPosition() + 1);
                                    rootJsonInner.put("reminder_till_date", globalGetGratitudeListModelInner.getReminderTillDate());
                                    rootJsonInner.put("last_reminder_inserted_date", globalGetGratitudeListModelInner.getLastReminderInsertedDate());
                                    rootJsonInner.put("Picture", globalGetGratitudeListModelInner.getPicture());
                                    rootJsonInner.put("UploadPictureImgFileName", globalGetGratitudeListModelInner.getUploadPictureImgFileName());
                                    rootJsonInner.put("UploadPictureImg", globalGetGratitudeListModelInner.getUploadPictureImg());
                                    //rootJsonInner.put("UploadPictureImgBase64",sharedPreference.read("BUCKETIMAGE",""));
                                    rootJsonInner.put("Song", songPathSelect);
                                    rootJsonInner.put("CreatedAt", globalGetGratitudeListModelInner.getCreatedAt());
                                    rootJsonInner.put("CreatedBy", globalGetGratitudeListModelInner.getCreatedBy());
                                    rootJsonInner.put("IsDeleted", globalGetGratitudeListModelInner.getIsDeleted());
                                    rootJsonInner.put("Status", globalGetGratitudeListModelInner.getStatus());
                                    rootJsonInner.put("IsCreatedUpdated", globalGetGratitudeListModelInner.getIsCreatedUpdated());
                                    String visiondateStr = txtCompletionDate.getText().toString();
                                    Date visionDate = null;
                                    try {
                                        visionDate = simpleFormat.parse(visiondateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    rootJsonInner.put("CompletionDate", format.format(visionDate));
                                    Log.e("print pic path--", cropPath + "???");
                                    if (!cropPath.equals("")) {
                                        rootJson.put("PictureDevicePath", cropPath);
                                    }else {
                                        try {
                                            rootJson.put("PictureDevicePath", globalGetGratitudeListModelInner.getPictureDevicePath());
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
                                    Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            if (!edtGratitudeName.getText().toString().equals("")) {
                                HashMap<String, Object> requestHash = new HashMap<>();
                                if (chkSetReminder.isChecked()) {
                                    globalGetGratitudeListModelInner.setId(0);
                                    globalGetGratitudeListModelInner.setName(edtGratitudeName.getText().toString());
                                    globalGetGratitudeListModelInner.setDescription("");
                                    globalGetGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    globalGetGratitudeListModelInner.setSong(songPathSelect);
                                    String visiondateStr = txtCompletionDate.getText().toString();
                                    Date visionDate = null;
                                    try {
                                        visionDate = simpleFormat.parse(visiondateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    globalGetGratitudeListModelInner.setCompletionDate(format.format(visionDate));
                                    //globalGetGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("BUCKETIMAGE",""));
                                    Log.e("print pic path--", cropPath + "???");
                                    if (!cropPath.equals("")) {
                                        globalGetGratitudeListModelInner.setPictureDevicePath(cropPath);
                                    }else {
                                        try {
                                            globalGetGratitudeListModelInner.setPictureDevicePath(globalGetGratitudeListModelInner.getPictureDevicePath());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    requestHash.put("model", globalGetGratitudeListModelInner);
                                    requestHash.put("Key", Util.KEY);
                                    requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

                                } else {
                                    BucketListModelInner getGratitudeListModelInner = new BucketListModelInner();
                                    getGratitudeListModelInner.setId(0);
                                    getGratitudeListModelInner.setName(edtGratitudeName.getText().toString());
                                    getGratitudeListModelInner.setDescription("");
                                    getGratitudeListModelInner.setCategoryId(spCategory.getSelectedItemPosition() + 1);
                                    //getGratitudeListModelInner.setUploadPictureImgBase64(sharedPreference.read("BUCKETIMAGE",""));
                                    getGratitudeListModelInner.setCreatedBy(Integer.parseInt(sharedPreference.read("UserID", "")));
                                    getGratitudeListModelInner.setStatus(false);
                                    getGratitudeListModelInner.setIsDeleted(false);
                                    getGratitudeListModelInner.setSong(songPathSelect);
                                    getGratitudeListModelInner.setFrequencyId(1);
                                    String visiondateStr = txtCompletionDate.getText().toString();
                                    Date visionDate = null;
                                    try {
                                        visionDate = simpleFormat.parse(visiondateStr);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    getGratitudeListModelInner.setCompletionDate(format.format(visionDate));
                                    Log.e("print pic path--", cropPath + "???");
                                    if (!cropPath.equals("")) {
                                        getGratitudeListModelInner.setPictureDevicePath(cropPath);
                                    }else {
                                        try {
                                            getGratitudeListModelInner.setPictureDevicePath(globalGetGratitudeListModelInner.getPictureDevicePath());
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
                                Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                            }

                        }

                    } else {
                        Toast.makeText(getActivity(), "Please enter bucket name", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        //  imgEdit.performClick();

     /*   imgDeleteGratitude.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteGratitude();

            }
        });*/


        txtCompletionDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //  if (editClicked)
                {
                    SimpleDateFormat myDateFormat=new SimpleDateFormat("MM/dd/yyyy");
                    DatePickerControllerFromTextView datePickerControllerFromTextView = new DatePickerControllerFromTextView(getActivity(), txtCompletionDate, myDateFormat.format(new Date()), false);
                    changeByUser();
                }
            }
        });
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //  rlSetReminder.performClick();
                if (globalGetGratitudeListModelInner.getPushNotification() != null && globalGetGratitudeListModelInner.getPushNotification()) {
                    if(isAdded()&&getArguments()!=null&&getArguments().containsKey("NTYPE")&&getArguments().getBoolean("NTYPE"))
                        funOpenReminder();
                }

            }
        }, 4000);


        return view;
    }
    private void changeByUser_() {
        clickPhoto.setColorFilter(ContextCompat.getColor(getActivity(), R.color.body_grey), PorterDuff.Mode.MULTIPLY);
        galleryPhoto.setColorFilter(ContextCompat.getColor(getActivity(), R.color.body_grey), PorterDuff.Mode.MULTIPLY);
        chooseDate.setColorFilter(ContextCompat.getColor(getActivity(), R.color.body_grey), PorterDuff.Mode.MULTIPLY);
        Log.i("changebyuser","changeByUser");

    }
    private void changeByUser() {
        rlSaveAchievement.setImageResource(R.drawable.ic_check);
        Log.i("changebyuser","changeByUser");
        Log.i("changebyuser","changeByUser");

    }

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

    private void changeBucketStatusApi(int bucketId, boolean status, int bucketStatus) {
        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");


            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
            hashReq.put("BucketID", bucketId);
            hashReq.put("BucketStatus", bucketStatus);
            // hashReq.put("Status", status);

            Call<JsonObject> userHabitSwapsModelCall = finisherService.ChangeBucketStatusAPI(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.showToast(getActivity(), "Saved Successfully");
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketListFragment());
                            if(bucketStatus == 0)
                            {
                                boolHiddenSaveSuccess = true;
                                rlSaveAchievement.performClick();
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
            Util.showToast(getActivity(), Util.networkMsg);
        }
    }

    private void deleteGratitude() {

        if (Util.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("BucketID", globalGetGratitudeListModelInner.getId());
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.deleteBucket(hashMap);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Toast.makeText(getActivity(), "Data successfully deleted", Toast.LENGTH_SHORT).show();
                            ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
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

            Call<JsonObject> jsonObjectCall = finisherService.addUpdateBucketWithPhoto(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(reqhash)));
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, final Response<JsonObject> response) {

                    progressDialog.setProgress(100);
                    Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            if (response.body() != null) {
                                if (response.body().get("SuccessFlag").getAsBoolean()) {

                                    Log.e("Body", response.body().toString());
                                    if (uploadCallback!=null){
                                        int image_type = 2;

                                        UploadEntity entity = new UploadEntity(image_type, response.body().getAsJsonObject("Details").get("id").getAsInt(), ANNUAL_CROP_PATH, false);
                                        uploadCallback.addInQueue(entity);
                                    }
                                   // Leanplum.track("Achieve_Android_Saved a bucket list");
                                    Toast.makeText(getActivity(), "Data successfully saved", Toast.LENGTH_SHORT).show();
                                   /*commented by sahenita*/
                                    // SetLocalNotificationForBucket.SetLocalNotificationForBucket(globalGetGratitudeListModelInner, getActivity());
                                    ((MainActivity) getActivity()).refershGamePoint(getActivity());
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketListFragment());
                                    ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);


                                }
                            }
                        }
                    };
                    new Handler().postDelayed(runnable, 500);
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

    private void deleteImage() {

        if (Util.checkConnection(getActivity())) {
            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> requestHash = new HashMap<>();
            requestHash.put("model", globalGetGratitudeListModelInner);
            requestHash.put("Key", Util.KEY);
            requestHash.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> jsonObjectCall = finisherService.addUpdateBucket(requestHash);
            jsonObjectCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Toast.makeText(getActivity(), "Image successfully deleted", Toast.LENGTH_SHORT).show();
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
                    // funFirstTimeDialog();
                }

                @Override
                public void onFailure(Call<MyValueListResponse> call, Throwable t) {
                    Log.e("error", "er");
                    progressDialog.dismiss();
                    // funFirstTimeDialog();
                }
            });
        } else {
            Util.showToast(getActivity(), Util.networkMsg);
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
                try {
                    // TODO Auto-generated method stub
                    bitimage = cropImageView.getCroppedImage();
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    imgGratitudeMain.setImageBitmap(null);
                    imgGratitudeMain.setAdjustViewBounds(true);
                    imgGratitudeMain.setImageBitmap(bitimage);
                    dialogcrop.dismiss();

                    cropPath = storeImage(bitimage);

                    Log.e("print pic path--", cropPath + "??");
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
                    imgGratitudeMain.setImageBitmap(myBitmapCancel);
                    preaprePictureForUpload(imgPath);
                    Log.e("print pic path cancel--", imgPath + "??");
                    dialogcrop.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
            image.compress(Bitmap.CompressFormat.PNG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d("TAG", "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d("TAG", "Error accessing file: " + e.getMessage());
        }
        return pictureFile.getAbsolutePath();
    }

    /** Create a File for saving an image or video */
    private  File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getActivity().getApplicationContext().getPackageName()
                + "/Files");

        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                return null;
            }
        }
        // Create a media file name
        String timeStamp = new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
        File mediaFile;
        String mImageName="MI_"+ timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        return mediaFile;
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title"+ Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
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




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 200: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission Gal", "Granted");
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
                        getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                    } else {
                        Log.e("camera","23");
                        openAppSettings();
                        // Permission was denied
                        // You can disable the feature that requires the camera permission
                    }
                } else {
                    Log.e("Permission Gal", "Denied");
                }
            }
          /*  case 201: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("Permission cam", "Granted");
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    out = createFolder();
                    imgPath = out.getAbsolutePath();
                    Uri photoURI = FileProvider.getUriForFile(getActivity(), "com.ashysystem.mbhq" + ".fileprovider", out);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    getActivity().startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

                } else {
                    Log.e("Permission cam", "Denied");
                }
                return;
            }*/
            case 203: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission was granted
                    // You can now use the camera in your app
                    Log.e("camera", "20");

                    openCustomCamera();


                } else {
                    Log.e("camera", "21");
                    openAppSettings();
                    // Permission was denied
                    // You can disable the feature that requires the camera permission
                }
            }
            case 202: {
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
                    getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

                } else {
                    Log.e("camera","23");
                    openAppSettings();
                    // Permission was denied
                    // You can disable the feature that requires the camera permission
                }
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
                    ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);

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
        imgRightBack.setVisibility(View.VISIBLE);
        txtPageHeader.setVisibility(View.GONE);
        imgLeftBack.setVisibility(View.GONE);
        imgCircleBack.setVisibility(View.VISIBLE);
        imgHelp.setVisibility(View.GONE);
        imgCalender.setVisibility(View.GONE);
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));


        imgRightBack.setVisibility(View.GONE);
        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (gratitudeStatus.equals("ADD")) {
                    if (!edtGratitudeName.getText().toString().equals("") || chkSetReminder.isChecked() || !stringImg.equals("")) {
                        final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                        alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
                        alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                            @Override
                            public void onDone(String title) {
                                rlSaveAchievement.performClick();
                            }

                            @Override
                            public void onCancel(String title) {
                                ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
                            }
                        });

                    } else {
                        ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
                    }
                } else if (gratitudeStatus.equals("EDIT") && imgEdit.getVisibility() == View.GONE) {
                    final AlertDialogCustom alertDialogCustom = new AlertDialogCustom(getActivity());
                    alertDialogCustom.ShowDialog("Alert!", "You may have some unsaved data.Do you want to save?", true);
                    alertDialogCustom.setAlertAction(new AlertDialogCustom.AlertResponse() {
                        @Override
                        public void onDone(String title) {
                            rlSaveAchievement.performClick();
                        }

                        @Override
                        public void onCancel(String title) {
                            ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
                        }
                    });
                } else {
                    ((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketList", null);
                }
            }
        });
    }

    private boolean hasCameraPermission() {
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
        //add jyotirmoy
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

    private void PlayMusic(String DataStream) {
        if (!DataStream.equals("")) {
            stopMusic();
            Log.e("print path", DataStream + "?");
            songPathSelect = DataStream;
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


    private void funOpenReminder() {
        txtSetReminder.setText("View Reminder");

        CustomReminderDialogForEdit customReminderDialog = new CustomReminderDialogForEdit();
        Gson gson = new Gson();
        Bundle bundle = new Bundle();
        bundle.putString("MODEL", gson.toJson(globalGetGratitudeListModelInner));
        bundle.putString("MODELNAME", "GRATITUDE");
        customReminderDialog.setArguments(bundle);
        customReminderDialog.mListener = new CustomReminderDialogForEdit.onSubmitListener() {
            @Override
            public void setOnSubmitListener(JSONObject arg) {
                Log.e("print jj", arg.toString() + "?");
                dialogOpenOnceForEdit = true;
                rootJsonInner = arg;
                try {
                    globalGetGratitudeListModelInner.setFrequencyId(rootJsonInner.getInt("FrequencyId"));
                    globalGetGratitudeListModelInner.setMonthReminder(rootJsonInner.getInt("MonthReminder"));
                    globalGetGratitudeListModelInner.setReminderOption(rootJsonInner.getInt("ReminderOption"));
                    globalGetGratitudeListModelInner.setReminderAt1(rootJsonInner.getInt("ReminderAt1"));
                    globalGetGratitudeListModelInner.setReminderAt2(rootJsonInner.getInt("ReminderAt2"));//...
                    globalGetGratitudeListModelInner.setEmail(rootJsonInner.getBoolean("Email"));
                    globalGetGratitudeListModelInner.setPushNotification(rootJsonInner.getBoolean("PushNotification"));
                    globalGetGratitudeListModelInner.setSunday(rootJsonInner.getBoolean("Sunday"));
                    globalGetGratitudeListModelInner.setMonday(rootJsonInner.getBoolean("Monday"));
                    globalGetGratitudeListModelInner.setTuesday(rootJsonInner.getBoolean("Tuesday"));
                    globalGetGratitudeListModelInner.setWednesday(rootJsonInner.getBoolean("Wednesday"));
                    globalGetGratitudeListModelInner.setThursday(rootJsonInner.getBoolean("Thursday"));
                    globalGetGratitudeListModelInner.setFriday(rootJsonInner.getBoolean("Friday"));
                    globalGetGratitudeListModelInner.setSaturday(rootJsonInner.getBoolean("Saturday"));
                    globalGetGratitudeListModelInner.setJanuary(rootJsonInner.getBoolean("January"));
                    globalGetGratitudeListModelInner.setFebruary(rootJsonInner.getBoolean("February"));
                    globalGetGratitudeListModelInner.setMarch(rootJsonInner.getBoolean("March"));
                    globalGetGratitudeListModelInner.setApril(rootJsonInner.getBoolean("April"));
                    globalGetGratitudeListModelInner.setMay(rootJsonInner.getBoolean("May"));
                    globalGetGratitudeListModelInner.setJune(rootJsonInner.getBoolean("June"));
                    globalGetGratitudeListModelInner.setAugust(rootJsonInner.getBoolean("August"));
                    globalGetGratitudeListModelInner.setSeptember(rootJsonInner.getBoolean("September"));
                    globalGetGratitudeListModelInner.setOctober(rootJsonInner.getBoolean("October"));
                    globalGetGratitudeListModelInner.setNovember(rootJsonInner.getBoolean("November"));
                    globalGetGratitudeListModelInner.setDecember(rootJsonInner.getBoolean("December"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ////////////////////


            }
        };
        customReminderDialog.show(getFragmentManager(), "");

    }


    @Override
    public void onAttach(Context context) {
        if (context instanceof UploadCallback){
            uploadCallback = (UploadCallback)context;
        }else {
            Log.e(TAG, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Classcast exception");
        }
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        if (uploadCallback!=null){
            uploadCallback = null;
        }
        super.onDetach();
    }

    public static final String TAG = BucketAddEditFragment.class.getSimpleName();
    private UploadCallback uploadCallback;
    public void uploadImageToServer(File file){
        mFile=file;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("PICTURE FRAGMENT", "trueeeeeeeee");
        if (resultCode != 0) {
            if (requestCode == REQUEST_CODE_SONG) {
                String songPath = data.getStringExtra("song");
                Log.e("song path-->", songPath + "?");
                if (!songPath.equals(""))
                    PlayMusic(songPath);
            }else if(requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
                if (photoFile != null) {
                    Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                    cropPhoto1(imageBitmap,photoFile.getAbsolutePath());
                }

            }

            /********works for camera*********/
            else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                //Uri selectedImageUri = intent.getData();
                Uri selectedImageUri = data.getParcelableExtra("CAMERA_URI");
                setImageToCropper(selectedImageUri);
            }

            else if (requestCode == ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST && resultCode == getActivity().RESULT_OK
                    && null != data) {
                Log.e("camera", "04");
                // Get the Image from data
                Log.e("PICTURE Gal---->", "123");

                Uri selectedImage = data.getData();
                setImageToCropper(selectedImage);
            }
            /********works for camera*********/
            else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                String imagePath = data.getStringExtra("image_path");
                try {


                    imgGratitudeMain.setVisibility(View.VISIBLE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        imgGratitudeMain.setBackgroundResource(0);
                    }
                    imgGratitudeMain.setImageBitmap(BitmapFactory.decodeFile(imagePath));

                    if (gratitudeStatus.equals("EDIT")) {
                        //sendPicInfoToserver();
                    }
                    Log.e("ANNUAL_CROP_PATH", imagePath + ">>>>>>");

                    ANNUAL_CROP_PATH = imagePath;

                    /** Edited by Amit dated on 5/03/2020 **/

                    String[] split_path = imagePath.split("/");
                    ANNUAL_CROP_PATH = split_path[split_path.length - 1];

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("CROPPPP", "preaprePictureForUpload: " + e.getMessage());
                }
                uploadImageToServer(new File(imagePath));
                // preaprePictureForUpload(imagePath);
            }
            else {
                /*if (requestCode == RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
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

                }
                else if (requestCode == ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST && resultCode == getActivity().RESULT_OK
                        && null != data) {
                    Log.e("camera", "04");
                    // Get the Image from data
                    Log.e("PICTURE Gal---->", "123");

                    Uri selectedImage = data.getData();
                    setImageToCropper(selectedImage);
                }
               else {
                    Toast.makeText(getActivity(), "You haven't picked Image",
                            Toast.LENGTH_LONG).show();
                }*/


            }


        } else {
            Toast.makeText(getActivity(), "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void setImageToCropper(Uri selectedImageUri) {
        try {
            if (selectedImageUri != null) {
                Intent intent = new Intent(getActivity(), ImageCropperActivity.class);
                intent.putExtra("crop_ratio", 43);
                intent.putExtra("image_uri", true);
                intent.setData(selectedImageUri);
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE);
            } else
                Toast.makeText(getActivity(), getString(R.string.failed_to_upload), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity(), getString(R.string.failed_to_upload), Toast.LENGTH_SHORT).show();
        }
    }


    private void openCam(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                photoFile = createImageFile();
                // Continue only if the File was successfully created
                Uri photoURI = FileProvider.getUriForFile(
                        getActivity(),
                        "com.ashysystem.mbhq.fileprovider",
                        photoFile
                );
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_PIC_REQUEST);
            } catch (Exception ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName, // prefix
                ".jpg", // suffix
                storageDir // directory
        );

        // Save a file path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
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
                // imgJournalLoadingBar.setVisibility(View.VISIBLE);

                try {




                    // TODO Auto-generated method stub
                    // edtGratitudeName.setMaxHeight(800);//////////////////////////////
                    imgGratitudeMain.setVisibility(View.VISIBLE);
                    // imgGratitudeMainCard.setVisibility(View.VISIBLE);
                    //fram_.setVisibility(View.VISIBLE);
                    bitimage = cropImageView.getCroppedImage();
                    Log.e(" height After crop--->", bitimage.getHeight() + "");
                    Log.e(" height After crop--->", bitimage.getHeight() + "");

                    dialogcrop.dismiss();

                    Uri uri = getImageUri(requireActivity(),bitimage);
                    String cropPath = Util.getFilePathFromUri(requireActivity(),uri);
                    preaprePictureForUpload1(cropPath);
                    // imgJournalLoadingBar.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    // imgJournalLoadingBar.setVisibility(View.GONE);
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
    private void preaprePictureForUpload1(String cropPath) {
        try {
            File file = new File(cropPath);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            stringImg = encodeImage(imageData);
            Log.e("BASE64STRING", stringImg);
            //sharedPreference.write("BUCKETIMAGE","",stringImg);
            mFile = file;


        } catch (IOException e) {
            e.printStackTrace();
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
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

    }
    private void preaprePictureForUpload(String cropPath) {
        try {
            File file = new File(cropPath);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            stringImg = encodeImage(imageData);
            Log.e("BASE64STRING", stringImg);
            //sharedPreference.write("BUCKETIMAGE","",stringImg);
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

        ANNUAL_CROP_PATH = cropPath;

        /** Edited by Amit dated on 5/03/2020 **/

        String[] split_path = cropPath.split("/");
        ANNUAL_CROP_PATH = split_path[split_path.length-1];

    }
}