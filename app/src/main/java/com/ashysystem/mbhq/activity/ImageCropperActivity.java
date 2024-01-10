package com.ashysystem.mbhq.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.ashysystem.mbhq.R;

import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class ImageCropperActivity extends AppCompatActivity {

    private Uri imageUri;
    private int _cropRatio = 0;
    private CropImageView cropImageView;
    private TextView imageCropDone;
    private TextView imageCropBack;
    private TextView imageCropDoneWithoutCrop;
    private boolean isCommercial = false;
    private boolean isSingle = true;

    private TextView title;
    private int position = 0;


    //for language change


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_image_cropper);

        if (getIntent() != null) {
            if (getIntent().hasExtra("image_uri")) {
                imageUri = getIntent().getData();
                Log.e("imageUri: ", imageUri + ">>");
            }
            if (getIntent().hasExtra("crop_ratio")) {
                _cropRatio = getIntent().getIntExtra("crop_ratio", 0);
            }


        }
        initComponents();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initComponents() {
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        imageCropDone = (TextView) findViewById(R.id.imageCropDone);
        imageCropBack = (TextView) findViewById(R.id.imageCropBack);
        //for commercial
        imageCropDoneWithoutCrop = (TextView) findViewById(R.id.imageCropDoneWithoutCrop);
        title = (TextView) findViewById(R.id.title);

        imageCropBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getCroppedImage(false);
            }
        });
        imageCropDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCroppedImage(true);
            }

        });
        imageCropDoneWithoutCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCroppedImage(false);
            }
        });

        if (isSingle) parseData();

    }







    private void parseData() {
        if (imageUri != null)
            cropImageView.setImageUriAsync(imageUri);

        if (isCommercial) imageCropDoneWithoutCrop.setVisibility(View.VISIBLE);
        else imageCropDoneWithoutCrop.setVisibility(View.GONE);

        cropImageView.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
        switch (_cropRatio) {
            case 1:
                cropImageView.setAspectRatio(1, 1);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 43:
                cropImageView.setAspectRatio(4, 3);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 16:
                cropImageView.setAspectRatio(16, 9);
                cropImageView.setFixedAspectRatio(true);
                break;
            default:
                cropImageView.setAspectRatio(16, 9);
                cropImageView.setFixedAspectRatio(false);
                break;

        }

        title.setVisibility(View.GONE);
        imageCropDone.setText(getString(R.string.done));
    }

    public void getCroppedImage(boolean isCrop) {
        File file;
        if (!isCrop) {
            String realPathFromURI = getRealPathFromURI(imageUri);
            Intent intent = new Intent();
            intent.putExtra("image_path", realPathFromURI);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else {
            Bitmap cropped = cropImageView.getCroppedImage();
            file = saveImageToInternalStorage(cropped);
            if (file != null) {
                Intent intent = new Intent();
                intent.putExtra("image_path", file.getAbsolutePath());
                setResult(Activity.RESULT_OK, intent);
                finish();
            } else
                Toast.makeText(ImageCropperActivity.this, getString(R.string.failed_to_upload), Toast.LENGTH_SHORT).show();
        }
    }

    private String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            CursorLoader loader = new CursorLoader(ImageCropperActivity.this, contentUri, proj, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(column_index);
            cursor.close();
            Log.e("getPath: ", result);
            return result;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "";

        }
    }

    /*creates a file in internal storage and write image data to that file
     *this method returns the file created in internal storage*/
    public File saveImageToInternalStorage(Bitmap bitmapImage) {
        FileOutputStream fos = null;
        try {
            //get internal directory
            File mediaDir = createMediaDirectory();
            //unique file name
            String fileName = String.format("%s_%s%s", "IMG", Calendar.getInstance().getTimeInMillis(), ".jpg");
            //create new file in internal directory
            File file = new File(mediaDir + File.separator + fileName);
            //save image to internal directory
            fos = new FileOutputStream(file);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            //close
            fos.close();
            Log.v("Image Path", file.getAbsolutePath());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //creates a folder in apps directory
    public File createMediaDirectory() {
        try {
            // Get the directory for the app's private pictures directory.
            File file = new File(getExternalFilesDir(null), "image");
            if (!file.mkdirs()) {
                Log.v("Directory", "Directory already created.");
            }
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
