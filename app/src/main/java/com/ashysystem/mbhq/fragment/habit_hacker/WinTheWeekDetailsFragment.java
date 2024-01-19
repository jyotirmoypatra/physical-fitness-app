package com.ashysystem.mbhq.fragment.habit_hacker;

import android.Manifest;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.PermissionChecker;
import androidx.fragment.app.Fragment;
import androidx.multidex.BuildConfig;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ashysystem.mbhq.R;
import com.ashysystem.mbhq.activity.MainActivity;

import com.ashysystem.mbhq.adapter.WinTheWeekStreakAdapter;
import com.ashysystem.mbhq.model.GetWinTheWeekStatsResponse;
import com.ashysystem.mbhq.util.SharedPreference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WinTheWeekDetailsFragment extends Fragment {

    private static final String ARG_PARAM1 = "WIN_THE_WEEK_STATS";
    private static final String ARG_PARAM2 = "IS_WEEKLY";

    private GetWinTheWeekStatsResponse winTheWeekStats;
    private boolean isWeekly;

    public WinTheWeekDetailsFragment() {
        // Required empty public constructor
    }

    public static WinTheWeekDetailsFragment newInstance(GetWinTheWeekStatsResponse winTheWeekStats, boolean isWeekly) {
        WinTheWeekDetailsFragment fragment = new WinTheWeekDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, winTheWeekStats);
        args.putBoolean(ARG_PARAM2, isWeekly);
        fragment.setArguments(args);
        return fragment;
    }

    private String TAG = "WinTheWeekDetailsFragment";

    private TextView currentStreakCount, personalBestCount, totalCount, wonTheWeekTitleText;

    RecyclerView rvStreaks;

    private View rlBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            winTheWeekStats = (GetWinTheWeekStatsResponse) getArguments().getSerializable(ARG_PARAM1);
            isWeekly = getArguments().getBoolean(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_win_the_week_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        currentStreakCount = view.findViewById(R.id.currentStreakCount);
        personalBestCount = view.findViewById(R.id.personalBestCount);
        totalCount = view.findViewById(R.id.totalCount);
        wonTheWeekTitleText = view.findViewById(R.id.wonTheWeekTitleText);
        rlBack = view.findViewById(R.id.rlBack);

        rvStreaks = view.findViewById(R.id.rvStreaks);

        rvStreaks.setLayoutManager(new LinearLayoutManager(getContext()));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.CAMERA}, 289);
        }else{
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 289);
        }

        if (isWeekly) {

            currentStreakCount.setText(winTheWeekStats.getCurrentWeeklyStreak().toString());
            personalBestCount.setText(winTheWeekStats.getPersonalBestWeeklyStreak().toString());
            wonTheWeekTitleText.setText("WEEKS WON");
            totalCount.setText(winTheWeekStats.getWeeklyWins().toString());
            rvStreaks.setAdapter(new WinTheWeekStreakAdapter(winTheWeekStats.getWeeklyWinBrackets(), isWeekly, new WinTheWeekStreakAdapter.InteractionListener() {
                @Override
                public void onClick(GetWinTheWeekStatsResponse.Wins.DayStatus data, boolean isWeekly) {
                    Log.i(TAG, "clicked");
                    openWinTheHabitModal(isWeekly, data);
                }
            }));

        } else {

            currentStreakCount.setText(winTheWeekStats.getCurrentDailyStreak().toString());
            personalBestCount.setText(winTheWeekStats.getPersonalBestDailyStreak().toString());
            wonTheWeekTitleText.setText("DAYS WON");
            totalCount.setText(winTheWeekStats.getDayWins().toString());
            rvStreaks.setAdapter(new WinTheWeekStreakAdapter(winTheWeekStats.getDailyWinBrackets(), isWeekly, new WinTheWeekStreakAdapter.InteractionListener() {
                @Override
                public void onClick(GetWinTheWeekStatsResponse.Wins.DayStatus data, boolean isWeekly) {
                    Log.i(TAG, "clicked");
                    openWinTheHabitModal(isWeekly, data);
                }
            }));
        }


        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).loadFragment(WinTheWeekStatsFragment.newInstance(), "WinTheWeekStatsFragment", null);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void openWinTheHabitModal(Boolean wonTheWeek, GetWinTheWeekStatsResponse.Wins.DayStatus dayStatus) {

        Log.i(TAG, "open win the habit modal");

        Integer winStreak = dayStatus.getDoneCount();
        String weekStartDate = dayStatus.getWeekDate();

        SimpleDateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat monthNameDateFormat = new SimpleDateFormat("MMM");
        SimpleDateFormat dayNameDateFormat = new SimpleDateFormat("EEE");
        SimpleDateFormat numericalDateFormat = new SimpleDateFormat("dd");

        Date currentDate = new Date();

        Dialog dialog = new Dialog(this.getContext(), R.style.DialogThemeAnother);
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
        View imgFooterLogo = dialog.findViewById(R.id.imgFooterLogo);
        Button saveButton = dialog.findViewById(R.id.saveButton);
        Button seeResultButton = dialog.findViewById(R.id.seeResultButton);

        SharedPreference sharedPreference = new SharedPreference(getContext());

        String firstName = sharedPreference.read("firstName", "");

        if (firstName.length() > 0) {
            firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
        }

        userGreetText.setText("Congratulations " + firstName);

        if (wonTheWeek) {
            winTypeText.setText("WON THE WEEK");

            try {
                winDateDayText.setText(
                        monthNameDateFormat.format(
                                apiDateFormat.parse(weekStartDate)
                        ).toUpperCase()
                );

                winDateText.setText(
                        numericalDateFormat.format(
                                apiDateFormat.parse(weekStartDate)
                        ).toUpperCase()
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }


            extraText.setText(winStreak + " Days Won");
            switch (winStreak) {
                case 7:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.seven_manny_3x));
                    break;
                case 6:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.six_manny_3x));
                    break;
                case 5:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.five_manny_3x));
                    break;
                default:
                    congratsImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.four_manny_3x));
            }

        } else {
            winTypeText.setText("WON THE DAY");
            extraText.setText("100% Habits Completed");

            try {
                winDateDayText.setText(

                        dayNameDateFormat.format(
                                apiDateFormat.parse(weekStartDate)
                        ).toUpperCase()
                );

                winDateText.setText(
                        numericalDateFormat.format(
                                apiDateFormat.parse(weekStartDate)
                        ).toUpperCase()
                );
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            /*winDateDayText.setText(
                    dayNameDateFormat.format(currentDate)
            );
            winDateText.setText(
                    numericalDateFormat.format(currentDate)
            );*/
            congratsImage.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.one_manny_thumbsup_silver_3x));
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
/*commented by sahenita temporary*/
               if (hasCameraPermission()) {


                   // getScreenshotOf(mainModal);
                   captureScreenshot(mainModal);
                } else {
                  //  requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 289);
                   if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                       requestPermissions(new String[]{Manifest.permission.READ_MEDIA_IMAGES,Manifest.permission.READ_MEDIA_AUDIO,Manifest.permission.READ_MEDIA_VIDEO,Manifest.permission.CAMERA}, 289);
                   }else{
                       requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, 289);
                   }
                }

            }

        });

        seeResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //((MainActivity) getActivity()).clearCacheForParticularFragment(WinTheWeekStatsFragment.newInstance());
                ((MainActivity) getActivity()).loadFragment(WinTheWeekStatsFragment.newInstance(), "WinTheWeekStatsFragment", null);
            }
        });

        dialog.show();

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
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
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
    private Uri saveImageToMediaStore(File imageFile) {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "shared_image.jpg");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);

        ContentResolver contentResolver = getActivity().getContentResolver();
        Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Uri resultUri = contentResolver.insert(contentUri, values);
        try {
            if (resultUri != null) {
                try (OutputStream outputStream = contentResolver.openOutputStream(resultUri);
                     InputStream inputStream = new FileInputStream(imageFile)) {
                    if (outputStream != null) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultUri;
    }
    public  Bitmap captureScreenshot( ViewGroup view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        File shareFile= getImageFile(bitmap);

        Uri contentUri = saveImageToMediaStore(shareFile);
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, contentUri);

        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(share, "Share Image"));

        return bitmap;
        // Save the bitmap using ContentResolver
        //saveBitmap(context, bitmap);
    }
    private File saveBitmapToFile(ByteArrayOutputStream bytes) {
        File filesDir = getActivity().getFilesDir();
        File imageFile = new File(filesDir, "image.jpg");

        try (FileOutputStream fos = new FileOutputStream(imageFile)) {
            // Compress the bitmap to a JPEG with compression factor 80 (adjust as needed)
            Bitmap compressedBitmap = BitmapFactory.decodeByteArray(bytes.toByteArray(), 0, bytes.size());
            compressedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);

            return imageFile;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private File getImageFile(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
        // Save the bitmap to a file
        File imageFile = saveBitmapToFile(bytes);

        if (imageFile != null) {
            // Get the Uri from the file
            return imageFile;
        } else {
            // Handle the case where imageFile is null
            Log.e("YourTag", "Failed to save bitmap to file.");
            return null;
        }
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

        Uri uri = FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID + ".fileprovider", file);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpeg");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(share, "Share Image"));

        Log.i(TAG, uri.toString());

        return screenshot;
    }

}