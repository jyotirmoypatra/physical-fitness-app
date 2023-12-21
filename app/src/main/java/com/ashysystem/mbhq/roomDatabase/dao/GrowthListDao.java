package com.ashysystem.mbhq.roomDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.ashysystem.mbhq.roomDatabase.entity.GrowthEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface GrowthListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertGrowth(GrowthEntity growthEntity);

    @Query("SELECT * FROM growth WHERE growthId = :growthId LIMIT 1")
    Single<GrowthEntity> getGrowthById(Integer growthId);

    @Query("SELECT * FROM growth")
    Single<List<GrowthEntity>> getAllGrowth();

    @Query("SELECT * FROM growth WHERE isSync = 0")
    Single<List<GrowthEntity>> getAllGrowthNotSynced();

    @Query("SELECT * FROM growth WHERE growthDate LIKE '%' || :growthdate || '%'")
    Single<List<GrowthEntity>> getAllGrowthByDate(String growthdate);

    @Query("DELETE FROM growth")
    void deleteAllGrowth();

    @Query("DELETE FROM growth WHERE growthId = :growthId")
    void deleteGrowthById(Integer growthId);

    @Query("DELETE FROM growth WHERE timeStamp LIKE '%' || :timeStamp || '%'")
    void deleteGrowthByTimeStamp(String timeStamp);

    @Query("SELECT COUNT(growthId) FROM growth WHERE growthId = :growthId")
    Single<Integer> getCount(Integer growthId);

}
