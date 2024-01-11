package com.ashysystem.mbhq.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ashysystem.mbhq.R;
import com.otaliastudios.cameraview.Audio;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.Facing;
import com.otaliastudios.cameraview.Flash;
import com.otaliastudios.cameraview.Gesture;
import com.otaliastudios.cameraview.GestureAction;
import com.otaliastudios.cameraview.Grid;
import com.otaliastudios.cameraview.Hdr;
import com.otaliastudios.cameraview.SessionType;
import com.otaliastudios.cameraview.WhiteBalance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraNewActivity extends AppCompatActivity {

    private ImageView imageView;
    private CameraView cameraView;
    private Uri imageUri;
    private ImageView captureButton;
    private TextView cancel;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_new);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//
//        if (new CommonHelper().isTablet(CameraNewActivity.this)) {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        } else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        }

        cancel = (TextView) findViewById(R.id.cancel);
        captureButton = (ImageView) findViewById(R.id.captureButton);
        cameraView = (CameraView) findViewById(R.id.cameraView);

        cameraView.mapGesture(Gesture.TAP, GestureAction.FOCUS_WITH_MARKER);
        cameraView.setSessionType(SessionType.PICTURE);
        cameraView.setFacing(Facing.BACK);
        cameraView.setFlash(Flash.AUTO);
        cameraView.setGrid(Grid.OFF);
        cameraView.setJpegQuality(100);
        cameraView.setWhiteBalance(WhiteBalance.AUTO);
        cameraView.setHdr(Hdr.ON);
        cameraView.setAudio(Audio.OFF);
        cameraView.setPlaySounds(true);
        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                // Create a bitmap or a file...
                // CameraUtils will read EXIF orientation for you, in a worker thread.
                Bitmap bitmap = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                //Bitmap _rBitmap = rotateBitmap(bitmap);

                imageUri = getImageUri(bitmap);

                takePhoto();
            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraView.capturePicture();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void takePhoto() {
        if (imageUri != null) {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("CAMERA_URI", imageUri);
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        }
    }

   /* private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/


    private File saveBitmapToFile(ByteArrayOutputStream bytes) {
        File filesDir = getFilesDir();
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
    private Uri getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
        // Save the bitmap to a file
        File imageFile = saveBitmapToFile(bytes);

        if (imageFile != null) {
            // Get the Uri from the file
            return Uri.fromFile(imageFile);
        } else {
            // Handle the case where imageFile is null
            Log.e("YourTag", "Failed to save bitmap to file.");
            return null;
        }
    }
    /*private Bitmap getImageBitmap(String photoPath) {
        ExifInterface ei = null;
        Bitmap rotatedBitmap = null;
        try {
            ei = new ExifInterface(photoPath);
            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotatedBitmap = rotateImage(bitmap, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotatedBitmap = rotateImage(bitmap, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotatedBitmap = rotateImage(bitmap, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:
                default:
                    rotatedBitmap = bitmap;
            }
            return rotatedBitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rotatedBitmap;
    }

    private Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap rotatedImg = null;
        try {
            rotatedImg = null;
            Matrix matrix = new Matrix();
            matrix.postRotate(angle);
            rotatedImg = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
            source.recycle();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotatedImg;
    }*/

    private Bitmap rotateBitmap(Bitmap bitmapOrg) {
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmapOrg, bitmapOrg.getWidth(), bitmapOrg.getHeight(), true);
        Bitmap rotatedBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0, scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix, true);

        return rotatedBitmap;
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
