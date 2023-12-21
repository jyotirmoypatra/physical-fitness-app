package com.ashysystem.mbhq.roomDatabase;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.room.RoomDatabase;


import com.ashysystem.mbhq.listener.UploadDatabaseCallback;
import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;

import io.reactivex.Completable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class UploadDatabaseManager {

    private static UploadDatabaseManager instance;
    private Context context;
    private static UploadDatabase database;

    public enum Type {
        GRATITUDE,
        GROWTH,
        BUCKET,
        VISSION
    }

    public static UploadDatabaseManager getInstance(Context context) {
        if (instance == null){
            instance = new UploadDatabaseManager(context);
        }
        return instance;
    }

    public UploadDatabaseManager(Context context) {
        this.context = context;
        if (database == null) {
            database = Room.databaseBuilder(context, UploadDatabase.class, "upload_image.db").build();
        }
    }


    public void addData(final UploadDatabaseCallback callback, final Enum type, final int itemId, final String name, final boolean synced){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                int image_type = 0;
                if (type == Type.BUCKET){
                    image_type = 2;
                }else if (type == Type.GRATITUDE){
                    image_type = 4;
                }else if (type == Type.VISSION){
                    image_type = 1;
                }else {
                    return;
                }
                if (image_type == 0){
                    return;
                }

                UploadEntity uploadEntity = new UploadEntity(image_type, itemId, name, synced);
                database.getUploadDao().insertUpload(uploadEntity);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    callback.onInsertCompleted();
                }, throwable -> {
                    throwable.printStackTrace();
                }
        );
    }

    public void addUploadQueueData(final UploadDatabaseCallback callback, UploadEntity uploadEntity){
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                Log.e("Inserting", " >>>>>>>>>>>>>>>>> " + uploadEntity.getImageName());
                database.getUploadDao().insertUpload(uploadEntity);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(
                () -> {
                    callback.onInsertCompleted();
                }, throwable -> {
                    Log.e("Failed", "<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>");
                    throwable.printStackTrace();
                }
        );
    }


    public void updatePhotoStatus(final String name, final boolean synced){
        /*database.getUploadDao().updatePhoto(name, synced)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(uploadEntity -> {
                    Log.e("Update", ">>>>>>>>>Success");
                }, throwable -> {
                    Log.e("UpdateFailed", ">>>>>>>>>Failed");
                });*/

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                database.getUploadDao().updatePhoto(name, synced);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(() -> {
            Log.e("Update", ">>>>>>>>>Success");
        }, throwable -> {
            Log.e("UpdateFailed", ">>>>>>>>>Failed");
        });
    }

    public void loadIncompleteUploadList(final UploadDatabaseCallback callback){
        database.getUploadDao().getIncompletedItems(false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(uploadEntityList -> {
                    callback.loadInCompletedList(uploadEntityList);
                },throwable -> {
                    Log.e("DataNotFound", ">>>>>>>>>>>>>>>> ");
                    throwable.printStackTrace();
                });
    }
}
