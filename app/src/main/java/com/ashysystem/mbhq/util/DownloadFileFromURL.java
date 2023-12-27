package com.ashysystem.mbhq.util;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by android-arindam on 2/12/15.
 */
public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread
     * Show Progress Bar Dialog
     * */

    //ProgressDialog pDialog;
    private Context context;
    private String fileUrl="";
    public interface AsynResponse {
        void processFinish(Boolean output);
    }

    AsynResponse asynResponse = null;
    String type="";
    String extension="";

public DownloadFileFromURL(Context context, String fileUrl, String type, boolean b, String extension, AsynResponse asynResponse)
{
    this.context=context;
    this.fileUrl=fileUrl;
    this.type=type;
    this.extension=extension;
    this.asynResponse = asynResponse;
    /*pDialog = new ProgressDialog(context);
    pDialog.setMessage("Downloading file. Please wait...");*/
   // pDialog.setIndeterminate(false);
  //  pDialog.setMax(100);
   // pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
    //pDialog.setCancelable(true);
    //pDialog.show();
    execute();
}
     @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Util.showToast(context,"File Downloaded");
      //  showDialog(progress_bar_type);
    }

    /**
     * Downloading file in background thread
     * */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            /////////////////

            String ALLOWED_URI_CHARS = "@#&=*+-_.,:!?()/~'%";

            String urlToDownload="";
            if(type.equals("Video"))
                urlToDownload+=Util.VIDEODOWNLOADBASEURL+fileUrl+extension;
            else if(type.equals("Audio"))
            {
                urlToDownload+=Util.AUDIOWNLOADBASEURL+fileUrl+extension;
            }
            else if(type.equals("AudioMotive"))
            {
                urlToDownload+=Util.AUDIOWNLOADMOTIVEBASEURL+fileUrl+extension;
            }
            Log.e("print url",urlToDownload+"?");
            String urlEncoded = Uri.encode(urlToDownload, ALLOWED_URI_CHARS);
            URL url = new URL(urlEncoded);
            InputStream input = null;
            ///////////////////////////
          if(type.equals("Video")||type.equals("AudioMotive")) {
                URLConnection conection = url.openConnection();
                conection.connect();
                input = new BufferedInputStream(url.openStream());
            }else if(type.equals("Audio")) {
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                url.openConnection();
                httpsURLConnection.setSSLSocketFactory(new TLSSocketFactory());
                input = httpsURLConnection.getInputStream();
            }
           /* URLConnection conection = url.openConnection();
            conection.connect();
            input = new BufferedInputStream(url.openStream());*/


            /////////////////////////////

            // input stream to read file - with 8k buffer
            // Output stream to write file
            // OutputStream output = new FileOutputStream("/sdcard/downloadedfile.jpg");
            String filePath=getOutputMediaFile();
            // Util.filePath=filePath;
            Log.e("m path",filePath+"?");

            OutputStream output = new FileOutputStream(filePath);

            byte data[] = new byte[5120];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                //  Log.e("Print progresss---->",(int)((total*100)/lenghtOfFile)+"????");
                //  publishProgress(""+(int)((total*100)/lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Error: ", e.getMessage());
            return null;
        }

        return null;
    }

    /**
     * Updating progress bar
     * */
    protected void onProgressUpdate(String... progress) {
        // setting progress percentage
        Log.e("Updating----->", Integer.parseInt(progress[0])+"?????");
       // pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    /**
     * After completing background task
     * Dismiss the progress dialog
     * **/
    @Override
    protected void onPostExecute(String file_url) {
        // dismiss the dialog after the file was downloaded
      //  pDialog.dismissDialog(progress_bar_type);
        //pDialog.dismiss();
        asynResponse.processFinish(true);

        // Displaying downloaded image into image view
        // Reading image path from sdcard
       // String imagePath = Environment.getExternalStorageDirectory().toString() + "/downloadedfile.jpg";
        // setting downloaded into image view
        //my_image.setImageDrawable(Drawable.createFromPath(imagePath));
    }
    private  String getOutputMediaFile() {

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory( Environment.DIRECTORY_PICTURES), ".Squad");
        //String fullPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"Squad";
        //File mediaStorageDir = new File(fullPath);


        if (!mediaStorageDir.exists()) {
            //if you cannot make this folder return
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

        // String filePath=mediaStorageDir.getPath() + File.separator + "CT_" + timeStamp + ".mp4";
         //String filePath=mediaStorageDir.getPath() + File.separator + fileUrl + ".mp4";
         String filePath=mediaStorageDir.getPath() + File.separator + fileUrl + extension;

          return filePath;
    }




}