package com.ashysystem.mbhq.fragment.bucket;

import static android.app.Activity.RESULT_OK;
import static com.ashysystem.mbhq.activity.MainActivity.decodeSampledBitmapFromFile;
import static com.ashysystem.mbhq.activity.MainActivity.encodeImage;

import android.Manifest;

import com.ashysystem.mbhq.activity.CameraNewActivity;
import com.ashysystem.mbhq.activity.ImageCropperActivity;
import com.ashysystem.mbhq.adapter.BucketListAdapter;
import com.ashysystem.mbhq.fragment.AchieveHomeFragment;
import com.ashysystem.mbhq.model.BucketListModel;
import com.ashysystem.mbhq.model.BucketListModelInner;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.activity.MainActivity;
import com.ashysystem.mbhq.model.IndividualBucketListModel;
import com.ashysystem.mbhq.util.AlertDialogCustom;
import com.ashysystem.mbhq.util.Connection;
import com.ashysystem.mbhq.util.DatePickerForGratitude;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;
import com.ashysystem.mbhq.view.drag_drop.ItemMoveCallbackBucket;
import com.ashysystem.mbhq.view.drag_drop.StartDragListener;
import com.edmodo.cropper.CropImageView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import id.zelory.compressor.Compressor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class BucketListFragment extends Fragment implements StartDragListener {
    private final int REQUEST_IMAGE_CAPTURE = 304;
    ImageView imgFiler;
    TextView txtGoalValue1, txtGoalValue2, txtGoalValue3;
    RecyclerView recyclerGratitudes;
    ImageView imgQuestionMark;
    VideoView video_view;
    LinearLayout llAddNew;
    FinisherServiceImpl finisherService;
    SharedPreference sharedPreference;
    ProgressDialog progressDialog;
    boolean videoBarOpenStatus = false;
    int numberOfVideos = 1;
    String[] arrVideos = {"https://player.vimeo.com/external/144428216.m3u8?s=43e9888e6d53f400a149335e79716b07d9edeb90", "https://player.vimeo.com/external/144428217.m3u8?s=4afc9d0ad8b161ae587eb3d67c48d3661f16ebd9", "https://player.vimeo.com/external/144428219.m3u8?s=46f60cd53e3d6df0d64b5277bf7f290d10f0c2f3"};
    private Dialog globalDialog;
    private File mFile;
    ImageView imgInfo;
    private boolean refreshApi = false;
    int filterSelectedvalue = 0;
    ImageView imgBackgroundPicTOP;
    private Bitmap onLineBitMap = null;

    ArrayList<Integer> arrSelectedValue = new ArrayList<>();
    boolean boolDateAdded = false, boolManual = false, boolActive = false, boolHidden = false, boolCompleted = false;
    private List<BucketListModelInner> globalData;
    private List<BucketListModelInner> lstManualDrag;
    ItemTouchHelper touchHelper;
    private boolean showManual = false;
    private RelativeLayout rlManualTick;
    private ImageView imgManualTick;
    View view = null;
    SwipeRefreshLayout swipeLayout;
    private boolean refresh = false;
    File out;

    ImageView imgBigPlus;
    ScrollView scrollMain;
    private String imgDecodableString = "";
    CardView cardViewBackgroundPicTOP;

    Integer timelineValue = -1;
    File photoFile = null;
    String mCurrentPhotoPath = "";
    private static int CAMERA_PIC_REQUEST = 1437;
    private CropImageView cropImageView;
    private Bitmap bitimage;
    String stringImg = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public BucketListFragment setLstManualDrag(List<BucketListModelInner> lstManualDrag) {
        this.lstManualDrag = lstManualDrag;
        return this;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_bucketlist, null);
            Log.e("load bucketlis","");
            ((MainActivity) getActivity()).funTabBarforHabits();

            funToolBar();
            imgFiler = view.findViewById(R.id.imgFiler);
            imgInfo = view.findViewById(R.id.imgInfo);
            swipeLayout = view.findViewById(R.id.swipeLayout);
            txtGoalValue1 = (TextView) view.findViewById(R.id.txtGoalValue1);
            txtGoalValue2 = (TextView) view.findViewById(R.id.txtGoalValue2);
            txtGoalValue3 = (TextView) view.findViewById(R.id.txtGoalValue3);
            recyclerGratitudes = (RecyclerView) view.findViewById(R.id.recyclerGratitudes);
            imgQuestionMark = (ImageView) view.findViewById(R.id.imgQuestionMark);
            video_view = (VideoView) view.findViewById(R.id.video_view);
            llAddNew = (LinearLayout) view.findViewById(R.id.llAddNew);
            rlManualTick = view.findViewById(R.id.rlManualTick);
            imgManualTick = view.findViewById(R.id.imgManualTick);
            imgBigPlus = view.findViewById(R.id.imgBigPlus);
            scrollMain = view.findViewById(R.id.scrollMain);

            finisherService = new FinisherServiceImpl(getActivity());
            sharedPreference = new SharedPreference(getActivity());

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            recyclerGratitudes.setLayoutManager(llm);

            if (getArguments() != null && getArguments().containsKey("NOTIFICATIONID")) {
                getIndividualBucket(getArguments().getInt("NOTIFICATIONID"), true);
            } else {
                //getValueListFromApi();
                getBucketListFromApi();
            }

            //FOR SHOWING BOTTOM TOOLBAR IF NET IS ON
            if (Util.checkConnection(getActivity())) {
                ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.VISIBLE);
                ((MainActivity) getActivity()).rlDownloadedMeditations.setVisibility(View.GONE);
            } else {
                ((MainActivity) getActivity()).llBottomMenu.setVisibility(View.GONE);
                ((MainActivity) getActivity()).rlDownloadedMeditations.setVisibility(View.VISIBLE);
            }

            rlManualTick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHabitSwapManualOrderApiCall();
                }
            });
            imgFiler.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openDialogForFilter();
                }
            });

            /*commented by sahenita*/
/*
            imgQuestionMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (!isTablet(getActivity())) {
                        if (videoBarOpenStatus) {
                            numberOfVideos = 1;
                            videoBarOpenStatus = false;
                            video_view.setVisibility(View.GONE);
                            video_view.stopPlayback();

                        } else {
                            numberOfVideos = 1;
                            videoBarOpenStatus = true;
                            video_view.setVisibility(View.VISIBLE);
                            playVideo(video_view, arrVideos[0]);
                        }
                    } else {
                        final Dialog dialog = new Dialog(getActivity());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.dialog_choosevideo);
                        RelativeLayout rlPart1 = (RelativeLayout) dialog.findViewById(R.id.rlPart1);
                        RelativeLayout rlPart2 = (RelativeLayout) dialog.findViewById(R.id.rlPart2);
                        RelativeLayout rlPart3 = (RelativeLayout) dialog.findViewById(R.id.rlPart3);
                        RelativeLayout rlPart4 = (RelativeLayout) dialog.findViewById(R.id.rlPart4);
                        RelativeLayout rlPart5 = (RelativeLayout) dialog.findViewById(R.id.rlPart5);

                        rlPart4.setVisibility(View.GONE);
                        rlPart5.setVisibility(View.GONE);

                        rlPart1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                                JSONArray arrJson = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("name", "HLS");
                                    JSONArray innerJsonArray = new JSONArray();
                                    JSONObject innerJsonObj = new JSONObject();
                                    innerJsonObj.put("name", "VIDEO");
                                    innerJsonObj.put("uri", arrVideos[0]);
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
                                        Log.e("Exception", "File write failed: " + e.toString());
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                        rlPart2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                                JSONArray arrJson = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("name", "HLS");
                                    JSONArray innerJsonArray = new JSONArray();
                                    JSONObject innerJsonObj = new JSONObject();
                                    innerJsonObj.put("name", "VIDEO");
                                    innerJsonObj.put("uri", arrVideos[1]);
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
                                        Log.e("Exception", "File write failed: " + e.toString());
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        rlPart3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();

                                JSONArray arrJson = new JSONArray();
                                JSONObject jsonObject = new JSONObject();
                                try {
                                    jsonObject.put("name", "HLS");
                                    JSONArray innerJsonArray = new JSONArray();
                                    JSONObject innerJsonObj = new JSONObject();
                                    innerJsonObj.put("name", "VIDEO");
                                    innerJsonObj.put("uri", arrVideos[2]);
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
                                        Log.e("Exception", "File write failed: " + e.toString());
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });

                        dialog.show();
                    }
                }
            });
*/

            llAddNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                /*Bundle bundle = new Bundle();
                bundle.putString("GRATITUDESTATUS", "ADD");
                ((MainActivity) getActivity()).loadFragment(new BucketAddEditFragment(), "BucketAddEdit", bundle);*/

                    openAddDialog();

                }
            });

            imgInfo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    openInfoDialog();
                }
            });

            swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    //webCommunity.loadUrl(new SharedPreference(getActivity()).read("COMMUNITY_URL",""));
                    refresh = true;
                    //getValueListFromApi();
                    getBucketListFromApi();
                    swipeLayout.setRefreshing(false);
                }
            });

            imgBigPlus.setOnClickListener(view1 -> {

                openAddDialog();

            });

            return view;

        } else {

            funToolBar();
            return view;

        }


    }

    private void openAddDialog() {
        globalDialog = new Dialog(getActivity(), R.style.DialogTheme);
        globalDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        globalDialog.getWindow().getDecorView().setBackgroundResource(android.R.color.white);
        globalDialog.setContentView(R.layout.layout_vision_statement_dialog);
        RelativeLayout rlHeader = globalDialog.findViewById(R.id.rlHeader);
        RelativeLayout rlSave = globalDialog.findViewById(R.id.rlSave);
        EditText edtNote = globalDialog.findViewById(R.id.edtNote);
        RelativeLayout rlShare=globalDialog.findViewById(R.id.rlShare);
        imgBackgroundPicTOP = globalDialog.findViewById(R.id.imgBackgroundPic);
        TextView addPicButton = globalDialog.findViewById(R.id.addPicButton);
        cardViewBackgroundPicTOP = globalDialog.findViewById(R.id.cardViewBackgroundPic);


        rlShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //gratitudeentry with add pic share option
                if (edtNote.getText().toString().trim().equals("")) {

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

                } else{


                    pickImageFromCamrea_(addPicButton);


                    if (imgBackgroundPicTOP.getResources() != null) {
                        addPicButton.setText("CHANGE PIC");
                    }
                }

            }
        });




        globalDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.e("Dialog dismiss", "123");
                if (refreshApi) {

                }


            }
        });

        rlHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                globalDialog.dismiss();
            }
        });
        rlSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!edtNote.getText().toString().equals("")) {
                        JSONObject rootJson = new JSONObject();
                        try {
                            JSONObject rootJsonInner = new JSONObject();
                            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                            ///////////////////////
                            rootJsonInner.put("CategoryId", 1);
                            Calendar cal = Calendar.getInstance();
                            Date today = cal.getTime();
                            cal.add(Calendar.YEAR, 1); // to get previous year add -1
                            Date nextYear = cal.getTime();
                            rootJsonInner.put("CompletionDate", format.format(nextYear));
                            rootJsonInner.put("Name", edtNote.getText().toString());
                            rootJsonInner.put("CreatedBy", Integer.parseInt(sharedPreference.read("UserID", "")));
                            rootJsonInner.put("Description", "");
                            rootJsonInner.put("FrequencyId", 1);
                            rootJsonInner.put("id", 0);
                            rootJsonInner.put("IsDeleted", false);
                            rootJsonInner.put("Status", false);
                            rootJsonInner.put("Song", "");


                            rootJson.put("model", rootJsonInner);
                            rootJson.put("Key", Util.KEY);
                            rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                            refreshApi = true;

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

            }
        });

        globalDialog.show();

    }
    /*commented by sahenita (temporary)*/

    private boolean hasCameraPermission() {
//        int hasPermissionWrite = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
//        int hasPermissionCamera = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
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


    /*commented by sahenita (temporary)*/

    private boolean hasGalleryPermission() {
//        int hasPermissionWrite = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
//        int hasPermissionRead = android.support.v4.content.ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
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
        /*commented by sahenita (temporary)*/

        rlCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dlg.dismiss();

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    if (hasCameraPermission() && hasGalleryPermission()) {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(requireActivity().getPackageManager()) != null) {
                            openCustomCamera();

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
                    openCustomCamera();
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
        /*commented by sahenita (temporary)*/

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
    private void openCustomCamera() {
        Intent intent = new Intent(getActivity(), CameraNewActivity.class);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
    /*commented by sahenita (temporary)*/
/*
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
*/
    /*commented by sahenita (temporary)*/
/*
    private void openCam(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
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
*/


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


                                    Toast.makeText(getActivity(), "Data successfully saved", Toast.LENGTH_SHORT).show();
                                    globalDialog.dismiss();
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketListFragment());
                                    ((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketAddEditFragment());
                                    JsonObject obj = response.body().getAsJsonObject("Details");
                                  /*  if (obj.has("id")) {
                                        getIndividualBucket(obj.get("id").getAsInt(), false);
                                    }*/
                                    Log.e("print id gen--", obj.get("id").getAsInt() + "??");

                                    ((MainActivity)getActivity()).setOnapiResponse(new MainActivity.OnApiResponseListener() {
                                        @Override
                                        public void apiFinish() {
                                            ((MainActivity)getActivity()).loadFragment(new BucketListFragment(),"BucketList",null);
                                        }
                                    });
                                    ((MainActivity)getActivity()).setOnDialogCloseListener(new MainActivity.OnDialogCloseListener() {
                                        @Override
                                        public void onClose() {
                                            ((MainActivity)getActivity()).loadFragment(new BucketListFragment(),"BucketList",null);
                                        }
                                    });

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



    private void getBucketListFromApi() {

        if (isAdded() && Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashReq = new HashMap<>();
            hashReq.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
            hashReq.put("Key", Util.KEY);
            hashReq.put("OrderBy", 1);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<BucketListModel> getGratitudeListModelCall = finisherService.getBucketList(hashReq);
            getGratitudeListModelCall.enqueue(new Callback<BucketListModel>() {
                @Override
                public void onResponse(Call<BucketListModel> call, Response<BucketListModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().getDetails() != null && response.body().getDetails().size() > 0) {
                            imgBigPlus.setVisibility(View.GONE);
                            scrollMain.setVisibility(View.VISIBLE);
                            globalData = response.body().getDetails();

                            if (refresh) {
                                //funRefresh();
                                filterData();
                            } else {

                                try {
                                    arrSelectedValue.clear();
                                    boolActive = sharedPreference.readBoolean("BUCKET_LIST_STATUS_ACTIVE", "");
                                    if (boolActive) {
                                        addArray(3);
                                    }
                                    boolHidden = sharedPreference.readBoolean("BUCKET_LIST_STATUS_HIDDEN", "");
                                    if (boolHidden) {
                                        addArray(4);
                                    }
                                    boolCompleted = sharedPreference.readBoolean("BUCKET_LIST_STATUS_COMPLETED", "");
                                    if (boolCompleted) {
                                        addArray(5);
                                    }
                                    boolDateAdded = sharedPreference.readBoolean("BUCKET_LIST_SORT_DUE_DATE", "");
                                    boolManual = sharedPreference.readBoolean("BUCKET_LIST_SORT_MANUAL", "");
                                    if (boolDateAdded || boolManual) {
                                        if (boolDateAdded) {
                                            addArray(1);
                                        } else if (boolManual) {
                                            addArray(2);
                                            blink();
                                            showManual = true;
                                            boolManual = true;
                                        }
                                    }
                                    String timelineString = sharedPreference.read("BUCKET_LIST_TIMELINE", "");
                                    if (!timelineString.isEmpty()) {
                                        timelineValue = Integer.parseInt(timelineString);
                                    }
                                    filterData();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    filterData();
                                }
                                funFirstTimeDialog();
                            }


                        } else {
                            funFirstTimeDialog();
                            imgBigPlus.setVisibility(View.VISIBLE);
                            scrollMain.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<BucketListModel> call, Throwable t) {
                    progressDialog.dismiss();
                    funFirstTimeDialog();

                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }
/*commented by sahenita*/
/*
    private void funRefresh() {


        for (int x = 0; x < arrSelectedValue.size(); x++) {
            int filterSelectedvalue = arrSelectedValue.get(x);
            if (filterSelectedvalue == 1) {

                //   imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);


                List<BucketListModelInner> bkpData = new ArrayList<>();
                bkpData.addAll(globalData);
                filterdatabyDate(bkpData);
                rlManualTick.setVisibility(View.GONE);
                showManual = false;
                if (bkpData.size() > 0) {
                    Log.e("print filter size--", bkpData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(bkpData);
                } else {
                    recyclerGratitudes.setAdapter(null);
                }
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);

            }
            if (filterSelectedvalue == 2) {

                //   imgManualCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);


                recyclerGratitudes.setAdapter(null);
                showManual = true;

                blink();
                if (globalData.size() > 0) {
                    Log.e("print filter size--", globalData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(globalData);
                } else {
                    recyclerGratitudes.setAdapter(null);
                }
                // loadAdapter(filterData);
            }
            if (filterSelectedvalue == 3) {


                //////////////
                List<BucketListModelInner> filterData = new ArrayList<>();
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);
                for (int y = 0; y < arrSelectedValue.size(); y++) {
                    if (arrSelectedValue.get(y).equals(1)) {
                        List<BucketListModelInner> bkpData = new ArrayList<>();
                        bkpData.addAll(globalData);
                        filterdatabyDate(bkpData);
                        filterData = bkpData;
                        showManual = false;
                    } else if (arrSelectedValue.get(y).equals(2)) {
                        recyclerGratitudes.setAdapter(null);
                        showManual = true;
                        filterData = globalData;
                        blink();
                        // loadAdapter(filterData);
                    } else {
                        showManual = false;
                        rlManualTick.setVisibility(View.GONE);
                        int machto = -1;
                        if (arrSelectedValue.get(y).equals(3))///Active
                            machto = 1;
                        if (arrSelectedValue.get(y).equals(4))//Hidden
                            machto = 0;
                        if (arrSelectedValue.get(y).equals(5))//Completed
                            machto = 2;
                        for (int p = 0; p < globalData.size(); p++) {
                            if (machto == globalData.get(p).getBucketStatus()) {
                                filterData.add(globalData.get(p));
                            }
                        }
                        machto = -1;
                    }


                }
                if (filterData.size() > 0) {
                    Log.e("print filter size--", filterData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(filterData);
                } else {
                    Util.showToast(getActivity(), "No data found");
                    recyclerGratitudes.setAdapter(null);
                }

            }
            if (filterSelectedvalue == 4) {

                // imgHiddenCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);

                //////////////
                List<BucketListModelInner> filterData = new ArrayList<>();
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);
                for (int y = 0; y < arrSelectedValue.size(); y++) {
                    if (arrSelectedValue.get(y).equals(1)) {
                        List<BucketListModelInner> bkpData = new ArrayList<>();
                        bkpData.addAll(globalData);
                        filterdatabyDate(bkpData);
                        filterData = bkpData;
                        showManual = false;
                    } else if (arrSelectedValue.get(y).equals(2)) {
                        recyclerGratitudes.setAdapter(null);
                        showManual = true;
                        filterData = globalData;
                        blink();
                        // loadAdapter(filterData);
                    } else {
                        showManual = false;
                        rlManualTick.setVisibility(View.GONE);
                        int machto = -1;
                        if (arrSelectedValue.get(y).equals(3))///Active
                            machto = 1;
                        if (arrSelectedValue.get(y).equals(4))//Hidden
                            machto = 0;
                        if (arrSelectedValue.get(y).equals(5))//Completed
                            machto = 2;
                        for (int p = 0; p < globalData.size(); p++) {
                            if (machto == globalData.get(p).getBucketStatus()) {
                                filterData.add(globalData.get(p));
                            }
                        }
                        machto = -1;
                    }


                }
                if (filterData.size() > 0) {
                    Log.e("print filter size--", filterData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(filterData);
                } else {
                    Util.showToast(getActivity(), "No data found");
                    recyclerGratitudes.setAdapter(null);
                }

            }
            if (filterSelectedvalue == 5) {

                // imgCompletedChk.setBackgroundResource(R.drawable.mbhq_ic_tick);

                ////////////
                List<BucketListModelInner> filterData = new ArrayList<>();
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);
                for (int y = 0; y < arrSelectedValue.size(); y++) {
                    if (arrSelectedValue.get(y).equals(1)) {
                        List<BucketListModelInner> bkpData = new ArrayList<>();
                        bkpData.addAll(globalData);
                        filterdatabyDate(bkpData);
                        filterData = bkpData;
                        showManual = false;
                    } else if (arrSelectedValue.get(y).equals(2)) {
                        recyclerGratitudes.setAdapter(null);
                        showManual = true;
                        filterData = globalData;
                        blink();
                        // loadAdapter(filterData);
                    } else {
                        showManual = false;
                        rlManualTick.setVisibility(View.GONE);
                        int machto = -1;
                        if (arrSelectedValue.get(y).equals(3))///Active
                            machto = 1;
                        if (arrSelectedValue.get(y).equals(4))//Hidden
                            machto = 0;
                        if (arrSelectedValue.get(y).equals(5))//Completed
                            machto = 2;
                        for (int p = 0; p < globalData.size(); p++) {
                            if (machto == globalData.get(p).getBucketStatus()) {
                                filterData.add(globalData.get(p));
                            }
                        }
                        machto = -1;
                    }


                }
                if (filterData.size() > 0) {
                    Log.e("print filter size--", filterData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(filterData);
                } else {
                    Util.showToast(getActivity(), "No data found");
                    recyclerGratitudes.setAdapter(null);
                }
            }

        }


    }
*/




    @Override
    public void onPause() {
        super.onPause();
        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(null);
    }

    @Override
    public void onResume() {
        super.onResume();

        LinearLayout llTabView = (LinearLayout) getActivity().findViewById(R.id.llTabView);
        llTabView.setVisibility(View.VISIBLE);
        ((MainActivity) getActivity()).funTabBarforHabits();
       // ((MainActivity) getActivity()).funDrawer();

        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {

                    // handle back button
                    ((MainActivity) getActivity()).loadFragment(new AchieveHomeFragment(), "AchieveHome", null);

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


        imgRightBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).loadFragment(new AchieveHomeFragment(), "AchieveHome", null);
            }
        });
    }

    private void loadAdapter(List<BucketListModelInner> details) {

        recyclerGratitudes.setAdapter(null);
        ///////////////////////
/*commented by sahenita*/
       /* for (int i = 0; i < details.size(); i++) {
            SetLocalNotificationForBucket.SetLocalNotificationForBucket(details.get(i), getActivity());
        }*/

        BucketListAdapter gratitudeMyListAdapter = new BucketListAdapter(getActivity(), details, this, showManual, BucketListFragment.this);

        ItemTouchHelper.Callback callback = new ItemMoveCallbackBucket(gratitudeMyListAdapter);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerGratitudes);
        recyclerGratitudes.setAdapter(gratitudeMyListAdapter);


    }

    private void playVideo(final VideoView vidExercise, String fileName) {
        vidExercise.setVideoURI(Uri.parse(fileName));
        vidExercise.setMediaController(new MediaController(getActivity()));
        vidExercise.requestFocus();
        vidExercise.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

                vidExercise.start();
            }
        });
        vidExercise.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                numberOfVideos++;

                if (numberOfVideos <= 3) {
                    if (numberOfVideos == 2) {
                        vidExercise.setVideoURI(null);
                        vidExercise.setMediaController(null);
                        playVideo(vidExercise, arrVideos[1]);
                    } else if (numberOfVideos == 3) {
                        vidExercise.setVideoURI(null);
                        vidExercise.setMediaController(null);
                        playVideo(vidExercise, arrVideos[2]);
                    }

                }

                //vidExercise.resume();
            }
        });
        vidExercise.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Toast.makeText(getActivity(), "Video cannot be played", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }


    @Override
    public void requestDrag(RecyclerView.ViewHolder viewHolder) {
        touchHelper.startDrag(viewHolder);

    }

/*commented by sahenita*/
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
                    //Log.e(TAG, "Error loading sample list: " + uri, e);
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
/*commented by sahenita*/
/*
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
*/
/*commented by sahenita*/
/*
    private static final class SampleGroup {

        public final String title;
        public final List<Sample> samples;

        public SampleGroup(String title) {
            this.title = title;
            this.samples = new ArrayList<>();
        }

    }
*/
/*commented by sahenita*/
/*
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

    }
*/
/*commented by sahenita*/
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
/*commented by sahenita*/
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
/*commented by sahenita*/
/*
    private void onSampleGroups(final List<SampleGroup> groups, boolean sawError) {
        if (sawError) {
            Toast.makeText(getActivity(), R.string.sample_list_load_error, Toast.LENGTH_LONG)
                    .show();
        }
        onSampleSelected(groups.get(0).samples.get(0));
    }
*/
/*commented by sahenita*/
/*
    private void onSampleSelected(Sample sample) {
        startActivity(sample.buildIntent(getActivity()));
    }
*/


    public void getIndividualBucket(Integer ID, boolean b) {

        if (Util.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("bucketId", ID);
            hashMap.put("Key", Util.KEY);
            hashMap.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<IndividualBucketListModel> achievementModelCall = finisherService.getIndividualBucket(hashMap);
            achievementModelCall.enqueue(new Callback<IndividualBucketListModel>() {
                @Override
                public void onResponse(Call<IndividualBucketListModel> call, Response<IndividualBucketListModel> response) {
                    progressDialog.dismiss();

                    if (response.body() != null) {
                        if (response.body().getSuccessFlag()) {
                            Gson gson = new Gson();
                            Bundle bundle = new Bundle();
                            bundle.putBoolean("NTYPE", b);
                            bundle.putString("GRATITUDESTATUS", "EDIT");
                            bundle.putString("GRATITUDEDATA", gson.toJson(response.body().getDetails()));
                            if (getArguments() != null && getArguments().containsKey("NOTIFICATIONID"))
                                bundle.putBoolean("NOTIFICATION", true);
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketAddEditFragment());
                            ((MainActivity) getActivity()).loadFragment(new BucketAddEditFragment(), "BucketAddEdit", bundle);
                        }
                    }

                }

                @Override
                public void onFailure(Call<IndividualBucketListModel> call, Throwable t) {
                    progressDialog.dismiss();
                }
            });

        } else {
            Util.showToast(getActivity(), Util.networkMsg);
        }

    }

    private void funFirstTimeDialog() {
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        if (sharedPreference.read("bucketFirstTime", "").equals("")) {
            if (isAdded())
                openInfoDialog();
        }

    }

    private void openDialogForFilter() {

        final Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.setContentView(R.layout.dialog_bucketlist_filter);

        ImageView imgClose = dialog.findViewById(R.id.imgClose);
        ImageView imgExpandClose = dialog.findViewById(R.id.imgExpandClose);
        //////////////////////////
        RelativeLayout rlDateAdded = dialog.findViewById(R.id.rlDateAdded);
        RelativeLayout rlManual = dialog.findViewById(R.id.rlManual);
        RelativeLayout rlActive = dialog.findViewById(R.id.rlActive);
        RelativeLayout rlHidden = dialog.findViewById(R.id.rlHidden);
        RelativeLayout rlCompleted = dialog.findViewById(R.id.rlCompleted);
        RelativeLayout rlWithIn1 = dialog.findViewById(R.id.rlWithIn1);
        RelativeLayout rlWithIn5 = dialog.findViewById(R.id.rlWithIn5);
        RelativeLayout rlAll = dialog.findViewById(R.id.rlAll);
        //////////////
        ImageView imgDateAddedCheck = dialog.findViewById(R.id.imgDateAddedCheck);
        ImageView imgManualCheck = dialog.findViewById(R.id.imgManualCheck);
        ImageView imgActiveCheck = dialog.findViewById(R.id.imgActiveCheck);
        ImageView imgWithIn1 = dialog.findViewById(R.id.imgWithIn1);
        ImageView imgWithIn5 = dialog.findViewById(R.id.imgWithIn5);
        ImageView imgAll = dialog.findViewById(R.id.imgAll);

        ImageView imgHiddenCheck = dialog.findViewById(R.id.imgHiddenCheck);
        ImageView imgCompletedChk = dialog.findViewById(R.id.imgCompletedChk);
        //////////////
        LinearLayout llCheckBoxOptions = dialog.findViewById(R.id.llCheckBoxOptions);
        RelativeLayout rlShowResults = dialog.findViewById(R.id.rlShowResults);
        RelativeLayout rlClearAll = dialog.findViewById(R.id.rlClearAll);
        RelativeLayout rlTransparent = dialog.findViewById(R.id.rlTransparent);
        TextView txtFromDate = dialog.findViewById(R.id.txtFromDate);
        TextView txtToDate = dialog.findViewById(R.id.txtToDate);
        LinearLayout llSecetDateWhole = dialog.findViewById(R.id.llSecetDateWhole);

        for (int x = 0; x < arrSelectedValue.size(); x++) {
            int filterSelectedvalue = arrSelectedValue.get(x);
            if (filterSelectedvalue == 1) {

                imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            }
            if (filterSelectedvalue == 2) {

                imgManualCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            }
            if (filterSelectedvalue == 3) {

                imgActiveCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            }
            if (filterSelectedvalue == 4) {

                imgHiddenCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
            }
            if (filterSelectedvalue == 5) {

                imgCompletedChk.setBackgroundResource(R.drawable.mbhq_ic_tick);
            }

        }

        if (timelineValue == 0) {
            imgAll.setBackgroundResource(R.drawable.mbhq_ic_tick);
            imgWithIn1.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgWithIn5.setBackgroundResource(R.drawable.mbhq_ic_untick);
        } else if (timelineValue == 1) {
            imgAll.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgWithIn1.setBackgroundResource(R.drawable.mbhq_ic_tick);
            imgWithIn5.setBackgroundResource(R.drawable.mbhq_ic_untick);
        } else if (timelineValue == 5) {
            imgAll.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgWithIn1.setBackgroundResource(R.drawable.mbhq_ic_untick);
            imgWithIn5.setBackgroundResource(R.drawable.mbhq_ic_tick);
        }


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

      /*  rlShowAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                filterSelectedvalue = 0;
                setAllImageViewUnchecked(imgDateAddedCheck,imgManualCheck,imgActiveCheck,imgHiddenCheck,imgCompletedChk);
                imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                llSecetDateWhole.setVisibility(View.GONE);
            }
        });*/

        rlDateAdded.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolDateAdded) {
                    if (!arrSelectedValue.contains(1)) {

                        addArray(1);
                        boolDateAdded = true;
                        imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                        sharedPreference.writeBoolean("BUCKET_LIST_SORT_DUE_DATE", "", true);

                        removeFromArray(2);
                        sharedPreference.clear("BUCKET_LIST_SORT_MANUAL");
                        boolManual = false;
                        showManual = false;

                    }

                } else {
                    removeFromArray(1);
                    boolDateAdded = false;
                    sharedPreference.writeBoolean("BUCKET_LIST_SORT_DUE_DATE", "", false);
                    imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }

                List<BucketListModelInner> bkpData = filterData();
                rlManualTick.setVisibility(View.GONE);
                showManual = false;
                if (bkpData.size() > 0) {
                    Log.e("print filter size--", bkpData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(bkpData);
                } else {
                    recyclerGratitudes.setAdapter(null);
                }
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);
                dialog.dismiss();


            }
        });

        rlManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!boolManual) {
                    if (!arrSelectedValue.contains(2)) {
                        addArray(2);
                        showManual = true;
                        boolManual = true;
                        imgManualCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                        sharedPreference.writeBoolean("BUCKET_LIST_SORT_MANUAL", "", true);

                        /////////////////////
                        removeFromArray(1);
                        sharedPreference.clear("BUCKET_LIST_SORT_DUE_DATE");
                        boolDateAdded = false;
                        imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);

                        blink();

                    }

                } else {
                    removeFromArray(2);
                    sharedPreference.writeBoolean("BUCKET_LIST_SORT_MANUAL", "", false);
                    boolManual = false;
                    showManual = false;
                    imgManualCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }

                filterData();
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);
                dialog.dismiss();

            }
        });


        rlWithIn1.setOnClickListener(view1 -> {
            sharedPreference.write("BUCKET_LIST_TIMELINE", "", 1 + "");
            timelineValue = 1;
            filterData();
            //filterByTimeline(timelineValue);
            dialog.dismiss();
        });


        rlWithIn5.setOnClickListener(view1 -> {
            sharedPreference.write("BUCKET_LIST_TIMELINE", "", 5 + "");
            timelineValue = 5;
            filterData();
            //filterByTimeline(timelineValue);
            dialog.dismiss();
        });


        rlAll.setOnClickListener(view1 -> {
            sharedPreference.write("BUCKET_LIST_TIMELINE", "", 0 + "");
            timelineValue = 0;
            filterData();
            //filterByTimeline(timelineValue);
            dialog.dismiss();

        });


        rlActive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!boolActive) {
                    if (!arrSelectedValue.contains(3)) {
                        addArray(3);
                        boolActive = true;
                        sharedPreference.writeBoolean("BUCKET_LIST_STATUS_ACTIVE", "", true);
                        imgActiveCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    }

                } else {
                    removeFromArray(3);
                    boolActive = false;
                    sharedPreference.writeBoolean("BUCKET_LIST_STATUS_ACTIVE", "", false);
                    imgActiveCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }

            }
        });

        rlHidden.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!boolHidden) {
                    if (!arrSelectedValue.contains(4)) {
                        addArray(4);
                        boolHidden = true;
                        sharedPreference.writeBoolean("BUCKET_LIST_STATUS_HIDDEN", "", true);
                        imgHiddenCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    }
                } else {
                    removeFromArray(4);
                    boolHidden = false;
                    sharedPreference.writeBoolean("BUCKET_LIST_STATUS_HIDDEN", "", false);
                    imgHiddenCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
                }

            }
        });

        rlCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!boolCompleted) {
                    if (!arrSelectedValue.contains(5)) {
                        addArray(5);
                        boolCompleted = true;
                        sharedPreference.writeBoolean("BUCKET_LIST_STATUS_COMPLETED", "", true);
                        imgCompletedChk.setBackgroundResource(R.drawable.mbhq_ic_tick);
                    }

                } else {
                    removeFromArray(5);
                    boolCompleted = false;
                    sharedPreference.writeBoolean("BUCKET_LIST_STATUS_COMPLETED", "", false);
                    imgCompletedChk.setBackgroundResource(R.drawable.mbhq_ic_untick);
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

        rlShowResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<BucketListModelInner> filterData = new ArrayList<>();
                imgFiler.setImageResource(R.drawable.mbhq_filter_green);

                filterData = filterData();

                if (filterData.size() > 0) {
                    Log.e("print filter size--", filterData.size() + "??");

                    recyclerGratitudes.setAdapter(null);
                    loadAdapter(filterData);
                } else {
                    Util.showToast(getActivity(), "No data found");
                    recyclerGratitudes.setAdapter(null);
                }
                dialog.dismiss();
            }
        });


        rlClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgFiler.setImageResource(R.drawable.mbhq_filter);
                timelineValue = -1;
                //filterSelectedvalue = 0;
                setAllImageViewUnchecked(imgDateAddedCheck, imgManualCheck, imgActiveCheck, imgHiddenCheck, imgCompletedChk);
                // imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_tick);
                llSecetDateWhole.setVisibility(View.GONE);
                rlManual.setVisibility(View.GONE);
                rlManualTick.setVisibility(View.GONE);
                sharedPreference.clear("BUCKET_LIST_STATUS_ACTIVE");
                sharedPreference.clear("BUCKET_LIST_STATUS_HIDDEN");
                sharedPreference.clear("BUCKET_LIST_STATUS_COMPLETED");
                sharedPreference.clear("BUCKET_LIST_SORT_DUE_DATE");
                sharedPreference.clear("BUCKET_LIST_SORT_MANUAL");
                sharedPreference.clear("BUCKET_LIST_TIMELINE");
                recyclerGratitudes.setAdapter(null);
                filterData();
                dialog.dismiss();
            }
        });


        dialog.show();
    }

    private List<BucketListModelInner> filterByTimeline(int yearWithIn) {

        List<BucketListModelInner> lstOneYear = new ArrayList<>();

        if (yearWithIn == 0 || yearWithIn == -1) {
            lstOneYear.addAll(globalData);
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date currentDate = calendar.getTime();
            calendar.add(Calendar.YEAR, yearWithIn);
            Date oneYearDate = calendar.getTime();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            for (int i = 0; i < globalData.size(); i++) {
                try {
                    Date completionDate = format.parse(globalData.get(i).getCompletionDate());

                    if (completionDate != null && ((completionDate.after(currentDate) && completionDate.before(oneYearDate)) || completionDate.equals(currentDate) || completionDate.equals(oneYearDate))) {
                        lstOneYear.add(globalData.get(i));
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        List<BucketListModelInner> lstUltimateResult = new ArrayList<>();

        if (arrSelectedValue.contains(3))//Active
        {
            for (int i = 0; i < lstOneYear.size(); i++) {
                if (lstOneYear.get(i).getBucketStatus() == 1) {
                    lstUltimateResult.add(lstOneYear.get(i));
                }
            }
        }

        if (arrSelectedValue.contains(4))//Hidden
        {
            for (int i = 0; i < lstOneYear.size(); i++) {
                if (lstOneYear.get(i).getBucketStatus() == 0) {
                    lstUltimateResult.add(lstOneYear.get(i));
                }
            }
        }

        if (arrSelectedValue.contains(5))//Completed
        {
            for (int i = 0; i < lstOneYear.size(); i++) {
                if (lstOneYear.get(i).getBucketStatus() == 2) {
                    lstUltimateResult.add(lstOneYear.get(i));
                }
            }
        }

        if (arrSelectedValue.contains(3) || arrSelectedValue.contains(4) || arrSelectedValue.contains(5)) {
            if (arrSelectedValue.contains(1)) {
                filterdatabyDate(lstUltimateResult);
            }
            recyclerGratitudes.setAdapter(null);
            loadAdapter(lstUltimateResult);
            return lstUltimateResult;
        } else {
            if (arrSelectedValue.contains(1)) {
                filterdatabyDate(lstUltimateResult);
            }
            recyclerGratitudes.setAdapter(null);
            loadAdapter(lstOneYear);
            return lstOneYear;
        }

    }

    private List<BucketListModelInner> filterData() {

        if (arrSelectedValue.contains(2)) {
            recyclerGratitudes.setAdapter(null);
            loadAdapter(globalData);
            return globalData;
        } else {
            List<BucketListModelInner> filteredBucketListByTimeline = new ArrayList<>();

            if (timelineValue == 0 || timelineValue < 0) {
                filteredBucketListByTimeline.addAll(globalData);
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date currentDate = calendar.getTime();
                calendar.add(Calendar.YEAR, timelineValue);
                Date oneYearDate = calendar.getTime();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < globalData.size(); i++) {
                    try {
                        Date completionDate = format.parse(globalData.get(i).getCompletionDate());

                        if (completionDate != null && ((completionDate.after(currentDate) && completionDate.before(oneYearDate)) || completionDate.equals(currentDate) || completionDate.equals(oneYearDate))) {
                            filteredBucketListByTimeline.add(globalData.get(i));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }

            List<BucketListModelInner> filteredBucketListByTimelineAndStatus = new ArrayList<>();

            if (!arrSelectedValue.contains(3) && !arrSelectedValue.contains(4) && !arrSelectedValue.contains(5)) {

                filteredBucketListByTimelineAndStatus.addAll(filteredBucketListByTimeline);

            } else {

                boolean doFilterForActiveStatus = arrSelectedValue.contains(3);
                boolean doFilterForHiddenStatus = arrSelectedValue.contains(4);
                boolean doFilterForCompletedStatus = arrSelectedValue.contains(5);

                for (int i = 0; i < filteredBucketListByTimeline.size(); i++) {
                    if (
                            (filteredBucketListByTimeline.get(i).getBucketStatus() == 1 && doFilterForActiveStatus)
                                    ||
                                    (filteredBucketListByTimeline.get(i).getBucketStatus() == 0 && doFilterForHiddenStatus)
                                    ||
                                    (filteredBucketListByTimeline.get(i).getBucketStatus() == 2 && doFilterForCompletedStatus)
                    ) {
                        filteredBucketListByTimelineAndStatus.add(filteredBucketListByTimeline.get(i));
                    }
                }


            }

            if (arrSelectedValue.contains(1)) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                Collections.sort(filteredBucketListByTimelineAndStatus, new Comparator<BucketListModelInner>() {
                    public int compare(BucketListModelInner o1, BucketListModelInner o2) {
                        if (o1.getCompletionDate() == null || o2.getCompletionDate() == null)
                            return 0;
                        try {
                            return simpleDateFormat.parse(o1.getCompletionDate()).compareTo(simpleDateFormat.parse(o2.getCompletionDate()));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            return 0;
                        }

                    }
                });
            }

            recyclerGratitudes.setAdapter(null);
            loadAdapter(filteredBucketListByTimelineAndStatus);
            return filteredBucketListByTimelineAndStatus;
        }

    }

    private void filterdatabyDate(List<BucketListModelInner> bkpData) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Collections.sort(bkpData, new Comparator<BucketListModelInner>() {
            public int compare(BucketListModelInner o1, BucketListModelInner o2) {
                if (o1.getCompletionDate() == null || o2.getCompletionDate() == null)
                    return 0;
                try {
                    return simpleDateFormat.parse(o1.getCompletionDate()).compareTo(simpleDateFormat.parse(o2.getCompletionDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                    return 0;
                }

            }
        });

    }

    private void addArray(int i) {
        //  Log.e("item rcvd to add--",i+"??");
        arrSelectedValue.add(i);
    }

    private void removeFromArray(int i) {

        Log.e("Item to remove--", i + "??");
        for (int x = 0; x < arrSelectedValue.size(); x++) {
            if (i == arrSelectedValue.get(x))
                arrSelectedValue.remove(x);


        }

    }


    private void setAllImageViewUnchecked(ImageView imgDateAddedCheck, ImageView imgManualCheck, ImageView imgActiveCheck, ImageView imgHiddenCheck, ImageView imgCompletedChk) {

        imgDateAddedCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgManualCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgActiveCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgHiddenCheck.setBackgroundResource(R.drawable.mbhq_ic_untick);
        imgCompletedChk.setBackgroundResource(R.drawable.mbhq_ic_untick);
        arrSelectedValue.clear();
        makeAllBoolFalse();

    }

    private void makeAllBoolFalse() {
        boolDateAdded = false;
        showManual =false;
        boolManual = false;
        boolActive = false;
        boolHidden = false;
        boolCompleted = false;
    }


    private void openInfoDialog() {
        Dialog dialog = new Dialog(getActivity(), R.style.DialogThemeAnother);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_info_bucket);
        ImageView imgCross = dialog.findViewById(R.id.imgCross);
        RelativeLayout rlGotIt = dialog.findViewById(R.id.rlGotIt);
        rlGotIt.setOnClickListener(new View.OnClickListener() {
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
        SharedPreference sharedPreference = new SharedPreference(getActivity());
        sharedPreference.write("bucketFirstTime", "", "true");
    }


    private void updateHabitSwapManualOrderApiCall() {

        if (Connection.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            List<Integer> lstHabitIds = new ArrayList<>();
            for (int i = 0; i < lstManualDrag.size(); i++) {
                lstHabitIds.add(lstManualDrag.get(i).getId());
            }

            HashMap<String, Object> hashReq = new HashMap<>();

            hashReq.put("BucketListIds", lstHabitIds);
            hashReq.put("Key", Util.KEY);
            hashReq.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));

            Call<JsonObject> userHabitSwapsModelCall = finisherService.UpdateBucketListManualOrder(hashReq);
            userHabitSwapsModelCall.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    progressDialog.dismiss();
                    if (response.body() != null) {
                        if (response.body().get("SuccessFlag").getAsBoolean()) {
                            Util.showToast(getActivity(), "Successfully Saved");
                            refresh = true;
                            getBucketListFromApi();
                            //((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketListFragment());
                            //((MainActivity) getActivity()).loadFragment(new BucketListFragment(), "BucketListFragment", null);
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

    private void blink() {
        rlManualTick.setVisibility(View.VISIBLE);
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(500); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        imgManualTick.startAnimation(animation); //to start animation
    }

    public void deleteGratitude(Integer bucket_Id) {

        if (Util.checkConnection(getActivity())) {

            progressDialog = ProgressDialog.show(getActivity(), "", "Please wait...");

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("BucketID", bucket_Id);
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
                            ((MainActivity) getActivity()).clearCacheForParticularFragment(new BucketListFragment());
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
                    Uri uri = getImageUri(requireActivity(),bitimage);
                    String cropPath = Util.getFilePathFromUri(requireActivity(),uri);
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

    private void preaprePictureForUpload(String cropPath) {
        try {
            File file = new File(cropPath);
            FileInputStream imageInFile = new FileInputStream(file);
            byte[] imageData = new byte[(int) file.length()];
            imageInFile.read(imageData);
            stringImg = encodeImage(imageData);
            Log.e("BASE64STRING", stringImg);//////
            //sharedPreference.write("GRATITUDEIMAGE", "", stringImg);
            mFile = file;
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
            imgBackgroundPicTOP.setVisibility(View.VISIBLE);
            if (null != cropPath) {
                Log.e("CRPPPPPP", "preaprePictureForUpload: " + cropPath );
                imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(cropPath));

            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("CROPPPP", "preaprePictureForUpload: " + e.getMessage());
        }
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
    public void uploadImageToServer(File file){
      mFile=file;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("MyAchievementsFragment", "request code => " + requestCode + "result code => " + resultCode);
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK){
            if (photoFile != null) {
                Bitmap imageBitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                cropPhoto(imageBitmap,photoFile.getAbsolutePath());
            }

        }else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //Uri selectedImageUri = intent.getData();
            Uri selectedImageUri = data.getParcelableExtra("CAMERA_URI");
            setImageToCropper(selectedImageUri);
        }
        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            String imagePath = data.getStringExtra("image_path");
            try {
                cardViewBackgroundPicTOP.setVisibility(View.VISIBLE);
                imgBackgroundPicTOP.setVisibility(View.VISIBLE);
                if (null != imagePath) {
                    Log.e("CRPPPPPP", "preaprePictureForUpload: " + imagePath );
                    imgBackgroundPicTOP.setImageBitmap(BitmapFactory.decodeFile(imagePath));

                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("CROPPPP", "preaprePictureForUpload: " + e.getMessage());
            }
              uploadImageToServer(new File(imagePath));
           // preaprePictureForUpload(imagePath);
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
//                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                    // Get the cursor
//                    if (getActivity() != null) {
//                        Cursor cursor = ((MainActivity) getActivity()).getContentResolver().query(selectedImage,
//                                filePathColumn, null, null, null);
//
//                    }
//                    Cursor cursor = ((MainActivity) getActivity()).getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    // Move to first row
//                    cursor.moveToFirst();
//                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                    imgDecodableString = cursor.getString(columnIndex);
//                    File comImg = null;
//                    try {
//                        if (getContext() != null) {
//                            comImg = new Compressor(getContext()).compressToFile(new File(imgDecodableString));
//
//                        }
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    long bitmapLengthSize = comImg.length();
//                    Log.e("gal compress img", "gallery compress img size>>>>>>>>>" + (bitmapLengthSize * 0.001) + " KB");
//                    cursor.close();
//                    cropPhoto(BitmapFactory.decodeFile(imgDecodableString), comImg.getAbsolutePath());
                    setImageToCropper(selectedImage);
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
                openCustomCamera();


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
                getActivity().startActivityForResult(galleryIntent, ((MainActivity) getActivity()).PICK_IMAGE_FROM_GALLERY_CODE_ACTIVITY_RESULT_FROM_GRATITUDE_LIST);

            } else {
                Log.e("camera","23");
                openAppSettings();
                // Permission was denied
                // You can disable the feature that requires the camera permission
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

}