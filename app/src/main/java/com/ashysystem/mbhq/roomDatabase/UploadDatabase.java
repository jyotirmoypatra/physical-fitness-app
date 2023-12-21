package com.ashysystem.mbhq.roomDatabase;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ashysystem.mbhq.roomDatabase.dao.UploadDao;
import com.ashysystem.mbhq.roomDatabase.entity.UploadEntity;


@Database(entities = UploadEntity.class, version = 2)
public abstract class UploadDatabase extends RoomDatabase {

    public abstract UploadDao getUploadDao();
}
