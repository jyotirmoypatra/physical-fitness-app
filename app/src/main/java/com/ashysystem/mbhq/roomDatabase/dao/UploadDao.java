package com.ashysystem.mbhq.roomDatabase.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface UploadDao {

    @Query("select * from upload where isSync=:isSynced")
    Flowable<List<UploadEntity>> getIncompletedItems(boolean isSynced);

    @Insert
    void insertUpload(UploadEntity upload);

    @Query("update upload set isSync=:synced where image_name =:fileName")
    void updatePhoto(String fileName, boolean synced);
}
