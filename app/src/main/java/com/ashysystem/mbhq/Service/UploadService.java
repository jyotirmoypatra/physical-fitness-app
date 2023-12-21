
package com.ashysystem.mbhq.Service;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;

import com.ashysystem.mbhq.Service.impl.FinisherServiceImpl;
import com.ashysystem.mbhq.Service.impl.ProgressRequestBody;
import com.ashysystem.mbhq.listener.UploadDatabaseCallback;
import com.ashysystem.mbhq.roomDatabase.UploadDatabaseManager;
import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;
import com.ashysystem.mbhq.util.SharedPreference;
import com.ashysystem.mbhq.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressLint("NewApi")
public class UploadService extends JobService {
    private static final String TAG = UploadService.class.getSimpleName();
    UploadDatabaseManager manager;
    JSONObject rootJson;
    FinisherServiceImpl finisherService;

    SharedPreference sharedPreference;

    File mFile = null;
    ProgressRequestBody fileBody = null;

    //String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures/";
    String path = Environment.getExternalStorageDirectory()
            + "/Android/data/"
            + "com.ashysystem.mbhq"
            + "/Files/";

    @Override
    public boolean onStartJob(JobParameters params) {
        HandlerThread handlerThread = new HandlerThread("upload_picture");
        handlerThread.start();

        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Log.e(TAG, "Running!!!!!!!!!!!!!");
                sharedPreference = new SharedPreference(getApplicationContext());
                finisherService = new FinisherServiceImpl(getApplicationContext());
                manager = UploadDatabaseManager.getInstance(getApplicationContext());
                manager.loadIncompleteUploadList(new UploadDatabaseCallback() {
                    @Override
                    public void loadInCompletedList(List<UploadEntity> uploadEntities) {
                        for (UploadEntity entity: uploadEntities){
                            Log.e("List>>>>>>>", "Queued data "+ entity.getImageName());
                            if (entity.getImageName()!=null && entity.getImageName().length()>0) {
                                if (!entity.getSync()) {

                                    try {

                                        //path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pictures/" + entity.getImageName();
                                        path = path + entity.getImageName();
                                        mFile = new File(path);


                                        rootJson = new JSONObject();
                                        rootJson.put("ImageName", entity.getImageName());
                                        rootJson.put("ImageType", entity.getImageType());
                                        rootJson.put("Key", Util.KEY);
                                        rootJson.put("UniqueId", entity.getItemId());
                                        rootJson.put("UserId", Integer.parseInt(sharedPreference.read("UserID", "")));
                                        rootJson.put("UserSessionID", Integer.parseInt(sharedPreference.read("UserSessionID", "")));
                                        Log.e("Path", path);
                                        Log.e("Request", rootJson.toString());
                                        HashMap<String, Object> hashMap = (HashMap<String, Object>) Util.jsonToMap(rootJson);
                                        uploadPhotos(hashMap, entity);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    return;
                                }
                            }
                        }

                    }

                    private void uploadPhotos(HashMap<String, Object> hasdata, UploadEntity uploadEntity) {
                        if (!mFile.exists()){
                            return;
                        }
                        if (mFile != null) {
                            fileBody = new ProgressRequestBody(mFile, new ProgressRequestBody.UploadCallbacks() {
                                @Override
                                public void onProgressUpdate(int percentage) {
                                }

                                @Override
                                public void onError() {

                                }

                                @Override
                                public void onFinish() {
                                }
                            }, 1);

                        }
                        Call<JSONObject> obj = finisherService.addPhotoInUploadQueue(fileBody, finisherService.createPartFromString(Util.getGsonFromObject(hasdata)));
                        obj.enqueue(new Callback<JSONObject>() {
                            @Override
                            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                                Log.e("Upload", response.message() + " Body: "+response.body());
                                if (response.isSuccessful()){
                                    manager.updatePhotoStatus(uploadEntity.getImageName(), true);
                                }
                            }

                            @Override
                            public void onFailure(Call<JSONObject> call, Throwable throwable) {
                                Log.e("UploadFailed", throwable.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onInsertCompleted() {

                    }
                });

                jobFinished(params, true);
            }
        });


        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.e(TAG, "onStopJob() was called");
        return true;
    }
}