package com.ashysystem.mbhq.roomDatabase.dao;



import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.ashysystem.mbhq.model.response.MyAchievementsListInnerModel;
import com.ashysystem.mbhq.roomDatabase.entity.GratitudeEntity;

import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;
import com.ashysystem.mbhq.roomDatabase.newpkg.User;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;



@Dao
public abstract class GratitudeListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract Completable insertGratitude(GratitudeEntity gratitudeEntity);

    @Insert /////////////
    public abstract Completable insertGratitudes(List<GratitudeEntity> gratitudeEntities);

    /////////////////////////////////////////////////////////////////////////////////////////
   /* @Insert
    Completable insertGratitudesNew(GratitudeEntityNew gratitudeEntity);*/
    /*@Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertGrat(GratitudeEntityNew gratitudeEntity);*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(MyAchievementsListInnerModel myAchievementsListInnerModel);

    @Transaction
    public void insertAchievements(List<MyAchievementsListInnerModel> myAchievementsListInnerModelList) {
        for (MyAchievementsListInnerModel model : myAchievementsListInnerModelList) {
            insertAll(model);
        }
    }

    // @Query("SELECT * FROM MyAchievementsListInnerModel")
    // Single<List<MyAchievementsListInnerModel>> getAllAchive(); ////////////


    @Query("SELECT * FROM MyAchievementsListInnerModel")
    public abstract Single<List<MyAchievementsListInnerModel>> getAllAchievements();
    /////////////////////////////////////////////////////////////////////////////////////////

    @Query("SELECT * FROM MyAchievementsListInnerModel LIMIT :position,:count")
    public abstract Single<List<MyAchievementsListInnerModel>> getAllAchive(Integer position,Integer count);

    @Query("DELETE FROM MyAchievementsListInnerModel WHERE id = :id")
    public abstract void deleteAllGratitudeNew_byid(Integer id);

    @Query("DELETE FROM MyAchievementsListInnerModel")
    public abstract void deleteAllGratitudeNew();
    @Query("DELETE FROM MyAchievementsListInnerModel")
    public abstract Completable deleteAllGratitudeNew_();

    @Query("SELECT * FROM gratitude WHERE gratitudeId = :gratitudeId LIMIT 1")
    public abstract Single<GratitudeEntity> getGratitudeById(Integer gratitudeId);

    @Query("SELECT * FROM gratitude")
    public abstract Single<List<GratitudeEntity>> getAllGratitude();

    @Query("SELECT * FROM gratitude WHERE isSync = 0")
    public abstract Single<List<GratitudeEntity>> getAllGratitudeNotSynced();

    @Query("SELECT * FROM gratitude WHERE gratitudeDate LIKE '%' || :gratitudeDate || '%'")
    public abstract Single<List<GratitudeEntity>> getAllGratitudeByDate(String gratitudeDate);

    @Query("DELETE FROM gratitude")
    public abstract void deleteAllGratitude();



    @Query("DELETE FROM gratitude WHERE gratitudeId = :gratitudeId")
    public abstract void deleteGratitudeById(Integer gratitudeId);

    @Query("DELETE FROM gratitude WHERE timeStamp LIKE '%' || :timeStamp || '%'")
    public abstract void deleteGratitudeByTimeStamp(String timeStamp);

    @Query("SELECT COUNT(gratitudeId) FROM gratitude WHERE gratitudeId = :gratitudeId")
    public abstract Single<Integer> getCount(Integer gratitudeId);

}
