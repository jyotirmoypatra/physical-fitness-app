package com.ashysystem.mbhq.util;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import com.ashysystem.mbhq.R;

import com.ashysystem.mbhq.model.ArchivedWebinalModel;
import com.ashysystem.mbhq.model.MeditationCourseModel;
import com.google.gson.Gson;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by android-krishnendu on 9/30/16.
 */

public class Downloader extends AsyncTask<String, String, String> {

    private Context context;
    private final NotificationManager mNotifyManager;
    private final NotificationCompat.Builder mBuilder;
    private final int id;
    private String videourl;
    private String filaName;
    ImageView imageView;
    TextView textView;
    int lenghtOfFile;
    ArchivedWebinalModel archivedWebinalModel;
    MeditationCourseModel.Webinar webinarMeditationModel;
    //int position;

    private OnDownloadCompleteListener onDownloadCompleteListener;

    public void setOnDownloadCompleteListener(OnDownloadCompleteListener onDownloadCompleteListener) {
        this.onDownloadCompleteListener = onDownloadCompleteListener;
    }

    public Downloader(Context context, NotificationManager mNotifyManager, NotificationCompat.Builder mBuilder, int id, String URL, String filaName, ImageView imageView, ArchivedWebinalModel archivedWebinalModel, TextView textView, MeditationCourseModel.Webinar webinarMeditationModel)
    {
        this.context = context;
        this.mNotifyManager = mNotifyManager;
        this.mBuilder = mBuilder;
        this.id = id;
        this.videourl = URL;
        this.filaName = filaName;
        this.imageView=imageView;
        this.archivedWebinalModel=archivedWebinalModel;
        this.textView=textView;
        this.webinarMeditationModel=webinarMeditationModel;
        //this.position=position;
        Log.e("print url--",videourl+"???");
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mBuilder.setProgress(100, 0, false);
        mNotifyManager.notify(id, mBuilder.build());

    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {

            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            lenghtOfFile = conection.getContentLength();
            //archivedWebinalModel.setDownloadedFileSize(lenghtOfFile);

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            File downloadingMediaFile = new File(context.getCacheDir(),"SQUAD/"+filaName);
            downloadingMediaFile.setReadable(true);

            downloadingMediaFile.getParentFile().mkdirs();
            // Output stream to write file
            OutputStream output = new FileOutputStream(downloadingMediaFile.getAbsolutePath());

            byte data[] = new byte[1024];

            long downloaded = 0;
            int lastProgress=0;

            while ((count = input.read(data)) != -1) {
                downloaded += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                int currentProgress=(int)((downloaded*100)/lenghtOfFile);

                if(currentProgress-lastProgress==10)
                {
                    publishProgress(""+currentProgress);
                    lastProgress=currentProgress;

                }


                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

            mBuilder.setOngoing(true);


            /*if(onDownloadCompleteListener!=null)
            {
                onDownloadCompleteListener.onDownloadComplete(position);
            }*/
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        // Update progress
        mBuilder.setProgress(100, Integer.parseInt(progress[0]), false);
        mNotifyManager.notify(id, mBuilder.build());
        super.onProgressUpdate(progress);
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
        //dismissDialog(progress_bar_type);

        // Displaying downloaded image into image view
        // Reading image path from sdcard
        //String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
        // setting downloaded into image view
        //my_image.setImageDrawable(Drawable.createFromPath(imagePath));
        mBuilder.setAutoCancel(true);
        mNotifyManager.cancel(id);
        File downloadingMediaFile = new File(context.getCacheDir(),"SQUAD/"+filaName);
        if(downloadingMediaFile.length()==lenghtOfFile)
        {
            if(imageView!=null)
            {
                if(!isTablet(context))
                {
                    imageView.setBackgroundResource(R.drawable.play_black);
                }else
                {
                    imageView.setBackgroundResource(R.drawable.play_back_tab);
                }
            }
            if(textView!=null)
            {
                textView.setText("Downloaded Meditation");
                onDownloadCompleteListener.onDownloadComplete(1);
                //textView.setTextColor(Color.parseColor("#FFD484B8"));
                Gson gson = new Gson();
                Log.e("HIT_BROADCAST","TRUEEEEEEEEEEEE");
                Intent intent = new Intent("com.ashysystem.mbhq.meditationDownloadComplete").putExtra("DOWNLOAD", gson.toJson(webinarMeditationModel));
                context.sendBroadcast(intent);

            }

        }else
        {
            downloadingMediaFile.delete();
            onDownloadCompleteListener.onDownloadComplete(0);
        }


    }

    public interface OnDownloadCompleteListener
    {
        void onDownloadComplete(int position);
    }

    public boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }
}
