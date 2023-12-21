package com.ashysystem.mbhq.roomDatabase.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.ashysystem.mbhq.roomDatabase.entity.MeditationEntity;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface MeditationListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeditation(MeditationEntity meditationEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeditations(List<MeditationEntity> meditationEntities);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    Completable updateMeditation(MeditationEntity meditationEntity);

    @Query("SELECT * FROM meditation")
    Single<List<MeditationEntity>> getAllMeditation();

    @Query("SELECT * FROM meditation WHERE isDownloaded = 1")
    Single<List<MeditationEntity>> getAllDownloadedMeditation();

    @Query("DELETE FROM meditation")
    void deleteAllMeditations();

    @Query("DELETE FROM meditation WHERE meditationId = :meditationId")
    void deleteMeditationById(Integer meditationId);

    @Query("SELECT COUNT(1) FROM meditation WHERE meditationId = :meditationId")
    Single<Integer> getMeditationCountById(Integer meditationId);

    @Query("SELECT * FROM meditation WHERE meditationId = :meditationId")
    Single<MeditationEntity> getMeditationById(Integer meditationId);


}
