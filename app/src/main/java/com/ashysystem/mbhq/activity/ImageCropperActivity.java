package com.ashysystem.mbhq.activity;

import android.app.Activity;
import android.app.Dialog;
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
import android.view.Window;
import android.widget.Button;
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

    private TextView editPhoto;
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
        editPhoto = (TextView) findViewById(R.id.editPhoto);
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

        editPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              openCropRatioDialog();
            }

        });

       parseData();

    }



    private void openCropRatioDialog() {
        Dialog customDialog = new Dialog(this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.crop_ratio_dialog);
        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        // Initialize and set up your custom UI components here
        Button closeCropRatioDialog = customDialog.findViewById(R.id.closeCropRatioDialog);
        closeCropRatioDialog.setOnClickListener(v -> customDialog.dismiss());

        TextView original = customDialog.findViewById(R.id.original);
        TextView square = customDialog.findViewById(R.id.square);
        TextView _3x2 = customDialog.findViewById(R.id._3x2);
        TextView _3x5 = customDialog.findViewById(R.id._3x5);
        TextView _4x3 = customDialog.findViewById(R.id._4x3);
        TextView _4x6 = customDialog.findViewById(R.id._4x6);
        TextView _5x7 = customDialog.findViewById(R.id._5x7);
        TextView _8x10 = customDialog.findViewById(R.id._8x10);
        TextView _16x9 = customDialog.findViewById(R.id._16x9);

        View.OnClickListener textViewClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedRatio = ((TextView) v).getText().toString();

                    handleSelectedRatio(selectedRatio);

                // Dismiss the dialog if needed
                customDialog.dismiss();
            }
        };

        original.setOnClickListener(textViewClickListener);
        square.setOnClickListener(textViewClickListener);
        _3x2.setOnClickListener(textViewClickListener);
        _3x5.setOnClickListener(textViewClickListener);
        _4x3.setOnClickListener(textViewClickListener);
        _4x6.setOnClickListener(textViewClickListener);
        _5x7.setOnClickListener(textViewClickListener);
        _8x10.setOnClickListener(textViewClickListener);
        _16x9.setOnClickListener(textViewClickListener);


        customDialog.show();
    }

    private void handleSelectedRatio(String selectedRatio) {
        int width = 0;
        int height= 0;

        // Handle the special case for "Original"
        if (selectedRatio.equalsIgnoreCase("Original")) {

            width = 0;
            height=0;
        }else if (selectedRatio.equalsIgnoreCase("Square")) {
            width = 1;
            height = 1;
        }else if (selectedRatio.equalsIgnoreCase("3 X 2")) {
            width = 3;
            height = 2;
        }
        else if (selectedRatio.equalsIgnoreCase("3 X 5")) {
            width = 3;
            height = 5;
        }else if (selectedRatio.equalsIgnoreCase("4 X 3")) {
            width = 4;
            height = 3;
        }else if (selectedRatio.equalsIgnoreCase("4 X 6")) {
            width = 4;
            height = 6;
        }else if (selectedRatio.equalsIgnoreCase("5 X 7")) {
            width = 5;
            height = 7;
        }else if (selectedRatio.equalsIgnoreCase("8 X 10")) {
            width = 8;
            height = 10;
        }else if (selectedRatio.equalsIgnoreCase("16 X 9")) {
            width = 16;
            height = 9;
        }

        setCropRatio(width, height);

    }
    private void setCropRatio(int width, int height) {
        _cropRatio = width * 10 + height;
        parseData();
    }

    private void parseData() {
        if (imageUri != null)
            cropImageView.setImageUriAsync(imageUri);



        cropImageView.setGuidelines(CropImageView.Guidelines.ON_TOUCH);
        switch (_cropRatio) {
            case 11:
                cropImageView.setAspectRatio(1, 1);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 32:
                cropImageView.setAspectRatio(3, 2);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 43:
                cropImageView.setAspectRatio(4, 3);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 46:
                cropImageView.setAspectRatio(4, 6);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 57:
                cropImageView.setAspectRatio(5, 7);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 90:
                cropImageView.setAspectRatio(8, 10);
                cropImageView.setFixedAspectRatio(true);
                break;
            case 169:
                cropImageView.setAspectRatio(16, 9);
                cropImageView.setFixedAspectRatio(true);
                break;
            default:
               cropImageView.setFixedAspectRatio(false);
                break;

        }


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
